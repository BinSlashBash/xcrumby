/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 */
package com.squareup.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.squareup.picasso.Action;
import com.squareup.picasso.BitmapHunter;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Dispatcher;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.Stats;
import com.squareup.picasso.Utils;
import java.io.IOException;

class ResourceBitmapHunter
extends BitmapHunter {
    private final Context context;

    ResourceBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(picasso, dispatcher, cache, stats, action);
        this.context = context;
    }

    private Bitmap decodeResource(Resources resources, int n2, Request request) {
        BitmapFactory.Options options = ResourceBitmapHunter.createBitmapOptions(request);
        if (ResourceBitmapHunter.requiresInSampleSize(options)) {
            BitmapFactory.decodeResource((Resources)resources, (int)n2, (BitmapFactory.Options)options);
            ResourceBitmapHunter.calculateInSampleSize(request.targetWidth, request.targetHeight, options);
        }
        return BitmapFactory.decodeResource((Resources)resources, (int)n2, (BitmapFactory.Options)options);
    }

    @Override
    Bitmap decode(Request request) throws IOException {
        Resources resources = Utils.getResources(this.context, request);
        return this.decodeResource(resources, Utils.getResourceId(resources, request), request);
    }

    @Override
    Picasso.LoadedFrom getLoadedFrom() {
        return Picasso.LoadedFrom.DISK;
    }
}

