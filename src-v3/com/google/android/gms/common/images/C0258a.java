package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.internal.ex;
import com.google.android.gms.internal.ey;
import com.google.android.gms.internal.ez;
import com.google.android.gms.internal.fa;
import com.google.android.gms.internal.fa.C0389a;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import java.lang.ref.WeakReference;

/* renamed from: com.google.android.gms.common.images.a */
public abstract class C0258a {
    final C0257a Cm;
    protected int Cn;
    protected int Co;
    private boolean Cp;
    private boolean Cq;
    protected int Cr;

    /* renamed from: com.google.android.gms.common.images.a.a */
    static final class C0257a {
        public final Uri uri;

        public C0257a(Uri uri) {
            this.uri = uri;
        }

        public boolean equals(Object obj) {
            if (obj instanceof C0257a) {
                return this == obj ? true : fo.equal(((C0257a) obj).uri, this.uri);
            } else {
                return false;
            }
        }

        public int hashCode() {
            return fo.hashCode(this.uri);
        }
    }

    /* renamed from: com.google.android.gms.common.images.a.b */
    public static final class C0798b extends C0258a {
        private WeakReference<ImageView> Cs;

        public C0798b(ImageView imageView, int i) {
            super(null, i);
            fb.m920d(imageView);
            this.Cs = new WeakReference(imageView);
        }

        public C0798b(ImageView imageView, Uri uri) {
            super(uri, 0);
            fb.m920d(imageView);
            this.Cs = new WeakReference(imageView);
        }

        private void m1697a(ImageView imageView, Drawable drawable, boolean z, boolean z2, boolean z3) {
            Object obj = (z2 || z3) ? null : 1;
            if (obj != null && (imageView instanceof ez)) {
                int eB = ((ez) imageView).eB();
                if (this.Co != 0 && eB == this.Co) {
                    return;
                }
            }
            boolean b = m167b(z, z2);
            Drawable a = b ? m162a(imageView.getDrawable(), drawable) : drawable;
            imageView.setImageDrawable(a);
            if (imageView instanceof ez) {
                ez ezVar = (ez) imageView;
                ezVar.m916e(z3 ? this.Cm.uri : null);
                ezVar.m915L(obj != null ? this.Co : 0);
            }
            if (b) {
                ((ex) a).startTransition(250);
            }
        }

        protected void m1698a(Drawable drawable, boolean z, boolean z2, boolean z3) {
            ImageView imageView = (ImageView) this.Cs.get();
            if (imageView != null) {
                m1697a(imageView, drawable, z, z2, z3);
            }
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0798b)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            ImageView imageView = (ImageView) this.Cs.get();
            ImageView imageView2 = (ImageView) ((C0798b) obj).Cs.get();
            boolean z = (imageView2 == null || imageView == null || !fo.equal(imageView2, imageView)) ? false : true;
            return z;
        }

        public int hashCode() {
            return 0;
        }
    }

    /* renamed from: com.google.android.gms.common.images.a.c */
    public static final class C0799c extends C0258a {
        private WeakReference<OnImageLoadedListener> Ct;

        public C0799c(OnImageLoadedListener onImageLoadedListener, Uri uri) {
            super(uri, 0);
            fb.m920d(onImageLoadedListener);
            this.Ct = new WeakReference(onImageLoadedListener);
        }

        protected void m1699a(Drawable drawable, boolean z, boolean z2, boolean z3) {
            if (!z2) {
                OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.Ct.get();
                if (onImageLoadedListener != null) {
                    onImageLoadedListener.onImageLoaded(this.Cm.uri, drawable, z3);
                }
            }
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0799c)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C0799c c0799c = (C0799c) obj;
            OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.Ct.get();
            OnImageLoadedListener onImageLoadedListener2 = (OnImageLoadedListener) c0799c.Ct.get();
            boolean z = onImageLoadedListener2 != null && onImageLoadedListener != null && fo.equal(onImageLoadedListener2, onImageLoadedListener) && fo.equal(c0799c.Cm, this.Cm);
            return z;
        }

        public int hashCode() {
            return fo.hashCode(this.Cm);
        }
    }

    public C0258a(Uri uri, int i) {
        this.Cn = 0;
        this.Co = 0;
        this.Cp = true;
        this.Cq = false;
        this.Cm = new C0257a(uri);
        this.Co = i;
    }

    private Drawable m159a(Context context, fa faVar, int i) {
        Resources resources = context.getResources();
        if (this.Cr <= 0) {
            return resources.getDrawable(i);
        }
        C0389a c0389a = new C0389a(i, this.Cr);
        Drawable drawable = (Drawable) faVar.get(c0389a);
        if (drawable != null) {
            return drawable;
        }
        drawable = resources.getDrawable(i);
        if ((this.Cr & 1) != 0) {
            drawable = m161a(resources, drawable);
        }
        faVar.put(c0389a, drawable);
        return drawable;
    }

    public void m160J(int i) {
        this.Co = i;
    }

    protected Drawable m161a(Resources resources, Drawable drawable) {
        return ey.m914a(resources, drawable);
    }

    protected ex m162a(Drawable drawable, Drawable drawable2) {
        if (drawable == null) {
            drawable = null;
        } else if (drawable instanceof ex) {
            drawable = ((ex) drawable).ez();
        }
        return new ex(drawable, drawable2);
    }

    void m163a(Context context, Bitmap bitmap, boolean z) {
        fb.m920d(bitmap);
        if ((this.Cr & 1) != 0) {
            bitmap = ey.m912a(bitmap);
        }
        m166a(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    void m164a(Context context, fa faVar) {
        Drawable drawable = null;
        if (this.Cn != 0) {
            drawable = m159a(context, faVar, this.Cn);
        }
        m166a(drawable, false, true, false);
    }

    void m165a(Context context, fa faVar, boolean z) {
        Drawable drawable = null;
        if (this.Co != 0) {
            drawable = m159a(context, faVar, this.Co);
        }
        m166a(drawable, z, false, false);
    }

    protected abstract void m166a(Drawable drawable, boolean z, boolean z2, boolean z3);

    protected boolean m167b(boolean z, boolean z2) {
        return this.Cp && !z2 && (!z || this.Cq);
    }
}
