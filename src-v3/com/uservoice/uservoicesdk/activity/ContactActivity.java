package com.uservoice.uservoicesdk.activity;

import android.os.Bundle;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.ui.ContactAdapter;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;

public class ContactActivity extends InstantAnswersActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(C0621R.string.uv_contact_us);
    }

    protected InstantAnswersAdapter createAdapter() {
        return new ContactAdapter(this);
    }

    protected int getDiscardDialogMessage() {
        return C0621R.string.uv_msg_confirm_discard_message;
    }
}
