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

public abstract class PaginatedAdapter<T>
extends ModelAdapter<T> {
    private int page = 1;

    public PaginatedAdapter(Context context, int n2, List<T> list) {
        super(context, n2, list);
    }

    static /* synthetic */ int access$012(PaginatedAdapter paginatedAdapter, int n2) {
        paginatedAdapter.page = n2 = paginatedAdapter.page + n2;
        return n2;
    }

    @Override
    protected List<T> getObjects() {
        if (this.shouldShowSearchResults()) {
            return this.searchResults;
        }
        return this.objects;
    }

    protected abstract int getTotalNumberOfObjects();

    public void loadMore() {
        if (this.loading || this.searchActive || this.objects.size() == this.getTotalNumberOfObjects()) {
            return;
        }
        this.loading = true;
        this.notifyDataSetChanged();
        this.loadPage(this.page, new DefaultCallback<List<T>>(this.context){

            @Override
            public void onModel(List<T> list) {
                PaginatedAdapter.this.objects.addAll(list);
                PaginatedAdapter.access$012(PaginatedAdapter.this, 1);
                PaginatedAdapter.this.loading = false;
                PaginatedAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public void reload() {
        if (this.loading) {
            return;
        }
        this.page = 1;
        this.objects = new ArrayList<E>();
        this.loadMore();
    }

}

