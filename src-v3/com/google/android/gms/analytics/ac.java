package com.google.android.gms.analytics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.ef;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.impl.client.DefaultHttpClient;

class ac implements C0206d {
    private static final String vx;
    private final Context mContext;
    private final C0207e sO;
    private C0210i tg;
    private final String vA;
    private ab vB;
    private long vC;
    private final int vD;
    private final C0201a vy;
    private volatile C0215n vz;

    /* renamed from: com.google.android.gms.analytics.ac.a */
    class C0201a extends SQLiteOpenHelper {
        final /* synthetic */ ac vE;
        private boolean vF;
        private long vG;

        C0201a(ac acVar, Context context, String str) {
            this.vE = acVar;
            super(context, str, null, 1);
            this.vG = 0;
        }

        private void m37a(SQLiteDatabase sQLiteDatabase) {
            Object obj = null;
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM hits2 WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (hashSet.remove("hit_id") && hashSet.remove("hit_url") && hashSet.remove("hit_string") && hashSet.remove("hit_time")) {
                    if (!hashSet.remove("hit_app_id")) {
                        obj = 1;
                    }
                    if (!hashSet.isEmpty()) {
                        throw new SQLiteException("Database has extra columns");
                    } else if (obj != null) {
                        sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id");
                        return;
                    } else {
                        return;
                    }
                }
                throw new SQLiteException("Database column missing");
            } finally {
                rawQuery.close();
            }
        }

        private boolean m38a(String str, SQLiteDatabase sQLiteDatabase) {
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
                        aa.m35z("Error querying for table " + str);
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
                aa.m35z("Error querying for table " + str);
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
            if (!this.vF || this.vG + 3600000 <= this.vE.tg.currentTimeMillis()) {
                SQLiteDatabase sQLiteDatabase = null;
                this.vF = true;
                this.vG = this.vE.tg.currentTimeMillis();
                try {
                    sQLiteDatabase = super.getWritableDatabase();
                } catch (SQLiteException e) {
                    this.vE.mContext.getDatabasePath(this.vE.vA).delete();
                }
                if (sQLiteDatabase == null) {
                    sQLiteDatabase = super.getWritableDatabase();
                }
                this.vF = false;
                return sQLiteDatabase;
            }
            throw new SQLiteException("Database creation failed");
        }

        public void onCreate(SQLiteDatabase db) {
            C0217p.m65G(db.getPath());
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
            if (m38a("hits2", db)) {
                m37a(db);
            } else {
                db.execSQL(ac.vx);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    /* renamed from: com.google.android.gms.analytics.ac.1 */
    class C07671 implements C0210i {
        final /* synthetic */ ac vE;

        C07671(ac acVar) {
            this.vE = acVar;
        }

        public long currentTimeMillis() {
            return System.currentTimeMillis();
        }
    }

    static {
        vx = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[]{"hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id"});
    }

    ac(C0207e c0207e, Context context) {
        this(c0207e, context, "google_analytics_v4.db", GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS);
    }

    ac(C0207e c0207e, Context context, String str, int i) {
        this.mContext = context.getApplicationContext();
        this.vA = str;
        this.sO = c0207e;
        this.tg = new C07671(this);
        this.vy = new C0201a(this, this.mContext, this.vA);
        this.vz = new ah(new DefaultHttpClient(), this.mContext);
        this.vC = 0;
        this.vD = i;
    }

    private SQLiteDatabase m1550L(String str) {
        try {
            return this.vy.getWritableDatabase();
        } catch (SQLiteException e) {
            aa.m35z(str);
            return null;
        }
    }

    private void m1552a(Map<String, String> map, long j, String str) {
        SQLiteDatabase L = m1550L("Error opening database for putHit");
        if (L != null) {
            long parseLong;
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_string", m1556w(map));
            contentValues.put("hit_time", Long.valueOf(j));
            if (map.containsKey("AppUID")) {
                try {
                    parseLong = Long.parseLong((String) map.get("AppUID"));
                } catch (NumberFormatException e) {
                    parseLong = 0;
                }
            } else {
                parseLong = 0;
            }
            contentValues.put("hit_app_id", Long.valueOf(parseLong));
            if (str == null) {
                str = "http://www.google-analytics.com/collect";
            }
            if (str.length() == 0) {
                aa.m35z("Empty path: not sending hit");
                return;
            }
            contentValues.put("hit_url", str);
            try {
                L.insert("hits2", null, contentValues);
                this.sO.m52r(false);
            } catch (SQLiteException e2) {
                aa.m35z("Error storing hit");
            }
        }
    }

    private void m1553a(Map<String, String> map, Collection<ef> collection) {
        String substring = "&_v".substring(1);
        if (collection != null) {
            for (ef efVar : collection) {
                if ("appendVersion".equals(efVar.getId())) {
                    map.put(substring, efVar.getValue());
                    return;
                }
            }
        }
    }

    private void cV() {
        int cX = (cX() - this.vD) + 1;
        if (cX > 0) {
            List s = m1561s(cX);
            aa.m34y("Store full, deleting " + s.size() + " hits to make room.");
            m1558a((String[]) s.toArray(new String[0]));
        }
    }

    static String m1556w(Map<String, String> map) {
        Iterable arrayList = new ArrayList(map.size());
        for (Entry entry : map.entrySet()) {
            arrayList.add(C0234y.encode((String) entry.getKey()) + "=" + C0234y.encode((String) entry.getValue()));
        }
        return TextUtils.join("&", arrayList);
    }

    public void m1557a(Map<String, String> map, long j, String str, Collection<ef> collection) {
        cW();
        cV();
        m1553a(map, collection);
        m1552a(map, j, str);
    }

    void m1558a(String[] strArr) {
        boolean z = true;
        if (strArr == null || strArr.length == 0) {
            aa.m35z("Empty hitIds passed to deleteHits.");
            return;
        }
        SQLiteDatabase L = m1550L("Error opening database for deleteHits.");
        if (L != null) {
            try {
                L.delete("hits2", String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                C0207e c0207e = this.sO;
                if (cX() != 0) {
                    z = false;
                }
                c0207e.m52r(z);
            } catch (SQLiteException e) {
                aa.m35z("Error deleting hits " + strArr);
            }
        }
    }

    @Deprecated
    void m1559b(Collection<C0233x> collection) {
        if (collection == null || collection.isEmpty()) {
            aa.m35z("Empty/Null collection passed to deleteHits.");
            return;
        }
        String[] strArr = new String[collection.size()];
        int i = 0;
        for (C0233x cP : collection) {
            int i2 = i + 1;
            strArr[i] = String.valueOf(cP.cP());
            i = i2;
        }
        m1558a(strArr);
    }

    public void bW() {
        boolean z = true;
        aa.m34y("Dispatch running...");
        if (this.vz.ch()) {
            List t = m1562t(40);
            if (t.isEmpty()) {
                aa.m34y("...nothing to dispatch");
                this.sO.m52r(true);
                return;
            }
            if (this.vB == null) {
                this.vB = new ab("_t=dispatch&_v=ma4.0.1", true);
            }
            if (cX() > t.size()) {
                z = false;
            }
            int a = this.vz.m61a(t, this.vB, z);
            aa.m34y("sent " + a + " of " + t.size() + " hits");
            m1559b(t.subList(0, Math.min(a, t.size())));
            if (a != t.size() || cX() <= 0) {
                this.vB = null;
            } else {
                GoogleAnalytics.getInstance(this.mContext).dispatchLocalHits();
            }
        }
    }

    public C0215n bX() {
        return this.vz;
    }

    int cW() {
        boolean z = true;
        long currentTimeMillis = this.tg.currentTimeMillis();
        if (currentTimeMillis <= this.vC + 86400000) {
            return 0;
        }
        this.vC = currentTimeMillis;
        SQLiteDatabase L = m1550L("Error opening database for deleteStaleHits.");
        if (L == null) {
            return 0;
        }
        int delete = L.delete("hits2", "HIT_TIME < ?", new String[]{Long.toString(this.tg.currentTimeMillis() - 2592000000L)});
        C0207e c0207e = this.sO;
        if (cX() != 0) {
            z = false;
        }
        c0207e.m52r(z);
        return delete;
    }

    int cX() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase L = m1550L("Error opening database for getNumStoredHits.");
        if (L != null) {
            try {
                cursor = L.rawQuery("SELECT COUNT(*) from hits2", null);
                if (cursor.moveToFirst()) {
                    i = (int) cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                aa.m35z("Error getting numStoredHits");
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

    public void m1560j(long j) {
        boolean z = true;
        SQLiteDatabase L = m1550L("Error opening database for clearHits");
        if (L != null) {
            if (j == 0) {
                L.delete("hits2", null, null);
            } else {
                L.delete("hits2", "hit_app_id = ?", new String[]{Long.valueOf(j).toString()});
            }
            C0207e c0207e = this.sO;
            if (cX() != 0) {
                z = false;
            }
            c0207e.m52r(z);
        }
    }

    List<String> m1561s(int i) {
        Cursor query;
        SQLiteException e;
        Throwable th;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            aa.m35z("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase L = m1550L("Error opening database for peekHitIds.");
        if (L == null) {
            return arrayList;
        }
        try {
            query = L.query("hits2", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", new Object[]{"hit_id"}), Integer.toString(i));
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
                    aa.m35z("Error in peekHits fetching hitIds: " + e.getMessage());
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
            aa.m35z("Error in peekHits fetching hitIds: " + e.getMessage());
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.android.gms.analytics.C0233x> m1562t(int r16) {
        /*
        r15 = this;
        r10 = new java.util.ArrayList;
        r10.<init>();
        r1 = "Error opening database for peekHits";
        r1 = r15.m1550L(r1);
        if (r1 != 0) goto L_0x000f;
    L_0x000d:
        r1 = r10;
    L_0x000e:
        return r1;
    L_0x000f:
        r11 = 0;
        r2 = "hits2";
        r3 = 2;
        r3 = new java.lang.String[r3];	 Catch:{ SQLiteException -> 0x00d2, all -> 0x00f7 }
        r4 = 0;
        r5 = "hit_id";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x00d2, all -> 0x00f7 }
        r4 = 1;
        r5 = "hit_time";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x00d2, all -> 0x00f7 }
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = "%s ASC";
        r9 = 1;
        r9 = new java.lang.Object[r9];	 Catch:{ SQLiteException -> 0x00d2, all -> 0x00f7 }
        r12 = 0;
        r13 = "hit_id";
        r9[r12] = r13;	 Catch:{ SQLiteException -> 0x00d2, all -> 0x00f7 }
        r8 = java.lang.String.format(r8, r9);	 Catch:{ SQLiteException -> 0x00d2, all -> 0x00f7 }
        r9 = java.lang.Integer.toString(r16);	 Catch:{ SQLiteException -> 0x00d2, all -> 0x00f7 }
        r12 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9);	 Catch:{ SQLiteException -> 0x00d2, all -> 0x00f7 }
        r11 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x0179, all -> 0x0173 }
        r11.<init>();	 Catch:{ SQLiteException -> 0x0179, all -> 0x0173 }
        r2 = r12.moveToFirst();	 Catch:{ SQLiteException -> 0x017f, all -> 0x0173 }
        if (r2 == 0) goto L_0x005d;
    L_0x0044:
        r2 = new com.google.android.gms.analytics.x;	 Catch:{ SQLiteException -> 0x017f, all -> 0x0173 }
        r3 = 0;
        r4 = 0;
        r4 = r12.getLong(r4);	 Catch:{ SQLiteException -> 0x017f, all -> 0x0173 }
        r6 = 1;
        r6 = r12.getLong(r6);	 Catch:{ SQLiteException -> 0x017f, all -> 0x0173 }
        r2.<init>(r3, r4, r6);	 Catch:{ SQLiteException -> 0x017f, all -> 0x0173 }
        r11.add(r2);	 Catch:{ SQLiteException -> 0x017f, all -> 0x0173 }
        r2 = r12.moveToNext();	 Catch:{ SQLiteException -> 0x017f, all -> 0x0173 }
        if (r2 != 0) goto L_0x0044;
    L_0x005d:
        if (r12 == 0) goto L_0x0062;
    L_0x005f:
        r12.close();
    L_0x0062:
        r10 = 0;
        r2 = "hits2";
        r3 = 3;
        r3 = new java.lang.String[r3];	 Catch:{ SQLiteException -> 0x0171 }
        r4 = 0;
        r5 = "hit_id";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0171 }
        r4 = 1;
        r5 = "hit_string";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0171 }
        r4 = 2;
        r5 = "hit_url";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0171 }
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = "%s ASC";
        r9 = 1;
        r9 = new java.lang.Object[r9];	 Catch:{ SQLiteException -> 0x0171 }
        r13 = 0;
        r14 = "hit_id";
        r9[r13] = r14;	 Catch:{ SQLiteException -> 0x0171 }
        r8 = java.lang.String.format(r8, r9);	 Catch:{ SQLiteException -> 0x0171 }
        r9 = java.lang.Integer.toString(r16);	 Catch:{ SQLiteException -> 0x0171 }
        r2 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9);	 Catch:{ SQLiteException -> 0x0171 }
        r1 = r2.moveToFirst();	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        if (r1 == 0) goto L_0x00ca;
    L_0x0097:
        r3 = r10;
    L_0x0098:
        r0 = r2;
        r0 = (android.database.sqlite.SQLiteCursor) r0;	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1 = r0;
        r1 = r1.getWindow();	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1 = r1.getNumRows();	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        if (r1 <= 0) goto L_0x00fe;
    L_0x00a6:
        r1 = r11.get(r3);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1 = (com.google.android.gms.analytics.C0233x) r1;	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r4 = 1;
        r4 = r2.getString(r4);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1.m70J(r4);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1 = r11.get(r3);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1 = (com.google.android.gms.analytics.C0233x) r1;	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r4 = 2;
        r4 = r2.getString(r4);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1.m71K(r4);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
    L_0x00c2:
        r1 = r3 + 1;
        r3 = r2.moveToNext();	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        if (r3 != 0) goto L_0x0185;
    L_0x00ca:
        if (r2 == 0) goto L_0x00cf;
    L_0x00cc:
        r2.close();
    L_0x00cf:
        r1 = r11;
        goto L_0x000e;
    L_0x00d2:
        r1 = move-exception;
        r2 = r1;
        r3 = r11;
        r1 = r10;
    L_0x00d6:
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0176 }
        r4.<init>();	 Catch:{ all -> 0x0176 }
        r5 = "Error in peekHits fetching hitIds: ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0176 }
        r2 = r2.getMessage();	 Catch:{ all -> 0x0176 }
        r2 = r4.append(r2);	 Catch:{ all -> 0x0176 }
        r2 = r2.toString();	 Catch:{ all -> 0x0176 }
        com.google.android.gms.analytics.aa.m35z(r2);	 Catch:{ all -> 0x0176 }
        if (r3 == 0) goto L_0x000e;
    L_0x00f2:
        r3.close();
        goto L_0x000e;
    L_0x00f7:
        r1 = move-exception;
    L_0x00f8:
        if (r11 == 0) goto L_0x00fd;
    L_0x00fa:
        r11.close();
    L_0x00fd:
        throw r1;
    L_0x00fe:
        r4 = "HitString for hitId %d too large.  Hit will be deleted.";
        r1 = 1;
        r5 = new java.lang.Object[r1];	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r6 = 0;
        r1 = r11.get(r3);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1 = (com.google.android.gms.analytics.C0233x) r1;	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r7 = r1.cP();	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1 = java.lang.Long.valueOf(r7);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r5[r6] = r1;	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        r1 = java.lang.String.format(r4, r5);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        com.google.android.gms.analytics.aa.m35z(r1);	 Catch:{ SQLiteException -> 0x011c, all -> 0x016e }
        goto L_0x00c2;
    L_0x011c:
        r1 = move-exception;
        r12 = r2;
    L_0x011e:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0167 }
        r2.<init>();	 Catch:{ all -> 0x0167 }
        r3 = "Error in peekHits fetching hitString: ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x0167 }
        r1 = r1.getMessage();	 Catch:{ all -> 0x0167 }
        r1 = r2.append(r1);	 Catch:{ all -> 0x0167 }
        r1 = r1.toString();	 Catch:{ all -> 0x0167 }
        com.google.android.gms.analytics.aa.m35z(r1);	 Catch:{ all -> 0x0167 }
        r2 = new java.util.ArrayList;	 Catch:{ all -> 0x0167 }
        r2.<init>();	 Catch:{ all -> 0x0167 }
        r3 = 0;
        r4 = r11.iterator();	 Catch:{ all -> 0x0167 }
    L_0x0142:
        r1 = r4.hasNext();	 Catch:{ all -> 0x0167 }
        if (r1 == 0) goto L_0x015a;
    L_0x0148:
        r1 = r4.next();	 Catch:{ all -> 0x0167 }
        r1 = (com.google.android.gms.analytics.C0233x) r1;	 Catch:{ all -> 0x0167 }
        r5 = r1.cO();	 Catch:{ all -> 0x0167 }
        r5 = android.text.TextUtils.isEmpty(r5);	 Catch:{ all -> 0x0167 }
        if (r5 == 0) goto L_0x0163;
    L_0x0158:
        if (r3 == 0) goto L_0x0162;
    L_0x015a:
        if (r12 == 0) goto L_0x015f;
    L_0x015c:
        r12.close();
    L_0x015f:
        r1 = r2;
        goto L_0x000e;
    L_0x0162:
        r3 = 1;
    L_0x0163:
        r2.add(r1);	 Catch:{ all -> 0x0167 }
        goto L_0x0142;
    L_0x0167:
        r1 = move-exception;
    L_0x0168:
        if (r12 == 0) goto L_0x016d;
    L_0x016a:
        r12.close();
    L_0x016d:
        throw r1;
    L_0x016e:
        r1 = move-exception;
        r12 = r2;
        goto L_0x0168;
    L_0x0171:
        r1 = move-exception;
        goto L_0x011e;
    L_0x0173:
        r1 = move-exception;
        r11 = r12;
        goto L_0x00f8;
    L_0x0176:
        r1 = move-exception;
        r11 = r3;
        goto L_0x00f8;
    L_0x0179:
        r1 = move-exception;
        r2 = r1;
        r3 = r12;
        r1 = r10;
        goto L_0x00d6;
    L_0x017f:
        r1 = move-exception;
        r2 = r1;
        r3 = r12;
        r1 = r11;
        goto L_0x00d6;
    L_0x0185:
        r3 = r1;
        goto L_0x0098;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.ac.t(int):java.util.List<com.google.android.gms.analytics.x>");
    }
}
