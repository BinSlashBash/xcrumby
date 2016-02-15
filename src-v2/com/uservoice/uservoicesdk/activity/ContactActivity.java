/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.uservoice.uservoicesdk.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.activity.InstantAnswersActivity;
import com.uservoice.uservoicesdk.ui.ContactAdapter;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;

public class ContactActivity
extends InstantAnswersActivity {
    @Override
    protected InstantAnswersAdapter createAdapter() {
        return new ContactAdapter(this);
    }

    @Override
    protected int getDiscardDialogMessage() {
        return R.string.uv_msg_confirm_discard_message;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(R.string.uv_contact_us);
    }
}

