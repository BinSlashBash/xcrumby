package com.google.android.gms.internal;

import android.text.TextUtils;
import com.crumby.C0065R;
import com.google.ads.AdSize;
import com.google.android.gms.common.images.WebImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ew {
    private static final String[] Ac;
    private static final String Ad;
    private static final er zb;

    static {
        zb = new er("MetadataUtils");
        Ac = new String[]{"Z", "+hh", "+hhmm", "+hh:mm"};
        Ad = "yyyyMMdd'T'HHmmss" + Ac[0];
    }

    public static String m909a(Calendar calendar) {
        if (calendar == null) {
            zb.m898b("Calendar object cannot be null", new Object[0]);
            return null;
        }
        String str = Ad;
        if (calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0) {
            str = "yyyyMMdd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        str = simpleDateFormat.format(calendar.getTime());
        return str.endsWith("+0000") ? str.replace("+0000", Ac[0]) : str;
    }

    public static void m910a(List<WebImage> list, JSONObject jSONObject) {
        try {
            list.clear();
            JSONArray jSONArray = jSONObject.getJSONArray("images");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                try {
                    list.add(new WebImage(jSONArray.getJSONObject(i)));
                } catch (IllegalArgumentException e) {
                }
            }
        } catch (JSONException e2) {
        }
    }

    public static void m911a(JSONObject jSONObject, List<WebImage> list) {
        if (list != null && !list.isEmpty()) {
            Object jSONArray = new JSONArray();
            for (WebImage dB : list) {
                jSONArray.put(dB.dB());
            }
            try {
                jSONObject.put("images", jSONArray);
            } catch (JSONException e) {
            }
        }
    }

    public static Calendar ac(String str) {
        if (TextUtils.isEmpty(str)) {
            zb.m898b("Input string is empty or null", new Object[0]);
            return null;
        }
        String ad = ad(str);
        if (TextUtils.isEmpty(ad)) {
            zb.m898b("Invalid date format", new Object[0]);
            return null;
        }
        String ae = ae(str);
        String str2 = "yyyyMMdd";
        if (!TextUtils.isEmpty(ae)) {
            ad = ad + "T" + ae;
            str2 = ae.length() == "HHmmss".length() ? "yyyyMMdd'T'HHmmss" : Ad;
        }
        Calendar instance = GregorianCalendar.getInstance();
        try {
            instance.setTime(new SimpleDateFormat(str2).parse(ad));
            return instance;
        } catch (ParseException e) {
            zb.m898b("Error parsing string: %s", e.getMessage());
            return null;
        }
    }

    private static String ad(String str) {
        String str2 = null;
        if (TextUtils.isEmpty(str)) {
            zb.m898b("Input string is empty or null", new Object[0]);
        } else {
            try {
                str2 = str.substring(0, "yyyyMMdd".length());
            } catch (IndexOutOfBoundsException e) {
                zb.m899c("Error extracting the date: %s", e.getMessage());
            }
        }
        return str2;
    }

    private static String ae(String str) {
        if (TextUtils.isEmpty(str)) {
            zb.m898b("string is empty or null", new Object[0]);
            return null;
        }
        int indexOf = str.indexOf(84);
        int i = indexOf + 1;
        if (indexOf != "yyyyMMdd".length()) {
            zb.m898b("T delimeter is not found", new Object[0]);
            return null;
        }
        try {
            String substring = str.substring(i);
            if (substring.length() == "HHmmss".length()) {
                return substring;
            }
            switch (substring.charAt("HHmmss".length())) {
                case C0065R.styleable.TwoWayView_android_hapticFeedbackEnabled /*43*/:
                case C0065R.styleable.TwoWayView_android_contentDescription /*45*/:
                    return af(substring) ? substring.replaceAll("([\\+\\-]\\d\\d):(\\d\\d)", "$1$2") : null;
                case AdSize.LARGE_AD_HEIGHT /*90*/:
                    return substring.length() == "HHmmss".length() + Ac[0].length() ? substring.substring(0, substring.length() - 1) + "+0000" : null;
                default:
                    return null;
            }
        } catch (IndexOutOfBoundsException e) {
            zb.m898b("Error extracting the time substring: %s", e.getMessage());
            return null;
        }
    }

    private static boolean af(String str) {
        int length = str.length();
        int length2 = "HHmmss".length();
        return length == Ac[1].length() + length2 || length == Ac[2].length() + length2 || length == length2 + Ac[3].length();
    }
}
