/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package com.uservoice.uservoicesdk.ui;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.ui.SearchAdapter;
import com.uservoice.uservoicesdk.ui.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MixedSearchAdapter
extends SearchAdapter<BaseModel>
implements AdapterView.OnItemClickListener {
    protected static int LOADING;
    public static int SCOPE_ALL;
    public static int SCOPE_ARTICLES;
    public static int SCOPE_IDEAS;
    protected static int SEARCH_RESULT;
    protected final FragmentActivity context;
    protected LayoutInflater inflater;

    static {
        SEARCH_RESULT = 0;
        LOADING = 1;
        SCOPE_ALL = 0;
        SCOPE_ARTICLES = 1;
        SCOPE_IDEAS = 2;
    }

    public MixedSearchAdapter(FragmentActivity fragmentActivity) {
        this.context = fragmentActivity;
        this.inflater = (LayoutInflater)fragmentActivity.getSystemService("layout_inflater");
    }

    public int getCount() {
        if (this.loading) {
            return 1;
        }
        return this.getScopedSearchResults().size();
    }

    public Object getItem(int n2) {
        if (this.loading) {
            return null;
        }
        return this.getScopedSearchResults().get(n2);
    }

    public long getItemId(int n2) {
        return 0;
    }

    public int getItemViewType(int n2) {
        if (this.loading) {
            return LOADING;
        }
        return SEARCH_RESULT;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<BaseModel> getScopedSearchResults() {
        if (this.scope == SCOPE_ALL) {
            return this.searchResults;
        }
        if (this.scope == SCOPE_ARTICLES) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            Iterator iterator = this.searchResults.iterator();
            do {
                Object object = arrayList;
                if (!iterator.hasNext()) return object;
                object = (BaseModel)iterator.next();
                if (!(object instanceof Article)) continue;
                arrayList.add(object);
            } while (true);
        }
        if (this.scope != SCOPE_IDEAS) return null;
        ArrayList<BaseModel> arrayList = new ArrayList<BaseModel>();
        Iterator iterator = this.searchResults.iterator();
        while (iterator.hasNext()) {
            BaseModel baseModel = (BaseModel)iterator.next();
            if (!(baseModel instanceof Suggestion)) continue;
            arrayList.add(baseModel);
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n2, View view, ViewGroup viewGroup) {
        viewGroup = view;
        int n3 = this.getItemViewType(n2);
        view = viewGroup;
        if (viewGroup == null) {
            if (n3 == SEARCH_RESULT) {
                view = this.inflater.inflate(R.layout.uv_instant_answer_item, null);
            } else {
                view = viewGroup;
                if (n3 == LOADING) {
                    view = this.inflater.inflate(R.layout.uv_loading_item, null);
                }
            }
        }
        if (n3 == SEARCH_RESULT) {
            Utils.displayInstantAnswer(view, (BaseModel)this.getItem(n2));
        }
        return view;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public boolean isEnabled(int n2) {
        if (!this.loading) {
            return true;
        }
        return false;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
        if (this.getItemViewType(n2) == SEARCH_RESULT) {
            Utils.showModel(this.context, (BaseModel)this.getItem(n2));
        }
    }

    @Override
    protected RestTask search(final String string2, final Callback<List<BaseModel>> callback) {
        this.currentQuery = string2;
        return Article.loadInstantAnswers(string2, new Callback<List<BaseModel>>(){

            @Override
            public void onError(RestResult restResult) {
                callback.onError(restResult);
            }

            @Override
            public void onModel(List<BaseModel> list) {
                ArrayList<Article> arrayList = new ArrayList<Article>();
                ArrayList<Suggestion> arrayList2 = new ArrayList<Suggestion>();
                for (BaseModel baseModel : list) {
                    if (baseModel instanceof Article) {
                        arrayList.add((Article)baseModel);
                        continue;
                    }
                    if (!(baseModel instanceof Suggestion)) continue;
                    arrayList2.add((Suggestion)baseModel);
                }
                Babayaga.track(Babayaga.Event.SEARCH_ARTICLES, string2, arrayList);
                Babayaga.track(Babayaga.Event.SEARCH_IDEAS, string2, arrayList2);
                callback.onModel(list);
            }
        });
    }

    @Override
    protected void searchResultsUpdated() {
        int n2 = 0;
        int n3 = 0;
        Iterator iterator = this.searchResults.iterator();
        while (iterator.hasNext()) {
            if ((BaseModel)iterator.next() instanceof Article) {
                ++n2;
                continue;
            }
            ++n3;
        }
        ((SearchActivity)((Object)this.context)).updateScopedSearch(this.searchResults.size(), n2, n3);
    }

}

