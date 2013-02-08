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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * This matches and removes any string that matches the regex.
 *
 * Use for complex strings like emails / urls etc.
 *
 * @author Alex Foreman at https://github.com/alexdforeman
 */
public class RegexSanitizer implements Sanitizer {

    /**
     * Regex that tries to match emails.
     */
    public static final String _EMAIL_REGEX =
            "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

    /**
     * Regex that tries to match URLs.
     */
    public static final String _URL_REGEX =
            "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    /**
     * Regex that matches numbers.
     */
    public static final String _NUMBER_REGEX = "^[0-9]+$";

    private final Pattern _PATTERN;

    /**
     * Constructor.
     * @param regex_ the regex of the string we want to remove.
     */
    public RegexSanitizer(final String regex_) {
        _PATTERN = Pattern.compile(regex_);
    }

    /**
     * @see com.alexdforeman.cethrepoh.sanitize.Sanitizer#sanitize().
     * @param collection_ Collection<String>
     */
    @Override
    public final void sanitize(final Collection<String> collection_) {
        Collection<String> remove = new HashSet<>();

        for (String string : collection_) {
            Matcher m = _PATTERN.matcher(string);
            if (m.matches()) {
                remove.add(string);
            }
        }
        collection_.removeAll(remove);
        }
}
