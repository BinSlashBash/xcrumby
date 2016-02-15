package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedAdapter<T> extends ModelAdapter<T> {
    private int page;

    /* renamed from: com.uservoice.uservoicesdk.ui.PaginatedAdapter.1 */
    class C14521 extends DefaultCallback<List<T>> {
        C14521(Context x0) {
            super(x0);
        }

        public void onModel(List<T> model) {
            PaginatedAdapter.this.objects.addAll(model);
            PaginatedAdapter.access$012(PaginatedAdapter.this, 1);
            PaginatedAdapter.this.loading = false;
            PaginatedAdapter.this.notifyDataSetChanged();
        }
    }

    protected abstract int getTotalNumberOfObjects();

    static /* synthetic */ int access$012(PaginatedAdapter x0, int x1) {
        int i = x0.page + x1;
        x0.page = i;
        return i;
    }

    public PaginatedAdapter(Context context, int layoutId, List<T> objects) {
        super(context, layoutId, objects);
        this.page = 1;
    }

    public void loadMore() {
        if (!this.loading && !this.searchActive && this.objects.size() != getTotalNumberOfObjects()) {
            this.loading = true;
            notifyDataSetChanged();
            loadPage(this.page, new C14521(this.context));
        }
    }

    protected List<T> getObjects() {
        return shouldShowSearchResults() ? this.searchResults : this.objects;
    }

    public void reload() {
        if (!this.loading) {
            this.page = 1;
            this.objects = new ArrayList();
            loadMore();
        }
    }
}
