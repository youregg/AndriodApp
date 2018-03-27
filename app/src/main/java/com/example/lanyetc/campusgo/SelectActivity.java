package com.example.lanyetc.campusgo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView headbtn;
    private Spinner sp_institute;
    private Spinner sp_grade;
    private ImageButton signbtn;
    protected static final int CHOOSE_PICTURE = 0;
    //private CircleImageView head_image;

    // 返回主线程更新数据
    private static Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //获取控件
        headbtn = (CircleImageView)findViewById(R.id.head_btn);
        sp_institute = (Spinner)findViewById(R.id.sp_institute);
        sp_grade = (Spinner)findViewById(R.id.sp_grade);
        signbtn = (ImageButton)findViewById(R.id.btn_sign);
        //Sninper点击事件，将内容存至SharedPreference
        sp_institute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] institutes = getResources().getStringArray(R.array.institute_labels); // 获取列表数据
                SharedPreferences userInfo = getSharedPreferences("userInfo",0);
                SharedPreferences.Editor editor = userInfo.edit();
                editor.putString("institute",institutes[i]);
                editor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sp_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] grades = getResources().getStringArray(R.array.grade_labels); // 获取列表数据
                SharedPreferences userInfo = getSharedPreferences("userInfo",0);
                SharedPreferences.Editor editor = userInfo.edit();
                editor.putString("grade",grades[i]);
                editor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //注册按钮监听事件
        headbtn.setOnClickListener(this);
        signbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.head_btn:    //上传头像
                Intent openAlbumIntent = new Intent(
                        Intent.ACTION_PICK);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                //new Thread(new SelectActivity.MyThread()).start();
                break;
            case R.id.btn_sign:
                //在新线程中处理数据提交
                new Thread(new SelectActivity.MyThread()).start();
                default:
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            Uri uri = data.getData();
            Log.v("URI: ", uri.toString());
            String path = getImagePath(uri, this);
            //将图片路径存入SharedPreferences
            SharedPreferences userInfo = getSharedPreferences("uderInfo",0);
            SharedPreferences.Editor editor = userInfo.edit();
            editor.putString("image",path);
            editor.commit();
            ContentResolver cr = this.getContentResolver();
            try {
               if(path.isEmpty()){
                   Log.e("qwe", "Path null");
               }
               else
                   Log.e("qwe", path);
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                /* 将Bitmap设定到ImageView */
                headbtn.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("qwe", e.getMessage(), e);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String getImagePath(Uri uri, Context context) {
        String path = null;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();

        }
        return path;

    }

    // 子线程接收数据，主线程修改数据
    public class MyThread implements Runnable {
        @Override
        public void run() {
            //bmob添加数据测试
            //创建用户对象
            _User p = new _User();
            //1、获取Preferences
            SharedPreferences userInfo = getSharedPreferences("userInfo",0);
            p.setUsername(userInfo.getString("username","null"));
            p.setPassword(userInfo.getString("password","null"));
            p.setInstitute(userInfo.getString("institute","null"));
            p.setGrade(userInfo.getString("grade","null"));
            final BmobFile file = new BmobFile(p.getUsername()+"_headimage",null,new File(userInfo.getString("path","null")).toString());
            p.setImage(file);
            p.signUp(new SaveListener<_User>() {
                @Override
                public void done(_User user,BmobException e) {
                    if(e==null){
                        Toast toast = Toast.makeText(SelectActivity.this,"注册成功", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else if (e.getErrorCode() == 202){
                        Toast toast = Toast.makeText(SelectActivity.this,"注册失败,用户名已存在。", Toast.LENGTH_SHORT);
                        Log.v("注册失败： ",e.toString());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else {
                        Toast toast = Toast.makeText(SelectActivity.this,"注册失败"+e, Toast.LENGTH_SHORT);
                        Log.v("注册失败： ",e.toString());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //infotv.setText(info);
                    //dialog.dismiss();
                }
            });
        }
    }
}
