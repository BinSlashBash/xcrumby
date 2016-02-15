/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.database.AbstractWindowedCursor
 *  android.database.CharArrayBuffer
 *  android.database.CursorIndexOutOfBoundsException
 *  android.database.CursorWindow
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.util.Log
 */
package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.AbstractWindowedCursor;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.data.DataHolderCreator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class DataHolder
implements SafeParcelable {
    private static final Builder BP;
    public static final DataHolderCreator CREATOR;
    private final int Ah;
    private final String[] BH;
    Bundle BI;
    private final CursorWindow[] BJ;
    private final Bundle BK;
    int[] BL;
    int BM;
    private Object BN;
    private boolean BO = true;
    boolean mClosed = false;
    private final int xH;

    static {
        CREATOR = new DataHolderCreator();
        BP = new Builder(new String[0], null){

            @Override
            public Builder withRow(ContentValues contentValues) {
                throw new UnsupportedOperationException("Cannot add data to empty builder");
            }

            @Override
            public Builder withRow(HashMap<String, Object> hashMap) {
                throw new UnsupportedOperationException("Cannot add data to empty builder");
            }
        };
    }

    DataHolder(int n2, String[] arrstring, CursorWindow[] arrcursorWindow, int n3, Bundle bundle) {
        this.xH = n2;
        this.BH = arrstring;
        this.BJ = arrcursorWindow;
        this.Ah = n3;
        this.BK = bundle;
    }

    public DataHolder(AbstractWindowedCursor abstractWindowedCursor, int n2, Bundle bundle) {
        this(abstractWindowedCursor.getColumnNames(), DataHolder.a(abstractWindowedCursor), n2, bundle);
    }

    private DataHolder(Builder builder, int n2, Bundle bundle) {
        this(builder.BH, DataHolder.a(builder, -1), n2, bundle);
    }

    private DataHolder(Builder builder, int n2, Bundle bundle, int n3) {
        this(builder.BH, DataHolder.a(builder, n3), n2, bundle);
    }

    public DataHolder(String[] arrstring, CursorWindow[] arrcursorWindow, int n2, Bundle bundle) {
        this.xH = 1;
        this.BH = fq.f(arrstring);
        this.BJ = fq.f(arrcursorWindow);
        this.Ah = n2;
        this.BK = bundle;
        this.validateContents();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static CursorWindow[] a(AbstractWindowedCursor var0) {
        block10 : {
            var5_1 = new ArrayList<CursorWindow>();
            try {
                var2_2 = var0.getCount();
                var4_3 = var0.getWindow();
                if (var4_3 != null && var4_3.getStartPosition() == 0) {
                    var4_3.acquireReference();
                    var0.setWindow(null);
                    var5_1.add(var4_3);
                    var1_5 = var4_3.getNumRows();
                } else {
                    var1_5 = 0;
                }
lbl12: // 3 sources:
                if (var1_5 >= var2_2 || !var0.moveToPosition(var1_5)) break block10;
                var4_3 = var0.getWindow();
                if (var4_3 != null) {
                    var4_3.acquireReference();
                    var0.setWindow(null);
                } else {
                    var4_3 = new CursorWindow(false);
                    var4_3.setStartPosition(var1_5);
                    var0.fillWindow(var1_5, var4_3);
                }
                if ((var1_5 = var4_3.getNumRows()) == 0) break block10;
                var5_1.add(var4_3);
                var1_5 = var4_3.getStartPosition();
                var3_6 = var4_3.getNumRows();
            }
            catch (Throwable var4_4) {
                var0.close();
                throw var4_4;
            }
            var1_5 = var3_6 + var1_5;
            ** GOTO lbl12
        }
        var0.close();
        return var5_1.toArray((T[])new CursorWindow[var5_1.size()]);
    }

    /*
     * Exception decompiling
     */
    private static CursorWindow[] a(Builder var0, int var1_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException: Backjump on non jumping statement [] lbl68 : TryStatement: try { 2[TRYBLOCK]

        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:44)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:22)
        // org.benf.cfr.reader.util.graph.GraphVisitorDFS.process(GraphVisitorDFS.java:68)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner.removeUnreachableCode(Cleaner.java:54)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.RemoveDeterministicJumps.apply(RemoveDeterministicJumps.java:35)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:507)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    public static Builder builder(String[] arrstring) {
        return new Builder(arrstring, null);
    }

    public static Builder builder(String[] arrstring, String string2) {
        fq.f(string2);
        return new Builder(arrstring, string2);
    }

    private void e(String string2, int n2) {
        if (this.BI == null || !this.BI.containsKey(string2)) {
            throw new IllegalArgumentException("No such column: " + string2);
        }
        if (this.isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        }
        if (n2 < 0 || n2 >= this.BM) {
            throw new CursorIndexOutOfBoundsException(n2, this.BM);
        }
    }

    public static DataHolder empty(int n2) {
        return DataHolder.empty(n2, null);
    }

    public static DataHolder empty(int n2, Bundle bundle) {
        return new DataHolder(BP, n2, bundle);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int G(int var1_1) {
        var3_2 = 0;
        var4_3 = var1_1 >= 0 && var1_1 < this.BM;
        fq.x(var4_3);
        do {
            var2_4 = var3_2;
            if (var3_2 >= this.BL.length) ** GOTO lbl9
            if (var1_1 < this.BL[var3_2]) {
                var2_4 = var3_2 - 1;
lbl9: // 2 sources:
                var1_1 = var2_4;
                if (var2_4 != this.BL.length) return var1_1;
                return var2_4 - 1;
            }
            ++var3_2;
        } while (true);
    }

    public void c(Object object) {
        this.BN = object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        synchronized (this) {
            if (this.mClosed) {
                return;
            }
            this.mClosed = true;
            int n2 = 0;
            while (n2 < this.BJ.length) {
                this.BJ[n2].close();
                ++n2;
            }
            return;
        }
    }

    public void copyToBuffer(String string2, int n2, int n3, CharArrayBuffer charArrayBuffer) {
        this.e(string2, n2);
        this.BJ[n3].copyStringToBuffer(n2, this.BI.getInt(string2), charArrayBuffer);
    }

    public int describeContents() {
        return 0;
    }

    String[] er() {
        return this.BH;
    }

    CursorWindow[] es() {
        return this.BJ;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void finalize() throws Throwable {
        try {
            if (this.BO && this.BJ.length > 0 && !this.isClosed()) {
                String string2 = this.BN == null ? "internal object: " + this.toString() : this.BN.toString();
                Log.e((String)"DataBuffer", (String)("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call close() on all DataBuffer extending objects when you are done with them. (" + string2 + ")"));
                this.close();
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public boolean getBoolean(String string2, int n2, int n3) {
        this.e(string2, n2);
        if (Long.valueOf(this.BJ[n3].getLong(n2, this.BI.getInt(string2))) == 1) {
            return true;
        }
        return false;
    }

    public byte[] getByteArray(String string2, int n2, int n3) {
        this.e(string2, n2);
        return this.BJ[n3].getBlob(n2, this.BI.getInt(string2));
    }

    public int getCount() {
        return this.BM;
    }

    public double getDouble(String string2, int n2, int n3) {
        this.e(string2, n2);
        return this.BJ[n3].getDouble(n2, this.BI.getInt(string2));
    }

    public int getInteger(String string2, int n2, int n3) {
        this.e(string2, n2);
        return this.BJ[n3].getInt(n2, this.BI.getInt(string2));
    }

    public long getLong(String string2, int n2, int n3) {
        this.e(string2, n2);
        return this.BJ[n3].getLong(n2, this.BI.getInt(string2));
    }

    public Bundle getMetadata() {
        return this.BK;
    }

    public int getStatusCode() {
        return this.Ah;
    }

    public String getString(String string2, int n2, int n3) {
        this.e(string2, n2);
        return this.BJ[n3].getString(n2, this.BI.getInt(string2));
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean hasColumn(String string2) {
        return this.BI.containsKey(string2);
    }

    public boolean hasNull(String string2, int n2, int n3) {
        this.e(string2, n2);
        return this.BJ[n3].isNull(n2, this.BI.getInt(string2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isClosed() {
        synchronized (this) {
            return this.mClosed;
        }
    }

    public Uri parseUri(String string2, int n2, int n3) {
        if ((string2 = this.getString(string2, n2, n3)) == null) {
            return null;
        }
        return Uri.parse((String)string2);
    }

    public void validateContents() {
        int n2;
        int n3 = 0;
        this.BI = new Bundle();
        for (n2 = 0; n2 < this.BH.length; ++n2) {
            this.BI.putInt(this.BH[n2], n2);
        }
        this.BL = new int[this.BJ.length];
        int n4 = 0;
        n2 = n3;
        n3 = n4;
        while (n2 < this.BJ.length) {
            this.BL[n2] = n3;
            n4 = this.BJ[n2].getStartPosition();
            n3 += this.BJ[n2].getNumRows() - (n3 - n4);
            ++n2;
        }
        this.BM = n3;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        DataHolderCreator.a(this, parcel, n2);
    }

    public static class Builder {
        private final String[] BH;
        private final ArrayList<HashMap<String, Object>> BQ;
        private final String BR;
        private final HashMap<Object, Integer> BS;
        private boolean BT;
        private String BU;

        private Builder(String[] arrstring, String string2) {
            this.BH = fq.f(arrstring);
            this.BQ = new ArrayList();
            this.BR = string2;
            this.BS = new HashMap();
            this.BT = false;
            this.BU = null;
        }

        private void a(HashMap<String, Object> object) {
            if ((object = object.get(this.BR)) == null) {
                return;
            }
            Integer n2 = this.BS.remove(object);
            if (n2 != null) {
                this.BQ.remove(n2);
            }
            this.BS.put(object, this.BQ.size());
        }

        static /* synthetic */ ArrayList b(Builder builder) {
            return builder.BQ;
        }

        private void et() {
            if (this.BR != null) {
                this.BS.clear();
                int n2 = this.BQ.size();
                for (int i2 = 0; i2 < n2; ++i2) {
                    Object object = this.BQ.get(i2).get(this.BR);
                    if (object == null) continue;
                    this.BS.put(object, i2);
                }
            }
        }

        public DataHolder build(int n2) {
            return new DataHolder(this, n2, null);
        }

        public DataHolder build(int n2, Bundle bundle) {
            return new DataHolder(this, n2, bundle, -1);
        }

        public DataHolder build(int n2, Bundle bundle, int n3) {
            return new DataHolder(this, n2, bundle, n3);
        }

        public int getCount() {
            return this.BQ.size();
        }

        public Builder removeRowsWithValue(String string2, Object object) {
            for (int i2 = this.BQ.size() - 1; i2 >= 0; --i2) {
                if (!fo.equal(this.BQ.get(i2).get(string2), object)) continue;
                this.BQ.remove(i2);
            }
            return this;
        }

        public Builder sort(String string2) {
            fb.d(string2);
            if (this.BT && string2.equals(this.BU)) {
                return this;
            }
            Collections.sort(this.BQ, new a(string2));
            this.et();
            this.BT = true;
            this.BU = string2;
            return this;
        }

        public Builder withRow(ContentValues object) {
            fb.d(object);
            HashMap<String, Object> hashMap = new HashMap<String, Object>(object.size());
            for (Map.Entry entry : object.valueSet()) {
                hashMap.put((String)entry.getKey(), entry.getValue());
            }
            return this.withRow(hashMap);
        }

        public Builder withRow(HashMap<String, Object> hashMap) {
            fb.d(hashMap);
            if (this.BR != null) {
                this.a(hashMap);
            }
            this.BQ.add(hashMap);
            this.BT = false;
            return this;
        }
    }

    private static final class a
    implements Comparator<HashMap<String, Object>> {
        private final String BV;

        a(String string2) {
            this.BV = fq.f(string2);
        }

        public int a(HashMap<String, Object> object, HashMap<String, Object> object2) {
            if ((object = fq.f(object.get(this.BV))).equals(object2 = fq.f(object2.get(this.BV)))) {
                return 0;
            }
            if (object instanceof Boolean) {
                return ((Boolean)object).compareTo((Boolean)object2);
            }
            if (object instanceof Long) {
                return ((Long)object).compareTo((Long)object2);
            }
            if (object instanceof Integer) {
                return ((Integer)object).compareTo((Integer)object2);
            }
            if (object instanceof String) {
                return ((String)object).compareTo((String)object2);
            }
            throw new IllegalArgumentException("Unknown type for lValue " + object);
        }

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.a((HashMap)object, (HashMap)object2);
        }
    }

}

