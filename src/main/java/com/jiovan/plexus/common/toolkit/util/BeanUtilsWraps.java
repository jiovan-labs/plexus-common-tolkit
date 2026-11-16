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


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import jakarta.annotation.Nullable;
import org.apache.commons.beanutils2.BeanIntrospector;
import org.apache.commons.beanutils2.BeanMap;
import org.apache.commons.beanutils2.BeanUtilsBean;
import org.apache.commons.beanutils2.ConvertUtils;
import org.apache.commons.beanutils2.Converter;
import org.apache.commons.beanutils2.FluentPropertyBeanIntrospector;
import org.apache.commons.beanutils2.PropertyUtils;
import org.apache.commons.beanutils2.PropertyUtilsBean;
import org.apache.commons.beanutils2.converters.ArrayConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import com.jiovan.plexus.common.toolkit.annotation.BeanCopyIgnore;
import com.jiovan.plexus.common.toolkit.annotation.ViewTransferIgnore;
import com.jiovan.plexus.common.toolkit.constant.JavaKeywordConst;
import com.jiovan.plexus.common.toolkit.converter.apache.JdkDateConverter;
import com.jiovan.plexus.common.toolkit.converter.apache.LocalDateConverter;
import com.jiovan.plexus.common.toolkit.converter.apache.LocalDateTimeConverter;
import com.jiovan.plexus.common.toolkit.converter.apache.LocalTimeConverter;
import com.jiovan.plexus.common.toolkit.converter.apache.SqlDateConverter;
import com.jiovan.plexus.common.toolkit.exception.BeanInvocationException;
import com.jiovan.plexus.common.toolkit.structure.BooleanDataStruct;


/**
 * Utilities for {@link org.apache.commons.beanutils2.BeanUtils}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.beanutils2.BeanUtils
 * @see org.apache.commons.beanutils2.BeanUtilsBean
 * @see org.apache.commons.beanutils2.BeanMap
 * @see org.apache.commons.beanutils2.ConvertUtils
 * @see org.apache.commons.beanutils2.ConvertUtilsBean
 *
 * @see "https://gitbox.apache.org/repos/asf?p=commons-beanutils.git;a=tree"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class BeanUtilsWraps {
    public static final BeanUtilsBean BEAN_UTILS_INSTANCE = new BeanUtilsBean();

    static {
        initPropertyIntrospectors(BEAN_UTILS_INSTANCE);
    }

    /**
     * Registers the fluent property bean introspector.
     *
     * The introspector is registered on the given BeanUtilsBean, or globally when no bean is provided.
     *
     * @param bean The BeanUtilsBean instance, or null for the global instance
     */
    public static void initPropertyIntrospectors(@Nullable BeanUtilsBean bean) {
        if (bean == null) {
            PropertyUtils.addBeanIntrospector(new FluentPropertyBeanIntrospector());
        } else {
            bean.getPropertyUtils().addBeanIntrospector(new FluentPropertyBeanIntrospector());
        }
    }

    /**
     * @see org.apache.commons.beanutils2.BeanIntrospector
     */
    public static boolean containsPropertyIntrospector(@Nullable Class<? extends BeanIntrospector> clazz, @Nullable BeanUtilsBean bean) {
        if (clazz == null) {
            return false;
        }
        PropertyUtilsBean alias = (bean != null) ? bean.getPropertyUtils() : BeanUtilsBean.getInstance().getPropertyUtils();
        if (alias == null) {
            return false;
        }
        List<?> introspectors = FieldUtilsWraps.readDeclaredFieldAs(alias, "introspectors", true, List.class);    // $NON-NLS-1$
        return CollectionPlainWraps.isNotEmpty(introspectors) && introspectors.stream().filter(Objects::nonNull).anyMatch(item -> ClassUtilsWraps.isAssignableValue(clazz, item));
    }

    /**
     * Copies properties from the source bean to the target bean.
     *
     * @param target The target bean
     * @param source The source bean
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void copyProperties(@Nullable Object target, @Nullable Object source) throws BeanInvocationException {
        if (ObjectUtils.anyNull(target, source)) {
            return;
        }
        mapToBean(target, new BeanMap(source));
    }

    /**
     * Copies properties from the source bean to the target bean.
     *
     * @param target The target bean
     * @param source The source bean
     * @param filter The predicate used to select entries
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void copyProperties(@Nullable Object target, @Nullable Object source, BiPredicate<String, Object> filter) throws BeanInvocationException {
        if (ObjectUtils.anyNull(target, source)) {
            return;
        }
        mapToBean(target, new BeanMap(source), filter);
    }

    /**
     * Copies properties from the source bean to the target bean.
     *
     * @param target The target bean
     * @param source The source bean
     * @param action The function used to transform entries
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void copyProperties(@Nullable Object target, @Nullable Object source, BiFunction<String, Object, BooleanDataStruct<Object>> action) throws BeanInvocationException {
        if (ObjectUtils.anyNull(target, source)) {
            return;
        }
        mapToBean(target, new BeanMap(source), action);
    }

    /**
     * Copies properties from the source bean to the target bean, excluding the specified fields.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void copyPropertiesExclusive(@Nullable Object target, @Nullable Object source, @Nullable String... fields) throws BeanInvocationException {
        copyPropertiesExclusive(target, source, ArrayUtilsWraps.asList(fields), false);
    }

    /**
     * Copies properties from the source bean to the target bean, excluding the specified fields.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void copyPropertiesExclusive(@Nullable Object target, @Nullable Object source, @Nullable Collection<String> fields) throws BeanInvocationException {
        copyPropertiesExclusive(target, source, fields, false);
    }

    /**
     * Copies properties from the source bean to the target bean, excluding the specified fields.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     * @param allowEmptyValue Whether empty values are allowed
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void copyPropertiesExclusive(@Nullable Object target, @Nullable Object source, @Nullable Collection<String> fields, boolean allowEmptyValue) throws BeanInvocationException {
        if (ObjectUtils.anyNull(target, source)) {
            return;
        }
        mapToBeanExclusive(target, new BeanMap(source), fields, allowEmptyValue);
    }

    /**
     * Copies only the specified fields from the source bean to the target bean.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void copyPropertiesInclusive(@Nullable Object target, @Nullable Object source, @Nullable String... fields) throws BeanInvocationException {
        copyPropertiesInclusive(target, source, ArrayUtilsWraps.asList(fields), false);
    }

    /**
     * Copies only the specified fields from the source bean to the target bean.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void copyPropertiesInclusive(@Nullable Object target, @Nullable Object source, @Nullable Collection<String> fields) throws BeanInvocationException {
        copyPropertiesInclusive(target, source, fields, false);
    }

    /**
     * Copies only the specified fields from the source bean to the target bean.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     * @param allowEmptyValue Whether empty values are allowed
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void copyPropertiesInclusive(@Nullable Object target, @Nullable Object source, @Nullable Collection<String> fields, boolean allowEmptyValue) throws BeanInvocationException {
        if (ObjectUtils.anyNull(target, source) || CollectionPlainWraps.isEmpty(fields)) {
            return;
        }
        mapToBeanInclusive(target, new BeanMap(source), fields, allowEmptyValue);
    }

    /**
     * Copies properties from the source bean to the target bean without throwing exceptions.
     *
     * Any exception raised while copying properties is ignored.
     *
     * @param target The target bean
     * @param source The source bean
     */
    public static void copyPropertiesQuietly(@Nullable Object target, @Nullable Object source) {
        try {
            copyProperties(target, source);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copies properties from the source bean to the target bean without throwing exceptions.
     *
     * Any exception raised while copying properties is ignored.
     *
     * @param target The target bean
     * @param source The source bean
     * @param filter The predicate used to select entries
     */
    public static void copyPropertiesQuietly(@Nullable Object target, @Nullable Object source, BiPredicate<String, Object> filter) {
        try {
            copyProperties(target, source, filter);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copies properties from the source bean to the target bean without throwing exceptions.
     *
     * Any exception raised while copying properties is ignored.
     *
     * @param target The target bean
     * @param source The source bean
     * @param action The function used to transform entries
     */
    public static void copyPropertiesQuietly(@Nullable Object target, @Nullable Object source, BiFunction<String, Object, BooleanDataStruct<Object>> action) {
        try {
            copyProperties(target, source, action);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copies properties excluding the specified fields without throwing exceptions.
     *
     * Any exception raised while copying properties is ignored.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     */
    public static void copyPropertiesExclusiveQuietly(@Nullable Object target, @Nullable Object source, @Nullable String... fields) {
        try {
            copyPropertiesExclusive(target, source, fields);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copies properties excluding the specified fields without throwing exceptions.
     *
     * Any exception raised while copying properties is ignored.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     */
    public static void copyPropertiesExclusiveQuietly(@Nullable Object target, @Nullable Object source, @Nullable Collection<String> fields) {
        try {
            copyPropertiesExclusive(target, source, fields);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copies properties excluding the specified fields without throwing exceptions.
     *
     * Any exception raised while copying properties is ignored.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     * @param allowEmptyValue Whether empty values are allowed
     */
    public static void copyPropertiesExclusiveQuietly(@Nullable Object target, @Nullable Object source, @Nullable Collection<String> fields, boolean allowEmptyValue) {
        try {
            copyPropertiesExclusive(target, source, fields, allowEmptyValue);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copies only the specified fields without throwing exceptions.
     *
     * Any exception raised while copying properties is ignored.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     */
    public static void copyPropertiesInclusiveQuietly(@Nullable Object target, @Nullable Object source, @Nullable String... fields) {
        try {
            copyPropertiesInclusive(target, source, fields);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copies only the specified fields without throwing exceptions.
     *
     * Any exception raised while copying properties is ignored.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     */
    public static void copyPropertiesInclusiveQuietly(@Nullable Object target, @Nullable Object source, @Nullable Collection<String> fields) {
        try {
            copyPropertiesInclusive(target, source, fields);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copies only the specified fields without throwing exceptions.
     *
     * Any exception raised while copying properties is ignored.
     *
     * @param target The target bean
     * @param source The source bean
     * @param fields The field names
     * @param allowEmptyValue Whether empty values are allowed
     */
    public static void copyPropertiesInclusiveQuietly(@Nullable Object target, @Nullable Object source, @Nullable Collection<String> fields, boolean allowEmptyValue) {
        try {
            copyPropertiesInclusive(target, source, fields, allowEmptyValue);
        } catch (Exception ignored) {
        }
    }

    /**
     * Converts the properties of the bean to a map.
     *
     * @param bean The bean instance
     *
     * @return a map containing the bean properties, or null when there are no properties
     */
    public static Map<String, Object> beanToMap(@Nullable Object bean) {
        return beanToMapExclusive(bean, (Collection<String>) null);
    }

    /**
     * Converts the properties of the bean to a map.
     *
     * @param bean The bean instance
     * @param keyPrefix The prefix added to property names
     * @param keySuffix The suffix added to property names
     *
     * @return a map containing the bean properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMap(@Nullable Object bean, @Nullable String keyPrefix, @Nullable String keySuffix) {
        return beanToMap(bean, keyPrefix, keySuffix, true, true);
    }

    /**
     * Converts the properties of the bean to a map.
     *
     * @param bean The bean instance
     * @param keyPrefix The prefix added to property names
     * @param keySuffix The suffix added to property names
     * @param allowClassKey Whether the class key is allowed
     * @param allowEmptyValue Whether empty values are allowed
     *
     * @return a map containing the bean properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMap(@Nullable Object bean, @Nullable String keyPrefix, @Nullable String keySuffix, boolean allowClassKey, boolean allowEmptyValue) {
        return PropertyPlainWraps.toStringObjectMap(beanToProperties(bean, keyPrefix, keySuffix, allowClassKey, allowEmptyValue));
    }

    /**
     * Converts the properties of the bean to a map.
     *
     * @param bean The bean instance
     * @param keyMapping The property name mapping
     * @param valueMapping The property value mapping
     *
     * @return a map containing the bean properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMap(@Nullable Object bean, @Nullable Map<String, String> keyMapping, @Nullable Map<Object, Object> valueMapping) {
        return beanToMap(bean, keyMapping, valueMapping, null);
    }

    /**
     * Converts the properties of the bean to a map.
     *
     * @param bean The bean instance
     * @param filer The predicate used to select entries
     *
     * @return a map containing the bean properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMap(@Nullable Object bean, @Nullable BiPredicate<String, Object> filer) {
        return beanToMap(bean, null, null, filer);
    }

    /**
     * Converts the properties of the bean to a map.
     *
     * @param bean The bean instance
     * @param keyMapping The property name mapping
     * @param valueMapping The property value mapping
     * @param filer The predicate used to select entries
     *
     * @return a map containing the bean properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMap(@Nullable Object bean, @Nullable Map<String, String> keyMapping, @Nullable Map<Object, Object> valueMapping, @Nullable BiPredicate<String, Object> filer) {
        return PropertyPlainWraps.toStringObjectMap(beanToProperties(bean, keyMapping, valueMapping, filer));
    }

    /**
     * Converts the properties of the bean to a map, excluding the specified fields.
     *
     * @param bean The bean instance
     * @param fields The field names
     *
     * @return a map containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMapExclusive(@Nullable Object bean, @Nullable String... fields) {
        return beanToMapExclusive(bean, ArrayUtilsWraps.asList(fields), true, true);
    }

    /**
     * Converts the properties of the bean to a map, excluding the specified fields.
     *
     * @param bean The bean instance
     * @param fields The field names
     *
     * @return a map containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMapExclusive(@Nullable Object bean, @Nullable Collection<String> fields) {
        return beanToMapExclusive(bean, fields, true, true);
    }

    /**
     * Converts the properties of the bean to a map, excluding the specified fields.
     *
     * @param bean The bean instance
     * @param fields The field names
     * @param allowClassKey Whether the class key is allowed
     * @param allowEmptyValue Whether empty values are allowed
     *
     * @return a map containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMapExclusive(@Nullable Object bean, @Nullable Collection<String> fields, boolean allowClassKey, boolean allowEmptyValue) {
        return PropertyPlainWraps.toStringObjectMap(beanToPropertiesExclusive(bean, fields, allowClassKey, allowEmptyValue));
    }

    /**
     * Converts only the specified properties of the bean to a map.
     *
     * @param bean The bean instance
     * @param fields The field names
     *
     * @return a map containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMapInclusive(@Nullable Object bean, @Nullable String... fields) {
        return beanToMapInclusive(bean, ArrayUtilsWraps.asList(fields), true, true);
    }

    /**
     * Converts only the specified properties of the bean to a map.
     *
     * @param bean The bean instance
     * @param fields The field names
     *
     * @return a map containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMapInclusive(@Nullable Object bean, @Nullable Collection<String> fields) {
        return beanToMapInclusive(bean, fields, true, true);
    }

    /**
     * Converts only the specified properties of the bean to a map.
     *
     * @param bean The bean instance
     * @param fields The field names
     * @param allowClassKey Whether the class key is allowed
     * @param allowEmptyValue Whether empty values are allowed
     *
     * @return a map containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Map<String, Object> beanToMapInclusive(@Nullable Object bean, @Nullable Collection<String> fields, boolean allowClassKey, boolean allowEmptyValue) {
        return PropertyPlainWraps.toStringObjectMap(beanToPropertiesInclusive(bean, fields, allowClassKey, allowEmptyValue));
    }

    /**
     * Converts the properties of the bean to a Properties object.
     *
     * @param bean The bean instance
     *
     * @return a Properties object containing the bean properties, or null when there are no properties
     */
    @Nullable
    public static Properties beanToProperties(@Nullable Object bean) {
        return beanToPropertiesExclusive(bean, (Collection<String>) null);
    }

    /**
     * Converts the properties of the bean to a Properties object.
     *
     * @param bean The bean instance
     * @param keyMapping The property name mapping
     * @param valueMapping The property value mapping
     *
     * @return a Properties object containing the bean properties, or null when there are no properties
     */
    @Nullable
    public static Properties beanToProperties(@Nullable Object bean, @Nullable Map<String, String> keyMapping, @Nullable Map<Object, Object> valueMapping) {
        return beanToProperties(bean, keyMapping, valueMapping, null);
    }

    /**
     * Converts the properties of the bean to a Properties object.
     *
     * @param bean The bean instance
     * @param filer The predicate used to select entries
     *
     * @return a Properties object containing the bean properties, or null when there are no properties
     */
    @Nullable
    public static Properties beanToProperties(@Nullable Object bean, @Nullable BiPredicate<String, Object> filer) {
        return beanToProperties(bean, null, null, filer);
    }

    /**
     * Converts the properties of the bean to a Properties object.
     *
     * @param bean The bean instance
     * @param keyMapping The property name mapping
     * @param valueMapping The property value mapping
     * @param filer The predicate used to select entries
     *
     * @return a Properties object containing the bean properties, or null when there are no properties
     */
    @Nullable
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public static Properties beanToProperties(@Nullable Object bean, @Nullable Map<String, String> keyMapping, @Nullable Map<Object, Object> valueMapping, @Nullable BiPredicate<String, Object> filer) {
        if (bean == null) {
            return null;
        }
        BeanMap map = new BeanMap(bean);
        if (MapPlainWraps.isEmpty(map)) {
            return null;
        }
        Properties properties = new Properties();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (filer == null || filer.test(entry.getKey(), entry.getValue())) {
                properties.put(MapPlainWraps.getString(keyMapping, entry.getKey(), entry.getKey()), MapPlainWraps.getObject(valueMapping, entry.getValue(), entry.getValue()));
            }
        }
        return MapPlainWraps.isEmpty(properties) ? null : properties;
    }

    /**
     * Converts the properties of the bean to a Properties object.
     *
     * @param bean The bean instance
     * @param keyPrefix The prefix added to property names
     * @param keySuffix The suffix added to property names
     *
     * @return a Properties object containing the bean properties, or null when there are no properties
     */
    @Nullable
    public static Properties beanToProperties(@Nullable Object bean, @Nullable CharSequence keyPrefix, @Nullable CharSequence keySuffix) {
        return beanToProperties(bean, keyPrefix, keySuffix, true, true);
    }

    /**
     * Converts the properties of the bean to a Properties object.
     *
     * @param bean The bean instance
     * @param keyPrefix The prefix added to property names
     * @param keySuffix The suffix added to property names
     * @param allowClassKey Whether the class key is allowed
     * @param allowEmptyValue Whether empty values are allowed
     *
     * @return a Properties object containing the bean properties, or null when there are no properties
     */
    @Nullable
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public static Properties beanToProperties(@Nullable Object bean, @Nullable CharSequence keyPrefix, @Nullable CharSequence keySuffix, boolean allowClassKey, boolean allowEmptyValue) {
        if (bean == null) {
            return null;
        }
        BeanMap map = new BeanMap(bean);
        if (MapPlainWraps.isEmpty(map)) {
            return null;
        }
        Properties properties = new Properties();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey() == null || (!allowEmptyValue && ObjectUtils.isEmpty(entry.getValue()))) {
                continue;
            }
            if (StringUtilsWraps.equals(entry.getKey(), JavaKeywordConst.CLASS)) {
                if (allowClassKey) {
                    properties.put(entry.getKey(), entry.getValue());
                }
                continue;
            }
            properties.put(StringUtils.join(keyPrefix, entry.getKey(), keySuffix), entry.getValue());
        }
        return MapPlainWraps.isEmpty(properties) ? null : properties;
    }

    /**
     * Converts the properties of the bean to a Properties object, excluding the specified fields.
     *
     * @param bean The bean instance
     * @param fields The field names
     *
     * @return a Properties object containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Properties beanToPropertiesExclusive(@Nullable Object bean, @Nullable String... fields) {
        return beanToPropertiesExclusive(bean, ArrayUtilsWraps.asList(fields), true, true);
    }

    /**
     * Converts the properties of the bean to a Properties object, excluding the specified fields.
     *
     * @param bean The bean instance
     * @param fields The field names
     *
     * @return a Properties object containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Properties beanToPropertiesExclusive(@Nullable Object bean, @Nullable Collection<String> fields) {
        return beanToPropertiesExclusive(bean, fields, true, true);
    }

    /**
     * Converts the properties of the bean to a Properties object, excluding the specified fields.
     *
     * @param bean The bean instance
     * @param fields The field names
     * @param allowClassKey Whether the class key is allowed
     * @param allowEmptyValue Whether empty values are allowed
     *
     * @return a Properties object containing the selected properties, or null when there are no properties
     */
    @Nullable
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public static Properties beanToPropertiesExclusive(@Nullable Object bean, @Nullable Collection<String> fields, boolean allowClassKey, boolean allowEmptyValue) {
        if (bean == null) {
            return null;
        }
        BeanMap map = new BeanMap(bean);
        if (MapPlainWraps.isEmpty(map)) {
            return null;
        }
        Properties properties = new Properties();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey() == null || (!allowClassKey && StringUtilsWraps.equals(entry.getKey(), JavaKeywordConst.CLASS)) || (!allowEmptyValue && ObjectUtils.isEmpty(entry.getValue()))) {
                continue;
            }
            if (!StringUtilsWraps.equalsAny(entry.getKey(), fields)) {
                properties.put(entry.getKey(), entry.getValue());
            }
        }
        return MapPlainWraps.isEmpty(properties) ? null : properties;
    }

    /**
     * Converts only the specified properties of the bean to a Properties object.
     *
     * @param bean The bean instance
     * @param fields The field names
     *
     * @return a Properties object containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Properties beanToPropertiesInclusive(@Nullable Object bean, @Nullable String... fields) {
        return beanToPropertiesInclusive(bean, ArrayUtilsWraps.asList(fields), true, true);
    }

    /**
     * Converts only the specified properties of the bean to a Properties object.
     *
     * @param bean The bean instance
     * @param fields The field names
     *
     * @return a Properties object containing the selected properties, or null when there are no properties
     */
    @Nullable
    public static Properties beanToPropertiesInclusive(@Nullable Object bean, @Nullable Collection<String> fields) {
        return beanToPropertiesInclusive(bean, fields, true, true);
    }

    /**
     * Converts only the specified properties of the bean to a Properties object.
     *
     * @param bean The bean instance
     * @param fields The field names
     * @param allowClassKey Whether the class key is allowed
     * @param allowEmptyValue Whether empty values are allowed
     *
     * @return a Properties object containing the selected properties, or null when there are no properties
     */
    @Nullable
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public static Properties beanToPropertiesInclusive(@Nullable Object bean, @Nullable Collection<String> fields, boolean allowClassKey, boolean allowEmptyValue) {
        if (bean == null || CollectionPlainWraps.isEmpty(fields)) {
            return null;
        }
        BeanMap map = new BeanMap(bean);
        if (MapPlainWraps.isEmpty(map)) {
            return null;
        }
        Properties properties = new Properties();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey() == null || (!allowClassKey && StringUtilsWraps.equals(entry.getKey(), JavaKeywordConst.CLASS)) || (!allowEmptyValue && ObjectUtils.isEmpty(entry.getValue()))) {
                continue;
            }
            if (StringUtilsWraps.equalsAny(entry.getKey(), fields)) {
                properties.put(entry.getKey(), entry.getValue());
            }
        }
        return MapPlainWraps.isEmpty(properties) ? null : properties;
    }

    /**
     * Sets bean properties from the given map.
     *
     * @param bean The bean instance
     * @param map The source map
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void mapToBean(@Nullable Object bean, @Nullable Map<String, ?> map) throws BeanInvocationException {
        if (bean == null || MapPlainWraps.isEmpty(map)) {
            return;
        }
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            setProperty(bean, entry.getKey(), entry.getValue());
        }
    }

    /**
     * Sets bean properties from the given map.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param filter The predicate used to select entries
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void mapToBean(@Nullable Object bean, @Nullable Map<String, ?> map, BiPredicate<String, Object> filter) throws BeanInvocationException {
        if (ObjectUtils.anyNull(bean, filter) || MapPlainWraps.isEmpty(map)) {
            return;
        }
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            boolean result = filter.test(entry.getKey(), entry.getValue());
            if (result) {
                setProperty(bean, entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Sets bean properties from the given map.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param action The function used to transform entries
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void mapToBean(@Nullable Object bean, @Nullable Map<String, ?> map, BiFunction<String, Object, BooleanDataStruct<Object>> action) throws BeanInvocationException {
        if (ObjectUtils.anyNull(bean, action) || MapPlainWraps.isEmpty(map)) {
            return;
        }
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            BooleanDataStruct<Object> result = action.apply(entry.getKey(), entry.getValue());
            if (result != null && result.isSuccess()) {
                setProperty(bean, entry.getKey(), result.getData());
            }
        }
    }

    /**
     * Sets bean properties from the map, excluding fields marked with BeanCopyIgnore or ViewTransferIgnore.
     *
     * @param bean The bean instance
     * @param map The source map
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void mapToBeanSheared(@Nullable Object bean, @Nullable Map<String, ?> map) throws BeanInvocationException {
        if (bean == null || MapPlainWraps.isEmpty(map)) {
            return;
        }
        List<String> excludes = new ArrayList<>();
        FieldUtilsWraps.doWithNestedFields(bean.getClass(), field -> excludes.add(field.getName()), field -> AnnotationUtilsWraps.anyPresent(field, BeanCopyIgnore.class, ViewTransferIgnore.class));
        mapToBeanExclusive(bean, map, excludes);
    }

    /**
     * Sets bean properties from the map, excluding the specified fields.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void mapToBeanExclusive(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable String... fields) throws BeanInvocationException {
        mapToBeanExclusive(bean, map, ArrayUtilsWraps.asList(fields), false);
    }

    /**
     * Sets bean properties from the map, excluding the specified fields.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void mapToBeanExclusive(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable Collection<String> fields) throws BeanInvocationException {
        mapToBeanExclusive(bean, map, fields, false);
    }

    /**
     * @see "http://blog.csdn.net/q358543781/article/details/50176953"
     */
    public static void mapToBeanExclusive(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable Collection<String> fields, boolean allowEmptyValue) throws BeanInvocationException {
        if (bean == null || MapPlainWraps.isEmpty(map)) {
            return;
        }
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            if (StringUtils.isBlank(entry.getKey()) || StringUtilsWraps.equals(entry.getKey(), JavaKeywordConst.CLASS) || (!allowEmptyValue && ObjectUtils.isEmpty(entry.getValue()))) {
                continue;
            }
            if (!StringUtilsWraps.equalsAny(entry.getKey(), fields)) {
                setProperty(bean, entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Sets only the specified properties of the bean from the map.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void mapToBeanInclusive(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable String... fields) throws BeanInvocationException {
        mapToBeanInclusive(bean, map, ArrayUtilsWraps.asList(fields), false);
    }

    /**
     * Sets only the specified properties of the bean from the map.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     *
     * @throws com.jiovan.plexus.common.toolkit.exception.BeanInvocationException if a property cannot be accessed or set
     */
    public static void mapToBeanInclusive(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable Collection<String> fields) throws BeanInvocationException {
        mapToBeanInclusive(bean, map, fields, false);
    }

    /**
     * @see "http://blog.csdn.net/q358543781/article/details/50176953"
     */
    public static void mapToBeanInclusive(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable Collection<String> fields, boolean allowEmptyValue) throws BeanInvocationException {
        if (bean == null || MapPlainWraps.isEmpty(map) || CollectionPlainWraps.isEmpty(fields)) {
            return;
        }
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            if (StringUtils.isBlank(entry.getKey()) || StringUtilsWraps.equals(entry.getKey(), JavaKeywordConst.CLASS) || (!allowEmptyValue && ObjectUtils.isEmpty(entry.getValue()))) {
                continue;
            }
            if (StringUtilsWraps.equalsAny(entry.getKey(), fields)) {
                setProperty(bean, entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Sets bean properties from the given map without throwing exceptions.
     *
     * Any exception raised while setting properties is ignored.
     *
     * @param bean The bean instance
     * @param map The source map
     */
    public static void mapToBeanQuietly(@Nullable Object bean, @Nullable Map<String, ?> map) {
        try {
            mapToBean(bean, map);
        } catch (Exception ignored) {
        }
    }

    /**
     * Sets bean properties from the given map without throwing exceptions.
     *
     * Any exception raised while setting properties is ignored.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param filter The predicate used to select entries
     */
    public static void mapToBeanQuietly(@Nullable Object bean, @Nullable Map<String, ?> map, BiPredicate<String, Object> filter) {
        try {
            mapToBean(bean, map, filter);
        } catch (Exception ignored) {
        }
    }

    /**
     * Sets bean properties from the given map without throwing exceptions.
     *
     * Any exception raised while setting properties is ignored.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param action The function used to transform entries
     */
    public static void mapToBeanQuietly(@Nullable Object bean, @Nullable Map<String, ?> map, BiFunction<String, Object, BooleanDataStruct<Object>> action) {
        try {
            mapToBean(bean, map, action);
        } catch (Exception ignored) {
        }
    }

    /**
     * Sets bean properties from the map, excluding the specified fields, without throwing exceptions.
     *
     * Any exception raised while setting properties is ignored.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     */
    public static void mapToBeanExclusiveQuietly(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable String... fields) {
        try {
            mapToBeanExclusive(bean, map, fields);
        } catch (Exception ignored) {
        }
    }

    /**
     * Sets bean properties from the map, excluding the specified fields, without throwing exceptions.
     *
     * Any exception raised while setting properties is ignored.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     */
    public static void mapToBeanExclusiveQuietly(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable Collection<String> fields) {
        try {
            mapToBeanExclusive(bean, map, fields);
        } catch (Exception ignored) {
        }
    }

    /**
     * Sets bean properties from the map, excluding the specified fields, without throwing exceptions.
     *
     * Any exception raised while setting properties is ignored.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     * @param allowEmptyValue Whether empty values are allowed
     */
    public static void mapToBeanExclusiveQuietly(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable Collection<String> fields, boolean allowEmptyValue) {
        try {
            mapToBeanExclusive(bean, map, fields, allowEmptyValue);
        } catch (Exception ignored) {
        }
    }

    /**
     * Sets only the specified properties of the bean from the map without throwing exceptions.
     *
     * Any exception raised while setting properties is ignored.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     */
    public static void mapToBeanInclusiveQuietly(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable String... fields) {
        try {
            mapToBeanInclusive(bean, map, fields);
        } catch (Exception ignored) {
        }
    }

    /**
     * Sets only the specified properties of the bean from the map without throwing exceptions.
     *
     * Any exception raised while setting properties is ignored.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     */
    public static void mapToBeanInclusiveQuietly(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable Collection<String> fields) {
        try {
            mapToBeanInclusive(bean, map, fields);
        } catch (Exception ignored) {
        }
    }

    /**
     * Sets only the specified properties of the bean from the map without throwing exceptions.
     *
     * Any exception raised while setting properties is ignored.
     *
     * @param bean The bean instance
     * @param map The source map
     * @param fields The field names
     * @param allowEmptyValue Whether empty values are allowed
     */
    public static void mapToBeanInclusiveQuietly(@Nullable Object bean, @Nullable Map<String, ?> map, @Nullable Collection<String> fields, boolean allowEmptyValue) {
        try {
            mapToBeanInclusive(bean, map, fields, allowEmptyValue);
        } catch (Exception ignored) {
        }
    }

    /**
     * Sets the property naming {@code property} of the given {@code bean} object, with value {@code value}
     *
     * @see org.apache.commons.beanutils2.BeanUtils#setProperty
     * @see "org.springframework.beans.BeanUtils#copyProperties"
     *
     * @see "https://stackoverflow.com/questions/22743765/beanutils-not-works-for-chain-setter"
     * @see "https://blog.csdn.net/u014074757/article/details/106273150/"
     */
    public static boolean setProperty(@Nullable Object bean, @Nullable String property, @Nullable Object value) throws BeanInvocationException {
        if (bean == null || StringUtils.isBlank(property)) {
            return false;
        }
        try {
            BEAN_UTILS_INSTANCE.setProperty(bean, property, value);
            return true;
        } catch (Exception ex) {
            throw new BeanInvocationException(String.format("Could not set property '%s' value of bean '%s'", property, bean.getClass().getName()));
        }
    }

    /**
     * Sets a bean property without throwing exceptions.
     *
     * Any exception raised while setting the property is ignored.
     *
     * @param bean The bean instance
     * @param property The property name
     * @param value The property value
     *
     * @return true when the property was set; false otherwise
     */
    public static boolean setPropertyQuietly(@Nullable Object bean, @Nullable String property, @Nullable Object value) {
        try {
            return setProperty(bean, property, value);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * @see org.apache.commons.beanutils2.ConvertUtilsBean#registerArrayConverter
     */
    @SuppressWarnings({"unchecked", "JavadocReference"})
    public static <T> void registerArrayConverter(@Nullable Class<T> clazz, @Nullable Converter<T> converter, @Nullable BeanUtilsBean bean) {
        if (clazz == null || converter == null) {
            return;
        }
        Class<T[]> arrayType = (Class<T[]>) Array.newInstance(clazz, 0).getClass();
        Converter<T[]> arrayConverter = new ArrayConverter<>(arrayType, converter, 0);
        if (bean == null) {
            ConvertUtils.register(arrayConverter, arrayType);
        } else {
            bean.getConvertUtils().register(arrayConverter, arrayType);
        }
    }

    /**
     * Registers a converter for the given class.
     *
     * The converter is registered globally when no BeanUtilsBean is provided.
     *
     * @param clazz The target class
     * @param converter The converter to register
     * @param bean The BeanUtilsBean instance, or null for the global instance
     */
    public static <T> void registerClassConverter(@Nullable Class<T> clazz, @Nullable Converter<T> converter, @Nullable BeanUtilsBean bean) {
        if (clazz == null || converter == null) {
            return;
        }
        if (bean == null) {
            ConvertUtils.register(converter, clazz);
        } else {
            bean.getConvertUtils().register(converter, clazz);
        }
    }

    /**
     * Registers converters for common date and time types.
     *
     * The converters are registered globally when no BeanUtilsBean is provided.
     *
     * @param bean The BeanUtilsBean instance, or null for the global instance
     */
    public static void registerTemporalConverters(@Nullable BeanUtilsBean bean) {
        registerClassConverter(java.util.Date.class, new JdkDateConverter(), bean);
        registerClassConverter(java.sql.Date.class, new SqlDateConverter(), bean);
        registerClassConverter(java.time.LocalDate.class, new LocalDateConverter(), bean);
        registerClassConverter(java.time.LocalDateTime.class, new LocalDateTimeConverter(), bean);
        registerClassConverter(java.time.LocalTime.class, new LocalTimeConverter(), bean);
    }
}
