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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class DataHolder implements SafeParcelable {
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
    private boolean BO;
    boolean mClosed;
    private final int xH;

    public static class Builder {
        private final String[] BH;
        private final ArrayList<HashMap<String, Object>> BQ;
        private final String BR;
        private final HashMap<Object, Integer> BS;
        private boolean BT;
        private String BU;

        private Builder(String[] columns, String uniqueColumn) {
            this.BH = (String[]) fq.m985f(columns);
            this.BQ = new ArrayList();
            this.BR = uniqueColumn;
            this.BS = new HashMap();
            this.BT = false;
            this.BU = null;
        }

        private void m137a(HashMap<String, Object> hashMap) {
            Object obj = hashMap.get(this.BR);
            if (obj != null) {
                Integer num = (Integer) this.BS.remove(obj);
                if (num != null) {
                    this.BQ.remove(num.intValue());
                }
                this.BS.put(obj, Integer.valueOf(this.BQ.size()));
            }
        }

        private void et() {
            if (this.BR != null) {
                this.BS.clear();
                int size = this.BQ.size();
                for (int i = 0; i < size; i++) {
                    Object obj = ((HashMap) this.BQ.get(i)).get(this.BR);
                    if (obj != null) {
                        this.BS.put(obj, Integer.valueOf(i));
                    }
                }
            }
        }

        public DataHolder build(int statusCode) {
            return new DataHolder(statusCode, null, null);
        }

        public DataHolder build(int statusCode, Bundle metadata) {
            return new DataHolder(statusCode, metadata, -1, null);
        }

        public DataHolder build(int statusCode, Bundle metadata, int maxResults) {
            return new DataHolder(statusCode, metadata, maxResults, null);
        }

        public int getCount() {
            return this.BQ.size();
        }

        public Builder removeRowsWithValue(String column, Object value) {
            for (int size = this.BQ.size() - 1; size >= 0; size--) {
                if (fo.equal(((HashMap) this.BQ.get(size)).get(column), value)) {
                    this.BQ.remove(size);
                }
            }
            return this;
        }

        public Builder sort(String sortColumn) {
            fb.m920d(sortColumn);
            if (!(this.BT && sortColumn.equals(this.BU))) {
                Collections.sort(this.BQ, new C0249a(sortColumn));
                et();
                this.BT = true;
                this.BU = sortColumn;
            }
            return this;
        }

        public Builder withRow(ContentValues values) {
            fb.m920d(values);
            HashMap hashMap = new HashMap(values.size());
            for (Entry entry : values.valueSet()) {
                hashMap.put(entry.getKey(), entry.getValue());
            }
            return withRow(hashMap);
        }

        public Builder withRow(HashMap<String, Object> row) {
            fb.m920d(row);
            if (this.BR != null) {
                m137a((HashMap) row);
            }
            this.BQ.add(row);
            this.BT = false;
            return this;
        }
    }

    /* renamed from: com.google.android.gms.common.data.DataHolder.a */
    private static final class C0249a implements Comparator<HashMap<String, Object>> {
        private final String BV;

        C0249a(String str) {
            this.BV = (String) fq.m985f(str);
        }

        public int m140a(HashMap<String, Object> hashMap, HashMap<String, Object> hashMap2) {
            Object f = fq.m985f(hashMap.get(this.BV));
            Object f2 = fq.m985f(hashMap2.get(this.BV));
            if (f.equals(f2)) {
                return 0;
            }
            if (f instanceof Boolean) {
                return ((Boolean) f).compareTo((Boolean) f2);
            }
            if (f instanceof Long) {
                return ((Long) f).compareTo((Long) f2);
            }
            if (f instanceof Integer) {
                return ((Integer) f).compareTo((Integer) f2);
            }
            if (f instanceof String) {
                return ((String) f).compareTo((String) f2);
            }
            throw new IllegalArgumentException("Unknown type for lValue " + f);
        }

        public /* synthetic */ int compare(Object x0, Object x1) {
            return m140a((HashMap) x0, (HashMap) x1);
        }
    }

    /* renamed from: com.google.android.gms.common.data.DataHolder.1 */
    static class C07941 extends Builder {
        C07941(String[] strArr, String str) {
            super(str, null);
        }

        public Builder withRow(ContentValues values) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }

        public Builder withRow(HashMap<String, Object> hashMap) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }
    }

    static {
        CREATOR = new DataHolderCreator();
        BP = new C07941(new String[0], null);
    }

    DataHolder(int versionCode, String[] columns, CursorWindow[] windows, int statusCode, Bundle metadata) {
        this.mClosed = false;
        this.BO = true;
        this.xH = versionCode;
        this.BH = columns;
        this.BJ = windows;
        this.Ah = statusCode;
        this.BK = metadata;
    }

    public DataHolder(AbstractWindowedCursor cursor, int statusCode, Bundle metadata) {
        this(cursor.getColumnNames(), m1684a(cursor), statusCode, metadata);
    }

    private DataHolder(Builder builder, int statusCode, Bundle metadata) {
        this(builder.BH, m1685a(builder, -1), statusCode, metadata);
    }

    private DataHolder(Builder builder, int statusCode, Bundle metadata, int maxResults) {
        this(builder.BH, m1685a(builder, maxResults), statusCode, metadata);
    }

    public DataHolder(String[] columns, CursorWindow[] windows, int statusCode, Bundle metadata) {
        this.mClosed = false;
        this.BO = true;
        this.xH = 1;
        this.BH = (String[]) fq.m985f(columns);
        this.BJ = (CursorWindow[]) fq.m985f(windows);
        this.Ah = statusCode;
        this.BK = metadata;
        validateContents();
    }

    private static CursorWindow[] m1684a(AbstractWindowedCursor abstractWindowedCursor) {
        int i;
        ArrayList arrayList = new ArrayList();
        int count = abstractWindowedCursor.getCount();
        CursorWindow window = abstractWindowedCursor.getWindow();
        if (window == null || window.getStartPosition() != 0) {
            i = 0;
        } else {
            window.acquireReference();
            abstractWindowedCursor.setWindow(null);
            arrayList.add(window);
            i = window.getNumRows();
        }
        while (i < count && abstractWindowedCursor.moveToPosition(i)) {
            CursorWindow window2 = abstractWindowedCursor.getWindow();
            if (window2 != null) {
                window2.acquireReference();
                abstractWindowedCursor.setWindow(null);
            } else {
                try {
                    window2 = new CursorWindow(false);
                    window2.setStartPosition(i);
                    abstractWindowedCursor.fillWindow(i, window2);
                } catch (Throwable th) {
                    abstractWindowedCursor.close();
                }
            }
            if (window2.getNumRows() == 0) {
                break;
            }
            arrayList.add(window2);
            i = window2.getNumRows() + window2.getStartPosition();
        }
        abstractWindowedCursor.close();
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    private static CursorWindow[] m1685a(Builder builder, int i) {
        int size;
        int i2 = 0;
        if (builder.BH.length == 0) {
            return new CursorWindow[0];
        }
        List b = (i < 0 || i >= builder.BQ.size()) ? builder.BQ : builder.BQ.subList(0, i);
        int size2 = b.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(builder.BH.length);
        int i3 = 0;
        int i4 = 0;
        while (i3 < size2) {
            int i5;
            int i6;
            CursorWindow cursorWindow2;
            if (cursorWindow.allocRow()) {
                i5 = i4;
            } else {
                Log.d("DataHolder", "Allocating additional cursor window for large data set (row " + i3 + ")");
                cursorWindow = new CursorWindow(false);
                cursorWindow.setStartPosition(i3);
                cursorWindow.setNumColumns(builder.BH.length);
                arrayList.add(cursorWindow);
                if (cursorWindow.allocRow()) {
                    i5 = 0;
                } else {
                    Log.e("DataHolder", "Unable to allocate row to hold data.");
                    arrayList.remove(cursorWindow);
                    return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                }
            }
            Map map = (Map) b.get(i3);
            boolean z = true;
            for (int i7 = 0; i7 < builder.BH.length && z; i7++) {
                String str = builder.BH[i7];
                Object obj = map.get(str);
                if (obj == null) {
                    z = cursorWindow.putNull(i5, i7);
                } else if (obj instanceof String) {
                    z = cursorWindow.putString((String) obj, i5, i7);
                } else if (obj instanceof Long) {
                    z = cursorWindow.putLong(((Long) obj).longValue(), i5, i7);
                } else if (obj instanceof Integer) {
                    z = cursorWindow.putLong((long) ((Integer) obj).intValue(), i5, i7);
                } else if (obj instanceof Boolean) {
                    z = cursorWindow.putLong(((Boolean) obj).booleanValue() ? 1 : 0, i5, i7);
                } else if (obj instanceof byte[]) {
                    z = cursorWindow.putBlob((byte[]) obj, i5, i7);
                } else if (obj instanceof Double) {
                    z = cursorWindow.putDouble(((Double) obj).doubleValue(), i5, i7);
                } else {
                    throw new IllegalArgumentException("Unsupported object for column " + str + ": " + obj);
                }
            }
            if (z) {
                i6 = i5 + 1;
                i4 = i3;
                cursorWindow2 = cursorWindow;
            } else {
                try {
                    Log.d("DataHolder", "Couldn't populate window data for row " + i3 + " - allocating new window.");
                    cursorWindow.freeLastRow();
                    CursorWindow cursorWindow3 = new CursorWindow(false);
                    cursorWindow3.setNumColumns(builder.BH.length);
                    arrayList.add(cursorWindow3);
                    i4 = i3 - 1;
                    cursorWindow2 = cursorWindow3;
                    i6 = 0;
                } catch (RuntimeException e) {
                    RuntimeException runtimeException = e;
                    size = arrayList.size();
                    while (i2 < size) {
                        ((CursorWindow) arrayList.get(i2)).close();
                        i2++;
                    }
                    throw runtimeException;
                }
            }
            cursorWindow = cursorWindow2;
            i3 = i4 + 1;
            i4 = i6;
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static Builder builder(String[] columns) {
        return new Builder(null, null);
    }

    public static Builder builder(String[] columns, String uniqueColumn) {
        fq.m985f(uniqueColumn);
        return new Builder(uniqueColumn, null);
    }

    private void m1686e(String str, int i) {
        if (this.BI == null || !this.BI.containsKey(str)) {
            throw new IllegalArgumentException("No such column: " + str);
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.BM) {
            throw new CursorIndexOutOfBoundsException(i, this.BM);
        }
    }

    public static DataHolder empty(int statusCode) {
        return empty(statusCode, null);
    }

    public static DataHolder empty(int statusCode, Bundle metadata) {
        return new DataHolder(BP, statusCode, metadata);
    }

    public int m1687G(int i) {
        int i2 = 0;
        boolean z = i >= 0 && i < this.BM;
        fq.m986x(z);
        while (i2 < this.BL.length) {
            if (i < this.BL[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.BL.length ? i2 - 1 : i2;
    }

    public void m1688c(Object obj) {
        this.BN = obj;
    }

    public void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.BJ) {
                    close.close();
                }
            }
        }
    }

    public void copyToBuffer(String column, int row, int windowIndex, CharArrayBuffer dataOut) {
        m1686e(column, row);
        this.BJ[windowIndex].copyStringToBuffer(row, this.BI.getInt(column), dataOut);
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

    protected void finalize() throws Throwable {
        try {
            if (this.BO && this.BJ.length > 0 && !isClosed()) {
                Log.e("DataBuffer", "Internal data leak within a DataBuffer object detected!  Be sure to explicitly call close() on all DataBuffer extending objects when you are done with them. (" + (this.BN == null ? "internal object: " + toString() : this.BN.toString()) + ")");
                close();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public boolean getBoolean(String column, int row, int windowIndex) {
        m1686e(column, row);
        return Long.valueOf(this.BJ[windowIndex].getLong(row, this.BI.getInt(column))).longValue() == 1;
    }

    public byte[] getByteArray(String column, int row, int windowIndex) {
        m1686e(column, row);
        return this.BJ[windowIndex].getBlob(row, this.BI.getInt(column));
    }

    public int getCount() {
        return this.BM;
    }

    public double getDouble(String column, int row, int windowIndex) {
        m1686e(column, row);
        return this.BJ[windowIndex].getDouble(row, this.BI.getInt(column));
    }

    public int getInteger(String column, int row, int windowIndex) {
        m1686e(column, row);
        return this.BJ[windowIndex].getInt(row, this.BI.getInt(column));
    }

    public long getLong(String column, int row, int windowIndex) {
        m1686e(column, row);
        return this.BJ[windowIndex].getLong(row, this.BI.getInt(column));
    }

    public Bundle getMetadata() {
        return this.BK;
    }

    public int getStatusCode() {
        return this.Ah;
    }

    public String getString(String column, int row, int windowIndex) {
        m1686e(column, row);
        return this.BJ[windowIndex].getString(row, this.BI.getInt(column));
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean hasColumn(String column) {
        return this.BI.containsKey(column);
    }

    public boolean hasNull(String column, int row, int windowIndex) {
        m1686e(column, row);
        return this.BJ[windowIndex].isNull(row, this.BI.getInt(column));
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public Uri parseUri(String column, int row, int windowIndex) {
        String string = getString(column, row, windowIndex);
        return string == null ? null : Uri.parse(string);
    }

    public void validateContents() {
        int i;
        int i2 = 0;
        this.BI = new Bundle();
        for (i = 0; i < this.BH.length; i++) {
            this.BI.putInt(this.BH[i], i);
        }
        this.BL = new int[this.BJ.length];
        i = 0;
        while (i2 < this.BJ.length) {
            this.BL[i2] = i;
            i += this.BJ[i2].getNumRows() - (i - this.BJ[i2].getStartPosition());
            i2++;
        }
        this.BM = i;
    }

    public void writeToParcel(Parcel dest, int flags) {
        DataHolderCreator.m141a(this, dest, flags);
    }
}
