# HackTheNorth2021

### Click [here](https://www.youtube.com/watch?v=4kxW-Gs9OlM) to watch a video on how the app works. 

## App screenshots:
![alt text](https://challengepost-s3-challengepost.netdna-ssl.com/photos/production/software_photos/001/662/447/datas/gallery.jpg)
![alt text](https://challengepost-s3-challengepost.netdna-ssl.com/photos/production/software_photos/001/662/448/datas/gallery.jpg)
![alt text](https://challengepost-s3-challengepost.netdna-ssl.com/photos/production/software_photos/001/662/449/datas/gallery.jpg)
![alt text](https://challengepost-s3-challengepost.netdna-ssl.com/photos/production/software_photos/001/662/450/datas/gallery.jpg)

## **Inspiration**

Going through all those newspapers and magazines trying to figure out how your stocks and investments are doing? Your stock value dropping because someone made a social media post? Being anxious and stressed about when to sell or buy?

Investing in stocks is not only a financial investment but also an investment in time. We wanted to build a platform where every investor can get access to all the latest information on their stocks, get curated updates and stock forecasts based on not only numbers but also the community members, and all this in a mobile platform where users have access to this information at the tip of their fingers.

Henceforth emerged the idea of StockClock, an Android application that provides investors maximal information in one package.

## **Introduction**

StockClock is an Android application with a seamless experience and an interactive UI that allows users to add their investment information to provide tailored updates of how their stocks are doing as well as news of the fluctuations in the market. It uses Yahoo Finance data to provide the latest stock market data including the open and close values, as well as day lows and highs. Users will be able to see their stock graphs in real-time and get the latest updates from social media platforms about the performance of different companies.

StockClock gathers information from the Internet about how each stock is performing through web scraping and curates news, magazine, and journal articles about the stock through machine learning algorithms. Users will be able to ask StockClock questions related to their investments; it uses natural language processing to provide answers.

With StockClock, investors will be able to stay up to date about their investments and make informed decisions.

## **Design and Development**

StockClock’s seamless user experience was developed using Java and Android Studio from scratch. The Yahoo Finance API was used to gather information about the latest updates in the stock market. The back-end involved working with Python where the data was gathered and stored in Google Firebase.

Reddit API was used to gather information from Wall Street Bets subreddit to update users regarding market fluctuations so that they will be able to make a smart decision with their investments.

The backend is a Flask web server on Python. This server is run on the cloud using Google's App Engine that has allowed us to quickly deploy and test our application without having to worry about hosting ourselves. The App Engine has been configured to scale up our instances as required (to a limit of course) and also provisions and automatically sets up load balancers so that we have a single endpoint.

The backend is configured to periodically refresh and download new Reddit posts and then re-train the Natural Language Processing Algorithm on the latest data. The stock information is also refreshed.

The data is stored both on the App Engine's disk and a Firebase Realtime Database. The database stores persistent data we need to maintain across instances and reboots.

This was then integrated with the Android application to provide mobile viewers with graphs and real-time information about the performance of their chosen stocks. Furthermore, users will be able to ask questions that are responded via a natural language processing algorithm that was developed using Python.

## **Challenges**

Getting information curated for the users isn’t a very easy task. The first challenge error we ran into was trying to scrape dynamic web pages. We attempted using the BeautifulSoup4 and headless Selenium packages in Python to gather meaningful data from the JavaScript code. However, we found better success using the Reddit API to gather new information tailored for the user. Furthermore, the Yahoo Finance API proved very useful in getting up-to-date information on various stocks.

During the development of the Android application, it was necessary to identify each company and provide information such as stock data, images, descriptions, and news. We overcame the issue by integrating the application with our data from Google Firebase.

## **Accomplishments**

We learned how to work with APIs and web scraping methods in Python. Furthermore, we learned about gathering data from these APIs as JSONs and uploading it to the Firebase server.

We have developed a fully working seamless Android application that provides users with the most up-to-date information on their investments. We have successfully incorporated the Reddit API and Yahoo Finance API into the application, which enabled us to offer that full-fledged application that we had initially imagined.

Our application gets new stock updates every twelve hours with data stored in Google Firebase and it uses a highly efficient natural language processing algorithm (NLTK by Google) to respond to users’ queries. Finally, for many of us, this was our first hackathon and submitting a complete project with real-world applications is just phenomenal.

## **Future**

StockClock has a big future. As the stock market keeps expanding, so do the features of this Android application. Our next steps would include expanding our services to users in other operating systems such as iOS, Windows, macOS, Linux, and web-based solutions. Furthermore, we would broaden our information coverage from just stocks to cryptocurrency and more modern forms of investment.

Our data sources would also widen incorporating more formerly accurate presses and websites. The machine-learning algorithm could be improved to provide better-consolidated results for investors and our natural language processing could be fed more data for better responses. Hack the North 2021 is only just the beginning for StockClock.
