package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public abstract class LoadAllAdapter<T> extends ModelAdapter<T> {

    /* renamed from: com.uservoice.uservoicesdk.ui.LoadAllAdapter.1 */
    class C14511 extends DefaultCallback<List<T>> {
        C14511(Context x0) {
            super(x0);
        }

        public void onModel(List<T> model) {
            LoadAllAdapter.this.objects.addAll(model);
            LoadAllAdapter.this.loading = false;
            LoadAllAdapter.this.notifyDataSetChanged();
        }
    }

    public LoadAllAdapter(Context context, int layoutId, List<T> objects) {
        super(context, layoutId, objects);
        loadAll();
    }

    private void loadAll() {
        this.loading = true;
        notifyDataSetChanged();
        loadPage(1, new C14511(this.context));
    }

    public void reload() {
        if (!this.loading) {
            this.objects = new ArrayList();
            loadAll();
        }
    }
}
