package com.tapstream.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ReferrerReceiver extends BroadcastReceiver {
    private static final String UUID_KEY = "TapstreamSDKUUID";

    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("referrer");
        if (stringExtra != null) {
            String str = UnsupportedUrlFragment.DISPLAY_NAME;
            try {
                str = URLDecoder.decode(stringExtra, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (str.length() > 0) {
                Editor edit = context.getApplicationContext().getSharedPreferences(UUID_KEY, 0).edit();
                edit.putString("referrer", str);
                edit.commit();
            }
        }
    }
}
