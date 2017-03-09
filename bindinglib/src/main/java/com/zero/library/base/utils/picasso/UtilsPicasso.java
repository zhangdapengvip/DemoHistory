package com.zero.library.base.utils.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zero.library.base.utils.picasso.transformations.CropCircleTransformation;
import com.zero.library.base.utils.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by MacPro on 16/3/12.
 * Picasso加载类
 */
public class UtilsPicasso {

    /**
     * Picasso使用demo注释
     *
     * @param context
     * @param defaultImg
     * @param url
     * @param imageView
     */
    private final void demo(Context context, int defaultImg, String url,
                            ImageView imageView) {
        Picasso.with(context)//初始化
                .load(url)//加载Url
                //NO_CACHE是指图片加载时放弃在内存缓存中查找，NO_STORE是指图片加载完不缓存在内存中
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(1, 1)//按像素重新计算大小
                .resizeDimen(1, 1)//按dimen从新计算大小
                .config(Bitmap.Config.RGB_565)//图片质量
                .centerCrop()//裁切中间
//              .transform(new Transformation())//自定义图片转换
                .placeholder(defaultImg)//加载中显示内容
                .error(defaultImg)//加载失败显示内容
                .into(imageView);//设置ImageView

        Picasso.with(context).cancelRequest(imageView);//取消加载
    }

    /**
     * @param context     当前上下文
     * @param url         加载url
     * @param defaultImg  默认图片
     * @param imageView   设置ImageView
     * @param dimenWidth  重新计算宽度
     * @param dimenHeight 重新计算高度
     */
    public static final void loadImage(Context context, final String url, int defaultImg,
                                       final ImageView imageView, int dimenWidth, int dimenHeight) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(defaultImg);
            return;
        }
        if (null != drawableCatch.get(url)) {
            imageView.setImageDrawable(drawableCatch.get(url));
            return;
        }
        Picasso.with(context)
                .load(url)
                .resizeDimen(dimenWidth, dimenHeight)
                .config(Bitmap.Config.RGB_565)
                .placeholder(defaultImg)
                .error(defaultImg)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        drawableCatch.put(url, imageView.getDrawable());
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    /**
     * @param context    当前上下文
     * @param url        加载url
     * @param defaultImg 默认图片
     * @param imageView  设置ImageView
     */
    public static final void loadImage(Context context, String url, int defaultImg,
                                       ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(defaultImg);
            return;
        }
        Picasso.with(context)
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .config(Bitmap.Config.RGB_565)
                .placeholder(defaultImg)
                .error(defaultImg)
                .into(imageView);
    }

    /**
     * @param context    当前上下文
     * @param url        加载url
     * @param defaultImg 默认图片
     * @param imageView  设置ImageView
     */
    public static final void loadCatchImage(Context context, String url, int defaultImg,
                                            ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(defaultImg);
            return;
        }
        Picasso.with(context)
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .placeholder(defaultImg)
                .error(defaultImg)
                .into(imageView);
    }

    /**
     * @param context    当前上下文
     * @param url        加载url
     * @param defaultImg 默认图片
     * @param imageView  设置ImageView
     * @param dimenId    裁切Dimen值
     */
    public static final void loadCenterImage(Context context, String url, int defaultImg,
                                             ImageView imageView, int dimenId) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(defaultImg);
            return;
        }
        Picasso.with(context)
                .load(url)
                .resizeDimen(dimenId, dimenId)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .placeholder(defaultImg)
                .error(defaultImg)
                .into(imageView);
    }

    /**
     * @param context    当前上下文
     * @param url        加载url
     * @param defaultImg 默认图片
     * @param imageView  设置ImageView
     * @param dimenId    裁切Dimen值
     */
    public static final void loadCircleImage(Context context, String url, int defaultImg,
                                             ImageView imageView, int dimenId) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(defaultImg);
            return;
        }
        Picasso.with(context)
                .load(url)
                .resizeDimen(dimenId, dimenId)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .transform(new CropCircleTransformation())
                .placeholder(defaultImg)
                .error(defaultImg)
                .into(imageView);
    }

    /**
     * @param context    当前上下文
     * @param url        加载url
     * @param defaultImg 默认图片
     * @param imageView  设置ImageView
     * @param dimenId    裁切Dimen值
     * @param corners    圆角角度
     */

    public static final void loadRoundImage(Context context, final String url, int defaultImg,
                                            final ImageView imageView, int dimenId, int corners) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(defaultImg);
            return;
        }
        if (null != drawableCatch.get(url)) {
            imageView.setImageDrawable(drawableCatch.get(url));
            return;
        }
        Picasso.with(context)
                .load(url)
                .resizeDimen(dimenId, dimenId)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .transform(new RoundedCornersTransformation(corners, 0))
                .placeholder(defaultImg)
                .error(defaultImg)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        drawableCatch.put(url, imageView.getDrawable());
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    /**
     * @return 当前App分配的空间大小
     */
    public static int getAllowSize() {
        return (int) Runtime.getRuntime().maxMemory();
    }

    /**
     * Bitmap缓存,空间大小为App的1/8
     */
    public static final LruCache<String, Bitmap> bitmapCatch = new LruCache<String, Bitmap>(
            getAllowSize() / 8) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }
    };


    /**
     * Drawable缓存,空间大小为App的1/8
     */
    public static final LruCache<String, Drawable> drawableCatch = new LruCache<String, Drawable>(
            getAllowSize() / 8) {
        @Override
        protected int sizeOf(String key, Drawable value) {
            return value.getIntrinsicWidth() * value.getIntrinsicHeight();
        }
    };
}
