package com.example.administrator.test.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ImageCache {
    LinkedList<Bitmap> images = new LinkedList<>();
    public void add(Bitmap bitmap){
        images.add(images.size(),bitmap);
    }
    public void isUsed(File file){

    }
    public void testCache(File file){
        LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(file, 1, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
