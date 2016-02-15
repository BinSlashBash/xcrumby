/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.XmlResourceParser
 *  android.os.Looper
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 *  android.widget.Toast
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.crumby.lib.router;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
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
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.router.FragmentLinkAdapter;
import com.crumby.lib.router.FragmentLinkTrie;
import com.crumby.lib.router.IndexAttributeValue;
import com.crumby.lib.router.IndexSetting;
import com.crumby.lib.router.SettingAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public final class FragmentRouter
extends Enum<FragmentRouter> {
    private static final /* synthetic */ FragmentRouter[] $VALUES;
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final /* enum */ FragmentRouter INSTANCE;
    private static final String SETTINGS_FILENAME = "crumby_website_settings";
    private final int UNLIMITED = -1;
    private Map<String, FragmentIndex> indexMap;
    FragmentLinkAdapter linkAdapter;
    private boolean savingSettings;
    private FragmentLinkTrie trie;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !FragmentRouter.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
        INSTANCE = new FragmentRouter();
        $VALUES = new FragmentRouter[]{INSTANCE};
    }

    private FragmentRouter() {
        super(string2, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void applySettings(Context object) {
        Iterator<FragmentIndex> iterator = null;
        try {
            object = (JsonObject)new JsonParser().parse(GalleryProducer.readStreamIntoString(object.openFileInput("crumby_website_settings")));
        }
        catch (Exception var1_2) {
            var1_2.printStackTrace();
            object = iterator;
        }
        catch (FileNotFoundException var1_3) {
            object = iterator;
        }
        for (FragmentIndex fragmentIndex : this.indexMap.values()) {
            if (fragmentIndex.getSettingAttributes() == null) continue;
            IndexSetting indexSetting = new IndexSetting(fragmentIndex);
            fragmentIndex.applySetting(indexSetting);
            if (object == null) continue;
            try {
                indexSetting.restoreFromJson((JsonObject)object.get(fragmentIndex.getFragmentClassName()));
            }
            catch (Exception var3_6) {}
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private List<FragmentIndex> buildBreadCrumbPath(FragmentIndex fragmentIndex) {
        Object object = fragmentIndex.getParent();
        object = object != null ? this.buildBreadCrumbPath((FragmentIndex)object) : new ArrayList<FragmentIndex>();
        object.add((FragmentIndex)fragmentIndex);
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void buildIndexMap(Context var1_1) {
        this.indexMap = new LinkedHashMap<String, FragmentIndex>();
        try {
            var3_2 = var1_1.getResources().getXml(2131034118);
            var2_5 = var3_2.getEventType();
            ** GOTO lbl13
        }
        catch (XmlPullParserException var3_3) {
            block8 : {
                var3_3.printStackTrace();
                ** GOTO lbl23
                catch (IOException var3_4) {
                    var3_4.printStackTrace();
                    break block8;
                }
lbl-1000: // 4 sources:
                {
                    var2_5 = var3_2.next();
lbl13: // 2 sources:
                    if (var2_5 == 1) break;
                    switch (var2_5) {
                        case 0: 
                        case 1: {
                            continue block7;
                        }
                        case 2: {
                            if (!var3_2.getName().equals("site")) continue block7;
                            var4_6 = new FragmentIndex(var3_2.getAttributeValue(null, "class"));
                            this.indexMap.put(var4_6.getFragmentClassName(), var4_6);
                            continue block7;
                        }
                    }
                    ** while (true)
                }
            }
            this.buildTreeFromBottom();
            this.applySettings(var1_1);
            return;
        }
    }

    private void buildTreeFromBottom() {
        for (FragmentIndex fragmentIndex : this.indexMap.values()) {
            if (fragmentIndex.getParent() != null || fragmentIndex.getParentClassName() == null) continue;
            fragmentIndex.setParent(this.getFragmentIndexById(fragmentIndex.getParentClassName()));
        }
    }

    private FragmentIndex getFragmentIndexById(String string2) {
        return this.indexMap.get(string2);
    }

    public static FragmentRouter valueOf(String string2) {
        return (FragmentRouter)((Object)Enum.valueOf(FragmentRouter.class, string2));
    }

    public static FragmentRouter[] values() {
        return (FragmentRouter[])$VALUES.clone();
    }

    public List<FragmentIndex> buildBreadCrumbPath(String string2) {
        return this.buildBreadCrumbPath(this.getExactIndexes(string2, 1).get(0));
    }

    public JsonObject convertIndexesToJsonObj(List<IndexSetting> object) throws Exception {
        JsonObject jsonObject = new JsonObject();
        object = object.iterator();
        while (object.hasNext()) {
            IndexSetting indexSetting = (IndexSetting)object.next();
            JsonObject jsonObject2 = indexSetting.getAsJson();
            jsonObject.add(indexSetting.getFragmentClassName(), jsonObject2);
        }
        return jsonObject;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<FragmentLink> findMatchingLinks(String string2, boolean bl2, int n2) {
        synchronized (this) {
            long l2 = System.currentTimeMillis();
            if (!$assertionsDisabled && Looper.getMainLooper().getThread().equals(Thread.currentThread())) {
                throw new AssertionError();
            }
            Thread.currentThread().setPriority(10);
            List<FragmentLink> list = this.linkAdapter.search(string2, bl2, n2);
            if (list != null) {
                Analytics.INSTANCE.newTimingEvent(AnalyticsCategories.ROUTING, l2, "match links", string2);
            }
            return list;
        }
    }

    public List<IndexSetting> getAllIndexSettings() {
        ArrayList<IndexSetting> arrayList = new ArrayList<IndexSetting>();
        Object object = new ArrayList();
        for (FragmentIndex fragmentIndex : this.indexMap.values()) {
            if (fragmentIndex.getSetting() == null || fragmentIndex.shouldBeHidden()) continue;
            object.add(fragmentIndex);
        }
        Collections.sort(object, new Comparator<FragmentIndex>(){

            @Override
            public int compare(FragmentIndex fragmentIndex, FragmentIndex fragmentIndex2) {
                return fragmentIndex.getDisplayName().compareTo(fragmentIndex2.getDisplayName());
            }
        });
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(((FragmentIndex)object.next()).getSetting());
        }
        return arrayList;
    }

    public String getAllRegexUrls() {
        Object object = new ArrayList();
        for (FragmentIndex fragmentIndex : this.indexMap.values()) {
            if (!GalleryImageFragment.class.isAssignableFrom(fragmentIndex.getFragmentClass())) continue;
            object.add(fragmentIndex.getRegexUrl());
        }
        try {
            object = new ObjectMapper().writeValueAsString(object);
            return object;
        }
        catch (JsonProcessingException var1_2) {
            var1_2.printStackTrace();
            return null;
        }
    }

    public List<String> getAllWebsiteUrls() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (FragmentIndex fragmentIndex : this.indexMap.values()) {
            if (fragmentIndex.getBaseUrl() == null || fragmentIndex.getParent() != null || !GalleryGridFragment.class.isAssignableFrom(fragmentIndex.getFragmentClass())) continue;
            arrayList.add(fragmentIndex.getBaseUrl());
        }
        return arrayList;
    }

    public List<FragmentIndex> getExactIndexes(String string2, int n2) {
        ArrayList<FragmentIndex> arrayList = new ArrayList<FragmentIndex>();
        int n3 = 0;
        for (Map.Entry<String, FragmentIndex> entry : this.indexMap.entrySet()) {
            int n4;
            if (!string2.matches(entry.getValue().getRegexUrl())) continue;
            arrayList.add(entry.getValue());
            n3 = n4 = n3 + 1;
            if (n2 == -1) continue;
            n3 = n4;
            if (n4 < n2) continue;
        }
        if (arrayList.isEmpty()) {
            arrayList.add(this.indexMap.get(CrumbyGalleryFragment.class.getName()));
        }
        return arrayList;
    }

    public FragmentIndex getGalleryFragmentIndexByUrl(String string2) {
        return this.getExactIndexes(string2, 1).get(0);
    }

    public GalleryViewerFragment getGalleryFragmentInstance(String string2) {
        return this.getGalleryFragmentIndexByUrl(string2).instantiate();
    }

    public IndexSetting getSettingByFragmentClass(Class class_) {
        if (this.indexMap.get(class_.getName()) == null) {
            return null;
        }
        return this.indexMap.get(class_.getName()).getSetting();
    }

    public void initialize(Context context) {
        if (this.linkAdapter != null) {
            return;
        }
        long l2 = System.currentTimeMillis();
        this.linkAdapter = new FragmentLinkAdapter(context);
        this.buildIndexMap(context);
        Analytics.INSTANCE.newTimingEvent(AnalyticsCategories.ROUTING, l2, "initialize", "");
    }

    public void saveSettings(Context context) {
        this.saveSettings(this.getAllIndexSettings(), context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void saveSettings(List<IndexSetting> object, Context context) {
        FileOutputStream fileOutputStream;
        if (context == null || this.savingSettings) {
            return;
        }
        this.savingSettings = true;
        Object object2 = fileOutputStream = null;
        try {
            JsonObject jsonObject = this.convertIndexesToJsonObj((List<IndexSetting>)object);
            object2 = fileOutputStream;
            object2 = object = context.openFileOutput("crumby_website_settings", 0);
            object.write(jsonObject.toString().getBytes());
            object2 = object;
            object.close();
        }
        catch (Exception var1_2) {
            if (object2 != null) {
                try {
                    object2.close();
                }
                catch (Exception var3_6) {
                    var3_6.printStackTrace();
                }
            }
            object2 = Toast.makeText((Context)context, (CharSequence)"", (int)0);
            object2.setGravity(80, 0, 0);
            context = (TextView)View.inflate((Context)context, (int)2130903118, (ViewGroup)null);
            context.setText((CharSequence)("Could not save settings: " + var1_2.getMessage()));
            object2.setView((View)context);
            object2.show();
        }
        this.savingSettings = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setDefaultGalleries(Context context, String object) {
        block7 : {
            Iterator<IndexSetting> iterator = this.getAllIndexSettings().iterator();
            while (iterator.hasNext()) {
                iterator.next().getAttributeValue("include_in_home").setValue(false);
            }
            try {
                object = new ObjectMapper().readTree((String)object);
                if (object.get("__deeplink") == null) break block7;
                if ((object = object.get("__deeplink").asText()).contains("furry")) {
                    this.setDefaults(FurAffinityFragment.class, e621Fragment.class, ImgurFragment.class);
                } else if (object.contains("anime")) {
                    this.setDefaults(DanbooruGalleryFragment.class, GelbooruFragment.class, KonachanFragment.class, YandereFragment.class);
                }
            }
            catch (IOException var2_3) {
                var2_3.printStackTrace();
            }
            this.saveSettings(context);
        }
    }

    public /* varargs */ void setDefaults(Class ... arrclass) {
        int n2 = arrclass.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.getSettingByFragmentClass(arrclass[i2]).getAttributeValue("include_in_home").setValue(true);
        }
    }

    public void stopMatching() {
        if (this.trie != null) {
            this.trie.terminateSearch();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean wantsSafeImagesInTopLevelGallery(Class object) {
        if (object == null || (object = INSTANCE.getSettingByFragmentClass((Class)object)) == null || (object = object.getAttributeValue("safe_in_top_level")) == null) {
            return false;
        }
        return (Boolean)object.getObject();
    }

}

