package com.example.huangwanping.myapplication;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;

/**
 * Created by leafact on 2016/12/21.
 */

public class FingerprintFragment extends Fragment{
    private Context mContext;
    private View view;


    public FingerprintFragment(){
    }

    public FingerprintFragment(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fingerprint,container,false);
        return view;
    }

    public void onFingerprintClick(){
        FingerprintUtil.callFingerPrint(mContext, new FingerprintUtil.OnCallBackListenr() {
            AlertDialog dialog;
            @Override
            public void onSupportFailed() {
                showToast("当前设备不支持指纹");
            }

            @Override
            public void onInsecurity() {
                showToast("当前设备未处于安全保护中");
            }

            @Override
            public void onEnrollFailed() {
                showToast("请到设置中设置指纹");
            }

            @Override
            public void onAuthenticationStart() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.fingerprint,null);
                initView(view);
                builder.setView(view);
                builder.setCancelable(false);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.removeMessages(0);
                        FingerprintUtil.cancel();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                showToast(errString.toString());
                if (dialog != null  &&dialog.isShowing()){
                    dialog.dismiss();
                    handler.removeMessages(0);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                showToast("开锁失败");
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                showToast(helpString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                showToast("开锁成功");
                if (dialog != null  &&dialog.isShowing()){
                    dialog.dismiss();
                    handler.removeMessages(0);
                }

            }
        });
    }
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                int i = postion % 5;
                if (i == 0){
                    tv[4].setBackground(null);
                    tv[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    tv[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    tv[i-1].setBackground(null);
                }
                postion++;
                handler.sendEmptyMessageDelayed(0,100);
            }
        }
    };
    TextView[] tv = new TextView[5];
    private int postion = 0;

    private void initView(View view) {
        postion = 0;
        tv[0] = (TextView) view.findViewById(R.id.tv_1);
        tv[1] = (TextView) view.findViewById(R.id.tv_2);
        tv[2] = (TextView) view.findViewById(R.id.tv_3);
        tv[3] = (TextView) view.findViewById(R.id.tv_4);
        tv[4] = (TextView) view.findViewById(R.id.tv_5);
        handler.sendEmptyMessageDelayed(0,100);
    }


    public void showToast(String name ){
        Toast.makeText(mContext,name,Toast.LENGTH_SHORT).show();
    }


}
