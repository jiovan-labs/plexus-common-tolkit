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

package com.jiovan.plexus.common.toolkit.function;


import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiPredicate;
import org.apache.commons.lang3.function.FailableBooleanSupplier;
import org.apache.commons.lang3.function.FailableCallable;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableDoubleSupplier;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableIntSupplier;
import org.apache.commons.lang3.function.FailableLongSupplier;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableShortSupplier;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.math.NumberUtils;


/**
 * Attempts to address the fact that lambdas are supposed not to throw exceptions, at least not checked exceptions
 *
 * @author David Hsing
 *
 * @see org.apache.commons.lang3.function.Failable
 * @see org.apache.commons.lang3.exception.ExceptionUtils
 *
 * @see "https://blog.csdn.net/Revivedsun/article/details/79906165/"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class IgnorableFailable {
    /**
     * Adapts a bi-consumer into a bi-consumer that ignores thrown exceptions.
     *
     * @param consumer the failable bi-consumer to adapt
     *
     * @return a bi-consumer that silently ignores thrown exceptions
     */
    @Nonnull
    public static <T, U> BiConsumer<T, U> asBiConsumer(@Nonnull FailableBiConsumer<T, U, ?> consumer) {
        return asBiConsumer(consumer, null);
    }

    /**
     * Adapts a bi-consumer into a bi-consumer that ignores exceptions to the given type.
     *
     * @param consumer the failable bi-consumer to adapt
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return a bi-consumer that silently ignores thrown exceptions
     */
    @Nonnull
    public static <T, U, E extends Throwable> BiConsumer<T, U> asBiConsumer(@Nonnull FailableBiConsumer<T, U, ?> consumer, @Nullable Class<E> throwable) {
        return (t, u) -> {
            try {
                consumer.accept(t, u);
            } catch (Throwable ex) {
                judgeThrowable(ex, throwable);
            }
        };
    }

    /**
     * Adapts a bi-function into a bi-function that ignores thrown exceptions.
     *
     * @param function the failable bi-function to adapt
     *
     * @return a bi-function that returns null when an exception is thrown
     */
    @Nonnull
    public static <T, U, R> BiFunction<T, U, R> asBiFunction(@Nonnull FailableBiFunction<T, U, R, ?> function) {
        return asBiFunction(function, null);
    }

    /**
     * Adapts a bi-function into a bi-function that ignores exceptions to the given type.
     *
     * @param function the failable bi-function to adapt
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return a bi-function that returns null when an exception is thrown
     */
    @Nonnull
    public static <T, U, R, E extends Throwable> BiFunction<T, U, R> asBiFunction(@Nonnull FailableBiFunction<T, U, R, ?> function, @Nullable Class<E> throwable) {
        return (t, u) -> {
            try {
                return function.apply(t, u);
            } catch (Throwable ex) {
                judgeThrowable(ex, throwable);
            }
            return null;
        };
    }

    /**
     * Adapts a bi-predicate into a bi-predicate that ignores thrown exceptions.
     *
     * @param predicate the failable bi-predicate to adapt
     *
     * @return a bi-predicate that returns {@code false} when an exception is thrown
     */
    @Nonnull
    public static <T, U> BiPredicate<T, U> asBiPredicate(@Nonnull FailableBiPredicate<T, U, ?> predicate) {
        return asBiPredicate(predicate, null);
    }

    /**
     * Adapts a bi-predicate into a bi-predicate that ignores exceptions to the given type.
     *
     * @param predicate the failable bi-predicate to adapt
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return a bi-predicate that returns {@code false} when an exception is thrown
     */
    @Nonnull
    public static <T, U, E extends Throwable> BiPredicate<T, U> asBiPredicate(@Nonnull FailableBiPredicate<T, U, ?> predicate, @Nullable Class<E> throwable) {
        return (t, u) -> {
            try {
                return predicate.test(t, u);
            } catch (Throwable ex) {
                judgeThrowable(ex, throwable);
            }
            return false;
        };
    }

    /**
     * Adapts a callable into a callable that ignores thrown exceptions.
     *
     * @param callable the failable callable to adapt
     *
     * @return a callable that returns null when an exception is thrown
     */
    @Nonnull
    public static <V> Callable<V> asCallable(@Nonnull FailableCallable<V, ?> callable) {
        return asCallable(callable, null);
    }

    /**
     * Adapts a callable into a callable that ignores exceptions to the given type.
     *
     * @param callable the failable callable to adapt
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return a callable that returns null when an exception is thrown
     */
    @Nonnull
    public static <V, E extends Throwable> Callable<V> asCallable(@Nonnull FailableCallable<V, ?> callable, @Nullable Class<E> throwable) {
        return () -> {
            try {
                return callable.call();
            } catch (Throwable ex) {
                judgeThrowable(ex, throwable);
            }
            return null;
        };
    }

    /**
     * Adapts a consumer into a consumer that ignores thrown exceptions.
     *
     * @param consumer the failable consumer to adapt
     *
     * @return a consumer that silently ignores thrown exceptions
     */
    @Nonnull
    public static <T> Consumer<T> asConsumer(@Nonnull FailableConsumer<T, ?> consumer) {
        return asConsumer(consumer, null);
    }

    /**
     * Adapts a consumer into a consumer that ignores exceptions to the given type.
     *
     * @param consumer the failable consumer to adapt
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return a consumer that silently ignores thrown exceptions
     */
    @Nonnull
    public static <T, E extends Throwable> Consumer<T> asConsumer(@Nonnull FailableConsumer<T, ?> consumer, @Nullable Class<E> throwable) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Throwable ex) {
                judgeThrowable(ex, throwable);
            }
        };
    }

    /**
     * Adapts a function into a function that ignores thrown exceptions.
     *
     * @param function the failable function to adapt
     *
     * @return a function that returns null when an exception is thrown
     */
    @Nonnull
    public static <T, R> Function<T, R> asFunction(@Nonnull FailableFunction<T, R, ?> function) {
        return asFunction(function, null);
    }

    /**
     * Adapts a function into a function that ignores exceptions to the given type.
     *
     * @param function the failable function to adapt
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return a function that returns null when an exception is thrown
     */
    @Nonnull
    public static <T, R, E extends Throwable> Function<T, R> asFunction(@Nonnull FailableFunction<T, R, ?> function, @Nullable Class<E> throwable) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Throwable ex) {
                judgeThrowable(ex, throwable);
            }
            return null;
        };
    }

    /**
     * Adapts a predicate into a predicate that ignores thrown exceptions.
     *
     * @param predicate the failable predicate to adapt
     *
     * @return a predicate that returns {@code false} when an exception is thrown
     */
    @Nonnull
    public static <T> Predicate<T> asPredicate(@Nonnull FailablePredicate<T, ?> predicate) {
        return asPredicate(predicate, null);
    }

    /**
     * Adapts a predicate into a predicate that ignores exceptions to the given type.
     *
     * @param predicate the failable predicate to adapt
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return a predicate that returns {@code false} when an exception is thrown
     */
    @Nonnull
    public static <T, E extends Throwable> Predicate<T> asPredicate(@Nonnull FailablePredicate<T, ?> predicate, @Nullable Class<E> throwable) {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Throwable ex) {
                judgeThrowable(ex, throwable);
            }
            return false;
        };
    }

    /**
     * Adapts a runnable into a runnable that ignores thrown exceptions.
     *
     * @param runnable the failable runnable to adapt
     *
     * @return a runnable that silently ignores thrown exceptions
     */
    @Nonnull
    public static Runnable asRunnable(@Nonnull FailableRunnable<?> runnable) {
        return asRunnable(runnable, null);
    }

    /**
     * Adapts a runnable into a runnable that ignores exceptions to the given type.
     *
     * @param runnable the failable runnable to adapt
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return a runnable that silently ignores thrown exceptions
     */
    @Nonnull
    public static <E extends Throwable> Runnable asRunnable(@Nonnull FailableRunnable<?> runnable, @Nullable Class<E> throwable) {
        return () -> {
            try {
                runnable.run();
            } catch (Throwable ex) {
                judgeThrowable(ex, throwable);
            }
        };
    }

    /**
     * Adapts a supplier into a supplier that ignores thrown exceptions.
     *
     * @param supplier the failable supplier to adapt
     *
     * @return a supplier that returns null when an exception is thrown
     */
    @Nonnull
    public static <T> Supplier<T> asSupplier(@Nonnull FailableSupplier<T, ?> supplier) {
        return asSupplier(supplier, null);
    }

    /**
     * Adapts a supplier into a supplier that ignores exceptions to the given type.
     *
     * @param supplier the failable supplier to adapt
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return a supplier that returns null when an exception is thrown
     */
    @Nonnull
    public static <T, E extends Throwable> Supplier<T> asSupplier(@Nonnull FailableSupplier<T, ?> supplier, @Nullable Class<E> throwable) {
        return () -> {
            try {
                return supplier.get();
            } catch (Throwable ex) {
                judgeThrowable(ex, throwable);
            }
            return null;
        };
    }

    /**
     * Calls the given failable callable, ignoring any thrown exception.
     *
     * @param callable the failable callable to invoke
     *
     * @return the result of the callable, or null when an exception is thrown
     */
    @Nullable
    public static <V> V call(@Nonnull FailableCallable<V, ?> callable) {
        return call(callable, null);
    }

    /**
     * Calls the given failable callable, ignoring exceptions to the given type.
     *
     * @param callable the failable callable to invoke
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return the result of the callable, or null when an exception is thrown
     */
    @Nullable
    public static <V, E extends Throwable> V call(@Nonnull FailableCallable<V, ?> callable, @Nullable Class<E> throwable) {
        try {
            return Failable.call(callable);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
        return null;
    }

    /**
     * Gets the value from the given failable supplier, ignoring any thrown exception.
     *
     * @param supplier the failable supplier to invoke
     *
     * @return the supplied value, or null when an exception is thrown
     */
    @Nullable
    public static <T> T get(@Nonnull FailableSupplier<T, ?> supplier) {
        return get(supplier, null);
    }

    /**
     * Gets the value from the given failable supplier, ignoring exceptions to the given type.
     *
     * @param supplier the failable supplier to invoke
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return the supplied value, or null when an exception is thrown
     */
    @Nullable
    public static <T, E extends Throwable> T get(@Nonnull FailableSupplier<T, ?> supplier, @Nullable Class<E> throwable) {
        try {
            return Failable.get(supplier);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
        return null;
    }

    /**
     * Gets the boolean value from the given failable supplier, ignoring any thrown exception.
     *
     * @param supplier the failable boolean supplier to invoke
     *
     * @return the supplied value, or {@code false} when an exception is thrown
     */
    public static boolean getAsBoolean(@Nonnull FailableBooleanSupplier<?> supplier) {
        return getAsBoolean(supplier, null);
    }

    /**
     * Gets the boolean value from the given failable supplier, ignoring exceptions to the given type.
     *
     * @param supplier the failable boolean supplier to invoke
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return the supplied value, or {@code false} when an exception is thrown
     */
    public static <E extends Throwable> boolean getAsBoolean(@Nonnull FailableBooleanSupplier<?> supplier, @Nullable Class<E> throwable) {
        try {
            return Failable.getAsBoolean(supplier);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
        return false;
    }

    /**
     * Gets the double value from the given failable supplier, ignoring any thrown exception.
     *
     * @param supplier the failable double supplier to invoke
     *
     * @return the supplied value, or zero when an exception is thrown
     */
    public static double getAsDouble(@Nonnull FailableDoubleSupplier<?> supplier) {
        return getAsDouble(supplier, null);
    }

    /**
     * Gets the double value from the given failable supplier, ignoring exceptions to the given type.
     *
     * @param supplier the failable double supplier to invoke
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return the supplied value, or zero when an exception is thrown
     */
    public static <E extends Throwable> double getAsDouble(@Nonnull FailableDoubleSupplier<?> supplier, @Nullable Class<E> throwable) {
        try {
            return Failable.getAsDouble(supplier);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
        return NumberUtils.DOUBLE_ZERO;
    }

    /**
     * Gets the int value from the given failable supplier, ignoring any thrown exception.
     *
     * @param supplier the failable int supplier to invoke
     *
     * @return the supplied value, or zero when an exception is thrown
     */
    public static int getAsInt(@Nonnull FailableIntSupplier<?> supplier) {
        return getAsInt(supplier, null);
    }

    /**
     * Gets the int value from the given failable supplier, ignoring exceptions to the given type.
     *
     * @param supplier the failable int supplier to invoke
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return the supplied value, or zero when an exception is thrown
     */
    public static <E extends Throwable> int getAsInt(@Nonnull FailableIntSupplier<?> supplier, @Nullable Class<E> throwable) {
        try {
            return Failable.getAsInt(supplier);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
        return NumberUtils.INTEGER_ZERO;
    }

    /**
     * Gets the long value from the given failable supplier, ignoring any thrown exception.
     *
     * @param supplier the failable long supplier to invoke
     *
     * @return the supplied value, or zero when an exception is thrown
     */
    public static long getAsLong(@Nonnull FailableLongSupplier<?> supplier) {
        return getAsLong(supplier, null);
    }

    /**
     * Gets the long value from the given failable supplier, ignoring exceptions to the given type.
     *
     * @param supplier the failable long supplier to invoke
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return the supplied value, or zero when an exception is thrown
     */
    public static <E extends Throwable> long getAsLong(@Nonnull FailableLongSupplier<?> supplier, @Nullable Class<E> throwable) {
        try {
            return Failable.getAsLong(supplier);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
        return NumberUtils.LONG_ZERO;
    }

    /**
     * Gets the short value from the given failable supplier, ignoring any thrown exception.
     *
     * @param supplier the failable short supplier to invoke
     *
     * @return the supplied value, or zero when an exception is thrown
     */
    public static short getAsShort(@Nonnull FailableShortSupplier<?> supplier) {
        return getAsShort(supplier, null);
    }

    /**
     * Gets the short value from the given failable supplier, ignoring exceptions to the given type.
     *
     * @param supplier the failable short supplier to invoke
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return the supplied value, or zero when an exception is thrown
     */
    public static <E extends Throwable> short getAsShort(@Nonnull FailableShortSupplier<?> supplier, @Nullable Class<E> throwable) {
        try {
            return Failable.getAsShort(supplier);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
        return NumberUtils.SHORT_ZERO;
    }

    /**
     * Runs the given failable runnable, ignoring any thrown exception.
     *
     * @param runnable the failable runnable to invoke
     */
    public static void run(@Nonnull FailableRunnable<?> runnable) {
        run(runnable, null);
    }

    /**
     * Runs the given failable runnable, ignoring exceptions to the given type.
     *
     * @param runnable the failable runnable to invoke
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     */
    public static <E extends Throwable> void run(@Nonnull FailableRunnable<?> runnable, @Nullable Class<E> throwable) {
        try {
            Failable.run(runnable);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
    }

    /**
     * Tests the given failable bi-predicate, ignoring any thrown exception.
     *
     * @param predicate the failable bi-predicate to evaluate
     * @param t the first argument to test, or null
     * @param u the second argument to test, or null
     *
     * @return {@code true} when the predicate matches and no exception is thrown; {@code false} otherwise
     */
    public static <T, U> boolean test(@Nonnull FailableBiPredicate<T, U, ?> predicate, @Nullable T t, @Nullable U u) {
        return test(predicate, t, u, null);
    }

    /**
     * Tests the given failable bi-predicate, ignoring exceptions to the given type.
     *
     * @param predicate the failable bi-predicate to evaluate
     * @param t the first argument to test, or null
     * @param u the second argument to test, or null
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return {@code true} when the predicate matches and no exception is thrown; {@code false} otherwise
     */
    public static <T, U, E extends Throwable> boolean test(@Nonnull FailableBiPredicate<T, U, ?> predicate, @Nullable T t, @Nullable U u, @Nullable Class<E> throwable) {
        try {
            return Failable.test(predicate, t, u);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
        return false;
    }

    /**
     * Tests the given failable predicate, ignoring any thrown exception.
     *
     * @param predicate the failable predicate to evaluate
     * @param t the first argument to test, or null
     *
     * @return {@code true} when the predicate matches and no exception is thrown; {@code false} otherwise
     */
    public static <T> boolean test(@Nonnull FailablePredicate<T, ?> predicate, @Nullable T t) {
        return test(predicate, t, null);
    }

    /**
     * Tests the given failable predicate, ignoring exceptions to the given type.
     *
     * @param predicate the failable predicate to evaluate
     * @param t the first argument to test, or null
     * @param throwable the exception type to ignore, or null to ignore all exceptions
     *
     * @return {@code true} when the predicate matches and no exception is thrown; {@code false} otherwise
     */
    public static <T, E extends Throwable> boolean test(@Nonnull FailablePredicate<T, ?> predicate, @Nullable T t, @Nullable Class<E> throwable) {
        try {
            return Failable.test(predicate, t);
        } catch (Throwable ex) {
            judgeThrowable(ex, throwable);
        }
        return false;
    }

    /**
     * Ignores the given exception when it matches the given type, and rethrows it otherwise.
     *
     * @param ex the exception to judge
     * @param clazz the exception type to ignore, or null to ignore all exceptions
     */
    protected static void judgeThrowable(@Nonnull Throwable ex, @Nullable Class<? extends Throwable> clazz) {
        if (clazz == null || clazz == Throwable.class || clazz == Exception.class) {
            return;
        }
        try {
            clazz.cast(ex);
        } catch (ClassCastException ignored) {
            Failable.rethrow(ex);
        }
    }
}
