package com.example.lanyetc.campusgo.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.example.lanyetc.campusgo.Bean.Post;
import com.example.lanyetc.campusgo.Bean.Publish;
import com.example.lanyetc.campusgo.Bean._User;
import com.example.lanyetc.campusgo.Bean.allEntity;
import com.example.lanyetc.campusgo.R;
import com.example.lanyetc.campusgo.ui.activity.LoginActivity;
import com.example.lanyetc.campusgo.ui.activity.newsDetailActivity;
import com.example.lanyetc.campusgo.ui.activity.postDetailActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class Fragment1 extends Fragment {

	ListView list;
	protected final static int DOWNLOAD_IMAGE_MSG = 0;
	private int listSize;
	private List<allEntity> mDatas;
	private allEntity tmpItem;
	private com.example.lanyetc.campusgo.Adapter.listAdapter lAdapter;
	/*
	private String username;
	private String title;
	private String content;
	private String time;
	private String imagePath;
	private Bitmap imagesrc;
	*/
	//private List<String> imagePath = new ArrayList<String>();
	private List<String> images;
	private List<String> mName;
	private List<String> Time;
	private List<String> Content;
	private List<String> titles;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case DOWNLOAD_IMAGE_MSG: {
					listSize=0;
					mDatas = (List<allEntity>) msg.obj;
					lAdapter = new com.example.lanyetc.campusgo.Adapter.listAdapter(getContext(),mDatas);
					list.setAdapter(lAdapter);
					//设置item的点击事件，即跳转到帖子详情页面
					list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							Intent intent=new Intent(getActivity(),newsDetailActivity.class);

							intent.putExtra("news_title", mDatas.get(position).getTitle());
							intent.putExtra("news_user", mDatas.get(position).getUserName());
							intent.putExtra("news_time", mDatas.get(position).getTime());
							intent.putExtra("news_content", mDatas.get(position).getContent());
							//intent.putExtra("news_portrait", mDatas.get(position).getImageSrc());   //不能直接传图片，因为太大了？？？
							intent.putExtra("news_portrait", images.get(position));

							//intent.putExtra("item",mDatas.get(position));
							startActivity(intent);
						}
					});
					break;
				}
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fra_base, null);
		images = new ArrayList<String>();
		mName = new ArrayList<String>();
		Time = new ArrayList<String>();
		Content = new ArrayList<String>();
		titles = new ArrayList<String>();
		mDatas = new ArrayList<>();
		list = (ListView)view.findViewById(R.id.lvnews);
		new Thread(new Fragment1.MyThread()).start();
		return view;
	}

//重写的getData
	public void getData(){
		final _User user = BmobUser.getCurrentUser(_User.class);
		BmobQuery<Post> query = new BmobQuery<Post>();
		query.include("author");
		query.order("-updatedAt");
		query.findObjects(new FindListener<Post>() {
			@Override
			public void done(List<Post> list, BmobException e) {
				if(e==null){
					int i = 0;
					for(Post post:list){
						Log.v("username: ", post.getAuthor().getUsername());
						mName.add(post.getAuthor().getUsername());
						titles.add(post.getTitle());
						Time.add(post.getUpdatedAt());
						Content.add(post.getContent());
						BmobFile bmobFile = post.getAuthor().getImage();
						images.add(bmobFile.getFileUrl());
						tmpItem = new allEntity(titles.get(i), images.get(i), mName.get(i), Time.get(i), Content.get(i));
						mDatas.add(tmpItem);
						i++;
					}
					//发送消息，更新ui
					Message message = new Message();
					message.what = DOWNLOAD_IMAGE_MSG;
					message.obj = mDatas;
					handler.sendMessage(message);
				}
				else{
					Log.v("失败： ",e.toString());
				}
			}
		});
	}

	// 子线程接收数据，主线程修改数据
	public class MyThread implements Runnable {
		@Override
		public void run() {
			getData();
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


