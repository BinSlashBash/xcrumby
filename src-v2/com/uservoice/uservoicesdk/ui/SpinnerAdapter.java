/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.util.TypedValue
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 */
package com.uservoice.uservoicesdk.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.uservoice.uservoicesdk.R;
import java.util.List;

public class SpinnerAdapter<T>
extends BaseAdapter {
    private static int NONE = 0;
    private static int OBJECT = 1;
    private int color;
    private LayoutInflater inflater;
    private final List<T> objects;

    public SpinnerAdapter(Activity activity, List<T> typedValue) {
        this.objects = typedValue;
        this.inflater = activity.getLayoutInflater();
        typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(16842806, typedValue, true);
        this.color = activity.getResources().getColor(typedValue.resourceId);
    }

    public int getCount() {
        return this.objects.size() + 1;
    }

    public View getDropDownView(int n2, View view, ViewGroup viewGroup) {
        viewGroup = view;
        int n3 = this.getItemViewType(n2);
        view = viewGroup;
        if (viewGroup == null) {
            view = this.inflater.inflate(17367043, null);
        }
        viewGroup = (TextView)view;
        if (n3 == OBJECT) {
            viewGroup.setTextColor(this.color);
            viewGroup.setText((CharSequence)this.getItem(n2).toString());
            return view;
        }
        viewGroup.setTextColor(-7829368);
        viewGroup.setText(R.string.uv_select_none);
        return view;
    }

    public Object getItem(int n2) {
        if (n2 == 0) {
            return null;
        }
        return this.objects.get(n2 - 1);
    }

    public long getItemId(int n2) {
        return 0;
    }

    public int getItemViewType(int n2) {
        if (n2 == 0) {
            return NONE;
        }
        return OBJECT;
    }

    public View getView(int n2, View view, ViewGroup viewGroup) {
        viewGroup = view;
        int n3 = this.getItemViewType(n2);
        view = viewGroup;
        if (viewGroup == null) {
            view = this.inflater.inflate(17367043, null);
        }
        viewGroup = (TextView)view;
        if (n3 == OBJECT) {
            viewGroup.setTextColor(this.color);
            viewGroup.setText((CharSequence)this.getItem(n2).toString());
            return view;
        }
        viewGroup.setTextColor(this.color);
        viewGroup.setText(R.string.uv_select_one);
        return view;
    }
}

