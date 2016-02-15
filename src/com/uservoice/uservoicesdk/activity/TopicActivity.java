package com.uservoice.uservoicesdk.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.uservoice.uservoicesdk.R.id;
import com.uservoice.uservoicesdk.R.layout;
import com.uservoice.uservoicesdk.R.menu;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.Topic;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.ui.PaginatedAdapter;
import com.uservoice.uservoicesdk.ui.PaginationScrollListener;
import java.util.ArrayList;
import java.util.List;

public class TopicActivity
  extends BaseListActivity
  implements SearchActivity
{
  public PaginatedAdapter<Article> getModelAdapter()
  {
    return (PaginatedAdapter)getListAdapter();
  }
  
  public void hideSearch()
  {
    super.hideSearch();
    getActionBar().setNavigationMode(1);
  }
  
  @SuppressLint({"InlinedApi", "NewApi"})
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = (Topic)getIntent().getParcelableExtra("topic");
    if (hasActionBar())
    {
      ActionBar localActionBar = getActionBar();
      localActionBar.setNavigationMode(1);
      localActionBar.setListNavigationCallbacks(new ArrayAdapter(localActionBar.getThemedContext(), 17367049, Session.getInstance().getTopics()), new ActionBar.OnNavigationListener()
      {
        public boolean onNavigationItemSelected(int paramAnonymousInt, long paramAnonymousLong)
        {
          Topic localTopic = (Topic)Session.getInstance().getTopics().get(paramAnonymousInt);
          TopicActivity.this.getIntent().putExtra("topic", localTopic);
          TopicActivity.this.getModelAdapter().reload();
          return true;
        }
      });
      localActionBar.setSelectedNavigationItem(Session.getInstance().getTopics().indexOf(paramBundle));
    }
    setTitle(null);
    getListView().setDivider(null);
    setListAdapter(new PaginatedAdapter(this, R.layout.uv_text_item, new ArrayList())
    {
      protected void customizeLayout(View paramAnonymousView, Article paramAnonymousArticle)
      {
        Topic localTopic = (Topic)TopicActivity.this.getIntent().getParcelableExtra("topic");
        TextView localTextView = (TextView)paramAnonymousView.findViewById(R.id.uv_text);
        paramAnonymousView = (TextView)paramAnonymousView.findViewById(R.id.uv_text2);
        localTextView.setText(paramAnonymousArticle.getTitle());
        if ((localTopic.getId() == -1) && (paramAnonymousArticle.getTopicName() != null))
        {
          paramAnonymousView.setVisibility(0);
          paramAnonymousView.setText(paramAnonymousArticle.getTopicName());
          return;
        }
        paramAnonymousView.setVisibility(8);
      }
      
      public int getTotalNumberOfObjects()
      {
        Topic localTopic = (Topic)TopicActivity.this.getIntent().getParcelableExtra("topic");
        if (localTopic.getId() == -1) {
          return -1;
        }
        return localTopic.getNumberOfArticles();
      }
      
      protected void loadPage(int paramAnonymousInt, Callback<List<Article>> paramAnonymousCallback)
      {
        Topic localTopic = (Topic)TopicActivity.this.getIntent().getParcelableExtra("topic");
        if (localTopic.getId() == -1)
        {
          Article.loadPage(paramAnonymousInt, paramAnonymousCallback);
          return;
        }
        Article.loadPageForTopic(localTopic.getId(), paramAnonymousInt, paramAnonymousCallback);
      }
    });
    getListView().setOnScrollListener(new PaginationScrollListener(getModelAdapter()));
    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        paramAnonymousAdapterView = (Article)TopicActivity.this.getListAdapter().getItem(paramAnonymousInt);
        paramAnonymousView = new Intent(TopicActivity.this, ArticleActivity.class);
        paramAnonymousView.putExtra("article", paramAnonymousAdapterView);
        TopicActivity.this.startActivity(paramAnonymousView);
      }
    });
    Babayaga.track(Babayaga.Event.VIEW_TOPIC, paramBundle.getId());
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.uv_portal, paramMenu);
    setupScopedSearch(paramMenu);
    return true;
  }
  
  public boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.uv_action_contact)
    {
      startActivity(new Intent(this, ContactActivity.class));
      return true;
    }
    return super.onMenuItemSelected(paramInt, paramMenuItem);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/activity/TopicActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */