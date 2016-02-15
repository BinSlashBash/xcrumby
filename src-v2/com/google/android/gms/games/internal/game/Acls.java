/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;

public interface Acls {

    public static interface LoadAclResult
    extends Releasable,
    Result {
    }

    public static interface LoadFAclResult
    extends Releasable,
    Result {
    }

    public static interface OnGameplayAclLoadedCallback {
    }

    public static interface OnGameplayAclUpdatedCallback {
    }

    public static interface OnNotifyAclLoadedCallback {
    }

    public static interface OnNotifyAclUpdatedCallback {
    }

}

