package com.example.administrator.test;

import android.appwidget.AppWidgetProvider;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.test.netutil.HttpClientAsyncTask;
import com.example.administrator.test.netutil.MyAsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FirstActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
//      new MyAsyncTask().execute("a","b","c");

        for (int i=0;i<500;i++){
            String task = "task@"+i;
            new TestAsyncTask(task).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,0);

        }
    }
    class TestAsyncTask extends AsyncTask{
        private Object threadPoolTaskData;

        public TestAsyncTask(String s) {
            this.threadPoolTaskData = s;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Log.i("testasynctask","start..."+threadPoolTaskData+"threadid:"+Thread.currentThread().getId()+"threadname:"+Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPoolTaskData=null;
            return null;
        }
    }
}
