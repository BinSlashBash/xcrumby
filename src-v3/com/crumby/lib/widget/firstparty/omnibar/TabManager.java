package com.crumby.lib.widget.firstparty.omnibar;

import android.view.View;
import android.view.View.OnClickListener;

public class TabManager implements OnClickListener {
    private FormTabButton current;
    private final FormTabButton[] formTabButtons;
    private final TabSwitchListener tabSwitchListener;

    public TabManager(TabSwitchListener listener, FormTabButton... formTabButtons) {
        this.formTabButtons = formTabButtons;
        this.tabSwitchListener = listener;
        for (FormTabButton button : formTabButtons) {
            button.initialize(this);
        }
        clear();
    }

    public void clear() {
        for (FormTabButton button : this.formTabButtons) {
            button.deactivate();
        }
    }

    public void onClick(View v) {
        if (this.current != v) {
            clear();
            this.current = (FormTabButton) v;
            this.current.activate();
            this.tabSwitchListener.onTabSwitch(this.current);
        }
    }
}
