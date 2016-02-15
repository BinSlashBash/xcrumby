package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.C0192R;
import org.json.zip.JSONzip;

public final class fs extends Button {
    public fs(Context context) {
        this(context, null);
    }

    public fs(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    private int m988b(int i, int i2, int i3) {
        switch (i) {
            case JSONzip.zipEmptyObject /*0*/:
                return i2;
            case Std.STD_FILE /*1*/:
                return i3;
            default:
                throw new IllegalStateException("Unknown color scheme: " + i);
        }
    }

    private void m989b(Resources resources, int i, int i2) {
        int b;
        switch (i) {
            case JSONzip.zipEmptyObject /*0*/:
            case Std.STD_FILE /*1*/:
                b = m988b(i2, C0192R.drawable.common_signin_btn_text_dark, C0192R.drawable.common_signin_btn_text_light);
                break;
            case Std.STD_URL /*2*/:
                b = m988b(i2, C0192R.drawable.common_signin_btn_icon_dark, C0192R.drawable.common_signin_btn_icon_light);
                break;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
        if (b == -1) {
            throw new IllegalStateException("Could not find background resource!");
        }
        setBackgroundDrawable(resources.getDrawable(b));
    }

    private void m990c(Resources resources) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        float f = resources.getDisplayMetrics().density;
        setMinHeight((int) ((f * 48.0f) + 0.5f));
        setMinWidth((int) ((f * 48.0f) + 0.5f));
    }

    private void m991c(Resources resources, int i, int i2) {
        setTextColor(resources.getColorStateList(m988b(i2, C0192R.color.common_signin_btn_text_dark, C0192R.color.common_signin_btn_text_light)));
        switch (i) {
            case JSONzip.zipEmptyObject /*0*/:
                setText(resources.getString(C0192R.string.common_signin_button_text));
            case Std.STD_FILE /*1*/:
                setText(resources.getString(C0192R.string.common_signin_button_text_long));
            case Std.STD_URL /*2*/:
                setText(null);
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
    }

    public void m992a(Resources resources, int i, int i2) {
        boolean z = true;
        boolean z2 = i >= 0 && i < 3;
        fq.m980a(z2, "Unknown button size " + i);
        if (i2 < 0 || i2 >= 2) {
            z = false;
        }
        fq.m980a(z, "Unknown color scheme " + i2);
        m990c(resources);
        m989b(resources, i, i2);
        m991c(resources, i, i2);
    }
}
