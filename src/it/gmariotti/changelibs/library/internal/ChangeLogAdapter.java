package it.gmariotti.changelibs.library.internal;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import it.gmariotti.changelibs.R.id;
import it.gmariotti.changelibs.library.Constants;
import java.util.List;

public class ChangeLogAdapter
  extends ArrayAdapter<ChangeLogRow>
{
  protected static final int TYPE_HEADER = 1;
  protected static final int TYPE_ROW = 0;
  private final Context mContext;
  private int mRowHeaderLayoutId = Constants.mRowHeaderLayoutId;
  private int mRowLayoutId = Constants.mRowLayoutId;
  private int mStringVersionHeader = Constants.mStringVersionHeader;
  
  public ChangeLogAdapter(Context paramContext, List<ChangeLogRow> paramList)
  {
    super(paramContext, 0, paramList);
    this.mContext = paramContext;
  }
  
  public int getItemViewType(int paramInt)
  {
    if (((ChangeLogRow)getItem(paramInt)).isHeader()) {
      return 1;
    }
    return 0;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ChangeLogRow localChangeLogRow = (ChangeLogRow)getItem(paramInt);
    paramInt = getItemViewType(paramInt);
    LayoutInflater localLayoutInflater = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
    Object localObject2;
    switch (paramInt)
    {
    default: 
      paramViewGroup = paramView;
      return paramViewGroup;
    case 1: 
      localObject1 = null;
      if (paramView != null)
      {
        localObject1 = paramView.getTag();
        if (!(localObject1 instanceof ViewHolderHeader)) {
          break label263;
        }
      }
      label263:
      for (localObject1 = (ViewHolderHeader)localObject1;; localObject1 = null)
      {
        if (paramView != null)
        {
          localObject2 = localObject1;
          if (localObject1 != null) {}
        }
        else
        {
          paramView = localLayoutInflater.inflate(this.mRowHeaderLayoutId, paramViewGroup, false);
          localObject2 = new ViewHolderHeader((TextView)paramView.findViewById(R.id.chg_headerVersion), (TextView)paramView.findViewById(R.id.chg_headerDate));
          paramView.setTag(localObject2);
        }
        paramViewGroup = paramView;
        if (localChangeLogRow == null) {
          break;
        }
        paramViewGroup = paramView;
        if (localObject2 == null) {
          break;
        }
        if (((ViewHolderHeader)localObject2).version != null)
        {
          paramViewGroup = new StringBuilder();
          localObject1 = getContext().getString(this.mStringVersionHeader);
          if (localObject1 != null) {
            paramViewGroup.append((String)localObject1);
          }
          paramViewGroup.append(localChangeLogRow.versionName);
          ((ViewHolderHeader)localObject2).version.setText(paramViewGroup.toString());
        }
        paramViewGroup = paramView;
        if (((ViewHolderHeader)localObject2).date == null) {
          break;
        }
        if (localChangeLogRow.changeDate == null) {
          break label269;
        }
        ((ViewHolderHeader)localObject2).date.setText(localChangeLogRow.changeDate);
        ((ViewHolderHeader)localObject2).date.setVisibility(0);
        return paramView;
      }
      label269:
      ((ViewHolderHeader)localObject2).date.setText("");
      ((ViewHolderHeader)localObject2).date.setVisibility(8);
      return paramView;
    }
    Object localObject1 = null;
    if (paramView != null)
    {
      localObject1 = paramView.getTag();
      if (!(localObject1 instanceof ViewHolderRow)) {
        break label461;
      }
    }
    label461:
    for (localObject1 = (ViewHolderRow)localObject1;; localObject1 = null)
    {
      if (paramView != null)
      {
        localObject2 = localObject1;
        if (localObject1 != null) {}
      }
      else
      {
        paramView = localLayoutInflater.inflate(this.mRowLayoutId, paramViewGroup, false);
        localObject2 = new ViewHolderRow((TextView)paramView.findViewById(R.id.chg_text), (TextView)paramView.findViewById(R.id.chg_textbullet));
        paramView.setTag(localObject2);
      }
      paramViewGroup = paramView;
      if (localChangeLogRow == null) {
        break;
      }
      paramViewGroup = paramView;
      if (localObject2 == null) {
        break;
      }
      if (((ViewHolderRow)localObject2).text != null)
      {
        ((ViewHolderRow)localObject2).text.setText(Html.fromHtml(localChangeLogRow.getChangeText(this.mContext)));
        ((ViewHolderRow)localObject2).text.setMovementMethod(LinkMovementMethod.getInstance());
      }
      paramViewGroup = paramView;
      if (((ViewHolderRow)localObject2).bulletText == null) {
        break;
      }
      if (!localChangeLogRow.isBulletedList()) {
        break label467;
      }
      ((ViewHolderRow)localObject2).bulletText.setVisibility(0);
      return paramView;
    }
    label467:
    ((ViewHolderRow)localObject2).bulletText.setVisibility(8);
    return paramView;
  }
  
  public int getViewTypeCount()
  {
    return 2;
  }
  
  public int getmRowHeaderLayoutId()
  {
    return this.mRowHeaderLayoutId;
  }
  
  public int getmRowLayoutId()
  {
    return this.mRowLayoutId;
  }
  
  public int getmStringVersionHeader()
  {
    return this.mStringVersionHeader;
  }
  
  public boolean isEnabled(int paramInt)
  {
    return false;
  }
  
  public void setmRowHeaderLayoutId(int paramInt)
  {
    this.mRowHeaderLayoutId = paramInt;
  }
  
  public void setmRowLayoutId(int paramInt)
  {
    this.mRowLayoutId = paramInt;
  }
  
  public void setmStringVersionHeader(int paramInt)
  {
    this.mStringVersionHeader = paramInt;
  }
  
  static class ViewHolderHeader
  {
    TextView date;
    TextView version;
    
    public ViewHolderHeader(TextView paramTextView1, TextView paramTextView2)
    {
      this.version = paramTextView1;
      this.date = paramTextView2;
    }
  }
  
  static class ViewHolderRow
  {
    TextView bulletText;
    TextView text;
    
    public ViewHolderRow(TextView paramTextView1, TextView paramTextView2)
    {
      this.text = paramTextView1;
      this.bulletText = paramTextView2;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/gmariotti/changelibs/library/internal/ChangeLogAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */