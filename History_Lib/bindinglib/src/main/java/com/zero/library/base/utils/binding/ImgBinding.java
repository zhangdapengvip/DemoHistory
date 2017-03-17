package com.zero.library.base.utils.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zero.library.BR;

/**
 * Created by ZeroAries on 2016/4/21.
 * 加载图片绑定
 */
public class ImgBinding extends BaseObservable {
    private String imgUrl;
    private int dimenSize;
    private int defRes;
    private int corners = 10;

    public ImgBinding(String imgUrl, int defRes) {
        this.imgUrl = imgUrl;
        this.defRes = defRes;
    }

    public ImgBinding(String imgUrl, int defRes, int dimenSize) {
        this.imgUrl = imgUrl;
        this.defRes = defRes;
        this.dimenSize = dimenSize;
    }

    public ImgBinding(String imgUrl, int defRes, int dimenSize, int corners) {
        this.imgUrl = imgUrl;
        this.defRes = defRes;
        this.dimenSize = dimenSize;
        this.corners = corners;
    }

    @Bindable
    public int getDefRes() {
        return defRes;
    }

    public void setDefRes(int defRes) {
        this.defRes = defRes;
        notifyPropertyChanged(BR.defRes);
    }

    @Bindable
    public int getDimenSize() {
        return dimenSize;
    }

    public void setDimenSize(int dimenSize) {
        this.dimenSize = dimenSize;
        notifyPropertyChanged(BR.dimenSize);
    }

    @Bindable
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        notifyPropertyChanged(BR.imgUrl);
    }

    @Bindable
    public int getCorners() {
        return corners;
    }

    public void setCorners(int corners) {
        this.corners = corners;
        notifyPropertyChanged(BR.corners);
    }
}
