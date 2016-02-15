package com.uservoice.uservoicesdk.image;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
    private ImageView imageView;
    private final String url;

    public DownloadImageTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
        imageView.setImageBitmap(null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected android.graphics.Bitmap doInBackground(java.lang.Void... r6) {
        /*
        r5 = this;
        r0 = 0;
        r2 = 0;
        r3 = new java.net.URL;	 Catch:{ Exception -> 0x0015 }
        r4 = r5.url;	 Catch:{ Exception -> 0x0015 }
        r3.<init>(r4);	 Catch:{ Exception -> 0x0015 }
        r2 = r3.openStream();	 Catch:{ Exception -> 0x0015 }
        r0 = android.graphics.BitmapFactory.decodeStream(r2);	 Catch:{ Exception -> 0x0015 }
        r2.close();	 Catch:{ Exception -> 0x0024 }
    L_0x0014:
        return r0;
    L_0x0015:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x001f }
        r2.close();	 Catch:{ Exception -> 0x001d }
        goto L_0x0014;
    L_0x001d:
        r3 = move-exception;
        goto L_0x0014;
    L_0x001f:
        r3 = move-exception;
        r2.close();	 Catch:{ Exception -> 0x0026 }
    L_0x0023:
        throw r3;
    L_0x0024:
        r3 = move-exception;
        goto L_0x0014;
    L_0x0026:
        r4 = move-exception;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uservoice.uservoicesdk.image.DownloadImageTask.doInBackground(java.lang.Void[]):android.graphics.Bitmap");
    }

    protected void onPostExecute(Bitmap bitmap) {
        ImageCache.getInstance().set(this.url, bitmap);
        this.imageView.setImageBitmap(bitmap);
    }
}
