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

package com.jiovan.plexus.common.toolkit.text;


import java.util.Map;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.lookup.StringLookup;


/**
 * Lookup for {@link org.apache.commons.text.lookup.StringLookup}
 *
 * @param <V> the type of the values in the map
 *
 * @author David Hsing
 *
 * @see org.apache.commons.text.StringSubstitutor#StringSubstitutor(org.apache.commons.text.lookup.StringLookup)
 * @see "org.apache.commons.text.StrLookup.MapStrLookup"
 */
@SuppressWarnings("unused")
public class MapStringLookup<V> implements StringLookup {
    private final Map<String, V> map;
    private final boolean nullKeyAsPlaceholder;
    private final boolean nullValueAsPlaceholder;
    private final String placeholder;

    /**
     * Creates a lookup over the given map with an empty placeholder for null values.
     *
     * @param map the map to look up values in, or null when absent
     */
    public MapStringLookup(@Nullable Map<String, V> map) {
        this(map, false, true, StringUtils.EMPTY);
    }

    /**
     * Creates a lookup over the given map with an empty placeholder for missing keys and null values.
     *
     * @param map the map to look up values in, or null when absent
     * @param nullKeyAsPlaceholder whether to return the placeholder for keys absent from the map
     * @param nullValueAsPlaceholder whether to return the placeholder for keys mapped to null
     */
    public MapStringLookup(@Nullable Map<String, V> map, boolean nullKeyAsPlaceholder, boolean nullValueAsPlaceholder) {
        this(map, nullKeyAsPlaceholder, nullValueAsPlaceholder, StringUtils.EMPTY);
    }

    /**
     * Creates a lookup over the given map with the given placeholder behavior.
     *
     * @param map the map to look up values in, or null when absent
     * @param nullKeyAsPlaceholder whether to return the placeholder for keys absent from the map
     * @param nullValueAsPlaceholder whether to return the placeholder for keys mapped to null
     * @param placeholder the value returned in place of missing keys or null values, or null when absent
     */
    public MapStringLookup(@Nullable Map<String, V> map, boolean nullKeyAsPlaceholder, boolean nullValueAsPlaceholder, @Nullable String placeholder) {
        this.map = map;
        this.nullKeyAsPlaceholder = nullKeyAsPlaceholder;
        this.nullValueAsPlaceholder = nullValueAsPlaceholder;
        this.placeholder = placeholder;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key to look up, or null when absent
     *
     * @return the string representation of the mapped value, the placeholder when configured for a missing key or null value, or null otherwise
     */
    @Override
    public String apply(@Nullable String key) {
        if (map == null) {
            return null;
        }
        if (!map.containsKey(key)) {
            return nullKeyAsPlaceholder ? placeholder : null;
        }
        Object value = map.get(key);
        if (value == null) {
            return nullValueAsPlaceholder ? placeholder : null;
        }
        return value.toString();
    }

    /**
     * Looks up a String key to provide a String value.
     *
     * @param key the key to look up, or null when absent
     *
     * @return the matching value, or null when there is no match
     *
     * @deprecated Use {@link #apply(String)}.
     */
    @Override
    @Deprecated
    public String lookup(String key) {
        return apply(key);
    }
}
