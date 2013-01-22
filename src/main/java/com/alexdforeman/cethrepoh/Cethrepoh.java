/*
Copyright (c) 2013, Alex Foreman at https://github.com/alexdforeman
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    Neither the name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.alexdforeman.cethrepoh;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.alexdforeman.cethrepoh.dictionary.DictionarySource;
import com.alexdforeman.cethrepoh.dictionary.WordsFileSource;
import com.alexdforeman.cethrepoh.extractor.AndroidStringsExtractor;
import com.alexdforeman.cethrepoh.extractor.WordExtractor;
import com.alexdforeman.cethrepoh.output.OutputGenerator;
import com.alexdforeman.cethrepoh.output.StandardOutputGenerator;

/**
 * Cethrepoh is a Spellchecker that requires a Dictionary and a list of Strings which are to be spellchecked and outputs
 * the verified data depending on its output generator
 *
 * @author Alex Foreman at https://github.com/alexdforeman
 */
public class Cethrepoh {
	
	public Cethrepoh(DictionarySource source, WordExtractor extractor, OutputGenerator output) {
		Collection<String> dictionary = source.getDictionary();
		Collection<String> extractWords = extractor.extractWords();
		
		System.out.println("Total Dictionary size: " + dictionary.size());
		System.out.println("Total words to check: " + extractWords.size());
		
		Map<String, Object> wrongWords = new HashMap<>();
		for (String string : extractWords) {
			if(!dictionary.contains(string)) {
				wrongWords.put(string, null);
			}
			
		}
		System.out.println("Wrong words: " + wrongWords.size());
		
		output.outputCorrections(wrongWords);
		
	}
	
	public static void main(String[] args) {
		new Cethrepoh(new WordsFileSource(), 
				new AndroidStringsExtractor(
//						new File("/home/alex/Development/github/cethrepoh/src/test/resources/strings.xml")),
						new File("/var/git/android-app/res/values/strings.xml")),
						
				new StandardOutputGenerator());
	}
}
