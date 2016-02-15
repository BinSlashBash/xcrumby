/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.ak;
import com.google.android.gms.analytics.o;
import com.google.android.gms.analytics.u;
import java.util.HashMap;
import java.util.Map;

public class HitBuilders {

    @Deprecated
    public static class AppViewBuilder
    extends HitBuilder<AppViewBuilder> {
        public AppViewBuilder() {
            u.cy().a(u.a.uS);
            this.set("&t", "appview");
        }
    }

    public static class EventBuilder
    extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            u.cy().a(u.a.uG);
            this.set("&t", "event");
        }

        public EventBuilder(String string2, String string3) {
            this();
            this.setCategory(string2);
            this.setAction(string3);
        }

        public EventBuilder setAction(String string2) {
            this.set("&ea", string2);
            return this;
        }

        public EventBuilder setCategory(String string2) {
            this.set("&ec", string2);
            return this;
        }

        public EventBuilder setLabel(String string2) {
            this.set("&el", string2);
            return this;
        }

        public EventBuilder setValue(long l2) {
            this.set("&ev", Long.toString(l2));
            return this;
        }
    }

    public static class ExceptionBuilder
    extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            u.cy().a(u.a.up);
            this.set("&t", "exception");
        }

        public ExceptionBuilder setDescription(String string2) {
            this.set("&exd", string2);
            return this;
        }

        public ExceptionBuilder setFatal(boolean bl2) {
            this.set("&exf", ak.u(bl2));
            return this;
        }
    }

    protected static class HitBuilder<T extends HitBuilder> {
        private Map<String, String> vl = new HashMap<String, String>();

        protected HitBuilder() {
        }

        public Map<String, String> build() {
            return this.vl;
        }

        protected String get(String string2) {
            return this.vl.get(string2);
        }

        public final T set(String string2, String string3) {
            u.cy().a(u.a.tI);
            if (string2 != null) {
                this.vl.put(string2, string3);
                return (T)this;
            }
            aa.z(" HitBuilder.set() called with a null paramName.");
            return (T)this;
        }

        public final T setAll(Map<String, String> map) {
            u.cy().a(u.a.tJ);
            if (map == null) {
                return (T)this;
            }
            this.vl.putAll(new HashMap<String, String>(map));
            return (T)this;
        }

        public T setCampaignParamsFromUrl(String object) {
            u.cy().a(u.a.tL);
            object = ak.O((String)object);
            if (TextUtils.isEmpty((CharSequence)object)) {
                return (T)this;
            }
            object = ak.N((String)object);
            this.set("&cc", (String)object.get("utm_content"));
            this.set("&cm", (String)object.get("utm_medium"));
            this.set("&cn", (String)object.get("utm_campaign"));
            this.set("&cs", (String)object.get("utm_source"));
            this.set("&ck", (String)object.get("utm_term"));
            this.set("&ci", (String)object.get("utm_id"));
            this.set("&gclid", (String)object.get("gclid"));
            this.set("&dclid", (String)object.get("dclid"));
            this.set("&gmob_t", (String)object.get("gmob_t"));
            return (T)this;
        }

        public T setCustomDimension(int n2, String string2) {
            this.set(o.q(n2), string2);
            return (T)this;
        }

        public T setCustomMetric(int n2, float f2) {
            this.set(o.r(n2), Float.toString(f2));
            return (T)this;
        }

        protected T setHitType(String string2) {
            this.set("&t", string2);
            return (T)this;
        }

        public T setNewSession() {
            this.set("&sc", "start");
            return (T)this;
        }

        public T setNonInteraction(boolean bl2) {
            this.set("&ni", ak.u(bl2));
            return (T)this;
        }
    }

    public static class ItemBuilder
    extends HitBuilder<ItemBuilder> {
        public ItemBuilder() {
            u.cy().a(u.a.uH);
            this.set("&t", "item");
        }

        public ItemBuilder setCategory(String string2) {
            this.set("&iv", string2);
            return this;
        }

        public ItemBuilder setCurrencyCode(String string2) {
            this.set("&cu", string2);
            return this;
        }

        public ItemBuilder setName(String string2) {
            this.set("&in", string2);
            return this;
        }

        public ItemBuilder setPrice(double d2) {
            this.set("&ip", Double.toString(d2));
            return this;
        }

        public ItemBuilder setQuantity(long l2) {
            this.set("&iq", Long.toString(l2));
            return this;
        }

        public ItemBuilder setSku(String string2) {
            this.set("&ic", string2);
            return this;
        }

        public ItemBuilder setTransactionId(String string2) {
            this.set("&ti", string2);
            return this;
        }
    }

    public static class ScreenViewBuilder
    extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            u.cy().a(u.a.uS);
            this.set("&t", "appview");
        }
    }

    public static class SocialBuilder
    extends HitBuilder<SocialBuilder> {
        public SocialBuilder() {
            u.cy().a(u.a.us);
            this.set("&t", "social");
        }

        public SocialBuilder setAction(String string2) {
            this.set("&sa", string2);
            return this;
        }

        public SocialBuilder setNetwork(String string2) {
            this.set("&sn", string2);
            return this;
        }

        public SocialBuilder setTarget(String string2) {
            this.set("&st", string2);
            return this;
        }
    }

    public static class TimingBuilder
    extends HitBuilder<TimingBuilder> {
        public TimingBuilder() {
            u.cy().a(u.a.ur);
            this.set("&t", "timing");
        }

        public TimingBuilder(String string2, String string3, long l2) {
            this();
            this.setVariable(string3);
            this.setValue(l2);
            this.setCategory(string2);
        }

        public TimingBuilder setCategory(String string2) {
            this.set("&utc", string2);
            return this;
        }

        public TimingBuilder setLabel(String string2) {
            this.set("&utl", string2);
            return this;
        }

        public TimingBuilder setValue(long l2) {
            this.set("&utt", Long.toString(l2));
            return this;
        }

        public TimingBuilder setVariable(String string2) {
            this.set("&utv", string2);
            return this;
        }
    }

    public static class TransactionBuilder
    extends HitBuilder<TransactionBuilder> {
        public TransactionBuilder() {
            u.cy().a(u.a.uo);
            this.set("&t", "transaction");
        }

        public TransactionBuilder setAffiliation(String string2) {
            this.set("&ta", string2);
            return this;
        }

        public TransactionBuilder setCurrencyCode(String string2) {
            this.set("&cu", string2);
            return this;
        }

        public TransactionBuilder setRevenue(double d2) {
            this.set("&tr", Double.toString(d2));
            return this;
        }

        public TransactionBuilder setShipping(double d2) {
            this.set("&ts", Double.toString(d2));
            return this;
        }

        public TransactionBuilder setTax(double d2) {
            this.set("&tt", Double.toString(d2));
            return this;
        }

        public TransactionBuilder setTransactionId(String string2) {
            this.set("&ti", string2);
            return this;
        }
    }

}

