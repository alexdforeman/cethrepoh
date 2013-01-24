/*
Copyright (c) 2013, Alex Foreman at https://github.com/alexdforeman
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    Neither the name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.alexdforeman.cethrepoh.extractor;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

/**
 * 
 * Takes a res/values/strings.xml file for an android application and extracts all english words. 
 * TODO enable the ability to drag words out of string-array inside the file.
 * 
 * @author Alex Foreman at https://github.com/alexdforeman
 */
public class AndroidStringsExtractor extends AbstractFileExtractor {
	private Logger _LOGGER = Logger.getLogger(getClass());

	public AndroidStringsExtractor(File file_) {
		super(file_);
	}

	/* (non-Javadoc)
	 * @see com.alexdforeman.cethrepoh.extractor.WordExtractor#extractWords()
	 */
	public Collection<String> extractWords() {
		Collection<String> strings = new HashSet<>();
		
		Builder builder = new Builder();
		Document doc;
		try {
			doc = builder.build(getFile());
			Element rootElement = doc.getRootElement();
			Elements children = rootElement.getChildElements("string");
			   for (int i = 0; i < children.size(); i++) {
			    strings.addAll(split(children.get(i).getValue()));
			  }
		} catch (ParsingException | IOException e) {
			e.printStackTrace();
		}
		_LOGGER.info("Size of un-sanitized Strings: " + strings.size());
		sanitize(strings);
		_LOGGER.info("Size of sanitized Strings: " + strings.size());
		return strings;
	}

	/*
	 * Converts a string into all its component words.
	 * TODO update to the new Sanitizer API
	 */
	private Set<String> split(String value) {
		Set<String> set = new HashSet<>();
		String[] split = value.trim().split("\\s");
		for (String string : split) {
			if(!string.equals("")) {
				set.add(string.toLowerCase());
			}
		}
		return set;
	}
}
