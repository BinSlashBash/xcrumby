package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.lib.ImageComment;
import java.util.List;

public class ImageCommentsView extends LinearLayout {
    private LinearLayout commentContainer;
    private List<ImageComment> comments;
    int index;
    private View loading;
    private View title;

    public ImageCommentsView(Context context) {
        super(context);
    }

    public ImageCommentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageCommentsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(List<ImageComment> imageComments) {
        if (imageComments == null || imageComments.isEmpty()) {
            setVisibility(8);
            return;
        }
        this.comments = imageComments;
        addComments(true);
    }

    public void addComments(boolean fullScreen) {
        CrDb.logTime("imgur image", "comments", true);
        if (this.comments != null) {
            this.title.setVisibility(0);
            this.loading.setVisibility(8);
            int newHeight = getResources().getDisplayMetrics().heightPixels;
            float margin = getResources().getDimension(C0065R.dimen.imgur_comment_margin_bottom);
            if (!fullScreen) {
                newHeight = 1;
            }
            while (newHeight > 0 && this.index < this.comments.size() && this.index < this.commentContainer.getChildCount()) {
                ImageCommentView view = (ImageCommentView) this.commentContainer.getChildAt(this.index);
                view.setVisibility(0);
                view.fillWith((ImageComment) this.comments.get(this.index));
                this.index++;
                newHeight -= view.getMeasuredHeight();
            }
            CrDb.logTime("imgur image", "comments", false);
        }
    }

    protected void onFinishInflate() {
        this.title = findViewById(C0065R.id.comments_title);
        this.loading = findViewById(C0065R.id.loading_comments);
        this.commentContainer = (LinearLayout) findViewById(C0065R.id.comments_container);
    }
}
