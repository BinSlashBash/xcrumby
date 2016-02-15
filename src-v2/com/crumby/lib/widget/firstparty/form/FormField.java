/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.widget.firstparty.form;

import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public interface FormField {
    public String getArgumentName();

    public String getFieldValue();

    public String getNiceName();

    public void setFieldValue(String var1);

    public void setWatcher(Watcher var1);
}

