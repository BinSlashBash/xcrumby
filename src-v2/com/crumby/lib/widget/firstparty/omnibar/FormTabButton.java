/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageButton
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import com.crumby.lib.widget.firstparty.omnibar.TabManager;

public class FormTabButton
extends ImageButton {
    private View view;

    public FormTabButton(Context context) {
        super(context);
    }

    public FormTabButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FormTabButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void activate() {
        this.getBackground().setAlpha(255);
        this.getDrawable().setAlpha(255);
        this.view.setVisibility(0);
    }

    public void deactivate() {
        this.getBackground().setAlpha(0);
        this.getDrawable().setAlpha(200);
        if (this.view != null) {
            this.view.setVisibility(8);
        }
    }

    public void hide() {
        this.setVisibility(8);
        if (this.view != null) {
            this.view.setVisibility(8);
        }
    }

    public void initialize(TabManager tabManager) {
        this.setOnClickListener((View.OnClickListener)tabManager);
        this.setImageDrawable(this.getDrawable().mutate());
        this.setBackgroundDrawable(this.getBackground().mutate());
    }

    public void setView(View view) {
        this.view = view;
    }
}

