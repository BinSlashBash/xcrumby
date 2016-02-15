/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.widget.CheckBox
 */
package com.crumby.lib.widget.firstparty.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.crumby.R;
import com.crumby.lib.widget.firstparty.form.FormField;
import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public class FormCheckBox
extends CheckBox
implements FormField {
    private String argumentName;
    private String niceName;
    private Watcher watcher;

    public FormCheckBox(Context context) {
        super(context);
    }

    public FormCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842860);
    }

    public FormCheckBox(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.setChecked(true);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.FormField, n2, 0);
        this.niceName = context.getString(1);
        this.argumentName = context.getString(0);
        this.setFieldValue("1");
        context.recycle();
    }

    @Override
    public String getArgumentName() {
        return this.argumentName;
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
        return this.niceName;
    }

    @Override
    public void setFieldValue(String string2) {
        if (!this.isChecked() && string2.equals("1") || this.isChecked() && string2.equals("0")) {
            this.toggle();
        }
    }

    @Override
    public void setWatcher(Watcher watcher) {
        this.watcher = watcher;
    }

    public void toggle() {
        super.toggle();
        if (this.watcher != null) {
            this.watcher.onValueChanged();
        }
    }
}

