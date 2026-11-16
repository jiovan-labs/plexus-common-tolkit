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

package com.jiovan.plexus.common.toolkit.identity;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;


/**
 * Nano id generator by {@link java.util.UUID}
 *
 * @author David Hsing
 *
 * @see "org.springframework.util.JdkIdGenerator"
 */
@SuppressWarnings("unused")
public abstract class NanoIdGenerator {
    private static final String ALPHABETIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";    // $NON-NLS-1$
    private static final String NUMERIC = "0123456789";    // $NON-NLS-1$

    private static final char[] POPULAR_CHARS = "346789ABCDEFGHJKMNPQRTWXYabcdefghjkmnpqrtwxy".toCharArray();    // $NON-NLS-1$
    private static final char[] ALPHABETIC_UPPERS = ALPHABETIC.toCharArray();
    private static final char[] ALPHABETIC_LOWERS = ALPHABETIC.toLowerCase().toCharArray();
    private static final char[] ALPHABETIC_MIXINS = (ALPHABETIC + ALPHABETIC.toLowerCase()).toCharArray();
    private static final char[] NUMERIC_CHARS = NUMERIC.toCharArray();
    private static final char[] ALPHANUMERIC_UPPERS = (ALPHABETIC + NUMERIC).toCharArray();
    private static final char[] ALPHANUMERIC_LOWERS = (ALPHABETIC.toLowerCase() + NUMERIC).toCharArray();
    private static final char[] ALPHANUMERIC_MIXINS = (ALPHABETIC + ALPHABETIC.toLowerCase() + NUMERIC).toCharArray();

    /**
     * Returns a random Nano ID using the popular alphabet.
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getPopularId() {
        return getPopularId(-1);
    }

    /**
     * Returns a random Nano ID using the popular alphabet.
     *
     * @param size The requested ID length
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getPopularId(int size) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, POPULAR_CHARS, (size > 0) ? size : NanoIdUtils.DEFAULT_SIZE);
    }

    /**
     * Returns a random Nano ID using alphabetic characters.
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphabeticId() {
        return getAlphabeticId(-1);
    }

    /**
     * Returns a random Nano ID using alphabetic characters.
     *
     * @param size The requested ID length
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphabeticId(int size) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHABETIC_MIXINS, (size > 0) ? size : NanoIdUtils.DEFAULT_SIZE);
    }

    /**
     * Returns a random Nano ID using lowercase alphabetic characters.
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphabeticLowerId() {
        return getAlphabeticLowerId(-1);
    }

    /**
     * Returns a random Nano ID using lowercase alphabetic characters.
     *
     * @param size The requested ID length
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphabeticLowerId(int size) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHABETIC_LOWERS, (size > 0) ? size : NanoIdUtils.DEFAULT_SIZE);
    }

    /**
     * Returns a random Nano ID using uppercase alphabetic characters.
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphabeticUpperId() {
        return getAlphabeticUpperId(-1);
    }

    /**
     * Returns a random Nano ID using uppercase alphabetic characters.
     *
     * @param size The requested ID length
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphabeticUpperId(int size) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHABETIC_UPPERS, (size > 0) ? size : NanoIdUtils.DEFAULT_SIZE);
    }

    /**
     * Returns a random Nano ID using mixed-case alphanumeric characters.
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphanumericId() {
        return getAlphanumericId(-1);
    }

    /**
     * Returns a random Nano ID using mixed-case alphanumeric characters.
     *
     * @param size The requested ID length
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphanumericId(int size) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHANUMERIC_MIXINS, (size > 0) ? size : NanoIdUtils.DEFAULT_SIZE);
    }

    /**
     * Returns a random Nano ID using lowercase alphanumeric characters.
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphanumericLowerId() {
        return getAlphanumericLowerId(-1);
    }

    /**
     * Returns a random Nano ID using lowercase alphanumeric characters.
     *
     * @param size The requested ID length
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphanumericLowerId(int size) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHANUMERIC_LOWERS, (size > 0) ? size : NanoIdUtils.DEFAULT_SIZE);
    }

    /**
     * Returns a random Nano ID using uppercase alphanumeric characters.
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphanumericUpperId() {
        return getAlphanumericUpperId(-1);
    }

    /**
     * Returns a random Nano ID using uppercase alphanumeric characters.
     *
     * @param size The requested ID length
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getAlphanumericUpperId(int size) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHANUMERIC_UPPERS, (size > 0) ? size : NanoIdUtils.DEFAULT_SIZE);
    }

    /**
     * Returns a random Nano ID using numeric characters.
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getNumericId() {
        return getNumericId(-1);
    }

    /**
     * Returns a random Nano ID using numeric characters.
     *
     * @param size The requested ID length
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getNumericId(int size) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NUMERIC_CHARS, (size > 0) ? size : NanoIdUtils.DEFAULT_SIZE);
    }

    /**
     * Returns a random Nano ID.
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getRandomId() {
        return NanoIdUtils.randomNanoId();
    }

    /**
     * Returns a random Nano ID.
     *
     * @param size The requested ID length
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getRandomId(int size) {
        return getRandomId(size, null);
    }

    /**
     * Returns a random Nano ID.
     *
     * @param size The requested ID length
     * @param alphabets The characters allowed in the generated ID
     *
     * @return a randomly generated ID
     */
    @Nonnull
    public static String getRandomId(int size, @Nullable char[] alphabets) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ((alphabets == null || alphabets.length == 0) ? NanoIdUtils.DEFAULT_ALPHABET : alphabets), (size > 0) ? size : NanoIdUtils.DEFAULT_SIZE);
    }
}
