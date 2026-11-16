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


import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;


/**
 * Utilities for {@link java.lang.Iterable}
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class IterablePlainWraps {
    /**
     * Checks whether the given {@code iterable} contains the given item
     *
     * @param iterable The source iterable to check
     * @param comparison The target item to look for
     *
     * @return {@code true} if found, {@code false} otherwise
     */
    public static boolean contains(@Nullable Iterable<?> iterable, @Nullable Object comparison) {
        return iterable != null && IteratorPlainWraps.contains(iterable.iterator(), comparison);
    }

    /**
     * Returns the first non-null element of the source.
     *
     * @param iterable The source iterable
     *
     * @return the first non-null element, or null when there is none
     */
    @Nullable
    public static <E> E firstNonNull(@Nullable Iterable<E> iterable) {
        return (iterable == null) ? null : IteratorPlainWraps.firstNonNull(iterable.iterator());
    }

    /**
     * Returns the first non-empty element of the source.
     *
     * @param iterable The source iterable
     *
     * @return the first non-empty element, or null when there is none
     */
    @Nullable
    public static <E> E firstNonEmpty(@Nullable Iterable<E> iterable) {
        return (iterable == null) ? null : IteratorPlainWraps.firstNonEmpty(iterable.iterator());
    }

    /**
     * Performs an action for each element of the source.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <E> void forEach(@Nullable Iterable<E> iterable, @Nullable Consumer<? super E> action) {
        forEach(iterable, action, null);
    }

    /**
     * Performs an action for each element of the source.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> void forEach(@Nullable Iterable<E> iterable, @Nullable Consumer<? super E> action, @Nullable Predicate<? super E> filter) {
        if (ObjectUtils.allNotNull(iterable, action)) {
            IteratorPlainWraps.forEach(iterable.iterator(), action, filter);
        }
    }

    /**
     * Performs an action for each element until the action returns false.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <E> void forEachBreakable(@Nullable Iterable<E> iterable, @Nullable Function<? super E, Boolean> action) {
        forEachBreakable(iterable, action, null);
    }

    /**
     * Performs an action for each element until the action returns false.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> void forEachBreakable(@Nullable Iterable<E> iterable, @Nullable Function<? super E, Boolean> action, @Nullable Predicate<? super E> filter) {
        if (ObjectUtils.allNotNull(iterable, action)) {
            IteratorPlainWraps.forEachBreakable(iterable.iterator(), action, filter);
        }
    }

    /**
     * Performs an action for all elements except the last one.
     *
     * The last element is returned after the action has been applied.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     *
     * @return the last element, or null when unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> E forEachTailing(@Nullable Iterable<E> iterable, @Nullable Consumer<? super E> action) {
        return ObjectUtils.anyNull(iterable, action) ? null : IteratorPlainWraps.forEachTailing(iterable.iterator(), action);
    }

    /**
     * Performs an action for each element together with its index.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <E> void forEachIndexing(@Nullable Iterable<E> iterable, @Nullable BiConsumer<Integer, ? super E> action) {
        forEachIndexing(iterable, action, null);
    }

    /**
     * Performs an action for each element together with its index.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> void forEachIndexing(@Nullable Iterable<E> iterable, @Nullable BiConsumer<Integer, ? super E> action, @Nullable BiPredicate<Integer, ? super E> filter) {
        if (ObjectUtils.allNotNull(iterable, action)) {
            IteratorPlainWraps.forEachIndexing(iterable.iterator(), action, filter);
        }
    }

    /**
     * Performs an indexed action for each element until the action returns false.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <E> void forEachIndexingBreakable(@Nullable Iterable<E> iterable, @Nullable BiFunction<Integer, ? super E, Boolean> action) {
        forEachIndexingBreakable(iterable, action, null);
    }

    /**
     * Performs an indexed action for each element until the action returns false.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> void forEachIndexingBreakable(@Nullable Iterable<E> iterable, @Nullable BiFunction<Integer, ? super E, Boolean> action, @Nullable BiPredicate<Integer, ? super E> filter) {
        if (ObjectUtils.allNotNull(iterable, action)) {
            IteratorPlainWraps.forEachIndexingBreakable(iterable.iterator(), action, filter);
        }
    }

    /**
     * Performs an indexed action for all elements except the last one.
     *
     * The last element is returned after the action has been applied.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     *
     * @return the last element, or null when unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> E forEachIndexingTailing(@Nullable Iterable<E> iterable, @Nullable BiConsumer<Integer, ? super E> action) {
        return ObjectUtils.anyNull(iterable, action) ? null : IteratorPlainWraps.forEachIndexingTailing(iterable.iterator(), action);
    }

    /**
     * Returns the index-th value in the iterable
     *
     * @param iterable The source to get from
     * @param index The index to get
     *
     * @return the index-th value in the iterable
     */
    @Nullable
    public static <E> E get(@Nullable Iterable<E> iterable, int index) {
        if (iterable == null || index < 0) {
            return null;
        }
        if (iterable instanceof List<E> alias) {
            return ListPlainWraps.get(alias, index);
        }
        return IteratorPlainWraps.get(iterable.iterator(), index);
    }

    /**
     * Returns the first item in the given iterable
     *
     * @param iterable The source iterable
     *
     * @return the first item in the given iterable
     */
    @Nullable
    public static <E> E getFirst(@Nullable Iterable<E> iterable) {
        if (iterable == null) {
            return null;
        }
        if (iterable instanceof List<E> alias) {
            return alias.get(0);
        } else if (iterable instanceof SortedSet<E> alias) {
            return alias.first();
        }
        Iterator<E> iterator = iterable.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    /**
     * Returns the last item in the given iterable
     *
     * @param iterable The source iterable
     *
     * @return the last item in the given iterable
     *
     * @see "org.springframework.util.CollectionUtils#lastElement"
     */
    @Nullable
    public static <E> E getLast(@Nullable Iterable<E> iterable) {
        if (iterable == null) {
            return null;
        }
        if (iterable instanceof List<E> alias) {
            return ListPlainWraps.getLast(alias);
        } else if (iterable instanceof SortedSet<E> alias) {
            return alias.last();
        }
        // Full iteration necessary...
        Iterator<E> iterator = iterable.iterator();
        E result = null;
        while (iterator.hasNext()) {
            result = iterator.next();
        }
        return result;
    }

    /**
     * Returns the component type inferred from the elements.
     *
     * When deepScan is false, only the first non-null element is checked.
     *
     * @param iterable The source iterable
     *
     * @return the component type, or null when it cannot be determined
     */
    @Nullable
    public static Class<?> getComponentType(@Nullable Iterable<?> iterable) {
        return getComponentType(iterable, false);
    }

    /**
     * Returns the component type inferred from the elements.
     *
     * When deepScan is false, only the first non-null element is checked.
     *
     * @param iterable The source iterable
     * @param deepScan Whether all elements should be scanned
     *
     * @return the component type, or null when it cannot be determined
     */
    @Nullable
    public static Class<?> getComponentType(@Nullable Iterable<?> iterable, boolean deepScan) {
        return (iterable == null) ? null : IteratorPlainWraps.getComponentType(iterable.iterator(), deepScan);
    }

    /**
     * Runs an action when the source contains exactly one element.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <T extends Iterable<?>> void ifSingleton(@Nullable T iterable, @Nullable Consumer<? super T> action) {
        ifSingletonOrElse(iterable, action, null);
    }

    /**
     * Runs an action when the source contains exactly one element.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <T extends Iterable<?>> void ifSingleton(@Nullable T iterable, @Nullable Runnable action) {
        ifSingletonOrElse(iterable, action, null);
    }

    /**
     * Runs one of two actions depending on whether the source is a singleton.
     *
     * @param iterable The source iterable
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Iterable<?>> void ifSingletonOrElse(@Nullable T iterable, @Nullable Consumer<? super T> presentAction, @Nullable Consumer<? super T> absentAction) {
        if (isSingleton(iterable)) {
            if (presentAction != null) {
                presentAction.accept(iterable);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(iterable);
            }
        }
    }

    /**
     * Runs one of two actions depending on whether the source is a singleton.
     *
     * @param iterable The source iterable
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Iterable<?>> void ifSingletonOrElse(@Nullable T iterable, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isSingleton(iterable)) {
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
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <T extends Iterable<?>> void ifMultitude(@Nullable T iterable, @Nullable Consumer<? super T> action) {
        if (isMultitude(iterable) && action != null) {
            action.accept(iterable);
        }
    }

    /**
     * Runs an action when the source contains more than one element.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <T extends Iterable<?>> void ifMultitude(@Nullable T iterable, @Nullable Runnable action) {
        if (isMultitude(iterable) && action != null) {
            action.run();
        }
    }

    /**
     * Runs an action when the source is empty.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static void ifEmpty(@Nullable Iterable<?> iterable, @Nullable Runnable action) {
        if (isEmpty(iterable) && action != null) {
            action.run();
        }
    }

    /**
     * Runs one of two actions depending on whether the source is empty.
     *
     * @param iterable The source iterable
     * @param absentAction The action to run when the condition is not satisfied
     * @param presentAction The action to run when the condition is satisfied
     */
    public static <E, T extends Iterable<E>> void ifEmptyOrElse(@Nullable T iterable, @Nullable Runnable absentAction, @Nullable Consumer<? super T> presentAction) {
        if (isEmpty(iterable)) {
            if (absentAction != null) {
                absentAction.run();
            }
        } else {
            if (presentAction != null) {
                presentAction.accept(iterable);
            }
        }
    }

    /**
     * Runs an action when the source is not empty.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <E, T extends Iterable<E>> void ifNotEmpty(@Nullable T iterable, @Nullable Consumer<? super T> action) {
        ifNotEmpty(iterable, action, null);
    }

    /**
     * Runs an action when the source is not empty.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     * @param filter The predicate used to select elements
     */
    public static <E, T extends Iterable<E>> void ifNotEmpty(@Nullable T iterable, @Nullable Consumer<? super T> action, @Nullable Predicate<? super T> filter) {
        if (isNotEmpty(iterable) && action != null && (filter == null || filter.test(iterable))) {
            action.accept(iterable);
        }
    }

    /**
     * Runs an action when the source is not empty.
     *
     * @param iterable The source iterable
     * @param action The action to perform
     */
    public static <E, T extends Iterable<E>> void ifNotEmpty(@Nullable T iterable, @Nullable Runnable action) {
        if (isNotEmpty(iterable) && action != null) {
            action.run();
        }
    }

    /**
     * Checks whether the source is null or empty.
     *
     * @param iterable The source iterable
     *
     * @return true when the source is empty; false otherwise
     */
    public static boolean isEmpty(@Nullable Iterable<?> iterable) {
        if (iterable == null) {
            return true;
        }
        if (iterable instanceof Collection<?> alias) {
            return alias.isEmpty();
        }
        return IteratorPlainWraps.isEmpty(iterable.iterator());
    }

    /**
     * Checks whether the source contains at least one element.
     *
     * @param iterable The source iterable
     *
     * @return true when the source is not empty; false otherwise
     */
    public static boolean isNotEmpty(@Nullable Iterable<?> iterable) {
        return !isEmpty(iterable);
    }

    /**
     * Checks whether all the given sources have the same size.
     *
     * At least two sources are required.
     *
     * @param iterables The source iterables
     *
     * @return true when all sizes are equal; false otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean isSameSize(@Nullable Iterable<?>... iterables) {
        if (ArrayUtils.getLength(iterables) < 2) {
            return false;
        }
        int size = size(ArrayUtils.get(iterables, 0));
        return Arrays.stream(iterables).skip(1L).allMatch(item -> size(item) == size);
    }

    /**
     * Checks whether the source contains exactly one element.
     *
     * @param iterable The source iterable
     *
     * @return true when the source contains exactly one element; false otherwise
     */
    public static boolean isSingleton(@Nullable Iterable<?> iterable) {
        return size(iterable) == 1;
    }

    /**
     * Checks whether the source contains more than one element.
     *
     * @param iterable The source iterable
     *
     * @return true when the source contains more than one element; false otherwise
     */
    public static boolean isMultitude(@Nullable Iterable<?> iterable) {
        return size(iterable) > 1;
    }

    /**
     * Checks whether the index is within the bounds of the source.
     *
     * @param iterable The source iterable
     * @param index The index to check
     *
     * @return true when the index is valid; false otherwise
     */
    public static boolean isIndexBound(@Nullable Iterable<?> iterable, int index) {
        return isIndexBound(iterable, index, false);
    }

    /**
     * Returns true if the index object exists in the iterable
     *
     * @param iterable The source to check
     * @param index The index to detect
     * @param adding indicates whether trying to add an item to the source iterable or not
     *
     * @return true if the index object exists in the iterable
     *
     * @see ArrayUtils#isArrayIndexValid
     */
    public static boolean isIndexBound(@Nullable Iterable<?> iterable, int index, boolean adding) {
        return index >= 0 && index < (size(iterable) + (adding ? 1 : 0));
    }

    /**
     * Returns null when the source is empty.
     *
     * @param iterable The source iterable
     *
     * @return the source, or null when it is empty
     */
    @Nullable
    public static <T extends Iterable<?>> T nullIfEmpty(@Nullable T iterable) {
        return isEmpty(iterable) ? null : iterable;
    }

    /**
     * Removes all elements that match the given predicate.
     *
     * @param iterable The source iterable
     * @param filter The predicate used to select elements
     *
     * @return true when at least one element was removed
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean removeIf(@Nullable Iterable<E> iterable, @Nullable Predicate<? super E> filter) {
        return ObjectUtils.allNotNull(iterable, filter) && IteratorPlainWraps.removeIf(iterable.iterator(), filter);
    }

    /**
     * Removes all elements whose index and value match the given predicate.
     *
     * @param iterable The source iterable
     * @param filter The predicate used to select elements
     *
     * @return true when at least one element was removed
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> boolean removeIfIndexing(@Nullable Iterable<E> iterable, @Nullable BiPredicate<Integer, ? super E> filter) {
        return ObjectUtils.allNotNull(iterable, filter) && IteratorPlainWraps.removeIfIndexing(iterable.iterator(), filter);
    }

    /**
     * Returns the number of elements in the source.
     *
     * @param iterable The source iterable
     *
     * @return the number of elements, or zero when the source is null
     */
    public static int size(@Nullable Iterable<?> iterable) {
        if (iterable == null) {
            return 0;
        }
        if (iterable instanceof Collection<?> alias) {
            return alias.size();
        }
        return IteratorPlainWraps.size(iterable.iterator());
    }

    /**
     * Returns the greatest size among the given sources.
     *
     * @param iterables The source iterables
     *
     * @return the greatest size, or zero when no sources are provided
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static int maxSize(@Nullable Iterable<?>... iterables) {
        return ArrayUtils.isEmpty(iterables) ? 0 : Arrays.stream(iterables).mapToInt(IterablePlainWraps::size).max().orElse(0);
    }

    /**
     * Returns the total size of the given sources.
     *
     * @param iterables The source iterables
     *
     * @return the combined size of all sources
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static int sumSize(@Nullable Iterable<?>... iterables) {
        return ArrayUtils.isEmpty(iterables) ? 0 : Arrays.stream(iterables).mapToInt(IterablePlainWraps::size).sum();
    }

    /**
     * Converts the source to an array of the expected element type.
     *
     * @param iterable The source iterable
     *
     * @return an array containing the elements, or null when the type is unavailable
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <E> E[] toElementArray(@Nullable Iterable<E> iterable) {
        Class<E> clazz = (Class<E>) getComponentType(iterable);
        return toElementArray(iterable, clazz);
    }

    /**
     * Converts the source to an array of the expected element type.
     *
     * @param iterable The source iterable
     * @param elementType The expected element type
     *
     * @return an array containing the elements, or null when the type is unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> E[] toElementArray(@Nullable Iterable<E> iterable, @Nullable Class<? super E> elementType) {
        return ObjectUtils.anyNull(iterable, elementType) ? null : IteratorPlainWraps.toElementArray(iterable.iterator(), elementType);
    }

    /**
     * Converts the source to an array of the expected element type.
     *
     * @param iterable The source iterable
     * @param target The source to check
     *
     * @return an array containing the elements, or null when the type is unavailable
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <E> E[] toElementArray(@Nullable Iterable<E> iterable, @Nullable E[] target) {
        return ObjectUtils.anyNull(iterable, target) ? null : IteratorPlainWraps.toElementArray(iterable.iterator(), target);
    }

    /**
     * Converts the source to an enumeration.
     *
     * @param iterable The source iterable
     *
     * @return an enumeration over the elements, or null when the source is empty
     */
    @Nullable
    public static <E> Enumeration<E> toElementEnumeration(@Nullable Iterable<E> iterable) {
        return (iterable == null) ? null : IteratorPlainWraps.toElementEnumeration(iterable.iterator());
    }

    /**
     * Converts the source to a list.
     *
     * @param iterable The source iterable
     *
     * @return a list containing the elements, or null when the source is null
     */
    @Nullable
    public static <E> List<E> toElementList(@Nullable Iterable<E> iterable) {
        return (iterable == null) ? null : IteratorPlainWraps.toElementList(iterable.iterator());
    }

    /**
     * Converts the source to a set.
     *
     * @param iterable The source iterable
     *
     * @return a set containing the elements, or null when the source is null
     */
    @Nullable
    public static <E> Set<E> toElementSet(@Nullable Iterable<E> iterable) {
        return (iterable == null) ? null : IteratorPlainWraps.toElementSet(iterable.iterator());
    }

    /**
     * Converts the source to an object array.
     *
     * @param iterable The source iterable
     *
     * @return an array containing the elements, or null when the source is null
     */
    @Nullable
    public static Object[] toObjectArray(@Nullable Iterable<?> iterable) {
        return (iterable == null) ? null : IteratorPlainWraps.toObjectArray(iterable.iterator());
    }

    /**
     * Converts the source to a list of objects.
     *
     * @param iterable The source iterable
     *
     * @return a list containing the elements, or null when the source is null
     */
    @Nullable
    public static List<?> toObjectList(@Nullable Iterable<?> iterable) {
        return (iterable == null) ? null : IteratorPlainWraps.toObjectList(iterable.iterator());
    }
}
