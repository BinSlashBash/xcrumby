/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.res.AssetManager
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package com.crumby.lib.router;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import com.crumby.GalleryViewer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FragmentLinkHelper
extends SQLiteOpenHelper {
    private static String DB_NAME;
    private static String DB_PATH;
    private static final int NO_PREVIOUS_VERSION = -1;
    private static String TAG;
    private final Context mContext;
    private SQLiteDatabase mDataBase;

    static {
        TAG = "DataBaseHelper";
        DB_PATH = "";
        DB_NAME = "crumby.db";
    }

    /*
     * Enabled aggressive block sorting
     */
    public FragmentLinkHelper(Context context) {
        super(context, DB_NAME, null, 1);
        DB_PATH = Build.VERSION.SDK_INT >= 17 ? context.getApplicationInfo().dataDir + "/databases/" : "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
    }

    private boolean checkDataBase() {
        return new File(DB_PATH + DB_NAME).exists();
    }

    private void copyDataBase() throws IOException {
        int n2;
        InputStream inputStream = this.mContext.getAssets().open(DB_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] arrby = new byte[1024];
        while ((n2 = inputStream.read(arrby)) > 0) {
            fileOutputStream.write(arrby, 0, n2);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
    }

    public void close() {
        synchronized (this) {
            if (this.mDataBase != null) {
                this.mDataBase.close();
            }
            super.close();
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void createDataBase() throws IOException {
        boolean bl2 = this.checkDataBase();
        float f2 = -1.0f;
        if (GalleryViewer.PREVIOUS_VERSION != null) {
            f2 = Float.parseFloat(GalleryViewer.PREVIOUS_VERSION);
        }
        if (!bl2 || GalleryViewer.USER_JUST_INSTALLED && f2 < 0.38f) {
            this.getReadableDatabase();
            this.close();
            this.copyDataBase();
            Log.e((String)TAG, (String)"createDatabase database created");
        }
        if (f2 == -1.0f) return;
        if (f2 <= 0.38f) {
            // empty if block
        }
        if (f2 > 0.4f) return;
        return;
        catch (IOException iOException) {
            throw new Error("ErrorCopyingDataBase");
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n2, int n3) {
    }

    public boolean openDataBase() throws SQLException {
        this.mDataBase = SQLiteDatabase.openDatabase((String)(DB_PATH + DB_NAME), (SQLiteDatabase.CursorFactory)null, (int)268435456);
        if (this.mDataBase != null) {
            return true;
        }
        return false;
    }
}

