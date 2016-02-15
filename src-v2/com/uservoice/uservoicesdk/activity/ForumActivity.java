/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Color
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.SearchView
 *  android.widget.SearchView$OnQueryTextListener
 *  android.widget.TextView
 */
package com.uservoice.uservoicesdk.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.activity.BaseListActivity;
import com.uservoice.uservoicesdk.activity.PostIdeaActivity;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment;
import com.uservoice.uservoicesdk.flow.InitManager;
import com.uservoice.uservoicesdk.model.ClientConfig;
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

public class ForumActivity
extends BaseListActivity
implements SearchActivity {
    private Forum forum;
    private List<Suggestion> suggestions;

    private void loadForum() {
        if (Session.getInstance().getForum() != null) {
            this.forum = Session.getInstance().getForum();
            Babayaga.track(Babayaga.Event.VIEW_FORUM, this.forum.getId());
            this.setTitle((CharSequence)this.forum.getName());
            this.getModelAdapter().loadMore();
            return;
        }
        Forum.loadForum(Session.getInstance().getConfig().getForumId(), new DefaultCallback<Forum>((Context)this){

            @Override
            public void onModel(Forum forum) {
                Session.getInstance().setForum(forum);
                ForumActivity.this.forum = forum;
                ForumActivity.this.setTitle((CharSequence)ForumActivity.this.forum.getName());
                ForumActivity.this.getModelAdapter().loadMore();
            }
        });
    }

    public PaginatedAdapter<Suggestion> getModelAdapter() {
        return (PaginatedAdapter)this.getListAdapter();
    }

    @Override
    public SearchAdapter<?> getSearchAdapter() {
        return this.getModelAdapter();
    }

    @Override
    public void hideSearch() {
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(R.string.uv_feedback_forum);
        this.suggestions = new ArrayList<Suggestion>();
        this.getListView().setDivider(null);
        this.setListAdapter((ListAdapter)new PaginatedAdapter<Suggestion>((Context)this, R.layout.uv_suggestion_item, this.suggestions){
            boolean initializing;

            /*
             * Enabled aggressive block sorting
             */
            @Override
            protected void customizeLayout(View view, Suggestion suggestion) {
                ((TextView)view.findViewById(R.id.uv_suggestion_title)).setText((CharSequence)suggestion.getTitle());
                TextView textView = (TextView)view.findViewById(R.id.uv_subscriber_count);
                if (Session.getInstance().getClientConfig().shouldDisplaySuggestionsByRank()) {
                    textView.setText((CharSequence)suggestion.getRankString());
                } else {
                    textView.setText((CharSequence)String.valueOf(suggestion.getNumberOfSubscribers()));
                }
                textView = (TextView)view.findViewById(R.id.uv_suggestion_status);
                view = view.findViewById(R.id.uv_suggestion_status_color);
                if (suggestion.getStatus() == null) {
                    textView.setVisibility(8);
                    view.setVisibility(8);
                    return;
                }
                int n2 = Color.parseColor((String)suggestion.getStatusColor());
                textView.setVisibility(0);
                textView.setTextColor(n2);
                textView.setText((CharSequence)suggestion.getStatus().toUpperCase(Locale.getDefault()));
                view.setVisibility(0);
                view.setBackgroundColor(n2);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public int getCount() {
                int n2;
                int n3 = super.getCount();
                if (this.initializing) {
                    n2 = 1;
                    do {
                        return n2 + (n3 + 2);
                        break;
                    } while (true);
                }
                n2 = 0;
                return n2 + (n3 + 2);
            }

            @Override
            public Object getItem(int n2) {
                return super.getItem(n2 - 2);
            }

            @Override
            public int getItemViewType(int n2) {
                if (n2 == 0) {
                    return 2;
                }
                if (n2 == 1) {
                    return 3;
                }
                if (n2 == 2 && this.initializing) {
                    return 1;
                }
                return super.getItemViewType(n2 - 2);
            }

            @Override
            public int getTotalNumberOfObjects() {
                return ForumActivity.this.forum.getNumberOfOpenSuggestions();
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public View getView(int n2, View view, ViewGroup viewGroup) {
                int n3 = this.getItemViewType(n2);
                if (n3 != 2 && n3 != 3) {
                    return super.getView(n2, view, viewGroup);
                }
                viewGroup = view;
                if (view != null) return viewGroup;
                {
                    if (n3 == 2) {
                        viewGroup = ForumActivity.this.getLayoutInflater().inflate(R.layout.uv_text_item, null);
                        ((TextView)viewGroup.findViewById(R.id.uv_text)).setText(R.string.uv_post_an_idea);
                        viewGroup.findViewById(R.id.uv_divider).setVisibility(8);
                        viewGroup.findViewById(R.id.uv_text2).setVisibility(8);
                        return viewGroup;
                    } else {
                        viewGroup = view;
                        if (n3 != 3) return viewGroup;
                        {
                            view = ForumActivity.this.getLayoutInflater().inflate(R.layout.uv_header_item_light, null);
                            ((TextView)view.findViewById(R.id.uv_header_text)).setText(R.string.uv_idea_text_heading);
                            return view;
                        }
                    }
                }
            }

            @Override
            public int getViewTypeCount() {
                return super.getViewTypeCount() + 2;
            }

            @Override
            public boolean isEnabled(int n2) {
                if (this.getItemViewType(n2) == 2 || super.isEnabled(n2)) {
                    return true;
                }
                return false;
            }

            @Override
            public void loadMore() {
                if (this.initializing) {
                    this.notifyDataSetChanged();
                }
                this.initializing = false;
                super.loadMore();
            }

            @Override
            public void loadPage(int n2, Callback<List<Suggestion>> callback) {
                Suggestion.loadSuggestions(ForumActivity.this.forum, n2, callback);
            }

            @Override
            public RestTask search(final String string2, final Callback<List<Suggestion>> callback) {
                if (ForumActivity.this.forum == null) {
                    return null;
                }
                return Suggestion.searchSuggestions(ForumActivity.this.forum, string2, new Callback<List<Suggestion>>(){

                    @Override
                    public void onError(RestResult restResult) {
                        callback.onError(restResult);
                    }

                    @Override
                    public void onModel(List<Suggestion> list) {
                        Babayaga.track(Babayaga.Event.SEARCH_IDEAS, string2, list);
                        callback.onModel(list);
                    }
                });
            }

        });
        this.getListView().setOnScrollListener((AbsListView.OnScrollListener)new PaginationScrollListener(this.getModelAdapter()){

            @Override
            public void onScroll(AbsListView absListView, int n2, int n3, int n4) {
                if (ForumActivity.this.forum != null) {
                    super.onScroll(absListView, n2, n3, n4);
                }
            }
        });
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
                if (n2 == 0) {
                    ForumActivity.this.startActivity(new Intent((Context)ForumActivity.this, (Class)PostIdeaActivity.class));
                    return;
                } else {
                    if (n2 == 1) return;
                    {
                        new SuggestionDialogFragment((Suggestion)ForumActivity.this.getModelAdapter().getItem(n2), null).show(ForumActivity.this.getSupportFragmentManager(), "SuggestionDialogFragment");
                        return;
                    }
                }
            }
        });
        new InitManager((Context)this, new Runnable(){

            @Override
            public void run() {
                ForumActivity.this.loadForum();
                Session.getInstance().setSignInListener(new Runnable(){

                    @Override
                    public void run() {
                        if (ForumActivity.this.forum != null) {
                            ForumActivity.this.getModelAdapter().reload();
                        }
                    }
                });
            }

        }).init();
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"NewApi"})
    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(R.menu.uv_forum, menu2);
        if (this.hasActionBar()) {
            menu2.findItem(R.id.uv_menu_search).setOnActionExpandListener((MenuItem.OnActionExpandListener)new SearchExpandListener(this));
            ((SearchView)menu2.findItem(R.id.uv_menu_search).getActionView()).setOnQueryTextListener((SearchView.OnQueryTextListener)new SearchQueryListener(this));
        } else {
            menu2.findItem(R.id.uv_menu_search).setVisible(false);
        }
        menu2.findItem(R.id.uv_new_idea).setVisible(Session.getInstance().getConfig().shouldShowPostIdea());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.uv_new_idea) {
            this.startActivity(new Intent((Context)this, (Class)PostIdeaActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onStop() {
        Session.getInstance().setSignInListener(null);
        super.onStop();
    }

    @Override
    public void showSearch() {
    }

    public void suggestionUpdated(Suggestion suggestion) {
        this.getModelAdapter().notifyDataSetChanged();
    }

}

