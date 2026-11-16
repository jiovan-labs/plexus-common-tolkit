/*
 * Copyright (c) 2016 Jiovan Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiovan.plexus.common.toolkit.io;


import java.io.Writer;
import jakarta.annotation.Nullable;


/**
 * {@link java.io.Writer} with no operations
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public class NoopWriter extends Writer {
    /**
     * Appends the given content without doing anything.
     *
     * @param character The character
     *
     * @return this writer
     */
    @Override
    public Writer append(char character) {
        return this;
    }

    /**
     * Appends the given content without doing anything.
     *
     * @param sequence The character sequence
     *
     * @return this writer
     */
    @Override
    public Writer append(@Nullable CharSequence sequence) {
        return this;
    }

    /**
     * Appends the given content without doing anything.
     *
     * @param sequence The character sequence
     * @param start The start index
     * @param end The end index
     *
     * @return this writer
     */
    @Override
    public Writer append(@Nullable CharSequence sequence, int start, int end) {
        return this;
    }

    /**
     * Writes the given content without doing anything.
     *
     * @param character The character
     */
    @Override
    public void write(int character) {
    }

    /**
     * Writes the given content without doing anything.
     *
     * @param buffer The character buffer
     */
    @Override
    public void write(@Nullable char[] buffer) {
    }

    /**
     * Writes the given content without doing anything.
     *
     * @param buffer The character buffer
     * @param offset The start offset
     * @param length The number of characters
     */
    @Override
    public void write(@Nullable char[] buffer, int offset, int length) {
    }

    /**
     * Writes the given content without doing anything.
     *
     * @param text The text
     */
    @Override
    public void write(@Nullable String text) {
    }

    /**
     * Writes the given content without doing anything.
     *
     * @param text The text
     * @param offset The start offset
     * @param length The number of characters
     */
    @Override
    public void write(@Nullable String text, int offset, int length) {
    }

    /**
     * Flushes the writer without doing anything.
     *
     */
    @Override
    public void flush() {
    }

    /**
     * Closes the writer without doing anything.
     *
     */
    @Override
    public void close() {
    }
}
