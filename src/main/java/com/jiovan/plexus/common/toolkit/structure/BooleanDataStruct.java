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


import java.util.Collection;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Structure with a boolean, a generic object, and a string list
 *
 * @param <T> the type of the object inside
 *
 * @author David Hsing
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("unused")
public class BooleanDataStruct<T> extends DataTextStruct<T> {
    private boolean success = false;

    /**
     * Creates a structure with the given success flag, data, and text messages.
     *
     * @param success the success flag
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     */
    public BooleanDataStruct(boolean success, @Nullable T data, @Nullable String... texts) {
        this.success = success;
        super.setData(data);
        super.addText(texts);
    }

    /**
     * Creates a structure with the given success flag, data, and text messages.
     *
     * @param success the success flag
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     */
    public BooleanDataStruct(boolean success, @Nullable T data, @Nullable Collection<String> texts) {
        this.success = success;
        super.setData(data);
        super.addText(texts);
    }

    /**
     * Creates a structure with the given success flag, data, and text messages.
     *
     * @param success the success flag
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     * @return a structure with the given success flag, data, and text messages
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> of(boolean success, @Nullable T data, @Nullable String... texts) {
        return new BooleanDataStruct<>(success, data, texts);
    }

    /**
     * Creates a structure with the given success flag, data, and text messages.
     *
     * @param success the success flag
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     * @return a structure with the given success flag, data, and text messages
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> of(boolean success, @Nullable T data, @Nullable Collection<String> texts) {
        return new BooleanDataStruct<>(success, data, texts);
    }

    /**
     * Creates a successful structure without data or text messages.
     *
     * @return a successful structure
     */
    @Nonnull
    public static BooleanDataStruct<?> ofSuccess() {
        return new BooleanDataStruct<>(true);
    }

    /**
     * Creates a successful structure with the given text messages.
     *
     * @param texts the text messages, or null when absent
     * @return a successful structure with the given text messages
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> ofSuccess(@Nullable String... texts) {
        return new BooleanDataStruct<>(true, null, texts);
    }

    /**
     * Creates a successful structure with the given text messages.
     *
     * @param texts the text messages, or null when absent
     * @return a successful structure with the given text messages
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> ofSuccess(@Nullable Collection<String> texts) {
        return new BooleanDataStruct<>(true, null, texts);
    }

    /**
     * Creates a successful structure containing the given data.
     *
     * @param data the data payload, or null when absent
     * @return a successful structure containing the given data
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> ofSuccessWithin(@Nullable T data) {
        return new BooleanDataStruct<>(true, data);
    }

    /**
     * Creates a successful structure containing the given data and text messages.
     *
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     * @return a successful structure containing the given data and text messages
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> ofSuccessWithin(@Nullable T data, @Nullable String... texts) {
        return new BooleanDataStruct<>(true, data, texts);
    }

    /**
     * Creates a successful structure containing the given data and text messages.
     *
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     * @return a successful structure containing the given data and text messages
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> ofSuccessWithin(@Nullable T data, @Nullable Collection<String> texts) {
        return new BooleanDataStruct<>(true, data, texts);
    }

    /**
     * Creates a failed structure without data or text messages.
     *
     * @return a failed structure
     */
    @Nonnull
    public static BooleanDataStruct<?> ofFailure() {
        return new BooleanDataStruct<>(false);
    }

    /**
     * Creates a failed structure with the given text messages.
     *
     * @param texts the text messages, or null when absent
     * @return a failed structure with the given text messages
     */
    @Nonnull
    public static BooleanDataStruct<?> ofFailure(@Nullable String... texts) {
        return new BooleanDataStruct<>(false, null, texts);
    }

    /**
     * Creates a failed structure with the given text messages.
     *
     * @param texts the text messages, or null when absent
     * @return a failed structure with the given text messages
     */
    @Nonnull
    public static BooleanDataStruct<?> ofFailure(@Nullable Collection<String> texts) {
        return new BooleanDataStruct<>(false, null, texts);
    }

    /**
     * Creates a failed structure containing the given data.
     *
     * @param data the data payload, or null when absent
     * @return a failed structure containing the given data
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> ofFailureWithin(@Nullable T data) {
        return new BooleanDataStruct<>(false, data);
    }

    /**
     * Creates a failed structure containing the given data and text messages.
     *
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     * @return a failed structure containing the given data and text messages
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> ofFailureWithin(@Nullable T data, @Nullable String... texts) {
        return new BooleanDataStruct<>(false, data, texts);
    }

    /**
     * Creates a failed structure containing the given data and text messages.
     *
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     * @return a failed structure containing the given data and text messages
     */
    @Nonnull
    public static <T> BooleanDataStruct<T> ofFailureWithin(@Nullable T data, @Nullable Collection<String> texts) {
        return new BooleanDataStruct<>(false, data, texts);
    }
}
