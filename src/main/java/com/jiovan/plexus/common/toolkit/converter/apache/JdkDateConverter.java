/*
 * Copyright (c) 2025 Jiovan Ltd. All rights reserved.
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

package com.jiovan.plexus.common.toolkit.converter.apache;


import java.util.Date;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.beanutils2.converters.DateTimeConverter;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatCombo;
import com.jiovan.plexus.common.toolkit.util.JdkDateWraps;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Converts an object to {@link java.util.Date}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.beanutils2.Converter
 * @see org.apache.commons.beanutils2.converters.DateConverter
 */
@NoArgsConstructor
@Getter
@Setter
@SuppressWarnings("unused")
public class JdkDateConverter extends DateTimeConverter<Date> {
    private String[] formats = TemporalFormatCombo.ALL_DATETIME_DATES;

    /**
     * Creates a converter with the given default value.
     *
     * @param defaultValue the value returned when conversion fails, or null when absent
     */
    public JdkDateConverter(@Nullable Date defaultValue) {
        super(defaultValue);
    }

    /**
     * Converts the given value to the requested date type. Tries the configured date-time patterns to parse the value.
     *
     * @param type the target type, which must be assignable from Date
     * @param value the value to convert, or null when absent
     * @return the converted value, or null when the value is null
     * @throws Exception when the value cannot be converted
     */
    @Override
    protected <T> T convertToType(@Nonnull Class<T> type, @Nullable Object value) throws Exception {
        if (value == null) {
            return null;
        }
        if (value instanceof String alias) {
            for (String pattern : formats) {
                T result = type.cast(JdkDateWraps.parseDateQuietly(alias, pattern));
                if (result != null) {
                    return result;
                }
            }
        }
        return super.convertToType(type, value);
    }

    /**
     * Returns the default target type of this converter.
     *
     * @return the Date class
     */
    @Override
    protected Class<Date> getDefaultType() {
        return Date.class;
    }
}
