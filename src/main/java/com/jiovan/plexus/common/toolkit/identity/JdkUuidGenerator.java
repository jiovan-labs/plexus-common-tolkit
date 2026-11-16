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


import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;
import java.util.UUID;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;


/**
 * UUID generator by {@link java.util.UUID}
 *
 * @author David Hsing
 *
 * @see "org.springframework.util.JdkIdGenerator"
 */
@SuppressWarnings("unused")
public abstract class JdkUuidGenerator {
    /**
     * Returns an uuid string with uppercase and none hyphens
     *
     * @return an uuid string with uppercase and none hyphens
     */
    @Nonnull
    public static String getCapitalId() {
        return getRandomId(true, false);
    }

    /**
     * Returns an uuid string with lowercase and none hyphens
     *
     * @return an uuid string with lowercase and none hyphens
     */
    @Nonnull
    public static String getPopularId() {
        return getRandomId(false, false);
    }

    /**
     * Returns a random UUID string.
     *
     * @return a randomly generated UUID string
     */
    @Nonnull
    public static String getRandomId() {
        return getRandomId(false, true);
    }

    /**
     * Returns a random UUID string.
     *
     * @param uppercase Whether the UUID should use uppercase letters
     *
     * @return a randomly generated UUID string
     */
    @Nonnull
    public static String getRandomId(boolean uppercase) {
        return getRandomId(uppercase, true);
    }

    /**
     * Returns a random UUID string.
     *
     * @param uppercase Whether the UUID should use uppercase letters
     * @param hyphen Whether the UUID should contain hyphens
     *
     * @return a randomly generated UUID string
     */
    @Nonnull
    public static String getRandomId(boolean uppercase, boolean hyphen) {
        String result = UUID.randomUUID().toString();
        result = uppercase ? StringUtils.upperCase(result) : StringUtils.lowerCase(result);
        return hyphen ? result : StringUtilsWraps.remove(result, CharVariantConst.HYPHEN);
    }
}
