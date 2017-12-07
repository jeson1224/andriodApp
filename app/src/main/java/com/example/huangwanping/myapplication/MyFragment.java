package com.example.huangwanping.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.app.Fragment;
/**
 * Created by Coder-pig on 2015/8/28 0028.
 */
public class MyFragment extends Fragment {

    private String content;
    private TextView textView;

    public MyFragment(){

    }
    public void SetContent(String content) {
        this.content = content;
        if (textView != null) {
            textView.setText(content);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content,container,false);
        textView = (TextView) view.findViewById(R.id.txt_content);
        textView.setText(content);
        return view;
    }
}