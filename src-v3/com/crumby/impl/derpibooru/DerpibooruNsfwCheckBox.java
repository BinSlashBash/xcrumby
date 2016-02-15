package com.crumby.impl.derpibooru;

import android.content.Context;
import android.util.AttributeSet;
import com.crumby.lib.widget.firstparty.form.FormCheckBox;

public class DerpibooruNsfwCheckBox extends FormCheckBox {
    public DerpibooruNsfwCheckBox(Context context) {
        super(context);
    }

    public DerpibooruNsfwCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DerpibooruNsfwCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (DerpibooruProducer.nsfwMode) {
            setFieldValue("1");
        } else {
            setFieldValue("0");
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        setFieldValue("0");
    }
}
