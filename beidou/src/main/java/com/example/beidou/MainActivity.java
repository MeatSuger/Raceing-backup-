package com.example.beidou;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private LocationManager locationManager;
    private String provider;
    private TextView tv1;
    private Location location;
    private TextView tv2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.textView1);

        //获取定位服务
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.this.startActivity(intent);
        } else {

            //动态检查权限
            ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        }

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        /*    if (location.getProvider().equals("gps")) {
                //获取当前位置，这里只用到了经纬度
                String string = "纬度为：" + location.getLatitude()
                        + "\n经度为：" + location.getLongitude()
                        + "\n时间为" + location.getTime()
                        + "\n分" + FenToMiao(location.getLatitude())
                        + "\n秒" + FenToMiao(FenToMiao(location.getLatitude()));
                tv1.setText(string);
                System.out.println(location.getProvider());
                Toast.makeText(this,"GPS",Toast.LENGTH_SHORT).show();
            }else {
                String string = "纬度为：" + location.getLatitude() + ",经度为："
                        + location.getLongitude() + "时间为"
                        + location.getTime();
                tv1.setText(string);
                Toast.makeText(this,"NET",Toast.LENGTH_SHORT).show();
            }*/
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }


        //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            }
//        }
//
//    }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    double longitude;          //经度
                    double latitude;             //纬度
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    @SuppressLint("DefaultLocale")
                    String string = String.format("经度为；%d,%.0f,%.2f\n纬度为；%d,%.0f,%.2f ",
                            (int) longitude,
                            FenToMiao(longitude),
                            FenToMiao(FenToMiao(longitude)),
                            (int) latitude,
                            FenToMiao(latitude),
                            FenToMiao(FenToMiao(latitude)));
                    long timeSeconds = System.currentTimeMillis();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());

                    locationchange(longitude, latitude);
                    String time = simpleDateFormat.format(timeSeconds);




                    /*
                     * 更新ui线程
                     * */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setText(string + "\n" + time + "\n" + location.getProvider());
                        }
                    });
                    System.out.println(string);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    private double FenToMiao(double Latitude) {
        double x = Latitude - (int) Latitude;
        return x * 60;
    }

    private void locationchange(double longitude, double latitude) {
        if (longitude < 97.527278 || longitude > 106.196958 || latitude < 21.142312 || latitude > 29.225286) {
            String str = "区域外";
            Log.d(TAG, "locationchange: " + str);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv2.setText(str);
                }
            });
        } else
            Log.d(TAG, "locationchange: " + "区域内");
    }
}

















