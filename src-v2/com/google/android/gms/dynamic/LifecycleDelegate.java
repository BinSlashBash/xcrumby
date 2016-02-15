/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface LifecycleDelegate {
    public void onCreate(Bundle var1);

    public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3);

    public void onDestroy();

    public void onDestroyView();

    public void onInflate(Activity var1, Bundle var2, Bundle var3);

    public void onLowMemory();

    public void onPause();

    public void onResume();

    public void onSaveInstanceState(Bundle var1);

    public void onStart();

    public void onStop();
}

