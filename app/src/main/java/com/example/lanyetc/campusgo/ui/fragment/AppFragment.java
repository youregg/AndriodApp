package com.example.lanyetc.campusgo.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.lanyetc.campusgo.ui.activity.OtherAppActivity;
import com.example.lanyetc.campusgo.R;
import com.example.lanyetc.campusgo.ui.activity.lostAndFoundActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppFragment extends Fragment implements View.OnClickListener {

    //六个应用按钮
    private ImageButton lostbtn;
    private ImageButton campuscardbtn;
    private ImageButton studentcardbtn;
    private ImageButton bankbtn;
    private ImageButton airconditionbtn;
    private ImageButton internetbtn;

    private View rootView;//缓存Fragment view
    public AppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_app, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        //获取控件
        lostbtn = rootView.findViewById(R.id.lostbtn);
        campuscardbtn = rootView.findViewById(R.id.campuscardbtn);
        studentcardbtn = rootView.findViewById(R.id.studentcardbtn);
        bankbtn = rootView.findViewById(R.id.bankbtn);
        airconditionbtn = rootView.findViewById(R.id.airconditionbtn);
        internetbtn = rootView.findViewById(R.id.internetbtn);

        //设置按钮监听器
        lostbtn.setOnClickListener(this);
        campuscardbtn.setOnClickListener(this);
        studentcardbtn.setOnClickListener(this);
        bankbtn.setOnClickListener(this);
        airconditionbtn.setOnClickListener(this);
        internetbtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.lostbtn){
            Intent intent =new Intent(getActivity(),lostAndFoundActivity.class);
            startActivity(intent);
        }
        else {
            //创建Intent对象
            Intent intent = new Intent(getActivity(), OtherAppActivity.class);
            //程序自动创建Bundle，然后将对Intent添加的数据装载在Bundle中，对用户透明
            intent.putExtra("btnid", v.getId());
            startActivity(intent);
        }
    }
}
