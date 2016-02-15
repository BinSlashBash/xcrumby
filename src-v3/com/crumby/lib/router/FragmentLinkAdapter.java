package com.crumby.lib.router;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.events.ClearHistoryEvent;
import com.crumby.lib.events.InsertFragmentLinkIntoDatabaseEvent;
import com.crumby.lib.events.RemoveFragmentLinkFromDatabaseEvent;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.plus.PlusShare;
import com.squareup.otto.Subscribe;
import java.io.IOException;
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

    public FragmentLinkAdapter(Context context) {
        this.mContext = context;
        this.mDbHelper = new FragmentLinkHelper(this.mContext);
        BusProvider.BUS.get().register(this);
        createDatabase();
        this.queue = new LinkedBlockingQueue();
        if (GalleryViewer.USER_JUST_INSTALLED) {
            try {
            } catch (IOException e) {
                Analytics.INSTANCE.newException(e);
                return;
            }
        }
        Iterator<JsonNode> it = new ObjectMapper().readTree(context.getAssets().open("websites.json")).iterator();
        open();
        this.mDb.beginTransaction();
        while (it.hasNext()) {
            JsonNode node = (JsonNode) it.next();
            this.mDb.execSQL("INSERT OR REPLACE INTO fragment_links_master (name, url, mandatory) VALUES (\"" + node.get("name").asText() + "\",\"" + node.get(PlusShare.KEY_CALL_TO_ACTION_URL).asText() + "\", 1)");
        }
        this.mDb.setTransactionSuccessful();
        this.mDb.endTransaction();
        close();
    }

    public FragmentLinkHelper createDatabase() throws SQLException {
        try {
            this.mDbHelper.createDataBase();
            return this.mDbHelper;
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
    }

    public FragmentLinkAdapter open() throws SQLException {
        try {
            this.mDbHelper.openDataBase();
            this.mDbHelper.close();
            this.mDb = this.mDbHelper.getWritableDatabase();
            return this;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public void close() {
        this.mDbHelper.close();
    }

    @Subscribe
    public void clearDatabase(ClearHistoryEvent event) {
        try {
            open();
            this.mDb.beginTransaction();
            this.mDb.delete(DB_NAME, "mandatory = 0", null);
            this.mDb.setTransactionSuccessful();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
        } finally {
            this.mDb.endTransaction();
        }
        close();
    }

    @Subscribe
    public void removeFragmentLink(RemoveFragmentLinkFromDatabaseEvent event) {
        open();
        CrDb.logTime(getClass().getSimpleName(), "remove", true);
        try {
            this.mDb.beginTransaction();
            CrDb.m0d("fragment link adapter", ((long) this.mDb.delete(DB_NAME, "url='" + event.baseUrl + "' AND mandatory=0", null)) + UnsupportedUrlFragment.DISPLAY_NAME);
            this.mDb.setTransactionSuccessful();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
        } finally {
            this.mDb.endTransaction();
        }
        CrDb.logTime(getClass().getSimpleName(), "remove", false);
        close();
    }

    @Subscribe
    public void insertFragmentLink(InsertFragmentLinkIntoDatabaseEvent event) {
        open();
        CrDb.logTime(getClass().getSimpleName(), "insert", true);
        try {
            this.mDb.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(PlusShare.KEY_CALL_TO_ACTION_URL, event.url);
            values.put("thumbnail", event.thumbnail);
            CrDb.m0d("fragment link adapter", this.mDb.insert(DB_NAME, null, values) + UnsupportedUrlFragment.DISPLAY_NAME);
            this.mDb.setTransactionSuccessful();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
        } finally {
            this.mDb.endTransaction();
        }
        CrDb.logTime(getClass().getSimpleName(), "insert", false);
        close();
    }

    public List<FragmentLink> search(String query, boolean matchUrl, int maxResults) {
        CrDb.logTime("link adapter", "get links", true);
        List<FragmentLink> fragmentLinks = new ArrayList();
        String queryString = "%";
        for (String subquery : query.split("\\s+")) {
            queryString = queryString + subquery + "%";
        }
        open();
        try {
            String column = "name";
            if (matchUrl) {
                column = PlusShare.KEY_CALL_TO_ACTION_URL;
            }
            Cursor mCur = this.mDb.rawQuery("SELECT * FROM fragment_links_master WHERE " + column + " LIKE '" + queryString + "' ORDER BY MANDATORY DESC, insert_time DESC LIMIT " + maxResults, null);
            mCur.moveToNext();
            while (mCur != null && !mCur.isAfterLast()) {
                fragmentLinks.add(new FragmentLink(mCur.getString(0), mCur.getString(1), mCur.getString(2), mCur.getInt(3)));
                mCur.moveToNext();
            }
        } catch (SQLException mSQLException) {
            try {
                Analytics.INSTANCE.newException(new Exception(query + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + queryString, mSQLException));
            } catch (Exception e) {
                Analytics.INSTANCE.newException(e);
            }
        }
        close();
        CrDb.logTime("link adapter", "get links", false);
        return fragmentLinks;
    }
}
