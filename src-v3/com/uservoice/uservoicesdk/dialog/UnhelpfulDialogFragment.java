package com.uservoice.uservoicesdk.dialog;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.activity.ContactActivity;
import com.uservoice.uservoicesdk.ui.Utils;

public class UnhelpfulDialogFragment extends DialogFragmentBugfixed {

    /* renamed from: com.uservoice.uservoicesdk.dialog.UnhelpfulDialogFragment.1 */
    class C06481 implements OnClickListener {
        C06481() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.UnhelpfulDialogFragment.2 */
    class C06492 implements OnClickListener {
        C06492() {
        }

        public void onClick(DialogInterface dialog, int which) {
            UnhelpfulDialogFragment.this.getActivity().startActivity(new Intent(UnhelpfulDialogFragment.this.getActivity(), ContactActivity.class));
            dialog.cancel();
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        if (!Utils.isDarkTheme(getActivity())) {
            builder.setInverseBackgroundForced(true);
        }
        builder.setTitle(C0621R.string.uv_unhelpful_article_message_question);
        builder.setNegativeButton(C0621R.string.uv_no, new C06481());
        builder.setPositiveButton(C0621R.string.uv_yes, new C06492());
        return builder.create();
    }
}
