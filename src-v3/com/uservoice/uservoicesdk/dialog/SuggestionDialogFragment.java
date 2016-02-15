package com.uservoice.uservoicesdk.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.activity.ForumActivity;
import com.uservoice.uservoicesdk.activity.InstantAnswersActivity;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.deflection.Deflection;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.flow.SigninManager;
import com.uservoice.uservoicesdk.image.ImageCache;
import com.uservoice.uservoicesdk.model.Comment;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.PaginatedAdapter;
import com.uservoice.uservoicesdk.ui.PaginationScrollListener;
import com.uservoice.uservoicesdk.ui.Utils;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressLint({"ValidFragment"})
public class SuggestionDialogFragment extends DialogFragmentBugfixed {
    private PaginatedAdapter<Comment> adapter;
    private Context context;
    private String deflectingType;
    private View headerView;
    private Suggestion suggestion;
    private View view;

    /* renamed from: com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment.1 */
    class C06461 implements OnClickListener {

        /* renamed from: com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment.1.2 */
        class C11762 extends SigninCallback {
            final /* synthetic */ DefaultCallback val$callback;

            C11762(DefaultCallback defaultCallback) {
                this.val$callback = defaultCallback;
            }

            public void onSuccess() {
                SuggestionDialogFragment.this.suggestion.subscribe(this.val$callback);
            }
        }

        /* renamed from: com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment.1.1 */
        class C14411 extends DefaultCallback<Suggestion> {
            C14411(Context x0) {
                super(x0);
            }

            public void onModel(Suggestion model) {
                if (SuggestionDialogFragment.this.getActivity() instanceof InstantAnswersActivity) {
                    Deflection.trackDeflection("subscribed", SuggestionDialogFragment.this.deflectingType, model);
                }
                SuggestionDialogFragment.this.suggestionSubscriptionUpdated(model);
            }
        }

        C06461() {
        }

        public void onClick(View v) {
            DefaultCallback<Suggestion> callback = new C14411(SuggestionDialogFragment.this.getActivity());
            if (SuggestionDialogFragment.this.suggestion.isSubscribed()) {
                SuggestionDialogFragment.this.suggestion.unsubscribe(callback);
            } else if (Session.getInstance().getEmail() != null) {
                SigninManager.signinForSubscribe(SuggestionDialogFragment.this.getActivity(), Session.getInstance().getEmail(), new C11762(callback));
            } else {
                new SubscribeDialogFragment(SuggestionDialogFragment.this.suggestion, SuggestionDialogFragment.this, SuggestionDialogFragment.this.deflectingType).show(SuggestionDialogFragment.this.getFragmentManager(), "SubscribeDialogFragment");
            }
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment.2 */
    class C06472 implements OnClickListener {
        C06472() {
        }

        public void onClick(View v) {
            new CommentDialogFragment(SuggestionDialogFragment.this.suggestion, SuggestionDialogFragment.this).show(SuggestionDialogFragment.this.getActivity().getSupportFragmentManager(), "CommentDialogFragment");
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.dialog.SuggestionDialogFragment.3 */
    class C15093 extends PaginatedAdapter<Comment> {
        C15093(Context x0, int x1, List x2) {
            super(x0, x1, x2);
        }

        protected int getTotalNumberOfObjects() {
            return SuggestionDialogFragment.this.suggestion.getNumberOfComments();
        }

        protected void customizeLayout(View view, Comment model) {
            ((TextView) view.findViewById(C0621R.id.uv_text)).setText(model.getText());
            ((TextView) view.findViewById(C0621R.id.uv_name)).setText(model.getUserName());
            ((TextView) view.findViewById(C0621R.id.uv_date)).setText(DateFormat.getDateInstance().format(model.getCreatedAt()));
            ImageCache.getInstance().loadImage(model.getAvatarUrl(), (ImageView) view.findViewById(C0621R.id.uv_avatar));
        }

        public boolean isEnabled(int position) {
            return false;
        }

        protected void loadPage(int page, Callback<List<Comment>> callback) {
            Comment.loadComments(SuggestionDialogFragment.this.suggestion, page, callback);
        }
    }

    public SuggestionDialogFragment(Suggestion suggestion, String deflectingType) {
        this.suggestion = suggestion;
        this.deflectingType = deflectingType;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        this.context = getActivity();
        setStyle(1, getTheme());
        if (!Utils.isDarkTheme(getActivity())) {
            builder.setInverseBackgroundForced(true);
        }
        this.view = getActivity().getLayoutInflater().inflate(C0621R.layout.uv_idea_dialog, null);
        this.headerView = getActivity().getLayoutInflater().inflate(C0621R.layout.uv_idea_dialog_header, null);
        this.headerView.findViewById(C0621R.id.uv_subscribe).setOnClickListener(new C06461());
        this.headerView.findViewById(C0621R.id.uv_post_comment).setOnClickListener(new C06472());
        ListView listView = (ListView) this.view.findViewById(C0621R.id.uv_list);
        listView.addHeaderView(this.headerView);
        displaySuggestion(this.view, this.suggestion);
        this.adapter = getListAdapter();
        listView.setAdapter(this.adapter);
        listView.setDivider(null);
        listView.setOnScrollListener(new PaginationScrollListener(this.adapter));
        builder.setView(this.view);
        builder.setNegativeButton(C0621R.string.uv_close, null);
        Babayaga.track(Event.VIEW_IDEA, this.suggestion.getId());
        return builder.create();
    }

    public void suggestionSubscriptionUpdated(Suggestion model) {
        if (getActivity() != null) {
            CheckBox checkbox = (CheckBox) this.headerView.findViewById(C0621R.id.uv_subscribe_checkbox);
            if (this.suggestion.isSubscribed()) {
                Toast.makeText(this.context, C0621R.string.uv_msg_subscribe_success, 0).show();
                checkbox.setChecked(true);
            } else {
                Toast.makeText(this.context, C0621R.string.uv_msg_unsubscribe, 0).show();
                checkbox.setChecked(false);
            }
            displaySuggestion(this.view, this.suggestion);
            if (getActivity() instanceof ForumActivity) {
                ((ForumActivity) getActivity()).suggestionUpdated(model);
            }
        }
    }

    private PaginatedAdapter<Comment> getListAdapter() {
        return new C15093(getActivity(), C0621R.layout.uv_comment_item, new ArrayList());
    }

    public void commentPosted(Comment comment) {
        this.adapter.add(0, comment);
        this.suggestion.commentPosted(comment);
        displaySuggestion(this.view, this.suggestion);
    }

    private void displaySuggestion(View view, Suggestion suggestion) {
        TextView status = (TextView) view.findViewById(C0621R.id.uv_status);
        TextView responseStatus = (TextView) view.findViewById(C0621R.id.uv_response_status);
        View responseDivider = view.findViewById(C0621R.id.uv_response_divider);
        TextView title = (TextView) view.findViewById(C0621R.id.uv_title);
        if (suggestion.isSubscribed()) {
            ((CheckBox) view.findViewById(C0621R.id.uv_subscribe_checkbox)).setChecked(true);
        }
        if (suggestion.getStatus() == null) {
            status.setVisibility(8);
            responseStatus.setTextColor(-12303292);
            responseDivider.setBackgroundColor(-12303292);
        } else {
            int color = Color.parseColor(suggestion.getStatusColor());
            status.setBackgroundColor(color);
            status.setText(suggestion.getStatus());
            responseStatus.setTextColor(color);
            responseStatus.setText(String.format(getString(C0621R.string.uv_admin_response_format), new Object[]{suggestion.getStatus().toUpperCase(Locale.getDefault())}));
            responseDivider.setBackgroundColor(color);
        }
        title.setText(suggestion.getTitle());
        ((TextView) view.findViewById(C0621R.id.uv_text)).setText(suggestion.getText());
        ((TextView) view.findViewById(C0621R.id.uv_creator)).setText(String.format(view.getContext().getString(C0621R.string.uv_posted_by_format), new Object[]{suggestion.getCreatorName(), DateFormat.getDateInstance().format(suggestion.getCreatedAt())}));
        if (suggestion.getAdminResponseText() == null) {
            view.findViewById(C0621R.id.uv_admin_response).setVisibility(8);
        } else {
            view.findViewById(C0621R.id.uv_admin_response).setVisibility(0);
            ((TextView) view.findViewById(C0621R.id.uv_admin_name)).setText(suggestion.getAdminResponseUserName());
            ((TextView) view.findViewById(C0621R.id.uv_response_date)).setText(DateFormat.getDateInstance().format(suggestion.getAdminResponseCreatedAt()));
            ((TextView) view.findViewById(C0621R.id.uv_response_text)).setText(suggestion.getAdminResponseText());
            ImageCache.getInstance().loadImage(suggestion.getAdminResponseAvatarUrl(), (ImageView) view.findViewById(C0621R.id.uv_admin_avatar));
        }
        ((TextView) view.findViewById(C0621R.id.uv_comment_count)).setText(Utils.getQuantityString(view, C0621R.plurals.uv_comments, suggestion.getNumberOfComments()).toUpperCase(Locale.getDefault()));
        if (Session.getInstance().getClientConfig().shouldDisplaySuggestionsByRank()) {
            ((TextView) view.findViewById(C0621R.id.uv_subscriber_count)).setText(String.format(view.getContext().getResources().getString(C0621R.string.uv_ranked), new Object[]{suggestion.getRankString()}));
            return;
        }
        ((TextView) view.findViewById(C0621R.id.uv_subscriber_count)).setText(String.format(view.getContext().getResources().getQuantityString(C0621R.plurals.uv_number_of_subscribers_format, suggestion.getNumberOfSubscribers()), new Object[]{Utils.getQuantityString(view, C0621R.plurals.uv_subscribers, suggestion.getNumberOfSubscribers())}));
    }
}
