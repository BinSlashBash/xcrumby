/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.ModelAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class LoadAllAdapter<T>
extends ModelAdapter<T> {
    public LoadAllAdapter(Context context, int n2, List<T> list) {
        super(context, n2, list);
        this.loadAll();
    }

    private void loadAll() {
        this.loading = true;
        this.notifyDataSetChanged();
        this.loadPage(1, new DefaultCallback<List<T>>(this.context){

            @Override
            public void onModel(List<T> list) {
                LoadAllAdapter.this.objects.addAll(list);
                LoadAllAdapter.this.loading = false;
                LoadAllAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public void reload() {
        if (this.loading) {
            return;
        }
        this.objects = new ArrayList();
        this.loadAll();
    }

}

