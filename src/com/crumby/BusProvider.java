package com.crumby;

import com.squareup.otto.Bus;

public enum BusProvider
{
  BUS;
  
  private final Bus bus = new Bus();
  
  private BusProvider() {}
  
  public Bus get()
  {
    return this.bus;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/BusProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */