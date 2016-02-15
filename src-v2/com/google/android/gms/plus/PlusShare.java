/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.plus;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.ih;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.internal.e;
import com.google.android.gms.plus.model.people.Person;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PlusShare {
    public static final String EXTRA_CALL_TO_ACTION = "com.google.android.apps.plus.CALL_TO_ACTION";
    public static final String EXTRA_CONTENT_DEEP_LINK_ID = "com.google.android.apps.plus.CONTENT_DEEP_LINK_ID";
    public static final String EXTRA_CONTENT_DEEP_LINK_METADATA = "com.google.android.apps.plus.CONTENT_DEEP_LINK_METADATA";
    public static final String EXTRA_CONTENT_URL = "com.google.android.apps.plus.CONTENT_URL";
    public static final String EXTRA_IS_INTERACTIVE_POST = "com.google.android.apps.plus.GOOGLE_INTERACTIVE_POST";
    public static final String EXTRA_SENDER_ID = "com.google.android.apps.plus.SENDER_ID";
    public static final String KEY_CALL_TO_ACTION_DEEP_LINK_ID = "deepLinkId";
    public static final String KEY_CALL_TO_ACTION_LABEL = "label";
    public static final String KEY_CALL_TO_ACTION_URL = "url";
    public static final String KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION = "description";
    public static final String KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL = "thumbnailUrl";
    public static final String KEY_CONTENT_DEEP_LINK_METADATA_TITLE = "title";
    public static final String PARAM_CONTENT_DEEP_LINK_ID = "deep_link_id";

    @Deprecated
    protected PlusShare() {
        throw new AssertionError();
    }

    public static Bundle a(String string2, String string3, Uri uri) {
        Bundle bundle = new Bundle();
        bundle.putString("title", string2);
        bundle.putString("description", string3);
        if (uri != null) {
            bundle.putString("thumbnailUrl", uri.toString());
        }
        return bundle;
    }

    protected static boolean bd(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            Log.e((String)"GooglePlusPlatform", (String)"The provided deep-link ID is empty.");
            return false;
        }
        if (string2.contains(" ")) {
            Log.e((String)"GooglePlusPlatform", (String)"Spaces are not allowed in deep-link IDs.");
            return false;
        }
        return true;
    }

    public static Person createPerson(String string2, String string3) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("MinimalPerson ID must not be empty.");
        }
        if (TextUtils.isEmpty((CharSequence)string3)) {
            throw new IllegalArgumentException("Display name must not be empty.");
        }
        return new ih(string3, string2, null, 0, null);
    }

    public static String getDeepLinkId(Intent intent) {
        String string2;
        String string3 = string2 = null;
        if (intent != null) {
            string3 = string2;
            if (intent.getData() != null) {
                string3 = intent.getData().getQueryParameter("deep_link_id");
            }
        }
        return string3;
    }

    public static class Builder {
        private boolean TZ;
        private ArrayList<Uri> Ua;
        private final Context mContext;
        private final Intent mIntent;

        public Builder(Activity activity) {
            this.mContext = activity;
            this.mIntent = new Intent().setAction("android.intent.action.SEND");
            this.mIntent.addFlags(524288);
            if (activity != null && activity.getComponentName() != null) {
                this.TZ = true;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Deprecated
        public Builder(Activity object, PlusClient plusClient) {
            this((Activity)object);
            boolean bl2 = plusClient != null;
            fq.a(bl2, "PlusClient must not be null.");
            fq.a(plusClient.isConnected(), "PlusClient must be connected to create an interactive post.");
            fq.a(plusClient.iI().bg("https://www.googleapis.com/auth/plus.login"), "Must request PLUS_LOGIN scope in PlusClient to create an interactive post.");
            object = plusClient.getCurrentPerson();
            object = object != null ? object.getId() : "0";
            this.mIntent.putExtra("com.google.android.apps.plus.SENDER_ID", (String)object);
        }

        public Builder(Context context) {
            this.mContext = context;
            this.mIntent = new Intent().setAction("android.intent.action.SEND");
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder addCallToAction(String string2, Uri uri, String string3) {
            fq.a(this.TZ, "Must include the launching activity with PlusShare.Builder constructor before setting call-to-action");
            boolean bl2 = uri != null && !TextUtils.isEmpty((CharSequence)uri.toString());
            fq.b(bl2, (Object)"Must provide a call to action URL");
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                bundle.putString("label", string2);
            }
            bundle.putString("url", uri.toString());
            if (!TextUtils.isEmpty((CharSequence)string3)) {
                fq.a(PlusShare.bd(string3), "The specified deep-link ID was malformed.");
                bundle.putString("deepLinkId", string3);
            }
            this.mIntent.putExtra("com.google.android.apps.plus.CALL_TO_ACTION", bundle);
            this.mIntent.putExtra("com.google.android.apps.plus.GOOGLE_INTERACTIVE_POST", true);
            this.mIntent.setType("text/plain");
            return this;
        }

        public Builder addStream(Uri uri) {
            Uri uri2 = (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            if (uri2 == null) {
                return this.setStream(uri);
            }
            if (this.Ua == null) {
                this.Ua = new ArrayList();
            }
            this.Ua.add(uri2);
            this.Ua.add(uri);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Intent getIntent() {
            boolean bl2 = true;
            boolean bl3 = this.Ua != null && this.Ua.size() > 1;
            boolean bl4 = "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
            boolean bl5 = this.mIntent.getBooleanExtra("com.google.android.apps.plus.GOOGLE_INTERACTIVE_POST", false);
            boolean bl6 = !bl3 || !bl5;
            fq.a(bl6, "Call-to-action buttons are only available for URLs.");
            bl6 = !bl5 || this.mIntent.hasExtra("com.google.android.apps.plus.CONTENT_URL");
            fq.a(bl6, "The content URL is required for interactive posts.");
            bl6 = bl2;
            if (bl5) {
                bl6 = bl2;
                if (!this.mIntent.hasExtra("com.google.android.apps.plus.CONTENT_URL")) {
                    bl6 = this.mIntent.hasExtra("com.google.android.apps.plus.CONTENT_DEEP_LINK_ID") ? bl2 : false;
                }
            }
            fq.a(bl6, "Must set content URL or content deep-link ID to use a call-to-action button.");
            if (this.mIntent.hasExtra("com.google.android.apps.plus.CONTENT_DEEP_LINK_ID")) {
                fq.a(PlusShare.bd(this.mIntent.getStringExtra("com.google.android.apps.plus.CONTENT_DEEP_LINK_ID")), "The specified deep-link ID was malformed.");
            }
            if (!bl3 && bl4) {
                this.mIntent.setAction("android.intent.action.SEND");
                if (this.Ua != null && !this.Ua.isEmpty()) {
                    this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)this.Ua.get(0));
                } else {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                }
                this.Ua = null;
            }
            if (bl3 && !bl4) {
                this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
                if (this.Ua != null && !this.Ua.isEmpty()) {
                    this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.Ua);
                } else {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                }
            }
            if ("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE".equals(this.mIntent.getAction())) {
                this.mIntent.setPackage("com.google.android.gms");
                return this.mIntent;
            }
            if (!this.mIntent.hasExtra("android.intent.extra.STREAM")) {
                this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_GOOGLE");
                this.mIntent.setPackage("com.google.android.gms");
                return this.mIntent;
            }
            this.mIntent.setPackage("com.google.android.apps.plus");
            return this.mIntent;
        }

        public Builder setContentDeepLinkId(String string2) {
            return this.setContentDeepLinkId(string2, null, null, null);
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder setContentDeepLinkId(String string2, String string3, String string4, Uri uri) {
            fq.b(this.TZ, (Object)"Must include the launching activity with PlusShare.Builder constructor before setting deep links");
            boolean bl2 = !TextUtils.isEmpty((CharSequence)string2);
            fq.b(bl2, (Object)"The deepLinkId parameter is required.");
            string3 = PlusShare.a(string3, string4, uri);
            this.mIntent.putExtra("com.google.android.apps.plus.CONTENT_DEEP_LINK_ID", string2);
            this.mIntent.putExtra("com.google.android.apps.plus.CONTENT_DEEP_LINK_METADATA", (Bundle)string3);
            this.mIntent.setType("text/plain");
            return this;
        }

        public Builder setContentUrl(Uri uri) {
            String string2 = null;
            if (uri != null) {
                string2 = uri.toString();
            }
            if (TextUtils.isEmpty((CharSequence)string2)) {
                this.mIntent.removeExtra("com.google.android.apps.plus.CONTENT_URL");
                return this;
            }
            this.mIntent.putExtra("com.google.android.apps.plus.CONTENT_URL", string2);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder setRecipients(Person object, List<Person> list) {
            Intent intent = this.mIntent;
            object = object != null ? object.getId() : "0";
            intent.putExtra("com.google.android.apps.plus.SENDER_ID", (String)object);
            return this.setRecipients(list);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Deprecated
        public Builder setRecipients(List<Person> iterator) {
            int n2 = iterator != null ? iterator.size() : 0;
            if (n2 == 0) {
                this.mIntent.removeExtra("com.google.android.apps.plus.RECIPIENT_IDS");
                this.mIntent.removeExtra("com.google.android.apps.plus.RECIPIENT_DISPLAY_NAMES");
                return this;
            }
            ArrayList<String> arrayList = new ArrayList<String>(n2);
            ArrayList<String> arrayList2 = new ArrayList<String>(n2);
            iterator = iterator.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.mIntent.putStringArrayListExtra("com.google.android.apps.plus.RECIPIENT_IDS", arrayList);
                    this.mIntent.putStringArrayListExtra("com.google.android.apps.plus.RECIPIENT_DISPLAY_NAMES", arrayList2);
                    return this;
                }
                Person person = (Person)iterator.next();
                arrayList.add(person.getId());
                arrayList2.add(person.getDisplayName());
            } while (true);
        }

        public Builder setStream(Uri uri) {
            this.Ua = null;
            this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)uri);
            return this;
        }

        public Builder setText(CharSequence charSequence) {
            this.mIntent.putExtra("android.intent.extra.TEXT", charSequence);
            return this;
        }

        public Builder setType(String string2) {
            this.mIntent.setType(string2);
            return this;
        }
    }

}

