/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

import com.tapstream.sdk.Utils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class Event {
    Map<String, Object> customFields = new HashMap<String, Object>();
    private String encodedName;
    private double firstFiredTime = 0.0;
    private boolean isTransaction = false;
    private String name;
    private boolean oneTimeOnly;
    private StringBuilder postData = new StringBuilder();
    private String productSku;
    private String uid;

    public Event(String string2, String string3, int n2) {
        this("", false);
        this.isTransaction = true;
        this.productSku = string3;
        this.addPair("", "purchase-transaction-id", string2, true);
        this.addPair("", "purchase-product-id", string3, true);
        this.addPair("", "purchase-quantity", n2, true);
    }

    public Event(String string2, String string3, int n2, int n3, String string4) {
        this("", false);
        this.isTransaction = true;
        this.productSku = string3;
        this.addPair("", "purchase-transaction-id", string2, true);
        this.addPair("", "purchase-product-id", string3, true);
        this.addPair("", "purchase-quantity", n2, true);
        this.addPair("", "purchase-price", n3, true);
        this.addPair("", "purchase-currency", string4, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Event(String string2, String object, String string3) throws JSONException {
        this("", false);
        this.isTransaction = true;
        object = new JSONObject((String)object);
        Object object2 = new JSONObject(string2);
        this.productSku = object2.getString("productId");
        object2 = object2.getString("orderId");
        try {
            String string4 = object.getString("price_currency_code");
            int n2 = (int)Math.round((double)object.getInt("price_amount_micros") / 10000.0);
            this.addPair("", "purchase-transaction-id", object2, true);
            this.addPair("", "purchase-product-id", this.productSku, true);
            this.addPair("", "purchase-quantity", 1, true);
            this.addPair("", "purchase-price", n2, true);
            this.addPair("", "purchase-currency", string4, true);
        }
        catch (JSONException var2_3) {
            this.addPair("", "purchase-transaction-id", object2, true);
            this.addPair("", "purchase-product-id", this.productSku, true);
            this.addPair("", "purchase-quantity", 1, true);
        }
        object = new JSONObject();
        object.put("purchase_data", string2);
        object.put("signature", string3);
        this.addPair("", "receipt-body", object.toString(), false);
    }

    public Event(String string2, boolean bl2) {
        this.uid = this.makeUid();
        this.oneTimeOnly = bl2;
        this.setName(string2);
    }

    private String makeUid() {
        return String.format(Locale.US, "%d:%f", System.currentTimeMillis(), Math.random());
    }

    public void addPair(String string2, Object object) {
        this.customFields.put(string2, object);
    }

    protected void addPair(String string2, String string3, Object object, boolean bl2) {
        if ((string2 = Utils.encodeEventPair(string2, string3, object, bl2)) != null) {
            this.postData.append("&");
            this.postData.append(string2);
        }
    }

    public String getEncodedName() {
        return this.encodedName;
    }

    public String getName() {
        return this.name;
    }

    public String getPostData() {
        if (this.postData != null) {
            return this.postData.toString();
        }
        return "";
    }

    public String getUid() {
        return this.uid;
    }

    public boolean isOneTimeOnly() {
        return this.oneTimeOnly;
    }

    boolean isTransaction() {
        return this.isTransaction;
    }

    void prepare(Map<String, Object> iterator) {
        if (this.firstFiredTime == 0.0) {
            this.firstFiredTime = System.currentTimeMillis();
            for (Map.Entry<String, Object> entry2 : iterator.entrySet()) {
                if (this.customFields.containsKey(entry2.getKey())) continue;
                this.customFields.put((String)entry2.getKey(), entry2.getValue());
            }
            this.postData.append(String.format(Locale.US, "&created-ms=%.0f", this.firstFiredTime));
            for (Map.Entry<String, Object> entry2 : this.customFields.entrySet()) {
                this.addPair("custom-", entry2.getKey(), entry2.getValue(), true);
            }
        }
    }

    void setName(String string2) {
        this.name = string2.toLowerCase().trim().replace(".", "_");
        try {
            this.encodedName = URLEncoder.encode(this.name, "UTF-8").replace("+", "%20");
            return;
        }
        catch (UnsupportedEncodingException var1_2) {
            var1_2.printStackTrace();
            return;
        }
    }

    void setNamePrefix(String string2) {
        this.setName(String.format(Locale.US, "android-%s-purchase-%s", string2, this.productSku));
    }
}

