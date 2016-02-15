package com.crumby.lib.widget.firstparty.multiselect;

import android.widget.Checkable;

public interface ImageCheckable extends Checkable {
    Object getTag();

    void invalidate();
}
