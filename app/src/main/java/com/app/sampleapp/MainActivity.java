package com.app.sampleapp;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
  ServiceWorker serviceWorker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    serviceWorker = new ServiceWorker("service worker");

    final Button button = findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        serviceWorker.addTask(new Task<String>() {
          @Override public String onExecuteTask() {
            return "hello";
          }

          @Override public void onTaskComplete(String s) {
            button.setText(s);
          }
        });
      }
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    serviceWorker.close();
  }
}
