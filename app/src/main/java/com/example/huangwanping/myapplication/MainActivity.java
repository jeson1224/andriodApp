package com.example.huangwanping.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import com.squareup.okhttp.*;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //UI Object
    private TextView txt_topbar;
    private TextView txt_start;
    private TextView txt_help;
    private TextView txt_my;
    private TextView txt_setting;
    private FrameLayout ly_content;

    //Fragment Object
    private GameFragment fg1;
    private FingerprintFragment fg3;
    private MyFragment fg2;
    private BitMapFragment fg4;
    private FragmentManager fManager;

    private final OkHttpClient client = new OkHttpClient();
    private static String reponseStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();
        bindTextView();
        txt_start.performClick();   //模拟一次点击，既进去后选择第一项

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ReqeustExecute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //txt_help.performClick();   //模拟一次点击
    }

    private void bindTextView()
    {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_start = (TextView) findViewById(R.id.txt_start);
        txt_help = (TextView) findViewById(R.id.txt_help);
        txt_my = (TextView) findViewById(R.id.txt_my);
        txt_setting = (TextView) findViewById(R.id.txt_setting);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_start.setOnClickListener(this);
        txt_help.setOnClickListener(this);
        txt_my.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_start:
                setSelected();
                txt_start.setSelected(true);
                if (fg1 == null){
                    fg1 = new GameFragment();
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.txt_help:
                setSelected();
                txt_help.setSelected(true);
                if(fg2 == null){
                    fg2 = new MyFragment();
                    fg2.SetContent(reponseStr);
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                    fg2.SetContent(reponseStr);
                }
                break;
            case R.id.txt_my:
                setSelected();
                txt_my.setSelected(true);
                if(fg3 == null){
                    fg3 = new FingerprintFragment(this);
                    fg3.onFingerprintClick();
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                    fg3.onFingerprintClick();
                }
                break;
            case R.id.txt_setting:
                setSelected();
                txt_setting.setSelected(true);
                if(fg4 == null){
                    fg4 = new BitMapFragment(this);
                    fTransaction.add(R.id.ly_content,fg4);
                }else{
                    fTransaction.show(fg4);
                }
                break;
        }

        fTransaction.commit();
    }

    //重置所有文本的选中状态
    private void setSelected()
    {
        txt_start.setSelected(false);
        txt_help.setSelected(false);
        txt_my.setSelected(false);
        txt_setting.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction)
    {
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }

    private void ReqeustExecute() throws Exception
    {
            Request request = new Request.Builder()
                    .url("http://192.168.43.254:8011/SSMWeb/view/hello/str.do")
                    .build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                //System.out.println(response.code());
                //System.out.println(response.body().string());
                reponseStr =  response.body().string();
                //reponseStr = response.message();
            }
    }

}
