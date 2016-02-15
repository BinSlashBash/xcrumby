/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.ui.SearchAdapter;
import java.util.List;

public abstract class ModelAdapter<T>
extends SearchAdapter<T> {
    protected static final int LOADING = 1;
    protected static final int MODEL = 0;
    protected int addedObjects = 0;
    protected LayoutInflater inflater;
    protected final int layoutId;
    protected List<T> objects;

    public ModelAdapter(Context context, int n2, List<T> list) {
        this.context = context;
        this.layoutId = n2;
        this.objects = list;
        this.inflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    public void add(int n2, T t2) {
        this.objects.add(n2, t2);
        ++this.addedObjects;
        this.notifyDataSetChanged();
    }

    protected abstract void customizeLayout(View var1, T var2);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getCount() {
        int n2;
        int n3 = this.getObjects().size();
        if (this.loading) {
            n2 = 1;
            do {
                return n2 + n3;
                break;
            } while (true);
        }
        n2 = 0;
        return n2 + n3;
    }

    public Object getItem(int n2) {
        if (n2 < this.getObjects().size()) {
            return this.getObjects().get(n2);
        }
        return null;
    }

    public long getItemId(int n2) {
        if (this.getItemViewType(n2) == 1) {
            return -1;
        }
        return n2;
    }

    public int getItemViewType(int n2) {
        if (n2 == this.getObjects().size()) {
            return 1;
        }
        return 0;
    }

    protected List<T> getObjects() {
        return this.objects;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n2, View view, ViewGroup viewGroup) {
        int n3 = this.getItemViewType(n2);
        viewGroup = view;
        if (view == null) {
            view = this.inflater;
            int n4 = n3 == 1 ? R.layout.uv_loading_item : this.layoutId;
            viewGroup = view.inflate(n4, null);
        }
        if (n3 == 0) {
            this.customizeLayout((View)viewGroup, this.getItem(n2));
        }
        return viewGroup;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public boolean isEnabled(int n2) {
        if (this.getItemViewType(n2) == 0) {
            return true;
        }
        return false;
    }

    protected abstract void loadPage(int var1, Callback<List<T>> var2);
}

