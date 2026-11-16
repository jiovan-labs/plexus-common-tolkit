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


import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.math.Fraction;
import com.jiovan.plexus.common.toolkit.constant.AssertMessageConst;
import com.jiovan.plexus.common.toolkit.constant.JavaKeywordConst;
import com.jiovan.plexus.common.toolkit.function.TriConsumer;
import com.jiovan.plexus.common.toolkit.function.TriPredicate;


/**
 * Utilities for {@link java.util.Map}
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class MapPlainWraps {
    /**
     * Default load factor for {@link java.util.HashMap}/{@link java.util.LinkedHashMap} variants
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;

    /**
     * Checks whether all the given maps are empty.
     *
     * @param maps The source maps
     *
     * @return {@code true} when all maps are empty; {@code false} otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean allEmpty(@Nullable Map<?, ?>... maps) {
        return ArrayUtils.isEmpty(maps) || Arrays.stream(maps).allMatch(MapPlainWraps::isEmpty);
    }

    /**
     * Checks whether all the given maps are not empty.
     *
     * @param maps The source maps
     *
     * @return {@code true} when all maps contain entries; {@code false} otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean allNotEmpty(@Nullable Map<?, ?>... maps) {
        return ArrayUtils.isNotEmpty(maps) && Arrays.stream(maps).noneMatch(MapPlainWraps::isEmpty);
    }

    /**
     * Checks whether any of the given maps is empty.
     *
     * @param maps The source maps
     *
     * @return {@code true} when any map is empty; {@code false} otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean anyEmpty(@Nullable Map<?, ?>... maps) {
        return ArrayUtils.isNotEmpty(maps) && Arrays.stream(maps).anyMatch(MapPlainWraps::isEmpty);
    }

    /**
     * Checks whether any of the given maps is not empty.
     *
     * @param maps The source maps
     *
     * @return {@code true} when any map contains entries; {@code false} otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean anyNotEmpty(@Nullable Map<?, ?>... maps) {
        return ArrayUtils.isNotEmpty(maps) && Arrays.stream(maps).anyMatch(MapPlainWraps::isNotEmpty);
    }

    /**
     * Computes .
     *
     * @param map The source map
     * @param key The key
     * @param action The action to perform
     *
     * @return the computed value, or null when no computation was performed
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> V compute(@Nullable Map<K, V> map, @Nullable K key, @Nullable BiFunction<? super K, ? super V, ? extends V> action) {
        return ObjectUtils.anyNull(map, action) ? null : map.compute(key, action);
    }

    /**
     * Computes if absent.
     *
     * @param map The source map
     * @param key The key
     * @param action The action to perform
     *
     * @return the computed value, or null when no computation was performed
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> V computeIfAbsent(@Nullable Map<K, V> map, @Nullable K key, @Nullable Function<? super K, ? extends V> action) {
        return ObjectUtils.anyNull(map, action) ? null : map.computeIfAbsent(key, action);
    }

    /**
     * Computes a value when the existing value equals the comparison value.
     *
     * @param map The source map
     * @param key The key
     * @param comparison The value to compare
     * @param action The action to perform
     *
     * @return the computed value, or null when no computation was performed
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> V computeIfEquals(@Nullable Map<K, V> map, @Nullable K key, @Nullable V comparison, @Nullable BiFunction<? super K, ? super V, ? extends V> action) {
        return (ObjectUtils.anyNull(map, action) || !Objects.equals(map.get(key), comparison)) ? null : map.compute(key, action);
    }

    /**
     * Computes a value when the existing value equals the comparison value, ignoring case.
     *
     * @param map The source map
     * @param key The key
     * @param comparison The value to compare
     * @param action The action to perform
     *
     * @return the computed value, or null when no computation was performed
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V extends CharSequence> V computeIfEqualsIgnoreCase(@Nullable Map<K, V> map, @Nullable K key, @Nullable CharSequence comparison, @Nullable BiFunction<? super K, ? super V, ? extends V> action) {
        return (ObjectUtils.anyNull(map, action) || !StringUtilsWraps.equalsIgnoreCase(map.get(key), comparison)) ? null : map.compute(key, action);
    }

    /**
     * Computes if present.
     *
     * @param map The source map
     * @param key The key
     * @param action The action to perform
     *
     * @return the computed value, or null when no computation was performed
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> V computeIfPresent(@Nullable Map<K, V> map, @Nullable K key, @Nullable BiFunction<? super K, ? super V, ? extends V> action) {
        return ObjectUtils.anyNull(map, action) ? null : map.computeIfPresent(key, action);
    }

    /**
     * Checks whether the map contains the given key.
     *
     * @param map The source map
     * @param key The key
     *
     * @return {@code true} when the key is present; {@code false} otherwise
     */
    public static <K> boolean containsKey(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return isNotEmpty(map) && map.containsKey(key);
    }

    /**
     * Checks whether the map contains the given key, ignoring case.
     *
     * @param map The source map
     * @param key The key
     *
     * @return {@code true} when the key is present; {@code false} otherwise
     */
    public static <K extends CharSequence> boolean containsKeyIgnoreCase(@Nullable Map<K, ?> map, @Nullable K key) {
        return isNotEmpty(map) && (map.containsKey(key) || map.keySet().stream().anyMatch(item -> StringUtilsWraps.equalsIgnoreCase(item, key)));
    }

    /**
     * Checks whether the map contains the given value.
     *
     * @param map The source map
     * @param value The value
     *
     * @return {@code true} when the value is present; {@code false} otherwise
     */
    public static <V> boolean containsValue(@Nullable Map<?, ? super V> map, @Nullable V value) {
        return isNotEmpty(map) && map.containsValue(value);
    }

    /**
     * Checks whether the map contains the given value, ignoring case.
     *
     * @param map The source map
     * @param value The value
     *
     * @return {@code true} when the value is present; {@code false} otherwise
     */
    public static <V extends CharSequence> boolean containsValueIgnoreCase(@Nullable Map<?, V> map, @Nullable V value) {
        return isNotEmpty(map) && (map.containsValue(value) || map.values().stream().anyMatch(item -> StringUtilsWraps.equalsIgnoreCase(item, value)));
    }

    /**
     * Checks whether the map contains the given key-value pair.
     *
     * @param map The source map
     * @param key The key
     * @param value The value
     *
     * @return {@code true} when the pair is present; {@code false} otherwise
     */
    public static <K, V> boolean containsKeyValue(@Nullable Map<K, V> map, @Nullable K key, @Nullable V value) {
        return isNotEmpty(map) && map.containsKey(key) && Objects.equals(map.get(key), value);
    }

    /**
     * Checks whether the map contains a matching key and value, ignoring case.
     *
     * @param map The source map
     * @param key The key
     * @param value The value
     *
     * @return {@code true} when both match; {@code false} otherwise
     */
    public static <K extends CharSequence, V extends CharSequence> boolean containsKeyIgnoreCaseValueIgnoreCase(@Nullable Map<K, V> map, @Nullable K key, @Nullable V value) {
        return isNotEmpty(map) && map.entrySet().stream().anyMatch(item -> StringUtilsWraps.equalsIgnoreCase(item.getKey(), key) && StringUtilsWraps.equalsIgnoreCase(item.getValue(), value));
    }

    /**
     * Checks whether the map contains all the given keys.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return {@code true} when every key is present; {@code false} otherwise
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
     * @return {@code true} when every key is present; {@code false} otherwise
     */
    public static <K> boolean containsAllKeys(@Nullable Map<? super K, ?> map, @Nullable Collection<K> keys) {
        return isNotEmpty(map) && CollectionPlainWraps.containsAll(map.keySet(), keys);
    }

    /**
     * Checks whether the map contains any of the given keys.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return {@code true} when at least one key is present; {@code false} otherwise
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
     * @return {@code true} when at least one key is present; {@code false} otherwise
     */
    public static <K> boolean containsAnyKeys(@Nullable Map<? super K, ?> map, @Nullable Collection<K> keys) {
        return isNotEmpty(map) && CollectionPlainWraps.containsAny(map.keySet(), keys);
    }

    /**
     * Checks whether the map contains all the given values.
     *
     * @param map The source map
     * @param values The values
     *
     * @return {@code true} when every value is present; {@code false} otherwise
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
     * @return {@code true} when every value is present; {@code false} otherwise
     */
    public static <V> boolean containsAllValues(@Nullable Map<?, ? super V> map, @Nullable Collection<V> values) {
        return isNotEmpty(map) && CollectionPlainWraps.containsAll(map.values(), values);
    }

    /**
     * Checks whether the map contains any of the given values.
     *
     * @param map The source map
     * @param values The values
     *
     * @return {@code true} when at least one value is present; {@code false} otherwise
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
     * @return {@code true} when at least one value is present; {@code false} otherwise
     */
    public static <V> boolean containsAnyValues(@Nullable Map<?, ? super V> map, @Nullable Collection<V> values) {
        return isNotEmpty(map) && CollectionPlainWraps.containsAny(map.values(), values);
    }

    /**
     * Returns null when the map is empty.
     *
     * @param map The source map
     *
     * @return the map, or null when it is empty
     */
    @Nullable
    public static <K, V> Map<K, V> emptyAsNull(@Nullable Map<K, V> map) {
        return isEmpty(map) ? null : map;
    }

    /**
     * Returns the first non-null value of the map.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return the first non-null value, or null when there is none
     */
    @Nullable
    @SafeVarargs
    public static <K, V> V firstNonNullValue(@Nullable Map<? super K, V> map, @Nullable K... keys) {
        return firstNonNullValue(map, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Returns the first non-null value of the map.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return the first non-null value, or null when there is none
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> V firstNonNullValue(@Nullable Map<? super K, V> map, @Nullable Collection<K> keys) {
        return ObjectUtilsWraps.anyEmpty(map, keys) ? null : keys.stream().map(map::get).filter(Objects::nonNull).findFirst().orElse(null);
    }

    /**
     * Returns the first non-blank value of the map.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return the first non-blank value, or null when there is none
     */
    @Nullable
    @SafeVarargs
    public static <K, V extends CharSequence> V firstNonBlankValue(@Nullable Map<? super K, V> map, @Nullable K... keys) {
        return firstNonBlankValue(map, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Returns the first non-blank value of the map.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return the first non-blank value, or null when there is none
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V extends CharSequence> V firstNonBlankValue(@Nullable Map<? super K, V> map, @Nullable Collection<K> keys) {
        return ObjectUtilsWraps.anyEmpty(map, keys) ? null : keys.stream().map(map::get).filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    /**
     * Returns the first non-empty value of the map.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return the first non-empty value, or null when there is none
     */
    @Nullable
    @SafeVarargs
    public static <K, V extends CharSequence> V firstNonEmptyValue(@Nullable Map<? super K, V> map, @Nullable K... keys) {
        return firstNonEmptyValue(map, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Returns the first non-empty value of the map.
     *
     * @param map The source map
     * @param keys The keys
     *
     * @return the first non-empty value, or null when there is none
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V extends CharSequence> V firstNonEmptyValue(@Nullable Map<? super K, V> map, @Nullable Collection<K> keys) {
        return ObjectUtilsWraps.anyEmpty(map, keys) ? null : keys.stream().map(map::get).filter(StringUtils::isNotEmpty).findFirst().orElse(null);
    }

    /**
     * Performs an action for each entry of the map.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <K, V> void forEach(@Nullable Map<K, V> map, @Nullable BiConsumer<? super K, ? super V> action) {
        forEach(map, action, null);
    }

    /**
     * Performs an action for each entry of the map.
     *
     * @param map The source map
     * @param action The action to perform
     * @param filter The predicate used to filter entries
     */
    public static <K, V> void forEach(@Nullable Map<K, V> map, @Nullable BiConsumer<? super K, ? super V> action, @Nullable BiPredicate<? super K, ? super V> filter) {
        if (isEmpty(map) || action == null) {
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (filter == null || filter.test(entry.getKey(), entry.getValue())) {
                action.accept(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Performs an action for each entry until the action returns false.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <K, V> void forEachBreakable(@Nullable Map<K, V> map, @Nullable BiFunction<? super K, ? super V, Boolean> action) {
        forEachBreakable(map, action, null);
    }

    /**
     * Performs an action for each entry until the action returns false.
     *
     * @param map The source map
     * @param action The action to perform
     * @param filter The predicate used to filter entries
     */
    public static <K, V> void forEachBreakable(@Nullable Map<K, V> map, @Nullable BiFunction<? super K, ? super V, Boolean> action, @Nullable BiPredicate<? super K, ? super V> filter) {
        if (isEmpty(map) || action == null) {
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if ((filter == null || filter.test(entry.getKey(), entry.getValue())) && BooleanUtils.isNotTrue(action.apply(entry.getKey(), entry.getValue()))) {
                break;
            }
        }
    }

    /**
     * Performs an action for each entry together with its index.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <K, V> void forEachIndexing(@Nullable Map<K, V> map, @Nullable TriConsumer<Integer, ? super K, ? super V> action) {
        forEachIndexing(map, action, null);
    }

    /**
     * Performs an action for each entry together with its index.
     *
     * @param map The source map
     * @param action The action to perform
     * @param filter The predicate used to filter entries
     */
    public static <K, V> void forEachIndexing(@Nullable Map<K, V> map, @Nullable TriConsumer<Integer, ? super K, ? super V> action, @Nullable TriPredicate<Integer, ? super K, ? super V> filter) {
        if (isEmpty(map) || action == null) {
            return;
        }
        int index = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (filter == null || filter.test(index, entry.getKey(), entry.getValue())) {
                action.accept(index, entry.getKey(), entry.getValue());
            }
            index++;
        }
    }

    /**
     * Performs an indexed action for each entry until the action returns false.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <K, V> void forEachIndexingBreakable(@Nullable Map<K, V> map, @Nullable TriFunction<Integer, ? super K, ? super V, Boolean> action) {
        forEachIndexingBreakable(map, action, null);
    }

    /**
     * Performs an indexed action for each entry until the action returns false.
     *
     * @param map The source map
     * @param action The action to perform
     * @param filter The predicate used to filter entries
     */
    public static <K, V> void forEachIndexingBreakable(@Nullable Map<K, V> map, @Nullable TriFunction<Integer, ? super K, ? super V, Boolean> action, @Nullable TriPredicate<Integer, ? super K, ? super V> filter) {
        if (isEmpty(map) || action == null) {
            return;
        }
        int index = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if ((filter == null || filter.test(index, entry.getKey(), entry.getValue())) && BooleanUtils.isNotTrue(action.apply(index, entry.getKey(), entry.getValue()))) {
                break;
            }
            index++;
        }
    }

    /**
     * Performs an indexed action for all entries except the last one.
     *
     * @param map The source map
     * @param action The action to perform
     *
     * @return the last entry, or null when unavailable
     */
    @Nullable
    public static <K, V> Map.Entry<K, V> forEachIndexingTailing(@Nullable Map<K, V> map, @Nullable TriConsumer<Integer, ? super K, ? super V> action) {
        if (isEmpty(map) || action == null) {
            return null;
        }
        int index = 0, size = map.size();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (index < size - 1) {
                action.accept(index, entry.getKey(), entry.getValue());
            } else {
                return entry;
            }
            index++;
        }
        return null;
    }

    /**
     * Performs an action for all entries except the last one.
     *
     * @param map The source map
     * @param action The action to perform
     *
     * @return the last entry, or null when unavailable
     */
    @Nullable
    public static <K, V> Map.Entry<K, V> forEachTailing(@Nullable Map<K, V> map, @Nullable BiConsumer<? super K, ? super V> action) {
        if (isEmpty(map) || action == null) {
            return null;
        }
        int index = 0, size = map.size();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (index < size - 1) {
                action.accept(entry.getKey(), entry.getValue());
            } else {
                return entry;
            }
            index++;
        }
        return null;
    }

    /**
     * Returns the key when the map contains exactly one entry.
     *
     * @param map The source map
     *
     * @return the singleton key, or null when the map is not a singleton
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K> K getKeyIfSingleton(@Nullable Map<K, ?> map) {
        if (isSingleton(map)) {
            for (Map.Entry<K, ?> entry : map.entrySet()) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns the keys of the map as an enumeration.
     *
     * @param map The source map
     *
     * @return an enumeration of keys, or null when the map is null
     */
    @Nullable
    public static <K> Enumeration<K> getKeysToEnumeration(@Nullable Map<K, ?> map) {
        return isEmpty(map) ? null : Collections.enumeration(map.keySet());
    }

    /**
     * Returns the values of the map as an enumeration.
     *
     * @param map The source map
     *
     * @return an enumeration of values, or null when the map is null
     */
    @Nullable
    public static <V> Enumeration<V> getValuesToEnumeration(@Nullable Map<?, V> map) {
        return isEmpty(map) ? null : Collections.enumeration(map.values());
    }

    /**
     * Returns the value when the map contains exactly one entry.
     *
     * @param map The source map
     *
     * @return the singleton value, or null when the map is not a singleton
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <V> V getValueIfSingleton(@Nullable Map<?, V> map) {
        if (isSingleton(map)) {
            for (Map.Entry<?, V> entry : map.entrySet()) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns the boolean from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Boolean getBoolean(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getBoolean(map, key, null);
    }

    /**
     * Returns the boolean from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Boolean getBoolean(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Boolean defaultValue) {
        return getObjectAs(map, key, Boolean.class, defaultValue);
    }

    /**
     * Returns the byte from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Byte getByte(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getByte(map, key, null);
    }

    /**
     * Returns the byte from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Byte getByte(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Byte defaultValue) {
        return getObjectAs(map, key, Byte.class, defaultValue);
    }

    /**
     * Returns the character from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Character getCharacter(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getCharacter(map, key, null);
    }

    /**
     * Returns the character from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Character getCharacter(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Character defaultValue) {
        return getObjectAs(map, key, Character.class, defaultValue);
    }

    /**
     * Returns the double from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Double getDouble(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getDouble(map, key, null);
    }

    /**
     * Returns the double from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Double getDouble(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Double defaultValue) {
        return getObjectAs(map, key, Double.class, defaultValue);
    }

    /**
     * Returns the float from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Float getFloat(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getFloat(map, key, null);
    }

    /**
     * Returns the float from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Float getFloat(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Float defaultValue) {
        return getObjectAs(map, key, Float.class, defaultValue);
    }

    /**
     * Returns the integer from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Integer getInteger(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getInteger(map, key, null);
    }

    /**
     * Returns the integer from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Integer getInteger(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Integer defaultValue) {
        return getObjectAs(map, key, Integer.class, defaultValue);
    }

    /**
     * Returns the long from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Long getLong(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getLong(map, key, null);
    }

    /**
     * Returns the long from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Long getLong(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Long defaultValue) {
        return getObjectAs(map, key, Long.class, defaultValue);
    }

    /**
     * Returns the short from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Short getShort(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getShort(map, key, null);
    }

    /**
     * Returns the short from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Short getShort(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Short defaultValue) {
        return getObjectAs(map, key, Short.class, defaultValue);
    }

    /**
     * Returns the big decimal from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> BigDecimal getBigDecimal(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getBigDecimal(map, key, null);
    }

    /**
     * Returns the big decimal from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> BigDecimal getBigDecimal(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable BigDecimal defaultValue) {
        return getObjectAs(map, key, BigDecimal.class, defaultValue);
    }

    /**
     * Returns the big integer from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> BigInteger getBigInteger(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getBigInteger(map, key, null);
    }

    /**
     * Returns the big integer from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> BigInteger getBigInteger(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable BigInteger defaultValue) {
        return getObjectAs(map, key, BigInteger.class, defaultValue);
    }

    /**
     * Returns the fraction from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Fraction getFraction(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getFraction(map, key, null);
    }

    /**
     * Returns the fraction from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Fraction getFraction(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Fraction defaultValue) {
        return getObjectAs(map, key, Fraction.class, defaultValue);
    }

    /**
     * Returns the string from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> String getString(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getString(map, key, null);
    }

    /**
     * Returns the string from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> String getString(@Nullable Map<? super K, ?> map, @Nullable K key, char defaultValue) {
        return getObjectAs(map, key, String.class, CharUtils.toString(defaultValue));
    }

    /**
     * Returns the string from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> String getString(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable String defaultValue) {
        return getObjectAs(map, key, String.class, defaultValue);
    }

    /**
     * Returns the object from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K, V> V getObject(@Nullable Map<? super K, V> map, @Nullable K key) {
        return isEmpty(map) ? null : map.get(key);
    }

    /**
     * Returns the object from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K, V> V getObject(@Nullable Map<? super K, V> map, @Nullable K key, @Nullable V defaultValue) {
        V result = getObject(map, key);
        return (result == null) ? defaultValue : result;
    }

    /**
     * Returns the object ignore case from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K extends CharSequence, V> V getObjectIgnoreCase(@Nullable Map<K, V> map, @Nullable K key) {
        if (isEmpty(map)) {
            return null;
        }
        V result = map.get(key);
        if (result != null) {
            return result;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (StringUtilsWraps.equalsIgnoreCase(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns the object ignore case from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K extends CharSequence, V> V getObjectIgnoreCase(@Nullable Map<K, V> map, @Nullable K key, @Nullable V defaultValue) {
        V result = getObjectIgnoreCase(map, key);
        return (result == null) ? defaultValue : result;
    }

    /**
     * Returns the object as from the source.
     *
     * @param map The source map
     * @param key The key
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    public static <K, T> T getObjectAs(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Class<T> expectType) {
        return getObjectAs(map, key, expectType, null);
    }

    /**
     * Returns the object as from the source.
     *
     * @param map The source map
     * @param key The key
     * @param expectType The expected type
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K, T> T getObjectAs(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Class<T> expectType, @Nullable T defaultValue) {
        if (isEmpty(map) || expectType == null) {
            return defaultValue;
        }
        T result = ObjectUtilsWraps.castAs(map.get(key), expectType);
        return (result == null) ? defaultValue : result;
    }

    /**
     * Returns the object as boolean from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Boolean getObjectAsBoolean(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getObjectAs(map, key, Boolean.class, null);
    }

    /**
     * Returns the object as boolean from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Boolean getObjectAsBoolean(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Boolean defaultValue) {
        return getObjectAs(map, key, Boolean.class, defaultValue);
    }

    /**
     * Returns the object as string from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> String getObjectAsString(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getObjectAs(map, key, String.class, null);
    }

    /**
     * Returns the object as string from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> String getObjectAsString(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable String defaultValue) {
        return getObjectAs(map, key, String.class, defaultValue);
    }

    /**
     * Returns the object as integer from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Integer getObjectAsInteger(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getObjectAs(map, key, Integer.class, null);
    }

    /**
     * Returns the object as integer from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Integer getObjectAsInteger(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Integer defaultValue) {
        return getObjectAs(map, key, Integer.class, defaultValue);
    }

    /**
     * Returns the object as long from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Long getObjectAsLong(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getObjectAs(map, key, Long.class, null);
    }

    /**
     * Returns the object as long from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Long getObjectAsLong(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Long defaultValue) {
        return getObjectAs(map, key, Long.class, defaultValue);
    }

    /**
     * Returns the object as double from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Double getObjectAsDouble(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getObjectAs(map, key, Double.class, null);
    }

    /**
     * Returns the object as double from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> Double getObjectAsDouble(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Double defaultValue) {
        return getObjectAs(map, key, Double.class, defaultValue);
    }

    /**
     * Returns the object array from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K> Object[] getObjectArray(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getObjectArray(map, key, null);
    }

    /**
     * Returns the object array from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K> Object[] getObjectArray(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Object[] defaultValue) {
        if (isEmpty(map)) {
            return defaultValue;
        }
        Object[] result = ObjectUtilsWraps.castAsArray(map.get(key));
        return ArrayUtils.isEmpty(result) ? defaultValue : result;
    }

    /**
     * Returns the object array as from the source.
     *
     * @param map The source map
     * @param key The key
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T, K> T[] getObjectArrayAs(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Class<T> expectType) {
        return getObjectArrayAs(map, key, expectType, null);
    }

    /**
     * Returns the object array as from the source.
     *
     * @param map The source map
     * @param key The key
     * @param expectType The expected type
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T, K> T[] getObjectArrayAs(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Class<T> expectType, @Nullable T[] defaultValue) {
        if (isEmpty(map) || expectType == null) {
            return defaultValue;
        }
        return ObjectUtilsWraps.castAsArray(map.get(key), expectType, defaultValue);
    }

    /**
     * Returns the object list from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K> List<Object> getObjectList(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getObjectList(map, key, null);
    }

    /**
     * Returns the object list from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K> List<Object> getObjectList(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable List<Object> defaultValue) {
        if (isEmpty(map)) {
            return defaultValue;
        }
        return ObjectUtilsWraps.castAsList(map.get(key), Object.class, defaultValue);
    }

    /**
     * Returns the object list as from the source.
     *
     * @param map The source map
     * @param key The key
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K, V> List<V> getObjectListAs(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Class<V> expectType) {
        return getObjectListAs(map, key, expectType, null);
    }

    /**
     * Returns the object list as from the source.
     *
     * @param map The source map
     * @param key The key
     * @param expectType The expected type
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> List<V> getObjectListAs(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Class<V> expectType, @Nullable List<V> defaultValue) {
        if (isEmpty(map) || expectType == null) {
            return defaultValue;
        }
        return ObjectUtilsWraps.castAsList(map.get(key), expectType, defaultValue);
    }

    /**
     * Returns the local date from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> LocalDate getLocalDate(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getLocalDate(map, key, null);
    }

    /**
     * Returns the local date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K> LocalDate getLocalDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable LocalDate defaultValue) {
        if (isEmpty(map)) {
            return null;
        }
        LocalDate result = null;
        Object value = map.get(key);
        if (value instanceof LocalDate alias) {
            result = alias;
        } else if (value instanceof String alias) {
            result = LocalDateWraps.parseDateGuessing(alias);
        }
        return (result == null) ? defaultValue : result;
    }

    /**
     * Returns the local date time from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> LocalDateTime getLocalDateTime(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getLocalDateTime(map, key, null);
    }

    /**
     * Returns the local date time from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K> LocalDateTime getLocalDateTime(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable LocalDateTime defaultValue) {
        if (isEmpty(map)) {
            return null;
        }
        LocalDateTime result = null;
        Object value = map.get(key);
        if (value instanceof LocalDateTime alias) {
            result = alias;
        } else if (value instanceof String alias) {
            result = LocalDateWraps.parseDateTimeGuessing(alias);
        }
        return (result == null) ? defaultValue : result;
    }

    /**
     * Returns the local time from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> LocalTime getLocalTime(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getLocalTime(map, key, null);
    }

    /**
     * Returns the local time from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <K> LocalTime getLocalTime(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable LocalTime defaultValue) {
        if (isEmpty(map)) {
            return null;
        }
        LocalTime result = null;
        Object value = map.get(key);
        if (value instanceof LocalTime alias) {
            result = alias;
        } else if (value instanceof String alias) {
            result = LocalDateWraps.parseTimeGuessing(alias);
        }
        return (result == null) ? defaultValue : result;
    }

    /**
     * Returns the sql date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     * @param formats The format patterns
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.sql.Date getSqlDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable java.sql.Date defaultValue, @Nullable Collection<String> formats) {
        java.util.Date utilDate = getJdkDate(map, key, formats);
        return (utilDate == null) ? defaultValue : SqlDateWraps.ofJdkDate(utilDate);
    }

    /**
     * Returns the jdk date from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.util.Date getJdkDate(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getJdkDate(map, key, null, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Returns the jdk date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param formats The format patterns
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.util.Date getJdkDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable String... formats) {
        return getJdkDate(map, key, ArrayUtilsWraps.asList(formats));
    }

    /**
     * Returns the jdk date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param formats The format patterns
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.util.Date getJdkDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Collection<String> formats) {
        return getJdkDate(map, key, null, formats);
    }

    /**
     * Returns the jdk date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.util.Date getJdkDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable java.util.Date defaultValue) {
        return getJdkDate(map, key, defaultValue, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Returns the jdk date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     * @param formats The format patterns
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.util.Date getJdkDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable java.util.Date defaultValue, @Nullable String... formats) {
        return getJdkDate(map, key, defaultValue, ArrayUtilsWraps.asList(formats));
    }

    /**
     * Returns the jdk date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     * @param formats The format patterns
     *
     * @return the result, or null when it is unavailable
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K> java.util.Date getJdkDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable java.util.Date defaultValue, @Nullable Collection<String> formats) {
        java.util.Date result = null;
        Object value = map.get(key);
        if (value instanceof java.util.Date alias) {
            result = alias;
        } else if (value instanceof String alias) {
            if (CollectionPlainWraps.isEmpty(formats)) {
                result = ObjectUtils.getIfNull(JdkDateWraps.parseDateTimeGuessing(alias), JdkDateWraps.parseDateGuessing(alias));
            } else {
                result = JdkDateWraps.parseDateFormats(alias, formats);
            }
        }
        return (result == null) ? defaultValue : result;
    }

    /**
     * Returns the sql date from the source.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.sql.Date getSqlDate(@Nullable Map<? super K, ?> map, @Nullable K key) {
        return getSqlDate(map, key, null, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Returns the sql date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param formats The format patterns
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.sql.Date getSqlDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable String... formats) {
        return getSqlDate(map, key, ArrayUtilsWraps.asList(formats));
    }

    /**
     * Returns the sql date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param formats The format patterns
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.sql.Date getSqlDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable Collection<String> formats) {
        return getSqlDate(map, key, null, formats);
    }

    /**
     * Returns the sql date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.sql.Date getSqlDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable java.sql.Date defaultValue) {
        return getSqlDate(map, key, defaultValue, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Returns the sql date from the source.
     *
     * @param map The source map
     * @param key The key
     * @param defaultValue The fallback value
     * @param formats The format patterns
     *
     * @return the result, or null when it is unavailable
     */
    public static <K> java.sql.Date getSqlDate(@Nullable Map<? super K, ?> map, @Nullable K key, @Nullable java.sql.Date defaultValue, @Nullable String... formats) {
        return getSqlDate(map, key, defaultValue, ArrayUtilsWraps.asList(formats));
    }

    /**
     * Returns the set of unique keys from the given maps.
     *
     * @param maps The source maps
     *
     * @return a set of unique keys, or null when there are none
     */
    @Nullable
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K> Set<K> getUniqueKeySet(@Nullable Map<? extends K, ?>... maps) {
        if (ArrayUtils.isEmpty(maps)) {
            return null;
        }
        Set<K> result = new LinkedHashSet<>();
        Arrays.stream(maps).filter(ObjectUtils::isNotEmpty).forEach(map -> CollectionPlainWraps.addAll(result, map.keySet()));
        return result.isEmpty() ? null : result;
    }

    /**
     * Runs an action when the map is empty.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <T extends Map<?, ?>> void ifEmpty(@Nullable T map, @Nullable Consumer<? super T> action) {
        ifEmptyOrElse(map, action, null);
    }

    /**
     * Runs an action when the map is empty.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <T extends Map<?, ?>> void ifEmpty(@Nullable T map, @Nullable Runnable action) {
        ifEmptyOrElse(map, action, null);
    }

    /**
     * Runs one of two actions depending on whether the map is empty.
     *
     * @param map The source map
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <T extends Map<?, ?>> void ifEmptyOrElse(@Nullable T map, @Nullable Consumer<? super T> absentAction, @Nullable Consumer<? super T> presentAction) {
        if (isEmpty(map)) {
            if (absentAction != null) {
                absentAction.accept(map);
            }
        } else {
            if (presentAction != null) {
                presentAction.accept(map);
            }
        }
    }

    /**
     * Runs one of two actions depending on whether the map is empty.
     *
     * @param map The source map
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <T extends Map<?, ?>> void ifEmptyOrElse(@Nullable T map, @Nullable Runnable absentAction, @Nullable Runnable presentAction) {
        if (isEmpty(map)) {
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
     * Runs an action when the map is not empty.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <T extends Map<?, ?>> void ifNotEmpty(@Nullable T map, @Nullable Consumer<? super T> action) {
        if (isNotEmpty(map) && action != null) {
            action.accept(map);
        }
    }

    /**
     * Runs an action when the map is not empty.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <T extends Map<?, ?>> void ifNotEmpty(@Nullable T map, @Nullable Runnable action) {
        if (isNotEmpty(map) && action != null) {
            action.run();
        }
    }

    /**
     * Runs an action when the map contains exactly one entry.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <T extends Map<?, ?>> void ifSingleton(@Nullable T map, @Nullable Consumer<? super T> action) {
        ifSingletonOrElse(map, action, null);
    }

    /**
     * Runs an action when the map contains exactly one entry.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <T extends Map<?, ?>> void ifSingleton(@Nullable T map, @Nullable Runnable action) {
        ifSingletonOrElse(map, action, null);
    }

    /**
     * Runs one of two actions depending on whether the map is a singleton.
     *
     * @param map The source map
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Map<?, ?>> void ifSingletonOrElse(@Nullable T map, @Nullable Consumer<? super T> presentAction, @Nullable Consumer<? super T> absentAction) {
        if (isSingleton(map)) {
            if (presentAction != null) {
                presentAction.accept(map);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(map);
            }
        }
    }

    /**
     * Runs one of two actions depending on whether the map is a singleton.
     *
     * @param map The source map
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Map<?, ?>> void ifSingletonOrElse(@Nullable T map, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isSingleton(map)) {
            if (presentAction != null) {
                presentAction.run();
            }
        } else {
            if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    /**
     * Runs an action when the map contains more than one entry.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <T extends Map<?, ?>> void ifMultitude(@Nullable T map, @Nullable Consumer<? super T> action) {
        if (isMultitude(map) && action != null) {
            action.accept(map);
        }
    }

    /**
     * Runs an action when the map contains more than one entry.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <T extends Map<?, ?>> void ifMultitude(@Nullable T map, @Nullable Runnable action) {
        if (isMultitude(map) && action != null) {
            action.run();
        }
    }

    /**
     * Checks whether the map is null or empty.
     *
     * @param map The source map
     *
     * @return {@code true} when the map is empty; {@code false} otherwise
     */
    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Checks whether the map contains at least one entry.
     *
     * @param map The source map
     *
     * @return {@code true} when the map is not empty; {@code false} otherwise
     */
    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Checks whether all the given maps have the same size.
     *
     * @param maps The source maps
     *
     * @return {@code true} when all sizes are equal; {@code false} otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean isSameSize(@Nullable Map<?, ?>... maps) {
        if (ArrayUtils.getLength(maps) < 2) {
            return false;
        }
        int size = size(ArrayUtils.get(maps, 0));
        return Arrays.stream(maps).skip(1L).allMatch(item -> size(item) == size);
    }

    /**
     * Checks whether the map contains exactly one entry.
     *
     * @param map The source map
     *
     * @return {@code true} when the map contains exactly one entry; {@code false} otherwise
     */
    public static boolean isSingleton(@Nullable Map<?, ?> map) {
        return size(map) == 1;
    }

    /**
     * Checks whether the map contains more than one entry.
     *
     * @param map The source map
     *
     * @return {@code true} when the map contains more than one entry; {@code false} otherwise
     */
    public static boolean isMultitude(@Nullable Map<?, ?> map) {
        return size(map) > 1;
    }

    /**
     * Checks whether the value of the given key is empty.
     *
     * @param map The source map
     * @param key The key
     *
     * @return {@code true} when the value is empty; {@code false} otherwise
     */
    public static <K> boolean isValueEmpty(@Nullable Map<K, ?> map, @Nullable K key) {
        return isEmpty(map) || ObjectUtils.isEmpty(map.get(key));
    }

    /**
     * Checks whether the value of the given key is blank.
     *
     * @param map The source map
     * @param key The key
     *
     * @return {@code true} when the value is blank; {@code false} otherwise
     */
    public static <K, V extends CharSequence> boolean isValueBlank(@Nullable Map<K, V> map, @Nullable K key) {
        return isEmpty(map) || StringUtils.isBlank(map.get(key));
    }

    /**
     * Returns the greatest size among the given maps.
     *
     * @param maps The source maps
     *
     * @return the greatest map size, or zero when no maps are provided
     */
    public static int maxSize(@Nullable Map<?, ?>... maps) {
        return maxSize(ArrayUtilsWraps.asList(maps));
    }

    /**
     * Returns the greatest size among the given maps.
     *
     * @param maps The source maps
     *
     * @return the greatest map size, or zero when no maps are provided
     */
    public static int maxSize(@Nullable Collection<Map<?, ?>> maps) {
        return CollectionPlainWraps.isEmpty(maps) ? 0 : maps.stream().mapToInt(MapPlainWraps::size).max().orElse(0);
    }

    /**
     * Creates a new hash map if null.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> HashMap<K, V> newHashMapIfNull(@Nullable Map<K, V> map) {
        return (map instanceof HashMap<K, V> alias) ? alias : (map == null ? new HashMap<>() : new HashMap<>(map));
    }

    /**
     * @see "org.springframework.util.CollectionUtils#newHashMap"
     * @see "com.google.common.collect.Maps#newHashMapWithExpectedSize"
     */
    @Nonnull
    public static <K, V> HashMap<K, V> newHashMapSizing(int size) {
        return (size < 0) ? new HashMap<>() : new HashMap<>((int) ((float) size / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * Creates a new hash map within.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> HashMap<K, V> newHashMapWithin(@Nullable Map<? extends K, ? extends V> map) {
        return (map != null ) ? new HashMap<>(map) : new HashMap<>();
    }

    /**
     * Creates a new hash map within.
     *
     * @param key The key
     * @param value The value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> HashMap<K, V> newHashMapWithin(@Nullable K key, @Nullable V value) {
        HashMap<K, V> map = new HashMap<>(1);
        map.put(key, value);
        return map;
    }

    /**
     * Creates a new hash map within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> HashMap<K, V> newHashMapWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2) {
        HashMap<K, V> map = new HashMap<>(2);
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

    /**
     * Creates a new hash map within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     * @param key3 The third key
     * @param value3 The third value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> HashMap<K, V> newHashMapWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2, @Nullable K key3, @Nullable V value3) {
        HashMap<K, V> map = new HashMap<>(3);
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        return map;
    }

    /**
     * Creates a new hash map within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> HashMap<K, V> newHashMapWithinFlat(@Nullable Map<? extends K, ? extends V>... maps) {
        return newHashMapWithinFlat(false, maps);
    }

    /**
     * Creates a new hash map within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    @SafeVarargs
    public static <K, V> HashMap<K, V> newHashMapWithinFlat(boolean emptyAsNull, @Nullable Map<? extends K, ? extends V>... maps) {
        return newHashMapWithinFlat(emptyAsNull, ArrayUtilsWraps.asList(maps));
    }

    /**
     * Creates a new hash map within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> HashMap<K, V> newHashMapWithinFlat(@Nullable Collection<Map<? extends K, ? extends V>> maps) {
        return newHashMapWithinFlat(false, maps);
    }

    /**
     * Creates a new hash map within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    public static <K, V> HashMap<K, V> newHashMapWithinFlat(boolean emptyAsNull, @Nullable Collection<Map<? extends K, ? extends V>> maps) {
        HashMap<K, V> result = new HashMap<>();
        putAll(result, maps);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Creates a new concurrent hash map if null.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapIfNull(@Nullable Map<K, V> map) {
        return (map instanceof ConcurrentHashMap<K, V> alias) ? alias : (map == null ? new ConcurrentHashMap<>() : new ConcurrentHashMap<>(map));
    }

    /**
     * Creates a new concurrent hash map sizing.
     *
     * @param size The requested size
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapSizing(int size) {
        return (size < 0) ? new ConcurrentHashMap<>() : new ConcurrentHashMap<>((int) ((float) size / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * Creates a new concurrent hash map within.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithin(@Nullable Map<? extends K, ? extends V> map) {
        return (map != null ) ? new ConcurrentHashMap<>(map) : new ConcurrentHashMap<>();
    }

    /**
     * Creates a new concurrent hash map within.
     *
     * @param key The key
     * @param value The value
     *
     * @return the new instance
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithin(@Nullable K key, @Nullable V value) {
        ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>(1);
        if (ObjectUtils.allNotNull(key, value)) {
            map.put(key, value);
        }
        return map;
    }

    /**
     * Creates a new concurrent hash map within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     *
     * @return the new instance
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2) {
        ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>(2);
        if (ObjectUtils.allNotNull(key1, value1)) {
            map.put(key1, value1);
        }
        if (ObjectUtils.allNotNull(key2, value2)) {
            map.put(key2, value2);
        }
        return map;
    }

    /**
     * Creates a new concurrent hash map within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     * @param key3 The third key
     * @param value3 The third value
     *
     * @return the new instance
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression", "DuplicatedCode"})
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2, @Nullable K key3, @Nullable V value3) {
        ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>(3);
        if (ObjectUtils.allNotNull(key1, value1)) {
            map.put(key1, value1);
        }
        if (ObjectUtils.allNotNull(key2, value2)) {
            map.put(key2, value2);
        }
        if (ObjectUtils.allNotNull(key3, value3)) {
            map.put(key3, value3);
        }
        return map;
    }

    /**
     * Creates a new concurrent hash map within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithinFlat(@Nullable Map<? extends K, ? extends V>... maps) {
        return newConcurrentHashMapWithinFlat(false, maps);
    }

    /**
     * Creates a new concurrent hash map within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    @SafeVarargs
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithinFlat(boolean emptyAsNull, @Nullable Map<? extends K, ? extends V>... maps) {
        return newConcurrentHashMapWithinFlat(emptyAsNull, ArrayUtilsWraps.asList(maps));
    }

    /**
     * Creates a new concurrent hash map within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithinFlat(@Nullable Collection<Map<? extends K, ? extends V>> maps) {
        return newConcurrentHashMapWithinFlat(false, maps);
    }

    /**
     * Creates a new concurrent hash map within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithinFlat(boolean emptyAsNull, @Nullable Collection<Map<? extends K, ? extends V>> maps) {
        ConcurrentHashMap<K, V> result = new ConcurrentHashMap<>();
        putAll(result, maps);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Creates a new linked hash map if null.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapIfNull(@Nullable Map<K, V> map) {
        return (map instanceof LinkedHashMap<K, V> alias) ? alias : (map == null ? new LinkedHashMap<>() : new LinkedHashMap<>(map));
    }

    /**
     * @see "org.springframework.util.CollectionUtils#newLinkedHashMap"
     * @see "com.google.common.collect.Maps#newLinkedHashMapWithExpectedSize"
     */
    @Nonnull
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapSizing(int size) {
        return (size < 0) ? new LinkedHashMap<>() : new LinkedHashMap<>((int) ((float) size / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * Creates a new linked hash map within.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithin(@Nullable Map<? extends K, ? extends V> map) {
        return (map != null ) ? new LinkedHashMap<>(map) : new LinkedHashMap<>();
    }

    /**
     * Creates a new linked hash map within.
     *
     * @param key The key
     * @param value The value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithin(@Nullable K key, @Nullable V value) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>(1);
        map.put(key, value);
        return map;
    }

    /**
     * Creates a new linked hash map within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>(2);
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

    /**
     * Creates a new linked hash map within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     * @param key3 The third key
     * @param value3 The third value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2, @Nullable K key3, @Nullable V value3) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>(3);
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        return map;
    }

    /**
     * Creates a new linked hash map within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithinFlat(@Nullable Map<? extends K, ? extends V>... maps) {
        return newLinkedHashMapWithinFlat(false, maps);
    }

    /**
     * Creates a new linked hash map within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    @SafeVarargs
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithinFlat(boolean emptyAsNull, @Nullable Map<? extends K, ? extends V>... maps) {
        return newLinkedHashMapWithinFlat(emptyAsNull, ArrayUtilsWraps.asList(maps));
    }

    /**
     * Creates a new linked hash map within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithinFlat(@Nullable Collection<Map<? extends K, ? extends V>> maps) {
        return newLinkedHashMapWithinFlat(false, maps);
    }

    /**
     * Creates a new linked hash map within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithinFlat(boolean emptyAsNull, @Nullable Collection<Map<? extends K, ? extends V>> maps) {
        LinkedHashMap<K, V> result = new LinkedHashMap<>();
        putAll(result, maps);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Creates a new tree map if null.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMapIfNull(@Nullable Map<K, V> map) {
        return (map instanceof TreeMap<K, V> alias) ? alias : (map == null ? new TreeMap<>() : new TreeMap<>(map));
    }

    /**
     * Creates a new tree map within.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMapWithin(@Nullable Map<? extends K, ? extends V> map) {
        return (map != null ) ? new TreeMap<>(map) : new TreeMap<>();
    }

    /**
     * @see "com.google.common.collect.Maps#newTreeMap()"
     */
    @Nonnull
    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMapWithin(@Nullable K key, @Nullable V value) {
        TreeMap<K, V> map = new TreeMap<>();
        if (key != null) {
            map.put(key, value);
        }
        return map;
    }

    /**
     * Creates a new tree map within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMapWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2) {
        TreeMap<K, V> map = new TreeMap<>();
        if (key1 != null) {
            map.put(key1, value1);
        }
        if (key2 != null) {
            map.put(key2, value2);
        }
        return map;
    }

    /**
     * Creates a new tree map within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     * @param key3 The third key
     * @param value3 The third value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMapWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2, @Nullable K key3, @Nullable V value3) {
        TreeMap<K, V> map = new TreeMap<>();
        if (key1 != null) {
            map.put(key1, value1);
        }
        if (key2 != null) {
            map.put(key2, value2);
        }
        if (key3 != null) {
            map.put(key3, value3);
        }
        return map;
    }

    /**
     * Creates a new tree map within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMapWithinFlat(@Nullable Map<? extends K, ? extends V>... maps) {
        return newTreeMapWithinFlat(false, maps);
    }

    /**
     * Creates a new tree map within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    @SafeVarargs
    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMapWithinFlat(boolean emptyAsNull, @Nullable Map<? extends K, ? extends V>... maps) {
        return newTreeMapWithinFlat(emptyAsNull, ArrayUtilsWraps.asList(maps));
    }

    /**
     * Creates a new tree map within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMapWithinFlat(@Nullable Collection<Map<? extends K, ? extends V>> maps) {
        return newTreeMapWithinFlat(false, maps);
    }

    /**
     * Creates a new tree map within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    public static <K extends Comparable<?>, V> TreeMap<K, V> newTreeMapWithinFlat(boolean emptyAsNull, @Nullable Collection<Map<? extends K, ? extends V>> maps) {
        TreeMap<K, V> result = new TreeMap<>();
        putAll(result, maps);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Creates a new hashtable if null.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> Hashtable<K, V> newHashtableIfNull(@Nullable Map<K, V> map) {
        return (map instanceof Hashtable<K, V> alias) ? alias : (map == null ? new Hashtable<>() : new Hashtable<>(map));
    }

    /**
     * Creates a new hashtable sizing.
     *
     * @param size The requested size
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> Hashtable<K, V> newHashtableSizing(int size) {
        return (size < 0) ? new Hashtable<>() : new Hashtable<>((int) ((float) size / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * Creates a new hash table within.
     *
     * @param map The source map
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> Hashtable<K, V> newHashTableWithin(@Nullable Map<? extends K, ? extends V> map) {
        return (map != null ) ? new Hashtable<>(map) : new Hashtable<>();
    }

    /**
     * Creates a new hash table within.
     *
     * @param key The key
     * @param value The value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> Hashtable<K, V> newHashTableWithin(@Nullable K key, @Nullable V value) {
        Hashtable<K, V> table = new Hashtable<>(1);
        if (ObjectUtils.allNotNull(key, value)) {
            table.put(key, value);
        }
        return table;
    }

    /**
     * Creates a new hash table within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> Hashtable<K, V> newHashTableWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2) {
        Hashtable<K, V> table = new Hashtable<>(2);
        if (ObjectUtils.allNotNull(key1, value1)) {
            table.put(key1, value1);
        }
        if (ObjectUtils.allNotNull(key2, value2)) {
            table.put(key2, value2);
        }
        return table;
    }

    /**
     * Creates a new hash table within.
     *
     * @param key1 The first key
     * @param value1 The first value
     * @param key2 The second key
     * @param value2 The second value
     * @param key3 The third key
     * @param value3 The third value
     *
     * @return the new instance
     */
    @Nonnull
    public static <K, V> Hashtable<K, V> newHashTableWithin(@Nullable K key1, @Nullable V value1, @Nullable K key2, @Nullable V value2, @Nullable K key3, @Nullable V value3) {
        Hashtable<K, V> table = new Hashtable<>(3);
        if (ObjectUtils.allNotNull(key1, value1)) {
            table.put(key1, value1);
        }
        if (ObjectUtils.allNotNull(key2, value2)) {
            table.put(key2, value2);
        }
        if (ObjectUtils.allNotNull(key3, value3)) {
            table.put(key3, value3);
        }
        return table;
    }

    /**
     * Creates a new hashtable within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> Hashtable<K, V> newHashtableWithinFlat(@Nullable Map<? extends K, ? extends V>... maps) {
        return newHashtableWithinFlat(false, maps);
    }

    /**
     * Creates a new hashtable within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    @SafeVarargs
    public static <K, V> Hashtable<K, V> newHashtableWithinFlat(boolean emptyAsNull, @Nullable Map<? extends K, ? extends V>... maps) {
        return newHashtableWithinFlat(emptyAsNull, ArrayUtilsWraps.asList(maps));
    }

    /**
     * Creates a new hashtable within flat.
     *
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> Hashtable<K, V> newHashtableWithinFlat(@Nullable Collection<Map<? extends K, ? extends V>> maps) {
        return newHashtableWithinFlat(false, maps);
    }

    /**
     * Creates a new hashtable within flat.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param maps The source maps
     *
     * @return the new instance
     */
    @Nullable
    public static <K, V> Hashtable<K, V> newHashtableWithinFlat(boolean emptyAsNull, @Nullable Collection<Map<? extends K, ? extends V>> maps) {
        Hashtable<K, V> result = new Hashtable<>();
        putAll(result, maps);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Returns null when the map is empty.
     *
     * @param map The source map
     *
     * @return the map, or null when it is empty
     */
    @Nullable
    public static <T extends Map<?, ?>> T nullIfEmpty(@Nullable T map) {
        return isEmpty(map) ? null : map;
    }

    /**
     * Puts all into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K, V> void putAll(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            target.putAll(source);
        }
    }

    /**
     * Puts all into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param sources The sources
     */
    @SafeVarargs
    public static <K, V> void putAll(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V>... sources) {
        putAll(target, ArrayUtilsWraps.asList(sources));
    }

    /**
     * Puts all into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param sources The sources
     */
    public static <K, V> void putAll(@Nullable Map<K, V> target, @Nullable Collection<Map<? extends K, ? extends V>> sources) {
        if (target != null && CollectionPlainWraps.isNotEmpty(sources)) {
            sources.stream().filter(MapPlainWraps::isNotEmpty).forEach(Failable.asConsumer(target::putAll));
        }
    }

    /**
     * Puts all if all not blank into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K extends CharSequence, V extends CharSequence> void putAllIfAllNotBlank(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            source.forEach(Failable.asBiConsumer((key, value) -> putIfAllNotBlank(target, key, value)));
        }
    }

    /**
     * Puts all if all not empty into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K, V> void putAllIfAllNotEmpty(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            source.forEach(Failable.asBiConsumer((key, value) -> putIfAllNotEmpty(target, key, value)));
        }
    }

    /**
     * Puts all if all not null into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K, V> void putAllIfAllNotNull(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            source.forEach(Failable.asBiConsumer((key, value) -> putIfAllNotNull(target, key, value)));
        }
    }

    /**
     * Puts all if key not null into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K, V> void putAllIfKeyNotNull(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            source.forEach(Failable.asBiConsumer((key, value) -> putIfKeyNotNull(target, key, value)));
        }
    }

    /**
     * Puts all if key not blank into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K extends CharSequence, V> void putAllIfKeyNotBlank(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            source.forEach(Failable.asBiConsumer((key, value) -> putIfKeyNotBlank(target, key, value)));
        }
    }

    /**
     * Puts all if key not empty into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K, V> void putAllIfKeyNotEmpty(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            source.forEach(Failable.asBiConsumer((key, value) -> putIfKeyNotEmpty(target, key, value)));
        }
    }

    /**
     * Puts all if value not null into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K, V> void putAllIfValueNotNull(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            source.forEach(Failable.asBiConsumer((key, value) -> putIfValueNotNull(target, key, value)));
        }
    }

    /**
     * Puts all if value not blank into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K, V extends CharSequence> void putAllIfValueNotBlank(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            source.forEach(Failable.asBiConsumer((key, value) -> putIfValueNotBlank(target, key, value)));
        }
    }

    /**
     * Puts all if value not empty into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param source The source
     */
    public static <K, V> void putAllIfValueNotEmpty(@Nullable Map<K, V> target, @Nullable Map<? extends K, ? extends V> source) {
        if (target != null && isNotEmpty(source)) {
            source.forEach(Failable.asBiConsumer((key, value) -> putIfValueNotEmpty(target, key, value)));
        }
    }

    /**
     * Puts if all not blank into the map when the condition is satisfied.
     *
     * @param map The source map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    public static <K extends CharSequence, V extends CharSequence> V putIfAllNotBlank(@Nullable Map<K, V> map, @Nullable K key, @Nullable V value) {
        return (map == null || StringUtils.isAnyBlank(key, value)) ? null : map.put(key, value);
    }

    /**
     * Puts if all not empty into the map when the condition is satisfied.
     *
     * @param map The source map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    public static <K, V> V putIfAllNotEmpty(@Nullable Map<K, V> map, @Nullable K key, @Nullable V value) {
        return (map == null || ObjectUtilsWraps.anyEmpty(key, value)) ? null : map.put(key, value);
    }

    /**
     * Puts if all not null into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> V putIfAllNotNull(@Nullable Map<K, V> target, @Nullable K key, @Nullable V value) {
        return ObjectUtils.anyNull(target, key, value) ? null : target.put(key, value);
    }

    /**
     * Puts if map not null into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    public static <K, V> V putIfMapNotNull(@Nullable Map<K, V> target, @Nullable K key, @Nullable V value) {
        return (target == null) ? null : target.put(key, value);
    }

    /**
     * Puts if key not null into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> V putIfKeyNotNull(@Nullable Map<K, V> target, @Nullable K key, @Nullable V value) {
        return ObjectUtils.anyNull(target, key) ? null : target.put(key, value);
    }

    /**
     * Puts if key not blank into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    public static <K extends CharSequence, V> V putIfKeyNotBlank(@Nullable Map<K, V> target, @Nullable K key, @Nullable V value) {
        return (target == null || StringUtils.isBlank(key)) ? null : target.put(key, value);
    }

    /**
     * Puts if key not empty into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    public static <K, V> V putIfKeyNotEmpty(@Nullable Map<K, V> target, @Nullable K key, @Nullable V value) {
        return (target == null || ObjectUtils.isEmpty(key)) ? null : target.put(key, value);
    }

    /**
     * Puts if value not null into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> V putIfValueNotNull(@Nullable Map<K, V> target, @Nullable K key, @Nullable V value) {
        return ObjectUtils.anyNull(target, value) ? null : target.put(key, value);
    }

    /**
     * Puts if value not blank into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    public static <K, V extends CharSequence> V putIfValueNotBlank(@Nullable Map<K, V> target, @Nullable K key, @Nullable V value) {
        return (target == null || StringUtils.isBlank(value)) ? null : target.put(key, value);
    }

    /**
     * Puts if value not empty into the map when the condition is satisfied.
     *
     * @param target The target map
     * @param key The key
     * @param value The value
     *
     * @return the previous value associated with the key, or null when no entry was added
     */
    @Nullable
    public static <K, V> V putIfValueNotEmpty(@Nullable Map<K, V> target, @Nullable K key, @Nullable V value) {
        return (target == null || ObjectUtils.isEmpty(value)) ? null : target.put(key, value);
    }

    /**
     * Recomputes the value for the given key.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <K, V> void recompute(@Nullable Map<K, V> map, @Nullable BiFunction<? super K, ? super V, V> action) {
        recompute(map, action, null);
    }

    /**
     * Recomputes the value for the given key.
     *
     * @param map The source map
     * @param action The action to perform
     * @param filter The predicate used to filter entries
     */
    public static <K, V> void recompute(@Nullable Map<K, V> map, @Nullable BiFunction<? super K, ? super V, V> action, @Nullable BiPredicate<? super K, ? super V> filter) {
        if (isEmpty(map) || action == null) {
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (filter == null || filter.test(entry.getKey(), entry.getValue())) {
                map.put(entry.getKey(), action.apply(entry.getKey(), entry.getValue()));
            }
        }
    }

    /**
     * Removes the entry with the given key.
     *
     * @param map The source map
     * @param key The key
     *
     * @return the removed value, or null when there was no entry
     */
    @Nullable
    public static <K, V> V removeByKey(@Nullable Map<K, V> map, @Nullable K key) {
        return isEmpty(map) ? null : map.remove(key);
    }

    /**
     * Removes the entries with the given keys.
     *
     * @param map The source map
     * @param keys The keys
     */
    @SafeVarargs
    public static <K> void removeByKeys(@Nullable Map<? super K, ?> map, @Nullable K... keys) {
        removeByKeys(map, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Removes the entries with the given keys.
     *
     * @param map The source map
     * @param keys The keys
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K> void removeByKeys(@Nullable Map<? super K, ?> map, @Nullable Collection<K> keys) {
        if (ObjectUtilsWraps.allNotEmpty(map, keys)) {
            keys.forEach(map::remove);
        }
    }

    /**
     * Removes entries with the given value.
     *
     * @param map The source map
     * @param value The value
     *
     * @return a map of removed entries, or null when there were none
     */
    @Nullable
    public static <K, V> K removeByValue(@Nullable Map<K, V> map, @Nullable V value) {
        if (isEmpty(map)) {
            return null;
        }
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();
            if (entry != null && Objects.equals(entry.getValue(), value)) {
                K result = entry.getKey();
                iterator.remove();
                return result;
            }
        }
        return null;
    }

    /**
     * Removes entries with the given values.
     *
     * @param map The source map
     * @param values The values
     */
    @SafeVarargs
    public static <V> void removeByValues(@Nullable Map<?, ? super V> map, @Nullable V... values) {
        removeByValues(map, ArrayUtilsWraps.asList(values));
    }

    /**
     * Removes entries with the given values.
     *
     * @param map The source map
     * @param values The values
     */
    public static <V> void removeByValues(@Nullable Map<?, ? super V> map, @Nullable Collection<V> values) {
        if (ObjectUtilsWraps.allNotEmpty(map, values)) {
            removeIfValue(map, value -> CollectionPlainWraps.contains(values, value));
        }
    }

    /**
     * Removes the class key from the map.
     *
     * @param map The source map
     *
     * @return the removed value, or null when there was no entry
     */
    @Nullable
    public static Object removeClassKey(@Nullable Map<String, ?> map) {
        return isEmpty(map) ? null : map.remove(JavaKeywordConst.CLASS);
    }

    /**
     * Removes entries whose value is empty.
     *
     * @param map The source map
     */
    public static <K, V> void removeEmptyValue(@Nullable Map<K, V> map) {
        removeIfValue(map, ObjectUtils::isEmpty);
    }

    /**
     * @see java.util.Collection#removeIf
     */
    public static <K, V> boolean removeIf(@Nullable Map<K, V> map, @Nullable BiPredicate<? super K, ? super V> filter) {
        if (isEmpty(map) || filter == null) {
            return false;
        }
        boolean removed = false;
        Iterator<? extends Map.Entry<K, V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();
            if (entry != null && filter.test(entry.getKey(), entry.getValue())) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Removes entries whose key matches the given predicate.
     *
     * @param map The source map
     * @param filter The predicate used to filter entries
     *
     * @return {@code true} when at least one entry was removed
     */
    public static <K> boolean removeIfKey(@Nullable Map<K, ?> map, @Nullable Predicate<? super K> filter) {
        if (isEmpty(map) || filter == null) {
            return false;
        }
        boolean removed = false;
        Iterator<? extends Map.Entry<K, ?>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, ?> entry = iterator.next();
            if (entry != null && filter.test(entry.getKey())) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Removes entries whose value matches the given predicate.
     *
     * @param map The source map
     * @param filter The predicate used to filter entries
     *
     * @return {@code true} when at least one entry was removed
     */
    public static <V> boolean removeIfValue(@Nullable Map<?, V> map, @Nullable Predicate<? super V> filter) {
        if (isEmpty(map) || filter == null) {
            return false;
        }
        boolean removed = false;
        Iterator<? extends Map.Entry<?, V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, V> entry = iterator.next();
            if (entry != null && filter.test(entry.getValue())) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Renames a key in the map.
     *
     * @param map The source map
     * @param oldKey The old key
     * @param newKey The new key
     *
     * @return the value associated with the old key, or null when the map is null
     */
    @Nullable
    public static <K, V> V renameKey(@Nullable Map<K, V> map, @Nullable K oldKey, @Nullable K newKey) {
        if (map == null) {
            return null;
        }
        V result = map.remove(oldKey);
        map.put(newKey, result);
        return result;
    }

    /**
     * Performs an action for each entry in reverse order.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <K, V> void reverseForEach(@Nullable Map<K, V> map, @Nullable BiConsumer<? super K, ? super V> action) {
        reverseForEach(map, action, null);
    }

    /**
     * Performs an action for each entry in reverse order.
     *
     * @param map The source map
     * @param action The action to perform
     * @param filter The predicate used to filter entries
     */
    public static <K, V> void reverseForEach(@Nullable Map<K, V> map, @Nullable BiConsumer<? super K, ? super V> action, @Nullable BiPredicate<? super K, ? super V> filter) {
        if (isEmpty(map) || action == null) {
            return;
        }
        ListIterator<Map.Entry<K, V>> iterator = ListPlainWraps.listIteratorTailing(new ArrayList<>(map.entrySet()));
        Validate.notNull(iterator, AssertMessageConst.NOT_NULL);
        while (iterator.hasPrevious()) {
            Map.Entry<K, V> entry = iterator.previous();
            if (filter == null || filter.test(entry.getKey(), entry.getValue())) {
                action.accept(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Performs an action for each entry in reverse order until the action returns false.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <K, V> void reverseForEachBreakable(@Nullable Map<K, V> map, @Nullable BiFunction<? super K, ? super V, Boolean> action) {
        reverseForEachBreakable(map, action, null);
    }

    /**
     * Performs an action for each entry in reverse order until the action returns false.
     *
     * @param map The source map
     * @param action The action to perform
     * @param filter The predicate used to filter entries
     */
    public static <K, V> void reverseForEachBreakable(@Nullable Map<K, V> map, @Nullable BiFunction<? super K, ? super V, Boolean> action, @Nullable BiPredicate<? super K, ? super V> filter) {
        if (isEmpty(map) || action == null) {
            return;
        }
        ListIterator<Map.Entry<K, V>> iterator = ListPlainWraps.listIteratorTailing(new ArrayList<>(map.entrySet()));
        Validate.notNull(iterator, AssertMessageConst.NOT_NULL);
        while (iterator.hasPrevious()) {
            Map.Entry<K, V> entry = iterator.previous();
            if ((filter == null || filter.test(entry.getKey(), entry.getValue())) && BooleanUtils.isNotTrue(action.apply(entry.getKey(), entry.getValue()))) {
                break;
            }
        }
    }

    /**
     * Performs an action for all entries except the first one in reverse order.
     *
     * @param map The source map
     * @param action The action to perform
     *
     * @return the first entry, or null when unavailable
     */
    @Nullable
    public static <K, V> Map.Entry<K, V> reverseForEachHeading(@Nullable Map<K, V> map, @Nullable BiConsumer<? super K, ? super V> action) {
        if (isEmpty(map) || action == null) {
            return null;
        }
        ListIterator<Map.Entry<K, V>> iterator = ListPlainWraps.listIteratorTailing(new ArrayList<>(map.entrySet()));
        Validate.notNull(iterator, AssertMessageConst.NOT_NULL);
        int index = map.size() - 1;
        while (iterator.hasPrevious()) {
            Map.Entry<K, V> entry = iterator.previous();
            if (index > 0) {
                action.accept(entry.getKey(), entry.getValue());
            } else {
                return entry;
            }
            index--;
        }
        return null;
    }

    /**
     * Performs an indexed action for each entry in reverse order.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <K, V> void reverseForEachIndexing(@Nullable Map<K, V> map, @Nullable TriConsumer<Integer, ? super K, ? super V> action) {
        reverseForEachIndexing(map, action, null);
    }

    /**
     * Performs an indexed action for each entry in reverse order.
     *
     * @param map The source map
     * @param action The action to perform
     * @param filter The predicate used to filter entries
     */
    public static <K, V> void reverseForEachIndexing(@Nullable Map<K, V> map, @Nullable TriConsumer<Integer, ? super K, ? super V> action, @Nullable TriPredicate<Integer, ? super K, ? super V> filter) {
        if (isEmpty(map) || action == null) {
            return;
        }
        ListIterator<Map.Entry<K, V>> iterator = ListPlainWraps.listIteratorTailing(new ArrayList<>(map.entrySet()));
        Validate.notNull(iterator, AssertMessageConst.NOT_NULL);
        int index = map.size() - 1;
        while (iterator.hasPrevious()) {
            Map.Entry<K, V> entry = iterator.previous();
            if (filter == null || filter.test(index, entry.getKey(), entry.getValue())) {
                action.accept(index, entry.getKey(), entry.getValue());
            }
            index--;
        }
    }

    /**
     * Performs an indexed action for each entry in reverse order until the action returns false.
     *
     * @param map The source map
     * @param action The action to perform
     */
    public static <K, V> void reverseForEachIndexingBreakable(@Nullable Map<K, V> map, @Nullable TriFunction<Integer, ? super K, ? super V, Boolean> action) {
        reverseForEachIndexingBreakable(map, action, null);
    }

    /**
     * Performs an indexed action for each entry in reverse order until the action returns false.
     *
     * @param map The source map
     * @param action The action to perform
     * @param filter The predicate used to filter entries
     */
    public static <K, V> void reverseForEachIndexingBreakable(@Nullable Map<K, V> map, @Nullable TriFunction<Integer, ? super K, ? super V, Boolean> action, @Nullable TriPredicate<Integer, ? super K, ? super V> filter) {
        if (isEmpty(map) || action == null) {
            return;
        }
        ListIterator<Map.Entry<K, V>> iterator = ListPlainWraps.listIteratorTailing(new ArrayList<>(map.entrySet()));
        Validate.notNull(iterator, AssertMessageConst.NOT_NULL);
        int index = map.size() - 1;
        while (iterator.hasPrevious()) {
            Map.Entry<K, V> entry = iterator.previous();
            if ((filter == null || filter.test(index, entry.getKey(), entry.getValue())) && BooleanUtils.isNotTrue(action.apply(index, entry.getKey(), entry.getValue()))) {
                break;
            }
            index--;
        }
    }

    /**
     * Performs an indexed action for all entries except the first one in reverse order.
     *
     * @param map The source map
     * @param action The action to perform
     *
     * @return the first entry, or null when unavailable
     */
    @Nullable
    public static <K, V> Map.Entry<K, V> reverseForEachIndexingHeading(@Nullable Map<K, V> map, @Nullable TriConsumer<Integer, ? super K, ? super V> action) {
        if (isEmpty(map) || action == null) {
            return null;
        }
        ListIterator<Map.Entry<K, V>> iterator = ListPlainWraps.listIteratorTailing(new ArrayList<>(map.entrySet()));
        Validate.notNull(iterator, AssertMessageConst.NOT_NULL);
        int index = map.size() - 1;
        while (iterator.hasPrevious()) {
            Map.Entry<K, V> entry = iterator.previous();
            if (index > 0) {
                action.accept(index, entry.getKey(), entry.getValue());
            } else {
                return entry;
            }
            index--;
        }
        return null;
    }

    /**
     * Returns the size of the given map.
     *
     * @param map The source map
     *
     * @return the map size, or zero when the map is null
     */
    public static int size(@Nullable Map<?, ?> map) {
        return (map == null) ? 0 : map.size();
    }

    /**
     * Returns the sorted map which converts the plain list to tree list, joining with the children prop
     *
     * @param maps The source maps to inspect
     * @param idKey The id key to identify each record, such as "id"
     * @param pidKey The parent key to identify the parent record of current, such as "pid"
     * @param childrenKey The children key to organize children records, such as "children"
     *
     * @return the sorted map which converts the plain list to tree list, joining with the children prop
     */
    @Nullable
    public static List<Map<String, Object>> sortChildrenTree(@Nullable Collection<Map<String, Object>> maps, @Nullable String idKey, @Nullable String pidKey, @Nullable String childrenKey) {
        return sortChildrenTree(maps, idKey, pidKey, childrenKey, (Comparator<Map<String, Object>>) null);
    }

    /**
     * Returns the sorted map which converts the plain list to tree list, joining with the children prop
     *
     * @param maps The source maps to inspect
     * @param idKey The id key to identify each record, such as "id"
     * @param pidKey The parent key to identify the parent record of current, such as "pid"
     * @param childrenKey The children key to organize children records, such as "children"
     * @param orderKey The order key to sort records, such as "order", note the value must implement {@link java.lang.Comparable}
     *
     * @return the sorted map which converts the plain list to tree list, joining with the children prop
     */
    @Nullable
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<Map<String, Object>> sortChildrenTree(@Nullable Collection<Map<String, Object>> maps, @Nullable String idKey, @Nullable String pidKey, @Nullable String childrenKey, @Nullable String orderKey) {
        return sortChildrenTree(maps, idKey, pidKey, childrenKey, (m1, m2) -> {
            Comparable t1 = getObjectAs(m1, orderKey, Comparable.class);
            Comparable t2 = getObjectAs(m2, orderKey, Comparable.class);
            return ObjectUtils.compare(t1, t2, true);
        });
    }

    /**
     * Returns the sorted list of maps which converts the plain list to tree list, joining with the children prop
     *
     * @param maps The source maps to inspect
     * @param idKey The id key to identify each source map, such as "id"
     * @param pidKey The parent key to identify the parent source map, such as "pid"
     * @param childrenKey The children key to organize children source maps, such as "children"
     * @param comparator comparator to sort maps
     *
     * @return the sorted list of maps which converts the plain list to tree list, joining with the children prop
     */
    @Nullable
    public static List<Map<String, Object>> sortChildrenTree(@Nullable Collection<Map<String, Object>> maps, @Nullable String idKey, @Nullable String pidKey, @Nullable String childrenKey, @Nullable Comparator<Map<String, Object>> comparator) {
        if (CollectionPlainWraps.isEmpty(maps) || StringUtils.isAnyBlank(idKey, pidKey, childrenKey)) {
            return null;
        }
        Map<Object, Map<String, Object>> indexes = new HashMap<>(maps.size());
        // Initialize all nodes with an empty children list
        for (Map<String, Object> map : maps) {
            if (map == null) {
                continue;
            }
            Object idValue = map.get(idKey);
            if (idValue != null) {
                map.put(childrenKey, new ArrayList<Map<String, Object>>());
                indexes.put(idValue, map);
            }
        }
        if (isEmpty(indexes)) {
            return null;
        }
        // Build the tree
        List<Map<String, Object>> roots = new ArrayList<>(maps.size());
        for (Map<String, Object> map : maps) {
            Object pidValue = map.get(pidKey);
            if (ObjectUtils.isEmpty(pidValue) || !indexes.containsKey(pidValue)) {
                roots.add(map);
            } else {
                Map<String, Object> parent = indexes.get(pidValue);
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> children = (List<Map<String, Object>>) parent.get(childrenKey);
                children.add(map);
                if (comparator != null) {
                    children.sort(comparator);
                }
            }
        }
        // Sort root nodes if comparator is provided
        if (comparator != null) {
            roots.sort(comparator);
        }
        return roots.isEmpty() ? null : roots;
    }

    /**
     * Returns the total size of the given maps.
     *
     * @param maps The source maps
     *
     * @return the combined size of all maps
     */
    public static int sumSize(@Nullable Map<?, ?>... maps) {
        return sumSize(ArrayUtilsWraps.asList(maps));
    }

    /**
     * Returns the total size of the given maps.
     *
     * @param maps The source maps
     *
     * @return the combined size of all maps
     */
    public static int sumSize(@Nullable Collection<Map<?, ?>> maps) {
        return CollectionPlainWraps.isEmpty(maps) ? 0 : maps.stream().mapToInt(MapPlainWraps::size).sum();
    }
}
