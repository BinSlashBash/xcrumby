package com.crumby.lib.widget.firstparty.form;

import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public interface FormField {
    String getArgumentName();

    String getFieldValue();

    String getNiceName();

    void setFieldValue(String str);

    void setWatcher(Watcher watcher);
}
