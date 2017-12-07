package com.example.huangwanping.myapplication;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Created by Coder-pig on 2015/8/28 0028.
 */
public class GameFragment extends Fragment implements View.OnClickListener {
    //game
    private ImageView[] views;
    private int[] Imgs;
    private int num;

    public  GameFragment() {

    }

    private void bindGameView(View v)
    {
        num = 0;
        views =  new ImageView[5];
        Imgs = new int[]{R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5};

        int[] imgIds = new int[]{R.id.img1,R.id.img2,R.id.img3,R.id.img4,R.id.img5};
        for (int i = 0; i<Imgs.length; i ++) {
            views[i] = (ImageView)v.findViewById(imgIds[i]);
            views[i].setOnClickListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game,container,false);
        //ImageView image_content = (ImageView) view.findViewById(R.id.txt_content);
        //txt_content.setText(content);
        bindGameView(view);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        DoChangeImage(v);
    }

    private void DoChangeImage(View v)
    {
        num++;
        num = num % 5;
        ImageView view = (ImageView)v;
        //点击后设置文本框显示的文字
        //textshow.setText("点击了按钮!");
        //Toast.makeText(getApplicationContext(), "按钮被点击了", Toast.LENGTH_SHORT).show();
        view.setImageResource(Imgs[num]);
    }

    /*
    * case R.id.img1:
                DoChangeImage(v);
                break;
            case R.id.img2:
                DoChangeImage(v);
                break;
            case R.id.img3:
                DoChangeImage(v);
                break;
            case R.id.img4:
                DoChangeImage(v);
                break;
            case R.id.img5:
                DoChangeImage(v);
                break;
      * */
}