package com.akshai.azymptotest;



import android.os.Handler;

import okhttp3.internal.http2.Http2Reader;

public class TestClass {

  /*  public Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
        }
    };*/

    final Handler handler = new Handler();


    public String createTime(int duration) {
        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        time += min + ":";
        if (sec < 0) time += "0";
        time += sec;

        return time;
    }

    public static void main(String[] args) {

        TestClass obj = new TestClass();
        String time = obj.createTime(10000);
        System.out.println("Time ->" + time);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this::run,1000);
            }
        },1000);



    }
}


