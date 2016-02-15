package com.uservoice.uservoicesdk.ui;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.rest.RestTask;
import java.util.ArrayList;
import java.util.List;

public class MixedSearchAdapter extends SearchAdapter<BaseModel> implements OnItemClickListener {
    protected static int LOADING;
    public static int SCOPE_ALL;
    public static int SCOPE_ARTICLES;
    public static int SCOPE_IDEAS;
    protected static int SEARCH_RESULT;
    protected final FragmentActivity context;
    protected LayoutInflater inflater;

    /* renamed from: com.uservoice.uservoicesdk.ui.MixedSearchAdapter.1 */
    class C12011 extends Callback<List<BaseModel>> {
        final /* synthetic */ Callback val$callback;
        final /* synthetic */ String val$query;

        C12011(String str, Callback callback) {
            this.val$query = str;
            this.val$callback = callback;
        }

        public void onModel(List<BaseModel> list) {
            List<Article> articles = new ArrayList();
            List<Suggestion> suggestions = new ArrayList();
            for (BaseModel model : list) {
                if (model instanceof Article) {
                    articles.add((Article) model);
                } else if (model instanceof Suggestion) {
                    suggestions.add((Suggestion) model);
                }
            }
            Babayaga.track(Event.SEARCH_ARTICLES, this.val$query, articles);
            Babayaga.track(Event.SEARCH_IDEAS, this.val$query, suggestions);
            this.val$callback.onModel(list);
        }

        public void onError(RestResult error) {
            this.val$callback.onError(error);
        }
    }

    static {
        SEARCH_RESULT = 0;
        LOADING = 1;
        SCOPE_ALL = 0;
        SCOPE_ARTICLES = 1;
        SCOPE_IDEAS = 2;
    }

    public MixedSearchAdapter(FragmentActivity context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public boolean isEnabled(int position) {
        return !this.loading;
    }

    public List<BaseModel> getScopedSearchResults() {
        if (this.scope == SCOPE_ALL) {
            return this.searchResults;
        }
        if (this.scope == SCOPE_ARTICLES) {
            List<BaseModel> articles = new ArrayList();
            for (BaseModel model : this.searchResults) {
                if (model instanceof Article) {
                    articles.add(model);
                }
            }
            return articles;
        } else if (this.scope != SCOPE_IDEAS) {
            return null;
        } else {
            List<BaseModel> ideas = new ArrayList();
            for (BaseModel model2 : this.searchResults) {
                if (model2 instanceof Suggestion) {
                    ideas.add(model2);
                }
            }
            return ideas;
        }
    }

    protected void searchResultsUpdated() {
        int articleResults = 0;
        int ideaResults = 0;
        for (BaseModel model : this.searchResults) {
            if (model instanceof Article) {
                articleResults++;
            } else {
                ideaResults++;
            }
        }
        ((SearchActivity) this.context).updateScopedSearch(this.searchResults.size(), articleResults, ideaResults);
    }

    public int getCount() {
        return this.loading ? 1 : getScopedSearchResults().size();
    }

    public Object getItem(int position) {
        return this.loading ? null : (BaseModel) getScopedSearchResults().get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getItemViewType(int position) {
        return this.loading ? LOADING : SEARCH_RESULT;
    }

    public int getViewTypeCount() {
        return 2;
    }

    protected RestTask search(String query, Callback<List<BaseModel>> callback) {
        this.currentQuery = query;
        return Article.loadInstantAnswers(query, new C12011(query, callback));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int type = getItemViewType(position);
        if (view == null) {
            if (type == SEARCH_RESULT) {
                view = this.inflater.inflate(C0621R.layout.uv_instant_answer_item, null);
            } else if (type == LOADING) {
                view = this.inflater.inflate(C0621R.layout.uv_loading_item, null);
            }
        }
        if (type == SEARCH_RESULT) {
            Utils.displayInstantAnswer(view, (BaseModel) getItem(position));
        }
        return view;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (getItemViewType(position) == SEARCH_RESULT) {
            Utils.showModel(this.context, (BaseModel) getItem(position));
        }
    }
}
