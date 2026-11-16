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


import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.function.Consumer;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.math.NumberUtils;
import com.jiovan.plexus.common.toolkit.constant.SymbolVariantConst;
import com.jiovan.plexus.common.toolkit.exception.UnsupportedClassException;


/**
 * Utilities for {@link org.apache.commons.lang3.math.NumberUtils}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.lang3.math.NumberUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class NumberUtilsWraps {
    /**
     * Checks whether all the given values are negative.
     *
     * @param values The values
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> boolean allNegative(@Nullable T... values) {
        return ArrayUtils.isNotEmpty(values) && Arrays.stream(values).allMatch(NumberUtilsWraps::isNegative);
    }

    /**
     * Checks whether all the given values are not negative.
     *
     * @param values The values
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> boolean allNotNegative(@Nullable T... values) {
        return ArrayUtils.isNotEmpty(values) && Arrays.stream(values).noneMatch(NumberUtilsWraps::isNegative);
    }

    /**
     * Checks whether any of the given values is negative.
     *
     * @param values The values
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> boolean anyNegative(@Nullable T... values) {
        return ArrayUtils.isNotEmpty(values) && Arrays.stream(values).anyMatch(NumberUtilsWraps::isNegative);
    }

    /**
     * Checks whether any of the given values is not negative.
     *
     * @param values The values
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> boolean anyNotNegative(@Nullable T... values) {
        return ArrayUtils.isNotEmpty(values) && Arrays.stream(values).anyMatch(NumberUtilsWraps::isNotNegative);
    }

    /**
     * Checks whether all the given values are positive.
     *
     * @param values The values
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> boolean allPositive(@Nullable T... values) {
        return ArrayUtils.isNotEmpty(values) && Arrays.stream(values).allMatch(NumberUtilsWraps::isPositive);
    }

    /**
     * Checks whether all the given values are not positive.
     *
     * @param values The values
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> boolean allNotPositive(@Nullable T... values) {
        return ArrayUtils.isNotEmpty(values) && Arrays.stream(values).noneMatch(NumberUtilsWraps::isPositive);
    }

    /**
     * Checks whether any of the given values is positive.
     *
     * @param values The values
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> boolean anyPositive(@Nullable T... values) {
        return ArrayUtils.isNotEmpty(values) && Arrays.stream(values).anyMatch(NumberUtilsWraps::isPositive);
    }

    /**
     * Checks whether any of the given values is not positive.
     *
     * @param values The values
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> boolean anyNotPositive(@Nullable T... values) {
        return ArrayUtils.isNotEmpty(values) && Arrays.stream(values).anyMatch(NumberUtilsWraps::isNotPositive);
    }

    /**
     * @see org.apache.commons.lang3.Range
     *
     * @see "https://cloud.tencent.com/developer/ask/44072"
     */
    public static <T extends Number & Comparable<? super T>> boolean between(@Nullable T value, @Nullable T startValue, @Nullable T endValue, boolean includeStart, boolean includeEnd) {
        if (value == null) {
            return (startValue == null && includeStart) || (endValue == null && includeEnd);
        }
        int compareStart = ObjectUtils.compare(value, startValue), compareEnd = ObjectUtils.compare(value, endValue);
        return (includeStart ? compareStart >= 0 : compareStart > 0) && (includeEnd ? compareEnd <= 0 : compareEnd < 0);
    }

    /**
     * Returns the first value between the start and end values.
     *
     * @param startValue The inclusive or exclusive start value
     * @param endValue The inclusive or exclusive end value
     * @param values The values
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static <T extends Number & Comparable<? super T>> T firstBetween(@Nullable T startValue, @Nullable T endValue, @Nullable T... values) {
        return firstBetween(startValue, endValue, false, false, values);
    }

    /**
     * Returns the first value between the start and end values.
     *
     * @param startValue The inclusive or exclusive start value
     * @param endValue The inclusive or exclusive end value
     * @param includeStart Whether the start value is included
     * @param includeEnd Whether the end value is included
     * @param values The values
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? super T>> T firstBetween(@Nullable T startValue, @Nullable T endValue, boolean includeStart, boolean includeEnd, @Nullable T... values) {
        if (ArrayUtils.isEmpty(values)) {
            return null;
        }
        return Arrays.stream(values).filter(value -> NumberUtilsWraps.between(value, startValue, endValue, includeStart, includeEnd)).findFirst().orElse(null);
    }

    /**
     * Returns the first negative value.
     *
     * @param values The values
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> T firstNegative(@Nullable T... values) {
        if (ArrayUtils.isEmpty(values)) {
            return null;
        }
        return Arrays.stream(values).filter(NumberUtilsWraps::isNegative).findFirst().orElse(null);
    }

    /**
     * Returns the first positive value.
     *
     * @param values The values
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number & Comparable<? extends Number>> T firstPositive(@Nullable T... values) {
        if (ArrayUtils.isEmpty(values)) {
            return null;
        }
        return Arrays.stream(values).filter(NumberUtilsWraps::isPositive).findFirst().orElse(null);
    }

    /**
     * Runs an action when the value is negative.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifNegative(@Nullable T value, @Nullable Consumer<? super T> action) {
        ifNegativeOrElse(value, action, null);
    }

    /**
     * Runs an action when the value is negative.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifNegative(@Nullable T value, @Nullable Runnable action) {
        ifNegativeOrElse(value, action, null);
    }

    /**
     * Runs one of two actions depending on whether the value is negative.
     *
     * @param value The value
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Number & Comparable<? extends Number>> void ifNegativeOrElse(@Nullable T value, @Nullable Consumer<? super T> presentAction, @Nullable Consumer<? super T> absentAction) {
        if (isNegative(value)) {
            if (presentAction != null) {
                presentAction.accept(value);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(value);
            }
        }
    }

    /**
     * Runs one of two actions depending on whether the value is negative.
     *
     * @param value The value
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Number & Comparable<? extends Number>> void ifNegativeOrElse(@Nullable T value, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isNegative(value)) {
            if (presentAction != null) {
                presentAction.run();
            }
        } else {
            if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    /**
     * Runs an action when the value is not negative.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifNotNegative(@Nullable T value, @Nullable Consumer<? super T> action) {
        if (isNotNegative(value) && action != null) {
            action.accept(value);
        }
    }

    /**
     * Runs an action when the value is not negative.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifNotNegative(@Nullable T value, @Nullable Runnable action) {
        if (isNotNegative(value) && action != null) {
            action.run();
        }
    }

    /**
     * Runs an action when the value is positive.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifPositive(@Nullable T value, @Nullable Consumer<? super T> action) {
        ifPositiveOrElse(value, action, null);
    }

    /**
     * Runs an action when the value is positive.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifPositive(@Nullable T value, @Nullable Runnable action) {
        ifPositiveOrElse(value, action, null);
    }

    /**
     * Runs one of two actions depending on whether the value is positive.
     *
     * @param value The value
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Number & Comparable<? extends Number>> void ifPositiveOrElse(@Nullable T value, @Nullable Consumer<? super T> presentAction, @Nullable Consumer<? super T> absentAction) {
        if (isPositive(value)) {
            if (presentAction != null) {
                presentAction.accept(value);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(value);
            }
        }
    }

    /**
     * Runs one of two actions depending on whether the value is positive.
     *
     * @param value The value
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Number & Comparable<? extends Number>> void ifPositiveOrElse(@Nullable T value, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isPositive(value)) {
            if (presentAction != null) {
                presentAction.run();
            }
        } else {
            if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    /**
     * Runs an action when the value is not positive.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifNotPositive(@Nullable T value, @Nullable Consumer<? super T> action) {
        if (isNotPositive(value) && action != null) {
            action.accept(value);
        }
    }

    /**
     * Runs an action when the value is not positive.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifNotPositive(@Nullable T value, @Nullable Runnable action) {
        if (isNotPositive(value) && action != null) {
            action.run();
        }
    }

    /**
     * Runs an action when the value is zero.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifZero(@Nullable T value, @Nullable Consumer<? super T> action) {
        ifZeroOrElse(value, action, null);
    }

    /**
     * Runs an action when the value is zero.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifZero(@Nullable T value, @Nullable Runnable action) {
        ifZeroOrElse(value, action, null);
    }

    /**
     * Runs one of two actions depending on whether the value is zero.
     *
     * @param value The value
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Number & Comparable<? extends Number>> void ifZeroOrElse(@Nullable T value, @Nullable Consumer<? super T> presentAction, @Nullable Consumer<? super T> absentAction) {
        if (isZero(value)) {
            if (presentAction != null) {
                presentAction.accept(value);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(value);
            }
        }
    }

    /**
     * Runs one of two actions depending on whether the value is zero.
     *
     * @param value The value
     * @param presentAction The action to run when the condition is satisfied
     * @param absentAction The action to run when the condition is not satisfied
     */
    public static <T extends Number & Comparable<? extends Number>> void ifZeroOrElse(@Nullable T value, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isZero(value)) {
            if (presentAction != null) {
                presentAction.run();
            }
        } else {
            if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    /**
     * Runs an action when the value is zero or negative.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifZeroOrNegative(@Nullable T value, @Nullable Consumer<? super T> action) {
        if (isZeroOrNegative(value) && action != null) {
            action.accept(value);
        }
    }

    /**
     * Runs an action when the value is zero or negative.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifZeroOrNegative(@Nullable T value, @Nullable Runnable action) {
        if (isZeroOrNegative(value) && action != null) {
            action.run();
        }
    }

    /**
     * Runs an action when the value is zero or positive.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifZeroOrPositive(@Nullable T value, @Nullable Consumer<? super T> action) {
        if (isZeroOrPositive(value) && action != null) {
            action.accept(value);
        }
    }

    /**
     * Runs an action when the value is zero or positive.
     *
     * @param value The value
     * @param action The action to perform
     */
    public static <T extends Number & Comparable<? extends Number>> void ifZeroOrPositive(@Nullable T value, @Nullable Runnable action) {
        if (isZeroOrPositive(value) && action != null) {
            action.run();
        }
    }

    /**
     * @see org.apache.commons.lang3.builder.CompareToBuilder
     */
    public static <T extends Number & Comparable<? extends Number>> boolean isNegative(@Nullable T value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Integer alias) {
            return ObjectUtils.compare(alias, NumberUtils.INTEGER_ZERO) < 0;
        } else if (value instanceof Long alias) {
            return ObjectUtils.compare(alias, NumberUtils.LONG_ZERO) < 0;
        } else if (value instanceof Short alias) {
            return ObjectUtils.compare(alias, NumberUtils.SHORT_ZERO) < 0;
        } else if (value instanceof Byte alias) {
            return ObjectUtils.compare(alias, NumberUtils.BYTE_ZERO) < 0;
        } else if (value instanceof Double alias) {
            return ObjectUtils.compare(alias, NumberUtils.DOUBLE_ZERO) < 0;
        } else if (value instanceof Float alias) {
            return ObjectUtils.compare(alias, NumberUtils.FLOAT_ZERO) < 0;
        } else if (value instanceof Fraction alias) {
            return ObjectUtils.compare(alias.floatValue(), NumberUtils.FLOAT_ZERO) < 0;
        } else if (value instanceof BigDecimal alias) {
            return ObjectUtils.compare(alias, BigDecimal.ZERO) < 0;
        } else if (value instanceof BigInteger alias) {
            return ObjectUtils.compare(alias, BigInteger.ZERO) < 0;
        }
        return value.longValue() < 0L;
    }

    /**
     * Checks whether the value is not negative.
     *
     * @param value The value
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static <T extends Number & Comparable<? extends Number>> boolean isNotNegative(@Nullable T value) {
        return !isNegative(value);
    }

    /**
     * @see org.apache.commons.lang3.builder.CompareToBuilder
     */
    public static <T extends Number & Comparable<? extends Number>> boolean isPositive(@Nullable T value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Integer alias) {
            return ObjectUtils.compare(alias, NumberUtils.INTEGER_ZERO) > 0;
        } else if (value instanceof Long alias) {
            return ObjectUtils.compare(alias, NumberUtils.LONG_ZERO) > 0;
        } else if (value instanceof Short alias) {
            return ObjectUtils.compare(alias, NumberUtils.SHORT_ZERO) > 0;
        } else if (value instanceof Byte alias) {
            return ObjectUtils.compare(alias, NumberUtils.BYTE_ZERO) > 0;
        } else if (value instanceof Double alias) {
            return ObjectUtils.compare(alias, NumberUtils.DOUBLE_ZERO) > 0;
        } else if (value instanceof Float alias) {
            return ObjectUtils.compare(alias, NumberUtils.FLOAT_ZERO) > 0;
        } else if (value instanceof Fraction alias) {
            return ObjectUtils.compare(alias.floatValue(), NumberUtils.FLOAT_ZERO) > 0;
        } else if (value instanceof BigDecimal alias) {
            return ObjectUtils.compare(alias, BigDecimal.ZERO) > 0;
        } else if (value instanceof BigInteger alias) {
            return ObjectUtils.compare(alias, BigInteger.ZERO) > 0;
        }
        return value.longValue() > 0L;
    }

    /**
     * Checks whether the value is not positive.
     *
     * @param value The value
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static <T extends Number & Comparable<? extends Number>> boolean isNotPositive(@Nullable T value) {
        return !isPositive(value);
    }

    /**
     * Checks whether the value is zero.
     *
     * @param value The value
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static <T extends Number & Comparable<? extends Number>> boolean isZero(@Nullable T value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Integer alias) {
            return ObjectUtils.compare(alias, NumberUtils.INTEGER_ZERO) == 0;
        } else if (value instanceof Long alias) {
            return ObjectUtils.compare(alias, NumberUtils.LONG_ZERO) == 0;
        } else if (value instanceof Short alias) {
            return ObjectUtils.compare(alias, NumberUtils.SHORT_ZERO) == 0;
        } else if (value instanceof Byte alias) {
            return ObjectUtils.compare(alias, NumberUtils.BYTE_ZERO) == 0;
        } else if (value instanceof Double alias) {
            return ObjectUtils.compare(alias, NumberUtils.DOUBLE_ZERO) == 0;
        } else if (value instanceof Float alias) {
            return ObjectUtils.compare(alias, NumberUtils.FLOAT_ZERO) == 0;
        } else if (value instanceof Fraction alias) {
            return ObjectUtils.compare(alias.floatValue(), NumberUtils.FLOAT_ZERO) == 0;
        } else if (value instanceof BigDecimal alias) {
            return ObjectUtils.compare(alias, BigDecimal.ZERO) == 0;
        } else if (value instanceof BigInteger alias) {
            return ObjectUtils.compare(alias, BigInteger.ZERO) == 0;
        }
        return false;
    }

    /**
     * Checks whether the value is zero or null.
     *
     * @param value The value
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static <T extends Number & Comparable<? extends Number>> boolean isZeroOrNull(@Nullable T value) {
        return value == null || isZero(value);
    }

    /**
     * Checks whether the value is zero or negative.
     *
     * @param value The value
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static <T extends Number & Comparable<? extends Number>> boolean isZeroOrNegative(@Nullable T value) {
        return isZero(value) || isNegative(value);
    }

    /**
     * Checks whether the value is zero or positive.
     *
     * @param value The value
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static <T extends Number & Comparable<? extends Number>> boolean isZeroOrPositive(@Nullable T value) {
        return isZero(value) || isPositive(value);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Float multiplyByHundred(@Nullable Float value) {
        return multiplyByHundred(value, null);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Float multiplyByHundred(@Nullable Float value, @Nullable Float defaultValue) {
        return multiplyByHundred(value, NumberUtils.INTEGER_TWO, null, defaultValue);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     * @param scale The scale of the result
     * @param mode The rounding mode
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Float multiplyByHundred(@Nullable Float value, int scale, @Nullable RoundingMode mode) {
        return multiplyByHundred(value, scale, mode, null);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     * @param scale The scale of the result
     * @param mode The rounding mode
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Float multiplyByHundred(@Nullable Float value, int scale, @Nullable RoundingMode mode, @Nullable Float defaultValue) {
        return (value == null) ? defaultValue : Float.valueOf(NumberUtils.toScaledBigDecimal(value * 100f, scale, mode).floatValue());
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Double multiplyByHundred(@Nullable Double value) {
        return multiplyByHundred(value, null);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Double multiplyByHundred(@Nullable Double value, @Nullable Double defaultValue) {
        return multiplyByHundred(value, NumberUtils.INTEGER_TWO, null, defaultValue);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     * @param scale The scale of the result
     * @param mode The rounding mode
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Double multiplyByHundred(@Nullable Double value, int scale, @Nullable RoundingMode mode) {
        return multiplyByHundred(value, scale, mode, null);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     * @param scale The scale of the result
     * @param mode The rounding mode
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Double multiplyByHundred(@Nullable Double value, int scale, @Nullable RoundingMode mode, @Nullable Double defaultValue) {
        return (value == null) ? defaultValue : Double.valueOf(NumberUtils.toScaledBigDecimal(value * 100d, scale, mode).doubleValue());
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static BigDecimal multiplyByHundred(@Nullable BigDecimal value) {
        return multiplyByHundred(value, null);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static BigDecimal multiplyByHundred(@Nullable BigDecimal value, @Nullable BigDecimal defaultValue) {
        return multiplyByHundred(value, NumberUtils.INTEGER_TWO, null, defaultValue);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     * @param scale The scale of the result
     * @param mode The rounding mode
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static BigDecimal multiplyByHundred(@Nullable BigDecimal value, int scale, @Nullable RoundingMode mode) {
        return multiplyByHundred(value, scale, mode, null);
    }

    /**
     * Multiplies the value by one hundred.
     *
     * @param value The value
     * @param scale The scale of the result
     * @param mode The rounding mode
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static BigDecimal multiplyByHundred(@Nullable BigDecimal value, int scale, @Nullable RoundingMode mode, @Nullable BigDecimal defaultValue) {
        return (value == null) ? defaultValue : NumberUtils.toScaledBigDecimal(value.multiply(new BigDecimal(100)), scale, mode);
    }

    /**
     * Parses the text as the expected number type.
     *
     * @param value The value
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     *
     * @throws java.lang.NumberFormatException if the requested operation cannot be completed
     *
     * @throws java.lang.ClassCastException if the requested operation cannot be completed
     */
    public static <T extends Number & Comparable<? extends Number>> T parseAs(@Nullable String value, @Nullable Class<T> expectType) throws NumberFormatException, ClassCastException {
        return parseAs(value, expectType, null);
    }

    /**
     * Parses the text as the expected number type.
     *
     * @param value The value
     * @param expectType The expected type
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     *
     * @throws java.lang.NumberFormatException if the requested operation cannot be completed
     *
     * @throws java.lang.ClassCastException if the requested operation cannot be completed
     */
    public static <T extends Number & Comparable<? extends Number>> T parseAs(@Nullable String value, @Nullable Class<T> expectType, @Nullable T defaultValue) throws NumberFormatException, ClassCastException {
        if (StringUtils.isBlank(value) || expectType == null) {
            return defaultValue;
        }
        if (expectType == BigDecimal.class) {
            return expectType.cast(NumberUtils.createBigDecimal(value));
        } else if (expectType == BigInteger.class) {
            return expectType.cast(NumberUtils.createBigInteger(value));
        } else if (expectType == Byte.class) {
            return expectType.cast(Byte.valueOf(value));
        } else if (expectType == Double.class) {
            return expectType.cast(Double.valueOf(value));
        } else if (expectType == Float.class) {
            return expectType.cast(Float.valueOf(value));
        } else if (expectType == Integer.class) {
            return expectType.cast(NumberUtils.createInteger(value));
        } else if (expectType == Long.class) {
            return expectType.cast(NumberUtils.createLong(value));
        } else if (expectType == Short.class) {
            return expectType.cast(Short.valueOf(value));
        }
        throw new UnsupportedClassException("Unsupported expected type: " + expectType.getName());
    }

    /**
     * Parses the text without throwing exceptions.
     *
     * @param value The value
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T extends Number & Comparable<? extends Number>> T parseAsQuietly(@Nullable String value, @Nullable Class<T> expectType) {
        return parseAsQuietly(value, expectType, null);
    }

    /**
     * Parses the text without throwing exceptions.
     *
     * @param value The value
     * @param expectType The expected type
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T extends Number & Comparable<? extends Number>> T parseAsQuietly(@Nullable String value, @Nullable Class<T> expectType, @Nullable T defaultValue) {
        try {
            return parseAs(value, expectType, defaultValue);
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    /**
     * Converts the value to a BigDecimal.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static BigDecimal toBigDecimal(@Nullable String value) {
        return toBigDecimal(value, null);
    }

    /**
     * Converts the value to a BigDecimal.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static BigDecimal toBigDecimal(@Nullable String value, @Nullable BigDecimal defaultValue) {
        return parseAsQuietly(value, BigDecimal.class, defaultValue);
    }

    /**
     * Converts the value to a BigInteger.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static BigInteger toBigInteger(@Nullable String value) {
        return toBigInteger(value, null);
    }

    /**
     * Converts the value to a BigInteger.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static BigInteger toBigInteger(@Nullable String value, @Nullable BigInteger defaultValue) {
        return parseAsQuietly(value, BigInteger.class, defaultValue);
    }

    /**
     * Converts the value to a Byte.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Byte toByte(@Nullable String value) {
        return toByte(value, null);
    }

    /**
     * Converts the value to a Byte.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Byte toByte(@Nullable String value, @Nullable Byte defaultValue) {
        return parseAsQuietly(value, Byte.class, defaultValue);
    }

    /**
     * Converts the value to a Double.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Double toDouble(@Nullable String value) {
        return toDouble(value, null);
    }

    /**
     * Converts the value to a Double.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Double toDouble(@Nullable String value, @Nullable Double defaultValue) {
        return parseAsQuietly(value, Double.class, defaultValue);
    }

    /**
     * Converts the value to a Float.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Float toFloat(@Nullable String value) {
        return toFloat(value, null);
    }

    /**
     * Converts the value to a Float.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Float toFloat(@Nullable String value, @Nullable Float defaultValue) {
        return parseAsQuietly(value, Float.class, defaultValue);
    }

    /**
     * Converts the value to an Integer.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Integer toInteger(@Nullable String value) {
        return toInteger(value, null);
    }

    /**
     * Converts the value to an Integer.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Integer toInteger(@Nullable String value, @Nullable Integer defaultValue) {
        return parseAsQuietly(value, Integer.class, defaultValue);
    }

    /**
     * Converts the value to an Integer.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Integer toInteger(@Nullable Short value) {
        return toInteger(value, null);
    }

    /**
     * Converts the value to an Integer.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Integer toInteger(@Nullable Short value, @Nullable Integer defaultValue) {
        return (value == null) ? defaultValue : Integer.valueOf(value);
    }

    /**
     * Converts the value to a Long.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Long toLong(@Nullable String value) {
        return toLong(value, null);
    }

    /**
     * Converts the value to a Long.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Long toLong(@Nullable String value, @Nullable Long defaultValue) {
        return parseAsQuietly(value, Long.class, defaultValue);
    }

    /**
     * Converts the value to a Long.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Long toLong(@Nullable Integer value) {
        return toLong(value, null);
    }

    /**
     * Converts the value to a Long.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Long toLong(@Nullable Integer value, @Nullable Long defaultValue) {
        return (value == null) ? defaultValue : Long.valueOf(value);
    }

    /**
     * Converts the value to a Long.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Long toLong(@Nullable Short value) {
        return toLong(value, null);
    }

    /**
     * Converts the value to a Long.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Long toLong(@Nullable Short value, @Nullable Long defaultValue) {
        return (value == null) ? defaultValue : Long.valueOf(value);
    }

    /**
     * Converts the value to a Short.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Short toShort(@Nullable String value) {
        return toShort(value, null);
    }

    /**
     * Converts the value to a Short.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Short toShort(@Nullable String value, @Nullable Short defaultValue) {
        return parseAsQuietly(value, Short.class, defaultValue);
    }

    /**
     * Converts the value to a hex string with a prefix.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T extends Number> String toHexPrefixedString(@Nullable T value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer || value instanceof Byte || value instanceof Short) {
            return StringUtils.join(SymbolVariantConst.HEX_PREFIX, Integer.toHexString(value.intValue()));
        } else if (value instanceof Double alias) {
            return StringUtils.join(SymbolVariantConst.HEX_PREFIX, Double.toHexString(alias));
        }
        return StringUtils.join(SymbolVariantConst.HEX_PREFIX, Long.toHexString(value.longValue()));
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    public static long toPrimitive(@Nullable Long value) {
        return toPrimitive(value, NumberUtils.LONG_ZERO);
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static long toPrimitive(@Nullable Long value, long defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    public static int toPrimitive(@Nullable Integer value) {
        return toPrimitive(value, NumberUtils.INTEGER_ZERO);
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static int toPrimitive(@Nullable Integer value, int defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    public static short toPrimitive(@Nullable Short value) {
        return toPrimitive(value, NumberUtils.SHORT_ZERO);
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static short toPrimitive(@Nullable Short value, short defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    public static byte toPrimitive(@Nullable Byte value) {
        return toPrimitive(value, NumberUtils.BYTE_ZERO);
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static byte toPrimitive(@Nullable Byte value, byte defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    public static double toPrimitive(@Nullable Double value) {
        return toPrimitive(value, NumberUtils.DOUBLE_ZERO);
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static double toPrimitive(@Nullable Double value, double defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     *
     * @return the result, or null when it is unavailable
     */
    public static float toPrimitive(@Nullable Float value) {
        return toPrimitive(value, NumberUtils.FLOAT_ZERO);
    }

    /**
     * Converts the value to a primitive number.
     *
     * @param value The value
     * @param defaultValue The fallback value
     *
     * @return the result, or null when it is unavailable
     */
    public static float toPrimitive(@Nullable Float value, float defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    /**
     * Returns an uppercase Character object on the given value
     * <pre>
     *     NumberUtilsWraps.toUppercaseChar(1)  = 'A'
     *     NumberUtilsWraps.toUppercaseChar(26) = 'Z'
     * </pre>
     *
     * @param value The value to convert, must between 1(inclusive) and 26(inclusive)
     *
     * @return an uppercase Character object on the given value
     */
    @Nullable
    public static Character toUppercaseChar(int value) {
        return (value < 1 || value > 26) ? null : (char) (value + 64);
    }

    /**
     * Returns a lowercase Character object on the given value
     * <pre>
     *     NumberUtilsWraps.toLowercaseChar(1)  = 'a'
     *     NumberUtilsWraps.toLowercaseChar(26) = 'z'
     * </pre>
     *
     * @param value The value to convert, must between 1(inclusive) and 26(inclusive)
     *
     * @return a lowercase Character object on the given value
     */
    @Nullable
    public static Character toLowercaseChar(int value) {
        return (value < 1 || value > 26) ? null : (char) (value + 96);
    }

    /**
     * Returns an uppercase string from the given value, just like Excel column title
     * <pre>
     *     NumberUtilsWraps.toExcelColumn(1)  = "A"
     *     NumberUtilsWraps.toExcelColumn(26) = "Z"
     *     NumberUtilsWraps.toExcelColumn(27) = "AA"
     *     NumberUtilsWraps.toExcelColumn(703) = "AAA"
     * </pre>
     *
     * @param value The value to convert, 1 based
     *
     * @return  an uppercase string from the given value, just like Excel column title
     */
    @Nullable
    public static String toExcelColumn(int value) {
        if (value < 1) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        while (value > 0) {
            value--;
            char ch = (char) ('A' + value % 26);
            result.append(ch);
            value /= 26;
        }
        return result.reverse().toString();
    }
}
