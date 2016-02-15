/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.lib.ImageComment;

public class ImageCommentView
extends LinearLayout {
    public ImageCommentView(Context context) {
        super(context);
    }

    public ImageCommentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ImageCommentView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void fillWith(ImageComment imageComment) {
        ((TextView)this.findViewById(2131492921)).setText((CharSequence)imageComment.getUsername());
        ((TextView)this.findViewById(2131492922)).setText((CharSequence)imageComment.getMessage());
    }
}

