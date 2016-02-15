package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.dynamic.C0305c.C0818a;

/* renamed from: com.google.android.gms.dynamic.h */
public final class C1325h extends C0818a {
    private Fragment Hz;

    private C1325h(Fragment fragment) {
        this.Hz = fragment;
    }

    public static C1325h m2711a(Fragment fragment) {
        return fragment != null ? new C1325h(fragment) : null;
    }

    public void m2712b(C0306d c0306d) {
        this.Hz.registerForContextMenu((View) C1324e.m2709d(c0306d));
    }

    public void m2713c(C0306d c0306d) {
        this.Hz.unregisterForContextMenu((View) C1324e.m2709d(c0306d));
    }

    public C0306d fX() {
        return C1324e.m2710h(this.Hz.getActivity());
    }

    public C0305c fY() {
        return C1325h.m2711a(this.Hz.getParentFragment());
    }

    public C0306d fZ() {
        return C1324e.m2710h(this.Hz.getResources());
    }

    public C0305c ga() {
        return C1325h.m2711a(this.Hz.getTargetFragment());
    }

    public Bundle getArguments() {
        return this.Hz.getArguments();
    }

    public int getId() {
        return this.Hz.getId();
    }

    public boolean getRetainInstance() {
        return this.Hz.getRetainInstance();
    }

    public String getTag() {
        return this.Hz.getTag();
    }

    public int getTargetRequestCode() {
        return this.Hz.getTargetRequestCode();
    }

    public boolean getUserVisibleHint() {
        return this.Hz.getUserVisibleHint();
    }

    public C0306d getView() {
        return C1324e.m2710h(this.Hz.getView());
    }

    public boolean isAdded() {
        return this.Hz.isAdded();
    }

    public boolean isDetached() {
        return this.Hz.isDetached();
    }

    public boolean isHidden() {
        return this.Hz.isHidden();
    }

    public boolean isInLayout() {
        return this.Hz.isInLayout();
    }

    public boolean isRemoving() {
        return this.Hz.isRemoving();
    }

    public boolean isResumed() {
        return this.Hz.isResumed();
    }

    public boolean isVisible() {
        return this.Hz.isVisible();
    }

    public void setHasOptionsMenu(boolean hasMenu) {
        this.Hz.setHasOptionsMenu(hasMenu);
    }

    public void setMenuVisibility(boolean menuVisible) {
        this.Hz.setMenuVisibility(menuVisible);
    }

    public void setRetainInstance(boolean retain) {
        this.Hz.setRetainInstance(retain);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.Hz.setUserVisibleHint(isVisibleToUser);
    }

    public void startActivity(Intent intent) {
        this.Hz.startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        this.Hz.startActivityForResult(intent, requestCode);
    }
}
