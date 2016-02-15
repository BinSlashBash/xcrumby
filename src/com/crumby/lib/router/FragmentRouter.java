package com.crumby.lib.router;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Looper;
import android.view.View;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import java.util.Map.Entry;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public enum FragmentRouter
{
  private static final String SETTINGS_FILENAME = "crumby_website_settings";
  private final int UNLIMITED = -1;
  private Map<String, FragmentIndex> indexMap;
  FragmentLinkAdapter linkAdapter;
  private boolean savingSettings;
  private FragmentLinkTrie trie;
  
  static
  {
    if (!FragmentRouter.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      INSTANCE = new FragmentRouter("INSTANCE", 0);
      $VALUES = new FragmentRouter[] { INSTANCE };
      return;
    }
  }
  
  private FragmentRouter() {}
  
  private void applySettings(Context paramContext)
  {
    Iterator localIterator = null;
    try
    {
      paramContext = (JsonObject)new JsonParser().parse(GalleryProducer.readStreamIntoString(paramContext.openFileInput("crumby_website_settings")));
      localIterator = this.indexMap.values().iterator();
      while (localIterator.hasNext())
      {
        FragmentIndex localFragmentIndex = (FragmentIndex)localIterator.next();
        if (localFragmentIndex.getSettingAttributes() != null)
        {
          IndexSetting localIndexSetting = new IndexSetting(localFragmentIndex);
          localFragmentIndex.applySetting(localIndexSetting);
          if (paramContext != null) {
            try
            {
              localIndexSetting.restoreFromJson((JsonObject)paramContext.get(localFragmentIndex.getFragmentClassName()));
            }
            catch (Exception localException) {}
          }
        }
      }
    }
    catch (Exception paramContext)
    {
      for (;;)
      {
        paramContext.printStackTrace();
        paramContext = localIterator;
      }
      return;
    }
    catch (FileNotFoundException paramContext)
    {
      for (;;)
      {
        paramContext = localIterator;
      }
    }
  }
  
  private List<FragmentIndex> buildBreadCrumbPath(FragmentIndex paramFragmentIndex)
  {
    Object localObject = paramFragmentIndex.getParent();
    if (localObject != null) {}
    for (localObject = buildBreadCrumbPath((FragmentIndex)localObject);; localObject = new ArrayList())
    {
      ((List)localObject).add(paramFragmentIndex);
      return (List<FragmentIndex>)localObject;
    }
  }
  
  private void buildIndexMap(Context paramContext)
  {
    this.indexMap = new LinkedHashMap();
    for (;;)
    {
      try
      {
        localXmlResourceParser = paramContext.getResources().getXml(2131034118);
        i = localXmlResourceParser.getEventType();
      }
      catch (XmlPullParserException localXmlPullParserException)
      {
        XmlResourceParser localXmlResourceParser;
        FragmentIndex localFragmentIndex;
        localXmlPullParserException.printStackTrace();
        buildTreeFromBottom();
        applySettings(paramContext);
        return;
      }
      catch (IOException localIOException)
      {
        int i;
        localIOException.printStackTrace();
        continue;
        if (i == 1) {
          continue;
        }
        switch (i)
        {
        }
        continue;
      }
      i = localXmlResourceParser.next();
      continue;
      if (localXmlResourceParser.getName().equals("site"))
      {
        localFragmentIndex = new FragmentIndex(localXmlResourceParser.getAttributeValue(null, "class"));
        this.indexMap.put(localFragmentIndex.getFragmentClassName(), localFragmentIndex);
      }
    }
  }
  
  private void buildTreeFromBottom()
  {
    Iterator localIterator = this.indexMap.values().iterator();
    while (localIterator.hasNext())
    {
      FragmentIndex localFragmentIndex = (FragmentIndex)localIterator.next();
      if ((localFragmentIndex.getParent() == null) && (localFragmentIndex.getParentClassName() != null)) {
        localFragmentIndex.setParent(getFragmentIndexById(localFragmentIndex.getParentClassName()));
      }
    }
  }
  
  private FragmentIndex getFragmentIndexById(String paramString)
  {
    return (FragmentIndex)this.indexMap.get(paramString);
  }
  
  public List<FragmentIndex> buildBreadCrumbPath(String paramString)
  {
    return buildBreadCrumbPath((FragmentIndex)getExactIndexes(paramString, 1).get(0));
  }
  
  public JsonObject convertIndexesToJsonObj(List<IndexSetting> paramList)
    throws Exception
  {
    JsonObject localJsonObject1 = new JsonObject();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      IndexSetting localIndexSetting = (IndexSetting)paramList.next();
      JsonObject localJsonObject2 = localIndexSetting.getAsJson();
      localJsonObject1.add(localIndexSetting.getFragmentClassName(), localJsonObject2);
    }
    return localJsonObject1;
  }
  
  public List<FragmentLink> findMatchingLinks(String paramString, boolean paramBoolean, int paramInt)
  {
    long l;
    try
    {
      l = System.currentTimeMillis();
      if ((!$assertionsDisabled) && (Looper.getMainLooper().getThread().equals(Thread.currentThread()))) {
        throw new AssertionError();
      }
    }
    finally {}
    Thread.currentThread().setPriority(10);
    List localList = this.linkAdapter.search(paramString, paramBoolean, paramInt);
    if (localList != null) {
      Analytics.INSTANCE.newTimingEvent(AnalyticsCategories.ROUTING, l, "match links", paramString);
    }
    return localList;
  }
  
  public List<IndexSetting> getAllIndexSettings()
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = new ArrayList();
    Iterator localIterator = this.indexMap.values().iterator();
    while (localIterator.hasNext())
    {
      FragmentIndex localFragmentIndex = (FragmentIndex)localIterator.next();
      if ((localFragmentIndex.getSetting() != null) && (!localFragmentIndex.shouldBeHidden())) {
        ((List)localObject).add(localFragmentIndex);
      }
    }
    Collections.sort((List)localObject, new Comparator()
    {
      public int compare(FragmentIndex paramAnonymousFragmentIndex1, FragmentIndex paramAnonymousFragmentIndex2)
      {
        return paramAnonymousFragmentIndex1.getDisplayName().compareTo(paramAnonymousFragmentIndex2.getDisplayName());
      }
    });
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      localArrayList.add(((FragmentIndex)((Iterator)localObject).next()).getSetting());
    }
    return localArrayList;
  }
  
  public String getAllRegexUrls()
  {
    Object localObject = new ArrayList();
    Iterator localIterator = this.indexMap.values().iterator();
    while (localIterator.hasNext())
    {
      FragmentIndex localFragmentIndex = (FragmentIndex)localIterator.next();
      if (GalleryImageFragment.class.isAssignableFrom(localFragmentIndex.getFragmentClass())) {
        ((ArrayList)localObject).add(localFragmentIndex.getRegexUrl());
      }
    }
    try
    {
      localObject = new ObjectMapper().writeValueAsString(localObject);
      return (String)localObject;
    }
    catch (JsonProcessingException localJsonProcessingException)
    {
      localJsonProcessingException.printStackTrace();
    }
    return null;
  }
  
  public List<String> getAllWebsiteUrls()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.indexMap.values().iterator();
    while (localIterator.hasNext())
    {
      FragmentIndex localFragmentIndex = (FragmentIndex)localIterator.next();
      if ((localFragmentIndex.getBaseUrl() != null) && (localFragmentIndex.getParent() == null) && (GalleryGridFragment.class.isAssignableFrom(localFragmentIndex.getFragmentClass()))) {
        localArrayList.add(localFragmentIndex.getBaseUrl());
      }
    }
    return localArrayList;
  }
  
  public List<FragmentIndex> getExactIndexes(String paramString, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    Iterator localIterator = this.indexMap.entrySet().iterator();
    int j;
    do
    {
      do
      {
        Map.Entry localEntry;
        do
        {
          if (!localIterator.hasNext()) {
            break;
          }
          localEntry = (Map.Entry)localIterator.next();
        } while (!paramString.matches(((FragmentIndex)localEntry.getValue()).getRegexUrl()));
        localArrayList.add(localEntry.getValue());
        j = i + 1;
        i = j;
      } while (paramInt == -1);
      i = j;
    } while (j < paramInt);
    if (localArrayList.isEmpty()) {
      localArrayList.add(this.indexMap.get(CrumbyGalleryFragment.class.getName()));
    }
    return localArrayList;
  }
  
  public FragmentIndex getGalleryFragmentIndexByUrl(String paramString)
  {
    return (FragmentIndex)getExactIndexes(paramString, 1).get(0);
  }
  
  public GalleryViewerFragment getGalleryFragmentInstance(String paramString)
  {
    return getGalleryFragmentIndexByUrl(paramString).instantiate();
  }
  
  public IndexSetting getSettingByFragmentClass(Class paramClass)
  {
    if (this.indexMap.get(paramClass.getName()) == null) {
      return null;
    }
    return ((FragmentIndex)this.indexMap.get(paramClass.getName())).getSetting();
  }
  
  public void initialize(Context paramContext)
  {
    if (this.linkAdapter != null) {
      return;
    }
    long l = System.currentTimeMillis();
    this.linkAdapter = new FragmentLinkAdapter(paramContext);
    buildIndexMap(paramContext);
    Analytics.INSTANCE.newTimingEvent(AnalyticsCategories.ROUTING, l, "initialize", "");
  }
  
  public void saveSettings(Context paramContext)
  {
    saveSettings(getAllIndexSettings(), paramContext);
  }
  
  public void saveSettings(List<IndexSetting> paramList, Context paramContext)
  {
    if ((paramContext == null) || (this.savingSettings)) {
      return;
    }
    this.savingSettings = true;
    Object localObject2 = null;
    Object localObject1 = localObject2;
    for (;;)
    {
      try
      {
        JsonObject localJsonObject = convertIndexesToJsonObj(paramList);
        localObject1 = localObject2;
        paramList = paramContext.openFileOutput("crumby_website_settings", 0);
        localObject1 = paramList;
        paramList.write(localJsonObject.toString().getBytes());
        localObject1 = paramList;
        paramList.close();
        this.savingSettings = false;
        return;
      }
      catch (Exception paramList)
      {
        if (localObject1 == null) {}
      }
      try
      {
        ((FileOutputStream)localObject1).close();
        localObject1 = Toast.makeText(paramContext, "", 0);
        ((Toast)localObject1).setGravity(80, 0, 0);
        paramContext = (TextView)View.inflate(paramContext, 2130903118, null);
        paramContext.setText("Could not save settings: " + paramList.getMessage());
        ((Toast)localObject1).setView(paramContext);
        ((Toast)localObject1).show();
      }
      catch (Exception localException)
      {
        for (;;)
        {
          localException.printStackTrace();
        }
      }
    }
  }
  
  public void setDefaultGalleries(Context paramContext, String paramString)
  {
    Iterator localIterator = getAllIndexSettings().iterator();
    while (localIterator.hasNext()) {
      ((IndexSetting)localIterator.next()).getAttributeValue("include_in_home").setValue(Boolean.valueOf(false));
    }
    try
    {
      paramString = new ObjectMapper().readTree(paramString);
      if (paramString.get("__deeplink") != null)
      {
        paramString = paramString.get("__deeplink").asText();
        if (!paramString.contains("furry")) {
          break label119;
        }
        setDefaults(new Class[] { FurAffinityFragment.class, e621Fragment.class, ImgurFragment.class });
      }
      for (;;)
      {
        saveSettings(paramContext);
        return;
        label119:
        if (paramString.contains("anime")) {
          setDefaults(new Class[] { DanbooruGalleryFragment.class, GelbooruFragment.class, KonachanFragment.class, YandereFragment.class });
        }
      }
    }
    catch (IOException paramString)
    {
      for (;;)
      {
        paramString.printStackTrace();
      }
    }
  }
  
  public void setDefaults(Class... paramVarArgs)
  {
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      getSettingByFragmentClass(paramVarArgs[i]).getAttributeValue("include_in_home").setValue(Boolean.valueOf(true));
      i += 1;
    }
  }
  
  public void stopMatching()
  {
    if (this.trie != null) {
      this.trie.terminateSearch();
    }
  }
  
  public boolean wantsSafeImagesInTopLevelGallery(Class paramClass)
  {
    if (paramClass == null) {}
    do
    {
      do
      {
        return false;
        paramClass = INSTANCE.getSettingByFragmentClass(paramClass);
      } while (paramClass == null);
      paramClass = paramClass.getAttributeValue("safe_in_top_level");
    } while (paramClass == null);
    return ((Boolean)paramClass.getObject()).booleanValue();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/FragmentRouter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */