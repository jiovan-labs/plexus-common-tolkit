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


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.google.common.collect.Range;
import com.jiovan.plexus.common.toolkit.util.RangeMapWraps;
import com.jiovan.plexus.common.toolkit.util.StackTraceWraps;
import lombok.extern.slf4j.Slf4j;


/**
 * Tests for {@link com.jiovan.plexus.common.toolkit.util.RangeMapWraps}
 *
 * @author David Hsing
 */
@Slf4j
class RangeMapWrapsTest {
    @Test
    @SuppressWarnings("NullableProblems")
    void anyOverlap() {
        Range<Integer> range1 = Range.closed(1, 5);
        Range<Integer> range2 = Range.closed(4, 8);
        Range<Integer> range3 = Range.closed(10, 15);
        boolean result = RangeMapWraps.anyOverlap(range1, range2, range3);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertTrue(result);
    }
}
