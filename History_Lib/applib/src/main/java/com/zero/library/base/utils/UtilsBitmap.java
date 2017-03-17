package com.zero.library.base.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;

import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 */
public class UtilsBitmap {

    /**
     * 调用系统相机
     *
     * @param uri 保存Uri路径
     * @return Intent
     */
    public static Intent getCameraIntent(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * 调用系统相册
     *
     * @return Intent
     */
    public static Intent getGalleryIntent() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        return intent;
    }

    /**
     * 获取系统相片裁剪Intent,通过onActivityResult中data.getParcelableExtra("data");
     *
     * @param uri  图片Uri路径
     * @param outX 裁剪X轴长度
     * @param outY 裁剪Y轴长度
     * @return Intent
     */
    public static Intent getCropIntent(Uri uri, int outX, int outY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outX);
        intent.putExtra("outputY", outY);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        return intent;
    }

    /**
     * 读取图片文件旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    public static int getPicRotate(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            Logger.e(e.getMessage());
        }
        return degree;
    }

    /**
     * 旋转图片至正向
     *
     * @param bitmap 需要旋转的图片
     * @param path   图片的路径
     */
    public static Bitmap reviewPicRotate(Bitmap bitmap, String path) {
        int degree = getPicRotate(path);
        Bitmap newBitmap = rotaingImageView(degree, bitmap);
        return newBitmap;
    }

    /**
     * 将图片旋转固定角度
     *
     * @param angle
     * @param mBitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap mBitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap b = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
                mBitmap.getHeight(), matrix, true);
        if (b != null && b != mBitmap) {
            mBitmap.recycle();
            mBitmap = b;
        }
        return mBitmap;
    }

    /**
     * 保存图片至指定路径
     *
     * @param bm
     * @param dir
     * @param name
     */
    public static void saveBitmap(Bitmap bm, String dir, String name) {
        File filesFolder = new File(dir);
        if (!filesFolder.exists()) {
            filesFolder.mkdirs();
            filesFolder.setLastModified(System.currentTimeMillis());
        }
        File f = new File(dir, name);
        if (f.exists()) f.delete();
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    /**
     * 获取bitmap的byte数组
     *
     * @param bmp bitmap
     * @return byte数组
     */
    public static byte[] getByteFromBitmap(Bitmap bmp) {
        if (null == bmp) {
            return null;
        }
        int size = bmp.getWidth() * bmp.getHeight() * 4;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(size);
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return byteStream.toByteArray();
    }

    /**
     * 质量压缩，压缩图片至Bitmap在指定大小内，
     *
     * @param bmp    源bitmap
     * @param sizeKb 指定大小
     * @param file   压缩后输出文件
     */
    public static void compressBitmapToFile(Bitmap bmp, int sizeKb, File file) {
        ByteArrayOutputStream byteOutStream = compressBitmapToStream(bmp, sizeKb);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteOutStream.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 质量压缩，压缩图片至Bitmap在指定大小内
     *
     * @param bmp    源bitmap
     * @param sizeKb 指定大小
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressBitmap(Bitmap bmp, int sizeKb) {
        ByteArrayOutputStream byteOutStream = compressBitmapToStream(bmp, sizeKb);
        ByteArrayInputStream isBm = new ByteArrayInputStream(byteOutStream.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 质量压缩，压缩图片至输出流在指定大小内，
     *
     * @param bmp    源bitmap
     * @param sizeKb 指定大小
     * @return 压缩图片输出流
     */
    private static ByteArrayOutputStream compressBitmapToStream(Bitmap bmp, int sizeKb) {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteOutStream);
        int options = 100;
        while (byteOutStream.toByteArray().length / 1024 > sizeKb) {
            byteOutStream.reset();
            bmp.compress(Bitmap.CompressFormat.JPEG, options, byteOutStream);
            options -= 10;
        }
        return byteOutStream;
    }

    /**
     * 尺寸压缩，压缩图片至指定分辨率
     *
     * @param srcPath   目标图片位置
     * @param tarPath   输出位置
     * @param name      输出名称
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     */
    public static void compressBitmapToFile(String srcPath, String tarPath,
                                            String name, int maxWidth, int maxHeight) {

        int picRotate = getPicRotate(srcPath);
        Bitmap bitmap = compressBitmap(srcPath, maxWidth, maxHeight);
        Bitmap newBitmap = rotaingImageView(picRotate, bitmap);
        saveBitmap(newBitmap, tarPath, name);
    }

    /**
     * 尺寸压缩，防止图片过大导致内存溢出
     *
     * @param srcPath   图片路径
     * @param maxWidth  最大宽
     * @param maxHeight 最大高
     * @return
     */
    public static Bitmap compressBitmap(String srcPath, int maxWidth,
                                        int maxHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        try {
            opts.inJustDecodeBounds = true;
            byte[] byteFile = UtilsFile.getByteFromFile(new File(srcPath));
            BitmapFactory.decodeByteArray(byteFile, 0, byteFile.length, opts);
//                BitmapFactory.decodeStream(imageStream, null, opts);
            double scaleW = opts.outWidth * 1.0 / maxWidth;
            double scaleH = opts.outHeight * 1.0 / maxHeight;
            double scale = Math.max(scaleW, scaleH);
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = (int) Math.floor(scale + 0.5);
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            opts.inInputShareable = true;
            opts.inPurgeable = true;
            return BitmapFactory.decodeByteArray(byteFile, 0, byteFile.length, opts);


        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return null;
    }

    /**
     * 获取图片的大小Pair{@link Pair}
     *
     * @param imageFile 图片路径
     * @return 图片大小Pair
     */
    public static Pair<Integer, Integer> getBitmapSize(File imageFile) {
        BitmapFactory.Options opts = null;
        try {
            opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(imageFile), null,
                    opts);
            return Pair.create(opts.outWidth, opts.outHeight);
        } catch (Exception e) {
            Logger.e(e.getMessage());
        } finally {
        }
        return null;
    }

    /**
     * 获取图片占用内存大小
     *
     * @param bitmap Bitmap
     * @return 内存大小
     */
    @SuppressLint("NewApi")
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 获取圆角位图的方法
     *
     * @param bitmap 需要转化成圆角的位图
     * @param pixels 圆角的度数，数值越大，圆角越大
     * @return 处理后的圆角位图
     */
    public static Bitmap getFilletBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return output;
    }


    /**
     * 将Drawable转换成Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public static boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(Context context, Uri uri) {
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            return getRealPathFromUriKitKatPlus(context, uri);
        } else {
            return getRealPathFromUriMinusKitKat(context, uri);
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getRealPathFromUriKitKatPlus(Context context, Uri uri) {
        Cursor cursor;
        String wholeId = DocumentsContract.getDocumentId(uri);
        String id = wholeId.split(":")[1];

        try {
            String proj[] = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    proj, sel, new String[]{id}, null);
            int column_index = cursor.getColumnIndexOrThrow(proj[0]);
            String filePath = "";
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(column_index);
            }
            cursor.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    private static String getRealPathFromUriMinusKitKat(Context context, Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        System.out.println(picturePath);
        return picturePath;
    }
}