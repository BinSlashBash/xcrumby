package com.crumby.lib.router;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FragmentLinkHelper extends SQLiteOpenHelper {
    private static String DB_NAME = null;
    private static String DB_PATH = null;
    private static final int NO_PREVIOUS_VERSION = -1;
    private static String TAG;
    private final Context mContext;
    private SQLiteDatabase mDataBase;

    static {
        TAG = "DataBaseHelper";
        DB_PATH = UnsupportedUrlFragment.DISPLAY_NAME;
        DB_NAME = "crumby.db";
    }

    public FragmentLinkHelper(Context context) {
        super(context, DB_NAME, null, 1);
        if (VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void createDataBase() throws IOException {
        boolean mDataBaseExist = checkDataBase();
        float previousVersion = GroundOverlayOptions.NO_DIMENSION;
        if (GalleryViewer.PREVIOUS_VERSION != null) {
            previousVersion = Float.parseFloat(GalleryViewer.PREVIOUS_VERSION);
        }
        if (!mDataBaseExist || (GalleryViewer.USER_JUST_INSTALLED && previousVersion < 0.38f)) {
            getReadableDatabase();
            close();
            try {
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException e) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
        if (previousVersion != GroundOverlayOptions.NO_DIMENSION) {
            if (previousVersion <= 0.38f) {
            }
            if (previousVersion > 0.4f) {
            }
        }
    }

    private boolean checkDataBase() {
        return new File(DB_PATH + DB_NAME).exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = this.mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int mLength = mInput.read(mBuffer);
            if (mLength > 0) {
                mOutput.write(mBuffer, 0, mLength);
            } else {
                mOutput.flush();
                mOutput.close();
                mInput.close();
                return;
            }
        }
    }

    public boolean openDataBase() throws SQLException {
        this.mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, DriveFile.MODE_READ_ONLY);
        return this.mDataBase != null;
    }

    public synchronized void close() {
        if (this.mDataBase != null) {
            this.mDataBase.close();
        }
        super.close();
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
