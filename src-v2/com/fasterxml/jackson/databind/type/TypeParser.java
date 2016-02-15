/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TypeParser
implements Serializable {
    private static final long serialVersionUID = 1;
    protected final TypeFactory _factory;

    public TypeParser(TypeFactory typeFactory) {
        this._factory = typeFactory;
    }

    protected IllegalArgumentException _problem(MyTokenizer myTokenizer, String string2) {
        return new IllegalArgumentException("Failed to parse type '" + myTokenizer.getAllInput() + "' (remaining: '" + myTokenizer.getRemainingInput() + "'): " + string2);
    }

    protected Class<?> findClass(String string2, MyTokenizer myTokenizer) {
        try {
            Class class_ = ClassUtil.findClass(string2);
            return class_;
        }
        catch (Exception var3_4) {
            if (var3_4 instanceof RuntimeException) {
                throw (RuntimeException)var3_4;
            }
            throw this._problem(myTokenizer, "Can not locate class '" + string2 + "', problem: " + var3_4.getMessage());
        }
    }

    public JavaType parse(String object) throws IllegalArgumentException {
        object = new MyTokenizer(object.trim());
        JavaType javaType = this.parseType((MyTokenizer)object);
        if (object.hasMoreTokens()) {
            throw this._problem((MyTokenizer)object, "Unexpected tokens after complete type");
        }
        return javaType;
    }

    protected JavaType parseType(MyTokenizer myTokenizer) throws IllegalArgumentException {
        if (!myTokenizer.hasMoreTokens()) {
            throw this._problem(myTokenizer, "Unexpected end-of-string");
        }
        Class class_ = this.findClass(myTokenizer.nextToken(), myTokenizer);
        if (myTokenizer.hasMoreTokens()) {
            String string2 = myTokenizer.nextToken();
            if ("<".equals(string2)) {
                return this._factory._fromParameterizedClass(class_, this.parseTypes(myTokenizer));
            }
            myTokenizer.pushBack(string2);
        }
        return this._factory._fromClass(class_, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected List<JavaType> parseTypes(MyTokenizer myTokenizer) throws IllegalArgumentException {
        String string2;
        ArrayList<JavaType> arrayList = new ArrayList<JavaType>();
        do {
            if (!myTokenizer.hasMoreTokens()) throw this._problem(myTokenizer, "Unexpected end-of-string");
            arrayList.add(this.parseType(myTokenizer));
            if (!myTokenizer.hasMoreTokens()) {
                throw this._problem(myTokenizer, "Unexpected end-of-string");
            }
            string2 = myTokenizer.nextToken();
            if (!">".equals(string2)) continue;
            return arrayList;
        } while (",".equals(string2));
        throw this._problem(myTokenizer, "Unexpected token '" + string2 + "', expected ',' or '>')");
    }

    static final class MyTokenizer
    extends StringTokenizer {
        protected int _index;
        protected final String _input;
        protected String _pushbackToken;

        public MyTokenizer(String string2) {
            super(string2, "<,>", true);
            this._input = string2;
        }

        public String getAllInput() {
            return this._input;
        }

        public String getRemainingInput() {
            return this._input.substring(this._index);
        }

        public String getUsedInput() {
            return this._input.substring(0, this._index);
        }

        @Override
        public boolean hasMoreTokens() {
            if (this._pushbackToken != null || super.hasMoreTokens()) {
                return true;
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public String nextToken() {
            String string2;
            if (this._pushbackToken != null) {
                string2 = this._pushbackToken;
                this._pushbackToken = null;
            } else {
                string2 = super.nextToken();
            }
            this._index += string2.length();
            return string2;
        }

        public void pushBack(String string2) {
            this._pushbackToken = string2;
            this._index -= string2.length();
        }
    }

}

