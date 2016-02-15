package com.tapstream.sdk;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

public class Event {
    Map<String, Object> customFields;
    private String encodedName;
    private double firstFiredTime;
    private boolean isTransaction;
    private String name;
    private boolean oneTimeOnly;
    private StringBuilder postData;
    private String productSku;
    private String uid;

    public Event(String str, String str2, int i) {
        this(UnsupportedUrlFragment.DISPLAY_NAME, false);
        this.isTransaction = true;
        this.productSku = str2;
        addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-transaction-id", str, true);
        addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-product-id", str2, true);
        addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-quantity", Integer.valueOf(i), true);
    }

    public Event(String str, String str2, int i, int i2, String str3) {
        this(UnsupportedUrlFragment.DISPLAY_NAME, false);
        this.isTransaction = true;
        this.productSku = str2;
        addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-transaction-id", str, true);
        addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-product-id", str2, true);
        addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-quantity", Integer.valueOf(i), true);
        addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-price", Integer.valueOf(i2), true);
        addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-currency", str3, true);
    }

    public Event(String str, String str2, String str3) throws JSONException {
        this(UnsupportedUrlFragment.DISPLAY_NAME, false);
        this.isTransaction = true;
        JSONObject jSONObject = new JSONObject(str2);
        JSONObject jSONObject2 = new JSONObject(str);
        this.productSku = jSONObject2.getString("productId");
        String string = jSONObject2.getString("orderId");
        try {
            String string2 = jSONObject.getString("price_currency_code");
            int round = (int) Math.round(((double) jSONObject.getInt("price_amount_micros")) / 10000.0d);
            addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-transaction-id", string, true);
            addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-product-id", this.productSku, true);
            addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-quantity", Integer.valueOf(1), true);
            addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-price", Integer.valueOf(round), true);
            addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-currency", string2, true);
        } catch (JSONException e) {
            addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-transaction-id", string, true);
            addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-product-id", this.productSku, true);
            addPair(UnsupportedUrlFragment.DISPLAY_NAME, "purchase-quantity", Integer.valueOf(1), true);
        }
        jSONObject = new JSONObject();
        jSONObject.put("purchase_data", (Object) str);
        jSONObject.put("signature", (Object) str3);
        addPair(UnsupportedUrlFragment.DISPLAY_NAME, "receipt-body", jSONObject.toString(), false);
    }

    public Event(String str, boolean z) {
        this.firstFiredTime = 0.0d;
        this.postData = new StringBuilder();
        this.isTransaction = false;
        this.customFields = new HashMap();
        this.uid = makeUid();
        this.oneTimeOnly = z;
        setName(str);
    }

    private String makeUid() {
        return String.format(Locale.US, "%d:%f", new Object[]{Long.valueOf(System.currentTimeMillis()), Double.valueOf(Math.random())});
    }

    public void addPair(String str, Object obj) {
        this.customFields.put(str, obj);
    }

    protected void addPair(String str, String str2, Object obj, boolean z) {
        String encodeEventPair = Utils.encodeEventPair(str, str2, obj, z);
        if (encodeEventPair != null) {
            this.postData.append("&");
            this.postData.append(encodeEventPair);
        }
    }

    public String getEncodedName() {
        return this.encodedName;
    }

    public String getName() {
        return this.name;
    }

    public String getPostData() {
        return this.postData != null ? this.postData.toString() : UnsupportedUrlFragment.DISPLAY_NAME;
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

    void prepare(Map<String, Object> map) {
        if (this.firstFiredTime == 0.0d) {
            this.firstFiredTime = (double) System.currentTimeMillis();
            for (Entry entry : map.entrySet()) {
                if (!this.customFields.containsKey(entry.getKey())) {
                    this.customFields.put(entry.getKey(), entry.getValue());
                }
            }
            this.postData.append(String.format(Locale.US, "&created-ms=%.0f", new Object[]{Double.valueOf(this.firstFiredTime)}));
            for (Entry entry2 : this.customFields.entrySet()) {
                addPair("custom-", (String) entry2.getKey(), entry2.getValue(), true);
            }
        }
    }

    void setName(String str) {
        this.name = str.toLowerCase().trim().replace(".", "_");
        try {
            this.encodedName = URLEncoder.encode(this.name, Hex.DEFAULT_CHARSET_NAME).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    void setNamePrefix(String str) {
        setName(String.format(Locale.US, "android-%s-purchase-%s", new Object[]{str, this.productSku}));
    }
}
