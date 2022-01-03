import yfinance as yf
import json

def getStockData(which):
    stock = yf.Ticker(which)
    hist = stock.history(period="max")
    hist = hist[-30:]
    longname = stock.info['longName'].split(' ')[0]
    for i in ['.',',','/']:
        if i in longname:
            longname=longname[:longname.index(i)]
    del hist['Volume']
    history = json.loads(hist.to_json())
    quarterly = json.loads(stock.quarterly_earnings.to_json())

    final = {}
    final['name'] = which
    final['quarterly'] = quarterly
    final['history'] = history
    final['longname'] = longname

    if 'Revenue' not in final['quarterly'].keys():
        return 0

    return final

#getStockData('AMZN')