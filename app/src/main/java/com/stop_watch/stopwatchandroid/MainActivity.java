package com.stop_watch.stopwatchandroid;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean isRunning;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        timer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = wasRunning;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = wasRunning;
    }

    public void startTimer(View view){
        isRunning = true;
    }
    public void stopTimer(View view){
        isRunning = false;
    }
    public void resetTimer(View view){
        isRunning = false;
        seconds = 0;
    }
    private void timer() {
        final TextView timeView = (TextView)findViewById(R.id.timeState);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d",
                        hours, minutes, secs);
                timeView.setText(time);
                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    // Handle configuration changes by saving the Activity state variables
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("isRunning", isRunning);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = isRunning;
        isRunning = false;
    }
}
