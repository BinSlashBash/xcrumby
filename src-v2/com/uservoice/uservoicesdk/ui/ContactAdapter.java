/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.EditText
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.uservoice.uservoicesdk.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.R;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.model.CustomField;
import com.uservoice.uservoicesdk.model.Ticket;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.ui.DefaultCallback;
import com.uservoice.uservoicesdk.ui.InstantAnswersAdapter;
import com.uservoice.uservoicesdk.ui.SpinnerAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContactAdapter
extends InstantAnswersAdapter {
    private int CUSTOM_PREDEFINED_FIELD = 9;
    private int CUSTOM_TEXT_FIELD = 8;
    private Map<String, String> customFieldValues = new HashMap<String, String>(Session.getInstance().getConfig().getCustomFields());

    public ContactAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.continueButtonMessage = R.string.uv_contact_continue_button;
    }

    private boolean validateCustomFields() {
        for (CustomField customField : Session.getInstance().getClientConfig().getCustomFields()) {
            String string2;
            if (!customField.isRequired() || (string2 = this.customFieldValues.get(customField.getName())) != null && string2.length() != 0) continue;
            return false;
        }
        return true;
    }

    @Override
    protected void doSubmit() {
        if (this.validateCustomFields()) {
            Ticket.createTicket(this.textField.getText().toString(), this.emailField.getText().toString(), this.nameField.getText().toString(), this.customFieldValues, new DefaultCallback<Ticket>((Context)this.context){

                @Override
                public void onError(RestResult restResult) {
                    ContactAdapter.this.isPosting = false;
                    super.onError(restResult);
                }

                @Override
                public void onModel(Ticket ticket) {
                    Babayaga.track(Babayaga.Event.SUBMIT_TICKET);
                    Toast.makeText((Context)ContactAdapter.this.context, (int)R.string.uv_msg_ticket_created, (int)0).show();
                    ContactAdapter.this.context.finish();
                }
            });
            return;
        }
        this.isPosting = false;
        Toast.makeText((Context)this.context, (int)R.string.uv_msg_custom_fields_validation, (int)0).show();
    }

    @Override
    protected List<Integer> getDetailRows() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.addAll(Arrays.asList(this.EMAIL_FIELD, this.NAME_FIELD, this.SPACE));
        Iterator<CustomField> iterator = Session.getInstance().getClientConfig().getCustomFields().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isPredefined()) {
                arrayList.add(this.CUSTOM_PREDEFINED_FIELD);
                continue;
            }
            arrayList.add(this.CUSTOM_TEXT_FIELD);
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Object getItem(int n2) {
        int n3 = this.getItemViewType(n2);
        if (n3 != this.CUSTOM_PREDEFINED_FIELD && n3 != this.CUSTOM_TEXT_FIELD) {
            return super.getItem(n2);
        }
        List<Integer> list = this.getRows();
        n3 = list.contains(this.CUSTOM_PREDEFINED_FIELD) ? list.indexOf(this.CUSTOM_PREDEFINED_FIELD) : list.size();
        int n4 = list.contains(this.CUSTOM_TEXT_FIELD) ? list.indexOf(this.CUSTOM_TEXT_FIELD) : list.size();
        n3 = Math.min(n3, n4);
        return Session.getInstance().getClientConfig().getCustomFields().get(n2 - n3);
    }

    @Override
    protected String getSubmitString() {
        return this.context.getString(R.string.uv_send_message);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public View getView(int n2, View object, ViewGroup object2) {
        Object object3 = object;
        int n3 = this.getItemViewType(n2);
        Object object4 = object3;
        if (object3 == null) {
            if (n3 == this.CUSTOM_TEXT_FIELD) {
                object4 = this.inflater.inflate(R.layout.uv_text_field_item, null);
            } else {
                if (n3 != this.CUSTOM_PREDEFINED_FIELD) return super.getView(n2, (View)object, (ViewGroup)object2);
                object4 = this.inflater.inflate(R.layout.uv_select_field_item, null);
            }
        }
        if (n3 == this.CUSTOM_TEXT_FIELD) {
            object = (TextView)object4.findViewById(R.id.uv_header_text);
            object2 = (EditText)object4.findViewById(R.id.uv_text_field);
            object3 = (CustomField)this.getItem(n2);
            String string2 = this.customFieldValues.get(object3.getName());
            object.setText((CharSequence)object3.getName());
            object2.setHint(R.string.uv_value);
            object2.setInputType(64);
            object2.setText((CharSequence)string2);
            object2.setOnFocusChangeListener(new View.OnFocusChangeListener((CustomField)object3, (EditText)object2){
                final /* synthetic */ CustomField val$customField;
                final /* synthetic */ EditText val$field;

                public void onFocusChange(View view, boolean bl2) {
                    if (!bl2) {
                        ContactAdapter.this.customFieldValues.put(this.val$customField.getName(), this.val$field.getText().toString());
                    }
                }
            });
            return object4;
        }
        if (n3 != this.CUSTOM_PREDEFINED_FIELD) return super.getView(n2, (View)object4, (ViewGroup)object2);
        object = (CustomField)this.getItem(n2);
        object2 = this.customFieldValues.get(object.getName());
        ((TextView)object4.findViewById(R.id.uv_header_text)).setText((CharSequence)object.getName());
        object3 = (Spinner)object4.findViewById(R.id.uv_select_field);
        object3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((CustomField)object){
            final /* synthetic */ CustomField val$customField;

            /*
             * Enabled aggressive block sorting
             */
            public void onItemSelected(AdapterView<?> object, View object2, int n2, long l2) {
                object2 = ContactAdapter.this.customFieldValues;
                String string2 = this.val$customField.getName();
                object = n2 == 0 ? null : this.val$customField.getPredefinedValues().get(n2 - 1);
                object2.put(string2, object);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        object3.setAdapter(new SpinnerAdapter<String>(this.context, object.getPredefinedValues()));
        if (object2 == null) return object4;
        if (!object.getPredefinedValues().contains(object2)) return object4;
        object3.setSelection(object.getPredefinedValues().indexOf(object2) + 1);
        return object4;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 2;
    }

}

