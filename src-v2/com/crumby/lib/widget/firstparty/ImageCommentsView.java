/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.widget.LinearLayout
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import com.crumby.CrDb;
import com.crumby.lib.ImageComment;
import com.crumby.lib.widget.firstparty.ImageCommentView;
import java.util.List;

public class ImageCommentsView
extends LinearLayout {
    private LinearLayout commentContainer;
    private List<ImageComment> comments;
    int index;
    private View loading;
    private View title;

    public ImageCommentsView(Context context) {
        super(context);
    }

    public ImageCommentsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ImageCommentsView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void addComments(boolean bl2) {
        CrDb.logTime("imgur image", "comments", true);
        if (this.comments == null) {
            return;
        }
        this.title.setVisibility(0);
        this.loading.setVisibility(8);
        int n2 = this.getResources().getDisplayMetrics().heightPixels;
        this.getResources().getDimension(2131165195);
        if (!bl2) {
            n2 = 1;
        }
        while (n2 > 0 && this.index < this.comments.size() && this.index < this.commentContainer.getChildCount()) {
            ImageCommentView imageCommentView = (ImageCommentView)this.commentContainer.getChildAt(this.index);
            imageCommentView.setVisibility(0);
            imageCommentView.fillWith(this.comments.get(this.index));
            ++this.index;
            n2 -= imageCommentView.getMeasuredHeight();
        }
        CrDb.logTime("imgur image", "comments", false);
    }

    public void initialize(List<ImageComment> list) {
        if (list == null || list.isEmpty()) {
            this.setVisibility(8);
            return;
        }
        this.comments = list;
        this.addComments(true);
    }

    protected void onFinishInflate() {
        this.title = this.findViewById(2131493023);
        this.loading = this.findViewById(2131493022);
        this.commentContainer = (LinearLayout)this.findViewById(2131493024);
    }
}

