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
package com.alexdforeman.cethrepoh.extractor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import com.alexdforeman.cethrepoh.sanitize.Sanitizer;


/**
 * WordExtractor abstract impl.
 * A lot of inputs are going to be files so we are creating a parent class that returns the specific files into a
 * string form so we can scan the files.
 *
 * @author Alex Foreman at https://github.com/alexdforeman
 */
public abstract class AbstractFileExtractor implements WordExtractor {

    private final File _file;

    private final Collection<Sanitizer> _SANITIZERS = new ArrayList<>();

    /**
     * Constructor.
     * @param file_ The file we want to check for spelling mistakes
     */
    public AbstractFileExtractor(final File file_) {
        _file = file_;
    }

    /**
     * Get the file.
     * @return {@link File}
     */
    public final File getFile() {
        return _file;
    }

    /**
     * Add a {@link Sanitizer} to the {@link WordExtractor}.
     * @param sanitizer_ Sanitizer
     */
    public final void addSanitizer(final Sanitizer sanitizer_) {
        _SANITIZERS.add(sanitizer_);
    }

    /**
     * This runs over all Sanitizers that have been added to the extractor and applys them to the passed Collection.
     * @param strings Collection<String>
     */
    public final void sanitize(final Collection<String> strings) {
        for (Sanitizer sanitizer : _SANITIZERS) {
            sanitizer.sanitize(strings);
        }
    }
}
