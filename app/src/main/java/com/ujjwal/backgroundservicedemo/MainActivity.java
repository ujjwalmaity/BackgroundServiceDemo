package com.ujjwal.backgroundservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Service Demo", "In MainActivity, thread id: " + Thread.currentThread().getId());

        init();
    }

    private void init() {
        intent = new Intent(getApplicationContext(), MyService.class);
    }

    public void stopBackgroundService(View view) {
    }

    public void startBackgroundService(View view) {
        startService(intent);
    }
}
