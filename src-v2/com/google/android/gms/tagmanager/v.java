/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.ak;
import com.google.android.gms.tagmanager.bh;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class v
implements DataLayer.c {
    private static final String XB = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", "datalayer", "ID", "key", "value", "expires");
    private gl Wv;
    private final Executor XC;
    private a XD;
    private int XE;
    private final Context mContext;

    public v(Context context) {
        this(context, gn.ft(), "google_tagmanager.db", 2000, Executors.newSingleThreadExecutor());
    }

    v(Context context, gl gl2, String string2, int n2, Executor executor) {
        this.mContext = context;
        this.Wv = gl2;
        this.XE = n2;
        this.XC = executor;
        this.XD = new a(this.mContext, string2);
    }

    private SQLiteDatabase L(String string2) {
        try {
            SQLiteDatabase sQLiteDatabase = this.XD.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException var2_3) {
            bh.z(string2);
            return null;
        }
    }

    private List<DataLayer.a> b(List<b> object) {
        ArrayList<DataLayer.a> arrayList = new ArrayList<DataLayer.a>();
        object = object.iterator();
        while (object.hasNext()) {
            b b2 = (b)object.next();
            arrayList.add(new DataLayer.a(b2.Xy, this.j(b2.XK)));
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void b(List<b> list, long l2) {
        synchronized (this) {
            try {
                void var2_3;
                long l3 = this.Wv.currentTimeMillis();
                this.u(l3);
                this.cb(list.size());
                this.c(list, l3 + var2_3);
                return;
            }
            finally {
                this.kv();
            }
        }
    }

    private void by(String string2) {
        SQLiteDatabase sQLiteDatabase = this.L("Error opening database for clearKeysWithPrefix.");
        if (sQLiteDatabase == null) {
            return;
        }
        try {
            int n2 = sQLiteDatabase.delete("datalayer", "key = ? OR key LIKE ?", new String[]{string2, string2 + ".%"});
            bh.y("Cleared " + n2 + " items");
            return;
        }
        catch (SQLiteException var3_4) {
            bh.z("Error deleting entries with key prefix: " + string2 + " (" + (Object)var3_4 + ").");
            return;
        }
        finally {
            this.kv();
        }
    }

    private List<b> c(List<DataLayer.a> object) {
        ArrayList<b> arrayList = new ArrayList<b>();
        object = object.iterator();
        while (object.hasNext()) {
            DataLayer.a a2 = (DataLayer.a)object.next();
            arrayList.add(new b(a2.Xy, this.j(a2.Xz)));
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void c(List<b> iterator, long l2) {
        SQLiteDatabase sQLiteDatabase = this.L("Error opening database for writeEntryToDatabase.");
        if (sQLiteDatabase != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                b b2 = (b)iterator.next();
                ContentValues contentValues = new ContentValues();
                contentValues.put("expires", Long.valueOf(l2));
                contentValues.put("key", b2.Xy);
                contentValues.put("value", b2.XK);
                sQLiteDatabase.insert("datalayer", null, contentValues);
            }
        }
    }

    private void cb(int n2) {
        n2 = this.ku() - this.XE + n2;
        if (n2 > 0) {
            List<String> list = this.cc(n2);
            bh.x("DataLayer store full, deleting " + list.size() + " entries to make room.");
            this.g(list.toArray(new String[0]));
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private List<String> cc(int var1_1) {
        block15 : {
            var6_2 = new ArrayList<String>();
            if (var1_1 <= 0) {
                bh.z("Invalid maxEntries specified. Skipping.");
                return var6_2;
            }
            var3_3 = this.L("Error opening database for peekEntryIds.");
            if (var3_3 == null) {
                return var6_2;
            }
            var4_4 = String.format("%s ASC", new Object[]{"ID"});
            var5_8 = Integer.toString(var1_1);
            var3_3 = var4_4 = var3_3.query("datalayer", new String[]{"ID"}, null, null, null, null, var4_4, var5_8);
            try {
                if (var4_4.moveToFirst()) {
                    do {
                        var3_3 = var4_4;
                        var6_2.add(String.valueOf(var4_4.getLong(0)));
                        var3_3 = var4_4;
                    } while (var2_12 = var4_4.moveToNext());
                }
                if (var4_4 == null) break block15;
            }
            catch (SQLiteException var5_11) {
                ** GOTO lbl26
            }
            var4_4.close();
        }
lbl22: // 3 sources:
        do {
            return var6_2;
            break;
        } while (true);
        catch (SQLiteException var5_9) {
            var4_4 = null;
lbl26: // 2 sources:
            var3_3 = var4_4;
            bh.z("Error in peekEntries fetching entryIds: " + var5_10.getMessage());
            if (var4_4 == null) ** GOTO lbl22
            var4_4.close();
            ** continue;
        }
        catch (Throwable var4_5) {
            var3_3 = null;
lbl34: // 2 sources:
            do {
                if (var3_3 != null) {
                    var3_3.close();
                }
                throw var4_6;
                break;
            } while (true);
        }
        {
            catch (Throwable var4_7) {
                ** continue;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void g(String[] arrstring) {
        SQLiteDatabase sQLiteDatabase;
        if (arrstring == null || arrstring.length == 0 || (sQLiteDatabase = this.L("Error opening database for deleteEntries.")) == null) {
            return;
        }
        String string2 = String.format("%s in (%s)", "ID", TextUtils.join((CharSequence)",", Collections.nCopies(arrstring.length, "?")));
        try {
            sQLiteDatabase.delete("datalayer", string2, arrstring);
            return;
        }
        catch (SQLiteException var2_3) {
            bh.z("Error deleting entries " + Arrays.toString(arrstring));
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Object j(byte[] var1_1) {
        var3_8 = new ByteArrayInputStream((byte[])var1_1);
        var1_1 = new ObjectInputStream(var3_8);
        var2_9 = var1_1.readObject();
        if (var1_1 == null) ** GOTO lbl9
        try {
            var1_1.close();
lbl9: // 2 sources:
            var3_8.close();
            return var2_9;
        }
        catch (IOException var1_7) {
            return var2_9;
        }
        catch (IOException var1_2) {
            var1_1 = null;
            ** GOTO lbl39
            catch (ClassNotFoundException var1_4) {
                var1_1 = null;
                ** GOTO lbl31
                catch (Throwable var2_10) {
                    var1_1 = null;
                    ** GOTO lbl23
                    catch (Throwable var2_12) {}
lbl23: // 2 sources:
                    if (var1_1 == null) ** GOTO lbl26
                    try {
                        var1_1.close();
lbl26: // 2 sources:
                        var3_8.close();
                    }
                    catch (IOException var1_6) {
                        throw var2_11;
                    }
                    throw var2_11;
                }
                catch (ClassNotFoundException var2_13) {}
lbl31: // 2 sources:
                if (var1_1 == null) ** GOTO lbl34
                try {
                    var1_1.close();
lbl34: // 2 sources:
                    var3_8.close();
                    return null;
                }
                catch (IOException var1_5) {
                    return null;
                }
            }
            catch (IOException var2_14) {}
lbl39: // 2 sources:
            if (var1_1 == null) ** GOTO lbl42
            try {
                var1_1.close();
lbl42: // 2 sources:
                var3_8.close();
                return null;
            }
            catch (IOException var1_3) {
                return null;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private byte[] j(Object var1_1) {
        var3_8 = new ByteArrayOutputStream();
        var2_9 = new ObjectOutputStream(var3_8);
        var2_9.writeObject(var1_1);
        var1_1 = var3_8.toByteArray();
        if (var2_9 == null) ** GOTO lbl10
        try {
            var2_9.close();
lbl10: // 2 sources:
            var3_8.close();
            return var1_1;
        }
        catch (IOException var2_11) {
            return var1_1;
        }
        catch (IOException var1_2) {
            var2_9 = null;
            ** GOTO lbl29
            catch (Throwable var1_4) {
                var2_9 = null;
                ** GOTO lbl21
                catch (Throwable var1_6) {}
lbl21: // 2 sources:
                if (var2_9 == null) ** GOTO lbl24
                try {
                    var2_9.close();
lbl24: // 2 sources:
                    var3_8.close();
                }
                catch (IOException var2_10) {
                    throw var1_5;
                }
                throw var1_5;
            }
            catch (IOException var1_7) {}
lbl29: // 2 sources:
            if (var2_9 == null) ** GOTO lbl32
            try {
                var2_9.close();
lbl32: // 2 sources:
                var3_8.close();
                return null;
            }
            catch (IOException var1_3) {
                return null;
            }
        }
    }

    private List<DataLayer.a> ks() {
        try {
            this.u(this.Wv.currentTimeMillis());
            List<DataLayer.a> list = this.b(this.kt());
            return list;
        }
        finally {
            this.kv();
        }
    }

    private List<b> kt() {
        ArrayList<b> arrayList;
        SQLiteDatabase sQLiteDatabase = this.L("Error opening database for loadSerialized.");
        arrayList = new ArrayList<b>();
        if (sQLiteDatabase == null) {
            return arrayList;
        }
        sQLiteDatabase = sQLiteDatabase.query("datalayer", new String[]{"key", "value"}, null, null, null, null, "ID", null);
        try {
            while (sQLiteDatabase.moveToNext()) {
                arrayList.add(new b(sQLiteDatabase.getString(0), sQLiteDatabase.getBlob(1)));
            }
        }
        finally {
            sQLiteDatabase.close();
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int ku() {
        int n2;
        SQLiteDatabase sQLiteDatabase;
        int n3;
        block6 : {
            long l2;
            SQLiteDatabase sQLiteDatabase2 = null;
            SQLiteDatabase sQLiteDatabase3 = null;
            n2 = 0;
            n3 = 0;
            sQLiteDatabase = this.L("Error opening database for getNumStoredEntries.");
            if (sQLiteDatabase == null) return n3;
            try {
                sQLiteDatabase3 = sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT COUNT(*) from datalayer", null);
                sQLiteDatabase2 = sQLiteDatabase;
                if (!sQLiteDatabase.moveToFirst()) break block6;
                sQLiteDatabase3 = sQLiteDatabase;
                sQLiteDatabase2 = sQLiteDatabase;
                l2 = sQLiteDatabase.getLong(0);
            }
            catch (SQLiteException var6_2) {
                block7 : {
                    sQLiteDatabase2 = sQLiteDatabase3;
                    try {
                        bh.z("Error getting numStoredEntries");
                        if (sQLiteDatabase3 != null) break block7;
                    }
                    catch (Throwable var5_4) {
                        if (sQLiteDatabase2 == null) throw var5_4;
                        {
                            sQLiteDatabase2.close();
                        }
                        throw var5_4;
                    }
                    return n3;
                }
                sQLiteDatabase3.close();
                return 0;
            }
            n2 = (int)l2;
        }
        n3 = n2;
        if (sQLiteDatabase == null) return n3;
        {
            sQLiteDatabase.close();
            return n2;
        }
    }

    private void kv() {
        try {
            this.XD.close();
            return;
        }
        catch (SQLiteException var1_1) {
            return;
        }
    }

    static /* synthetic */ String kw() {
        return XB;
    }

    private void u(long l2) {
        SQLiteDatabase sQLiteDatabase = this.L("Error opening database for deleteOlderThan.");
        if (sQLiteDatabase == null) {
            return;
        }
        try {
            int n2 = sQLiteDatabase.delete("datalayer", "expires <= ?", new String[]{Long.toString(l2)});
            bh.y("Deleted " + n2 + " expired items");
            return;
        }
        catch (SQLiteException var4_3) {
            bh.z("Error deleting old entries.");
            return;
        }
    }

    @Override
    public void a(final DataLayer.c.a a2) {
        this.XC.execute(new Runnable(){

            @Override
            public void run() {
                a2.a(v.this.ks());
            }
        });
    }

    @Override
    public void a(final List<DataLayer.a> list, final long l2) {
        list = this.c(list);
        this.XC.execute(new Runnable(){

            @Override
            public void run() {
                v.this.b(list, l2);
            }
        });
    }

    @Override
    public void bx(final String string2) {
        this.XC.execute(new Runnable(){

            @Override
            public void run() {
                v.this.by(string2);
            }
        });
    }

    class a
    extends SQLiteOpenHelper {
        a(Context context, String string2) {
            super(context, string2, null, 1);
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            String[] arrstring;
            int n2;
            sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
            HashSet<String> hashSet = new HashSet<String>();
            try {
                arrstring = sQLiteDatabase.getColumnNames();
                n2 = 0;
            }
            catch (Throwable var3_3) {
                sQLiteDatabase.close();
                throw var3_3;
            }
            do {
                if (n2 >= arrstring.length) break;
                hashSet.add(arrstring[n2]);
                ++n2;
            } while (true);
            sQLiteDatabase.close();
            if (!(hashSet.remove("key") && hashSet.remove("value") && hashSet.remove("ID") && hashSet.remove("expires"))) {
                throw new SQLiteException("Database column missing");
            }
            if (!hashSet.isEmpty()) {
                throw new SQLiteException("Database has extra columns");
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive exception aggregation
         */
        private boolean a(String var1_1, SQLiteDatabase var2_6) {
            block11 : {
                var4_8 = null;
                var2_6 = var2_6.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{var1_1}, null, null, null);
                try {
                    var3_10 = var2_6.moveToFirst();
                    if (var2_6 == null) break block11;
                }
                catch (Throwable var1_4) {
                    ** GOTO lbl20
                }
                var2_6.close();
            }
            return var3_10;
            catch (SQLiteException var2_7) {
                block12 : {
                    var2_6 = null;
lbl13: // 3 sources:
                    bh.z("Error querying for table " + var1_1);
                    if (var2_6 == null) break block12;
                    var2_6.close();
                }
                return false;
            }
            catch (Throwable var1_2) {
                var2_6 = var4_8;
lbl20: // 3 sources:
                do {
                    if (var2_6 != null) {
                        var2_6.close();
                    }
                    throw var1_3;
                    break;
                } while (true);
            }
            {
                catch (Throwable var1_5) {
                    ** continue;
                }
            }
            catch (SQLiteException var4_9) {
                ** GOTO lbl13
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public SQLiteDatabase getWritableDatabase() {
            SQLiteDatabase sQLiteDatabase;
            SQLiteDatabase sQLiteDatabase2 = null;
            try {
                sQLiteDatabase2 = sQLiteDatabase = super.getWritableDatabase();
            }
            catch (SQLiteException var2_3) {
                v.this.mContext.getDatabasePath("google_tagmanager.db").delete();
            }
            sQLiteDatabase = sQLiteDatabase2;
            if (sQLiteDatabase2 != null) return sQLiteDatabase;
            return super.getWritableDatabase();
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            ak.G(sQLiteDatabase.getPath());
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void onOpen(SQLiteDatabase var1_1) {
            if (Build.VERSION.SDK_INT < 15) {
                var2_3 = var1_1.rawQuery("PRAGMA journal_mode=memory", null);
                var2_3.moveToFirst();
            }
            if (!this.a("datalayer", var1_1)) {
                var1_1.execSQL(v.kw());
                return;
            }
            ** GOTO lbl12
            finally {
                var2_3.close();
            }
lbl12: // 1 sources:
            this.a(var1_1);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n2, int n3) {
        }
    }

    private static class b {
        final byte[] XK;
        final String Xy;

        b(String string2, byte[] arrby) {
            this.Xy = string2;
            this.XK = arrby;
        }

        public String toString() {
            return "KeyAndSerialized: key = " + this.Xy + " serialized hash = " + Arrays.hashCode(this.XK);
        }
    }

}

