package it.gmariotti.changelibs.library.internal;

public class ChangeLogRowHeader
  extends ChangeLogRow
{
  public ChangeLogRowHeader()
  {
    setHeader(true);
    setBulletedList(false);
    setChangeTextTitle(null);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("header=" + this.header);
    localStringBuilder.append(",");
    localStringBuilder.append("versionName=" + this.versionName);
    localStringBuilder.append(",");
    localStringBuilder.append("changeDate=" + this.changeDate);
    return localStringBuilder.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/gmariotti/changelibs/library/internal/ChangeLogRowHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */