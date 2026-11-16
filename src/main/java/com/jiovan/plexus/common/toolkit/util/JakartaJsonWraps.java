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


import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import jakarta.annotation.Nullable;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonStructure;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * Utilities for {@link jakarta.json.JsonStructure}
 *
 * @author David Hsing
 *
 * @see jakarta.json.JsonStructure
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JakartaJsonWraps {
    /**
     * Parses JSON content into a JsonStructure.
     *
     * @param content The JSON content
     *
     * @return the parsed structure, or null when parsing fails
     */
    @Nullable
    public static JsonStructure ofJson(@Nullable String content) {
        return StringUtils.isBlank(content) ? null : ofJson(new StringReader(content));
    }

    /**
     * Parses JSON content into a JsonStructure.
     *
     * @param file The source file
     *
     * @return the parsed structure, or null when parsing fails
     */
    @Nullable
    public static JsonStructure ofJson(@Nullable File file) {
        return ofJson(file, null);
    }

    /**
     * Parses JSON content into a JsonStructure.
     *
     * @param file The source file
     * @param charset The charset
     *
     * @return the parsed structure, or null when parsing fails
     */
    @Nullable
    public static JsonStructure ofJson(@Nullable File file, @Nullable Charset charset) {
        try {
            return (file == null) ? null : ofJson(new FileReader(file, ObjectUtils.getIfNull(charset, StandardCharsets.UTF_8)));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Parses JSON content into a JsonStructure.
     *
     * @param stream The input stream
     *
     * @return the parsed structure, or null when parsing fails
     */
    @Nullable
    public static JsonStructure ofJson(@Nullable InputStream stream) {
        return ofJson(stream, null);
    }

    /**
     * Parses JSON content into a JsonStructure.
     *
     * @param stream The input stream
     * @param charset The charset
     *
     * @return the parsed structure, or null when parsing fails
     */
    @Nullable
    public static JsonStructure ofJson(@Nullable InputStream stream, @Nullable Charset charset) {
        try {
            return (stream == null) ? null : ofJson(new InputStreamReader(stream, ObjectUtils.getIfNull(charset, StandardCharsets.UTF_8)));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Parses JSON content into a JsonStructure.
     *
     * @param reader The source reader
     *
     * @return the parsed structure, or null when parsing fails
     */
    @Nullable
    public static JsonStructure ofJson(@Nullable Reader reader) {
        if (reader == null) {
            return null;
        }
        try (JsonReader jsonReader = Json.createReader(reader)) {
            return jsonReader.read();
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Parses JSON content into an object of the expected type.
     *
     * @param content The JSON content
     * @param expectType The expected result type
     *
     * @return the parsed object, or null when parsing fails
     */
    @Nullable
    public static <T> T ofJsonAs(@Nullable String content, @Nullable Class<T> expectType) {
        return ofJsonAs(content, expectType, null);
    }

    /**
     * Parses JSON content into an object of the expected type.
     *
     * @param content The JSON content
     * @param expectType The expected result type
     * @param config The JSON-B configuration
     *
     * @return the parsed object, or null when parsing fails
     */
    @Nullable
    public static <T> T ofJsonAs(@Nullable String content, @Nullable Class<T> expectType, @Nullable JsonbConfig config) {
        if (StringUtils.isBlank(content) || expectType == null) {
            return null;
        }
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            return jsonb.fromJson(content, expectType);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Parses JSON content into an object of the expected type.
     *
     * @param file The source file
     * @param expectType The expected result type
     *
     * @return the parsed object, or null when parsing fails
     */
    @Nullable
    public static <T> T ofJsonAs(@Nullable File file, @Nullable Class<T> expectType) {
        return ofJsonAs(file, expectType, null, null);
    }

    /**
     * Parses JSON content into an object of the expected type.
     *
     * @param file The source file
     * @param expectType The expected result type
     * @param charset The charset
     *
     * @return the parsed object, or null when parsing fails
     */
    @Nullable
    public static <T> T ofJsonAs(@Nullable File file, @Nullable Class<T> expectType, @Nullable Charset charset) {
        return ofJsonAs(file, expectType, null, charset);
    }

    /**
     * Parses JSON content into an object of the expected type.
     *
     * @param file The source file
     * @param expectType The expected result type
     * @param config The JSON-B configuration
     * @param charset The charset
     *
     * @return the parsed object, or null when parsing fails
     */
    @Nullable
    public static <T> T ofJsonAs(@Nullable File file, @Nullable Class<T> expectType, @Nullable JsonbConfig config, @Nullable Charset charset) {
        if (ObjectUtils.anyNull(file, expectType)) {
            return null;
        }
        String content = FileUtilsWraps.readFileToString(file, ObjectUtils.getIfNull(charset, StandardCharsets.UTF_8));
        if (StringUtils.isBlank(content)) {
            return null;
        }
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            return jsonb.fromJson(content, expectType);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Parses JSON content into an object of the expected type.
     *
     * @param stream The input stream
     * @param expectType The expected result type
     *
     * @return the parsed object, or null when parsing fails
     */
    @Nullable
    public static <T> T ofJsonAs(@Nullable InputStream stream, @Nullable Class<T> expectType) {
        return ofJsonAs(stream, expectType, null);
    }

    /**
     * Parses JSON content into an object of the expected type.
     *
     * @param stream The input stream
     * @param expectType The expected result type
     * @param config The JSON-B configuration
     *
     * @return the parsed object, or null when parsing fails
     */
    @Nullable
    public static <T> T ofJsonAs(@Nullable InputStream stream, @Nullable Class<T> expectType, @Nullable JsonbConfig config) {
        if (ObjectUtils.anyNull(stream, expectType)) {
            return null;
        }
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            return jsonb.fromJson(stream, expectType);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Parses JSON content into an object of the expected type.
     *
     * @param reader The source reader
     * @param expectType The expected result type
     *
     * @return the parsed object, or null when parsing fails
     */
    @Nullable
    public static <T> T ofJsonAs(@Nullable Reader reader, @Nullable Class<T> expectType) {
        return ofJsonAs(reader, expectType, null);
    }

    /**
     * Parses JSON content into an object of the expected type.
     *
     * @param reader The source reader
     * @param expectType The expected result type
     * @param config The JSON-B configuration
     *
     * @return the parsed object, or null when parsing fails
     */
    @Nullable
    public static <T> T ofJsonAs(@Nullable Reader reader, @Nullable Class<T> expectType, @Nullable JsonbConfig config) {
        if (ObjectUtils.anyNull(reader, expectType)) {
            return null;
        }
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            return jsonb.fromJson(reader, expectType);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Serializes the object to JSON.
     *
     * @param source The source object
     *
     * @return the JSON string, or null when serialization fails
     */
    @Nullable
    public static String toJson(@Nullable Object source) {
        return toJson(source, null);
    }

    /**
     * Serializes the object to JSON.
     *
     * @param source The source object
     * @param config The JSON-B configuration
     *
     * @return the JSON string, or null when serialization fails
     */
    @Nullable
    public static String toJson(@Nullable Object source, @Nullable JsonbConfig config) {
        if (source == null) {
            return null;
        }
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            return jsonb.toJson(source);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads a JSON structure from the reader.
     *
     * @param reader The source reader
     *
     * @return the structure, or null when reading fails
     */
    @Nullable
    public static JsonStructure readStructure(@Nullable JsonReader reader) {
        if (reader == null) {
            return null;
        }
        try {
            return reader.read();
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads a JSON array from the reader.
     *
     * @param reader The source reader
     *
     * @return the array, or null when reading fails
     */
    @Nullable
    public static JsonArray readArray(@Nullable JsonReader reader) {
        if (reader == null) {
            return null;
        }
        try {
            return reader.readArray();
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads a JSON object from the reader.
     *
     * @param reader The source reader
     *
     * @return the object, or null when reading fails
     */
    @Nullable
    public static JsonObject readObject(@Nullable JsonReader reader) {
        if (reader == null) {
            return null;
        }
        try {
            return reader.readObject();
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads a JSON value from the reader.
     *
     * @param reader The source reader
     *
     * @return the value, or null when reading fails
     */
    @Nullable
    public static JsonValue readValue(@Nullable JsonReader reader) {
        if (reader == null) {
            return null;
        }
        try {
            return reader.readValue();
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a boolean value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     *
     * @return the boolean value, or the default value when it is absent
     */
    public static Boolean getBoolean(@Nullable JsonArray instance, int index) {
        return getBoolean(instance, index, null);
    }

    /**
     * Returns a boolean value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     * @param defaultValue The value returned when the element is absent
     *
     * @return the boolean value, or the default value when it is absent
     */
    public static Boolean getBoolean(@Nullable JsonArray instance, int index, Boolean defaultValue) {
        if (instance == null || index < 0) {
            return null;
        }
        try {
            return instance.getBoolean(index, defaultValue);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a boolean value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     *
     * @return the boolean value, or the default value when it is absent
     */
    public static Boolean getBoolean(@Nullable JsonObject instance, @Nullable String name) {
        return getBoolean(instance, name, null);
    }

    /**
     * Returns a boolean value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     * @param defaultValue The value returned when the element is absent
     *
     * @return the boolean value, or the default value when it is absent
     */
    public static Boolean getBoolean(@Nullable JsonObject instance, @Nullable String name, Boolean defaultValue) {
        if (instance == null || StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return instance.getBoolean(name, defaultValue);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns an integer value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     *
     * @return the integer value, or the default value when it is absent
     */
    public static Integer getInteger(@Nullable JsonArray instance, int index) {
        return getInteger(instance, index, null);
    }

    /**
     * Returns an integer value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     * @param defaultValue The value returned when the element is absent
     *
     * @return the integer value, or the default value when it is absent
     */
    public static Integer getInteger(@Nullable JsonArray instance, int index, Integer defaultValue) {
        if (instance == null || index < 0) {
            return null;
        }
        try {
            return instance.getInt(index, defaultValue);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns an integer value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     *
     * @return the integer value, or the default value when it is absent
     */
    public static Integer getInteger(@Nullable JsonObject instance, @Nullable String name) {
        return getInteger(instance, name, null);
    }

    /**
     * Returns an integer value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     * @param defaultValue The value returned when the element is absent
     *
     * @return the integer value, or the default value when it is absent
     */
    public static Integer getInteger(@Nullable JsonObject instance, @Nullable String name, Integer defaultValue) {
        if (instance == null || StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return instance.getInt(name, defaultValue);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a string value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     *
     * @return the string value, or the default value when it is absent
     */
    @Nullable
    public static String getString(@Nullable JsonArray instance, int index) {
        return getString(instance, index, null);
    }

    /**
     * Returns a string value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     * @param defaultValue The value returned when the element is absent
     *
     * @return the string value, or the default value when it is absent
     */
    @Nullable
    public static String getString(@Nullable JsonArray instance, int index, @Nullable String defaultValue) {
        if (instance == null || index < 0) {
            return null;
        }
        try {
            return instance.getString(index, defaultValue);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a string value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     *
     * @return the string value, or the default value when it is absent
     */
    @Nullable
    public static String getString(@Nullable JsonObject instance, @Nullable String name) {
        return getString(instance, name, null);
    }

    /**
     * Returns a string value from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     * @param defaultValue The value returned when the element is absent
     *
     * @return the string value, or the default value when it is absent
     */
    @Nullable
    public static String getString(@Nullable JsonObject instance, @Nullable String name, @Nullable String defaultValue) {
        if (instance == null || StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return instance.getString(name, defaultValue);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a JsonArray from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     *
     * @return the array, or null when it is absent
     */
    @Nullable
    public static JsonArray getJsonArray(@Nullable JsonArray instance, int index) {
        if (instance == null || index < 0) {
            return null;
        }
        try {
            return instance.getJsonArray(index);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a JsonArray from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     *
     * @return the array, or null when it is absent
     */
    @Nullable
    public static JsonArray getJsonArray(@Nullable JsonObject instance, @Nullable String name) {
        if (instance == null || StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return instance.getJsonArray(name);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a JsonNumber from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     *
     * @return the number, or null when it is absent
     */
    @Nullable
    public static JsonNumber getJsonNumber(@Nullable JsonArray instance, int index) {
        if (instance == null || index < 0) {
            return null;
        }
        try {
            return instance.getJsonNumber(index);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a JsonNumber from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     *
     * @return the number, or null when it is absent
     */
    @Nullable
    public static JsonNumber getJsonNumber(@Nullable JsonObject instance, @Nullable String name) {
        if (instance == null || StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return instance.getJsonNumber(name);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a JsonObject from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     *
     * @return the object, or null when it is absent
     */
    @Nullable
    public static JsonObject getJsonObject(@Nullable JsonArray instance, int index) {
        if (instance == null || index < 0) {
            return null;
        }
        try {
            return instance.getJsonObject(index);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a JsonObject from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     *
     * @return the object, or null when it is absent
     */
    @Nullable
    public static JsonObject getJsonObject(@Nullable JsonObject instance, @Nullable String name) {
        if (instance == null || StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return instance.getJsonObject(name);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a JsonString from the JSON instance.
     *
     * @param instance The JSON instance
     * @param index The array index
     *
     * @return the JSON string, or null when it is absent
     */
    @Nullable
    public static JsonString getJsonString(@Nullable JsonArray instance, int index) {
        if (instance == null || index < 0) {
            return null;
        }
        try {
            return instance.getJsonString(index);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a JsonString from the JSON instance.
     *
     * @param instance The JSON instance
     * @param name The property name
     *
     * @return the JSON string, or null when it is absent
     */
    @Nullable
    public static JsonString getJsonString(@Nullable JsonObject instance, @Nullable String name) {
        if (instance == null || StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return instance.getJsonString(name);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a named value from the JSON structure.
     *
     * @param structure The JSON structure
     * @param name The property name
     *
     * @return the value, or null when it is absent
     */
    @Nullable
    public static JsonValue getJsonValue(@Nullable JsonStructure structure, @Nullable String name) {
        return (structure == null || StringUtils.isBlank(name)) ? null : structure.getValue(name);
    }

    /**
     * Returns all values of the JSON array.
     *
     * @param instance The JSON instance
     *
     * @return a list of values, or null when the array is null
     */
    @Nullable
    public static List<JsonValue> getValues(@Nullable JsonArray instance) {
        return (instance == null) ? null : instance.getValuesAs(JsonValue.class);
    }

    /**
     * Returns the JSON array values converted to the requested type.
     *
     * @param instance The JSON instance
     * @param clazz The expected value type
     *
     * @return a list of converted values, or null when the array is null
     */
    @Nullable
    public static <T extends JsonValue> List<T> getValuesAs(@Nullable JsonArray instance, @Nullable Class<T> clazz) {
        return ObjectUtils.anyNull(instance, clazz) ? null : instance.getValuesAs(clazz);
    }

    /**
     * Returns the JSON array values converted to the requested type.
     *
     * @param instance The JSON instance
     * @param func The function used to convert values
     *
     * @return a list of converted values, or null when the array is null
     */
    @Nullable
    public static <T, K extends JsonValue> List<T> getValuesAs(@Nullable JsonArray instance, @Nullable Function<K, T> func) {
        return ObjectUtils.anyNull(instance, func) ? null : instance.getValuesAs(func);
    }

    /**
     * Returns the JSON array values as strings.
     *
     * @param instance The JSON instance
     *
     * @return a list of strings, or null when the array is null
     */
    @Nullable
    public static List<String> getValuesAsString(@Nullable JsonArray instance) {
        return (instance == null) ? null : instance.getValuesAs(JsonString::getString);
    }

    /**
     * Returns the JSON array values as integers.
     *
     * @param instance The JSON instance
     *
     * @return a list of integers, or null when the array is null
     */
    @Nullable
    public static List<Integer> getValuesAsInteger(@Nullable JsonArray instance) {
        return (instance == null) ? null : instance.getValuesAs(JsonNumber::intValue);
    }

    /**
     * Checks whether the JSON value represents null.
     *
     * @param instance The JSON instance
     * @param index The array index
     *
     * @return true when the value is null; false otherwise
     */
    public static Boolean isNull(@Nullable JsonArray instance, int index) {
        if (instance == null || index < 0) {
            return null;
        }
        try {
            return instance.isNull(index);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Checks whether the JSON value represents null.
     *
     * @param instance The JSON instance
     * @param name The property name
     *
     * @return true when the value is null; false otherwise
     */
    public static Boolean isNull(@Nullable JsonObject instance, @Nullable String name) {
        if (instance == null || StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return instance.isNull(name);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Checks whether the JSON structure is an array.
     *
     * @param value The JSON value
     *
     * @return true when the structure is an array; false otherwise
     */
    public static boolean isArrayType(@Nullable JsonValue value) {
        return value != null && value.getValueType() == JsonValue.ValueType.ARRAY;
    }

    /**
     * Checks whether the JSON structure is an object.
     *
     * @param value The JSON value
     *
     * @return true when the structure is an object; false otherwise
     */
    public static boolean isObjectType(@Nullable JsonValue value) {
        return value != null && value.getValueType() == JsonValue.ValueType.OBJECT;
    }

    /**
     * Checks whether the value type is JSON null.
     *
     * @param value The JSON value
     *
     * @return true when the value type is null; false otherwise
     */
    public static boolean isNullType(@Nullable JsonValue value) {
        return value != null && value.getValueType() == JsonValue.ValueType.NULL;
    }
}
