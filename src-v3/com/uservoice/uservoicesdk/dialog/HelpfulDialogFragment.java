package com.uservoice.uservoicesdk.dialog;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.ui.Utils;

public class HelpfulDialogFragment extends DialogFragmentBugfixed {

    /* renamed from: com.uservoice.uservoicesdk.dialog.HelpfulDialogFragment.1 */
    class C06381 implements OnClickListener {
        C06381() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            HelpfulDialogFragment.this.getActivity().finish();
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        if (!Utils.isDarkTheme(getActivity())) {
            builder.setInverseBackgroundForced(true);
        }
        builder.setTitle(C0621R.string.uv_helpful_article_message_question);
        builder.setNegativeButton(C0621R.string.uv_no, new C06381());
        builder.setPositiveButton(C0621R.string.uv_yes, null);
        return builder.create();
    }
}
