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

package com.jiovan.plexus.common.toolkit.xml;


import java.io.ByteArrayInputStream;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;


/**
 * {@link org.xml.sax.EntityResolver} with empty stream
 *
 * @author David Hsing
 *
 * @see "http://www.jdom.org/docs/faq.html#a0350"
 */
@SuppressWarnings("unused")
public class EmptyEntityResolver implements EntityResolver {
    /**
     * Resolves an entity to an empty input source.
     *
     * @param publicId The public identifier of the entity
     * @param systemId The system identifier of the entity
     *
     * @return an empty input source
     */
    @Override
    public InputSource resolveEntity(@Nullable String publicId, @Nullable String systemId) {
        return new InputSource(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY));
    }
}
