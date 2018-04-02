package com.example.lanyetc.campusgo.ui.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.DocumentsContract;
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

import com.example.lanyetc.campusgo.Bean._User;
import com.example.lanyetc.campusgo.R;

import java.io.File;
import java.io.FileNotFoundException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.BmobNative;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView headbtn;
    private Spinner sp_institute;
    private Spinner sp_grade;
    private ImageButton signbtn;
    private String path;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int RIGESTER_UER = 1;
    //private CircleImageView head_image;

    // 返回主线程更新数据
    private Handler handler = new Handler(){
       @Override
       public void handleMessage(Message msg){
           super.handleMessage(msg);
           switch (msg.what){
               case RIGESTER_UER:
                   //创建用户对象
                   _User p = new _User();
                   //1、获取Preferences
                   SharedPreferences userInfo = getSharedPreferences("userInfo",0);
                   p.setUsername(userInfo.getString("username","null"));
                   p.setPassword(userInfo.getString("password","null"));
                   p.setInstitute(userInfo.getString("institute","null"));
                   p.setGrade(userInfo.getString("grade","null"));
                   BmobFile file = (BmobFile) msg.obj;
                   BmobFile imagefile = new BmobFile(file.getFilename(),null,file.getUrl());
                   Log.v("FileName ",file.getFilename());
                   p.setImage(file);
                   p.signUp(new SaveListener<_User>() {
                       @Override
                       public void done(_User user,BmobException e) {
                           if(e==null){
                               Toast toast = Toast.makeText(SelectActivity.this,"注册成功", Toast.LENGTH_SHORT);
                               toast.setGravity(Gravity.CENTER, 0, 0);
                               toast.show();
                               Intent intent = new Intent(SelectActivity.this, LoginActivity.class);
                               startActivity(intent);
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
           }
       }

    };
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
            path = getPath(this,uri); //真机测试
           // path = getImagePath(uri, this);
            Log.v("Path: ", path);
            //将图片路径存入SharedPreferences
            SharedPreferences userInfo = getSharedPreferences("uderInfo",0);
            SharedPreferences.Editor editor = userInfo.edit();
            editor.putString("image",path);
            Log.v("path: ",path);
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

    //针对图片URI格式为Uri:: file:///storage/emulated/0/DCIM/Camera/IMG_20170613_132837.jpg
    private static String getRealPathFromUri_Byfile(Context context,Uri uri){
        String uri2Str = uri.toString();
        String filePath = uri2Str.substring(uri2Str.indexOf(":") + 3);
        return filePath;
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
        else{
            Log.v("cursor: ","null");
        }
        return path;

    }

    //另一种获取图片路径的方法
    private String getPath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
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
            //path = "file://"+path;
            Log.v("userInfo.Path:  ",path);
            //final BmobFile file = new BmobFile(p.getUsername()+"_headimage",null,tmpfile.toString());
           final BmobFile file = new BmobFile(new File(path));
            if(file != null){
                file.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            Log.v("BmobFile: ", "上传成功");
                            //发送信息，处理注册
                            Message message = new Message();
                            message.what = RIGESTER_UER;
                            message.obj = file;
                            handler.sendMessage(message);
                        }
                        else{
                            Log.v("BmobFile: ", e.toString());
                        }
                    }
                });
            }
            else{
                Log.v("File ","null");
            }
            /*
            file.getFilename();
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
            */

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
