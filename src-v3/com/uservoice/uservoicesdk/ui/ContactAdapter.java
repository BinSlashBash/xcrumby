package com.uservoice.uservoicesdk.ui;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.uservoice.uservoicesdk.C0621R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.babayaga.Babayaga.Event;
import com.uservoice.uservoicesdk.model.CustomField;
import com.uservoice.uservoicesdk.model.Ticket;
import com.uservoice.uservoicesdk.rest.RestResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactAdapter extends InstantAnswersAdapter {
    private int CUSTOM_PREDEFINED_FIELD;
    private int CUSTOM_TEXT_FIELD;
    private Map<String, String> customFieldValues;

    /* renamed from: com.uservoice.uservoicesdk.ui.ContactAdapter.1 */
    class C06521 implements OnFocusChangeListener {
        final /* synthetic */ CustomField val$customField;
        final /* synthetic */ EditText val$field;

        C06521(CustomField customField, EditText editText) {
            this.val$customField = customField;
            this.val$field = editText;
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                ContactAdapter.this.customFieldValues.put(this.val$customField.getName(), this.val$field.getText().toString());
            }
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.ui.ContactAdapter.2 */
    class C06532 implements OnItemSelectedListener {
        final /* synthetic */ CustomField val$customField;

        C06532(CustomField customField) {
            this.val$customField = customField;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            Object obj;
            Map access$000 = ContactAdapter.this.customFieldValues;
            String name = this.val$customField.getName();
            if (position == 0) {
                obj = null;
            } else {
                String str = (String) this.val$customField.getPredefinedValues().get(position - 1);
            }
            access$000.put(name, obj);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.uservoice.uservoicesdk.ui.ContactAdapter.3 */
    class C14493 extends DefaultCallback<Ticket> {
        C14493(Context x0) {
            super(x0);
        }

        public void onModel(Ticket model) {
            Babayaga.track(Event.SUBMIT_TICKET);
            Toast.makeText(ContactAdapter.this.context, C0621R.string.uv_msg_ticket_created, 0).show();
            ContactAdapter.this.context.finish();
        }

        public void onError(RestResult error) {
            ContactAdapter.this.isPosting = false;
            super.onError(error);
        }
    }

    public ContactAdapter(FragmentActivity context) {
        super(context);
        this.CUSTOM_TEXT_FIELD = 8;
        this.CUSTOM_PREDEFINED_FIELD = 9;
        this.customFieldValues = new HashMap(Session.getInstance().getConfig().getCustomFields());
        this.continueButtonMessage = C0621R.string.uv_contact_continue_button;
    }

    public int getViewTypeCount() {
        return super.getViewTypeCount() + 2;
    }

    protected List<Integer> getDetailRows() {
        List<Integer> rows = new ArrayList();
        rows.addAll(Arrays.asList(new Integer[]{Integer.valueOf(this.EMAIL_FIELD), Integer.valueOf(this.NAME_FIELD), Integer.valueOf(this.SPACE)}));
        for (CustomField customField : Session.getInstance().getClientConfig().getCustomFields()) {
            if (customField.isPredefined()) {
                rows.add(Integer.valueOf(this.CUSTOM_PREDEFINED_FIELD));
            } else {
                rows.add(Integer.valueOf(this.CUSTOM_TEXT_FIELD));
            }
        }
        return rows;
    }

    public Object getItem(int position) {
        int type = getItemViewType(position);
        if (type != this.CUSTOM_PREDEFINED_FIELD && type != this.CUSTOM_TEXT_FIELD) {
            return super.getItem(position);
        }
        List<Integer> rows = getRows();
        return Session.getInstance().getClientConfig().getCustomFields().get(position - Math.min(rows.contains(Integer.valueOf(this.CUSTOM_PREDEFINED_FIELD)) ? rows.indexOf(Integer.valueOf(this.CUSTOM_PREDEFINED_FIELD)) : rows.size(), rows.contains(Integer.valueOf(this.CUSTOM_TEXT_FIELD)) ? rows.indexOf(Integer.valueOf(this.CUSTOM_TEXT_FIELD)) : rows.size()));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int type = getItemViewType(position);
        if (view == null) {
            if (type == this.CUSTOM_TEXT_FIELD) {
                view = this.inflater.inflate(C0621R.layout.uv_text_field_item, null);
            } else if (type != this.CUSTOM_PREDEFINED_FIELD) {
                return super.getView(position, convertView, parent);
            } else {
                view = this.inflater.inflate(C0621R.layout.uv_select_field_item, null);
            }
        }
        CustomField customField;
        String value;
        if (type == this.CUSTOM_TEXT_FIELD) {
            EditText field = (EditText) view.findViewById(C0621R.id.uv_text_field);
            customField = (CustomField) getItem(position);
            value = (String) this.customFieldValues.get(customField.getName());
            ((TextView) view.findViewById(C0621R.id.uv_header_text)).setText(customField.getName());
            field.setHint(C0621R.string.uv_value);
            field.setInputType(64);
            field.setText(value);
            field.setOnFocusChangeListener(new C06521(customField, field));
        } else if (type != this.CUSTOM_PREDEFINED_FIELD) {
            return super.getView(position, view, parent);
        } else {
            customField = (CustomField) getItem(position);
            value = (String) this.customFieldValues.get(customField.getName());
            ((TextView) view.findViewById(C0621R.id.uv_header_text)).setText(customField.getName());
            Spinner field2 = (Spinner) view.findViewById(C0621R.id.uv_select_field);
            field2.setOnItemSelectedListener(new C06532(customField));
            field2.setAdapter(new SpinnerAdapter(this.context, customField.getPredefinedValues()));
            if (value != null && customField.getPredefinedValues().contains(value)) {
                field2.setSelection(customField.getPredefinedValues().indexOf(value) + 1);
            }
        }
        return view;
    }

    private boolean validateCustomFields() {
        for (CustomField field : Session.getInstance().getClientConfig().getCustomFields()) {
            if (field.isRequired()) {
                String string = (String) this.customFieldValues.get(field.getName());
                if (string == null || string.length() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void doSubmit() {
        if (validateCustomFields()) {
            Ticket.createTicket(this.textField.getText().toString(), this.emailField.getText().toString(), this.nameField.getText().toString(), this.customFieldValues, new C14493(this.context));
            return;
        }
        this.isPosting = false;
        Toast.makeText(this.context, C0621R.string.uv_msg_custom_fields_validation, 0).show();
    }

    protected String getSubmitString() {
        return this.context.getString(C0621R.string.uv_send_message);
    }
}
