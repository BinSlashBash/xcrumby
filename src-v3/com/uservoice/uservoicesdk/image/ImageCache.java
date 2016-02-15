package com.uservoice.uservoicesdk.image;

import android.graphics.Bitmap;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageCache {
    private static ImageCache instance;
    private Map<String, Bitmap> cache;
    private int capacity;
    private List<String> mru;

    public ImageCache() {
        this.capacity = 20;
        this.cache = new HashMap(this.capacity);
        this.mru = new ArrayList();
    }

    public static ImageCache getInstance() {
        if (instance == null) {
            instance = new ImageCache();
        }
        return instance;
    }

    public void loadImage(String url, ImageView imageView) {
        if (this.cache.containsKey(url)) {
            imageView.setImageBitmap((Bitmap) this.cache.get(url));
            this.mru.remove(url);
            this.mru.add(url);
            return;
        }
        new DownloadImageTask(url, imageView).execute(new Void[0]);
    }

    public void set(String url, Bitmap bitmap) {
        if (this.cache.containsKey(url)) {
            this.cache.put(url, bitmap);
            this.mru.remove(url);
            this.mru.add(url);
            return;
        }
        if (this.cache.size() == this.capacity) {
            this.cache.remove((String) this.mru.get(0));
            this.mru.remove(0);
        }
        this.cache.put(url, bitmap);
        this.mru.add(url);
    }

    public void purge() {
        this.cache.clear();
        this.mru.clear();
    }
}
