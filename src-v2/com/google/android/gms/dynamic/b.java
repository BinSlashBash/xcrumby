/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.View
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;

public final class b
extends c.a {
    private Fragment Hv;

    private b(Fragment fragment) {
        this.Hv = fragment;
    }

    public static b a(Fragment fragment) {
        if (fragment != null) {
            return new b(fragment);
        }
        return null;
    }

    @Override
    public void b(d d2) {
        d2 = (View)e.d(d2);
        this.Hv.registerForContextMenu((View)d2);
    }

    @Override
    public void c(d d2) {
        d2 = (View)e.d(d2);
        this.Hv.unregisterForContextMenu((View)d2);
    }

    @Override
    public d fX() {
        return e.h(this.Hv.getActivity());
    }

    @Override
    public c fY() {
        return b.a(this.Hv.getParentFragment());
    }

    @Override
    public d fZ() {
        return e.h(this.Hv.getResources());
    }

    @Override
    public c ga() {
        return b.a(this.Hv.getTargetFragment());
    }

    @Override
    public Bundle getArguments() {
        return this.Hv.getArguments();
    }

    @Override
    public int getId() {
        return this.Hv.getId();
    }

    @Override
    public boolean getRetainInstance() {
        return this.Hv.getRetainInstance();
    }

    @Override
    public String getTag() {
        return this.Hv.getTag();
    }

    @Override
    public int getTargetRequestCode() {
        return this.Hv.getTargetRequestCode();
    }

    @Override
    public boolean getUserVisibleHint() {
        return this.Hv.getUserVisibleHint();
    }

    @Override
    public d getView() {
        return e.h(this.Hv.getView());
    }

    @Override
    public boolean isAdded() {
        return this.Hv.isAdded();
    }

    @Override
    public boolean isDetached() {
        return this.Hv.isDetached();
    }

    @Override
    public boolean isHidden() {
        return this.Hv.isHidden();
    }

    @Override
    public boolean isInLayout() {
        return this.Hv.isInLayout();
    }

    @Override
    public boolean isRemoving() {
        return this.Hv.isRemoving();
    }

    @Override
    public boolean isResumed() {
        return this.Hv.isResumed();
    }

    @Override
    public boolean isVisible() {
        return this.Hv.isVisible();
    }

    @Override
    public void setHasOptionsMenu(boolean bl2) {
        this.Hv.setHasOptionsMenu(bl2);
    }

    @Override
    public void setMenuVisibility(boolean bl2) {
        this.Hv.setMenuVisibility(bl2);
    }

    @Override
    public void setRetainInstance(boolean bl2) {
        this.Hv.setRetainInstance(bl2);
    }

    @Override
    public void setUserVisibleHint(boolean bl2) {
        this.Hv.setUserVisibleHint(bl2);
    }

    @Override
    public void startActivity(Intent intent) {
        this.Hv.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int n2) {
        this.Hv.startActivityForResult(intent, n2);
    }
}

