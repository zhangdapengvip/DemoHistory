package com.healthsoulmate.zkl.forum.protocol;

import com.healthsoulmate.zkl.forum.protocol.request.DiscussreplySaveRequest;
import com.healthsoulmate.zkl.forum.protocol.request.DiscuzsectionPageRequest;
import com.healthsoulmate.zkl.forum.protocol.request.FavoriteRequest;
import com.healthsoulmate.zkl.forum.protocol.request.MyPostHistoryRequest;
import com.healthsoulmate.zkl.forum.protocol.request.MyReplyHistoryRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PageDiscussReplyRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostFavoritesFavoriteRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostFavoritesMyFavoritesRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostbrowseAddReadsRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostreportSaveRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostsDetailPostsRequest;
import com.healthsoulmate.zkl.forum.protocol.request.PostsPageRequest;
import com.healthsoulmate.zkl.forum.protocol.request.UserfocusPageRequest;
import com.healthsoulmate.zkl.forum.protocol.request.UserfocusSaveRequest;
import com.healthsoulmate.zkl.forum.protocol.response.DiscuzsectionPageResponse;
import com.healthsoulmate.zkl.forum.protocol.response.FavoriteResponse;
import com.healthsoulmate.zkl.forum.protocol.response.MyReplyHistoryResponse;
import com.healthsoulmate.zkl.forum.protocol.response.PageDiscussReplyResponse;
import com.healthsoulmate.zkl.forum.protocol.response.PostsDetailPostsResponse;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.healthsoulmate.zkl.forum.protocol.response.UserfocusPageResponse;
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
public interface ForumImp {

    //论坛列表
    @POST("discuzsection/page")
    Observable<DiscuzsectionPageResponse> discuzsectionPageRequest(@Body DiscuzsectionPageRequest request);

    //收藏板块
    @POST("sectionFavorites/favorite")
    Observable<FavoriteResponse> favoriteRequest(@Body FavoriteRequest request);

    //帖子列表
    @POST("posts/page")
    Observable<PostsPageResponse> postsPageRequest(@Body PostsPageRequest request);

    //添加浏览历史
    @POST("postbrowse/addReads")
    Observable<DefaultResponse> postbrowseAddReadsRequest(@Body PostbrowseAddReadsRequest request);

    //发布帖子
    @Multipart
    @POST("posts/savePosts")
    Observable<DefaultResponse> postsSavePostsRequest(@PartMap Map<String, RequestBody> params);

    //帖子详情
    @POST("posts/detailPosts")
    Observable<PostsDetailPostsResponse> postsDetailPostsRequest(@Body PostsDetailPostsRequest request);

    //帖子评论
    @Multipart
    @POST("discuss/save")
    Observable<DefaultResponse> discussSaveRequest(@PartMap Map<String, RequestBody> params);

    //帖子回复
    @POST("discussreply/save")
    Observable<DefaultResponse> discussreplySaveRequest(@Body DiscussreplySaveRequest request);

    //帖子收藏
    @POST("postFavorites/favorite")
    Observable<DefaultResponse> postFavoritesFavoriteRequest(@Body PostFavoritesFavoriteRequest request);

    //帖子收藏列表
    @POST("postFavorites/myFavorites")
    Observable<PostsPageResponse> postFavoritesMyFavoritesRequest(@Body PostFavoritesMyFavoritesRequest request);

    //帖子举报
    @POST("postreport/save")
    Observable<DefaultResponse> postreportSaveRequest(@Body PostreportSaveRequest request);

    //跟帖评论
    @POST("discussreply/pageDiscussReply")
    Observable<PageDiscussReplyResponse> pageDiscussReplyRequest(@Body PageDiscussReplyRequest request);

    //我发布的帖子
    @POST("posts/myPostHistory")
    Observable<PostsPageResponse> myPostHistoryRequest(@Body MyPostHistoryRequest request);

    //我的回复
    @POST("discussreply/myReplyHistory")
    Observable<MyReplyHistoryResponse> myReplyHistoryRequest(@Body MyReplyHistoryRequest request);

    //关注
    @POST("userfocus/save")
    Observable<DefaultResponse> userfocusSaveRequest(@Body UserfocusSaveRequest request);

    //关注列表
    @POST("userfocus/page")
    Observable<UserfocusPageResponse> userfocusPageRequest(@Body UserfocusPageRequest request);

}