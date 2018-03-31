package com.example.lanyetc.campusgo.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by LZZKBC on 2018/3/28.
 */

public class Post extends BmobObject{
    private String title;   //帖子标题
    private  String content;    //帖子内容
    private BmobDate updateTime;   //帖子更新时间
    private boolean type;     //帖子类型，true为失物，false 为招领
    private _User author;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setUpdateTime(BmobDate updateTime) {
        this.updateTime = updateTime;
    }

    public BmobDate getUpdateTime() {
        return updateTime;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isType() {
        return type;
    }

    public void setAuthor(_User author) {
        this.author = author;
    }

    public _User getAuthor() {
        return author;
    }
}
