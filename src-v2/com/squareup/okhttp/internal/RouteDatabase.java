/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal;

import com.squareup.okhttp.Route;
import java.util.LinkedHashSet;
import java.util.Set;

public final class RouteDatabase {
    private final Set<Route> failedRoutes = new LinkedHashSet<Route>();

    public void connected(Route route) {
        synchronized (this) {
            this.failedRoutes.remove(route);
            return;
        }
    }

    public void failed(Route route) {
        synchronized (this) {
            this.failedRoutes.add(route);
            return;
        }
    }

    public int failedRoutesCount() {
        synchronized (this) {
            int n2 = this.failedRoutes.size();
            return n2;
        }
    }

    public boolean shouldPostpone(Route route) {
        synchronized (this) {
            boolean bl2 = this.failedRoutes.contains(route);
            return bl2;
        }
    }
}

