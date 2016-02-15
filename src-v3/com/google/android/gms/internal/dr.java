package com.google.android.gms.internal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Map;

public final class dr {
    private final Context mContext;
    private int mState;
    private String rh;
    private final float ri;
    private float rj;
    private float rk;
    private float rl;

    /* renamed from: com.google.android.gms.internal.dr.1 */
    class C03641 implements OnClickListener {
        final /* synthetic */ String rm;
        final /* synthetic */ dr rn;

        C03641(dr drVar, String str) {
            this.rn = drVar;
            this.rm = str;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.rn.mContext.startActivity(Intent.createChooser(new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", this.rm), "Share via"));
        }
    }

    /* renamed from: com.google.android.gms.internal.dr.2 */
    class C03652 implements OnClickListener {
        final /* synthetic */ dr rn;

        C03652(dr drVar) {
            this.rn = drVar;
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    public dr(Context context) {
        this.mState = 0;
        this.mContext = context;
        this.ri = context.getResources().getDisplayMetrics().density;
    }

    public dr(Context context, String str) {
        this(context);
        this.rh = str;
    }

    private void m798a(int i, float f, float f2) {
        if (i == 0) {
            this.mState = 0;
            this.rj = f;
            this.rk = f2;
            this.rl = f2;
        } else if (this.mState == -1) {
        } else {
            if (i == 2) {
                if (f2 > this.rk) {
                    this.rk = f2;
                } else if (f2 < this.rl) {
                    this.rl = f2;
                }
                if (this.rk - this.rl > BitmapDescriptorFactory.HUE_ORANGE * this.ri) {
                    this.mState = -1;
                    return;
                }
                if (this.mState == 0 || this.mState == 2) {
                    if (f - this.rj >= 50.0f * this.ri) {
                        this.rj = f;
                        this.mState++;
                    }
                } else if ((this.mState == 1 || this.mState == 3) && f - this.rj <= -50.0f * this.ri) {
                    this.rj = f;
                    this.mState++;
                }
                if (this.mState == 1 || this.mState == 3) {
                    if (f > this.rj) {
                        this.rj = f;
                    }
                } else if (this.mState == 2 && f < this.rj) {
                    this.rj = f;
                }
            } else if (i == 1 && this.mState == 4) {
                showDialog();
            }
        }
    }

    private void showDialog() {
        Object obj;
        if (TextUtils.isEmpty(this.rh)) {
            obj = "No debug information";
        } else {
            Uri build = new Builder().encodedQuery(this.rh).build();
            StringBuilder stringBuilder = new StringBuilder();
            Map b = dq.m787b(build);
            for (String str : b.keySet()) {
                stringBuilder.append(str).append(" = ").append((String) b.get(str)).append("\n\n");
            }
            obj = stringBuilder.toString().trim();
            if (TextUtils.isEmpty(obj)) {
                obj = "No debug information";
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setMessage(obj);
        builder.setTitle("Ad Information");
        builder.setPositiveButton("Share", new C03641(this, obj));
        builder.setNegativeButton("Close", new C03652(this));
        builder.create().show();
    }

    public void m799c(MotionEvent motionEvent) {
        int historySize = motionEvent.getHistorySize();
        for (int i = 0; i < historySize; i++) {
            m798a(motionEvent.getActionMasked(), motionEvent.getHistoricalX(0, i), motionEvent.getHistoricalY(0, i));
        }
        m798a(motionEvent.getActionMasked(), motionEvent.getX(), motionEvent.getY());
    }

    public void m800t(String str) {
        this.rh = str;
    }
}
