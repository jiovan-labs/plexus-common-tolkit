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


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;


/**
 * Utilities for {@link java.util.Iterator}
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class IteratorPlainWraps {
    /**
     * Checks whether the given {@code iterator} contains the given item
     *
     * @param iterator The source iterator to check
     * @param comparison The target item to look for
     *
     * @return {@code true} if found, {@code false} otherwise
     */
    public static boolean contains(@Nullable Iterator<?> iterator, @Nullable Object comparison) {
        if (iterator == null) {
            return false;
        }
        while (iterator.hasNext()) {
            Object item = iterator.next();
            if (ObjectUtilsWraps.equals(item, comparison)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the first non-null element of the source.
     *
     * @param iterator The source iterator
     *
     * @return the first non-null element, or null when there is none
     */
    @Nullable
    public static <E> E firstNonNull(@Nullable Iterator<E> iterator) {
        if (iterator == null) {
            return null;
        }
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    /**
     * Returns the first non-empty element of the source.
     *
     * @param iterator The source iterator
     *
     * @return the first non-empty element, or null when there is none
     */
    @Nullable
    public static <E> E firstNonEmpty(@Nullable Iterator<E> iterator) {
        if (iterator == null) {
            return null;
        }
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (ObjectUtils.isNotEmpty(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Performs an action for each element of the source.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <E> void forEach(@Nullable Iterator<E> iterator, @Nullable Consumer<? super E> action) {
        forEach(iterator, action, null);
    }

    /**
     * Performs an action for each element of the source.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> void forEach(@Nullable Iterator<E> iterator, @Nullable Consumer<? super E> action, @Nullable Predicate<? super E> filter) {
        if (ObjectUtils.anyNull(iterator, action)) {
            return;
        }
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (filter == null || filter.test(item)) {
                action.accept(item);
            }
        }
    }

    /**
     * Performs an action for each element until the action returns false.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <E> void forEachBreakable(@Nullable Iterator<E> iterator, @Nullable Function<? super E, Boolean> action) {
        forEachBreakable(iterator, action, null);
    }

    /**
     * Perform an action for filtered items in the {@code iterator} until the {@code action} return false
     *
     * @param iterator The source iterator to lookup
     * @param action The action to be performed. when return {@code true}, means continue next item; otherwise means break loop
     * @param filter The filter to choose items
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> void forEachBreakable(@Nullable Iterator<E> iterator, @Nullable Function<? super E, Boolean> action, @Nullable Predicate<? super E> filter) {
        if (ObjectUtils.anyNull(iterator, action)) {
            return;
        }
        while (iterator.hasNext()) {
            E item = iterator.next();
            if ((filter == null || filter.test(item)) && BooleanUtils.isNotTrue(action.apply(item))) {
                break;
            }
        }
    }

    /**
     * Performs an action for all elements except the last one.
     *
     * The last element is returned after the action has been applied.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     *
     * @return the last element, or null when unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> E forEachTailing(@Nullable Iterator<E> iterator, @Nullable Consumer<? super E> action) {
        if (ObjectUtils.anyNull(iterator, action)) {
            return null;
        }
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (iterator.hasNext()) {
                action.accept(item);
            } else {
                return item;
            }
        }
        return null;
    }

    /**
     * Performs an action for each element together with its index.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <E> void forEachIndexing(@Nullable Iterator<E> iterator, @Nullable BiConsumer<Integer, ? super E> action) {
        forEachIndexing(iterator, action, null);
    }

    /**
     * Performs an action for each element together with its index.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> void forEachIndexing(@Nullable Iterator<E> iterator, @Nullable BiConsumer<Integer, ? super E> action, @Nullable BiPredicate<Integer, ? super E> filter) {
        if (ObjectUtils.anyNull(iterator, action)) {
            return;
        }
        int index = 0;
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (filter == null || filter.test(index, item)) {
                action.accept(index, item);
            }
            index++;
        }
    }

    /**
     * Performs an indexed action for each element until the action returns false.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <E> void forEachIndexingBreakable(@Nullable Iterator<E> iterator, @Nullable BiFunction<Integer, ? super E, Boolean> action) {
        forEachIndexingBreakable(iterator, action, null);
    }

    /**
     * Perform an action with index for filtered items in the {@code iterator} until the {@code action} return false
     *
     * @param iterator The iterator to get a value from
     * @param action The action to be performed. when return {@code true}, means continue next item; otherwise means break loop
     * @param filter The filter to choose items
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> void forEachIndexingBreakable(@Nullable Iterator<E> iterator, @Nullable BiFunction<Integer, ? super E, Boolean> action, @Nullable BiPredicate<Integer, ? super E> filter) {
        if (ObjectUtils.anyNull(iterator, action)) {
            return;
        }
        int index = 0;
        while (iterator.hasNext()) {
            E item = iterator.next();
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
     * @param iterator The source iterator
     * @param action The action to perform
     *
     * @return the last element, or null when unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> E forEachIndexingTailing(@Nullable Iterator<E> iterator, @Nullable BiConsumer<Integer, ? super E> action) {
        if (ObjectUtils.anyNull(iterator, action)) {
            return null;
        }
        int index = 0;
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (iterator.hasNext()) {
                action.accept(index, item);
            } else {
                return item;
            }
            index++;
        }
        return null;
    }

    /**
     * Returns the index-th value in the iterator
     *
     * @param iterator The source to get from
     * @param index The index to get
     *
     * @return the index-th value in the iterator
     */
    @Nullable
    public static <E> E get(@Nullable Iterator<E> iterator, int index) {
        if (iterator == null || index < 0) {
            return null;
        }
        int cursor = 0;
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (cursor == index) {
                return item;
            }
            cursor++;
        }
        return null;
    }

    /**
     * Returns the component type inferred from the elements.
     *
     * When deepScan is false, only the first non-null element is checked.
     *
     * @param iterator The source iterator
     *
     * @return the component type, or null when it cannot be determined
     */
    @Nullable
    public static Class<?> getComponentType(@Nullable Iterator<?> iterator) {
        return getComponentType(iterator, false);
    }

    /**
     * Returns the component type of superclass in the iterator
     *
     * @param iterator The iterator to check
     * @param deepScan true means need to scan all the items; false means by the first nonnull item
     *
     * @see "org.springframework.util.CollectionUtils#findCommonElementType"
     */
    @Nullable
    @SuppressWarnings("DuplicatedCode")
    public static Class<?> getComponentType(@Nullable Iterator<?> iterator, boolean deepScan) {
        if (iterator == null) {
            return null;
        }
        Class<?> candidate = null;
        while (iterator.hasNext()) {
            Object item = iterator.next();
            if (item == null) {
                continue;
            }
            if (!deepScan) {
                return item.getClass();
            }
            if (candidate == null) {
                candidate = item.getClass();
            } else {
                boolean assignable = false;
                if (ClassUtils.isAssignable(candidate, item.getClass())) {
                    candidate = item.getClass();
                    assignable = true;
                }
                if (!assignable) {
                    assignable = ClassUtils.isAssignable(item.getClass(), candidate);
                }
                if (!assignable) {
                    return null;
                }
            }
        }
        return candidate;
    }

    /**
     * Runs an action when the source is empty.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static void ifEmpty(@Nullable Iterator<?> iterator, @Nullable Runnable action) {
        if (isEmpty(iterator) && action != null) {
            action.run();
        }
    }

    /**
     * Runs one of two actions depending on whether the source is empty.
     *
     * @param iterator The source iterator
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <E, T extends Iterator<E>> void ifEmptyOrElse(@Nullable T iterator, @Nullable Runnable absentAction, @Nullable Consumer<? super T> presentAction) {
        if (isEmpty(iterator)) {
            if (absentAction != null) {
                absentAction.run();
            }
        } else {
            if (presentAction != null) {
                presentAction.accept(iterator);
            }
        }
    }

    /**
     * Runs an action when the source is not empty.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <E, T extends Iterator<E>> void ifNotEmpty(@Nullable T iterator, @Nullable Consumer<? super T> action) {
        ifNotEmpty(iterator, action, null);
    }

    /**
     * Runs an action when the source is not empty.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    public static <E, T extends Iterator<E>> void ifNotEmpty(@Nullable T iterator, @Nullable Consumer<? super T> action, @Nullable Predicate<? super T> filter) {
        if (isNotEmpty(iterator) && action != null && (filter == null || filter.test(iterator))) {
            action.accept(iterator);
        }
    }

    /**
     * Runs an action when the source is not empty.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <E, T extends Iterator<E>> void ifNotEmpty(@Nullable T iterator, @Nullable Runnable action) {
        if (isNotEmpty(iterator) && action != null) {
            action.run();
        }
    }

    /**
     * Runs an action when the source contains exactly one element.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <T extends Iterator<?>> void ifSingleton(@Nullable T iterator, @Nullable Consumer<? super T> action) {
        ifSingletonOrElse(iterator, action, null);
    }

    /**
     * Runs an action when the source contains exactly one element.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <T extends Iterator<?>> void ifSingleton(@Nullable T iterator, @Nullable Runnable action) {
        ifSingletonOrElse(iterator, action, null);
    }

    /**
     * Runs one of two actions depending on whether the source is a singleton.
     *
     * @param iterator The source iterator
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Iterator<?>> void ifSingletonOrElse(@Nullable T iterator, @Nullable Consumer<? super T> presentAction, @Nullable Consumer<? super T> absentAction) {
        if (isSingleton(iterator)) {
            if (presentAction != null) {
                presentAction.accept(iterator);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(iterator);
            }
        }
    }

    /**
     * Runs one of two actions depending on whether the source is a singleton.
     *
     * @param iterator The source iterator
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Iterator<?>> void ifSingletonOrElse(@Nullable T iterator, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isSingleton(iterator)) {
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
     * Runs an action when the source contains more than one element.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <T extends Iterator<?>> void ifMultitude(@Nullable T iterator, @Nullable Consumer<? super T> action) {
        if (isMultitude(iterator) && action != null) {
            action.accept(iterator);
        }
    }

    /**
     * Runs an action when the source contains more than one element.
     *
     * @param iterator The source iterator
     * @param action The action to perform
     */
    public static <T extends Iterator<?>> void ifMultitude(@Nullable T iterator, @Nullable Runnable action) {
        if (isMultitude(iterator) && action != null) {
            action.run();
        }
    }

    /**
     * Checks whether the source is null or empty.
     *
     * @param iterator The source iterator
     *
     * @return true when the source is empty; false otherwise
     */
    public static boolean isEmpty(@Nullable Iterator<?> iterator) {
        return iterator == null || !iterator.hasNext();
    }

    /**
     * Checks whether the source contains at least one element.
     *
     * @param iterator The source iterator
     *
     * @return true when the source is not empty; false otherwise
     */
    public static boolean isNotEmpty(@Nullable Iterator<?> iterator) {
        return !isEmpty(iterator);
    }

    /**
     * Checks whether the index is within the bounds of the source.
     *
     * @param iterator The source iterator
     * @param index The index to check
     *
     * @return true when the index is valid; false otherwise
     */
    public static boolean isIndexBound(@Nullable Iterator<?> iterator, int index) {
        return index >= 0 && index < size(iterator);
    }

    /**
     * Checks whether all the given sources have the same size.
     *
     * At least two sources are required.
     *
     * @param iterators The source iterators
     *
     * @return true when all sizes are equal; false otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean isSameSize(@Nullable Iterator<?>... iterators) {
        if (ArrayUtils.getLength(iterators) < 2) {
            return false;
        }
        int size = size(ArrayUtils.get(iterators, 0));
        return Arrays.stream(iterators).skip(1L).allMatch(item -> size(item) == size);
    }

    /**
     * Checks whether the source contains exactly one element.
     *
     * @param iterator The source iterator
     *
     * @return true when the source contains exactly one element; false otherwise
     */
    public static boolean isSingleton(@Nullable Iterator<?> iterator) {
        return size(iterator) == 1;
    }

    /**
     * Checks whether the source contains more than one element.
     *
     * @param iterator The source iterator
     *
     * @return true when the source contains more than one element; false otherwise
     */
    public static boolean isMultitude(@Nullable Iterator<?> iterator) {
        return size(iterator) > 1;
    }

    /**
     * Returns null when the source is empty.
     *
     * @param iterator The source iterator
     *
     * @return the source, or null when it is empty
     */
    @Nullable
    public static <T extends Iterator<?>> T nullIfEmpty(@Nullable T iterator) {
        return isEmpty(iterator) ? null : iterator;
    }

    /**
     * @see java.util.Collection#removeIf
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean removeIf(@Nullable Iterator<E> iterator, @Nullable Predicate<? super E> filter) {
        if (ObjectUtils.anyNull(iterator, filter)) {
            return false;
        }
        boolean removed = false;
        while (iterator.hasNext()) {
            if (filter.test(iterator.next())) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Removes all elements whose index and value match the given predicate.
     *
     * @param iterator The source iterator
     * @param filter The predicate used to select elements
     *
     * @return true when at least one element was removed
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean removeIfIndexing(@Nullable Iterator<E> iterator, @Nullable BiPredicate<Integer, ? super E> filter) {
        if (ObjectUtils.anyNull(iterator, filter)) {
            return false;
        }
        boolean removed = false;
        int index = 0;
        while (iterator.hasNext()) {
            if (filter.test(index, iterator.next())) {
                iterator.remove();
                removed = true;
            }
            index++;
        }
        return removed;
    }

    /**
     * Returns the number of elements in the source.
     *
     * @param iterator The source iterator
     *
     * @return the number of elements, or zero when the source is null
     */
    public static int size(@Nullable Iterator<?> iterator) {
        if (iterator == null) {
            return 0;
        }
        int result = 0;
        while (iterator.hasNext()) {
            iterator.next();
            result++;
        }
        return result;
    }

    /**
     * Returns the greatest size among the given sources.
     *
     * @param iterators The source iterators
     *
     * @return the greatest size, or zero when no sources are provided
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static int maxSize(@Nullable Iterator<?>... iterators) {
        return ArrayUtils.isEmpty(iterators) ? 0 : Arrays.stream(iterators).mapToInt(IteratorPlainWraps::size).max().orElse(0);
    }

    /**
     * Returns the total size of the given sources.
     *
     * @param iterators The source iterators
     *
     * @return the combined size of all sources
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static int sumSize(@Nullable Iterator<?>... iterators) {
        return ArrayUtils.isEmpty(iterators) ? 0 : Arrays.stream(iterators).mapToInt(IteratorPlainWraps::size).sum();
    }

    /**
     * Converts the source to an array of the expected element type.
     *
     * @param iterator The source iterator
     *
     * @return an array containing the elements, or null when the type is unavailable
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <E> E[] toElementArray(@Nullable Iterator<E> iterator) {
        Class<E> clazz = (Class<E>) getComponentType(iterator);
        return toElementArray(iterator, clazz);
    }

    /**
     * Converts the source to an array of the expected element type.
     *
     * @param iterator The source iterator
     * @param elementType The expected element type
     *
     * @return an array containing the elements, or null when the type is unavailable
     */
    @Nullable
    @SuppressWarnings({"unchecked", "SuspiciousToArrayCall"})
    public static <E> E[] toElementArray(@Nullable Iterator<E> iterator, @Nullable Class<? super E> elementType) {
        if (ObjectUtils.anyNull(iterator, elementType)) {
            return null;
        }
        List<E> result = toElementList(iterator);
        if (CollectionPlainWraps.isEmpty(result)) {
            return null;
        }
        if (elementType == Boolean.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY);
        } else if (elementType == Byte.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY);
        } else if (elementType == Character.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY);
        } else if (elementType == Class.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_CLASS_ARRAY);
        } else if (elementType == Double.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY);
        } else if (elementType == Field.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_FIELD_ARRAY);
        } else if (elementType == Float.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY);
        } else if (elementType == Integer.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY);
        } else if (elementType == Long.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_LONG_OBJECT_ARRAY);
        } else if (elementType == Method.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_METHOD_ARRAY);
        } else if (elementType == Object.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_OBJECT_ARRAY);
        } else if (elementType == Short.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY);
        } else if (elementType == String.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        } else if (elementType == Throwable.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_THROWABLE_ARRAY);
        } else if (elementType == Type.class) {
            return (E[]) result.toArray(ArrayUtils.EMPTY_TYPE_ARRAY);
        } else {
            E[] array = (E[]) Array.newInstance(elementType, result.size());
            return result.toArray(array);
        }
    }

    /**
     * Converts the source to an array of the expected element type.
     *
     * @param iterator The source iterator
     * @param target The source to check
     *
     * @return an array containing the elements, or null when the type is unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> E[] toElementArray(@Nullable Iterator<E> iterator, @Nullable E[] target) {
        if (ObjectUtils.anyNull(iterator, target)) {
            return null;
        }
        List<E> result = toElementList(iterator);
        return CollectionPlainWraps.isEmpty(result) ? null : result.toArray(target);
    }

    /**
     * Converts the source to an enumeration.
     *
     * @param iterator The source iterator
     *
     * @return an enumeration over the elements, or null when the source is empty
     */
    @Nullable
    public static <E> Enumeration<E> toElementEnumeration(@Nullable Iterator<E> iterator) {
        return CollectionPlainWraps.toElementEnumeration(toElementList(iterator));
    }

    /**
     * Converts the source to a list.
     *
     * @param iterator The source iterator
     *
     * @return a list containing the elements, or null when the source is null
     */
    @Nullable
    public static <E> List<E> toElementList(@Nullable Iterator<E> iterator) {
        if (iterator == null) {
            return null;
        }
        List<E> result = new ArrayList<>();
        forEach(iterator, result::add);
        return result.isEmpty() ? null : result;
    }

    /**
     * Converts the source to a set.
     *
     * @param iterator The source iterator
     *
     * @return a set containing the elements, or null when the source is null
     */
    @Nullable
    public static <E> Set<E> toElementSet(@Nullable Iterator<E> iterator) {
        if (iterator == null) {
            return null;
        }
        Set<E> result = new LinkedHashSet<>();
        forEach(iterator, result::add);
        return result.isEmpty() ? null : result;
    }

    /**
     * Converts the source to an object array.
     *
     * @param iterator The source iterator
     *
     * @return an array containing the elements, or null when the source is null
     */
    @Nullable
    public static Object[] toObjectArray(@Nullable Iterator<?> iterator) {
        List<?> result = toObjectList(iterator);
        return CollectionPlainWraps.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    /**
     * Converts the source to a list of objects.
     *
     * @param iterator The source iterator
     *
     * @return a list containing the elements, or null when the source is null
     */
    @Nullable
    public static List<?> toObjectList(@Nullable Iterator<?> iterator) {
        if (iterator == null) {
            return null;
        }
        List<Object> result = new ArrayList<>();
        forEach(iterator, result::add);
        return result.isEmpty() ? null : result;
    }
}
