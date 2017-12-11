package com.example.huangwanping.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.BarcodeFormat;


/**
 * Created by Coder-pig on 2015/8/28 0028.
 */
public class BitMapFragment extends Fragment implements View.OnClickListener{

    private ImageView imgView;
    private Button bitBt;
    private EditText editText;
    private Context context;
    public BitMapFragment(Context ct){
        context = ct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bitmap,container,false);
        imgView = (ImageView) view.findViewById(R.id.bitMapView);
        bitBt = (Button) view.findViewById(R.id.bitBt);
        editText = (EditText) view.findViewById(R.id.textStr);
        bitBt.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        generate(v);
    }

    public void generate(View view) {

        String bitContent = editText.getText().toString();
        if (bitContent == null) {
            Toast.makeText(context,"请输入标识",Toast.LENGTH_SHORT).show();
        }else {
            Bitmap qrBitmap = generateBitmap(bitContent, 400, 400);
            imgView.setImageBitmap(qrBitmap);
        }
    }

    private Bitmap generateBitmap(String content,int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


}