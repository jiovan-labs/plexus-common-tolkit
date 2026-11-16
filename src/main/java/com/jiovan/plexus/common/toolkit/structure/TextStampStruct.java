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

package com.jiovan.plexus.common.toolkit.structure;


import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import com.jiovan.plexus.common.toolkit.constant.LogMessageConst;
import com.jiovan.plexus.common.toolkit.constant.SymbolVariantConst;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.CharSequenceWraps;
import com.jiovan.plexus.common.toolkit.util.CollectionPlainWraps;
import com.jiovan.plexus.common.toolkit.util.ListPlainWraps;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Structure with a string list and a timestamp
 *
 * @author David Hsing
 */
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Slf4j
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class TextStampStruct implements Serializable {
    private final List<String> texts = new ArrayList<>();
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Creates a structure with the given text messages.
     *
     * @param texts the text messages, or null when absent
     */
    public TextStampStruct(@Nullable String... texts) {
        addText(texts);
    }

    /**
     * Creates a structure with the given text messages.
     *
     * @param texts the text messages, or null when absent
     */
    public TextStampStruct(@Nullable Collection<String> texts) {
        addText(texts);
    }

    /**
     * Adds the given text messages to this structure.
     *
     * @param texts the text messages, or null when absent
     * @return this structure
     */
    public TextStampStruct addText(@Nullable String... texts) {
        return addText(ArrayUtilsWraps.asList(texts));
    }

    /**
     * Adds the given text messages to this structure.
     *
     * @param texts the text messages, or null when absent
     * @return this structure
     */
    public TextStampStruct addText(@Nullable Collection<String> texts) {
        texts = CollectionPlainWraps.newArrayListIfNull(texts);
        CollectionPlainWraps.addAllIfNotEmpty(this.texts, texts);
        return this;
    }

    /**
     * Inserts the given text messages at the given index.
     *
     * @param index the zero-based index
     * @param texts the text messages, or null when absent
     * @return this structure
     */
    public TextStampStruct addTextAt(int index, @Nullable String... texts) {
        return addTextAt(index, ArrayUtilsWraps.asList(texts));
    }

    /**
     * Inserts the given text messages at the given index.
     *
     * @param index the zero-based index
     * @param texts the text messages, or null when absent
     * @return this structure
     */
    public TextStampStruct addTextAt(int index, @Nullable Collection<String> texts) {
        texts = CollectionPlainWraps.newArrayListIfNull(texts);
        ListPlainWraps.addAllIfNotEmpty(this.texts, index, texts);
        return this;
    }

    /**
     * Removes all text messages from this structure.
     *
     * @return this structure
     */
    public TextStampStruct clearTexts() {
        texts.clear();
        return this;
    }

    /**
     * Returns the text message at the given index.
     *
     * @param index the zero-based index
     * @return the text at the given index, or null when the index is out of bounds
     */
    public String getText(int index) {
        return (CollectionPlainWraps.isEmpty(texts) || index < 0 || index >= texts.size()) ? null : texts.get(index);
    }

    /**
     * Returns the first text message.
     *
     * @return the first text message, or null when no texts are present
     */
    public String getFirstText() {
        return getText(0);
    }

    /**
     * Returns the last text message.
     *
     * @return the last text message, or null when no texts are present
     */
    public String getLastText() {
        return ListPlainWraps.getLast(texts);
    }

    /**
     * Returns all text messages joined without a delimiter.
     *
     * @return the joined text, or an empty string when no texts are present
     */
    public String getCompositeText() {
        return getCompositeText(null);
    }

    /**
     * Returns all text messages joined with the given delimiter.
     *
     * @param delimiter the delimiter character
     * @return the joined text, or an empty string when no texts are present
     */
    public String getCompositeText(char delimiter) {
        return getCompositeText(CharUtils.toString(delimiter));
    }

    /**
     * Returns all text messages joined with the given delimiter.
     *
     * @param delimiter the delimiter, or null when no delimiter is needed
     * @return the joined text, or an empty string when no texts are present
     */
    public String getCompositeText(@Nullable String delimiter) {
        return StringUtils.join(texts, StringUtilsWraps.defaultString(delimiter));
    }

    /**
     * Returns all text messages numbered and joined with the default ordering format.
     *
     * @return the ordered composite text, or null when no texts are present
     */
    public String getCompositeTextOrdering() {
        return getCompositeTextOrdering(null, SymbolVariantConst.ORDER_SQUARES_SPACE);
    }

    /**
     * Returns all text messages numbered and joined with the given delimiter.
     *
     * @param delimiter the delimiter character
     * @return the ordered composite text, or null when no texts are present
     */
    public String getCompositeTextOrdering(char delimiter) {
        return getCompositeTextOrdering(delimiter, SymbolVariantConst.ORDER_SQUARES_SPACE);
    }

    /**
     * Returns all text messages numbered with the given format and joined with the given delimiter.
     *
     * @param delimiter the delimiter character
     * @param order the ordering format, or null for no numbering
     * @return the ordered composite text, or null when no texts are present
     */
    public String getCompositeTextOrdering(char delimiter, @Nullable String order) {
        return getCompositeTextOrdering(CharUtils.toString(delimiter), order);
    }

    /**
     * Returns all text messages numbered and joined with the given delimiter.
     *
     * @param delimiter the delimiter, or null when no delimiter is needed
     * @return the ordered composite text, or null when no texts are present
     */
    public String getCompositeTextOrdering(@Nullable String delimiter) {
        return getCompositeTextOrdering(delimiter, SymbolVariantConst.ORDER_SQUARES_SPACE);
    }

    /**
     * Returns all text messages numbered with the given format and joined with the given delimiter.
     *
     * @param delimiter the delimiter, or null when no delimiter is needed
     * @param order the ordering format, or null for no numbering
     * @return the ordered composite text, or null when no texts are present
     */
    public String getCompositeTextOrdering(@Nullable String delimiter, @Nullable String order) {
        if (CollectionPlainWraps.isEmpty(texts)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(CollectionPlainWraps.forEachIndexingTailing(texts, (index, text) -> {
            if (StringUtils.isNotEmpty(order)) {
                try {
                    builder.append(String.format(order, index + 1));
                } catch (Exception ex) {
                    if (log.isWarnEnabled()) {
                        log.warn(LogMessageConst.EXCEPTION_OCCURRED_REASON, ex.getMessage());
                    }
                }
            }
            builder.append(text).append(StringUtilsWraps.defaultString(delimiter));
        }));
        return CharSequenceWraps.toStringIgnoreEmpty(builder);
    }

    /**
     * Checks whether this structure has no text messages.
     *
     * @return {@code true} when this structure has no text messages; {@code false} otherwise
     */
    public boolean isTextsEmpty() {
        return CollectionPlainWraps.isEmpty(texts);
    }

    /**
     * Removes the text message at the given index when the index is in bounds.
     *
     * @param index the zero-based index
     * @return this structure
     */
    public TextStampStruct removeText(int index) {
        if (CollectionPlainWraps.isIndexBound(texts, index)) {
            texts.remove(index);
        }
        return this;
    }
}
