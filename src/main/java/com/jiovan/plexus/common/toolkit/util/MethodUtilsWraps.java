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


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.reflect.TypeUtils;


/**
 * Utilities for {@link org.apache.commons.lang3.reflect.MethodUtils}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.lang3.reflect.MethodUtils
 * @see "org.springframework.security.util.MethodInvocationUtils"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class MethodUtilsWraps {
    /**
     * Performs an action for each declared method of the given class.
     *
     * @param clazz The class to inspect
     * @param action The action to perform
     */
    public static void doWithDeclaredMethods(@Nullable Class<?> clazz, @Nullable Consumer<Method> action) {
        doWithDeclaredMethods(clazz, action, null);
    }

    /**
     * Performs an action for each declared method of the given class.
     *
     * @param clazz The class to inspect
     * @param action The action to perform
     * @param filter The predicate used to filter results
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void doWithDeclaredMethods(@Nullable Class<?> clazz, @Nullable Consumer<Method> action, @Nullable Predicate<Method> filter) {
        if (ObjectUtils.anyNull(clazz, action) || clazz == Object.class) {
            return;
        }
        Method[] methods = clazz.getDeclaredMethods();
        if (ArrayUtils.isEmpty(methods)) {
            return;
        }
        for (Method method : methods) {
            if (filter == null || filter.test(method)) {
                action.accept(method);
            }
        }
    }

    /**
     * Performs an action for each method of the given class and its superclasses.
     *
     * @param clazz The class to inspect
     * @param action The action to perform
     */
    public static void doWithNestedMethods(@Nullable Class<?> clazz, @Nullable Consumer<Method> action) {
        doWithNestedMethods(clazz, action, null);
    }

    /**
     * Performs an action for each method of the given class and its superclasses.
     *
     * @param clazz The class to inspect
     * @param action The action to perform
     * @param filter The predicate used to filter results
     */
    public static void doWithNestedMethods(@Nullable Class<?> clazz, @Nullable Consumer<Method> action, @Nullable Predicate<Method> filter) {
        doWithNestedMethodsUntil(clazz, null, action, filter);
    }

    /**
     * Performs an action for each method up to the specified boundary class.
     *
     * @param clazz The class to inspect
     * @param untilClass The boundary class
     * @param action The action to perform
     */
    public static void doWithNestedMethodsUntil(@Nullable Class<?> clazz, @Nullable Class<?> untilClass, @Nullable Consumer<Method> action) {
        doWithNestedMethodsUntil(clazz, null, action, null);
    }

    /**
     * @see "org.springframework.util.ReflectionUtils#doWithMethods"
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void doWithNestedMethodsUntil(@Nullable Class<?> clazz, @Nullable Class<?> untilClass, @Nullable Consumer<Method> action, @Nullable Predicate<Method> filter) {
        if (ObjectUtils.anyNull(clazz, action) || (untilClass != null && untilClass == clazz)) {
            return;
        }
        Method[] methods = clazz.getDeclaredMethods();
        if (ArrayUtils.isNotEmpty(methods)) {
            for (Method method : methods) {
                if ((filter == null || filter.test(method)) && isUserDefined(method)) {
                    action.accept(method);
                }
            }
        }
        if (clazz.getSuperclass() == null) {
            if (clazz.isInterface()) {
                Class<?>[] interfaceClasses = clazz.getInterfaces();
                for (Class<?> interfaceClass : interfaceClasses) {
                    doWithNestedMethodsUntil(interfaceClass, untilClass, action, filter);
                }
            }
        } else {
            doWithNestedMethodsUntil(clazz.getSuperclass(), untilClass, action, filter);
        }
    }

    /**
     * Performs the getDeclaredMethods operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method[] getDeclaredMethods(@Nullable Class<?> clazz) {
        return (clazz == null) ? null : clazz.getDeclaredMethods();
    }

    /**
     * Performs the getDeclaredMethods operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method[] getDeclaredMethods(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<Method> methods = getDeclaredMethodsToList(clazz, filter);
        return CollectionPlainWraps.isEmpty(methods) ? null : methods.toArray(ArrayUtils.EMPTY_METHOD_ARRAY);
    }

    /**
     * Performs the getDeclaredMethodsToList operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<Method> getDeclaredMethodsToList(@Nullable Class<?> clazz) {
        return getDeclaredMethodsToList(clazz, null);
    }

    /**
     * Performs the getDeclaredMethodsToList operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<Method> getDeclaredMethodsToList(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<Method> result = new ArrayList<>();
        doWithDeclaredMethods(clazz, result::add, filter);
        return result.isEmpty() ? null : result;
    }

    /**
     * Performs the getDeclaredMethodsToSet operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<Method> getDeclaredMethodsToSet(@Nullable Class<?> clazz) {
        return getDeclaredMethodsToSet(clazz, null);
    }

    /**
     * Performs the getDeclaredMethodsToSet operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<Method> getDeclaredMethodsToSet(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<Method> methods = getDeclaredMethodsToList(clazz, filter);
        return CollectionPlainWraps.isEmpty(methods) ? null : new LinkedHashSet<>(methods);
    }

    /**
     * Performs the getDeclaredMethodNames operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String[] getDeclaredMethodNames(@Nullable Class<?> clazz) {
        return getDeclaredMethodNames(clazz, null);
    }

    /**
     * Performs the getDeclaredMethodNames operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String[] getDeclaredMethodNames(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<String> names = getDeclaredMethodNamesToList(clazz, filter);
        return CollectionPlainWraps.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Performs the getDeclaredMethodNamesToList operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<String> getDeclaredMethodNamesToList(@Nullable Class<?> clazz) {
        return getDeclaredMethodNamesToList(clazz, null);
    }

    /**
     * Performs the getDeclaredMethodNamesToList operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<String> getDeclaredMethodNamesToList(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<String> result = new ArrayList<>();
        doWithDeclaredMethods(clazz, method -> result.add(method.getName()), filter);
        return result.isEmpty() ? null : result;
    }

    /**
     * Performs the getDeclaredMethodNamesToSet operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<String> getDeclaredMethodNamesToSet(@Nullable Class<?> clazz) {
        return getDeclaredMethodNamesToSet(clazz, null);
    }

    /**
     * Performs the getDeclaredMethodNamesToSet operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<String> getDeclaredMethodNamesToSet(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<String> names = getDeclaredMethodNamesToList(clazz, filter);
        return CollectionPlainWraps.isEmpty(names) ? null : new LinkedHashSet<>(names);
    }

    /**
     * Performs the getNestedMethods operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method[] getNestedMethods(@Nullable Class<?> clazz) {
        return getNestedMethods(clazz, null);
    }

    /**
     * Performs the getNestedMethods operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method[] getNestedMethods(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<Method> methods = getNestedMethodsToList(clazz, filter);
        return CollectionPlainWraps.isEmpty(methods) ? null : methods.toArray(ArrayUtils.EMPTY_METHOD_ARRAY);
    }

    /**
     * Performs the getNestedMethodsToList operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<Method> getNestedMethodsToList(@Nullable Class<?> clazz) {
        return getNestedMethodsToList(clazz, null);
    }

    /**
     * Performs the getNestedMethodsToList operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<Method> getNestedMethodsToList(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<Method> result = new ArrayList<>();
        doWithNestedMethods(clazz, result::add, filter);
        return result.isEmpty() ? null : result;
    }

    /**
     * Performs the getNestedMethodsToSet operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<Method> getNestedMethodsToSet(@Nullable Class<?> clazz) {
        return getNestedMethodsToSet(clazz, null);
    }

    /**
     * Performs the getNestedMethodsToSet operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<Method> getNestedMethodsToSet(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<Method> methods = getNestedMethodsToList(clazz, filter);
        return CollectionPlainWraps.isEmpty(methods) ? null : new LinkedHashSet<>(methods);
    }

    /**
     * Performs the getNestedMethodNames operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String[] getNestedMethodNames(@Nullable Class<?> clazz) {
        return getNestedMethodNames(clazz, null);
    }

    /**
     * Performs the getNestedMethodNames operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String[] getNestedMethodNames(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<String> names = getNestedMethodNamesToList(clazz, filter);
        return CollectionPlainWraps.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Performs the getNestedMethodNamesToList operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<String> getNestedMethodNamesToList(@Nullable Class<?> clazz) {
        return getNestedMethodNamesToList(clazz, null);
    }

    /**
     * Performs the getNestedMethodNamesToList operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<String> getNestedMethodNamesToList(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<String> result = new ArrayList<>();
        doWithNestedMethods(clazz, method -> result.add(method.getName()), filter);
        return CollectionPlainWraps.isEmpty(result) ? null : result;
    }

    /**
     * Performs the getNestedMethodNamesToSet operation.
     *
     * @param clazz The class to inspect
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<String> getNestedMethodNamesToSet(@Nullable Class<?> clazz) {
        return getNestedMethodNamesToSet(clazz, null);
    }

    /**
     * Performs the getNestedMethodNamesToSet operation.
     *
     * @param clazz The class to inspect
     * @param filter The predicate used to filter results
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<String> getNestedMethodNamesToSet(@Nullable Class<?> clazz, @Nullable Predicate<Method> filter) {
        List<String> names = getNestedMethodNamesToList(clazz, filter);
        return CollectionPlainWraps.isEmpty(names) ? null : new LinkedHashSet<>(names);
    }

    /**
     * Performs the getMethodsWithAllAnnotations operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static Method[] getMethodsWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAllAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodsWithAllAnnotations operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method[] getMethodsWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAllAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(methods) ? null : methods.toArray(ArrayUtils.EMPTY_METHOD_ARRAY);
    }

    /**
     * Performs the getMethodsWithAllAnnotationsToList operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static List<Method> getMethodsWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAllAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodsWithAllAnnotationsToList operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<Method> getMethodsWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (clazz == null || CollectionPlainWraps.isEmpty(annotations)) {
            return null;
        }
        List<Method> result = getNestedMethodsToList(clazz, item -> AnnotationUtilsWraps.allPresent(item, annotations));
        return CollectionPlainWraps.isEmpty(result) ? null : result;
    }

    /**
     * Performs the getMethodsWithAllAnnotationsToSet operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static Set<Method> getMethodsWithAllAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAllAnnotationsToSet(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodsWithAllAnnotationsToSet operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<Method> getMethodsWithAllAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAllAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(methods) ? null : new LinkedHashSet<>(methods);
    }

    /**
     * Performs the getMethodsWithAnyAnnotations operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static Method[] getMethodsWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAnyAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodsWithAnyAnnotations operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method[] getMethodsWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAnyAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(methods) ? null : methods.toArray(ArrayUtils.EMPTY_METHOD_ARRAY);
    }

    /**
     * Performs the getMethodsWithAnyAnnotationsToList operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static List<Method> getMethodsWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAnyAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodsWithAnyAnnotationsToList operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<Method> getMethodsWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (clazz == null || CollectionPlainWraps.isEmpty(annotations)) {
            return null;
        }
        List<Method> result = getNestedMethodsToList(clazz, item -> AnnotationUtilsWraps.anyPresent(item, annotations));
        return CollectionPlainWraps.isEmpty(result) ? null : result;
    }

    /**
     * Performs the getMethodsWithAnyAnnotationsToSet operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static Set<Method> getMethodsWithAnyAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAnyAnnotationsToSet(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodsWithAnyAnnotationsToSet operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<Method> getMethodsWithAnyAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAnyAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(methods) ? null : new LinkedHashSet<>(methods);
    }

    /**
     * Performs the getMethodNamesWithAllAnnotations operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static String[] getMethodNamesWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAllAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodNamesWithAllAnnotations operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String[] getMethodNamesWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<String> names = getMethodNamesWithAllAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Performs the getMethodNamesWithAllAnnotationsToList operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static List<String> getMethodNamesWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAllAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodNamesWithAllAnnotationsToList operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<String> getMethodNamesWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAllAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(methods) ? null : methods.stream().map(Method::getName).collect(Collectors.toList());
    }

    /**
     * Performs the getMethodNamesWithAllAnnotationsToSet operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static Set<String> getMethodNamesWithAllAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAllAnnotationsToSet(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodNamesWithAllAnnotationsToSet operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<String> getMethodNamesWithAllAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<String> methods = getMethodNamesWithAllAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(methods) ? null : new LinkedHashSet<>(methods);
    }

    /**
     * Performs the getMethodNamesWithAnyAnnotations operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static String[] getMethodNamesWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAnyAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodNamesWithAnyAnnotations operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static String[] getMethodNamesWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<String> names = getMethodNamesWithAnyAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Performs the getMethodNamesWithAnyAnnotationsToList operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static List<String> getMethodNamesWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAnyAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodNamesWithAnyAnnotationsToList operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static List<String> getMethodNamesWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAnyAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(methods) ? null : methods.stream().map(Method::getName).collect(Collectors.toList());
    }

    /**
     * Performs the getMethodNamesWithAnyAnnotationsToSet operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    @SafeVarargs
    public static Set<String> getMethodNamesWithAnyAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAnyAnnotationsToSet(clazz, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Performs the getMethodNamesWithAnyAnnotationsToSet operation.
     *
     * @param clazz The class to inspect
     * @param annotations The annotation types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Set<String> getMethodNamesWithAnyAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<String> methods = getMethodNamesWithAnyAnnotationsToList(clazz, annotations);
        return CollectionPlainWraps.isEmpty(methods) ? null : new LinkedHashSet<>(methods);
    }

    /**
     * Finds a method matching the given name and parameter types.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method findMethod(@Nullable Class<?> clazz, @Nullable String methodName) {
        return findMethod(clazz, methodName, ArrayUtils.EMPTY_CLASS_ARRAY);
    }

    /**
     * Finds a method matching the given name and parameter types.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param paramTypes The parameter types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method findMethod(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Class<?>... paramTypes) {
        try {
            return MethodUtils.getMatchingMethod(clazz, methodName, paramTypes);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Finds an accessible method matching the given name and parameter types.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method findMethodAccessible(@Nullable Class<?> clazz, @Nullable String methodName) {
        return findMethodAccessible(clazz, methodName, ArrayUtils.EMPTY_CLASS_ARRAY);
    }

    /**
     * Finds an accessible method matching the given name and parameter types.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param paramTypes The parameter types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Method findMethodAccessible(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Class<?>... paramTypes) {
        if (clazz == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        try {
            return MethodUtils.getMatchingAccessibleMethod(clazz, methodName, paramTypes);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Invokes a method on the target object.
     *
     * @param target The target value
     * @param methodName The method name
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeMethod(@Nullable Object target, @Nullable String methodName) {
        return invokeMethod(target, false, methodName);
    }

    /**
     * Invokes a method on the target object.
     *
     * @param target The target value
     * @param forceAccess Whether scope restrictions should be bypassed
     * @param methodName The method name
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeMethod(@Nullable Object target, boolean forceAccess, @Nullable String methodName) {
        return invokeMethod(target, forceAccess, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    /**
     * Invokes a method on the target object.
     *
     * @param target The target value
     * @param methodName The method name
     * @param args The invocation arguments
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeMethod(@Nullable Object target, @Nullable String methodName, @Nullable Object... args) {
        return invokeMethod(target, false, methodName, args);
    }

    /**
     * Invokes a method on the target object.
     *
     * @param target The target value
     * @param forceAccess Whether scope restrictions should be bypassed
     * @param methodName The method name
     * @param args The invocation arguments
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeMethod(@Nullable Object target, boolean forceAccess, @Nullable String methodName, @Nullable Object... args) {
        if (target == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        try {
            return MethodUtils.invokeMethod(target, forceAccess, methodName, args);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Invokes a method on the target object.
     *
     * @param target The target value
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeMethod(@Nullable Object target, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes) {
        return invokeMethod(target, false, methodName, args, paramTypes);
    }

    /**
     * Invokes a method on the target object.
     *
     * @param target The target value
     * @param forceAccess Whether scope restrictions should be bypassed
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeMethod(@Nullable Object target, boolean forceAccess, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes) {
        if (target == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        try {
            return MethodUtils.invokeMethod(target, forceAccess, methodName, args, paramTypes);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Invokes a method and casts the result to the expected type.
     *
     * @param target The target value
     * @param methodName The method name
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeMethodAs(@Nullable Object target, @Nullable String methodName, @Nullable Class<T> expectType) {
        return invokeMethodAs(target, false, methodName, expectType);
    }

    /**
     * Invokes a method and casts the result to the expected type.
     *
     * @param target The target value
     * @param forceAccess Whether scope restrictions should be bypassed
     * @param methodName The method name
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeMethodAs(@Nullable Object target, boolean forceAccess, @Nullable String methodName, @Nullable Class<T> expectType) {
        return invokeMethodAs(target, forceAccess, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, expectType);
    }

    /**
     * Invokes a method and casts the result to the expected type.
     *
     * @param target The target value
     * @param methodName The method name
     * @param args The invocation arguments
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeMethodAs(@Nullable Object target, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<T> expectType) {
        return invokeMethodAs(target, false, methodName, args, expectType);
    }

    /**
     * Invokes a method and casts the result to the expected type.
     *
     * @param target The target value
     * @param forceAccess Whether scope restrictions should be bypassed
     * @param methodName The method name
     * @param args The invocation arguments
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeMethodAs(@Nullable Object target, boolean forceAccess, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(target, expectType) || StringUtils.isBlank(methodName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(invokeMethod(target, forceAccess, methodName, args), expectType);
    }

    /**
     * Invokes a method and casts the result to the expected type.
     *
     * @param target The target value
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeMethodAs(@Nullable Object target, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes, @Nullable Class<T> expectType) {
        return invokeMethodAs(target, false, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, null, expectType);
    }

    /**
     * Invokes a method and casts the result to the expected type.
     *
     * @param target The target value
     * @param forceAccess Whether scope restrictions should be bypassed
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeMethodAs(@Nullable Object target, boolean forceAccess, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(target, expectType) || StringUtils.isBlank(methodName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(invokeMethod(target, forceAccess, methodName, args, paramTypes), expectType);
    }

    /**
     * Invokes a static method on the given class.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param args The invocation arguments
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeStaticMethod(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Object... args) {
        if (clazz == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        try {
            return MethodUtils.invokeStaticMethod(clazz, methodName, args);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Invokes a static method on the given class.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeStaticMethod(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes) {
        if (clazz == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        try {
            return MethodUtils.invokeStaticMethod(clazz, methodName, args, paramTypes);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Invokes a static method and casts the result to the expected type.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param args The invocation arguments
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeStaticMethodAs(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(clazz, expectType) || StringUtils.isBlank(methodName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(invokeStaticMethod(clazz, methodName, args), expectType);
    }

    /**
     * Invokes a static method and casts the result to the expected type.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeStaticMethodAs(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(clazz, expectType) || StringUtils.isBlank(methodName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(invokeStaticMethod(clazz, methodName, args, paramTypes), expectType);
    }

    /**
     * Invokes a method with exactly the given arguments.
     *
     * @param target The target value
     * @param methodName The method name
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeExactMethod(@Nullable Object target, @Nullable String methodName) {
        return invokeExactMethod(target, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    /**
     * Invokes a method with exactly the given arguments.
     *
     * @param target The target value
     * @param methodName The method name
     * @param args The invocation arguments
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeExactMethod(@Nullable Object target, @Nullable String methodName, @Nullable Object... args) {
        return invokeExactMethod(target, methodName, args, null);
    }

    /**
     * Invokes a method with exactly the given arguments.
     *
     * @param target The target value
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeExactMethod(@Nullable Object target, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes) {
        if (target == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        try {
            return MethodUtils.invokeExactMethod(target, methodName, args, paramTypes);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Invokes a method with exactly the given arguments and casts the result.
     *
     * @param target The target value
     * @param methodName The method name
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeExactMethodAs(@Nullable Object target, @Nullable String methodName, @Nullable Class<T> expectType) {
        return invokeExactMethodAs(target, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, expectType);
    }

    /**
     * Invokes a method with exactly the given arguments and casts the result.
     *
     * @param target The target value
     * @param methodName The method name
     * @param args The invocation arguments
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeExactMethodAs(@Nullable Object target, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<T> expectType) {
        return invokeExactMethodAs(target, methodName, args, null, expectType);
    }

    /**
     * Invokes a method with exactly the given arguments and casts the result.
     *
     * @param target The target value
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeExactMethodAs(@Nullable Object target, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes, @Nullable Class<T> expectType) {
        if (target == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(invokeExactMethod(target, methodName, args, paramTypes), expectType);
    }

    /**
     * Invokes a static method with exactly the given arguments.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeExactStaticMethod(@Nullable Class<?> clazz, @Nullable String methodName) {
        return invokeExactStaticMethod(clazz, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    /**
     * Invokes a static method with exactly the given arguments.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param args The invocation arguments
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeExactStaticMethod(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Object... args) {
        return invokeExactStaticMethod(clazz, methodName, args, null);
    }

    /**
     * Invokes a static method with exactly the given arguments.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static Object invokeExactStaticMethod(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes) {
        if (clazz == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        try {
            return MethodUtils.invokeExactStaticMethod(clazz, methodName, args, paramTypes);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Invokes a static method with exactly the given arguments and casts the result.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeExactStaticMethodAs(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Class<T> expectType) {
        return invokeExactStaticMethodAs(clazz, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, expectType);
    }

    /**
     * Invokes a static method with exactly the given arguments and casts the result.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param args The invocation arguments
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeExactStaticMethodAs(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<T> expectType) {
        return invokeExactStaticMethodAs(clazz, methodName, args, null, expectType);
    }

    /**
     * Invokes a static method with exactly the given arguments and casts the result.
     *
     * @param clazz The class to inspect
     * @param methodName The method name
     * @param args The invocation arguments
     * @param paramTypes The parameter types
     * @param expectType The expected type
     *
     * @return the result, or null when it is unavailable
     */
    @Nullable
    public static <T> T invokeExactStaticMethodAs(@Nullable Class<?> clazz, @Nullable String methodName, @Nullable Object[] args, @Nullable Class<?>[] paramTypes, @Nullable Class<T> expectType) {
        if (clazz == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(invokeExactStaticMethod(clazz, methodName, args, paramTypes), expectType);
    }

    /**
     * Checks whether the method parameters can be assigned from the given types.
     *
     * @param method The method to inspect or invoke
     * @param paramTypes The parameter types
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isParamAssignable(@Nullable Method method, @Nullable Class<?>... paramTypes) {
        return method != null && ClassUtils.isAssignable(paramTypes, method.getParameterTypes());
    }

    /**
     * Checks whether the method parameters can be assigned from the given values.
     *
     * @param method The method to inspect or invoke
     * @param subObjects The values to subtract
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isParamAssignableValue(@Nullable Method method, @Nullable Object[] subObjects) {
        return method != null && ClassUtilsWraps.isAssignableValue(method.getParameterTypes(), subObjects);
    }

    /**
     * Checks whether the method is public.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isPublic(@Nullable Method method) {
        return method != null && Modifier.isPublic(method.getModifiers());
    }

    /**
     * Checks whether the method is static.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isStatic(@Nullable Method method) {
        return method != null && Modifier.isStatic(method.getModifiers());
    }

    /**
     * Checks whether the method is final.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isFinal(@Nullable Method method) {
        return method != null && Modifier.isFinal(method.getModifiers());
    }

    /**
     * Checks whether the method declares exceptions.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isThrows(@Nullable Method method) {
        return method != null && ArrayUtils.isNotEmpty(method.getExceptionTypes());
    }

    /**
     * Checks whether the method is public and static.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isPublicStatic(@Nullable Method method) {
        return isPublic(method) && isStatic(method);
    }

    /**
     * Checks whether the method is public and declares exceptions.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isPublicThrows(@Nullable Method method) {
        return isPublic(method) && isThrows(method);
    }

    /**
     * Checks whether the method is public, static and final.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isPublicStaticFinal(@Nullable Method method) {
        return isPublic(method) && isStatic(method) && isFinal(method);
    }

    /**
     * Checks whether the method is public, static and declares exceptions.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isPublicStaticThrows(@Nullable Method method) {
        return isPublic(method) && isStatic(method) && isThrows(method);
    }

    /**
     * Checks whether the method is user defined.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean isUserDefined(@Nullable Method method) {
        return method != null && !method.isBridge() && !method.isSynthetic() && method.getDeclaringClass() != Object.class;
    }

    /**
     * Checks whether the subclass overrides the specified method.
     *
     * @param superclass The superclass or boundary class
     * @param subclass The subclass to inspect
     * @param methodName The method name
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static <A, B extends A> boolean isUserOverride(@Nullable Class<A> superclass, @Nullable Class<B> subclass, @Nullable String methodName) {
        return isUserOverride(superclass, subclass, methodName, ArrayUtils.EMPTY_CLASS_ARRAY);
    }

    /**
     * Checks whether the subclass overrides the specified method.
     *
     * @param superclass The superclass or boundary class
     * @param subclass The subclass to inspect
     * @param methodName The method name
     * @param paramTypes The parameter types
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <A, B extends A> boolean isUserOverride(@Nullable Class<A> superclass, @Nullable Class<B> subclass, @Nullable String methodName, @Nullable Class<?>... paramTypes) {
        if (ObjectUtils.anyNull(superclass, subclass) || superclass == subclass || StringUtils.isBlank(methodName)) {
            return false;
        }
        Method superMethod = findMethod(superclass, methodName, paramTypes), subMethod = findMethod(subclass, methodName, paramTypes);
        return superMethod.getDeclaringClass() != subMethod.getDeclaringClass();
    }

    /**
     * @see "org.apache.commons.lang3.reflect.MemberUtils#setAccessibleWorkaround"
     * @see "org.springframework.util.ReflectionUtils#makeAccessible"
     */
    public static void makeAccessible(@Nullable Method method) {
        if (method == null) {
            return;
        }
        if (!method.canAccess(null) && (!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))) {
            method.setAccessible(true);
        }
    }

    /**
     * Checks whether the method returns a generic type.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean returnGeneric(@Nullable Method method) {
        return method != null && !TypeUtils.equals(method.getGenericReturnType(), method.getReturnType());
    }

    /**
     * Checks whether the method returns a primitive type.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean returnPrimitive(@Nullable Method method) {
        return method != null && method.getReturnType().isPrimitive();
    }

    /**
     * Checks whether the method returns void.
     *
     * @param method The method to inspect or invoke
     *
     * @return {@code true} when the condition is satisfied; {@code false} otherwise
     */
    public static boolean returnVoid(@Nullable Method method) {
        return method != null && method.getReturnType() == Void.TYPE;
    }
}
