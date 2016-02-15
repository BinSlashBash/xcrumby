/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.content.Context
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.EditText
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.uservoice.uservoicesdk.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.flow.SigninCallback;
import com.uservoice.uservoicesdk.flow.SigninManager;
import com.uservoice.uservoicesdk.model.Category;
import com.uservoice.uservoicesdk.model.Forum;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;
import com.uservoice.uservoicesdk.ui.SpinnerAdapter;
import java.util.ArrayList;
import java.util.List;

public class PostIdeaAdapter
extends InstantAnswersAdapter {
    private static int CATEGORY;
    private static int DESCRIPTION;
    private static int HELP;
    private static int TEXT_HEADING;
    private Spinner categorySelect;
    private EditText descriptionField;

    static {
        DESCRIPTION = 8;
        CATEGORY = 9;
        HELP = 10;
        TEXT_HEADING = 11;
    }

    public PostIdeaAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.continueButtonMessage = R.string.uv_post_idea_continue_button;
    }

    @Override
    protected void doSubmit() {
        this.isPosting = false;
        SigninManager.signIn(this.context, this.emailField.getText().toString(), this.nameField.getText().toString(), new SigninCallback(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onSuccess() {
                PostIdeaAdapter.this.isPosting = true;
                Category category = PostIdeaAdapter.this.categorySelect == null ? null : (Category)PostIdeaAdapter.this.categorySelect.getSelectedItem();
                Suggestion.createSuggestion(Session.getInstance().getForum(), category, PostIdeaAdapter.this.textField.getText().toString(), PostIdeaAdapter.this.descriptionField.getText().toString(), 1, new DefaultCallback<Suggestion>((Context)PostIdeaAdapter.this.context){

                    @Override
                    public void onError(RestResult restResult) {
                        PostIdeaAdapter.this.isPosting = false;
                        super.onError(restResult);
                    }

                    @Override
                    public void onModel(Suggestion suggestion) {
                        Babayaga.track(Babayaga.Event.SUBMIT_IDEA);
                        Toast.makeText((Context)PostIdeaAdapter.this.context, (int)R.string.uv_msg_idea_created, (int)0).show();
                        PostIdeaAdapter.this.context.finish();
                    }
                });
            }

        });
    }

    @Override
    protected List<Integer> getDetailRows() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(DESCRIPTION);
        if (Session.getInstance().getForum().getCategories().size() > 0) {
            arrayList.add(CATEGORY);
        }
        arrayList.add(this.SPACE);
        arrayList.add(this.EMAIL_FIELD);
        arrayList.add(this.NAME_FIELD);
        return arrayList;
    }

    @Override
    protected List<Integer> getRows() {
        List<Integer> list = super.getRows();
        list.add(0, TEXT_HEADING);
        if (this.state == InstantAnswersAdapter.State.DETAILS) {
            list.add(HELP);
        }
        return list;
    }

    @Override
    protected String getSubmitString() {
        return this.context.getString(R.string.uv_submit_idea);
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"CutPasteId"})
    @Override
    public View getView(int n2, View view, ViewGroup viewGroup) {
        View view2 = view;
        int n3 = this.getItemViewType(n2);
        View view3 = view2;
        if (view2 == null) {
            if (n3 == DESCRIPTION) {
                view3 = this.inflater.inflate(R.layout.uv_text_field_item, null);
                ((TextView)view3.findViewById(R.id.uv_header_text)).setText(R.string.uv_idea_description_heading);
                view = (EditText)view3.findViewById(R.id.uv_text_field);
                this.restoreEnteredText(this.descriptionField, (EditText)view, "");
                this.descriptionField = view;
                this.descriptionField.setHint(R.string.uv_idea_description_hint);
            } else if (n3 == CATEGORY) {
                view3 = this.inflater.inflate(R.layout.uv_select_field_item, null);
                view = (TextView)view3.findViewById(R.id.uv_header_text);
                this.categorySelect = (Spinner)view3.findViewById(R.id.uv_select_field);
                this.categorySelect.setAdapter(new SpinnerAdapter<Category>(this.context, Session.getInstance().getForum().getCategories()));
                view.setText(R.string.uv_category);
            } else if (n3 == HELP) {
                view3 = this.inflater.inflate(R.layout.uv_idea_help_item, null);
            } else if (n3 == TEXT_HEADING) {
                view3 = this.inflater.inflate(R.layout.uv_header_item, null);
                ((TextView)view3.findViewById(R.id.uv_header_text)).setText(R.string.uv_idea_text_heading);
            } else {
                view3 = super.getView(n2, view, viewGroup);
            }
        }
        if (n3 == DESCRIPTION || n3 == CATEGORY || n3 == HELP || n3 == TEXT_HEADING) {
            return view3;
        }
        if (n3 == this.TEXT) {
            view = (TextView)view3.findViewById(R.id.uv_text);
            view.setHint(R.string.uv_idea_text_hint);
            view.setMinLines(1);
            return view3;
        }
        return super.getView(n2, view3, viewGroup);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 4;
    }

}

