package com.example.lanyetc.campusgo.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.lanyetc.campusgo.R;
import com.example.lanyetc.campusgo.ui.activity.libActivity;
import com.example.lanyetc.campusgo.ui.activity.lostAndFoundActivity;
import com.example.lanyetc.campusgo.ui.activity.postDetailActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class GPSFragment extends Fragment implements View.OnClickListener{
    private View rootView;//缓存Fragment view
    private ImageButton goBuilding;
    public GPSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_gps, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        goBuilding = rootView.findViewById(R.id.lib_icon);
        goBuilding.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lib_icon:
                Intent intent1 =new Intent(getActivity(),libActivity.class);
                startActivity(intent1);
        }
    }
}
