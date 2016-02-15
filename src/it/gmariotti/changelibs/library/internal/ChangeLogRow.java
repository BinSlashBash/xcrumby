package it.gmariotti.changelibs.library.internal;

import android.content.Context;
import android.content.res.Resources;
import it.gmariotti.changelibs.R.string;

public class ChangeLogRow
{
  public static final int BUGFIX = 1;
  public static final int DEFAULT = 0;
  public static final int IMPROVEMENT = 2;
  private boolean bulletedList;
  protected String changeDate;
  private String changeText;
  private String changeTextTitle;
  protected boolean header;
  private int type;
  protected int versionCode;
  protected String versionName;
  
  public String getChangeDate()
  {
    return this.changeDate;
  }
  
  public String getChangeText()
  {
    return this.changeText;
  }
  
  public String getChangeText(Context paramContext)
  {
    if (paramContext == null) {
      return getChangeText();
    }
    String str = "";
    switch (this.type)
    {
    default: 
      paramContext = str;
    }
    for (;;)
    {
      return paramContext + " " + this.changeText;
      paramContext = paramContext.getResources().getString(R.string.changelog_row_prefix_bug).replaceAll("\\[", "<").replaceAll("\\]", ">");
      continue;
      paramContext = paramContext.getResources().getString(R.string.changelog_row_prefix_improvement).replaceAll("\\[", "<").replaceAll("\\]", ">");
    }
  }
  
  public String getChangeTextTitle()
  {
    return this.changeTextTitle;
  }
  
  public int getVersionCode()
  {
    return this.versionCode;
  }
  
  public String getVersionName()
  {
    return this.versionName;
  }
  
  public boolean isBulletedList()
  {
    return this.bulletedList;
  }
  
  public boolean isHeader()
  {
    return this.header;
  }
  
  public void parseChangeText(String paramString)
  {
    String str = paramString;
    if (paramString != null) {
      str = paramString.replaceAll("\\[", "<").replaceAll("\\]", ">");
    }
    setChangeText(str);
  }
  
  public void setBulletedList(boolean paramBoolean)
  {
    this.bulletedList = paramBoolean;
  }
  
  public void setChangeDate(String paramString)
  {
    this.changeDate = paramString;
  }
  
  public void setChangeText(String paramString)
  {
    this.changeText = paramString;
  }
  
  public void setChangeTextTitle(String paramString)
  {
    this.changeTextTitle = paramString;
  }
  
  public void setHeader(boolean paramBoolean)
  {
    this.header = paramBoolean;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public void setVersionCode(int paramInt)
  {
    this.versionCode = paramInt;
  }
  
  public void setVersionName(String paramString)
  {
    this.versionName = paramString;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("header=" + this.header);
    localStringBuilder.append(",");
    localStringBuilder.append("versionName=" + this.versionName);
    localStringBuilder.append(",");
    localStringBuilder.append("versionCode=" + this.versionCode);
    localStringBuilder.append(",");
    localStringBuilder.append("bulletedList=" + this.bulletedList);
    localStringBuilder.append(",");
    localStringBuilder.append("changeText=" + this.changeText);
    return localStringBuilder.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/gmariotti/changelibs/library/internal/ChangeLogRow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */