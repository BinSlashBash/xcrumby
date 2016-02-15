/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.res.AssetManager
 */
package com.crumby.lib.router;

import android.content.res.AssetManager;
import com.crumby.lib.router.FileOpener;
import java.io.IOException;
import java.io.InputStream;

public class AssetWrapper
implements FileOpener {
    private AssetManager manager;

    public AssetWrapper(AssetManager assetManager) {
        this.manager = assetManager;
    }

    @Override
    public InputStream open(String object) {
        try {
            object = this.manager.open((String)object);
            return object;
        }
        catch (IOException var1_2) {
            var1_2.printStackTrace();
            return null;
        }
    }
}

