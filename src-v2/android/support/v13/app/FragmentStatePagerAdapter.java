/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.Fragment$SavedState
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v13.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Set;

public abstract class FragmentStatePagerAdapter
extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentStatePagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList();
    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList();

    public FragmentStatePagerAdapter(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int n2, Object object) {
        viewGroup = (Fragment)object;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= n2) {
            this.mSavedState.add(null);
        }
        this.mSavedState.set(n2, this.mFragmentManager.saveFragmentInstanceState((Fragment)viewGroup));
        this.mFragments.set(n2, null);
        this.mCurTransaction.remove((Fragment)viewGroup);
    }

    @Override
    public void finishUpdate(ViewGroup viewGroup) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
        }
    }

    public abstract Fragment getItem(int var1);

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int n2) {
        Fragment.SavedState savedState;
        Fragment fragment;
        if (this.mFragments.size() > n2 && (fragment = this.mFragments.get(n2)) != null) {
            return fragment;
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        fragment = this.getItem(n2);
        if (this.mSavedState.size() > n2 && (savedState = this.mSavedState.get(n2)) != null) {
            fragment.setInitialSavedState(savedState);
        }
        while (this.mFragments.size() <= n2) {
            this.mFragments.add(null);
        }
        FragmentCompat.setMenuVisibility(fragment, false);
        FragmentCompat.setUserVisibleHint(fragment, false);
        this.mFragments.set(n2, fragment);
        this.mCurTransaction.add(viewGroup.getId(), fragment);
        return fragment;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (((Fragment)object).getView() == view) {
            return true;
        }
        return false;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader object) {
        if (parcelable != null) {
            int n2;
            parcelable = (Bundle)parcelable;
            parcelable.setClassLoader((ClassLoader)object);
            object = parcelable.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if (object != null) {
                for (n2 = 0; n2 < object.length; ++n2) {
                    this.mSavedState.add((Fragment.SavedState)object[n2]);
                }
            }
            for (String string2 : parcelable.keySet()) {
                if (!string2.startsWith("f")) continue;
                n2 = Integer.parseInt(string2.substring(1));
                Fragment fragment = this.mFragmentManager.getFragment((Bundle)parcelable, string2);
                if (fragment != null) {
                    while (this.mFragments.size() <= n2) {
                        this.mFragments.add(null);
                    }
                    FragmentCompat.setMenuVisibility(fragment, false);
                    this.mFragments.set(n2, fragment);
                    continue;
                }
                Log.w((String)"FragmentStatePagerAdapter", (String)("Bad fragment at key " + string2));
            }
        }
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle;
        Object object = null;
        if (this.mSavedState.size() > 0) {
            object = new Bundle();
            bundle = new Fragment.SavedState[this.mSavedState.size()];
            this.mSavedState.toArray((T[])bundle);
            object.putParcelableArray("states", (Parcelable[])bundle);
        }
        for (int i2 = 0; i2 < this.mFragments.size(); ++i2) {
            Fragment fragment = this.mFragments.get(i2);
            bundle = object;
            if (fragment != null) {
                bundle = object;
                if (object == null) {
                    bundle = new Bundle();
                }
                object = "f" + i2;
                this.mFragmentManager.putFragment(bundle, (String)object, fragment);
            }
            object = bundle;
        }
        return object;
    }

    @Override
    public void setPrimaryItem(ViewGroup viewGroup, int n2, Object object) {
        viewGroup = (Fragment)object;
        if (viewGroup != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                FragmentCompat.setMenuVisibility(this.mCurrentPrimaryItem, false);
                FragmentCompat.setUserVisibleHint(this.mCurrentPrimaryItem, false);
            }
            if (viewGroup != null) {
                FragmentCompat.setMenuVisibility((Fragment)viewGroup, true);
                FragmentCompat.setUserVisibleHint((Fragment)viewGroup, true);
            }
            this.mCurrentPrimaryItem = viewGroup;
        }
    }

    @Override
    public void startUpdate(ViewGroup viewGroup) {
    }
}

