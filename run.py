import feedparser
import json
from pymongo import MongoClient
from pymongo import UpdateOne

client = MongoClient(port=27017)
collection = client.newsroom.articles

urls = [
    "https://rss.nytimes.com/services/xml/rss/nyt/World.xml",
    "http://rss.cnn.com/rss/cnn_world.rss",
    "http://feeds.washingtonpost.com/rss/world",
    "http://feeds.bbci.co.uk/news/world/rss.xml",
    "https://www.latimes.com/world-nation/rss2.0.xml"
]

new_rows = []
bulk_op = []

for u in urls:
    feeds = feedparser.parse(u)
    for entry in feeds.entries:
        if "author" in entry.keys():
            new_row = {
                "author":entry.author,
                "link":entry.link,
                "date":entry.published,
                "title":entry.title
            }
            new_rows.append(new_row)
            bulk_op.append(UpdateOne({'link': entry.link},{'$set':new_row},upsert=True))
collection.bulk_write(bulk_op)