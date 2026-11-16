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
import jakarta.annotation.Nullable;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Structure with an int, a generic object, and a string list
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
public class StatusDataStruct<T> extends DataTextStruct<T> {
    private Integer status;

    /**
     * Creates a structure with the given status and data.
     *
     * @param status the status code, or null when absent
     * @param data the data payload, or null when absent
     */
    public StatusDataStruct(@Nullable Integer status, @Nullable T data) {
        this.status = status;
        super.setData(data);
    }

    /**
     * Creates a structure with the given status, data, and text messages.
     *
     * @param status the status code, or null when absent
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     */
    public StatusDataStruct(@Nullable Integer status, @Nullable T data, @Nullable String... texts) {
        this.status = status;
        super.setData(data);
        super.addText(texts);
    }

    /**
     * Creates a structure with the given status, data, and text messages.
     *
     * @param status the status code, or null when absent
     * @param data the data payload, or null when absent
     * @param texts the text messages, or null when absent
     */
    public StatusDataStruct(@Nullable Integer status, @Nullable T data, @Nullable Collection<String> texts) {
        this.status = status;
        super.setData(data);
        super.addText(texts);
    }
}
