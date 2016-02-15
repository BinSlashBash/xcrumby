package com.crumby.lib.widget.firstparty.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.widget.firstparty.omnibar.Watcher;

public class FormEditText extends EditText implements FormField {
    private String argumentName;
    private String niceName;
    private Watcher watcher;

    public FormEditText(Context context) {
        super(context);
    }

    public FormEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 16842862);
    }

    public FormEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, C0065R.styleable.FormField, defStyle, 0);
        if (getHint() == null || getHint().equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            setHint("Enter Keywords");
        }
        this.argumentName = a.getString(0);
        this.niceName = a.getString(1);
        setFieldValue(a.getString(2));
        a.recycle();
    }

    public String getNiceName() {
        return this.niceName;
    }

    public String getArgumentName() {
        return this.argumentName;
    }

    public String getFieldValue() {
        return getText().toString();
    }

    public void setFieldValue(String value) {
        setText(value);
    }

    public void setWatcher(Watcher watcher) {
        this.watcher = watcher;
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (this.watcher != null) {
            this.watcher.onValueChanged();
        }
    }

    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (this.watcher != null) {
            this.watcher.onValueChanged();
        }
    }
}
