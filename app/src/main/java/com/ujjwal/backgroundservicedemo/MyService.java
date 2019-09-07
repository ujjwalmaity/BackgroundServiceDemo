package com.ujjwal.backgroundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {

    private int randomNumber;
    private boolean isRandomNumberGeneratorON;

    private final int MIN = 0;
    private final int MAX = 100;

    class MyServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private IBinder iBinder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Service Demo", "In onBind");
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("Service Demo", "In onUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("Service Demo", "In onReBind");
    }

    @Override
    public void onDestroy() {
        stopRandomNumberGenerator();
        Log.i("Service Demo", "Service Destroyed");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service Demo", "In onStartCommand, thread id: " + Thread.currentThread().getId());

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
                    Log.i("Service Demo", "Thread id: " + Thread.currentThread().getId() + "Random Number: " + randomNumber);
                }
            } catch (InterruptedException e) {
                Log.i("Service Demo", "Thread Interrupted");
            }
        }
    }

    private void stopRandomNumberGenerator() {
        isRandomNumberGeneratorON = false;
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}
