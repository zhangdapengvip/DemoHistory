package com.zero.library.base.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zero.library.base.AppBase;
import com.zero.library.base.bean.AppInfo;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.R;

public class UtilsFile {
    private static final float KBYTES = 1024;
    private static final float MEGAS = 1024 * KBYTES;
    /**
     * The asset file header
     */
    public static final String ASSETS = "assets://";
    /**
     * Application file type. APK files
     */
    public static final int FILE_TYPE_APP = 0;
    /**
     * Video file type. ".mp4", ".rm", ".mpg", ".avi", ".mpeg", ".mov", ".rmvb",
     * ".mkv", ".3gp"
     */
    public static final int FILE_TYPE_VIDEO = 1;
    /**
     * Audio file type. ".mp3", ".wav", ".ogg", ".midi", ".wma"
     */
    public static final int FILE_TYPE_AUDIO = 2;
    /**
     * Image file type. ".png", ".gif", ".jpg", ".jpeg", ".bmp"
     */
    public static final int FILE_TYPE_IMAGE = 3;
    /**
     * Office file type.
     */
    public static final int FILE_TYPE_OFFICE = 4;
    /**
     * Pdf file type.
     */
    public static final int FILE_TYPE_PDF = 5;
    /**
     * text file type. ".txt", ".log", ".info"
     */
    public static final int FILE_TYPE_TEXT = 6;
    /**
     * html file type. ".html", ".xml"
     */
    public static final int FILE_TYPE_HTML = 7;
    /**
     * Raw file type
     */
    public static final int FILE_TYPE_DATA = 8;
    /**
     * jar file type
     */
    public static final int FILE_TYPE_LIB = 9;
    /**
     * download application file type
     */
    public static final int FILE_TYPE_SELF = 10;
    /**
     * zpt file type. ".zpt"
     */
    public static final int FILE_TYPE_ZPT = 11;
    /**
     * zml file type. ".zml"
     */
    public static final int FILE_TYPE_ZML = 12;
    /**
     * chat emo file type ".emo"
     */
    public static final int FILE_TYPE_EMO = 13;
    /**
     * chat gca file type ".gca"
     */
    public static final int FILE_TYPE_GCA = 14;

    /**
     * Unknown file type
     */
    public static final int FILE_TYPE_UNKNOWN = 99;
    private static final String[] SIMAGES = new String[]{".png", ".gif",
            ".jpg", ".jpeg", ".bmp"};
    private static final String[] SAUDIOS = new String[]{".mp3", ".wav",
            ".ogg", ".midi", ".wma"};
    private static final String[] SVIDEOS = new String[]{".mp4", ".rm",
            ".mpg", ".avi", ".mpeg", ".mov", ".rmvb", ".mkv", ".3gp"};
    private static final String[] SAPKS = new String[]{".apk"};
    private static final String[] STEXTS = new String[]{".txt", ".log",
            ".info"};
    private static final String[] SZML = new String[]{".zml"};
    private static final String[] SZPT = new String[]{".zpt"};
    private static final String[] SPDF = new String[]{".pdf"};
    private static final String[] SEMO = new String[]{".emo"};
    private static final String[] SGCA = new String[]{".gca"};

    public static String getSizeString(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (size > MEGAS) {
            return String.valueOf(df.format(size / MEGAS)) + "MB";
        } else {
            return String.valueOf(df.format(size / KBYTES)) + "KB";
        }
    }

    /**
     * Checks whether checkItsEnd ends with one of the Strings from fileEndings
     */
    public static boolean checkEndsWithInStringArray(String checkItsEnd,
                                                     String[] fileEndings) {
        checkItsEnd = checkItsEnd.toLowerCase();
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    /**
     * Find the proper activity to open the file
     *
     * @param context
     * @param aFile   the source file
     */
    public static void openFile(Context context, File aFile) {
        Intent intent = getOpenIntent(context, aFile.getAbsolutePath());
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.e("failed to open the file", e);
            Toast.makeText(context, "can't open this file", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * Find the proper activity to open the file
     *
     * @param context
     * @param path    the source file path
     */
    public static void openFile(Context context, String path) {
        File file = new File(path);
        openFile(context, file);
    }

    public static void openFile(Activity context, int reqId, String filepath,
                                String title) {
        Intent intent = getOpenIntent(context, filepath);
        intent.putExtra("title", title);
        try {
            context.startActivityForResult(intent, reqId);
        } catch (Exception e) {
            Logger.e("failed to open the file", e);
            Toast.makeText(context, "can't open this file", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public static void openFile(Context context, String filepath, String title) {
        Intent intent = getOpenIntent(context, filepath);
        intent.putExtra("title", title);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.e("failed to open the file", e);
            Toast.makeText(context, "can't open this file", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * Get the open Intent for the file
     *
     * @param context
     * @param path    the file path
     * @return the Intent
     */
    public static Intent getOpenIntent(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(path);
        int type = getFileType(context, path);
        switch (type) {
            case UtilsFile.FILE_TYPE_APP:
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                break;
            case UtilsFile.FILE_TYPE_VIDEO:
                intent.setDataAndType(Uri.fromFile(file), "video/*");
                break;
            case UtilsFile.FILE_TYPE_AUDIO:
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
                break;
            case UtilsFile.FILE_TYPE_IMAGE:
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                break;
            case UtilsFile.FILE_TYPE_OFFICE:
                break;
            case UtilsFile.FILE_TYPE_PDF:
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                if (useMinePdfViewer(context)) {
                    intent.setClassName(context, MINE_PDF_VIEWER);
                }
                break;
            case UtilsFile.FILE_TYPE_TEXT:
                intent.setDataAndType(Uri.fromFile(file), "text/plain");
                break;
            case UtilsFile.FILE_TYPE_HTML:
                intent.setDataAndType(Uri.fromFile(file), "text/html");
                break;
            case UtilsFile.FILE_TYPE_UNKNOWN:
            case UtilsFile.FILE_TYPE_DATA:
            case UtilsFile.FILE_TYPE_LIB:
                intent.setData(Uri.fromFile(file));
                break;
            case UtilsFile.FILE_TYPE_ZPT:
                break;
            case UtilsFile.FILE_TYPE_ZML:
                break;
            default:
                break;
        }

        return intent;
    }

    private static final String MINE_PDF_VIEWER = "cx.hell.android.pdfview.OpenFileActivity";

    private static boolean useMinePdfViewer(Context context) {
        try {
            String s = "cx.hell.android.lib.pdf.PDF";
            Class<?> cls = Class.forName(s);
            Method m = cls.getMethod("setApplicationContext", Context.class);
            m.invoke(null, context.getApplicationContext());
            Class.forName(MINE_PDF_VIEWER);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get the file type. See {@link #FILE_TYPE_APP} to {@link #FILE_TYPE_GCA}
     *
     * @param context
     * @param path
     * @return
     */
    public static int getFileType(Context context, String path) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        return getFileType(context, uri);
    }

    /**
     * Get the file type
     *
     * @param context
     * @param uri
     * @return
     */
    public static int getFileType(Context context, Uri uri) {
        int type = FILE_TYPE_UNKNOWN;
        String fileName = uri.getLastPathSegment();
        if (checkEndsWithInStringArray(fileName, SIMAGES)) {
            type = FILE_TYPE_IMAGE;
        } else if (checkEndsWithInStringArray(fileName, SAUDIOS)) {
            type = FILE_TYPE_AUDIO;
        } else if (checkEndsWithInStringArray(fileName, SVIDEOS)) {
            type = FILE_TYPE_VIDEO;
        } else if (checkEndsWithInStringArray(fileName, SAPKS)) {
            type = FILE_TYPE_APP;
        } else if (checkEndsWithInStringArray(fileName, STEXTS)) {
            type = FILE_TYPE_TEXT;
        } else if (checkEndsWithInStringArray(fileName, SZPT)) {
            type = FILE_TYPE_ZPT;
        } else if (checkEndsWithInStringArray(fileName, SZML)) {
            type = FILE_TYPE_ZML;
        } else if (checkEndsWithInStringArray(fileName, SPDF)) {
            type = FILE_TYPE_PDF;
        } else if (checkEndsWithInStringArray(fileName, SEMO)) {
            type = FILE_TYPE_EMO;
        } else if (checkEndsWithInStringArray(fileName, SGCA)) {
            type = FILE_TYPE_GCA;
        } else {
            Logger.d("get the file type no match:" + fileName);
        }

        return type;
    }

    /**
     * Get an application(apk file) information
     *
     * @param context
     * @param file
     * @param defaultName
     * @return
     */
    public static AppInfo getAppInfo(Context context, File file,
                                     String defaultName) throws Exception {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(file.getAbsolutePath(),
                PackageManager.GET_ACTIVITIES);
        if (null == info) {
            throw new Exception("Error package");
        }
        AppInfo localAppInfo = new AppInfo();
        ApplicationInfo appInfo = info.applicationInfo;
        appInfo.sourceDir = file.getAbsolutePath();
        appInfo.publicSourceDir = file.getAbsolutePath();
        if ((appInfo.loadIcon(pm)) != null) {
            localAppInfo.setAppIcon(appInfo.loadIcon(pm));
            localAppInfo.setAppName(appInfo.loadLabel(pm).toString());
            localAppInfo.setPackageName(appInfo.packageName);
            localAppInfo.setSysApp(false);
            localAppInfo.setLocalFilePath(file.getAbsolutePath());
        } else {
            localAppInfo.setAppName(defaultName);
        }
        return localAppInfo;
    }

    /**
     * Get the thumbnail image for a video file
     *
     * @param file
     * @param i
     * @return
     */
    public static String getVedioThumbnail(File file, int i) {
        File thumbFile = new File(DirConstants.DEFAULT_TMB_DIR + file.getName()
                + ".jpg");
        if (!thumbFile.exists() || thumbFile.length() == 0) {
            try {
                thumbFile.createNewFile();
                Bitmap bmp = ThumbnailUtils.createVideoThumbnail(
                        file.getAbsolutePath(),
                        MediaStore.Images.Thumbnails.MINI_KIND);
                if (null != bmp) {
                    FileOutputStream fos = new FileOutputStream(thumbFile);
                    Bitmap dstbmp = Bitmap
                            .createBitmap(bmp, 0, 0, bmp.getWidth(),
                                    bmp.getHeight(), new Matrix(), true);
                    dstbmp.compress(CompressFormat.JPEG, 100, fos);
                    fos.close();
                    bmp.recycle();
                    dstbmp.recycle();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return thumbFile.getPath();
    }

    /**
     * rename a file
     *
     * @param oldPath
     * @param newPath
     */
    public static void renameFile(String oldPath, String newPath) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the extension of a file. like "zml" from "dkj.zml"
     *
     * @param url
     * @return
     */
    public static String getExtensionFromUri(String url) {
        String extension = null;
        int index = url.lastIndexOf(".");
        if (index + 1 < url.length()) {
            extension = url.substring(index + 1);
        }
        return extension;
    }

    /**
     * Get the file name from a url. like "a.mp4" from "dsf/djfa/87/a.mp4"
     *
     * @param uri
     * @return
     */
    public static String getNameFromUri(String uri) {
        String fileName = UUID.randomUUID() + ".unknown";
        if (!TextUtils.isEmpty(uri)) {
            int index = uri.lastIndexOf(File.separator);
            if (-1 != index && index != uri.length() - 1) {
                fileName = uri.substring(index + 1);
            }
        }
        return fileName;
    }

    /**
     * Get the storage path for the type of file
     *
     * @param type
     * @return
     */
    public static String getStorePathByType(int type) {
        String path = DirConstants.DEFAULT_DIR;
        switch (type) {
            case UtilsFile.FILE_TYPE_APP:
                path = DirConstants.DEFAULT_APP_DIR;
                break;
            case UtilsFile.FILE_TYPE_VIDEO:
                path = DirConstants.DEFAULT_VIDEO_DIR;
                break;
            case UtilsFile.FILE_TYPE_AUDIO:
                path = DirConstants.DEFAULT_AUDIO_DIR;
                break;
            case UtilsFile.FILE_TYPE_IMAGE:
                path = DirConstants.DEFAULT_IMG_DIR;
                break;
            case UtilsFile.FILE_TYPE_OFFICE:
            case UtilsFile.FILE_TYPE_PDF:
            case UtilsFile.FILE_TYPE_TEXT:
            case UtilsFile.FILE_TYPE_HTML:
            case UtilsFile.FILE_TYPE_UNKNOWN:
            case UtilsFile.FILE_TYPE_DATA:
            case UtilsFile.FILE_TYPE_LIB:
            case UtilsFile.FILE_TYPE_ZPT:
            case UtilsFile.FILE_TYPE_ZML:
            default:
                path = DirConstants.DEFAULT_FILES_DIR;
                break;
        }
        return path;
    }

    /**
     * Get the assets file path to {@link AssetManager#open}
     *
     * @param src
     * @return
     */
    public static String getAssetsFilePath(String src) {
        String path = null;
        if (src.contains(ASSETS)) {
            path = src.substring(src.indexOf(ASSETS) + ASSETS.length());
        }
        return path;
    }

    /**
     * Open an InputStream for a file
     *
     * @param context
     * @param file
     * @return
     */
    public static InputStream getFileInputStream(Context context, String file) {
        InputStream is = null;
        try {
            if (file.contains(ASSETS)) {
                is = context.getAssets().open(getAssetsFilePath(file));
            } else {
                is = new FileInputStream(new File(file));
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return is;
    }

    /**
     * Read and return the text file content. If the content length is greater
     * than 1024K, exception will be thrown.
     *
     * @param context
     * @param file
     * @return
     */
    public static String getFileContent(Context context, String file) {
        InputStream stream = getFileInputStream(context, file);
        if (null == stream) {
            return null;
        }
        int max = 1024 * 1024;
        try {
            BufferedReader reader = null;
            int bufSize = 128;
            char[] buffer = new char[bufSize];
            InputStreamReader inputStreamReader = null;
            inputStreamReader = new InputStreamReader(stream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            int size = 0;
            int length = 0;
            while ((size = reader.read(buffer, 0, bufSize)) > 0) {
                sb.append(buffer, 0, size);
                length += size;
                if (length > max) {
                    throw new Exception(
                            "The content of the file is greater than 1024*1024 bytes");
                }
            }
            return sb.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean isFileExist(Context context, String file) {
        boolean exist = false;
        InputStream is = getFileInputStream(context, file);
        if (null != is) {
            exist = true;
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return exist;
    }

    /**
     * Get the file length for a file
     *
     * @param context
     * @param file
     * @return
     */
    public static long getFileLength(Context context, String file) {
        long size = 0;
        if (file.contains(ASSETS)) {
            try {
                size = context.getAssets().openFd(getAssetsFilePath(file))
                        .getLength();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            size = new File(file).length();
        }
        return size;
    }

    /**
     * Copy a folder to another folder
     *
     * @param context
     * @param src
     * @param dst
     * @return
     */
    public static int copyFolder(Context context, String src, String dst) {
        int result = 0;
        File file = new File(src);
        if (!file.isDirectory()) {
            result = copyFile(context, src, dst);
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                result += copyFolder(context, f.getAbsolutePath(), dst
                        + File.separator + f.getName());
            }
        }
        return result;
    }

    /**
     * copy a file to another file
     *
     * @param context
     * @param src
     * @param dst
     * @return
     */
    public static int copyFile(Context context, String src, String dst) {
        int size = 0;
        InputStream is = null;
        OutputStream os = null;
        try {
            if (src.contains("assets")) {
                is = context.getAssets().open(getAssetsFilePath(src));
            } else {
                is = new FileInputStream(src);
            }
            if (null != is) {
                os = new FileOutputStream(dst);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) > 0) {
                    os.write(buffer, 0, len);
                    size += len;
                }
            }
        } catch (IOException e) {
            Logger.e("failed to handle the file copy from: " + src + " To:"
                    + dst);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (0 != size) {
                Logger.d("success copy file:" + src + " to:" + dst);
            }
        }
        return size;
    }

    public static boolean isValidImageFile(String path) {
        boolean valid = false;
        try {
            Options opts = new Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);
            if (-1 != opts.outHeight && -1 != opts.outWidth) {
                valid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public static List<Bitmap> getImageFromAssetsFile(Context context,
                                                      String folderName) throws IOException {
        AssetManager am = context.getResources().getAssets();
        String[] paths = context.getResources().getAssets().list(folderName);
        if (null != paths && paths.length != 0) {
            List<Bitmap> list = new ArrayList<Bitmap>();
            for (String path : paths) {
                InputStream is = am.open(folderName + "/" + path);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                list.add(bitmap);
                is.close();
            }
            return list;
        }
        return null;
    }

    public static boolean prepareDir(String filePath) {
        if (!filePath.endsWith(File.separator)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists() || file.mkdirs();
    }

    public static void deleteAllByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if (item.isDirectory()) {
                    deleteAllByDirectory(item);
                }
                item.delete();
            }
        }
    }

    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (parentFile.exists() == false) {
            prepareDir(parentFile.getAbsolutePath());
        }
        try {
            boolean result = file.createNewFile();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public static String readProperties(String filePath, String key,
                                        String defaultValue) {
        if (UtilsString.isEmpty(key) || UtilsString.isEmpty(filePath)) {
            return null;
        }
        String value = null;
        FileInputStream fis = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);
            value = p.getProperty(key, defaultValue);
        } catch (IOException e) {
            Logger.e(e.toString());
        } finally {
            UtilsIO.close(fis);
        }
        return value;
    }

    /**
     * 从指定地址下载文件
     *
     * @param path           下载地址
     * @param progressDialog 进度条
     * @param outFile        输出文件
     * @return 输出文件
     * @throws Exception
     */
    public static File getFileFromServer(String path, ProgressDialog progressDialog, File outFile) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Encoding", "identity");
            conn.setConnectTimeout(5000);
            progressDialog.setMax(conn.getContentLength());
            inputStream = conn.getInputStream();
            outputStream = new FileOutputStream(outFile);
            bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len, total = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                total += len;
                progressDialog.setProgress(total);
            }
            return outFile;
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            return null;
        } finally {
            try {
                if (null != conn) conn.disconnect();
                if (null != outputStream) outputStream.close();
                if (null != bufferedInputStream) bufferedInputStream.close();
                if (null != inputStream) inputStream.close();
            } catch (Exception closeError) {
                closeError.printStackTrace();
            }
        }
    }

    /**
     * 获取文件的byte数组
     *
     * @param file 目标文件
     * @return byte数组
     */
    public static byte[] getByteFromFile(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            Logger.e(e.getMessage());
        }
        return buffer;
    }
}
