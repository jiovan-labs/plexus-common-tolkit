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


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;


/**
 * Utilities for {@link java.nio.file.Files}
 *
 * @author David Hsing
 * @see java.nio.file.Files
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class FilePlainWraps {
    private static final long KB = 1024L;
    private static final long MB = KB * 1024L;
    private static final long GB = MB * 1024L;
    private static final long TB = GB * 1024L;

    /**
     * Checks whether the path is hidden.
     *
     * @param path The path
     *
     * @return true when the path is hidden; false otherwise
     */
    public static boolean isHidden(@Nullable Path path) {
        if (path == null) {
            return false;
        }
        try {
            return Files.isHidden(path);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Returns the size of the given file.
     *
     * @param path The path
     *
     * @return the file size in bytes, or zero when it cannot be determined
     */
    public static long size(@Nullable Path path) {
        if (path == null) {
            return 0L;
        }
        try {
            return Files.size(path);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    /**
     * Returns the file size as a human-readable string.
     *
     * @param path The path
     *
     * @return a human-readable size string
     */
    public static String sizeToHumanReadable(@Nullable Path path) {
        return sizeToHumanReadable(size(path));
    }

    /**
     * Format bytes into a human-readable string (e.g., 1.5 GB, 256 MB, 1024 KB).
     *
     * @param bytes the number of bytes
     * @return formatted string with appropriate unit, or "0 B" if bytes is 0 or negative
     */
    @Nonnull
    public static String sizeToHumanReadable(long bytes) {
        if (bytes <= 0) {
            return "0 B";    // $NON-NLS-1$
        }
        if (bytes < KB) {
            return String.format("%d B", bytes);    // $NON-NLS-1$
        }
        if (bytes < MB) {
            return String.format("%.2f KB", bytes / (double) KB);    // $NON-NLS-1$
        }
        if (bytes < GB) {
            return String.format("%.2f MB", bytes / (double) MB);    // $NON-NLS-1$
        }
        if (bytes < TB) {
            return String.format("%.2f GB", bytes / (double) GB);    // $NON-NLS-1$
        }
        return String.format("%.2f TB", bytes / (double) TB);    // $NON-NLS-1$
    }

    /**
     * Reads all lines from the given file.
     *
     * @param path The path
     *
     * @return a stream of lines, or null when the file cannot be read
     */
    @Nullable
    public static Stream<String> lines(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.lines(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads all lines from the given file.
     *
     * @param path The path
     * @param charset The character set
     *
     * @return a stream of lines, or null when the file cannot be read
     */
    @Nullable
    public static Stream<String> lines(@Nullable Path path, @Nullable Charset charset) {
        if (path == null) {
            return null;
        }
        try {
            return Files.lines(path, CharsetPlainWraps.defaultCharset(charset));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Lists the entries of the given directory.
     *
     * @param path The path
     *
     * @return a stream of entries, or null when the directory cannot be read
     */
    @Nullable
    public static Stream<Path> list(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.list(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Finds paths under the given directory that match the glob pattern.
     *
     * @param path The path
     * @param maxDepth The maximum depth to traverse
     * @param filter The predicate used to filter results
     * @param options The options
     *
     * @return a stream of matching paths, or null when the search fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Stream<Path> find(@Nullable Path path, int maxDepth, @Nullable BiPredicate<Path, BasicFileAttributes> filter, @Nullable FileVisitOption[] options) {
        if (path == null || filter == null) {
            return null;
        }
        try {
            return Files.find(path, maxDepth, filter, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Writes bytes to the given file.
     *
     * @param path The path
     * @param lines The lines to write
     * @param charset The character set
     * @param options The options
     *
     * @return the target path, or null when the write fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path write(@Nullable Path path, @Nullable Iterable<? extends CharSequence> lines, @Nullable Charset charset, @Nullable OpenOption[] options) {
        if (path == null || lines == null) {
            return null;
        }
        try {
            return Files.write(path, lines, CharsetPlainWraps.defaultCharset(charset), options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Writes bytes to the given file.
     *
     * @param path The path
     * @param bytes The bytes to write
     * @param options The options
     *
     * @return the target path, or null when the write fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path write(@Nullable Path path, @Nullable byte[] bytes, @Nullable OpenOption[] options) {
        if (path == null || bytes == null) {
            return null;
        }
        try {
            return Files.write(path, bytes, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Writes bytes to the given file.
     *
     * @param path The path
     * @param lines The lines to write
     * @param options The options
     *
     * @return the target path, or null when the write fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path write(@Nullable Path path, @Nullable Iterable<? extends CharSequence> lines, @Nullable OpenOption[] options) {
        if (path == null || lines == null) {
            return null;
        }
        try {
            return Files.write(path, lines, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Deletes the given path.
     *
     * @param path The path
     */
    public static void delete(@Nullable Path path) {
        if (path == null) {
            return;
        }
        try {
            Files.delete(path);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copies the source path to the target path.
     *
     * @param inputStream The input stream
     * @param path The path
     * @param options The options
     *
     * @return the target path, or null when the copy fails
     */
    @SuppressWarnings("DataFlowIssue")
    public static long copy(@Nullable InputStream inputStream, @Nullable Path path, @Nullable CopyOption[] options) {
        if (inputStream == null || path == null) {
            return 0L;
        }
        try {
            return Files.copy(inputStream, path, options);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    /**
     * Copies the source path to the target path.
     *
     * @param source The source path
     * @param target The target path
     * @param options The options
     *
     * @return the target path, or null when the copy fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path copy(@Nullable Path source, @Nullable Path target, @Nullable CopyOption[] options) {
        if (source == null || target == null) {
            return null;
        }
        try {
            return Files.copy(source, target, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Copies the source path to the target path.
     *
     * @param path The path
     * @param outputStream The output stream
     *
     * @return the target path, or null when the copy fails
     */
    public static long copy(@Nullable Path path, @Nullable OutputStream outputStream) {
        if (path == null || outputStream == null) {
            return 0L;
        }
        try {
            return Files.copy(path, outputStream);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    /**
     * Reads all bytes from the given file.
     *
     * @param path The path
     *
     * @return a byte array, or null when the file cannot be read
     */
    @Nullable
    public static byte[] readAllBytes(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.readAllBytes(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a stream that walks the file tree rooted at the given path.
     *
     * @param path The path
     * @param maxDepth The maximum depth to traverse
     * @param options The options
     *
     * @return a stream of paths, or null when the tree cannot be walked
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Stream<Path> walk(@Nullable Path path, int maxDepth, @Nullable FileVisitOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.walk(path, maxDepth, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a stream that walks the file tree rooted at the given path.
     *
     * @param path The path
     * @param options The options
     *
     * @return a stream of paths, or null when the tree cannot be walked
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Stream<Path> walk(@Nullable Path path, @Nullable FileVisitOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.walk(path, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Checks whether the path exists.
     *
     * @param path The path
     * @param options The options
     *
     * @return true when the path exists; false otherwise
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean exists(@Nullable Path path, @Nullable LinkOption[] options) {
        if (path == null) {
            return false;
        }
        try {
            return Files.exists(path, options);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Finds the first position where two files differ.
     *
     * @param path1 The first path
     * @param path2 The second path
     *
     * @return the mismatch position, or -1 when the files are identical
     */
    public static long mismatch(@Nullable Path path1, @Nullable Path path2) {
        if (path1 == null || path2 == null) {
            return 0L;
        }
        try {
            return Files.mismatch(path1, path2);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    /**
     * Checks whether the path is a directory.
     *
     * @param path The path
     * @param options The options
     *
     * @return true when the path is a directory; false otherwise
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean isDirectory(@Nullable Path path, @Nullable LinkOption[] options) {
        if (path == null) {
            return false;
        }
        try {
            return Files.isDirectory(path, options);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Returns the last modified time of the given path.
     *
     * @param path The path
     * @param options The options
     *
     * @return the last modified time, or null when it cannot be read
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static FileTime getLastModifiedTime(@Nullable Path path, @Nullable LinkOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.getLastModifiedTime(path, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates a directory.
     *
     * @param path The path
     * @param attrs The file attributes
     *
     * @return the directory path, or null when creation fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path createDirectory(@Nullable Path path, @Nullable FileAttribute<?>[] attrs) {
        if (path == null) {
            return null;
        }
        try {
            return Files.createDirectory(path, attrs);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Sets the last modified time of the given path.
     *
     * @param path The path
     * @param time The time value
     *
     * @return the result of the operation
     */
    @Nullable
    public static Path setLastModifiedTime(@Nullable Path path, @Nullable FileTime time) {
        if (path == null || time == null) {
            return null;
        }
        try {
            return Files.setLastModifiedTime(path, time);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates a temporary file.
     *
     * @param path The path
     * @param prefix The prefix
     * @param suffix The suffix
     * @param attrs The file attributes
     *
     * @return the temporary file path, or null when creation fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path createTempFile(@Nullable Path path, @Nullable String prefix, @Nullable String suffix, @Nullable FileAttribute<?>[] attrs) {
        if (path == null) {
            return null;
        }
        try {
            return Files.createTempFile(path, prefix, suffix, attrs);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates a temporary file.
     *
     * @param prefix The prefix
     * @param suffix The suffix
     * @param attrs The file attributes
     *
     * @return the temporary file path, or null when creation fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path createTempFile(@Nullable String prefix, @Nullable String suffix, @Nullable FileAttribute<?>[] attrs) {
        try {
            return Files.createTempFile(prefix, suffix, attrs);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Checks whether the path is a regular file.
     *
     * @param path The path
     * @param options The options
     *
     * @return true when the path is a regular file; false otherwise
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean isRegularFile(@Nullable Path path, @Nullable LinkOption[] options) {
        if (path == null) {
            return false;
        }
        try {
            return Files.isRegularFile(path, options);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Opens an input stream for the given file.
     *
     * @param path The path
     * @param options The options
     *
     * @return an input stream, or null when the file cannot be opened
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static InputStream newInputStream(@Nullable Path path, @Nullable OpenOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.newInputStream(path, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens an output stream for the given file.
     *
     * @param path The path
     * @param options The options
     *
     * @return an output stream, or null when the file cannot be opened
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static OutputStream newOutputStream(@Nullable Path path, @Nullable OpenOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.newOutputStream(path, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens a byte channel for the given file.
     *
     * @param path The path
     * @param options The options
     *
     * @return a byte channel, or null when the file cannot be opened
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static SeekableByteChannel newByteChannel(@Nullable Path path, @Nullable OpenOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.newByteChannel(path, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens a byte channel for the given file.
     *
     * @param path The path
     * @param options The options
     * @param attrs The file attributes
     *
     * @return a byte channel, or null when the file cannot be opened
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static SeekableByteChannel newByteChannel(@Nullable Path path, @Nullable Set<? extends OpenOption> options, @Nullable FileAttribute<?>[] attrs) {
        if (path == null) {
            return null;
        }
        try {
            return Files.newByteChannel(path, options, attrs);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens a directory stream for the given directory.
     *
     * @param path The path
     *
     * @return a directory stream, or null when the directory cannot be opened
     */
    @Nullable
    public static DirectoryStream<Path> newDirectoryStream(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.newDirectoryStream(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens a directory stream for the given directory.
     *
     * @param path The path
     * @param filter The predicate used to filter results
     *
     * @return a directory stream, or null when the directory cannot be opened
     */
    @Nullable
    public static DirectoryStream<Path> newDirectoryStream(@Nullable Path path, @Nullable DirectoryStream.Filter<? super Path> filter) {
        if (path == null || filter == null) {
            return null;
        }
        try {
            return Files.newDirectoryStream(path, filter);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens a directory stream for the given directory.
     *
     * @param path The path
     * @param glob The glob pattern
     *
     * @return a directory stream, or null when the directory cannot be opened
     */
    @Nullable
    public static DirectoryStream<Path> newDirectoryStream(@Nullable Path path, @Nullable String glob) {
        if (path == null || glob == null) {
            return null;
        }
        try {
            return Files.newDirectoryStream(path, glob);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates a temporary directory.
     *
     * @param path The path
     * @param prefix The prefix
     * @param attrs The file attributes
     *
     * @return the temporary directory path, or null when creation fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path createTempDirectory(@Nullable Path path, @Nullable String prefix, @Nullable FileAttribute<?>[] attrs) {
        if (path == null) {
            return null;
        }
        try {
            return Files.createTempDirectory(path, prefix, attrs);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates a temporary directory.
     *
     * @param prefix The prefix
     * @param attrs The file attributes
     *
     * @return the temporary directory path, or null when creation fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path createTempDirectory(@Nullable String prefix, @Nullable FileAttribute<?>[] attrs) {
        try {
            return Files.createTempDirectory(prefix, attrs);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates a symbolic link to the target.
     *
     * @param link The link path
     * @param target The target path
     * @param attrs The file attributes
     *
     * @return the link path, or null when creation fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path createSymbolicLink(@Nullable Path link, @Nullable Path target, @Nullable FileAttribute<?>[] attrs) {
        if (link == null || target == null) {
            return null;
        }
        try {
            return Files.createSymbolicLink(link, target, attrs);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates a hard link to the target.
     *
     * @param link The link path
     * @param exist The expected existence state
     *
     * @return the link path, or null when creation fails
     */
    @Nullable
    public static Path createLink(@Nullable Path link, @Nullable Path exist) {
        if (link == null || exist == null) {
            return null;
        }
        try {
            return Files.createLink(link, exist);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Deletes the given path if it exists.
     *
     * @param path The path
     *
     * @return true when the path was deleted; false otherwise
     */
    public static boolean deleteIfExists(@Nullable Path path) {
        if (path == null) {
            return false;
        }
        try {
            return Files.deleteIfExists(path);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Moves the source path to the target path.
     *
     * @param source The source path
     * @param target The target path
     * @param options The options
     *
     * @return the target path, or null when the move fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path move(@Nullable Path source, @Nullable Path target, @Nullable CopyOption[] options) {
        if (source == null || target == null) {
            return null;
        }
        try {
            return Files.move(source, target, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads the target of the given symbolic link.
     *
     * @param path The path
     *
     * @return the link target, or null when it cannot be read
     */
    @Nullable
    public static Path readSymbolicLink(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.readSymbolicLink(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns the file store that contains the given path.
     *
     * @param path The path
     *
     * @return the file store, or null when it cannot be determined
     */
    @Nullable
    public static FileStore getFileStore(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.getFileStore(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Checks whether two paths locate the same file.
     *
     * @param path1 The first path
     * @param path2 The second path
     *
     * @return true when both paths locate the same file; false otherwise
     */
    public static boolean isSameFile(@Nullable Path path1, @Nullable Path path2) {
        if (path1 == null || path2 == null) {
            return false;
        }
        try {
            return Files.isSameFile(path1, path2);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Probes the content type of the given file.
     *
     * @param path The path
     *
     * @return the content type, or null when it cannot be determined
     */
    @Nullable
    public static String probeContentType(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.probeContentType(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a file attribute view of the requested type.
     *
     * @param path The path
     * @param type The requested type
     * @param options The options
     *
     * @return the attribute view, or null when it is unavailable
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <V extends FileAttributeView> V getFileAttributeView(@Nullable Path path, @Nullable Class<V> type, @Nullable LinkOption[] options) {
        if (path == null || type == null) {
            return null;
        }
        try {
            return Files.getFileAttributeView(path, type, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads the attributes of the given path.
     *
     * @param path The path
     * @param type The requested type
     * @param options The options
     *
     * @return the attributes, or null when they cannot be read
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <A extends BasicFileAttributes> A readAttributes(@Nullable Path path, @Nullable Class<A> type, @Nullable LinkOption[] options) {
        if (path == null || type == null) {
            return null;
        }
        try {
            return Files.readAttributes(path, type, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads the attributes of the given path.
     *
     * @param path The path
     * @param attrs The file attributes
     * @param options The options
     *
     * @return the attributes, or null when they cannot be read
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Map<String, Object> readAttributes(@Nullable Path path, @Nullable String attrs, @Nullable LinkOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.readAttributes(path, attrs, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Sets the value of the given file attribute.
     *
     * @param path The path
     * @param attr The attribute name
     * @param value The value
     * @param options The options
     *
     * @return the result of the operation
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path setAttribute(@Nullable Path path, @Nullable String attr, @Nullable Object value, @Nullable LinkOption[] options) {
        if (path == null || attr == null) {
            return null;
        }
        try {
            return Files.setAttribute(path, attr, value, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Checks whether the path is a symbolic link.
     *
     * @param path The path
     *
     * @return true when the path is a symbolic link; false otherwise
     */
    public static boolean isSymbolicLink(@Nullable Path path) {
        if (path == null) {
            return false;
        }
        try {
            return Files.isSymbolicLink(path);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Walks the file tree rooted at the given path using the visitor.
     *
     * @param path The path
     * @param visitor The file visitor
     *
     * @return the starting path, or null when the tree cannot be walked
     */
    @Nullable
    public static Path walkFileTree(@Nullable Path path, @Nullable FileVisitor<? super Path> visitor) {
        if (path == null || visitor == null) {
            return null;
        }
        try {
            return Files.walkFileTree(path, visitor);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Walks the file tree rooted at the given path using the visitor.
     *
     * @param path The path
     * @param options The options
     * @param maxDepth The maximum depth to traverse
     * @param visitor The file visitor
     *
     * @return the starting path, or null when the tree cannot be walked
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path walkFileTree(@Nullable Path path, @Nullable Set<FileVisitOption> options, int maxDepth, @Nullable FileVisitor<? super Path> visitor) {
        if (path == null || visitor == null) {
            return null;
        }
        try {
            return Files.walkFileTree(path, options, maxDepth, visitor);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens a buffered reader for the given file.
     *
     * @param path The path
     *
     * @return a buffered reader, or null when the file cannot be opened
     */
    @Nullable
    public static BufferedReader newBufferedReader(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.newBufferedReader(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens a buffered reader for the given file.
     *
     * @param path The path
     * @param charset The character set
     *
     * @return a buffered reader, or null when the file cannot be opened
     */
    @Nullable
    public static BufferedReader newBufferedReader(@Nullable Path path, @Nullable Charset charset) {
        if (path == null) {
            return null;
        }
        try {
            return Files.newBufferedReader(path, CharsetPlainWraps.defaultCharset(charset));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens a buffered writer for the given file.
     *
     * @param path The path
     * @param options The options
     *
     * @return a buffered writer, or null when the file cannot be opened
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static BufferedWriter newBufferedWriter(@Nullable Path path, @Nullable OpenOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.newBufferedWriter(path, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Opens a buffered writer for the given file.
     *
     * @param path The path
     * @param charset The character set
     * @param options The options
     *
     * @return a buffered writer, or null when the file cannot be opened
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static BufferedWriter newBufferedWriter(@Nullable Path path, @Nullable Charset charset, @Nullable OpenOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.newBufferedWriter(path, CharsetPlainWraps.defaultCharset(charset), options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads the entire content of the given file as a string.
     *
     * @param path The path
     * @param charset The character set
     *
     * @return the file content, or null when the file cannot be read
     */
    @Nullable
    public static String readString(@Nullable Path path, @Nullable Charset charset) {
        if (path == null) {
            return null;
        }
        try {
            return Files.readString(path, CharsetPlainWraps.defaultCharset(charset));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads the entire content of the given file as a string.
     *
     * @param path The path
     *
     * @return the file content, or null when the file cannot be read
     */
    @Nullable
    public static String readString(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.readString(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads all lines from the given file.
     *
     * @param path The path
     *
     * @return a list of lines, or null when the file cannot be read
     */
    @Nullable
    public static List<String> readAllLines(@Nullable Path path) {
        if (path == null) {
            return null;
        }
        try {
            return Files.readAllLines(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads all lines from the given file.
     *
     * @param path The path
     * @param charset The character set
     *
     * @return a list of lines, or null when the file cannot be read
     */
    @Nullable
    public static List<String> readAllLines(@Nullable Path path, @Nullable Charset charset) {
        if (path == null) {
            return null;
        }
        try {
            return Files.readAllLines(path, CharsetPlainWraps.defaultCharset(charset));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Writes a string to the given file.
     *
     * @param path The path
     * @param sequence The character sequence to write
     * @param options The options
     *
     * @return the result of the operation
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path writeString(@Nullable Path path, @Nullable CharSequence sequence, @Nullable OpenOption[] options) {
        if (path == null || sequence == null) {
            return null;
        }
        try {
            return Files.writeString(path, sequence, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Writes a string to the given file.
     *
     * @param path The path
     * @param sequence The character sequence to write
     * @param charset The character set
     * @param options The options
     *
     * @return the result of the operation
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path writeString(@Nullable Path path, @Nullable CharSequence sequence, @Nullable Charset charset, @Nullable OpenOption[] options) {
        if (path == null || sequence == null) {
            return null;
        }
        try {
            return Files.writeString(path, sequence, CharsetPlainWraps.defaultCharset(charset), options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates a new empty file.
     *
     * @param path The path
     * @param attrs The file attributes
     *
     * @return the file path, or null when creation fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path createFile(@Nullable Path path, @Nullable FileAttribute<?>[] attrs) {
        if (path == null) {
            return null;
        }
        try {
            return Files.createFile(path, attrs);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Creates directories including any necessary parent directories.
     *
     * @param path The path
     * @param attrs The file attributes
     *
     * @return the directory path, or null when creation fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Path createDirectories(@Nullable Path path, @Nullable FileAttribute<?>[] attrs) {
        if (path == null) {
            return null;
        }
        try {
            return Files.createDirectories(path, attrs);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns the value of the given file attribute.
     *
     * @param path The path
     * @param attr The attribute name
     * @param options The options
     *
     * @return the attribute value, or null when it cannot be read
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Object getAttribute(@Nullable Path path, @Nullable String attr, @Nullable LinkOption[] options) {
        if (path == null || StringUtils.isBlank(attr)) {
            return null;
        }
        try {
            return Files.getAttribute(path, attr, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns the POSIX permissions of the given path.
     *
     * @param path The path
     * @param options The options
     *
     * @return the permissions, or null when they cannot be read
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Set<PosixFilePermission> getPosixFilePermissions(@Nullable Path path, @Nullable LinkOption[] options) {
        if (path == null) {
            return null;
        }
        try {
            return Files.getPosixFilePermissions(path, options);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Sets the POSIX permissions of the given path.
     *
     * @param path The path
     * @param permissions The permissions
     *
     * @return the result of the operation
     */
    @Nullable
    public static Path setPosixFilePermissions(@Nullable Path path, @Nullable Set<PosixFilePermission> permissions) {
        if (path == null || permissions == null) {
            return null;
        }
        try {
            return Files.setPosixFilePermissions(path, permissions);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Checks whether the path does not exist.
     *
     * @param path The path
     * @param options The options
     *
     * @return true when the path does not exist; false otherwise
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean notExists(@Nullable Path path, @Nullable LinkOption[] options) {
        if (path == null) {
            return true;
        }
        try {
            return Files.notExists(path, options);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Checks whether the path is readable.
     *
     * @param path The path
     *
     * @return true when the path is readable; false otherwise
     */
    public static boolean isReadable(@Nullable Path path) {
        if (path == null) {
            return false;
        }
        try {
            return Files.isReadable(path);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Checks whether the path is writable.
     *
     * @param path The path
     *
     * @return true when the path is writable; false otherwise
     */
    public static boolean isWritable(@Nullable Path path) {
        if (path == null) {
            return false;
        }
        try {
            return Files.isWritable(path);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Checks whether the path is executable.
     *
     * @param path The path
     *
     * @return true when the path is executable; false otherwise
     */
    public static boolean isExecutable(@Nullable Path path) {
        if (path == null) {
            return false;
        }
        try {
            return Files.isExecutable(path);
        } catch (Exception ignored) {
        }
        return false;
    }
}
