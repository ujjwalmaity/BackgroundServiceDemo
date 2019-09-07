package com.ujjwal.backgroundservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    private ServiceConnection serviceConnection;
    private boolean isServiceBound;
    private MyService myService;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Service Demo", "In MainActivity, thread id: " + Thread.currentThread().getId());

        init();
    }

    private void init() {
        intent = new Intent(getApplicationContext(), MyService.class);
        textView = findViewById(R.id.textView);
    }

    public void stopBackgroundService(View view) {
        stopService(intent);
    }

    public void startBackgroundService(View view) {
        startService(intent);
    }

    public void bindService(View view) {
        if (serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder) iBinder;
                    myService = myServiceBinder.getService();
                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    isServiceBound = false;
                }
            };
        }

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    public void getRandomNumber(View view) {
        if (isServiceBound) {
            textView.setText("Random Number: " + myService.getRandomNumber());
        } else {
            textView.setText("Service not bound");
        }
    }
}