/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TabHost
 *  android.widget.TabHost$OnTabChangeListener
 *  android.widget.TabHost$TabContentFactory
 *  android.widget.TabHost$TabSpec
 *  android.widget.TabWidget
 */
package android.support.v13.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import java.util.ArrayList;

public class FragmentTabHost
extends TabHost
implements TabHost.OnTabChangeListener {
    private boolean mAttached;
    private int mContainerId;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private TabInfo mLastTab;
    private TabHost.OnTabChangeListener mOnTabChangeListener;
    private FrameLayout mRealTabContent;
    private final ArrayList<TabInfo> mTabs = new ArrayList();

    public FragmentTabHost(Context context) {
        super(context, null);
        this.initFragmentTabHost(context, null);
    }

    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initFragmentTabHost(context, attributeSet);
    }

    /*
     * Enabled aggressive block sorting
     */
    private FragmentTransaction doTabChanged(String string2, FragmentTransaction fragmentTransaction) {
        TabInfo tabInfo = null;
        for (int i2 = 0; i2 < this.mTabs.size(); ++i2) {
            TabInfo tabInfo2 = this.mTabs.get(i2);
            if (!tabInfo2.tag.equals(string2)) continue;
            tabInfo = tabInfo2;
        }
        if (tabInfo == null) {
            throw new IllegalStateException("No tab known for tag " + string2);
        }
        string2 = fragmentTransaction;
        if (this.mLastTab != tabInfo) {
            string2 = fragmentTransaction;
            if (fragmentTransaction == null) {
                string2 = this.mFragmentManager.beginTransaction();
            }
            if (this.mLastTab != null && this.mLastTab.fragment != null) {
                string2.detach(this.mLastTab.fragment);
            }
            if (tabInfo != null) {
                if (tabInfo.fragment == null) {
                    tabInfo.fragment = Fragment.instantiate((Context)this.mContext, (String)tabInfo.clss.getName(), (Bundle)tabInfo.args);
                    string2.add(this.mContainerId, tabInfo.fragment, tabInfo.tag);
                } else {
                    string2.attach(tabInfo.fragment);
                }
            }
            this.mLastTab = tabInfo;
        }
        return string2;
    }

    private void ensureContent() {
        if (this.mRealTabContent == null) {
            this.mRealTabContent = (FrameLayout)this.findViewById(this.mContainerId);
            if (this.mRealTabContent == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
            }
        }
    }

    private void ensureHierarchy(Context context) {
        if (this.findViewById(16908307) == null) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            this.addView((View)linearLayout, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
            TabWidget tabWidget = new TabWidget(context);
            tabWidget.setId(16908307);
            tabWidget.setOrientation(0);
            linearLayout.addView((View)tabWidget, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2, 0.0f));
            tabWidget = new FrameLayout(context);
            tabWidget.setId(16908305);
            linearLayout.addView((View)tabWidget, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, 0, 0.0f));
            this.mRealTabContent = context = new FrameLayout(context);
            this.mRealTabContent.setId(this.mContainerId);
            linearLayout.addView((View)context, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    private void initFragmentTabHost(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, new int[]{16842995}, 0, 0);
        this.mContainerId = context.getResourceId(0, 0);
        context.recycle();
        super.setOnTabChangedListener((TabHost.OnTabChangeListener)this);
    }

    public void addTab(TabHost.TabSpec tabSpec, Class<?> object, Bundle bundle) {
        tabSpec.setContent((TabHost.TabContentFactory)new DummyTabFactory(this.mContext));
        String string2 = tabSpec.getTag();
        object = new TabInfo(string2, object, bundle);
        if (this.mAttached) {
            ((TabInfo)object).fragment = this.mFragmentManager.findFragmentByTag(string2);
            if (((TabInfo)object).fragment != null && !((TabInfo)object).fragment.isDetached()) {
                bundle = this.mFragmentManager.beginTransaction();
                bundle.detach(((TabInfo)object).fragment);
                bundle.commit();
            }
        }
        this.mTabs.add((TabInfo)object);
        this.addTab(tabSpec);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        String string2 = this.getCurrentTabTag();
        FragmentTransaction fragmentTransaction = null;
        for (int i2 = 0; i2 < this.mTabs.size(); ++i2) {
            TabInfo tabInfo = this.mTabs.get(i2);
            tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tabInfo.tag);
            FragmentTransaction fragmentTransaction2 = fragmentTransaction;
            if (tabInfo.fragment != null) {
                fragmentTransaction2 = fragmentTransaction;
                if (!tabInfo.fragment.isDetached()) {
                    if (tabInfo.tag.equals(string2)) {
                        this.mLastTab = tabInfo;
                        fragmentTransaction2 = fragmentTransaction;
                    } else {
                        fragmentTransaction2 = fragmentTransaction;
                        if (fragmentTransaction == null) {
                            fragmentTransaction2 = this.mFragmentManager.beginTransaction();
                        }
                        fragmentTransaction2.detach(tabInfo.fragment);
                    }
                }
            }
            fragmentTransaction = fragmentTransaction2;
        }
        this.mAttached = true;
        if ((fragmentTransaction = this.doTabChanged(string2, fragmentTransaction)) != null) {
            fragmentTransaction.commit();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    protected void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setCurrentTabByTag(object.curTab);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.curTab = this.getCurrentTabTag();
        return savedState;
    }

    public void onTabChanged(String string2) {
        FragmentTransaction fragmentTransaction;
        if (this.mAttached && (fragmentTransaction = this.doTabChanged(string2, null)) != null) {
            fragmentTransaction.commit();
        }
        if (this.mOnTabChangeListener != null) {
            this.mOnTabChangeListener.onTabChanged(string2);
        }
    }

    public void setOnTabChangedListener(TabHost.OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(Context context, FragmentManager fragmentManager) {
        this.ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.ensureContent();
    }

    public void setup(Context context, FragmentManager fragmentManager, int n2) {
        this.ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.mContainerId = n2;
        this.ensureContent();
        this.mRealTabContent.setId(n2);
        if (this.getId() == -1) {
            this.setId(16908306);
        }
    }

    static class DummyTabFactory
    implements TabHost.TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            this.mContext = context;
        }

        public View createTabContent(String string2) {
            string2 = new View(this.mContext);
            string2.setMinimumWidth(0);
            string2.setMinimumHeight(0);
            return string2;
        }
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        String curTab;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.curTab = parcel.readString();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode((Object)this)) + " curTab=" + this.curTab + "}";
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeString(this.curTab);
        }

    }

    static final class TabInfo {
        private final Bundle args;
        private final Class<?> clss;
        private Fragment fragment;
        private final String tag;

        TabInfo(String string2, Class<?> class_, Bundle bundle) {
            this.tag = string2;
            this.clss = class_;
            this.args = bundle;
        }
    }

}

