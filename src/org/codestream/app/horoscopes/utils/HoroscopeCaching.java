package org.codestream.app.horoscopes.utils;

import android.content.Context;
import android.os.Environment;

import java.io.*;

public class HoroscopeCaching {
    private Context context;
    private static final int APP_VERSION = 1;

    public HoroscopeCaching() {
    }

    public HoroscopeCaching(Context context, String name, int cacheSize) {
        this.context = context;
        this.context = context;
    }

    public boolean writeToCache(String text) throws IOException {
        File f = new File("/tmp/cache.txt");
        if(!f.exists() || f.length() > 10000000){
            f.createNewFile();
        } else {
            RandomAccessFile randomAccessFile = new RandomAccessFile(f,"rw");
            randomAccessFile.writeBytes(text);
        }
        return true;
    }
}