/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.BackStackRecord;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManagerImpl;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;

final class BackStackState
implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator<BackStackState>(){

        public BackStackState createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }

        public BackStackState[] newArray(int n2) {
            return new BackStackState[n2];
        }
    };
    final int mBreadCrumbShortTitleRes;
    final CharSequence mBreadCrumbShortTitleText;
    final int mBreadCrumbTitleRes;
    final CharSequence mBreadCrumbTitleText;
    final int mIndex;
    final String mName;
    final int[] mOps;
    final int mTransition;
    final int mTransitionStyle;

    public BackStackState(Parcel parcel) {
        this.mOps = parcel.createIntArray();
        this.mTransition = parcel.readInt();
        this.mTransitionStyle = parcel.readInt();
        this.mName = parcel.readString();
        this.mIndex = parcel.readInt();
        this.mBreadCrumbTitleRes = parcel.readInt();
        this.mBreadCrumbTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mBreadCrumbShortTitleRes = parcel.readInt();
        this.mBreadCrumbShortTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    /*
     * Enabled aggressive block sorting
     */
    public BackStackState(FragmentManagerImpl object, BackStackRecord backStackRecord) {
        int n2;
        int n3 = 0;
        object = backStackRecord.mHead;
        while (object != null) {
            n2 = n3;
            if (object.removed != null) {
                n2 = n3 + object.removed.size();
            }
            object = object.next;
            n3 = n2;
        }
        this.mOps = new int[backStackRecord.mNumOp * 7 + n3];
        if (!backStackRecord.mAddToBackStack) {
            throw new IllegalStateException("Not on back stack");
        }
        object = backStackRecord.mHead;
        n3 = 0;
        do {
            if (object == null) {
                this.mTransition = backStackRecord.mTransition;
                this.mTransitionStyle = backStackRecord.mTransitionStyle;
                this.mName = backStackRecord.mName;
                this.mIndex = backStackRecord.mIndex;
                this.mBreadCrumbTitleRes = backStackRecord.mBreadCrumbTitleRes;
                this.mBreadCrumbTitleText = backStackRecord.mBreadCrumbTitleText;
                this.mBreadCrumbShortTitleRes = backStackRecord.mBreadCrumbShortTitleRes;
                this.mBreadCrumbShortTitleText = backStackRecord.mBreadCrumbShortTitleText;
                return;
            }
            int[] arrn = this.mOps;
            n2 = n3 + 1;
            arrn[n3] = object.cmd;
            arrn = this.mOps;
            int n4 = n2 + 1;
            n3 = object.fragment != null ? object.fragment.mIndex : -1;
            arrn[n2] = n3;
            arrn = this.mOps;
            n3 = n4 + 1;
            arrn[n4] = object.enterAnim;
            arrn = this.mOps;
            n2 = n3 + 1;
            arrn[n3] = object.exitAnim;
            arrn = this.mOps;
            n3 = n2 + 1;
            arrn[n2] = object.popEnterAnim;
            arrn = this.mOps;
            int n5 = n3 + 1;
            arrn[n3] = object.popExitAnim;
            if (object.removed != null) {
                this.mOps[n5] = n4 = object.removed.size();
                n3 = n5 + 1;
                for (n2 = 0; n2 < n4; ++n2, ++n3) {
                    this.mOps[n3] = object.removed.get((int)n2).mIndex;
                }
            } else {
                arrn = this.mOps;
                n3 = n5 + 1;
                arrn[n5] = 0;
            }
            object = object.next;
        } while (true);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public BackStackRecord instantiate(FragmentManagerImpl fragmentManagerImpl) {
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
        int n2 = 0;
        int n3 = 0;
        do {
            if (n2 >= this.mOps.length) {
                backStackRecord.mTransition = this.mTransition;
                backStackRecord.mTransitionStyle = this.mTransitionStyle;
                backStackRecord.mName = this.mName;
                backStackRecord.mIndex = this.mIndex;
                backStackRecord.mAddToBackStack = true;
                backStackRecord.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
                backStackRecord.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
                backStackRecord.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
                backStackRecord.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
                backStackRecord.bumpBackStackNesting(1);
                return backStackRecord;
            }
            BackStackRecord.Op op = new BackStackRecord.Op();
            Object object = this.mOps;
            int n4 = n2 + 1;
            op.cmd = object[n2];
            if (FragmentManagerImpl.DEBUG) {
                Log.v((String)"FragmentManager", (String)("Instantiate " + backStackRecord + " op #" + n3 + " base fragment #" + this.mOps[n4]));
            }
            object = this.mOps;
            n2 = n4 + 1;
            op.fragment = (n4 = object[n4]) >= 0 ? fragmentManagerImpl.mActive.get(n4) : null;
            object = this.mOps;
            n4 = n2 + 1;
            op.enterAnim = object[n2];
            object = this.mOps;
            n2 = n4 + 1;
            op.exitAnim = object[n4];
            object = this.mOps;
            n4 = n2 + 1;
            op.popEnterAnim = object[n2];
            object = this.mOps;
            int n5 = n4 + 1;
            op.popExitAnim = object[n4];
            object = this.mOps;
            n2 = n5 + 1;
            int n6 = object[n5];
            n4 = n2;
            if (n6 > 0) {
                op.removed = new ArrayList(n6);
                n5 = 0;
                do {
                    n4 = n2;
                    if (n5 >= n6) break;
                    if (FragmentManagerImpl.DEBUG) {
                        Log.v((String)"FragmentManager", (String)("Instantiate " + backStackRecord + " set remove fragment #" + this.mOps[n2]));
                    }
                    object = fragmentManagerImpl.mActive.get(this.mOps[n2]);
                    op.removed.add((Fragment)object);
                    ++n5;
                    ++n2;
                } while (true);
            }
            n2 = n4;
            backStackRecord.addOp(op);
            ++n3;
        } while (true);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeIntArray(this.mOps);
        parcel.writeInt(this.mTransition);
        parcel.writeInt(this.mTransitionStyle);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mIndex);
        parcel.writeInt(this.mBreadCrumbTitleRes);
        TextUtils.writeToParcel((CharSequence)this.mBreadCrumbTitleText, (Parcel)parcel, (int)0);
        parcel.writeInt(this.mBreadCrumbShortTitleRes);
        TextUtils.writeToParcel((CharSequence)this.mBreadCrumbShortTitleText, (Parcel)parcel, (int)0);
    }

}

