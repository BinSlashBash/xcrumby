package com.crumby.lib.widget.firstparty.form;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;
import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public class FormToggleButton extends ToggleButton implements FormField {
    public FormToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FormToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormToggleButton(Context context) {
        super(context);
    }

    public String getNiceName() {
        return null;
    }

    public String getArgumentName() {
        return getText().toString();
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
        watcher.onValueChanged();
    }
}
