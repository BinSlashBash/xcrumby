/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.os.AsyncTask
 *  android.widget.ImageView
 */
package com.uservoice.uservoicesdk.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.uservoice.uservoicesdk.image.ImageCache;
import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask
extends AsyncTask<Void, Void, Bitmap> {
    private ImageView imageView;
    private final String url;

    public DownloadImageTask(String string2, ImageView imageView) {
        this.url = string2;
        this.imageView = imageView;
        imageView.setImageBitmap(null);
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected /* varargs */ Bitmap doInBackground(Void ... object) {
        InputStream inputStream;
        Object object2 = null;
        object = null;
        object = inputStream = new URL(this.url).openStream();
        object2 = inputStream;
        Bitmap bitmap = BitmapFactory.decodeStream((InputStream)inputStream);
        try {
            inputStream.close();
            return bitmap;
        }
        catch (Exception var1_4) {
            return bitmap;
        }
        catch (Exception exception) {
            object2 = object;
            exception.printStackTrace();
            try {
                object.close();
                return null;
            }
            catch (Exception var1_2) {
                return null;
            }
            catch (Throwable throwable) {
                try {
                    object2.close();
                }
                catch (Exception var2_6) {
                    throw throwable;
                }
                do {
                    throw throwable;
                    break;
                } while (true);
            }
        }
    }

    protected void onPostExecute(Bitmap bitmap) {
        ImageCache.getInstance().set(this.url, bitmap);
        this.imageView.setImageBitmap(bitmap);
    }
}

