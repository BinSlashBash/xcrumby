/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 *  org.apache.http.NameValuePair
 *  org.apache.http.client.utils.URLEncodedUtils
 *  org.apache.http.message.BasicNameValuePair
 */
package com.crumby.lib;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.form.FormField;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class SearchForm {
    private Map<String, FormField> form = new HashMap<String, FormField>();

    public SearchForm(ViewGroup viewGroup) {
        this.parseForm(viewGroup);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void parseForm(ViewGroup viewGroup) {
        int n2 = viewGroup.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            View view = viewGroup.getChildAt(n3);
            if (view instanceof ViewGroup) {
                this.parseForm((ViewGroup)view);
            } else if (view instanceof FormField) {
                this.form.put(((FormField)view).getArgumentName(), (FormField)view);
            }
            ++n3;
        }
    }

    private List<NameValuePair> retrieveFormData() {
        ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, FormField> entry : this.form.entrySet()) {
            arrayList.add((NameValuePair)new BasicNameValuePair(entry.getKey(), entry.getValue().getFieldValue()));
        }
        return arrayList;
    }

    public String encodeFormData() {
        return URLEncodedUtils.format(this.retrieveFormData(), (String)"UTF-8");
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setEditorActionListener(TextView.OnEditorActionListener onEditorActionListener) {
        if (this.form != null) {
            for (FormField formField : this.form.values()) {
                if (!(formField instanceof EditText)) continue;
                ((EditText)formField).setOnEditorActionListener(onEditorActionListener);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String setFormData(URL uRL) {
        Uri uri = Uri.parse((String)uRL.toString());
        if (!uri.isHierarchical()) {
            return "";
        }
        Iterator iterator = uri.getQueryParameterNames().iterator();
        String string2 = "";
        do {
            String string3 = string2;
            if (!iterator.hasNext()) return string3;
            Object object = (String)iterator.next();
            string3 = GalleryProducer.getQueryParameter(uri, uRL.toString(), (String)object);
            if ((object = this.form.get(object)) == null || object.getFieldValue().equals(string3)) continue;
            object.setFieldValue(string3);
            if (object.getNiceName() == null) continue;
            string2 = string2 + object.getNiceName() + " " + string3 + ", ";
        } while (true);
    }

    public void setFormData(String string2) {
        try {
            this.setFormData(new URL(string2));
            return;
        }
        catch (MalformedURLException var1_2) {
            return;
        }
    }
}

