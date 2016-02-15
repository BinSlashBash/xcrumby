package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.C0065R;
import com.crumby.lib.ImageComment;

public class ImageCommentView extends LinearLayout {
    public ImageCommentView(Context context) {
        super(context);
    }

    public ImageCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageCommentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void fillWith(ImageComment comment) {
        ((TextView) findViewById(C0065R.id.comment_username)).setText(comment.getUsername());
        ((TextView) findViewById(C0065R.id.comment_message)).setText(comment.getMessage());
    }
}
