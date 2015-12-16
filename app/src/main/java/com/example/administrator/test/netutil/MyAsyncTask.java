package com.example.administrator.test.netutil;

import android.os.AsyncTask;
import android.os.Environment;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2015/12/14.
 */
public class MyAsyncTask extends AsyncTask {


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
//        HttpClient httpClient = new DefaultHttpClient();
//        String urlstr= "";
        String baidu = "http://www.baidu.com";
        try {
            URL url = new URL(baidu);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            String sdcard = Environment.getExternalStorageDirectory().toString();
            String path = sdcard+"/"+"baiduwenjian";
            File f = new File(path);
            if (f.exists()){

            }else {
//                f.mkdirs();
                FileOutputStream fos = new FileOutputStream(f);
                byte[] buffer = new byte[1024];
                int len;
                while ((len=is.read(buffer))!=-1){
                    fos.write(buffer,0,len);
                }
                fos.flush();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
