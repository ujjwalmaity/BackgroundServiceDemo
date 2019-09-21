package com.ujjwal.backgroundservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName();

    private int randomNumber;
    private boolean isRandomNumberGeneratorON;
    private final int MIN = 0;
    private final int MAX = 100;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent, thread name: " + Thread.currentThread().getName());
        isRandomNumberGeneratorON = true;
        startRandomNumberGenerator();
    }

    @Override
    public void onDestroy() {
        isRandomNumberGeneratorON = false;
        Log.i(TAG, "onDestroy, thread id: " + Thread.currentThread().getId());
        super.onDestroy();
    }

    private void startRandomNumberGenerator() {
        while (isRandomNumberGeneratorON) {
            try {
                Thread.sleep(1000);
                randomNumber = new Random().nextInt(MAX) + MIN;
                EventBus.getDefault().post(new RandomNumberEvent(randomNumber));
                Log.i(TAG, "Thread id: " + Thread.currentThread().getId() + "Random Number: " + randomNumber);
            } catch (InterruptedException e) {
                Log.i(TAG, "Thread Interrupted");
            }
        }
    }
}
