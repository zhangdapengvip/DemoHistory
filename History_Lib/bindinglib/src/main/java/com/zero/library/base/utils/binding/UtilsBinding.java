package com.zero.library.base.utils.binding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.widget.ImageView;

import com.zero.library.R;
import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.utils.picasso.UtilsPicasso;

import java.util.Date;

/**
 * Created by ZeroAries on 2016/4/21.
 * DataBinding扩展类
 * <variable     name="imgInfo"     type="com.zero.library.base.utils.binding.ImgBinding"/>
 * app:imageNormal="@{imgInfo}"默认加载图片ImgBinding(imgUrl, defRes);
 * app:imageCatch="@{imgInfo}"默认加载图片ImgBinding(imgUrl, defRes);
 * app:imageSquare="@{imgInfo}"裁切方形图片ImgBinding(imgUrl, defRes, dimenSize);
 * app:imageCircle="@{imgInfo}"加载圆形图片ImgBinding(imgUrl, defRes, dimenSize);
 * app:imageRound="@{imgInfo}"加载圆角图片ImgBinding(imgUrl, defRes, dimenSize, corners));
 */
public class UtilsBinding {
    @BindingAdapter({"bind:imageNormal"})
    public static void loadImage(ImageView imageView, ImgBinding info) {
        if (null != info) UtilsPicasso.loadImage(imageView.getContext(),
                info.getImgUrl(), info.getDefRes(), imageView);
    }

    @BindingAdapter({"bind:imageCatch"})
    public static void loadCatchImage(ImageView imageView, ImgBinding info) {
        if (null != info) UtilsPicasso.loadCatchImage(imageView.getContext(),
                info.getImgUrl(), info.getDefRes(), imageView);
    }

    @BindingAdapter({"bind:imageSquare"})
    public static void loadCenterImage(ImageView imageView, ImgBinding info) {
        if (null != info) UtilsPicasso.loadCenterImage(imageView.getContext(),
                info.getImgUrl(), info.getDefRes(), imageView, info.getDimenSize());
    }

    @BindingAdapter({"bind:imageCircle"})
    public static void loadCircleImage(ImageView imageView, ImgBinding info) {
        if (null != info) UtilsPicasso.loadCircleImage(imageView.getContext(),
                info.getImgUrl(), info.getDefRes(), imageView, info.getDimenSize());
    }

    @BindingAdapter({"bind:imageRound"})
    public static void loadRoundImage(ImageView imageView, ImgBinding info) {
        if (null != info) UtilsPicasso.loadRoundImage(imageView.getContext(),
                info.getImgUrl(), info.getDefRes(), imageView, info.getDimenSize(), info.getCorners());
    }

    @BindingConversion
    public static String convertDate(Date date) {
        return UtilsDate.formatDate(date, UtilsDate.FORMAT_DATE_YEAR);
    }
}
