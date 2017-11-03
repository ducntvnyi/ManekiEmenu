package com.qslib.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.View;

import com.qslib.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    // CAMERA
    public static final int CAMERA_REQUEST = 100;
    public static final int REQUEST_PICK_CONTENT = 101;

    /**
     * capture image
     *
     * @param activity
     * @param imageUri
     */
    public static void captureCamera(Activity activity, Uri imageUri) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            activity.startActivityForResult(intent, CAMERA_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * capture image
     *
     * @param fragment
     * @param imageUri
     */
    public static void captureCamera(Fragment fragment, Uri imageUri) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            fragment.startActivityForResult(intent, CAMERA_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * choose image from gallery
     *
     * @param activity
     * @param titleChooseImage
     */
    public static void chooseImageFromGallery(Activity activity, String titleChooseImage) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            activity.startActivityForResult(
                    Intent.createChooser(intent, titleChooseImage),
                    REQUEST_PICK_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * choose image from gallery
     *
     * @param fragment
     * @param titleChooseImage
     */
    public static void chooseImageFromGallery(Fragment fragment, String titleChooseImage) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            fragment.startActivityForResult(
                    Intent.createChooser(intent, titleChooseImage),
                    REQUEST_PICK_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filePath
     */
    public static void displayLengthFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists() || file.length() <= 0) return;

            // bytes
            double bytes = file.length();
            // kilobytes
            double kilobytes = (bytes / 1024);
            // megabytes
            double megabytes = (kilobytes / 1024);
            // gigabytes
            double gigabytes = (megabytes / 1024);
            // terabytes
            double terabytes = (gigabytes / 1024);
            // petabytes
            double petabytes = (terabytes / 1024);
            // exabytes
            double exabytes = (petabytes / 1024);
            // zettabytes
            double zettabytes = (exabytes / 1024);
            // yottabytes
            double yottabytes = (zettabytes / 1024);

            System.out.println("bytes : " + bytes);
            System.out.println("kilobytes : " + kilobytes);
            System.out.println("megabytes : " + megabytes);
            System.out.println("gigabytes : " + gigabytes);
            System.out.println("terabytes : " + terabytes);
            System.out.println("petabytes : " + petabytes);
            System.out.println("exabytes : " + exabytes);
            System.out.println("zettabytes : " + zettabytes);
            System.out.println("yottabytes : " + yottabytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * decode bitmap from path
     *
     * @param path
     * @return
     */
    public static Bitmap rotateImageWhenCaptureOrSelectFromGallery(String path) {
        try {
            if (StringUtils.isEmpty(path)) return null;
            File file = new File(path);
            if (!file.exists()) return null;

            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;

            int scale = 0;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }

            // decode with inSampleSize
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = scale;
            // get bitmap from sdcard
            Bitmap bmOrigin = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            // rotate image
            Bitmap bmRotate = bmOrigin;
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Matrix matrix = new Matrix();
            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                matrix.postRotate(180);
                bmRotate = Bitmap.createBitmap(bmOrigin, 0, 0, bmOrigin.getWidth(),
                        bmOrigin.getHeight(), matrix, true);
                return bmRotate;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
                bmRotate = Bitmap.createBitmap(bmOrigin, 0, 0, bmOrigin.getWidth(),
                        bmOrigin.getHeight(), matrix, true);
                return bmRotate;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
                bmRotate = Bitmap.createBitmap(bmOrigin, 0, 0, bmOrigin.getWidth(),
                        bmOrigin.getHeight(), matrix, true);
                return bmRotate;
            }

            return bmRotate;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param bitmap
     * @param scale  (0.5 = 50%)
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, float scale) {
        try {
            return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scale), (int) (bitmap.getHeight() * scale), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * save bit map to sdcard
     *
     * @param bitmap
     * @param path
     */
    public static boolean saveBitmap(Bitmap bitmap, String path) {
        boolean result = false;
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(path);
            result = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            bitmap.recycle();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * get bitmap from view
     *
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view) {
        try {
            if (view.getWidth() == 0 || view.getHeight() == 0)
                return null;

            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * convert bitmap to base 64
     *
     * @param bitmap
     * @return
     */
    public static String encodeBitmapToBase64(Bitmap bitmap) {
        try {
            if (bitmap == null) return null;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * convert base 64 to bitmap
     *
     * @param base64
     * @return
     */
    public static Bitmap decodeBitmapFromBase64(String base64) {
        try {
            byte[] decodedByte = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Drawable setColorFilterToImage(Context context, int imageId, int color) {
        // set color filter
        Drawable drawable = null;

        try {
            drawable = context.getResources().getDrawable(imageId);
            drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return drawable;
    }

    public static boolean isImage(String path) {
        try {
            if (StringUtils.isEmpty(path)) return false;
            return path.toLowerCase().contains(".jpg") || path.toLowerCase().contains(".jpeg") ||
                    path.toLowerCase().contains(".png") || path.toLowerCase().contains(".bmp") ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
