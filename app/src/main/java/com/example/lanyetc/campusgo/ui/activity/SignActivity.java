package com.example.lanyetc.campusgo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lanyetc.campusgo.Bean._User;
import com.example.lanyetc.campusgo.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SignActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_sign_username;
    private EditText et_sign_password;
    private ImageButton nextbtn;
    private ImageButton logbtn;
    private  ImageButton signBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //获取控件
        et_sign_username = (EditText)findViewById(R.id.et_sign_username);
        et_sign_password = (EditText)findViewById(R.id.et_sign_password);
        signBackBtn = findViewById(R.id.sign_back);
        logbtn = (ImageButton)findViewById(R.id.sign_login_btn);
        nextbtn = (ImageButton)findViewById(R.id.next_btn);
        //注册按钮监听事件
        logbtn.setOnClickListener(this);
        nextbtn.setOnClickListener(this);
        signBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.next_btn:
                String username = et_sign_username.getText().toString();
                String password = et_sign_password.getText().toString();
                //非空验证
                if(username.isEmpty() || password.isEmpty()){
                    Toast toast = Toast.makeText(this,"账号或密码不能为空",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    return;
                }
                //使用SharedPreferences来传递数据
                //1、打开Preferences，名称为userInfo，如果存在则打开它，否则创建新的Preferences
                SharedPreferences userInfo = getSharedPreferences("userInfo",0);
                //2、让setting处于编辑状态
                SharedPreferences.Editor editor = userInfo.edit();
                //3、存放数据
                editor.putString("username",username);
                editor.putString("password",password);
                //4、完成提交
                editor.commit();

                //判断用户名是否已经存在
                BmobQuery<_User> query = new BmobQuery<_User>();
                query.addWhereEqualTo("username",username);
                query.findObjects(new FindListener<_User>() {
                    @Override
                    public void done(List<_User> list, BmobException e) {
                        if(e == null){
                            if(list.size() == 0){
                                //跳转页面
                                Intent intent1 = new Intent(SignActivity.this, SelectActivity.class);
                                startActivity(intent1);
                            }
                            else {
                                Toast toast = Toast.makeText(SignActivity.this,"用户已存在，请尝试其他用户名。",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                            }
                        }
                        else{
                            Toast toast = Toast.makeText(SignActivity.this,"注册失败： "+ e,Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                        }
                    }
                });
                break;
            case R.id.sign_login_btn:
                //跳转页面
                //创建Intent对象
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                break;
            case R.id.sign_back:
                finish();
            default:
                break;
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                //使EditText触发一次失去焦点事件
                v.setFocusable(false);
//                v.setFocusable(true); //这里不需要是因为下面一句代码会同时实现这个功能
                v.setFocusableInTouchMode(true);
                return true;
            }
        }
        return false;
    }
}
