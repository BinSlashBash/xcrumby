/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.Html
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ArrayAdapter
 *  android.widget.TextView
 */
package it.gmariotti.changelibs.library.internal;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import it.gmariotti.changelibs.R;
import it.gmariotti.changelibs.library.Constants;
import it.gmariotti.changelibs.library.internal.ChangeLogRow;
import java.util.List;

public class ChangeLogAdapter
extends ArrayAdapter<ChangeLogRow> {
    protected static final int TYPE_HEADER = 1;
    protected static final int TYPE_ROW = 0;
    private final Context mContext;
    private int mRowHeaderLayoutId = Constants.mRowHeaderLayoutId;
    private int mRowLayoutId = Constants.mRowLayoutId;
    private int mStringVersionHeader = Constants.mStringVersionHeader;

    public ChangeLogAdapter(Context context, List<ChangeLogRow> list) {
        super(context, 0, list);
        this.mContext = context;
    }

    public int getItemViewType(int n2) {
        if (((ChangeLogRow)this.getItem(n2)).isHeader()) {
            return 1;
        }
        return 0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public View getView(int var1_1, View var2_2, ViewGroup var3_3) {
        var6_4 = (ChangeLogRow)this.getItem(var1_1);
        var1_1 = this.getItemViewType(var1_1);
        var7_5 = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
        switch (var1_1) {
            default: {
                return var2_2;
            }
            case 1: {
                var4_6 = null;
                if (var2_2 != null) {
                    var4_6 = var2_2.getTag();
                    var4_6 = var4_6 instanceof ViewHolderHeader != false ? (ViewHolderHeader)var4_6 : null;
                }
                if (var2_2 == null) ** GOTO lbl-1000
                var5_8 = var4_6;
                if (var4_6 == null) lbl-1000: // 2 sources:
                {
                    var2_2 = var7_5.inflate(this.mRowHeaderLayoutId, (ViewGroup)var3_3, false);
                    var5_8 = new ViewHolderHeader((TextView)var2_2.findViewById(R.id.chg_headerVersion), (TextView)var2_2.findViewById(R.id.chg_headerDate));
                    var2_2.setTag(var5_8);
                }
                var3_3 = var2_2;
                if (var6_4 == null) return var3_3;
                var3_3 = var2_2;
                if (var5_8 == null) return var3_3;
                if (var5_8.version != null) {
                    var3_3 = new StringBuilder();
                    var4_6 = this.getContext().getString(this.mStringVersionHeader);
                    if (var4_6 != null) {
                        var3_3.append((String)var4_6);
                    }
                    var3_3.append(var6_4.versionName);
                    var5_8.version.setText((CharSequence)var3_3.toString());
                }
                var3_3 = var2_2;
                if (var5_8.date == null) return var3_3;
                if (var6_4.changeDate != null) {
                    var5_8.date.setText((CharSequence)var6_4.changeDate);
                    var5_8.date.setVisibility(0);
                    return var2_2;
                }
                var5_8.date.setText((CharSequence)"");
                var5_8.date.setVisibility(8);
                return var2_2;
            }
            case 0: 
        }
        var4_7 = null;
        if (var2_2 != null) {
            var4_7 = var2_2.getTag();
            var4_7 = var4_7 instanceof ViewHolderRow != false ? (ViewHolderRow)var4_7 : null;
        }
        if (var2_2 == null) ** GOTO lbl-1000
        var5_9 = var4_7;
        if (var4_7 == null) lbl-1000: // 2 sources:
        {
            var2_2 = var7_5.inflate(this.mRowLayoutId, (ViewGroup)var3_3, false);
            var5_9 = new ViewHolderRow((TextView)var2_2.findViewById(R.id.chg_text), (TextView)var2_2.findViewById(R.id.chg_textbullet));
            var2_2.setTag(var5_9);
        }
        var3_3 = var2_2;
        if (var6_4 == null) return var3_3;
        var3_3 = var2_2;
        if (var5_9 == null) return var3_3;
        if (var5_9.text != null) {
            var5_9.text.setText((CharSequence)Html.fromHtml((String)var6_4.getChangeText(this.mContext)));
            var5_9.text.setMovementMethod(LinkMovementMethod.getInstance());
        }
        var3_3 = var2_2;
        if (var5_9.bulletText == null) return var3_3;
        if (var6_4.isBulletedList()) {
            var5_9.bulletText.setVisibility(0);
            return var2_2;
        }
        var5_9.bulletText.setVisibility(8);
        return var2_2;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public int getmRowHeaderLayoutId() {
        return this.mRowHeaderLayoutId;
    }

    public int getmRowLayoutId() {
        return this.mRowLayoutId;
    }

    public int getmStringVersionHeader() {
        return this.mStringVersionHeader;
    }

    public boolean isEnabled(int n2) {
        return false;
    }

    public void setmRowHeaderLayoutId(int n2) {
        this.mRowHeaderLayoutId = n2;
    }

    public void setmRowLayoutId(int n2) {
        this.mRowLayoutId = n2;
    }

    public void setmStringVersionHeader(int n2) {
        this.mStringVersionHeader = n2;
    }

    static class ViewHolderHeader {
        TextView date;
        TextView version;

        public ViewHolderHeader(TextView textView, TextView textView2) {
            this.version = textView;
            this.date = textView2;
        }
    }

    static class ViewHolderRow {
        TextView bulletText;
        TextView text;

        public ViewHolderRow(TextView textView, TextView textView2) {
            this.text = textView;
            this.bulletText = textView2;
        }
    }

}

