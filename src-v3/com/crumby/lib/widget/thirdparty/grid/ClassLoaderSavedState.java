package com.crumby.lib.widget.thirdparty.grid;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public abstract class ClassLoaderSavedState implements Parcelable {
    public static final Creator<ClassLoaderSavedState> CREATOR;
    public static final ClassLoaderSavedState EMPTY_STATE;
    private ClassLoader mClassLoader;
    private Parcelable mSuperState;

    /* renamed from: com.crumby.lib.widget.thirdparty.grid.ClassLoaderSavedState.2 */
    static class C01652 implements Creator<ClassLoaderSavedState> {
        C01652() {
        }

        public ClassLoaderSavedState createFromParcel(Parcel in) {
            if (in.readParcelable(null) == null) {
                return ClassLoaderSavedState.EMPTY_STATE;
            }
            throw new IllegalStateException("superState must be null");
        }

        public ClassLoaderSavedState[] newArray(int size) {
            return new ClassLoaderSavedState[size];
        }
    }

    /* renamed from: com.crumby.lib.widget.thirdparty.grid.ClassLoaderSavedState.1 */
    static class C07391 extends ClassLoaderSavedState {
        C07391() {
            super();
        }
    }

    static {
        EMPTY_STATE = new C07391();
        CREATOR = new C01652();
    }

    private ClassLoaderSavedState() {
        this.mSuperState = EMPTY_STATE;
        this.mSuperState = null;
        this.mClassLoader = null;
    }

    protected ClassLoaderSavedState(Parcelable superState, ClassLoader classLoader) {
        this.mSuperState = EMPTY_STATE;
        this.mClassLoader = classLoader;
        if (superState == null) {
            throw new IllegalArgumentException("superState must not be null");
        }
        if (superState == EMPTY_STATE) {
            superState = null;
        }
        this.mSuperState = superState;
    }

    protected ClassLoaderSavedState(Parcel source) {
        this.mSuperState = EMPTY_STATE;
        Parcelable superState = source.readParcelable(this.mClassLoader);
        if (superState == null) {
            superState = EMPTY_STATE;
        }
        this.mSuperState = superState;
    }

    public final Parcelable getSuperState() {
        return this.mSuperState;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSuperState, flags);
    }
}
