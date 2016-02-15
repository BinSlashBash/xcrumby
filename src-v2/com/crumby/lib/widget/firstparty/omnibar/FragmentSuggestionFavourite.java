/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.widget.ImageButton
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class FragmentSuggestionFavourite
extends ImageButton {
    private boolean checked;

    public FragmentSuggestionFavourite(Context context) {
        super(context);
    }

    public FragmentSuggestionFavourite(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FragmentSuggestionFavourite(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean bl2) {
        this.checked = bl2;
        if (bl2) {
            this.setImageDrawable(this.getResources().getDrawable(2130837627));
            return;
        }
        this.setImageDrawable(this.getResources().getDrawable(2130837630));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void toggle() {
        boolean bl2 = !this.checked;
        this.setChecked(bl2);
    }
}

