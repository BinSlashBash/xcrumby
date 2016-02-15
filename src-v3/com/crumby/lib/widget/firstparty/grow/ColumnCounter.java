package com.crumby.lib.widget.firstparty.grow;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class ColumnCounter extends TextView {
    private int columnMax;
    private int columnNormal;
    private boolean outOfBounds;

    public ColumnCounter(Context context) {
        super(context);
        this.columnMax = getResources().getColor(C0065R.color.HoloBlue);
        this.columnNormal = getResources().getColor(C0065R.color.White);
    }

    public ColumnCounter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.columnMax = getResources().getColor(C0065R.color.HoloBlue);
        this.columnNormal = getResources().getColor(C0065R.color.White);
    }

    public ColumnCounter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.columnMax = getResources().getColor(C0065R.color.HoloBlue);
        this.columnNormal = getResources().getColor(C0065R.color.White);
    }

    public void set(int columns) {
        String columnString = UnsupportedUrlFragment.DISPLAY_NAME;
        for (int i = 0; i < columns; i++) {
            columnString = columnString + " \u25a0";
        }
        setText(columnString + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
    }

    public void show() {
        setVisibility(0);
    }

    public void hide() {
        setVisibility(8);
    }

    public void indicateLimit() {
        setPressed(true);
        setTextColor(this.columnMax);
        this.outOfBounds = true;
    }

    public void indicateFree() {
        setTextColor(this.columnNormal);
        setPressed(false);
        this.outOfBounds = false;
    }
}
