/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  android.widget.ImageView
 */
package com.uservoice.uservoicesdk.image;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.uservoice.uservoicesdk.image.DownloadImageTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageCache {
    private static ImageCache instance;
    private Map<String, Bitmap> cache;
    private int capacity = 20;
    private List<String> mru;

    public ImageCache() {
        this.cache = new HashMap<String, Bitmap>(this.capacity);
        this.mru = new ArrayList<String>();
    }

    public static ImageCache getInstance() {
        if (instance == null) {
            instance = new ImageCache();
        }
        return instance;
    }

    public void loadImage(String string2, ImageView imageView) {
        if (this.cache.containsKey(string2)) {
            imageView.setImageBitmap(this.cache.get(string2));
            this.mru.remove(string2);
            this.mru.add(string2);
            return;
        }
        new DownloadImageTask(string2, imageView).execute((Object[])new Void[0]);
    }

    public void purge() {
        this.cache.clear();
        this.mru.clear();
    }

    public void set(String string2, Bitmap bitmap) {
        if (this.cache.containsKey(string2)) {
            this.cache.put(string2, bitmap);
            this.mru.remove(string2);
            this.mru.add(string2);
            return;
        }
        if (this.cache.size() == this.capacity) {
            String string3 = this.mru.get(0);
            this.cache.remove(string3);
            this.mru.remove(0);
        }
        this.cache.put(string2, bitmap);
        this.mru.add(string2);
    }
}

