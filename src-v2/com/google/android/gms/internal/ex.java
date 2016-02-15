/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.google.android.gms.internal.gr;

public final class ex
extends Drawable
implements Drawable.Callback {
    private int CA;
    private int CB = 0;
    private boolean CC;
    private b CD;
    private Drawable CE;
    private Drawable CF;
    private boolean CG;
    private boolean CH;
    private boolean CI;
    private int CJ;
    private boolean Cp = true;
    private int Cv = 0;
    private long Cw;
    private int Cx;
    private int Cy;
    private int Cz = 255;

    public ex(Drawable object, Drawable object2) {
        this(null);
        Drawable drawable2 = object;
        if (object == null) {
            drawable2 = CK;
        }
        this.CE = drawable2;
        drawable2.setCallback((Drawable.Callback)this);
        object = this.CD;
        object.CN |= drawable2.getChangingConfigurations();
        object = object2;
        if (object2 == null) {
            object = CK;
        }
        this.CF = object;
        object.setCallback((Drawable.Callback)this);
        object2 = this.CD;
        object2.CN |= object.getChangingConfigurations();
    }

    ex(b b2) {
        this.CD = new b(b2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean canConstantState() {
        if (!this.CG) {
            boolean bl2 = this.CE.getConstantState() != null && this.CF.getConstantState() != null;
            this.CH = bl2;
            this.CG = true;
        }
        return this.CH;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n2 = 1;
        int n3 = 1;
        int n4 = 0;
        switch (this.Cv) {
            case 1: {
                this.Cw = SystemClock.uptimeMillis();
                this.Cv = 2;
                n3 = n4;
                break;
            }
            case 2: {
                if (this.Cw < 0) break;
                float f2 = (float)(SystemClock.uptimeMillis() - this.Cw) / (float)this.CA;
                n3 = f2 >= 1.0f ? n2 : 0;
                if (n3 != 0) {
                    this.Cv = 0;
                }
                f2 = Math.min(f2, 1.0f);
                float f3 = this.Cx;
                this.CB = (int)(f2 * (float)(this.Cy - this.Cx) + f3);
            }
        }
        n2 = this.CB;
        boolean bl2 = this.Cp;
        Drawable drawable2 = this.CE;
        Drawable drawable3 = this.CF;
        if (n3 != 0) {
            if (!bl2 || n2 == 0) {
                drawable2.draw(canvas);
            }
            if (n2 == this.Cz) {
                drawable3.setAlpha(this.Cz);
                drawable3.draw(canvas);
            }
            return;
        }
        if (bl2) {
            drawable2.setAlpha(this.Cz - n2);
        }
        drawable2.draw(canvas);
        if (bl2) {
            drawable2.setAlpha(this.Cz);
        }
        if (n2 > 0) {
            drawable3.setAlpha(n2);
            drawable3.draw(canvas);
            drawable3.setAlpha(this.Cz);
        }
        this.invalidateSelf();
    }

    public Drawable ez() {
        return this.CF;
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.CD.CM | this.CD.CN;
    }

    public Drawable.ConstantState getConstantState() {
        if (this.canConstantState()) {
            this.CD.CM = this.getChangingConfigurations();
            return this.CD;
        }
        return null;
    }

    public int getIntrinsicHeight() {
        return Math.max(this.CE.getIntrinsicHeight(), this.CF.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() {
        return Math.max(this.CE.getIntrinsicWidth(), this.CF.getIntrinsicWidth());
    }

    public int getOpacity() {
        if (!this.CI) {
            this.CJ = Drawable.resolveOpacity((int)this.CE.getOpacity(), (int)this.CF.getOpacity());
            this.CI = true;
        }
        return this.CJ;
    }

    public void invalidateDrawable(Drawable drawable2) {
        if (gr.fu() && (drawable2 = this.getCallback()) != null) {
            drawable2.invalidateDrawable((Drawable)this);
        }
    }

    public Drawable mutate() {
        if (!this.CC && super.mutate() == this) {
            if (!this.canConstantState()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.CE.mutate();
            this.CF.mutate();
            this.CC = true;
        }
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        this.CE.setBounds(rect);
        this.CF.setBounds(rect);
    }

    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l2) {
        if (gr.fu() && (drawable2 = this.getCallback()) != null) {
            drawable2.scheduleDrawable((Drawable)this, runnable, l2);
        }
    }

    public void setAlpha(int n2) {
        if (this.CB == this.Cz) {
            this.CB = n2;
        }
        this.Cz = n2;
        this.invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.CE.setColorFilter(colorFilter);
        this.CF.setColorFilter(colorFilter);
    }

    public void startTransition(int n2) {
        this.Cx = 0;
        this.Cy = this.Cz;
        this.CB = 0;
        this.CA = n2;
        this.Cv = 1;
        this.invalidateSelf();
    }

    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        if (gr.fu() && (drawable2 = this.getCallback()) != null) {
            drawable2.unscheduleDrawable((Drawable)this, runnable);
        }
    }

    private static final class com.google.android.gms.internal.ex$a
    extends Drawable {
        private static final com.google.android.gms.internal.ex$a CK = new com.google.android.gms.internal.ex$a();
        private static final a CL = new a();

        private com.google.android.gms.internal.ex$a() {
        }

        public void draw(Canvas canvas) {
        }

        public Drawable.ConstantState getConstantState() {
            return CL;
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int n2) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        private static final class a
        extends Drawable.ConstantState {
            private a() {
            }

            public int getChangingConfigurations() {
                return 0;
            }

            public Drawable newDrawable() {
                return CK;
            }
        }

    }

    static final class b
    extends Drawable.ConstantState {
        int CM;
        int CN;

        b(b b2) {
            if (b2 != null) {
                this.CM = b2.CM;
                this.CN = b2.CN;
            }
        }

        public int getChangingConfigurations() {
            return this.CM;
        }

        public Drawable newDrawable() {
            return new ex(this);
        }
    }

}

