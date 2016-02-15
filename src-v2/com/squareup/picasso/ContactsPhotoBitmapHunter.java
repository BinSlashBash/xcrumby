/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.UriMatcher
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Rect
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.provider.ContactsContract
 *  android.provider.ContactsContract$Contacts
 */
package com.squareup.picasso;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
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

class ContactsPhotoBitmapHunter
extends BitmapHunter {
    private static final int ID_CONTACT = 3;
    private static final int ID_DISPLAY_PHOTO = 4;
    private static final int ID_LOOKUP = 1;
    private static final int ID_THUMBNAIL = 2;
    private static final UriMatcher matcher = new UriMatcher(-1);
    final Context context;

    static {
        matcher.addURI("com.android.contacts", "contacts/lookup/*/#", 1);
        matcher.addURI("com.android.contacts", "contacts/lookup/*", 1);
        matcher.addURI("com.android.contacts", "contacts/#/photo", 2);
        matcher.addURI("com.android.contacts", "contacts/#", 3);
        matcher.addURI("com.android.contacts", "display_photo/#", 4);
    }

    ContactsPhotoBitmapHunter(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        super(picasso, dispatcher, cache, stats, action);
        this.context = context;
    }

    private Bitmap decodeStream(InputStream inputStream, Request request) throws IOException {
        InputStream inputStream2;
        if (inputStream == null) {
            return null;
        }
        BitmapFactory.Options options = ContactsPhotoBitmapHunter.createBitmapOptions(request);
        if (ContactsPhotoBitmapHunter.requiresInSampleSize(options)) {
            inputStream2 = this.getInputStream();
            BitmapFactory.decodeStream((InputStream)inputStream2, (Rect)null, (BitmapFactory.Options)options);
            ContactsPhotoBitmapHunter.calculateInSampleSize(request.targetWidth, request.targetHeight, options);
        }
        return BitmapFactory.decodeStream((InputStream)inputStream, (Rect)null, (BitmapFactory.Options)options);
        finally {
            Utils.closeQuietly(inputStream2);
        }
    }

    private InputStream getInputStream() throws IOException {
        Uri uri;
        ContentResolver contentResolver = this.context.getContentResolver();
        Uri uri2 = uri = this.getData().uri;
        switch (matcher.match(uri)) {
            default: {
                throw new IllegalStateException("Invalid uri: " + (Object)uri);
            }
            case 1: {
                uri2 = uri = ContactsContract.Contacts.lookupContact((ContentResolver)contentResolver, (Uri)uri);
                if (uri == null) {
                    return null;
                }
            }
            case 3: {
                if (Build.VERSION.SDK_INT < 14) {
                    return ContactsContract.Contacts.openContactPhotoInputStream((ContentResolver)contentResolver, (Uri)uri2);
                }
                return ContactPhotoStreamIcs.get(contentResolver, uri2);
            }
            case 2: 
            case 4: 
        }
        return contentResolver.openInputStream(uri);
    }

    @Override
    Bitmap decode(Request request) throws IOException {
        InputStream inputStream;
        InputStream inputStream2 = null;
        try {
            inputStream2 = inputStream = this.getInputStream();
        }
        catch (Throwable var1_2) {
            Utils.closeQuietly(inputStream2);
            throw var1_2;
        }
        request = this.decodeStream(inputStream, request);
        Utils.closeQuietly(inputStream);
        return request;
    }

    @Override
    Picasso.LoadedFrom getLoadedFrom() {
        return Picasso.LoadedFrom.DISK;
    }

    @TargetApi(value=14)
    private static class ContactPhotoStreamIcs {
        private ContactPhotoStreamIcs() {
        }

        static InputStream get(ContentResolver contentResolver, Uri uri) {
            return ContactsContract.Contacts.openContactPhotoInputStream((ContentResolver)contentResolver, (Uri)uri, (boolean)true);
        }
    }

}

