package com.crumby.lib.widget.firstparty.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.crumby.C0065R;
import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public class FormCheckBox extends CheckBox implements FormField {
    private String argumentName;
    private String niceName;
    private Watcher watcher;

    public FormCheckBox(Context context) {
        super(context);
    }

    public FormCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 16842860);
    }

    public FormCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setChecked(true);
        TypedArray a = context.obtainStyledAttributes(attrs, C0065R.styleable.FormField, defStyle, 0);
        this.niceName = a.getString(1);
        this.argumentName = a.getString(0);
        setFieldValue("1");
        a.recycle();
    }

    public String getNiceName() {
        return this.niceName;
    }

    public String getArgumentName() {
        return this.argumentName;
    }

    public String getFieldValue() {
        return isChecked() ? "1" : "0";
    }

    public void setFieldValue(String value) {
        if ((!isChecked() && value.equals("1")) || (isChecked() && value.equals("0"))) {
            toggle();
        }
    }

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
