package com.google.android.gms.common.images;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.images.C0258a.C0257a;
import com.google.android.gms.common.images.C0258a.C0798b;
import com.google.android.gms.common.images.C0258a.C0799c;
import com.google.android.gms.internal.fa;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fu;
import com.google.android.gms.internal.gr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    private static final Object BY;
    private static HashSet<Uri> BZ;
    private static ImageManager Ca;
    private static ImageManager Cb;
    private final ExecutorService Cc;
    private final C0797b Cd;
    private final fa Ce;
    private final Map<C0258a, ImageReceiver> Cf;
    private final Map<Uri, ImageReceiver> Cg;
    private final Context mContext;
    private final Handler mHandler;

    private final class ImageReceiver extends ResultReceiver {
        private final ArrayList<C0258a> Ch;
        final /* synthetic */ ImageManager Ci;
        private final Uri mUri;

        ImageReceiver(ImageManager imageManager, Uri uri) {
            this.Ci = imageManager;
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
            this.Ch = new ArrayList();
        }

        public void m144b(C0258a c0258a) {
            fb.aj("ImageReceiver.addImageRequest() must be called in the main thread");
            this.Ch.add(c0258a);
        }

        public void m145c(C0258a c0258a) {
            fb.aj("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.Ch.remove(c0258a);
        }

        public void ey() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            this.Ci.mContext.sendBroadcast(intent);
        }

        public void onReceiveResult(int resultCode, Bundle resultData) {
            this.Ci.Cc.execute(new C0253c(this.Ci, this.mUri, (ParcelFileDescriptor) resultData.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.a */
    private static final class C0252a {
        static int m146a(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.c */
    private final class C0253c implements Runnable {
        final /* synthetic */ ImageManager Ci;
        private final ParcelFileDescriptor Cj;
        private final Uri mUri;

        public C0253c(ImageManager imageManager, Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.Ci = imageManager;
            this.mUri = uri;
            this.Cj = parcelFileDescriptor;
        }

        public void run() {
            fb.ak("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean z = false;
            Bitmap bitmap = null;
            if (this.Cj != null) {
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(this.Cj.getFileDescriptor());
                } catch (Throwable e) {
                    Log.e("ImageManager", "OOM while loading bitmap for uri: " + this.mUri, e);
                    z = true;
                }
                try {
                    this.Cj.close();
                } catch (Throwable e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.Ci.mHandler.post(new C0256f(this.Ci, this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                Log.w("ImageManager", "Latch interrupted while posting " + this.mUri);
            }
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.d */
    private final class C0254d implements Runnable {
        final /* synthetic */ ImageManager Ci;
        private final C0258a Ck;

        public C0254d(ImageManager imageManager, C0258a c0258a) {
            this.Ci = imageManager;
            this.Ck = c0258a;
        }

        public void run() {
            fb.aj("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) this.Ci.Cf.get(this.Ck);
            if (imageReceiver != null) {
                this.Ci.Cf.remove(this.Ck);
                imageReceiver.m145c(this.Ck);
            }
            C0257a c0257a = this.Ck.Cm;
            if (c0257a.uri == null) {
                this.Ck.m165a(this.Ci.mContext, this.Ci.Ce, true);
                return;
            }
            Bitmap a = this.Ci.m149a(c0257a);
            if (a != null) {
                this.Ck.m163a(this.Ci.mContext, a, true);
                return;
            }
            this.Ck.m164a(this.Ci.mContext, this.Ci.Ce);
            imageReceiver = (ImageReceiver) this.Ci.Cg.get(c0257a.uri);
            if (imageReceiver == null) {
                imageReceiver = new ImageReceiver(this.Ci, c0257a.uri);
                this.Ci.Cg.put(c0257a.uri, imageReceiver);
            }
            imageReceiver.m144b(this.Ck);
            if (!(this.Ck instanceof C0799c)) {
                this.Ci.Cf.put(this.Ck, imageReceiver);
            }
            synchronized (ImageManager.BY) {
                if (!ImageManager.BZ.contains(c0257a.uri)) {
                    ImageManager.BZ.add(c0257a.uri);
                    imageReceiver.ey();
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.e */
    private static final class C0255e implements ComponentCallbacks2 {
        private final C0797b Cd;

        public C0255e(C0797b c0797b) {
            this.Cd = c0797b;
        }

        public void onConfigurationChanged(Configuration newConfig) {
        }

        public void onLowMemory() {
            this.Cd.evictAll();
        }

        public void onTrimMemory(int level) {
            if (level >= 60) {
                this.Cd.evictAll();
            } else if (level >= 20) {
                this.Cd.trimToSize(this.Cd.size() / 2);
            }
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.f */
    private final class C0256f implements Runnable {
        private final CountDownLatch AD;
        final /* synthetic */ ImageManager Ci;
        private boolean Cl;
        private final Bitmap mBitmap;
        private final Uri mUri;

        public C0256f(ImageManager imageManager, Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.Ci = imageManager;
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.Cl = z;
            this.AD = countDownLatch;
        }

        private void m147a(ImageReceiver imageReceiver, boolean z) {
            ArrayList a = imageReceiver.Ch;
            int size = a.size();
            for (int i = 0; i < size; i++) {
                C0258a c0258a = (C0258a) a.get(i);
                if (z) {
                    c0258a.m163a(this.Ci.mContext, this.mBitmap, false);
                } else {
                    c0258a.m165a(this.Ci.mContext, this.Ci.Ce, false);
                }
                if (!(c0258a instanceof C0799c)) {
                    this.Ci.Cf.remove(c0258a);
                }
            }
        }

        public void run() {
            fb.aj("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (this.Ci.Cd != null) {
                if (this.Cl) {
                    this.Ci.Cd.evictAll();
                    System.gc();
                    this.Cl = false;
                    this.Ci.mHandler.post(this);
                    return;
                } else if (z) {
                    this.Ci.Cd.put(new C0257a(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) this.Ci.Cg.remove(this.mUri);
            if (imageReceiver != null) {
                m147a(imageReceiver, z);
            }
            this.AD.countDown();
            synchronized (ImageManager.BY) {
                ImageManager.BZ.remove(this.mUri);
            }
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.b */
    private static final class C0797b extends fu<C0257a, Bitmap> {
        public C0797b(Context context) {
            super(C0797b.m1693w(context));
        }

        private static int m1693w(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int memoryClass = (((context.getApplicationInfo().flags & AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START) != 0 ? 1 : null) == null || !gr.fu()) ? activityManager.getMemoryClass() : C0252a.m146a(activityManager);
            return (int) (((float) (memoryClass * AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START)) * 0.33f);
        }

        protected int m1694a(C0257a c0257a, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        protected void m1695a(boolean z, C0257a c0257a, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z, c0257a, bitmap, bitmap2);
        }

        protected /* synthetic */ void entryRemoved(boolean x0, Object x1, Object x2, Object x3) {
            m1695a(x0, (C0257a) x1, (Bitmap) x2, (Bitmap) x3);
        }

        protected /* synthetic */ int sizeOf(Object x0, Object x1) {
            return m1694a((C0257a) x0, (Bitmap) x1);
        }
    }

    static {
        BY = new Object();
        BZ = new HashSet();
    }

    private ImageManager(Context context, boolean withMemoryCache) {
        this.mContext = context.getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.Cc = Executors.newFixedThreadPool(4);
        if (withMemoryCache) {
            this.Cd = new C0797b(this.mContext);
            if (gr.fx()) {
                ev();
            }
        } else {
            this.Cd = null;
        }
        this.Ce = new fa();
        this.Cf = new HashMap();
        this.Cg = new HashMap();
    }

    private Bitmap m149a(C0257a c0257a) {
        return this.Cd == null ? null : (Bitmap) this.Cd.get(c0257a);
    }

    public static ImageManager m150a(Context context, boolean z) {
        if (z) {
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
        return m150a(context, false);
    }

    private void ev() {
        this.mContext.registerComponentCallbacks(new C0255e(this.Cd));
    }

    public void m158a(C0258a c0258a) {
        fb.aj("ImageManager.loadImage() must be called in the main thread");
        new C0254d(this, c0258a).run();
    }

    public void loadImage(ImageView imageView, int resId) {
        m158a(new C0798b(imageView, resId));
    }

    public void loadImage(ImageView imageView, Uri uri) {
        m158a(new C0798b(imageView, uri));
    }

    public void loadImage(ImageView imageView, Uri uri, int defaultResId) {
        C0258a c0798b = new C0798b(imageView, uri);
        c0798b.m160J(defaultResId);
        m158a(c0798b);
    }

    public void loadImage(OnImageLoadedListener listener, Uri uri) {
        m158a(new C0799c(listener, uri));
    }

    public void loadImage(OnImageLoadedListener listener, Uri uri, int defaultResId) {
        C0258a c0799c = new C0799c(listener, uri);
        c0799c.m160J(defaultResId);
        m158a(c0799c);
    }
}
