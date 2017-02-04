package com.lihonghui.vinci.data.Entity;

/**
 * Created by yq05481 on 2016/12/9.
 */

public class Like {
    private int id;

    private String created_at;

    private Shot shot;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
    public String getCreated_at(){
        return this.created_at;
    }
    public void setShot(Shot shot){
        this.shot = shot;
    }
    public Shot getShot(){
        return this.shot;
    }
}
