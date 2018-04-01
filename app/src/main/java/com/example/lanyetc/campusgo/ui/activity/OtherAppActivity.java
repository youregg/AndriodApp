package com.example.lanyetc.campusgo.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.lanyetc.campusgo.R;

public class OtherAppActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int btnid = intent.getIntExtra("btnid", 0);
        Log.v("OtherApp: ", btnid+" ");
        switch (btnid){
            case R.id.campuscardbtn:
                setContentView(R.layout.content_campuscard);
                break;
            case R.id.airconditionbtn:
                setContentView(R.layout.content_aircondition);
                break;
            case R.id.bankbtn:
                setContentView(R.layout.content_bank);
                break;
            case R.id.internetbtn:
                setContentView(R.layout.content_internet);
                break;
            case R.id.studentcardbtn:
                setContentView(R.layout.content_studentcard);
                break;
        }

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

        backbtn = findViewById(R.id.aircondition_back);
        backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case  R.id.aircondition_back:
                finish();
                break;

        }
    }
}
