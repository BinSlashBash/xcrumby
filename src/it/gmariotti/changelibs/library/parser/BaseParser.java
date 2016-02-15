package it.gmariotti.changelibs.library.parser;

import android.content.Context;
import it.gmariotti.changelibs.library.internal.ChangeLog;

public abstract class BaseParser
{
  protected boolean bulletedList;
  protected Context mContext;
  
  public BaseParser(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public abstract ChangeLog readChangeLogFile()
    throws Exception;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/gmariotti/changelibs/library/parser/BaseParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */