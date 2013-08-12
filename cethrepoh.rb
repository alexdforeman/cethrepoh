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

class Cethrepoh
  def initialize(dict, whitelist)
    @dictionary = Set.new
    for word in File.open(dict, 'r') {|file| file.read }.downcase.split(/\r?\n/)
      @dictionary.add(word)
    end
    if File.file?(whitelist)
      for word in File.open(whitelist, 'r') {|file| file.read }.downcase.split(/\r?\n/)
        @dictionary.add(word)
      end
    end

  end

  def spellcheck(file)
    for word in File.open(file, 'r') { |word| word.read }.downcase.split(/\r?\n/)
      stripword = word.gsub(/[…\\\?\:\!\.\,\;\(\)0-9©\+-]/, '').downcase.strip
      violations.add(stripword) unless uri?(word) || @dictionary.member?(word) || @dictionary.member?(stripword)
    end
    return violations
  end

  def uri?(string)
    uri = URI.parse(string)
    %w( http https ).include?(uri.scheme)
  rescue URI::BadURIError
    false
  rescue URI::InvalidURIError
    false
  end
end

class AndroidSC < Cethrepoh
  def spellcheck(file)
    violations = Set.new
    doc = Nokogiri::HTML(open($FILE))
    doc.xpath('//resources/string').each do |link|
      for word in link.content.downcase.split()
        stripword = word.gsub(/[…\\\?\:\!\.\,\;\(\)0-9©\+-]/, '').downcase.strip
        violations.add(stripword) unless uri?(word) || @dictionary.member?(word) || @dictionary.member?(stripword)
      end
    end
    return violations
  end
end

class BlackBerrySC < Cethrepoh
  def spellcheck(file)
    violations = Set.new
    doc = Nokogiri::HTML(open($FILE))
    doc.xpath('//context/message/source').each do |link|
      for word in link.content.downcase.split()
        stripword = word.gsub(/[…\\\?\:\!\.\,\;\(\)0-9©\+-]/, '').downcase.strip
        violations.add(stripword) unless uri?(word) || @dictionary.member?(word) || @dictionary.member?(stripword)
      end
    end
    return violations
  end
end

class IosSC < Cethrepoh
  def spellcheck(file)
    violations = Set.new
    for line in File.open(file, 'r') { |line| line.read }.downcase.split(/\r?\n/)
      i = line.index("=")
      if i
        words = line[i + 1..-1].gsub('"', '').split
        for word in words
          stripword = word.gsub(/[…\\\?\:\!\.\,\;\(\)0-9©\+-]/, '').downcase.strip
          violations.add(stripword) unless uri?(word) || @dictionary.member?(word) || @dictionary.member?(stripword)
        end
      end
    end
    return violations
  end
end


if $TYPE == 'android'
  spellchecker = AndroidSC.new $DICTIONARY_FILE, $WHITELIST_FILE
elsif $TYPE == 'bb10'
  spellchecker = BlackBerrySC.new $DICTIONARY_FILE, $WHITELIST_FILE
elsif $TYPE == 'ios'
  spellchecker = IosSC.new $DICTIONARY_FILE, $WHITELIST_FILE
end

VIOLATIONS = spellchecker.spellcheck($FILE)
if VIOLATIONS.size() > 0
  puts "You have " + VIOLATIONS.size().to_s + "Violations"
  for word in VIOLATIONS
    puts word
  end
  abort
end
puts "No Violations Detected"
