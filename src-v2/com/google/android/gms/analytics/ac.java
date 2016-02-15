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
package com.google.android.gms.analytics;

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
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.ab;
import com.google.android.gms.analytics.ah;
import com.google.android.gms.analytics.d;
import com.google.android.gms.analytics.e;
import com.google.android.gms.analytics.i;
import com.google.android.gms.analytics.n;
import com.google.android.gms.analytics.p;
import com.google.android.gms.analytics.x;
import com.google.android.gms.analytics.y;
import com.google.android.gms.internal.ef;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

class ac
implements d {
    private static final String vx = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    private final Context mContext;
    private final e sO;
    private i tg;
    private final String vA;
    private ab vB;
    private long vC;
    private final int vD;
    private final a vy;
    private volatile n vz;

    ac(e e2, Context context) {
        this(e2, context, "google_analytics_v4.db", 2000);
    }

    ac(e e2, Context context, String string2, int n2) {
        this.mContext = context.getApplicationContext();
        this.vA = string2;
        this.sO = e2;
        this.tg = new i(){

            @Override
            public long currentTimeMillis() {
                return System.currentTimeMillis();
            }
        };
        this.vy = new a(this.mContext, this.vA);
        this.vz = new ah((HttpClient)new DefaultHttpClient(), this.mContext);
        this.vC = 0;
        this.vD = n2;
    }

    private SQLiteDatabase L(String string2) {
        try {
            SQLiteDatabase sQLiteDatabase = this.vy.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException var2_3) {
            aa.z(string2);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(Map<String, String> object, long l2, String string2) {
        SQLiteDatabase sQLiteDatabase = this.L("Error opening database for putHit");
        if (sQLiteDatabase == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", ac.w(object));
        contentValues.put("hit_time", Long.valueOf(l2));
        if (object.containsKey("AppUID")) {
            try {
                l2 = Long.parseLong((String)object.get("AppUID"));
            }
            catch (NumberFormatException var1_2) {
                l2 = 0;
            }
        } else {
            l2 = 0;
        }
        contentValues.put("hit_app_id", Long.valueOf(l2));
        object = string2;
        if (string2 == null) {
            object = "http://www.google-analytics.com/collect";
        }
        if (object.length() == 0) {
            aa.z("Empty path: not sending hit");
            return;
        }
        contentValues.put("hit_url", (String)object);
        try {
            sQLiteDatabase.insert("hits2", null, contentValues);
            this.sO.r(false);
            return;
        }
        catch (SQLiteException var1_3) {
            aa.z("Error storing hit");
            return;
        }
    }

    private void a(Map<String, String> map, Collection<ef> object) {
        String string2 = "&_v".substring(1);
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                ef ef2 = (ef)object.next();
                if (!"appendVersion".equals(ef2.getId())) continue;
                map.put(string2, ef2.getValue());
                break;
            }
        }
    }

    private void cV() {
        int n2 = this.cX() - this.vD + 1;
        if (n2 > 0) {
            List<String> list = this.s(n2);
            aa.y("Store full, deleting " + list.size() + " hits to make room.");
            this.a(list.toArray(new String[0]));
        }
    }

    static /* synthetic */ String cY() {
        return vx;
    }

    static String w(Map<String, String> object) {
        ArrayList<String> arrayList = new ArrayList<String>(object.size());
        for (Map.Entry entry : object.entrySet()) {
            arrayList.add(y.encode((String)entry.getKey()) + "=" + y.encode((String)entry.getValue()));
        }
        return TextUtils.join((CharSequence)"&", arrayList);
    }

    @Override
    public void a(Map<String, String> map, long l2, String string2, Collection<ef> collection) {
        this.cW();
        this.cV();
        this.a(map, collection);
        this.a(map, l2, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void a(String[] arrstring) {
        Object object;
        boolean bl2;
        block4 : {
            block3 : {
                bl2 = true;
                if (arrstring == null || arrstring.length == 0) {
                    aa.z("Empty hitIds passed to deleteHits.");
                    return;
                }
                object = this.L("Error opening database for deleteHits.");
                if (object == null) return;
                String string2 = String.format("HIT_ID in (%s)", TextUtils.join((CharSequence)",", Collections.nCopies(arrstring.length, "?")));
                try {
                    object.delete("hits2", string2, arrstring);
                    object = this.sO;
                    if (this.cX() != 0) break block3;
                    break block4;
                }
                catch (SQLiteException var3_4) {
                    aa.z("Error deleting hits " + arrstring);
                    return;
                }
            }
            bl2 = false;
        }
        object.r(bl2);
    }

    @Deprecated
    void b(Collection<x> object) {
        if (object == null || object.isEmpty()) {
            aa.z("Empty/Null collection passed to deleteHits.");
            return;
        }
        String[] arrstring = new String[object.size()];
        object = object.iterator();
        int n2 = 0;
        while (object.hasNext()) {
            arrstring[n2] = String.valueOf(((x)object.next()).cP());
            ++n2;
        }
        this.a(arrstring);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void bW() {
        boolean bl2 = true;
        aa.y("Dispatch running...");
        if (!this.vz.ch()) {
            return;
        }
        List<x> list = this.t(40);
        if (list.isEmpty()) {
            aa.y("...nothing to dispatch");
            this.sO.r(true);
            return;
        }
        if (this.vB == null) {
            this.vB = new ab("_t=dispatch&_v=ma4.0.1", true);
        }
        if (this.cX() > list.size()) {
            bl2 = false;
        }
        int n2 = this.vz.a(list, this.vB, bl2);
        aa.y("sent " + n2 + " of " + list.size() + " hits");
        this.b(list.subList(0, Math.min(n2, list.size())));
        if (n2 == list.size() && this.cX() > 0) {
            GoogleAnalytics.getInstance(this.mContext).dispatchLocalHits();
            return;
        }
        this.vB = null;
    }

    @Override
    public n bX() {
        return this.vz;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    int cW() {
        boolean bl2 = true;
        long l2 = this.tg.currentTimeMillis();
        if (l2 <= this.vC + 86400000) {
            return 0;
        }
        this.vC = l2;
        Object object = this.L("Error opening database for deleteStaleHits.");
        if (object == null) return 0;
        int n2 = object.delete("hits2", "HIT_TIME < ?", new String[]{Long.toString(this.tg.currentTimeMillis() - 2592000000L)});
        object = this.sO;
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
                sQLiteDatabase3 = sQLiteDatabase = sQLiteDatabase.rawQuery("SELECT COUNT(*) from hits2", null);
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
                        aa.z("Error getting numStoredHits");
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

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void j(long l2) {
        boolean bl2 = true;
        Object object = this.L("Error opening database for clearHits");
        if (object == null) return;
        if (l2 == 0) {
            object.delete("hits2", null, null);
        } else {
            object.delete("hits2", "hit_app_id = ?", new String[]{Long.valueOf(l2).toString()});
        }
        object = this.sO;
        if (this.cX() != 0) {
            bl2 = false;
        }
        object.r(bl2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    List<String> s(int var1_1) {
        block15 : {
            var6_2 = new ArrayList<String>();
            if (var1_1 <= 0) {
                aa.z("Invalid maxHits specified. Skipping");
                return var6_2;
            }
            var3_3 = this.L("Error opening database for peekHitIds.");
            if (var3_3 == null) {
                return var6_2;
            }
            var4_4 = String.format("%s ASC", new Object[]{"hit_id"});
            var5_8 = Integer.toString(var1_1);
            var3_3 = var4_4 = var3_3.query("hits2", new String[]{"hit_id"}, null, null, null, null, var4_4, var5_8);
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
            aa.z("Error in peekHits fetching hitIds: " + var5_10.getMessage());
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
    public List<x> t(int var1_1) {
        block35 : {
            block34 : {
                var5_2 = new ArrayList<x>();
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
                var4_13 = var6_6 = var8_4.query("hits2", new String[]{"hit_id", "hit_time"}, null, null, null, null, (String)var6_6, (String)var7_18);
                var7_18 = new ArrayList<E>();
                if (var4_13.moveToFirst()) {
                    do {
                        var7_18.add(new x(null, var4_13.getLong(0), var4_13.getLong(1)));
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
            var6_6 = var8_4.query("hits2", new String[]{"hit_id", "hit_string", "hit_url"}, null, null, null, null, (String)var6_6, var9_21);
            if (!var6_6.moveToFirst()) ** GOTO lbl-1000
            var1_1 = 0;
lbl33: // 2 sources:
            if (((SQLiteCursor)var6_6).getWindow().getNumRows() <= 0) break block35;
            ((x)var7_18.get(var1_1)).J(var6_6.getString(1));
            ((x)var7_18.get(var1_1)).K(var6_6.getString(2));
lbl36: // 2 sources:
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
lbl48: // 4 sources:
                aa.z("Error in peekHits fetching hitIds: " + var6_5.getMessage());
                var6_5 = var4_13;
                if (var5_2 == null) ** continue;
                var5_2.close();
                return var4_13;
            }
            catch (Throwable var6_8) {
                var5_2 = var4_13;
                var4_13 = var6_8;
lbl56: // 3 sources:
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
            aa.z(String.format("HitString for hitId %d too large.  Hit will be deleted.", new Object[]{((x)var7_18.get(var1_1)).cP()}));
            ** GOTO lbl36
        }
        catch (SQLiteException var5_3) {
            var4_13 = var6_6;
            var6_6 = var5_3;
lbl67: // 2 sources:
            do {
                var5_2 = var4_13;
                try {
                    aa.z("Error in peekHits fetching hitString: " + var6_6.getMessage());
                    var5_2 = var4_13;
                    var6_6 = new ArrayList<E>();
                    var1_1 = 0;
                    var5_2 = var4_13;
                    var7_18 = var7_18.iterator();
                    do {
                        var5_2 = var4_13;
                        if (var7_18.hasNext()) {
                            var5_2 = var4_13;
                            var8_4 = (x)var7_18.next();
                            var5_2 = var4_13;
                            var3_20 = TextUtils.isEmpty((CharSequence)var8_4.cO());
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
            ** GOTO lbl56
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
                ** GOTO lbl48
            }
            catch (SQLiteException var6_12) {
                var5_2 = var4_13;
                var4_13 = var7_18;
                ** GOTO lbl48
            }
        }
        ++var1_1;
        ** GOTO lbl33
    }

    class a
    extends SQLiteOpenHelper {
        private boolean vF;
        private long vG;

        a(Context context, String string2) {
            super(context, string2, null, 1);
            this.vG = 0;
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            String[] arrstring;
            int n2;
            int n3 = 0;
            Cursor cursor = sQLiteDatabase.rawQuery("SELECT * FROM hits2 WHERE 0", null);
            HashSet<String> hashSet = new HashSet<String>();
            try {
                arrstring = cursor.getColumnNames();
                n2 = 0;
            }
            catch (Throwable var1_2) {
                cursor.close();
                throw var1_2;
            }
            do {
                if (n2 >= arrstring.length) break;
                hashSet.add(arrstring[n2]);
                ++n2;
            } while (true);
            cursor.close();
            if (!(hashSet.remove("hit_id") && hashSet.remove("hit_url") && hashSet.remove("hit_string") && hashSet.remove("hit_time"))) {
                throw new SQLiteException("Database column missing");
            }
            n2 = n3;
            if (!hashSet.remove("hit_app_id")) {
                n2 = 1;
            }
            if (!hashSet.isEmpty()) {
                throw new SQLiteException("Database has extra columns");
            }
            if (n2 != 0) {
                sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id");
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
                    aa.z("Error querying for table " + var1_1);
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
            if (this.vF && this.vG + 3600000 > ac.this.tg.currentTimeMillis()) {
                throw new SQLiteException("Database creation failed");
            }
            SQLiteDatabase sQLiteDatabase2 = null;
            this.vF = true;
            this.vG = ac.this.tg.currentTimeMillis();
            try {
                sQLiteDatabase2 = sQLiteDatabase = super.getWritableDatabase();
            }
            catch (SQLiteException var2_3) {
                ac.this.mContext.getDatabasePath(ac.this.vA).delete();
            }
            sQLiteDatabase = sQLiteDatabase2;
            if (sQLiteDatabase2 == null) {
                sQLiteDatabase = super.getWritableDatabase();
            }
            this.vF = false;
            return sQLiteDatabase;
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            p.G(sQLiteDatabase.getPath());
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
            if (!this.a("hits2", var1_1)) {
                var1_1.execSQL(ac.cY());
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

