/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.AsyncTask
 */
package com.uservoice.uservoicesdk.babayaga;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.uservoice.uservoicesdk.babayaga.BabayagaTask;
import com.uservoice.uservoicesdk.model.BaseModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Babayaga {
    public static String CHANNEL;
    static String DOMAIN;
    public static String EXTERNAL_CHANNEL;
    private static SharedPreferences prefs;
    private static Map<String, Object> traits;
    private static String uvts;

    static {
        DOMAIN = "by.uservoice.com";
        CHANNEL = "d";
        EXTERNAL_CHANNEL = "x";
    }

    public static String getUvts() {
        return uvts;
    }

    public static void init(Context context) {
        prefs = context.getSharedPreferences("uv", 0);
        if (prefs.contains("uvts")) {
            uvts = prefs.getString("uvts", null);
        }
        Babayaga.track(Event.VIEW_APP);
    }

    public static void setUserTraits(Map<String, Object> map) {
        traits = map;
    }

    public static void setUvts(String string2) {
        uvts = string2;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("uvts", string2);
        editor.commit();
    }

    public static void track(Event event) {
        Babayaga.track(event, null);
    }

    public static void track(Event event, int n2) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", n2);
        Babayaga.track(event, hashMap);
    }

    public static void track(Event event, String string2, List<? extends BaseModel> object) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        ArrayList<Integer> arrayList = new ArrayList<Integer>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(((BaseModel)object.next()).getId());
        }
        hashMap.put("ids", arrayList);
        hashMap.put("text", string2);
        Babayaga.track(event, hashMap);
    }

    public static void track(Event event, Map<String, Object> map) {
        Babayaga.track(event.getCode(), map);
    }

    public static void track(String string2, Map<String, Object> map) {
        new BabayagaTask(string2, uvts, traits, map).execute((Object[])new String[0]);
    }

    public static enum Event {
        VIEW_APP("g"),
        VIEW_FORUM("m"),
        VIEW_TOPIC("c"),
        VIEW_KB("k"),
        VIEW_CHANNEL("o"),
        VIEW_IDEA("i"),
        VIEW_ARTICLE("f"),
        AUTHENTICATE("u"),
        SEARCH_IDEAS("s"),
        SEARCH_ARTICLES("r"),
        VOTE_IDEA("v"),
        VOTE_ARTICLE("z"),
        SUBMIT_TICKET("t"),
        SUBMIT_IDEA("d"),
        SUBSCRIBE_IDEA("b"),
        IDENTIFY("y"),
        COMMENT_IDEA("h");
        
        private final String code;

        private Event(String string3) {
            this.code = string3;
        }

        public String getCode() {
            return this.code;
        }
    }

    private static class Track {
        public String event;
        public Map<String, Object> eventProps;

        public Track(String string2, Map<String, Object> map) {
            this.event = string2;
            this.eventProps = map;
        }
    }

}

