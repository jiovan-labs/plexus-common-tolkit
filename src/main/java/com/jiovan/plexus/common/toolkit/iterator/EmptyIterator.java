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


/**
 * {@link java.util.Iterator} for empty iterator
 *
 * @see org.apache.commons.collections4.iterators.EmptyIterator
 */
@SuppressWarnings("unused")
public class EmptyIterator<E> implements Iterator<E> {
    /**
     * Checks whether the iteration has more elements.
     *
     * @return {@code true} when another element is available; {@code false} otherwise
     */
    @Override
    public boolean hasNext() {
        return false;
    }

    /**
     * Returns the next element of the iteration.
     *
     * @return the next element
     */
    @Override
    public E next() {
        throw new NoSuchElementException("Iterator contains no elements");    // $NON-NLS-1$
    }

    /**
     * Removes the last element returned by the iterator.
     */
    @Override
    public void remove() {
        throw new IllegalStateException("Iterator contains no elements");    // $NON-NLS-1$
    }
}
