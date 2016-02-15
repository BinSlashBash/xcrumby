/*
 * Decompiled with CFR 0_110.
 */
package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.model.BaseModel;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class Ticket
extends BaseModel {
    public static void createTicket(String iterator, String entry22, String string2, Map<String, String> map, final Callback<Ticket> callback) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("ticket[message]", (String)((Object)iterator));
        if (entry22 != null) {
            hashMap.put("email", (String)((Object)entry22));
        }
        if (string2 != null) {
            hashMap.put("display_name", string2);
        }
        if (Babayaga.getUvts() != null) {
            hashMap.put("uvts", Babayaga.getUvts());
        }
        for (Map.Entry<String, String> entry22 : Ticket.getSession().getExternalIds().entrySet()) {
            hashMap.put(String.format("created_by[external_ids][%s]", entry22.getKey()), entry22.getValue());
        }
        if (Ticket.getConfig().getCustomFields() != null) {
            for (Map.Entry<String, String> entry22 : Ticket.getConfig().getCustomFields().entrySet()) {
                hashMap.put(String.format("ticket[custom_field_values][%s]", entry22.getKey()), entry22.getValue());
            }
        }
        if (map != null) {
            for (Map.Entry<String, String> entry22 : map.entrySet()) {
                hashMap.put(String.format("ticket[custom_field_values][%s]", entry22.getKey()), entry22.getValue());
            }
        }
        Ticket.doPost(Ticket.apiPath("/tickets.json", new Object[0]), hashMap, new RestTaskCallback(callback){

            @Override
            public void onComplete(JSONObject jSONObject) throws JSONException {
                callback.onModel(BaseModel.deserializeObject(jSONObject, "ticket", Ticket.class));
            }
        });
    }

    public static void createTicket(String string2, Map<String, String> map, Callback<Ticket> callback) {
        Ticket.createTicket(string2, null, null, map, callback);
    }

}

