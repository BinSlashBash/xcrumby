package com.uservoice.uservoicesdk.model;

import com.uservoice.uservoicesdk.babayaga.Babayaga;
import com.uservoice.uservoicesdk.rest.Callback;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class Ticket extends BaseModel {

    /* renamed from: com.uservoice.uservoicesdk.model.Ticket.1 */
    static class C11931 extends RestTaskCallback {
        final /* synthetic */ Callback val$callback;

        C11931(Callback x0, Callback callback) {
            this.val$callback = callback;
            super(x0);
        }

        public void onComplete(JSONObject result) throws JSONException {
            this.val$callback.onModel(BaseModel.deserializeObject(result, "ticket", Ticket.class));
        }
    }

    public static void createTicket(String message, Map<String, String> customFields, Callback<Ticket> callback) {
        createTicket(message, null, null, customFields, callback);
    }

    public static void createTicket(String message, String email, String name, Map<String, String> customFields, Callback<Ticket> callback) {
        Map<String, String> params = new HashMap();
        params.put("ticket[message]", message);
        if (email != null) {
            params.put("email", email);
        }
        if (name != null) {
            params.put("display_name", name);
        }
        if (Babayaga.getUvts() != null) {
            params.put("uvts", Babayaga.getUvts());
        }
        for (Entry<String, String> entry : BaseModel.getSession().getExternalIds().entrySet()) {
            Object[] objArr = new Object[]{entry.getKey()};
            params.put(String.format("created_by[external_ids][%s]", objArr), ((Entry) i$.next()).getValue());
        }
        if (BaseModel.getConfig().getCustomFields() != null) {
            for (Entry<String, String> entry2 : BaseModel.getConfig().getCustomFields().entrySet()) {
                objArr = new Object[]{entry2.getKey()};
                params.put(String.format("ticket[custom_field_values][%s]", objArr), ((Entry) i$.next()).getValue());
            }
        }
        if (customFields != null) {
            for (Entry<String, String> entry22 : customFields.entrySet()) {
                objArr = new Object[]{entry22.getKey()};
                params.put(String.format("ticket[custom_field_values][%s]", objArr), ((Entry) i$.next()).getValue());
            }
        }
        BaseModel.doPost(BaseModel.apiPath("/tickets.json", new Object[0]), params, new C11931(callback, callback));
    }
}
