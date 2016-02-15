package com.uservoice.uservoicesdk.ui;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.uservoice.uservoicesdk.R.layout;
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
import java.util.Iterator;
import java.util.List;

public class MixedSearchAdapter
  extends SearchAdapter<BaseModel>
  implements AdapterView.OnItemClickListener
{
  protected static int LOADING = 1;
  public static int SCOPE_ALL = 0;
  public static int SCOPE_ARTICLES = 1;
  public static int SCOPE_IDEAS = 2;
  protected static int SEARCH_RESULT = 0;
  protected final FragmentActivity context;
  protected LayoutInflater inflater;
  
  public MixedSearchAdapter(FragmentActivity paramFragmentActivity)
  {
    this.context = paramFragmentActivity;
    this.inflater = ((LayoutInflater)paramFragmentActivity.getSystemService("layout_inflater"));
  }
  
  public int getCount()
  {
    if (this.loading) {
      return 1;
    }
    return getScopedSearchResults().size();
  }
  
  public Object getItem(int paramInt)
  {
    if (this.loading) {
      return null;
    }
    return (BaseModel)getScopedSearchResults().get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return 0L;
  }
  
  public int getItemViewType(int paramInt)
  {
    if (this.loading) {
      return LOADING;
    }
    return SEARCH_RESULT;
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
      if (i != SEARCH_RESULT) {
        break label57;
      }
      paramView = this.inflater.inflate(R.layout.uv_instant_answer_item, null);
    }
    for (;;)
    {
      if (i == SEARCH_RESULT) {
        Utils.displayInstantAnswer(paramView, (BaseModel)getItem(paramInt));
      }
      return paramView;
      label57:
      paramView = paramViewGroup;
      if (i == LOADING) {
        paramView = this.inflater.inflate(R.layout.uv_loading_item, null);
      }
    }
  }
  
  public int getViewTypeCount()
  {
    return 2;
  }
  
  public boolean isEnabled(int paramInt)
  {
    return !this.loading;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (getItemViewType(paramInt) == SEARCH_RESULT) {
      Utils.showModel(this.context, (BaseModel)getItem(paramInt));
    }
  }
  
  protected RestTask search(final String paramString, final Callback<List<BaseModel>> paramCallback)
  {
    this.currentQuery = paramString;
    Article.loadInstantAnswers(paramString, new Callback()
    {
      public void onError(RestResult paramAnonymousRestResult)
      {
        paramCallback.onError(paramAnonymousRestResult);
      }
      
      public void onModel(List<BaseModel> paramAnonymousList)
      {
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        Iterator localIterator = paramAnonymousList.iterator();
        while (localIterator.hasNext())
        {
          BaseModel localBaseModel = (BaseModel)localIterator.next();
          if ((localBaseModel instanceof Article)) {
            localArrayList1.add((Article)localBaseModel);
          } else if ((localBaseModel instanceof Suggestion)) {
            localArrayList2.add((Suggestion)localBaseModel);
          }
        }
        Babayaga.track(Babayaga.Event.SEARCH_ARTICLES, paramString, localArrayList1);
        Babayaga.track(Babayaga.Event.SEARCH_IDEAS, paramString, localArrayList2);
        paramCallback.onModel(paramAnonymousList);
      }
    });
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


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/ui/MixedSearchAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */