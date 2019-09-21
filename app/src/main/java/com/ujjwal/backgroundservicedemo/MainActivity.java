package com.ujjwal.backgroundservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView textView;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate, thread id: " + Thread.currentThread().getId());

        init();
    }

    private void init() {
        textView = findViewById(R.id.textView);

        intent = new Intent(getApplicationContext(), MyIntentService.class);
    }

    public void stopBackgroundService(View view) {
        stopService(intent);
    }

    public void startBackgroundService(View view) {
        startService(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRandomNumberEvent(RandomNumberEvent randomNumberEvent) {
        textView.setText(randomNumberEvent.getRandomNumber() + "");
    }
}
