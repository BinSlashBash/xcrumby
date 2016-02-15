/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.LinearLayout
 *  android.widget.ListView
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestions;
import com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestionsHolder;

public class OmnibarModal
extends LinearLayout {
    private FragmentSuggestions fragmentSuggestions;
    private ListView listView;

    public OmnibarModal(Context context) {
        super(context);
    }

    public OmnibarModal(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OmnibarModal(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void hide() {
        this.fragmentSuggestions.hide();
        this.setVisibility(8);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.fragmentSuggestions = (FragmentSuggestions)this.findViewById(2131493073);
    }

    public void shareSuggestionsViewWith(FragmentSuggestionsHolder fragmentSuggestionsHolder) {
        fragmentSuggestionsHolder.setFragmentSuggestions(this.fragmentSuggestions);
    }

    public void show(boolean bl2) {
        if (bl2) {
            this.fragmentSuggestions.show();
        }
        this.setVisibility(0);
    }
}

