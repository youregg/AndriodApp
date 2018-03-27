package com.example.lanyetc.campusgo.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanyetc.campusgo.R;

/**
 * Created by ZHANGXY on 2018/3/24.
 */

public class newsDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private Button backbtn;
    private TextView news_title;
    private TextView news_user;
    private TextView news_time;
    private TextView news_content;
    private ImageView news_portrait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        Bundle bundle=getIntent().getExtras();
        Intent intent = getIntent();

        news_title= (TextView) findViewById(R.id.news_title);
        news_user= (TextView)findViewById(R.id.news_user);
        news_time= (TextView)findViewById(R.id.news_time);
        news_content= (TextView)findViewById(R.id.news_content);
        news_portrait = (ImageView) findViewById(R.id.news_portrait);

        String title= intent.getStringExtra("news_title");
        news_title.setText(title);
        String name= intent.getStringExtra("news_user");
        news_user.setText(name);
        String time= intent.getStringExtra("news_time");
        news_time.setText(time);
        String content= intent.getStringExtra("news_content");
        news_content.setText(content);
        int image= bundle.getInt("news_portrait");
        news_portrait.setImageResource(image);

        backbtn = findViewById(R.id.news_back);
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
            case R.id.news_back:
                finish();
                break;
        }
    }
}
