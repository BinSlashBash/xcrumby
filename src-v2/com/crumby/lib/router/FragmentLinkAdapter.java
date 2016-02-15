/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.util.Log
 */
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
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.router.FragmentLinkHelper;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FragmentLinkAdapter {
    private static final String DB_NAME = "fragment_links_master";
    protected static final String TAG = "DataAdapter";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private FragmentLinkHelper mDbHelper;
    private BlockingQueue<InsertFragmentLinkIntoDatabaseEvent> queue;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public FragmentLinkAdapter(Context object) {
        this.mContext = object;
        this.mDbHelper = new FragmentLinkHelper(this.mContext);
        BusProvider.BUS.get().register(this);
        this.createDatabase();
        this.queue = new LinkedBlockingQueue<InsertFragmentLinkIntoDatabaseEvent>();
        if (GalleryViewer.USER_JUST_INSTALLED) {
            // empty if block
        }
        try {
            object = new ObjectMapper().readTree(object.getAssets().open("websites.json")).iterator();
            this.open();
            this.mDb.beginTransaction();
            while (object.hasNext()) {
                Object object2 = (JsonNode)object.next();
                String string2 = object2.get("url").asText();
                object2 = object2.get("name").asText();
                this.mDb.execSQL("INSERT OR REPLACE INTO fragment_links_master (name, url, mandatory) VALUES (\"" + (String)object2 + "\",\"" + string2 + "\", 1)");
            }
            this.mDb.setTransactionSuccessful();
            this.mDb.endTransaction();
            this.close();
            return;
        }
        catch (IOException var1_2) {
            Analytics.INSTANCE.newException(var1_2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Subscribe
    public void clearDatabase(ClearHistoryEvent clearHistoryEvent) {
        try {
            this.open();
            this.mDb.beginTransaction();
            this.mDb.delete("fragment_links_master", "mandatory = 0", null);
            this.mDb.setTransactionSuccessful();
        }
        catch (SQLException var1_2) {
            Log.e((String)"DataAdapter", (String)("getTestData >>" + var1_2.toString()));
        }
        finally {
            this.mDb.endTransaction();
        }
        this.close();
    }

    public void close() {
        this.mDbHelper.close();
    }

    public FragmentLinkHelper createDatabase() throws SQLException {
        try {
            this.mDbHelper.createDataBase();
            return this.mDbHelper;
        }
        catch (IOException var1_1) {
            Log.e((String)"DataAdapter", (String)(var1_1.toString() + "  UnableToCreateDatabase"));
            throw new Error("UnableToCreateDatabase");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Subscribe
    public void insertFragmentLink(InsertFragmentLinkIntoDatabaseEvent insertFragmentLinkIntoDatabaseEvent) {
        this.open();
        CrDb.logTime(this.getClass().getSimpleName(), "insert", true);
        try {
            this.mDb.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("url", insertFragmentLinkIntoDatabaseEvent.url);
            contentValues.put("thumbnail", insertFragmentLinkIntoDatabaseEvent.thumbnail);
            long l2 = this.mDb.insert("fragment_links_master", null, contentValues);
            CrDb.d("fragment link adapter", "" + l2 + "");
            this.mDb.setTransactionSuccessful();
        }
        catch (SQLException var1_2) {
            Log.e((String)"DataAdapter", (String)("getTestData >>" + var1_2.toString()));
        }
        finally {
            this.mDb.endTransaction();
        }
        CrDb.logTime(this.getClass().getSimpleName(), "insert", false);
        this.close();
    }

    public FragmentLinkAdapter open() throws SQLException {
        try {
            this.mDbHelper.openDataBase();
            this.mDbHelper.close();
            this.mDb = this.mDbHelper.getWritableDatabase();
            return this;
        }
        catch (SQLException var1_1) {
            Log.e((String)"DataAdapter", (String)("open >>" + var1_1.toString()));
            throw var1_1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Subscribe
    public void removeFragmentLink(RemoveFragmentLinkFromDatabaseEvent removeFragmentLinkFromDatabaseEvent) {
        this.open();
        CrDb.logTime(this.getClass().getSimpleName(), "remove", true);
        try {
            this.mDb.beginTransaction();
            long l2 = this.mDb.delete("fragment_links_master", "url='" + removeFragmentLinkFromDatabaseEvent.baseUrl + "' AND mandatory=0", null);
            CrDb.d("fragment link adapter", "" + l2 + "");
            this.mDb.setTransactionSuccessful();
        }
        catch (SQLException var1_2) {
            Log.e((String)"DataAdapter", (String)("getTestData >>" + var1_2.toString()));
        }
        finally {
            this.mDb.endTransaction();
        }
        CrDb.logTime(this.getClass().getSimpleName(), "remove", false);
        this.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<FragmentLink> search(String string2, boolean bl2, int n2) {
        CrDb.logTime("link adapter", "get links", true);
        ArrayList<FragmentLink> arrayList = new ArrayList<FragmentLink>();
        Object object = string2.split("\\s+");
        String string3 = "%";
        for (String string4 : object) {
            string3 = string3 + string4 + "%";
        }
        this.open();
        object = "name";
        if (bl2) {
            object = "url";
        }
        try {
            object = this.mDb.rawQuery("SELECT * FROM fragment_links_master WHERE " + (String)object + " LIKE '" + string3 + "' ORDER BY MANDATORY DESC, insert_time DESC LIMIT " + n2, null);
            object.moveToNext();
            while (object != null && !object.isAfterLast()) {
                arrayList.add(new FragmentLink(object.getString(0), object.getString(1), object.getString(2), object.getInt(3)));
                object.moveToNext();
            }
        }
        catch (SQLException var7_7) {
            try {
                Analytics.INSTANCE.newException(new Exception(string2 + " " + string3, (Throwable)var7_7));
            }
            catch (Exception var1_2) {
                Analytics.INSTANCE.newException(var1_2);
            }
        }
        this.close();
        CrDb.logTime("link adapter", "get links", false);
        return arrayList;
    }
}

