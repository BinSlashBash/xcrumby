package com.crumby.lib;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.form.FormField;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class SearchForm {
    private Map<String, FormField> form;

    public SearchForm(ViewGroup gallerySearchForm) {
        this.form = new HashMap();
        parseForm(gallerySearchForm);
    }

    private void parseForm(ViewGroup viewGroup) {
        int childSize = viewGroup.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                parseForm((ViewGroup) view);
            } else if (view instanceof FormField) {
                this.form.put(((FormField) view).getArgumentName(), (FormField) view);
            }
        }
    }

    public String encodeFormData() {
        return URLEncodedUtils.format(retrieveFormData(), Hex.DEFAULT_CHARSET_NAME);
    }

    private List<NameValuePair> retrieveFormData() {
        List<NameValuePair> formData = new ArrayList();
        for (Entry<String, FormField> entry : this.form.entrySet()) {
            formData.add(new BasicNameValuePair((String) entry.getKey(), ((FormField) entry.getValue()).getFieldValue()));
        }
        return formData;
    }

    public String setFormData(URL formData) {
        Uri uri = Uri.parse(formData.toString());
        if (!uri.isHierarchical()) {
            return UnsupportedUrlFragment.DISPLAY_NAME;
        }
        String searchMessage = UnsupportedUrlFragment.DISPLAY_NAME;
        for (String parameter : uri.getQueryParameterNames()) {
            String value = GalleryProducer.getQueryParameter(uri, formData.toString(), parameter);
            FormField formField = (FormField) this.form.get(parameter);
            if (!(formField == null || formField.getFieldValue().equals(value))) {
                formField.setFieldValue(value);
                if (formField.getNiceName() != null) {
                    searchMessage = searchMessage + formField.getNiceName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + value + ", ";
                }
            }
        }
        return searchMessage;
    }

    public void setFormData(String query) {
        try {
            setFormData(new URL(query));
        } catch (MalformedURLException e) {
        }
    }

    public void setEditorActionListener(OnEditorActionListener onEditorActionListener) {
        if (this.form != null) {
            for (FormField field : this.form.values()) {
                if (field instanceof EditText) {
                    ((EditText) field).setOnEditorActionListener(onEditorActionListener);
                }
            }
        }
    }
}
