package com.uservoice.uservoicesdk.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.deflection.Deflection;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.Suggestion;
import java.util.ArrayList;
import java.util.List;

public abstract class InstantAnswersAdapter extends BaseAdapter implements OnHierarchyChangeListener, OnItemClickListener {
    protected int BUTTON;
    protected int EMAIL_FIELD;
    protected int HEADING;
    protected int INSTANT_ANSWER;
    protected int LOADING;
    protected int NAME_FIELD;
    protected int SPACE;
    protected int TEXT;
    protected FragmentActivity context;
    protected int continueButtonMessage;
    protected String deflectingType;
    protected EditText emailField;
    protected LayoutInflater inflater;
    protected List<BaseModel> instantAnswers;
    protected boolean isPosting;
    protected EditText nameField;
    protected State state;
    protected EditText textField;

    /* renamed from: com.uservoice.uservoicesdk.ui.InstantAnswersAdapter.1 */
    class C06541 implements OnClickListener {
        C06541() {
        }

        public void onClick(View v) {
            InstantAnswersAdapter.this.onButtonTapped();
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.ui.InstantAnswersAdapter.2 */
    class C06552 implements TextWatcher {
        C06552() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (InstantAnswersAdapter.this.state != State.INIT) {
                InstantAnswersAdapter.this.state = State.INIT;
                InstantAnswersAdapter.this.notifyDataSetChanged();
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.ui.InstantAnswersAdapter.4 */
    static /* synthetic */ class C06564 {
        static final /* synthetic */ int[] f57x9b6ee749;

        static {
            f57x9b6ee749 = new int[State.values().length];
            try {
                f57x9b6ee749[State.INIT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f57x9b6ee749[State.INIT_LOADING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f57x9b6ee749[State.INSTANT_ANSWERS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f57x9b6ee749[State.DETAILS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    protected enum State {
        INIT,
        INIT_LOADING,
        INSTANT_ANSWERS,
        DETAILS
    }

    /* renamed from: com.uservoice.uservoicesdk.ui.InstantAnswersAdapter.3 */
    class C14503 extends DefaultCallback<List<BaseModel>> {
        C14503(Context x0) {
            super(x0);
        }

        public void onModel(List<BaseModel> model) {
            Deflection.trackSearchDeflection(model.subList(0, Math.min(model.size(), 3)), InstantAnswersAdapter.this.deflectingType);
            InstantAnswersAdapter.this.instantAnswers = model;
            if (InstantAnswersAdapter.this.instantAnswers.isEmpty()) {
                InstantAnswersAdapter.this.state = State.DETAILS;
            } else {
                InstantAnswersAdapter.this.state = State.INSTANT_ANSWERS;
            }
            InstantAnswersAdapter.this.notifyDataSetChanged();
        }
    }

    protected abstract void doSubmit();

    protected abstract List<Integer> getDetailRows();

    protected abstract String getSubmitString();

    public InstantAnswersAdapter(FragmentActivity context) {
        this.TEXT = 0;
        this.BUTTON = 1;
        this.HEADING = 2;
        this.LOADING = 3;
        this.INSTANT_ANSWER = 4;
        this.EMAIL_FIELD = 5;
        this.NAME_FIELD = 6;
        this.SPACE = 7;
        this.state = State.INIT;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public int getViewTypeCount() {
        return 8;
    }

    protected List<Integer> getRows() {
        List<Integer> rows = new ArrayList();
        rows.add(Integer.valueOf(this.TEXT));
        if (!(this.state == State.INIT || this.state == State.INIT_LOADING || this.instantAnswers.isEmpty())) {
            rows.add(Integer.valueOf(this.SPACE));
            rows.add(Integer.valueOf(this.HEADING));
        }
        if (this.state == State.INSTANT_ANSWERS || this.state == State.DETAILS) {
            if (this.instantAnswers.size() > 0) {
                rows.add(Integer.valueOf(this.INSTANT_ANSWER));
            }
            if (this.instantAnswers.size() > 1) {
                rows.add(Integer.valueOf(this.INSTANT_ANSWER));
            }
            if (this.instantAnswers.size() > 2) {
                rows.add(Integer.valueOf(this.INSTANT_ANSWER));
            }
        }
        if (this.state == State.DETAILS) {
            rows.add(Integer.valueOf(this.SPACE));
            rows.addAll(getDetailRows());
        }
        rows.add(Integer.valueOf(this.BUTTON));
        return rows;
    }

    protected boolean isLoading() {
        return Session.getInstance().getClientConfig() == null;
    }

    public int getCount() {
        return isLoading() ? 1 : getRows().size();
    }

    public int getItemViewType(int position) {
        return isLoading() ? this.LOADING : ((Integer) getRows().get(position)).intValue();
    }

    public void notHelpful() {
        if (this.state == State.INSTANT_ANSWERS) {
            this.state = State.DETAILS;
            notifyDataSetChanged();
        }
    }

    public boolean isEnabled(int position) {
        return getItemViewType(position) == this.INSTANT_ANSWER;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (getItemViewType(position) == this.INSTANT_ANSWER) {
            Deflection.trackDeflection("show", this.deflectingType, (BaseModel) getItem(position));
            Utils.showModel(this.context, (BaseModel) getItem(position), this.deflectingType);
        }
    }

    public void onChildViewAdded(View parent, View child) {
        if (this.state == State.DETAILS && this.emailField != null) {
            this.emailField.requestFocus();
        } else if (this.textField != null) {
            this.textField.requestFocus();
        }
    }

    public void onChildViewRemoved(View parent, View child) {
        onChildViewAdded(null, null);
    }

    @SuppressLint({"CutPasteId"})
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int type = getItemViewType(position);
        if (view == null) {
            if (type == this.LOADING) {
                view = this.inflater.inflate(C0621R.layout.uv_loading_item, null);
            } else if (type == this.BUTTON) {
                view = this.inflater.inflate(C0621R.layout.uv_contact_button_item, null);
                ((Button) view.findViewById(C0621R.id.uv_contact_button)).setOnClickListener(new C06541());
            } else if (type == this.HEADING) {
                view = this.inflater.inflate(C0621R.layout.uv_header_item, null);
            } else if (type == this.INSTANT_ANSWER) {
                view = this.inflater.inflate(C0621R.layout.uv_instant_answer_item, null);
            } else if (type == this.SPACE) {
                view = new LinearLayout(this.context);
                view.setPadding(0, 30, 0, 0);
            } else if (type == this.TEXT) {
                view = this.inflater.inflate(C0621R.layout.uv_contact_text_item, null);
                EditText field = (EditText) view.findViewById(C0621R.id.uv_text);
                restoreEnteredText(this.textField, field, UnsupportedUrlFragment.DISPLAY_NAME);
                this.textField = field;
                this.textField.addTextChangedListener(new C06552());
            } else if (type == this.EMAIL_FIELD || type == this.NAME_FIELD) {
                view = this.inflater.inflate(C0621R.layout.uv_text_field_item, null);
            }
        }
        if (type == this.BUTTON) {
            Button button = (Button) view.findViewById(C0621R.id.uv_contact_button);
            button.setEnabled(this.state != State.INIT_LOADING);
            switch (C06564.f57x9b6ee749[this.state.ordinal()]) {
                case Std.STD_FILE /*1*/:
                    button.setText(C0621R.string.uv_next);
                    break;
                case Std.STD_URL /*2*/:
                    button.setText(C0621R.string.uv_loading);
                    break;
                case Std.STD_URI /*3*/:
                    button.setText(this.continueButtonMessage);
                    break;
                case Std.STD_CLASS /*4*/:
                    button.setText(getSubmitString());
                    break;
            }
        } else if (type == this.INSTANT_ANSWER) {
            Utils.displayInstantAnswer(view, (BaseModel) getItem(position));
            view.findViewById(C0621R.id.uv_divider).setVisibility(getRows().lastIndexOf(Integer.valueOf(this.INSTANT_ANSWER)) == position ? 8 : 0);
        } else if (type == this.EMAIL_FIELD || type == this.NAME_FIELD) {
            TextView title = (TextView) view.findViewById(C0621R.id.uv_header_text);
            field = (EditText) view.findViewById(C0621R.id.uv_text_field);
            if (type == this.EMAIL_FIELD) {
                title.setText(C0621R.string.uv_your_email_address);
                restoreEnteredText(this.emailField, field, Session.getInstance().getEmail());
                this.emailField = field;
                field.setHint(C0621R.string.uv_email_address_hint);
                field.setInputType(32);
            } else if (type == this.NAME_FIELD) {
                title.setText(C0621R.string.uv_your_name);
                restoreEnteredText(this.nameField, field, Session.getInstance().getName());
                this.nameField = field;
                field.setHint(C0621R.string.uv_name_hint);
                field.setInputType(96);
            }
        } else if (type == this.HEADING) {
            TextView textView = (TextView) view.findViewById(C0621R.id.uv_header_text);
            boolean hasArticles = false;
            boolean hasIdeas = false;
            for (BaseModel model : this.instantAnswers) {
                if (model instanceof Article) {
                    hasArticles = true;
                }
                if (model instanceof Suggestion) {
                    hasIdeas = true;
                }
            }
            int i = hasArticles ? hasIdeas ? C0621R.string.uv_matching_articles_and_ideas : C0621R.string.uv_matching_articles : C0621R.string.uv_matching_ideas;
            textView.setText(i);
        }
        return view;
    }

    protected void restoreEnteredText(EditText previous, EditText current, String defaultText) {
        if (previous != null) {
            String text = previous.getText().toString();
            if (!TextUtils.isEmpty(text)) {
                defaultText = text;
            }
            current.setText(defaultText);
            current.setSelection(current.getText().length());
            return;
        }
        current.setText(defaultText);
        current.setSelection(current.getText().length());
    }

    public Object getItem(int position) {
        if (getItemViewType(position) == this.INSTANT_ANSWER) {
            return this.instantAnswers.get(position - getRows().indexOf(Integer.valueOf(this.INSTANT_ANSWER)));
        }
        return null;
    }

    protected void onButtonTapped() {
        if (this.state == State.INIT) {
            String query = this.textField.getText().toString().trim();
            if (query.length() != 0) {
                this.state = State.INIT_LOADING;
                notifyDataSetChanged();
                Deflection.setSearchText(query);
                ((InputMethodManager) this.context.getSystemService("input_method")).toggleSoftInput(1, 0);
                Article.loadInstantAnswers(query, new C14503(this.context));
            }
        } else if (this.state == State.INSTANT_ANSWERS) {
            this.state = State.DETAILS;
            notifyDataSetChanged();
        } else if (this.state == State.DETAILS) {
            String name = this.nameField.getText().toString();
            String email = this.emailField.getText().toString();
            if (email.length() == 0) {
                Builder builder = new Builder(this.context);
                builder.setTitle(C0621R.string.uv_error);
                builder.setMessage(C0621R.string.uv_msg_user_identity_validation);
                builder.create().show();
            } else if (!this.isPosting) {
                this.isPosting = true;
                Session.getInstance().persistIdentity(name, email);
                doSubmit();
            }
        }
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public boolean hasText() {
        return (this.textField == null || this.textField.getText().toString().length() == 0) ? false : true;
    }
}
