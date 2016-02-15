/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.router;

import com.crumby.lib.FormSearchHandler;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentLink;
import com.crumby.lib.router.IndexSetting;
import com.crumby.lib.router.SettingAttributes;
import java.lang.reflect.Field;

public class FragmentIndex
extends FragmentLink {
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

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !FragmentIndex.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
    }

    public FragmentIndex() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public FragmentIndex(String object) {
        try {
            this.fragmentClassName = object;
            this.fragmentClass = Class.forName((String)object);
            this.baseUrl = (String)this.getField("BASE_URL");
            this.regexUrl = (String)this.getField("REGEX_URL");
            if (!$assertionsDisabled && this.regexUrl == null) {
                throw new AssertionError();
            }
            this.displayName = (String)this.getField("DISPLAY_NAME");
            this.breadcrumbName = (String)this.getField("BREADCRUMB_NAME");
            this.searchFormId = this.getIntegerField("SEARCH_FORM_ID");
            this.suggestable = this.getBooleanField("SUGGESTABLE");
            this.altName = this.getBooleanField("BREADCRUMB_ALT_NAME");
            this.breadcrumbIcon = this.getIntegerField("BREADCRUMB_ICON");
            this.accountType = (String)this.getField("ACCOUNT_TYPE");
            this.accountLayout = this.getIntegerField("ACCOUNT_LAYOUT");
            this.settingAttributes = (SettingAttributes)this.getField("SETTING_ATTRIBUTES");
            this.defaultToHidden = this.getBooleanField("DEFAULT_TO_HIDDEN");
            object = this.getField("FORM_SEARCH_HANDLER");
            if (object != null) {
                this.formSearchHandler = (FormSearchHandler)object;
            }
            object = (Class)this.getField("BREADCRUMB_PARENT_CLASS");
            Class class_ = (Class)this.getField("ALIAS_CLASS");
            if (object != null) {
                this.parentClassName = object.getName();
            }
            if (class_ != null) {
                this.fragmentClassAlias = class_.getName();
            }
        }
        catch (ClassNotFoundException var1_2) {
            var1_2.printStackTrace();
        }
        this.setFavicon();
    }

    private boolean getBooleanField(String string2) {
        if (this.getField(string2) == null || !((Boolean)this.getField(string2)).booleanValue()) {
            return false;
        }
        return true;
    }

    private Object getField(String object) {
        try {
            object = this.fragmentClass.getDeclaredField((String)object).get(null);
            return object;
        }
        catch (NoSuchFieldException var1_2) {
            return null;
        }
        catch (IllegalAccessException var1_3) {
            return null;
        }
    }

    private int getIntegerField(String string2) {
        if (this.getField(string2) == null) {
            return -1;
        }
        return (Integer)this.getField(string2);
    }

    public void applySetting(IndexSetting indexSetting) {
        this.indexSetting = indexSetting;
    }

    public int getAccountLayout() {
        return this.accountLayout;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public int getBreadcrumbIcon() {
        return this.breadcrumbIcon;
    }

    public String getBreadcrumbName() {
        return this.breadcrumbName;
    }

    public int getFirstParentBreadcrumbIcon() {
        FragmentIndex fragmentIndex = this;
        while (fragmentIndex != null) {
            if (fragmentIndex.hasBreadcrumbIcon()) {
                return fragmentIndex.breadcrumbIcon;
            }
            fragmentIndex = fragmentIndex.parent;
        }
        return 0;
    }

    public FormSearchHandler getFormSearchHandler() {
        return this.formSearchHandler;
    }

    public Class getFragmentClass() {
        return this.fragmentClass;
    }

    public String getFragmentClassAlias() {
        return this.fragmentClassAlias;
    }

    public String getFragmentClassName() {
        return this.fragmentClassName;
    }

    public FragmentIndex getParent() {
        return this.parent;
    }

    public String getParentClassName() {
        return this.parentClassName;
    }

    public GalleryProducer getProducer() {
        GalleryViewerFragment galleryViewerFragment = this.instantiate();
        GalleryProducer galleryProducer = galleryViewerFragment.getProducer();
        galleryProducer.removeConsumer(galleryViewerFragment.getConsumer());
        return galleryProducer;
    }

    @Override
    public String getRegexUrl() {
        return this.regexUrl;
    }

    public int getRootBreadcrumbIcon() {
        FragmentIndex fragmentIndex = this;
        while (fragmentIndex != null) {
            if (fragmentIndex.parent == null && fragmentIndex.hasBreadcrumbIcon()) {
                return fragmentIndex.breadcrumbIcon;
            }
            fragmentIndex = fragmentIndex.parent;
        }
        return 0;
    }

    public int getSearchFormId() {
        return this.searchFormId;
    }

    public IndexSetting getSetting() {
        return this.indexSetting;
    }

    public SettingAttributes getSettingAttributes() {
        return this.settingAttributes;
    }

    public boolean hasAltName() {
        return this.altName;
    }

    public boolean hasBreadcrumbIcon() {
        if (this.breadcrumbIcon != -1) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public GalleryViewerFragment instantiate() {
        Class class_ = this.getFragmentClass();
        try {
            return (GalleryViewerFragment)class_.newInstance();
        }
        catch (InstantiationException var1_2) {
            var1_2.printStackTrace();
            do {
                return null;
                break;
            } while (true);
        }
        catch (IllegalAccessException var1_3) {
            var1_3.printStackTrace();
            return null;
        }
    }

    public boolean isIndexOf(Class class_) {
        return class_.equals(this.fragmentClass);
    }

    public boolean matches(FragmentIndex fragmentIndex) {
        if (this.fragmentClassName.equals(fragmentIndex.getFragmentClassName()) || this.fragmentClassName.equals(fragmentIndex.getFragmentClassAlias())) {
            return true;
        }
        return false;
    }

    public void setAccountLayout(int n2) {
        this.accountLayout = n2;
    }

    public void setParent(FragmentIndex fragmentIndex) {
        this.parent = fragmentIndex;
    }

    public boolean shouldBeHidden() {
        return this.defaultToHidden;
    }
}

