package com.qslib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    private static String TAG = FileUtils.class.getSimpleName();

    /**
     * @param path
     * @return
     */
    public static boolean isExists(String path) {
        try {
            File file = new File(path);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @param context
     * @param folderName
     * @return
     */
    public static File getRootFolderPath(Context context, String folderName) {
        try {
            // check sdcard existed
            if (isSdPresent()) {
                try {
                    File sdPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), folderName);
                    if (!sdPath.exists()) sdPath.mkdirs();
                    return sdPath;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    File cacheDir = new File(context.getCacheDir(), folderName);
                    if (!cacheDir.exists()) cacheDir.mkdirs();
                    return cacheDir;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * get path by path and folder name
     *
     * @param path
     * @param folderName
     * @return
     */
    public static File getFolderPath(String path, String folderName) {
        return createFolder(path, folderName);
    }

    /**
     * get file name
     *
     * @param path
     * @param fileName
     * @return
     */
    public static String getFileName(String path, String fileName) {
        try {
            File file = new File(path, fileName);
            if (file.exists()) {
                String fileNamePath = file.getAbsolutePath();
                Log.e("fileNamePath", "fileNamePath:: " + fileNamePath);
                return fileNamePath;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /**
     * @return
     */
    public static boolean isSdPresent() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * change file or folder name in android
     *
     * @param fileOrFolderPath
     * @param newName
     */
    public static boolean renameFileOrFolder(String fileOrFolderPath, String newName) {
        try {
            // old file or folder
            File oldFile = new File(fileOrFolderPath);
            if (!oldFile.exists()) return false;

            // new file or folder
            File newFile = new File(oldFile.getParent(), newName);

            // rename
            return oldFile.renameTo(newFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * @param path
     * @param folderName
     */
    public static File createFolder(String path, String folderName) {
        try {
            Log.i("Create Folder", "path:: " + path + ", folderName:: " + folderName);
            File folder = new File(path, folderName);
            if (!folder.exists()) {
                folder.mkdirs();
                System.out.println("create folder is successfully!");
            }

            return folder;
        } catch (Exception ex) {
            System.out.println("create folder is fail! " + ex.getMessage());
        }

        return null;
    }

    /**
     * copy file
     *
     * @param src
     * @param dst
     */
    public static boolean copy(File src, File dst) {
        try {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
            in.close();
            out.close();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * convert file to base64
     *
     * @param file
     * @return
     */
    public static String convertFileToBase64(File file) {
        try {
            InputStream inputStream = new FileInputStream(file.getAbsolutePath());
            byte[] buffer = new byte[8192];
            int bytesRead = 0;

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1)
                output64.write(buffer, 0, bytesRead);
            output64.close();

            return output.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /**
     * get byte[] from path
     *
     * @param path
     * @return
     */
    public static byte[] getByteFromPath(String path) {
        try {
            if (StringUtils.isEmpty(path)) return null;
            File file = new File(path);
            if (!file.exists())return null;

            byte[] buf = new byte[(int) file.length()];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InputStream fis = new FileInputStream(file);
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
                Log.i("", "read num bytes: " + readNum);
            }

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
