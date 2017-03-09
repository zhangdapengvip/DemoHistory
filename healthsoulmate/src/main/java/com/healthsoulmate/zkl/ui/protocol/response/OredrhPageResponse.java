package com.healthsoulmate.zkl.ui.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.zero.library.base.retrofit.DefaultResponse;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/22.
 * 响应待支付订单
 */
public class OredrhPageResponse extends DefaultResponse {

    private List<ListBean> list;


    public static class ListBean implements Parcelable {
        private int orderstate;// 支付状态
        private String paytime;// 支付时间
        private String timeLeft;//剩余时间
        private String payno;
        private int fee;// 总价
        private String ordertime;//下单时间
        private String orderno;// 订单号
        private String pkOrderh;
        private int buysource;//购买方式
        private int actualfee;// 实付款
        private String pkBuyer;
        private List<OrderbsBean> orderbs;

        public static class OrderbsBean implements Parcelable {
            private int actualfee;
            private String sellername;
            private String pkOrderh;
            private int fee;
            private String pkSeller;
            private String orderno;
            private String pkOrderb;
            private List<GoodsBean> goods;

            public static class GoodsBean implements Parcelable {
                private int buynum;
                private int actualfee;
                private String imageurl;
                private String pkOrdergoods;
                private int price;
                private String goodsname;
                private int fee;
                private String pkOrderb;
                private String pkGoods;

                public int getBuynum() {
                    return buynum;
                }

                public void setBuynum(int buynum) {
                    this.buynum = buynum;
                }

                public int getActualfee() {
                    return actualfee;
                }

                public void setActualfee(int actualfee) {
                    this.actualfee = actualfee;
                }

                public String getImageurl() {
                    return imageurl;
                }

                public void setImageurl(String imageurl) {
                    this.imageurl = imageurl;
                }

                public String getPkOrdergoods() {
                    return pkOrdergoods;
                }

                public void setPkOrdergoods(String pkOrdergoods) {
                    this.pkOrdergoods = pkOrdergoods;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public String getGoodsname() {
                    return goodsname;
                }

                public void setGoodsname(String goodsname) {
                    this.goodsname = goodsname;
                }

                public int getFee() {
                    return fee;
                }

                public void setFee(int fee) {
                    this.fee = fee;
                }

                public String getPkOrderb() {
                    return pkOrderb;
                }

                public void setPkOrderb(String pkOrderb) {
                    this.pkOrderb = pkOrderb;
                }

                public String getPkGoods() {
                    return pkGoods;
                }

                public void setPkGoods(String pkGoods) {
                    this.pkGoods = pkGoods;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.buynum);
                    dest.writeInt(this.actualfee);
                    dest.writeString(this.imageurl);
                    dest.writeString(this.pkOrdergoods);
                    dest.writeInt(this.price);
                    dest.writeString(this.goodsname);
                    dest.writeInt(this.fee);
                    dest.writeString(this.pkOrderb);
                    dest.writeString(this.pkGoods);
                }

                public GoodsBean() {
                }

                protected GoodsBean(Parcel in) {
                    this.buynum = in.readInt();
                    this.actualfee = in.readInt();
                    this.imageurl = in.readString();
                    this.pkOrdergoods = in.readString();
                    this.price = in.readInt();
                    this.goodsname = in.readString();
                    this.fee = in.readInt();
                    this.pkOrderb = in.readString();
                    this.pkGoods = in.readString();
                }

                public static final Parcelable.Creator<GoodsBean> CREATOR = new Parcelable.Creator<GoodsBean>() {
                    @Override
                    public GoodsBean createFromParcel(Parcel source) {
                        return new GoodsBean(source);
                    }

                    @Override
                    public GoodsBean[] newArray(int size) {
                        return new GoodsBean[size];
                    }
                };
            }

            public int getActualfee() {
                return actualfee;
            }

            public void setActualfee(int actualfee) {
                this.actualfee = actualfee;
            }

            public String getSellername() {
                return sellername;
            }

            public void setSellername(String sellername) {
                this.sellername = sellername;
            }

            public String getPkOrderh() {
                return pkOrderh;
            }

            public void setPkOrderh(String pkOrderh) {
                this.pkOrderh = pkOrderh;
            }

            public int getFee() {
                return fee;
            }

            public void setFee(int fee) {
                this.fee = fee;
            }

            public String getPkSeller() {
                return pkSeller;
            }

            public void setPkSeller(String pkSeller) {
                this.pkSeller = pkSeller;
            }

            public String getOrderno() {
                return orderno;
            }

            public void setOrderno(String orderno) {
                this.orderno = orderno;
            }

            public String getPkOrderb() {
                return pkOrderb;
            }

            public void setPkOrderb(String pkOrderb) {
                this.pkOrderb = pkOrderb;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.actualfee);
                dest.writeString(this.sellername);
                dest.writeString(this.pkOrderh);
                dest.writeInt(this.fee);
                dest.writeString(this.pkSeller);
                dest.writeString(this.orderno);
                dest.writeString(this.pkOrderb);
                dest.writeTypedList(this.goods);
            }

            public OrderbsBean() {
            }

            protected OrderbsBean(Parcel in) {
                this.actualfee = in.readInt();
                this.sellername = in.readString();
                this.pkOrderh = in.readString();
                this.fee = in.readInt();
                this.pkSeller = in.readString();
                this.orderno = in.readString();
                this.pkOrderb = in.readString();
                this.goods = in.createTypedArrayList(GoodsBean.CREATOR);
            }

            public static final Parcelable.Creator<OrderbsBean> CREATOR = new Parcelable.Creator<OrderbsBean>() {
                @Override
                public OrderbsBean createFromParcel(Parcel source) {
                    return new OrderbsBean(source);
                }

                @Override
                public OrderbsBean[] newArray(int size) {
                    return new OrderbsBean[size];
                }
            };
        }

        public int getOrderstate() {
            return orderstate;
        }

        public void setOrderstate(int orderstate) {
            this.orderstate = orderstate;
        }

        public String getPaytime() {
            return paytime;
        }

        public void setPaytime(String paytime) {
            this.paytime = paytime;
        }

        public String getTimeLeft() {
            return timeLeft;
        }

        public void setTimeLeft(String timeLeft) {
            this.timeLeft = timeLeft;
        }

        public String getPayno() {
            return payno;
        }

        public void setPayno(String payno) {
            this.payno = payno;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public String getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getPkOrderh() {
            return pkOrderh;
        }

        public void setPkOrderh(String pkOrderh) {
            this.pkOrderh = pkOrderh;
        }

        public int getBuysource() {
            return buysource;
        }

        public void setBuysource(int buysource) {
            this.buysource = buysource;
        }

        public int getActualfee() {
            return actualfee;
        }

        public void setActualfee(int actualfee) {
            this.actualfee = actualfee;
        }

        public String getPkBuyer() {
            return pkBuyer;
        }

        public void setPkBuyer(String pkBuyer) {
            this.pkBuyer = pkBuyer;
        }

        public List<OrderbsBean> getOrderbs() {
            return orderbs;
        }

        public void setOrderbs(List<OrderbsBean> orderbs) {
            this.orderbs = orderbs;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.orderstate);
            dest.writeString(this.paytime);
            dest.writeString(this.timeLeft);
            dest.writeString(this.payno);
            dest.writeInt(this.fee);
            dest.writeString(this.ordertime);
            dest.writeString(this.orderno);
            dest.writeString(this.pkOrderh);
            dest.writeInt(this.buysource);
            dest.writeInt(this.actualfee);
            dest.writeString(this.pkBuyer);
            dest.writeTypedList(this.orderbs);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.orderstate = in.readInt();
            this.paytime = in.readString();
            this.timeLeft = in.readString();
            this.payno = in.readString();
            this.fee = in.readInt();
            this.ordertime = in.readString();
            this.orderno = in.readString();
            this.pkOrderh = in.readString();
            this.buysource = in.readInt();
            this.actualfee = in.readInt();
            this.pkBuyer = in.readString();
            this.orderbs = in.createTypedArrayList(OrderbsBean.CREATOR);
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }
}
