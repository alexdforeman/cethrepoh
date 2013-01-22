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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;


/**
 * 
 * IosStringsExtractor parses a ios .strings file in the format
 * "key" = "value";
 * To extract all the words ready for parsing.
 * 
 * @author Alex Foreman at https://github.com/alexdforeman
 */
public class IosStringsExtractor extends AbstractFileExtractor{

	public IosStringsExtractor(File file_) {
		super(file_);
	}

	/* (non-Javadoc)
	 * @see com.alexdforeman.cethrepoh.extractor.WordExtractor#extractWords()
	 */
	@Override
	public Collection<String> extractWords() {
		Collection<String> strings = new HashSet<>();
		
		try (FileInputStream fis = new FileInputStream(getFile());
		        DataInputStream in = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String strLine;
			while ((strLine = br.readLine()) != null) {
				System.out.println(strLine);
				if(!strLine.trim().equals("") && !strLine.trim().startsWith("//")){
					// FIXME this works but is nasty
					String[] splitString =
							strLine.substring(strLine.indexOf("=") + 1).replaceAll(";", "").replaceAll("\"", "").trim().split(" ");
					for (String string : splitString) {
						strings.add(string.toLowerCase());
					}
				}
			}	
		} catch (IOException ioe_){
			throw new RuntimeException("Something went wrong whilst reading the .strings file", ioe_);
		}	
		return strings;
	}
}
