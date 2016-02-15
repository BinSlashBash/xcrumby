/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Rect
 *  android.net.Uri
 */
package com.squareup.picasso;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import com.squareup.picasso.Action;
import com.squareup.picasso.BitmapHunter;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Dispatcher;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.Stats;
import com.squareup.picasso.Utils;
import java.io.IOException;
import java.io.InputStream;

class AssetBitmapHunter
extends BitmapHunter {
    protected static final String ANDROID_ASSET = "android_asset";
    private static final int ASSET_PREFIX_LENGTH = "file:///android_asset/".length();
    private final AssetManager assetManager;

    public AssetBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(picasso, dispatcher, cache, stats, action);
        this.assetManager = context.getAssets();
    }

    @Override
    Bitmap decode(Request request) throws IOException {
        return this.decodeAsset(request.uri.toString().substring(ASSET_PREFIX_LENGTH));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    Bitmap decodeAsset(String object) throws IOException {
        InputStream inputStream;
        BitmapFactory.Options options = AssetBitmapHunter.createBitmapOptions(this.data);
        if (AssetBitmapHunter.requiresInSampleSize(options)) {
            InputStream inputStream2;
            inputStream = null;
            inputStream = inputStream2 = this.assetManager.open((String)object);
            BitmapFactory.decodeStream((InputStream)inputStream2, (Rect)null, (BitmapFactory.Options)options);
            Utils.closeQuietly(inputStream2);
            AssetBitmapHunter.calculateInSampleSize(this.data.targetWidth, this.data.targetHeight, options);
        }
        object = this.assetManager.open((String)object);
        inputStream = BitmapFactory.decodeStream((InputStream)object, (Rect)null, (BitmapFactory.Options)options);
        return inputStream;
        catch (Throwable throwable) {
            Utils.closeQuietly(inputStream);
            throw throwable;
        }
        finally {
            Utils.closeQuietly((InputStream)object);
        }
    }

    @Override
    Picasso.LoadedFrom getLoadedFrom() {
        return Picasso.LoadedFrom.DISK;
    }
}

