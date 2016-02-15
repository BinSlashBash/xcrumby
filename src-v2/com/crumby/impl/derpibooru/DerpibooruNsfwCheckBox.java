/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package com.crumby.impl.derpibooru;

import android.content.Context;
import android.util.AttributeSet;
import com.crumby.impl.derpibooru.DerpibooruProducer;
import com.crumby.lib.widget.firstparty.form.FormCheckBox;

public class DerpibooruNsfwCheckBox
extends FormCheckBox {
    public DerpibooruNsfwCheckBox(Context context) {
        super(context);
    }

    public DerpibooruNsfwCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DerpibooruNsfwCheckBox(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        if (DerpibooruProducer.nsfwMode) {
            this.setFieldValue("1");
            return;
        }
        this.setFieldValue("0");
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setFieldValue("0");
    }
}

