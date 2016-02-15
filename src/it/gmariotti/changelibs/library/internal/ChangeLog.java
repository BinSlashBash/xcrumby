package it.gmariotti.changelibs.library.internal;

import java.util.Iterator;
import java.util.LinkedList;

public class ChangeLog
{
  private boolean bulletedList;
  private LinkedList<ChangeLogRow> rows = new LinkedList();
  
  public void addRow(ChangeLogRow paramChangeLogRow)
  {
    if (paramChangeLogRow != null)
    {
      if (this.rows == null) {
        this.rows = new LinkedList();
      }
      this.rows.add(paramChangeLogRow);
    }
  }
  
  public void clearAllRows()
  {
    this.rows = new LinkedList();
  }
  
  public LinkedList<ChangeLogRow> getRows()
  {
    return this.rows;
  }
  
  public boolean isBulletedList()
  {
    return this.bulletedList;
  }
  
  public void setBulletedList(boolean paramBoolean)
  {
    this.bulletedList = paramBoolean;
  }
  
  public void setRows(LinkedList<ChangeLogRow> paramLinkedList)
  {
    this.rows = paramLinkedList;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("bulletedList=" + this.bulletedList);
    localStringBuilder.append("\n");
    if (this.rows != null)
    {
      Iterator localIterator = this.rows.iterator();
      while (localIterator.hasNext())
      {
        ChangeLogRow localChangeLogRow = (ChangeLogRow)localIterator.next();
        localStringBuilder.append("row=[");
        localStringBuilder.append(localChangeLogRow.toString());
        localStringBuilder.append("]\n");
      }
    }
    localStringBuilder.append("rows:none");
    return localStringBuilder.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/it/gmariotti/changelibs/library/internal/ChangeLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */