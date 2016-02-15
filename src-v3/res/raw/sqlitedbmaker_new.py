#!/usr/bin/python
import json
from pprint import pprint
import sqlite3
import os
import string
import copy

database_name = "crumby"
database_file = database_name + ".db"
alphabet = "abcdefghijklmnoprstuvwxyz1234567890"
vowels = "aeiou"
table_prefix  = "fragment_links_"

def dump(file_name, output):
	with open(file_name + ".json", 'wb') as fp:
		print file_name + " " + str(len(output))
		json.dump(output, fp)

def open_json(filename):
	f = open(filename, 'r')
	return json.load(f)

def parse_json(loaded_json, name_key, url_key, url_prefix=""):
	parsed_json = []
	for index in loaded_json:
		parsed_json.append({
			"name": index[name_key],
			"url": string.lower(url_prefix + str(index[url_key]))
 		})
	return parsed_json

class db_maker(object):
	def __init__(self):
		try:
			os.remove(database_file)
		except OSError, e: 
			print ("Error: %s - %s." % (e.filename,e.strerror))
		self.c = sqlite3.connect(database_file)
		self.conn = self.c.cursor()
		self.url_bases = {
			"http://": {
				"imgur.com": {
				},
				"danbooru.com": {
				}
			}
		}
		self.name_bases = {}

	def done(self):
		self.conn.close()

	def build(self):
		self.urlModeOn()
		self.build_sql()
		self.nameModeOn()
		self.build_sql()

	def build_sql(self):
		self.conn.execute("CREATE TABLE fragment_links_master_" + self.index_type + " (name text, url text)")
		self.bases_build = copy.deepcopy(self.bases)
		self.build_bases(start_index=1, bases=self.bases_build)
		self.c.commit()
		dump("tree_" + self.index_type, self.bases_build)

	def build_bases(self, start_index, bases=None, name=None, bases_parent=None):
		if bases is None or (not isinstance(bases, list) and not isinstance(bases, dict)):
			return
		end_index = start_index
		remove_tree = False
		for base in bases:
			if isinstance(bases, dict):
				end_index = self.build_bases(start_index=end_index, bases=bases[base], name=base, bases_parent=bases)
			else:
				remove_tree = True
				t = (base["name"], base["url"])
				self.conn.execute("INSERT INTO fragment_links_master_" + self.index_type + " VALUES (?,?)", t)
				end_index += 1
				pass
		if bases_parent is not None:
			if remove_tree:
				bases_parent[name] = {}
			bases_parent[name]["s"] = start_index
			bases_parent[name]["e"] = end_index
		return end_index


	def test(self):
		self.urlModeOn()
		print "Url Fragment links tested: ", self.test_bases(self.bases)
		# on each index... include the characters that come next.
		self.nameModeOn()
		print "Name Fragment links tested: ", self.test_bases(self.bases)

	def test_bases(self, bases=None, tabs=""):
		if bases is None or (not isinstance(bases, list) and not isinstance(bases, dict)):
			return
		print tabs , "Length of base level: ", len(bases)
		count = 0
		for base in bases:
			print tabs , "Index at Base Level: ", base
			try:
				count += self.test_bases(bases[base], tabs + "\t")
			except TypeError:
				count += 1
				pass
		return count


	def urlModeOn(self):
		self.bases = self.url_bases
		self.index_type = "url"

	def nameModeOn(self):
		self.bases = self.name_bases
		self.index_type = "name"

	def convert_gallery_to_dict(self, gallery):
		parsed_json = parse_json(open_json(gallery + ".json"), "name", "url")
		self.urlModeOn()
		self.create_new_base(parsed_json)
		self.nameModeOn()
		self.create_new_base(parsed_json)

	def create_new_base(self, parsed_json):
		last_url = None
		urls = []
		for gallery in parsed_json:
			urls.append(gallery[self.index_type])
		self.add_to_bases(self.bases, os.path.commonprefix(urls), parsed_json, "")

	def create_normal_bins(self):
		bin = {}
		for character in alphabet:
			bin[character] = []
		bin["etc"] = []
		return bin

	def filter_links(self, bins):
		i = 0
		remove_indexes = []
		explode_indexes = []
		for index in bins:
			bin_length =  len(bins[index])
			print index , " " , bin_length
			if (bin_length > 100):
				explode_indexes += [index]
			elif bin_length == 0:
				remove_indexes += [index]
			i += bin_length
		for index in remove_indexes:
			bins.pop(index, None)
			print index , " removed"
		for index in explode_indexes:
			new_json = copy.deepcopy(bins[index])
			self.add_links(bins, new_json, "", index)
		dump("bases", self.bases)

	def add_links(self, bases, parsed_json, remove_base, key):
		bases[key] = self.create_normal_bins()
		bins = bases[key]
		for gallery in parsed_json:
			gallery[self.index_type] = gallery[self.index_type][len(remove_base + key):]
			if len(gallery[self.index_type]) > 0:
				lowercase = gallery[self.index_type][0].lower()
				if lowercase in bins:
					bins[lowercase[0]].append(gallery)
			else:
				bins["etc"].append(gallery)
		print (len(parsed_json))
		self.filter_links(bins)

	def add_to_bases(self, bases, key, parsed_json, remove_base):
		print key
		for base in bases:
			if key[0:len(base)] == base:
				key = key[len(base):]
				self.add_to_bases(bases[base], key, parsed_json, remove_base + base)
				return
		self.add_links(bases, parsed_json, remove_base, key)

def main():
	maker = db_maker()
	maker.convert_gallery_to_dict("reddit")
	maker.convert_gallery_to_dict("danbooru")
	# maker.test()
	maker.build()
	maker.done()

main()