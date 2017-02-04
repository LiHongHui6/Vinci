package com.lihonghui.vinci.detail;

import com.lihonghui.vinci.data.DataManager;
import com.lihonghui.vinci.data.Entity.Attachment;
import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.Comment;
import com.lihonghui.vinci.data.Entity.Shot;
import com.socks.library.KLog;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class DetailPresenter implements DetailContact.Presenter {
    private DataManager mDataManager;
    private DetailContact.View mView;
    private final int LOAD_ALL = -1;
    private int commmentPage = 1;
    private boolean isLike;

    public DetailPresenter(DetailContact.View view) {
        this.mDataManager = DataManager.getInstance();
        this.mView = view;
    }

    @Override
    public void getData(Shot shot) {
        mView.onGettingData();
        mDataManager.getNetWorkManager().getShot(shot.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Shot>() {
                    @Override
                    public void call(Shot shot) {
                        mView.onDataRefresh(shot);
                        commmentPage = 1;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<Shot, Observable<AttachmentAndComment>>() {
                    @Override
                    public Observable<AttachmentAndComment> call(final Shot shot) {
                        return Observable.zip(mDataManager.getNetWorkManager().getAttachmentList(shot.getId()),
                                mDataManager.getNetWorkManager().getCommentList(shot.getId(), commmentPage),
                                new Func2<List<Attachment>, List<Comment>, AttachmentAndComment>() {
                                    @Override
                                    public AttachmentAndComment call(List<Attachment> attachments, List<Comment> comments) {
                                        return new AttachmentAndComment(attachments, comments, shot.getId());
                                    }
                                });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<AttachmentAndComment>() {
                    @Override
                    public void call(AttachmentAndComment attachmentAndComment) {
                        mView.onGetAttachment(attachmentAndComment.getAttachmentList());
                        mView.onGetComments(attachmentAndComment.getCommentList());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<AttachmentAndComment, Observable<Void>>() {
                    @Override
                    public Observable<Void> call(AttachmentAndComment attachmentAndComment) {
                        return mDataManager.getNetWorkManager().checkIsLike(attachmentAndComment.getShotID());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        mView.getDataFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().contains("404 Not Found")) {
                            isLike = false;
                            mView.likeChecked(isLike);
                            mView.getDataFinish();
                        } else {
                            mView.onError(e.getMessage());
                        }

                    }

                    @Override
                    public void onNext(Void o) {
                        isLike = true;
                        mView.likeChecked(isLike);

                    }
                });
    }

    @Override
    public void likeSelect(Shot mShot) {
        if (isLike) {
            unLikeShot(mShot);
        } else {
            likeShot(mShot);
        }
    }

    @Override
    public void bucketSelect(Shot shot, Bucket bucket) {

    }

    @Override
    public void getMoreComment(Shot mShot) {
        if(commmentPage == LOAD_ALL){
            mView.onLoadAllComment();
            return;
        }
        KLog.e("getMoreComment request page: "+(commmentPage + 1));
        mDataManager.getNetWorkManager().getCommentList(mShot.getId(), commmentPage + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {
                        if(commmentPage != LOAD_ALL){
                            commmentPage ++;
                        }
                        mView.getDataFinish();
                        KLog.e("getMoreComment onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e.getMessage());
                        KLog.e("getMoreComment onError");
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        KLog.e("getMoreComment onNext");
                        if(comments.size() != 0){
                            mView.onGetMoreComments(comments);
                        }else{
                            commmentPage = LOAD_ALL;
                            mView.onLoadAllComment();
                        }
                    }

                    @Override
                    public void onStart() {
                        mView.onGettingData();
                    }
                });
    }

    private void likeShot(Shot shot) {
        mDataManager.getNetWorkManager().likeShot(shot.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                KLog.e("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("onError " + e.getMessage());
            }

            @Override
            public void onNext(Void aVoid) {
                KLog.e("onNext ");
                isLike = true;
                mView.likeShot();
            }

            @Override
            public void onStart() {
                mView.onLikeShot();
            }
        });
    }

    private void unLikeShot(final Shot mShot) {
        mDataManager.getNetWorkManager().unLikeShot(mShot.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                KLog.e("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("onError " + e.getMessage());
            }

            @Override
            public void onNext(Void aVoid) {
                isLike = false;
                mShot.setLikes_count(mShot.getLikes_count() - 1);
                mView.unLikeShot();
            }

            @Override
            public void onStart() {
                mView.onUnLikeShot();
            }
        });
    }

    class AttachmentAndComment {
        int shotID;
        List<Comment> commentList;
        List<Attachment> attachmentList;

        public AttachmentAndComment(List<Attachment> attachmentList, List<Comment> commentList, int shotID) {
            this.attachmentList = attachmentList;
            this.commentList = commentList;
            this.shotID = shotID;
        }

        public int getShotID() {
            return shotID;
        }

        public void setShotID(int shotID) {
            this.shotID = shotID;
        }

        public List<Attachment> getAttachmentList() {
            return attachmentList;
        }

        public void setAttachmentList(List<Attachment> attachmentList) {
            this.attachmentList = attachmentList;
        }

        public List<Comment> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<Comment> commentList) {
            this.commentList = commentList;
        }
    }
}
