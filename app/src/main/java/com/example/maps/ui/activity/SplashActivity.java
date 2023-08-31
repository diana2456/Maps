package com.example.maps.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maps.R;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

   private ProgressBar progressBar;
   private Handler handler;
   private int progressStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
        Objects.requireNonNull(getSupportActionBar()).hide();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;

                    // Обновляем ProgressBar на главном потоке
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });

                    try {
                        // Приостанавливаем поток на 30 миллисекунд
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
