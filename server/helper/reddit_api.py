from re import sub
import praw
import os

def code(company):
    try:
        os.makedirs(f"corpus_{company}")  
    except FileExistsError:
        pass
    reddit = praw.Reddit(client_id='4X4rvkrRinvj3Gk3h3dOMw', \
                        client_secret='syLgqCrG7niaoNVbRzGAknebU2TCHQ', \
                        user_agent='Stock Bot', \
                        username='not_an_api_bot', \
                        password='ApiBotForHackTheNorth')

    subreddit = reddit.subreddit('wallstreetbets').search(company)
    subreddit2 = reddit.subreddit('StockMarket').search(company)
    #sussy = subreddit.top(limit = 10)
    temp = 0
    for i in subreddit:
        if len(i.selftext) == 0:
            continue
        temp +=1

        f = open(f"./corpus_{company}/{temp}_reddit.txt", 'w', encoding='utf-8')
        f.write(i.selftext)
        f.close()