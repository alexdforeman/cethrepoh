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
package com.alexdforeman.cethrepoh.sanitize;

import java.util.Collection;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

/**
 *
 * EmailSanitizerTest.
 *
 * @author Alex Foreman at https://github.com/alexdforeman
 */
public class RegexSanitizerTest {

    /**
     * Test we remove emails from the spellchecker.
     */
    @Test
    public final void testSanitizeEmail() {
        Collection<String> strings = new HashSet<>();
        strings.add("cethrepoh@qmail.com");
        strings.add("r2d2@coldmail.com");
        strings.add("emailAtExampleDotCom");
        Sanitizer sanitizer = new RegexSanitizer(RegexSanitizer._EMAIL_REGEX);
        sanitizer.sanitize(strings);

        Assert.assertTrue(strings.size() == 1);
        Assert.assertTrue(strings.contains("emailAtExampleDotCom"));
        Assert.assertTrue(!strings.contains("r2d2@coldmail.com"));
        Assert.assertTrue(!strings.contains("cethrepoh@qmail.com"));
    }

    /**
     * test we remove urls from the spellchecker.
     */
    @Test
    public final void testSanitizeURL() {
        Collection<String> strings = new HashSet<>();
        strings.add("http://www.google.com");
        strings.add("https://google.com");
        strings.add("emailAtExampleDotCom");
        strings.add("r2d2@coldmail.com");
        Sanitizer sanitizer = new RegexSanitizer(RegexSanitizer._URL_REGEX);
        sanitizer.sanitize(strings);

        Assert.assertTrue(strings.size() == 2);
        Assert.assertTrue(strings.contains("emailAtExampleDotCom"));
        Assert.assertTrue(strings.contains("r2d2@coldmail.com"));
        Assert.assertTrue(!strings.contains("http://www.google.com"));
        Assert.assertTrue(!strings.contains("https://google.com"));
    }
}
