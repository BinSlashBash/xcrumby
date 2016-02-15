package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.rest.Callback;
import java.util.List;

public abstract class ModelAdapter<T> extends SearchAdapter<T> {
    protected static final int LOADING = 1;
    protected static final int MODEL = 0;
    protected int addedObjects;
    protected LayoutInflater inflater;
    protected final int layoutId;
    protected List<T> objects;

    protected abstract void customizeLayout(View view, T t);

    protected abstract void loadPage(int i, Callback<List<T>> callback);

    public ModelAdapter(Context context, int layoutId, List<T> objects) {
        this.addedObjects = 0;
        this.context = context;
        this.layoutId = layoutId;
        this.objects = objects;
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int type = getItemViewType(position);
        if (view == null) {
            view = this.inflater.inflate(type == LOADING ? C0621R.layout.uv_loading_item : this.layoutId, null);
        }
        if (type == 0) {
            customizeLayout(view, getItem(position));
        }
        return view;
    }

    public boolean isEnabled(int position) {
        return getItemViewType(position) == 0;
    }

    public int getItemViewType(int position) {
        return position == getObjects().size() ? LOADING : 0;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public int getCount() {
        return (this.loading ? LOADING : 0) + getObjects().size();
    }

    public Object getItem(int position) {
        return position < getObjects().size() ? getObjects().get(position) : null;
    }

    public long getItemId(int position) {
        return getItemViewType(position) == LOADING ? -1 : (long) position;
    }

    protected List<T> getObjects() {
        return this.objects;
    }

    public void add(int location, T object) {
        this.objects.add(location, object);
        this.addedObjects += LOADING;
        notifyDataSetChanged();
    }
}
