/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.text.Editable
 *  android.util.AttributeSet
 *  android.widget.EditText
 */
package com.crumby.lib.widget.firstparty.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;
import com.crumby.R;
import com.crumby.lib.widget.firstparty.form.FormField;
import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public class FormEditText
extends EditText
implements FormField {
    private String argumentName;
    private String niceName;
    private Watcher watcher;

    public FormEditText(Context context) {
        super(context);
    }

    public FormEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842862);
    }

    public FormEditText(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.FormField, n2, 0);
        if (this.getHint() == null || this.getHint().equals("")) {
            this.setHint((CharSequence)"Enter Keywords");
        }
        this.argumentName = context.getString(0);
        this.niceName = context.getString(1);
        this.setFieldValue(context.getString(2));
        context.recycle();
    }

    @Override
    public String getArgumentName() {
        return this.argumentName;
    }

    @Override
    public String getFieldValue() {
        return this.getText().toString();
    }

    @Override
    public String getNiceName() {
        return this.niceName;
    }

    protected void onFocusChanged(boolean bl2, int n2, Rect rect) {
        super.onFocusChanged(bl2, n2, rect);
        if (this.watcher != null) {
            this.watcher.onValueChanged();
        }
    }

    protected void onTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
        super.onTextChanged(charSequence, n2, n3, n4);
        if (this.watcher != null) {
            this.watcher.onValueChanged();
        }
    }

    @Override
    public void setFieldValue(String string2) {
        this.setText((CharSequence)string2);
    }

    @Override
    public void setWatcher(Watcher watcher) {
        this.watcher = watcher;
    }
}

