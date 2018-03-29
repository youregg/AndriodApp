package com.example.lanyetc.campusgo.Bean;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by ZHANGXY on 2018/3/22.
 */

public class allEntity {
    private String title;
    private String userName;
    private String Time;
    private String content;
    private int images;
    private  String pictureID;

    public allEntity(String title, String pictureID, String userName, String Time, String content) {
        this.title=title;
        this.userName=userName;
        this.pictureID=pictureID;
        this.Time=Time;
        this.content=content;
    }
    public String getPictureID() {
        return pictureID;
    }

    public void setPictureID(String pictureID) {
        this.pictureID = pictureID;
    }

    public void setTime(String Time) {
        this.Time= Time;
    }
    public void setUserName(String userNmae) {
        this.userName = userNmae;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setImages(int images) {
        this.images = images;
    }
    public void setContent(String content) {this.content=content;}

    public String getTitle() {
        return title;
    }
    public String getTime() {
        return Time;
    }
    public String getUserName() {
        return userName;
    }
    public int getImages() {
        return images;
    }
    public String getContent(){return content;}
}
