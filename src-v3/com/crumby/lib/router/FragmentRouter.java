package com.crumby.lib.router;

import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.danbooru.DanbooruGalleryFragment;
import com.crumby.impl.e621.e621Fragment;
import com.crumby.impl.furaffinity.FurAffinityFragment;
import com.crumby.impl.gelbooru.GelbooruFragment;
import com.crumby.impl.imgur.ImgurFragment;
import com.crumby.impl.konachan.KonachanFragment;
import com.crumby.impl.yandere.YandereFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public enum FragmentRouter {
    ;
    
    private static final String SETTINGS_FILENAME = "crumby_website_settings";
    private final int UNLIMITED;
    private Map<String, FragmentIndex> indexMap;
    FragmentLinkAdapter linkAdapter;
    private boolean savingSettings;
    private FragmentLinkTrie trie;

    /* renamed from: com.crumby.lib.router.FragmentRouter.1 */
    class C01091 implements Comparator<FragmentIndex> {
        C01091() {
        }

        public int compare(FragmentIndex lhs, FragmentIndex rhs) {
            return lhs.getDisplayName().compareTo(rhs.getDisplayName());
        }
    }

    static {
        $assertionsDisabled = !FragmentRouter.class.desiredAssertionStatus() ? true : $assertionsDisabled;
        INSTANCE = new FragmentRouter("INSTANCE", 0);
        $VALUES = new FragmentRouter[]{INSTANCE};
    }

    public void initialize(Context context) {
        if (this.linkAdapter == null) {
            long start = System.currentTimeMillis();
            this.linkAdapter = new FragmentLinkAdapter(context);
            buildIndexMap(context);
            Analytics.INSTANCE.newTimingEvent(AnalyticsCategories.ROUTING, start, "initialize", UnsupportedUrlFragment.DISPLAY_NAME);
        }
    }

    private void buildIndexMap(Context context) {
        this.indexMap = new LinkedHashMap();
        try {
            XmlPullParser parser = context.getResources().getXml(C0065R.xml.rules);
            for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                switch (eventType) {
                    case Std.STD_URL /*2*/:
                        if (!parser.getName().equals("site")) {
                            break;
                        }
                        FragmentIndex newIndex = new FragmentIndex(parser.getAttributeValue(null, "class"));
                        this.indexMap.put(newIndex.getFragmentClassName(), newIndex);
                        break;
                    default:
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        buildTreeFromBottom();
        applySettings(context);
    }

    private void buildTreeFromBottom() {
        for (FragmentIndex index : this.indexMap.values()) {
            if (index.getParent() == null && index.getParentClassName() != null) {
                index.setParent(getFragmentIndexById(index.getParentClassName()));
            }
        }
    }

    public List<FragmentIndex> buildBreadCrumbPath(String url) {
        return buildBreadCrumbPath((FragmentIndex) getExactIndexes(url, 1).get(0));
    }

    private List<FragmentIndex> buildBreadCrumbPath(FragmentIndex firstFragmentIndex) {
        List<FragmentIndex> parents;
        FragmentIndex parent = firstFragmentIndex.getParent();
        if (parent != null) {
            parents = buildBreadCrumbPath(parent);
        } else {
            parents = new ArrayList();
        }
        parents.add(firstFragmentIndex);
        return parents;
    }

    private FragmentIndex getFragmentIndexById(String id) {
        return (FragmentIndex) this.indexMap.get(id);
    }

    public void stopMatching() {
        if (this.trie != null) {
            this.trie.terminateSearch();
        }
    }

    public synchronized List<FragmentLink> findMatchingLinks(String query, boolean matchUrl, int maxResults) {
        List<FragmentLink> fragmentLinks;
        long start = System.currentTimeMillis();
        if ($assertionsDisabled || !Looper.getMainLooper().getThread().equals(Thread.currentThread())) {
            Thread.currentThread().setPriority(10);
            fragmentLinks = this.linkAdapter.search(query, matchUrl, maxResults);
            if (fragmentLinks != null) {
                Analytics.INSTANCE.newTimingEvent(AnalyticsCategories.ROUTING, start, "match links", query);
            }
        } else {
            throw new AssertionError();
        }
        return fragmentLinks;
    }

    public List<FragmentIndex> getExactIndexes(String query, int limit) {
        List<FragmentIndex> fragmentIndexes = new ArrayList();
        int i = 0;
        for (Entry<String, FragmentIndex> entry : this.indexMap.entrySet()) {
            if (query.matches(((FragmentIndex) entry.getValue()).getRegexUrl())) {
                fragmentIndexes.add(entry.getValue());
                i++;
                if (limit != -1 && i >= limit) {
                    break;
                }
            }
        }
        if (fragmentIndexes.isEmpty()) {
            fragmentIndexes.add(this.indexMap.get(CrumbyGalleryFragment.class.getName()));
        }
        return fragmentIndexes;
    }

    public GalleryViewerFragment getGalleryFragmentInstance(String url) {
        return getGalleryFragmentIndexByUrl(url).instantiate();
    }

    public FragmentIndex getGalleryFragmentIndexByUrl(String url) {
        return (FragmentIndex) getExactIndexes(url, 1).get(0);
    }

    public List<IndexSetting> getAllIndexSettings() {
        ArrayList<IndexSetting> settings = new ArrayList();
        List<FragmentIndex> rootIndexes = new ArrayList();
        for (FragmentIndex index : this.indexMap.values()) {
            if (!(index.getSetting() == null || index.shouldBeHidden())) {
                rootIndexes.add(index);
            }
        }
        Collections.sort(rootIndexes, new C01091());
        for (FragmentIndex index2 : rootIndexes) {
            settings.add(index2.getSetting());
        }
        return settings;
    }

    private void applySettings(Context context) {
        JsonObject savedSettings = null;
        try {
            savedSettings = (JsonObject) new JsonParser().parse(GalleryProducer.readStreamIntoString(context.openFileInput(SETTINGS_FILENAME)));
        } catch (FileNotFoundException e) {
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        for (FragmentIndex index : this.indexMap.values()) {
            if (index.getSettingAttributes() != null) {
                IndexSetting indexSetting = new IndexSetting(index);
                index.applySetting(indexSetting);
                if (savedSettings != null) {
                    try {
                        indexSetting.restoreFromJson((JsonObject) savedSettings.get(index.getFragmentClassName()));
                    } catch (Exception e3) {
                    }
                }
            }
        }
    }

    public JsonObject convertIndexesToJsonObj(List<IndexSetting> newIndexes) throws Exception {
        JsonObject object = new JsonObject();
        for (IndexSetting setting : newIndexes) {
            object.add(setting.getFragmentClassName(), setting.getAsJson());
        }
        return object;
    }

    public void saveSettings(Context context) {
        saveSettings(getAllIndexSettings(), context);
    }

    public void saveSettings(List<IndexSetting> newSettings, Context context) {
        if (context != null && !this.savingSettings) {
            this.savingSettings = true;
            FileOutputStream fos = null;
            try {
                JsonObject object = convertIndexesToJsonObj(newSettings);
                fos = context.openFileOutput(SETTINGS_FILENAME, 0);
                fos.write(object.toString().getBytes());
                fos.close();
            } catch (Exception e) {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                Toast toast = Toast.makeText(context, UnsupportedUrlFragment.DISPLAY_NAME, 0);
                toast.setGravity(80, 0, 0);
                TextView view = (TextView) View.inflate(context, C0065R.layout.toast_alert_error, null);
                view.setText("Could not save settings: " + e.getMessage());
                toast.setView(view);
                toast.show();
            }
            this.savingSettings = $assertionsDisabled;
        }
    }

    public IndexSetting getSettingByFragmentClass(Class clazz) {
        if (this.indexMap.get(clazz.getName()) == null) {
            return null;
        }
        return ((FragmentIndex) this.indexMap.get(clazz.getName())).getSetting();
    }

    public boolean wantsSafeImagesInTopLevelGallery(Class clazz) {
        if (clazz == null) {
            return $assertionsDisabled;
        }
        IndexSetting setting = INSTANCE.getSettingByFragmentClass(clazz);
        if (setting == null) {
            return $assertionsDisabled;
        }
        IndexAttributeValue value = setting.getAttributeValue(GalleryViewerFragment.SAFE_IN_TOP_LEVEL_KEY);
        if (value != null) {
            return ((Boolean) value.getObject()).booleanValue();
        }
        return $assertionsDisabled;
    }

    public String getAllRegexUrls() {
        ArrayList<String> strings = new ArrayList();
        for (FragmentIndex index : this.indexMap.values()) {
            if (GalleryImageFragment.class.isAssignableFrom(index.getFragmentClass())) {
                strings.add(index.getRegexUrl());
            }
        }
        try {
            return new ObjectMapper().writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllWebsiteUrls() {
        List<String> strings = new ArrayList();
        for (FragmentIndex index : this.indexMap.values()) {
            if (index.getBaseUrl() != null && index.getParent() == null && GalleryGridFragment.class.isAssignableFrom(index.getFragmentClass())) {
                strings.add(index.getBaseUrl());
                String[] strArr = new String[]{"girl", "solo", "long_hair", "highres", "short_hair", "blush", "touhou", "breasts", "multiple_girls", "smile", "blonde_hair", "blue_eyes", "brown_hair", "open_mouth", "hat", "thighhighs", "red_eyes", "skirt"};
            }
        }
        return strings;
    }

    public void setDefaults(Class... classes) {
        for (Class clazz : classes) {
            getSettingByFragmentClass(clazz).getAttributeValue(GalleryViewerFragment.INCLUDE_IN_HOME_KEY).setValue(Boolean.valueOf(true));
        }
    }

    public void setDefaultGalleries(Context context, String jsonData) {
        for (IndexSetting setting : getAllIndexSettings()) {
            setting.getAttributeValue(GalleryViewerFragment.INCLUDE_IN_HOME_KEY).setValue(Boolean.valueOf($assertionsDisabled));
        }
        try {
            JsonNode node = new ObjectMapper().readTree(jsonData);
            if (node.get("__deeplink") != null) {
                String home = node.get("__deeplink").asText();
                if (home.contains("furry")) {
                    setDefaults(FurAffinityFragment.class, e621Fragment.class, ImgurFragment.class);
                } else if (home.contains("anime")) {
                    setDefaults(DanbooruGalleryFragment.class, GelbooruFragment.class, KonachanFragment.class, YandereFragment.class);
                }
                saveSettings(context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
