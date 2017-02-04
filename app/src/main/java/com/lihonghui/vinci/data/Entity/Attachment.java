package com.lihonghui.vinci.data.Entity;

/**
 * Created by yq05481 on 2016/12/12.
 */

public class Attachment {
    private int id;

    private String url;

    private String thumbnail_url;

    private int size;

    private String content_type;

    private int views_count;

    private String created_at;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setThumbnail_url(String thumbnail_url){
        this.thumbnail_url = thumbnail_url;
    }
    public String getThumbnail_url(){
        return this.thumbnail_url;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return this.size;
    }
    public void setContent_type(String content_type){
        this.content_type = content_type;
    }
    public String getContent_type(){
        return this.content_type;
    }
    public void setViews_count(int views_count){
        this.views_count = views_count;
    }
    public int getViews_count(){
        return this.views_count;
    }
    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
    public String getCreated_at(){
        return this.created_at;
    }

}
