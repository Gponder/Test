package com.example.administrator.test.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.test.R;

import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ImageCache {
    LinkedList<Bitmap> images = new LinkedList<>();
    private DiskLruCache mDiskLruCache;

    public void add(Bitmap bitmap){
        images.add(images.size(),bitmap);
    }
    public void isUsed(File file){

    }
    public String CACHE_FOLDER_NAME;
    public void testCache(File file, Context context,ImageView imageView){
        try {
            mDiskLruCache = DiskLruCache.open(getDiskCacheDir(context,CACHE_FOLDER_NAME),
                    getAppVersion(context) , 1, 10*1024*1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestQueue rq = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(rq,new ImageLruCache(10*1024*1024));
        byte[] b = new byte[100];
//      网络地址 要价在图片的控件 加载的图片 出错是加载的图片
        imageLoader.get("",getImageLinseter(imageView,BitmapFactory.decodeByteArray(b,0,100),BitmapFactory.decodeByteArray(b,0,100)));

        LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(file, 1, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageLoader.ImageListener getImageLinseter(final ImageView view,final Bitmap defaultImageBitmap, final Bitmap errorImageBitmap){
        return new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if(imageContainer.getBitmap() != null ){
                    view.setImageBitmap(imageContainer.getBitmap());
                }else if(defaultImageBitmap != null ){
                    view.setImageBitmap(defaultImageBitmap);
                }
            }
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(errorImageBitmap != null){
                    view.setImageBitmap(errorImageBitmap);
                }
            }
        };
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    class ImageLruCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache{

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         */
        public ImageLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            Bitmap bitmap = (Bitmap) value;
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB_MR1){
                return bitmap.getByteCount();
            }
            return bitmap.getRowBytes()*bitmap.getHeight();
        }

        public String hashKeyForDisk(String key) {
            String cacheKey;
            try {
                final MessageDigest mDigest = MessageDigest.getInstance("MD5");
                mDigest.update(key.getBytes());
                cacheKey = bytesToHexString(mDigest.digest());
            } catch (NoSuchAlgorithmException e) {
                cacheKey = String.valueOf(key.hashCode());
            }
            return cacheKey;
        }
        private String bytesToHexString(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        }

        @Override
        public Bitmap getBitmap(String s) {
            String key = hashKeyForDisk(s);
            try {
                if(mDiskLruCache.get(key)==null){
                    return get(s);
                }else{
                    DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
                    Bitmap bitmap = null;
                    if (snapShot != null) {
                        InputStream is = snapShot.getInputStream(0);
                        bitmap = BitmapFactory.decodeStream(is);
                    }
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            put(s,bitmap);
            String key = hashKeyForDisk(s);
            try {
                if(null == mDiskLruCache.get(key)){
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
