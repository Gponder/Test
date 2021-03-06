package com.example.administrator.test.netutil;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2015/12/16.
 */
public class HttpClientAsyncTask extends AsyncTask {
//  请求方法 空格 url 空格 协议版本 回车换行   请求行
    @Override
    protected Object doInBackground(Object[] objects) {
        this.defaultHttpClient();

        return null;
    }
    public void defaultHttpClient(){
        HttpClient httpClient = new DefaultHttpClient();
        HttpUriRequest httpUriRequest = new HttpGet("http://www.baidu.com");
        HttpPost post = new HttpPost("http://www.baidu.com");
        try {
            InputStream is = new FileInputStream("baidu");
            HttpEntity httpEntity = new InputStreamEntity(is,is.available());
//            MultipartEntity
            post.setEntity(httpEntity);
            HttpResponse reponse = httpClient.execute(httpUriRequest);
            HttpEntity entity = reponse.getEntity();
            InputStream content = entity.getContent();
            OutputStream os=new FileOutputStream("/baidu");
            entity.writeTo(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void androidHttpClient(Context context){
        HttpClient httpClient = AndroidHttpClient.newInstance("");
        BasicHttpContext basicHttpContext = new BasicHttpContext();
        basicHttpContext.setAttribute(ClientContext.COOKIE_STORE,new BasicCookieStore());
        try {
            HttpResponse res = httpClient.execute(null, basicHttpContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
