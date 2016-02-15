/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.ActionBar
 *  android.app.ActionBar$OnNavigationListener
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 */
package com.uservoice.uservoicesdk.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.activity.ArticleActivity;
import com.uservoice.uservoicesdk.activity.BaseListActivity;
import com.uservoice.uservoicesdk.activity.ContactActivity;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.Topic;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.ui.PaginatedAdapter;
import com.uservoice.uservoicesdk.ui.PaginationScrollListener;
import java.util.ArrayList;
import java.util.List;

public class TopicActivity
extends BaseListActivity
implements SearchActivity {
    public PaginatedAdapter<Article> getModelAdapter() {
        return (PaginatedAdapter)this.getListAdapter();
    }

    @Override
    public void hideSearch() {
        super.hideSearch();
        this.getActionBar().setNavigationMode(1);
    }

    @SuppressLint(value={"InlinedApi", "NewApi"})
    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = (Topic)this.getIntent().getParcelableExtra("topic");
        if (this.hasActionBar()) {
            ActionBar actionBar = this.getActionBar();
            actionBar.setNavigationMode(1);
            actionBar.setListNavigationCallbacks((SpinnerAdapter)new ArrayAdapter(actionBar.getThemedContext(), 17367049, Session.getInstance().getTopics()), new ActionBar.OnNavigationListener(){

                public boolean onNavigationItemSelected(int n2, long l2) {
                    Topic topic = Session.getInstance().getTopics().get(n2);
                    TopicActivity.this.getIntent().putExtra("topic", (Parcelable)topic);
                    TopicActivity.this.getModelAdapter().reload();
                    return true;
                }
            });
            actionBar.setSelectedNavigationItem(Session.getInstance().getTopics().indexOf(object));
        }
        this.setTitle(null);
        this.getListView().setDivider(null);
        this.setListAdapter((ListAdapter)new PaginatedAdapter<Article>((Context)this, R.layout.uv_text_item, new ArrayList()){

            @Override
            protected void customizeLayout(View view, Article article) {
                Topic topic = (Topic)TopicActivity.this.getIntent().getParcelableExtra("topic");
                TextView textView = (TextView)view.findViewById(R.id.uv_text);
                view = (TextView)view.findViewById(R.id.uv_text2);
                textView.setText((CharSequence)article.getTitle());
                if (topic.getId() == -1 && article.getTopicName() != null) {
                    view.setVisibility(0);
                    view.setText((CharSequence)article.getTopicName());
                    return;
                }
                view.setVisibility(8);
            }

            @Override
            public int getTotalNumberOfObjects() {
                Topic topic = (Topic)TopicActivity.this.getIntent().getParcelableExtra("topic");
                if (topic.getId() == -1) {
                    return -1;
                }
                return topic.getNumberOfArticles();
            }

            @Override
            protected void loadPage(int n2, Callback<List<Article>> callback) {
                Topic topic = (Topic)TopicActivity.this.getIntent().getParcelableExtra("topic");
                if (topic.getId() == -1) {
                    Article.loadPage(n2, callback);
                    return;
                }
                Article.loadPageForTopic(topic.getId(), n2, callback);
            }
        });
        this.getListView().setOnScrollListener((AbsListView.OnScrollListener)new PaginationScrollListener(this.getModelAdapter()));
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> object, View view, int n2, long l2) {
                object = (Article)TopicActivity.this.getListAdapter().getItem(n2);
                view = new Intent((Context)TopicActivity.this, (Class)ArticleActivity.class);
                view.putExtra("article", (Parcelable)object);
                TopicActivity.this.startActivity((Intent)view);
            }
        });
        Babayaga.track(Babayaga.Event.VIEW_TOPIC, object.getId());
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(R.menu.uv_portal, menu2);
        this.setupScopedSearch(menu2);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int n2, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.uv_action_contact) {
            this.startActivity(new Intent((Context)this, (Class)ContactActivity.class));
            return true;
        }
        return super.onMenuItemSelected(n2, menuItem);
    }

}

