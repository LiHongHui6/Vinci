package com.lihonghui.vinci.detail;

import com.lihonghui.vinci.data.Entity.Attachment;
import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.Comment;
import com.lihonghui.vinci.data.Entity.Shot;

import java.util.List;

/**
 * Created by yq05481 on 2016/12/12.
 */

public interface DetailContact {
    interface View{
        void onGetAttachment(List<Attachment> attachments);
        void onGetComments(List<Comment> commentList);
        void onGettingData();
        void onDataRefresh(Shot shot);

        void onError(String message);

        void getDataFinish();

        void likeChecked(boolean isLike);

        void unLikeShot();

        void likeShot();

        void onLikeShot();

        void onUnLikeShot();

        void onGetMoreComments(List<Comment> comments);

        void onLoadAllComment();
    }

    interface Presenter{
        void getData(Shot shot);

        void likeSelect(Shot mShot);

        void bucketSelect(Shot shot, Bucket bucket);

        void getMoreComment(Shot mShot);
    }
}
