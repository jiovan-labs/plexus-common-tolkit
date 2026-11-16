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
import com.jiovan.plexus.common.toolkit.util.SqlParserWraps;
import com.jiovan.plexus.common.toolkit.util.StackTraceWraps;
import lombok.extern.slf4j.Slf4j;


/**
 * Tests for {@link com.jiovan.plexus.common.toolkit.util.SqlParserWraps}
 *
 * @author David Hsing
 */
@Slf4j
class SqlParserWrapsTest {
    @Test
    void parseCommentSql() {
        boolean result = SqlParserWraps.isStatementParsable("select 'X' from table_name where '1' = '1' -- or 2 = 2");    // $NON-NLS-1$
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertTrue(result);
    }

    @Test
    void parseInsertSql() {
        boolean result = SqlParserWraps.isStatementParsable("insert into table_name values (NULL);");    // $NON-NLS-1$
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertTrue(result);
    }

    @Test
    void parseNonInsertSql() {
        boolean result = SqlParserWraps.isStatementParsable("insert username --");    // $NON-NLS-1$
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertFalse(result);
    }

    @Test
    void parseSelectSql() {
        boolean result = SqlParserWraps.isStatementParsable("select username from table_name   ");    // $NON-NLS-1$
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertTrue(result);
    }

    @Test
    void parseNonSelectSql1() {
        boolean result = SqlParserWraps.isStatementParsable("select");    // $NON-NLS-1$
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertFalse(result);
    }

    @Test
    void parseNonSelectSql2() {
        boolean result = SqlParserWraps.isStatementParsable("please select your username");    // $NON-NLS-1$
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertFalse(result);
    }
}
