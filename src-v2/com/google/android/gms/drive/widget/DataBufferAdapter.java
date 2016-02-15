/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.CursorIndexOutOfBoundsException
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 */
package com.google.android.gms.drive.widget;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.gms.common.data.DataBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataBufferAdapter<T>
extends BaseAdapter {
    private final int Hd;
    private int He;
    private final int Hf;
    private final List<DataBuffer<T>> Hg;
    private final LayoutInflater Hh;
    private boolean Hi = true;
    private final Context mContext;

    public DataBufferAdapter(Context context, int n2) {
        this(context, n2, 0, new ArrayList<DataBuffer<T>>());
    }

    public DataBufferAdapter(Context context, int n2, int n3) {
        this(context, n2, n3, new ArrayList<DataBuffer<T>>());
    }

    public DataBufferAdapter(Context context, int n2, int n3, List<DataBuffer<T>> list) {
        this.mContext = context;
        this.He = n2;
        this.Hd = n2;
        this.Hf = n3;
        this.Hg = list;
        this.Hh = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    public /* varargs */ DataBufferAdapter(Context context, int n2, int n3, DataBuffer<T> ... arrdataBuffer) {
        this(context, n2, n3, Arrays.asList(arrdataBuffer));
    }

    public DataBufferAdapter(Context context, int n2, List<DataBuffer<T>> list) {
        this(context, n2, 0, list);
    }

    public /* varargs */ DataBufferAdapter(Context context, int n2, DataBuffer<T> ... arrdataBuffer) {
        this(context, n2, 0, Arrays.asList(arrdataBuffer));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private View a(int n2, View view, ViewGroup viewGroup, int n3) {
        if (view == null) {
            view = this.Hh.inflate(n3, viewGroup, false);
        }
        try {
            viewGroup = this.Hf == 0 ? (TextView)view : (TextView)view.findViewById(this.Hf);
        }
        catch (ClassCastException var2_3) {
            Log.e((String)"DataBufferAdapter", (String)"You must supply a resource ID for a TextView");
            throw new IllegalStateException("DataBufferAdapter requires the resource ID to be a TextView", var2_3);
        }
        T t2 = this.getItem(n2);
        if (t2 instanceof CharSequence) {
            viewGroup.setText((CharSequence)t2);
            return view;
        }
        viewGroup.setText((CharSequence)t2.toString());
        return view;
    }

    public void append(DataBuffer<T> dataBuffer) {
        this.Hg.add(dataBuffer);
        if (this.Hi) {
            this.notifyDataSetChanged();
        }
    }

    public void clear() {
        Iterator<DataBuffer<T>> iterator = this.Hg.iterator();
        while (iterator.hasNext()) {
            iterator.next().close();
        }
        this.Hg.clear();
        if (this.Hi) {
            this.notifyDataSetChanged();
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getCount() {
        Iterator<DataBuffer<T>> iterator = this.Hg.iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            n2 = iterator.next().getCount() + n2;
        }
        return n2;
    }

    public View getDropDownView(int n2, View view, ViewGroup viewGroup) {
        return this.a(n2, view, viewGroup, this.He);
    }

    public T getItem(int n2) throws CursorIndexOutOfBoundsException {
        Iterator iterator = this.Hg.iterator();
        int n3 = n2;
        while (iterator.hasNext()) {
            DataBuffer<T> dataBuffer = iterator.next();
            int n4 = dataBuffer.getCount();
            if (n4 <= n3) {
                n3 -= n4;
                continue;
            }
            try {
                iterator = dataBuffer.get(n3);
            }
            catch (CursorIndexOutOfBoundsException var4_3) {
                throw new CursorIndexOutOfBoundsException(n2, this.getCount());
            }
            return (T)iterator;
        }
        throw new CursorIndexOutOfBoundsException(n2, this.getCount());
    }

    public long getItemId(int n2) {
        return n2;
    }

    public View getView(int n2, View view, ViewGroup viewGroup) {
        return this.a(n2, view, viewGroup, this.Hd);
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.Hi = true;
    }

    public void setDropDownViewResource(int n2) {
        this.He = n2;
    }

    public void setNotifyOnChange(boolean bl2) {
        this.Hi = bl2;
    }
}

