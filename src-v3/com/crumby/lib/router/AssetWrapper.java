package com.crumby.lib.router;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

public class AssetWrapper implements FileOpener {
    private AssetManager manager;

    public AssetWrapper(AssetManager manager) {
        this.manager = manager;
    }

    public InputStream open(String filename) {
        try {
            return this.manager.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
