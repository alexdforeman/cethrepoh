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

import org.junit.Assert;
import org.junit.Test;

import com.alexdforeman.cethrepoh.extractor.AndroidStringsExtractor;

/**
 * 
 * Test the AndroidStringExtractor
 * 
 * @author Alex Foreman at https://github.com/alexdforeman
 */
public class AndroidStringExtractorTest {

	@Test
	public void testSanitizedInput() {

		AndroidStringsExtractor extractor = new AndroidStringsExtractor(new File(
				"/home/alex/workspace/cethrepoh/src/test/resources/strings.xml"));

		Collection<String> extractWords = extractor.extractWords();
		Assert.assertTrue(extractWords.size() == 14);
		Assert.assertTrue(extractWords.contains("capitals"));
		Assert.assertTrue(extractWords.contains("to"));
		Assert.assertTrue(extractWords.contains("going"));
		Assert.assertTrue(extractWords.contains("comma"));
		Assert.assertTrue(extractWords.contains("numb3rs"));
		Assert.assertTrue(extractWords.contains("a"));
		Assert.assertTrue(extractWords.contains("fullstop"));
		Assert.assertTrue(extractWords.contains("that"));
		Assert.assertTrue(extractWords.contains("are"));
		Assert.assertTrue(extractWords.contains("1234567890"));
		Assert.assertTrue(extractWords.contains("string"));
		Assert.assertTrue(extractWords.contains("against"));
		Assert.assertTrue(extractWords.contains("match"));
		Assert.assertTrue(extractWords.contains("we"));
	}
}