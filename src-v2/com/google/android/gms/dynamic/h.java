/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.View
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;

public final class h
extends c.a {
    private Fragment Hz;

    private h(Fragment fragment) {
        this.Hz = fragment;
    }

    public static h a(Fragment fragment) {
        if (fragment != null) {
            return new h(fragment);
        }
        return null;
    }

    @Override
    public void b(d d2) {
        d2 = (View)e.d(d2);
        this.Hz.registerForContextMenu((View)d2);
    }

    @Override
    public void c(d d2) {
        d2 = (View)e.d(d2);
        this.Hz.unregisterForContextMenu((View)d2);
    }

    @Override
    public d fX() {
        return e.h(this.Hz.getActivity());
    }

    @Override
    public c fY() {
        return h.a(this.Hz.getParentFragment());
    }

    @Override
    public d fZ() {
        return e.h(this.Hz.getResources());
    }

    @Override
    public c ga() {
        return h.a(this.Hz.getTargetFragment());
    }

    @Override
    public Bundle getArguments() {
        return this.Hz.getArguments();
    }

    @Override
    public int getId() {
        return this.Hz.getId();
    }

    @Override
    public boolean getRetainInstance() {
        return this.Hz.getRetainInstance();
    }

    @Override
    public String getTag() {
        return this.Hz.getTag();
    }

    @Override
    public int getTargetRequestCode() {
        return this.Hz.getTargetRequestCode();
    }

    @Override
    public boolean getUserVisibleHint() {
        return this.Hz.getUserVisibleHint();
    }

    @Override
    public d getView() {
        return e.h(this.Hz.getView());
    }

    @Override
    public boolean isAdded() {
        return this.Hz.isAdded();
    }

    @Override
    public boolean isDetached() {
        return this.Hz.isDetached();
    }

    @Override
    public boolean isHidden() {
        return this.Hz.isHidden();
    }

    @Override
    public boolean isInLayout() {
        return this.Hz.isInLayout();
    }

    @Override
    public boolean isRemoving() {
        return this.Hz.isRemoving();
    }

    @Override
    public boolean isResumed() {
        return this.Hz.isResumed();
    }

    @Override
    public boolean isVisible() {
        return this.Hz.isVisible();
    }

    @Override
    public void setHasOptionsMenu(boolean bl2) {
        this.Hz.setHasOptionsMenu(bl2);
    }

    @Override
    public void setMenuVisibility(boolean bl2) {
        this.Hz.setMenuVisibility(bl2);
    }

    @Override
    public void setRetainInstance(boolean bl2) {
        this.Hz.setRetainInstance(bl2);
    }

    @Override
    public void setUserVisibleHint(boolean bl2) {
        this.Hz.setUserVisibleHint(bl2);
    }

    @Override
    public void startActivity(Intent intent) {
        this.Hz.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int n2) {
        this.Hz.startActivityForResult(intent, n2);
    }
}

