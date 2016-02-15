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
import com.uservoice.uservoicesdk.activity.InstantAnswersActivity;
import com.uservoice.uservoicesdk.deflection.Deflection;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.flow.SigninManager;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint({"ValidFragment"})
public class SubscribeDialogFragment extends DialogFragmentBugfixed {
    private final String deflectingType;
    private final Suggestion suggestion;
    private final SuggestionDialogFragment suggestionDialog;

    /* renamed from: com.uservoice.uservoicesdk.dialog.SubscribeDialogFragment.1 */
    class C06451 implements OnClickListener {
        final /* synthetic */ EditText val$emailField;

        /* renamed from: com.uservoice.uservoicesdk.dialog.SubscribeDialogFragment.1.1 */
        class C11751 extends SigninCallback {
            final /* synthetic */ DialogInterface val$dialog;

            /* renamed from: com.uservoice.uservoicesdk.dialog.SubscribeDialogFragment.1.1.1 */
            class C14401 extends DefaultCallback<Suggestion> {
                C14401(Context x0) {
                    super(x0);
                }

                public void onModel(Suggestion model) {
                    if (SubscribeDialogFragment.this.getActivity() instanceof InstantAnswersActivity) {
                        Deflection.trackDeflection("subscribed", SubscribeDialogFragment.this.deflectingType, model);
                    }
                    SubscribeDialogFragment.this.suggestionDialog.suggestionSubscriptionUpdated(model);
                    C11751.this.val$dialog.dismiss();
                }
            }

            C11751(DialogInterface dialogInterface) {
                this.val$dialog = dialogInterface;
            }

            public void onSuccess() {
                SubscribeDialogFragment.this.suggestion.subscribe(new C14401(SubscribeDialogFragment.this.getActivity()));
            }
        }

        C06451(EditText editText) {
            this.val$emailField = editText;
        }

        public void onClick(DialogInterface dialog, int which) {
            Session.getInstance().persistIdentity(Session.getInstance().getName(), this.val$emailField.getText().toString());
            SigninManager.signinForSubscribe(SubscribeDialogFragment.this.getActivity(), Session.getInstance().getEmail(), new C11751(dialog));
        }
    }

    public SubscribeDialogFragment(Suggestion suggestion, SuggestionDialogFragment suggestionDialog, String deflectingType) {
        this.suggestion = suggestion;
        this.suggestionDialog = suggestionDialog;
        this.deflectingType = deflectingType;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        builder.setTitle(C0621R.string.uv_subscribe_dialog_title);
        if (!Utils.isDarkTheme(getActivity())) {
            builder.setInverseBackgroundForced(true);
        }
        View view = getActivity().getLayoutInflater().inflate(C0621R.layout.uv_subscribe_dialog, null);
        EditText emailField = (EditText) view.findViewById(C0621R.id.uv_email);
        emailField.setText(Session.getInstance().getEmail());
        builder.setView(view);
        builder.setNegativeButton(C0621R.string.uv_nevermind, null);
        builder.setPositiveButton(C0621R.string.uv_subscribe, new C06451(emailField));
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(5);
        return dialog;
    }
}
