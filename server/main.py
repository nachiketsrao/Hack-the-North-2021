import os
import flask
import firebase_admin
import time
from sys import platform
from firebase_admin import db
from apscheduler.schedulers.background import BackgroundScheduler
from flask import request, jsonify
from numpy import cumproduct
from helper import reddit_api as rApi, text_nlp as nlp, yfinance_api as yf, good_or_bad as GoB

current_dir = os.path.dirname(os.path.abspath(__file__))
if 'win' in platform:
    pathConn = '\canvas-antler-326322-firebase-adminsdk-92hda-251ccf610b.json'
else:
    pathConn = '/canvas-antler-326322-firebase-adminsdk-92hda-251ccf610b.json'
cred_obj = firebase_admin.credentials.Certificate(current_dir+pathConn)
default_app = firebase_admin.initialize_app(cred_obj, {
	'databaseURL': 'https://canvas-antler-326322-default-rtdb.firebaseio.com/'
	})

app = flask.Flask(__name__)
app.config["DEBUG"] = True

knownStocks = []

def getKnownStocks():
    global knownStocks
    ref = db.reference('/stocks')
    all_data = ref.get()
    knownStocks = list(all_data.keys())
    print(knownStocks)

@app.route('/', methods=['GET'])
def home():
    return 'StockClock API v1'.encode()

@app.route('/argument-test', methods=['GET'])
def argtest():
    results = {}
    for i in request.args:
        results[i] = request.args.get(i)
    return jsonify(results)

@app.route('/add-stock', methods=['GET'])
def addCompany():
    results = {}
    if 'stock' in request.args:
        stock = request.args.get('stock').upper()
        if stock in knownStocks:
            results['stock-status'] = 'Exists'
        else:
            data = yf.getStockData(stock)
            if data != 0:
                ref = db.reference('/stocks/'+data['name'])
                ref.set(data)
                rApi.code(data['name'])
                nlp.learn_text(data['name'])
                redditView = GoB.check(GoB.get_text(stock))
                ref = db.reference('/stocks/'+stock+'/redditView')
                ref.set(redditView)
                #knownStocks.append(stock.upper())
                #getKnownStocks()
                results['stock-status'] = 'Stock added'
            else:
                results['stock-status'] = '0'
    return jsonify(results)

@app.route('/query-nlp', methods=['GET'])
def queryNLP():
    results = {}
    if (('query' in request.args) and ('stock' in request.args)):
        ref = db.reference('/stocks/'+request.args.get('stock').upper()+'/longname')
        company = ref.get()
        results['answer'] = nlp.ask_questions(company , request.args.get('query'))
    else:
        bad_request(400)
    return jsonify(results)

def newestValueFinder(dict):
    closeValues = list(dict.values())
    closeKeys = [int(x) for x in list(dict.keys())]
    latestIndex = closeKeys.index(max(closeKeys))
    return closeValues[latestIndex]

@app.route('/latest-close', methods=['GET'])
def lastClose():
    results = {}
    if 'stock' in request.args:
        ref = db.reference('/stocks/'+request.args.get('stock').upper()+'/history/Close')
        close_all = ref.get()
        results['prev-close'] = newestValueFinder(close_all)
    else:
        bad_request(400)
    return jsonify(results)

@app.route('/latest-all', methods=['GET'])
def lastAll():
    results = {}
    if 'stock' in request.args:
        for i in ['Close','Dividends','High','Low','Open','Stock Splits']:
            ref = db.reference('/stocks/'+request.args.get('stock').upper()+'/history/'+i)
            values_all = ref.get()
            results[i] = newestValueFinder(values_all)
        ref = db.reference('stocks/'+request.args.get('stock').upper()+'/redditView')
        redditView = ref.get()
        results['redditView'] = redditView
    else:
        bad_request(400)
    return jsonify(results)

@app.route('/graph-coords', methods=['GET'])
def graphCoords():
    results = {}
    if 'stock' in request.args:
        ref = db.reference('/stocks/'+request.args.get('stock').upper()+'/history/Close')
        values_all = ref.get()
        results = values_all
    else:
        bad_request(400)
    return jsonify(results)


@app.errorhandler(404)
def page_not_found(e):
    return 'Unknown Path | StockClock API v1'.encode(), 404

@app.errorhandler(400)
def bad_request(e):
    return 'Bad request | StockClock API v1'.encode(), 400

def loopedYFinanceTask():
    ref = db.reference('/stocks')
    stocks_all=ref.get()
    del stocks_all['TEST']
    for stock in stocks_all.keys():
        data = yf.getStockData(stock)
        if data == 0:
            continue
        else:
            ref = db.reference('/stocks/'+(data['name']).upper())
            ref.set(data)

def loopedRedditTask():
    ref = db.reference('/stocks')
    stocks_all=ref.get()
    del stocks_all['TEST']
    for stock in stocks_all.keys():
        stock = stocks_all[stock]['longname']
        rApi.code(stock)
    print('Completed Reddit Task')
    print('Initializing NLP Task')
    teachNLP()
    print('Completed NLP Task')
    print('Initializing Evaluation Task')
    evaluateReddit()

def teachNLP():
    ref = db.reference('/stocks')
    stocks_all=ref.get()
    del stocks_all['TEST']
    for stock in stocks_all.keys():
        stock = stocks_all[stock]['longname']
        nlp.learn_text(stock)

def evaluateReddit():
    ref = db.reference('/stocks')
    stocks_all=ref.get()
    del stocks_all['TEST']
    for stock in stocks_all.keys():
        stockLN = stocks_all[stock]['longname']
        redditView = GoB.check(GoB.get_text(stockLN))
        ref = db.reference('/stocks/'+stocks_all[stock]['name'].upper()+'/redditView')
        ref.set(redditView)


print('Initializing Schedulers')
scheduler = BackgroundScheduler()
scheduler.add_job(loopedYFinanceTask, 'interval', hours=24)
scheduler.add_job(loopedRedditTask, 'interval', hours=1)
scheduler.start()
print('Completed Schedulers')

print('Initializing FetchStock Task')
getKnownStocks()
print('Completed FetchStock Task')
print('Initializing YFinance Task')
#loopedYFinanceTask()
print('Completed YFinance Task')
print('Initializing Reddit Task')
#loopedRedditTask()
print('Completed Evaluation Task')
app.run(host='0.0.0.0', port=8080)