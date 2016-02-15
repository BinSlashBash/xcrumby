package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.crumby.C0065R;

public class OmnibarModal extends LinearLayout {
    private FragmentSuggestions fragmentSuggestions;
    private ListView listView;

    public OmnibarModal(Context context) {
        super(context);
    }

    public OmnibarModal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OmnibarModal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.fragmentSuggestions = (FragmentSuggestions) findViewById(C0065R.id.fragment_suggestions);
    }

    public void show(boolean showSuggestions) {
        if (showSuggestions) {
            this.fragmentSuggestions.show();
        }
        setVisibility(0);
    }

    public void hide() {
        this.fragmentSuggestions.hide();
        setVisibility(8);
    }

    public void shareSuggestionsViewWith(FragmentSuggestionsHolder fragmentSuggestionsHolder) {
        fragmentSuggestionsHolder.setFragmentSuggestions(this.fragmentSuggestions);
    }
}
