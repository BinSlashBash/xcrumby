package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GalleryViewerTab extends LinearLayout {
    int taskId;
    String url;

    public GalleryViewerTab(Context context, int taskId, String url) {
        super(context);
        setMinimumHeight(150);
        setMinimumWidth(100);
        TextView textView = new TextView(context);
        textView.setText(url);
        addView(textView);
        setClickable(true);
        this.taskId = taskId;
    }

    public int getTaskId() {
        return this.taskId;
    }
}
