package com.squareup.otto;

public class DeadEvent
{
  public final Object event;
  public final Object source;
  
  public DeadEvent(Object paramObject1, Object paramObject2)
  {
    this.source = paramObject1;
    this.event = paramObject2;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/otto/DeadEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */