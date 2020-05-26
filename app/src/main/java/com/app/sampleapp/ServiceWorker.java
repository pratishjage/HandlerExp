package com.app.sampleapp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;

public class ServiceWorker extends HandlerThread {

  private CustomHandler customHandler;
  private UiHandler uiHandler;

  public ServiceWorker(String name) {
    super(name);
    start();
  }

  @Override
  protected void onLooperPrepared() {
    super.onLooperPrepared();
    customHandler = new CustomHandler(getLooper());
    uiHandler = new UiHandler(Looper.getMainLooper(), null);
  }

  public void addTask(final Task task) {

    if (customHandler != null) {
      uiHandler.addTask(task);
      customHandler.post(new CustomRunnable(task));
    }
  }

  static private class CustomHandler extends Handler {

    CustomHandler(Looper looper) {
      super(looper);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
    }
  }

  private static class UiHandler extends Handler {
    Task task;

    UiHandler(Looper looper, Task task) {
      super(looper);
    }

    private void addTask(Task task) {
      this.task = task;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
      task.onTaskComplete(msg.obj);
    }
  }

  class CustomRunnable implements Runnable {
    Task task;

    CustomRunnable(Task task) {
      this.task = task;
    }

    @Override public void run() {
      Object T = task
          .onExecuteTask();
      Message message = new Message();
      message.obj = T;
      uiHandler.sendMessage(message);
    }
  }

  public void close() {
    quitSafely();
  }
}

