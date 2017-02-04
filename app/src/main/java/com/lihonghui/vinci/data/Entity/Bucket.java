package com.lihonghui.vinci.data.Entity;

/**
 * Created by yq05481 on 2016/12/19.
 */

public class Bucket {
    private int id;

    private String name;

    private String description;

    private int shots_count;

    private String created_at;

    private String updated_at;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setShots_count(int shots_count){
        this.shots_count = shots_count;
    }
    public int getShots_count(){
        return this.shots_count;
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
}
