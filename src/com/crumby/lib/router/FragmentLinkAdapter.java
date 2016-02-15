package com.crumby.lib.router;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.events.ClearHistoryEvent;
import com.crumby.lib.events.InsertFragmentLinkIntoDatabaseEvent;
import com.crumby.lib.events.RemoveFragmentLinkFromDatabaseEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FragmentLinkAdapter
{
  private static final String DB_NAME = "fragment_links_master";
  protected static final String TAG = "DataAdapter";
  private final Context mContext;
  private SQLiteDatabase mDb;
  private FragmentLinkHelper mDbHelper;
  private BlockingQueue<InsertFragmentLinkIntoDatabaseEvent> queue;
  
  public FragmentLinkAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    this.mDbHelper = new FragmentLinkHelper(this.mContext);
    BusProvider.BUS.get().register(this);
    createDatabase();
    this.queue = new LinkedBlockingQueue();
    if (GalleryViewer.USER_JUST_INSTALLED) {}
    try
    {
      paramContext = new ObjectMapper().readTree(paramContext.getAssets().open("websites.json")).iterator();
      open();
      this.mDb.beginTransaction();
      while (paramContext.hasNext())
      {
        Object localObject = (JsonNode)paramContext.next();
        String str = ((JsonNode)localObject).get("url").asText();
        localObject = ((JsonNode)localObject).get("name").asText();
        this.mDb.execSQL("INSERT OR REPLACE INTO fragment_links_master (name, url, mandatory) VALUES (\"" + (String)localObject + "\",\"" + str + "\", 1)");
      }
      this.mDb.setTransactionSuccessful();
    }
    catch (IOException paramContext)
    {
      Analytics.INSTANCE.newException(paramContext);
      return;
    }
    this.mDb.endTransaction();
    close();
  }
  
  @Subscribe
  public void clearDatabase(ClearHistoryEvent paramClearHistoryEvent)
  {
    try
    {
      open();
      this.mDb.beginTransaction();
      this.mDb.delete("fragment_links_master", "mandatory = 0", null);
      this.mDb.setTransactionSuccessful();
    }
    catch (SQLException paramClearHistoryEvent)
    {
      for (;;)
      {
        Log.e("DataAdapter", "getTestData >>" + paramClearHistoryEvent.toString());
        this.mDb.endTransaction();
      }
    }
    finally
    {
      this.mDb.endTransaction();
    }
    close();
  }
  
  public void close()
  {
    this.mDbHelper.close();
  }
  
  public FragmentLinkHelper createDatabase()
    throws SQLException
  {
    try
    {
      this.mDbHelper.createDataBase();
      return this.mDbHelper;
    }
    catch (IOException localIOException)
    {
      Log.e("DataAdapter", localIOException.toString() + "  UnableToCreateDatabase");
      throw new Error("UnableToCreateDatabase");
    }
  }
  
  @Subscribe
  public void insertFragmentLink(InsertFragmentLinkIntoDatabaseEvent paramInsertFragmentLinkIntoDatabaseEvent)
  {
    open();
    CrDb.logTime(getClass().getSimpleName(), "insert", true);
    try
    {
      this.mDb.beginTransaction();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("url", paramInsertFragmentLinkIntoDatabaseEvent.url);
      localContentValues.put("thumbnail", paramInsertFragmentLinkIntoDatabaseEvent.thumbnail);
      long l = this.mDb.insert("fragment_links_master", null, localContentValues);
      CrDb.d("fragment link adapter", l + "");
      this.mDb.setTransactionSuccessful();
    }
    catch (SQLException paramInsertFragmentLinkIntoDatabaseEvent)
    {
      for (;;)
      {
        Log.e("DataAdapter", "getTestData >>" + paramInsertFragmentLinkIntoDatabaseEvent.toString());
        this.mDb.endTransaction();
      }
    }
    finally
    {
      this.mDb.endTransaction();
    }
    CrDb.logTime(getClass().getSimpleName(), "insert", false);
    close();
  }
  
  public FragmentLinkAdapter open()
    throws SQLException
  {
    try
    {
      this.mDbHelper.openDataBase();
      this.mDbHelper.close();
      this.mDb = this.mDbHelper.getWritableDatabase();
      return this;
    }
    catch (SQLException localSQLException)
    {
      Log.e("DataAdapter", "open >>" + localSQLException.toString());
      throw localSQLException;
    }
  }
  
  @Subscribe
  public void removeFragmentLink(RemoveFragmentLinkFromDatabaseEvent paramRemoveFragmentLinkFromDatabaseEvent)
  {
    open();
    CrDb.logTime(getClass().getSimpleName(), "remove", true);
    try
    {
      this.mDb.beginTransaction();
      long l = this.mDb.delete("fragment_links_master", "url='" + paramRemoveFragmentLinkFromDatabaseEvent.baseUrl + "' AND mandatory=0", null);
      CrDb.d("fragment link adapter", l + "");
      this.mDb.setTransactionSuccessful();
    }
    catch (SQLException paramRemoveFragmentLinkFromDatabaseEvent)
    {
      for (;;)
      {
        Log.e("DataAdapter", "getTestData >>" + paramRemoveFragmentLinkFromDatabaseEvent.toString());
        this.mDb.endTransaction();
      }
    }
    finally
    {
      this.mDb.endTransaction();
    }
    CrDb.logTime(getClass().getSimpleName(), "remove", false);
    close();
  }
  
  public List<FragmentLink> search(String paramString, boolean paramBoolean, int paramInt)
  {
    CrDb.logTime("link adapter", "get links", true);
    localArrayList = new ArrayList();
    Object localObject = paramString.split("\\s+");
    str1 = "%";
    int j = localObject.length;
    int i = 0;
    while (i < j)
    {
      String str2 = localObject[i];
      str1 = str1 + str2 + "%";
      i += 1;
    }
    open();
    localObject = "name";
    if (paramBoolean) {
      localObject = "url";
    }
    try
    {
      localObject = this.mDb.rawQuery("SELECT * FROM fragment_links_master WHERE " + (String)localObject + " LIKE '" + str1 + "' ORDER BY MANDATORY DESC, insert_time DESC LIMIT " + paramInt, null);
      ((Cursor)localObject).moveToNext();
      while ((localObject != null) && (!((Cursor)localObject).isAfterLast()))
      {
        localArrayList.add(new FragmentLink(((Cursor)localObject).getString(0), ((Cursor)localObject).getString(1), ((Cursor)localObject).getString(2), ((Cursor)localObject).getInt(3)));
        ((Cursor)localObject).moveToNext();
      }
      try
      {
        Analytics.INSTANCE.newException(new Exception(paramString + " " + str1, localSQLException));
        close();
        CrDb.logTime("link adapter", "get links", false);
        return localArrayList;
      }
      catch (Exception paramString)
      {
        for (;;)
        {
          Analytics.INSTANCE.newException(paramString);
        }
      }
    }
    catch (SQLException localSQLException) {}
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/FragmentLinkAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */