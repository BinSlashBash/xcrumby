package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.a;

public class e
  extends a<Long>
{
  public e(String paramString, int paramInt)
  {
    super(paramString, paramInt);
  }
  
  protected void a(Bundle paramBundle, Long paramLong)
  {
    paramBundle.putLong(getName(), paramLong.longValue());
  }
  
  protected Long g(DataHolder paramDataHolder, int paramInt1, int paramInt2)
  {
    return Long.valueOf(paramDataHolder.getLong(getName(), paramInt1, paramInt2));
  }
  
  protected Long i(Bundle paramBundle)
  {
    return Long.valueOf(paramBundle.getLong(getName()));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/android/gms/drive/metadata/internal/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */