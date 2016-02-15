/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 */
package com.uservoice.uservoicesdk;

import android.content.Context;
import android.content.Intent;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.activity.ContactActivity;
import com.uservoice.uservoicesdk.activity.ForumActivity;
import com.uservoice.uservoicesdk.activity.PortalActivity;
import com.uservoice.uservoicesdk.activity.PostIdeaActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import java.util.Map;

public class UserVoice {
    public static String getVersion() {
        return "1.1.1";
    }

    public static void init(Config config, Context context) {
        Session.reset();
        Babayaga.init(context);
        Babayaga.setUserTraits(config.getUserTraits());
        Session.getInstance().setContext(context);
        Session.getInstance().setConfig(config);
    }

    public static void launchContactUs(Context context) {
        context.startActivity(new Intent(context, (Class)ContactActivity.class));
    }

    public static void launchForum(Context context) {
        context.startActivity(new Intent(context, (Class)ForumActivity.class));
    }

    public static void launchPostIdea(Context context) {
        context.startActivity(new Intent(context, (Class)PostIdeaActivity.class));
    }

    public static void launchUserVoice(Context context) {
        context.startActivity(new Intent(context, (Class)PortalActivity.class));
    }

    public static void setExternalId(String string2, String string3) {
        Session.getInstance().setExternalId(string2, string3);
    }

    public static void track(String string2) {
        UserVoice.track(string2, null);
    }

    public static void track(String string2, Map<String, Object> map) {
        Babayaga.track(string2, map);
    }
}

