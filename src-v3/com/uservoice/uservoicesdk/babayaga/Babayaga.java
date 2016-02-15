package com.uservoice.uservoicesdk.babayaga;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.crumby.impl.danbooru.DanbooruImageFragment;
import com.crumby.impl.ehentai.HentaiSubGalleryFragment;
import com.uservoice.uservoicesdk.model.BaseModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Babayaga {
    public static String CHANNEL;
    static String DOMAIN;
    public static String EXTERNAL_CHANNEL;
    private static SharedPreferences prefs;
    private static Map<String, Object> traits;
    private static String uvts;

    public enum Event {
        VIEW_APP(HentaiSubGalleryFragment.BREADCRUMB_NAME),
        VIEW_FORUM("m"),
        VIEW_TOPIC("c"),
        VIEW_KB("k"),
        VIEW_CHANNEL("o"),
        VIEW_IDEA("i"),
        VIEW_ARTICLE("f"),
        AUTHENTICATE("u"),
        SEARCH_IDEAS(DanbooruImageFragment.BREADCRUMB_NAME),
        SEARCH_ARTICLES("r"),
        VOTE_IDEA("v"),
        VOTE_ARTICLE("z"),
        SUBMIT_TICKET("t"),
        SUBMIT_IDEA("d"),
        SUBSCRIBE_IDEA("b"),
        IDENTIFY("y"),
        COMMENT_IDEA("h");
        
        private final String code;

        private Event(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

    private static class Track {
        public String event;
        public Map<String, Object> eventProps;

        public Track(String event, Map<String, Object> eventProps) {
            this.event = event;
            this.eventProps = eventProps;
        }
    }

    static {
        DOMAIN = "by.uservoice.com";
        CHANNEL = "d";
        EXTERNAL_CHANNEL = "x";
    }

    public static void setUvts(String uvts) {
        uvts = uvts;
        Editor edit = prefs.edit();
        edit.putString("uvts", uvts);
        edit.commit();
    }

    public static void setUserTraits(Map<String, Object> traits) {
        traits = traits;
    }

    public static void track(Event event) {
        track(event, null);
    }

    public static void track(Event event, String searchText, List<? extends BaseModel> results) {
        Map props = new HashMap();
        List<Integer> ids = new ArrayList(results.size());
        for (BaseModel model : results) {
            ids.add(Integer.valueOf(model.getId()));
        }
        props.put("ids", ids);
        props.put("text", searchText);
        track(event, props);
    }

    public static void track(Event event, int id) {
        Map props = new HashMap();
        props.put("id", Integer.valueOf(id));
        track(event, props);
    }

    public static void track(Event event, Map<String, Object> eventProps) {
        track(event.getCode(), (Map) eventProps);
    }

    public static void track(String event, Map<String, Object> eventProps) {
        new BabayagaTask(event, uvts, traits, eventProps).execute(new String[0]);
    }

    public static void init(Context context) {
        prefs = context.getSharedPreferences("uv", 0);
        if (prefs.contains("uvts")) {
            uvts = prefs.getString("uvts", null);
        }
        track(Event.VIEW_APP);
    }

    public static String getUvts() {
        return uvts;
    }
}
