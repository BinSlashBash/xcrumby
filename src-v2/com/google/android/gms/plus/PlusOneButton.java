/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.FrameLayout
 */
package com.google.android.gms.plus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.ft;
import com.google.android.gms.plus.internal.g;

public final class PlusOneButton
extends FrameLayout {
    public static final int ANNOTATION_BUBBLE = 1;
    public static final int ANNOTATION_INLINE = 2;
    public static final int ANNOTATION_NONE = 0;
    public static final int DEFAULT_ACTIVITY_REQUEST_CODE = -1;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_SMALL = 0;
    public static final int SIZE_STANDARD = 3;
    public static final int SIZE_TALL = 2;
    private View TT;
    private int TU;
    private int TV;
    private OnPlusOneClickListener TW;
    private int mSize;
    private String ro;

    public PlusOneButton(Context context) {
        this(context, null);
    }

    public PlusOneButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSize = PlusOneButton.getSize(context, attributeSet);
        this.TU = PlusOneButton.getAnnotation(context, attributeSet);
        this.TV = -1;
        this.v(this.getContext());
        if (this.isInEditMode()) {
            // empty if block
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static int getAnnotation(Context object, AttributeSet attributeSet) {
        int n2 = 0;
        if ("INLINE".equalsIgnoreCase((String)(object = ft.a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "annotation", (Context)object, attributeSet, true, false, "PlusOneButton")))) {
            return 2;
        }
        if ("NONE".equalsIgnoreCase((String)object)) return n2;
        return 1;
    }

    protected static int getSize(Context object, AttributeSet attributeSet) {
        if ("SMALL".equalsIgnoreCase((String)(object = ft.a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "size", (Context)object, attributeSet, true, false, "PlusOneButton")))) {
            return 0;
        }
        if ("MEDIUM".equalsIgnoreCase((String)object)) {
            return 1;
        }
        if ("TALL".equalsIgnoreCase((String)object)) {
            return 2;
        }
        return 3;
    }

    private void v(Context context) {
        if (this.TT != null) {
            this.removeView(this.TT);
        }
        this.TT = g.a(context, this.mSize, this.TU, this.ro, this.TV);
        this.setOnPlusOneClickListener(this.TW);
        this.addView(this.TT);
    }

    public void initialize(String string2, int n2) {
        fq.a(this.getContext() instanceof Activity, "To use this method, the PlusOneButton must be placed in an Activity. Use initialize(String, OnPlusOneClickListener).");
        this.ro = string2;
        this.TV = n2;
        this.v(this.getContext());
    }

    public void initialize(String string2, OnPlusOneClickListener onPlusOneClickListener) {
        this.ro = string2;
        this.TV = 0;
        this.v(this.getContext());
        this.setOnPlusOneClickListener(onPlusOneClickListener);
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        this.TT.layout(0, 0, n4 - n2, n5 - n3);
    }

    protected void onMeasure(int n2, int n3) {
        View view = this.TT;
        this.measureChild(view, n2, n3);
        this.setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public void setAnnotation(int n2) {
        this.TU = n2;
        this.v(this.getContext());
    }

    public void setOnPlusOneClickListener(OnPlusOneClickListener onPlusOneClickListener) {
        this.TW = onPlusOneClickListener;
        this.TT.setOnClickListener((View.OnClickListener)new DefaultOnPlusOneClickListener(onPlusOneClickListener));
    }

    public void setSize(int n2) {
        this.mSize = n2;
        this.v(this.getContext());
    }

    protected class DefaultOnPlusOneClickListener
    implements View.OnClickListener,
    OnPlusOneClickListener {
        private final OnPlusOneClickListener TX;

        public DefaultOnPlusOneClickListener(OnPlusOneClickListener onPlusOneClickListener) {
            this.TX = onPlusOneClickListener;
        }

        public void onClick(View view) {
            view = (Intent)PlusOneButton.this.TT.getTag();
            if (this.TX != null) {
                this.TX.onPlusOneClick((Intent)view);
                return;
            }
            this.onPlusOneClick((Intent)view);
        }

        @Override
        public void onPlusOneClick(Intent intent) {
            Context context = PlusOneButton.this.getContext();
            if (context instanceof Activity && intent != null) {
                ((Activity)context).startActivityForResult(intent, PlusOneButton.this.TV);
            }
        }
    }

    public static interface OnPlusOneClickListener {
        public void onPlusOneClick(Intent var1);
    }

}

