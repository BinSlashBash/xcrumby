/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class FormElement
extends Element {
    private final Elements elements = new Elements();

    public FormElement(Tag tag, String string2, Attributes attributes) {
        super(tag, string2, attributes);
    }

    public FormElement addElement(Element element) {
        this.elements.add(element);
        return this;
    }

    public Elements elements() {
        return this.elements;
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    public List<Connection.KeyVal> formData() {
        ArrayList<Connection.KeyVal> arrayList = new ArrayList<Connection.KeyVal>();
        Iterator<Element> iterator = this.elements.iterator();
        while (iterator.hasNext()) {
            String string2;
            Object object = iterator.next();
            if (!object.tag().isFormSubmittable() || (string2 = object.attr("name")).length() == 0) continue;
            if ("select".equals(object.tagName())) {
                object = object.select("option[selected]").iterator();
                while (object.hasNext()) {
                    arrayList.add(HttpConnection.KeyVal.create(string2, ((Element)object.next()).val()));
                }
                continue;
            }
            arrayList.add(HttpConnection.KeyVal.create(string2, object.val()));
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Connection submit() {
        Connection.Method method;
        String string2 = this.hasAttr("action") ? this.absUrl("action") : this.baseUri();
        Validate.notEmpty(string2, "Could not determine a form action URL for submit. Ensure you set a base URI when parsing.");
        if (this.attr("method").toUpperCase().equals("POST")) {
            method = Connection.Method.POST;
            return Jsoup.connect(string2).data(this.formData()).method(method);
        }
        method = Connection.Method.GET;
        return Jsoup.connect(string2).data(this.formData()).method(method);
    }
}

