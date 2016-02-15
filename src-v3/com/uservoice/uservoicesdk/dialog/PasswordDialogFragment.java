package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.model.AccessToken;
import com.uservoice.uservoicesdk.model.RequestToken;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint({"ValidFragment"})
public class PasswordDialogFragment extends DialogFragmentBugfixed {
    private final SigninCallback callback;
    private EditText password;

    /* renamed from: com.uservoice.uservoicesdk.dialog.PasswordDialogFragment.1 */
    class C06391 implements OnClickListener {

        /* renamed from: com.uservoice.uservoicesdk.dialog.PasswordDialogFragment.1.1 */
        class C14341 extends DefaultCallback<RequestToken> {
            C14341(Context x0) {
                super(x0);
            }

            public void onModel(RequestToken model) {
                Session.getInstance().setRequestToken(model);
                PasswordDialogFragment.this.authorize();
            }
        }

        C06391() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (Session.getInstance().getRequestToken() != null) {
                PasswordDialogFragment.this.authorize();
            } else {
                RequestToken.getRequestToken(new C14341(PasswordDialogFragment.this.getActivity()));
            }
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.PasswordDialogFragment.2 */
    class C14352 extends DefaultCallback<AccessToken> {
        C14352(Context x0) {
            super(x0);
        }

        public void onModel(AccessToken model) {
            Session.getInstance().setAccessToken(model);
            PasswordDialogFragment.this.callback.onSuccess();
        }
    }

    public PasswordDialogFragment(SigninCallback callback) {
        this.callback = callback;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        builder.setTitle(C0621R.string.uv_password_dialog_title);
        if (!Utils.isDarkTheme(getActivity())) {
            builder.setInverseBackgroundForced(true);
        }
        View view = getActivity().getLayoutInflater().inflate(C0621R.layout.uv_password_dialog, null);
        this.password = (EditText) view.findViewById(C0621R.id.uv_password);
        builder.setView(view);
        builder.setNegativeButton(C0621R.string.uv_cancel, null);
        builder.setPositiveButton(17039370, new C06391());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(5);
        return dialog;
    }

    private void authorize() {
        AccessToken.authorize(Session.getInstance().getEmail(), this.password.getText().toString(), new C14352(getActivity()));
    }
}
