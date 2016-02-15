/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.cast;

import android.text.TextUtils;
import java.util.Collection;

public final class CastMediaControlIntent {
    public static final String ACTION_SYNC_STATUS = "com.google.android.gms.cast.ACTION_SYNC_STATUS";
    public static final String CATEGORY_CAST = "com.google.android.gms.cast.CATEGORY_CAST";
    public static final String DEFAULT_MEDIA_RECEIVER_APPLICATION_ID = "CC1AD845";
    public static final int ERROR_CODE_REQUEST_FAILED = 1;
    public static final int ERROR_CODE_SESSION_START_FAILED = 2;
    public static final int ERROR_CODE_TEMPORARILY_DISCONNECTED = 3;
    public static final String EXTRA_CAST_APPLICATION_ID = "com.google.android.gms.cast.EXTRA_CAST_APPLICATION_ID";
    public static final String EXTRA_CAST_RELAUNCH_APPLICATION = "com.google.android.gms.cast.EXTRA_CAST_RELAUNCH_APPLICATION";
    public static final String EXTRA_CAST_STOP_APPLICATION_WHEN_SESSION_ENDS = "com.google.android.gms.cast.EXTRA_CAST_STOP_APPLICATION_WHEN_SESSION_ENDS";
    public static final String EXTRA_CUSTOM_DATA = "com.google.android.gms.cast.EXTRA_CUSTOM_DATA";
    public static final String EXTRA_DEBUG_LOGGING_ENABLED = "com.google.android.gms.cast.EXTRA_DEBUG_LOGGING_ENABLED";
    public static final String EXTRA_ERROR_CODE = "com.google.android.gms.cast.EXTRA_ERROR_CODE";

    private CastMediaControlIntent() {
    }

    private static String a(String charSequence, String string2, Collection<String> collection) throws IllegalArgumentException {
        charSequence = new StringBuffer((String)charSequence);
        if (string2 != null) {
            if (!string2.matches("[A-F0-9]+")) {
                throw new IllegalArgumentException("Invalid application ID: " + string2);
            }
            charSequence.append("/").append(string2);
        }
        if (collection != null) {
            if (collection.isEmpty()) {
                throw new IllegalArgumentException("Must specify at least one namespace");
            }
            for (String string3 : collection) {
                if (!TextUtils.isEmpty((CharSequence)string3) && !string3.trim().equals("")) continue;
                throw new IllegalArgumentException("Namespaces must not be null or empty");
            }
            if (string2 == null) {
                charSequence.append("/");
            }
            charSequence.append("/").append(TextUtils.join((CharSequence)",", collection));
        }
        return charSequence.toString();
    }

    public static String categoryForCast(String string2) throws IllegalArgumentException {
        if (string2 == null) {
            throw new IllegalArgumentException("applicationId cannot be null");
        }
        return CastMediaControlIntent.a("com.google.android.gms.cast.CATEGORY_CAST", string2, null);
    }

    public static String categoryForCast(String string2, Collection<String> collection) {
        if (string2 == null) {
            throw new IllegalArgumentException("applicationId cannot be null");
        }
        if (collection == null) {
            throw new IllegalArgumentException("namespaces cannot be null");
        }
        return CastMediaControlIntent.a("com.google.android.gms.cast.CATEGORY_CAST", string2, collection);
    }

    public static String categoryForCast(Collection<String> collection) throws IllegalArgumentException {
        if (collection == null) {
            throw new IllegalArgumentException("namespaces cannot be null");
        }
        return CastMediaControlIntent.a("com.google.android.gms.cast.CATEGORY_CAST", null, collection);
    }

    public static String categoryForRemotePlayback() {
        return CastMediaControlIntent.a("com.google.android.gms.cast.CATEGORY_CAST_REMOTE_PLAYBACK", null, null);
    }

    public static String categoryForRemotePlayback(String string2) throws IllegalArgumentException {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("applicationId cannot be null or empty");
        }
        return CastMediaControlIntent.a("com.google.android.gms.cast.CATEGORY_CAST_REMOTE_PLAYBACK", string2, null);
    }
}

