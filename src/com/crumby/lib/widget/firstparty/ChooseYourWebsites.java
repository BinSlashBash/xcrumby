package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.crumby.lib.router.FragmentRouter;
import java.util.List;

public class ChooseYourWebsites
  extends GridView
{
  public ChooseYourWebsites(Context paramContext)
  {
    super(paramContext);
  }
  
  public ChooseYourWebsites(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ChooseYourWebsites(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    setAdapter(new BaseAdapter()
    {
      public int getCount()
      {
        return this.val$indexSettingList.size();
      }
      
      public Object getItem(int paramAnonymousInt)
      {
        return null;
      }
      
      public long getItemId(int paramAnonymousInt)
      {
        return 0L;
      }
      
      public View getView(int paramAnonymousInt, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
      {
        if (paramAnonymousView == null) {
          View.inflate(ChooseYourWebsites.this.getContext(), 2130903048, null);
        }
        return null;
      }
    });
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/ChooseYourWebsites.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */