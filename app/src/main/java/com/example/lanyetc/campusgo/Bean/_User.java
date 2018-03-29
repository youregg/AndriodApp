package com.example.lanyetc.campusgo.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by LZZKBC on 2018/3/25.
 */

public class _User extends BmobUser {
    //private String name;
    //private String password;
    private BmobFile image;
    private String institute;
    private String grade;

/*
    public String getPassword() {
        return password;
    }
    public void setPassword(String address) {
        this.password = address;
    }
*/
    public BmobFile getImage(){return image;}
    public void setImage(BmobFile image){this.image = image;}

    public String getInstitute(){return institute;}
    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getGrade(){return grade;}
    public void setGrade(String grade){this.grade = grade;}
}
