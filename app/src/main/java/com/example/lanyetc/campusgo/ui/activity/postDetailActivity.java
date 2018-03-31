package com.example.lanyetc.campusgo.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lanyetc.campusgo.Bean.Post;
import com.example.lanyetc.campusgo.Bean.Publish;
import com.example.lanyetc.campusgo.Bean._User;
import com.example.lanyetc.campusgo.R;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by ZHANGXY on 2018/3/25.
 */

public class postDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private Button backbtn;
    private Button subbtn;
    private EditText editTitle;
    private EditText editContent;
    private org.angmarch.views.NiceSpinner niceSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Bmob.initialize(this, "91da8de5dc31ab7f5ff8763aa82fda28");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        backbtn = findViewById(R.id.post_back);
        backbtn.setOnClickListener(this);
        subbtn = findViewById(R.id.post_sub);
        subbtn.setOnClickListener(this);
        editTitle = findViewById(R.id.edt_order_note_title);
        editContent = findViewById(R.id.edt_order_note_text);
        niceSpinner = findViewById(R.id.nice_spinner);

        List<String> dataSet = new LinkedList<>(Arrays.asList("失物", "招领"));
        niceSpinner.attachDataSource(dataSet);

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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_back:
                //Intent intent =new Intent(lostAndFoundActivity.this,MenuActivity.class);
                //startActivity(intent);
                finish();
                break;
            case R.id.post_sub:
                String post_title = editTitle.getText().toString();
                String post_content = editContent.getText().toString();
                String nice_type = niceSpinner.getText().toString();
                final String userName = "admin";

                boolean spinner_type = false;
                if(nice_type=="失物"){
                    spinner_type = true;
                }
               if(nice_type=="招领"){
                    spinner_type =false;
               }
                if (post_title.equals("") || post_content.equals("")) {
                    return;
                }
                String postId ;

                final Post postObj = new Post();
                postObj.setTitle(post_title);
                postObj.setContent(post_content);
                postObj.setType(spinner_type);
                postObj.setAuthor(BmobUser.getCurrentUser(_User.class));
                postObj.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            String userName = "admin";
                            Toast.makeText(postDetailActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                            Publish pubObj = new Publish();
                            pubObj.setUsername(userName);
                            System.out.println(s);
                            pubObj.setPostId(s);
                            pubObj.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e == null){
                                        Toast.makeText(postDetailActivity.this, "添加pub数据成功", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(postDetailActivity.this, "添加pub数据失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(postDetailActivity.this, "添加数据失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                Intent intent = new Intent(postDetailActivity.this, lostAndFoundActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                finish();
                break;
        }
    }
}
