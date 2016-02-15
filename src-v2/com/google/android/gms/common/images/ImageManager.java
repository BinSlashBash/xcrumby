/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.content.ComponentCallbacks
 *  android.content.ComponentCallbacks2
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Configuration
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 *  android.util.Log
 *  android.widget.ImageView
 */
package com.google.android.gms.common.images;

import android.app.ActivityManager;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.images.a;
import com.google.android.gms.internal.fa;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fu;
import com.google.android.gms.internal.gr;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    private static final Object BY = new Object();
    private static HashSet<Uri> BZ = new HashSet();
    private static ImageManager Ca;
    private static ImageManager Cb;
    private final ExecutorService Cc;
    private final b Cd;
    private final fa Ce;
    private final Map<com.google.android.gms.common.images.a, ImageReceiver> Cf;
    private final Map<Uri, ImageReceiver> Cg;
    private final Context mContext;
    private final Handler mHandler;

    /*
     * Enabled aggressive block sorting
     */
    private ImageManager(Context context, boolean bl2) {
        this.mContext = context.getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.Cc = Executors.newFixedThreadPool(4);
        if (bl2) {
            this.Cd = new b(this.mContext);
            if (gr.fx()) {
                this.ev();
            }
        } else {
            this.Cd = null;
        }
        this.Ce = new fa();
        this.Cf = new HashMap<com.google.android.gms.common.images.a, ImageReceiver>();
        this.Cg = new HashMap<Uri, ImageReceiver>();
    }

    private Bitmap a(a.a a2) {
        if (this.Cd == null) {
            return null;
        }
        return (Bitmap)this.Cd.get(a2);
    }

    public static ImageManager a(Context context, boolean bl2) {
        if (bl2) {
            if (Cb == null) {
                Cb = new ImageManager(context, true);
            }
            return Cb;
        }
        if (Ca == null) {
            Ca = new ImageManager(context, false);
        }
        return Ca;
    }

    public static ImageManager create(Context context) {
        return ImageManager.a(context, false);
    }

    private void ev() {
        this.mContext.registerComponentCallbacks((ComponentCallbacks)new e(this.Cd));
    }

    public void a(com.google.android.gms.common.images.a a2) {
        fb.aj("ImageManager.loadImage() must be called in the main thread");
        new d(a2).run();
    }

    public void loadImage(ImageView imageView, int n2) {
        this.a(new a.b(imageView, n2));
    }

    public void loadImage(ImageView imageView, Uri uri) {
        this.a(new a.b(imageView, uri));
    }

    public void loadImage(ImageView object, Uri uri, int n2) {
        object = new a.b((ImageView)object, uri);
        object.J(n2);
        this.a((com.google.android.gms.common.images.a)object);
    }

    public void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        this.a(new a.c(onImageLoadedListener, uri));
    }

    public void loadImage(OnImageLoadedListener object, Uri uri, int n2) {
        object = new a.c((OnImageLoadedListener)object, uri);
        object.J(n2);
        this.a((com.google.android.gms.common.images.a)object);
    }

    private final class ImageReceiver
    extends ResultReceiver {
        private final ArrayList<com.google.android.gms.common.images.a> Ch;
        private final Uri mUri;

        ImageReceiver(Uri uri) {
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
            this.Ch = new ArrayList();
        }

        public void b(com.google.android.gms.common.images.a a2) {
            fb.aj("ImageReceiver.addImageRequest() must be called in the main thread");
            this.Ch.add(a2);
        }

        public void c(com.google.android.gms.common.images.a a2) {
            fb.aj("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.Ch.remove(a2);
        }

        public void ey() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", (Parcelable)this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", (Parcelable)this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            ImageManager.this.mContext.sendBroadcast(intent);
        }

        public void onReceiveResult(int n2, Bundle bundle) {
            bundle = (ParcelFileDescriptor)bundle.getParcelable("com.google.android.gms.extra.fileDescriptor");
            ImageManager.this.Cc.execute(new c(this.mUri, (ParcelFileDescriptor)bundle));
        }
    }

    public static interface OnImageLoadedListener {
        public void onImageLoaded(Uri var1, Drawable var2, boolean var3);
    }

    private static final class a {
        static int a(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    private static final class b
    extends fu<a.a, Bitmap> {
        public b(Context context) {
            super(b.w(context));
        }

        /*
         * Enabled aggressive block sorting
         */
        private static int w(Context context) {
            ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
            int n2 = (context.getApplicationInfo().flags & 1048576) != 0 ? 1 : 0;
            if (n2 != 0 && gr.fu()) {
                n2 = a.a(activityManager);
                return (int)((float)(n2 * 1048576) * 0.33f);
            }
            n2 = activityManager.getMemoryClass();
            return (int)((float)(n2 * 1048576) * 0.33f);
        }

        protected int a(a.a a2, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        protected void a(boolean bl2, a.a a2, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(bl2, a2, bitmap, bitmap2);
        }

        @Override
        protected /* synthetic */ void entryRemoved(boolean bl2, Object object, Object object2, Object object3) {
            this.a(bl2, (a.a)object, (Bitmap)object2, (Bitmap)object3);
        }

        @Override
        protected /* synthetic */ int sizeOf(Object object, Object object2) {
            return this.a((a.a)object, (Bitmap)object2);
        }
    }

    private final class c
    implements Runnable {
        private final ParcelFileDescriptor Cj;
        private final Uri mUri;

        public c(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.mUri = uri;
            this.Cj = parcelFileDescriptor;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            fb.ak("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean bl2 = false;
            boolean bl3 = false;
            Object object = null;
            CountDownLatch countDownLatch = null;
            if (this.Cj != null) {
                try {
                    object = BitmapFactory.decodeFileDescriptor((FileDescriptor)this.Cj.getFileDescriptor());
                    bl2 = bl3;
                }
                catch (OutOfMemoryError var3_4) {
                    Log.e((String)"ImageManager", (String)("OOM while loading bitmap for uri: " + (Object)this.mUri), (Throwable)var3_4);
                    bl2 = true;
                    object = countDownLatch;
                }
                try {
                    this.Cj.close();
                }
                catch (IOException var4_7) {
                    Log.e((String)"ImageManager", (String)"closed failed", (Throwable)var4_7);
                }
            }
            countDownLatch = new CountDownLatch(1);
            ImageManager.this.mHandler.post((Runnable)new f(this.mUri, (Bitmap)object, bl2, countDownLatch));
            try {
                countDownLatch.await();
                return;
            }
            catch (InterruptedException var3_5) {
                Log.w((String)"ImageManager", (String)("Latch interrupted while posting " + (Object)this.mUri));
                return;
            }
        }
    }

    private final class d
    implements Runnable {
        private final com.google.android.gms.common.images.a Ck;

        public d(com.google.android.gms.common.images.a a2) {
            this.Ck = a2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Object object;
            fb.aj("LoadImageRunnable must be executed on the main thread");
            Object object2 = (ImageReceiver)((Object)ImageManager.this.Cf.get(this.Ck));
            if (object2 != null) {
                ImageManager.this.Cf.remove(this.Ck);
                object2.c(this.Ck);
            }
            a.a a2 = this.Ck.Cm;
            if (a2.uri == null) {
                this.Ck.a(ImageManager.this.mContext, ImageManager.this.Ce, true);
                return;
            }
            object2 = ImageManager.this.a(a2);
            if (object2 != null) {
                this.Ck.a(ImageManager.this.mContext, (Bitmap)object2, true);
                return;
            }
            this.Ck.a(ImageManager.this.mContext, ImageManager.this.Ce);
            object2 = object = (ImageReceiver)((Object)ImageManager.this.Cg.get((Object)a2.uri));
            if (object == null) {
                object2 = new ImageReceiver(a2.uri);
                ImageManager.this.Cg.put(a2.uri, object2);
            }
            object2.b(this.Ck);
            if (!(this.Ck instanceof a.c)) {
                ImageManager.this.Cf.put(this.Ck, object2);
            }
            object = BY;
            synchronized (object) {
                if (!BZ.contains((Object)a2.uri)) {
                    BZ.add(a2.uri);
                    object2.ey();
                }
                return;
            }
        }
    }

    private static final class e
    implements ComponentCallbacks2 {
        private final b Cd;

        public e(b b2) {
            this.Cd = b2;
        }

        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onLowMemory() {
            this.Cd.evictAll();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onTrimMemory(int n2) {
            if (n2 >= 60) {
                this.Cd.evictAll();
                return;
            } else {
                if (n2 < 20) return;
                {
                    this.Cd.trimToSize(this.Cd.size() / 2);
                    return;
                }
            }
        }
    }

    private final class f
    implements Runnable {
        private final CountDownLatch AD;
        private boolean Cl;
        private final Bitmap mBitmap;
        private final Uri mUri;

        public f(Uri uri, Bitmap bitmap, boolean bl2, CountDownLatch countDownLatch) {
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.Cl = bl2;
            this.AD = countDownLatch;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void a(ImageReceiver object, boolean bl2) {
            object = ((ImageReceiver)((Object)object)).Ch;
            int n2 = object.size();
            int n3 = 0;
            while (n3 < n2) {
                com.google.android.gms.common.images.a a2 = (com.google.android.gms.common.images.a)object.get(n3);
                if (bl2) {
                    a2.a(ImageManager.this.mContext, this.mBitmap, false);
                } else {
                    a2.a(ImageManager.this.mContext, ImageManager.this.Ce, false);
                }
                if (!(a2 instanceof a.c)) {
                    ImageManager.this.Cf.remove(a2);
                }
                ++n3;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Object object;
            fb.aj("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean bl2 = this.mBitmap != null;
            if (ImageManager.this.Cd != null) {
                if (this.Cl) {
                    ImageManager.this.Cd.evictAll();
                    System.gc();
                    this.Cl = false;
                    ImageManager.this.mHandler.post((Runnable)this);
                    return;
                }
                if (bl2) {
                    ImageManager.this.Cd.put(new a.a(this.mUri), this.mBitmap);
                }
            }
            if ((object = (ImageReceiver)((Object)ImageManager.this.Cg.remove((Object)this.mUri))) != null) {
                this.a((ImageReceiver)((Object)object), bl2);
            }
            this.AD.countDown();
            object = BY;
            synchronized (object) {
                BZ.remove((Object)this.mUri);
                return;
            }
        }
    }

}

