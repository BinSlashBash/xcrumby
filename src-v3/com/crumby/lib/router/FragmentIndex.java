package com.crumby.lib.router;

import com.crumby.lib.FormSearchHandler;
import com.crumby.lib.authentication.AuthenticatorActivity;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class FragmentIndex extends FragmentLink {
    static final /* synthetic */ boolean $assertionsDisabled;
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

    static {
        $assertionsDisabled = !FragmentIndex.class.desiredAssertionStatus();
    }

    public FragmentIndex(String fragmentClassName) {
        try {
            this.fragmentClassName = fragmentClassName;
            this.fragmentClass = Class.forName(fragmentClassName);
            this.baseUrl = (String) getField("BASE_URL");
            this.regexUrl = (String) getField("REGEX_URL");
            if ($assertionsDisabled || this.regexUrl != null) {
                this.displayName = (String) getField("DISPLAY_NAME");
                this.breadcrumbName = (String) getField("BREADCRUMB_NAME");
                this.searchFormId = getIntegerField("SEARCH_FORM_ID");
                this.suggestable = getBooleanField("SUGGESTABLE");
                this.altName = getBooleanField("BREADCRUMB_ALT_NAME");
                this.breadcrumbIcon = getIntegerField("BREADCRUMB_ICON");
                this.accountType = (String) getField(AuthenticatorActivity.ARG_ACCOUNT_TYPE);
                this.accountLayout = getIntegerField("ACCOUNT_LAYOUT");
                this.settingAttributes = (SettingAttributes) getField("SETTING_ATTRIBUTES");
                this.defaultToHidden = getBooleanField("DEFAULT_TO_HIDDEN");
                Object formSearchHandler = getField("FORM_SEARCH_HANDLER");
                if (formSearchHandler != null) {
                    this.formSearchHandler = (FormSearchHandler) formSearchHandler;
                }
                Class parentClass = (Class) getField("BREADCRUMB_PARENT_CLASS");
                Class altFragmentClass = (Class) getField("ALIAS_CLASS");
                if (parentClass != null) {
                    this.parentClassName = parentClass.getName();
                }
                if (altFragmentClass != null) {
                    this.fragmentClassAlias = altFragmentClass.getName();
                }
                setFavicon();
                return;
            }
            throw new AssertionError();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean shouldBeHidden() {
        return this.defaultToHidden;
    }

    private boolean getBooleanField(String field) {
        if (getField(field) == null || !((Boolean) getField(field)).booleanValue()) {
            return false;
        }
        return true;
    }

    private int getIntegerField(String field) {
        if (getField(field) == null) {
            return -1;
        }
        return ((Integer) getField(field)).intValue();
    }

    private Object getField(String field) {
        Object obj = null;
        try {
            obj = this.fragmentClass.getDeclaredField(field).get(null);
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e2) {
        }
        return obj;
    }

    public boolean hasAltName() {
        return this.altName;
    }

    public FragmentIndex getParent() {
        return this.parent;
    }

    public String getBreadcrumbName() {
        return this.breadcrumbName;
    }

    public String getRegexUrl() {
        return this.regexUrl;
    }

    public String getParentClassName() {
        return this.parentClassName;
    }

    public String getFragmentClassName() {
        return this.fragmentClassName;
    }

    public void setParent(FragmentIndex parent) {
        this.parent = parent;
    }

    public Class getFragmentClass() {
        return this.fragmentClass;
    }

    public int getSearchFormId() {
        return this.searchFormId;
    }

    public int getBreadcrumbIcon() {
        return this.breadcrumbIcon;
    }

    public boolean hasBreadcrumbIcon() {
        return this.breadcrumbIcon != -1;
    }

    public boolean matches(FragmentIndex fragmentIndex) {
        return this.fragmentClassName.equals(fragmentIndex.getFragmentClassName()) || this.fragmentClassName.equals(fragmentIndex.getFragmentClassAlias());
    }

    public String getFragmentClassAlias() {
        return this.fragmentClassAlias;
    }

    public boolean isIndexOf(Class otherFragmentClass) {
        return otherFragmentClass.equals(this.fragmentClass);
    }

    public int getFirstParentBreadcrumbIcon() {
        for (FragmentIndex index = this; index != null; index = index.parent) {
            if (index.hasBreadcrumbIcon()) {
                return index.breadcrumbIcon;
            }
        }
        return 0;
    }

    public int getRootBreadcrumbIcon() {
        FragmentIndex index = this;
        while (index != null) {
            if (index.parent == null && index.hasBreadcrumbIcon()) {
                return index.breadcrumbIcon;
            }
            index = index.parent;
        }
        return 0;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public int getAccountLayout() {
        return this.accountLayout;
    }

    public void setAccountLayout(int accountLayout) {
        this.accountLayout = accountLayout;
    }

    public void applySetting(IndexSetting indexSetting) {
        this.indexSetting = indexSetting;
    }

    public IndexSetting getSetting() {
        return this.indexSetting;
    }

    public SettingAttributes getSettingAttributes() {
        return this.settingAttributes;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.crumby.lib.fragment.GalleryViewerFragment instantiate() {
        /*
        r3 = this;
        r1 = r3.getFragmentClass();
        r2 = r1.newInstance();	 Catch:{ InstantiationException -> 0x000b, IllegalAccessException -> 0x0011 }
        r2 = (com.crumby.lib.fragment.GalleryViewerFragment) r2;	 Catch:{ InstantiationException -> 0x000b, IllegalAccessException -> 0x0011 }
    L_0x000a:
        return r2;
    L_0x000b:
        r0 = move-exception;
        r0.printStackTrace();
    L_0x000f:
        r2 = 0;
        goto L_0x000a;
    L_0x0011:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x000f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crumby.lib.router.FragmentIndex.instantiate():com.crumby.lib.fragment.GalleryViewerFragment");
    }

    public GalleryProducer getProducer() {
        GalleryViewerFragment fragment = instantiate();
        GalleryProducer producer = fragment.getProducer();
        producer.removeConsumer(fragment.getConsumer());
        return producer;
    }

    public FormSearchHandler getFormSearchHandler() {
        return this.formSearchHandler;
    }
}
