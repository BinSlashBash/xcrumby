#!/usr/bin/python
import json
from pprint import pprint
import sqlite3
import os
import string

database_name = "crumby"
database_file = database_name + ".db"

def dump(file_name, output):
	with open(file_name + ".json", 'wb') as fp:
		print file_name + " " + str(len(output))
		json.dump(output, fp)

def open_json(filename):
	f = open(filename, 'r')
	return json.load(f)

def parse_json(loaded_json, title_key, url_key, url_prefix=""):
	parsed_json = []
	for index in loaded_json:
		parsed_json.append({
			"display_name": index["name"],
			"url_extension": string.lower(url_prefix + str(index[url_key]))
 		})
	return parsed_json

def create_table(conn, table_name, character):
	generated_table_name = table_name + "_" + character
	print generated_table_name
	conn.execute('''CREATE TABLE ''' + generated_table_name + ''' (name text, extension text)''')
	return generated_table_name

def insert_into_table(conn, table_name, index, offset):
	generated_table_name = table_name + "_" + offset
	t = (index["display_name"], index["url_extension"])
	conn.execute("INSERT INTO " + generated_table_name + " VALUES (?,?)", t)

#TODO: Test with combinations as opposed to start indexes...
def generate_fragment_link_table(conn, name, parsed_json, bin_index):
	table_name = 'fragment_links_' + name  + '_'
	tables = []
	alphabet = "abcdefghijklmnopqrstuvwxyz"
	vowels = "aeiou"
	for character in alphabet:
		charcheck = character in vowels
		# for character1 in alphabet:
		# 	charcheck1 = character1 in vowels
		# 	two_vowels = charcheck and charcheck1
		# 	same_characters = character1 == character
		# 	if not(two_vowels) and not(same_characters):

		# TODO: Figure out to recursively increase the entropy (e.g. touhou)
		tables.append(create_table(conn, table_name, character))
	tables.append(create_table(conn, table_name, "etc"))
	for index in parsed_json:
		offset = "etc"
		try:
		 	start = string.lower(index[bin_index][0])
			if table_name + "_" + start in tables:
				offset = start
		except IndexError:
			pass
		print offset
		insert_into_table(conn, table_name, index, offset)
	print len(parsed_json)
	return tables

def test_generated_table(conn, tables):
	for table in tables:
		print "SELECT * from " + table
		conn.execute("SELECT COUNT(*) from " + table)
		print conn.fetchone()


def main():
	danbooru = parse_json(open_json("pools.json"), "name", "id")
	imgur = parse_json(open_json("reddit.json"), "title", "url")
	try:
	    os.remove(database_file)
	except OSError, e: 
	    print ("Error: %s - %s." % (e.filename,e.strerror))

	c = sqlite3.connect(database_file)
	conn = c.cursor()

	danbooru_tables = generate_fragment_link_table(conn, "danbooru", danbooru, "display_name")
	imgur_tables = generate_fragment_link_table(conn, "imgur", imgur, "url_extension")
	c.commit()
	test_generated_table(conn, danbooru_tables)
	test_generated_table(conn, imgur_tables)

	# Save (commit) the changes
	

	# We can also close the connection if we are done with it.
	# Just be sure any changes have been committed or they will be lost.

	# with open('dump.sql', 'w') as f:
	#     for line in conn.iterdump():
	#         f.write('%s\n' % line)

	conn.close()

main()