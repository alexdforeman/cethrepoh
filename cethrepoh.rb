#!/usr/bin/ruby
# encoding: utf-8

###
# requires
require 'nokogiri'
require 'open-uri'
require 'set'
require 'optparse'

###
# options
$WHITELIST_FILE = 'whitelist'
OptionParser.new do |o|
  o.on('-d DICTIONARY', 'Dictionary File. (required)') { |dict| $DICTIONARY_FILE = dict }
  o.on('-f FILENAME', 'File we are parsing. (required)') { |filename| $FILE = filename }
  o.on('-t TYPE', 'android, blackberry10, iOS. (required)') { |type| $TYPE = type }
  o.on('-w WHITELIST', 'Optional WhiteList') { |filename| $WHITELIST_FILE = filename }
  o.on('-h', '--help', 'Print usage') { puts o; exit }
  o.parse!
end
raise OptionParser::MissingArgument if $DICTIONARY_FILE.nil?
raise OptionParser::MissingArgument if $FILE.nil?
raise OptionParser::MissingArgument if $TYPE.nil?

###
# Methods
def uri?(string)
  uri = URI.parse(string)
  %w( http https ).include?(uri.scheme)
rescue URI::BadURIError
  false
rescue URI::InvalidURIError
  false
end

###
# Program
if File.file?($WHITELIST_FILE)
  WHITELIST = File.open($WHITELIST_FILE, 'r') {|file| file.read }.downcase.split(/\r?\n/)
else
  WHITELIST = []
end
DICTIONARY_WORDS = File.open($DICTIONARY_FILE, 'r') {|file| file.read }.downcase.split(/\r?\n/)
#FILE_WORDS = Set.new
VIOLATIONS = Set.new



doc = Nokogiri::HTML(open($FILE))
doc.xpath('//resources/string').each do |link|
  for word in link.content.downcase.split()
    stripword = word.gsub(/[…\\\?\:\!\.\,\;\(\)0-9©\+-]/, '').downcase.strip
    VIOLATIONS.add(stripword) unless uri?(word) || WHITELIST.index(word) || WHITELIST.index(stripword) || DICTIONARY_WORDS.include?(stripword)
  end
end


if VIOLATIONS.size() > 0
  puts "You have " + VIOLATIONS.size().to_s + "Violations"
  for word in VIOLATIONS
    puts word
  end
  abort
end

puts "No Violations Detected"
