/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 */
package com.google.android.gms.internal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.internal.dq;
import java.util.Set;

public final class dr {
    private final Context mContext;
    private int mState = 0;
    private String rh;
    private final float ri;
    private float rj;
    private float rk;
    private float rl;

    public dr(Context context) {
        this.mContext = context;
        this.ri = context.getResources().getDisplayMetrics().density;
    }

    public dr(Context context, String string2) {
        this(context);
        this.rh = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(int n2, float f2, float f3) {
        if (n2 == 0) {
            this.mState = 0;
            this.rj = f2;
            this.rk = f3;
            this.rl = f3;
            return;
        } else {
            if (this.mState == -1) return;
            {
                if (n2 == 2) {
                    if (f3 > this.rk) {
                        this.rk = f3;
                    } else if (f3 < this.rl) {
                        this.rl = f3;
                    }
                    if (this.rk - this.rl > 30.0f * this.ri) {
                        this.mState = -1;
                        return;
                    }
                    if (this.mState == 0 || this.mState == 2) {
                        if (f2 - this.rj >= 50.0f * this.ri) {
                            this.rj = f2;
                            ++this.mState;
                        }
                    } else if ((this.mState == 1 || this.mState == 3) && f2 - this.rj <= -50.0f * this.ri) {
                        this.rj = f2;
                        ++this.mState;
                    }
                    if (this.mState == 1 || this.mState == 3) {
                        if (f2 <= this.rj) return;
                        {
                            this.rj = f2;
                            return;
                        }
                    } else {
                        if (this.mState != 2 || f2 >= this.rj) return;
                        {
                            this.rj = f2;
                            return;
                        }
                    }
                } else {
                    if (n2 != 1 || this.mState != 4) return;
                    {
                        this.showDialog();
                        return;
                    }
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showDialog() {
        Object object;
        void var1_4;
        if (!TextUtils.isEmpty((CharSequence)this.rh)) {
            object = new Uri.Builder().encodedQuery(this.rh).build();
            StringBuilder stringBuilder = new StringBuilder();
            object = dq.b((Uri)object);
            for (String string2 : object.keySet()) {
                stringBuilder.append(string2).append(" = ").append((String)object.get(string2)).append("\n\n");
            }
            String string3 = stringBuilder.toString().trim();
            if (TextUtils.isEmpty((CharSequence)string3)) {
                String string4 = "No debug information";
            }
        } else {
            String string5 = "No debug information";
        }
        object = new AlertDialog.Builder(this.mContext);
        object.setMessage((CharSequence)var1_4);
        object.setTitle((CharSequence)"Ad Information");
        object.setPositiveButton((CharSequence)"Share", new DialogInterface.OnClickListener((String)var1_4){
            final /* synthetic */ String rm;

            public void onClick(DialogInterface dialogInterface, int n2) {
                dr.this.mContext.startActivity(Intent.createChooser((Intent)new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", this.rm), (CharSequence)"Share via"));
            }
        });
        object.setNegativeButton((CharSequence)"Close", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
            }
        });
        object.create().show();
    }

    public void c(MotionEvent motionEvent) {
        int n2 = motionEvent.getHistorySize();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.a(motionEvent.getActionMasked(), motionEvent.getHistoricalX(0, i2), motionEvent.getHistoricalY(0, i2));
        }
        this.a(motionEvent.getActionMasked(), motionEvent.getX(), motionEvent.getY());
    }

    public void t(String string2) {
        this.rh = string2;
    }

}

