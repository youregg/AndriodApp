package com.example.lanyetc.campusgo.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lanyetc.campusgo.Bean.allEntity;
import com.example.lanyetc.campusgo.R;
import com.example.lanyetc.campusgo.ui.activity.newsDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
	ListView list;
	private List<allEntity> mDatas;
	private com.example.lanyetc.campusgo.Adapter.listAdapter lAdapter;
	private String[] mName = { "头发浓密的少女", "做好事不留名", "今天DDL赶完了吗", "不愿透露姓名的同学" };
	private String[] titles = { "F楼212捡到黑色的伞一把",
			"图书馆大厅捡到一张校园卡，放在咨询栏了",
			"宿舍楼道捡到一条毛巾挂在门口了，自取",
			"春合一楼捡到手机，捡到时屏已碎，失主请联系" };
	private String[] Time = { "2018.3.20", "2018.3.21", "2018.3.19", "201.3.22" };
	private int[] ID ={1,2,3,4};
	private String[] Content={
			"今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
			"今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
			"今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
			"今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
			"今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
			"今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
			"今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
			"今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fra_base, null);
		list = (ListView)view.findViewById(R.id.lvnews);
		mDatas = new ArrayList<allEntity>();
		for(int i = 0; i < mName.length; i ++){
			allEntity bean = new allEntity(titles[i],R.drawable.guii,mName[i],Time[i],ID[i],Content[i]);
			mDatas.add(bean);
		}
		lAdapter = new com.example.lanyetc.campusgo.Adapter.listAdapter(getContext(),mDatas);
		list.setAdapter(lAdapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent=new Intent(getActivity(),newsDetailActivity.class);
				intent.putExtra("news_title", mDatas.get(position).getTitle());
				intent.putExtra("news_user", mDatas.get(position).getUserName());
				intent.putExtra("news_time", mDatas.get(position).getTime());
				intent.putExtra("news_content", mDatas.get(position).getContent());
				intent.putExtra("news_portrait", mDatas.get(position).getImages());
				startActivity(intent);
			}
		});
		return view;
	}



}
