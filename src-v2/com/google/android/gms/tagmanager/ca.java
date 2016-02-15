/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.CursorWindow
 *  android.database.sqlite.SQLiteCursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  org.apache.http.client.HttpClient
 *  org.apache.http.impl.client.DefaultHttpClient
 */
package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.internal.gl;
import com.google.android.gms.internal.gn;
import com.google.android.gms.tagmanager.ab;
import com.google.android.gms.tagmanager.ak;
import com.google.android.gms.tagmanager.ap;
import com.google.android.gms.tagmanager.at;
import com.google.android.gms.tagmanager.au;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cx;
import com.google.android.gms.tagmanager.da;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

class ca
implements at {
    private static final String vx = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", "gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time");
    private gl Wv;
    private final b YI;
    private volatile ab YJ;
    private final au YK;
    private final Context mContext;
    private final String vA;
    private long vC;
    private final int vD;

    ca(au au2, Context context) {
        this(au2, context, "gtm_urls.db", 2000);
    }

    ca(au au2, Context context, String string2, int n2) {
        this.mContext = context.getApplicationContext();
        this.vA = string2;
        this.YK = au2;
        this.Wv = gn.ft();
        this.YI = new b(this.mContext, this.vA);
        this.YJ = new da((HttpClient)new DefaultHttpClient(), this.mContext, new a());
        this.vC = 0;
        this.vD = n2;
    }

    private SQLiteDatabase L(String string2) {
        try {
            SQLiteDatabase sQLiteDatabase = this.YI.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException var2_3) {
            bh.z(string2);
            return null;
        }
    }

    private void c(long l2, long l3) {
        SQLiteDatabase sQLiteDatabase = this.L("Error opening database for getNumStoredHits.");
        if (sQLiteDatabase == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_first_send_time", Long.valueOf(l3));
        try {
            sQLiteDatabase.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(l2)});
            return;
        }
        catch (SQLiteException var5_4) {
            bh.z("Error setting HIT_FIRST_DISPATCH_TIME for hitId: " + l2);
            this.v(l2);
            return;
        }
    }

    private void cV() {
        int n2 = this.cX() - this.vD + 1;
        if (n2 > 0) {
            List<String> list = this.s(n2);
            bh.y("Store full, deleting " + list.size() + " hits to make room.");
            this.a(list.toArray(new String[0]));
        }
    }

    private void f(long l2, String string2) {
        SQLiteDatabase sQLiteDatabase = this.L("Error opening database for putHit");
        if (sQLiteDatabase == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_time", Long.valueOf(l2));
        contentValues.put("hit_url", string2);
        contentValues.put("hit_first_send_time", Integer.valueOf(0));
        try {
            sQLiteDatabase.insert("gtm_hits", null, contentValues);
            this.YK.r(false);
            return;
        }
        catch (SQLiteException var3_3) {
            bh.z("Error storing hit");
            return;
        }
    }

    static /* synthetic */ String kS() {
        return vx;
    }

    private void v(long l2) {
        this.a(new String[]{String.valueOf(l2)});
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void a(String[] object) {
        boolean bl2;
        block4 : {
            block3 : {
                SQLiteDatabase sQLiteDatabase;
                bl2 = true;
                if (object == null) return;
                if (object.length == 0 || (sQLiteDatabase = this.L("Error opening database for deleteHits.")) == null) {
                    return;
                }
                String string2 = String.format("HIT_ID in (%s)", TextUtils.join((CharSequence)",", Collections.nCopies(object.length, "?")));
                try {
                    sQLiteDatabase.delete("gtm_hits", string2, (String[])object);
                    object = this.YK;
                    if (this.cX() != 0) break block3;
                    break block4;
                }
                catch (SQLiteException var1_2) {
                    bh.z("Error deleting hits");
                    return;
                }
            }
            bl2 = false;
        }
        object.r(bl2);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void bW() {
        bh.y("GTM Dispatch running...");
        if (!this.YJ.ch()) {
            return;
        }
        List<ap> list = this.t(40);
        if (list.isEmpty()) {
            bh.y("...nothing to dispatch");
            this.YK.r(true);
            return;
        }
        this.YJ.d(list);
        if (this.kR() <= 0) return;
        cx.lG().bW();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    int cW() {
        boolean bl2 = true;
        long l2 = this.Wv.currentTimeMillis();
        if (l2 <= this.vC + 86400000) {
            return 0;
        }
        this.vC = l2;
        Object object = this.L("Error opening database for deleteStaleHits.");
        if (object == null) return 0;
        int n2 = object.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.Wv.currentTimeMillis() - 2592000000L)});
        object = this.YK;
        if (this.cX() != 0) {
            bl2 = false;
        }
        object.r(bl2);
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int cX() {
        int n2;
        SQLiteDatabase sQLiteDatabase;
        int n3;
        block6 : {
            long l2;
            SQLiteDatabase sQLiteDatabase2 = null;
            SQLiteDatabase sQLiteDatabase3 = null;
            n2 = 0;
            n3 = 0;
            sQLiteDatabase = this.L("Error opening database for getNumStoredHits.");
            if (sQLiteDatabase == null) return n3;
            try {
                sQLiteDatabase3 = sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT COUNT(*) from gtm_hits", null);
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
                        bh.z("Error getting numStoredHits");
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

    @Override
    public void e(long l2, String string2) {
        this.cW();
        this.cV();
        this.f(l2, string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    int kR() {
        var4_1 = null;
        var3_3 = this.L("Error opening database for getNumStoredHits.");
        if (var3_3 == null) {
            return 0;
        }
        var3_3 = var3_3.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", null, null, null, null);
        var1_7 = var2_6 = var3_3.getCount();
        if (var3_3 == null) return var1_7;
        var3_3.close();
        return var2_6;
        catch (SQLiteException var3_4) {
            var3_3 = null;
            ** GOTO lbl22
            catch (Throwable var3_5) {}
            ** GOTO lbl-1000
            catch (Throwable var5_8) {
                var4_1 = var3_3;
                var3_3 = var5_8;
                ** GOTO lbl-1000
            }
            catch (SQLiteException var4_2) {}
lbl22: // 2 sources:
            try {
                bh.z("Error getting num untried hits");
                if (var3_3 == null) return 0;
            }
            catch (Throwable var5_9) {
                var4_1 = var3_3;
                var3_3 = var5_9;
            }
            var3_3.close();
            return 0;
        }
lbl-1000: // 3 sources:
        {
            if (var4_1 == null) throw var3_3;
            var4_1.close();
            throw var3_3;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    List<String> s(int var1_1) {
        block15 : {
            var6_2 = new ArrayList<String>();
            if (var1_1 <= 0) {
                bh.z("Invalid maxHits specified. Skipping");
                return var6_2;
            }
            var3_3 = this.L("Error opening database for peekHitIds.");
            if (var3_3 == null) {
                return var6_2;
            }
            var4_4 = String.format("%s ASC", new Object[]{"hit_id"});
            var5_8 = Integer.toString(var1_1);
            var3_3 = var4_4 = var3_3.query("gtm_hits", new String[]{"hit_id"}, null, null, null, null, var4_4, var5_8);
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
            bh.z("Error in peekHits fetching hitIds: " + var5_10.getMessage());
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
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public List<ap> t(int var1_1) {
        block35 : {
            block34 : {
                var5_2 = new ArrayList<ap>();
                var8_4 = this.L("Error opening database for peekHits");
                if (var8_4 == null) {
                    var6_5 = var5_2;
                    do {
                        return var6_5;
                        break;
                    } while (true);
                }
                var4_13 = null;
                var6_6 = String.format("%s ASC", new Object[]{"hit_id"});
                var7_18 = Integer.toString(var1_1);
                var4_13 = var6_6 = var8_4.query("gtm_hits", new String[]{"hit_id", "hit_time", "hit_first_send_time"}, null, null, null, null, (String)var6_6, (String)var7_18);
                var7_18 = new ArrayList<E>();
                if (var4_13.moveToFirst()) {
                    do {
                        var7_18.add(new ap(var4_13.getLong(0), var4_13.getLong(1), var4_13.getLong(2)));
                    } while (var3_20 = var4_13.moveToNext());
                }
                if (var4_13 == null) break block34;
                var4_13.close();
            }
            var5_2 = var4_13;
            var6_6 = String.format("%s ASC", new Object[]{"hit_id"});
            var5_2 = var4_13;
            var9_21 = Integer.toString(var1_1);
            var5_2 = var4_13;
            var6_6 = var8_4.query("gtm_hits", new String[]{"hit_id", "hit_url"}, null, null, null, null, (String)var6_6, var9_21);
            if (!var6_6.moveToFirst()) ** GOTO lbl-1000
            var1_1 = 0;
lbl33: // 2 sources:
            if (((SQLiteCursor)var6_6).getWindow().getNumRows() <= 0) break block35;
            ((ap)var7_18.get(var1_1)).K(var6_6.getString(1));
lbl35: // 2 sources:
            while (!(var3_20 = var6_6.moveToNext())) lbl-1000: // 2 sources:
            {
                if (var6_6 == null) break block36;
            }
            {
                block36 : {
                    var6_6.close();
                }
                return var7_18;
                break;
            }
            catch (SQLiteException var6_7) {
                var7_19 = null;
                var4_13 = var5_2;
                var5_2 = var7_19;
lbl47: // 4 sources:
                bh.z("Error in peekHits fetching hitIds: " + var6_5.getMessage());
                var6_5 = var4_13;
                if (var5_2 == null) ** continue;
                var5_2.close();
                return var4_13;
            }
            catch (Throwable var6_8) {
                var5_2 = var4_13;
                var4_13 = var6_8;
lbl55: // 3 sources:
                do {
                    if (var5_2 != null) {
                        var5_2.close();
                    }
                    throw var4_13;
                    break;
                } while (true);
            }
        }
        try {
            bh.z(String.format("HitString for hitId %d too large.  Hit will be deleted.", new Object[]{((ap)var7_18.get(var1_1)).cP()}));
            ** GOTO lbl35
        }
        catch (SQLiteException var5_3) {
            var4_13 = var6_6;
            var6_6 = var5_3;
lbl66: // 2 sources:
            do {
                var5_2 = var4_13;
                try {
                    bh.z("Error in peekHits fetching hit url: " + var6_6.getMessage());
                    var5_2 = var4_13;
                    var6_6 = new ArrayList<E>();
                    var1_1 = 0;
                    var5_2 = var4_13;
                    var7_18 = var7_18.iterator();
                    do {
                        var5_2 = var4_13;
                        if (var7_18.hasNext()) {
                            var5_2 = var4_13;
                            var8_4 = (ap)var7_18.next();
                            var5_2 = var4_13;
                            var3_20 = TextUtils.isEmpty((CharSequence)var8_4.kE());
                            var2_22 = var1_1;
                            if (!var3_20) break block37;
                            if (var1_1 == 0) break block38;
                        }
                        if (var4_13 == null) break block39;
                        break;
                    } while (true);
                }
                catch (Throwable var4_14) lbl-1000: // 2 sources:
                {
                    do {
                        if (var5_2 != null) {
                            var5_2.close();
                        }
                        throw var4_15;
                        break;
                    } while (true);
                }
                {
                    block37 : {
                        block38 : {
                            block39 : {
                                var4_13.close();
                            }
                            return var6_6;
                        }
                        var2_22 = 1;
                    }
                    var5_2 = var4_13;
                    var6_6.add(var8_4);
                    var1_1 = var2_22;
                    continue;
                }
                break;
            } while (true);
        }
        catch (Throwable var4_16) {
            var5_2 = var6_6;
            ** continue;
        }
        catch (SQLiteException var6_9) {
            ** continue;
        }
        catch (Throwable var6_10) {
            var5_2 = var4_13;
            var4_13 = var6_10;
            ** GOTO lbl55
        }
        {
            catch (Throwable var4_17) {
                ** continue;
            }
        }
        {
            catch (SQLiteException var6_11) {
                var7_18 = var4_13;
                var4_13 = var5_2;
                var5_2 = var7_18;
                ** GOTO lbl47
            }
            catch (SQLiteException var6_12) {
                var5_2 = var4_13;
                var4_13 = var7_18;
                ** GOTO lbl47
            }
        }
        ++var1_1;
        ** GOTO lbl33
    }

    class a
    implements da.a {
        a() {
        }

        @Override
        public void a(ap ap2) {
            ca.this.v(ap2.cP());
        }

        @Override
        public void b(ap ap2) {
            ca.this.v(ap2.cP());
            bh.y("Permanent failure dispatching hitId: " + ap2.cP());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void c(ap ap2) {
            long l2 = ap2.kD();
            if (l2 == 0) {
                ca.this.c(ap2.cP(), ca.this.Wv.currentTimeMillis());
                return;
            } else {
                if (l2 + 14400000 >= ca.this.Wv.currentTimeMillis()) return;
                {
                    ca.this.v(ap2.cP());
                    bh.y("Giving up on failed hitId: " + ap2.cP());
                    return;
                }
            }
        }
    }

    class b
    extends SQLiteOpenHelper {
        private boolean vF;
        private long vG;

        b(Context context, String string2) {
            super(context, string2, null, 1);
            this.vG = 0;
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            String[] arrstring;
            int n2;
            sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
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
            if (!(hashSet.remove("hit_id") && hashSet.remove("hit_url") && hashSet.remove("hit_time") && hashSet.remove("hit_first_send_time"))) {
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
            if (this.vF && this.vG + 3600000 > ca.this.Wv.currentTimeMillis()) {
                throw new SQLiteException("Database creation failed");
            }
            SQLiteDatabase sQLiteDatabase2 = null;
            this.vF = true;
            this.vG = ca.this.Wv.currentTimeMillis();
            try {
                sQLiteDatabase2 = sQLiteDatabase = super.getWritableDatabase();
            }
            catch (SQLiteException var2_3) {
                ca.this.mContext.getDatabasePath(ca.this.vA).delete();
            }
            sQLiteDatabase = sQLiteDatabase2;
            if (sQLiteDatabase2 == null) {
                sQLiteDatabase = super.getWritableDatabase();
            }
            this.vF = false;
            return sQLiteDatabase;
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
            if (!this.a("gtm_hits", var1_1)) {
                var1_1.execSQL(ca.kS());
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

}

