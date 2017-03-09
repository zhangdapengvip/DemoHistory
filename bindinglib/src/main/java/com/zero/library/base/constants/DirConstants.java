package com.zero.library.base.constants;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.zero.library.base.utils.UtilsSharedPreference;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DirConstants {
    public static final String ASSETS_PATH = "file:///android_asset/";

    public static final String KEY_EXTERNAL_PATH = "external_path";
    public static String mHomeDir = "jkzy";
    public static final String DEFAULT_SD_DIR = getExternalStoragePath();
    public static final String DEFAULT_DIR = DEFAULT_SD_DIR + File.separator
            + mHomeDir + File.separator;
    public static final String DEFAULT_CATCH_DIR = DEFAULT_DIR + "catchs"
            + File.separator;
    public static final String DEFAULT_IMG_DIR = DEFAULT_DIR + "images"
            + File.separator;
    public static final String DEFAULT_VIDEO_DIR = DEFAULT_DIR + "videos"
            + File.separator;
    public static final String DEFAULT_AUDIO_DIR = DEFAULT_DIR + "audios"
            + File.separator;
    public static final String DEFAULT_FILES_DIR = DEFAULT_DIR + "files"
            + File.separator;
    public static final String DEFAULT_APP_DIR = DEFAULT_DIR + "apps"
            + File.separator;
    public static final String DEFAULT_TMB_DIR = DEFAULT_DIR + "thumbs"
            + File.separator;
    public static final String DEFAULT_WEB_DIR = DEFAULT_DIR + "webs"
            + File.separator;
    public static final String DEFAULT_INFO_DIR = DEFAULT_DIR + "info"
            + File.separator;
    public static final String DEFAULT_LOG_DIR = DEFAULT_DIR + "log"
            + File.separator;

    public static List<String> getCreatDirList() {
        List<String> dirLists = new ArrayList<>();
        dirLists.add(DEFAULT_DIR);
        dirLists.add(DEFAULT_CATCH_DIR);
        dirLists.add(DEFAULT_IMG_DIR);
        dirLists.add(DEFAULT_VIDEO_DIR);
        dirLists.add(DEFAULT_AUDIO_DIR);
        dirLists.add(DEFAULT_FILES_DIR);
        dirLists.add(DEFAULT_APP_DIR);
        dirLists.add(DEFAULT_TMB_DIR);
        dirLists.add(DEFAULT_WEB_DIR);
        dirLists.add(DEFAULT_INFO_DIR);
        dirLists.add(DEFAULT_LOG_DIR);
        return dirLists;
    }

    public static String getExternalStoragePath() {
        String path = UtilsSharedPreference.getStringValue(KEY_EXTERNAL_PATH);
        if (!TextUtils.isEmpty(path)) {
            return path;
        }
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File f = Environment.getExternalStorageDirectory();
            path = f.getPath();
        } else {
            path = getPath();
        }
        UtilsSharedPreference.setStringValue(KEY_EXTERNAL_PATH, path);
        return path;
    }

    private static String getPath() {
        String path;
        String expath = null;
        String inpath = null;
        Runtime runtime = Runtime.getRuntime();
        Process proc;
        try {
            proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure")) {
                    continue;
                }
                if (line.contains("asec")) {
                    continue;
                }

                if ((line.contains("fat") || line.contains("sdcardfs"))
                        && !line.contains("dmask")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        expath = columns[1];
                        System.out.println("this " + columns[1]
                                + " is external storage.");
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        inpath = columns[1];
                        System.out.println("this " + columns[1]
                                + " is internal storage.");
                    }
                }
            }
        } catch (IOException e) {
            Logger.e(e.getMessage());
        }
        if (null != expath) {
            if (null == inpath) {
                inpath = Environment.getExternalStorageDirectory().getPath();
            }
            path = getProperPath(expath, inpath);
        } else {
            path = Environment.getExternalStorageDirectory().getPath();
        }

        return path;
    }

    public static String getProperPath(String pa, String pb) {
        File fa = new File(pa);
        File fb = new File(pb);
        if (!fa.exists()) {
            if (!fb.exists()) {
                return null;
            } else {
                return pb;
            }
        }
        if (!fb.exists()) {
            return pa;
        }

        StatFs sfa = new StatFs(pa);
        StatFs sfb = null;
        try {
            sfb = new StatFs(pb);
        } catch (Exception e) {
            Logger.e(e.getMessage());
            return pa;
        }
        @SuppressWarnings("deprecation")
        long aa = (long) sfa.getAvailableBlocks() * (long) sfa.getBlockSize();
        @SuppressWarnings("deprecation")
        long ab = (long) sfb.getAvailableBlocks() * (long) sfb.getBlockSize();
        return (aa >= ab) ? pa : pb;

    }
}