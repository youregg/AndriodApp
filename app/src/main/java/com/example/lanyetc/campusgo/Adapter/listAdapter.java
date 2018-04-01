package com.example.lanyetc.campusgo.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanyetc.campusgo.Bean.allEntity;
import com.example.lanyetc.campusgo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ZHANGXY on 2018/3/22.
 */

public class listAdapter extends BaseAdapter {
    private Context context;
    protected LayoutInflater mInflater;
    private List<allEntity> mDatas;

    public listAdapter (Context context , List<allEntity> datas)
    {
        this.mInflater=LayoutInflater.from(context);
        this.mDatas = datas;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get( position);
    }

    @Override
    public long getItemId(int  position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.fra_item,null);
            holder=new Holder(convertView);
            convertView.setTag(holder);
        }else {

            holder= (Holder) convertView.getTag();
        }
        allEntity news=mDatas.get(position);
        holder.tvnews1.setText(news.getTitle());
        //holder.ivnews.setImageBitmap(news.getImageSrc());   //设置图片的uri以显示图片
        holder.tvnews2.setText(news.getUserName());
        holder.tvnews3.setText(news.getTime());
        Picasso.with(context).load(news.getImageURL()).into(holder.ivnews);  //直接使用框架设置图片
        return convertView;
    }
    private class Holder {
        ImageView ivnews;
        TextView tvnews1;
        TextView tvnews2;
        TextView tvnews3;
        public  Holder(View view){
            ivnews= (ImageView) view.findViewById(R.id.portrait);
            tvnews1= (TextView) view.findViewById(R.id.title);
            tvnews2= (TextView) view.findViewById(R.id.user_name);
            tvnews3= (TextView) view.findViewById(R.id.time);
        }
    }

}
