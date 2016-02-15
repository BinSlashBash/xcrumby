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
import com.google.android.gms.tagmanager.da.C0520a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.http.impl.client.DefaultHttpClient;

class ca implements at {
    private static final String vx;
    private gl Wv;
    private final C0500b YI;
    private volatile ab YJ;
    private final au YK;
    private final Context mContext;
    private final String vA;
    private long vC;
    private final int vD;

    /* renamed from: com.google.android.gms.tagmanager.ca.b */
    class C0500b extends SQLiteOpenHelper {
        final /* synthetic */ ca YL;
        private boolean vF;
        private long vG;

        C0500b(ca caVar, Context context, String str) {
            this.YL = caVar;
            super(context, str, null, 1);
            this.vG = 0;
        }

        private void m1397a(SQLiteDatabase sQLiteDatabase) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (!hashSet.remove("hit_id") || !hashSet.remove("hit_url") || !hashSet.remove("hit_time") || !hashSet.remove("hit_first_send_time")) {
                    throw new SQLiteException("Database column missing");
                } else if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
            } finally {
                rawQuery.close();
            }
        }

        private boolean m1398a(String str, SQLiteDatabase sQLiteDatabase) {
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
            if (!this.vF || this.vG + 3600000 <= this.YL.Wv.currentTimeMillis()) {
                SQLiteDatabase sQLiteDatabase = null;
                this.vF = true;
                this.vG = this.YL.Wv.currentTimeMillis();
                try {
                    sQLiteDatabase = super.getWritableDatabase();
                } catch (SQLiteException e) {
                    this.YL.mContext.getDatabasePath(this.YL.vA).delete();
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
            if (m1398a("gtm_hits", db)) {
                m1397a(db);
            } else {
                db.execSQL(ca.vx);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.ca.a */
    class C1064a implements C0520a {
        final /* synthetic */ ca YL;

        C1064a(ca caVar) {
            this.YL = caVar;
        }

        public void m2478a(ap apVar) {
            this.YL.m2489v(apVar.cP());
        }

        public void m2479b(ap apVar) {
            this.YL.m2489v(apVar.cP());
            bh.m1386y("Permanent failure dispatching hitId: " + apVar.cP());
        }

        public void m2480c(ap apVar) {
            long kD = apVar.kD();
            if (kD == 0) {
                this.YL.m2487c(apVar.cP(), this.YL.Wv.currentTimeMillis());
            } else if (kD + 14400000 < this.YL.Wv.currentTimeMillis()) {
                this.YL.m2489v(apVar.cP());
                bh.m1386y("Giving up on failed hitId: " + apVar.cP());
            }
        }
    }

    static {
        vx = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", new Object[]{"gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time"});
    }

    ca(au auVar, Context context) {
        this(auVar, context, "gtm_urls.db", GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS);
    }

    ca(au auVar, Context context, String str, int i) {
        this.mContext = context.getApplicationContext();
        this.vA = str;
        this.YK = auVar;
        this.Wv = gn.ft();
        this.YI = new C0500b(this, this.mContext, this.vA);
        this.YJ = new da(new DefaultHttpClient(), this.mContext, new C1064a(this));
        this.vC = 0;
        this.vD = i;
    }

    private SQLiteDatabase m2481L(String str) {
        try {
            return this.YI.getWritableDatabase();
        } catch (SQLiteException e) {
            bh.m1387z(str);
            return null;
        }
    }

    private void m2487c(long j, long j2) {
        SQLiteDatabase L = m2481L("Error opening database for getNumStoredHits.");
        if (L != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_first_send_time", Long.valueOf(j2));
            try {
                L.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
            } catch (SQLiteException e) {
                bh.m1387z("Error setting HIT_FIRST_DISPATCH_TIME for hitId: " + j);
                m2489v(j);
            }
        }
    }

    private void cV() {
        int cX = (cX() - this.vD) + 1;
        if (cX > 0) {
            List s = m2492s(cX);
            bh.m1386y("Store full, deleting " + s.size() + " hits to make room.");
            m2490a((String[]) s.toArray(new String[0]));
        }
    }

    private void m2488f(long j, String str) {
        SQLiteDatabase L = m2481L("Error opening database for putHit");
        if (L != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_time", Long.valueOf(j));
            contentValues.put("hit_url", str);
            contentValues.put("hit_first_send_time", Integer.valueOf(0));
            try {
                L.insert("gtm_hits", null, contentValues);
                this.YK.m1371r(false);
            } catch (SQLiteException e) {
                bh.m1387z("Error storing hit");
            }
        }
    }

    private void m2489v(long j) {
        m2490a(new String[]{String.valueOf(j)});
    }

    void m2490a(String[] strArr) {
        boolean z = true;
        if (strArr != null && strArr.length != 0) {
            SQLiteDatabase L = m2481L("Error opening database for deleteHits.");
            if (L != null) {
                try {
                    L.delete("gtm_hits", String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                    au auVar = this.YK;
                    if (cX() != 0) {
                        z = false;
                    }
                    auVar.m1371r(z);
                } catch (SQLiteException e) {
                    bh.m1387z("Error deleting hits");
                }
            }
        }
    }

    public void bW() {
        bh.m1386y("GTM Dispatch running...");
        if (this.YJ.ch()) {
            List t = m2493t(40);
            if (t.isEmpty()) {
                bh.m1386y("...nothing to dispatch");
                this.YK.m1371r(true);
                return;
            }
            this.YJ.m1359d(t);
            if (kR() > 0) {
                cx.lG().bW();
            }
        }
    }

    int cW() {
        boolean z = true;
        long currentTimeMillis = this.Wv.currentTimeMillis();
        if (currentTimeMillis <= this.vC + 86400000) {
            return 0;
        }
        this.vC = currentTimeMillis;
        SQLiteDatabase L = m2481L("Error opening database for deleteStaleHits.");
        if (L == null) {
            return 0;
        }
        int delete = L.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.Wv.currentTimeMillis() - 2592000000L)});
        au auVar = this.YK;
        if (cX() != 0) {
            z = false;
        }
        auVar.m1371r(z);
        return delete;
    }

    int cX() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase L = m2481L("Error opening database for getNumStoredHits.");
        if (L != null) {
            try {
                cursor = L.rawQuery("SELECT COUNT(*) from gtm_hits", null);
                if (cursor.moveToFirst()) {
                    i = (int) cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                bh.m1387z("Error getting numStoredHits");
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

    public void m2491e(long j, String str) {
        cW();
        cV();
        m2488f(j, str);
    }

    int kR() {
        int count;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        SQLiteDatabase L = m2481L("Error opening database for getNumStoredHits.");
        if (L == null) {
            return 0;
        }
        try {
            Cursor query = L.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", null, null, null, null);
            try {
                count = query.getCount();
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e) {
                cursor = query;
                try {
                    bh.m1387z("Error getting num untried hits");
                    if (cursor == null) {
                        count = 0;
                    } else {
                        cursor.close();
                        count = 0;
                    }
                    return count;
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
            bh.m1387z("Error getting num untried hits");
            if (cursor == null) {
                cursor.close();
                count = 0;
            } else {
                count = 0;
            }
            return count;
        } catch (Throwable th4) {
            th = th4;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
        return count;
    }

    List<String> m2492s(int i) {
        Cursor query;
        SQLiteException e;
        Throwable th;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            bh.m1387z("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase L = m2481L("Error opening database for peekHitIds.");
        if (L == null) {
            return arrayList;
        }
        try {
            query = L.query("gtm_hits", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", new Object[]{"hit_id"}), Integer.toString(i));
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
                    bh.m1387z("Error in peekHits fetching hitIds: " + e.getMessage());
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
            bh.m1387z("Error in peekHits fetching hitIds: " + e.getMessage());
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
    public java.util.List<com.google.android.gms.tagmanager.ap> m2493t(int r16) {
        /*
        r15 = this;
        r10 = new java.util.ArrayList;
        r10.<init>();
        r1 = "Error opening database for peekHits";
        r1 = r15.m2481L(r1);
        if (r1 != 0) goto L_0x000f;
    L_0x000d:
        r1 = r10;
    L_0x000e:
        return r1;
    L_0x000f:
        r11 = 0;
        r2 = "gtm_hits";
        r3 = 3;
        r3 = new java.lang.String[r3];	 Catch:{ SQLiteException -> 0x00c8, all -> 0x00ed }
        r4 = 0;
        r5 = "hit_id";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x00c8, all -> 0x00ed }
        r4 = 1;
        r5 = "hit_time";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x00c8, all -> 0x00ed }
        r4 = 2;
        r5 = "hit_first_send_time";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x00c8, all -> 0x00ed }
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = "%s ASC";
        r9 = 1;
        r9 = new java.lang.Object[r9];	 Catch:{ SQLiteException -> 0x00c8, all -> 0x00ed }
        r12 = 0;
        r13 = "hit_id";
        r9[r12] = r13;	 Catch:{ SQLiteException -> 0x00c8, all -> 0x00ed }
        r8 = java.lang.String.format(r8, r9);	 Catch:{ SQLiteException -> 0x00c8, all -> 0x00ed }
        r9 = java.lang.Integer.toString(r16);	 Catch:{ SQLiteException -> 0x00c8, all -> 0x00ed }
        r12 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9);	 Catch:{ SQLiteException -> 0x00c8, all -> 0x00ed }
        r11 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x016f, all -> 0x0169 }
        r11.<init>();	 Catch:{ SQLiteException -> 0x016f, all -> 0x0169 }
        r2 = r12.moveToFirst();	 Catch:{ SQLiteException -> 0x0175, all -> 0x0169 }
        if (r2 == 0) goto L_0x0066;
    L_0x0049:
        r2 = new com.google.android.gms.tagmanager.ap;	 Catch:{ SQLiteException -> 0x0175, all -> 0x0169 }
        r3 = 0;
        r3 = r12.getLong(r3);	 Catch:{ SQLiteException -> 0x0175, all -> 0x0169 }
        r5 = 1;
        r5 = r12.getLong(r5);	 Catch:{ SQLiteException -> 0x0175, all -> 0x0169 }
        r7 = 2;
        r7 = r12.getLong(r7);	 Catch:{ SQLiteException -> 0x0175, all -> 0x0169 }
        r2.<init>(r3, r5, r7);	 Catch:{ SQLiteException -> 0x0175, all -> 0x0169 }
        r11.add(r2);	 Catch:{ SQLiteException -> 0x0175, all -> 0x0169 }
        r2 = r12.moveToNext();	 Catch:{ SQLiteException -> 0x0175, all -> 0x0169 }
        if (r2 != 0) goto L_0x0049;
    L_0x0066:
        if (r12 == 0) goto L_0x006b;
    L_0x0068:
        r12.close();
    L_0x006b:
        r10 = 0;
        r2 = "gtm_hits";
        r3 = 2;
        r3 = new java.lang.String[r3];	 Catch:{ SQLiteException -> 0x0167 }
        r4 = 0;
        r5 = "hit_id";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0167 }
        r4 = 1;
        r5 = "hit_url";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0167 }
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = "%s ASC";
        r9 = 1;
        r9 = new java.lang.Object[r9];	 Catch:{ SQLiteException -> 0x0167 }
        r13 = 0;
        r14 = "hit_id";
        r9[r13] = r14;	 Catch:{ SQLiteException -> 0x0167 }
        r8 = java.lang.String.format(r8, r9);	 Catch:{ SQLiteException -> 0x0167 }
        r9 = java.lang.Integer.toString(r16);	 Catch:{ SQLiteException -> 0x0167 }
        r2 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9);	 Catch:{ SQLiteException -> 0x0167 }
        r1 = r2.moveToFirst();	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        if (r1 == 0) goto L_0x00c0;
    L_0x009b:
        r3 = r10;
    L_0x009c:
        r0 = r2;
        r0 = (android.database.sqlite.SQLiteCursor) r0;	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r1 = r0;
        r1 = r1.getWindow();	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r1 = r1.getNumRows();	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        if (r1 <= 0) goto L_0x00f4;
    L_0x00aa:
        r1 = r11.get(r3);	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r1 = (com.google.android.gms.tagmanager.ap) r1;	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r4 = 1;
        r4 = r2.getString(r4);	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r1.m1368K(r4);	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
    L_0x00b8:
        r1 = r3 + 1;
        r3 = r2.moveToNext();	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        if (r3 != 0) goto L_0x017b;
    L_0x00c0:
        if (r2 == 0) goto L_0x00c5;
    L_0x00c2:
        r2.close();
    L_0x00c5:
        r1 = r11;
        goto L_0x000e;
    L_0x00c8:
        r1 = move-exception;
        r2 = r1;
        r3 = r11;
        r1 = r10;
    L_0x00cc:
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x016c }
        r4.<init>();	 Catch:{ all -> 0x016c }
        r5 = "Error in peekHits fetching hitIds: ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x016c }
        r2 = r2.getMessage();	 Catch:{ all -> 0x016c }
        r2 = r4.append(r2);	 Catch:{ all -> 0x016c }
        r2 = r2.toString();	 Catch:{ all -> 0x016c }
        com.google.android.gms.tagmanager.bh.m1387z(r2);	 Catch:{ all -> 0x016c }
        if (r3 == 0) goto L_0x000e;
    L_0x00e8:
        r3.close();
        goto L_0x000e;
    L_0x00ed:
        r1 = move-exception;
    L_0x00ee:
        if (r11 == 0) goto L_0x00f3;
    L_0x00f0:
        r11.close();
    L_0x00f3:
        throw r1;
    L_0x00f4:
        r4 = "HitString for hitId %d too large.  Hit will be deleted.";
        r1 = 1;
        r5 = new java.lang.Object[r1];	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r6 = 0;
        r1 = r11.get(r3);	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r1 = (com.google.android.gms.tagmanager.ap) r1;	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r7 = r1.cP();	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r1 = java.lang.Long.valueOf(r7);	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r5[r6] = r1;	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        r1 = java.lang.String.format(r4, r5);	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        com.google.android.gms.tagmanager.bh.m1387z(r1);	 Catch:{ SQLiteException -> 0x0112, all -> 0x0164 }
        goto L_0x00b8;
    L_0x0112:
        r1 = move-exception;
        r12 = r2;
    L_0x0114:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x015d }
        r2.<init>();	 Catch:{ all -> 0x015d }
        r3 = "Error in peekHits fetching hit url: ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x015d }
        r1 = r1.getMessage();	 Catch:{ all -> 0x015d }
        r1 = r2.append(r1);	 Catch:{ all -> 0x015d }
        r1 = r1.toString();	 Catch:{ all -> 0x015d }
        com.google.android.gms.tagmanager.bh.m1387z(r1);	 Catch:{ all -> 0x015d }
        r2 = new java.util.ArrayList;	 Catch:{ all -> 0x015d }
        r2.<init>();	 Catch:{ all -> 0x015d }
        r3 = 0;
        r4 = r11.iterator();	 Catch:{ all -> 0x015d }
    L_0x0138:
        r1 = r4.hasNext();	 Catch:{ all -> 0x015d }
        if (r1 == 0) goto L_0x0150;
    L_0x013e:
        r1 = r4.next();	 Catch:{ all -> 0x015d }
        r1 = (com.google.android.gms.tagmanager.ap) r1;	 Catch:{ all -> 0x015d }
        r5 = r1.kE();	 Catch:{ all -> 0x015d }
        r5 = android.text.TextUtils.isEmpty(r5);	 Catch:{ all -> 0x015d }
        if (r5 == 0) goto L_0x0159;
    L_0x014e:
        if (r3 == 0) goto L_0x0158;
    L_0x0150:
        if (r12 == 0) goto L_0x0155;
    L_0x0152:
        r12.close();
    L_0x0155:
        r1 = r2;
        goto L_0x000e;
    L_0x0158:
        r3 = 1;
    L_0x0159:
        r2.add(r1);	 Catch:{ all -> 0x015d }
        goto L_0x0138;
    L_0x015d:
        r1 = move-exception;
    L_0x015e:
        if (r12 == 0) goto L_0x0163;
    L_0x0160:
        r12.close();
    L_0x0163:
        throw r1;
    L_0x0164:
        r1 = move-exception;
        r12 = r2;
        goto L_0x015e;
    L_0x0167:
        r1 = move-exception;
        goto L_0x0114;
    L_0x0169:
        r1 = move-exception;
        r11 = r12;
        goto L_0x00ee;
    L_0x016c:
        r1 = move-exception;
        r11 = r3;
        goto L_0x00ee;
    L_0x016f:
        r1 = move-exception;
        r2 = r1;
        r3 = r12;
        r1 = r10;
        goto L_0x00cc;
    L_0x0175:
        r1 = move-exception;
        r2 = r1;
        r3 = r12;
        r1 = r11;
        goto L_0x00cc;
    L_0x017b:
        r3 = r1;
        goto L_0x009c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.ca.t(int):java.util.List<com.google.android.gms.tagmanager.ap>");
    }
}
