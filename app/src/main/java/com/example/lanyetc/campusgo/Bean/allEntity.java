package com.example.lanyetc.campusgo.Bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by ZHANGXY on 2018/3/22.
 */

public class allEntity implements Parcelable {
    private String title;
    private String userName;
    private String Time;
    private String content;
    private String imageURL;
    private Bitmap imageSrc;
    //private  String pictureID;

    //Adapter中填充的帖子条目实体
    public allEntity(String title, String imageURL, String userName, String Time, String content) {
        this.title=title;   //标题
        this.userName=userName;   //用户名
        this.imageURL = imageURL;  //图片URL
        //this.imageSrc = imageSrc;   //图片路径
        this.Time=Time;    //发帖时间
        this.content=content;   //内容
    }

   /*
    public String getPictureID() {
        return pictureID;
    }

    public void setPictureID(String pictureID) {
        this.pictureID = pictureID;
    }
*/
    public void setTime(String Time) {
        this.Time= Time;
    }
    public void setUserName(String userNmae) {
        this.userName = userNmae;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageSrc(Bitmap imageSrc) {
        this.imageSrc = imageSrc;
    }

    public void setContent(String content) {this.content=content;}

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }
    public String getTime() {
        return Time;
    }
    public String getUserName() {
        return userName;
    }

    public Bitmap getImageSrc() {
        return imageSrc;
    }

    public String getContent(){return content;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.userName);
        dest.writeString(this.Time);
        dest.writeString(this.content);
        dest.writeParcelable(this.imageSrc, flags);
    }

    protected allEntity(Parcel in) {
        this.title = in.readString();
        this.userName = in.readString();
        this.Time = in.readString();
        this.content = in.readString();
        this.imageSrc = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Parcelable.Creator<allEntity> CREATOR = new Parcelable.Creator<allEntity>() {
        @Override
        public allEntity createFromParcel(Parcel source) {
            return new allEntity(source);
        }

        @Override
        public allEntity[] newArray(int size) {
            return new allEntity[size];
        }
    };
}
