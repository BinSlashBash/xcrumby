package com.uservoice.uservoicesdk.model;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Html;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
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

    public void load(JSONObject object) throws JSONException {
        this.id = object.getInt("id");
    }

    public int getId() {
        return this.id;
    }

    public boolean persist(SharedPreferences prefs, String prefsKey, String rootKey) {
        Object object = new JSONObject();
        JSONObject container = new JSONObject();
        try {
            save(object);
            container.put(rootKey, object);
            Editor edit = prefs.edit();
            edit.putString(prefsKey, container.toString());
            return edit.commit();
        } catch (JSONException e) {
            return false;
        }
    }

    public static <T extends BaseModel> T load(SharedPreferences prefs, String prefsKey, String rootKey, Class<T> modelClass) {
        try {
            return deserializeObject(new JSONObject(prefs.getString(prefsKey, "{}")), rootKey, modelClass);
        } catch (JSONException e) {
            return null;
        }
    }

    public void save(JSONObject object) throws JSONException {
        object.put("id", this.id);
    }

    protected String getString(JSONObject object, String key) throws JSONException {
        return object.isNull(key) ? null : Html.fromHtml(object.getString(key)).toString().trim();
    }

    protected String getHtml(JSONObject object, String key) throws JSONException {
        return object.isNull(key) ? null : object.getString(key);
    }

    @SuppressLint({"SimpleDateFormat"})
    protected Date getDate(JSONObject object, String key) throws JSONException {
        String dateString = getString(object, key);
        try {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z").parse(dateString);
        } catch (ParseException e) {
            throw new JSONException("Could not parse date: " + dateString);
        }
    }

    protected static Session getSession() {
        return Session.getInstance();
    }

    protected static Config getConfig() {
        return getSession().getConfig();
    }

    protected static ClientConfig getClientConfig() {
        return getSession().getClientConfig();
    }

    protected static String apiPath(String path, Object... args) {
        return "/api/v1" + String.format(path, args);
    }

    protected static RestTask doGet(String path, RestTaskCallback callback) {
        return doGet(path, null, callback);
    }

    protected static RestTask doPost(String path, RestTaskCallback callback) {
        return doPost(path, null, callback);
    }

    protected static RestTask doDelete(String path, RestTaskCallback callback) {
        return doDelete(path, null, callback);
    }

    protected static RestTask doPut(String path, RestTaskCallback callback) {
        return doPut(path, null, callback);
    }

    protected static RestTask doGet(String path, Map<String, String> params, RestTaskCallback callback) {
        RestTask task = new RestTask(RestMethod.GET, path, (Map) params, callback);
        task.execute(new String[0]);
        return task;
    }

    protected static RestTask doPost(String path, Map<String, String> params, RestTaskCallback callback) {
        RestTask task = new RestTask(RestMethod.POST, path, (Map) params, callback);
        task.execute(new String[0]);
        return task;
    }

    protected static RestTask doDelete(String path, Map<String, String> params, RestTaskCallback callback) {
        RestTask task = new RestTask(RestMethod.DELETE, path, (Map) params, callback);
        task.execute(new String[0]);
        return task;
    }

    protected static RestTask doPut(String path, Map<String, String> params, RestTaskCallback callback) {
        RestTask task = new RestTask(RestMethod.PUT, path, (Map) params, callback);
        task.execute(new String[0]);
        return task;
    }

    protected static <T extends BaseModel> List<T> deserializeList(JSONObject object, String rootKey, Class<T> modelClass) throws JSONException {
        if (!object.has(rootKey)) {
            return null;
        }
        try {
            JSONArray array = object.getJSONArray(rootKey);
            List<T> list = new ArrayList(array.length());
            for (int i = 0; i < array.length(); i++) {
                BaseModel model = (BaseModel) modelClass.newInstance();
                model.load(array.getJSONObject(i));
                list.add(model);
            }
            return list;
        } catch (IllegalArgumentException e) {
            throw new JSONException("Reflection failed trying to call load on " + modelClass + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e.getMessage());
        } catch (IllegalAccessException e2) {
            throw new JSONException("Reflection failed trying to call load on " + modelClass + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e2.getMessage());
        } catch (InstantiationException e3) {
            throw new JSONException("Reflection failed trying to instantiate " + modelClass + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e3.getMessage());
        }
    }

    protected static <T extends BaseModel> T deserializeObject(JSONObject object, String rootKey, Class<T> modelClass) throws JSONException {
        if (!object.has(rootKey)) {
            return null;
        }
        try {
            BaseModel model = (BaseModel) modelClass.newInstance();
            model.load(object.getJSONObject(rootKey));
            return (BaseModel) modelClass.cast(model);
        } catch (JSONException e) {
            throw new JSONException("JSON deserialization failure for " + modelClass + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e.getMessage() + " JSON: " + object.toString());
        } catch (IllegalArgumentException e2) {
            throw new JSONException("Reflection failed trying to call load on " + modelClass + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e2.getMessage());
        } catch (IllegalAccessException e3) {
            throw new JSONException("Reflection failed trying to call load on " + modelClass + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e3.getMessage());
        } catch (InstantiationException e4) {
            throw new JSONException("Reflection failed trying to instantiate " + modelClass + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e4.getMessage());
        }
    }

    protected static List<BaseModel> deserializeHeterogenousList(JSONObject object, String rootKey) throws JSONException {
        if (!object.has(rootKey)) {
            return null;
        }
        JSONArray array = object.getJSONArray(rootKey);
        List<BaseModel> list = new ArrayList(array.length());
        for (int i = 0; i < array.length(); i++) {
            BaseModel model;
            JSONObject o = array.getJSONObject(i);
            String type = o.getString("type");
            if (type.equals("suggestion")) {
                model = new Suggestion();
            } else if (type.equals("article")) {
                model = new Article();
            } else {
            }
            model.load(o);
            list.add(model);
        }
        return list;
    }
}
