package com.example.administrator.test.cache;

import android.graphics.Bitmap;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ImageCache {
    LinkedList<Bitmap> images = new LinkedList<>();
    public void add(Bitmap bitmap){
        images.add(images.size(),bitmap);
    }
    public void isUsed(){

    }
}
