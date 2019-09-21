package com.ujjwal.backgroundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {

    private int randomNumber;
    private boolean isRandomNumberGeneratorON;
    private final int MIN = 0;
    private final int MAX = 100;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        isRandomNumberGeneratorON = false;
        Log.i("Service Demo", "In onDestroy, thread id: " + Thread.currentThread().getId());
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

        return super.onStartCommand(intent, flags, startId);
    }

    private void startRandomNumberGenerator() {
        while (isRandomNumberGeneratorON) {
            try {
                Thread.sleep(1000);
                randomNumber = new Random().nextInt(MAX) + MIN;
                Log.i("Service Demo", "Thread id: " + Thread.currentThread().getId() + "Random Number: " + randomNumber);
            } catch (InterruptedException e) {
                Log.i("Service Demo", "Thread Interrupted");
            }
        }
    }
}
