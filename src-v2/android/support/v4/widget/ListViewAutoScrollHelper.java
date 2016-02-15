/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.ListView
 */
package android.support.v4.widget;

import android.support.v4.widget.AutoScrollHelper;
import android.view.View;
import android.widget.ListView;

public class ListViewAutoScrollHelper
extends AutoScrollHelper {
    private final ListView mTarget;

    public ListViewAutoScrollHelper(ListView listView) {
        super((View)listView);
        this.mTarget = listView;
    }

    @Override
    public boolean canTargetScrollHorizontally(int n2) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean canTargetScrollVertically(int n2) {
        ListView listView = this.mTarget;
        int n3 = listView.getCount();
        int n4 = listView.getChildCount();
        int n5 = listView.getFirstVisiblePosition();
        if (n2 > 0 ? n5 + n4 >= n3 && listView.getChildAt(n4 - 1).getBottom() <= listView.getHeight() : n2 >= 0 || n5 <= 0 && listView.getChildAt(0).getTop() >= 0) {
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void scrollTargetBy(int n2, int n3) {
        View view;
        ListView listView = this.mTarget;
        n2 = listView.getFirstVisiblePosition();
        if (n2 == -1 || (view = listView.getChildAt(0)) == null) {
            return;
        }
        listView.setSelectionFromTop(n2, view.getTop() - n3);
    }
}

