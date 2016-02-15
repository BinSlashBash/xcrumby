package com.crumby.lib.router;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FragmentLinkTrie
{
  public static final boolean MATCH_NAME = false;
  public static final boolean MATCH_URL = true;
  private static int MAX_RESULT_SIZE = 5;
  JsonNode nameRootNode;
  FileOpener opener;
  private boolean terminateCurrentMatch;
  JsonNode urlRootNode;
  
  public FragmentLinkTrie(FileOpener paramFileOpener) {}
  
  private boolean find(String paramString1, JsonNode paramJsonNode, List<Integer> paramList, String paramString2)
  {
    if (paramList.size() > MAX_RESULT_SIZE) {
      return true;
    }
    int i = 0;
    JsonNode localJsonNode1 = null;
    int k = paramString1.length();
    String str1 = paramString2;
    while (i < k)
    {
      if (this.terminateCurrentMatch) {
        return false;
      }
      char c2 = paramString1.charAt(i);
      int j = 0;
      char c1 = c2;
      if (c2 == ' ')
      {
        j = 0 + 1;
        c1 = paramString1.charAt(i + 1);
      }
      String str2 = String.valueOf(c1);
      JsonNode localJsonNode2 = paramJsonNode.get(str2);
      if (localJsonNode2 != null)
      {
        localJsonNode1 = paramJsonNode;
        paramString2 = localJsonNode2;
        if (!localJsonNode2.isContainerNode())
        {
          paramString2 = getNode(localJsonNode2.asText());
          ((ObjectNode)paramJsonNode).put(str2, paramString2);
        }
        paramJsonNode = paramString2;
        str1 = str1 + c1;
        i = i + 1 + j;
      }
      else
      {
        if (localJsonNode1 != null) {
          iterateOverLeaves(paramString1, localJsonNode1, paramList, str1);
        }
        paramString2 = paramString1;
        if (c2 == ' ') {
          paramString2 = paramString1.substring(i);
        }
        iterateOverLeaves(paramString2, paramJsonNode, paramList, str1);
        return false;
      }
    }
    getValues(paramJsonNode, paramList);
    return true;
  }
  
  private JsonNode getNode(String paramString)
  {
    try
    {
      paramString = new ObjectMapper().readTree(this.opener.open("branches/" + paramString + ".json"));
      return paramString;
    }
    catch (IOException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  private void getValues(JsonNode paramJsonNode, List<Integer> paramList)
  {
    if ((this.terminateCurrentMatch) || (paramList.size() > MAX_RESULT_SIZE)) {}
    for (;;)
    {
      return;
      JsonNode localJsonNode = paramJsonNode.get("_v");
      if (localJsonNode != null) {
        paramList.add(Integer.valueOf(localJsonNode.asInt()));
      }
      paramJsonNode = paramJsonNode.elements();
      while ((paramJsonNode.hasNext()) && (!this.terminateCurrentMatch) && (paramList.size() <= MAX_RESULT_SIZE)) {
        getValues((JsonNode)paramJsonNode.next(), paramList);
      }
    }
  }
  
  private void iterateOverLeaves(String paramString1, JsonNode paramJsonNode, List<Integer> paramList, String paramString2)
  {
    Iterator localIterator = paramJsonNode.fieldNames();
    for (;;)
    {
      if ((!localIterator.hasNext()) || (this.terminateCurrentMatch)) {
        return;
      }
      String str = (String)localIterator.next();
      if (!str.equals("_v"))
      {
        JsonNode localJsonNode2 = paramJsonNode.get(str);
        JsonNode localJsonNode1 = localJsonNode2;
        if (!localJsonNode2.isContainerNode())
        {
          localJsonNode1 = getNode(localJsonNode2.asText());
          ((ObjectNode)paramJsonNode).put(str, localJsonNode1);
        }
        find(paramString1, localJsonNode1, paramList, paramString2 + str);
      }
    }
  }
  
  public List<Integer> search(String paramString, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (JsonNode localJsonNode = this.urlRootNode;; localJsonNode = this.nameRootNode)
    {
      ArrayList localArrayList = new ArrayList();
      paramString = " " + paramString.trim();
      if (!paramString.equals(" "))
      {
        this.terminateCurrentMatch = false;
        find(paramString, localJsonNode, localArrayList, "");
      }
      return localArrayList;
    }
  }
  
  public void terminateSearch()
  {
    this.terminateCurrentMatch = true;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/FragmentLinkTrie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */