package com.crumby.lib.widget.thirdparty.grid;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import java.util.ArrayList;
import java.util.Iterator;

public class HeaderViewListAdapter
  implements WrapperListAdapter, Filterable
{
  static final ArrayList<ExtendableListView.FixedViewInfo> EMPTY_INFO_LIST = new ArrayList();
  private final ListAdapter mAdapter;
  boolean mAreAllFixedViewsSelectable;
  ArrayList<ExtendableListView.FixedViewInfo> mFooterViewInfos;
  ArrayList<ExtendableListView.FixedViewInfo> mHeaderViewInfos;
  private final boolean mIsFilterable;
  
  public HeaderViewListAdapter(ArrayList<ExtendableListView.FixedViewInfo> paramArrayList1, ArrayList<ExtendableListView.FixedViewInfo> paramArrayList2, ListAdapter paramListAdapter)
  {
    this.mAdapter = paramListAdapter;
    this.mIsFilterable = (paramListAdapter instanceof Filterable);
    if (paramArrayList1 == null)
    {
      this.mHeaderViewInfos = EMPTY_INFO_LIST;
      if (paramArrayList2 != null) {
        break label79;
      }
      this.mFooterViewInfos = EMPTY_INFO_LIST;
      label39:
      if ((!areAllListInfosSelectable(this.mHeaderViewInfos)) || (!areAllListInfosSelectable(this.mFooterViewInfos))) {
        break label87;
      }
    }
    label79:
    label87:
    for (boolean bool = true;; bool = false)
    {
      this.mAreAllFixedViewsSelectable = bool;
      return;
      this.mHeaderViewInfos = paramArrayList1;
      break;
      this.mFooterViewInfos = paramArrayList2;
      break label39;
    }
  }
  
  private boolean areAllListInfosSelectable(ArrayList<ExtendableListView.FixedViewInfo> paramArrayList)
  {
    if (paramArrayList != null)
    {
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext()) {
        if (!((ExtendableListView.FixedViewInfo)paramArrayList.next()).isSelectable) {
          return false;
        }
      }
    }
    return true;
  }
  
  public boolean areAllItemsEnabled()
  {
    return (this.mAdapter == null) || ((this.mAreAllFixedViewsSelectable) && (this.mAdapter.areAllItemsEnabled()));
  }
  
  public int getCount()
  {
    if (this.mAdapter != null) {
      return getFootersCount() + getHeadersCount() + this.mAdapter.getCount();
    }
    return getFootersCount() + getHeadersCount();
  }
  
  public Filter getFilter()
  {
    if (this.mIsFilterable) {
      return ((Filterable)this.mAdapter).getFilter();
    }
    return null;
  }
  
  public int getFootersCount()
  {
    return this.mFooterViewInfos.size();
  }
  
  public int getHeadersCount()
  {
    return this.mHeaderViewInfos.size();
  }
  
  public Object getItem(int paramInt)
  {
    int i = getHeadersCount();
    if (paramInt < i) {
      return ((ExtendableListView.FixedViewInfo)this.mHeaderViewInfos.get(paramInt)).data;
    }
    int j = paramInt - i;
    paramInt = 0;
    if (this.mAdapter != null)
    {
      i = this.mAdapter.getCount();
      paramInt = i;
      if (j < i) {
        return this.mAdapter.getItem(j);
      }
    }
    return ((ExtendableListView.FixedViewInfo)this.mFooterViewInfos.get(j - paramInt)).data;
  }
  
  public long getItemId(int paramInt)
  {
    int i = getHeadersCount();
    if ((this.mAdapter != null) && (paramInt >= i))
    {
      paramInt -= i;
      if (paramInt < this.mAdapter.getCount()) {
        return this.mAdapter.getItemId(paramInt);
      }
    }
    return -1L;
  }
  
  public int getItemViewType(int paramInt)
  {
    int i = getHeadersCount();
    if ((this.mAdapter != null) && (paramInt >= i))
    {
      paramInt -= i;
      if (paramInt < this.mAdapter.getCount()) {
        return this.mAdapter.getItemViewType(paramInt);
      }
    }
    return -2;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    int i = getHeadersCount();
    if (paramInt < i) {
      return ((ExtendableListView.FixedViewInfo)this.mHeaderViewInfos.get(paramInt)).view;
    }
    int j = paramInt - i;
    paramInt = 0;
    if (this.mAdapter != null)
    {
      i = this.mAdapter.getCount();
      paramInt = i;
      if (j < i) {
        return this.mAdapter.getView(j, paramView, paramViewGroup);
      }
    }
    return ((ExtendableListView.FixedViewInfo)this.mFooterViewInfos.get(j - paramInt)).view;
  }
  
  public int getViewTypeCount()
  {
    if (this.mAdapter != null) {
      return this.mAdapter.getViewTypeCount();
    }
    return 1;
  }
  
  public ListAdapter getWrappedAdapter()
  {
    return this.mAdapter;
  }
  
  public boolean hasStableIds()
  {
    if (this.mAdapter != null) {
      return this.mAdapter.hasStableIds();
    }
    return false;
  }
  
  public boolean isEmpty()
  {
    return (this.mAdapter == null) || (this.mAdapter.isEmpty());
  }
  
  public boolean isEnabled(int paramInt)
  {
    int i = getHeadersCount();
    if (paramInt < i) {
      return ((ExtendableListView.FixedViewInfo)this.mHeaderViewInfos.get(paramInt)).isSelectable;
    }
    int j = paramInt - i;
    paramInt = 0;
    if (this.mAdapter != null)
    {
      i = this.mAdapter.getCount();
      paramInt = i;
      if (j < i) {
        return this.mAdapter.isEnabled(j);
      }
    }
    return ((ExtendableListView.FixedViewInfo)this.mFooterViewInfos.get(j - paramInt)).isSelectable;
  }
  
  public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    if (this.mAdapter != null) {
      this.mAdapter.registerDataSetObserver(paramDataSetObserver);
    }
  }
  
  public boolean removeFooter(View paramView)
  {
    boolean bool2 = false;
    int i = 0;
    while (i < this.mFooterViewInfos.size())
    {
      if (((ExtendableListView.FixedViewInfo)this.mFooterViewInfos.get(i)).view == paramView)
      {
        this.mFooterViewInfos.remove(i);
        boolean bool1 = bool2;
        if (areAllListInfosSelectable(this.mHeaderViewInfos))
        {
          bool1 = bool2;
          if (areAllListInfosSelectable(this.mFooterViewInfos)) {
            bool1 = true;
          }
        }
        this.mAreAllFixedViewsSelectable = bool1;
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public boolean removeHeader(View paramView)
  {
    boolean bool2 = false;
    int i = 0;
    while (i < this.mHeaderViewInfos.size())
    {
      if (((ExtendableListView.FixedViewInfo)this.mHeaderViewInfos.get(i)).view == paramView)
      {
        this.mHeaderViewInfos.remove(i);
        boolean bool1 = bool2;
        if (areAllListInfosSelectable(this.mHeaderViewInfos))
        {
          bool1 = bool2;
          if (areAllListInfosSelectable(this.mFooterViewInfos)) {
            bool1 = true;
          }
        }
        this.mAreAllFixedViewsSelectable = bool1;
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    if (this.mAdapter != null) {
      this.mAdapter.unregisterDataSetObserver(paramDataSetObserver);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/grid/HeaderViewListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */