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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * Utilities for {@link java.util.Collection}
 *
 * @author David Hsing
 *
 * @see java.util.Collections
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class CollectionPlainWraps {
    /**
     * Default load factor for {@link java.util.HashMap}/{@link java.util.LinkedHashMap} variants
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;

    /**
     * Adds all elements from the source array to the target collection.
     *
     * @param target The target collection to add elements to
     * @param sources The source elements to add
     *
     * @return {@code true} if the collection changed as a result of the call
     */
    @SafeVarargs
    public static <E> boolean addAll(@Nullable Collection<? super E> target, @Nullable E... sources) {
        return addAllIf(target, null, sources);
    }

    /**
     * Adds all elements from multiple source arrays to the target collection.
     *
     * @param target The target collection to add elements to
     * @param sources The source arrays containing elements to add
     *
     * @return {@code true} if the collection changed as a result of the call
     */
    @SafeVarargs
    public static <E> boolean addAll(@Nullable Collection<E> target, @Nullable E[]... sources) {
        return addAllIf(target, null, sources);
    }

    /**
     * Adds all elements from the source collection to the target collection.
     *
     * @param target The target collection to add elements to
     * @param source The source collection containing elements to add
     *
     * @return {@code true} if the collection changed as a result of the call
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAll(@Nullable Collection<E> target, @Nullable Collection<? extends E> source) {
        return ObjectUtils.allNotNull(target, source) && target.addAll(source);
    }

    /**
     * Adds all elements from the source iterable to the target collection.
     *
     * @param target The target collection to add elements to
     * @param source The source iterable containing elements to add
     *
     * @return {@code true} if the collection changed as a result of the call
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAll(@Nullable Collection<E> target, @Nullable Iterable<? extends E> source) {
        if (ObjectUtils.anyNull(target, source)) {
            return false;
        }
        boolean changed = false;
        for (E item : source) {
            changed |= target.add(item);
        }
        return changed;
    }

    /**
     * Adds all elements from multiple source iterables to the target collection.
     *
     * @param target The target collection to add elements to
     * @param sources The source iterables containing elements to add
     *
     * @return {@code true} if the collection changed as a result of the call
     */
    @SafeVarargs
    public static <E> boolean addAll(@Nullable Collection<E> target, @Nullable Iterable<? extends E>... sources) {
        return addAllIf(target, null, sources);
    }

    /**
     * Adds all elements from the source iterator to the target collection.
     *
     * @param target The target collection to add elements to
     * @param source The source iterator containing elements to add
     *
     * @return {@code true} if the collection changed as a result of the call
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAll(@Nullable Collection<E> target, @Nullable Iterator<? extends E> source) {
        if (ObjectUtils.anyNull(target, source)) {
            return false;
        }
        boolean changed = false;
        while (source.hasNext()) {
            changed |= target.add(source.next());
        }
        return changed;
    }

    /**
     * Adds all elements from multiple source iterators to the target collection.
     *
     * @param target The target collection to add elements to
     * @param sources The source iterators containing elements to add
     *
     * @return {@code true} if the collection changed as a result of the call
     */
    @SafeVarargs
    public static <E> boolean addAll(@Nullable Collection<E> target, @Nullable Iterator<? extends E>... sources) {
        return addAllIf(target, null, sources);
    }

    /**
     * Adds all elements from the source enumeration to the target collection.
     *
     * @param target The target collection to add elements to
     * @param source The source enumeration containing elements to add
     *
     * @return {@code true} if the collection changed as a result of the call
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAll(@Nullable Collection<E> target, @Nullable Enumeration<? extends E> source) {
        if (ObjectUtils.anyNull(target, source)) {
            return false;
        }
        boolean changed = false;
        while (source.hasMoreElements()) {
            changed |= target.add(source.nextElement());
        }
        return changed;
    }

    /**
     * Adds all elements from multiple source enumerations to the target collection.
     *
     * @param target The target collection to add elements to
     * @param sources The source enumerations containing elements to add
     *
     * @return {@code true} if the collection changed as a result of the call
     */
    @SafeVarargs
    public static <E> boolean addAll(@Nullable Collection<E> target, @Nullable Enumeration<? extends E>... sources) {
        return addAllIf(target, null, sources);
    }

    /**
     * Adds all items to the target collection if matched the filter
     *
     * @param target The collection to add to
     * @param filter The filter to apply to the target collection and each item
     * @param sources The items to add
     *
     * @return {@code true} if the collection was changed, {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAllIf(@Nullable Collection<? super E> target, @Nullable BiPredicate<Collection<? super E>, E> filter, @Nullable E... sources) {
        if (target == null || ArrayUtils.isEmpty(sources)) {
            return false;
        }
        boolean changed = false;
        for (E source : sources) {
            changed |= addIf(target, source, filter);
        }
        return changed;
    }

    /**
     * Adds all items to the target collection if matched the filter
     *
     * @param target The target collection to merge the array into
     * @param filter The filter to apply to the target collection and each item
     * @param sources The item arrays to add
     *
     * @return {@code true} if the collection was changed, {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAllIf(@Nullable Collection<? super E> target, @Nullable BiPredicate<Collection<? super E>, E> filter, @Nullable E[]... sources) {
        if (target == null || ArrayUtils.isEmpty(sources)) {
            return false;
        }
        boolean changed = false;
        for (E[] source : sources) {
            changed |= addAllIf(target, filter, source);
        }
        return changed;
    }

    /**
     * Adds all items to the target collection if matched the filter
     *
     * @param target The collection to add to
     * @param filter The filter to apply to the target collection and each item
     * @param source The item iterable to add
     *
     * @return {@code true} if the collection was changed, {@code false} otherwise
     */
    @SuppressWarnings({"unchecked", "DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAllIf(@Nullable Collection<E> target, @Nullable BiPredicate<Collection<? super E>, E> filter, @Nullable Iterable<? extends E> source) {
        if (ObjectUtils.anyNull(target, source)) {
            return false;
        }
        boolean changed = false;
        for (E item : source) {
            changed |= addIf(target, item, filter);
        }
        return changed;
    }

    /**
     * Adds all items to the target collection if matched the filter
     *
     * @param target The collection to add to
     * @param filter The filter to apply to the target collection and each item
     * @param sources The item iterables to add
     *
     * @return {@code true} if the collection was changed, {@code false} otherwise
     */
    @SuppressWarnings({"unchecked", "DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAllIf(@Nullable Collection<E> target, @Nullable BiPredicate<Collection<? super E>, E> filter, @Nullable Iterable<? extends E>... sources) {
        if (target == null || ArrayUtils.isEmpty(sources)) {
            return false;
        }
        boolean changed = false;
        for (Iterable<? extends E> source : sources) {
            if (source == null) {
                continue;
            }
            for (E item : source) {
                changed |= addIf(target, item, filter);
            }
        }
        return changed;
    }

    /**
     * Adds all items to the target collection if matched the filter
     *
     * @param target The collection to add to
     * @param filter The filter to apply to the target collection and each item
     * @param source The item iterator to add
     *
     * @return {@code true} if the collections was changed, {@code false} otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAllIf(@Nullable Collection<E> target, @Nullable BiPredicate<Collection<? super E>, E> filter, @Nullable Iterator<? extends E> source) {
        if (ObjectUtils.anyNull(target, source)) {
            return false;
        }
        boolean changed = false;
        while (source.hasNext()) {
            changed |= addIf(target, source.next(), filter);
        }
        return changed;
    }

    /**
     * Adds all items to the target collection if matched the filter
     *
     * @param target The collection to add to
     * @param filter The filter to apply to the target collection and each item
     * @param sources The item iterators to add
     *
     * @return {@code true} if the collections was changed, {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean addAllIf(@Nullable Collection<E> target, @Nullable BiPredicate<Collection<? super E>, E> filter, @Nullable Iterator<? extends E>... sources) {
        if (target == null || ArrayUtils.isEmpty(sources)) {
            return false;
        }
        boolean changed = false;
        for (Iterator<? extends E> source : sources) {
            if (source == null) {
                continue;
            }
            while (source.hasNext()) {
                changed |= addIf(target, source.next(), filter);
            }
        }
        return changed;
    }

    /**
     * Adds all items to the target collection if matched the filter
     *
     * @param target The collection to add to
     * @param filter The filter to apply to the target collection and each item
     * @param source The item enumeration to add
     *
     * @return {@code true} if the collections was changed, {@code false} otherwise
     */
    public static <E> boolean addAllIf(@Nullable Collection<E> target, @Nullable BiPredicate<Collection<? super E>, E> filter, Enumeration<? extends E> source) {
        if (ObjectUtils.anyNull(target, source)) {
            return false;
        }
        boolean changed = false;
        while (source.hasMoreElements()) {
            changed |= addIf(target, source.nextElement(), filter);
        }
        return changed;
    }

    /**
     * Adds all items to the target collection if matched the filter
     *
     * @param target The collection to add to
     * @param filter The filter to apply to the target collection and each item
     * @param sources The item enumerations to add
     *
     * @return {@code true} if the collections was changed, {@code false} otherwise
     */
    @SafeVarargs
    public static <E> boolean addAllIf(@Nullable Collection<E> target, @Nullable BiPredicate<Collection<? super E>, E> filter, Enumeration<? extends E>... sources) {
        if (target == null || ArrayUtils.isEmpty(sources)) {
            return false;
        }
        boolean changed = false;
        for (Enumeration<? extends E> source : sources) {
            if (source == null) {
                continue;
            }
            while (source.hasMoreElements()) {
                changed |= addIf(target, source.nextElement(), filter);
            }
        }
        return changed;
    }

    /**
     * Adds non-null source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotNull(@Nullable Collection<? super E> target, @Nullable E... sources) {
        return addAllIf(target, (collection, item) -> item != null, sources);
    }

    /**
     * Adds non-null source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotNull(@Nullable Collection<E> target, @Nullable E[]... sources) {
        return addAllIf(target, (collection, item) -> item != null, sources);
    }

    /**
     * Adds non-null source elements to the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addAllIfNotNull(@Nullable Collection<E> target, @Nullable Iterable<? extends E> source) {
        return addAllIf(target, (collection, item) -> item != null, source);
    }

    /**
     * Adds non-null source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotNull(@Nullable Collection<E> target, @Nullable Iterable<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> item != null, sources);
    }

    /**
     * Adds non-null source elements to the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addAllIfNotNull(@Nullable Collection<E> target, @Nullable Iterator<? extends E> source) {
        return addAllIf(target, (collection, item) -> item != null, source);
    }

    /**
     * Adds non-null source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotNull(@Nullable Collection<E> target, @Nullable Iterator<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> item != null, sources);
    }

    /**
     * Adds non-null source elements to the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addAllIfNotNull(@Nullable Collection<E> target, @Nullable Enumeration<? extends E> source) {
        return addAllIf(target, (collection, item) -> item != null, source);
    }

    /**
     * Adds non-null source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotNull(@Nullable Collection<E> target, @Nullable Enumeration<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> item != null, sources);
    }

    /**
     * Adds source elements that are not already present in the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotContains(@Nullable Collection<? super E> target, @Nullable E... sources) {
        return addAllIf(target, (collection, item) -> !contains(collection, item), sources);
    }

    /**
     * Adds source elements that are not already present in the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotContains(@Nullable Collection<E> target, @Nullable E[]... sources) {
        return addAllIf(target, (collection, item) -> !contains(collection, item), sources);
    }

    /**
     * Adds source elements that are not already present in the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addAllIfNotContains(@Nullable Collection<E> target, @Nullable Iterable<? extends E> source) {
        return addAllIf(target, (collection, item) -> !contains(collection, item), source);
    }

    /**
     * Adds source elements that are not already present in the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotContains(@Nullable Collection<E> target, @Nullable Iterable<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> !contains(collection, item), sources);
    }

    /**
     * Adds source elements that are not already present in the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addAllIfNotContains(@Nullable Collection<E> target, @Nullable Iterator<? extends E> source) {
        return addAllIf(target, (collection, item) -> !contains(collection, item), source);
    }

    /**
     * Adds source elements that are not already present in the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotContains(@Nullable Collection<E> target, @Nullable Iterator<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> !contains(collection, item), sources);
    }

    /**
     * Adds source elements that are not already present in the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotContains(@Nullable Collection<E> target, @Nullable Enumeration<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> !contains(collection, item), sources);
    }

    /**
     * Adds non-blank source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E extends CharSequence> boolean addAllIfNotBlank(@Nullable Collection<? super E> target, @Nullable E... sources) {
        return addAllIf(target, (collection, item) -> StringUtils.isNotBlank(item), sources);
    }

    /**
     * Adds non-blank source elements to the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E extends CharSequence> boolean addAllIfNotBlank(@Nullable Collection<E> target, @Nullable Iterable<? extends E> source) {
        return addAllIf(target, (collection, item) -> StringUtils.isNotBlank(item), source);
    }

    /**
     * Adds non-blank source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E extends CharSequence> boolean addAllIfNotBlank(@Nullable Collection<E> target, @Nullable Iterable<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> StringUtils.isNotBlank(item), sources);
    }

    /**
     * Adds non-blank source elements to the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E extends CharSequence> boolean addAllIfNotBlank(@Nullable Collection<E> target, @Nullable Iterator<? extends E> source) {
        return addAllIf(target, (collection, item) -> StringUtils.isNotBlank(item), source);
    }

    /**
     * Adds non-blank source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E extends CharSequence> boolean addAllIfNotBlank(@Nullable Collection<E> target, @Nullable Iterator<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> StringUtils.isNotBlank(item), sources);
    }

    /**
     * Adds non-blank source elements to the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E extends CharSequence> boolean addAllIfNotBlank(@Nullable Collection<E> target, @Nullable Enumeration<? extends E> source) {
        return addAllIf(target, (collection, item) -> StringUtils.isNotBlank(item), source);
    }

    /**
     * Adds non-blank source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E extends CharSequence> boolean addAllIfNotBlank(@Nullable Collection<E> target, @Nullable Enumeration<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> StringUtils.isNotBlank(item), sources);
    }

    /**
     * Adds non-empty source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotEmpty(@Nullable Collection<? super E> target, @Nullable E... sources) {
        return addAllIf(target, (collection, item) -> ObjectUtils.isNotEmpty(item), sources);
    }

    /**
     * Adds non-empty source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotEmpty(@Nullable Collection<E> target, @Nullable E[]... sources) {
        return addAllIf(target, (collection, item) -> ObjectUtils.isNotEmpty(item), sources);
    }

    /**
     * Adds non-empty source elements to the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addAllIfNotEmpty(@Nullable Collection<E> target, @Nullable Iterable<? extends E> source) {
        return addAllIf(target, (collection, item) -> ObjectUtils.isNotEmpty(item), source);
    }

    /**
     * Adds non-empty source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotEmpty(@Nullable Collection<E> target, @Nullable Iterable<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> ObjectUtils.isNotEmpty(item), sources);
    }

    /**
     * Adds non-empty source elements to the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addAllIfNotEmpty(@Nullable Collection<E> target, @Nullable Iterator<? extends E> source) {
        return addAllIf(target, (collection, item) -> ObjectUtils.isNotEmpty(item), source);
    }

    /**
     * Adds non-empty source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotEmpty(@Nullable Collection<E> target, @Nullable Iterator<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> ObjectUtils.isNotEmpty(item), sources);
    }

    /**
     * Adds non-empty source elements to the target collection.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addAllIfNotEmpty(@Nullable Collection<E> target, @Nullable Enumeration<? extends E> source) {
        return addAllIf(target, (collection, item) -> ObjectUtils.isNotEmpty(item), source);
    }

    /**
     * Adds non-empty source elements to the target collection.
     *
     * @param target The target collection
     * @param sources The source elements
     *
     * @return true when the target collection changed
     */
    @SafeVarargs
    public static <E> boolean addAllIfNotEmpty(@Nullable Collection<E> target, @Nullable Enumeration<? extends E>... sources) {
        return addAllIf(target, (collection, item) -> ObjectUtils.isNotEmpty(item), sources);
    }

    /**
     * Adds an item to the target collection when the predicate matches.
     *
     * @param target The target collection
     * @param source The source collection
     * @param filter The predicate used to select elements
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addIf(@Nullable Collection<? super E> target, @Nullable E source, @Nullable BiPredicate<Collection<? super E>, E> filter) {
        return target != null && (filter == null || filter.test(target, source)) && target.add(source);
    }

    /**
     * Adds an item to the target collection when it is not null.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addIfNotNull(@Nullable Collection<? super E> target, @Nullable E source) {
        return addIf(target, source, (collection, item) -> item != null);
    }

    /**
     * Adds an item to the target collection when it is not already present.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addIfNotContains(@Nullable Collection<? super E> target, @Nullable E source) {
        return addIf(target, source, (collection, item) -> !contains(target, item));
    }

    /**
     * Adds an item to the target collection when it is not blank.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E extends CharSequence> boolean addIfNotBlank(@Nullable Collection<? super E> target, @Nullable E source) {
        return addIf(target, source, (collection, item) -> StringUtils.isNotBlank(item));
    }

    /**
     * Adds an item to the target collection when it is not empty.
     *
     * @param target The target collection
     * @param source The source collection
     *
     * @return true when the target collection changed
     */
    public static <E> boolean addIfNotEmpty(@Nullable Collection<? super E> target, @Nullable E source) {
        return addIf(target, source, (collection, item) -> ObjectUtils.isNotEmpty(item));
    }

    /**
     * Checks whether all the given values are null.
     *
     * @param collection The source collection
     *
     * @return true when all values are null; false otherwise
     */
    public static boolean allNull(@Nullable Collection<?> collection) {
        return collection == null || collection.stream().allMatch(Objects::isNull);
    }

    /**
     * Checks whether all the given values are null.
     *
     * @param collections The source collections
     *
     * @return true when all values are null; false otherwise
     */
    public static boolean allNull(@Nullable Collection<?>... collections) {
        return collections == null || Arrays.stream(collections).allMatch(CollectionPlainWraps::allNull);
    }

    /**
     * Checks whether all the given values are not null.
     *
     * @param collection The source collection
     *
     * @return true when all values are non-null; false otherwise
     */
    public static boolean allNotNull(@Nullable Collection<?> collection) {
        return collection != null && collection.stream().allMatch(Objects::nonNull);
    }

    /**
     * Checks whether all the given values are not null.
     *
     * @param collections The source collections
     *
     * @return true when all values are non-null; false otherwise
     */
    public static boolean allNotNull(@Nullable Collection<?>... collections) {
        return collections != null && Arrays.stream(collections).allMatch(CollectionPlainWraps::allNotNull);
    }

    /**
     * Checks whether all the given collections are empty.
     *
     * A null collection is treated as empty.
     *
     * @param collection The source collection
     *
     * @return true when all collections are empty; false otherwise
     */
    public static boolean allEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.stream().allMatch(ObjectUtils::isEmpty);
    }

    /**
     * Checks whether all the given collections are empty.
     *
     * A null collection is treated as empty.
     *
     * @param collections The source collections
     *
     * @return true when all collections are empty; false otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean allEmpty(@Nullable Collection<?>... collections) {
        return ArrayUtils.isEmpty(collections) || Arrays.stream(collections).allMatch(CollectionPlainWraps::allEmpty);
    }

    /**
     * Checks whether all the given collections are not empty.
     *
     * @param collection The source collection
     *
     * @return true when all collections contain elements; false otherwise
     */
    public static boolean allNotEmpty(@Nullable Collection<?> collection) {
        return collection != null && collection.stream().allMatch(ObjectUtils::isNotEmpty);
    }

    /**
     * Checks whether all the given collections are not empty.
     *
     * @param collections The source collections
     *
     * @return true when all collections contain elements; false otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean allNotEmpty(@Nullable Collection<?>... collections) {
        return ArrayUtils.isNotEmpty(collections) && Arrays.stream(collections).allMatch(CollectionPlainWraps::allNotEmpty);
    }

    /**
     * Checks whether any of the given values is null.
     *
     * @param collection The source collection
     *
     * @return true when any value is null; false otherwise
     */
    public static boolean anyNull(@Nullable Collection<?> collection) {
        return collection == null || collection.stream().anyMatch(Objects::isNull);
    }

    /**
     * Checks whether any of the given values is null.
     *
     * @param collections The source collections
     *
     * @return true when any value is null; false otherwise
     */
    public static boolean anyNull(@Nullable Collection<?>... collections) {
        return collections == null || Arrays.stream(collections).anyMatch(CollectionPlainWraps::anyNull);
    }

    /**
     * Checks whether any of the given values is not null.
     *
     * @param collection The source collection
     *
     * @return true when any value is non-null; false otherwise
     */
    public static boolean anyNotNull(@Nullable Collection<?> collection) {
        return collection != null && collection.stream().anyMatch(Objects::nonNull);
    }

    /**
     * Checks whether any of the given values is not null.
     *
     * @param collections The source collections
     *
     * @return true when any value is non-null; false otherwise
     */
    public static boolean anyNotNull(@Nullable Collection<?>... collections) {
        return collections != null && Arrays.stream(collections).anyMatch(CollectionPlainWraps::anyNotNull);
    }

    /**
     * Checks whether any of the given collections is empty.
     *
     * A null collection is treated as empty.
     *
     * @param collection The source collection
     *
     * @return true when any collection is empty; false otherwise
     */
    public static boolean anyEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.stream().anyMatch(ObjectUtils::isEmpty);
    }

    /**
     * Checks whether any of the given collections is empty.
     *
     * A null collection is treated as empty.
     *
     * @param collections The source collections
     *
     * @return true when any collection is empty; false otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean anyEmpty(@Nullable Collection<?>... collections) {
        return ArrayUtils.isEmpty(collections) || Arrays.stream(collections).anyMatch(CollectionPlainWraps::isEmpty);
    }

    /**
     * Checks whether any of the given collections is not empty.
     *
     * @param collection The source collection
     *
     * @return true when any collection contains elements; false otherwise
     */
    public static boolean anyNotEmpty(@Nullable Collection<?> collection) {
        return collection != null && collection.stream().anyMatch(ObjectUtils::isNotEmpty);
    }

    /**
     * Checks whether any of the given collections is not empty.
     *
     * @param collections The source collections
     *
     * @return true when any collection contains elements; false otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean anyNotEmpty(@Nullable Collection<?>... collections) {
        return ArrayUtils.isNotEmpty(collections) && Arrays.stream(collections).anyMatch(CollectionPlainWraps::anyNotEmpty);
    }

    /**
     * Casts the given collection to a list of the expected type.
     *
     * @param sources The source elements
     * @param expectType The expected component type
     *
     * @return a typed list, or null when the cast cannot be performed
     */
    @Nullable
    public static <E> List<E> castToList(@Nullable Collection<?> sources, @Nullable Class<E> expectType) {
        if (CollectionPlainWraps.isEmpty(sources) || expectType == null || expectType == Void.class) {
            return null;
        }
        return sources.stream().map(item -> ObjectUtilsWraps.castAs(item, expectType)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Casts the given collection to a set of the expected type.
     *
     * @param sources The source elements
     * @param expectType The expected component type
     *
     * @return a typed set, or null when the cast cannot be performed
     */
    @Nullable
    public static <E> Set<E> castToSet(@Nullable Collection<?> sources, @Nullable Class<E> expectType) {
        if (CollectionPlainWraps.isEmpty(sources) || expectType == null || expectType == Void.class) {
            return null;
        }
        return sources.stream().map(item -> ObjectUtilsWraps.castAs(item, expectType)).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * Returns {@code true} if the item is in collection
     *
     * @param collection The collection to check
     * @param item The item to look for
     *
     * @return {@code true} if the item is in collection
     */
    public static <E> boolean contains(@Nullable Collection<?> collection, @Nullable E item) {
        return collection != null && collection.contains(item);
    }

    /**
     * Checks whether the collection contains all the given items.
     *
     * @param target The target collection
     * @param comparisons The items to look for
     *
     * @return true when all items are present; false otherwise
     */
    @SafeVarargs
    public static <E> boolean containsAll(@Nullable Collection<? super E> target, @Nullable E... comparisons) {
        return containsAll(target, ArrayUtilsWraps.asList(comparisons));
    }

    /**
     * Returns {@code true} if all items of {@code comparison} is in the {@code target} collection
     *
     * @param target The target collection to check
     * @param comparison The items to look for
     *
     * @return {@code true} if all items of {@code comparison} is in the {@code target} collection
     */
    public static <E> boolean containsAll(@Nullable Collection<?> target, @Nullable Collection<?> comparison) {
        if (isEmpty(target) || size(target) < size(comparison)) {
            return false;
        }
        if (isEmpty(comparison)) {
            return true;
        }
        for (Object item : comparison) {
            if (!target.contains(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the collection contains any of the given items.
     *
     * @param target The target collection
     * @param comparisons The items to look for
     *
     * @return true when any item is present; false otherwise
     */
    @SafeVarargs
    public static <E> boolean containsAny(@Nullable Collection<? super E> target, @Nullable E... comparisons) {
        return containsAny(target, ArrayUtilsWraps.asList(comparisons));
    }

    /**
     * Returns {@code true} if any items of {@code comparison} is in the {@code target} collection
     *
     * @param target The target collection to check
     * @param comparison The items to look for
     *
     * @return {@code true} if any items of {@code comparison} is in the {@code target} collection
     */
    public static <E> boolean containsAny(@Nullable Collection<?> target, @Nullable Collection<?> comparison) {
        if (isEmpty(target) || isEmpty(comparison)) {
            return false;
        }
        if (target.size() < comparison.size()) {
            for (Object item : target) {
                if (comparison.contains(item)) {
                    return true;
                }
            }
        } else {
            for (Object item : comparison) {
                if (target.contains(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the collection contains the sequence as an exact match.
     *
     * @param collection The source collection
     * @param sequence The character sequence to search for
     *
     * @return true when the sequence is present; false otherwise
     */
    public static boolean containsString(@Nullable Collection<? extends CharSequence> collection, @Nullable CharSequence sequence) {
        return isNotEmpty(collection) && collection.stream().anyMatch(item -> StringUtilsWraps.equals(item, sequence));
    }

    /**
     * Checks whether the collection contains the sequence, ignoring case.
     *
     * @param collection The source collection
     * @param sequence The character sequence to search for
     *
     * @return true when the sequence is present; false otherwise
     */
    public static boolean containsStringIgnoreCase(@Nullable Collection<? extends CharSequence> collection, @Nullable CharSequence sequence) {
        return isNotEmpty(collection) && collection.stream().anyMatch(item -> StringUtilsWraps.equalsIgnoreCase(item, sequence));
    }

    /**
     * Returns {@code true} if <b>all</b> of the {@code sequences} are found as
     * exact matches in the {@code collection}.
     *
     * @param collection The collection to search within
     * @param sequences The string sequences to look for
     *
     * @return {@code true} if every sequence is found as an exact match in the collection
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean containsAllString(@Nullable Collection<? extends CharSequence> collection, @Nullable Collection<? extends CharSequence> sequences) {
        return allNotEmpty(collection, sequences) && sequences.stream().allMatch(item -> containsString(collection, item));
    }

    /**
     * Returns {@code true} if <b>all</b> of the {@code sequences} are found as
     * exact matches in the {@code collection}, ignoring case.
     *
     * @param collection The collection to search within
     * @param sequences The string sequences to look for
     *
     * @return {@code true} if every sequence is found as an exact match in the collection ignoring case
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean containsAllStringIgnoreCase(@Nullable Collection<? extends CharSequence> collection, @Nullable Collection<? extends CharSequence> sequences) {
        return allNotEmpty(collection, sequences) && sequences.stream().allMatch(item -> containsStringIgnoreCase(collection, item));
    }

    /**
     * Returns {@code true} if any element in {@code collection} matches any of
     * the {@code sequences} as an exact string.
     *
     * @param collection The collection to search within
     * @param sequences The string sequences to look for
     *
     * @return {@code true} if any element matches any of the sequences
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean containsAnyString(@Nullable Collection<? extends CharSequence> collection, @Nullable Collection<? extends CharSequence> sequences) {
        return allNotEmpty(collection, sequences) && sequences.stream().anyMatch(item -> containsString(collection, item));
    }

    /**
     * Returns {@code true} if any element in {@code collection} matches any of
     * the {@code sequences} as an exact string, ignoring case.
     *
     * @param collection The collection to search within
     * @param sequences The string sequences to look for
     *
     * @return {@code true} if any element matches any of the sequences ignoring case
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean containsAnyStringIgnoreCase(@Nullable Collection<? extends CharSequence> collection, @Nullable Collection<? extends CharSequence> sequences) {
        return allNotEmpty(collection, sequences) && sequences.stream().anyMatch(item -> containsStringIgnoreCase(collection, item));
    }

    /**
     * Checks whether a collection element contains the given sequence as a substring.
     *
     * @param collection The source collection
     * @param sequence The character sequence to search for
     *
     * @return true when the substring is present; false otherwise
     */
    public static boolean containsSubstring(@Nullable Collection<? extends CharSequence> collection, @Nullable CharSequence sequence) {
        return isNotEmpty(collection) && collection.stream().anyMatch(item -> StringUtilsWraps.contains(item, sequence));
    }

    /**
     * Checks whether a collection element contains the given substring, ignoring case.
     *
     * @param collection The source collection
     * @param sequence The character sequence to search for
     *
     * @return true when the substring is present; false otherwise
     */
    public static boolean containsSubstringIgnoreCase(@Nullable Collection<? extends CharSequence> collection, @Nullable CharSequence sequence) {
        return isNotEmpty(collection) && collection.stream().anyMatch(item -> StringUtilsWraps.containsIgnoreCase(item, sequence));
    }

    /**
     * Returns {@code true} if <b>all</b> of the {@code sequences} are found as
     * substrings in at least one element of the {@code collection}.
     *
     * @param collection The collection to search within
     * @param sequences The substring sequences to look for
     *
     * @return {@code true} if every sequence is found as a substring somewhere
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean containsAllSubstring(@Nullable Collection<? extends CharSequence> collection, @Nullable Collection<? extends CharSequence> sequences) {
        return allNotEmpty(collection, sequences) && sequences.stream().allMatch(item -> containsSubstring(collection, item));
    }

    /**
     * Returns {@code true} if <b>all</b> of the {@code sequences} are found as
     * substrings in at least one element of the {@code collection}, ignoring case.
     *
     * @param collection The collection to search within
     * @param sequences The substring sequences to look for
     *
     * @return {@code true} if every sequence is found as a substring somewhere ignoring case
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean containsAllSubstringIgnoreCase(@Nullable Collection<? extends CharSequence> collection, @Nullable Collection<? extends CharSequence> sequences) {
        return allNotEmpty(collection, sequences) && sequences.stream().allMatch(item -> containsSubstringIgnoreCase(collection, item));
    }

    /**
     * Returns {@code true} if any element in {@code collection} contains any of
     * the {@code sequences} as a substring.
     *
     * @param collection The collection to search within
     * @param sequences The substring sequences to look for
     *
     * @return {@code true} if any element contains any of the sequences
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean containsAnySubstring(@Nullable Collection<? extends CharSequence> collection, @Nullable Collection<? extends CharSequence> sequences) {
        return allNotEmpty(collection, sequences) && sequences.stream().anyMatch(item -> containsSubstring(collection, item));
    }

    /**
     * Returns {@code true} if any element in {@code collection} contains any of
     * the {@code sequences} as a substring, ignoring case.
     *
     * @param collection The collection to search within
     * @param sequences The substring sequences to look for
     *
     * @return {@code true} if any element contains any of the sequences ignoring case
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean containsAnySubstringIgnoreCase(@Nullable Collection<? extends CharSequence> collection, @Nullable Collection<? extends CharSequence> sequences) {
        return allNotEmpty(collection, sequences) && sequences.stream().anyMatch(item -> containsSubstringIgnoreCase(collection, item));
    }

    /**
     * Performs an action for each element of the collection.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <E> void forEach(@Nullable Collection<E> collection, @Nullable Consumer<? super E> action) {
        forEach(collection, action, null);
    }

    /**
     * Performs an action for each element of the collection.
     *
     * @param collection The source collection
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    public static <E> void forEach(@Nullable Collection<E> collection, @Nullable Consumer<? super E> action, @Nullable Predicate<? super E> filter) {
        if (isEmpty(collection) || action == null) {
            return;
        }
        for (E item : collection) {
            if (filter == null || filter.test(item)) {
                action.accept(item);
            }
        }
    }

    /**
     * Performs an action for each element until the action returns false.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <E> void forEachBreakable(@Nullable Collection<E> collection, @Nullable Function<? super E, Boolean> action) {
        forEachBreakable(collection, action, null);
    }

    /**
     * Performs an action for each element until the action returns false.
     *
     * @param collection The source collection
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    public static <E> void forEachBreakable(@Nullable Collection<E> collection, @Nullable Function<? super E, Boolean> action, @Nullable Predicate<? super E> filter) {
        if (isEmpty(collection) || action == null) {
            return;
        }
        for (E item : collection) {
            if ((filter == null || filter.test(item)) && BooleanUtils.isNotTrue(action.apply(item))) {
                break;
            }
        }
    }

    /**
     * Performs an action for each element together with its index.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <E> void forEachIndexing(@Nullable Collection<E> collection, @Nullable BiConsumer<Integer, ? super E> action) {
        forEachIndexing(collection, action, null);
    }

    /**
     * Performs an action for each element together with its index.
     *
     * @param collection The source collection
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    public static <E> void forEachIndexing(@Nullable Collection<E> collection, @Nullable BiConsumer<Integer, ? super E> action, @Nullable BiPredicate<Integer, ? super E> filter) {
        if (isEmpty(collection) || action == null) {
            return;
        }
        int index = 0;
        for (E item : collection) {
            if (filter == null || filter.test(index, item)) {
                action.accept(index, item);
            }
            index++;
        }
    }

    /**
     * Performs an indexed action for each element until the action returns false.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <E> void forEachIndexingBreakable(@Nullable Collection<E> collection, @Nullable BiFunction<Integer, ? super E, Boolean> action) {
        forEachIndexingBreakable(collection, action, null);
    }

    /**
     * Performs an indexed action for each element until the action returns false.
     *
     * @param collection The source collection
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    public static <E> void forEachIndexingBreakable(@Nullable Collection<E> collection, @Nullable BiFunction<Integer, ? super E, Boolean> action, @Nullable BiPredicate<Integer, ? super E> filter) {
        if (isEmpty(collection) || action == null) {
            return;
        }
        int index = 0;
        for (E item : collection) {
            if ((filter == null || filter.test(index, item)) && BooleanUtils.isNotTrue(action.apply(index, item))) {
                break;
            }
            index++;
        }
    }

    /**
     * Performs an indexed action for all elements except the last one.
     *
     * The last element is returned after the action has been applied.
     *
     * @param collection The source collection
     * @param action The action to perform
     *
     * @return the last element, or null when unavailable
     */
    @Nullable
    public static <E> E forEachIndexingTailing(@Nullable Collection<E> collection, @Nullable BiConsumer<Integer, ? super E> action) {
        if (isEmpty(collection) || action == null) {
            return null;
        }
        int index = 0, size = collection.size();
        for (E item : collection) {
            if (index < size - 1) {
                action.accept(index, item);
            } else {
                return item;
            }
            index++;
        }
        return null;
    }

    /**
     * Performs an action for all elements except the last one.
     *
     * The last element is returned after the action has been applied.
     *
     * @param collection The source collection
     * @param action The action to perform
     *
     * @return the last element, or null when unavailable
     */
    @Nullable
    public static <E> E forEachTailing(@Nullable Collection<E> collection, @Nullable Consumer<? super E> action) {
        if (isEmpty(collection) || action == null) {
            return null;
        }
        int index = 0, size = collection.size();
        for (E item : collection) {
            if (index < size - 1) {
                action.accept(item);
            } else {
                return item;
            }
            index++;
        }
        return null;
    }

    /**
     * Returns the component type inferred from the collection elements.
     *
     * @param collection The source collection
     *
     * @return the component type, or null when it cannot be determined
     */
    @Nullable
    public static Class<?> getComponentType(@Nullable Collection<?> collection) {
        return getComponentType(collection, false);
    }

    /**
     * Returns the component type inferred from the collection elements.
     *
     * @param collection The source collection
     * @param deepScan Whether to scan superclasses
     *
     * @return the component type, or null when it cannot be determined
     */
    @Nullable
    public static Class<?> getComponentType(@Nullable Collection<?> collection, boolean deepScan) {
        return (collection == null) ? null : IteratorPlainWraps.getComponentType(collection.iterator(), deepScan);
    }

    /**
     * Runs an action when the collection is empty.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <T extends Collection<?>> void ifEmpty(@Nullable T collection, @Nullable Consumer<? super T> action) {
        ifEmptyOrElse(collection, action, null);
    }

    /**
     * Runs an action when the collection is empty.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <T extends Collection<?>> void ifEmpty(@Nullable T collection, @Nullable Runnable action) {
        ifEmptyOrElse(collection, action, null);
    }

    /**
     * Runs one of two actions depending on whether the collection is empty.
     *
     * @param collection The source collection
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <T extends Collection<?>> void ifEmptyOrElse(@Nullable T collection, @Nullable Consumer<? super T> absentAction, @Nullable Consumer<? super T> presentAction) {
        if (isEmpty(collection)) {
            if (absentAction != null) {
                absentAction.accept(collection);
            }
        } else {
            if (presentAction != null) {
                presentAction.accept(collection);
            }
        }
    }

    /**
     * Runs one of two actions depending on whether the collection is empty.
     *
     * @param collection The source collection
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <T extends Collection<?>> void ifEmptyOrElse(@Nullable T collection, @Nullable Runnable absentAction, @Nullable Runnable presentAction) {
        if (isEmpty(collection)) {
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
     * Runs an action when the collection is not empty.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <T extends Collection<?>> void ifNotEmpty(@Nullable T collection, @Nullable Consumer<? super T> action) {
        if (isNotEmpty(collection) && action != null) {
            action.accept(collection);
        }
    }

    /**
     * Runs an action when the collection is not empty.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <T extends Collection<?>> void ifNotEmpty(@Nullable T collection, @Nullable Runnable action) {
        if (isNotEmpty(collection) && action != null) {
            action.run();
        }
    }

    /**
     * Runs an action when the collection contains exactly one element.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <T extends Collection<?>> void ifSingleton(@Nullable T collection, @Nullable Consumer<? super T> action) {
        ifSingletonOrElse(collection, action, null);
    }

    /**
     * Runs an action when the collection contains exactly one element.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <T extends Collection<?>> void ifSingleton(@Nullable T collection, @Nullable Runnable action) {
        ifSingletonOrElse(collection, action, null);
    }

    /**
     * Runs one of two actions depending on whether the collection is a singleton.
     *
     * @param collection The source collection
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Collection<?>> void ifSingletonOrElse(@Nullable T collection, @Nullable Consumer<? super T> presentAction, @Nullable Consumer<? super T> absentAction) {
        if (isSingleton(collection)) {
            if (presentAction != null) {
                presentAction.accept(collection);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(collection);
            }
        }
    }

    /**
     * Runs one of two actions depending on whether the collection is a singleton.
     *
     * @param collection The source collection
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Collection<?>> void ifSingletonOrElse(@Nullable T collection, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isSingleton(collection)) {
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
     * Runs an action when the collection contains more than one element.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <T extends Collection<?>> void ifMultitude(@Nullable T collection, @Nullable Consumer<? super T> action) {
        if (isMultitude(collection) && action != null) {
            action.accept(collection);
        }
    }

    /**
     * Runs an action when the collection contains more than one element.
     *
     * @param collection The source collection
     * @param action The action to perform
     */
    public static <T extends Collection<?>> void ifMultitude(@Nullable T collection, @Nullable Runnable action) {
        if (isMultitude(collection) && action != null) {
            action.run();
        }
    }

    /**
     * Checks whether all elements of the collection are distinct.
     *
     * Null elements can be ignored.
     *
     * @param collection The source collection
     *
     * @return true when all elements are distinct; false otherwise
     */
    public static boolean isDistinct(@Nullable Collection<?> collection) {
        return isDistinct(collection, true);
    }

    /**
     * Checks whether all elements of the collection are distinct.
     *
     * Null elements can be ignored.
     *
     * @param collection The source collection
     * @param ignoreNull Whether null elements are ignored
     *
     * @return true when all elements are distinct; false otherwise
     */
    public static boolean isDistinct(@Nullable Collection<?> collection, boolean ignoreNull) {
        if (isEmpty(collection)) {
            return false;
        }
        if (ignoreNull) {
            List<?> list = collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
            Set<?> set = new LinkedHashSet<>(list);
            return isSameSize(list, set);
        } else {
            Set<?> set = new LinkedHashSet<>(collection);
            return isSameSize(collection, set);
        }
    }

    /**
     * Checks whether the collection is null or empty.
     *
     * @param collection The source collection
     *
     * @return true when the collection is empty; false otherwise
     */
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks whether the collection contains at least one element.
     *
     * @param collection The source collection
     *
     * @return true when the collection is not empty; false otherwise
     */
    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Checks whether the index is within the bounds of the collection.
     *
     * @param collection The source collection
     * @param index The index to check
     *
     * @return true when the index is valid; false otherwise
     */
    public static boolean isIndexBound(@Nullable Collection<?> collection, int index) {
        return isIndexBound(collection, index, false);
    }

    /**
     * Returns true if the index object exists in the collection
     *
     * @param collection The source to check
     * @param index The index to detect
     * @param adding indicates whether trying to add an item to the source collection or not
     *
     * @return true if the index object exists in the collection
     *
     * @see ArrayUtils#isArrayIndexValid
     */
    public static boolean isIndexBound(@Nullable Collection<?> collection, int index, boolean adding) {
        return index >= 0 && index < (size(collection) + (adding ? 1 : 0));
    }

    /**
     * Checks whether all the given collections have the same size.
     *
     * At least two collections are required.
     *
     * @param collections The source collections
     *
     * @return true when all collections have the same size; false otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean isSameSize(@Nullable Collection<?>... collections) {
        if (ArrayUtils.getLength(collections) < 2) {
            return false;
        }
        int size = size(ArrayUtils.get(collections, 0));
        return Arrays.stream(collections).skip(1L).allMatch(item -> size(item) == size);
    }

    /**
     * Checks whether the collection contains exactly one element.
     *
     * @param collection The source collection
     *
     * @return true when the collection contains exactly one element; false otherwise
     */
    public static boolean isSingleton(@Nullable Collection<?> collection) {
        return size(collection) == 1;
    }

    /**
     * Checks whether the collection contains more than one element.
     *
     * @param collection The source collection
     *
     * @return true when the collection contains more than one element; false otherwise
     */
    public static boolean isMultitude(@Nullable Collection<?> collection) {
        return size(collection) > 1;
    }

    /**
     * Returns an ArrayList containing the given collection elements.
     *
     * The original list is reused when it is already an ArrayList.
     *
     * @param collection The source collection
     *
     * @return an ArrayList containing the elements
     */
    @Nonnull
    public static <E> ArrayList<E> newArrayListIfNull(@Nullable Collection<E> collection) {
        return (collection instanceof ArrayList<E> alias) ? alias : (collection == null ? new ArrayList<>() : new ArrayList<>(collection));
    }

    /**
     * Creates an ArrayList with enough capacity for the given size.
     *
     * @param size The requested collection size
     *
     * @return a new ArrayList
     */
    @Nonnull
    public static <E> ArrayList<E> newArrayListSizing(int size) {
        return (size < 0) ? new ArrayList<>() : new ArrayList<>((int) ((float) size / DEFAULT_LOAD_FACTOR));
    }

    /**
     * Creates an ArrayList containing the given items.
     *
     * @param collection The source collection
     *
     * @return a new ArrayList, or null when configured to return null for empty input
     */
    @Nonnull
    public static <E> ArrayList<E> newArrayListWithin(@Nullable Collection<E> collection) {
        return (collection == null) ? new ArrayList<>() : new ArrayList<>(collection);
    }

    /**
     * Creates an ArrayList containing the given items.
     *
     * @param items The items to add or check
     *
     * @return a new ArrayList, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> ArrayList<E> newArrayListWithin(@Nullable E... items) {
        return newArrayListWithin(false, items);
    }

    /**
     * Creates an ArrayList containing the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new ArrayList, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> ArrayList<E> newArrayListWithin(boolean emptyAsNull, @Nullable E... items) {
        return newArrayListWithinAll(emptyAsNull, ArrayUtilsWraps.asList(items));
    }

    /**
     * Creates an ArrayList containing all elements of the given iterables.
     *
     * @param items The items to add or check
     *
     * @return a new ArrayList, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> ArrayList<E> newArrayListWithinAll(@Nullable Iterable<? extends E>... items) {
        return newArrayListWithinAll(false, items);
    }

    /**
     * Creates an ArrayList containing all elements of the given iterables.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new ArrayList, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> ArrayList<E> newArrayListWithinAll(boolean emptyAsNull, @Nullable Iterable<? extends E>... items) {
        ArrayList<E> result = new ArrayList<>();
        addAll(result, items);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Creates an ArrayList containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new ArrayList without the excluded elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> ArrayList<E> newArrayListWithout(@Nullable Collection<E> source, @Nullable E... excludes) {
        return newArrayListWithout(source, ArrayUtilsWraps.asSet(excludes));
    }

    /**
     * Creates an ArrayList containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new ArrayList without the excluded elements
     */
    @Nonnull
    public static <E> ArrayList<E> newArrayListWithout(@Nullable Collection<E> source, @Nullable Collection<? extends E> excludes) {
        ArrayList<E> result = newArrayListWithin(source);
        if (isNotEmpty(excludes)) {
            result.removeAll(excludes);
        }
        return result;
    }

    /**
     * Returns a CopyOnWriteArrayList containing the given collection elements.
     *
     * The original list is reused when it has the requested type.
     *
     * @param collection The source collection
     *
     * @return a CopyOnWriteArrayList containing the elements
     */
    @Nonnull
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayListIfNull(@Nullable Collection<E> collection) {
        return (collection instanceof CopyOnWriteArrayList<E> alias) ? alias : (collection == null ? new CopyOnWriteArrayList<>() : new CopyOnWriteArrayList<>(collection));
    }

    /**
     * Creates a CopyOnWriteArrayList containing the given items.
     *
     * @param collection The source collection
     *
     * @return a new CopyOnWriteArrayList, or null when configured to return null for empty input
     */
    @Nonnull
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayListWithin(@Nullable Collection<E> collection) {
        return (collection == null) ? new CopyOnWriteArrayList<>() : new CopyOnWriteArrayList<>(collection);
    }

    /**
     * Creates a CopyOnWriteArrayList containing the given items.
     *
     * @param items The items to add or check
     *
     * @return a new CopyOnWriteArrayList, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayListWithin(@Nullable E... items) {
        return newCopyOnWriteArrayListWithin(false, items);
    }

    /**
     * Creates a CopyOnWriteArrayList containing the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new CopyOnWriteArrayList, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayListWithin(boolean emptyAsNull, @Nullable E... items) {
        return newCopyOnWriteArrayListWithinAll(emptyAsNull, ArrayUtilsWraps.asList(items));
    }

    /**
     * Creates a CopyOnWriteArrayList containing all elements of the given iterables.
     *
     * @param items The items to add or check
     *
     * @return a new CopyOnWriteArrayList, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayListWithinAll(@Nullable Iterable<? extends E>... items) {
        return newCopyOnWriteArrayListWithinAll(false, items);
    }

    /**
     * Creates a CopyOnWriteArrayList containing all elements of the given iterables.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new CopyOnWriteArrayList, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayListWithinAll(boolean emptyAsNull, @Nullable Iterable<? extends E>... items) {
        CopyOnWriteArrayList<E> result = new CopyOnWriteArrayList<>();
        addAll(result, items);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Creates a CopyOnWriteArrayList containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new CopyOnWriteArrayList without the excluded elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayListWithout(@Nullable Collection<E> source, @Nullable E... excludes) {
        return newCopyOnWriteArrayListWithout(source, ArrayUtilsWraps.asSet(excludes));
    }

    /**
     * Creates a CopyOnWriteArrayList containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new CopyOnWriteArrayList without the excluded elements
     */
    @Nonnull
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayListWithout(@Nullable Collection<E> source, @Nullable Collection<? extends E> excludes) {
        CopyOnWriteArrayList<E> result = newCopyOnWriteArrayListWithin(source);
        if (isNotEmpty(excludes)) {
            result.removeAll(excludes);
        }
        return result;
    }

    /**
     * Returns a HashSet containing the given collection elements.
     *
     * The original set is reused when it is already a HashSet.
     *
     * @param collection The source collection
     *
     * @return a HashSet containing the elements
     */
    @Nonnull
    public static <E> HashSet<E> newHashSetIfNull(@Nullable Collection<E> collection) {
        return (collection instanceof HashSet<E> alias) ? alias : (collection == null ? new HashSet<>() : new HashSet<>(collection));
    }

    /**
     * Creates a HashSet with enough capacity for the given size.
     *
     * @param size The requested collection size
     *
     * @return a new HashSet
     */
    @Nonnull
    public static <E> HashSet<E> newHashSetSizing(int size) {
        return (size < 0) ? new HashSet<>() : new HashSet<>((int) ((float) size / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * Creates a HashSet containing the given items.
     *
     * @param collection The source collection
     *
     * @return a new HashSet, or null when configured to return null for empty input
     */
    @Nonnull
    public static <E> HashSet<E> newHashSetWithin(@Nullable Collection<E> collection) {
        return (collection == null) ? new HashSet<>() : new HashSet<>(collection);
    }

    /**
     * Creates a HashSet containing the given items.
     *
     * @param items The items to add or check
     *
     * @return a new HashSet, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> HashSet<E> newHashSetWithin(@Nullable E... items) {
        return newHashSetWithin(false, items);
    }

    /**
     * Creates a HashSet containing the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new HashSet, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> HashSet<E> newHashSetWithin(boolean emptyAsNull, @Nullable E... items) {
        return newHashSetWithinAll(emptyAsNull, ArrayUtilsWraps.asList(items));
    }

    /**
     * Creates a HashSet containing all elements of the given iterables.
     *
     * @param items The items to add or check
     *
     * @return a new HashSet, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> HashSet<E> newHashSetWithinAll(@Nullable Iterable<? extends E>... items) {
        return newHashSetWithinAll(false, items);
    }

    /**
     * Creates a HashSet containing all elements of the given iterables.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new HashSet, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> HashSet<E> newHashSetWithinAll(boolean emptyAsNull, @Nullable Iterable<? extends E>... items) {
        HashSet<E> result = new HashSet<>();
        addAll(result, items);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Creates a HashSet containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new HashSet without the excluded elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> HashSet<E> newHashSetWithout(@Nullable Collection<E> source, @Nullable E... excludes) {
        return newHashSetWithout(source, ArrayUtilsWraps.asSet(excludes));
    }

    /**
     * Creates a HashSet containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new HashSet without the excluded elements
     */
    @Nonnull
    public static <E> HashSet<E> newHashSetWithout(@Nullable Collection<E> source, @Nullable Collection<? extends E> excludes) {
        HashSet<E> result = newHashSetWithin(source);
        if (isNotEmpty(excludes)) {
            result.removeAll(excludes);
        }
        return result;
    }

    /**
     * Returns a LinkedHashSet containing the given collection elements.
     *
     * The original set is reused when it is already a LinkedHashSet.
     *
     * @param collection The source collection
     *
     * @return a LinkedHashSet containing the elements
     */
    @Nonnull
    public static <E> LinkedHashSet<E> newLinkedHashSetIfNull(@Nullable Collection<E> collection) {
        return (collection instanceof LinkedHashSet<E> alias) ? alias : (collection == null ? new LinkedHashSet<>() : new LinkedHashSet<>(collection));
    }

    /**
     * Creates a LinkedHashSet with enough capacity for the given size.
     *
     * @param size The requested collection size
     *
     * @return a new LinkedHashSet
     */
    @Nonnull
    public static <E> LinkedHashSet<E> newLinkedHashSetSizing(int size) {
        return (size < 0) ? new LinkedHashSet<>() : new LinkedHashSet<>((int) ((float) size / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * Creates a LinkedHashSet containing the given items.
     *
     * @param collection The source collection
     *
     * @return a new LinkedHashSet, or null when configured to return null for empty input
     */
    @Nonnull
    public static <E> LinkedHashSet<E> newLinkedHashSetWithin(@Nullable Collection<E> collection) {
        return (collection == null) ? new LinkedHashSet<>() : new LinkedHashSet<>(collection);
    }

    /**
     * Creates a LinkedHashSet containing the given items.
     *
     * @param items The items to add or check
     *
     * @return a new LinkedHashSet, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> LinkedHashSet<E> newLinkedHashSetWithin(@Nullable E... items) {
        return newLinkedHashSetWithin(false, items);
    }

    /**
     * Creates a LinkedHashSet containing the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new LinkedHashSet, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> LinkedHashSet<E> newLinkedHashSetWithin(boolean emptyAsNull, @Nullable E... items) {
        return newLinkedHashSetWithinAll(emptyAsNull, ArrayUtilsWraps.asList(items));
    }

    /**
     * Creates a LinkedHashSet containing all elements of the given iterables.
     *
     * @param items The items to add or check
     *
     * @return a new LinkedHashSet, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> LinkedHashSet<E> newLinkedHashSetWithinAll(@Nullable Iterable<? extends E>... items) {
        return newLinkedHashSetWithinAll(false, items);
    }

    /**
     * Creates a LinkedHashSet containing all elements of the given iterables.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new LinkedHashSet, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> LinkedHashSet<E> newLinkedHashSetWithinAll(boolean emptyAsNull, @Nullable Iterable<? extends E>... items) {
        LinkedHashSet<E> result = new LinkedHashSet<>();
        addAll(result, items);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Creates a LinkedHashSet containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new LinkedHashSet without the excluded elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> LinkedHashSet<E> newLinkedHashSetWithout(@Nullable Collection<E> source, @Nullable E... excludes) {
        return newLinkedHashSetWithout(source, ArrayUtilsWraps.asSet(excludes));
    }

    /**
     * Creates a LinkedHashSet containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new LinkedHashSet without the excluded elements
     */
    @Nonnull
    public static <E> LinkedHashSet<E> newLinkedHashSetWithout(@Nullable Collection<E> source, @Nullable Collection<? extends E> excludes) {
        LinkedHashSet<E> result = newLinkedHashSetWithin(source);
        if (isNotEmpty(excludes)) {
            result.removeAll(excludes);
        }
        return result;
    }

    /**
     * Returns a LinkedList containing the given collection elements.
     *
     * The original list is reused when it is already a LinkedList.
     *
     * @param collection The source collection
     *
     * @return a LinkedList containing the elements
     */
    @Nonnull
    public static <E> LinkedList<E> newLinkedListIfNull(@Nullable Collection<E> collection) {
        return (collection instanceof LinkedList<E> alias) ? alias : (collection == null ? new LinkedList<>() : new LinkedList<>(collection));
    }

    /**
     * Creates a LinkedList containing the given items.
     *
     * @param collection The source collection
     *
     * @return a new LinkedList, or null when configured to return null for empty input
     */
    @Nonnull
    public static <E> LinkedList<E> newLinkedListWithin(@Nullable Collection<E> collection) {
        return (collection == null) ? new LinkedList<>() : new LinkedList<>(collection);
    }

    /**
     * Creates a LinkedList containing the given items.
     *
     * @param items The items to add or check
     *
     * @return a new LinkedList, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> LinkedList<E> newLinkedListWithin(@Nullable E... items) {
        return newLinkedListWithin(false, items);
    }

    /**
     * Creates a LinkedList containing the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new LinkedList, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> LinkedList<E> newLinkedListWithin(boolean emptyAsNull, @Nullable E... items) {
        return newLinkedListWithinAll(emptyAsNull, ArrayUtilsWraps.asList(items));
    }

    /**
     * Creates a LinkedList containing all elements of the given iterables.
     *
     * @param items The items to add or check
     *
     * @return a new LinkedList, or null when configured to return null for empty input
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> LinkedList<E> newLinkedListWithinAll(@Nullable Iterable<? extends E>... items) {
        return newLinkedListWithinAll(false, items);
    }

    /**
     * Creates a LinkedList containing all elements of the given iterables.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return a new LinkedList, or null when configured to return null for empty input
     */
    @Nullable
    @SafeVarargs
    public static <E> LinkedList<E> newLinkedListWithinAll(boolean emptyAsNull, @Nullable Iterable<? extends E>... items) {
        LinkedList<E> result = new LinkedList<>();
        addAll(result, items);
        return (emptyAsNull && isEmpty(result)) ? null : result;
    }

    /**
     * Creates a LinkedList containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new LinkedList without the excluded elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> LinkedList<E> newLinkedListWithout(@Nullable Collection<E> source, @Nullable E... excludes) {
        return newLinkedListWithout(source, ArrayUtilsWraps.asSet(excludes));
    }

    /**
     * Creates a LinkedList containing the source elements except the excluded ones.
     *
     * @param source The source collection
     * @param excludes The elements to exclude
     *
     * @return a new LinkedList without the excluded elements
     */
    @Nonnull
    public static <E> LinkedList<E> newLinkedListWithout(@Nullable Collection<E> source, @Nullable Collection<? extends E> excludes) {
        LinkedList<E> result = newLinkedListWithin(source);
        if (isNotEmpty(excludes)) {
            result.removeAll(excludes);
        }
        return result;
    }

    /**
     * Returns null when the collection is empty.
     *
     * @param collection The source collection
     *
     * @return the collection, or null when it is empty
     */
    @Nullable
    public static <T extends Collection<?>> T nullIfEmpty(@Nullable T collection) {
        return isEmpty(collection) ? null : collection;
    }

    /**
     * Removes all elements that match the given predicate.
     *
     * @param collection The source collection
     * @param filter The predicate used to select elements
     *
     * @return true when at least one element was removed
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean removeIf(@Nullable Collection<E> collection, @Nullable Predicate<? super E> filter) {
        return ObjectUtils.allNotNull(collection, filter) && collection.removeIf(filter);
    }

    /**
     * Returns the size of the given collection.
     *
     * @param collection The source collection
     *
     * @return the collection size, or zero when the collection is null
     */
    public static int size(@Nullable Collection<?> collection) {
        return (collection == null) ? 0 : collection.size();
    }

    /**
     * Returns the greatest size among the given collections.
     *
     * @param collections The source collections
     *
     * @return the greatest collection size, or zero when no collections are provided
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static int maxSize(@Nullable Collection<?>... collections) {
        return ArrayUtils.isEmpty(collections) ? 0 : Arrays.stream(collections).mapToInt(CollectionPlainWraps::size).max().orElse(0);
    }

    /**
     * Returns the total size of the given collections.
     *
     * @param collections The source collections
     *
     * @return the combined size of all collections
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static int sumSize(@Nullable Collection<?>... collections) {
        return ArrayUtils.isEmpty(collections) ? 0 : Arrays.stream(collections).mapToInt(CollectionPlainWraps::size).sum();
    }

    /**
     * Return an array containing all the items in the collection
     *
     * @param collection The collection to convert, with item that can not be primitive types
     *
     * @return an array containing all the items in the collection
     *
     * @see org.apache.commons.lang3.ArrayUtils#addAll
     * @see "org.springframework.util.CollectionUtils#toArray"
     * @see "org.springframework.util.StringUtils#toStringArray"
     * @see "org.springframework.util.CollectionUtils#findCommonElementType"
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <E> E[] toElementArray(@Nullable Collection<E> collection) {
        Class<E> clazz = (Class<E>) getComponentType(collection);
        return toElementArray(collection, clazz);
    }

    /**
     * Converts the collection to an array of the expected element type.
     *
     * @param collection The source collection
     * @param elementType The expected element type
     *
     * @return an array containing the collection elements, or null when the type is unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> E[] toElementArray(@Nullable Collection<E> collection, @Nullable Class<? super E> elementType) {
        return ObjectUtils.anyNull(collection, elementType) ? null : IteratorPlainWraps.toElementArray(collection.iterator(), elementType);
    }

    /**
     * Converts the collection to an array of the expected element type.
     *
     * @param collection The source collection
     * @param target The target collection
     *
     * @return an array containing the collection elements, or null when the type is unavailable
     */
    @Nullable
    public static <E> E[] toElementArray(@Nullable Collection<E> collection, @Nullable E[] target) {
        return (isEmpty(collection) || target == null) ? null : collection.toArray(target);
    }

    /**
     * Converts the collection to an enumeration.
     *
     * @param collection The source collection
     *
     * @return an enumeration over the elements, or null when the collection is empty
     */
    @Nullable
    public static <E> Enumeration<E> toElementEnumeration(@Nullable Collection<E> collection) {
        return isEmpty(collection) ? null : Collections.enumeration(collection);
    }

    /**
     * Converts the collection to an object array.
     *
     * @param collection The source collection
     *
     * @return an array containing the collection elements, or null when the collection is null
     */
    @Nullable
    public static Object[] toObjectArray(@Nullable Collection<?> collection) {
        return (collection == null) ? null : collection.toArray();
    }

    /**
     * Returns an unmodifiable view of the given items.
     *
     * @param items The items to add or check
     *
     * @return an unmodifiable collection, or null when configured to return null for empty input
     */
    @SafeVarargs
    public static <E> Collection<E> unmodifiableCollection(@Nullable E... items) {
        return unmodifiableCollection(false, items);
    }

    /**
     * Returns an unmodifiable view of the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return an unmodifiable collection, or null when configured to return null for empty input
     */
    @SafeVarargs
    public static <E> Collection<E> unmodifiableCollection(boolean emptyAsNull, @Nullable E... items) {
        return unmodifiableCollection(emptyAsNull, ArrayUtilsWraps.asList(items));
    }

    /**
     * Returns an unmodifiable view of the given items.
     *
     * @param collection The source collection
     *
     * @return an unmodifiable collection, or null when configured to return null for empty input
     */
    @Nullable
    public static <E> Collection<E> unmodifiableCollection(@Nullable Collection<? extends E> collection) {
        return unmodifiableCollection(false, collection);
    }

    /**
     * Returns an unmodifiable view of the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param collection The source collection
     *
     * @return an unmodifiable collection, or null when configured to return null for empty input
     */
    @Nullable
    public static <E> Collection<E> unmodifiableCollection(boolean emptyAsNull, @Nullable Collection<? extends E> collection) {
        return (collection == null || (emptyAsNull && collection.isEmpty())) ? null : Collections.unmodifiableCollection(collection);
    }

    /**
     * Returns an unmodifiable list view of the given items.
     *
     * @param items The items to add or check
     *
     * @return an unmodifiable list, or null when configured to return null for empty input
     */
    @SafeVarargs
    public static <E> List<E> unmodifiableList(@Nullable E... items) {
        return unmodifiableList(false, items);
    }

    /**
     * Returns an unmodifiable list view of the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return an unmodifiable list, or null when configured to return null for empty input
     */
    @SafeVarargs
    public static <E> List<E> unmodifiableList(boolean emptyAsNull, @Nullable E... items) {
        return unmodifiableList(emptyAsNull, ArrayUtilsWraps.asList(items));
    }

    /**
     * Returns an unmodifiable list view of the given items.
     *
     * @param list The target list
     *
     * @return an unmodifiable list, or null when configured to return null for empty input
     */
    @Nullable
    public static <E> List<E> unmodifiableList(@Nullable List<? extends E> list) {
        return unmodifiableList(false, list);
    }

    /**
     * Returns an unmodifiable list view of the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param list The target list
     *
     * @return an unmodifiable list, or null when configured to return null for empty input
     */
    @Nullable
    public static <E> List<E> unmodifiableList(boolean emptyAsNull, @Nullable List<? extends E> list) {
        return (list == null || (emptyAsNull && list.isEmpty())) ? null : Collections.unmodifiableList(list);
    }

    /**
     * Returns an unmodifiable set view of the given items.
     *
     * @param items The items to add or check
     *
     * @return an unmodifiable set, or null when configured to return null for empty input
     */
    @SafeVarargs
    public static <E> Set<E> unmodifiableSet(@Nullable E... items) {
        return unmodifiableSet(false, items);
    }

    /**
     * Returns an unmodifiable set view of the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param items The items to add or check
     *
     * @return an unmodifiable set, or null when configured to return null for empty input
     */
    @SafeVarargs
    public static <E> Set<E> unmodifiableSet(boolean emptyAsNull, @Nullable E... items) {
        return unmodifiableSet(emptyAsNull, ArrayUtilsWraps.asSet(items));
    }

    /**
     * Returns an unmodifiable set view of the given items.
     *
     * @param set The target set
     *
     * @return an unmodifiable set, or null when configured to return null for empty input
     */
    @Nullable
    public static <E> Set<E> unmodifiableSet(@Nullable Set<? extends E> set) {
        return unmodifiableSet(false, set);
    }

    /**
     * Returns an unmodifiable set view of the given items.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param set The target set
     *
     * @return an unmodifiable set, or null when configured to return null for empty input
     */
    @Nullable
    public static <E> Set<E> unmodifiableSet(boolean emptyAsNull, @Nullable Set<? extends E> set) {
        return (set == null || (emptyAsNull && set.isEmpty())) ? null : Collections.unmodifiableSet(set);
    }

    /**
     * Returns an unmodifiable map view of the given map.
     *
     * @param map The map to wrap
     *
     * @return an unmodifiable map, or null when configured to return null for empty input
     */
    @Nullable
    public static <K, V> Map<K, V> unmodifiableMap(@Nullable Map<? extends K, ? extends V> map) {
        return unmodifiableMap(false, map);
    }

    /**
     * Returns an unmodifiable map view of the given map.
     *
     * @param emptyAsNull Whether an empty result should be returned as null
     * @param map The map to wrap
     *
     * @return an unmodifiable map, or null when configured to return null for empty input
     */
    @Nullable
    public static <K, V> Map<K, V> unmodifiableMap(boolean emptyAsNull, @Nullable Map<? extends K, ? extends V> map) {
        return (map == null || (emptyAsNull && map.isEmpty())) ? null : Collections.unmodifiableMap(map);
    }
}
