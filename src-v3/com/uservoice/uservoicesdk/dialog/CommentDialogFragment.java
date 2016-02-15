package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.flow.SigninManager;
import com.uservoice.uservoicesdk.model.Comment;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint({"ValidFragment"})
public class CommentDialogFragment extends DialogFragmentBugfixed {
    private final Suggestion suggestion;
    private final SuggestionDialogFragment suggestionDialog;

    /* renamed from: com.uservoice.uservoicesdk.dialog.CommentDialogFragment.1 */
    class C06371 implements OnClickListener {
        final /* synthetic */ Activity val$context;
        final /* synthetic */ EditText val$emailField;
        final /* synthetic */ EditText val$nameField;
        final /* synthetic */ EditText val$textField;

        /* renamed from: com.uservoice.uservoicesdk.dialog.CommentDialogFragment.1.1 */
        class C11721 extends SigninCallback {
            final /* synthetic */ String val$text;

            /* renamed from: com.uservoice.uservoicesdk.dialog.CommentDialogFragment.1.1.1 */
            class C14331 extends DefaultCallback<Comment> {
                C14331(Context x0) {
                    super(x0);
                }

                public void onModel(Comment model) {
                    Toast.makeText(C06371.this.val$context, C0621R.string.uv_msg_comment_posted, 0).show();
                    CommentDialogFragment.this.suggestionDialog.commentPosted(model);
                }
            }

            C11721(String str) {
                this.val$text = str;
            }

            public void onSuccess() {
                Comment.createComment(CommentDialogFragment.this.suggestion, this.val$text, new C14331(CommentDialogFragment.this.getActivity()));
            }
        }

        C06371(EditText editText, EditText editText2, EditText editText3, Activity activity) {
            this.val$textField = editText;
            this.val$emailField = editText2;
            this.val$nameField = editText3;
            this.val$context = activity;
        }

        public void onClick(DialogInterface dialog, int which) {
            String text = this.val$textField.getText().toString();
            if (text.trim().length() > 0) {
                SigninManager.signIn(CommentDialogFragment.this.getActivity(), this.val$emailField.getText().toString(), this.val$nameField.getText().toString(), new C11721(text));
            }
        }
    }

    public CommentDialogFragment(Suggestion suggestion, SuggestionDialogFragment suggestionDialog) {
        this.suggestion = suggestion;
        this.suggestionDialog = suggestionDialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        if (!Utils.isDarkTheme(getActivity())) {
            builder.setInverseBackgroundForced(true);
        }
        builder.setTitle(C0621R.string.uv_post_a_comment);
        View view = getActivity().getLayoutInflater().inflate(C0621R.layout.uv_comment_dialog, null);
        EditText textField = (EditText) view.findViewById(C0621R.id.uv_comment_edit_text);
        View email = view.findViewById(C0621R.id.uv_email);
        View name = view.findViewById(C0621R.id.uv_name);
        EditText emailField = (EditText) email.findViewById(C0621R.id.uv_text_field);
        EditText nameField = (EditText) name.findViewById(C0621R.id.uv_text_field);
        if (Session.getInstance().getUser() != null) {
            email.setVisibility(8);
            name.setVisibility(8);
        } else {
            emailField.setText(Session.getInstance().getEmail());
            ((TextView) email.findViewById(C0621R.id.uv_header_text)).setText(C0621R.string.uv_your_email_address);
            nameField.setText(Session.getInstance().getName());
            ((TextView) name.findViewById(C0621R.id.uv_header_text)).setText(C0621R.string.uv_your_name);
        }
        builder.setView(view);
        builder.setNegativeButton(C0621R.string.uv_cancel, null);
        builder.setPositiveButton(C0621R.string.uv_post_comment, new C06371(textField, emailField, nameField, getActivity()));
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(5);
        return dialog;
    }
}
