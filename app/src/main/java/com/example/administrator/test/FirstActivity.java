package com.example.administrator.test;

import android.appwidget.AppWidgetProvider;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
        for (int i=0;i<500;i++){
            String task = "task@"+i;
            new MyAsyncTask().execute("a","b","c");
            new HttpClientAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,task);

        }
    }
}
