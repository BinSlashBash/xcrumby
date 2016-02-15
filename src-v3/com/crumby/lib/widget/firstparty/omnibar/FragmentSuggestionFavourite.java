package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import com.crumby.C0065R;

public class FragmentSuggestionFavourite extends ImageButton {
    private boolean checked;

    public FragmentSuggestionFavourite(Context context) {
        super(context);
    }

    public FragmentSuggestionFavourite(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentSuggestionFavourite(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            setImageDrawable(getResources().getDrawable(C0065R.drawable.ic_action_important_copy));
        } else {
            setImageDrawable(getResources().getDrawable(C0065R.drawable.ic_action_not_important));
        }
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void toggle() {
        setChecked(!this.checked);
    }
}
