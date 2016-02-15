/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.dialog.DialogFragmentBugfixed;
import com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.flow.SigninManager;
import com.uservoice.uservoicesdk.model.Comment;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.model.User;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.Utils;

@SuppressLint(value={"ValidFragment"})
public class CommentDialogFragment
extends DialogFragmentBugfixed {
    private final Suggestion suggestion;
    private final SuggestionDialogFragment suggestionDialog;

    public CommentDialogFragment(Suggestion suggestion, SuggestionDialogFragment suggestionDialogFragment) {
        this.suggestion = suggestion;
        this.suggestionDialog = suggestionDialogFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = new AlertDialog.Builder((Context)this.getActivity());
        if (!Utils.isDarkTheme((Context)this.getActivity())) {
            bundle.setInverseBackgroundForced(true);
        }
        bundle.setTitle(R.string.uv_post_a_comment);
        Object object = this.getActivity().getLayoutInflater().inflate(R.layout.uv_comment_dialog, null);
        final EditText editText = (EditText)object.findViewById(R.id.uv_comment_edit_text);
        View view = object.findViewById(R.id.uv_email);
        View view2 = object.findViewById(R.id.uv_name);
        final EditText editText2 = (EditText)view.findViewById(R.id.uv_text_field);
        final EditText editText3 = (EditText)view2.findViewById(R.id.uv_text_field);
        if (Session.getInstance().getUser() != null) {
            view.setVisibility(8);
            view2.setVisibility(8);
        } else {
            editText2.setText((CharSequence)Session.getInstance().getEmail());
            ((TextView)view.findViewById(R.id.uv_header_text)).setText(R.string.uv_your_email_address);
            editText3.setText((CharSequence)Session.getInstance().getName());
            ((TextView)view2.findViewById(R.id.uv_header_text)).setText(R.string.uv_your_name);
        }
        bundle.setView((View)object);
        bundle.setNegativeButton(R.string.uv_cancel, null);
        object = this.getActivity();
        bundle.setPositiveButton(R.string.uv_post_comment, new DialogInterface.OnClickListener((Activity)object){
            final /* synthetic */ Activity val$context;

            public void onClick(DialogInterface object, int n2) {
                object = editText.getText().toString();
                if (object.trim().length() > 0) {
                    SigninManager.signIn(CommentDialogFragment.this.getActivity(), editText2.getText().toString(), editText3.getText().toString(), new SigninCallback((String)object){
                        final /* synthetic */ String val$text;

                        @Override
                        public void onSuccess() {
                            Comment.createComment(CommentDialogFragment.this.suggestion, this.val$text, new DefaultCallback<Comment>((Context)CommentDialogFragment.this.getActivity()){

                                @Override
                                public void onModel(Comment comment) {
                                    Toast.makeText((Context)1.this.val$context, (int)R.string.uv_msg_comment_posted, (int)0).show();
                                    CommentDialogFragment.this.suggestionDialog.commentPosted(comment);
                                }
                            });
                        }

                    });
                }
            }

        });
        bundle = bundle.create();
        bundle.getWindow().setSoftInputMode(5);
        return bundle;
    }

}

