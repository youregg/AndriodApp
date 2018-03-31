package com.example.lanyetc.campusgo.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanyetc.campusgo.Bean.allEntity;
import com.example.lanyetc.campusgo.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by ZHANGXY on 2018/3/24.
 */

public class newsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    protected final static int SET_IMAGEVIEW = 0;

    private Button backbtn;
    private TextView news_title;
    private TextView news_user;
    private TextView news_time;
    private TextView news_content;
    private ImageView news_portrait;

    private String imageURL;
    private Bitmap bitmap;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SET_IMAGEVIEW:
                    news_portrait.setImageBitmap(bitmap);
                    break;
                default:
                        break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bmob.initialize(this, "91da8de5dc31ab7f5ff8763aa82fda28");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();

        news_title = (TextView) findViewById(R.id.news_title);
        news_user = (TextView) findViewById(R.id.news_user);
        news_time = (TextView) findViewById(R.id.news_time);
        news_content = (TextView) findViewById(R.id.news_content);
        news_portrait = (ImageView) findViewById(R.id.news_portrait);

        //获取fragment1传过来的数据
        String title = intent.getStringExtra("news_title");
        news_title.setText(title);
        String name = intent.getStringExtra("news_user");
        news_user.setText(name);
        String time = intent.getStringExtra("news_time");
        news_time.setText(time);
        String content = intent.getStringExtra("news_content");
        news_content.setText(content);
        imageURL = intent.getStringExtra("news_portrait");   //只是图片的URL
        new Thread(new newsDetailActivity.BitmapThread()).start();

        //news_portrait.setImageBitmap(bitmap);
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

    //根据URL获得bitmap资源
    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    // 子线程接收数据，主线程修改数据
    public class BitmapThread implements Runnable {
        @Override
        public void run() {
            Log.v("imagePath: ",imageURL);
            bitmap = getBitmapFromURL(imageURL);
            Message message = new Message();
            message.what = SET_IMAGEVIEW;
            message.obj = bitmap;
            handler.sendMessage(message);
        }
    }
}
