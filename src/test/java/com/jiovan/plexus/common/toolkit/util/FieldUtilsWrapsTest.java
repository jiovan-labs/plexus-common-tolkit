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


import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.jiovan.plexus.common.toolkit.structure.StatusDataStruct;
import com.jiovan.plexus.common.toolkit.util.FieldUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.StackTraceWraps;
import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;
import lombok.extern.slf4j.Slf4j;


/**
 * Tests for {@link com.jiovan.plexus.common.toolkit.util.FieldUtilsWraps}
 *
 * @author David Hsing
 */
@Slf4j
class FieldUtilsWrapsTest {
    @Test
    void declaredFieldNames() {
        Set<String> result = FieldUtilsWraps.getDeclaredFieldNamesToSet(StatusDataStruct.class);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), StringUtilsWraps.joinWithCommaSpace(result));
        Assertions.assertNotNull(result);
    }

    @Test
    void nestedFieldNames() {
        Set<String> result = FieldUtilsWraps.getNestedFieldNamesToSet(StatusDataStruct.class);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), StringUtilsWraps.joinWithCommaSpace(result));
        Assertions.assertNotNull(result);
    }
}
