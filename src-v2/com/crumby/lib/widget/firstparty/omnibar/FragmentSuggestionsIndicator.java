/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 *  android.widget.LinearLayout
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;

public class FragmentSuggestionsIndicator
extends LinearLayout {
    private View introText;
    private View loading;
    private View notFound;

    public FragmentSuggestionsIndicator(Context context) {
        super(context);
    }

    public FragmentSuggestionsIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FragmentSuggestionsIndicator(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void hide() {
        this.notFound.setVisibility(8);
        this.loading.setVisibility(8);
        this.clearAnimation();
    }

    public void hideIntroText() {
        this.introText.setVisibility(8);
    }

    public void indicateNotFound() {
        this.clearAnimation();
        this.loading.setVisibility(8);
        this.notFound.setVisibility(0);
        this.introText.setVisibility(8);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.loading = this.findViewById(2131493076);
        this.notFound = this.findViewById(2131493077);
        this.introText = this.findViewById(2131493078);
        this.introText.setVisibility(0);
    }

    public void show() {
        this.setVisibility(0);
        this.loading.setVisibility(0);
        this.notFound.setVisibility(8);
        this.setAlpha(0.0f);
        this.animate().alpha(1.0f).setStartDelay(500);
    }

    public void showIntroText() {
        this.introText.setVisibility(0);
        this.hide();
    }
}

