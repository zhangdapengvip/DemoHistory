package com.healthsoulmate.zkl.ui.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZeroAries on 2016/5/20.
 * 商品信息
 */
public class ProductBean implements Parcelable {

    public static final String STATE_ALL = "all";
    public static final String STATE_BUY = "1";
    public static final String STATE_UNBUY = "0";

    private String sellnum;//销售数量
    private String pkGoods;//商品主键
    private String pkSeller;//商家主键
    private String sellername;//商家名称
    private String canceldate;//下架日期
    private String goodsname;//商品名称
    private String detailurl;//详情页面
    private String address;//供货地区
    private String description;//商品描述
    private String inventorynum;//库存数量
    private String imageurl;//列表缩略图
    private String selldate;//上架日期
    private String pkUser;//发布人
    private String commentsnum;//评价数
    private String goddsImages;//商品介绍图
    private String price;//价格
    private String reputably;//好评率
    private String transfee;//运费
    private String type;//商品类型
    private int buynum;// 购买数量
    private String buydate;//购买日期
    private String fee;// 条目总价
    private String isBuy;// 是否购买
    private String pkShoppingcart;// 购物车条目主键
    private String sellerName; // 卖家名

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuydate() {
        return buydate;
    }

    public void setBuydate(String buydate) {
        this.buydate = buydate;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

    public String getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(String canceldate) {
        this.canceldate = canceldate;
    }

    public String getCommentsnum() {
        return commentsnum;
    }

    public void setCommentsnum(String commentsnum) {
        this.commentsnum = commentsnum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getGoddsImages() {
        return goddsImages;
    }

    public void setGoddsImages(String goddsImages) {
        this.goddsImages = goddsImages;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getInventorynum() {
        return inventorynum;
    }

    public void setInventorynum(String inventorynum) {
        this.inventorynum = inventorynum;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public String getPkGoods() {
        return pkGoods;
    }

    public void setPkGoods(String pkGoods) {
        this.pkGoods = pkGoods;
    }

    public String getPkSeller() {
        return pkSeller;
    }

    public void setPkSeller(String pkSeller) {
        this.pkSeller = pkSeller;
    }

    public String getPkShoppingcart() {
        return pkShoppingcart;
    }

    public void setPkShoppingcart(String pkShoppingcart) {
        this.pkShoppingcart = pkShoppingcart;
    }

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReputably() {
        return reputably;
    }

    public void setReputably(String reputably) {
        this.reputably = reputably;
    }

    public String getSelldate() {
        return selldate;
    }

    public void setSelldate(String selldate) {
        this.selldate = selldate;
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellnum() {
        return sellnum;
    }

    public void setSellnum(String sellnum) {
        this.sellnum = sellnum;
    }

    public String getTransfee() {
        return transfee;
    }

    public void setTransfee(String transfee) {
        this.transfee = transfee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sellnum);
        dest.writeString(this.pkGoods);
        dest.writeString(this.pkSeller);
        dest.writeString(this.sellername);
        dest.writeString(this.canceldate);
        dest.writeString(this.goodsname);
        dest.writeString(this.detailurl);
        dest.writeString(this.address);
        dest.writeString(this.description);
        dest.writeString(this.inventorynum);
        dest.writeString(this.imageurl);
        dest.writeString(this.selldate);
        dest.writeString(this.pkUser);
        dest.writeString(this.commentsnum);
        dest.writeString(this.goddsImages);
        dest.writeString(this.price);
        dest.writeString(this.reputably);
        dest.writeString(this.transfee);
        dest.writeString(this.type);
        dest.writeInt(this.buynum);
        dest.writeString(this.buydate);
        dest.writeString(this.fee);
        dest.writeString(this.isBuy);
        dest.writeString(this.pkShoppingcart);
        dest.writeString(this.sellerName);
    }

    public ProductBean() {
    }

    protected ProductBean(Parcel in) {
        this.sellnum = in.readString();
        this.pkGoods = in.readString();
        this.pkSeller = in.readString();
        this.sellername = in.readString();
        this.canceldate = in.readString();
        this.goodsname = in.readString();
        this.detailurl = in.readString();
        this.address = in.readString();
        this.description = in.readString();
        this.inventorynum = in.readString();
        this.imageurl = in.readString();
        this.selldate = in.readString();
        this.pkUser = in.readString();
        this.commentsnum = in.readString();
        this.goddsImages = in.readString();
        this.price = in.readString();
        this.reputably = in.readString();
        this.transfee = in.readString();
        this.type = in.readString();
        this.buynum = in.readInt();
        this.buydate = in.readString();
        this.fee = in.readString();
        this.isBuy = in.readString();
        this.pkShoppingcart = in.readString();
        this.sellerName = in.readString();
    }

    public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
        @Override
        public ProductBean createFromParcel(Parcel source) {
            return new ProductBean(source);
        }

        @Override
        public ProductBean[] newArray(int size) {
            return new ProductBean[size];
        }
    };
}
