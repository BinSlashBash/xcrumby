/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.AsyncTask
 *  android.text.Html
 */
package com.uservoice.uservoicesdk.model;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.Html;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.model.Article;
import com.uservoice.uservoicesdk.model.ClientConfig;
import com.uservoice.uservoicesdk.model.Suggestion;
import com.uservoice.uservoicesdk.rest.RestMethod;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseModel {
    protected int id;

    protected static /* varargs */ String apiPath(String string2, Object ... arrobject) {
        return "/api/v1" + String.format(string2, arrobject);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected static List<BaseModel> deserializeHeterogenousList(JSONObject var0, String var1_1) throws JSONException {
        if (!var0.has((String)var1_1)) {
            return null;
        }
        var3_2 = var0.getJSONArray((String)var1_1);
        var1_1 = new ArrayList<E>(var3_2.length());
        var2_3 = 0;
        do {
            var0 = var1_1;
            if (var2_3 >= var3_2.length()) return var0;
            var4_4 = var3_2.getJSONObject(var2_3);
            var0 = var4_4.getString("type");
            if (!var0.equals("suggestion")) ** GOTO lbl14
            var0 = new Suggestion();
            ** GOTO lbl16
lbl14: // 1 sources:
            if (var0.equals("article")) {
                var0 = new Article();
lbl16: // 2 sources:
                var0.load(var4_4);
                var1_1.add(var0);
            }
            ++var2_3;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static <T extends BaseModel> List<T> deserializeList(JSONObject object, String list, Class<T> class_) throws JSONException {
        int n2;
        JSONArray jSONArray;
        if (!object.has((String)((Object)list))) {
            return null;
        }
        try {
            jSONArray = object.getJSONArray((String)((Object)list));
            list = new ArrayList(jSONArray.length());
            n2 = 0;
        }
        catch (IllegalArgumentException var0_1) {
            throw new JSONException("Reflection failed trying to call load on " + class_ + " " + var0_1.getMessage());
        }
        catch (IllegalAccessException var0_2) {
            throw new JSONException("Reflection failed trying to call load on " + class_ + " " + var0_2.getMessage());
        }
        catch (InstantiationException var0_3) {
            throw new JSONException("Reflection failed trying to instantiate " + class_ + " " + var0_3.getMessage());
        }
        do {
            object = list;
            if (n2 >= jSONArray.length()) return object;
            object = (BaseModel)class_.newInstance();
            object.load(jSONArray.getJSONObject(n2));
            list.add(object);
            ++n2;
            continue;
            break;
        } while (true);
    }

    protected static <T extends BaseModel> T deserializeObject(JSONObject jSONObject, String object, Class<T> class_) throws JSONException {
        if (!jSONObject.has((String)object)) {
            return null;
        }
        try {
            object = jSONObject.getJSONObject((String)object);
            BaseModel baseModel = (BaseModel)class_.newInstance();
            baseModel.load((JSONObject)object);
            object = (BaseModel)class_.cast(baseModel);
        }
        catch (JSONException var1_5) {
            throw new JSONException("JSON deserialization failure for " + class_ + " " + var1_5.getMessage() + " JSON: " + jSONObject.toString());
        }
        catch (IllegalArgumentException var0_1) {
            throw new JSONException("Reflection failed trying to call load on " + class_ + " " + var0_1.getMessage());
        }
        catch (IllegalAccessException var0_2) {
            throw new JSONException("Reflection failed trying to call load on " + class_ + " " + var0_2.getMessage());
        }
        catch (InstantiationException var0_3) {
            throw new JSONException("Reflection failed trying to instantiate " + class_ + " " + var0_3.getMessage());
        }
        return (T)object;
    }

    protected static RestTask doDelete(String string2, RestTaskCallback restTaskCallback) {
        return BaseModel.doDelete(string2, null, restTaskCallback);
    }

    protected static RestTask doDelete(String object, Map<String, String> map, RestTaskCallback restTaskCallback) {
        object = new RestTask(RestMethod.DELETE, (String)object, map, restTaskCallback);
        object.execute((Object[])new String[0]);
        return object;
    }

    protected static RestTask doGet(String string2, RestTaskCallback restTaskCallback) {
        return BaseModel.doGet(string2, null, restTaskCallback);
    }

    protected static RestTask doGet(String object, Map<String, String> map, RestTaskCallback restTaskCallback) {
        object = new RestTask(RestMethod.GET, (String)object, map, restTaskCallback);
        object.execute((Object[])new String[0]);
        return object;
    }

    protected static RestTask doPost(String string2, RestTaskCallback restTaskCallback) {
        return BaseModel.doPost(string2, null, restTaskCallback);
    }

    protected static RestTask doPost(String object, Map<String, String> map, RestTaskCallback restTaskCallback) {
        object = new RestTask(RestMethod.POST, (String)object, map, restTaskCallback);
        object.execute((Object[])new String[0]);
        return object;
    }

    protected static RestTask doPut(String string2, RestTaskCallback restTaskCallback) {
        return BaseModel.doPut(string2, null, restTaskCallback);
    }

    protected static RestTask doPut(String object, Map<String, String> map, RestTaskCallback restTaskCallback) {
        object = new RestTask(RestMethod.PUT, (String)object, map, restTaskCallback);
        object.execute((Object[])new String[0]);
        return object;
    }

    protected static ClientConfig getClientConfig() {
        return BaseModel.getSession().getClientConfig();
    }

    protected static Config getConfig() {
        return BaseModel.getSession().getConfig();
    }

    protected static Session getSession() {
        return Session.getInstance();
    }

    public static <T extends BaseModel> T load(SharedPreferences object, String string2, String string3, Class<T> class_) {
        try {
            object = BaseModel.deserializeObject(new JSONObject(object.getString(string2, "{}")), string3, class_);
        }
        catch (JSONException var0_1) {
            return null;
        }
        return (T)object;
    }

    @SuppressLint(value={"SimpleDateFormat"})
    protected Date getDate(JSONObject object, String object2) throws JSONException {
        object = this.getString((JSONObject)object, (String)object2);
        object2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
        try {
            object2 = object2.parse((String)object);
            return object2;
        }
        catch (ParseException var2_3) {
            throw new JSONException("Could not parse date: " + (String)object);
        }
    }

    protected String getHtml(JSONObject jSONObject, String string2) throws JSONException {
        if (jSONObject.isNull(string2)) {
            return null;
        }
        return jSONObject.getString(string2);
    }

    public int getId() {
        return this.id;
    }

    protected String getString(JSONObject jSONObject, String string2) throws JSONException {
        if (jSONObject.isNull(string2)) {
            return null;
        }
        return Html.fromHtml((String)jSONObject.getString(string2)).toString().trim();
    }

    public void load(JSONObject jSONObject) throws JSONException {
        this.id = jSONObject.getInt("id");
    }

    public boolean persist(SharedPreferences sharedPreferences, String string2, String string3) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            this.save(jSONObject);
            jSONObject2.put(string3, jSONObject);
        }
        catch (JSONException var1_2) {
            return false;
        }
        sharedPreferences = sharedPreferences.edit();
        sharedPreferences.putString(string2, jSONObject2.toString());
        return sharedPreferences.commit();
    }

    public void save(JSONObject jSONObject) throws JSONException {
        jSONObject.put("id", this.id);
    }
}

