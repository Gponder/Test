package com.example.administrator.test.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/15.
 */
public class Test {
    public static void main(String[] args){
        File file = new File("D:/baidu");
        try {
            FileOutputStream f = new FileOutputStream(file);
            byte[] buffer = new byte[]{1,1,1,1};
            f.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
