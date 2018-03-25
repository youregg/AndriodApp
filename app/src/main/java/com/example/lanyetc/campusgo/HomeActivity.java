package com.example.lanyetc.campusgo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import cn.bmob.v3.Bmob;

import java.util.ArrayList;

public class HomeActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private ArrayList<Tab> mTabs= new ArrayList<Tab>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        initTab();
    }


    private void initTab() {

        //实例化4个Tab类的对象
        Tab Tab_home = new Tab(R.drawable.selector_home,0,HomeFragment.class);
        Tab Tab_app = new Tab(R.drawable.selector_app,1,AppFragment.class);
        Tab Tab_gps = new Tab(R.drawable.selector_gps,2,GPSFragment.class);
        Tab Tab_user = new Tab(R.drawable.selector_user,3,UserFragment.class);

        //将这4个对象加到一个List中
        mTabs.add(Tab_home);
        mTabs.add(Tab_app);
        mTabs.add(Tab_gps);
        mTabs.add(Tab_user);

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realcontent);
        //mInflater = LayoutInflater.from(this);

        //通过循环实例化一个个TabSpec
        //并调用其中setIndicator方法
        //然后将TabSpec加到TabHost中
        for (Tab tab  :mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(String.valueOf(tab.getText()));
            tabSpec.setIndicator(buildView(tab));
            mTabHost.addTab(tabSpec,tab.getFragment(), null);
        }

        //通过这行代码可以去除掉底部菜单4个图表之间的分割线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);    }

    //设置Indicator中的View
    private View buildView(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator,null);
        ImageView Tab_img = (ImageView) view.findViewById(R.id.tab_img);
        //TextView Tab_txt = (TextView) view.findViewById(R.id.tab_txt);

        Tab_img.setBackgroundResource(tab.getImage());
        //Tab_txt.setText(tab.getText());
        return view;
    }
}
