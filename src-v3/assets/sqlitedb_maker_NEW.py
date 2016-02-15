#!/usr/bin/python
from __future__ import unicode_literals
import json
from pprint import pprint
import sqlite3
import os
import string
import copy
import time
import re
import unicodedata
import shutil

table_prefix  = "fragment_links_"

def open_json(filename):
	f = open(filename + ".json", 'r')
	return json.load(f)

def parse_json(loaded_json, url_prefix=""):
	parsed_json = []
	for index in loaded_json:
		parsed_json.append({
			"name": index["name"],
			"url": string.lower(url_prefix + str(index["url"]))
 		})
	return parsed_json


def slugify(value):
    """
    STOLEN FROM DJANGO......... HAHAHAHA
    Converts to lowercase, removes non-word characters (alphanumerics and
    underscores) and converts spaces to hyphens. Also strips leading and
    trailing whitespace.
    """
    value = unicodedata.normalize('NFKD', value).encode('ascii', 'ignore').decode('ascii')
    value = re.sub('[^\w\s-]', '', value).strip().lower()
    return re.sub('[-\s]+', '-', value)
	# slugify = allow_lazy(slugify, six.text_type)

database_name = "crumby"
database_file = database_name + ".db"

def main():
	try:
		if os.path.exists(database_file):
			os.remove(database_file)
	except OSError, e: 
		print ("Error: %s - %s." % (e.filename,e.strerror))
	c = sqlite3.connect(database_file)
	conn = c.cursor()
	conn.execute("CREATE TABLE fragment_links_master (name text, url text PRIMARY KEY, thumbnail text, mandatory boolean DEFAULT 0, insert_time DATETIME DEFAULT CURRENT_TIMESTAMP)")
	parsed_json = parse_json(open_json("websites"))
	for json in parsed_json:
		t = (json["name"], json["url"], None, True)
		conn.execute("INSERT OR IGNORE INTO fragment_links_master (name, url, thumbnail, mandatory) VALUES (?,?,?,?)", t)
	c.commit()

main()