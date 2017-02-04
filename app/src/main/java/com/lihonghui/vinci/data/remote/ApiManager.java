package com.lihonghui.vinci.data.remote;

import com.lihonghui.vinci.data.Entity.Attachment;
import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.Comment;
import com.lihonghui.vinci.data.Entity.Like;
import com.lihonghui.vinci.data.Entity.Shot;
import com.lihonghui.vinci.data.Entity.User;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yq05481 on 2016/12/8.
 */

public interface ApiManager {
    interface TokenApi {
        @FormUrlEncoded
        @POST("oauth/token")
        Observable<TokenResponseJson> getAToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret, @Field("code") String code);
    }

    interface AppApi {
        @GET("user")
        Observable<User> getOAuthUserInfo(@Query("access_token") String token);

        @GET("user/likes")
        Observable<List<Like>> getOAuthUserLikes(@Query("access_token") String token, @Query("page") int page);

        @GET("shots/{id}/attachments")
        Observable<List<Attachment>> getAttachmentList(@Path("id") int id, @Query("access_token") String token);

        @GET("shots/{id}/comments")
        Observable<List<Comment>> getCommentList(@Path("id") int id, @Query("access_token") String token,@Query("page") int page);

        @GET("shots/{id}")
        Observable<Shot> getShot(@Path("id") int id, @Query("access_token") String token);

        @GET("shots/{id}/like")
        Observable<Void> checkIsLike(@Path("id") int id, @Query("access_token") String token);

        @POST("shots/{id}/like")
        Observable<Void> likeShot(@Path("id") int id, @Query("access_token") String token);

        @DELETE("shots/{id}/like")
        Observable<Void> unlikeShot(@Path("id") int id, @Query("access_token") String token);

        @GET("user/buckets")
        Observable<List<Bucket>> getOAuthUserBucket(@Query("access_token") String token, @Query("page") int page);

        @GET("buckets/{id}/shots")
        Observable<List<Shot>> getBucketShots(@Path("id") int id,@Query("access_token") String token,@Query("page") int page);

        @FormUrlEncoded
        @POST("buckets")
        Observable<Bucket> createBucket(@Query("access_token") String token,@Field("name") String bucketTitle,@Field("description") String bucketDescription);

        @GET("shots")
        Observable<List<Shot>> getShots(@Query("access_token")String token,@Query("page") int page);
    }


    class TokenResponseJson {
        String access_token;
        String token_type;
        String scope;

        @Override
        public String toString() {
            return "ReponseJson{" +
                    "access_token='" + access_token + '\'' +
                    ", token_type='" + token_type + '\'' +
                    ", scope='" + scope + '\'' +
                    '}';
        }
    }
}
