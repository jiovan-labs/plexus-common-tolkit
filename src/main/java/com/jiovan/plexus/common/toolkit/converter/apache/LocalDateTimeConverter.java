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


import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.beanutils2.converters.DateTimeConverter;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatCombo;
import com.jiovan.plexus.common.toolkit.util.LocalDateWraps;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Converts an object to {@link java.time.LocalDateTime}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.beanutils2.Converter
 * @see org.apache.commons.beanutils2.converters.LocalDateTimeConverter
 */
@NoArgsConstructor
@Getter
@Setter
@SuppressWarnings("unused")
public class LocalDateTimeConverter extends DateTimeConverter<LocalDateTime> {
    private String[] formats = TemporalFormatCombo.DATETIME_FORMATS;
    private String[] spares = TemporalFormatCombo.DATE_FORMATS;

    /**
     * Creates a converter with the given default value.
     *
     * @param defaultValue the value returned when conversion fails, or null when absent
     */
    public LocalDateTimeConverter(@Nullable LocalDateTime defaultValue) {
        super(defaultValue);
    }

    /**
     * Converts the given value to the requested date-time type. Tries the configured date-time patterns, then the date patterns, and extends the date to midnight.
     *
     * @param type the target type, which must be assignable from LocalDateTime
     * @param value the value to convert, or null when absent
     *
     * @return the converted value, or null when the value is null
     *
     * @throws Exception when the value cannot be converted
     */
    @Override
    protected <T> T convertToType(@Nonnull Class<T> type, @Nullable Object value) throws Exception {
        if (value == null) {
            return null;
        }
        if (value instanceof String alias) {
            for (String pattern : formats) {
                T result = type.cast(LocalDateWraps.parseDateTimeQuietly(alias, pattern));
                if (result != null) {
                    return result;
                }
            }
            for (String pattern : spares) {
                LocalDate date = LocalDateWraps.parseDateQuietly(alias, pattern);
                if (date != null) {
                    return type.cast(LocalDateWraps.toLocalDateTime(date));
                }
            }
        }
        return super.convertToType(type, value);
    }

    /**
     * Returns the default target type of this converter.
     *
     * @return the LocalDateTime class
     */
    @Override
    protected Class<LocalDateTime> getDefaultType() {
        return LocalDateTime.class;
    }
}
