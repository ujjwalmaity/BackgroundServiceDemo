package com.ujjwal.backgroundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("Service Demo", "In onDestroy, thread id: " + Thread.currentThread().getId());
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service Demo", "In onStartCommand, thread id: " + Thread.currentThread().getId());
        stopSelf(); // Service Cannot be stop from activity, so we stop it from here.
        return super.onStartCommand(intent, flags, startId);
    }
}
