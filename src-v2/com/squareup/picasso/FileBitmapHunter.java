/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.media.ExifInterface
 *  android.net.Uri
 */
package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import com.squareup.picasso.Action;
import com.squareup.picasso.Cache;
import com.squareup.picasso.ContentStreamBitmapHunter;
import com.squareup.picasso.Dispatcher;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.Stats;
import java.io.IOException;

class FileBitmapHunter
extends ContentStreamBitmapHunter {
    FileBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(context, picasso, dispatcher, cache, stats, action);
    }

    static int getFileExifRotation(Uri uri) throws IOException {
        switch (new ExifInterface(uri.getPath()).getAttributeInt("Orientation", 1)) {
            default: {
                return 0;
            }
            case 6: {
                return 90;
            }
            case 3: {
                return 180;
            }
            case 8: 
        }
        return 270;
    }

    @Override
    Bitmap decode(Request request) throws IOException {
        this.setExifRotation(FileBitmapHunter.getFileExifRotation(request.uri));
        return super.decode(request);
    }
}

