package it.gmariotti.changelibs.library.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import it.gmariotti.changelibs.R.string;
import it.gmariotti.changelibs.R.styleable;
import it.gmariotti.changelibs.library.Constants;
import it.gmariotti.changelibs.library.Util;
import it.gmariotti.changelibs.library.internal.ChangeLog;
import it.gmariotti.changelibs.library.internal.ChangeLogAdapter;
import it.gmariotti.changelibs.library.internal.ChangeLogRow;
import it.gmariotti.changelibs.library.parser.XmlParser;
import java.util.Iterator;
import java.util.LinkedList;

public class ChangeLogListView
  extends ListView
  implements AdapterView.OnItemClickListener
{
  protected static String TAG = "ChangeLogListView";
  protected ChangeLogAdapter mAdapter;
  protected int mChangeLogFileResourceId = Constants.mChangeLogFileResourceId;
  protected String mChangeLogFileResourceUrl = null;
  protected int mRowHeaderLayoutId = Constants.mRowHeaderLayoutId;
  protected int mRowLayoutId = Constants.mRowLayoutId;
  
  public ChangeLogListView(Context paramContext)
  {
    super(paramContext);
    init(null, 0);
  }
  
  public ChangeLogListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet, 0);
  }
  
  public ChangeLogListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet, paramInt);
  }
  
  protected void init(AttributeSet paramAttributeSet, int paramInt)
  {
    initAttrs(paramAttributeSet, paramInt);
    initAdapter();
    setDividerHeight(0);
  }
  
  protected void initAdapter()
  {
    try
    {
      XmlParser localXmlParser;
      if (this.mChangeLogFileResourceUrl != null)
      {
        localXmlParser = new XmlParser(getContext(), this.mChangeLogFileResourceUrl);
        ChangeLog localChangeLog = new ChangeLog();
        if (localChangeLog == null) {
          break label186;
        }
        this.mAdapter = new ChangeLogAdapter(getContext(), localChangeLog.getRows());
        this.mAdapter.setmRowLayoutId(this.mRowLayoutId);
        this.mAdapter.setmRowHeaderLayoutId(this.mRowHeaderLayoutId);
        if ((this.mChangeLogFileResourceUrl != null) && ((this.mChangeLogFileResourceUrl == null) || (!Util.isConnected(getContext())))) {
          break label149;
        }
        new ParseAsyncTask(this.mAdapter, localXmlParser).execute(new Void[0]);
      }
      for (;;)
      {
        setAdapter(this.mAdapter);
        return;
        localXmlParser = new XmlParser(getContext(), this.mChangeLogFileResourceId);
        break;
        label149:
        Toast.makeText(getContext(), R.string.changelog_internal_error_internet_connection, 1).show();
      }
      setAdapter(null);
    }
    catch (Exception localException)
    {
      Log.e(TAG, getResources().getString(R.string.changelog_internal_error_parsing), localException);
      return;
    }
    label186:
  }
  
  protected void initAttrs(AttributeSet paramAttributeSet, int paramInt)
  {
    paramAttributeSet = getContext().getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.ChangeLogListView, paramInt, paramInt);
    try
    {
      this.mRowLayoutId = paramAttributeSet.getResourceId(0, this.mRowLayoutId);
      this.mRowHeaderLayoutId = paramAttributeSet.getResourceId(1, this.mRowHeaderLayoutId);
      this.mChangeLogFileResourceId = paramAttributeSet.getResourceId(2, this.mChangeLogFileResourceId);
      this.mChangeLogFileResourceUrl = paramAttributeSet.getString(3);
      return;
    }
    finally
    {
      paramAttributeSet.recycle();
    }
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {}
  
  public void setAdapter(ChangeLogAdapter paramChangeLogAdapter)
  {
    super.setAdapter(paramChangeLogAdapter);
  }
  
  protected class ParseAsyncTask
    extends AsyncTask<Void, Void, ChangeLog>
  {
    private ChangeLogAdapter mAdapter;
    private XmlParser mParse;
    
    public ParseAsyncTask(ChangeLogAdapter paramChangeLogAdapter, XmlParser paramXmlParser)
    {
      this.mAdapter = paramChangeLogAdapter;
      this.mParse = paramXmlParser;
    }
    
    protected ChangeLog doInBackground(Void... paramVarArgs)
    {
      try
      {
        if (this.mParse != null)
        {
          paramVarArgs = this.mParse.readChangeLogFile();
          return paramVarArgs;
        }
      }
      catch (Exception paramVarArgs)
      {
        Log.e(ChangeLogListView.TAG, ChangeLogListView.this.getResources().getString(R.string.changelog_internal_error_parsing), paramVarArgs);
      }
      return null;
    }
    
    protected void onPostExecute(ChangeLog paramChangeLog)
    {
      if (paramChangeLog != null)
      {
        if (Build.VERSION.SDK_INT < 11) {
          break label31;
        }
        this.mAdapter.addAll(paramChangeLog.getRows());
      }
      for (;;)
      {
        this.mAdapter.notifyDataSetChanged();
        return;
        label31:
        if (paramChangeLog.getRows() != null)
        {
          paramChangeLog = paramChangeLog.getRows().iterator();
          while (paramChangeLog.hasNext())
          {
            ChangeLogRow localChangeLogRow = (ChangeLogRow)paramChangeLog.next();
            this.mAdapter.add(localChangeLogRow);
          }
        }
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/gmariotti/changelibs/library/view/ChangeLogListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */