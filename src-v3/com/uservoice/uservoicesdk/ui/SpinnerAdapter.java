package com.uservoice.uservoicesdk.ui;

import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.uservoice.uservoicesdk.C0621R;
import java.util.List;

public class SpinnerAdapter<T> extends BaseAdapter {
    private static int NONE;
    private static int OBJECT;
    private int color;
    private LayoutInflater inflater;
    private final List<T> objects;

    static {
        NONE = 0;
        OBJECT = 1;
    }

    public SpinnerAdapter(Activity context, List<T> objects) {
        this.objects = objects;
        this.inflater = context.getLayoutInflater();
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(16842806, tv, true);
        this.color = context.getResources().getColor(tv.resourceId);
    }

    public int getCount() {
        return this.objects.size() + 1;
    }

    public Object getItem(int position) {
        if (position == 0) {
            return null;
        }
        return this.objects.get(position - 1);
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getItemViewType(int position) {
        return position == 0 ? NONE : OBJECT;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int type = getItemViewType(position);
        if (view == null) {
            view = this.inflater.inflate(17367043, null);
        }
        TextView textView = (TextView) view;
        if (type == OBJECT) {
            textView.setTextColor(this.color);
            textView.setText(getItem(position).toString());
        } else {
            textView.setTextColor(-7829368);
            textView.setText(C0621R.string.uv_select_none);
        }
        return view;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int type = getItemViewType(position);
        if (view == null) {
            view = this.inflater.inflate(17367043, null);
        }
        TextView textView = (TextView) view;
        if (type == OBJECT) {
            textView.setTextColor(this.color);
            textView.setText(getItem(position).toString());
        } else {
            textView.setTextColor(this.color);
            textView.setText(C0621R.string.uv_select_one);
        }
        return view;
    }
}
