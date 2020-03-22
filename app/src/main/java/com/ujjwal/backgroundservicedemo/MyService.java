package com.ujjwal.backgroundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {

    private static String TAG = MyService.class.getSimpleName();

    private int randomNumber;
    private boolean isRandomNumberGeneratorON;

    private final int MIN = 0;
    private final int MAX = 100;

    Callback mCallback;

    public interface Callback {
        void getRandomNumber(int num);
    }

    public void setCallbacks(Callback callback) {
        mCallback = callback;
    }

    class MyServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private IBinder iBinder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "In onBind");
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "In onUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "In onReBind");
    }

    @Override
    public void onDestroy() {
        stopRandomNumberGenerator();
        Log.i(TAG, "Service Destroyed");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "In onStartCommand, thread id: " + Thread.currentThread().getId());

        try {
            int val = intent.getIntExtra("value", 0);
            Log.i(TAG, "In onStartCommand, value from activity is: " + val);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        isRandomNumberGeneratorON = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomNumberGenerator();
            }
        }).start();

//        stopSelf(); // Service Cannot be stop from activity, so we stop it from here.
        return START_STICKY;
    }

    private void startRandomNumberGenerator() {
        while (isRandomNumberGeneratorON) {
            try {
                Thread.sleep(1000);
                if (isRandomNumberGeneratorON) {
                    randomNumber = new Random().nextInt(MAX) + MIN;
                    if (mCallback != null) {
                        mCallback.getRandomNumber(randomNumber);
                    }
                    Log.i(TAG, "Thread id: " + Thread.currentThread().getId() + ", Random Number: " + randomNumber);
                }
            } catch (InterruptedException e) {
                Log.i(TAG, "Thread Interrupted");
            }
        }
    }

    private void stopRandomNumberGenerator() {
        isRandomNumberGeneratorON = false;
    }
}
