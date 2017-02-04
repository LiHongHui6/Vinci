package com.lihonghui.vinci.data.Entity;

/**
 * Created by yq05481 on 2016/12/12.
 */

public class Comment {
    private int id;

    private String body;

    private int likes_count;

    private String likes_url;

    private String created_at;

    private String updated_at;

    private User user;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setBody(String body){
        this.body = body;
    }
    public String getBody(){
        return this.body;
    }
    public void setLikes_count(int likes_count){
        this.likes_count = likes_count;
    }
    public int getLikes_count(){
        return this.likes_count;
    }
    public void setLikes_url(String likes_url){
        this.likes_url = likes_url;
    }
    public String getLikes_url(){
        return this.likes_url;
    }
    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
    public String getCreated_at(){
        return this.created_at;
    }
    public void setUpdated_at(String updated_at){
        this.updated_at = updated_at;
    }
    public String getUpdated_at(){
        return this.updated_at;
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
}
