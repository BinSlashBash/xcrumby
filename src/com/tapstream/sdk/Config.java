package com.tapstream.sdk;

import java.util.HashMap;
import java.util.Map;

public class Config
{
  private String androidId = null;
  private boolean collectAdvertisingId = true;
  private boolean collectTasteData = true;
  private String deviceId = null;
  private boolean fireAutomaticInstallEvent = true;
  private boolean fireAutomaticOpenEvent = true;
  public Map<String, Object> globalEventParams = new HashMap();
  private String hardware = null;
  private String installEventName = null;
  private String odin1 = null;
  private String openEventName = null;
  private String openUdid = null;
  private String wifiMac = null;
  
  public String getAndroidId()
  {
    return this.androidId;
  }
  
  public boolean getCollectAdvertisingId()
  {
    return this.collectAdvertisingId;
  }
  
  @Deprecated
  public boolean getCollectAndroidId()
  {
    return false;
  }
  
  @Deprecated
  public boolean getCollectDeviceId()
  {
    return false;
  }
  
  public boolean getCollectTasteData()
  {
    return this.collectTasteData;
  }
  
  @Deprecated
  public boolean getCollectWifiMac()
  {
    return false;
  }
  
  public String getDeviceId()
  {
    return this.deviceId;
  }
  
  public boolean getFireAutomaticInstallEvent()
  {
    return this.fireAutomaticInstallEvent;
  }
  
  public boolean getFireAutomaticOpenEvent()
  {
    return this.fireAutomaticOpenEvent;
  }
  
  public String getHardware()
  {
    return this.hardware;
  }
  
  public String getInstallEventName()
  {
    return this.installEventName;
  }
  
  public String getOdin1()
  {
    return this.odin1;
  }
  
  public String getOpenEventName()
  {
    return this.openEventName;
  }
  
  public String getOpenUdid()
  {
    return this.openUdid;
  }
  
  public String getWifiMac()
  {
    return this.wifiMac;
  }
  
  public void setAndroidId(String paramString)
  {
    this.androidId = paramString;
  }
  
  public void setCollectAdvertisingId(boolean paramBoolean)
  {
    this.collectAdvertisingId = paramBoolean;
  }
  
  @Deprecated
  public void setCollectAndroidId(boolean paramBoolean) {}
  
  @Deprecated
  public void setCollectDeviceId(boolean paramBoolean) {}
  
  public void setCollectTasteData(boolean paramBoolean)
  {
    this.collectTasteData = paramBoolean;
  }
  
  @Deprecated
  public void setCollectWifiMac(boolean paramBoolean) {}
  
  public void setDeviceId(String paramString)
  {
    this.deviceId = paramString;
  }
  
  public void setFireAutomaticInstallEvent(boolean paramBoolean)
  {
    this.fireAutomaticInstallEvent = paramBoolean;
  }
  
  public void setFireAutomaticOpenEvent(boolean paramBoolean)
  {
    this.fireAutomaticOpenEvent = paramBoolean;
  }
  
  public void setHardware(String paramString)
  {
    this.hardware = paramString;
  }
  
  public void setInstallEventName(String paramString)
  {
    this.installEventName = paramString;
  }
  
  public void setOdin1(String paramString)
  {
    this.odin1 = paramString;
  }
  
  public void setOpenEventName(String paramString)
  {
    this.openEventName = paramString;
  }
  
  public void setOpenUdid(String paramString)
  {
    this.openUdid = paramString;
  }
  
  public void setWifiMac(String paramString)
  {
    this.wifiMac = paramString;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/tapstream/sdk/Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */