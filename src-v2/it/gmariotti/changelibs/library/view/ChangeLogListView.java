/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.Toast
 */
package it.gmariotti.changelibs.library.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import it.gmariotti.changelibs.R;
import it.gmariotti.changelibs.library.Constants;
import it.gmariotti.changelibs.library.Util;
import it.gmariotti.changelibs.library.internal.ChangeLog;
import it.gmariotti.changelibs.library.internal.ChangeLogAdapter;
import it.gmariotti.changelibs.library.internal.ChangeLogRow;
import it.gmariotti.changelibs.library.parser.XmlParser;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ChangeLogListView
extends ListView
implements AdapterView.OnItemClickListener {
    protected static String TAG = "ChangeLogListView";
    protected ChangeLogAdapter mAdapter;
    protected int mChangeLogFileResourceId = Constants.mChangeLogFileResourceId;
    protected String mChangeLogFileResourceUrl = null;
    protected int mRowHeaderLayoutId = Constants.mRowHeaderLayoutId;
    protected int mRowLayoutId = Constants.mRowLayoutId;

    public ChangeLogListView(Context context) {
        super(context);
        this.init(null, 0);
    }

    public ChangeLogListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(attributeSet, 0);
    }

    public ChangeLogListView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.init(attributeSet, n2);
    }

    protected void init(AttributeSet attributeSet, int n2) {
        this.initAttrs(attributeSet, n2);
        this.initAdapter();
        this.setDividerHeight(0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void initAdapter() {
        try {
            XmlParser xmlParser = this.mChangeLogFileResourceUrl != null ? new XmlParser(this.getContext(), this.mChangeLogFileResourceUrl) : new XmlParser(this.getContext(), this.mChangeLogFileResourceId);
            ChangeLog changeLog = new ChangeLog();
            if (changeLog != null) {
                this.mAdapter = new ChangeLogAdapter(this.getContext(), changeLog.getRows());
                this.mAdapter.setmRowLayoutId(this.mRowLayoutId);
                this.mAdapter.setmRowHeaderLayoutId(this.mRowHeaderLayoutId);
                if (this.mChangeLogFileResourceUrl == null || this.mChangeLogFileResourceUrl != null && Util.isConnected(this.getContext())) {
                    new ParseAsyncTask(this.mAdapter, xmlParser).execute((Object[])new Void[0]);
                } else {
                    Toast.makeText((Context)this.getContext(), (int)R.string.changelog_internal_error_internet_connection, (int)1).show();
                }
                this.setAdapter(this.mAdapter);
                return;
            }
            this.setAdapter(null);
            return;
        }
        catch (Exception var1_2) {
            Log.e((String)TAG, (String)this.getResources().getString(R.string.changelog_internal_error_parsing), (Throwable)var1_2);
            return;
        }
    }

    protected void initAttrs(AttributeSet attributeSet, int n2) {
        attributeSet = this.getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.ChangeLogListView, n2, n2);
        try {
            this.mRowLayoutId = attributeSet.getResourceId(0, this.mRowLayoutId);
            this.mRowHeaderLayoutId = attributeSet.getResourceId(1, this.mRowHeaderLayoutId);
            this.mChangeLogFileResourceId = attributeSet.getResourceId(2, this.mChangeLogFileResourceId);
            this.mChangeLogFileResourceUrl = attributeSet.getString(3);
            return;
        }
        finally {
            attributeSet.recycle();
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
    }

    public void setAdapter(ChangeLogAdapter changeLogAdapter) {
        super.setAdapter((ListAdapter)changeLogAdapter);
    }

    protected class ParseAsyncTask
    extends AsyncTask<Void, Void, ChangeLog> {
        private ChangeLogAdapter mAdapter;
        private XmlParser mParse;

        public ParseAsyncTask(ChangeLogAdapter changeLogAdapter, XmlParser xmlParser) {
            this.mAdapter = changeLogAdapter;
            this.mParse = xmlParser;
        }

        protected /* varargs */ ChangeLog doInBackground(Void ... object) {
            try {
                if (this.mParse != null) {
                    object = this.mParse.readChangeLogFile();
                    return object;
                }
            }
            catch (Exception var1_2) {
                Log.e((String)ChangeLogListView.TAG, (String)ChangeLogListView.this.getResources().getString(R.string.changelog_internal_error_parsing), (Throwable)var1_2);
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void onPostExecute(ChangeLog iterator) {
            if (iterator != null) {
                if (Build.VERSION.SDK_INT >= 11) {
                    this.mAdapter.addAll(iterator.getRows());
                } else if (iterator.getRows() != null) {
                    for (ChangeLogRow changeLogRow : iterator.getRows()) {
                        this.mAdapter.add((Object)changeLogRow);
                    }
                }
                this.mAdapter.notifyDataSetChanged();
            }
        }
    }

}

