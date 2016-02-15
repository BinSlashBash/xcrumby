/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package it.gmariotti.changelibs.library.parser;

import android.content.Context;
import it.gmariotti.changelibs.library.internal.ChangeLog;

public abstract class BaseParser {
    protected boolean bulletedList;
    protected Context mContext;

    public BaseParser(Context context) {
        this.mContext = context;
    }

    public abstract ChangeLog readChangeLogFile() throws Exception;
}

