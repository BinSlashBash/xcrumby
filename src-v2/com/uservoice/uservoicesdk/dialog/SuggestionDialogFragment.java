/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.CheckBox
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.activity.ForumActivity;
import com.uservoice.uservoicesdk.activity.InstantAnswersActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.deflection.Deflection;
import com.uservoice.uservoicesdk.dialog.CommentDialogFragment;
import com.uservoice.uservoicesdk.dialog.DialogFragmentBugfixed;
import com.uservoice.uservoicesdk.dialog.SubscribeDialogFragment;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.flow.SigninManager;
import com.uservoice.uservoicesdk.image.ImageCache;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.model.Comment;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.PaginatedAdapter;
import com.uservoice.uservoicesdk.ui.PaginationScrollListener;
import com.uservoice.uservoicesdk.ui.Utils;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint(value={"ValidFragment"})
public class SuggestionDialogFragment
extends DialogFragmentBugfixed {
    private PaginatedAdapter<Comment> adapter;
    private Context context;
    private String deflectingType;
    private View headerView;
    private Suggestion suggestion;
    private View view;

    public SuggestionDialogFragment(Suggestion suggestion, String string2) {
        this.suggestion = suggestion;
        this.deflectingType = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void displaySuggestion(View view, Suggestion suggestion) {
        TextView textView = (TextView)view.findViewById(R.id.uv_status);
        TextView textView2 = (TextView)view.findViewById(R.id.uv_response_status);
        View view2 = view.findViewById(R.id.uv_response_divider);
        TextView textView3 = (TextView)view.findViewById(R.id.uv_title);
        if (suggestion.isSubscribed()) {
            ((CheckBox)view.findViewById(R.id.uv_subscribe_checkbox)).setChecked(true);
        }
        if (suggestion.getStatus() == null) {
            textView.setVisibility(8);
            textView2.setTextColor(-12303292);
            view2.setBackgroundColor(-12303292);
        } else {
            int n2 = Color.parseColor((String)suggestion.getStatusColor());
            textView.setBackgroundColor(n2);
            textView.setText((CharSequence)suggestion.getStatus());
            textView2.setTextColor(n2);
            textView2.setText((CharSequence)String.format(this.getString(R.string.uv_admin_response_format), suggestion.getStatus().toUpperCase(Locale.getDefault())));
            view2.setBackgroundColor(n2);
        }
        textView3.setText((CharSequence)suggestion.getTitle());
        ((TextView)view.findViewById(R.id.uv_text)).setText((CharSequence)suggestion.getText());
        ((TextView)view.findViewById(R.id.uv_creator)).setText((CharSequence)String.format(view.getContext().getString(R.string.uv_posted_by_format), suggestion.getCreatorName(), DateFormat.getDateInstance().format(suggestion.getCreatedAt())));
        if (suggestion.getAdminResponseText() == null) {
            view.findViewById(R.id.uv_admin_response).setVisibility(8);
        } else {
            view.findViewById(R.id.uv_admin_response).setVisibility(0);
            ((TextView)view.findViewById(R.id.uv_admin_name)).setText((CharSequence)suggestion.getAdminResponseUserName());
            ((TextView)view.findViewById(R.id.uv_response_date)).setText((CharSequence)DateFormat.getDateInstance().format(suggestion.getAdminResponseCreatedAt()));
            ((TextView)view.findViewById(R.id.uv_response_text)).setText((CharSequence)suggestion.getAdminResponseText());
            textView = (ImageView)view.findViewById(R.id.uv_admin_avatar);
            ImageCache.getInstance().loadImage(suggestion.getAdminResponseAvatarUrl(), (ImageView)textView);
        }
        ((TextView)view.findViewById(R.id.uv_comment_count)).setText((CharSequence)Utils.getQuantityString(view, R.plurals.uv_comments, suggestion.getNumberOfComments()).toUpperCase(Locale.getDefault()));
        if (Session.getInstance().getClientConfig().shouldDisplaySuggestionsByRank()) {
            ((TextView)view.findViewById(R.id.uv_subscriber_count)).setText((CharSequence)String.format(view.getContext().getResources().getString(R.string.uv_ranked), suggestion.getRankString()));
            return;
        }
        ((TextView)view.findViewById(R.id.uv_subscriber_count)).setText((CharSequence)String.format(view.getContext().getResources().getQuantityString(R.plurals.uv_number_of_subscribers_format, suggestion.getNumberOfSubscribers()), Utils.getQuantityString(view, R.plurals.uv_subscribers, suggestion.getNumberOfSubscribers())));
    }

    private PaginatedAdapter<Comment> getListAdapter() {
        return new PaginatedAdapter<Comment>((Context)this.getActivity(), R.layout.uv_comment_item, new ArrayList()){

            @Override
            protected void customizeLayout(View view, Comment comment) {
                ((TextView)view.findViewById(R.id.uv_text)).setText((CharSequence)comment.getText());
                ((TextView)view.findViewById(R.id.uv_name)).setText((CharSequence)comment.getUserName());
                ((TextView)view.findViewById(R.id.uv_date)).setText((CharSequence)DateFormat.getDateInstance().format(comment.getCreatedAt()));
                view = (ImageView)view.findViewById(R.id.uv_avatar);
                ImageCache.getInstance().loadImage(comment.getAvatarUrl(), (ImageView)view);
            }

            @Override
            protected int getTotalNumberOfObjects() {
                return SuggestionDialogFragment.this.suggestion.getNumberOfComments();
            }

            @Override
            public boolean isEnabled(int n2) {
                return false;
            }

            @Override
            protected void loadPage(int n2, Callback<List<Comment>> callback) {
                Comment.loadComments(SuggestionDialogFragment.this.suggestion, n2, callback);
            }
        };
    }

    public void commentPosted(Comment comment) {
        this.adapter.add(0, comment);
        this.suggestion.commentPosted(comment);
        this.displaySuggestion(this.view, this.suggestion);
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = new AlertDialog.Builder((Context)this.getActivity());
        this.context = this.getActivity();
        this.setStyle(1, this.getTheme());
        if (!Utils.isDarkTheme((Context)this.getActivity())) {
            bundle.setInverseBackgroundForced(true);
        }
        this.view = this.getActivity().getLayoutInflater().inflate(R.layout.uv_idea_dialog, null);
        this.headerView = this.getActivity().getLayoutInflater().inflate(R.layout.uv_idea_dialog_header, null);
        this.headerView.findViewById(R.id.uv_subscribe).setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = new DefaultCallback<Suggestion>((Context)SuggestionDialogFragment.this.getActivity()){

                    @Override
                    public void onModel(Suggestion suggestion) {
                        if (SuggestionDialogFragment.this.getActivity() instanceof InstantAnswersActivity) {
                            Deflection.trackDeflection("subscribed", SuggestionDialogFragment.this.deflectingType, suggestion);
                        }
                        SuggestionDialogFragment.this.suggestionSubscriptionUpdated(suggestion);
                    }
                };
                if (SuggestionDialogFragment.this.suggestion.isSubscribed()) {
                    SuggestionDialogFragment.this.suggestion.unsubscribe((Callback<Suggestion>)object);
                    return;
                }
                if (Session.getInstance().getEmail() != null) {
                    SigninManager.signinForSubscribe(SuggestionDialogFragment.this.getActivity(), Session.getInstance().getEmail(), new SigninCallback((DefaultCallback)object){
                        final /* synthetic */ DefaultCallback val$callback;

                        @Override
                        public void onSuccess() {
                            SuggestionDialogFragment.this.suggestion.subscribe(this.val$callback);
                        }
                    });
                    return;
                }
                new SubscribeDialogFragment(SuggestionDialogFragment.this.suggestion, SuggestionDialogFragment.this, SuggestionDialogFragment.this.deflectingType).show(SuggestionDialogFragment.this.getFragmentManager(), "SubscribeDialogFragment");
            }

        });
        this.headerView.findViewById(R.id.uv_post_comment).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                new CommentDialogFragment(SuggestionDialogFragment.this.suggestion, SuggestionDialogFragment.this).show(SuggestionDialogFragment.this.getActivity().getSupportFragmentManager(), "CommentDialogFragment");
            }
        });
        ListView listView = (ListView)this.view.findViewById(R.id.uv_list);
        listView.addHeaderView(this.headerView);
        this.displaySuggestion(this.view, this.suggestion);
        this.adapter = this.getListAdapter();
        listView.setAdapter(this.adapter);
        listView.setDivider(null);
        listView.setOnScrollListener((AbsListView.OnScrollListener)new PaginationScrollListener(this.adapter));
        bundle.setView(this.view);
        bundle.setNegativeButton(R.string.uv_close, null);
        Babayaga.track(Babayaga.Event.VIEW_IDEA, this.suggestion.getId());
        return bundle.create();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void suggestionSubscriptionUpdated(Suggestion suggestion) {
        if (this.getActivity() == null) {
            return;
        }
        CheckBox checkBox = (CheckBox)this.headerView.findViewById(R.id.uv_subscribe_checkbox);
        if (this.suggestion.isSubscribed()) {
            Toast.makeText((Context)this.context, (int)R.string.uv_msg_subscribe_success, (int)0).show();
            checkBox.setChecked(true);
        } else {
            Toast.makeText((Context)this.context, (int)R.string.uv_msg_unsubscribe, (int)0).show();
            checkBox.setChecked(false);
        }
        this.displaySuggestion(this.view, this.suggestion);
        if (!(this.getActivity() instanceof ForumActivity)) return;
        ((ForumActivity)this.getActivity()).suggestionUpdated(suggestion);
    }

}

