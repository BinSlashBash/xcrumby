/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.os.Parcelable
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v13.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentPagerAdapter
extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentPagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;

    public FragmentPagerAdapter(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    private static String makeFragmentName(int n2, long l2) {
        return "android:switcher:" + n2 + ":" + l2;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int n2, Object object) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        this.mCurTransaction.detach((Fragment)object);
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

    public long getItemId(int n2) {
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Object instantiateItem(ViewGroup object, int n2) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        long l2 = this.getItemId(n2);
        String string2 = FragmentPagerAdapter.makeFragmentName(object.getId(), l2);
        if ((string2 = this.mFragmentManager.findFragmentByTag(string2)) != null) {
            this.mCurTransaction.attach((Fragment)string2);
            object = string2;
        } else {
            string2 = this.getItem(n2);
            this.mCurTransaction.add(object.getId(), (Fragment)string2, FragmentPagerAdapter.makeFragmentName(object.getId(), l2));
            object = string2;
        }
        if (object != this.mCurrentPrimaryItem) {
            FragmentCompat.setMenuVisibility((Fragment)object, false);
            FragmentCompat.setUserVisibleHint((Fragment)object, false);
        }
        return object;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (((Fragment)object).getView() == view) {
            return true;
        }
        return false;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
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

