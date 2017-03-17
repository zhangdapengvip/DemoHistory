package com.yijia.zkl.ui.protocol;

import com.yijia.zkl.ui.protocol.request.GeneapplyPageRequest;
import com.yijia.zkl.ui.protocol.request.GeneapplySaveRequest;
import com.yijia.zkl.ui.protocol.request.GenetestPageRequest;
import com.yijia.zkl.ui.protocol.request.GetMessageSendRequest;
import com.yijia.zkl.ui.protocol.request.GetVersionRequest;
import com.yijia.zkl.ui.protocol.request.HelpDetailRequest;
import com.yijia.zkl.ui.protocol.request.HelpGetRequest;
import com.yijia.zkl.ui.protocol.request.HospitalPageRequest;
import com.yijia.zkl.ui.protocol.request.InfodiscussPageRequest;
import com.yijia.zkl.ui.protocol.request.InfodiscussSaveRequest;
import com.yijia.zkl.ui.protocol.request.InformationHeadRequest;
import com.yijia.zkl.ui.protocol.request.InformationPageRequest;
import com.yijia.zkl.ui.protocol.request.InformationUpdateRequest;
import com.yijia.zkl.ui.protocol.request.JoinRequest;
import com.yijia.zkl.ui.protocol.request.LoginRequest;
import com.yijia.zkl.ui.protocol.request.OnlineqaPageRequest;
import com.yijia.zkl.ui.protocol.request.OtherLoginRequest;
import com.yijia.zkl.ui.protocol.request.PageRequest;
import com.yijia.zkl.ui.protocol.request.QandaPageRequest;
import com.yijia.zkl.ui.protocol.request.QandaSaveRequest;
import com.yijia.zkl.ui.protocol.request.QusetionPageRequest;
import com.yijia.zkl.ui.protocol.request.QusetionSaveRequest;
import com.yijia.zkl.ui.protocol.request.QusetionUpdateRequest;
import com.yijia.zkl.ui.protocol.request.RegisteRequest;
import com.yijia.zkl.ui.protocol.request.SaveRequest;
import com.yijia.zkl.ui.protocol.request.UpdatePasswordRequest;
import com.yijia.zkl.ui.protocol.request.ViewPageRequest;
import com.yijia.zkl.ui.protocol.response.GeneapplyPageResponse;
import com.yijia.zkl.ui.protocol.response.GenetestPageResponse;
import com.yijia.zkl.ui.protocol.response.GetVersionResponse;
import com.yijia.zkl.ui.protocol.response.HelpDetailRsponse;
import com.yijia.zkl.ui.protocol.response.HelpGetResponse;
import com.yijia.zkl.ui.protocol.response.HospitalPageResponse;
import com.yijia.zkl.ui.protocol.response.InfoiscussPageResponse;
import com.yijia.zkl.ui.protocol.response.InformationHeadResponse;
import com.yijia.zkl.ui.protocol.response.InformationPageResponse;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.yijia.zkl.ui.protocol.response.OnlineqaPageResponse;
import com.yijia.zkl.ui.protocol.response.PageResponse;
import com.yijia.zkl.ui.protocol.response.QandaPageResponse;
import com.yijia.zkl.ui.protocol.response.QusetionPageResponse;
import com.yijia.zkl.ui.protocol.response.ViewPageResponse;
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

    //用户——版本更新
    @POST("user/getversion")
    Observable<GetVersionResponse> userGetversionRequest(@Body GetVersionRequest request);

    //用户——注册
    @POST("user/register")
    Observable<DefaultResponse> registRequest(@Body RegisteRequest request);

    //用户——登录
    @POST("user/login")
    Observable<LoginResponse> loginRequest(@Body LoginRequest request);

    //用户——获取验证码
    @POST("user/getMessageSend")
    Observable<DefaultResponse> getMessageSendRequest(@Body GetMessageSendRequest request);

    //用户——第三方登录
    @POST("user/otherLogin")
    Observable<LoginResponse> otherLoginRequest(@Body OtherLoginRequest request);

    //用户——修改密码
    @POST("user/updatePassword")
    Observable<DefaultResponse> updatePasswordRequest(@Body UpdatePasswordRequest request);

    //用户——个人信息修改
    @Multipart
    @POST("user/updateUser")
    Observable<LoginResponse> updateUserRequest(@PartMap Map<String, RequestBody> params);

    //用户——认证
    @Multipart
    @POST("user/authentication")
    Observable<LoginResponse> authenticationRequest(@PartMap Map<String, RequestBody> params);

    //广告栏
    @POST("information/head")
    Observable<InformationHeadResponse> informationHeadRequest(@Body InformationHeadRequest request);

    //资讯列表
    @POST("information/page")
    Observable<InformationPageResponse> informationPageRequest(@Body InformationPageRequest request);


    //资讯列表
    @POST("information/update")
    Observable<DefaultResponse> informationUpdateRequest(@Body InformationUpdateRequest request);

    //资讯详情评论
    @POST("infodiscuss/save")
    Observable<DefaultResponse> informationSaveRequest(@Body InfodiscussSaveRequest request);

    //资讯详情列表
    @POST("infodiscuss/page")
    Observable<InfoiscussPageResponse> infodiscussPageRequest(@Body InfodiscussPageRequest request);

    //视野
    @POST("view/page")
    Observable<ViewPageResponse> viewPageRequest(@Body ViewPageRequest request);

    //视野——医友提问
    @POST("qusetion/save")
    Observable<DefaultResponse> qusetionSaveRequest(@Body QusetionSaveRequest request);

    //视野——同问题计数
    @POST("qusetion/update")
    Observable<DefaultResponse> qusetionUpdateRequest(@Body QusetionUpdateRequest request);

    //视野——医友提问列表
    @POST("qusetion/page")
    Observable<QusetionPageResponse> qusetionPageRequest(@Body QusetionPageRequest request);

    //名医堂——专家在线
    @POST("onlineqa/page")
    Observable<OnlineqaPageResponse> onlineqaPageRequest(@Body OnlineqaPageRequest request);

    //名医堂——提问
    @POST("qanda/save")
    Observable<DefaultResponse> qandaSaveRequest(@Body QandaSaveRequest request);

    //名医堂——问题列表
    @POST("qanda/page")
    Observable<QandaPageResponse> qandaPageRequest(@Body QandaPageRequest request);

    //特需——基因检测列表
    @POST("genetest/page")
    Observable<GenetestPageResponse> genetestPageRequest(@Body GenetestPageRequest request);

    //特需——基因检测推荐
    @POST("geneapply/save")
    Observable<DefaultResponse> geneapplySaveRequest(@Body GeneapplySaveRequest request);

    //特需——基因检测推荐历史
    @POST("geneapply/page")
    Observable<GeneapplyPageResponse> geneapplyPageRequest(@Body GeneapplyPageRequest request);

    //特需——医疗援助列表
    @POST("hospital/page")
    Observable<HospitalPageResponse> hospitalPageRequest(@Body HospitalPageRequest request);

    //特需——发布招募
    @POST("researchproject/save")
    Observable<DefaultResponse> saveRequest(@Body SaveRequest request);

    //特需——发起求助
    @Multipart
    @POST("help/save")
    Observable<DefaultResponse> helpSaveRequest(@PartMap Map<String, RequestBody> params);

    //特需——我的求助
    @POST("help/get")
    Observable<HelpGetResponse> helpGetRequest(@Body HelpGetRequest request);

    //特需——求助详情
    @POST("help/getHelpDetail")
    Observable<HelpDetailRsponse> getHelpDetailRequest(@Body HelpDetailRequest request);

    //特需——科研参与列表
    @POST("researchproject/page")
    Observable<PageResponse> pageRequest(@Body PageRequest request);

    //特需——参加科研招募
    @POST("researchproject/join")
    Observable<DefaultResponse> joinRequest(@Body JoinRequest request);
}