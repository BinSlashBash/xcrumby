package com.google.android.gms.dynamic;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.dynamic.C0305c.C0818a;

/* renamed from: com.google.android.gms.dynamic.b */
public final class C1323b extends C0818a {
    private Fragment Hv;

    private C1323b(Fragment fragment) {
        this.Hv = fragment;
    }

    public static C1323b m2706a(Fragment fragment) {
        return fragment != null ? new C1323b(fragment) : null;
    }

    public void m2707b(C0306d c0306d) {
        this.Hv.registerForContextMenu((View) C1324e.m2709d(c0306d));
    }

    public void m2708c(C0306d c0306d) {
        this.Hv.unregisterForContextMenu((View) C1324e.m2709d(c0306d));
    }

    public C0306d fX() {
        return C1324e.m2710h(this.Hv.getActivity());
    }

    public C0305c fY() {
        return C1323b.m2706a(this.Hv.getParentFragment());
    }

    public C0306d fZ() {
        return C1324e.m2710h(this.Hv.getResources());
    }

    public C0305c ga() {
        return C1323b.m2706a(this.Hv.getTargetFragment());
    }

    public Bundle getArguments() {
        return this.Hv.getArguments();
    }

    public int getId() {
        return this.Hv.getId();
    }

    public boolean getRetainInstance() {
        return this.Hv.getRetainInstance();
    }

    public String getTag() {
        return this.Hv.getTag();
    }

    public int getTargetRequestCode() {
        return this.Hv.getTargetRequestCode();
    }

    public boolean getUserVisibleHint() {
        return this.Hv.getUserVisibleHint();
    }

    public C0306d getView() {
        return C1324e.m2710h(this.Hv.getView());
    }

    public boolean isAdded() {
        return this.Hv.isAdded();
    }

    public boolean isDetached() {
        return this.Hv.isDetached();
    }

    public boolean isHidden() {
        return this.Hv.isHidden();
    }

    public boolean isInLayout() {
        return this.Hv.isInLayout();
    }

    public boolean isRemoving() {
        return this.Hv.isRemoving();
    }

    public boolean isResumed() {
        return this.Hv.isResumed();
    }

    public boolean isVisible() {
        return this.Hv.isVisible();
    }

    public void setHasOptionsMenu(boolean hasMenu) {
        this.Hv.setHasOptionsMenu(hasMenu);
    }

    public void setMenuVisibility(boolean menuVisible) {
        this.Hv.setMenuVisibility(menuVisible);
    }

    public void setRetainInstance(boolean retain) {
        this.Hv.setRetainInstance(retain);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.Hv.setUserVisibleHint(isVisibleToUser);
    }

    public void startActivity(Intent intent) {
        this.Hv.startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        this.Hv.startActivityForResult(intent, requestCode);
    }
}
