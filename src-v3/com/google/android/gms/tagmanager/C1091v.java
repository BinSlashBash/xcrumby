package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import com.google.android.gms.tagmanager.DataLayer.C0485a;
import com.google.android.gms.tagmanager.DataLayer.C0488c;
import com.google.android.gms.tagmanager.DataLayer.C0488c.C0487a;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* renamed from: com.google.android.gms.tagmanager.v */
class C1091v implements C0488c {
    private static final String XB;
    private gl Wv;
    private final Executor XC;
    private C0535a XD;
    private int XE;
    private final Context mContext;

    /* renamed from: com.google.android.gms.tagmanager.v.1 */
    class C05321 implements Runnable {
        final /* synthetic */ List XF;
        final /* synthetic */ long XG;
        final /* synthetic */ C1091v XH;

        C05321(C1091v c1091v, List list, long j) {
            this.XH = c1091v;
            this.XF = list;
            this.XG = j;
        }

        public void run() {
            this.XH.m2552b(this.XF, this.XG);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.v.2 */
    class C05332 implements Runnable {
        final /* synthetic */ C1091v XH;
        final /* synthetic */ C0487a XI;

        C05332(C1091v c1091v, C0487a c0487a) {
            this.XH = c1091v;
            this.XI = c0487a;
        }

        public void run() {
            this.XI.m1332a(this.XH.ks());
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.v.3 */
    class C05343 implements Runnable {
        final /* synthetic */ C1091v XH;
        final /* synthetic */ String XJ;

        C05343(C1091v c1091v, String str) {
            this.XH = c1091v;
            this.XJ = str;
        }

        public void run() {
            this.XH.by(this.XJ);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.v.a */
    class C0535a extends SQLiteOpenHelper {
        final /* synthetic */ C1091v XH;

        C0535a(C1091v c1091v, Context context, String str) {
            this.XH = c1091v;
            super(context, str, null, 1);
        }

        private void m1484a(SQLiteDatabase sQLiteDatabase) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (!hashSet.remove("key") || !hashSet.remove("value") || !hashSet.remove("ID") || !hashSet.remove("expires")) {
                    throw new SQLiteException("Database column missing");
                } else if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
            } finally {
                rawQuery.close();
            }
        }

        private boolean m1485a(String str, SQLiteDatabase sQLiteDatabase) {
            Cursor cursor;
            Throwable th;
            Cursor cursor2 = null;
            try {
                SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
                Cursor query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
                try {
                    boolean moveToFirst = query.moveToFirst();
                    if (query == null) {
                        return moveToFirst;
                    }
                    query.close();
                    return moveToFirst;
                } catch (SQLiteException e) {
                    cursor = query;
                    try {
                        bh.m1387z("Error querying for table " + str);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    } catch (Throwable th2) {
                        cursor2 = cursor;
                        th = th2;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    cursor2 = query;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                cursor = null;
                bh.m1387z("Error querying for table " + str);
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            } catch (Throwable th4) {
                th = th4;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                this.XH.mContext.getDatabasePath("google_tagmanager.db").delete();
            }
            return sQLiteDatabase == null ? super.getWritableDatabase() : sQLiteDatabase;
        }

        public void onCreate(SQLiteDatabase db) {
            ak.m1367G(db.getPath());
        }

        public void onOpen(SQLiteDatabase db) {
            if (VERSION.SDK_INT < 15) {
                Cursor rawQuery = db.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    rawQuery.moveToFirst();
                } finally {
                    rawQuery.close();
                }
            }
            if (m1485a("datalayer", db)) {
                m1484a(db);
            } else {
                db.execSQL(C1091v.XB);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.v.b */
    private static class C0536b {
        final byte[] XK;
        final String Xy;

        C0536b(String str, byte[] bArr) {
            this.Xy = str;
            this.XK = bArr;
        }

        public String toString() {
            return "KeyAndSerialized: key = " + this.Xy + " serialized hash = " + Arrays.hashCode(this.XK);
        }
    }

    static {
        XB = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", new Object[]{"datalayer", "ID", "key", "value", "expires"});
    }

    public C1091v(Context context) {
        this(context, gn.ft(), "google_tagmanager.db", GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS, Executors.newSingleThreadExecutor());
    }

    C1091v(Context context, gl glVar, String str, int i, Executor executor) {
        this.mContext = context;
        this.Wv = glVar;
        this.XE = i;
        this.XC = executor;
        this.XD = new C0535a(this, this.mContext, str);
    }

    private SQLiteDatabase m2546L(String str) {
        try {
            return this.XD.getWritableDatabase();
        } catch (SQLiteException e) {
            bh.m1387z(str);
            return null;
        }
    }

    private List<C0485a> m2551b(List<C0536b> list) {
        List<C0485a> arrayList = new ArrayList();
        for (C0536b c0536b : list) {
            arrayList.add(new C0485a(c0536b.Xy, m2556j(c0536b.XK)));
        }
        return arrayList;
    }

    private synchronized void m2552b(List<C0536b> list, long j) {
        try {
            long currentTimeMillis = this.Wv.currentTimeMillis();
            m2558u(currentTimeMillis);
            cb(list.size());
            m2554c(list, currentTimeMillis + j);
            kv();
        } catch (Throwable th) {
            kv();
        }
    }

    private void by(String str) {
        SQLiteDatabase L = m2546L("Error opening database for clearKeysWithPrefix.");
        if (L != null) {
            try {
                bh.m1386y("Cleared " + L.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, str + ".%"}) + " items");
            } catch (SQLiteException e) {
                bh.m1387z("Error deleting entries with key prefix: " + str + " (" + e + ").");
            } finally {
                kv();
            }
        }
    }

    private List<C0536b> m2553c(List<C0485a> list) {
        List<C0536b> arrayList = new ArrayList();
        for (C0485a c0485a : list) {
            arrayList.add(new C0536b(c0485a.Xy, m2557j(c0485a.Xz)));
        }
        return arrayList;
    }

    private void m2554c(List<C0536b> list, long j) {
        SQLiteDatabase L = m2546L("Error opening database for writeEntryToDatabase.");
        if (L != null) {
            for (C0536b c0536b : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("expires", Long.valueOf(j));
                contentValues.put("key", c0536b.Xy);
                contentValues.put("value", c0536b.XK);
                L.insert("datalayer", null, contentValues);
            }
        }
    }

    private void cb(int i) {
        int ku = (ku() - this.XE) + i;
        if (ku > 0) {
            List cc = cc(ku);
            bh.m1385x("DataLayer store full, deleting " + cc.size() + " entries to make room.");
            m2555g((String[]) cc.toArray(new String[0]));
        }
    }

    private List<String> cc(int i) {
        Cursor query;
        SQLiteException e;
        Throwable th;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            bh.m1387z("Invalid maxEntries specified. Skipping.");
            return arrayList;
        }
        SQLiteDatabase L = m2546L("Error opening database for peekEntryIds.");
        if (L == null) {
            return arrayList;
        }
        try {
            query = L.query("datalayer", new String[]{"ID"}, null, null, null, null, String.format("%s ASC", new Object[]{"ID"}), Integer.toString(i));
            try {
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(String.valueOf(query.getLong(0)));
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    bh.m1387z("Error in peekEntries fetching entryIds: " + e.getMessage());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            bh.m1387z("Error in peekEntries fetching entryIds: " + e.getMessage());
            if (query != null) {
                query.close();
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        return arrayList;
    }

    private void m2555g(String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            SQLiteDatabase L = m2546L("Error opening database for deleteEntries.");
            if (L != null) {
                try {
                    L.delete("datalayer", String.format("%s in (%s)", new Object[]{"ID", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                } catch (SQLiteException e) {
                    bh.m1387z("Error deleting entries " + Arrays.toString(strArr));
                }
            }
        }
    }

    private Object m2556j(byte[] bArr) {
        Object readObject;
        Throwable th;
        ObjectInputStream objectInputStream = null;
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ObjectInputStream objectInputStream2;
        try {
            objectInputStream2 = new ObjectInputStream(byteArrayInputStream);
            try {
                readObject = objectInputStream2.readObject();
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e) {
                    }
                }
                byteArrayInputStream.close();
            } catch (IOException e2) {
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayInputStream.close();
                return readObject;
            } catch (ClassNotFoundException e4) {
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e5) {
                    }
                }
                byteArrayInputStream.close();
                return readObject;
            } catch (Throwable th2) {
                th = th2;
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e6) {
                        throw th;
                    }
                }
                byteArrayInputStream.close();
                throw th;
            }
        } catch (IOException e7) {
            objectInputStream2 = objectInputStream;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            return readObject;
        } catch (ClassNotFoundException e8) {
            objectInputStream2 = objectInputStream;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            return readObject;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            objectInputStream2 = objectInputStream;
            th = th4;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            throw th;
        }
        return readObject;
    }

    private byte[] m2557j(Object obj) {
        ObjectOutputStream objectOutputStream;
        Throwable th;
        byte[] bArr = null;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(obj);
                bArr = byteArrayOutputStream.toByteArray();
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                    }
                }
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayOutputStream.close();
                return bArr;
            } catch (Throwable th2) {
                th = th2;
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e4) {
                        throw th;
                    }
                }
                byteArrayOutputStream.close();
                throw th;
            }
        } catch (IOException e5) {
            objectOutputStream = bArr;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
            return bArr;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            objectOutputStream = bArr;
            th = th4;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
            throw th;
        }
        return bArr;
    }

    private List<C0485a> ks() {
        try {
            m2558u(this.Wv.currentTimeMillis());
            List<C0485a> b = m2551b(kt());
            return b;
        } finally {
            kv();
        }
    }

    private List<C0536b> kt() {
        SQLiteDatabase L = m2546L("Error opening database for loadSerialized.");
        List<C0536b> arrayList = new ArrayList();
        if (L == null) {
            return arrayList;
        }
        Cursor query = L.query("datalayer", new String[]{"key", "value"}, null, null, null, null, "ID", null);
        while (query.moveToNext()) {
            try {
                arrayList.add(new C0536b(query.getString(0), query.getBlob(1)));
            } finally {
                query.close();
            }
        }
        return arrayList;
    }

    private int ku() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase L = m2546L("Error opening database for getNumStoredEntries.");
        if (L != null) {
            try {
                cursor = L.rawQuery("SELECT COUNT(*) from datalayer", null);
                if (cursor.moveToFirst()) {
                    i = (int) cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                bh.m1387z("Error getting numStoredEntries");
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return i;
    }

    private void kv() {
        try {
            this.XD.close();
        } catch (SQLiteException e) {
        }
    }

    private void m2558u(long j) {
        SQLiteDatabase L = m2546L("Error opening database for deleteOlderThan.");
        if (L != null) {
            try {
                bh.m1386y("Deleted " + L.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)}) + " expired items");
            } catch (SQLiteException e) {
                bh.m1387z("Error deleting old entries.");
            }
        }
    }

    public void m2559a(C0487a c0487a) {
        this.XC.execute(new C05332(this, c0487a));
    }

    public void m2560a(List<C0485a> list, long j) {
        this.XC.execute(new C05321(this, m2553c(list), j));
    }

    public void bx(String str) {
        this.XC.execute(new C05343(this, str));
    }
}
