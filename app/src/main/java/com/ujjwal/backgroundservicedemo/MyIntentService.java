package com.ujjwal.backgroundservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Intent Service Demo", "In onCreate, thread id: " + Thread.currentThread().getId());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("Intent Service Demo", "In onHandleIntent, thread id: " + Thread.currentThread().getId());
        Log.i("Intent Service Demo", Thread.currentThread().getName());

        if (intent != null) {
            int duration = intent.getIntExtra("sleepTime", -1);
            int ctr = 1;
            while (ctr <= duration) {
                Log.i("Intent Service Demo", "Time elapsed " + ctr + " sec");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctr++;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Intent Service Demo", "In onDestroy");
    }
}
