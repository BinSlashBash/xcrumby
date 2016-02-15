/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

import com.tapstream.sdk.Event;
import com.tapstream.sdk.Hit;

public interface Api {
    public void fireEvent(Event var1);

    public void fireHit(Hit var1, Hit.CompletionHandler var2);
}

