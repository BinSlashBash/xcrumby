package com.google.android.gms.tagmanager;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import com.google.android.gms.plus.PlusShare;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class az extends aj {
    private static final String ID;
    private static final String XQ;
    private static final String Ym;
    private static final String Yn;
    private static final String Yo;

    /* renamed from: com.google.android.gms.tagmanager.az.1 */
    static /* synthetic */ class C04961 {
        static final /* synthetic */ int[] Yp;

        static {
            Yp = new int[C0497a.values().length];
            try {
                Yp[C0497a.URL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                Yp[C0497a.BACKSLASH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                Yp[C0497a.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.az.a */
    private enum C0497a {
        NONE,
        URL,
        BACKSLASH
    }

    static {
        ID = C0321a.JOINER.toString();
        XQ = C0325b.ARG0.toString();
        Ym = C0325b.ITEM_SEPARATOR.toString();
        Yn = C0325b.KEY_VALUE_SEPARATOR.toString();
        Yo = C0325b.ESCAPE.toString();
    }

    public az() {
        super(ID, XQ);
    }

    private String m2462a(String str, C0497a c0497a, Set<Character> set) {
        switch (C04961.Yp[c0497a.ordinal()]) {
            case Std.STD_FILE /*1*/:
                try {
                    return dk.cd(str);
                } catch (Throwable e) {
                    bh.m1381b("Joiner: unsupported encoding", e);
                    return str;
                }
            case Std.STD_URL /*2*/:
                String replace = str.replace("\\", "\\\\");
                String str2 = replace;
                for (Character ch : set) {
                    CharSequence ch2 = ch.toString();
                    str2 = str2.replace(ch2, "\\" + ch2);
                }
                return str2;
            default:
                return str;
        }
    }

    private void m2463a(StringBuilder stringBuilder, String str, C0497a c0497a, Set<Character> set) {
        stringBuilder.append(m2462a(str, c0497a, set));
    }

    private void m2464a(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2465x(Map<String, C1367a> map) {
        C1367a c1367a = (C1367a) map.get(XQ);
        if (c1367a == null) {
            return dh.lT();
        }
        C0497a c0497a;
        Set set;
        C1367a c1367a2 = (C1367a) map.get(Ym);
        String j = c1367a2 != null ? dh.m1460j(c1367a2) : UnsupportedUrlFragment.DISPLAY_NAME;
        c1367a2 = (C1367a) map.get(Yn);
        String j2 = c1367a2 != null ? dh.m1460j(c1367a2) : "=";
        C0497a c0497a2 = C0497a.NONE;
        c1367a2 = (C1367a) map.get(Yo);
        if (c1367a2 != null) {
            String j3 = dh.m1460j(c1367a2);
            if (PlusShare.KEY_CALL_TO_ACTION_URL.equals(j3)) {
                c0497a = C0497a.URL;
                set = null;
            } else if ("backslash".equals(j3)) {
                c0497a = C0497a.BACKSLASH;
                set = new HashSet();
                m2464a(set, j);
                m2464a(set, j2);
                set.remove(Character.valueOf('\\'));
            } else {
                bh.m1384w("Joiner: unsupported escape type: " + j3);
                return dh.lT();
            }
        }
        set = null;
        c0497a = c0497a2;
        StringBuilder stringBuilder = new StringBuilder();
        switch (c1367a.type) {
            case Std.STD_URL /*2*/:
                Object obj = 1;
                C1367a[] c1367aArr = c1367a.fO;
                int length = c1367aArr.length;
                int i = 0;
                while (i < length) {
                    C1367a c1367a3 = c1367aArr[i];
                    if (obj == null) {
                        stringBuilder.append(j);
                    }
                    m2463a(stringBuilder, dh.m1460j(c1367a3), c0497a, set);
                    i++;
                    obj = null;
                }
                break;
            case Std.STD_URI /*3*/:
                for (int i2 = 0; i2 < c1367a.fP.length; i2++) {
                    if (i2 > 0) {
                        stringBuilder.append(j);
                    }
                    String j4 = dh.m1460j(c1367a.fP[i2]);
                    String j5 = dh.m1460j(c1367a.fQ[i2]);
                    m2463a(stringBuilder, j4, c0497a, set);
                    stringBuilder.append(j2);
                    m2463a(stringBuilder, j5, c0497a, set);
                }
                break;
            default:
                m2463a(stringBuilder, dh.m1460j(c1367a), c0497a, set);
                break;
        }
        return dh.m1471r(stringBuilder.toString());
    }
}
