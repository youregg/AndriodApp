package com.example.lanyetc.campusgo.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lanyetc.campusgo.Bean.Post;
import com.example.lanyetc.campusgo.Bean.Publish;
import com.example.lanyetc.campusgo.Bean._User;
import com.example.lanyetc.campusgo.Bean.allEntity;
import com.example.lanyetc.campusgo.R;
import com.example.lanyetc.campusgo.ui.activity.newsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by ZHANGXY on 2018/3/17.
 */

public class Fragment4 extends Fragment {
    ListView list;
    private int i=0;
    private List<allEntity> mDatas;
    private com.example.lanyetc.campusgo.Adapter.listAdapter lAdapter;
    private List<String> images = new ArrayList<String>();
    private List<String> mName =new ArrayList<String>();
    private List<String> Time =new ArrayList<String>();
    private List<String> Content=new ArrayList<String>();
    private List<String> titles=new ArrayList<String>();
   //private String[] mName = { "丢卡狂魔" };
   /* private String[] titles = { "在图书馆丢了张校园卡！急！学号1552xxx"};
    private String[] Time = { "2018.3.20" };
    private String[] Content={
            "今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
            "今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
            "今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
            "今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
            "今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
            "今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
            "今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~",
            "今天下午第三节课在F楼的212教室捡到一把黑色雨伞。上面有白色方格花纹，伞柄是白色的，遗失的同学请尽快联系我哦~"};*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_base, null);
        list = (ListView)view.findViewById(R.id.lvnews);
        mDatas = new ArrayList<>();
        getMyData();
        for(int j = 0; j <titles.size(); j++){
            allEntity bean = new allEntity(titles.get(j),images.get(j),mName.get(j),Time.get(j),Content.get(j));
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
                intent.putExtra("news_portrait", mDatas.get(position).getPictureID());
                startActivity(intent);
            }
        });
        return view;
    }
    public void getMyData() {

        BmobQuery<Publish> publishBmobQuery = new BmobQuery<>();
        publishBmobQuery.addWhereEqualTo("username","admin");
        publishBmobQuery.findObjects(new FindListener<Publish>() {
            @Override
            public void done(List<Publish> list, BmobException e) {
                if(e==null){
                    for (Publish pubData :list) {
                        mName.add(pubData.getUsername());
                        BmobQuery<_User> userBmobQuery = new BmobQuery<>();
                        userBmobQuery.addWhereEqualTo("name",mName.get(i));
                        userBmobQuery.findObjects(new FindListener<_User>(){
                            @Override
                            public void done(List<_User> list, BmobException e) {
                                if(e==null){
                                    Toast.makeText(getActivity(), "user查询数据成功", Toast.LENGTH_SHORT).show();
                                    for (_User userData :list){
                                        BmobFile file = userData.getImage();
                                        images.add(file.getFileUrl());
                                        System.out.println(images.get(i));
                                    }
                                }
                                else{
                                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                }
                            }
                        });
                        BmobQuery<Post> postBmobQuery = new BmobQuery<Post>();
                        postBmobQuery.addWhereEqualTo("ObjectId",pubData.getPostId());
                        postBmobQuery.findObjects(new FindListener<Post>(){
                            @Override
                            public void done(List<Post> list, BmobException e) {
                                if(e==null){
                                    for (Post postData :list){
                                        titles.add(postData.getTitle());
                                        Time.add(postData.getUpdatedAt().toString());
                                        Content.add(postData.getContent());
                                    }
                                }
                                else{
                                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                }
                            }
                        });
                        i++;
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }

            }
        });

    }
}
