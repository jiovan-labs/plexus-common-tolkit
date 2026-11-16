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


import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import jakarta.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;


/**
 * Utilities for {@link org.apache.commons.collections4.MapUtils}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.collections4.MapUtils
 * @see "com.google.common.collect.Maps"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class MapUtilsWraps {
    /**
     * Checks whether the map contains the given key.
     *
     * @param map The source map
     * @param key The key
     *
     * @return true when the key is present; false otherwise
     */
    public static <K> boolean containsKey(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return MapUtils.isNotEmpty(map) && map.containsKey(key);
    }

    /**
     * Checks whether the map contains the given value.
     *
     * @param map The source map
     * @param value The value
     *
     * @return true when the value is present; false otherwise
     */
    public static <V> boolean containsValue(@Nullable Map<?, ? super V> map, @Nullable V value) {
        return MapUtils.isNotEmpty(map) && map.containsValue(value);
    }

    /**
     * Checks whether the map contains the given key-value pair.
     *
     * @param map The source map
     * @param key The key
     * @param value The value
     *
     * @return true when the pair is present; false otherwise
     */
    public static <K, V> boolean containsKeyValue(@Nullable Map<K, V> map, @Nullable K key, @Nullable V value) {
        return MapUtils.isNotEmpty(map) && map.containsKey(key) && Objects.equals(map.get(key), value);
    }

    /**
     * Checks whether the map contains all the given keys.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return true when every key is present; false otherwise
     */
    @SafeVarargs
    public static <K> boolean containsAllKeys(@Nullable Map<? super K, ?> map, @Nullable K... keys) {
        return containsAllKeys(map, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Checks whether the map contains all the given keys.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return true when every key is present; false otherwise
     */
    public static <K> boolean containsAllKeys(@Nullable Map<? super K, ?> map, @Nullable Collection<K> keys) {
        return MapUtils.isNotEmpty(map) && CollectionUtils.isNotEmpty(keys) && CollectionUtils.containsAll(map.keySet(), keys);
    }

    /**
     * Checks whether the map contains any of the given keys.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return true when at least one key is present; false otherwise
     */
    @SafeVarargs
    public static <K> boolean containsAnyKeys(@Nullable Map<? super K, ?> map, @Nullable K... keys) {
        return containsAnyKeys(map, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Checks whether the map contains any of the given keys.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return true when at least one key is present; false otherwise
     */
    public static <K> boolean containsAnyKeys(@Nullable Map<? super K, ?> map, @Nullable Collection<K> keys) {
        return MapUtils.isNotEmpty(map) && CollectionUtils.isNotEmpty(keys) && CollectionUtils.containsAny(map.keySet(), keys);
    }

    /**
     * Checks whether the map contains all the given values.
     *
     * @param map The source map
     * @param values The values
     *
     * @return true when every value is present; false otherwise
     */
    @SafeVarargs
    public static <V> boolean containsAllValues(@Nullable Map<?, ? super V> map, @Nullable V... values) {
        return containsAllValues(map, ArrayUtilsWraps.asList(values));
    }

    /**
     * Checks whether the map contains all the given values.
     *
     * @param map The source map
     * @param values The values
     *
     * @return true when every value is present; false otherwise
     */
    public static <V> boolean containsAllValues(@Nullable Map<?, ? super V> map, @Nullable Collection<V> values) {
        return MapUtils.isNotEmpty(map) && CollectionUtils.isNotEmpty(values) && CollectionUtils.containsAll(map.values(), values);
    }

    /**
     * Checks whether the map contains any of the given values.
     *
     * @param map The source map
     * @param values The values
     *
     * @return true when at least one value is present; false otherwise
     */
    @SafeVarargs
    public static <V> boolean containsAnyValues(@Nullable Map<?, ? super V> map, @Nullable V... values) {
        return containsAnyValues(map, ArrayUtilsWraps.asList(values));
    }

    /**
     * Checks whether the map contains any of the given values.
     *
     * @param map The source map
     * @param values The values
     *
     * @return true when at least one value is present; false otherwise
     */
    public static <V> boolean containsAnyValues(@Nullable Map<?, ? super V> map, @Nullable Collection<V> values) {
        return MapUtils.isNotEmpty(map) && CollectionUtils.isNotEmpty(values) && CollectionUtils.containsAny(map.values(), values);
    }
}
