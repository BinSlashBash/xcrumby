package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;

public interface Acls {

    public interface OnGameplayAclLoadedCallback {
    }

    public interface OnGameplayAclUpdatedCallback {
    }

    public interface OnNotifyAclLoadedCallback {
    }

    public interface OnNotifyAclUpdatedCallback {
    }

    public interface LoadAclResult extends Releasable, Result {
    }

    public interface LoadFAclResult extends Releasable, Result {
    }
}
