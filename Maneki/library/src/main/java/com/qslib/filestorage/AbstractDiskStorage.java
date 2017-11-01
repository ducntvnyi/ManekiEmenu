package com.qslib.filestorage;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StatFs;

import com.qslib.filestorage.helpers.ImmutablePair;
import com.qslib.filestorage.helpers.OrderType;
import com.qslib.filestorage.helpers.SizeUnit;
import com.qslib.filestorage.security.SecurityUtil;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.Cipher;

/**
 * Common class for internal and external storage implementations
 *
 * @author Roman Kushnarenko - sromku (sromku@gmail.com)
 */
@SuppressWarnings("ALL")
abstract class AbstractDiskStorage implements Storage {
    protected static final String UTF_8 = "UTF-8";

    AbstractDiskStorage() {
    }

    SimpleStorageConfiguration getConfiguration() {
        return SimpleStorage.getConfiguration();
    }

    /**
     * create folder
     *
     * @param name The name of the directory.
     * @return
     */
    @Override
    public boolean createDirectory(String name) {
        try {
            String path = buildPath(name);

            // Check if the directory already exist
            if (isDirectoryExists(path)) {
                throw new RuntimeException("The direcory already exist. You can't override the existing one. Use createDirectory(String path, boolean override)");
            }

            File directory = new File(path);
            // Create a new directory
            return directory.mkdirs();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * create folder that allow overwrite
     *
     * @param name     The name of the directory.
     * @param override Set <code>True</code> if you want to override the directory if
     *                 such exists. The default is <code>False</code>.<br>
     *                 Set <code>False</code> then it checks if directory already
     *                 exist, if yes then do nothing and return true, otherwise it
     *                 creates a new directory
     * @return
     */
    @Override
    public boolean createDirectory(String name, boolean override) {
        try {
            // If override==false, then don't override
            if (!override) {
                return isDirectoryExists(name) || createDirectory(name);
            }

            // Check if directory exists. If yes, then delete all directory
            if (isDirectoryExists(name)) {
                deleteDirectory(name);
            }

            // Create new directory
            boolean wasCreated = createDirectory(name);
            // If directory is already exist then wasCreated=false
            if (!wasCreated) {
                throw new RuntimeException("Couldn't create new direcory");
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * delete folder
     *
     * @param name The name of the directory.
     * @return
     */
    @Override
    public boolean deleteDirectory(String name) {
        try {
            String path = buildPath(name);

            return deleteDirectoryImpl(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * check folder exists
     *
     * @param name The name of the directory.
     * @return
     */
    @Override
    public boolean isDirectoryExists(String name) {
        try {
            String path = buildPath(name);

            return new File(path).exists();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * create new file
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @param content       The content which will filled the file
     * @return
     */
    @Override
    public boolean createFile(String directoryName, String fileName, String content) {
        try {
            return createFile(directoryName, fileName, content.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * create new file
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @param storable      The content which will filled the file
     * @return
     */
    @Override
    public boolean createFile(String directoryName, String fileName, Storageable storable) {
        try {
            return createFile(directoryName, fileName, storable.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * create new file
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @param content       The content which will filled the file
     * @return
     */
    @Override
    public boolean createFile(String directoryName, String fileName, byte[] content) {
        String path = buildPath(directoryName, fileName);
        try {
            OutputStream stream = new FileOutputStream(new File(path));

			/*
             * Check if needs to be encrypted. If yes, then encrypt it.
			 */
            if (getConfiguration().isEncrypted()) {
                content = encrypt(content, Cipher.ENCRYPT_MODE);
            }

            stream.write(content);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create", e);
        }

        return true;
    }

    /**
     * create new file
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @param bitmap        The content which will filled the file
     * @return
     */
    @Override
    public boolean createFile(String directoryName, String fileName, Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] byteArray = stream.toByteArray();

            return createFile(directoryName, fileName, byteArray);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * delete file
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @return
     */
    @Override
    public boolean deleteFile(String directoryName, String fileName) {
        try {
            String path = buildPath(directoryName, fileName);
            File file = new File(path);

            return file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * check file exists
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @return
     */
    @Override
    public boolean isFileExist(String directoryName, String fileName) {
        try {
            String path = buildPath(directoryName, fileName);

            return new File(path).exists();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * read file
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @return
     */
    @Override
    public byte[] readFile(String directoryName, String fileName) {
        String path = buildPath(directoryName, fileName);
        final FileInputStream stream;
        try {
            stream = new FileInputStream(new File(path));
            return readFile(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to read file to input stream", e);
        }
    }

    /**
     * read text file
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @return
     */
    @Override
    public String readTextFile(String directoryName, String fileName) {
        try {
            byte[] bytes = readFile(directoryName, fileName);
            return new String(bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * append string content to file
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @param content
     */
    @Override
    public void appendFile(String directoryName, String fileName, String content) {
        try {
            appendFile(directoryName, fileName, content.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * append string content to file
     *
     * @param directoryName The directory name
     * @param fileName      The file name
     * @param bytes
     */
    @Override
    public void appendFile(String directoryName, String fileName, byte[] bytes) {
        if (!isFileExist(directoryName, fileName)) {
            throw new RuntimeException("Impossible to append content, because such file doesn't exist");
        }

        try {
            String path = buildPath(directoryName, fileName);
            FileOutputStream stream = new FileOutputStream(new File(path), true);
            stream.write(bytes);
            stream.write(System.getProperty("line.separator").getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to append content to file", e);
        }
    }

    /**
     * get all file in folder
     *
     * @param directoryName
     * @return
     */
    @Override
    public List<File> getNestedFiles(String directoryName) {
        try {
            String buildPath = buildPath(directoryName);
            File file = new File(buildPath);
            List<File> out = new ArrayList<>();
            getDirectoryFilesImpl(file, out);

            return out;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * get all file in folder
     *
     * @param directoryName
     * @param matchRegex    Set regular expression to match files you need.
     * @return
     */
    @Override
    public List<File> getFiles(String directoryName, final String matchRegex) {
        try {
            String buildPath = buildPath(directoryName);
            File file = new File(buildPath);
            List<File> out;

            if (matchRegex != null) {
                FilenameFilter filter = (dir, fileName) -> {
                    try {
                        if (fileName.matches(matchRegex)) {
                            return true;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    return false;
                };
                out = Arrays.asList(file.listFiles(filter));
            } else {
                out = Arrays.asList(file.listFiles());
            }

            return out;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * get all file in folder
     *
     * @param directoryName
     * @param orderType
     * @return
     */
    @Override
    public List<File> getFiles(String directoryName, OrderType orderType) {
        try {
            List<File> files = getFiles(directoryName, (String) null);
            Collections.sort(files, orderType.getComparator());

            return files;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * get file
     *
     * @param name
     * @return
     */
    @Override
    public File getFile(String name) {
        try {
            String path = buildPath(name);
            return new File(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * get file
     *
     * @param directoryName
     * @param fileName
     * @return
     */
    @Override
    public File getFile(String directoryName, String fileName) {
        try {
            String path = buildPath(directoryName, fileName);

            return new File(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * rename file
     *
     * @param file    The file you want to change. You can get the {@link File} by
     *                calling to one of the {@link #getFile(String)} methods
     * @param newName
     */
    @Override
    public void rename(File file, String newName) {
        try {
            String name = file.getName();
            String newFullName = file.getAbsolutePath().replaceAll(name, newName);
            File newFile = new File(newFullName);
            file.renameTo(newFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * get size of file
     *
     * @param file
     * @param unit
     * @return
     */
    @Override
    public double getSize(File file, SizeUnit unit) {
        try {
            long length = file.length();

            return (double) length / (double) unit.inBytes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    public long getFreeSpace(SizeUnit sizeUnit) {
        try {
            String path = buildAbsolutePath();
            StatFs statFs = new StatFs(path);
            long availableBlocks;
            long blockSize;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableBlocks = statFs.getAvailableBlocks();
                blockSize = statFs.getBlockSize();
            } else {
                availableBlocks = statFs.getAvailableBlocksLong();
                blockSize = statFs.getBlockSizeLong();
            }

            long freeBytes = availableBlocks * blockSize;

            return freeBytes / sizeUnit.inBytes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    public long getUsedSpace(SizeUnit sizeUnit) {
        try {
            String path = buildAbsolutePath();
            StatFs statFs = new StatFs(path);

            long availableBlocks;
            long blockSize;
            long totalBlocks;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableBlocks = statFs.getAvailableBlocks();
                blockSize = statFs.getBlockSize();
                totalBlocks = statFs.getBlockCount();
            } else {
                availableBlocks = statFs.getAvailableBlocksLong();
                blockSize = statFs.getBlockSizeLong();
                totalBlocks = statFs.getBlockCountLong();
            }

            long usedBytes = totalBlocks * blockSize - availableBlocks * blockSize;

            return usedBytes / sizeUnit.inBytes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    /**
     * copy file
     *
     * @param file          The file you want to copy
     * @param directoryName The destination directory
     * @param fileName      The destination file name
     */
    @Override
    public void copy(File file, String directoryName, String fileName) {
        if (!file.isFile()) {
            return;
        }

        FileInputStream inStream = null;
        FileOutputStream outStream = null;

        try {
            inStream = new FileInputStream(file);
            outStream = new FileOutputStream(new File(buildPath(directoryName, fileName)));
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            closeQuietly(inStream);
            closeQuietly(outStream);
        }
    }

    /**
     * move file to another folder
     *
     * @param file          The file you want to move
     * @param directoryName The destination directory
     * @param fileName      The destination file name
     */
    @Override
    public void move(File file, String directoryName, String fileName) {
        try {
            copy(file, directoryName, fileName);
            file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * read file from input stream
     *
     * @param stream
     * @return
     */
    byte[] readFile(final FileInputStream stream) {
        class Reader extends Thread {
            byte[] array = null;
        }

        Reader reader = new Reader() {
            public void run() {
                try {
                    LinkedList<ImmutablePair<byte[], Integer>> chunks = new LinkedList<>();

                    // read the file and build chunks
                    int size = 0;
                    int globalSize = 0;
                    do {
                        try {
                            int chunkSize = getConfiguration().getChuckSize();
                            // read chunk
                            byte[] buffer = new byte[chunkSize];
                            size = stream.read(buffer, 0, chunkSize);
                            if (size > 0) {
                                globalSize += size;

                                // add chunk to list
                                chunks.add(new ImmutablePair<>(buffer, size));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } while (size > 0);

                    try {
                        stream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    array = new byte[globalSize];

                    // append all chunks to one array
                    int offset = 0;
                    for (ImmutablePair<byte[], Integer> chunk : chunks) {
                        // flush chunk to array
                        System.arraycopy(chunk.element1 != null ? chunk.element1 : new byte[0], 0, array, offset, chunk.element2);
                        offset += chunk.element2;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        reader.start();
        try {
            reader.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed on reading file from storage while the locking Thread", e);
        }

        if (getConfiguration().isEncrypted()) {
            return encrypt(reader.array, Cipher.DECRYPT_MODE);
        } else {
            return reader.array;
        }
    }

    protected abstract String buildAbsolutePath();

    protected abstract String buildPath(String name);

    protected abstract String buildPath(String directoryName, String fileName);

    /**
     * Encrypt or Descrypt the content. <br>
     *
     * @param content        The content to encrypt or descrypt.
     * @param encryptionMode Use: {@link Cipher#ENCRYPT_MODE} or
     *                       {@link Cipher#DECRYPT_MODE}
     * @return
     */
    synchronized byte[] encrypt(byte[] content, int encryptionMode) {
        try {
            final byte[] secretKey = getConfiguration().getSecretKey();
            final byte[] ivx = getConfiguration().getIvParameter();

            return SecurityUtil.encrypt(content, encryptionMode, secretKey, ivx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Delete the directory and all sub content.
     *
     * @param path The absolute directory path. For example:
     *             <i>mnt/sdcard/NewFolder/</i>.
     * @return <code>True</code> if the directory was deleted, otherwise return
     * <code>False</code>
     */
    private boolean deleteDirectoryImpl(String path) {
        try {
            File directory = new File(path);

            // If the directory exists then delete
            if (directory.exists()) {
                File[] files = directory.listFiles();
                if (files == null) {
                    return true;
                }

                // Run on all sub files and folders and delete them
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectoryImpl(file.getAbsolutePath());
                    } else {
                        file.delete();
                    }
                }
            }

            return directory.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Get all files under the directory
     *
     * @param directory
     * @param out
     * @return
     */
    private void getDirectoryFilesImpl(File directory, List<File> out) {
        try {
            if (directory.exists()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            getDirectoryFilesImpl(file, out);
                        } else {
                            out.add(file);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}