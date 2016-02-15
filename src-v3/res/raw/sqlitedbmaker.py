#!/usr/bin/python
import json
from pprint import pprint
import sqlite3
import os
import string

database_name = "crumby"
database_file = database_name + ".db"
alphabet = "abcdefghijklmnoprstuvwy"
vowels = "aeiou"
table_prefix  = "fragment_links_"
master_index = 0


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
			"display_name": index[title_key],
			"url_extension": string.lower(url_prefix + str(index[url_key]))
 		})
	return parsed_json

def create_master_table(conn):
	conn.execute("CREATE TABLE " + table_prefix + "master (id int PRIMARY KEY, name text, extension text)")
	return table_prefix + "master"

def create_suffix_table(conn, suffix):
	conn.execute('''CREATE TABLE ''' + table_prefix + suffix + ''' (link_id int, FOREIGN KEY(link_id) REFERENCES fragment_links_master(id))''')
	print table_prefix + suffix
	return table_prefix + suffix

def insert_into_master_table(conn, index):
	global master_index
	print "Inserting into master index " + str(master_index)
	master_index += 1
	t = (master_index, index["display_name"], index["url_extension"])
	conn.execute("INSERT INTO " + table_prefix + "master VALUES (?,?,?)", t)

def insert_into_suffix_table(conn, suffix, key):
	conn.execute("INSERT INTO " + table_prefix + suffix + " VALUES (?)", (key,))

#TODO: Test with combinations as opposed to start indexes...
# def generate_fragment_link_table(conn, name, parsed_json, bin_index):
# 	table_name = 'fragment_links_' + name  + '_'
# 	tables = []
# 	alphabet = "abcdefghijklmnopqrstuvwxyz"
# 	vowels = "aeiou"
# 	for character in alphabet:
# 		charcheck = character in vowels
# 		# for character1 in alphabet:
# 		# 	charcheck1 = character1 in vowels
# 		# 	two_vowels = charcheck and charcheck1
# 		# 	same_characters = character1 == character
# 		# 	if not(two_vowels) and not(same_characters):

# 		# TODO: Figure out to recursively increase the entropy (e.g. touhou)
# 		tables.append(create_table(conn, table_name, character))
# 	tables.append(create_table(conn, table_name, "etc"))
# 	for index in parsed_json:
# 		offset = "etc"
# 		try:
# 		 	start = string.lower(index[bin_index][-2])
# 			if table_name + "_" + start in tables:
# 				offset = start
# 		except IndexError:
# 			pass
# 		print offset
# 		insert_into_table(conn, table_name,z index, offset)
# 	print len(parsed_json)
# 	return tables


def generate_all_tables(conn):
	tables = []
	tables.append(create_master_table(conn))
	for character in alphabet:
		charcheck = character in vowels
		for character1 in alphabet:
			charcheck1 = character1 in vowels
			two_vowels = charcheck and charcheck1
			same_characters = character1 == character
			if not(two_vowels) and not(same_characters):
			# TODO: Figure out to recursively increase the entropy (e.g. touhou)
				tables.append(create_suffix_table(conn, character + character1))
	tables.append(create_suffix_table(conn, "etc"))
	return tables

def generate_fragment_link_table(conn, name, parsed_json, bin_index, tables):
	global master_index
	for index in parsed_json:
		insert_into_master_table(conn, index)
		gen = string.lower(index[bin_index])
		pairs_detected = []
		for i in range(0, len(gen) - 1):
			suffix = "etc"
			start = gen[i] + gen[i+1] 
			if table_prefix + start in tables:
				suffix = start
			if suffix not in pairs_detected:
				# print "Inserting " + str(master_index) + " into _" + suffix
				pairs_detected.append(suffix)
				insert_into_suffix_table(conn, suffix, master_index)
	print len(parsed_json)

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
	tables = generate_all_tables(conn)
	generate_fragment_link_table(conn, "danbooru", danbooru, "display_name", tables)
	generate_fragment_link_table(conn, "imgur", imgur, "url_extension", tables)
	c.commit()
	test_generated_table(conn, tables)
	# test_generated_table(conn, tables)

	# Save (commit) the changes
	

	# We can also close the connection if we are done with it.
	# Just be sure any changes have been committed or they will be lost.

	# with open('dump.sql', 'w') as f:
	#     for line in conn.iterdump():
	#         f.write('%s\n' % line)

	conn.close()

main()