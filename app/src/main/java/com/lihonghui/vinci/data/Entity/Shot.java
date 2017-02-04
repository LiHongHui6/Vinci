package com.lihonghui.vinci.data.Entity;

import java.util.List;

/**
 * Created by yq05481 on 2016/12/9.
 */

public class Shot {
    private int id;

    private String title;

    private String description;

    private int width;

    private int height;

    private Images images;

    private int views_count;

    private int likes_count;

    private int comments_count;

    private int attachments_count;

    private int rebounds_count;

    private int buckets_count;

    private String created_at;

    private String updated_at;

    private String html_url;

    private String attachments_url;

    private String buckets_url;

    private String comments_url;

    private String likes_url;

    private String projects_url;

    private String rebounds_url;

    private boolean animated;

    private List<String> tags ;

    private User user;

    private Team team;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getWidth(){
        return this.width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public int getHeight(){
        return this.height;
    }
    public void setImages(Images images){
        this.images = images;
    }
    public Images getImages(){
        return this.images;
    }
    public void setViews_count(int views_count){
        this.views_count = views_count;
    }
    public int getViews_count(){
        return this.views_count;
    }
    public void setLikes_count(int likes_count){
        this.likes_count = likes_count;
    }
    public int getLikes_count(){
        return this.likes_count;
    }
    public void setComments_count(int comments_count){
        this.comments_count = comments_count;
    }
    public int getComments_count(){
        return this.comments_count;
    }
    public void setAttachments_count(int attachments_count){
        this.attachments_count = attachments_count;
    }
    public int getAttachments_count(){
        return this.attachments_count;
    }
    public void setRebounds_count(int rebounds_count){
        this.rebounds_count = rebounds_count;
    }
    public int getRebounds_count(){
        return this.rebounds_count;
    }
    public void setBuckets_count(int buckets_count){
        this.buckets_count = buckets_count;
    }
    public int getBuckets_count(){
        return this.buckets_count;
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
    public void setHtml_url(String html_url){
        this.html_url = html_url;
    }
    public String getHtml_url(){
        return this.html_url;
    }
    public void setAttachments_url(String attachments_url){
        this.attachments_url = attachments_url;
    }
    public String getAttachments_url(){
        return this.attachments_url;
    }
    public void setBuckets_url(String buckets_url){
        this.buckets_url = buckets_url;
    }
    public String getBuckets_url(){
        return this.buckets_url;
    }
    public void setComments_url(String comments_url){
        this.comments_url = comments_url;
    }
    public String getComments_url(){
        return this.comments_url;
    }
    public void setLikes_url(String likes_url){
        this.likes_url = likes_url;
    }
    public String getLikes_url(){
        return this.likes_url;
    }
    public void setProjects_url(String projects_url){
        this.projects_url = projects_url;
    }
    public String getProjects_url(){
        return this.projects_url;
    }
    public void setRebounds_url(String rebounds_url){
        this.rebounds_url = rebounds_url;
    }
    public String getRebounds_url(){
        return this.rebounds_url;
    }
    public void setAnimated(boolean animated){
        this.animated = animated;
    }
    public boolean getAnimated(){
        return this.animated;
    }
    public void setString(List<String> tags){
        this.tags = tags;
    }
    public List<String> getString(){
        return this.tags;
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
    public void setTeam(Team team){
        this.team = team;
    }
    public Team getTeam(){
        return this.team;
    }



    @Override
    public String toString() {
        return "Shot{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", images=" + images +
                ", views_count=" + views_count +
                ", likes_count=" + likes_count +
                ", comments_count=" + comments_count +
                ", attachments_count=" + attachments_count +
                ", rebounds_count=" + rebounds_count +
                ", buckets_count=" + buckets_count +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", html_url='" + html_url + '\'' +
                ", attachments_url='" + attachments_url + '\'' +
                ", buckets_url='" + buckets_url + '\'' +
                ", comments_url='" + comments_url + '\'' +
                ", likes_url='" + likes_url + '\'' +
                ", projects_url='" + projects_url + '\'' +
                ", rebounds_url='" + rebounds_url + '\'' +
                ", animated=" + animated +
                ", tags=" + tags +
                ", user=" + user +
                ", team=" + team +
                '}';
    }
}
