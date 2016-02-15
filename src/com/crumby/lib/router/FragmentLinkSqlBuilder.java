package com.crumby.lib.router;

import java.util.Iterator;
import java.util.List;

public class FragmentLinkSqlBuilder
{
  public static String build(List<Integer> paramList)
  {
    if (paramList.isEmpty()) {
      return null;
    }
    Object localObject = "(";
    Iterator localIterator = paramList.iterator();
    for (paramList = (List<Integer>)localObject; localIterator.hasNext(); paramList = paramList + localObject + ",") {
      localObject = (Integer)localIterator.next();
    }
    paramList = paramList.substring(0, paramList.length() - 1) + ")";
    return "SELECT * FROM fragment_links_master WHERE id IN " + paramList;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/FragmentLinkSqlBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */