package com.crumby.lib.router;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.util.Log;
import com.crumby.GalleryViewer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FragmentLinkHelper
  extends SQLiteOpenHelper
{
  private static String DB_NAME = "crumby.db";
  private static String DB_PATH;
  private static final int NO_PREVIOUS_VERSION = -1;
  private static String TAG = "DataBaseHelper";
  private final Context mContext;
  private SQLiteDatabase mDataBase;
  
  static
  {
    DB_PATH = "";
  }
  
  public FragmentLinkHelper(Context paramContext)
  {
    super(paramContext, DB_NAME, null, 1);
    if (Build.VERSION.SDK_INT >= 17) {}
    for (DB_PATH = paramContext.getApplicationInfo().dataDir + "/databases/";; DB_PATH = "/data/data/" + paramContext.getPackageName() + "/databases/")
    {
      this.mContext = paramContext;
      return;
    }
  }
  
  private boolean checkDataBase()
  {
    return new File(DB_PATH + DB_NAME).exists();
  }
  
  private void copyDataBase()
    throws IOException
  {
    InputStream localInputStream = this.mContext.getAssets().open(DB_NAME);
    FileOutputStream localFileOutputStream = new FileOutputStream(DB_PATH + DB_NAME);
    byte[] arrayOfByte = new byte['Ѐ'];
    for (;;)
    {
      int i = localInputStream.read(arrayOfByte);
      if (i <= 0) {
        break;
      }
      localFileOutputStream.write(arrayOfByte, 0, i);
    }
    localFileOutputStream.flush();
    localFileOutputStream.close();
    localInputStream.close();
  }
  
  public void close()
  {
    try
    {
      if (this.mDataBase != null) {
        this.mDataBase.close();
      }
      super.close();
      return;
    }
    finally {}
  }
  
  public void createDataBase()
    throws IOException
  {
    boolean bool = checkDataBase();
    float f = -1.0F;
    if (GalleryViewer.PREVIOUS_VERSION != null) {
      f = Float.parseFloat(GalleryViewer.PREVIOUS_VERSION);
    }
    if ((!bool) || ((GalleryViewer.USER_JUST_INSTALLED) && (f < 0.38F)))
    {
      getReadableDatabase();
      close();
    }
    try
    {
      copyDataBase();
      Log.e(TAG, "createDatabase database created");
      if ((f != -1.0F) && ((f > 0.38F) || (f <= 0.4F))) {}
      return;
    }
    catch (IOException localIOException)
    {
      throw new Error("ErrorCopyingDataBase");
    }
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {}
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
  
  public boolean openDataBase()
    throws SQLException
  {
    this.mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 268435456);
    return this.mDataBase != null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/FragmentLinkHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */