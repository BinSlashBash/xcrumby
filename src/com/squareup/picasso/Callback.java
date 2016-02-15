package com.squareup.picasso;

public abstract interface Callback
{
  public abstract void onError(Exception paramException);
  
  public abstract void onSuccess();
  
  public static class EmptyCallback
    implements Callback
  {
    public void onError(Exception paramException) {}
    
    public void onSuccess() {}
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/picasso/Callback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */