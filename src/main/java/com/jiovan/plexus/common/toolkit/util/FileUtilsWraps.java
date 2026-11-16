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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import com.jiovan.plexus.common.toolkit.function.IgnorableFailable;


/**
 * Utilities for {@link org.apache.commons.io.FileUtils}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.io.FileUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class FileUtilsWraps {
    /**
     * Cleans the given directory without deleting it.
     *
     * @param directory The directory
     *
     * @return the result of the operation
     */
    public static boolean cleanDirectory(@Nullable File directory) {
        if (directory == null) {
            return false;
        }
        try {
            FileUtils.cleanDirectory(directory);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies a directory recursively to the destination.
     *
     * @param srcDir The source directory
     * @param destDir The destination directory
     *
     * @return the result of the operation
     */
    public static boolean copyDirectory(@Nullable File srcDir, @Nullable File destDir) {
        return copyDirectory(srcDir, destDir, null, true);
    }

    /**
     * Copies a directory recursively to the destination.
     *
     * @param srcDir The source directory
     * @param destDir The destination directory
     * @param preserveFileDate Whether the file date should be preserved
     *
     * @return the result of the operation
     */
    public static boolean copyDirectory(@Nullable File srcDir, @Nullable File destDir, boolean preserveFileDate) {
        return copyDirectory(srcDir, destDir, null, preserveFileDate);
    }

    /**
     * Copies a directory recursively to the destination.
     *
     * @param srcDir The source directory
     * @param destDir The destination directory
     * @param fileFilter The filter applied to files
     *
     * @return the result of the operation
     */
    public static boolean copyDirectory(@Nullable File srcDir, @Nullable File destDir, @Nullable FileFilter fileFilter) {
        return copyDirectory(srcDir, destDir, fileFilter, true);
    }

    /**
     * Copies a directory recursively to the destination.
     *
     * @param srcDir The source directory
     * @param destDir The destination directory
     * @param fileFilter The filter applied to files
     * @param preserveFileDate Whether the file date should be preserved
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyDirectory(@Nullable File srcDir, @Nullable File destDir, @Nullable FileFilter fileFilter, boolean preserveFileDate) {
        if (ObjectUtils.anyNull(srcDir, destDir)) {
            return false;
        }
        try {
            FileUtils.copyDirectory(srcDir, destDir, fileFilter, preserveFileDate);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies a directory recursively to the destination.
     *
     * @param srcDir The source directory
     * @param destDir The destination directory
     * @param fileFilter The filter applied to files
     * @param preserveFileDate Whether the file date should be preserved
     * @param copyOptions The copy options
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyDirectory(@Nullable File srcDir, @Nullable File destDir, @Nullable FileFilter fileFilter, boolean preserveFileDate, @Nullable CopyOption... copyOptions) {
        if (ObjectUtils.anyNull(srcDir, destDir, copyOptions)) {
            return false;
        }
        try {
            FileUtils.copyDirectory(srcDir, destDir, fileFilter, preserveFileDate, copyOptions);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies a directory into the given destination directory.
     *
     * @param srcDir The source directory
     * @param destDir The destination directory
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyDirectoryToDirectory(@Nullable File srcDir, @Nullable File destDir) {
        if (ObjectUtils.anyNull(srcDir, destDir)) {
            return false;
        }
        try {
            FileUtils.copyDirectoryToDirectory(srcDir, destDir);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies a file to the destination.
     *
     * @param srcFile The source file
     * @param destFile The destination file
     *
     * @return the result of the operation
     */
    public static boolean copyFile(@Nullable File srcFile, @Nullable File destFile) {
        return copyFile(srcFile, destFile, true);
    }

    /**
     * Copies a file to the destination.
     *
     * @param srcFile The source file
     * @param destFile The destination file
     * @param preserveFileDate Whether the file date should be preserved
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyFile(@Nullable File srcFile, @Nullable File destFile, boolean preserveFileDate) {
        if (ObjectUtils.anyNull(srcFile, destFile)) {
            return false;
        }
        try {
            FileUtils.copyFile(srcFile, destFile, preserveFileDate);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies a file to the destination.
     *
     * @param srcFile The source file
     * @param destFile The destination file
     * @param preserveFileDate Whether the file date should be preserved
     * @param copyOptions The copy options
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyFile(@Nullable File srcFile, @Nullable File destFile, boolean preserveFileDate, @Nullable CopyOption... copyOptions) {
        if (ObjectUtils.anyNull(srcFile, destFile, copyOptions)) {
            return false;
        }
        try {
            FileUtils.copyFile(srcFile, destFile, preserveFileDate, copyOptions);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies a file to the destination.
     *
     * @param srcFile The source file
     * @param destFile The destination file
     * @param copyOptions The copy options
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyFile(@Nullable File srcFile, @Nullable File destFile, @Nullable CopyOption... copyOptions) {
        if (ObjectUtils.anyNull(srcFile, destFile, copyOptions)) {
            return false;
        }
        try {
            FileUtils.copyFile(srcFile, destFile, copyOptions);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies a file to the destination.
     *
     * @param src The source path
     * @param dest The destination path
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static long copyFile(@Nullable File src, @Nullable OutputStream dest) {
        if (ObjectUtils.anyNull(src, dest)) {
            return 0L;
        }
        try {
            return FileUtils.copyFile(src, dest);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    /**
     * Copies a file into the given destination directory.
     *
     * @param srcFile The source file
     * @param destDir The destination directory
     *
     * @return the result of the operation
     */
    public static boolean copyFileToDirectory(@Nullable File srcFile, @Nullable File destDir) {
        return copyFileToDirectory(srcFile, destDir, true);
    }

    /**
     * Copies a file into the given destination directory.
     *
     * @param srcFile The source file
     * @param destDir The destination directory
     * @param preserveFileDate Whether the file date should be preserved
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyFileToDirectory(@Nullable File srcFile, @Nullable File destDir, boolean preserveFileDate) {
        if (ObjectUtils.anyNull(srcFile, destDir)) {
            return false;
        }
        try {
            FileUtils.copyFileToDirectory(srcFile, destDir, preserveFileDate);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies the input stream content to the given file.
     *
     * @param src The source path
     * @param dest The destination path
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyInputStreamToFile(@Nullable InputStream src, @Nullable File dest) {
        if (ObjectUtils.anyNull(src, dest)) {
            return false;
        }
        try {
            FileUtils.copyInputStreamToFile(src, dest);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies the given sources into the destination directory.
     *
     * @param srcFile The source file
     * @param destDir The destination directory
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyToDirectory(@Nullable File srcFile, @Nullable File destDir) {
        if (ObjectUtils.anyNull(srcFile, destDir)) {
            return false;
        }
        try {
            FileUtils.copyToDirectory(srcFile, destDir);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies the given sources into the destination directory.
     *
     * @param srcFiles The source files
     * @param destDir The destination directory
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyToDirectory(@Nullable Iterable<File> srcFiles, @Nullable File destDir) {
        if (ObjectUtils.anyNull(srcFiles, destDir)) {
            return false;
        }
        try {
            FileUtils.copyToDirectory(srcFiles, destDir);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies the input stream content to the given file.
     *
     * @param src The source path
     * @param dest The destination path
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyToFile(@Nullable InputStream src, @Nullable File dest) {
        if (ObjectUtils.anyNull(src, dest)) {
            return false;
        }
        try {
            FileUtils.copyToFile(src, dest);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies the content of the given URL to a file.
     *
     * @param src The source path
     * @param dest The destination path
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyUrlToFile(@Nullable URL src, @Nullable File dest) {
        if (ObjectUtils.anyNull(src, dest)) {
            return false;
        }
        try {
            FileUtils.copyURLToFile(src, dest);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Copies the content of the given URL to a file.
     *
     * @param src The source path
     * @param dest The destination path
     * @param connectionTimeoutMillis The connection timeout in milliseconds
     * @param readTimeoutMillis The read timeout in milliseconds
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean copyUrlToFile(@Nullable URL src, @Nullable File dest, int connectionTimeoutMillis, int readTimeoutMillis) {
        if (ObjectUtils.anyNull(src, dest)) {
            return false;
        }
        try {
            FileUtils.copyURLToFile(src, dest, connectionTimeoutMillis, readTimeoutMillis);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Creates parent directories for the given file.
     *
     * @param file The file
     *
     * @return the result of the operation
     */
    @Nullable
    public static File createParentDirectories(@Nullable File file) {
        if (file == null) {
            return null;
        }
        try {
            return FileUtils.createParentDirectories(file);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Deletes the directory and all of its content.
     *
     * @param directory The directory
     *
     * @return true when the directory was deleted; false otherwise
     */
    public static boolean deleteDirectory(@Nullable File directory) {
        if (directory == null) {
            return false;
        }
        try {
            FileUtils.deleteDirectory(directory);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Checks whether the path exists.
     *
     * @param file The file
     *
     * @return true when the path exists; false otherwise
     */
    public static boolean exists(@Nullable File file) {
        return file != null && file.exists();
    }

    /**
     * Checks whether the path exists.
     *
     * @param path The path
     *
     * @return true when the path exists; false otherwise
     */
    public static boolean exists(@Nullable String path) {
        return StringUtils.isNotBlank(path) && new File(path).exists();
    }

    /**
     * Deletes a file or directory, forcing deletion when necessary.
     *
     * @param file The file
     *
     * @return the result of the operation
     */
    public static boolean forceDelete(@Nullable File file) {
        if (file == null) {
            return false;
        }
        try {
            FileUtils.forceDelete(file);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Schedules a file or directory for forced deletion on JVM exit.
     *
     * @param file The file
     *
     * @return the result of the operation
     */
    public static boolean forceDeleteOnExit(@Nullable File file) {
        if (file == null) {
            return false;
        }
        try {
            FileUtils.forceDeleteOnExit(file);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Creates a directory, including any necessary parent directories.
     *
     * @param directory The directory
     *
     * @return the result of the operation
     */
    public static boolean forceMkdir(@Nullable File directory) {
        if (directory == null) {
            return false;
        }
        try {
            FileUtils.forceMkdir(directory);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Creates parent directories for the destination and optionally deletes an existing target.
     *
     * @param file The file
     *
     * @return the result of the operation
     */
    public static boolean forceMkdirParent(@Nullable File file) {
        if (file == null) {
            return false;
        }
        try {
            FileUtils.forceMkdirParent(file);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Moves a directory to the destination.
     *
     * @param srcDir The source directory
     * @param destDir The destination directory
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean moveDirectory(@Nullable File srcDir, @Nullable File destDir) {
        if (ObjectUtils.anyNull(srcDir, destDir)) {
            return false;
        }
        try {
            FileUtils.moveDirectory(srcDir, destDir);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Moves a directory into the given destination directory.
     *
     * @param srcDir The source directory
     * @param destDir The destination directory
     * @param createDestDir Whether to create the destination directory
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean moveDirectoryToDirectory(@Nullable File srcDir, @Nullable File destDir, boolean createDestDir) {
        if (ObjectUtils.anyNull(srcDir, destDir)) {
            return false;
        }
        try {
            FileUtils.moveDirectoryToDirectory(srcDir, destDir, createDestDir);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Moves a file to the destination.
     *
     * @param srcFile The source file
     * @param destFile The destination file
     *
     * @return the result of the operation
     */
    public static boolean moveFile(@Nullable File srcFile, @Nullable File destFile) {
        return moveFile(srcFile, destFile, StandardCopyOption.COPY_ATTRIBUTES);
    }

    /**
     * Moves a file to the destination.
     *
     * @param srcFile The source file
     * @param destFile The destination file
     * @param copyOptions The copy options
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean moveFile(@Nullable File srcFile, @Nullable File destFile, @Nullable CopyOption... copyOptions) {
        if (ObjectUtils.anyNull(srcFile, destFile, copyOptions)) {
            return false;
        }
        try {
            FileUtils.moveFile(srcFile, destFile, copyOptions);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Moves a file into the given destination directory.
     *
     * @param srcFile The source file
     * @param destDir The destination directory
     * @param createDestDir Whether to create the destination directory
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean moveFileToDirectory(@Nullable File srcFile, @Nullable File destDir, boolean createDestDir) {
        if (ObjectUtils.anyNull(srcFile, destDir)) {
            return false;
        }
        try {
            FileUtils.moveFileToDirectory(srcFile, destDir, createDestDir);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Moves the given source into the destination directory.
     *
     * @param srcDir The source directory
     * @param destDir The destination directory
     * @param createDestDir Whether to create the destination directory
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean moveToDirectory(@Nullable File srcDir, @Nullable File destDir, boolean createDestDir) {
        if (ObjectUtils.anyNull(srcDir, destDir)) {
            return false;
        }
        try {
            FileUtils.moveToDirectory(srcDir, destDir, createDestDir);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Opens an input stream for the given file.
     *
     * @param file The file
     *
     * @return an input stream, or null when the file cannot be opened
     */
    @Nullable
    public static FileInputStream openInputStream(@Nullable File file) {
        if (file == null) {
            return null;
        }
        try {
            return FileUtils.openInputStream(file);
        } catch (Exception ignored) {
        } return null;
    }

    /**
     * Opens an output stream for the given file.
     *
     * @param file The file
     *
     * @return an output stream, or null when the file cannot be opened
     */
    @Nullable
    public static FileOutputStream openOutputStream(@Nullable File file) {
        return openOutputStream(file, false);
    }

    /**
     * Opens an output stream for the given file.
     *
     * @param file The file
     * @param append Whether to append to an existing file
     *
     * @return an output stream, or null when the file cannot be opened
     */
    @Nullable
    public static FileOutputStream openOutputStream(@Nullable File file, boolean append) {
        if (file == null) {
            return null;
        }
        try {
            return FileUtils.openOutputStream(file, append);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads the entire content of a file into a byte array.
     *
     * @param file The file
     *
     * @return the file content, or null when the file cannot be read
     */
    @Nullable
    public static byte[] readFileToByteArray(@Nullable File file) {
        if (file == null) {
            return null;
        }
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads the entire content of a file as a string.
     *
     * @param file The file
     * @param charset The character set
     *
     * @return the file content, or null when the file cannot be read
     */
    @Nullable
    public static String readFileToString(@Nullable File file, @Nullable Charset charset) {
        if (file == null) {
            return null;
        }
        try {
            return FileUtils.readFileToString(file, charset);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads the entire content of a file as a string.
     *
     * @param file The file
     * @param charset The character set
     *
     * @return the file content, or null when the file cannot be read
     */
    @Nullable
    public static String readFileToString(@Nullable File file, @Nullable String charset) {
        if (file == null) {
            return null;
        }
        try {
            return FileUtils.readFileToString(file, CharsetPlainWraps.defaultCharsetName(charset));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads all lines from the given file.
     *
     * @param file The file
     * @param charset The character set
     *
     * @return a list of lines, or null when the file cannot be read
     */
    @Nullable
    public static List<String> readLines(@Nullable File file, @Nullable Charset charset) {
        if (file == null) {
            return null;
        }
        try {
            return FileUtils.readLines(file, CharsetPlainWraps.defaultCharset(charset));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Reads all lines from the given file.
     *
     * @param file The file
     * @param charset The character set
     *
     * @return a list of lines, or null when the file cannot be read
     */
    @Nullable
    public static List<String> readLines(@Nullable File file, @Nullable String charset) {
        if (file == null) {
            return null;
        }
        try {
            return FileUtils.readLines(file, CharsetPlainWraps.defaultCharsetName(charset));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns a stream over files in the given directory.
     *
     * @param directory The directory
     * @param recursive Whether to recurse into subdirectories
     * @param extensions The extensions to check
     *
     * @return a stream of files, or null when the directory cannot be read
     */
    @Nonnull
    public static Stream<File> streamFiles(@Nullable File directory, boolean recursive, @Nullable String... extensions) {
        if (directory == null || ArrayUtils.isEmpty(extensions)) {
            return Stream.empty();
        }
        try {
            return FileUtils.streamFiles(directory, recursive, extensions);
        } catch (Exception ignored) {
        }
        return Stream.empty();
    }

    /**
     * Writes a character sequence to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param charset The character set
     *
     * @return the result of the operation
     */
    public static boolean writeCharSequence(@Nullable File file, @Nullable CharSequence data, @Nullable Charset charset) {
        return writeCharSequence(file, data, charset, false);
    }

    /**
     * Writes a character sequence to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param charset The character set
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean writeCharSequence(@Nullable File file, @Nullable CharSequence data, @Nullable Charset charset, boolean append) {
        if (ObjectUtils.anyNull(file, data)) {
            return false;
        }
        try {
            FileUtils.write(file, data, charset, append);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Writes a character sequence to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param charset The character set
     *
     * @return the result of the operation
     */
    public static boolean writeCharSequence(@Nullable File file, @Nullable CharSequence data, @Nullable String charset) {
        return writeCharSequence(file, data, charset, false);
    }

    /**
     * Writes a character sequence to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param charset The character set
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean writeCharSequence(@Nullable File file, @Nullable CharSequence data, @Nullable String charset, boolean append) {
        if (ObjectUtils.anyNull(file, data)) {
            return false;
        }
        try {
            FileUtils.write(file, data, CharsetPlainWraps.defaultCharsetName(charset), append);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Writes a byte array to the given file.
     *
     * @param file The file
     * @param data The data to write
     *
     * @return the result of the operation
     */
    public static boolean writeByteArray(@Nullable File file, @Nullable byte[] data) {
        return writeByteArray(file, data, false);
    }

    /**
     * Writes a byte array to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean writeByteArray(@Nullable File file, @Nullable byte[] data, boolean append) {
        if (file == null || ArrayUtils.isEmpty(data)) {
            return false;
        }
        try {
            FileUtils.writeByteArrayToFile(file, data, append);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Writes a byte array to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param offset The starting offset
     * @param length The requested length
     *
     * @return the result of the operation
     */
    public static boolean writeByteArray(@Nullable File file, @Nullable byte[] data, int offset, int length) {
        return writeByteArray(file, data, 0, ArrayUtils.getLength(data), false);
    }

    /**
     * Writes a byte array to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param offset The starting offset
     * @param length The requested length
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    public static boolean writeByteArray(@Nullable File file, @Nullable byte[] data, int offset, int length, boolean append) {
        if (file == null || ArrayUtils.isEmpty(data) || offset < 0 || length <= 0) {
            return false;
        }
        try {
            FileUtils.writeByteArrayToFile(file, data, offset, length, append);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Writes lines of text to the given file.
     *
     * @param file The file
     * @param lines The lines to write
     *
     * @return the result of the operation
     */
    public static boolean writeLines(@Nullable File file, @Nullable Collection<?> lines) {
        return writeLines(file, lines, null, false);
    }

    /**
     * Writes lines of text to the given file.
     *
     * @param file The file
     * @param lines The lines to write
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    public static boolean writeLines(@Nullable File file, @Nullable Collection<?> lines, boolean append) {
        return writeLines(file, lines, null, append);
    }

    /**
     * Writes lines of text to the given file.
     *
     * @param file The file
     * @param lines The lines to write
     * @param lineEnding The line ending
     *
     * @return the result of the operation
     */
    public static boolean writeLines(@Nullable File file, @Nullable Collection<?> lines, @Nullable String lineEnding) {
        return writeLines(file, lines, lineEnding, false);
    }

    /**
     * Writes lines of text to the given file.
     *
     * @param file The file
     * @param lines The lines to write
     * @param lineEnding The line ending
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    public static boolean writeLines(@Nullable File file, @Nullable Collection<?> lines, @Nullable String lineEnding, boolean append) {
        if (file == null || CollectionPlainWraps.isEmpty(lines)) {
            return false;
        }
        try {
            FileUtils.writeLines(file, lines, lineEnding, append);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Writes lines of text to the given file.
     *
     * @param file The file
     * @param charset The character set
     * @param lines The lines to write
     *
     * @return the result of the operation
     */
    public static boolean writeLines(@Nullable File file, @Nullable String charset, @Nullable Collection<?> lines) {
        return writeLines(file, charset, lines, null, false);
    }

    /**
     * Writes lines of text to the given file.
     *
     * @param file The file
     * @param charset The character set
     * @param lines The lines to write
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    public static boolean writeLines(@Nullable File file, @Nullable String charset, @Nullable Collection<?> lines, boolean append) {
        return writeLines(file, charset, lines, null, append);
    }

    /**
     * Writes lines of text to the given file.
     *
     * @param file The file
     * @param charset The character set
     * @param lines The lines to write
     * @param lineEnding The line ending
     *
     * @return the result of the operation
     */
    public static boolean writeLines(@Nullable File file, @Nullable String charset, @Nullable Collection<?> lines, String lineEnding) {
        return writeLines(file, charset, lines, lineEnding, false);
    }

    /**
     * Writes lines of text to the given file.
     *
     * @param file The file
     * @param charset The character set
     * @param lines The lines to write
     * @param lineEnding The line ending
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    public static boolean writeLines(@Nullable File file, @Nullable String charset, @Nullable Collection<?> lines, String lineEnding, boolean append) {
        if (file == null || CollectionPlainWraps.isEmpty(lines)) {
            return false;
        }
        try {
            FileUtils.writeLines(file, CharsetPlainWraps.defaultCharsetName(charset), lines, lineEnding, append);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Writes the input stream content to the given file.
     *
     * @param file The file
     * @param inputStream The input stream
     *
     * @return the result of the operation
     */
    public static boolean writeStream(@Nullable File file, @Nullable InputStream inputStream) {
        if (ObjectUtils.anyNull(file, inputStream) || !file.isFile() || !file.canWrite()) {
            return false;
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] bufferRead = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(bufferRead)) != -1) {
                outputStream.write(bufferRead, 0, bytesRead);
            }
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Writes a string to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param charset The character set
     *
     * @return the result of the operation
     */
    public static boolean writeString(@Nullable File file, @Nullable String data, @Nullable Charset charset) {
        return writeString(file, data, charset, false);
    }

    /**
     * Writes a string to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param charset The character set
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    public static boolean writeString(@Nullable File file, @Nullable String data, @Nullable Charset charset, boolean append) {
        if (file == null || StringUtils.isEmpty(data)) {
            return false;
        }
        try {
            FileUtils.writeStringToFile(file, data, charset, append);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Writes a string to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param charset The character set
     *
     * @return the result of the operation
     */
    public static boolean writeString(@Nullable File file, @Nullable String data, @Nullable String charset) {
        return writeString(file, data, charset, false);
    }

    /**
     * Writes a string to the given file.
     *
     * @param file The file
     * @param data The data to write
     * @param charset The character set
     * @param append Whether to append to an existing file
     *
     * @return the result of the operation
     */
    public static boolean writeString(@Nullable File file, @Nullable String data, @Nullable String charset, boolean append) {
        if (file == null || StringUtils.isEmpty(data)) {
            return false;
        }
        try {
            FileUtils.writeStringToFile(file, data, CharsetPlainWraps.defaultCharsetName(charset), append);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Checks whether the directory contains all the given children.
     *
     * @param directory The directory
     * @param children The child files or directories
     *
     * @return true when every child is contained; false otherwise
     */
    public static boolean directoryContainsAll(@Nullable File directory, @Nullable File... children) {
        return directoryContainsAll(directory, ArrayUtilsWraps.asList(children));
    }

    /**
     * Checks whether the directory contains all the given children.
     *
     * @param directory The directory
     * @param children The child files or directories
     *
     * @return true when every child is contained; false otherwise
     */
    public static boolean directoryContainsAll(@Nullable File directory, @Nullable Collection<File> children) {
        if (directory == null || CollectionPlainWraps.isEmpty(children)) {
            return false;
        }
        return children.stream().allMatch(IgnorableFailable.asPredicate(child -> FileUtils.directoryContains(directory, child)));
    }

    /**
     * Checks whether the directory contains any of the given children.
     *
     * @param directory The directory
     * @param children The child files or directories
     *
     * @return true when at least one child is contained; false otherwise
     */
    public static boolean directoryContainsAny(@Nullable File directory, @Nullable File... children) {
        return directoryContainsAny(directory, ArrayUtilsWraps.asList(children));
    }

    /**
     * Checks whether the directory contains any of the given children.
     *
     * @param directory The directory
     * @param children The child files or directories
     *
     * @return true when at least one child is contained; false otherwise
     */
    public static boolean directoryContainsAny(@Nullable File directory, @Nullable Collection<File> children) {
        return directory != null && CollectionPlainWraps.isNotEmpty(children) && children.stream().anyMatch(IgnorableFailable.asPredicate(child -> FileUtils.directoryContains(directory, child)));
    }

    /**
     * Force creating parents with ability to delete {@code dest} file
     *
     * @param dest The destination file or folder
     * @param deleteIfExists When {@code dest} file exists, if true means this will delete it first; while false means not to delete
     *
     * @see org.apache.commons.io.FileUtils#forceMkdirParent
     */
    public static boolean forceMkdirParent(@Nullable File dest, boolean deleteIfExists) {
        if (dest == null) {
            return false;
        }
        try {
            if (!dest.exists()) {
                FileUtils.forceMkdirParent(dest);
            } else {
                if (deleteIfExists) {
                    if (dest.isFile()) {
                        FileUtils.delete(dest);
                    } else if (dest.isDirectory()) {
                        FileUtils.deleteDirectory(dest);
                    }
                }
            }
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Opens an input stream for reading an image file.
     *
     * @param file The file
     *
     * @return an image input stream, or null when the file cannot be opened
     */
    @Nullable
    public static FileImageInputStream openImageInputStream(@Nullable File file) {
        try {
            return (file == null || file.isDirectory()) ? null : new FileImageInputStream(file);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * @see org.apache.commons.io.FileUtils#openOutputStream(java.io.File, boolean)
     */
    @Nullable
    public static FileImageOutputStream openImageOutputStream(@Nullable File file, boolean deleteIfExists) {
        if (file == null || file.isDirectory()) {
            return null;
        }
        try {
            if (file.exists()) {
                if (deleteIfExists) {
                    FileUtils.delete(file);
                }
            } else {
                FileUtils.createParentDirectories(file);
            }
            return new FileImageOutputStream(file);
        } catch (Exception ignored) {
        }
        return null;
    }
}
