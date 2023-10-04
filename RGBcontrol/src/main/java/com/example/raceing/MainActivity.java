package com.example.raceing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {



    protected int rID;
    private int gID;
    private  int  bID;

    private TextView tvR;
    private TextView tvG;
    private TextView tvB;
    private SeekBar sbR;
    private SeekBar sbG;
    private SeekBar sbB;
    private Bitmap mSrc;
    private ColorMatrix mColorMatrix;
    private ColorMatrix mHueMatrix;
    private ImageView myview;
    private TextView rgBchanelvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvR = findViewById(R.id.textViewR);
        tvG = findViewById(R.id.textViewG);
        tvB = findViewById(R.id.textViewB);
        sbR = findViewById(R.id.seekBarR);
        sbG = findViewById(R.id.seekBarG);
        sbB = findViewById(R.id.seekBarB);
        rgBchanelvalue = findViewById(R.id.textView6);
        sbR.setOnSeekBarChangeListener(this);
        sbG.setOnSeekBarChangeListener(this);
        sbB.setOnSeekBarChangeListener(this);
        myview = findViewById(R.id.imageView);
        mSrc = BitmapFactory.decodeResource(getResources(),R.drawable.img);
        mColorMatrix = new ColorMatrix();
        mHueMatrix = new ColorMatrix();
        mColorMatrix.reset();
        rID = sbR.getId();
        gID = sbG.getId();
        bID = sbB.getId();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        float time = System.currentTimeMillis();
        tvR.setText("红   " + sbR.getProgress());
        tvG.setText("绿   " + sbG.getProgress());
        tvB.setText("蓝   " + sbB.getProgress());
        imagec(sbR.getProgress(), sbG.getProgress(),sbB.getProgress());
//        rgBchanelvalue.setText('(' + sbR.getProgress() + ',' +  sbG.getProgress() + ',' + sbB.getProgress() +')');
        System.out.println(String.format("(%s,%s,%s)",sbR.getProgress(),sbG.getProgress(),sbB.getProgress()));
        rgBchanelvalue.setText(String.format("(%s,%s,%s)",sbR.getProgress(),sbG.getProgress(),sbB.getProgress()));
        float delaytime = System.currentTimeMillis();
        System.out.println(delaytime-time);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

/**
 * 无法使用
 * USELESS
 */

    private void initialize(int a,int value){
//        switch (a){
//            case 0:
//                mHueMatrix.setRotate(0,value);
//            case 1:
//                mHueMatrix.setRotate(1,value);
//            case 2:
//                mHueMatrix.setRotate(2,value);
        Bitmap dstBp = Bitmap.createBitmap(mSrc.getWidth(),mSrc.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dstBp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        float[] matrix = new float[]{
                1, 0, 0, 0, 0, // 红色通道
                0, 1, 0, 0, 0, // 绿色通道
                0, 0, 1, 0, 0, // 蓝色通道
                0, 0, 0, 1, 0, // 透明度通道
        };

        if (a == rID){
//            matrix[4] = value;
//            matrix[5] = value;
//            matrix[6] = value;
//            matrix[7] = value;
//            matrix[8] = value;
//            matrix[9] = value;
//            matrix[10] = value;
//            System.out.println(value);
//            System.out.println(matrix);
//            mHueMatrix.set(matrix);
////            mColorMatrix.postConcat(mHueMatrix);
//            paint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
//            canvas.drawBitmap(mSrc,0,0,paint);
//            myview.setImageBitmap(dstBp);

            imagec(value,0,0);

        } else if (a == gID) {
//            mHueMatrix.setRotate(2,value);
//            mColorMatrix.postConcat(mHueMatrix);
//            matrix[7] = value;
//            System.out.println(value);
//            mHueMatrix.set(matrix);
//            paint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
//            canvas.drawBitmap(mSrc,0,0,paint);
//            myview.setImageBitmap(dstBp);
            imagec(0,value,0);
        } else if (a == bID) {
//            mHueMatrix.setRotate(3,value);
//            mColorMatrix.postConcat(mHueMatrix);
//            paint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
//            canvas.drawBitmap(mSrc,0,0,paint);
//            myview.setImageBitmap(dstBp);
            imagec(0,0,value);
        }
    }

    public void imagec(int r, int g, int b) {

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.img);
        ColorMatrix colorMatrix=new ColorMatrix(new float[]{
                1,0,0,0,r,
                0,1,0,0,g,
                0,0,1,0,b,
                0,0,0,1,0});
        ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(colorMatrix);
        Bitmap newBitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(newBitmap);
        Paint paint=new Paint();
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(bitmap,0,0,paint);
        myview.setImageBitmap(newBitmap);
    }
}
















