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
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.text.html.HTML;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.CollectionPlainWraps;
import com.jiovan.plexus.common.toolkit.util.ListPlainWraps;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Structure for {@link javax.swing.text.html.HTML.Tag}
 *
 * @author David Hsing
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public class HtmlTagStruct implements Serializable {
    private HTML.Tag tag;
    private String name;
    private String id;
    private String placeholder;
    private Map<HTML.Attribute, String> attributes = new LinkedHashMap<>();
    private Boolean disabled;
    private Boolean multiple;
    private Boolean readonly;
    private List<String> subNodes = new ArrayList<>();
    private Map<String, String> data = new LinkedHashMap<>();

    /**
     * Creates a structure for the given HTML tag.
     *
     * @param tag the HTML tag, or null when absent
     */
    public HtmlTagStruct(@Nullable HTML.Tag tag) {
        this.tag = tag;
    }

    /**
     * Creates a structure for the given HTML tag, name, and id.
     *
     * @param tag the HTML tag, or null when absent
     * @param name the name attribute value, or null when absent
     * @param id the id attribute value, or null when absent
     */
    public HtmlTagStruct(@Nullable HTML.Tag tag, @Nullable String name, @Nullable String id) {
        this.tag = tag;
        this.name = name;
        this.id = id;
    }

    /**
     * Creates a structure for the given HTML tag and attributes.
     *
     * @param tag the HTML tag, or null when absent
     * @param attributes the HTML attributes, or null when absent
     */
    public HtmlTagStruct(@Nullable HTML.Tag tag, @Nullable Map<HTML.Attribute, String> attributes) {
        this.tag = tag;
        this.attributes = attributes;
    }

    /**
     * Creates a structure for the given HTML tag, name, id, and attributes.
     *
     * @param tag the HTML tag, or null when absent
     * @param name the name attribute value, or null when absent
     * @param id the id attribute value, or null when absent
     * @param attributes the HTML attributes, or null when absent
     */
    public HtmlTagStruct(@Nullable HTML.Tag tag, @Nullable String name, @Nullable String id, @Nullable Map<HTML.Attribute, String> attributes) {
        this(tag, name, id);
        this.attributes = attributes;
    }

    /**
     * Adds an HTML attribute to this tag.
     *
     * @param key the attribute key, or null when absent
     * @param value the value, or null when absent
     * @return this structure
     */
    public HtmlTagStruct addAttribute(@Nullable HTML.Attribute key, @Nullable String value) {
        attributes = MapPlainWraps.newLinkedHashMapIfNull(attributes);
        MapPlainWraps.putIfKeyNotNull(attributes, key, value);
        return this;
    }

    /**
     * Adds a data entry to this tag.
     *
     * @param key the data key, or null or blank when absent
     * @param value the value, or null when absent
     * @return this structure
     */
    public HtmlTagStruct addData(@Nullable String key, @Nullable String value) {
        data = MapPlainWraps.newLinkedHashMapIfNull(data);
        MapPlainWraps.putIfKeyNotBlank(data, key, value);
        return this;
    }

    /**
     * Appends the given lines to the sub-nodes of this tag.
     *
     * @param lines the lines to add, or null when absent
     * @return this structure
     */
    public HtmlTagStruct appendSubNode(@Nullable String... lines) {
        return appendSubNode(ArrayUtilsWraps.asList(lines));
    }

    /**
     * Appends the given lines to the sub-nodes of this tag.
     *
     * @param lines the lines to add, or null when absent
     * @return this structure
     */
    public HtmlTagStruct appendSubNode(@Nullable Collection<String> lines) {
        subNodes = CollectionPlainWraps.newArrayListIfNull(subNodes);
        CollectionPlainWraps.addAll(subNodes, lines);
        return this;
    }

    /**
     * Prepends the given lines to the sub-nodes of this tag.
     *
     * @param lines the lines to add, or null when absent
     * @return this structure
     */
    public HtmlTagStruct prependSubNode(@Nullable String... lines) {
        return prependSubNode(ArrayUtilsWraps.asList(lines));
    }

    /**
     * Prepends the given lines to the sub-nodes of this tag.
     *
     * @param lines the lines to add, or null when absent
     * @return this structure
     */
    public HtmlTagStruct prependSubNode(@Nullable Collection<String> lines) {
        subNodes = CollectionPlainWraps.newArrayListIfNull(subNodes);
        ListPlainWraps.addAll(subNodes, 0, lines);
        return this;
    }

    /**
     * Removes all attributes from this tag.
     *
     * @return this structure
     */
    public HtmlTagStruct clearAttributes() {
        if (attributes != null) {
            attributes.clear();
        }
        return this;
    }

    /**
     * Removes all sub-nodes from this tag.
     *
     * @return this structure
     */
    public HtmlTagStruct clearSubNodes() {
        if (subNodes != null) {
            subNodes.clear();
        }
        return this;
    }

    /**
     * Removes all data entries from this tag.
     *
     * @return this structure
     */
    public HtmlTagStruct clearData() {
        if (data != null) {
            data.clear();
        }
        return this;
    }

    /**
     * Checks whether this tag has no sub-nodes.
     *
     * @return {@code true} when this tag has no sub-nodes; {@code false} otherwise
     */
    public boolean isSubNodesEmpty() {
        return CollectionPlainWraps.isEmpty(subNodes);
    }

    /**
     * Checks whether this tag has no data entries.
     *
     * @return {@code true} when this tag has no data entries; {@code false} otherwise
     */
    public boolean isDataEmpty() {
        return MapPlainWraps.isEmpty(data);
    }

    /**
     * Removes and returns the value of the given HTML attribute.
     *
     * @param key the attribute key, or null when absent
     * @return the removed attribute value, or null when the key is null or no value was mapped
     */
    public String removeAttribute(@Nullable HTML.Attribute key) {
        return (key == null) ? null : attributes.remove(key);
    }

    /**
     * Removes and returns the value associated with the given data key.
     *
     * @param key the data key, or null or blank when absent
     * @return the removed data value, or null when the key is null or blank or no value was mapped
     */
    public String removeData(@Nullable String key) {
        return StringUtils.isBlank(key) ? null : data.remove(key);
    }

    /**
     * Returns the HTML representation of this tag.
     *
     * @return the HTML representation of this tag, or null when no tag is set
     */
    @Override
    public String toString() {
        return toString(CharVariantConst.DOUBLE_QUOTE);
    }

    /**
     * Returns the HTML representation of this tag using the given attribute quote character.
     *
     * @param delimiter the quote character used to surround attribute values
     * @return the HTML representation of this tag, or null when no tag is set
     */
    @Nullable
    public String toString(char delimiter) {
        if (tag == null) {
            return null;
        }
        // prepare attributes
        Map<String, String> textAttrs = new LinkedHashMap<>();
        textAttrs.put("name", name);    // $NON-NLS-1$
        textAttrs.put("id", id);    // $NON-NLS-1$
        textAttrs.put("placeholder", placeholder);    // $NON-NLS-1$
        Map<String, Boolean> boolAttrs = new LinkedHashMap<>();
        boolAttrs.put("multiple", multiple);    // $NON-NLS-1$
        boolAttrs.put("disabled", disabled);    // $NON-NLS-1$
        boolAttrs.put("readonly", readonly);    // $NON-NLS-1$
        // build node string
        StringBuilder builder = new StringBuilder();
        builder.append(CharVariantConst.ANGLE_BRACKET_LEFT).append(tag);
        MapPlainWraps.forEach(textAttrs, (key, value) -> builder.append(String.format(" %s=%c%s%c", key, delimiter, value, delimiter)), (key, value) -> StringUtils.isNotEmpty(value));    // $NON-NLS-1$
        MapPlainWraps.forEach(attributes, (key, value) -> builder.append(String.format(" %s=%c%s%c", key.toString(), delimiter, StringUtilsWraps.defaultString(value), delimiter)), ObjectUtils::allNotNull);    // $NON-NLS-1$
        MapPlainWraps.forEach(data, (key, value) -> {
            if (value == null) {
                builder.append(String.format(" data-%s", key));    // $NON-NLS-1$
            } else {
                builder.append(String.format(" data-%s=%c%s%c", key, delimiter, StringUtilsWraps.defaultString(value), delimiter));    // $NON-NLS-1$
            }
        }, (key, value) -> StringUtils.isNotBlank(key));    // $NON-NLS-1$
        MapPlainWraps.forEach(boolAttrs, (key, value) -> builder.append(CharVariantConst.SPACE).append(key), (key, value) -> BooleanUtils.isTrue(value));
        if (tag.isBlock()) {
            builder.append(CharVariantConst.ANGLE_BRACKET_RIGHT);
            CollectionPlainWraps.forEach(subNodes, builder::append, Objects::nonNull);
            builder.append(CharVariantConst.ANGLE_BRACKET_LEFT).append(CharVariantConst.SLASH).append(tag).append(CharVariantConst.ANGLE_BRACKET_RIGHT);
        } else {
            builder.append(CharVariantConst.SLASH).append(CharVariantConst.ANGLE_BRACKET_RIGHT);
        }
        builder.append(CharVariantConst.ANGLE_BRACKET_RIGHT);
        return builder.toString();
    }
}
