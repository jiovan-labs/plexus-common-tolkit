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

package com.jiovan.plexus.common.toolkit.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;


/**
 * Tests for {@link com.jiovan.plexus.common.toolkit.util.StringUtilsWraps}
 *
 * @author David Hsing
 */
@Slf4j
class StringUtilsWrapsTest {
    @Test
    void appendIfMissing() {
        Assertions.assertEquals("file.txt", StringUtilsWraps.appendIfMissing("file", ".txt"));
        Assertions.assertEquals("file.txt", StringUtilsWraps.appendIfMissing("file", ".txt", ".md"));
    }

    @Test
    void appendIfMissingIgnoreCase() {
        Assertions.assertEquals("file.TXT", StringUtilsWraps.appendIfMissingIgnoreCase("file.TXT", ".txt", ".md"));
    }

    @Test
    void chomp() {
        Assertions.assertEquals("abc", StringUtilsWraps.chomp("abc\r\n", "\r\n"));
        Assertions.assertEquals("abc", StringUtilsWraps.chomp("abc", "\r\n"));
    }

    @Test
    void compare() {
        Assertions.assertTrue(StringUtilsWraps.compare("a", "b") < 0);
        Assertions.assertTrue(StringUtilsWraps.compare("b", "a") > 0);
        Assertions.assertEquals(0, StringUtilsWraps.compare("a", "a"));
        Assertions.assertTrue(StringUtilsWraps.compare(null, "a") < 0);
    }

    @Test
    void compareIgnoreCase() {
        Assertions.assertEquals(0, StringUtilsWraps.compareIgnoreCase("A", "a"));
        Assertions.assertTrue(StringUtilsWraps.compareIgnoreCase("A", "b") < 0);
    }

    @Test
    void contains() {
        Assertions.assertTrue(StringUtilsWraps.contains("abc", "b"));
        Assertions.assertTrue(StringUtilsWraps.contains("abc", 'b'));
        Assertions.assertFalse(StringUtilsWraps.contains("abc", "B"));
        Assertions.assertFalse(StringUtilsWraps.contains("abc", 'x'));
        Assertions.assertFalse(StringUtilsWraps.contains(null, "a"));
    }

    @Test
    void containsIgnoreCase() {
        Assertions.assertTrue(StringUtilsWraps.containsIgnoreCase("abc", "B"));
        Assertions.assertFalse(StringUtilsWraps.containsIgnoreCase("abc", "d"));
        Assertions.assertFalse(StringUtilsWraps.containsIgnoreCase(null, "a"));
    }

    @Test
    void endsWith() {
        Assertions.assertTrue(StringUtilsWraps.endsWith("abc", "bc"));
        Assertions.assertFalse(StringUtilsWraps.endsWith("abc", "BC"));
        Assertions.assertFalse(StringUtilsWraps.endsWith(null, "a"));
    }

    @Test
    void endsWithAny() {
        Assertions.assertTrue(StringUtilsWraps.endsWithAny("abc", "x", "bc"));
        Assertions.assertFalse(StringUtilsWraps.endsWithAny("abc", "x", "y"));
    }

    @Test
    void endsWithIgnoreCase() {
        Assertions.assertTrue(StringUtilsWraps.endsWithIgnoreCase("abc", "BC"));
        Assertions.assertFalse(StringUtilsWraps.endsWithIgnoreCase("abc", "BD"));
    }

    @Test
    void equals() {
        Assertions.assertTrue(StringUtilsWraps.equals("abc", "abc"));
        Assertions.assertFalse(StringUtilsWraps.equals("abc", "ABC"));
        Assertions.assertTrue(StringUtilsWraps.equals(null, null));
        Assertions.assertFalse(StringUtilsWraps.equals(null, ""));
    }

    @Test
    void equalsIgnoreCase() {
        Assertions.assertTrue(StringUtilsWraps.equalsIgnoreCase("abc", "ABC"));
        Assertions.assertFalse(StringUtilsWraps.equalsIgnoreCase("abc", "ABD"));
    }

    @Test
    void indexOf() {
        Assertions.assertEquals(1, StringUtilsWraps.indexOf("abcabc", "bc"));
        Assertions.assertEquals(4, StringUtilsWraps.indexOf("abcabc", "bc", 2));
        Assertions.assertEquals(-1, StringUtilsWraps.indexOf("abcabc", "x"));
    }

    @Test
    void indexOfIgnoreCase() {
        Assertions.assertEquals(1, StringUtilsWraps.indexOfIgnoreCase("ABCabc", "bc"));
        Assertions.assertEquals(4, StringUtilsWraps.indexOfIgnoreCase("ABCabc", "bc", 2));
        Assertions.assertEquals(-1, StringUtilsWraps.indexOfIgnoreCase("ABCabc", "x"));
    }

    @Test
    void joinWith() {
        String result = StringUtilsWraps.joinWith(new String[]{"a", "b", "c"}, '*', '[', ']');
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertEquals("[a]*[b]*[c]", result);
    }

    @Test
    void lastIndexOf() {
        Assertions.assertEquals(4, StringUtilsWraps.lastIndexOf("abcabc", "bc"));
        Assertions.assertEquals(1, StringUtilsWraps.lastIndexOf("abcabc", "bc", 3));
        Assertions.assertEquals(-1, StringUtilsWraps.lastIndexOf("abcabc", "x"));
    }

    @Test
    void lastIndexOfIgnoreCase() {
        Assertions.assertEquals(4, StringUtilsWraps.lastIndexOfIgnoreCase("ABCabc", "bc"));
        Assertions.assertEquals(1, StringUtilsWraps.lastIndexOfIgnoreCase("ABCabc", "bc", 3));
        Assertions.assertEquals(-1, StringUtilsWraps.lastIndexOfIgnoreCase("ABCabc", "x"));
    }

    @Test
    void prependIfMissing() {
        Assertions.assertEquals(".gitignore", StringUtilsWraps.prependIfMissing("gitignore", "."));
        Assertions.assertEquals(".gitignore", StringUtilsWraps.prependIfMissing("gitignore", ".", "!"));
    }

    @Test
    void prependIfMissingIgnoreCase() {
        Assertions.assertEquals(".GITIGNORE", StringUtilsWraps.prependIfMissingIgnoreCase(".GITIGNORE", ".git"));
        Assertions.assertEquals(".gitignore", StringUtilsWraps.prependIfMissingIgnoreCase("gitignore", "."));
    }

    @Test
    void remove() {
        Assertions.assertEquals("qd", StringUtilsWraps.remove("queued", "ue"));
        Assertions.assertEquals("ac", StringUtilsWraps.remove("abc", 'b'));
        Assertions.assertNull(StringUtilsWraps.remove(null, "ue"));
    }

    @Test
    void removeEndIgnoreCase() {
        Assertions.assertEquals("file", StringUtilsWraps.removeEndIgnoreCase("file.TXT", ".txt"));
        Assertions.assertEquals("file.TXT", StringUtilsWraps.removeEndIgnoreCase("file.TXT", ".md"));
    }

    @Test
    void removeFirst() {
        Assertions.assertEquals("bcabc", StringUtilsWraps.removeFirst("abcabc", "a"));
        Assertions.assertEquals("abcabc", StringUtilsWraps.removeFirst("abcabc", "\\d+"));
    }

    @Test
    void removeIgnoreCase() {
        Assertions.assertEquals("qd", StringUtilsWraps.removeIgnoreCase("quEUed", "UE"));
        Assertions.assertNull(StringUtilsWraps.removeIgnoreCase(null, "UE"));
    }

    @Test
    void removePattern() {
        Assertions.assertEquals("abc", StringUtilsWraps.removePattern("abc123", "\\d+"));
        Assertions.assertEquals("abc123", StringUtilsWraps.removePattern("abc123", "[A-Z]+"));
    }

    @Test
    void replaceFirst() {
        Assertions.assertEquals("zbcabc", StringUtilsWraps.replaceFirst("abcabc", "a", "z"));
        Assertions.assertEquals("abcabc", StringUtilsWraps.replaceFirst("abcabc", "\\d+", "z"));
    }

    @Test
    void replaceOnce() {
        Assertions.assertEquals("zba", StringUtilsWraps.replaceOnce("aba", "a", "z"));
    }

    @Test
    void replaceOnceIgnoreCase() {
        Assertions.assertEquals("zba", StringUtilsWraps.replaceOnceIgnoreCase("aba", "A", "z"));
    }

    @Test
    void replacePattern() {
        Assertions.assertEquals("abc", StringUtilsWraps.replacePattern("abc123", "\\d+", ""));
        Assertions.assertEquals("abc123", StringUtilsWraps.replacePattern("abc123", "[A-Z]+", ""));
    }

    @Test
    void startsWith() {
        Assertions.assertTrue(StringUtilsWraps.startsWith("abc", "ab"));
        Assertions.assertFalse(StringUtilsWraps.startsWith("abc", "AB"));
        Assertions.assertFalse(StringUtilsWraps.startsWith(null, "a"));
    }

    @Test
    void startsWithAny() {
        Assertions.assertTrue(StringUtilsWraps.startsWithAny("abc", "x", "ab"));
        Assertions.assertFalse(StringUtilsWraps.startsWithAny("abc", "x", "y"));
    }

    @Test
    void startsWithIgnoreCase() {
        Assertions.assertTrue(StringUtilsWraps.startsWithIgnoreCase("abc", "AB"));
        Assertions.assertFalse(StringUtilsWraps.startsWithIgnoreCase("abc", "AC"));
    }

    @Test
    void unwrapWithCurlyBrackets() {
        String result = StringUtilsWraps.unwrapWithCurlyBrackets("{StringUtilsWraps}");
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertEquals("StringUtilsWraps", result);
    }

    @Test
    void unwrapWithParentheses() {
        String result = StringUtilsWraps.unwrapWithParentheses("(StringUtilsWraps)");
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertEquals("StringUtilsWraps", result);
    }

    @Test
    void unwrapWithSquareBrackets() {
        String result = StringUtilsWraps.unwrapWithSquareBrackets("[StringUtilsWraps]");
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertEquals("StringUtilsWraps", result);
    }
}
