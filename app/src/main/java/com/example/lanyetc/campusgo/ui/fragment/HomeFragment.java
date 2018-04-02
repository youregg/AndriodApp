package com.example.lanyetc.campusgo.ui.fragment;


import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lanyetc.campusgo.Bean.QnamakerBean;
import com.example.lanyetc.campusgo.R;
import com.example.lanyetc.campusgo.else_tools.PinyinTool;
import com.example.lanyetc.campusgo.ui.activity.LoginActivity;
import com.example.lanyetc.campusgo.ui.activity.OtherAppActivity;
import com.example.lanyetc.campusgo.ui.activity.SelectActivity;
import com.example.lanyetc.campusgo.ui.activity.SignActivity;
import com.example.lanyetc.campusgo.ui.activity.libActivity;
import com.example.lanyetc.campusgo.ui.activity.lostAndFoundActivity;
import com.google.gson.GsonBuilder;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View rootView;//缓存Fragment view
    public HomeFragment() {
        // Required empty public constructor
    }

    //用于调试Qnamaker Post 结果的Text View
    private TextView qnamaker, qnamakerQuestion, qnamakerScore, qnamakerAddress;
    private ImageButton btnSearch;
    private EditText EditQuestion;

    String localAnswer = "", localQuestion = "", localScore = "", localAddress = "";

    //字符串设置问题和答案
    String question, result;

    MyHandler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_home, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        handler = new MyHandler();
        //获取控件
        qnamaker = (TextView) rootView.findViewById(R.id.text_answer);
        qnamakerQuestion = (TextView)rootView.findViewById(R.id.text_qmaker);
        //qnamakerScore = (TextView)findViewById(R.id.textViewScore);
        qnamakerAddress = (TextView)rootView.findViewById(R.id.hint_building);
        EditQuestion = (EditText) rootView.findViewById(R.id.ed_question);
        btnSearch = (ImageButton) rootView.findViewById(R.id.search_btn);
        qnamakerQuestion.setText("Hi,有什么可以帮您的吗？");
        //设置控件监听事件
        btnSearch.setOnClickListener(this);
        qnamakerAddress.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.search_btn){
            question = EditQuestion.getText().toString();
            //发送POST请求
            new Thread(new PostThread()).start();
        }
        else if(v.getId() == R.id.hint_building && localAddress != null &&localAddress != ""){
            int id = -1;
            //Log.v("localAddress",localAddress);
            if(localAddress.equals("F楼")){
                //跳转页面
                id = 0;
                Log.v("localAddress",localAddress);
            }
            else if(localAddress.equals("济事楼")){
                id = 1;
            }
            else if(localAddress.equals("济人楼")){
                id = 2;
            }
            else if(localAddress.equals("同心楼")){
                id = 3;
            }
            else if(localAddress.equals("图书馆")){
                id = 4;
            }
            if(id != -1){ //如果是已经存在的界面
                Intent intent = new Intent(getActivity(), libActivity.class);
                intent.putExtra("build_id", id);
                startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(getActivity(),"十分抱歉，暂无该建筑详情。", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
        class PostThread implements Runnable {
            @Override
            public void run() {
                String str = null;
                DefaultHttpClient httpclient = new DefaultHttpClient();

                try {
                    URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/qnamaker/v2.0/knowledgebases/7f60a669-90ce-4e03-aa99-d2d9ac5a2401/generateAnswer");

                    URI uri = builder.build();
                    HttpPost request = new HttpPost(uri);
                    request.setHeader("Content-Type", "application/json;charset=utf-8");
                    request.setHeader("Ocp-Apim-Subscription-Key", "8fef50115dd24a58b0cf8447ed30d7bc");

                    PinyinTool tool = new PinyinTool();
                    try {
                        question = tool.toPinYin(question, "", PinyinTool.Type.LOWERCASE);
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                    //request.addHeader("charset","utf-8");

                    // Request body
                    String wholeQuestion = "{\"question\" : \"" + question + "\" ,\n" +
                            "    \"top\": 3}";
                    StringEntity reqEntity = new StringEntity(wholeQuestion);
                    reqEntity.setContentEncoding("utf-8");
                    request.setEntity(reqEntity);

                    HttpResponse response = httpclient.execute(request);
                    HttpEntity entity = response.getEntity();

                    if (entity != null) {
                        str = EntityUtils.toString(entity);

                        Message msg = handler.obtainMessage();//每发送一次都要重新获取

                        msg.obj = str;
                        handler.sendMessage(msg);//用handler向主线程发送信息
                        System.out.println(EntityUtils.toString(entity));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        //自定义handler类
        class MyHandler extends Handler {
            @Override
            //接收别的线程的信息并处理
            public void handleMessage(Message msg) {
                result = (String) msg.obj;
                QnamakerBean qnamakerBean = new GsonBuilder().create().fromJson(result, QnamakerBean.class);
                String localAnswerWithAddress[] = qnamakerBean.getAnswers().get(0).getAnswer().split("%");

                //为四个变量赋值
                if(!qnamakerBean.getAnswers().get(0).getAnswer().equals("No good match found in the KB")) {
                    if(localAnswerWithAddress.length >= 2) {
                        localAnswer = localAnswerWithAddress[0];
                        localAddress = localAnswerWithAddress[localAnswerWithAddress.length - 1];
                    }
                    localQuestion = qnamakerBean.getAnswers().get(0).getQuestions().get(0);
                    localScore = String.valueOf(qnamakerBean.getAnswers().get(0).getScore());
                    qnamaker.setText(localAnswer);
                    if(localAddress != null){
                        qnamakerAddress.setText("点击查看更多关于"+ localAddress+"的信息");
                        qnamakerAddress.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);  //下划线
                        qnamakerAddress.getPaint().setAntiAlias(true);  //抗锯齿
                    }
                    qnamakerQuestion.setText(localQuestion);
                }
                else {
                    localAnswer = "小C暂时还不明白，我会尽快学会这些问题的！";
                    localAddress = localQuestion = "";
                    localScore = String.valueOf(qnamakerBean.getAnswers().get(0).getScore());
                    qnamaker.setText("小C暂时还不明白，我会尽快学会这些问题的！");
                    qnamakerQuestion.setText("Hi,有什么可以帮您的吗？");
//                    qnamakerAddress.setText("");
//                    qnamakerScore.setText(localScore);
//                    qnamakerQuestion.setText("");
                }
                qnamaker.setBackgroundResource(R.color.colorAnswerbg);
            }
        }
    }

