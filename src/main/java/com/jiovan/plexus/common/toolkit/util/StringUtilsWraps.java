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


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;
import com.jiovan.plexus.common.toolkit.constant.SymbolVariantConst;


/**
 * Utilities for {@link org.apache.commons.lang3.StringUtils}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.lang3.StringUtils
 * @see org.apache.commons.lang3.Strings
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class StringUtilsWraps {
    /**
     * Checks whether all the given sequences are empty.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean allEmpty(@Nullable CharSequence... sequences) {
        return StringUtils.isAllEmpty(sequences);
    }

    /**
     * Checks whether all the given sequences are empty.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean allEmpty(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isEmpty(sequences) || sequences.stream().allMatch(StringUtils::isEmpty);
    }

    /**
     * Checks whether all the given sequences are not empty.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean allNotEmpty(@Nullable CharSequence... sequences) {
        return allNotEmpty(ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Checks whether all the given sequences are not empty.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean allNotEmpty(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isNotEmpty(sequences) && sequences.stream().noneMatch(StringUtils::isEmpty);
    }

    /**
     * Checks whether all the given sequences are blank.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean allBlank(@Nullable CharSequence... sequences) {
        return StringUtils.isAllBlank(sequences);
    }

    /**
     * Checks whether all the given sequences are blank.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean allBlank(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isEmpty(sequences) || sequences.stream().allMatch(StringUtils::isBlank);
    }

    /**
     * Checks whether all the given sequences are not blank.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean allNotBlank(@Nullable CharSequence... sequences) {
        return allNotBlank(ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Checks whether all the given sequences are not blank.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean allNotBlank(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isNotEmpty(sequences) && sequences.stream().noneMatch(StringUtils::isBlank);
    }

    /**
     * Checks whether any of the given sequences are empty.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean anyEmpty(@Nullable CharSequence... sequences) {
        return StringUtils.isAnyEmpty(sequences);
    }

    /**
     * Checks whether any of the given sequences are empty.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean anyEmpty(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isEmpty(sequences) || sequences.stream().anyMatch(StringUtils::isEmpty);
    }

    /**
     * Checks whether any of the given sequences are not empty.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean anyNotEmpty(@Nullable CharSequence... sequences) {
        return anyNotEmpty(ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Checks whether any of the given sequences are not empty.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean anyNotEmpty(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isNotEmpty(sequences) && sequences.stream().anyMatch(StringUtils::isNotEmpty);
    }

    /**
     * Checks whether any of the given sequences are blank.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean anyBlank(@Nullable CharSequence... sequences) {
        return StringUtils.isAnyBlank(sequences);
    }

    /**
     * Checks whether any of the given sequences are blank.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean anyBlank(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isEmpty(sequences) || sequences.stream().anyMatch(StringUtils::isBlank);
    }

    /**
     * Checks whether any of the given sequences are not blank.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean anyNotBlank(@Nullable CharSequence... sequences) {
        return anyNotBlank(ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Checks whether any of the given sequences are not blank.
     *
     * @param sequences The source char sequences
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean anyNotBlank(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isNotEmpty(sequences) && sequences.stream().anyMatch(StringUtils::isNotBlank);
    }

    /**
     * Returns a string that appends the suffix to the end of the sequence if the sequence does not already end with the suffix
     *
     * @param sequence The source char sequence to be appended
     * @param append The suffix to append to the end of the string
     *
     * @return a string that appends the suffix to the end of the string if the string does not already end with the suffix
     */
    public static String appendIfMissing(@Nullable CharSequence sequence, char append) {
        return appendIfMissing(sequence, CharUtils.toString(append));
    }

    /**
     * Returns a string that appends the suffix to the end of the sequence if the sequence does not already end with the suffix
     *
     * @param sequence The source char sequence to be appended
     * @param append The suffix to append to the end of the string
     *
     * @return a string that appends the suffix to the end of the string if the string does not already end with the suffix
     */
    public static String appendIfMissing(@Nullable CharSequence sequence, @Nullable CharSequence append) {
        return Strings.CS.appendIfMissing(CharSequenceWraps.toStringIgnoreNull(sequence), append, append);
    }

    /**
     * Adds text to the sequence using append if missing.
     *
     * @param collection The source collection
     * @param append The text to append
     * @param suffixes The suffixes
     */
    public static void appendIfMissing(@Nullable Collection<String> collection, char append, char... suffixes) {
        appendIfMissing(collection, CharUtils.toString(append), CharUtilsWraps.toStringArray(suffixes));
    }

    /**
     * Adds text to the sequence using append if missing.
     *
     * @param collection The source collection
     * @param append The text to append
     */
    public static void appendIfMissing(@Nullable Collection<String> collection, @Nullable CharSequence append) {
        appendIfMissing(collection, append, ArrayUtilsWraps.singletonArray(append));
    }

    /**
     * Adds text to the sequence using append if missing.
     *
     * @param collection The source collection
     * @param append The text to append
     * @param suffixes The suffixes
     */
    public static void appendIfMissing(@Nullable Collection<String> collection, char append, @Nullable CharSequence... suffixes) {
        appendIfMissing(collection, CharUtils.toString(append), suffixes);
    }

    /**
     * Adds text to the sequence using append if missing.
     *
     * @param collection The source collection
     * @param append The text to append
     * @param suffixes The suffixes
     */
    public static void appendIfMissing(@Nullable Collection<String> collection, @Nullable CharSequence append, char... suffixes) {
        appendIfMissing(collection, append, CharUtilsWraps.toStringArray(suffixes));
    }

    /**
     * Append the suffix to the end of the string if the string does not already end with any of the suffixes
     *
     * @param collection The collection to get the input from
     * @param append The suffix to append to the end of the string
     * @param suffixes The additional suffixes that are valid terminators
     *
     * @throws java.lang.UnsupportedOperationException if the {@code clear} operation is not supported by this collection
     *
     * @see "org.springframework.util.ObjectUtils#addObjectToArray"
     */
    public static void appendIfMissing(@Nullable Collection<String> collection, @Nullable CharSequence append, @Nullable CharSequence... suffixes) {
        if (CollectionPlainWraps.isEmpty(collection)) {
            return;
        }
        String[] array = collection.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        collection.clear();
        Arrays.stream(array).map(item -> Strings.CS.appendIfMissing(item, append, suffixes)).forEach(collection::add);
    }

    /**
     * Appends the suffix if the sequence does not already end with any of the suffixes, case-sensitive
     *
     * @param sequence The source char sequence
     * @param append The suffix to append
     * @param suffixes The additional suffixes that are valid terminators
     *
     * @return the sequence with the suffix appended when needed
     */
    public static String appendIfMissing(@Nullable CharSequence sequence, @Nullable CharSequence append, @Nullable CharSequence... suffixes) {
        return Strings.CS.appendIfMissing(CharSequenceWraps.toStringIgnoreNull(sequence), append, suffixes);
    }

    /**
     * Returns a string that appends the suffix to the end of the string if the string does not already end with the suffix, case-insensitive
     *
     * @param sequence The source char sequence to be appended
     * @param append The suffix to append to the end of the string
     *
     * @return a string that appends the suffix to the end of the string if the string does not already end with the suffix
     */
    public static String appendIfMissingIgnoreCase(@Nullable CharSequence sequence, char append) {
        return appendIfMissingIgnoreCase(sequence, CharUtils.toString(append));
    }

    /**
     * Returns a string that appends the suffix to the end of the string if the string does not already end with the suffix, case-insensitive
     *
     * @param sequence The source char sequence to be appended
     * @param append The suffix to append to the end of the string
     *
     * @return a string that appends the suffix to the end of the string if the string does not already end with the suffix
     */
    public static String appendIfMissingIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence append) {
        return Strings.CI.appendIfMissing(CharSequenceWraps.toStringIgnoreNull(sequence), append, append);
    }

    /**
     * Adds text to the sequence using append if missing ignore case.
     *
     * @param collection The source collection
     * @param append The text to append
     */
    public static void appendIfMissingIgnoreCase(@Nullable Collection<String> collection, @Nullable CharSequence append) {
        appendIfMissingIgnoreCase(collection, append, ArrayUtilsWraps.singletonArray(append));
    }

    /**
     * Adds text to the sequence using append if missing ignore case.
     *
     * @param collection The source collection
     * @param append The text to append
     * @param suffixes The suffixes
     */
    public static void appendIfMissingIgnoreCase(@Nullable Collection<String> collection, char append, char... suffixes) {
        appendIfMissingIgnoreCase(collection, CharUtils.toString(append), CharUtilsWraps.toStringArray(suffixes));
    }

    /**
     * Adds text to the sequence using append if missing ignore case.
     *
     * @param collection The source collection
     * @param append The text to append
     * @param suffixes The suffixes
     */
    public static void appendIfMissingIgnoreCase(@Nullable Collection<String> collection, char append, @Nullable CharSequence... suffixes) {
        appendIfMissingIgnoreCase(collection, CharUtils.toString(append), suffixes);
    }

    /**
     * Adds text to the sequence using append if missing ignore case.
     *
     * @param collection The source collection
     * @param append The text to append
     * @param suffixes The suffixes
     */
    public static void appendIfMissingIgnoreCase(@Nullable Collection<String> collection, @Nullable CharSequence append, char... suffixes) {
        appendIfMissingIgnoreCase(collection, append, CharUtilsWraps.toStringArray(suffixes));
    }

    /**
     * Append the suffix to the end of the string if the string does not already end with any of the suffixes, case-insensitive
     *
     * @param collection The collection to get the input from
     * @param append The suffix to append to the end of the string
     * @param suffixes The additional suffixes that are valid terminators
     *
     * @throws java.lang.UnsupportedOperationException if the {@code clear} operation is not supported by this collection
     *
     * @see org.apache.commons.lang3.Strings#appendIfMissing
     */
    public static void appendIfMissingIgnoreCase(@Nullable Collection<String> collection, @Nullable CharSequence append, @Nullable CharSequence... suffixes) {
        if (CollectionPlainWraps.isEmpty(collection)) {
            return;
        }
        String[] array = collection.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        collection.clear();
        Arrays.stream(array).map(item -> Strings.CI.appendIfMissing(item, append, suffixes)).forEach(collection::add);
    }

    /**
     * Appends the suffix if the sequence does not already end with any of the suffixes, case-insensitive
     *
     * @param sequence The source char sequence
     * @param append The suffix to append
     * @param suffixes The additional suffixes that are valid terminators
     *
     * @return the sequence with the suffix appended when needed
     */
    public static String appendIfMissingIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence append, @Nullable CharSequence... suffixes) {
        return Strings.CI.appendIfMissing(CharSequenceWraps.toStringIgnoreNull(sequence), append, suffixes);
    }

    /**
     * Adds text to the sequence using append if sequence not empty.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSequenceNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence append) {
        return appendIfSequenceNotEmpty(sequence, append, null);
    }

    /**
     * Adds text to the sequence using append if sequence not empty.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSequenceNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence append, char delimiter) {
        return appendIfSequenceNotEmpty(sequence, append, CharUtils.toString(delimiter));
    }

    /**
     * Adds text to the sequence using append if sequence not empty.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSequenceNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence append, @Nullable CharSequence delimiter) {
        return StringUtils.isEmpty(sequence) ? CharSequenceWraps.toStringIgnoreNull(sequence) : StringUtils.join(sequence, delimiter, append);
    }

    /**
     * Adds text to the sequence using append if sequence not blank.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSequenceNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence append) {
        return appendIfSequenceNotBlank(sequence, append, null);
    }

    /**
     * Adds text to the sequence using append if sequence not blank.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSequenceNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence append, char delimiter) {
        return appendIfSequenceNotBlank(sequence, append, CharUtils.toString(delimiter));
    }

    /**
     * Adds text to the sequence using append if sequence not blank.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSequenceNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence append, @Nullable CharSequence delimiter) {
        return StringUtils.isBlank(sequence) ? CharSequenceWraps.toStringIgnoreNull(sequence) : StringUtils.join(sequence, delimiter, append);
    }

    /**
     * Adds text to the sequence using append if suffix not empty.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSuffixNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence append) {
        return appendIfSuffixNotEmpty(sequence, append, null);
    }

    /**
     * Adds text to the sequence using append if suffix not empty.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSuffixNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence append, char delimiter) {
        return appendIfSuffixNotEmpty(sequence, append, CharUtils.toString(delimiter));
    }

    /**
     * Adds text to the sequence using append if suffix not empty.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSuffixNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence append, @Nullable CharSequence delimiter) {
        return StringUtils.isEmpty(append) ? CharSequenceWraps.toStringIgnoreNull(sequence) : StringUtils.join(sequence, delimiter, append);
    }

    /**
     * Adds text to the sequence using append if suffix not blank.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSuffixNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence append) {
        return appendIfSuffixNotBlank(sequence, append, null);
    }

    /**
     * Adds text to the sequence using append if suffix not blank.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSuffixNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence append, char delimiter) {
        return appendIfSuffixNotBlank(sequence, append, CharUtils.toString(delimiter));
    }

    /**
     * Adds text to the sequence using append if suffix not blank.
     *
     * @param sequence The source char sequence
     * @param append The text to append
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String appendIfSuffixNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence append, @Nullable CharSequence delimiter) {
        return StringUtils.isBlank(append) ? CharSequenceWraps.toStringIgnoreNull(sequence) : StringUtils.join(sequence, delimiter, append);
    }

    /**
     * Performs the capitalizeFirstLowerTail operation.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String capitalizeFirstLowerTail(@Nullable CharSequence sequence) {
        return StringUtils.capitalize(StringUtils.lowerCase(CharSequenceWraps.toStringIgnoreNull(sequence)));
    }

    /**
     * Removes the separator from the end of the sequence
     *
     * @param sequence The source char sequence
     * @param separator The separator to remove
     *
     * @return the sequence without the trailing separator
     */
    public static String chomp(@Nullable CharSequence sequence, @Nullable CharSequence separator) {
        return Strings.CS.removeEnd(CharSequenceWraps.toStringIgnoreNull(sequence), CharSequenceWraps.toStringIgnoreNull(separator));
    }

    /**
     * Combine multiple lines or tabs into a single line with spaces.
     * Replaces all line breaks and tabs with single spaces, and collapses multiple consecutive spaces into one.
     *
     * @param text the input text to process
     *
     * @return trimmed single-line string, or {@code null} if input is blank
     */
    @Nullable
    public static String combineSpaceLines(@Nullable String text) {
        return StringUtils.isBlank(text) ? null : text.replaceAll("(\\r?\\n|\\t)+", StringUtils.SPACE).replaceAll("\\s{2,}", StringUtils.SPACE).trim();    // $NON-NLS-1$ // $NON-NLS-2$
    }

    /**
     * Compares two {@link CharSequence}s, null is considered less than non-null
     *
     * @param sequence The first char sequence
     * @param comparison The second char sequence
     *
     * @return a negative value, zero, or a positive value
     */
    public static int compare(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CS.compare(CharSequenceWraps.toStringIgnoreNull(sequence), CharSequenceWraps.toStringIgnoreNull(comparison));
    }

    /**
     * Compares two {@link CharSequence}s, ignoring case, null is considered less than non-null
     *
     * @param sequence The first char sequence
     * @param comparison The second char sequence
     *
     * @return a negative value, zero, or a positive value
     */
    public static int compareIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CI.compare(CharSequenceWraps.toStringIgnoreNull(sequence), CharSequenceWraps.toStringIgnoreNull(comparison));
    }

    /**
     * Checks if the sequence contains the comparison, case-sensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     *
     * @return true if the sequence contains the comparison
     */
    public static boolean contains(@Nullable CharSequence sequence, char comparison) {
        return Strings.CS.contains(sequence, CharUtils.toString(comparison));
    }

    /**
     * Checks whether the sequence matches contains.
     *
     * @param sequence The source char sequence
     * @param comparison The value to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean contains(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CS.contains(sequence, comparison);
    }

    /**
     * Checks if the sequence contains the comparison, case-insensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     *
     * @return true if the sequence contains the comparison
     */
    public static boolean containsIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CI.contains(sequence, comparison);
    }

    /**
     * Checks whether the sequence matches contains all.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAll(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return containsAll(sequence, ArrayUtilsWraps.asList(comparisons));
    }

    /**
     * Checks whether the sequence matches contains all.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAll(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> comparisons) {
        return StringUtils.isNotEmpty(sequence) && CollectionPlainWraps.isNotEmpty(comparisons) && comparisons.stream().allMatch(item -> Strings.CS.contains(sequence, item));
    }

    /**
     * Checks whether the sequence matches contains all ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAllIgnoreCase(@Nullable CharSequence sequence, char... comparisons) {
        return containsAllIgnoreCase(sequence, CharUtilsWraps.toStringArray(comparisons));
    }

    /**
     * Checks whether the sequence matches contains all ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAllIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return containsAllIgnoreCase(sequence, ArrayUtilsWraps.asList(comparisons));
    }

    /**
     * Checks whether the sequence matches contains all ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAllIgnoreCase(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> comparisons) {
        return StringUtils.isNotEmpty(sequence) && CollectionPlainWraps.isNotEmpty(comparisons) && comparisons.stream().allMatch(item -> Strings.CI.contains(sequence, item));
    }

    /**
     * Checks whether the sequence matches contains any.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAny(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return containsAny(sequence, ArrayUtilsWraps.asList(comparisons));
    }

    /**
     * Checks whether the sequence matches contains any.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAny(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> comparisons) {
        return StringUtils.isNotEmpty(sequence) && CollectionPlainWraps.isNotEmpty(comparisons) && comparisons.stream().anyMatch(item -> Strings.CS.contains(sequence, item));
    }

    /**
     * Checks whether the sequence matches contains any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAnyIgnoreCase(@Nullable CharSequence sequence, char... comparisons) {
        return Strings.CI.containsAny(sequence, CharUtilsWraps.toStringArray(comparisons));
    }

    /**
     * Checks whether the sequence matches contains any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAnyIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return containsAnyIgnoreCase(sequence, ArrayUtilsWraps.asList(comparisons));
    }

    /**
     * Checks whether the sequence matches contains any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean containsAnyIgnoreCase(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> comparisons) {
        return StringUtils.isNotEmpty(sequence) && CollectionPlainWraps.isNotEmpty(comparisons) && comparisons.stream().anyMatch(item -> Strings.CI.contains(sequence, item));
    }

    /**
     * Returns a fallback value using default if equals.
     *
     * @param sequence The source char sequence
     * @param comparison The value to compare
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <T extends CharSequence> T defaultIfEquals(@Nullable T sequence, @Nullable CharSequence comparison, @Nullable T defaultValue) {
        return Strings.CS.equals(sequence, comparison) ? defaultValue : sequence;
    }

    /**
     * Returns a fallback value using default if equals ignore case.
     *
     * @param sequence The source char sequence
     * @param comparison The value to compare
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <T extends CharSequence> T defaultIfEqualsIgnoreCase(@Nullable T sequence, @Nullable CharSequence comparison, @Nullable T defaultValue) {
        return Strings.CI.equals(sequence, comparison) ? defaultValue : sequence;
    }

    /**
     * Returns a fallback value using default string.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String defaultString(@Nullable CharSequence sequence) {
        return defaultString(sequence, StringUtils.EMPTY);
    }

    /**
     * Returns a fallback value using default string.
     *
     * @param sequence The source char sequence
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static String defaultString(@Nullable CharSequence sequence, @Nullable String defaultValue) {
        return (sequence == null) ? defaultValue : sequence.toString();
    }

    /**
     * Returns a fallback value using default string if blank.
     *
     * @param sequence The source char sequence
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static String defaultStringIfBlank(@Nullable CharSequence sequence, @Nullable String defaultValue) {
        return StringUtils.isBlank(sequence) ? defaultValue : sequence.toString();
    }

    /**
     * Returns a fallback value using default string if empty.
     *
     * @param sequence The source char sequence
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static String defaultStringIfEmpty(@Nullable CharSequence sequence, @Nullable String defaultValue) {
        return StringUtils.isEmpty(sequence) ? defaultValue : sequence.toString();
    }

    /**
     * Returns the distinct values using distinct ignore null.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] distinctIgnoreNull(@Nullable String... texts) {
        return distinctIgnoreNull(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns the distinct values using distinct ignore null.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String[] distinctIgnoreNull(@Nullable Collection<String> texts) {
        Set<String> result = distinctIgnoreNullToSet(texts);
        return CollectionPlainWraps.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Returns the distinct values using distinct ignore null to list.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> distinctIgnoreNullToList(@Nullable String... texts) {
        return distinctIgnoreNullToList(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns the distinct values using distinct ignore null to list.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<String> distinctIgnoreNullToList(@Nullable Collection<String> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * Returns the distinct values using distinct ignore null to set.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> distinctIgnoreNullToSet(@Nullable String... texts) {
        return distinctIgnoreNullToSet(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns the distinct values using distinct ignore null to set.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<String> distinctIgnoreNullToSet(@Nullable Collection<String> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * Returns the distinct values using distinct ignore empty.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] distinctIgnoreEmpty(@Nullable String... texts) {
        return distinctIgnoreEmpty(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns the distinct values using distinct ignore empty.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String[] distinctIgnoreEmpty(@Nullable Collection<String> texts) {
        Set<String> result = distinctIgnoreEmptyToSet(texts);
        return CollectionPlainWraps.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Returns the distinct values using distinct ignore empty to list.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> distinctIgnoreEmptyToList(@Nullable String... texts) {
        return distinctIgnoreEmptyToList(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns the distinct values using distinct ignore empty to list.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<String> distinctIgnoreEmptyToList(@Nullable Collection<String> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
    }

    /**
     * Returns the distinct values using distinct ignore empty to set.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> distinctIgnoreEmptyToSet(@Nullable String... texts) {
        return distinctIgnoreEmptyToSet(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns the distinct values using distinct ignore empty to set.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<String> distinctIgnoreEmptyToSet(@Nullable Collection<String> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
    }

    /**
     * Returns the distinct values using distinct ignore blank.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] distinctIgnoreBlank(@Nullable String... texts) {
        return distinctIgnoreBlank(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns the distinct values using distinct ignore blank.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String[] distinctIgnoreBlank(@Nullable Collection<String> texts) {
        Set<String> result = distinctIgnoreBlankToSet(texts);
        return CollectionPlainWraps.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Returns the distinct values using distinct ignore blank to list.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> distinctIgnoreBlankToList(@Nullable String... texts) {
        return distinctIgnoreBlankToList(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns the distinct values using distinct ignore blank to list.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<String> distinctIgnoreBlankToList(@Nullable Collection<String> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
    }

    /**
     * Returns the distinct values using distinct ignore blank to set.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> distinctIgnoreBlankToSet(@Nullable String... texts) {
        return distinctIgnoreBlankToSet(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns the distinct values using distinct ignore blank to set.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<String> distinctIgnoreBlankToSet(@Nullable Collection<String> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet());
    }

    /**
     * Checks if two char sequences are equal, case-sensitive
     *
     * @param sequence The first char sequence
     * @param comparison The second char sequence
     *
     * @return true if both char sequences are equal
     */
    public static boolean equals(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CS.equals(sequence, comparison);
    }

    /**
     * Checks if two char sequences are equal, case-insensitive
     *
     * @param sequence The first char sequence
     * @param comparison The second char sequence
     *
     * @return true if both char sequences are equal
     */
    public static boolean equalsIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CI.equals(sequence, comparison);
    }

    /**
     * Checks whether the sequence matches equals any.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean equalsAny(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return equalsAny(sequence, ArrayUtilsWraps.asList(comparisons));
    }

    /**
     * Checks whether the sequence matches equals any.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean equalsAny(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> comparisons) {
        return sequence != null && CollectionPlainWraps.isNotEmpty(comparisons) && comparisons.stream().anyMatch(item -> Strings.CS.equals(sequence, item));
    }

    /**
     * Checks whether the sequence matches equals any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean equalsAnyIgnoreCase(@Nullable CharSequence sequence, char... comparisons) {
        return Strings.CI.equalsAny(sequence, CharUtilsWraps.toStringArray(comparisons));
    }

    /**
     * Checks whether the sequence matches equals any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean equalsAnyIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return Strings.CI.equalsAny(sequence, comparisons);
    }

    /**
     * Checks whether the sequence matches equals any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean equalsAnyIgnoreCase(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> comparisons) {
        return sequence != null && CollectionPlainWraps.isNotEmpty(comparisons) && comparisons.stream().anyMatch(item -> Strings.CI.equals(sequence, item));
    }

    /**
     * Encodes the given value using encode to string.
     *
     * @param text The source text
     * @param ofCharset The source charset
     * @param toCharset The target charset
     *
     * @return the result, or null when it is unavailable
     */
    public static String encodeToString(@Nullable String text, @Nonnull Charset ofCharset, @Nonnull Charset toCharset) {
        return StringUtils.isEmpty(text) ? text : StringUtils.toEncodedString(text.getBytes(ofCharset), toCharset);
    }

    /**
     * Encodes the given value using encode to string.
     *
     * @param text The source text
     * @param ofCharset The source charset
     * @param toCharset The target charset
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String encodeToString(@Nullable String text, @Nonnull CharSequence ofCharset, @Nonnull CharSequence toCharset) {
        if (StringUtils.isEmpty(text) || CharsetPlainWraps.anyNotSupported(ofCharset, toCharset)) {
            return text;
        }
        try {
            return StringUtils.toEncodedString(text.getBytes(ofCharset.toString()), Charset.forName(toCharset.toString()));
        } catch (UnsupportedEncodingException ignored) {
        }
        return null;
    }

    /**
     * @see org.apache.commons.io.IOUtils#toString(byte[], java.lang.String)
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String encodeToString(@Nullable byte[] bytes, @Nullable CharSequence charset) {
        if (ArrayUtils.isEmpty(bytes) || CharsetPlainWraps.isNotSupported(charset)) {
            return null;
        }
        return StringUtils.toEncodedString(bytes, Charset.forName(charset.toString()));
    }

    /**
     * Encodes the given value using encode to string with ascii.
     *
     * @param bytes The bytes
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String encodeToStringWithAscii(@Nullable byte[] bytes) {
        return ArrayUtils.isEmpty(bytes) ? null : StringUtils.toEncodedString(bytes, StandardCharsets.US_ASCII);
    }

    /**
     * Encodes the given value using encode to string with utf8.
     *
     * @param bytes The bytes
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String encodeToStringWithUtf8(@Nullable byte[] bytes) {
        return ArrayUtils.isEmpty(bytes) ? null : StringUtils.toEncodedString(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Checks whether the sequence matches ends with.
     *
     * @param sequence The source char sequence
     * @param comparison The value to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean endsWith(@Nullable CharSequence sequence, char comparison) {
        return Strings.CS.endsWith(sequence, CharUtils.toString(comparison));
    }

    /**
     * Checks if the sequence ends with the comparison, case-sensitive
     *
     * @param sequence The source char sequence
     * @param comparison The suffix to search for
     *
     * @return true if the sequence ends with the comparison
     */
    public static boolean endsWith(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CS.endsWith(sequence, comparison);
    }

    /**
     * Checks whether the sequence matches ends with any.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean endsWithAny(@Nullable CharSequence sequence, char... comparisons) {
        return Strings.CS.endsWithAny(sequence, CharUtilsWraps.toStringArray(comparisons));
    }

    /**
     * Checks if the sequence ends with any of the comparisons, case-sensitive
     *
     * @param sequence The source char sequence
     * @param comparisons The suffixes to search for
     *
     * @return true if the sequence ends with any of the comparisons
     */
    public static boolean endsWithAny(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return Strings.CS.endsWithAny(sequence, comparisons);
    }

    /**
     * Checks whether the sequence matches ends with any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean endsWithAnyIgnoreCase(@Nullable CharSequence sequence, char... comparisons) {
        return endsWithAnyIgnoreCase(sequence, CharUtilsWraps.toStringArray(comparisons));
    }

    /**
     * Checks whether the sequence matches ends with any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean endsWithAnyIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return endsWithAnyIgnoreCase(sequence, ArrayUtilsWraps.asList(comparisons));
    }

    /**
     * Check if a CharSequence ends with any of the provided case-insensitive suffixes
     *
     * @param sequence The CharSequence to check
     * @param comparisons The case-insensitive CharSequence suffixes, may be empty or contain {@code null}
     *
     * @see org.apache.commons.lang3.Strings#endsWithAny
     */
    public static boolean endsWithAnyIgnoreCase(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> comparisons) {
        return StringUtils.isNotEmpty(sequence) && CollectionPlainWraps.isNotEmpty(comparisons) && comparisons.stream().anyMatch(item -> Strings.CI.endsWith(sequence, item));
    }

    /**
     * Checks if the sequence ends with the comparison, case-insensitive
     *
     * @param sequence The source char sequence
     * @param comparison The suffix to search for
     *
     * @return true if the sequence ends with the comparison
     */
    public static boolean endsWithIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CI.endsWith(sequence, comparison);
    }

    /**
     * Performs the firstNonBlank operation.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T extends CharSequence> T firstNonBlank(@Nullable Collection<T> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    /**
     * Performs the firstNonEmpty operation.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T extends CharSequence> T firstNonEmpty(@Nullable Collection<T> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(StringUtils::isNotEmpty).findFirst().orElse(null);
    }

    /**
     * Performs the forEachChars operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void forEachChars(@Nullable T sequence, @Nullable Consumer<Character> action) {
        forEachChars(sequence, action, null);
    }

    /**
     * Performs the forEachChars operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     * @param filter The predicate used to filter values
     */
    public static <T extends CharSequence> void forEachChars(@Nullable T sequence, @Nullable Consumer<Character> action, @Nullable Predicate<Character> filter) {
        if (StringUtils.isNotEmpty(sequence)) {
            ArrayUtilsWraps.forEach(sequence.toString().toCharArray(), action, filter);
        }
    }

    /**
     * Performs the forEachCharsBreakable operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void forEachCharsBreakable(@Nullable T sequence, @Nullable Function<Character, Boolean> action) {
        forEachCharsBreakable(sequence, action, null);
    }

    /**
     * Performs the forEachCharsBreakable operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     * @param filter The predicate used to filter values
     */
    public static <T extends CharSequence> void forEachCharsBreakable(@Nullable T sequence, @Nullable Function<Character, Boolean> action, @Nullable Predicate<Character> filter) {
        if (StringUtils.isNotEmpty(sequence)) {
            ArrayUtilsWraps.forEachBreakable(sequence.toString().toCharArray(), action, filter);
        }
    }

    /**
     * Performs the forEachCharsIndexing operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void forEachCharsIndexing(@Nullable T sequence, @Nullable BiConsumer<Integer, Character> action) {
        forEachCharsIndexing(sequence, action, null);
    }

    /**
     * Performs the forEachCharsIndexing operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     * @param filter The predicate used to filter values
     */
    public static <T extends CharSequence> void forEachCharsIndexing(@Nullable T sequence, @Nullable BiConsumer<Integer, Character> action, @Nullable BiPredicate<Integer, Character> filter) {
        if (StringUtils.isNotEmpty(sequence)) {
            ArrayUtilsWraps.forEachIndexing(sequence.toString().toCharArray(), action, filter);
        }
    }

    /**
     * Performs the ifBlank operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void ifBlank(@Nullable T sequence, @Nullable Consumer<? super T> action) {
        ifBlankOrElse(sequence, action, null);
    }

    /**
     * Performs the ifBlank operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void ifBlank(@Nullable T sequence, @Nullable Runnable action) {
        ifBlankOrElse(sequence, action, null);
    }

    /**
     * Performs the ifBlankOrElse operation.
     *
     * @param sequence The source char sequence
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <T extends CharSequence> void ifBlankOrElse(@Nullable T sequence, @Nullable Consumer<? super T> absentAction, @Nullable Consumer<? super T> presentAction) {
        if (StringUtils.isBlank(sequence)) {
            if (absentAction != null) {
                absentAction.accept(sequence);
            }
        } else {
            if (presentAction != null) {
                presentAction.accept(sequence);
            }
        }
    }

    /**
     * Performs the ifBlankOrElse operation.
     *
     * @param sequence The source char sequence
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <T extends CharSequence> void ifBlankOrElse(@Nullable T sequence, @Nullable Runnable absentAction, @Nullable Runnable presentAction) {
        if (StringUtils.isBlank(sequence)) {
            if (absentAction != null) {
                absentAction.run();
            }
        } else {
            if (presentAction != null) {
                presentAction.run();
            }
        }
    }

    /**
     * Performs the ifEmpty operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void ifEmpty(@Nullable T sequence, @Nullable Consumer<? super T> action) {
        ifEmptyOrElse(sequence, action, null);
    }

    /**
     * Performs the ifEmpty operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void ifEmpty(@Nullable T sequence, @Nullable Runnable action) {
        ifEmptyOrElse(sequence, action, null);
    }

    /**
     * Performs the ifEmptyOrElse operation.
     *
     * @param sequence The source char sequence
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <T extends CharSequence> void ifEmptyOrElse(@Nullable T sequence, @Nullable Consumer<? super T> absentAction, @Nullable Consumer<? super T> presentAction) {
        if (StringUtils.isEmpty(sequence)) {
            if (absentAction != null) {
                absentAction.accept(sequence);
            }
        } else {
            if (presentAction != null) {
                presentAction.accept(sequence);
            }
        }
    }

    /**
     * Performs the ifEmptyOrElse operation.
     *
     * @param sequence The source char sequence
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <T extends CharSequence> void ifEmptyOrElse(@Nullable T sequence, @Nullable Runnable absentAction, @Nullable Runnable presentAction) {
        if (StringUtils.isEmpty(sequence)) {
            if (absentAction != null) {
                absentAction.run();
            }
        } else {
            if (presentAction != null) {
                presentAction.run();
            }
        }
    }

    /**
     * Performs the ifNotBlank operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void ifNotBlank(@Nullable T sequence, @Nullable Consumer<? super T> action) {
        ifNotBlank(sequence, action, null);
    }

    /**
     * Performs the ifNotBlank operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     * @param filter The predicate used to filter values
     */
    public static <T extends CharSequence> void ifNotBlank(@Nullable T sequence, @Nullable Consumer<? super T> action, @Nullable Predicate<? super T> filter) {
        if (StringUtils.isNotBlank(sequence) && action != null && (filter == null || filter.test(sequence))) {
            action.accept(sequence);
        }
    }

    /**
     * Performs the ifNotBlank operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void ifNotBlank(@Nullable T sequence, @Nullable Runnable action) {
        if (StringUtils.isNotBlank(sequence) && action != null) {
            action.run();
        }
    }

    /**
     * Performs the ifNotEmpty operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void ifNotEmpty(@Nullable T sequence, @Nullable Consumer<? super T> action) {
        ifNotEmpty(sequence, action, null);
    }

    /**
     * Performs the ifNotEmpty operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     * @param filter The predicate used to filter values
     */
    public static <T extends CharSequence> void ifNotEmpty(@Nullable T sequence, @Nullable Consumer<? super T> action, @Nullable Predicate<? super T> filter) {
        if (StringUtils.isNotEmpty(sequence) && action != null && (filter == null || filter.test(sequence))) {
            action.accept(sequence);
        }
    }

    /**
     * Performs the ifNotEmpty operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void ifNotEmpty(@Nullable T sequence, @Nullable Runnable action) {
        if (StringUtils.isNotEmpty(sequence) && action != null) {
            action.run();
        }
    }

    /**
     * Finds the first index of the comparison, case-sensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     *
     * @return the first index, or -1 if not found
     */
    public static int indexOf(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CS.indexOf(sequence, comparison);
    }

    /**
     * Finds the first index of the comparison from the start position, case-sensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     * @param startPos The start position
     *
     * @return the first index, or -1 if not found
     */
    public static int indexOf(@Nullable CharSequence sequence, @Nullable CharSequence comparison, int startPos) {
        return Strings.CS.indexOf(sequence, comparison, startPos);
    }

    /**
     * Finds the index of the requested value using index of any.
     *
     * @param sequence The source char sequence
     * @param searches The texts to search for
     *
     * @return the matched index, or -1 when there is no match
     */
    public static int indexOfAny(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> searches) {
        if (StringUtils.isEmpty(sequence) || CollectionPlainWraps.isEmpty(searches)) {
            return -1;
        }
        return StringUtils.indexOfAny(sequence, searches.toArray(ArrayUtilsWraps.EMPTY_CHAR_SEQUENCE_ARRAY));
    }

    /**
     * Finds the first index of the comparison, case-insensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     *
     * @return the first index, or -1 if not found
     */
    public static int indexOfIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CI.indexOf(sequence, comparison);
    }

    /**
     * Finds the first index of the comparison from the start position, case-insensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     * @param startPos The start position
     *
     * @return the first index, or -1 if not found
     */
    public static int indexOfIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison, int startPos) {
        return Strings.CI.indexOf(sequence, comparison, startPos);
    }

    /**
     * Checks whether the sequence matches is distinct ignore null.
     *
     * @param texts The source texts
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isDistinctIgnoreNull(@Nullable String... texts) {
        return isDistinctIgnoreNull(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Checks whether the sequence matches is distinct ignore null.
     *
     * @param texts The source texts
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isDistinctIgnoreNull(@Nullable Collection<String> texts) {
        if (CollectionPlainWraps.isEmpty(texts)) {
            return false;
        }
        List<String> list = texts.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionPlainWraps.isEmpty(list)) {
            return false;
        }
        Set<String> set = new LinkedHashSet<>(list);
        return CollectionPlainWraps.isSameSize(list, set);
    }

    /**
     * Checks whether the sequence matches is distinct ignore empty.
     *
     * @param texts The source texts
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isDistinctIgnoreEmpty(@Nullable String... texts) {
        return isDistinctIgnoreEmpty(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Checks whether the sequence matches is distinct ignore empty.
     *
     * @param texts The source texts
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isDistinctIgnoreEmpty(@Nullable Collection<String> texts) {
        if (CollectionPlainWraps.isEmpty(texts)) {
            return false;
        }
        List<String> list = texts.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        if (CollectionPlainWraps.isEmpty(list)) {
            return false;
        }
        Set<String> set = new LinkedHashSet<>(list);
        return CollectionPlainWraps.isSameSize(list, set);
    }

    /**
     * Checks whether the sequence matches is distinct ignore blank.
     *
     * @param texts The source texts
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isDistinctIgnoreBlank(@Nullable String... texts) {
        return isDistinctIgnoreBlank(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Checks whether the sequence matches is distinct ignore blank.
     *
     * @param texts The source texts
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isDistinctIgnoreBlank(@Nullable Collection<String> texts) {
        if (CollectionPlainWraps.isEmpty(texts)) {
            return false;
        }
        List<String> list = texts.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if (CollectionPlainWraps.isEmpty(list)) {
            return false;
        }
        Set<String> set = new LinkedHashSet<>(list);
        return CollectionPlainWraps.isSameSize(list, set);
    }

    /**
     * Checks whether the sequence matches is wrapped with.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isWrappedWith(@Nullable CharSequence sequence, char delimiter) {
        return isWrappedWith(sequence, CharUtils.toString(delimiter));
    }

    /**
     * Checks whether the sequence matches is wrapped with.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isWrappedWith(@Nullable CharSequence sequence, @Nullable CharSequence delimiter) {
        return StringUtils.isNoneEmpty(sequence, delimiter) && StringUtils.length(sequence) >= 2 * StringUtils.length(delimiter) && Strings.CS.startsWith(sequence, delimiter) && Strings.CS.endsWith(sequence, delimiter);
    }

    /**
     * Checks whether the sequence matches is wrapped with ignore case.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isWrappedWithIgnoreCase(@Nullable CharSequence sequence, char delimiter) {
        return isWrappedWithIgnoreCase(sequence, CharUtils.toString(delimiter));
    }

    /**
     * Checks whether the sequence matches is wrapped with ignore case.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isWrappedWithIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence delimiter) {
        return StringUtils.isNoneEmpty(sequence, delimiter) && StringUtils.length(sequence) >= 2 * StringUtils.length(delimiter) && Strings.CI.startsWith(sequence, delimiter) && Strings.CI.endsWith(sequence, delimiter);
    }

    /**
     * Joins the given values using join with.
     *
     * @param objects The source objects
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static <T> String joinWith(@Nullable T[] objects, char delimiter) {
        return joinWith(ArrayUtilsWraps.asList(objects), delimiter);
    }

    /**
     * Joins the given values using join with.
     *
     * @param collection The source collection
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWith(@Nullable Collection<?> collection, char delimiter) {
        return joinWith(collection, CharUtils.toString(delimiter), null, null);
    }

    /**
     * Joins the given values using join with.
     *
     * @param objects The source objects
     * @param delimiter The delimiter
     * @param prefix The prefix
     * @param suffix The suffix
     *
     * @return the result, or null when it is unavailable
     */
    public static <T> String joinWith(@Nullable T[] objects, char delimiter, char prefix, char suffix) {
        return joinWith(ArrayUtilsWraps.asList(objects), delimiter, prefix, suffix);
    }

    /**
     * Joins the given values using join with.
     *
     * @param collection The source collection
     * @param delimiter The delimiter
     * @param prefix The prefix
     * @param suffix The suffix
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWith(@Nullable Collection<?> collection, char delimiter, char prefix, char suffix) {
        return joinWith(collection, CharUtils.toString(delimiter), CharUtils.toString(prefix), CharUtils.toString(suffix));
    }

    /**
     * Joins the given values using join with.
     *
     * @param objects The source objects
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static <T> String joinWith(@Nullable T[] objects, @Nullable CharSequence delimiter) {
        return joinWith(ArrayUtilsWraps.asList(objects), delimiter);
    }

    /**
     * Joins the given values using join with.
     *
     * @param collection The source collection
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWith(@Nullable Collection<?> collection, @Nullable CharSequence delimiter) {
        return joinWith(collection, delimiter, null, null);
    }

    /**
     * Joins the given values using join with.
     *
     * @param objects The source objects
     * @param delimiter The delimiter
     * @param prefix The prefix
     * @param suffix The suffix
     *
     * @return the result, or null when it is unavailable
     */
    public static <T> String joinWith(@Nullable T[] objects, @Nullable CharSequence delimiter, char prefix, char suffix) {
        return joinWith(ArrayUtilsWraps.asList(objects), delimiter, prefix, suffix);
    }

    /**
     * Joins the given values using join with.
     *
     * @param collection The source collection
     * @param delimiter The delimiter
     * @param prefix The prefix
     * @param suffix The suffix
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWith(@Nullable Collection<?> collection, @Nullable CharSequence delimiter, char prefix, char suffix) {
        return joinWith(collection, delimiter, CharUtils.toString(prefix), CharUtils.toString(suffix));
    }

    /**
     * Joins the given values using join with.
     *
     * @param objects The source objects
     * @param delimiter The delimiter
     * @param prefix The prefix
     * @param suffix The suffix
     *
     * @return the result, or null when it is unavailable
     */
    public static <T> String joinWith(@Nullable T[] objects, @Nullable CharSequence delimiter, @Nullable CharSequence prefix, @Nullable CharSequence suffix) {
        return joinWith(ArrayUtilsWraps.asList(objects), delimiter, prefix, suffix);
    }

    /**
     * Returns the joined items into a String containing the provided items, with delimiter, prefix and suffix
     *
     * <pre>
     *     StringUtilsWraps.joinWith(Arrays.asList(new String[]{"a", "b", "c"}), null) = "abc"
     *     StringUtilsWraps.joinWith(Arrays.asList(new String[]{"a", "b", "c"}), '*') = "a*b*c"
     * Joins the given values using join with.
     * </pre>
     *
     * @param collection The varargs providing the values to join together
     * @param delimiter The separator character to use, null treated as empty string
     * @param prefix The prefix for each item in the collection, null treated as empty string
     * @param suffix The suffix for each item in the collection, null treated as empty string
     *
     * @return the joined items into a String containing the provided items, with delimiter, prefix and suffix
     *
     * @see org.apache.commons.lang3.StringUtils#joinWith
     */
    @Nullable
    public static <T> String joinWith(@Nullable Collection<T> collection, @Nullable CharSequence delimiter, @Nullable CharSequence prefix, @Nullable CharSequence suffix) {
        if (CollectionPlainWraps.isEmpty(collection)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        int index = 0, size = collection.size();
        for (T item : collection) {
            builder.append(defaultString(prefix)).append(ObjectUtilsWraps.toString(item, StringUtils.EMPTY)).append(defaultString(suffix));
            if (index < size - 1) {
                builder.append(defaultString(delimiter));
            }
            index++;
        }
        return builder.toString();
    }

    /**
     * Joins the given values using join with colon.
     *
     * @param objects The source objects
     *
     * @return the result, or null when it is unavailable
     */
    @SafeVarargs
    public static <T> String joinWithColon(@Nullable T... objects) {
        return joinWithColon(ArrayUtilsWraps.asList(objects));
    }

    /**
     * Joins the given values using join with colon.
     *
     * @param collection The source collection
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithColon(@Nullable Collection<?> collection) {
        return joinWith(collection, CharVariantConst.COLON);
    }

    /**
     * Joins the given values using join with comma.
     *
     * @param objects The source objects
     *
     * @return the result, or null when it is unavailable
     */
    @SafeVarargs
    public static <T> String joinWithComma(@Nullable T... objects) {
        return joinWithComma(ArrayUtilsWraps.asList(objects));
    }

    /**
     * Joins the given values using join with comma.
     *
     * @param collection The source collection
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithComma(@Nullable Collection<?> collection) {
        return joinWith(collection, CharVariantConst.COMMA);
    }

    /**
     * Joins the given values using join with comma space.
     *
     * @param objects The source objects
     *
     * @return the result, or null when it is unavailable
     */
    @SafeVarargs
    public static <T> String joinWithCommaSpace(@Nullable T... objects) {
        return joinWithCommaSpace(ArrayUtilsWraps.asList(objects));
    }

    /**
     * Joins the given values using join with comma space.
     *
     * @param collection The source collection
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithCommaSpace(@Nullable Collection<?> collection) {
        return joinWith(collection, SymbolVariantConst.COMMA_SPACE);
    }

    /**
     * Joins the given values using join with dot.
     *
     * @param objects The source objects
     *
     * @return the result, or null when it is unavailable
     */
    @SafeVarargs
    public static <T> String joinWithDot(@Nullable T... objects) {
        return joinWithDot(ArrayUtilsWraps.asList(objects));
    }

    /**
     * Joins the given values using join with dot.
     *
     * @param collection The source collection
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithDot(@Nullable Collection<?> collection) {
        return joinWith(collection, CharVariantConst.DOT);
    }

    /**
     * Joins the given values using join with semicolon.
     *
     * @param objects The source objects
     *
     * @return the result, or null when it is unavailable
     */
    @SafeVarargs
    public static <T> String joinWithSemicolon(@Nullable T... objects) {
        return joinWithSemicolon(ArrayUtilsWraps.asList(objects));
    }

    /**
     * Joins the given values using join with semicolon.
     *
     * @param collection The source collection
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithSemicolon(@Nullable Collection<?> collection) {
        return joinWith(collection, CharVariantConst.SEMICOLON);
    }

    /**
     * Joins the given values using join with semicolon space.
     *
     * @param objects The source objects
     *
     * @return the result, or null when it is unavailable
     */
    @SafeVarargs
    public static <T> String joinWithSemicolonSpace(@Nullable T... objects) {
        return joinWithSemicolonSpace(ArrayUtilsWraps.asList(objects));
    }

    /**
     * Joins the given values using join with semicolon space.
     *
     * @param collection The source collection
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithSemicolonSpace(@Nullable Collection<?> collection) {
        return joinWith(collection, SymbolVariantConst.SEMICOLON_SPACE);
    }

    /**
     * Joins the given values using join with vertical.
     *
     * @param objects The source objects
     *
     * @return the result, or null when it is unavailable
     */
    @SafeVarargs
    public static <T> String joinWithVertical(@Nullable T... objects) {
        return joinWithVertical(ArrayUtilsWraps.asList(objects));
    }

    /**
     * Joins the given values using join with vertical.
     *
     * @param collection The source collection
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithVertical(@Nullable Collection<?> collection) {
        return joinWith(collection, CharVariantConst.VERTICAL);
    }

    /**
     * Joins the given values using join with ignore blank.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithIgnoreBlank(char delimiter, @Nullable CharSequence... sequences) {
        return joinWithIgnoreBlank(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join with ignore blank.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithIgnoreBlank(char delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        return joinWithIgnoreBlank(CharUtils.toString(delimiter), sequences);
    }

    /**
     * Joins the given values using join with ignore blank.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithIgnoreBlank(@Nullable CharSequence delimiter, @Nullable CharSequence... sequences) {
        return joinWithIgnoreBlank(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join with ignore blank.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String joinWithIgnoreBlank(@Nullable CharSequence delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isEmpty(sequences) ? null : joinWith(removeBlankSequence(sequences), delimiter);
    }

    /**
     * Joins the given values using join with ignore empty.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithIgnoreEmpty(char delimiter, @Nullable CharSequence... sequences) {
        return joinWithIgnoreEmpty(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join with ignore empty.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithIgnoreEmpty(char delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        return joinWithIgnoreEmpty(CharUtils.toString(delimiter), sequences);
    }

    /**
     * Joins the given values using join with ignore empty.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinWithIgnoreEmpty(@Nullable CharSequence delimiter, @Nullable CharSequence... sequences) {
        return joinWithIgnoreEmpty(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join with ignore empty.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String joinWithIgnoreEmpty(@Nullable CharSequence delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isEmpty(sequences) ? null : joinWith(removeEmptySequences(sequences), delimiter);
    }

    /**
     * Joins the given values using join once.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnce(char delimiter, @Nullable CharSequence... sequences) {
        return joinOnce(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join once.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnce(char delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        return joinOnce(CharUtils.toString(delimiter), sequences);
    }

    /**
     * Joins the given values using join once.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnce(@Nullable CharSequence delimiter, @Nullable CharSequence... sequences) {
        return joinOnce(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Returns the joined items into a single String containing the provided items, with one single delimiter
     *
     * <pre>
     *     StringUtilsWraps.joinWithOnce(null, "a", "b", "c") = "abc"
     *     StringUtilsWraps.joinWithOnce('', "a", "b", "c") = "abc"
     *     StringUtilsWraps.joinWithOnce(':', "a", "b", "c") = "a:b:c"
     *     StringUtilsWraps.joinWithOnce(':', "a:", "b", "c") = "a:b:c"
     *     StringUtilsWraps.joinWithOnce(':', "a", ":b", "c") = "a:b:c"
     *     StringUtilsWraps.joinWithOnce(':', "a", "b", ":c:") = "a:b:c:"
     * </pre>
     *
     * @param delimiter The separator character to use, null treated as empty string
     * @param sequences The varargs providing the values to join together
     *
     * @return the joined items into a single String containing the provided items, with one single delimiter
     *
     * @see org.apache.commons.lang3.StringUtils#joinWith
     */
    @Nullable
    public static String joinOnce(@Nullable CharSequence delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        if (CollectionPlainWraps.isEmpty(sequences)) {
            return null;
        }
        if (StringUtils.isEmpty(delimiter)) {
            return sequences.stream().map(StringUtilsWraps::defaultString).collect(Collectors.joining());
        }
        StringJoiner joiner = new StringJoiner(delimiter);
        int index = 0, size = sequences.size();
        for (CharSequence sequence : sequences) {
            if (index == 0) {
                joiner.add(defaultString(size == 1 ? sequence : removeEnd(sequence, delimiter)));
            } else {
                joiner.add(defaultString(removeStart(sequence, delimiter)));
            }
            index++;
        }
        return joiner.toString();
    }

    /**
     * Joins the given values using join once ignore case.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnceIgnoreCase(char delimiter, @Nullable CharSequence... sequences) {
        return joinOnceIgnoreCase(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join once ignore case.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnceIgnoreCase(char delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        return joinOnceIgnoreCase(CharUtils.toString(delimiter), sequences);
    }

    /**
     * Joins the given values using join once ignore case.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnceIgnoreCase(@Nullable CharSequence delimiter, @Nullable CharSequence... sequences) {
        return joinOnceIgnoreCase(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join once ignore case.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String joinOnceIgnoreCase(@Nullable CharSequence delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        if (CollectionPlainWraps.isEmpty(sequences)) {
            return null;
        }
        if (StringUtils.isEmpty(delimiter)) {
            return sequences.stream().map(StringUtilsWraps::defaultString).collect(Collectors.joining());
        }
        StringJoiner joiner = new StringJoiner(delimiter);
        int index = 0, size = sequences.size();
        for (CharSequence sequence : sequences) {
            if (index == 0) {
                joiner.add(defaultString(size == 1 ? sequence : removeEndIgnoreCase(sequence, delimiter)));
            } else {
                joiner.add(defaultString(removeStartIgnoreCase(sequence, delimiter)));
            }
            index++;
        }
        return joiner.toString();
    }

    /**
     * Joins the given values using join once ignore blank.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnceIgnoreBlank(char delimiter, @Nullable CharSequence... sequences) {
        return joinOnceIgnoreBlank(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join once ignore blank.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnceIgnoreBlank(char delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        return joinOnceIgnoreBlank(CharUtils.toString(delimiter), sequences);
    }

    /**
     * Joins the given values using join once ignore blank.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnceIgnoreBlank(@Nullable CharSequence delimiter, @Nullable CharSequence... sequences) {
        return joinOnceIgnoreBlank(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join once ignore blank.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String joinOnceIgnoreBlank(@Nullable CharSequence delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        if (CollectionPlainWraps.isEmpty(sequences)) {
            return null;
        }
        if (StringUtils.isEmpty(delimiter)) {
            return sequences.stream().map(StringUtilsWraps::defaultString).collect(Collectors.joining());
        }
        StringJoiner joiner = new StringJoiner(delimiter);
        int index = 0, size = sequences.size();
        for (CharSequence sequence : sequences) {
            String alias;
            if (index == 0) {
                alias = defaultString(size == 1 ? sequence : removeEnd(sequence, delimiter));
            } else {
                alias = defaultString(removeStart(sequence, delimiter));
            }
            if (StringUtils.isNotBlank(alias)) {
                joiner.add(alias);
            }
            index++;
        }
        return joiner.toString();
    }

    /**
     * Joins the given values using join once ignore empty.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnceIgnoreEmpty(char delimiter, @Nullable CharSequence... sequences) {
        return joinOnceIgnoreEmpty(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join once ignore empty.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnceIgnoreEmpty(char delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        return joinOnceIgnoreEmpty(CharUtils.toString(delimiter), sequences);
    }

    /**
     * Joins the given values using join once ignore empty.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinOnceIgnoreEmpty(@Nullable CharSequence delimiter, @Nullable CharSequence... sequences) {
        return joinOnceIgnoreEmpty(delimiter, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Joins the given values using join once ignore empty.
     *
     * @param delimiter The delimiter
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String joinOnceIgnoreEmpty(@Nullable CharSequence delimiter, @Nullable Collection<? extends CharSequence> sequences) {
        if (CollectionPlainWraps.isEmpty(sequences)) {
            return null;
        }
        if (StringUtils.isEmpty(delimiter)) {
            return sequences.stream().map(StringUtilsWraps::defaultString).collect(Collectors.joining());
        }
        StringJoiner joiner = new StringJoiner(delimiter);
        int index = 0, size = sequences.size();
        for (CharSequence sequence : sequences) {
            String alias;
            if (index == 0) {
                alias = defaultString(size == 1 ? sequence : removeEnd(sequence, delimiter));
            } else {
                alias = defaultString(removeStart(sequence, delimiter));
            }
            if (StringUtils.isNotEmpty(alias)) {
                joiner.add(alias);
            }
            index++;
        }
        return joiner.toString();
    }

    /**
     * Joins the given values using join roughly.
     *
     * @param objects The source objects
     *
     * @return the result, or null when it is unavailable
     */
    public static <T> String joinRoughly(@Nullable T[] objects) {
        return joinRoughly(ArrayUtilsWraps.asList());
    }

    /**
     * Joins the given values using join roughly.
     *
     * @param collection The source collection
     *
     * @return the result, or null when it is unavailable
     */
    public static String joinRoughly(@Nullable Collection<?> collection) {
        return joinWith(collection, StringUtils.EMPTY);
    }

    /**
     * Finds the last index of the comparison, case-sensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     *
     * @return the last index, or -1 if not found
     */
    public static int lastIndexOf(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CS.lastIndexOf(sequence, comparison);
    }

    /**
     * Finds the last index of the comparison from the start position, case-sensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     * @param startPos The start position
     *
     * @return the last index, or -1 if not found
     */
    public static int lastIndexOf(@Nullable CharSequence sequence, @Nullable CharSequence comparison, int startPos) {
        return Strings.CS.lastIndexOf(sequence, comparison, startPos);
    }

    /**
     * Finds the last index of the comparison, case-insensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     *
     * @return the last index, or -1 if not found
     */
    public static int lastIndexOfIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CI.lastIndexOf(sequence, comparison);
    }

    /**
     * Finds the last index of the comparison from the start position, case-insensitive
     *
     * @param sequence The source char sequence
     * @param comparison The char sequence to search for
     * @param startPos The start position
     *
     * @return the last index, or -1 if not found
     */
    public static int lastIndexOfIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison, int startPos) {
        return Strings.CI.lastIndexOf(sequence, comparison, startPos);
    }

    /**
     * Performs the lastSubstringBetween operation.
     *
     * @param text The source text
     * @param tag The tag
     *
     * @return the result, or null when it is unavailable
     */
    public static String lastSubstringBetween(@Nullable String text, char tag) {
        return lastSubstringBetween(text, tag, tag);
    }

    /**
     * Performs the lastSubstringBetween operation.
     *
     * @param text The source text
     * @param tag The tag
     *
     * @return the result, or null when it is unavailable
     */
    public static String lastSubstringBetween(@Nullable String text, @Nullable CharSequence tag) {
        return lastSubstringBetween(text, tag, tag);
    }

    /**
     * Performs the lastSubstringBetween operation.
     *
     * @param text The source text
     * @param open The opening text
     * @param close The closing text
     *
     * @return the result, or null when it is unavailable
     */
    public static String lastSubstringBetween(@Nullable String text, char open, char close) {
        return lastSubstringBetween(text, CharUtils.toString(open), CharUtils.toString(close));
    }

    /**
     * Returns a String that is nested in between two Strings, only the last match is returned
     *
     * @param text a String containing the substring
     * @param open The String before the substring
     * @param close The String after the substring
     *
     * @return a String that is nested in between two Strings, only the last match is returned
     *
     * @see org.apache.commons.lang3.StringUtils#substringBetween(String, String, String)
     */
    @Nullable
    public static String lastSubstringBetween(@Nullable String text, @Nullable CharSequence open, @Nullable CharSequence close) {
        if (StringUtils.isAnyEmpty(text, open, close)) {
            return null;
        }
        int start = Strings.CS.lastIndexOf(text, open), length = StringUtils.length(open);
        int end = Strings.CS.lastIndexOf(text, close, start + length);
        return (start != -1 && end != -1) ? StringUtils.substring(text, start + length, end) : null;
    }

    /**
     * Returns a fallback value using null if blank.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T extends CharSequence> T nullIfBlank(@Nullable T sequence) {
        return StringUtils.isBlank(sequence) ? null : sequence;
    }

    /**
     * Returns a fallback value using null if empty.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T extends CharSequence> T nullIfEmpty(@Nullable T sequence) {
        return StringUtils.isEmpty(sequence) ? null : sequence;
    }

    /**
     * Returns a fallback value using null if equals.
     *
     * @param sequence The source char sequence
     * @param comparison The value to compare
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static CharSequence nullIfEquals(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CS.equals(sequence, comparison) ? null : sequence;
    }

    /**
     * Returns a fallback value using null if equals ignore case.
     *
     * @param sequence The source char sequence
     * @param comparison The value to compare
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static CharSequence nullIfEqualsIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CI.equals(sequence, comparison) ? null : sequence;
    }

    /**
     * Returns a string that prepends the prefix to the start of the sequence if the sequence does not already start with any of the prefixes
     *
     * @param sequence The source char sequence to be prepended
     * @param prepend The prefix to prepend to the start of the string
     *
     * @return a string that prepends the prefix to the start of the string if the string does not already start with any of the prefixes
     */
    public static String prependIfMissing(@Nullable CharSequence sequence, char prepend) {
        return prependIfMissing(sequence, CharUtils.toString(prepend));
    }

    /**
     * Returns a string that prepends the prefix to the start of the sequence if the sequence does not already start with any of the prefixes
     *
     * @param sequence The source char sequence to be prepended
     * @param prepend The prefix to prepend to the start of the string
     *
     * @return a string that prepends the prefix to the start of the string if the string does not already start with any of the prefixes
     */
    public static String prependIfMissing(@Nullable CharSequence sequence, @Nullable CharSequence prepend) {
        return Strings.CS.prependIfMissing(CharSequenceWraps.toStringIgnoreNull(sequence), prepend, prepend);
    }

    /**
     * Adds text to the sequence using prepend if missing.
     *
     * @param collection The source collection
     * @param prepend The text to prepend
     */
    public static void prependIfMissing(@Nullable Collection<String> collection, @Nullable CharSequence prepend) {
        prependIfMissing(collection, prepend, ArrayUtilsWraps.singletonArray(prepend));
    }

    /**
     * Adds text to the sequence using prepend if missing.
     *
     * @param collection The source collection
     * @param prepend The text to prepend
     * @param prefixes The prefixes
     */
    public static void prependIfMissing(@Nullable Collection<String> collection, char prepend, char... prefixes) {
        prependIfMissing(collection, CharUtils.toString(prepend), CharUtilsWraps.toStringArray(prefixes));
    }

    /**
     * Adds text to the sequence using prepend if missing.
     *
     * @param collection The source collection
     * @param prepend The text to prepend
     * @param prefixes The prefixes
     */
    public static void prependIfMissing(@Nullable Collection<String> collection, char prepend, @Nullable CharSequence... prefixes) {
        prependIfMissing(collection, CharUtils.toString(prepend), prefixes);
    }

    /**
     * Adds text to the sequence using prepend if missing.
     *
     * @param collection The source collection
     * @param prepend The text to prepend
     * @param prefixes The prefixes
     */
    public static void prependIfMissing(@Nullable Collection<String> collection, @Nullable CharSequence prepend, char... prefixes) {
        prependIfMissing(collection, prepend, CharUtilsWraps.toStringArray(prefixes));
    }

    /**
     * Prepend the prefix to the start of the string if the string does not already start with any of the suffixes
     *
     * @param collection The collection to get the input from
     * @param prepend The prefix to append to the start of the string
     * @param prefixes additional prefixes that are valid terminators
     *
     * @throws java.lang.UnsupportedOperationException if the {@code clear} operation is not supported by this collection
     *
     * @see "org.springframework.util.ObjectUtils#addObjectToArray"
     */
    public static void prependIfMissing(@Nullable Collection<String> collection, @Nullable CharSequence prepend, @Nullable CharSequence... prefixes) {
        if (CollectionPlainWraps.isEmpty(collection)) {
            return;
        }
        String[] array = collection.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        collection.clear();
        Arrays.stream(array).map(item -> Strings.CS.prependIfMissing(item, prepend, prefixes)).forEach(collection::add);
    }

    /**
     * Prepends the prefix if the sequence does not already start with any of the prefixes, case-sensitive
     *
     * @param sequence The source char sequence
     * @param prepend The prefix to prepend
     * @param prefixes The additional prefixes that are valid terminators
     *
     * @return the sequence with the prefix prepended when needed
     */
    public static String prependIfMissing(@Nullable CharSequence sequence, @Nullable CharSequence prepend, @Nullable CharSequence... prefixes) {
        return Strings.CS.prependIfMissing(CharSequenceWraps.toStringIgnoreNull(sequence), prepend, prefixes);
    }

    /**
     * Returns a string that prepends the prefix to the start of the sequence if the sequence does not already start with any of the prefixes, case-insensitive
     *
     * @param sequence The source char sequence to be prepended
     * @param prepend The prefix to prepend to the start of the string
     *
     * @return a string that prepends the prefix to the start of the string if the string does not already start with any of the prefixes
     */
    public static String prependIfMissingIgnoreCase(@Nullable CharSequence sequence, char prepend) {
        return prependIfMissingIgnoreCase(sequence, CharUtils.toString(prepend));
    }

    /**
     * Returns a string that prepends the prefix to the start of the sequence if the sequence does not already start with any of the prefixes, case-insensitive
     *
     * @param sequence The source char sequence to be prepended
     * @param prepend The prefix to prepend to the start of the string
     *
     * @return a string that prepends the prefix to the start of the string if the string does not already start with any of the prefixes
     */
    public static String prependIfMissingIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence prepend) {
        return Strings.CI.prependIfMissing(CharSequenceWraps.toStringIgnoreNull(sequence), prepend, prepend);
    }

    /**
     * Adds text to the sequence using prepend if missing ignore case.
     *
     * @param collection The source collection
     * @param prepend The text to prepend
     */
    public static void prependIfMissingIgnoreCase(@Nullable Collection<String> collection, @Nullable CharSequence prepend) {
        prependIfMissingIgnoreCase(collection, prepend, ArrayUtilsWraps.singletonArray(prepend));
    }

    /**
     * Adds text to the sequence using prepend if missing ignore case.
     *
     * @param collection The source collection
     * @param prepend The text to prepend
     * @param prefixes The prefixes
     */
    public static void prependIfMissingIgnoreCase(@Nullable Collection<String> collection, char prepend, char... prefixes) {
        prependIfMissingIgnoreCase(collection, CharUtils.toString(prepend), CharUtilsWraps.toStringArray(prefixes));
    }

    /**
     * Adds text to the sequence using prepend if missing ignore case.
     *
     * @param collection The source collection
     * @param prepend The text to prepend
     * @param prefixes The prefixes
     */
    public static void prependIfMissingIgnoreCase(@Nullable Collection<String> collection, char prepend, @Nullable CharSequence... prefixes) {
        prependIfMissingIgnoreCase(collection, CharUtils.toString(prepend), prefixes);
    }

    /**
     * Adds text to the sequence using prepend if missing ignore case.
     *
     * @param collection The source collection
     * @param prefix The prefix
     * @param prefixes The prefixes
     */
    public static void prependIfMissingIgnoreCase(@Nullable Collection<String> collection, @Nullable CharSequence prefix, char... prefixes) {
        prependIfMissingIgnoreCase(collection, prefix, CharUtilsWraps.toStringArray(prefixes));
    }

    /**
     * Prepend the prefix to the start of the string if the string does not already start with any of the suffixes, case-insensitive
     *
     * @param collection The collection to get the input from
     * @param prepend The prefix to append to the start of the string
     * @param prefixes additional prefixes that are valid terminators
     *
     * @throws java.lang.UnsupportedOperationException if the {@code clear} operation is not supported by this collection
     *
     * @see "org.springframework.util.ObjectUtils#addObjectToArray"
     */
    public static void prependIfMissingIgnoreCase(@Nullable Collection<String> collection, @Nullable CharSequence prepend, @Nullable CharSequence... prefixes) {
        if (CollectionPlainWraps.isEmpty(collection)) {
            return;
        }
        String[] array = collection.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        collection.clear();
        Arrays.stream(array).map(item -> Strings.CI.prependIfMissing(item, prepend, prefixes)).forEach(collection::add);
    }

    /**
     * Prepends the prefix if the sequence does not already start with any of the prefixes, case-insensitive
     *
     * @param sequence The source char sequence
     * @param prepend The prefix to prepend
     * @param prefixes The additional prefixes that are valid terminators
     *
     * @return the sequence with the prefix prepended when needed
     */
    public static String prependIfMissingIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence prepend, @Nullable CharSequence... prefixes) {
        return Strings.CI.prependIfMissing(CharSequenceWraps.toStringIgnoreNull(sequence), prepend, prefixes);
    }

    /**
     * Adds text to the sequence using prepend if sequence not empty.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfSequenceNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence prepend) {
        return prependIfSequenceNotEmpty(sequence, prepend, null);
    }

    /**
     * Adds text to the sequence using prepend if sequence not empty.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfSequenceNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence prepend, char delimiter) {
        return prependIfSequenceNotEmpty(sequence, prepend, CharUtils.toString(delimiter));
    }

    /**
     * Adds text to the sequence using prepend if sequence not empty.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfSequenceNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence prepend, @Nullable CharSequence delimiter) {
        return StringUtils.isEmpty(sequence) ? CharSequenceWraps.toStringIgnoreNull(sequence) : StringUtils.join(prepend, delimiter, sequence);
    }

    /**
     * Adds text to the sequence using prepend if sequence not blank.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfSequenceNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence prepend) {
        return prependIfSequenceNotBlank(sequence, prepend, null);
    }

    /**
     * Adds text to the sequence using prepend if sequence not blank.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfSequenceNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence prepend, char delimiter) {
        return prependIfSequenceNotBlank(sequence, prepend, CharUtils.toString(delimiter));
    }

    /**
     * Adds text to the sequence using prepend if sequence not blank.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfSequenceNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence prepend, @Nullable CharSequence delimiter) {
        return StringUtils.isBlank(sequence) ? CharSequenceWraps.toStringIgnoreNull(sequence) : StringUtils.join(prepend, delimiter, sequence);
    }

    /**
     * Adds text to the sequence using prepend if prefix not empty.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfPrefixNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence prepend) {
        return prependIfPrefixNotEmpty(sequence, prepend, null);
    }

    /**
     * Adds text to the sequence using prepend if prefix not empty.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfPrefixNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence prepend, char delimiter) {
        return prependIfPrefixNotEmpty(sequence, prepend, CharUtils.toString(delimiter));
    }

    /**
     * Adds text to the sequence using prepend if prefix not empty.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfPrefixNotEmpty(@Nullable CharSequence sequence, @Nullable CharSequence prepend, @Nullable CharSequence delimiter) {
        return StringUtils.isEmpty(prepend) ? CharSequenceWraps.toStringIgnoreNull(sequence) : StringUtils.join(prepend, delimiter, sequence);
    }

    /**
     * Adds text to the sequence using prepend if prefix not blank.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfPrefixNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence prepend) {
        return prependIfPrefixNotBlank(sequence, prepend, null);
    }

    /**
     * Adds text to the sequence using prepend if prefix not blank.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfPrefixNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence prepend, char delimiter) {
        return prependIfPrefixNotBlank(sequence, prepend, CharUtils.toString(delimiter));
    }

    /**
     * Adds text to the sequence using prepend if prefix not blank.
     *
     * @param sequence The source char sequence
     * @param prepend The text to prepend
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String prependIfPrefixNotBlank(@Nullable CharSequence sequence, @Nullable CharSequence prepend, @Nullable CharSequence delimiter) {
        return StringUtils.isBlank(prepend) ? CharSequenceWraps.toStringIgnoreNull(sequence) : StringUtils.join(prepend, delimiter, sequence);
    }

    /**
     * Adds or removes delimiters using parenthesis.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String parenthesis(@Nullable CharSequence sequence) {
        return parenthesis(sequence, false);
    }

    /**
     * Adds or removes delimiters using parenthesis.
     *
     * @param sequence The source char sequence
     * @param emptyAsNull Whether an empty result should be returned as null
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String parenthesis(@Nullable CharSequence sequence, boolean emptyAsNull) {
        return (StringUtils.isEmpty(sequence) && emptyAsNull) ? null : StringUtils.join(CharVariantConst.PARENTHESIS_LEFT, sequence, CharVariantConst.PARENTHESIS_RIGHT);
    }

    /**
     * Adds or removes delimiters using quote single.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String quoteSingle(@Nullable CharSequence sequence) {
        return quoteSingle(sequence, false);
    }

    /**
     * Adds or removes delimiters using quote single.
     *
     * @param sequence The source char sequence
     * @param emptyAsNull Whether an empty result should be returned as null
     *
     * @return the result, or null when it is unavailable
     */
    public static String quoteSingle(@Nullable CharSequence sequence, boolean emptyAsNull) {
        return wrapWith(sequence, CharVariantConst.SINGLE_QUOTE, emptyAsNull);
    }

    /**
     * Adds or removes delimiters using quote double.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String quoteDouble(@Nullable CharSequence sequence) {
        return quoteDouble(sequence, false);
    }

    /**
     * Adds or removes delimiters using quote double.
     *
     * @param sequence The source char sequence
     * @param emptyAsNull Whether an empty result should be returned as null
     *
     * @return the result, or null when it is unavailable
     */
    public static String quoteDouble(@Nullable CharSequence sequence, boolean emptyAsNull) {
        return wrapWith(sequence, CharVariantConst.DOUBLE_QUOTE, emptyAsNull);
    }

    /**
     * Removes all occurrences of the comparison from the sequence, case-sensitive
     *
     * @param sequence The source char sequence
     * @param remove The char sequence to remove
     *
     * @return the sequence with all occurrences removed
     */
    public static String remove(@Nullable CharSequence sequence, char remove) {
        return Strings.CS.remove(CharSequenceWraps.toStringIgnoreNull(sequence), CharUtils.toString(remove));
    }

    /**
     * Removes the requested parts using remove.
     *
     * @param sequence The source char sequence
     * @param remove The text to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String remove(@Nullable CharSequence sequence, @Nullable CharSequence remove) {
        return Strings.CS.remove(CharSequenceWraps.toStringIgnoreNull(sequence), CharSequenceWraps.toStringIgnoreNull(remove));
    }

    /**
     * Removes the requested parts using remove all.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeAll(@Nullable CharSequence sequence, char... removes) {
        return removeAll(sequence, CharUtilsWraps.toStringArray(removes));
    }

    /**
     * Removes the requested parts using remove all.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeAll(@Nullable CharSequence sequence, @Nullable CharSequence... removes) {
        return removeAll(sequence, ArrayUtilsWraps.asList(removes));
    }

    /**
     * Returns a String that removed some substrings from within the source string
     *
     * @param sequence The source char sequence to search
     * @param removes some Strings to search for and remove
     *
     * @return a String that removed some substrings from within the source string
     *
     * @see org.apache.commons.lang3.Strings#remove
     */
    public static String removeAll(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> removes) {
        String text = CharSequenceWraps.toStringIgnoreNull(sequence);
        if (StringUtils.isEmpty(text) || removes == null || removes.isEmpty()) {
            return text;
        }
        for (CharSequence remove : removes) {
            text = Strings.CS.remove(text, CharSequenceWraps.toStringIgnoreNull(remove));
        }
        return text;
    }

    /**
     * Removes the requested parts using remove blank sequence.
     *
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static CharSequence[] removeBlankSequence(@Nullable CharSequence... sequences) {
        return removeBlankSequence(ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Returns char sequences which every item is not blank
     *
     * @param sequences The source of char sequences
     *
     * @return char sequences which every item is not blank
     */
    @Nullable
    public static CharSequence[] removeBlankSequence(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isEmpty(sequences) ? null : sequences.stream().filter(StringUtils::isNotBlank).toArray(CharSequence[]::new);
    }

    /**
     * Removes the requested parts using remove empty sequences.
     *
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static CharSequence[] removeEmptySequences(@Nullable CharSequence... sequences) {
        return removeEmptySequences(ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Returns char sequences which every item is not empty
     *
     * @param sequences The source of char sequences
     *
     * @return char sequences which every item is not empty
     */
    @Nullable
    public static CharSequence[] removeEmptySequences(@Nullable Collection<? extends CharSequence> sequences) {
        return CollectionPlainWraps.isEmpty(sequences) ? null : sequences.stream().filter(StringUtils::isNotEmpty).toArray(CharSequence[]::new);
    }

    /**
     * Removes the requested parts using remove blank string.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] removeBlankString(@Nullable String... texts) {
        return removeBlankString(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns strings which every item is not blank
     *
     * @param texts The source of strings
     *
     * @return strings which every item is not blank
     */
    @Nullable
    public static String[] removeBlankString(@Nullable Collection<String> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(StringUtils::isNotBlank).toArray(String[]::new);
    }

    /**
     * Removes the requested parts using remove empty strings.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] removeEmptyStrings(@Nullable String... texts) {
        return removeEmptyStrings(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Returns strings which every item is not empty
     *
     * @param texts The source of strings
     *
     * @return strings which every item is not empty
     */
    @Nullable
    public static String[] removeEmptyStrings(@Nullable Collection<String> texts) {
        return CollectionPlainWraps.isEmpty(texts) ? null : texts.stream().filter(StringUtils::isNotEmpty).toArray(String[]::new);
    }

    /**
     * Removes the requested parts using remove end.
     *
     * @param sequence The source char sequence
     * @param remove The text to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeEnd(@Nullable CharSequence sequence, char remove) {
        return removeEnd(sequence, CharUtils.toString(remove));
    }

    /**
     * Removes the requested parts using remove end.
     *
     * @param sequence The source char sequence
     * @param remove The text to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeEnd(@Nullable CharSequence sequence, @Nullable CharSequence remove) {
        return Strings.CS.removeEnd(CharSequenceWraps.toStringIgnoreNull(sequence), CharSequenceWraps.toStringIgnoreNull(remove));
    }

    /**
     * Removes the requested parts using remove end.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeEnd(@Nullable CharSequence sequence, char... removes) {
        return removeEnd(sequence, CharUtilsWraps.toStringArray(removes));
    }

    /**
     * Removes the requested parts using remove end.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeEnd(@Nullable CharSequence sequence, @Nullable CharSequence... removes) {
        return removeEnd(sequence, ArrayUtilsWraps.asList(removes));
    }

    /**
     * Returns a string that removed some substrings only if at end of the source string
     *
     * @param sequence The source char sequence to search
     * @param removes some Strings to search for and remove
     *
     * @return a string that removed some substrings only if at end of the source string
     *
     * @see org.apache.commons.lang3.Strings#removeEnd
     */
    public static String removeEnd(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> removes) {
        String text = CharSequenceWraps.toStringIgnoreNull(sequence);
        if (StringUtils.isEmpty(text) || CollectionPlainWraps.isEmpty(removes)) {
            return text;
        }
        for (CharSequence remove : removes) {
            text = Strings.CS.removeEnd(text, CharSequenceWraps.toStringIgnoreNull(remove));
        }
        return text;
    }

    /**
     * Removes the requested parts using remove end.
     *
     * @param collection The source collection
     * @param remove The text to remove
     */
    public static void removeEnd(@Nullable Collection<String> collection, char remove) {
        removeEnd(collection, CharUtils.toString(remove));
    }

    /**
     * Removes the requested parts using remove end.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    public static void removeEnd(@Nullable Collection<String> collection, char... removes) {
        removeEnd(collection, CharUtilsWraps.toStringArray(removes));
    }

    /**
     * Removes the requested parts using remove end.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    public static void removeEnd(@Nullable Collection<String> collection, @Nullable CharSequence... removes) {
        removeEnd(collection, ArrayUtilsWraps.asList(removes));
    }

    /**
     * Removes the requested parts using remove end.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void removeEnd(@Nullable Collection<String> collection, @Nullable Collection<? extends CharSequence> removes) {
        if (CollectionPlainWraps.anyEmpty(collection, removes)) {
            return;
        }
        String[] array = collection.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        collection.clear();
        Arrays.stream(array).map(item -> removeEnd(item, removes)).forEach(collection::add);
    }

    /**
     * Removes the requested parts using remove end ignore case.
     *
     * @param sequence The source char sequence
     * @param remove The text to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeEndIgnoreCase(@Nullable CharSequence sequence, char remove) {
        return removeEndIgnoreCase(sequence, CharUtils.toString(remove));
    }

    /**
     * Removes the requested parts using remove end ignore case.
     *
     * @param sequence The source char sequence
     * @param remove The text to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeEndIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence remove) {
        return Strings.CI.removeEnd(CharSequenceWraps.toStringIgnoreNull(sequence), CharSequenceWraps.toStringIgnoreNull(remove));
    }

    /**
     * Removes the requested parts using remove end ignore case.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeEndIgnoreCase(@Nullable CharSequence sequence, char... removes) {
        return removeEndIgnoreCase(sequence, CharUtilsWraps.toStringArray(removes));
    }

    /**
     * Removes the requested parts using remove end ignore case.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeEndIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence... removes) {
        return removeEndIgnoreCase(sequence, ArrayUtilsWraps.asList(removes));
    }

    /**
     * @see org.apache.commons.lang3.Strings#removeEnd
     */
    public static String removeEndIgnoreCase(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> removes) {
        String text = CharSequenceWraps.toStringIgnoreNull(sequence);
        if (StringUtils.isEmpty(text) || CollectionPlainWraps.isEmpty(removes)) {
            return text;
        }
        for (CharSequence remove : removes) {
            text = Strings.CI.removeEnd(text, CharSequenceWraps.toStringIgnoreNull(remove));
        }
        return text;
    }

    /**
     * Removes the requested parts using remove end ignore case.
     *
     * @param collection The source collection
     * @param remove The text to remove
     */
    public static void removeEndIgnoreCase(@Nullable Collection<String> collection, char remove) {
        removeEndIgnoreCase(collection, CharUtils.toString(remove));
    }

    /**
     * Removes the requested parts using remove end ignore case.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    public static void removeEndIgnoreCase(@Nullable Collection<String> collection, char... removes) {
        removeEndIgnoreCase(collection, CharUtilsWraps.toStringArray((removes)));
    }

    /**
     * Removes the requested parts using remove end ignore case.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    public static void removeEndIgnoreCase(@Nullable Collection<String> collection, @Nullable CharSequence... removes) {
        removeEndIgnoreCase(collection, ArrayUtilsWraps.asList(removes));
    }

    /**
     * Removes the requested parts using remove end ignore case.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void removeEndIgnoreCase(@Nullable Collection<String> collection, @Nullable Collection<? extends CharSequence> removes) {
        if (CollectionPlainWraps.anyEmpty(collection, removes)) {
            return;
        }
        String[] array = collection.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        collection.clear();
        Arrays.stream(array).map(item -> removeEndIgnoreCase(item, removes)).forEach(collection::add);
    }

    /**
     * Removes the requested parts using remove comma.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeComma(@Nullable CharSequence sequence) {
        return Strings.CS.remove(CharSequenceWraps.toStringIgnoreNull(sequence), CharUtils.toString(CharVariantConst.COMMA));
    }

    /**
     * Removes the first occurrence of the regex from the sequence
     *
     * @param sequence The source char sequence
     * @param regex The regular expression to remove
     *
     * @return the sequence with the first occurrence removed
     */
    public static String removeFirst(@Nullable CharSequence sequence, @Nullable CharSequence regex) {
        String text = CharSequenceWraps.toStringIgnoreNull(sequence);
        return StringUtils.isAnyEmpty(text, regex) ? text : RegExUtils.removeFirst(text, CharSequenceWraps.toStringIgnoreNull(regex));
    }

    /**
     * Removes all occurrences of the comparison from the sequence, case-insensitive
     *
     * @param sequence The source char sequence
     * @param remove The char sequence to remove
     *
     * @return the sequence with all occurrences removed
     */
    public static String removeIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence remove) {
        return Strings.CI.remove(CharSequenceWraps.toStringIgnoreNull(sequence), CharSequenceWraps.toStringIgnoreNull(remove));
    }

    /**
     * Removes all occurrences of the regex from the sequence
     *
     * @param sequence The source char sequence
     * @param regex The regular expression to remove
     *
     * @return the sequence with all occurrences removed
     */
    public static String removePattern(@Nullable CharSequence sequence, @Nullable CharSequence regex) {
        String text = CharSequenceWraps.toStringIgnoreNull(sequence);
        return StringUtils.isAnyEmpty(text, regex) ? text : RegExUtils.removeAll(text, CharSequenceWraps.toStringIgnoreNull(regex));
    }

    /**
     * Removes the requested parts using remove space.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeSpace(@Nullable CharSequence sequence) {
        return Strings.CS.remove(CharSequenceWraps.toStringIgnoreNull(sequence), CharUtils.toString(CharVariantConst.SPACE));
    }

    /**
     * Removes the requested parts using remove hex prefix.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeHexPrefix(@Nullable CharSequence sequence) {
        return removeStartIgnoreCase(sequence, SymbolVariantConst.HEX_PREFIX);
    }

    /**
     * Removes the requested parts using remove start.
     *
     * @param sequence The source char sequence
     * @param remove The text to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeStart(@Nullable CharSequence sequence, char remove) {
        return removeStart(sequence, CharUtils.toString(remove));
    }

    /**
     * Removes the requested parts using remove start.
     *
     * @param sequence The source char sequence
     * @param remove The text to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeStart(@Nullable CharSequence sequence, CharSequence remove) {
        return Strings.CS.removeStart(CharSequenceWraps.toStringIgnoreNull(sequence), CharSequenceWraps.toStringIgnoreNull(remove));
    }

    /**
     * Removes the requested parts using remove start.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeStart(@Nullable CharSequence sequence, char... removes) {
        return removeStart(sequence, CharUtilsWraps.toStringArray(removes));
    }

    /**
     * Removes the requested parts using remove start.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeStart(@Nullable CharSequence sequence, @Nullable CharSequence... removes) {
        return removeStart(sequence, ArrayUtilsWraps.asList(removes));
    }

    /**
     * Returns a string that removed some substrings only if at start of the source string
     *
     * @param sequence The source char sequence to search
     * @param removes some Strings to search for and remove
     *
     * @return a string that removed some substrings only if at start of the source string
     *
     * @see org.apache.commons.lang3.Strings#removeStart
     */
    public static String removeStart(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> removes) {
        String text = CharSequenceWraps.toStringIgnoreNull(sequence);
        if (StringUtils.isEmpty(text) || CollectionPlainWraps.isEmpty(removes)) {
            return text;
        }
        for (CharSequence remove : removes) {
            text = Strings.CS.removeStart(text, CharSequenceWraps.toStringIgnoreNull(remove));
        }
        return text;
    }

    /**
     * Removes the requested parts using remove start.
     *
     * @param collection The source collection
     * @param remove The text to remove
     */
    public static void removeStart(@Nullable Collection<String> collection, char remove) {
        removeStart(collection, CharUtils.toString(remove));
    }

    /**
     * Removes the requested parts using remove start.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    public static void removeStart(@Nullable Collection<String> collection, char... removes) {
        removeStart(collection, CharUtilsWraps.toStringArray(removes));
    }

    /**
     * Removes the requested parts using remove start.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    public static void removeStart(@Nullable Collection<String> collection, @Nullable CharSequence... removes) {
        removeStart(collection, ArrayUtilsWraps.asList(removes));
    }

    /**
     * Removes the requested parts using remove start.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void removeStart(@Nullable Collection<String> collection, @Nullable Collection<? extends CharSequence> removes) {
        if (CollectionPlainWraps.anyEmpty(collection, removes)) {
            return;
        }
        String[] array = collection.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        collection.clear();
        Arrays.stream(array).map(item -> removeStart(item, removes)).forEach(collection::add);
    }

    /**
     * Removes the requested parts using remove start ignore case.
     *
     * @param sequence The source char sequence
     * @param remove The text to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeStartIgnoreCase(@Nullable CharSequence sequence, char remove) {
        return Strings.CI.removeStart(CharSequenceWraps.toStringIgnoreNull(sequence), CharUtils.toString(remove));
    }

    /**
     * Removes the requested parts using remove start ignore case.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeStartIgnoreCase(@Nullable CharSequence sequence, @Nullable char... removes) {
        return removeStartIgnoreCase(sequence, CharUtilsWraps.toStringArray(removes));
    }

    /**
     * Removes the requested parts using remove start ignore case.
     *
     * @param sequence The source char sequence
     * @param removes The texts to remove
     *
     * @return the result, or null when it is unavailable
     */
    public static String removeStartIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence... removes) {
        return removeStartIgnoreCase(sequence, ArrayUtilsWraps.asList(removes));
    }

    /**
     * @see org.apache.commons.lang3.Strings#removeStart
     */
    public static String removeStartIgnoreCase(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> removes) {
        String text = CharSequenceWraps.toStringIgnoreNull(sequence);
        if (StringUtils.isEmpty(text) || CollectionPlainWraps.isEmpty(removes)) {
            return text;
        }
        for (CharSequence remove : removes) {
            text = Strings.CI.removeStart(text, CharSequenceWraps.toStringIgnoreNull(remove));
        }
        return text;
    }

    /**
     * Removes the requested parts using remove start ignore case.
     *
     * @param collection The source collection
     * @param remove The text to remove
     */
    public static void removeStartIgnoreCase(@Nullable Collection<String> collection, char remove) {
        removeStartIgnoreCase(collection, CharUtils.toString(remove));
    }

    /**
     * Removes the requested parts using remove start ignore case.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    public static void removeStartIgnoreCase(@Nullable Collection<String> collection, char... removes) {
        removeStartIgnoreCase(collection, CharUtilsWraps.toStringArray(removes));
    }

    /**
     * Removes the requested parts using remove start ignore case.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    public static void removeStartIgnoreCase(@Nullable Collection<String> collection, @Nullable CharSequence... removes) {
        removeStartIgnoreCase(collection, ArrayUtilsWraps.asList(removes));
    }

    /**
     * Removes the requested parts using remove start ignore case.
     *
     * @param collection The source collection
     * @param removes The texts to remove
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void removeStartIgnoreCase(@Nullable Collection<String> collection, @Nullable Collection<? extends CharSequence> removes) {
        if (CollectionPlainWraps.anyEmpty(collection, removes)) {
            return;
        }
        String[] array = collection.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        collection.clear();
        Arrays.stream(array).map(item -> removeStartIgnoreCase(item, removes)).forEach(collection::add);
    }

    /**
     * Replaces the requested parts using replace.
     *
     * @param text The source text
     * @param search The text to search for
     * @param replacement The replacement text
     *
     * @return the result, or null when it is unavailable
     */
    public static String replace(@Nullable String text, char search, char replacement) {
        return replace(text, search, replacement, -1);
    }

    /**
     * Replaces the requested parts using replace.
     *
     * @param text The source text
     * @param search The text to search for
     * @param replacement The replacement text
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static String replace(@Nullable String text, char search, char replacement, int max) {
        return StringUtils.isEmpty(text) ? text : Strings.CS.replace(text, CharUtils.toString(search), CharUtils.toString(replacement), max);
    }

    /**
     * Replaces the requested parts using replace.
     *
     * @param text The source text
     * @param search The text to search for
     * @param replacement The replacement text
     *
     * @return the result, or null when it is unavailable
     */
    public static String replace(@Nullable String text, @Nullable CharSequence search, @Nullable CharSequence replacement) {
        return replace(text, replacement, replacement, -1);
    }

    /**
     * Replaces the requested parts using replace.
     *
     * @param text The source text
     * @param search The text to search for
     * @param replacement The replacement text
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static String replace(@Nullable String text, @Nullable CharSequence search, @Nullable CharSequence replacement, int max) {
        return StringUtils.isAnyEmpty(text, search) ? text : Strings.CS.replace(text, CharSequenceWraps.toStringIgnoreNull(search), CharSequenceWraps.toStringIgnoreNull(replacement), max);
    }

    /**
     * Replaces the first occurrence of the regex in the text
     *
     * @param text The source text
     * @param regex The regular expression to replace
     * @param replacement The replacement text
     *
     * @return the text with the first occurrence replaced
     */
    public static String replaceFirst(@Nullable String text, @Nullable CharSequence regex, @Nullable CharSequence replacement) {
        return (StringUtils.isEmpty(text) || StringUtils.isEmpty(regex) || replacement == null) ? text : RegExUtils.replaceFirst(text, CharSequenceWraps.toStringIgnoreNull(regex), CharSequenceWraps.toStringIgnoreNull(replacement));
    }

    /**
     * Replaces the requested parts using replace ignore case.
     *
     * @param text The source text
     * @param search The text to search for
     * @param replacement The replacement text
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceIgnoreCase(@Nullable String text, char search, char replacement) {
        return replaceIgnoreCase(text, search, replacement, -1);
    }

    /**
     * Replaces the requested parts using replace ignore case.
     *
     * @param text The source text
     * @param search The text to search for
     * @param replacement The replacement text
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceIgnoreCase(@Nullable String text, char search, char replacement, int max) {
        return StringUtils.isEmpty(text) ? text : Strings.CI.replace(text, CharUtils.toString(search), CharUtils.toString(replacement), max);
    }

    /**
     * Replaces the requested parts using replace ignore case.
     *
     * @param text The source text
     * @param search The text to search for
     * @param replacement The replacement text
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceIgnoreCase(@Nullable String text, @Nullable CharSequence search, @Nullable CharSequence replacement) {
        return replaceIgnoreCase(text, replacement, replacement, -1);
    }

    /**
     * Replaces the requested parts using replace ignore case.
     *
     * @param text The source text
     * @param search The text to search for
     * @param replacement The replacement text
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceIgnoreCase(@Nullable String text, @Nullable CharSequence search, @Nullable CharSequence replacement, int max) {
        return StringUtils.isAnyEmpty(text, search) ? text : Strings.CI.replace(text, CharSequenceWraps.toStringIgnoreNull(search), CharSequenceWraps.toStringIgnoreNull(replacement), max);
    }

    /**
     * Replaces the requested parts using replace all.
     *
     * @param text The source text
     * @param replacement The replacement text
     * @param searches The texts to search for
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceAll(@Nullable String text, char replacement, char... searches) {
        if (StringUtils.isEmpty(text) || ArrayUtils.isEmpty(searches)) {
            return text;
        }
        String result = text;
        for (char search : searches) {
            result = replace(result, search, replacement);
        }
        return result;
    }

    /**
     * Replaces the requested parts using replace all.
     *
     * @param text The source text
     * @param replacement The replacement text
     * @param searches The texts to search for
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceAll(@Nullable String text, @Nullable CharSequence replacement, @Nullable CharSequence... searches) {
        return replaceAll(text, replacement, ArrayUtilsWraps.asList(searches));
    }

    /**
     * Replaces the requested parts using replace all.
     *
     * @param text The source text
     * @param replacement The replacement text
     * @param searches The texts to search for
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceAll(@Nullable String text, @Nullable CharSequence replacement, @Nullable Collection<? extends CharSequence> searches) {
        if (StringUtils.isEmpty(text) || CollectionPlainWraps.isEmpty(searches)) {
            return text;
        }
        String result = text;
        for (CharSequence search : searches) {
            result = replace(result, search, replacement);
        }
        return result;
    }

    /**
     * Replaces the requested parts using replace all.
     *
     * @param text The source text
     * @param replacements The replacement texts
     *
     * @return the result, or null when it is unavailable
     */
    public static <K extends CharSequence, V extends CharSequence> String replaceAll(@Nullable String text, @Nullable Map<K, V> replacements) {
        if (StringUtils.isEmpty(text) || MapPlainWraps.isEmpty(replacements)) {
            return text;
        }
        String result = text;
        for (Map.Entry<K, V> entry : replacements.entrySet()) {
            result = replace(result, entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Replaces the requested parts using replace all ignore case.
     *
     * @param text The source text
     * @param replacement The replacement text
     * @param searches The texts to search for
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceAllIgnoreCase(@Nullable String text, char replacement, char... searches) {
        if (StringUtils.isEmpty(text) || ArrayUtils.isEmpty(searches)) {
            return text;
        }
        String result = text;
        for (char search : searches) {
            result = replaceIgnoreCase(result, search, replacement);
        }
        return result;
    }

    /**
     * Replaces the requested parts using replace all ignore case.
     *
     * @param text The source text
     * @param replacement The replacement text
     * @param searches The texts to search for
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceAllIgnoreCase(@Nullable String text, @Nullable CharSequence replacement, @Nullable CharSequence... searches) {
        return replaceAllIgnoreCase(text, replacement, ArrayUtilsWraps.asList(searches));
    }

    /**
     * Replaces the requested parts using replace all ignore case.
     *
     * @param text The source text
     * @param replacement The replacement text
     * @param searches The texts to search for
     *
     * @return the result, or null when it is unavailable
     */
    public static String replaceAllIgnoreCase(@Nullable String text, @Nullable CharSequence replacement, @Nullable Collection<? extends CharSequence> searches) {
        if (StringUtils.isEmpty(text) || CollectionPlainWraps.isEmpty(searches)) {
            return text;
        }
        String result = text;
        for (CharSequence search : searches) {
            result = replaceIgnoreCase(result, search, replacement);
        }
        return result;
    }

    /**
     * Replaces the requested parts using replace all ignore case.
     *
     * @param text The source text
     * @param replacements The replacement texts
     *
     * @return the result, or null when it is unavailable
     */
    public static <K extends CharSequence, V extends CharSequence> String replaceAllIgnoreCase(@Nullable String text, @Nullable Map<K, V> replacements) {
        if (StringUtils.isEmpty(text) || MapPlainWraps.isEmpty(replacements)) {
            return text;
        }
        String result = text;
        for (Map.Entry<K, V> entry : replacements.entrySet()) {
            result = replaceIgnoreCase(result, entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Replaces the first occurrence of the search sequence in the text, case-sensitive
     *
     * @param text The source text
     * @param search The char sequence to search for
     * @param replacement The replacement text
     *
     * @return the text with the first occurrence replaced
     */
    public static String replaceOnce(@Nullable String text, @Nullable CharSequence search, @Nullable CharSequence replacement) {
        return Strings.CS.replaceOnce(text, CharSequenceWraps.toStringIgnoreNull(search), CharSequenceWraps.toStringIgnoreNull(replacement));
    }

    /**
     * Replaces the first occurrence of the search sequence in the text, case-insensitive
     *
     * @param text The source text
     * @param search The char sequence to search for
     * @param replacement The replacement text
     *
     * @return the text with the first occurrence replaced
     */
    public static String replaceOnceIgnoreCase(@Nullable String text, @Nullable CharSequence search, @Nullable CharSequence replacement) {
        return Strings.CI.replaceOnce(text, CharSequenceWraps.toStringIgnoreNull(search), CharSequenceWraps.toStringIgnoreNull(replacement));
    }

    /**
     * Replaces all occurrences of the regex in the text
     *
     * @param text The source text
     * @param regex The regular expression to replace
     * @param replacement The replacement text
     *
     * @return the text with all occurrences replaced
     */
    public static String replacePattern(@Nullable String text, @Nullable CharSequence regex, @Nullable CharSequence replacement) {
        return (StringUtils.isEmpty(text) || StringUtils.isEmpty(regex) || replacement == null) ? text : RegExUtils.replaceAll(text, CharSequenceWraps.toStringIgnoreNull(regex), CharSequenceWraps.toStringIgnoreNull(replacement));
    }

    /**
     * Performs the reverseForEachChars operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void reverseForEachChars(@Nullable T sequence, @Nullable Consumer<Character> action) {
        reverseForEachChars(sequence, action, null);
    }

    /**
     * Performs the reverseForEachChars operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     * @param filter The predicate used to filter values
     */
    public static <T extends CharSequence> void reverseForEachChars(@Nullable T sequence, @Nullable Consumer<Character> action, @Nullable Predicate<Character> filter) {
        if (StringUtils.isNotEmpty(sequence)) {
            ArrayUtilsWraps.reverseForEach(sequence.toString().toCharArray(), action, filter);
        }
    }

    /**
     * Performs the reverseForEachCharsBreakable operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void reverseForEachCharsBreakable(@Nullable T sequence, @Nullable Function<Character, Boolean> action) {
        reverseForEachCharsBreakable(sequence, action, null);
    }

    /**
     * Performs the reverseForEachCharsBreakable operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     * @param filter The predicate used to filter values
     */
    public static <T extends CharSequence> void reverseForEachCharsBreakable(@Nullable T sequence, @Nullable Function<Character, Boolean> action, @Nullable Predicate<Character> filter) {
        if (StringUtils.isNotEmpty(sequence)) {
            ArrayUtilsWraps.reverseForEachBreakable(sequence.toString().toCharArray(), action, filter);
        }
    }

    /**
     * Performs the reverseForEachCharsIndexing operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     */
    public static <T extends CharSequence> void reverseForEachCharsIndexing(@Nullable T sequence, @Nullable BiConsumer<Integer, Character> action) {
        reverseForEachCharsIndexing(sequence, action, null);
    }

    /**
     * Performs the reverseForEachCharsIndexing operation.
     *
     * @param sequence The source char sequence
     * @param action The action to perform
     * @param filter The predicate used to filter values
     */
    public static <T extends CharSequence> void reverseForEachCharsIndexing(@Nullable T sequence, @Nullable BiConsumer<Integer, Character> action, @Nullable BiPredicate<Integer, Character> filter) {
        if (StringUtils.isNotEmpty(sequence)) {
            ArrayUtilsWraps.reverseForEachIndexing(sequence.toString().toCharArray(), action, filter);
        }
    }

    /**
     * Splits the text using split by.
     *
     * @param text The source text
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] splitBy(@Nullable String text, char delimiter) {
        return splitBy(text, delimiter, false, -1);
    }

    /**
     * Splits the text using split by.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] splitBy(@Nullable String text, char delimiter, boolean trim) {
        return splitBy(text, delimiter, trim, -1);
    }

    /**
     * Splits the text using split by.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] splitBy(@Nullable String text, char delimiter, boolean trim, int max) {
        return splitBy(text, CharUtils.toString(delimiter), trim, max);
    }

    /**
     * Splits the text using split by.
     *
     * @param text The source text
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] splitBy(@Nullable String text, @Nullable CharSequence delimiter) {
        return splitBy(text, delimiter, false, -1);
    }

    /**
     * Splits the text using split by.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] splitBy(@Nullable String text, @Nullable CharSequence delimiter, boolean trim) {
        return splitBy(text, delimiter, trim, -1);
    }

    /**
     * Returns a split {@code String} array at the first occurrence of the delimiter
     *
     * @param text The string to split (potentially {@code null} or empty)
     * @param delimiter The separate string, {@code null} means use whitespace
     * @param trim indicates whether trim the split items or not
     * @param max The maximum number of items to include in the array. A zero or negative value implies no limit
     *
     * @return a split {@code String} array at the first occurrence of the delimiter
     */
    @Nullable
    public static String[] splitBy(@Nullable String text, @Nullable CharSequence delimiter, boolean trim, int max) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        String[] result = StringUtils.split(text, CharSequenceWraps.toStringIgnoreNull(delimiter), max);
        if (ArrayUtils.isEmpty(result)) {
            return null;
        }
        return trim ? trimStringArray(result) : result;
    }

    /**
     * Splits the text using split by to list.
     *
     * @param text The source text
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> splitByToList(@Nullable String text, char delimiter) {
        return splitByToList(text, delimiter, false, -1);
    }

    /**
     * Splits the text using split by to list.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> splitByToList(@Nullable String text, char delimiter, boolean trim) {
        return splitByToList(text, delimiter, trim, -1);
    }

    /**
     * Splits the text using split by to list.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> splitByToList(@Nullable String text, char delimiter, boolean trim, int max) {
        return splitByToList(text, CharUtils.toString(delimiter), trim, max);
    }

    /**
     * Splits the text using split by to list.
     *
     * @param text The source text
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> splitByToList(@Nullable String text, @Nullable CharSequence delimiter) {
        return splitByToList(text, delimiter, false, -1);
    }

    /**
     * Splits the text using split by to list.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> splitByToList(@Nullable String text, @Nullable CharSequence delimiter, boolean trim) {
        return splitByToList(text, delimiter, trim, -1);
    }

    /**
     * Splits the text using split by to list.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> splitByToList(@Nullable String text, @Nullable CharSequence delimiter, boolean trim, int max) {
        return ArrayUtilsWraps.asList(true, splitBy(text, delimiter, trim, max));
    }

    /**
     * Splits the text using split by to set.
     *
     * @param text The source text
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> splitByToSet(@Nullable String text, char delimiter) {
        return splitByToSet(text, delimiter, false, -1);
    }

    /**
     * Splits the text using split by to set.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> splitByToSet(@Nullable String text, char delimiter, boolean trim) {
        return splitByToSet(text, delimiter, trim, -1);
    }

    /**
     * Splits the text using split by to set.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> splitByToSet(@Nullable String text, char delimiter, boolean trim, int max) {
        return splitByToSet(text, CharUtils.toString(delimiter), trim, max);
    }

    /**
     * Splits the text using split by to set.
     *
     * @param text The source text
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> splitByToSet(@Nullable String text, @Nullable CharSequence delimiter) {
        return splitByToSet(text, delimiter, false, -1);
    }

    /**
     * Splits the text using split by to set.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> splitByToSet(@Nullable String text, @Nullable CharSequence delimiter, boolean trim) {
        return splitByToSet(text, delimiter, trim, -1);
    }

    /**
     * Splits the text using split by to set.
     *
     * @param text The source text
     * @param delimiter The delimiter
     * @param trim The characters to trim
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> splitByToSet(@Nullable String text, @Nullable CharSequence delimiter, boolean trim, int max) {
        return ArrayUtilsWraps.asSet(splitBy(text, delimiter, trim, max));
    }

    /**
     * Splits the text using split by comma.
     *
     * @param text The source text
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] splitByComma(@Nullable String text) {
        return splitByComma(text, false, -1);
    }

    /**
     * Splits the text using split by comma.
     *
     * @param text The source text
     * @param trim The characters to trim
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] splitByComma(@Nullable String text, boolean trim) {
        return splitByComma(text, trim, -1);
    }

    /**
     * Splits the text using split by comma.
     *
     * @param text The source text
     * @param trim The characters to trim
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] splitByComma(@Nullable String text, boolean trim, int max) {
        return splitBy(text, CharVariantConst.COMMA, trim, max);
    }

    /**
     * Splits the text using split by comma to list.
     *
     * @param text The source text
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> splitByCommaToList(@Nullable String text) {
        return splitByCommaToList(text, false);
    }

    /**
     * Splits the text using split by comma to list.
     *
     * @param text The source text
     * @param trim The characters to trim
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> splitByCommaToList(@Nullable String text, boolean trim) {
        return splitByCommaToList(text, trim, -1);
    }

    /**
     * Splits the text using split by comma to list.
     *
     * @param text The source text
     * @param trim The characters to trim
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static List<String> splitByCommaToList(@Nullable String text, boolean trim, int max) {
        return ArrayUtilsWraps.asList(true, splitByComma(text, trim, max));
    }

    /**
     * Splits the text using split by comma to set.
     *
     * @param text The source text
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> splitByCommaToSet(@Nullable String text) {
        return splitByCommaToSet(text, false);
    }

    /**
     * Splits the text using split by comma to set.
     *
     * @param text The source text
     * @param trim The characters to trim
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> splitByCommaToSet(@Nullable String text, boolean trim) {
        return splitByCommaToSet(text, trim, -1);
    }

    /**
     * Splits the text using split by comma to set.
     *
     * @param text The source text
     * @param trim The characters to trim
     * @param max The maximum number of occurrences
     *
     * @return the result, or null when it is unavailable
     */
    public static Set<String> splitByCommaToSet(@Nullable String text, boolean trim, int max) {
        return ArrayUtilsWraps.asSet(splitByComma(text, trim, max));
    }

    /**
     * Checks whether the sequence matches starts with.
     *
     * @param sequence The source char sequence
     * @param comparison The value to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean startsWith(@Nullable CharSequence sequence, char comparison) {
        return Strings.CS.startsWith(sequence, CharUtils.toString(comparison));
    }

    /**
     * Checks if the sequence starts with the comparison, case-sensitive
     *
     * @param sequence The source char sequence
     * @param comparison The prefix to search for
     *
     * @return true if the sequence starts with the comparison
     */
    public static boolean startsWith(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CS.startsWith(sequence, comparison);
    }

    /**
     * Checks if the sequence starts with any of the comparisons, case-sensitive
     *
     * @param sequence The source char sequence
     * @param comparisons The prefixes to search for
     *
     * @return true if the sequence starts with any of the comparisons
     */
    public static boolean startsWithAny(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return Strings.CS.startsWithAny(sequence, comparisons);
    }

    /**
     * Checks whether the sequence matches starts with ignore case.
     *
     * @param sequence The source char sequence
     * @param comparison The value to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean startsWithIgnoreCase(@Nullable CharSequence sequence, char comparison) {
        return Strings.CI.startsWith(sequence, CharUtils.toString(comparison));
    }

    /**
     * Checks whether the sequence matches starts with any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean startsWithAnyIgnoreCase(@Nullable CharSequence sequence, char... comparisons) {
        return startsWithAnyIgnoreCase(sequence, CharUtilsWraps.toStringArray(comparisons));
    }

    /**
     * Checks whether the sequence matches starts with any ignore case.
     *
     * @param sequence The source char sequence
     * @param comparisons The values to compare
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean startsWithAnyIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence... comparisons) {
        return startsWithAnyIgnoreCase(sequence, ArrayUtilsWraps.asList(comparisons));
    }

    /**
     * Check if a CharSequence starts with any of the provided case-insensitive prefixes
     *
     * @param sequence The CharSequence to check
     * @param comparisons The case-insensitive CharSequence prefixes
     *
     * @see org.apache.commons.lang3.Strings#startsWithAny
     */
    public static boolean startsWithAnyIgnoreCase(@Nullable CharSequence sequence, @Nullable Collection<? extends CharSequence> comparisons) {
        return StringUtils.isNotEmpty(sequence) && CollectionPlainWraps.isNotEmpty(comparisons) && comparisons.stream().anyMatch(item -> Strings.CI.startsWith(sequence, item));
    }

    /**
     * Checks if the sequence starts with the comparison, case-insensitive
     *
     * @param sequence The source char sequence
     * @param comparison The prefix to search for
     *
     * @return true if the sequence starts with the comparison
     */
    public static boolean startsWithIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence comparison) {
        return Strings.CI.startsWith(sequence, comparison);
    }

    /**
     * Returns a substring using substring after.
     *
     * @param text The source text
     * @param separator The separator
     * @param length The requested length
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringAfter(@Nullable String text, char separator, int length) {
        return substringAfter(text, CharUtils.toString(separator), length);
    }

    /**
     * Returns the substring after the first occurrence of a separator, with specified length, the separator is not returned.
     * If length is negative, no length limit is applied (returns all characters after separator).
     *
     * <pre>
     *     StringUtilsWraps.substringAfter("foo.bar.bar", ".", 3) = "bar"
     *     StringUtilsWraps.substringAfter("foo.foobar.bar", ".", 3) = "foo"
     *     StringUtilsWraps.substringAfter("foo.foobar.bar", "*", 3) = null
     *     StringUtilsWraps.substringAfter("foo.bar.baz", ".", -1) = "bar.baz"
     *     StringUtilsWraps.substringAfter("foo.longtext", ".", -1) = "longtext"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for
     * @param length The max length of substring, or negative value for no limit
     *
     * @return the substring after the first occurrence of a separator, with specified length, the separator is not returned
     *
     * @see org.apache.commons.lang3.StringUtils#substringAfter(String, String)
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String substringAfter(@Nullable String text, @Nullable String separator, int length) {
        if (StringUtils.isAnyEmpty(text, separator)) {
            return null;
        }
        int index = text.indexOf(separator);
        if (index == -1 || index == text.length() - separator.length()) {
            return null;
        }
        // If length is negative, no limit; otherwise apply length constraint
        if (length < 0) {
            return text.substring(index + separator.length());
        }
        if (length == 0) {
            return null;
        }
        int endIndex = Math.min(index + separator.length() + length, text.length());
        return text.substring(index + separator.length(), endIndex);
    }

    /**
     * Returns a substring using substring after ignore case.
     *
     * @param text The source text
     * @param separator The separator
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringAfterIgnoreCase(@Nullable String text, char separator) {
        return substringAfterIgnoreCase(text, CharUtils.toString(separator));
    }

    /**
     * Returns the substring after the first occurrence of a separator (case-insensitive), the separator is not returned.
     * <p>
     * A {@code null} input String will return {@code null}.
     * A {@code null} separator will return the original string.
     *
     * <pre>
     *     StringUtilsWraps.substringAfterIgnoreCase("foo.BAR.baz", "bar") = ".baz"
     *     StringUtilsWraps.substringAfterIgnoreCase("foo.bar.baz", "BAR") = ".baz"
     *     StringUtilsWraps.substringAfterIgnoreCase("foo.bar.baz", "xyz") = null
     *     StringUtilsWraps.substringAfterIgnoreCase(null, "bar") = null
     *     StringUtilsWraps.substringAfterIgnoreCase("foo.bar", null) = "foo.bar"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for (case-insensitive)
     *
     * @return the substring after the first occurrence of a separator, the separator is not returned
     *         or {@code null} if separator not found
     *
     * @see org.apache.commons.lang3.StringUtils#substringAfter(String, String)
     * @see org.apache.commons.lang3.Strings#contains
     */
    @Nullable
    public static String substringAfterIgnoreCase(@Nullable String text, @Nullable String separator) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(separator)) {
            return text;
        }
        String lowerText = text.toLowerCase();
        String lowerSeparator = separator.toLowerCase();
        int index = lowerText.indexOf(lowerSeparator);
        if (index == -1) {
            return null;
        }
        return text.substring(index + separator.length());
    }

    /**
     * Returns a substring using substring after including.
     *
     * @param text The source text
     * @param separator The separator
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringAfterIncluding(@Nullable String text, char separator) {
        return substringAfterIncluding(text, CharUtils.toString(separator));
    }

    /**
     * Returns the substring after the first occurrence of a separator, the separator is returned
     *
     * <pre>
     *     StringUtilsWraps.substringAfterIncluding("foo.bar.bar", "bar") = "bar.bar"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for
     *
     * @return the substring after the first occurrence of a separator, the separator is returned
     *
     * @see org.apache.commons.lang3.StringUtils#substringAfter(String, String)
     */
    @Nullable
    public static String substringAfterIncluding(@Nullable String text, @Nullable String separator) {
        if (StringUtils.isAnyEmpty(text, separator) || !Strings.CS.contains(text, separator)) {
            return null;
        }
        return StringUtils.join(separator, StringUtils.substringAfter(text, separator));
    }

    /**
     * Returns a substring using substring after last.
     *
     * @param text The source text
     * @param separator The separator
     * @param length The requested length
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringAfterLast(@Nullable String text, char separator, int length) {
        return substringAfterLast(text, CharUtils.toString(separator), length);
    }

    /**
     * Returns a substring using substring after last including.
     *
     * @param text The source text
     * @param separator The separator
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringAfterLastIncluding(@Nullable String text, char separator) {
        return substringAfterLastIncluding(text, CharUtils.toString(separator));
    }

    /**
     * Returns the substring after the last occurrence of a separator, the separator is returned
     *
     * <pre>
     *     StringUtilsWraps.substringAfterIncluding("foo.bar.bar", "bar") = "bar"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for
     *
     * @return the substring after the last occurrence of a separator, the separator is returned
     *
     * @see org.apache.commons.lang3.StringUtils#substringAfter(String, String)
     */
    @Nullable
    public static String substringAfterLastIncluding(@Nullable String text, @Nullable String separator) {
        if (StringUtils.isAnyEmpty(text, separator) || !Strings.CS.contains(text, separator)) {
            return null;
        }
        return StringUtils.join(separator, StringUtils.substringAfterLast(text, separator));
    }

    /**
     * Returns the substring after the last occurrence of a separator, with specified length, the separator is not returned.
     * If length is negative, no length limit is applied (returns all characters after separator).
     *
     * <pre>
     *     StringUtilsWraps.substringAfterLast("foo.bar.bar", ".", 3) = "bar"
     *     StringUtilsWraps.substringAfterLast("foo.bar.foobar", ".", 3) = "foo"
     *     StringUtilsWraps.substringAfterLast("foo.bar.foobar", "*", 3) = null
     *     StringUtilsWraps.substringAfterLast("foo.bar.baz", ".", -1) = "baz"
     *     StringUtilsWraps.substringAfterLast("foo.longtext", ".", -1) = "longtext"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for
     * @param length The max length of substring, or negative value for no limit
     *
     * @return the substring after the last occurrence of a separator, with specified length, the separator is not returned
     *
     * @see org.apache.commons.lang3.StringUtils#substringAfterLast(String, String)
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String substringAfterLast(@Nullable String text, @Nullable String separator, int length) {
        if (StringUtils.isAnyEmpty(text, separator)) {
            return null;
        }
        int index = text.lastIndexOf(separator);
        if (index == -1 || index == text.length() - separator.length()) {
            return null;
        }
        // If length is negative, no limit; otherwise apply length constraint
        if (length < 0) {
            return text.substring(index + separator.length());
        }
        if (length == 0) {
            return null;
        }
        int endIndex = Math.min(index + separator.length() + length, text.length());
        return text.substring(index + separator.length(), endIndex);
    }

    /**
     * Returns a substring using substring before.
     *
     * @param text The source text
     * @param separator The separator
     * @param length The requested length
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringBefore(@Nullable String text, char separator, int length) {
        return substringBefore(text, CharUtils.toString(separator), length);
    }

    /**
     * Returns the substring before the first occurrence of a separator, with specified length, the separator is not returned.
     * If length is negative, no length limit is applied (returns all characters before separator).
     *
     * <pre>
     *     StringUtilsWraps.substringBefore("foo.foobar.bar", ".", 3) = "foo"
     *     StringUtilsWraps.substringBefore("foobar.foo.bar", ".", 3) = "bar"
     *     StringUtilsWraps.substringBefore("foo.foobar.bar", "*", 3) = null
     *     StringUtilsWraps.substringBefore("foo.bar.baz", ".", -1) = "foo"
     *     StringUtilsWraps.substringBefore("longtext.bar", ".", -1) = "longtext"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for
     * @param length The max length of substring, or negative value for no limit
     *
     * @return the substring before the first occurrence of a separator, with specified length, the separator is not returned
     *
     * @see org.apache.commons.lang3.StringUtils#substringBefore(String, String)
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String substringBefore(@Nullable String text, @Nullable String separator, int length) {
        if (StringUtils.isAnyEmpty(text, separator)) {
            return null;
        }
        int index = text.indexOf(separator);
        if (index == -1) {
            return null;
        }
        // If length is negative, no limit; otherwise apply length constraint
        if (length < 0) {
            return text.substring(0, index);
        }
        if (length == 0 || index < length) {
            return null;
        }
        return text.substring(index - length, index);
    }

    /**
     * Returns a substring using substring before ignore case.
     *
     * @param text The source text
     * @param separator The separator
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringBeforeIgnoreCase(@Nullable String text, char separator) {
        return substringBeforeIgnoreCase(text, CharUtils.toString(separator));
    }

    /**
     * Returns the substring before the first occurrence of a separator (case-insensitive), the separator is not returned.
     * <p>
     * A {@code null} input String will return {@code null}.
     * A {@code null} separator will return the original string.
     *
     * <pre>
     *     StringUtilsWraps.substringBeforeIgnoreCase("foo.BAR.baz", "bar") = "foo."
     *     StringUtilsWraps.substringBeforeIgnoreCase("foo.bar.baz", "BAR") = "foo."
     *     StringUtilsWraps.substringBeforeIgnoreCase("foo.bar.baz", "xyz") = null
     *     StringUtilsWraps.substringBeforeIgnoreCase(null, "bar") = null
     *     StringUtilsWraps.substringBeforeIgnoreCase("foo.bar", null) = "foo.bar"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for (case-insensitive)
     *
     * @return the substring before the first occurrence of a separator, the separator is not returned
     *         or {@code null} if separator not found
     *
     * @see org.apache.commons.lang3.StringUtils#substringBefore(String, String)
     * @see org.apache.commons.lang3.Strings#contains
     */
    @Nullable
    public static String substringBeforeIgnoreCase(@Nullable String text, @Nullable String separator) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(separator)) {
            return text;
        }
        String lowerText = text.toLowerCase();
        String lowerSeparator = separator.toLowerCase();
        int index = lowerText.indexOf(lowerSeparator);
        if (index == -1) {
            return null;
        }
        return text.substring(0, index);
    }

    /**
     * Returns a substring using substring before including.
     *
     * @param text The source text
     * @param separator The separator
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringBeforeIncluding(@Nullable String text, char separator) {
        return substringBeforeIncluding(text, CharUtils.toString(separator));
    }

    /**
     * Returns the substring before the first occurrence of a separator, the separator is returned
     *
     * <pre>
     *     StringUtilsWraps.substringBeforeIncluding("foo.bar.bar", "bar") = "foo.bar"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for
     *
     * @return the substring before the first occurrence of a separator, the separator is returned
     *
     * @see org.apache.commons.lang3.StringUtils#substringBefore(String, String)
     */
    @Nullable
    public static String substringBeforeIncluding(@Nullable String text, @Nullable String separator) {
        if (StringUtils.isAnyEmpty(text, separator) || !Strings.CS.contains(text, separator)) {
            return null;
        }
        return StringUtils.join(StringUtils.substringBefore(text, separator), separator);
    }

    /**
     * Returns a substring using substring before last.
     *
     * @param text The source text
     * @param separator The separator
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringBeforeLast(@Nullable String text, char separator) {
        return StringUtils.substringBeforeLast(text, CharUtils.toString(separator));
    }

    /**
     * Returns a substring using substring before last.
     *
     * @param text The source text
     * @param separator The separator
     * @param length The requested length
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringBeforeLast(@Nullable String text, char separator, int length) {
        return substringBeforeLast(text, CharUtils.toString(separator), length);
    }

    /**
     * Returns the substring before the last occurrence of a separator, with specified length, the separator is not returned.
     * If length is negative, no length limit is applied (returns all characters before separator).
     *
     * <pre>
     *     StringUtilsWraps.substringBeforeLast("foo.bar", ".", 3) = "foo"
     *     StringUtilsWraps.substringBeforeLast("foobar.bar", ".", 3) = "bar"
     *     StringUtilsWraps.substringBeforeLast("foobar.bar", "*", 3) = null
     *     StringUtilsWraps.substringBeforeLast("foo.bar.baz", ".", -1) = "foo.bar"
     *     StringUtilsWraps.substringBeforeLast("longtext.bar", ".", -1) = "longtext"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for
     * @param length The max length of substring, or negative value for no limit
     *
     * @return the substring before the last occurrence of a separator, with specified length, the separator is not returned
     *
     * @see org.apache.commons.lang3.StringUtils#substringBeforeLast(String, String)
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String substringBeforeLast(@Nullable String text, @Nullable String separator, int length) {
        if (StringUtils.isAnyEmpty(text, separator)) {
            return null;
        }
        int index = text.lastIndexOf(separator);
        if (index == -1) {
            return null;
        }
        // If length is negative, no limit; otherwise apply length constraint
        if (length < 0) {
            return text.substring(0, index);
        }
        if (length == 0 || index < length) {
            return null;
        }
        return text.substring(index - length, index);
    }

    /**
     * Returns a substring using substring before last including.
     *
     * @param text The source text
     * @param separator The separator
     *
     * @return the result, or null when it is unavailable
     */
    public static String substringBeforeLastIncluding(@Nullable String text, char separator) {
        return substringBeforeLastIncluding(text, CharUtils.toString(separator));
    }

    /**
     * Returns the substring before the last occurrence of a separator, the separator is returned
     *
     * <pre>
     *     StringUtilsWraps.substringBeforeIncluding("foo.bar.bar", "bar") = "foo.bar.bar"
     * </pre>
     *
     * @param text The String to get a substring from
     * @param separator The String to search for
     *
     * @return the substring before the last occurrence of a separator, the separator is returned
     *
     * @see org.apache.commons.lang3.StringUtils#substringBefore(String, String)
     */
    @Nullable
    public static String substringBeforeLastIncluding(@Nullable String text, @Nullable String separator) {
        if (StringUtils.isAnyEmpty(text, separator) || !Strings.CS.contains(text, separator)) {
            return null;
        }
        return StringUtils.join(StringUtils.substringBeforeLast(text, separator), separator);
    }

    /**
     * @see org.apache.commons.lang3.StringUtils#substringBetween(java.lang.String, java.lang.String)
     */
    @Nullable
    public static String substringBetween(@Nullable String text, char separator) {
        return StringUtils.isEmpty(text) ? null : StringUtils.substringBetween(text, CharUtils.toString(separator));
    }

    /**
     * @see org.apache.commons.lang3.StringUtils#substringBetween(java.lang.String, java.lang.String, java.lang.String)
     */
    @Nullable
    public static String substringBetween(@Nullable String text, char open, char close) {
        return StringUtils.isEmpty(text) ? null : StringUtils.substringBetween(text, CharUtils.toString(open), CharUtils.toString(close));
    }

    /**
     * @see org.apache.commons.lang3.StringUtils#substringsBetween(java.lang.String, java.lang.String, java.lang.String)
     */
    @Nullable
    public static String[] substringsBetween(@Nullable String text, char open, char close) {
        return StringUtils.isEmpty(text) ? null : StringUtils.substringsBetween(text, CharUtils.toString(open), CharUtils.toString(close));
    }

    /**
     * @see org.apache.commons.lang3.StringUtils#toRootLowerCase
     */
    @Nullable
    public static String toLowerCase(@Nullable CharSequence sequence) {
        return (sequence == null) ? null : sequence.toString().toLowerCase();
    }

    /**
     * @see org.apache.commons.lang3.StringUtils#toRootUpperCase
     */
    @Nullable
    public static String toUpperCase(@Nullable CharSequence sequence) {
        return (sequence == null) ? null : sequence.toString().toUpperCase();
    }

    /**
     * Trims the given values using trim char sequence.
     *
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static CharSequence[] trimCharSequence(@Nullable CharSequence... sequences) {
        return trimCharSequence(false, sequences);
    }

    /**
     * Trims the given values using trim char sequence.
     *
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static CharSequence[] trimCharSequence(@Nullable Collection<? extends CharSequence> sequences) {
        return trimCharSequence(false, sequences);
    }

    /**
     * Trims the given values using trim char sequence.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    public static CharSequence[] trimCharSequence(boolean emptyAsNull, @Nullable CharSequence... sequences) {
        return trimCharSequence(emptyAsNull, ArrayUtilsWraps.asList(sequences));
    }

    /**
     * Trims the given values using trim char sequence.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param sequences The source char sequences
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static CharSequence[] trimCharSequence(boolean emptyAsNull, @Nullable Collection<? extends CharSequence> sequences) {
        if (CollectionPlainWraps.isEmpty(sequences)) {
            return emptyAsNull ? null : CollectionPlainWraps.toElementArray(sequences, CharSequence.class);
        }
        return sequences.stream().map(CharSequenceWraps::toStringIgnoreNull).map(item -> emptyAsNull ? StringUtils.trimToNull(item) : StringUtils.trim(item)).toArray(CharSequence[]::new);
    }

    /**
     * Trims the given values using trim string array.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] trimStringArray(@Nullable String... texts) {
        return trimStringArray(false, texts);
    }

    /**
     * Trims the given values using trim string array.
     *
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] trimStringArray(@Nullable Collection<String> texts) {
        return trimStringArray(false, texts);
    }

    /**
     * Trims the given values using trim string array.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param texts The source texts
     *
     * @return the result, or null when it is unavailable
     */
    public static String[] trimStringArray(boolean emptyAsNull, @Nullable String... texts) {
        return trimStringArray(emptyAsNull, ArrayUtilsWraps.asList(texts));
    }

    /**
     * @see "org.springframework.util.StringUtils#trimArrayElements"
     */
    @Nullable
    public static String[] trimStringArray(boolean emptyAsNull, @Nullable Collection<String> texts) {
        if (CollectionPlainWraps.isEmpty(texts)) {
            return emptyAsNull ? null : CollectionPlainWraps.toElementArray(texts, String.class);
        }
        return texts.stream().map(item -> emptyAsNull ? StringUtils.trimToNull(item) : StringUtils.trim(item)).toArray(String[]::new);
    }

    /**
     * Trims the given values using trim string collection.
     *
     * @param texts The source texts
     */
    public static void trimStringCollection(@Nullable Collection<String> texts) {
        trimStringCollection(false, texts);
    }

    /**
     * Trims the given values using trim string collection.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param texts The source texts
     */
    public static void trimStringCollection(boolean emptyAsNull, @Nullable Collection<String> texts) {
        if (CollectionPlainWraps.isEmpty(texts)) {
            return;
        }
        String[] array = texts.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        texts.clear();
        Arrays.stream(array).map(item -> emptyAsNull ? StringUtils.trimToNull(item) : StringUtils.trim(item)).forEach(texts::add);
    }

    /**
     * Adds or removes delimiters using unquote.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String unquote(@Nullable CharSequence sequence) {
        return RegExUtils.replaceAll(CharSequenceWraps.toStringIgnoreNull(sequence), "^['\"`]|['\"`]$", StringUtils.EMPTY);    // $NON-NLS-1$
    }

    /**
     * Adds or removes delimiters using unwrap with.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String unwrapWith(@Nullable CharSequence sequence, char delimiter) {
        return removeEnd(removeStart(sequence, delimiter), delimiter);
    }

    /**
     * Adds or removes delimiters using unwrap with.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String unwrapWith(@Nullable CharSequence sequence, @Nullable CharSequence delimiter) {
        return removeEnd(removeStart(sequence, delimiter), delimiter);
    }

    /**
     * Adds or removes delimiters using unwrap with.
     *
     * @param sequence The source char sequence
     * @param delimiters The delimiters
     *
     * @return the result, or null when it is unavailable
     */
    public static String unwrapWith(@Nullable CharSequence sequence, char... delimiters) {
        return removeEnd(removeStart(sequence, delimiters), delimiters);
    }

    /**
     * Adds or removes delimiters using unwrap with.
     *
     * @param sequence The source char sequence
     * @param delimiters The delimiters
     *
     * @return the result, or null when it is unavailable
     */
    public static String unwrapWith(@Nullable CharSequence sequence, @Nullable CharSequence... delimiters) {
        return removeEnd(removeStart(sequence, delimiters), delimiters);
    }

    /**
     * Adds or removes delimiters using unwrap with.
     *
     * @param collection The source collection
     * @param delimiters The delimiters
     */
    public static void unwrapWith(@Nullable Collection<String> collection, char... delimiters) {
        removeStart(collection, delimiters);
        removeEnd(collection, delimiters);
    }

    /**
     * Adds or removes delimiters using unwrap with.
     *
     * @param collection The source collection
     * @param delimiters The delimiters
     */
    public static void unwrapWith(@Nullable Collection<String> collection, @Nullable CharSequence... delimiters) {
        removeStart(collection, delimiters);
        removeEnd(collection, delimiters);
    }

    /**
     * Adds or removes delimiters using unwrap with ignore case.
     *
     * @param sequence The source char sequence
     * @param delimiters The delimiters
     *
     * @return the result, or null when it is unavailable
     */
    public static String unwrapWithIgnoreCase(@Nullable CharSequence sequence, char... delimiters) {
        return removeStartIgnoreCase(removeEndIgnoreCase(sequence, delimiters), delimiters);
    }

    /**
     * Adds or removes delimiters using unwrap with ignore case.
     *
     * @param sequence The source char sequence
     * @param delimiters The delimiters
     *
     * @return the result, or null when it is unavailable
     */
    public static String unwrapWithIgnoreCase(@Nullable CharSequence sequence, @Nullable CharSequence... delimiters) {
        return removeStartIgnoreCase(removeEndIgnoreCase(sequence, delimiters), delimiters);
    }

    /**
     * Adds or removes delimiters using unwrap with ignore case.
     *
     * @param collection The source collection
     * @param delimiters The delimiters
     */
    public static void unwrapWithIgnoreCase(@Nullable Collection<String> collection, char... delimiters) {
        removeStartIgnoreCase(collection, delimiters);
        removeEndIgnoreCase(collection, delimiters);
    }

    /**
     * Adds or removes delimiters using unwrap with ignore case.
     *
     * @param collection The source collection
     * @param delimiters The delimiters
     */
    public static void unwrapWithIgnoreCase(@Nullable Collection<String> collection, @Nullable CharSequence... delimiters) {
        removeStartIgnoreCase(collection, delimiters);
        removeEndIgnoreCase(collection, delimiters);
    }

    /**
     * Adds or removes delimiters using unwrap with parentheses.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String unwrapWithParentheses(@Nullable CharSequence sequence) {
        return RegExUtils.replaceAll(CharSequenceWraps.toStringIgnoreNull(sequence), "^[\\(]|[\\)]$", StringUtils.EMPTY);    // $NON-NLS-1$
    }

    /**
     * Adds or removes delimiters using unwrap with square brackets.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String unwrapWithSquareBrackets(@Nullable CharSequence sequence) {
        return RegExUtils.replaceAll(CharSequenceWraps.toStringIgnoreNull(sequence), "^[\\[]|[\\]]$", StringUtils.EMPTY);    // $NON-NLS-1$
    }

    /**
     * Adds or removes delimiters using unwrap with curly brackets.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String unwrapWithCurlyBrackets(@Nullable CharSequence sequence) {
        return RegExUtils.replaceAll(CharSequenceWraps.toStringIgnoreNull(sequence), "^[\\{]|[\\}]$", StringUtils.EMPTY);    // $NON-NLS-1$
    }

    /**
     * Adds or removes delimiters using wrap with.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String wrapWith(@Nullable CharSequence sequence, char delimiter) {
        return wrapWith(sequence, delimiter, false);
    }

    /**
     * Adds or removes delimiters using wrap with.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     * @param emptyAsNull Whether an empty result should be returned as null
     *
     * @return the result, or null when it is unavailable
     */
    public static String wrapWith(@Nullable CharSequence sequence, char delimiter, boolean emptyAsNull) {
        return wrapWith(sequence, CharUtils.toString(delimiter), emptyAsNull);
    }

    /**
     * Adds or removes delimiters using wrap with.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     *
     * @return the result, or null when it is unavailable
     */
    public static String wrapWith(@Nullable CharSequence sequence, CharSequence delimiter) {
        return wrapWith(sequence, delimiter, false);
    }

    /**
     * Adds or removes delimiters using wrap with.
     *
     * @param sequence The source char sequence
     * @param delimiter The delimiter
     * @param emptyAsNull Whether an empty result should be returned as null
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String wrapWith(@Nullable CharSequence sequence, CharSequence delimiter, boolean emptyAsNull) {
        return (emptyAsNull && StringUtils.isEmpty(sequence)) ? null : StringUtils.join(delimiter, sequence, delimiter);
    }

    /**
     * Adds or removes delimiters using wrap with parentheses.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String wrapWithParentheses(@Nullable CharSequence sequence) {
        return wrapWithParentheses(sequence, false);
    }

    /**
     * Returns a string that wraps the {@code sequence} with parentheses ("()")
     *
     * @param sequence The source to be wrapped
     * @param emptyAsNull When {@code sequence} is empty, true means this will return {@code null}, otherwise this will return an empty parentheses
     *
     * @return a string that wraps the {@code sequence} with parentheses ("()")
     */
    @Nullable
    public static String wrapWithParentheses(@Nullable CharSequence sequence, boolean emptyAsNull) {
        return (emptyAsNull && StringUtils.isEmpty(sequence)) ? null : StringUtils.join(CharVariantConst.PARENTHESIS_LEFT, sequence, CharVariantConst.PARENTHESIS_RIGHT);
    }

    /**
     * Adds or removes delimiters using wrap with square brackets.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String wrapWithSquareBrackets(@Nullable CharSequence sequence) {
        return wrapWithSquareBrackets(sequence, false);
    }

    /**
     * Returns a string that wraps the {@code sequence} with square brackets ("[]")
     *
     * @param sequence The source to be wrapped
     * @param emptyAsNull When {@code sequence} is empty, true means this will return {@code null}, otherwise this will return an empty parentheses
     *
     * @return a string that wraps the {@code sequence} with square brackets ("[]")
     */
    @Nullable
    public static String wrapWithSquareBrackets(@Nullable CharSequence sequence, boolean emptyAsNull) {
        return (emptyAsNull && StringUtils.isEmpty(sequence)) ? null : StringUtils.join(CharVariantConst.SQUARE_BRACKET_LEFT, sequence, CharVariantConst.SQUARE_BRACKET_RIGHT);
    }

    /**
     * Adds or removes delimiters using wrap with curly brackets.
     *
     * @param sequence The source char sequence
     *
     * @return the result, or null when it is unavailable
     */
    public static String wrapWithCurlyBrackets(@Nullable CharSequence sequence) {
        return wrapWithCurlyBrackets(sequence, false);
    }

    /**
     * Returns a string that wraps the {@code sequence} with curly brackets ("{}")
     *
     * @param sequence The source to be wrapped
     * @param emptyAsNull When {@code sequence} is empty, true means this will return {@code null}, otherwise this will return an empty parentheses
     *
     * @return a string that wraps the {@code sequence} with curly brackets ("{}")
     */
    @Nullable
    public static String wrapWithCurlyBrackets(@Nullable CharSequence sequence, boolean emptyAsNull) {
        return (emptyAsNull && StringUtils.isEmpty(sequence)) ? null : StringUtils.join(CharVariantConst.CURLY_BRACKET_LEFT, sequence, CharVariantConst.CURLY_BRACKET_RIGHT);
    }

    /**
     * Checks whether the sequence matches is wrapped with parentheses.
     *
     * @param sequence The source char sequence
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isWrappedWithParentheses(@Nullable CharSequence sequence) {
        return startsWith(sequence, CharVariantConst.PARENTHESIS_LEFT) && endsWith(sequence, CharVariantConst.PARENTHESIS_RIGHT);
    }

    /**
     * Checks whether the sequence matches is wrapped with square brackets.
     *
     * @param sequence The source char sequence
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isWrappedWithSquareBrackets(@Nullable CharSequence sequence) {
        return startsWith(sequence, CharVariantConst.SQUARE_BRACKET_LEFT) && endsWith(sequence, CharVariantConst.SQUARE_BRACKET_RIGHT);
    }

    /**
     * Checks whether the sequence matches is wrapped with curly brackets.
     *
     * @param sequence The source char sequence
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isWrappedWithCurlyBrackets(@Nullable CharSequence sequence) {
        return startsWith(sequence, CharVariantConst.CURLY_BRACKET_LEFT) && endsWith(sequence, CharVariantConst.CURLY_BRACKET_RIGHT);
    }
}
