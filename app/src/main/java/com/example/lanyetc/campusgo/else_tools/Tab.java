package com.example.lanyetc.campusgo.else_tools;

/**
 * Created by LZZKBC on 2018/3/19.
 */

public class Tab {
    private int Image;
    private int Text;
    private Class Fragment;

    public Tab(int image, int text, Class fragment) {
        this.Image = image;
        this.Text = text;
        this.Fragment = fragment;
    }

    public int getImage() {        return Image;    }
    public void setImage(int image) {        this.Image = image;    }
    public int getText() {        return Text;    }
    public void setText(int text) {        this.Text = text;    }
    public Class getFragment() {        return Fragment;    }
    public void setFragment(Class fragment) {        this.Fragment = fragment;    }
}
