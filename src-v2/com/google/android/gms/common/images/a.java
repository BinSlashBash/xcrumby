/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.widget.ImageView
 */
package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.internal.ex;
import com.google.android.gms.internal.ey;
import com.google.android.gms.internal.ez;
import com.google.android.gms.internal.fa;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import java.lang.ref.WeakReference;

public abstract class a {
    final a Cm;
    protected int Cn = 0;
    protected int Co = 0;
    private boolean Cp = true;
    private boolean Cq = false;
    protected int Cr;

    public a(Uri uri, int n2) {
        this.Cm = new a(uri);
        this.Co = n2;
    }

    private Drawable a(Context context, fa fa2, int n2) {
        Resources resources = context.getResources();
        if (this.Cr > 0) {
            Drawable drawable2;
            fa.a a2 = new fa.a(n2, this.Cr);
            context = drawable2 = (Drawable)fa2.get(a2);
            if (drawable2 == null) {
                context = drawable2 = resources.getDrawable(n2);
                if ((this.Cr & 1) != 0) {
                    context = this.a(resources, drawable2);
                }
                fa2.put(a2, context);
            }
            return context;
        }
        return resources.getDrawable(n2);
    }

    public void J(int n2) {
        this.Co = n2;
    }

    protected Drawable a(Resources resources, Drawable drawable2) {
        return ey.a(resources, drawable2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected ex a(Drawable drawable2, Drawable drawable3) {
        Drawable drawable4;
        if (drawable2 != null) {
            drawable4 = drawable2;
            if (drawable2 instanceof ex) {
                drawable4 = ((ex)drawable2).ez();
            }
            do {
                return new ex(drawable4, drawable3);
                break;
            } while (true);
        }
        drawable4 = null;
        return new ex(drawable4, drawable3);
    }

    void a(Context context, Bitmap bitmap, boolean bl2) {
        fb.d((Object)bitmap);
        Bitmap bitmap2 = bitmap;
        if ((this.Cr & 1) != 0) {
            bitmap2 = ey.a(bitmap);
        }
        this.a((Drawable)new BitmapDrawable(context.getResources(), bitmap2), bl2, false, true);
    }

    void a(Context context, fa fa2) {
        Drawable drawable2 = null;
        if (this.Cn != 0) {
            drawable2 = this.a(context, fa2, this.Cn);
        }
        this.a(drawable2, false, true, false);
    }

    void a(Context context, fa fa2, boolean bl2) {
        Drawable drawable2 = null;
        if (this.Co != 0) {
            drawable2 = this.a(context, fa2, this.Co);
        }
        this.a(drawable2, bl2, false, false);
    }

    protected abstract void a(Drawable var1, boolean var2, boolean var3, boolean var4);

    protected boolean b(boolean bl2, boolean bl3) {
        if (this.Cp && !bl3 && (!bl2 || this.Cq)) {
            return true;
        }
        return false;
    }

    static final class a {
        public final Uri uri;

        public a(Uri uri) {
            this.uri = uri;
        }

        public boolean equals(Object object) {
            if (!(object instanceof a)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            return fo.equal((Object)((a)object).uri, (Object)this.uri);
        }

        public int hashCode() {
            return fo.hashCode(new Object[]{this.uri});
        }
    }

    public static final class b
    extends a {
        private WeakReference<ImageView> Cs;

        public b(ImageView imageView, int n2) {
            super(null, n2);
            fb.d((Object)imageView);
            this.Cs = new WeakReference<ImageView>(imageView);
        }

        public b(ImageView imageView, Uri uri) {
            super(uri, 0);
            fb.d((Object)imageView);
            this.Cs = new WeakReference<ImageView>(imageView);
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private void a(ImageView imageView, Drawable drawable2, boolean bl2, boolean bl3, boolean bl4) {
            int n2 = !bl3 && !bl4 ? 1 : 0;
            if (n2 != 0 && imageView instanceof ez) {
                int n3 = ((ez)imageView).eB();
                if (this.Co != 0 && n3 == this.Co) {
                    return;
                }
            }
            if (bl2 = this.b(bl2, bl3)) {
                drawable2 = this.a(imageView.getDrawable(), drawable2);
            }
            imageView.setImageDrawable(drawable2);
            if (imageView instanceof ez) {
                ez ez2 = (ez)imageView;
                imageView = bl4 ? this.Cm.uri : null;
                ez2.e((Uri)imageView);
                n2 = n2 != 0 ? this.Co : 0;
                ez2.L(n2);
            }
            if (!bl2) return;
            ((ex)drawable2).startTransition(250);
        }

        @Override
        protected void a(Drawable drawable2, boolean bl2, boolean bl3, boolean bl4) {
            ImageView imageView = this.Cs.get();
            if (imageView != null) {
                this.a(imageView, drawable2, bl2, bl3, bl4);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            if (!(object instanceof b)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            b b2 = (b)object;
            object = this.Cs.get();
            b2 = b2.Cs.get();
            if (b2 == null) return false;
            if (object == null) return false;
            if (!fo.equal(b2, object)) return false;
            return true;
        }

        public int hashCode() {
            return 0;
        }
    }

    public static final class c
    extends a {
        private WeakReference<ImageManager.OnImageLoadedListener> Ct;

        public c(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
            super(uri, 0);
            fb.d(onImageLoadedListener);
            this.Ct = new WeakReference<ImageManager.OnImageLoadedListener>(onImageLoadedListener);
        }

        @Override
        protected void a(Drawable drawable2, boolean bl2, boolean bl3, boolean bl4) {
            ImageManager.OnImageLoadedListener onImageLoadedListener;
            if (!bl3 && (onImageLoadedListener = this.Ct.get()) != null) {
                onImageLoadedListener.onImageLoaded(this.Cm.uri, drawable2, bl4);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            if (!(object instanceof c)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (c)object;
            ImageManager.OnImageLoadedListener onImageLoadedListener = this.Ct.get();
            ImageManager.OnImageLoadedListener onImageLoadedListener2 = object.Ct.get();
            if (onImageLoadedListener2 == null) return false;
            if (onImageLoadedListener == null) return false;
            if (!fo.equal(onImageLoadedListener2, onImageLoadedListener)) return false;
            if (!fo.equal(object.Cm, this.Cm)) return false;
            return true;
        }

        public int hashCode() {
            return fo.hashCode(this.Cm);
        }
    }

}

