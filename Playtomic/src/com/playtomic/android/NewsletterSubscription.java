package com.playtomic.android;
import org.json.JSONObject;
import org.json.JSONException;

public class NewsletterSubscription extends JSONObject {

    public void set(String name, Object value) {
        try {
            put(name, value);
        } catch(JSONException err) {

        }
    }

    public void setEmail(String email) {
        set("email", email);
    }

    public void setFirstName(String firstname) {
        set("firstname", firstname);
    }

    public void setLastName(String lastname) {
        set("lastname", lastname);
    }

    public void setField(String name, Object value) {
        if(!has("fields")) {
            set("fields", new JSONObject());
        }

        try {
            optJSONObject("fields").put(name, value);
        } catch(JSONException err) {

        }
    }
}
