package com.app.sampleapp;

public interface Task<T> {
  T onExecuteTask();

  void onTaskComplete(T t);
}