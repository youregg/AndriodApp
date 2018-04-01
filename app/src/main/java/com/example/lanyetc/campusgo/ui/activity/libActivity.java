package com.example.lanyetc.campusgo.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;

import com.example.lanyetc.campusgo.R;
import com.example.lanyetc.campusgo.util.OpenAPP;

import java.util.List;

/**
 * Created by ZHANGXY on 2018/3/25.
 */

public class libActivity extends AppCompatActivity implements View.OnClickListener {
    private Button backbtn;
    private String Lat;
    private String Long;
    private ImageButton lib_go;
    private String location = "嘉定大学图书馆";
    protected static final int MMM2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_detail);

        backbtn = findViewById(R.id.lib_back);
        backbtn.setOnClickListener(this);
        lib_go = findViewById(R.id.go_lib);
        lib_go.setOnClickListener(this);

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
            case R.id.lib_back:
                //Intent intent =new Intent(lostAndFoundActivity.this,MenuActivity.class);
                //startActivity(intent);
                finish();
                break;
            case R.id.go_lib:
                getLocation();
                OpenAPP open = new OpenAPP();
                open.goTominimap(libActivity.this,Long,Lat,location);

                break;

        }
    }
    void getLocation(){
        double latitude = 0.0;
        double longitude = 0.0;
        System.out.println("jinlaile!");
        LocationManager mLocationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// 没有读写存储权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(libActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MMM2);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// 没有读写存储权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(libActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MMM2);
            }
        }
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Long = longitude+"";
                Lat = latitude+"";
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                 getLngAndLatWithNetwork();
            }
        }
        else if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*60*1000, 1, mLocationListener);
            Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Long = longitude+"";
                Lat = latitude+"";
            }
        }
        else{
                    Toast.makeText(libActivity.this, "请开启定位服务！", Toast.LENGTH_SHORT).show();
        }
    }
    //从网络获取经纬度
    public void getLngAndLatWithNetwork() {
        double latitude = 0.0;
        double longitude = 0.0;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// 没有读写存储权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(libActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MMM2);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// 没有读写存储权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(libActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MMM2);
            }
        }
        LocationManager mLocationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*60*1000, 1, mLocationListener);
        Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Long = longitude+"";
            Lat = latitude+"";
        }
    }
    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };


}
