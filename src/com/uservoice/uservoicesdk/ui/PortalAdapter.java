package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.R.id;
import com.uservoice.uservoicesdk.R.layout;
import com.uservoice.uservoicesdk.R.plurals;
import com.uservoice.uservoicesdk.R.string;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.UserVoice;
import com.uservoice.uservoicesdk.activity.ContactActivity;
import com.uservoice.uservoicesdk.activity.ForumActivity;
import com.uservoice.uservoicesdk.activity.SearchActivity;
import com.uservoice.uservoicesdk.flow.InitManager;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.model.Forum;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.model.Topic;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PortalAdapter
  extends SearchAdapter<BaseModel>
  implements AdapterView.OnItemClickListener
{
  private static int ARTICLE = 5;
  private static int CONTACT;
  private static int FORUM;
  private static int KB_HEADER;
  private static int LOADING;
  private static int POWERED_BY = 6;
  public static int SCOPE_ALL = 0;
  public static int SCOPE_ARTICLES = 1;
  public static int SCOPE_IDEAS = 2;
  private static int TOPIC;
  private List<Article> articles;
  private boolean configLoaded = false;
  private final FragmentActivity context;
  private LayoutInflater inflater;
  private List<Integer> staticRows;
  
  static
  {
    KB_HEADER = 0;
    FORUM = 1;
    TOPIC = 2;
    LOADING = 3;
    CONTACT = 4;
  }
  
  public PortalAdapter(FragmentActivity paramFragmentActivity)
  {
    this.context = paramFragmentActivity;
    this.inflater = ((LayoutInflater)paramFragmentActivity.getSystemService("layout_inflater"));
    new InitManager(paramFragmentActivity, new Runnable()
    {
      public void run()
      {
        PortalAdapter.access$002(PortalAdapter.this, true);
        PortalAdapter.this.notifyDataSetChanged();
        PortalAdapter.this.loadForum();
        PortalAdapter.this.loadTopics();
      }
    }).init();
  }
  
  private void computeStaticRows()
  {
    if (this.staticRows == null)
    {
      this.staticRows = new ArrayList();
      Config localConfig = Session.getInstance().getConfig();
      if (localConfig.shouldShowContactUs()) {
        this.staticRows.add(Integer.valueOf(CONTACT));
      }
      if (localConfig.shouldShowForum()) {
        this.staticRows.add(Integer.valueOf(FORUM));
      }
      if (localConfig.shouldShowKnowledgeBase()) {
        this.staticRows.add(Integer.valueOf(KB_HEADER));
      }
    }
  }
  
  private List<Topic> getTopics()
  {
    return Session.getInstance().getTopics();
  }
  
  private void loadForum()
  {
    Forum.loadForum(Session.getInstance().getConfig().getForumId(), new DefaultCallback(this.context)
    {
      public void onModel(Forum paramAnonymousForum)
      {
        Session.getInstance().setForum(paramAnonymousForum);
        PortalAdapter.this.notifyDataSetChanged();
      }
    });
  }
  
  private void loadTopics()
  {
    final DefaultCallback local3 = new DefaultCallback(this.context)
    {
      public void onModel(List<Article> paramAnonymousList)
      {
        Session.getInstance().setTopics(new ArrayList());
        PortalAdapter.access$302(PortalAdapter.this, paramAnonymousList);
        PortalAdapter.this.notifyDataSetChanged();
      }
    };
    if (Session.getInstance().getConfig().getTopicId() != -1)
    {
      Article.loadPageForTopic(Session.getInstance().getConfig().getTopicId(), 1, local3);
      return;
    }
    Topic.loadTopics(new DefaultCallback(this.context)
    {
      public void onModel(List<Topic> paramAnonymousList)
      {
        if (paramAnonymousList.isEmpty())
        {
          Session.getInstance().setTopics(paramAnonymousList);
          Article.loadPage(1, local3);
          return;
        }
        paramAnonymousList = new ArrayList(paramAnonymousList);
        paramAnonymousList.add(Topic.ALL_ARTICLES);
        Session.getInstance().setTopics(paramAnonymousList);
        PortalAdapter.this.notifyDataSetChanged();
      }
    });
  }
  
  private boolean shouldShowArticles()
  {
    return (Session.getInstance().getConfig().getTopicId() != -1) || ((getTopics() != null) && (getTopics().isEmpty()));
  }
  
  public int getCount()
  {
    int j;
    if (!this.configLoaded) {
      j = 1;
    }
    do
    {
      return j;
      computeStaticRows();
      j = this.staticRows.size();
      i = j;
      if (Session.getInstance().getConfig().shouldShowKnowledgeBase())
      {
        if ((getTopics() != null) && ((!shouldShowArticles()) || (this.articles != null))) {
          break;
        }
        i = j + 1;
      }
      j = i;
    } while (Session.getInstance().getClientConfig().isWhiteLabel());
    return i + 1;
    if (shouldShowArticles()) {}
    for (int i = this.articles.size();; i = getTopics().size())
    {
      i = j + i;
      break;
    }
  }
  
  public Object getItem(int paramInt)
  {
    computeStaticRows();
    if ((paramInt < this.staticRows.size()) && (((Integer)this.staticRows.get(paramInt)).intValue() == FORUM)) {
      return Session.getInstance().getForum();
    }
    if ((getTopics() != null) && (!shouldShowArticles()) && (paramInt >= this.staticRows.size()) && (paramInt - this.staticRows.size() < getTopics().size())) {
      return getTopics().get(paramInt - this.staticRows.size());
    }
    if ((this.articles != null) && (shouldShowArticles()) && (paramInt >= this.staticRows.size()) && (paramInt - this.staticRows.size() < this.articles.size())) {
      return this.articles.get(paramInt - this.staticRows.size());
    }
    return null;
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public int getItemViewType(int paramInt)
  {
    if (!this.configLoaded) {
      paramInt = LOADING;
    }
    do
    {
      int i;
      do
      {
        return paramInt;
        computeStaticRows();
        if (paramInt >= this.staticRows.size()) {
          break;
        }
        i = ((Integer)this.staticRows.get(paramInt)).intValue();
        paramInt = i;
      } while (i != FORUM);
      paramInt = i;
    } while (Session.getInstance().getForum() != null);
    return LOADING;
    if (Session.getInstance().getConfig().shouldShowKnowledgeBase()) {
      if ((getTopics() == null) || ((shouldShowArticles()) && (this.articles == null)))
      {
        if (paramInt - this.staticRows.size() == 0) {
          return LOADING;
        }
      }
      else
      {
        if ((shouldShowArticles()) && (paramInt - this.staticRows.size() < this.articles.size())) {
          return ARTICLE;
        }
        if ((!shouldShowArticles()) && (paramInt - this.staticRows.size() < getTopics().size())) {
          return TOPIC;
        }
      }
    }
    return POWERED_BY;
  }
  
  public List<BaseModel> getScopedSearchResults()
  {
    Object localObject1;
    if (this.scope == SCOPE_ALL)
    {
      localObject1 = this.searchResults;
      return (List<BaseModel>)localObject1;
    }
    Object localObject2;
    Object localObject3;
    if (this.scope == SCOPE_ARTICLES)
    {
      localObject2 = new ArrayList();
      localObject3 = this.searchResults.iterator();
      for (;;)
      {
        localObject1 = localObject2;
        if (!((Iterator)localObject3).hasNext()) {
          break;
        }
        localObject1 = (BaseModel)((Iterator)localObject3).next();
        if ((localObject1 instanceof Article)) {
          ((List)localObject2).add(localObject1);
        }
      }
    }
    if (this.scope == SCOPE_IDEAS)
    {
      localObject1 = new ArrayList();
      localObject2 = this.searchResults.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (BaseModel)((Iterator)localObject2).next();
        if ((localObject3 instanceof Suggestion)) {
          ((List)localObject1).add(localObject3);
        }
      }
      return (List<BaseModel>)localObject1;
    }
    return null;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    paramViewGroup = paramView;
    int i = getItemViewType(paramInt);
    paramView = paramViewGroup;
    if (paramViewGroup == null)
    {
      if (i == LOADING) {
        paramView = this.inflater.inflate(R.layout.uv_loading_item, null);
      }
    }
    else
    {
      if (i != FORUM) {
        break label302;
      }
      ((TextView)paramView.findViewById(R.id.uv_text)).setText(R.string.uv_feedback_forum);
      paramViewGroup = (TextView)paramView.findViewById(R.id.uv_text2);
      paramViewGroup.setText(Utils.getQuantityString(paramViewGroup, R.plurals.uv_ideas, Session.getInstance().getForum().getNumberOfOpenSuggestions()));
      label90:
      paramViewGroup = paramView.findViewById(R.id.uv_divider);
      if (paramViewGroup != null) {
        if (((paramInt != getCount() - 2) || (getItemViewType(getCount() - 1) != POWERED_BY)) && (paramInt != getCount() - 1)) {
          break label579;
        }
      }
    }
    label302:
    label579:
    for (paramInt = 8;; paramInt = 0)
    {
      paramViewGroup.setVisibility(paramInt);
      if (i == FORUM) {
        paramViewGroup.setVisibility(8);
      }
      return paramView;
      if (i == FORUM)
      {
        paramView = this.inflater.inflate(R.layout.uv_text_item, null);
        break;
      }
      if (i == KB_HEADER)
      {
        paramView = this.inflater.inflate(R.layout.uv_header_item_light, null);
        break;
      }
      if (i == TOPIC)
      {
        paramView = this.inflater.inflate(R.layout.uv_text_item, null);
        break;
      }
      if (i == CONTACT)
      {
        paramView = this.inflater.inflate(R.layout.uv_text_item, null);
        break;
      }
      if (i == ARTICLE)
      {
        paramView = this.inflater.inflate(R.layout.uv_text_item, null);
        break;
      }
      paramView = paramViewGroup;
      if (i != POWERED_BY) {
        break;
      }
      paramView = this.inflater.inflate(R.layout.uv_powered_by_item, null);
      break;
      if (i == KB_HEADER)
      {
        ((TextView)paramView.findViewById(R.id.uv_header_text)).setText(R.string.uv_knowledge_base);
        break label90;
      }
      if (i == TOPIC)
      {
        paramViewGroup = (Topic)getItem(paramInt);
        ((TextView)paramView.findViewById(R.id.uv_text)).setText(paramViewGroup.getName());
        TextView localTextView = (TextView)paramView.findViewById(R.id.uv_text2);
        if (paramViewGroup == Topic.ALL_ARTICLES)
        {
          localTextView.setVisibility(8);
          break label90;
        }
        localTextView.setVisibility(0);
        localTextView.setText(String.format("%d %s", new Object[] { Integer.valueOf(paramViewGroup.getNumberOfArticles()), this.context.getResources().getQuantityString(R.plurals.uv_articles, paramViewGroup.getNumberOfArticles()) }));
        break label90;
      }
      if (i == CONTACT)
      {
        ((TextView)paramView.findViewById(R.id.uv_text)).setText(R.string.uv_contact_us);
        paramView.findViewById(R.id.uv_text2).setVisibility(8);
        break label90;
      }
      if (i == ARTICLE)
      {
        ((TextView)paramView.findViewById(R.id.uv_text)).setText(((Article)getItem(paramInt)).getTitle());
        break label90;
      }
      if (i != POWERED_BY) {
        break label90;
      }
      ((TextView)paramView.findViewById(R.id.uv_version)).setText(this.context.getString(R.string.uv_android_sdk) + " v" + UserVoice.getVersion());
      break label90;
    }
  }
  
  public int getViewTypeCount()
  {
    return 7;
  }
  
  public boolean isEnabled(int paramInt)
  {
    if (!this.configLoaded) {
      return false;
    }
    computeStaticRows();
    if (paramInt < this.staticRows.size())
    {
      paramInt = ((Integer)this.staticRows.get(paramInt)).intValue();
      if ((paramInt == KB_HEADER) || (paramInt == LOADING)) {
        return false;
      }
    }
    return true;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    int i = getItemViewType(paramInt);
    if (i == CONTACT) {
      this.context.startActivity(new Intent(this.context, ContactActivity.class));
    }
    do
    {
      return;
      if (i == FORUM)
      {
        this.context.startActivity(new Intent(this.context, ForumActivity.class));
        return;
      }
    } while ((i != TOPIC) && (i != ARTICLE));
    Utils.showModel(this.context, (BaseModel)getItem(paramInt));
  }
  
  protected void searchResultsUpdated()
  {
    int j = 0;
    int i = 0;
    Iterator localIterator = this.searchResults.iterator();
    while (localIterator.hasNext()) {
      if (((BaseModel)localIterator.next() instanceof Article)) {
        j += 1;
      } else {
        i += 1;
      }
    }
    ((SearchActivity)this.context).updateScopedSearch(this.searchResults.size(), j, i);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/ui/PortalAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */