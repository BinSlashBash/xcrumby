#!/usr/bin/python
import json
from pprint import pprint
import sqlite3
import os
import string
import copy

alphanumeric = "abcdefghijklmnoprstuvwxyz1234567890/:."
vowels = "aeiou"
table_prefix  = "fragment_links_"

def dump(file_name, output):
	with open(file_name + ".json", 'wb') as fp:
		print file_name + " " + str(len(output))
		json.dump(output, fp)

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

database_name = "crumby"
database_file = database_name + ".db"

#TODO: now that this class finally works... it needs to be rearchitected to follow proper OOP. UGH.
class tree_generator(object):
	def __init__(self, index_type, start_tree={}, database=None):
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

	def test(self):
		self.tree = open_json("compiled_tree_substring_" + self.index_type)
		self.test_leaf(self.tree, "", "")

	def test_leaf(self, tree, leaves, tabs):
		if not tree:
			return
		for leaf in tree:
			if not isinstance(tree[leaf], dict):
				for index in tree[leaf]:
					t = (index, )
					self.conn.execute("SELECT * FROM fragment_links_master WHERE id=(?)", t)
					fetched_row = self.conn.fetchone()
					replace_index = 2
					if self.index_type == "name":
						replace_index = 1
					row = [fetched_row[0], fetched_row[1], fetched_row[2], fetched_row[3]]
					row[replace_index] = fetched_row[replace_index].replace(leaves, " >>>> " + leaves + " <<<< ", 1)
					print tabs, leaves, row

			else:
				print tabs, leaf, "==============="
				self.test_leaf(tree[leaf], leaves + leaf, tabs + "\t")

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

	def add_tree_from_json(self, gallery):
		parsed_json = parse_json(open_json(gallery), "name", "url")
		root_name = self.prepare_urls(parsed_json, gallery)
		#A root name will be useless for this calculation. TODO: refactor name into a base class, and url into a child class.
		tree, leaf, key = self.add_to_tree(self.tree, root_name)
		new_tree = self.build_tree(parsed_json, "")
		if leaf is None:
			self.tree = new_tree
		elif key != "":
			tree[leaf][key] = new_tree
		else:
			tree[leaf] = new_tree 

	def compile(self):
		insert_links = False
		if not self.database: # Pull database out into another class.
			self.conn.execute("CREATE TABLE fragment_links_master (id int PRIMARY KEY, name text, url text, base_url text)")
			self.database = {}
			self.database_id = 1
			insert_links = True
		compiled_tree = copy.deepcopy(self.tree)
		self.index_tree(compiled_tree)
		if insert_links:
			for index in self.database:
				gallery = self.database[index]
				t = (gallery["id"], gallery["gallery"]["name"], gallery["gallery"]["url"], gallery["gallery"]["base_url"])
				self.conn.execute("INSERT INTO fragment_links_master  VALUES (?,?,?,?)", t)
			self.c.commit()
		dump("full_tree_substring_" + self.index_type, self.tree)
		dump("compiled_tree_substring_"+ self.index_type, compiled_tree)

	def add_to_tree(self, tree, key):
		for leaf in tree:
			if key[0:len(leaf)] == leaf:
				key = key[len(leaf):]
				child = self.add_to_tree(tree[leaf], key)
				if child[1] is None:
					return tree, leaf, key
				else:
					return child[0], child[1], child[2]
		return tree, None, None

	def index_tree(self, tree):
		if not tree:
			return
		for leaf in tree:
			if not isinstance(tree[leaf], dict):
				leaf_list = tree[leaf]
				for i, gallery in enumerate(leaf_list):
					url = gallery["url"].lower() # the url is guaranteed to be unique so we can use that as n index.
					if url not in self.database:
						self.database[url] = {
							"id": self.database_id,
							"gallery": gallery
						}
						tree[leaf][i] = self.database_id
						self.database_id = len(self.database) + 1
					else:
						tree[leaf][i] = self.database[url]["id"]
			else:
				self.index_tree(tree[leaf])
				

	def build_tree(self, parsed_json, base):
		tree = {}
		print base
		if not tree:
			for character in alphanumeric:
				tree[character] = []
			tree["etc"] = []
		for i, gallery in enumerate(parsed_json):
			touched = False
			for character in alphanumeric:
				lookup = base + character
				if lookup in gallery[self.index_type].lower():
					tree[character] += [gallery]
					touched = True
			if not touched:
				tree["etc"] += [gallery]
		explode_node = []
		remove_node = []
		for node in tree:
			if (len(tree[node]) == 0):
				remove_node.append(node)
			if node == "etc":
				continue
			if (len(tree[node]) > 100):
				explode_node.append(node) 
		for node in explode_node:
			list_of_nodes = tree[node]
			tree[node] = self.build_tree(list_of_nodes, base + node)
		for node in remove_node:
			tree.pop(node)
		return tree

# write test method to make sure that the trees are actualy working.

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

	# url_tests = [
	# 	("img .com test", True),
	# 	("img .com", True),
	# 	("pics", True),
	# 	("asdfasdfasdfadsfasdf", False)
	# ]

	url_maker.test()
	name_maker.test()

main()