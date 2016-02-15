/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ToggleButton
 */
package com.crumby.lib.widget.firstparty.form;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;
import com.crumby.lib.widget.firstparty.form.FormField;
import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public class FormToggleButton
extends ToggleButton
implements FormField {
    public FormToggleButton(Context context) {
        super(context);
    }

    public FormToggleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FormToggleButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    @Override
    public String getArgumentName() {
        return this.getText().toString();
    }

    @Override
    public String getFieldValue() {
        if (this.isChecked()) {
            return "1";
        }
        return "0";
    }

    @Override
    public String getNiceName() {
        return null;
    }

    @Override
    public void setFieldValue(String string2) {
        if (!this.isChecked() && string2.equals("1") || this.isChecked() && string2.equals("0")) {
            this.toggle();
        }
    }

    @Override
    public void setWatcher(Watcher watcher) {
        watcher.onValueChanged();
    }
}

