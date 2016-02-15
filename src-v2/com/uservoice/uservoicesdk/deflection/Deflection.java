/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.util.Log
 */
package com.uservoice.uservoicesdk.deflection;

import android.os.AsyncTask;
import android.util.Log;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestMethod;
import com.uservoice.uservoicesdk.rest.RestResult;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Deflection {
    private static int interactionIdentifier = Integer.parseInt(String.valueOf(new Date().getTime()).substring(4));
    private static String searchText;

    private static Map<String, String> deflectionParams() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (Babayaga.getUvts() != null) {
            hashMap.put("uvts", Babayaga.getUvts());
        }
        hashMap.put("channel", "android");
        hashMap.put("search_term", searchText);
        hashMap.put("interaction_identifier", String.valueOf(interactionIdentifier));
        hashMap.put("subdomain_id", String.valueOf(Session.getInstance().getClientConfig().getSubdomainId()));
        return hashMap;
    }

    private static RestTaskCallback getCallback() {
        return new RestTaskCallback(null){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                Log.d((String)"UV", (String)jSONObject.toString());
            }

            @Override
            public void onError(RestResult restResult) {
                Log.e((String)"UV", (String)("Failed sending deflection: " + restResult.getMessage()));
            }
        };
    }

    public static void setSearchText(String string2) {
        if (string2.equals(searchText)) {
            return;
        }
        searchText = string2;
        ++interactionIdentifier;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void trackDeflection(String string2, String string3, BaseModel baseModel) {
        Map<String, String> map = Deflection.deflectionParams();
        map.put("kind", string2);
        map.put("deflecting_type", string3);
        map.put("deflector_id", String.valueOf(baseModel.getId()));
        string2 = baseModel instanceof Article ? "Faq" : "Suggestion";
        map.put("deflector_type", string2);
        new RestTask(RestMethod.GET, "/clients/omnibox/deflections/upsert.json", map, Deflection.getCallback()).execute((Object[])new String[0]);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void trackSearchDeflection(List<BaseModel> iterator, String string2) {
        Map<String, String> map = Deflection.deflectionParams();
        map.put("kind", "list");
        map.put("deflecting_type", string2);
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        iterator = iterator.iterator();
        do {
            int n5;
            int n6;
            if (!iterator.hasNext()) {
                map.put("faq_results", String.valueOf(n2));
                map.put("suggestion_results", String.valueOf(n3));
                new RestTask(RestMethod.GET, "/clients/omnibox/deflections/list_view.json", map, Deflection.getCallback()).execute((Object[])new String[0]);
                return;
            }
            BaseModel baseModel = (BaseModel)iterator.next();
            string2 = "results[" + String.valueOf(n4) + "]";
            map.put(string2 + "[position]", String.valueOf(n4));
            map.put(string2 + "[deflector_id]", String.valueOf(baseModel.getId()));
            if (baseModel instanceof Suggestion) {
                n6 = n3 + 1;
                baseModel = (Suggestion)baseModel;
                map.put(string2 + "[weight]", String.valueOf(baseModel.getWeight()));
                map.put(string2 + "[deflector_type]", "Suggestion");
                n5 = n2;
            } else {
                n5 = n2;
                n6 = n3;
                if (baseModel instanceof Article) {
                    n5 = n2 + 1;
                    baseModel = (Article)baseModel;
                    map.put(string2 + "[weight]", String.valueOf(baseModel.getWeight()));
                    map.put(string2 + "[deflector_type]", "Faq");
                    n6 = n3;
                }
            }
            ++n4;
            n2 = n5;
            n3 = n6;
        } while (true);
    }

}

