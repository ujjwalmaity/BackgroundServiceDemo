package com.ujjwal.backgroundservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private Intent intent, intent2;

    private ServiceConnection serviceConnection;
    private boolean isServiceBound;
    private MyService myService;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "In MainActivity, thread id: " + Thread.currentThread().getId());

        init();

        bindService();

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService();
    }

    private void init() {
        intent = new Intent(getApplicationContext(), MyService.class);
        intent2 = new Intent(getApplicationContext(), MyIntentService.class);
        textView = findViewById(R.id.textView);
    }

    public void stopBackgroundService(View view) {
        stopService(intent);
    }

    public void startBackgroundService(View view) {
        intent.putExtra("value", 12);
        startService(intent);
    }

    public void bindService() {
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isServiceBound) {
                    try {
                        Thread.sleep(1000);
                        getMyService();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void unbindService() {
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    public void getMyService() {
        if (isServiceBound) {
            myService.setCallbacks(new MyService.Callback() {
                @Override
                public void getRandomNumber(final int num) {
                    /*
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Random Number: " + num);
                        }
                    });
                    */
                    runOnUiThread(new Runnable() {
                        public void run() {
                            textView.setText("Random Number: " + num);
                        }
                    });
                }
            });
        } else {
            textView.setText("Service not bound");
        }
    }

    public void startIntentService(View view) {
        intent2.putExtra("sleepTime", 12);
        startService(intent2);
    }
}