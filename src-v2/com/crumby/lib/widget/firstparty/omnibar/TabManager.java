/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.view.View;
import com.crumby.lib.widget.firstparty.omnibar.FormTabButton;
import com.crumby.lib.widget.firstparty.omnibar.TabSwitchListener;

public class TabManager
implements View.OnClickListener {
    private FormTabButton current;
    private final FormTabButton[] formTabButtons;
    private final TabSwitchListener tabSwitchListener;

    public /* varargs */ TabManager(TabSwitchListener tabSwitchListener, FormTabButton ... arrformTabButton) {
        this.formTabButtons = arrformTabButton;
        this.tabSwitchListener = tabSwitchListener;
        int n2 = arrformTabButton.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrformTabButton[i2].initialize(this);
        }
        this.clear();
    }

    public void clear() {
        FormTabButton[] arrformTabButton = this.formTabButtons;
        int n2 = arrformTabButton.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrformTabButton[i2].deactivate();
        }
    }

    public void onClick(View view) {
        if (this.current == view) {
            return;
        }
        this.clear();
        this.current = (FormTabButton)view;
        this.current.activate();
        this.tabSwitchListener.onTabSwitch(this.current);
    }
}

