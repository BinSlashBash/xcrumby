package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;

public final class ch extends FrameLayout implements OnClickListener {
    private final Activity nS;
    private final ImageButton oB;

    public ch(Activity activity, int i) {
        super(activity);
        this.nS = activity;
        setOnClickListener(this);
        this.oB = new ImageButton(activity);
        this.oB.setImageResource(17301527);
        this.oB.setBackgroundColor(0);
        this.oB.setOnClickListener(this);
        this.oB.setPadding(0, 0, 0, 0);
        int a = dv.m808a((Context) activity, i);
        addView(this.oB, new LayoutParams(a, a, 17));
    }

    public void m695i(boolean z) {
        this.oB.setVisibility(z ? 4 : 0);
    }

    public void onClick(View view) {
        this.nS.finish();
    }
}
