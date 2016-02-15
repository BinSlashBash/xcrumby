package com.google.android.gms.internal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.crumby.impl.device.DeviceFragment;

public final class bz {
    public static boolean m678a(Context context, cb cbVar, ci ciVar) {
        if (cbVar == null) {
            dw.m823z("No intent data for launcher overlay.");
            return false;
        }
        Intent intent = new Intent();
        if (TextUtils.isEmpty(cbVar.nO)) {
            dw.m823z("Open GMSG did not contain a URL.");
            return false;
        }
        if (TextUtils.isEmpty(cbVar.mimeType)) {
            intent.setData(Uri.parse(cbVar.nO));
        } else {
            intent.setDataAndType(Uri.parse(cbVar.nO), cbVar.mimeType);
        }
        intent.setAction("android.intent.action.VIEW");
        if (!TextUtils.isEmpty(cbVar.packageName)) {
            intent.setPackage(cbVar.packageName);
        }
        if (!TextUtils.isEmpty(cbVar.nP)) {
            String[] split = cbVar.nP.split(DeviceFragment.REGEX_BASE, 2);
            if (split.length < 2) {
                dw.m823z("Could not parse component name from open GMSG: " + cbVar.nP);
                return false;
            }
            intent.setClassName(split[0], split[1]);
        }
        try {
            dw.m822y("Launching an intent: " + intent);
            context.startActivity(intent);
            ciVar.m696U();
            return true;
        } catch (ActivityNotFoundException e) {
            dw.m823z(e.getMessage());
            return false;
        }
    }
}
