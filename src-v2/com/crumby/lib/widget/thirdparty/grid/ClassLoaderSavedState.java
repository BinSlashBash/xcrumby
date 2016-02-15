/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.crumby.lib.widget.thirdparty.grid;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class ClassLoaderSavedState
implements Parcelable {
    public static final Parcelable.Creator<ClassLoaderSavedState> CREATOR;
    public static final ClassLoaderSavedState EMPTY_STATE;
    private ClassLoader mClassLoader;
    private Parcelable mSuperState = EMPTY_STATE;

    static {
        EMPTY_STATE = new ClassLoaderSavedState(){};
        CREATOR = new Parcelable.Creator<ClassLoaderSavedState>(){

            public ClassLoaderSavedState createFromParcel(Parcel parcel) {
                if (parcel.readParcelable(null) != null) {
                    throw new IllegalStateException("superState must be null");
                }
                return ClassLoaderSavedState.EMPTY_STATE;
            }

            public ClassLoaderSavedState[] newArray(int n2) {
                return new ClassLoaderSavedState[n2];
            }
        };
    }

    private ClassLoaderSavedState() {
        this.mSuperState = null;
        this.mClassLoader = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected ClassLoaderSavedState(Parcel object) {
        object = object.readParcelable(this.mClassLoader);
        if (object == null) {
            object = EMPTY_STATE;
        }
        this.mSuperState = object;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected ClassLoaderSavedState(Parcelable parcelable, ClassLoader classLoader) {
        this.mClassLoader = classLoader;
        if (parcelable == null) {
            throw new IllegalArgumentException("superState must not be null");
        }
        if (parcelable == EMPTY_STATE) {
            parcelable = null;
        }
        this.mSuperState = parcelable;
    }

    public int describeContents() {
        return 0;
    }

    public final Parcelable getSuperState() {
        return this.mSuperState;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeParcelable(this.mSuperState, n2);
    }

}

