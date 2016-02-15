/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.kt;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ku {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void a(String string2, Object object, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        Class class_;
        Class class_2;
        int n2;
        String string3;
        Field[] arrfield;
        int n3;
        int n4;
        int n5;
        if (object == null) {
            return;
        }
        if (object instanceof kt) {
            n3 = stringBuffer.length();
            if (string2 != null) {
                stringBuffer2.append(stringBuffer).append(ku.cg(string2)).append(" <\n");
                stringBuffer.append("  ");
            }
            class_ = object.getClass();
            arrfield = class_.getFields();
            n4 = arrfield.length;
        } else {
            string2 = ku.cg(string2);
            stringBuffer2.append(stringBuffer).append(string2).append(": ");
            if (object instanceof String) {
                string2 = ku.ch((String)object);
                stringBuffer2.append("\"").append(string2).append("\"");
            } else if (object instanceof byte[]) {
                ku.a((byte[])object, stringBuffer2);
            } else {
                stringBuffer2.append(object);
            }
            stringBuffer2.append("\n");
            return;
        }
        for (n5 = 0; n5 < n4; ++n5) {
            Field field = arrfield[n5];
            n2 = field.getModifiers();
            string3 = field.getName();
            if ((n2 & 1) != 1 || (n2 & 8) == 8 || string3.startsWith("_") || string3.endsWith("_")) continue;
            class_2 = field.getType();
            Object object2 = field.get(object);
            if (class_2.isArray()) {
                if (class_2.getComponentType() == Byte.TYPE) {
                    ku.a(string3, object2, stringBuffer, stringBuffer2);
                    continue;
                }
                n2 = object2 == null ? 0 : Array.getLength(object2);
                for (int i2 = 0; i2 < n2; ++i2) {
                    ku.a(string3, Array.get(object2, i2), stringBuffer, stringBuffer2);
                }
                continue;
            }
            ku.a(string3, object2, stringBuffer, stringBuffer2);
        }
        arrfield = class_.getMethods();
        n2 = arrfield.length;
        n5 = 0;
        do {
            block19 : {
                if (n5 >= n2) {
                    if (string2 == null) return;
                    stringBuffer.setLength(n3);
                    stringBuffer2.append(stringBuffer).append(">\n");
                    return;
                }
                string3 = arrfield[n5].getName();
                if (string3.startsWith("set")) {
                    string3 = string3.substring(3);
                    try {
                        class_2 = class_.getMethod("has" + string3, new Class[0]);
                        if (!((Boolean)class_2.invoke(object, new Object[0])).booleanValue()) break block19;
                    }
                    catch (NoSuchMethodException var11_13) {}
                    try {
                        class_2 = class_.getMethod("get" + string3, new Class[0]);
                    }
                    catch (NoSuchMethodException var11_12) {
                        break block19;
                    }
                    ku.a(string3, class_2.invoke(object, new Object[0]), stringBuffer, stringBuffer2);
                }
            }
            ++n5;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void a(byte[] arrby, StringBuffer stringBuffer) {
        if (arrby == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append('\"');
        int n2 = 0;
        do {
            if (n2 >= arrby.length) {
                stringBuffer.append('\"');
                return;
            }
            char c2 = arrby[n2];
            if (c2 == '\\' || c2 == '\"') {
                stringBuffer.append('\\').append(c2);
            } else if (c2 >= ' ' && c2 < '') {
                stringBuffer.append(c2);
            } else {
                stringBuffer.append(String.format("\\%03o", c2));
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String av(String string2) {
        int n2 = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n2);
        int n3 = 0;
        while (n3 < n2) {
            char c2 = string2.charAt(n3);
            if (c2 >= ' ' && c2 <= '~' && c2 != '\"' && c2 != '\'') {
                stringBuilder.append(c2);
            } else {
                stringBuilder.append(String.format("\\u%04x", c2));
            }
            ++n3;
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String cg(String string2) {
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        while (n2 < string2.length()) {
            char c2 = string2.charAt(n2);
            if (n2 == 0) {
                stringBuffer.append(Character.toLowerCase(c2));
            } else if (Character.isUpperCase(c2)) {
                stringBuffer.append('_').append(Character.toLowerCase(c2));
            } else {
                stringBuffer.append(c2);
            }
            ++n2;
        }
        return stringBuffer.toString();
    }

    private static String ch(String string2) {
        String string3 = string2;
        if (!string2.startsWith("http")) {
            string3 = string2;
            if (string2.length() > 200) {
                string3 = string2.substring(0, 200) + "[...]";
            }
        }
        return ku.av(string3);
    }

    public static <T extends kt> String e(T t2) {
        if (t2 == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            ku.a(null, t2, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        }
        catch (IllegalAccessException var0_1) {
            return "Error printing proto: " + var0_1.getMessage();
        }
        catch (InvocationTargetException var0_2) {
            return "Error printing proto: " + var0_2.getMessage();
        }
    }
}

