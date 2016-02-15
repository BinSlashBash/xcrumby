/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.tagmanager.Container;

public interface ContainerHolder
extends Releasable,
Result {
    public Container getContainer();

    public void refresh();

    public void setContainerAvailableListener(ContainerAvailableListener var1);

    public static interface ContainerAvailableListener {
        public void onContainerAvailable(ContainerHolder var1, String var2);
    }

}

