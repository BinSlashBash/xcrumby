package com.crumby.lib;

import java.util.ArrayList;

public class ImageComment
{
  private ArrayList<ImageComment> children;
  private String markdown;
  private String message;
  private String username;
  
  public ImageComment() {}
  
  public ImageComment(String paramString1, String paramString2, ArrayList<ImageComment> paramArrayList)
  {
    this.username = paramString1;
    this.message = paramString2;
    this.children = paramArrayList;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setChildren(ArrayList<ImageComment> paramArrayList)
  {
    this.children = paramArrayList;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public void setUsername(String paramString)
  {
    this.username = paramString;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/ImageComment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */