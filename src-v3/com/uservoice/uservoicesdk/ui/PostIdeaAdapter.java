package com.uservoice.uservoicesdk.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.flow.SigninManager;
import com.uservoice.uservoicesdk.model.Category;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.RestResult;
import java.util.ArrayList;
import java.util.List;

public class PostIdeaAdapter extends InstantAnswersAdapter {
    private static int CATEGORY;
    private static int DESCRIPTION;
    private static int HELP;
    private static int TEXT_HEADING;
    private Spinner categorySelect;
    private EditText descriptionField;

    /* renamed from: com.uservoice.uservoicesdk.ui.PostIdeaAdapter.1 */
    class C12021 extends SigninCallback {

        /* renamed from: com.uservoice.uservoicesdk.ui.PostIdeaAdapter.1.1 */
        class C14561 extends DefaultCallback<Suggestion> {
            C14561(Context x0) {
                super(x0);
            }

            public void onModel(Suggestion model) {
                Babayaga.track(Event.SUBMIT_IDEA);
                Toast.makeText(PostIdeaAdapter.this.context, C0621R.string.uv_msg_idea_created, 0).show();
                PostIdeaAdapter.this.context.finish();
            }

            public void onError(RestResult error) {
                PostIdeaAdapter.this.isPosting = false;
                super.onError(error);
            }
        }

        C12021() {
        }

        public void onSuccess() {
            PostIdeaAdapter.this.isPosting = true;
            Suggestion.createSuggestion(Session.getInstance().getForum(), PostIdeaAdapter.this.categorySelect == null ? null : (Category) PostIdeaAdapter.this.categorySelect.getSelectedItem(), PostIdeaAdapter.this.textField.getText().toString(), PostIdeaAdapter.this.descriptionField.getText().toString(), 1, new C14561(PostIdeaAdapter.this.context));
        }
    }

    static {
        DESCRIPTION = 8;
        CATEGORY = 9;
        HELP = 10;
        TEXT_HEADING = 11;
    }

    public PostIdeaAdapter(FragmentActivity context) {
        super(context);
        this.continueButtonMessage = C0621R.string.uv_post_idea_continue_button;
    }

    public int getViewTypeCount() {
        return super.getViewTypeCount() + 4;
    }

    protected List<Integer> getDetailRows() {
        List<Integer> rows = new ArrayList();
        rows.add(Integer.valueOf(DESCRIPTION));
        if (Session.getInstance().getForum().getCategories().size() > 0) {
            rows.add(Integer.valueOf(CATEGORY));
        }
        rows.add(Integer.valueOf(this.SPACE));
        rows.add(Integer.valueOf(this.EMAIL_FIELD));
        rows.add(Integer.valueOf(this.NAME_FIELD));
        return rows;
    }

    protected List<Integer> getRows() {
        List<Integer> rows = super.getRows();
        rows.add(0, Integer.valueOf(TEXT_HEADING));
        if (this.state == State.DETAILS) {
            rows.add(Integer.valueOf(HELP));
        }
        return rows;
    }

    @SuppressLint({"CutPasteId"})
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int type = getItemViewType(position);
        if (view == null) {
            if (type == DESCRIPTION) {
                view = this.inflater.inflate(C0621R.layout.uv_text_field_item, null);
                ((TextView) view.findViewById(C0621R.id.uv_header_text)).setText(C0621R.string.uv_idea_description_heading);
                EditText field = (EditText) view.findViewById(C0621R.id.uv_text_field);
                restoreEnteredText(this.descriptionField, field, UnsupportedUrlFragment.DISPLAY_NAME);
                this.descriptionField = field;
                this.descriptionField.setHint(C0621R.string.uv_idea_description_hint);
            } else if (type == CATEGORY) {
                view = this.inflater.inflate(C0621R.layout.uv_select_field_item, null);
                TextView title = (TextView) view.findViewById(C0621R.id.uv_header_text);
                this.categorySelect = (Spinner) view.findViewById(C0621R.id.uv_select_field);
                this.categorySelect.setAdapter(new SpinnerAdapter(this.context, Session.getInstance().getForum().getCategories()));
                title.setText(C0621R.string.uv_category);
            } else if (type == HELP) {
                view = this.inflater.inflate(C0621R.layout.uv_idea_help_item, null);
            } else if (type == TEXT_HEADING) {
                view = this.inflater.inflate(C0621R.layout.uv_header_item, null);
                ((TextView) view.findViewById(C0621R.id.uv_header_text)).setText(C0621R.string.uv_idea_text_heading);
            } else {
                view = super.getView(position, convertView, parent);
            }
        }
        if (type == DESCRIPTION || type == CATEGORY || type == HELP || type == TEXT_HEADING) {
            return view;
        }
        if (type != this.TEXT) {
            return super.getView(position, view, parent);
        }
        TextView textView = (TextView) view.findViewById(C0621R.id.uv_text);
        textView.setHint(C0621R.string.uv_idea_text_hint);
        textView.setMinLines(1);
        return view;
    }

    protected void doSubmit() {
        this.isPosting = false;
        SigninManager.signIn(this.context, this.emailField.getText().toString(), this.nameField.getText().toString(), new C12021());
    }

    protected String getSubmitString() {
        return this.context.getString(C0621R.string.uv_submit_idea);
    }
}
