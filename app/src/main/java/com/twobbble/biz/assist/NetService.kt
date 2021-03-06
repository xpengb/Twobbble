package com.twobbble.biz.assist

import com.twobbble.entity.*
import com.twobbble.tools.Constant
import org.jetbrains.annotations.NotNull
import retrofit2.http.*
import rx.Observable
import rx.Observer

/**
 * Created by liuzipeng on 2017/2/21.
 */
interface NetService {
    /**
     * 获取一个shot列表
     *
     * @param access_token token  默认值为公用token
     * @param list  列表类型，比如带GIF的，团队的  animated ，attachments ，debuts ，playoffs，rebounds，teams
     * @param sort 排序 comments评论最多的，recent 最近的，views查看最多的
     * @param timeframe 时间   week一周，month一个月，year一键，ever无论何时
     * @param page 获取哪一页
     *
     * @return MutableList<Shot>
     */
    @GET("shots") fun getShots(@NotNull @Query("access_token") access_token: String,
                               @Query("list") list: String?,
                               @Query("timeframe") timeframe: String?,
                               @Query("sort") sort: String?,
                               @Query("page") page: Int?): Observable<MutableList<Shot>>

    /**
     * 获取一个shot下的评论列表
     *
     * @param id 这条shot的ID
     * @param access_token token  默认值为公用token
     * @param page 获取哪一页  默认为空，评论列表应为dribbble评论偏少的缘故，不做上拉加载
     *
     * return MutableList<Comment>
     */
    @GET("shots/{id}/comments") fun getComments(@NotNull @Path("id") id: Long,
                                                @NotNull @Query("access_token") access_token: String,
                                                @Query("page") page: Int?,
                                                @Query("per_page") per_page: Int? = 100): Observable<MutableList<Comment>>

    /**
     * 用户登录获取token
     * @param oauthCode 跳转到浏览器用户登录后返回的code
     */
    @FormUrlEncoded
    @POST("/oauth/token") fun getToken(@Field("client_id") clientId: String = Constant.CLIENT_ID,
                                       @Field("client_secret") clientSecret: String = Constant.CLIENT_SECRET,
                                       @Field("code") oauthCode: String): Observable<Token>


    /**
     * 获取登录的用户的个人信息
     * @param access_token 登录后获得的用户的token
     */
    @GET("user") fun getMyInfo(@NotNull @Query("access_token") access_token: String): Observable<User>


    /**
     * 喜欢一个shot
     * @param access_token
     * @param id  shot的id
     */
    @FormUrlEncoded
    @POST("shots/{id}/like") fun likeShot(@NotNull @Path("id") id: Long,
                                          @NotNull @Field("access_token") access_token: String): Observable<LikeShotResponse>

    /**
     * 获取这个shot是否已被喜欢
     * @param access_token
     * @param id  shot的id
     */
    @GET("shots/{id}/like") fun checkIfLikeShot(@NotNull @Path("id") id: Long,
                                                @NotNull @Query("access_token") access_token: String): Observable<LikeShotResponse>

    /**
     * 删除一个like
     */
    @DELETE("shots/{id}/like") fun unlikeShot(@NotNull @Path("id") id: Long,
                                              @NotNull @Query("access_token") access_token: String): Observable<LikeShotResponse>

    /**
     * 创建一条评论
     *
     * @param access_token
     * @param body 评论内容
     * @param id shot的ID
     */
    @FormUrlEncoded
    @POST("shots/{id}/comments") fun createComment(@NotNull @Path("id") id: Long,
                                                   @NotNull @Field("access_token") access_token: String,
                                                   @NotNull @Field("body") body: String): Observable<Comment>

    /**
     * 获取当前用户点击了like的shot列表
     */
    @GET("user/likes") fun getMyLikes(@NotNull @Query("access_token") access_token: String,
                                      @Query("page") page: Int?): Observable<MutableList<Like>>

    /**
     * 获取当前用户扥bucket列表
     */
    @GET("user/buckets") fun getMyBuckets(@NotNull @Query("access_token") access_token: String,
                                          @Query("page") page: Int? = 100): Observable<MutableList<Bucket>>

    /**
     * 创建一个bucket
     *
     * @param access_token
     * @param description bucket描述
     * @param name bucket名
     */
    @FormUrlEncoded
    @POST("buckets") fun createBucket(@NotNull @Field("access_token") access_token: String,
                                      @NotNull @Field("name") name: String,
                                      @NotNull @Field("description") description: String?): Observable<Bucket>

    /**
     * 删除一个bucket
     */
    @DELETE("buckets/{id}") fun deleteBucket(@NotNull @Path("id") id: Long,
                                             @NotNull @Query("access_token") access_token: String): Observable<Bucket>

    /**
     * 修改一个bucket
     */
    @FormUrlEncoded
    @PUT("buckets/{id}") fun modifyBucket(@NotNull @Path("id") id: Long,
                                          @NotNull @Field("access_token") access_token: String,
                                          @NotNull @Field("name") name: String,
                                          @NotNull @Field("description") description: String?): Observable<Bucket>


    /**
     * 获取一个bucket中的shot列表
     *
     * @param access_token token  默认值为公用token
     * @param id 这个bucket的id
     * @param page 获取哪一页
     *
     * @return MutableList<Shot>
     */
    @GET("buckets/{id}/shots") fun getBucketShots(@NotNull @Path("id") id: Long,
                                                  @NotNull @Query("access_token") access_token: String,
                                                  @Query("page") page: Int?): Observable<MutableList<Shot>>

    /**
     * 删除bucket中的一个shot
     *
     * @param access_token token  默认值为公用token
     * @param id 这个bucket的id
     * @param shot_id 要删除的shot的id
     *
     * @return
     */
    @DELETE("buckets/{id}/shots") fun removeShotFromBucket(@NotNull @Path("id") id: Long,
                                                           @NotNull @Query("access_token") access_token: String,
                                                           @Query("shot_id") shot_id: Long?): Observable<Shot>

    /**
     * 添加一个shot到一个bucket
     *
     * @param access_token token  默认值为公用token
     * @param id 这个bucket的id
     * @param shot_id 要添加的shot的id
     *
     * @return
     */
    @FormUrlEncoded
    @PUT("buckets/{id}/shots") fun addShot2Bucket(@NotNull @Path("id") id: Long,
                                                  @NotNull @Field("access_token") access_token: String,
                                                  @Field("shot_id") shot_id: Long?): Observable<Shot>

    /**
     * 获取一个用户的shot
     * @param user 用户类型   user是自己   users是其它用户
     * @param id 用户id   如果是自己的  给null
     * @param access_token
     * @param page 页码
     */
    @GET("{user}/{id}/shots") fun getUserShot(@NotNull @Path("user") user: String,
                                              @Path("id") id: String?,
                                              @NotNull @Query("access_token") access_token: String,
                                              @Query("page") page: Int?): Observable<MutableList<Shot>>


    /**
     * 检查是否已关注这个用户
     * @param access_token
     * @param id  要检查的用户的id
     */
    @GET("user/following/{id}") fun checkIfFollowingUser(@NotNull @Path("id") id: Long,
                                                         @NotNull @Query("access_token") access_token: String): Observable<NullResponse>

    /**
     * 关注一个用户
     * @param access_token
     * @param id  要关注的用户的id
     */
    @FormUrlEncoded
    @PUT("users/{id}/follow") fun followUser(@NotNull @Path("id") id: Long,
                                             @NotNull @Field("access_token") access_token: String): Observable<NullResponse>

    /**
     * 取消关注一个用户
     * @param access_token
     * @param id  要取关的用户的id
     */
    @DELETE("users/{id}/follow") fun unFollowUser(@NotNull @Path("id") id: Long,
                                                      @NotNull @Query("access_token") access_token: String): Observable<NullResponse>
}