package com.example.lanyetc.campusgo.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.astuetz.PagerSlidingTabStrip;
import com.example.lanyetc.campusgo.R;
import com.example.lanyetc.campusgo.ui.fragment.Fragment1;

import cn.bmob.v3.Bmob;

public class lostAndFoundActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager pager;
    private PagerSlidingTabStrip tab;
    private ImageButton postbtn;
    private Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bmob.initialize(this, "91da8de5dc31ab7f5ff8763aa82fda28");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定ViewPager和PagerSlidingTabStrip
        pager= findViewById(R.id.pager);
        tab= findViewById(R.id.tab);
        pager.setAdapter(new com.example.lanyetc.campusgo.Adapter.PagerAdapter(getSupportFragmentManager()));
        tab.setViewPager(pager);
        //设置tab标签文字大小
        tab.setTextSize(50);

        postbtn = findViewById(R.id.postBtn);
        backbtn = findViewById(R.id.btn_lost_back);
        postbtn.setOnClickListener(this);
        backbtn.setOnClickListener(this);

        //实现透明状态栏
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.postBtn:
                Intent intent1 =new Intent(lostAndFoundActivity.this,postDetailActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_lost_back:
                finish();
                break;
            default:
                break;
        }
    }
}
