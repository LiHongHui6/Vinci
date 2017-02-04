package com.lihonghui.vinci.data.Entity;

/**
 * Created by yq05481 on 2016/12/20.
 */

public class BucketModel {
    Shot firstShot;
    Bucket bucket;

    public Shot getFirstShot() {
        return firstShot;
    }

    public void setFirstShot(Shot firstShot) {
        this.firstShot = firstShot;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public BucketModel(Shot firstShot, Bucket bucket) {
        this.firstShot = firstShot;
        this.bucket = bucket;
    }
}
