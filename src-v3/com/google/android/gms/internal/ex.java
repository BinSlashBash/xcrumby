package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;
import com.crumby.GalleryViewer;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;

public final class ex extends Drawable implements Callback {
    private int CA;
    private int CB;
    private boolean CC;
    private C0385b CD;
    private Drawable CE;
    private Drawable CF;
    private boolean CG;
    private boolean CH;
    private boolean CI;
    private int CJ;
    private boolean Cp;
    private int Cv;
    private long Cw;
    private int Cx;
    private int Cy;
    private int Cz;

    /* renamed from: com.google.android.gms.internal.ex.a */
    private static final class C0384a extends Drawable {
        private static final C0384a CK;
        private static final C0383a CL;

        /* renamed from: com.google.android.gms.internal.ex.a.a */
        private static final class C0383a extends ConstantState {
            private C0383a() {
            }

            public int getChangingConfigurations() {
                return 0;
            }

            public Drawable newDrawable() {
                return C0384a.CK;
            }
        }

        static {
            CK = new C0384a();
            CL = new C0383a();
        }

        private C0384a() {
        }

        public void draw(Canvas canvas) {
        }

        public ConstantState getConstantState() {
            return CL;
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter cf) {
        }
    }

    /* renamed from: com.google.android.gms.internal.ex.b */
    static final class C0385b extends ConstantState {
        int CM;
        int CN;

        C0385b(C0385b c0385b) {
            if (c0385b != null) {
                this.CM = c0385b.CM;
                this.CN = c0385b.CN;
            }
        }

        public int getChangingConfigurations() {
            return this.CM;
        }

        public Drawable newDrawable() {
            return new ex(this);
        }
    }

    public ex(Drawable drawable, Drawable drawable2) {
        this(null);
        if (drawable == null) {
            drawable = C0384a.CK;
        }
        this.CE = drawable;
        drawable.setCallback(this);
        C0385b c0385b = this.CD;
        c0385b.CN |= drawable.getChangingConfigurations();
        if (drawable2 == null) {
            drawable2 = C0384a.CK;
        }
        this.CF = drawable2;
        drawable2.setCallback(this);
        c0385b = this.CD;
        c0385b.CN |= drawable2.getChangingConfigurations();
    }

    ex(C0385b c0385b) {
        this.Cv = 0;
        this.Cz = MotionEventCompat.ACTION_MASK;
        this.CB = 0;
        this.Cp = true;
        this.CD = new C0385b(c0385b);
    }

    public boolean canConstantState() {
        if (!this.CG) {
            boolean z = (this.CE.getConstantState() == null || this.CF.getConstantState() == null) ? false : true;
            this.CH = z;
            this.CG = true;
        }
        return this.CH;
    }

    public void draw(Canvas canvas) {
        int i = 1;
        int i2 = 0;
        switch (this.Cv) {
            case Std.STD_FILE /*1*/:
                this.Cw = SystemClock.uptimeMillis();
                this.Cv = 2;
                break;
            case Std.STD_URL /*2*/:
                if (this.Cw >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.Cw)) / ((float) this.CA);
                    if (uptimeMillis < GalleryViewer.PROGRESS_COMPLETED) {
                        i = 0;
                    }
                    if (i != 0) {
                        this.Cv = 0;
                    }
                    float min = Math.min(uptimeMillis, GalleryViewer.PROGRESS_COMPLETED);
                    this.CB = (int) ((min * ((float) (this.Cy - this.Cx))) + ((float) this.Cx));
                    break;
                }
                break;
        }
        i2 = i;
        i = this.CB;
        boolean z = this.Cp;
        Drawable drawable = this.CE;
        Drawable drawable2 = this.CF;
        if (i2 != 0) {
            if (!z || i == 0) {
                drawable.draw(canvas);
            }
            if (i == this.Cz) {
                drawable2.setAlpha(this.Cz);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z) {
            drawable.setAlpha(this.Cz - i);
        }
        drawable.draw(canvas);
        if (z) {
            drawable.setAlpha(this.Cz);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.Cz);
        }
        invalidateSelf();
    }

    public Drawable ez() {
        return this.CF;
    }

    public int getChangingConfigurations() {
        return (super.getChangingConfigurations() | this.CD.CM) | this.CD.CN;
    }

    public ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.CD.CM = getChangingConfigurations();
        return this.CD;
    }

    public int getIntrinsicHeight() {
        return Math.max(this.CE.getIntrinsicHeight(), this.CF.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() {
        return Math.max(this.CE.getIntrinsicWidth(), this.CF.getIntrinsicWidth());
    }

    public int getOpacity() {
        if (!this.CI) {
            this.CJ = Drawable.resolveOpacity(this.CE.getOpacity(), this.CF.getOpacity());
            this.CI = true;
        }
        return this.CJ;
    }

    public void invalidateDrawable(Drawable who) {
        if (gr.fu()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    public Drawable mutate() {
        if (!this.CC && super.mutate() == this) {
            if (canConstantState()) {
                this.CE.mutate();
                this.CF.mutate();
                this.CC = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    protected void onBoundsChange(Rect bounds) {
        this.CE.setBounds(bounds);
        this.CF.setBounds(bounds);
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (gr.fu()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.scheduleDrawable(this, what, when);
            }
        }
    }

    public void setAlpha(int alpha) {
        if (this.CB == this.Cz) {
            this.CB = alpha;
        }
        this.Cz = alpha;
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter cf) {
        this.CE.setColorFilter(cf);
        this.CF.setColorFilter(cf);
    }

    public void startTransition(int durationMillis) {
        this.Cx = 0;
        this.Cy = this.Cz;
        this.CB = 0;
        this.CA = durationMillis;
        this.Cv = 1;
        invalidateSelf();
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (gr.fu()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.unscheduleDrawable(this, what);
            }
        }
    }
}
