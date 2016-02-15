/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.router;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileOpener {
    public InputStream open(String var1) throws FileNotFoundException;
}

