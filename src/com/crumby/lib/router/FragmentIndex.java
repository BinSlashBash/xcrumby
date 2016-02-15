package com.crumby.lib.router;

import com.crumby.lib.FormSearchHandler;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.lang.reflect.Field;

public class FragmentIndex
  extends FragmentLink
{
  private int accountLayout;
  private String accountType;
  protected boolean altName;
  private int breadcrumbIcon;
  private String breadcrumbName;
  private boolean defaultToHidden;
  public FormSearchHandler formSearchHandler;
  private Class fragmentClass;
  private String fragmentClassAlias;
  private String fragmentClassName;
  private IndexSetting indexSetting;
  private FragmentIndex parent;
  private String parentClassName;
  private String regexUrl;
  private int searchFormId;
  private SettingAttributes settingAttributes;
  
  static
  {
    if (!FragmentIndex.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public FragmentIndex() {}
  
  public FragmentIndex(String paramString)
  {
    try
    {
      this.fragmentClassName = paramString;
      this.fragmentClass = Class.forName(paramString);
      this.baseUrl = ((String)getField("BASE_URL"));
      this.regexUrl = ((String)getField("REGEX_URL"));
      if ((!$assertionsDisabled) && (this.regexUrl == null)) {
        throw new AssertionError();
      }
    }
    catch (ClassNotFoundException paramString)
    {
      paramString.printStackTrace();
    }
    for (;;)
    {
      setFavicon();
      return;
      this.displayName = ((String)getField("DISPLAY_NAME"));
      this.breadcrumbName = ((String)getField("BREADCRUMB_NAME"));
      this.searchFormId = getIntegerField("SEARCH_FORM_ID");
      this.suggestable = getBooleanField("SUGGESTABLE");
      this.altName = getBooleanField("BREADCRUMB_ALT_NAME");
      this.breadcrumbIcon = getIntegerField("BREADCRUMB_ICON");
      this.accountType = ((String)getField("ACCOUNT_TYPE"));
      this.accountLayout = getIntegerField("ACCOUNT_LAYOUT");
      this.settingAttributes = ((SettingAttributes)getField("SETTING_ATTRIBUTES"));
      this.defaultToHidden = getBooleanField("DEFAULT_TO_HIDDEN");
      paramString = getField("FORM_SEARCH_HANDLER");
      if (paramString != null) {
        this.formSearchHandler = ((FormSearchHandler)paramString);
      }
      paramString = (Class)getField("BREADCRUMB_PARENT_CLASS");
      Class localClass = (Class)getField("ALIAS_CLASS");
      if (paramString != null) {
        this.parentClassName = paramString.getName();
      }
      if (localClass != null) {
        this.fragmentClassAlias = localClass.getName();
      }
    }
  }
  
  private boolean getBooleanField(String paramString)
  {
    return (getField(paramString) != null) && (((Boolean)getField(paramString)).booleanValue());
  }
  
  private Object getField(String paramString)
  {
    try
    {
      paramString = this.fragmentClass.getDeclaredField(paramString).get(null);
      return paramString;
    }
    catch (NoSuchFieldException paramString)
    {
      return null;
    }
    catch (IllegalAccessException paramString) {}
    return null;
  }
  
  private int getIntegerField(String paramString)
  {
    if (getField(paramString) == null) {
      return -1;
    }
    return ((Integer)getField(paramString)).intValue();
  }
  
  public void applySetting(IndexSetting paramIndexSetting)
  {
    this.indexSetting = paramIndexSetting;
  }
  
  public int getAccountLayout()
  {
    return this.accountLayout;
  }
  
  public String getAccountType()
  {
    return this.accountType;
  }
  
  public int getBreadcrumbIcon()
  {
    return this.breadcrumbIcon;
  }
  
  public String getBreadcrumbName()
  {
    return this.breadcrumbName;
  }
  
  public int getFirstParentBreadcrumbIcon()
  {
    for (FragmentIndex localFragmentIndex = this; localFragmentIndex != null; localFragmentIndex = localFragmentIndex.parent) {
      if (localFragmentIndex.hasBreadcrumbIcon()) {
        return localFragmentIndex.breadcrumbIcon;
      }
    }
    return 0;
  }
  
  public FormSearchHandler getFormSearchHandler()
  {
    return this.formSearchHandler;
  }
  
  public Class getFragmentClass()
  {
    return this.fragmentClass;
  }
  
  public String getFragmentClassAlias()
  {
    return this.fragmentClassAlias;
  }
  
  public String getFragmentClassName()
  {
    return this.fragmentClassName;
  }
  
  public FragmentIndex getParent()
  {
    return this.parent;
  }
  
  public String getParentClassName()
  {
    return this.parentClassName;
  }
  
  public GalleryProducer getProducer()
  {
    GalleryViewerFragment localGalleryViewerFragment = instantiate();
    GalleryProducer localGalleryProducer = localGalleryViewerFragment.getProducer();
    localGalleryProducer.removeConsumer(localGalleryViewerFragment.getConsumer());
    return localGalleryProducer;
  }
  
  public String getRegexUrl()
  {
    return this.regexUrl;
  }
  
  public int getRootBreadcrumbIcon()
  {
    for (FragmentIndex localFragmentIndex = this; localFragmentIndex != null; localFragmentIndex = localFragmentIndex.parent) {
      if ((localFragmentIndex.parent == null) && (localFragmentIndex.hasBreadcrumbIcon())) {
        return localFragmentIndex.breadcrumbIcon;
      }
    }
    return 0;
  }
  
  public int getSearchFormId()
  {
    return this.searchFormId;
  }
  
  public IndexSetting getSetting()
  {
    return this.indexSetting;
  }
  
  public SettingAttributes getSettingAttributes()
  {
    return this.settingAttributes;
  }
  
  public boolean hasAltName()
  {
    return this.altName;
  }
  
  public boolean hasBreadcrumbIcon()
  {
    return this.breadcrumbIcon != -1;
  }
  
  public GalleryViewerFragment instantiate()
  {
    Object localObject = getFragmentClass();
    try
    {
      localObject = (GalleryViewerFragment)((Class)localObject).newInstance();
      return (GalleryViewerFragment)localObject;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return null;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      for (;;)
      {
        localIllegalAccessException.printStackTrace();
      }
    }
  }
  
  public boolean isIndexOf(Class paramClass)
  {
    return paramClass.equals(this.fragmentClass);
  }
  
  public boolean matches(FragmentIndex paramFragmentIndex)
  {
    return (this.fragmentClassName.equals(paramFragmentIndex.getFragmentClassName())) || (this.fragmentClassName.equals(paramFragmentIndex.getFragmentClassAlias()));
  }
  
  public void setAccountLayout(int paramInt)
  {
    this.accountLayout = paramInt;
  }
  
  public void setParent(FragmentIndex paramFragmentIndex)
  {
    this.parent = paramFragmentIndex;
  }
  
  public boolean shouldBeHidden()
  {
    return this.defaultToHidden;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/FragmentIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */