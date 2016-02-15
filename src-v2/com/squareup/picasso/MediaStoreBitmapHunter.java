/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.Context
 *  android.database.Cursor
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.net.Uri
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Thumbnails
 *  android.provider.MediaStore$Video
 *  android.provider.MediaStore$Video$Thumbnails
 */
package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import com.squareup.picasso.Action;
import com.squareup.picasso.Cache;
import com.squareup.picasso.ContentStreamBitmapHunter;
import com.squareup.picasso.Dispatcher;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.Stats;
import java.io.IOException;

class MediaStoreBitmapHunter
extends ContentStreamBitmapHunter {
    private static final String[] CONTENT_ORIENTATION = new String[]{"orientation"};

    MediaStoreBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(context, picasso, dispatcher, cache, stats, action);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int getExifOrientation(ContentResolver contentResolver, Uri uri) {
        int n2;
        ContentResolver contentResolver2;
        ContentResolver contentResolver3;
        block6 : {
            block5 : {
                contentResolver3 = null;
                contentResolver2 = null;
                contentResolver = contentResolver.query(uri, CONTENT_ORIENTATION, null, null, null);
                if (contentResolver == null) break block5;
                contentResolver2 = contentResolver;
                contentResolver3 = contentResolver;
                boolean bl2 = contentResolver.moveToFirst();
                if (bl2) break block6;
            }
            if (contentResolver == null) return 0;
            contentResolver.close();
            return 0;
        }
        contentResolver2 = contentResolver;
        contentResolver3 = contentResolver;
        try {
            int n3 = n2 = contentResolver.getInt(0);
            if (contentResolver == null) return n3;
        }
        catch (RuntimeException var0_1) {
            if (contentResolver2 == null) return 0;
            contentResolver2.close();
            return 0;
        }
        catch (Throwable var0_2) {
            if (contentResolver3 == null) throw var0_2;
            contentResolver3.close();
            throw var0_2;
        }
        contentResolver.close();
        return n2;
    }

    static PicassoKind getPicassoKind(int n2, int n3) {
        if (n2 <= PicassoKind.MICRO.width && n3 <= PicassoKind.MICRO.height) {
            return PicassoKind.MICRO;
        }
        if (n2 <= PicassoKind.MINI.width && n3 <= PicassoKind.MINI.height) {
            return PicassoKind.MINI;
        }
        return PicassoKind.FULL;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    Bitmap decode(Request request) throws IOException {
        ContentResolver contentResolver = this.context.getContentResolver();
        this.setExifRotation(MediaStoreBitmapHunter.getExifOrientation(contentResolver, request.uri));
        Object object = contentResolver.getType(request.uri);
        int n2 = object != null && object.startsWith("video/") ? 1 : 0;
        if (!request.hasSize()) return super.decode(request);
        object = MediaStoreBitmapHunter.getPicassoKind(request.targetWidth, request.targetHeight);
        if (n2 == 0 && object == PicassoKind.FULL) {
            return super.decode(request);
        }
        long l2 = ContentUris.parseId((Uri)request.uri);
        BitmapFactory.Options options = MediaStoreBitmapHunter.createBitmapOptions(request);
        options.inJustDecodeBounds = true;
        MediaStoreBitmapHunter.calculateInSampleSize(request.targetWidth, request.targetHeight, object.width, object.height, options);
        if (n2 != 0) {
            n2 = object == PicassoKind.FULL ? 1 : object.androidKind;
            contentResolver = MediaStore.Video.Thumbnails.getThumbnail((ContentResolver)contentResolver, (long)l2, (int)n2, (BitmapFactory.Options)options);
        } else {
            contentResolver = MediaStore.Images.Thumbnails.getThumbnail((ContentResolver)contentResolver, (long)l2, (int)object.androidKind, (BitmapFactory.Options)options);
        }
        object = contentResolver;
        if (contentResolver != null) return object;
        return super.decode(request);
    }

    static enum PicassoKind {
        MICRO(3, 96, 96),
        MINI(1, 512, 384),
        FULL(2, -1, -1);
        
        final int androidKind;
        final int height;
        final int width;

        private PicassoKind(int n3, int n4, int n5) {
            this.androidKind = n3;
            this.width = n4;
            this.height = n5;
        }
    }

}

