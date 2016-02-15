package com.uservoice.uservoicesdk.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.TextView;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment;
import com.uservoice.uservoicesdk.flow.InitManager;
import com.uservoice.uservoicesdk.model.Forum;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.PaginatedAdapter;
import com.uservoice.uservoicesdk.ui.PaginationScrollListener;
import com.uservoice.uservoicesdk.ui.SearchAdapter;
import com.uservoice.uservoicesdk.ui.SearchExpandListener;
import com.uservoice.uservoicesdk.ui.SearchQueryListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ForumActivity extends BaseListActivity implements SearchActivity {
    private Forum forum;
    private List<Suggestion> suggestions;

    /* renamed from: com.uservoice.uservoicesdk.activity.ForumActivity.3 */
    class C06263 implements OnItemClickListener {
        C06263() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (position == 0) {
                ForumActivity.this.startActivity(new Intent(ForumActivity.this, PostIdeaActivity.class));
            } else if (position != 1) {
                new SuggestionDialogFragment((Suggestion) ForumActivity.this.getModelAdapter().getItem(position), null).show(ForumActivity.this.getSupportFragmentManager(), "SuggestionDialogFragment");
            }
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.activity.ForumActivity.4 */
    class C06284 implements Runnable {

        /* renamed from: com.uservoice.uservoicesdk.activity.ForumActivity.4.1 */
        class C06271 implements Runnable {
            C06271() {
            }

            public void run() {
                if (ForumActivity.this.forum != null) {
                    ForumActivity.this.getModelAdapter().reload();
                }
            }
        }

        C06284() {
        }

        public void run() {
            ForumActivity.this.loadForum();
            Session.getInstance().setSignInListener(new C06271());
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.activity.ForumActivity.2 */
    class C11702 extends PaginationScrollListener {
        C11702(PaginatedAdapter x0) {
            super(x0);
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (ForumActivity.this.forum != null) {
                super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.activity.ForumActivity.5 */
    class C14315 extends DefaultCallback<Forum> {
        C14315(Context x0) {
            super(x0);
        }

        public void onModel(Forum model) {
            Session.getInstance().setForum(model);
            ForumActivity.this.forum = model;
            ForumActivity.this.setTitle(ForumActivity.this.forum.getName());
            ForumActivity.this.getModelAdapter().loadMore();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.activity.ForumActivity.1 */
    class C15071 extends PaginatedAdapter<Suggestion> {
        boolean initializing;

        /* renamed from: com.uservoice.uservoicesdk.activity.ForumActivity.1.1 */
        class C11691 extends Callback<List<Suggestion>> {
            final /* synthetic */ Callback val$callback;
            final /* synthetic */ String val$query;

            C11691(String str, Callback callback) {
                this.val$query = str;
                this.val$callback = callback;
            }

            public void onModel(List<Suggestion> model) {
                Babayaga.track(Event.SEARCH_IDEAS, this.val$query, model);
                this.val$callback.onModel(model);
            }

            public void onError(RestResult error) {
                this.val$callback.onError(error);
            }
        }

        C15071(Context x0, int x1, List x2) {
            super(x0, x1, x2);
            this.initializing = true;
        }

        public void loadMore() {
            if (this.initializing) {
                notifyDataSetChanged();
            }
            this.initializing = false;
            super.loadMore();
        }

        public int getViewTypeCount() {
            return super.getViewTypeCount() + 2;
        }

        public boolean isEnabled(int position) {
            return getItemViewType(position) == 2 || super.isEnabled(position);
        }

        public int getCount() {
            return (this.initializing ? 1 : 0) + (super.getCount() + 2);
        }

        public int getItemViewType(int position) {
            if (position == 0) {
                return 2;
            }
            if (position == 1) {
                return 3;
            }
            if (position == 2 && this.initializing) {
                return 1;
            }
            return super.getItemViewType(position - 2);
        }

        public Object getItem(int position) {
            return super.getItem(position - 2);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            if (type != 2 && type != 3) {
                return super.getView(position, convertView, parent);
            }
            View view = convertView;
            if (view != null) {
                return view;
            }
            if (type == 2) {
                view = ForumActivity.this.getLayoutInflater().inflate(C0621R.layout.uv_text_item, null);
                ((TextView) view.findViewById(C0621R.id.uv_text)).setText(C0621R.string.uv_post_an_idea);
                view.findViewById(C0621R.id.uv_divider).setVisibility(8);
                view.findViewById(C0621R.id.uv_text2).setVisibility(8);
                return view;
            } else if (type != 3) {
                return view;
            } else {
                view = ForumActivity.this.getLayoutInflater().inflate(C0621R.layout.uv_header_item_light, null);
                ((TextView) view.findViewById(C0621R.id.uv_header_text)).setText(C0621R.string.uv_idea_text_heading);
                return view;
            }
        }

        protected void customizeLayout(View view, Suggestion model) {
            ((TextView) view.findViewById(C0621R.id.uv_suggestion_title)).setText(model.getTitle());
            TextView textView = (TextView) view.findViewById(C0621R.id.uv_subscriber_count);
            if (Session.getInstance().getClientConfig().shouldDisplaySuggestionsByRank()) {
                textView.setText(model.getRankString());
            } else {
                textView.setText(String.valueOf(model.getNumberOfSubscribers()));
            }
            textView = (TextView) view.findViewById(C0621R.id.uv_suggestion_status);
            View colorView = view.findViewById(C0621R.id.uv_suggestion_status_color);
            if (model.getStatus() == null) {
                textView.setVisibility(8);
                colorView.setVisibility(8);
                return;
            }
            int color = Color.parseColor(model.getStatusColor());
            textView.setVisibility(0);
            textView.setTextColor(color);
            textView.setText(model.getStatus().toUpperCase(Locale.getDefault()));
            colorView.setVisibility(0);
            colorView.setBackgroundColor(color);
        }

        public void loadPage(int page, Callback<List<Suggestion>> callback) {
            Suggestion.loadSuggestions(ForumActivity.this.forum, page, callback);
        }

        public RestTask search(String query, Callback<List<Suggestion>> callback) {
            if (ForumActivity.this.forum == null) {
                return null;
            }
            return Suggestion.searchSuggestions(ForumActivity.this.forum, query, new C11691(query, callback));
        }

        public int getTotalNumberOfObjects() {
            return ForumActivity.this.forum.getNumberOfOpenSuggestions();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(C0621R.string.uv_feedback_forum);
        this.suggestions = new ArrayList();
        getListView().setDivider(null);
        setListAdapter(new C15071(this, C0621R.layout.uv_suggestion_item, this.suggestions));
        getListView().setOnScrollListener(new C11702(getModelAdapter()));
        getListView().setOnItemClickListener(new C06263());
        new InitManager(this, new C06284()).init();
    }

    @SuppressLint({"NewApi"})
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0621R.menu.uv_forum, menu);
        if (hasActionBar()) {
            menu.findItem(C0621R.id.uv_menu_search).setOnActionExpandListener(new SearchExpandListener(this));
            ((SearchView) menu.findItem(C0621R.id.uv_menu_search).getActionView()).setOnQueryTextListener(new SearchQueryListener(this));
        } else {
            menu.findItem(C0621R.id.uv_menu_search).setVisible(false);
        }
        menu.findItem(C0621R.id.uv_new_idea).setVisible(Session.getInstance().getConfig().shouldShowPostIdea());
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != C0621R.id.uv_new_idea) {
            return super.onOptionsItemSelected(item);
        }
        startActivity(new Intent(this, PostIdeaActivity.class));
        return true;
    }

    protected void onStop() {
        Session.getInstance().setSignInListener(null);
        super.onStop();
    }

    private void loadForum() {
        if (Session.getInstance().getForum() != null) {
            this.forum = Session.getInstance().getForum();
            Babayaga.track(Event.VIEW_FORUM, this.forum.getId());
            setTitle(this.forum.getName());
            getModelAdapter().loadMore();
            return;
        }
        Forum.loadForum(Session.getInstance().getConfig().getForumId(), new C14315(this));
    }

    public SearchAdapter<?> getSearchAdapter() {
        return getModelAdapter();
    }

    public PaginatedAdapter<Suggestion> getModelAdapter() {
        return (PaginatedAdapter) getListAdapter();
    }

    public void showSearch() {
    }

    public void hideSearch() {
    }

    public void suggestionUpdated(Suggestion model) {
        getModelAdapter().notifyDataSetChanged();
    }
}
