#!/usr/bin/pypy
import json
from pprint import pprint
import sqlite3
import os
import string
import copy
import time

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

	def _test_set(self, a, b, first_set_name, second_set_name):
		setone = set(a)
		settwo = set(b)
		if len(setone.intersection(settwo)) > 500:
			print "\t", first_set_name, "(", len(a), ")", "-", second_set_name, "(", len(b), ")", "=", len(setone.intersection(settwo))

	def _test_subset(self, a, first_set_name, second_set_name):
		if isinstance(self.compiled_tree[second_set_name], list):
			self._test_set(a, self.compiled_tree[second_set_name], first_set_name, second_set_name + "*")
		else:
			for fourth_leaf in self.compiled_tree[second_set_name]:
				self._test_set(a, self.compiled_tree[second_set_name][fourth_leaf], first_set_name, second_set_name + fourth_leaf)

	def test_set_theory(self):
			assert(self.compiled_tree is not None)
			for leaf in self.compiled_tree:
				if isinstance(self.compiled_tree[leaf], list):
					a = self.compiled_tree[leaf]
					for third_leaf in self.compiled_tree:
						self._test_subset(a, leaf + "*", third_leaf)
				else:
					for second_leaf in self.compiled_tree[leaf]:
						a = self.compiled_tree[leaf][second_leaf]
						for third_leaf in self.compiled_tree:
							self._test_subset(a, leaf + second_leaf, third_leaf)

	def test(self):
		self.tree = open_json("compiled_tree_subsequence_" + self.index_type)
		self.test_leaf(self.tree, "", "")

	def highlight(self, highlight, index):
		return highlight[:index] + " >>> " + highlight[index] + " <<< " + highlight[index+1:] 

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
					search_string = leaves
					if leaf != "etc":
						search_string += leaf
					# print tabs, search_string, "          ", row
					lower = fetched_row[replace_index].lower()
					offset = 0
					for character1 in search_string:
						# print tabs, lower, "Finding:", character1
						index1 = lower.find(character1)
						offset += index1
						row[replace_index] = self.highlight(row[replace_index], offset)
						offset += 11
						lower = lower[index1+1:]
					print tabs, search_string, "          ", row

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
		self.compiled_tree = copy.deepcopy(self.tree)
		self.index_tree(self.compiled_tree)
		if insert_links:
			for index in self.database:
				gallery = self.database[index]
				t = (gallery["id"], gallery["gallery"]["name"], gallery["gallery"]["url"], gallery["gallery"]["base_url"])
				self.conn.execute("INSERT INTO fragment_links_master  VALUES (?,?,?,?)", t)
			self.c.commit()
		# dump("full_tree_" + self.index_type, self.tree)
		dump("compiled_tree_subsequence_"+ self.index_type, self.compiled_tree)

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
		# print base
		if not tree:
			for character in alphanumeric:
				tree[character] = []
			tree["etc"] = []
		for i, gallery in enumerate(parsed_json):
			touched = False
			lower = gallery[self.index_type].lower()
			for character1 in base:
				lower = lower[lower.find(character1)+1:]
			for character in alphanumeric:
				if character in lower:
					#TODO: SORT BY FIRST INDEX OF BASE.
					tree[character] += [gallery]
					touched = True
			if not touched:
				tree["etc"] += [gallery]
		explode_node = []
		remove_node = []
		for node in tree:
			if (len(tree[node]) == 0):
				remove_node.append(node)
				continue
			if node == "etc":
				continue
			if (len(tree[node]) > 1000 and len(base) < 5):
				explode_node.append(node)
			# elif len(tree[node]) > 1000:
			# 	self.extra_tables.append(len(tree[node]))
			# 	tree[node] = tree[node][:1000] 
				
		# print explode_node
		# time.sleep(10)
		for node in explode_node:
			list_of_nodes = tree[node]
			tree[node] = self.build_tree(list_of_nodes, base + node)
		for node in remove_node:
			tree.pop(node)
		return tree

# write test method to make sure that the trees are actualy working.
# t e f
# t contains all the galleries that have the letter "t"
# e contains all the galleries that have the letter "e"
# t e = all the galleries that have the letter "t" followed by "e" followed by "f".
# t e f = all the galleries that have the letter "t" followed by "e" followed by "f"
# t - (e f) the intersection of all the galleries that have the letter "t" and  "e" followed by "f"
# this means galleries that could have t before or t after (e f)
# (t t) - (e f)

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

	# for table in name_maker.extra_tables:
	# 	print table

	# # print name_maker.extra_table

	# url_maker.test()
	# name_maker.test()

	url_maker.test_set_theory()
	name_maker.test_set_theory()

main()