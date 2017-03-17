package com.http.utils;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

/**
 * Created by ZeroAries on 2016/4/20.
 * 获取图片颜色
 */
public class PaletteUtils {

    public static Palette.Builder getBuilder(Bitmap bitmap) {
        return new Palette.Builder(bitmap);
    }

    /**
     * 获取Bitmap的颜色，设置字体颜色
     * Vibrant(充满活力的)
     * Vibrant dark(充满活力的黑)
     * Vibrant light(充满活力的亮)
     * Muted(柔和的)
     * Muted dark(柔和的黑)
     * Muted lighr(柔和的亮)
     *
     * @param bitmap
     * @param listener
     */
    public static void generate(Bitmap bitmap, Palette.PaletteAsyncListener listener) {
        getBuilder(bitmap).generate(listener);
    }
}
