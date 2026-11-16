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


import java.util.Map;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import net.objecthunter.exp4j.ExpressionBuilder;


/**
 * Utilities for `exp4j` math
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class Exp4jMathWraps {
    /**
     * Evaluates the given mathematical expression.
     *
     * Variables are replaced from the given map before evaluation.
     *
     * @param math The mathematical expression
     *
     * @return the evaluation result, or null when the expression is blank
     *
     * @throws java.lang.IllegalArgumentException if the expression is invalid
     */
    @Nullable
    public static Double evalMathExpress(@Nullable String math) throws IllegalArgumentException {
        return evalMathExpress(math, null);
    }

    /**
     * Evaluates the given mathematical expression.
     *
     * Variables are replaced from the given map before evaluation.
     *
     * @param math The mathematical expression
     * @param variables The variables used in the expression
     *
     * @return the evaluation result, or null when the expression is blank
     *
     * @throws java.lang.IllegalArgumentException if the expression is invalid
     */
    @Nullable
    public static Double evalMathExpress(@Nullable String math, @Nullable Map<String, Object> variables) throws IllegalArgumentException {
        if (StringUtils.isBlank(math)) {
            return null;
        }
        String alias = math;
        if (MapPlainWraps.isNotEmpty(variables)) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                if (StringUtils.isBlank(entry.getKey())) {
                    continue;
                }
                alias = alias.replace(entry.getKey(), ObjectUtilsWraps.toString(entry.getValue(), StringUtils.EMPTY));
            }
        }
        return new ExpressionBuilder(alias).build().evaluate();
    }

    /**
     * Evaluates the given mathematical expression without throwing exceptions.
     *
     * Any evaluation error results in null.
     *
     * @param math The mathematical expression
     *
     * @return the evaluation result, or null when the expression cannot be evaluated
     */
    @Nullable
    public static Double evalMathExpressQuietly(@Nullable String math) {
        return evalMathExpressQuietly(math, null);
    }

    /**
     * Evaluates the given mathematical expression without throwing exceptions.
     *
     * Any evaluation error results in null.
     *
     * @param math The mathematical expression
     * @param variables The variables used in the expression
     *
     * @return the evaluation result, or null when the expression cannot be evaluated
     */
    @Nullable
    public static Double evalMathExpressQuietly(@Nullable String math, @Nullable Map<String, Object> variables) {
        try {
            return evalMathExpress(math, variables);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Checks whether the given mathematical expression can be evaluated.
     *
     * @param math The mathematical expression
     *
     * @return true when the expression is valid; false otherwise
     */
    public static boolean testMathExpress(@Nullable String math) {
        return testMathExpress(math, null);
    }

    /**
     * Checks whether the given mathematical expression can be evaluated.
     *
     * @param math The mathematical expression
     * @param variables The variables used in the expression
     *
     * @return true when the expression is valid; false otherwise
     */
    public static boolean testMathExpress(@Nullable String math, @Nullable Map<String, Object> variables) {
        try {
            return evalMathExpress(math, variables) != null;
        } catch (Exception ignored) {
        }
        return false;
    }
}
