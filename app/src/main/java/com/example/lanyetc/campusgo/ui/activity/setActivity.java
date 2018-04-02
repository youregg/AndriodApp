package com.example.lanyetc.campusgo.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lanyetc.campusgo.R;

import cn.bmob.v3.BmobUser;

/**
 * Created by ZHANGXY on 2018/3/25.
 */

public class setActivity extends AppCompatActivity implements View.OnClickListener {
    private Button backbtn;
    private ImageButton quitbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_detail);

        backbtn = findViewById(R.id.set_back);
        quitbtn = findViewById(R.id.btn_quit);
        backbtn.setOnClickListener(this);
        quitbtn.setOnClickListener(this);

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
            case R.id.set_back:
                finish();
                break;
            case R.id.btn_quit:
                BmobUser.logOut();   //清除缓存用户对象
                Toast toast = Toast.makeText(setActivity.this,"您已退出登录！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
        }
    }
}
