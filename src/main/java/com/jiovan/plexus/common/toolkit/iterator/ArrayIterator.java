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

package com.jiovan.plexus.common.toolkit.iterator;


import java.util.Iterator;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * {@link java.util.Iterator} for array
 *
 * @see "com.fasterxml.jackson.databind.util.ArrayIterator"
 */
@RequiredArgsConstructor
@Getter
@SuppressWarnings("unused")
public class ArrayIterator<E> implements Iterator<E> {
    private final E[] array;
    private int index = 0;

    /**
     * Checks whether the iteration has more elements.
     *
     * @return {@code true} when another element is available; {@code false} otherwise
     */
    public boolean hasNext() {
        return index < array.length;
    }

    /**
     * Returns the next element of the iteration.
     *
     * @return the next element
     */
    public E next() {
        if (index < 0 || index >= array.length) {
            throw new NoSuchElementException("Array contains no more elements");    // $NON-NLS-1$
        }
        return array[index++];
    }

    /**
     * Removes the last element returned by the iterator.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
