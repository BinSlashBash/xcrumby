/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Rect
 *  android.net.Uri
 */
package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.Context;
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

class ContentStreamBitmapHunter
extends BitmapHunter {
    final Context context;

    ContentStreamBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(picasso, dispatcher, cache, stats, action);
        this.context = context;
    }

    @Override
    Bitmap decode(Request request) throws IOException {
        return this.decodeContentStream(request);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected Bitmap decodeContentStream(Request object) throws IOException {
        InputStream inputStream;
        ContentResolver contentResolver = this.context.getContentResolver();
        BitmapFactory.Options options = ContentStreamBitmapHunter.createBitmapOptions((Request)object);
        if (ContentStreamBitmapHunter.requiresInSampleSize(options)) {
            InputStream inputStream2;
            inputStream = null;
            inputStream = inputStream2 = contentResolver.openInputStream(object.uri);
            BitmapFactory.decodeStream((InputStream)inputStream2, (Rect)null, (BitmapFactory.Options)options);
            Utils.closeQuietly(inputStream2);
            ContentStreamBitmapHunter.calculateInSampleSize(object.targetWidth, object.targetHeight, options);
        }
        object = contentResolver.openInputStream(object.uri);
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

