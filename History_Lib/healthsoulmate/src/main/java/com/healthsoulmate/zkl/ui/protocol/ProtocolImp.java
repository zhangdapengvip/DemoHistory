package com.healthsoulmate.zkl.ui.protocol;


import com.healthsoulmate.zkl.ui.protocol.request.AlreadyPayOrderRequest;
import com.healthsoulmate.zkl.ui.protocol.request.CommentCommentnumRequest;
import com.healthsoulmate.zkl.ui.protocol.request.CommentPageRequest;
import com.healthsoulmate.zkl.ui.protocol.request.CommentSaveRequest;
import com.healthsoulmate.zkl.ui.protocol.request.ForgetPasswordRequest;
import com.healthsoulmate.zkl.ui.protocol.request.GetMessageSendRequest;
import com.healthsoulmate.zkl.ui.protocol.request.GoodsDetailstRequest;
import com.healthsoulmate.zkl.ui.protocol.request.GoodsPageRequest;
import com.healthsoulmate.zkl.ui.protocol.request.InformationHeadRequest;
import com.healthsoulmate.zkl.ui.protocol.request.InformationPageRequest;
import com.healthsoulmate.zkl.ui.protocol.request.InformationUpdateRequest;
import com.healthsoulmate.zkl.ui.protocol.request.LoginRequest;
import com.healthsoulmate.zkl.ui.protocol.request.OredrhPageRequest;
import com.healthsoulmate.zkl.ui.protocol.request.OredrhSaveRequest;
import com.healthsoulmate.zkl.ui.protocol.request.OtherLoginRequest;
import com.healthsoulmate.zkl.ui.protocol.request.OtherVerificationRequest;
import com.healthsoulmate.zkl.ui.protocol.request.PageShoppingGoodsRequest;
import com.healthsoulmate.zkl.ui.protocol.request.QueryUserRequest;
import com.healthsoulmate.zkl.ui.protocol.request.RegisteRequest;
import com.healthsoulmate.zkl.ui.protocol.request.SettleAccountsRequest;
import com.healthsoulmate.zkl.ui.protocol.request.ShoppingcartRemoveRequest;
import com.healthsoulmate.zkl.ui.protocol.request.ShoppingcartSaveRequest;
import com.healthsoulmate.zkl.ui.protocol.request.ShoppingcartUpdateRequest;
import com.healthsoulmate.zkl.ui.protocol.request.UpdatePasswordRequest;
import com.healthsoulmate.zkl.ui.protocol.response.AlreadyPayOrderResponse;
import com.healthsoulmate.zkl.ui.protocol.response.CommentCommentnumResponse;
import com.healthsoulmate.zkl.ui.protocol.response.CommentPageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.GoodsDetailsResponse;
import com.healthsoulmate.zkl.ui.protocol.response.GoodsPageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.GoodsTypePageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.InformationHeadResponse;
import com.healthsoulmate.zkl.ui.protocol.response.InformationPageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.healthsoulmate.zkl.ui.protocol.response.OredrhPageResponse;
import com.healthsoulmate.zkl.ui.protocol.response.PageShoppingGoodsResponse;
import com.zero.library.base.retrofit.DefaultRequest;
import com.zero.library.base.retrofit.DefaultResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * 接口Interface
 * Created by ZeroAries on 2016/1/12.
 */
public interface ProtocolImp {

    //用户——退出登录
    @POST("j_spring_security_logout")
    Observable<DefaultResponse> logOutRequest();

    //用户——获取验证码
    @POST("user/getMessageSend")
    Observable<DefaultResponse> getMessageSendRequest(@Body GetMessageSendRequest request);

    //用户——注册
    @POST("user/register")
    Observable<DefaultResponse> registRequest(@Body RegisteRequest request);

    //用户——登录
    @POST("user/login")
    Observable<LoginResponse> loginRequest(@Body LoginRequest request);

    //用户——第三方登录
    @POST("user/otherLogin")
    Observable<LoginResponse> otherLoginRequest(@Body OtherLoginRequest request);

    //用户——第三方登录验证信息
    @POST("user/otherVerification")
    Observable<LoginResponse> otherVerificationRequest(@Body OtherVerificationRequest request);

    //用户——忘记密码
    @POST("user/forgetPassword")
    Observable<DefaultResponse> forgetPasswordRequest(@Body ForgetPasswordRequest request);

    //用户——修改密码
    @POST("user/updatePassword")
    Observable<DefaultResponse> updatePasswordRequest(@Body UpdatePasswordRequest request);

    //用户——获取个人信息
    @POST("user/queryUser")
    Observable<LoginResponse> queryUserRequest(@Body QueryUserRequest request);

    //用户——更新个人信息
    @Multipart
    @POST("user/updateUser")
    Observable<LoginResponse> userpdateUserRequest(@PartMap Map<String, RequestBody> params);

    //广告栏
    @POST("information/head")
    Observable<InformationHeadResponse> informationHeadRequest(@Body InformationHeadRequest request);

    //资讯列表
    @POST("information/page")
    Observable<InformationPageResponse> informationPageRequest(@Body InformationPageRequest request);

    //增加阅读数
    @POST("information/update")
    Observable<DefaultResponse> informationUpdateRequest(@Body InformationUpdateRequest request);

    //商品类型
    @POST("goodsType/page")
    Observable<GoodsTypePageResponse> goodsTypePageRequest(@Body DefaultRequest request);

    //商品列表
    @POST("goods/page")
    Observable<GoodsPageResponse> goodsPageRequest(@Body GoodsPageRequest request);

    //商品详情
    @POST("goods/details")
    Observable<GoodsDetailsResponse> goodsDetailsRequest(@Body GoodsDetailstRequest request);

    //评价列表
    @POST("comment/page")
    Observable<CommentPageResponse> commentPageRequest(@Body CommentPageRequest request);

    //评价计数
    @POST("comment/commentnum")
    Observable<CommentCommentnumResponse> commentCommentnumRequest(@Body CommentCommentnumRequest request);

    //保存评价
    @POST("comment/save")
    Observable<DefaultResponse> commentSaveRequest(@Body CommentSaveRequest request);

    //加入购物车
    @POST("shoppingcart/save")
    Observable<PageShoppingGoodsResponse> shoppingcartSaveRequest(@Body ShoppingcartSaveRequest request);

    //查询购物车
    @POST("shoppingcart/pageShoppingGoods")
    Observable<PageShoppingGoodsResponse> pageShoppingGoodsRequest(@Body PageShoppingGoodsRequest request);

    //修改数量
    @POST("shoppingcart/update")
    Observable<PageShoppingGoodsResponse> shoppingcartUpdateRequest(@Body ShoppingcartUpdateRequest request);

    //删除条目
    @POST("shoppingcart/remove")
    Observable<PageShoppingGoodsResponse> shoppingcartRemoveRequest(@Body ShoppingcartRemoveRequest request);

    //提交订单
    @POST("oredrh/save")
    Observable<DefaultResponse> oredrhSaveRequest(@Body OredrhSaveRequest request);

    //提交订单
    @POST("oredrh/settleAccounts")
    Observable<DefaultResponse> settleAccountsRequest(@Body SettleAccountsRequest request);

    //待支付订单
    @POST("oredrh/page")
    Observable<OredrhPageResponse> oredrhPageRequest(@Body OredrhPageRequest request);

    //已支付订单
    @POST("oredrh/alreadyPayOrder")
    Observable<AlreadyPayOrderResponse> alreadyPayOrderRequest(@Body AlreadyPayOrderRequest request);

}
