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

current_milli_time = lambda: int(round(time.time() * 1000))
alphanumeric = "abcdefghijklmnoprstuvwxyz1234567890/:."
vowels = "aeiou"
table_prefix  = "fragment_links_"

def dump(directory, file_name, output):
	file_name = slugify(file_name)
	with open(directory + "/" + file_name + ".json", 'wb') as fp:
		print file_name + " " + str(len(output))
		json.dump(output, fp)
	return file_name

def open_json(filename):
	f = open(filename + ".json", 'r')
	return json.load(f)

def parse_json(loaded_json, name_key, url_key, url_prefix=""):
	parsed_json = []
	for index in loaded_json:
		parsed_json.append({
			"name": index[name_key],
			"url": string.lower(url_prefix + str(index[url_key]))
 		})
	return parsed_json


def slugify(value):
    """
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

#TODO: now that this class finally works... it needs to be rearchitected to follow proper OOP. UGH.
class tree_generator(object):
	def __init__(self, index_type, start_tree={}, database=None):
		self.extra_tables = []
		self.index_type = index_type
		self.c = sqlite3.connect(database_file)
		self.conn = self.c.cursor()
		self.database = database
		self.database_id = 1
		if database:
			self.database_id = len(database) + 1
		# TODO: Write up test cases
		# we can't sort because it may be (for example: a)

		self.tree = start_tree

	def add_trees(self, galleries):
		for gallery in galleries:
			self.add_tree_from_json(gallery)

	def prepare_urls(self, parsed_json, gallery_name):
		urls = []
		for gallery in parsed_json:
			urls.append(gallery["url"])
		root_name = os.path.commonprefix(urls)
		for gallery in parsed_json:
			gallery["url"] = gallery["url"].replace(root_name, "")
			gallery["base_url"] = gallery_name[0]
		if self.index_type == "url": #TODO: refactor this into classes. really.
			return root_name
		return ""

	def test_multiple_finds(self, *args):
		for search in args:
			results = []
			start = current_milli_time()
			results = self.query(search)
			print search
			print "\t\t Took ", current_milli_time()  - start, " milliseconds"
			pprint(results)

	def get_values(self, results, search_tree):
		try:
			results.append(search_tree["_v"])
		except KeyError:
			pass
		for character in search_tree:
			if character != "_v":
				self.get_values(results, search_tree[character])
			if len(results) > 10:
				break

	def try_leaves(self, search_tree, next_search, results, root):
		for character1 in search_tree:
			if character1 != "_v":
				self.find(next_search, search_tree[character1], results, root + character1) #If this is true, once we don't need to back track.

	def query(self, search):
		results = []
		self.find(search, self.compiled_tree, results, "")
		for i, result in enumerate(results):
			t = (result, )
			self.conn.execute("SELECT * FROM fragment_links_master WHERE id=(?)", t)
			results[i] = self.conn.fetchone()
		return results


	# ROSSUM"S UNIVERSAL EQUATION FOR LIFE.
	def find(self, search, search_tree, results, root):
		# time.sleep(.1)
		if len(results) > 10:
			return True
		# print root, search
		i = 0
		found_tree = {}
		found = False
		while i < len(search):
			character = search[i]
			m = 0
			if character != "_v":
				# print "\tLooking for character", character
				next_character = character
				if character == " ":
					m += 1
					next_character = search[i+m]
					# print "\tSpace detected. Trying next character", next_character
				if next_character in search_tree:
					found_tree = search_tree
					search_tree = search_tree[next_character]
					root += next_character
					# print "\tCharacter found in current branch!"
					found = True
				else:
					# print "\tCan't find the character in current branch."
					if found:
						# print "HEYYYYYYYYYYYYYYYYYYYYYYYY"
						self.try_leaves(found_tree, search, results, root)
					next_search = search
					if character == " ":
						next_search = search[i:]
					self.try_leaves(search_tree, next_search, results, root)
					
					# print "\t Cannot add values", root, "FOUND:", found
					return False
			i += 1
			i += m
		self.get_values(results, search_tree)
		return True

	def add_tree_from_json(self, gallery):
		parsed_json = parse_json(open_json(gallery), "name", "url")
		self.build_tree(parsed_json)
		print "TREE BUILT!"

	def compile(self):
		insert_links = False
		if not self.database: # Pull database out into another class.
			self.conn.execute("CREATE TABLE fragment_links_master (id int PRIMARY KEY, name text, url text)")
			self.database = {}
			self.database_id = 1
			insert_links = True
		self.compiled_tree = copy.deepcopy(self.tree)
		self.index_tree(self.compiled_tree)
		if insert_links:
			for index in self.database:
				gallery = self.database[index]
				t = (gallery["id"], gallery["gallery"]["name"], gallery["gallery"]["url"])
				self.conn.execute("INSERT INTO fragment_links_master  VALUES (?,?,?)", t)
			self.c.commit()
		pruned_tree = copy.deepcopy(self.compiled_tree)
		self.prune_tree(pruned_tree, "")
		dump("branches", "root_" + self.index_type, pruned_tree)


	def prune_tree(self, tree, substring):
		if not tree:
			return 0
		count = 1
		for leaf in tree:
			if leaf != "_v":
				leaf_name = substring + leaf
				leaf_count = self.prune_tree(tree[leaf], leaf_name)
				if leaf_count > 500:
					tree[leaf] = dump("branches", self.index_type + "_" + leaf_name, tree[leaf])
				else: 
					count += leaf_count
		return count

	def index_tree(self, tree):
		if not tree:
			return
		for leaf in tree:
			if leaf == "_v":
				gallery = tree[leaf]
				# leaf_list = tree[leaf]
				# for i, gallery in enumerate(leaf_list):
				url = gallery["url"].lower() # the url is guaranteed to be unique so we can use that as n index.
				if url not in self.database:
					self.database[url] = {
						"id": self.database_id,
						"gallery": gallery
					}
					tree[leaf] = self.database_id
					self.database_id = len(self.database) + 1
				else:
					tree[leaf] = self.database[url]["id"]
			else:
				self.index_tree(tree[leaf])


	def build_tree(self, parsed_json):
		tree = self.tree
		for gallery in parsed_json:
			next_tree = self.tree
			for character in gallery[self.index_type].lower():
				try:
					next_tree = next_tree[character]
				except KeyError:
					next_tree[character] = {}
					next_tree = next_tree[character]
			try:
				next_tree["_v"] = gallery
			except KeyError:
				next_tree = {
					"_v": gallery
				}

def main():
	try:
		os.remove(database_file)
	except OSError, e: 
		print ("Error: %s - %s." % (e.filename,e.strerror))
	url_start_tree = {
						"http://": {
						}
					}
	url_maker = tree_generator("url", url_start_tree)
	url_maker.add_trees(["reddit", "danbooru"])
	url_maker.compile()

	name_maker = tree_generator("name", database=url_maker.database)
	name_maker.add_trees(["reddit", "danbooru"])
	name_maker.compile()

	url_maker.test_multiple_finds("touhou", "whack a cat", "australia", "dog")
	name_maker.test_multiple_finds("touhou", "whack a cat", "australia", "dog")

main()