/*
 * Copyright (c) 2013, Alex Foreman at https://github.com/alexdforeman
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *  Neither the name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.alexdforeman.cethrepoh.dictionary;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Creates a DictionarySource impl which reads from a file.
 * Takes a File Object or a String path to a words file which should be a new line delimited file with a distinct word
 * on each one as a verified source.
 *
 * Currently default constructor hard-coded to /usr/share/dict/words
 *
 * @author Alex Foreman at https://github.com/alexdforeman
 */
public class WordsFileSource implements DictionarySource {

    private Logger _LOGGER = Logger.getLogger(getClass());

    private File _dictionary;

    /**
     * Default Constructor.
     * Tries to find a dictionary as per: http://en.wikipedia.org/wiki/Words_%28Unix%29
     * Looks at: /usr/share/dict/words
     */
    public WordsFileSource() {
        this("/usr/share/dict/words");
    }

    /**
     * Takes a path to a File which should be a new line delimited file with a distinct word on each one as a verified source.
     * @param pathToFile_ String
     */
    public WordsFileSource(final String pathToFile_) {
        this(new File(pathToFile_));
    }

    /**
     * Takes a File which should be a new line delimited file with a distinct word on each one as a verified source.
     * @param wordsFile_ File
     */
    public WordsFileSource(final File wordsFile_) {
       if (!wordsFile_.exists() || !wordsFile_.isFile()) {
           throw new IllegalArgumentException("File: " + wordsFile_.getAbsolutePath()
                   + " does not exist or is not a file.");
       }
       _dictionary = wordsFile_;
    }

    /* (non-Javadoc)
     * @see com.alexdforeman.cethrepoh.dictionary.DictionarySource#getDictionary()
     */
    @Override
    public final Collection<String> getDictionary() {
        Set<String> strings = new HashSet<>();
        try {
            List<String> lines = Files.readLines(_dictionary, Charsets.UTF_8);
            for (String string : lines) {
                strings.add(string.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        _LOGGER.info("Size of returned Dictionary: " + strings.size());
        return strings;
    }
}
