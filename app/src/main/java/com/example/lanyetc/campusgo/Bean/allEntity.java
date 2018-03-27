package com.example.lanyetc.campusgo.Bean;

/**
 * Created by ZHANGXY on 2018/3/22.
 */

public class allEntity {
    private String title;
    private String userName;
    private String Time;
    private String content;
    private int images;
    private int id;

    public allEntity(String title, int images, String userName, String Time, int id, String content) {
        this.title=title;
        this.userName=userName;
        this.images=images;
        this.Time=Time;
        this.id = id;
        this.content=content;
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
    public void setId(int id) {
        this.id = id;
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
    public int getId() {
        return id;
    }
    public String getContent(){return content;}
}
