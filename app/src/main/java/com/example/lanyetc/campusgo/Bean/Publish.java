package com.example.lanyetc.campusgo.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by LZZKBC on 2018/3/28.
 */

public class Publish extends BmobObject {
    private String postId;
    private String username;

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
