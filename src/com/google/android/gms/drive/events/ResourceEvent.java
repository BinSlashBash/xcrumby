package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveId;

public abstract interface ResourceEvent
  extends DriveEvent
{
  public abstract DriveId getDriveId();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/drive/events/ResourceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */