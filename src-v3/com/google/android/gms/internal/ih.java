package com.google.android.gms.internal;

import android.os.Parcel;
import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.crumby.impl.crumby.HomeFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.ga.C0900a;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.AgeRange;
import com.google.android.gms.plus.model.people.Person.Cover;
import com.google.android.gms.plus.model.people.Person.Cover.CoverInfo;
import com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto;
import com.google.android.gms.plus.model.people.Person.Image;
import com.google.android.gms.plus.model.people.Person.Name;
import com.google.android.gms.plus.model.people.Person.Organizations;
import com.google.android.gms.plus.model.people.Person.PlacesLived;
import com.google.android.gms.plus.model.people.Person.Urls;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ih extends ga implements SafeParcelable, Person {
    public static final ii CREATOR;
    private static final HashMap<String, C0900a<?, ?>> UI;
    private String HA;
    private final Set<Integer> UJ;
    private String VH;
    private C1384a VI;
    private String VJ;
    private String VK;
    private int VL;
    private C1387b VM;
    private String VN;
    private C1388c VO;
    private boolean VP;
    private String VQ;
    private C1389d VR;
    private String VS;
    private int VT;
    private List<C1390f> VU;
    private List<C1391g> VV;
    private int VW;
    private int VX;
    private String VY;
    private List<C1392h> VZ;
    private boolean Wa;
    private int lZ;
    private String ro;
    private String wp;
    private final int xH;

    /* renamed from: com.google.android.gms.internal.ih.e */
    public static class C0405e {
        public static int bi(String str) {
            if (str.equals("person")) {
                return 0;
            }
            if (str.equals("page")) {
                return 1;
            }
            throw new IllegalArgumentException("Unknown objectType string: " + str);
        }
    }

    /* renamed from: com.google.android.gms.internal.ih.a */
    public static final class C1384a extends ga implements SafeParcelable, AgeRange {
        public static final ij CREATOR;
        private static final HashMap<String, C0900a<?, ?>> UI;
        private final Set<Integer> UJ;
        private int Wb;
        private int Wc;
        private final int xH;

        static {
            CREATOR = new ij();
            UI = new HashMap();
            UI.put("max", C0900a.m2227g("max", 2));
            UI.put("min", C0900a.m2227g("min", 3));
        }

        public C1384a() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        C1384a(Set<Integer> set, int i, int i2, int i3) {
            this.UJ = set;
            this.xH = i;
            this.Wb = i2;
            this.Wc = i3;
        }

        protected boolean m3094a(C0900a c0900a) {
            return this.UJ.contains(Integer.valueOf(c0900a.ff()));
        }

        protected Object aq(String str) {
            return null;
        }

        protected boolean ar(String str) {
            return false;
        }

        protected Object m3095b(C0900a c0900a) {
            switch (c0900a.ff()) {
                case Std.STD_URL /*2*/:
                    return Integer.valueOf(this.Wb);
                case Std.STD_URI /*3*/:
                    return Integer.valueOf(this.Wc);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
            }
        }

        public int describeContents() {
            ij ijVar = CREATOR;
            return 0;
        }

        public HashMap<String, C0900a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C1384a)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C1384a c1384a = (C1384a) obj;
            for (C0900a c0900a : UI.values()) {
                if (m3094a(c0900a)) {
                    if (!c1384a.m3094a(c0900a)) {
                        return false;
                    }
                    if (!m3095b(c0900a).equals(c1384a.m3095b(c0900a))) {
                        return false;
                    }
                } else if (c1384a.m3094a(c0900a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return jD();
        }

        public int getMax() {
            return this.Wb;
        }

        public int getMin() {
            return this.Wc;
        }

        int getVersionCode() {
            return this.xH;
        }

        public boolean hasMax() {
            return this.UJ.contains(Integer.valueOf(2));
        }

        public boolean hasMin() {
            return this.UJ.contains(Integer.valueOf(3));
        }

        public int hashCode() {
            int i = 0;
            for (C0900a c0900a : UI.values()) {
                int hashCode;
                if (m3094a(c0900a)) {
                    hashCode = m3095b(c0900a).hashCode() + (i + c0900a.ff());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        public boolean isDataValid() {
            return true;
        }

        public C1384a jD() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel out, int flags) {
            ij ijVar = CREATOR;
            ij.m1080a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.ih.b */
    public static final class C1387b extends ga implements SafeParcelable, Cover {
        public static final ik CREATOR;
        private static final HashMap<String, C0900a<?, ?>> UI;
        private final Set<Integer> UJ;
        private C1385a Wd;
        private C1386b We;
        private int Wf;
        private final int xH;

        /* renamed from: com.google.android.gms.internal.ih.b.a */
        public static final class C1385a extends ga implements SafeParcelable, CoverInfo {
            public static final il CREATOR;
            private static final HashMap<String, C0900a<?, ?>> UI;
            private final Set<Integer> UJ;
            private int Wg;
            private int Wh;
            private final int xH;

            static {
                CREATOR = new il();
                UI = new HashMap();
                UI.put("leftImageOffset", C0900a.m2227g("leftImageOffset", 2));
                UI.put("topImageOffset", C0900a.m2227g("topImageOffset", 3));
            }

            public C1385a() {
                this.xH = 1;
                this.UJ = new HashSet();
            }

            C1385a(Set<Integer> set, int i, int i2, int i3) {
                this.UJ = set;
                this.xH = i;
                this.Wg = i2;
                this.Wh = i3;
            }

            protected boolean m3096a(C0900a c0900a) {
                return this.UJ.contains(Integer.valueOf(c0900a.ff()));
            }

            protected Object aq(String str) {
                return null;
            }

            protected boolean ar(String str) {
                return false;
            }

            protected Object m3097b(C0900a c0900a) {
                switch (c0900a.ff()) {
                    case Std.STD_URL /*2*/:
                        return Integer.valueOf(this.Wg);
                    case Std.STD_URI /*3*/:
                        return Integer.valueOf(this.Wh);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
                }
            }

            public int describeContents() {
                il ilVar = CREATOR;
                return 0;
            }

            public HashMap<String, C0900a<?, ?>> eY() {
                return UI;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof C1385a)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                C1385a c1385a = (C1385a) obj;
                for (C0900a c0900a : UI.values()) {
                    if (m3096a(c0900a)) {
                        if (!c1385a.m3096a(c0900a)) {
                            return false;
                        }
                        if (!m3097b(c0900a).equals(c1385a.m3097b(c0900a))) {
                            return false;
                        }
                    } else if (c1385a.m3096a(c0900a)) {
                        return false;
                    }
                }
                return true;
            }

            public /* synthetic */ Object freeze() {
                return jH();
            }

            public int getLeftImageOffset() {
                return this.Wg;
            }

            public int getTopImageOffset() {
                return this.Wh;
            }

            int getVersionCode() {
                return this.xH;
            }

            public boolean hasLeftImageOffset() {
                return this.UJ.contains(Integer.valueOf(2));
            }

            public boolean hasTopImageOffset() {
                return this.UJ.contains(Integer.valueOf(3));
            }

            public int hashCode() {
                int i = 0;
                for (C0900a c0900a : UI.values()) {
                    int hashCode;
                    if (m3096a(c0900a)) {
                        hashCode = m3097b(c0900a).hashCode() + (i + c0900a.ff());
                    } else {
                        hashCode = i;
                    }
                    i = hashCode;
                }
                return i;
            }

            public boolean isDataValid() {
                return true;
            }

            public C1385a jH() {
                return this;
            }

            Set<Integer> ja() {
                return this.UJ;
            }

            public void writeToParcel(Parcel out, int flags) {
                il ilVar = CREATOR;
                il.m1082a(this, out, flags);
            }
        }

        /* renamed from: com.google.android.gms.internal.ih.b.b */
        public static final class C1386b extends ga implements SafeParcelable, CoverPhoto {
            public static final im CREATOR;
            private static final HashMap<String, C0900a<?, ?>> UI;
            private final Set<Integer> UJ;
            private int kr;
            private int ks;
            private String ro;
            private final int xH;

            static {
                CREATOR = new im();
                UI = new HashMap();
                UI.put("height", C0900a.m2227g("height", 2));
                UI.put(PlusShare.KEY_CALL_TO_ACTION_URL, C0900a.m2230j(PlusShare.KEY_CALL_TO_ACTION_URL, 3));
                UI.put("width", C0900a.m2227g("width", 4));
            }

            public C1386b() {
                this.xH = 1;
                this.UJ = new HashSet();
            }

            C1386b(Set<Integer> set, int i, int i2, String str, int i3) {
                this.UJ = set;
                this.xH = i;
                this.ks = i2;
                this.ro = str;
                this.kr = i3;
            }

            protected boolean m3098a(C0900a c0900a) {
                return this.UJ.contains(Integer.valueOf(c0900a.ff()));
            }

            protected Object aq(String str) {
                return null;
            }

            protected boolean ar(String str) {
                return false;
            }

            protected Object m3099b(C0900a c0900a) {
                switch (c0900a.ff()) {
                    case Std.STD_URL /*2*/:
                        return Integer.valueOf(this.ks);
                    case Std.STD_URI /*3*/:
                        return this.ro;
                    case Std.STD_CLASS /*4*/:
                        return Integer.valueOf(this.kr);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
                }
            }

            public int describeContents() {
                im imVar = CREATOR;
                return 0;
            }

            public HashMap<String, C0900a<?, ?>> eY() {
                return UI;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof C1386b)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                C1386b c1386b = (C1386b) obj;
                for (C0900a c0900a : UI.values()) {
                    if (m3098a(c0900a)) {
                        if (!c1386b.m3098a(c0900a)) {
                            return false;
                        }
                        if (!m3099b(c0900a).equals(c1386b.m3099b(c0900a))) {
                            return false;
                        }
                    } else if (c1386b.m3098a(c0900a)) {
                        return false;
                    }
                }
                return true;
            }

            public /* synthetic */ Object freeze() {
                return jI();
            }

            public int getHeight() {
                return this.ks;
            }

            public String getUrl() {
                return this.ro;
            }

            int getVersionCode() {
                return this.xH;
            }

            public int getWidth() {
                return this.kr;
            }

            public boolean hasHeight() {
                return this.UJ.contains(Integer.valueOf(2));
            }

            public boolean hasUrl() {
                return this.UJ.contains(Integer.valueOf(3));
            }

            public boolean hasWidth() {
                return this.UJ.contains(Integer.valueOf(4));
            }

            public int hashCode() {
                int i = 0;
                for (C0900a c0900a : UI.values()) {
                    int hashCode;
                    if (m3098a(c0900a)) {
                        hashCode = m3099b(c0900a).hashCode() + (i + c0900a.ff());
                    } else {
                        hashCode = i;
                    }
                    i = hashCode;
                }
                return i;
            }

            public boolean isDataValid() {
                return true;
            }

            public C1386b jI() {
                return this;
            }

            Set<Integer> ja() {
                return this.UJ;
            }

            public void writeToParcel(Parcel out, int flags) {
                im imVar = CREATOR;
                im.m1083a(this, out, flags);
            }
        }

        static {
            CREATOR = new ik();
            UI = new HashMap();
            UI.put("coverInfo", C0900a.m2224a("coverInfo", 2, C1385a.class));
            UI.put("coverPhoto", C0900a.m2224a("coverPhoto", 3, C1386b.class));
            UI.put("layout", C0900a.m2223a("layout", 4, new fx().m2221f("banner", 0), false));
        }

        public C1387b() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        C1387b(Set<Integer> set, int i, C1385a c1385a, C1386b c1386b, int i2) {
            this.UJ = set;
            this.xH = i;
            this.Wd = c1385a;
            this.We = c1386b;
            this.Wf = i2;
        }

        protected boolean m3100a(C0900a c0900a) {
            return this.UJ.contains(Integer.valueOf(c0900a.ff()));
        }

        protected Object aq(String str) {
            return null;
        }

        protected boolean ar(String str) {
            return false;
        }

        protected Object m3101b(C0900a c0900a) {
            switch (c0900a.ff()) {
                case Std.STD_URL /*2*/:
                    return this.Wd;
                case Std.STD_URI /*3*/:
                    return this.We;
                case Std.STD_CLASS /*4*/:
                    return Integer.valueOf(this.Wf);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
            }
        }

        public int describeContents() {
            ik ikVar = CREATOR;
            return 0;
        }

        public HashMap<String, C0900a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C1387b)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C1387b c1387b = (C1387b) obj;
            for (C0900a c0900a : UI.values()) {
                if (m3100a(c0900a)) {
                    if (!c1387b.m3100a(c0900a)) {
                        return false;
                    }
                    if (!m3101b(c0900a).equals(c1387b.m3101b(c0900a))) {
                        return false;
                    }
                } else if (c1387b.m3100a(c0900a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return jG();
        }

        public CoverInfo getCoverInfo() {
            return this.Wd;
        }

        public CoverPhoto getCoverPhoto() {
            return this.We;
        }

        public int getLayout() {
            return this.Wf;
        }

        int getVersionCode() {
            return this.xH;
        }

        public boolean hasCoverInfo() {
            return this.UJ.contains(Integer.valueOf(2));
        }

        public boolean hasCoverPhoto() {
            return this.UJ.contains(Integer.valueOf(3));
        }

        public boolean hasLayout() {
            return this.UJ.contains(Integer.valueOf(4));
        }

        public int hashCode() {
            int i = 0;
            for (C0900a c0900a : UI.values()) {
                int hashCode;
                if (m3100a(c0900a)) {
                    hashCode = m3101b(c0900a).hashCode() + (i + c0900a.ff());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        public boolean isDataValid() {
            return true;
        }

        C1385a jE() {
            return this.Wd;
        }

        C1386b jF() {
            return this.We;
        }

        public C1387b jG() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel out, int flags) {
            ik ikVar = CREATOR;
            ik.m1081a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.ih.c */
    public static final class C1388c extends ga implements SafeParcelable, Image {
        public static final in CREATOR;
        private static final HashMap<String, C0900a<?, ?>> UI;
        private final Set<Integer> UJ;
        private String ro;
        private final int xH;

        static {
            CREATOR = new in();
            UI = new HashMap();
            UI.put(PlusShare.KEY_CALL_TO_ACTION_URL, C0900a.m2230j(PlusShare.KEY_CALL_TO_ACTION_URL, 2));
        }

        public C1388c() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        public C1388c(String str) {
            this.UJ = new HashSet();
            this.xH = 1;
            this.ro = str;
            this.UJ.add(Integer.valueOf(2));
        }

        C1388c(Set<Integer> set, int i, String str) {
            this.UJ = set;
            this.xH = i;
            this.ro = str;
        }

        protected boolean m3102a(C0900a c0900a) {
            return this.UJ.contains(Integer.valueOf(c0900a.ff()));
        }

        protected Object aq(String str) {
            return null;
        }

        protected boolean ar(String str) {
            return false;
        }

        protected Object m3103b(C0900a c0900a) {
            switch (c0900a.ff()) {
                case Std.STD_URL /*2*/:
                    return this.ro;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
            }
        }

        public int describeContents() {
            in inVar = CREATOR;
            return 0;
        }

        public HashMap<String, C0900a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C1388c)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C1388c c1388c = (C1388c) obj;
            for (C0900a c0900a : UI.values()) {
                if (m3102a(c0900a)) {
                    if (!c1388c.m3102a(c0900a)) {
                        return false;
                    }
                    if (!m3103b(c0900a).equals(c1388c.m3103b(c0900a))) {
                        return false;
                    }
                } else if (c1388c.m3102a(c0900a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return jJ();
        }

        public String getUrl() {
            return this.ro;
        }

        int getVersionCode() {
            return this.xH;
        }

        public boolean hasUrl() {
            return this.UJ.contains(Integer.valueOf(2));
        }

        public int hashCode() {
            int i = 0;
            for (C0900a c0900a : UI.values()) {
                int hashCode;
                if (m3102a(c0900a)) {
                    hashCode = m3103b(c0900a).hashCode() + (i + c0900a.ff());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        public boolean isDataValid() {
            return true;
        }

        public C1388c jJ() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel out, int flags) {
            in inVar = CREATOR;
            in.m1084a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.ih.d */
    public static final class C1389d extends ga implements SafeParcelable, Name {
        public static final io CREATOR;
        private static final HashMap<String, C0900a<?, ?>> UI;
        private final Set<Integer> UJ;
        private String Vh;
        private String Vk;
        private String Wi;
        private String Wj;
        private String Wk;
        private String Wl;
        private final int xH;

        static {
            CREATOR = new io();
            UI = new HashMap();
            UI.put("familyName", C0900a.m2230j("familyName", 2));
            UI.put("formatted", C0900a.m2230j("formatted", 3));
            UI.put("givenName", C0900a.m2230j("givenName", 4));
            UI.put("honorificPrefix", C0900a.m2230j("honorificPrefix", 5));
            UI.put("honorificSuffix", C0900a.m2230j("honorificSuffix", 6));
            UI.put("middleName", C0900a.m2230j("middleName", 7));
        }

        public C1389d() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        C1389d(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, String str6) {
            this.UJ = set;
            this.xH = i;
            this.Vh = str;
            this.Wi = str2;
            this.Vk = str3;
            this.Wj = str4;
            this.Wk = str5;
            this.Wl = str6;
        }

        protected boolean m3104a(C0900a c0900a) {
            return this.UJ.contains(Integer.valueOf(c0900a.ff()));
        }

        protected Object aq(String str) {
            return null;
        }

        protected boolean ar(String str) {
            return false;
        }

        protected Object m3105b(C0900a c0900a) {
            switch (c0900a.ff()) {
                case Std.STD_URL /*2*/:
                    return this.Vh;
                case Std.STD_URI /*3*/:
                    return this.Wi;
                case Std.STD_CLASS /*4*/:
                    return this.Vk;
                case Std.STD_JAVA_TYPE /*5*/:
                    return this.Wj;
                case Std.STD_CURRENCY /*6*/:
                    return this.Wk;
                case Std.STD_PATTERN /*7*/:
                    return this.Wl;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
            }
        }

        public int describeContents() {
            io ioVar = CREATOR;
            return 0;
        }

        public HashMap<String, C0900a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C1389d)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C1389d c1389d = (C1389d) obj;
            for (C0900a c0900a : UI.values()) {
                if (m3104a(c0900a)) {
                    if (!c1389d.m3104a(c0900a)) {
                        return false;
                    }
                    if (!m3105b(c0900a).equals(c1389d.m3105b(c0900a))) {
                        return false;
                    }
                } else if (c1389d.m3104a(c0900a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return jK();
        }

        public String getFamilyName() {
            return this.Vh;
        }

        public String getFormatted() {
            return this.Wi;
        }

        public String getGivenName() {
            return this.Vk;
        }

        public String getHonorificPrefix() {
            return this.Wj;
        }

        public String getHonorificSuffix() {
            return this.Wk;
        }

        public String getMiddleName() {
            return this.Wl;
        }

        int getVersionCode() {
            return this.xH;
        }

        public boolean hasFamilyName() {
            return this.UJ.contains(Integer.valueOf(2));
        }

        public boolean hasFormatted() {
            return this.UJ.contains(Integer.valueOf(3));
        }

        public boolean hasGivenName() {
            return this.UJ.contains(Integer.valueOf(4));
        }

        public boolean hasHonorificPrefix() {
            return this.UJ.contains(Integer.valueOf(5));
        }

        public boolean hasHonorificSuffix() {
            return this.UJ.contains(Integer.valueOf(6));
        }

        public boolean hasMiddleName() {
            return this.UJ.contains(Integer.valueOf(7));
        }

        public int hashCode() {
            int i = 0;
            for (C0900a c0900a : UI.values()) {
                int hashCode;
                if (m3104a(c0900a)) {
                    hashCode = m3105b(c0900a).hashCode() + (i + c0900a.ff());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        public boolean isDataValid() {
            return true;
        }

        public C1389d jK() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel out, int flags) {
            io ioVar = CREATOR;
            io.m1085a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.ih.f */
    public static final class C1390f extends ga implements SafeParcelable, Organizations {
        public static final ip CREATOR;
        private static final HashMap<String, C0900a<?, ?>> UI;
        private String EB;
        private String HD;
        private int LF;
        private final Set<Integer> UJ;
        private String Vg;
        private String Vw;
        private String Wm;
        private String Wn;
        private boolean Wo;
        private String mName;
        private final int xH;

        static {
            CREATOR = new ip();
            UI = new HashMap();
            UI.put("department", C0900a.m2230j("department", 2));
            UI.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, C0900a.m2230j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, 3));
            UI.put("endDate", C0900a.m2230j("endDate", 4));
            UI.put("location", C0900a.m2230j("location", 5));
            UI.put("name", C0900a.m2230j("name", 6));
            UI.put("primary", C0900a.m2229i("primary", 7));
            UI.put("startDate", C0900a.m2230j("startDate", 8));
            UI.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, C0900a.m2230j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, 9));
            UI.put("type", C0900a.m2223a("type", 10, new fx().m2221f("work", 0).m2221f("school", 1), false));
        }

        public C1390f() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        C1390f(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, boolean z, String str6, String str7, int i2) {
            this.UJ = set;
            this.xH = i;
            this.Wm = str;
            this.HD = str2;
            this.Vg = str3;
            this.Wn = str4;
            this.mName = str5;
            this.Wo = z;
            this.Vw = str6;
            this.EB = str7;
            this.LF = i2;
        }

        protected boolean m3106a(C0900a c0900a) {
            return this.UJ.contains(Integer.valueOf(c0900a.ff()));
        }

        protected Object aq(String str) {
            return null;
        }

        protected boolean ar(String str) {
            return false;
        }

        protected Object m3107b(C0900a c0900a) {
            switch (c0900a.ff()) {
                case Std.STD_URL /*2*/:
                    return this.Wm;
                case Std.STD_URI /*3*/:
                    return this.HD;
                case Std.STD_CLASS /*4*/:
                    return this.Vg;
                case Std.STD_JAVA_TYPE /*5*/:
                    return this.Wn;
                case Std.STD_CURRENCY /*6*/:
                    return this.mName;
                case Std.STD_PATTERN /*7*/:
                    return Boolean.valueOf(this.Wo);
                case Std.STD_LOCALE /*8*/:
                    return this.Vw;
                case Std.STD_CHARSET /*9*/:
                    return this.EB;
                case Std.STD_TIME_ZONE /*10*/:
                    return Integer.valueOf(this.LF);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
            }
        }

        public int describeContents() {
            ip ipVar = CREATOR;
            return 0;
        }

        public HashMap<String, C0900a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C1390f)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C1390f c1390f = (C1390f) obj;
            for (C0900a c0900a : UI.values()) {
                if (m3106a(c0900a)) {
                    if (!c1390f.m3106a(c0900a)) {
                        return false;
                    }
                    if (!m3107b(c0900a).equals(c1390f.m3107b(c0900a))) {
                        return false;
                    }
                } else if (c1390f.m3106a(c0900a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return jL();
        }

        public String getDepartment() {
            return this.Wm;
        }

        public String getDescription() {
            return this.HD;
        }

        public String getEndDate() {
            return this.Vg;
        }

        public String getLocation() {
            return this.Wn;
        }

        public String getName() {
            return this.mName;
        }

        public String getStartDate() {
            return this.Vw;
        }

        public String getTitle() {
            return this.EB;
        }

        public int getType() {
            return this.LF;
        }

        int getVersionCode() {
            return this.xH;
        }

        public boolean hasDepartment() {
            return this.UJ.contains(Integer.valueOf(2));
        }

        public boolean hasDescription() {
            return this.UJ.contains(Integer.valueOf(3));
        }

        public boolean hasEndDate() {
            return this.UJ.contains(Integer.valueOf(4));
        }

        public boolean hasLocation() {
            return this.UJ.contains(Integer.valueOf(5));
        }

        public boolean hasName() {
            return this.UJ.contains(Integer.valueOf(6));
        }

        public boolean hasPrimary() {
            return this.UJ.contains(Integer.valueOf(7));
        }

        public boolean hasStartDate() {
            return this.UJ.contains(Integer.valueOf(8));
        }

        public boolean hasTitle() {
            return this.UJ.contains(Integer.valueOf(9));
        }

        public boolean hasType() {
            return this.UJ.contains(Integer.valueOf(10));
        }

        public int hashCode() {
            int i = 0;
            for (C0900a c0900a : UI.values()) {
                int hashCode;
                if (m3106a(c0900a)) {
                    hashCode = m3107b(c0900a).hashCode() + (i + c0900a.ff());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        public boolean isDataValid() {
            return true;
        }

        public boolean isPrimary() {
            return this.Wo;
        }

        public C1390f jL() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel out, int flags) {
            ip ipVar = CREATOR;
            ip.m1086a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.ih.g */
    public static final class C1391g extends ga implements SafeParcelable, PlacesLived {
        public static final iq CREATOR;
        private static final HashMap<String, C0900a<?, ?>> UI;
        private final Set<Integer> UJ;
        private boolean Wo;
        private String mValue;
        private final int xH;

        static {
            CREATOR = new iq();
            UI = new HashMap();
            UI.put("primary", C0900a.m2229i("primary", 2));
            UI.put("value", C0900a.m2230j("value", 3));
        }

        public C1391g() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        C1391g(Set<Integer> set, int i, boolean z, String str) {
            this.UJ = set;
            this.xH = i;
            this.Wo = z;
            this.mValue = str;
        }

        protected boolean m3108a(C0900a c0900a) {
            return this.UJ.contains(Integer.valueOf(c0900a.ff()));
        }

        protected Object aq(String str) {
            return null;
        }

        protected boolean ar(String str) {
            return false;
        }

        protected Object m3109b(C0900a c0900a) {
            switch (c0900a.ff()) {
                case Std.STD_URL /*2*/:
                    return Boolean.valueOf(this.Wo);
                case Std.STD_URI /*3*/:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
            }
        }

        public int describeContents() {
            iq iqVar = CREATOR;
            return 0;
        }

        public HashMap<String, C0900a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C1391g)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C1391g c1391g = (C1391g) obj;
            for (C0900a c0900a : UI.values()) {
                if (m3108a(c0900a)) {
                    if (!c1391g.m3108a(c0900a)) {
                        return false;
                    }
                    if (!m3109b(c0900a).equals(c1391g.m3109b(c0900a))) {
                        return false;
                    }
                } else if (c1391g.m3108a(c0900a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return jM();
        }

        public String getValue() {
            return this.mValue;
        }

        int getVersionCode() {
            return this.xH;
        }

        public boolean hasPrimary() {
            return this.UJ.contains(Integer.valueOf(2));
        }

        public boolean hasValue() {
            return this.UJ.contains(Integer.valueOf(3));
        }

        public int hashCode() {
            int i = 0;
            for (C0900a c0900a : UI.values()) {
                int hashCode;
                if (m3108a(c0900a)) {
                    hashCode = m3109b(c0900a).hashCode() + (i + c0900a.ff());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        public boolean isDataValid() {
            return true;
        }

        public boolean isPrimary() {
            return this.Wo;
        }

        public C1391g jM() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel out, int flags) {
            iq iqVar = CREATOR;
            iq.m1087a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.ih.h */
    public static final class C1392h extends ga implements SafeParcelable, Urls {
        public static final ir CREATOR;
        private static final HashMap<String, C0900a<?, ?>> UI;
        private int LF;
        private final Set<Integer> UJ;
        private String Wp;
        private final int Wq;
        private String mValue;
        private final int xH;

        static {
            CREATOR = new ir();
            UI = new HashMap();
            UI.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, C0900a.m2230j(PlusShare.KEY_CALL_TO_ACTION_LABEL, 5));
            UI.put("type", C0900a.m2223a("type", 6, new fx().m2221f(HomeFragment.DISPLAY_NAME, 0).m2221f("work", 1).m2221f("blog", 2).m2221f(Scopes.PROFILE, 3).m2221f("other", 4).m2221f("otherProfile", 5).m2221f("contributor", 6).m2221f("website", 7), false));
            UI.put("value", C0900a.m2230j("value", 4));
        }

        public C1392h() {
            this.Wq = 4;
            this.xH = 2;
            this.UJ = new HashSet();
        }

        C1392h(Set<Integer> set, int i, String str, int i2, String str2, int i3) {
            this.Wq = 4;
            this.UJ = set;
            this.xH = i;
            this.Wp = str;
            this.LF = i2;
            this.mValue = str2;
        }

        protected boolean m3110a(C0900a c0900a) {
            return this.UJ.contains(Integer.valueOf(c0900a.ff()));
        }

        protected Object aq(String str) {
            return null;
        }

        protected boolean ar(String str) {
            return false;
        }

        protected Object m3111b(C0900a c0900a) {
            switch (c0900a.ff()) {
                case Std.STD_CLASS /*4*/:
                    return this.mValue;
                case Std.STD_JAVA_TYPE /*5*/:
                    return this.Wp;
                case Std.STD_CURRENCY /*6*/:
                    return Integer.valueOf(this.LF);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
            }
        }

        public int describeContents() {
            ir irVar = CREATOR;
            return 0;
        }

        public HashMap<String, C0900a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C1392h)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C1392h c1392h = (C1392h) obj;
            for (C0900a c0900a : UI.values()) {
                if (m3110a(c0900a)) {
                    if (!c1392h.m3110a(c0900a)) {
                        return false;
                    }
                    if (!m3111b(c0900a).equals(c1392h.m3111b(c0900a))) {
                        return false;
                    }
                } else if (c1392h.m3110a(c0900a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return jO();
        }

        public String getLabel() {
            return this.Wp;
        }

        public int getType() {
            return this.LF;
        }

        public String getValue() {
            return this.mValue;
        }

        int getVersionCode() {
            return this.xH;
        }

        public boolean hasLabel() {
            return this.UJ.contains(Integer.valueOf(5));
        }

        public boolean hasType() {
            return this.UJ.contains(Integer.valueOf(6));
        }

        public boolean hasValue() {
            return this.UJ.contains(Integer.valueOf(4));
        }

        public int hashCode() {
            int i = 0;
            for (C0900a c0900a : UI.values()) {
                int hashCode;
                if (m3110a(c0900a)) {
                    hashCode = m3111b(c0900a).hashCode() + (i + c0900a.ff());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        public boolean isDataValid() {
            return true;
        }

        @Deprecated
        public int jN() {
            return 4;
        }

        public C1392h jO() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel out, int flags) {
            ir irVar = CREATOR;
            ir.m1088a(this, out, flags);
        }
    }

    static {
        CREATOR = new ii();
        UI = new HashMap();
        UI.put("aboutMe", C0900a.m2230j("aboutMe", 2));
        UI.put("ageRange", C0900a.m2224a("ageRange", 3, C1384a.class));
        UI.put("birthday", C0900a.m2230j("birthday", 4));
        UI.put("braggingRights", C0900a.m2230j("braggingRights", 5));
        UI.put("circledByCount", C0900a.m2227g("circledByCount", 6));
        UI.put("cover", C0900a.m2224a("cover", 7, C1387b.class));
        UI.put("currentLocation", C0900a.m2230j("currentLocation", 8));
        UI.put("displayName", C0900a.m2230j("displayName", 9));
        UI.put("gender", C0900a.m2223a("gender", 12, new fx().m2221f("male", 0).m2221f("female", 1).m2221f("other", 2), false));
        UI.put("id", C0900a.m2230j("id", 14));
        UI.put("image", C0900a.m2224a("image", 15, C1388c.class));
        UI.put("isPlusUser", C0900a.m2229i("isPlusUser", 16));
        UI.put("language", C0900a.m2230j("language", 18));
        UI.put("name", C0900a.m2224a("name", 19, C1389d.class));
        UI.put("nickname", C0900a.m2230j("nickname", 20));
        UI.put("objectType", C0900a.m2223a("objectType", 21, new fx().m2221f("person", 0).m2221f("page", 1), false));
        UI.put("organizations", C0900a.m2225b("organizations", 22, C1390f.class));
        UI.put("placesLived", C0900a.m2225b("placesLived", 23, C1391g.class));
        UI.put("plusOneCount", C0900a.m2227g("plusOneCount", 24));
        UI.put("relationshipStatus", C0900a.m2223a("relationshipStatus", 25, new fx().m2221f("single", 0).m2221f("in_a_relationship", 1).m2221f("engaged", 2).m2221f("married", 3).m2221f("its_complicated", 4).m2221f("open_relationship", 5).m2221f("widowed", 6).m2221f("in_domestic_partnership", 7).m2221f("in_civil_union", 8), false));
        UI.put("tagline", C0900a.m2230j("tagline", 26));
        UI.put(PlusShare.KEY_CALL_TO_ACTION_URL, C0900a.m2230j(PlusShare.KEY_CALL_TO_ACTION_URL, 27));
        UI.put("urls", C0900a.m2225b("urls", 28, C1392h.class));
        UI.put("verified", C0900a.m2229i("verified", 29));
    }

    public ih() {
        this.xH = 2;
        this.UJ = new HashSet();
    }

    public ih(String str, String str2, C1388c c1388c, int i, String str3) {
        this.xH = 2;
        this.UJ = new HashSet();
        this.HA = str;
        this.UJ.add(Integer.valueOf(9));
        this.wp = str2;
        this.UJ.add(Integer.valueOf(14));
        this.VO = c1388c;
        this.UJ.add(Integer.valueOf(15));
        this.VT = i;
        this.UJ.add(Integer.valueOf(21));
        this.ro = str3;
        this.UJ.add(Integer.valueOf(27));
    }

    ih(Set<Integer> set, int i, String str, C1384a c1384a, String str2, String str3, int i2, C1387b c1387b, String str4, String str5, int i3, String str6, C1388c c1388c, boolean z, String str7, C1389d c1389d, String str8, int i4, List<C1390f> list, List<C1391g> list2, int i5, int i6, String str9, String str10, List<C1392h> list3, boolean z2) {
        this.UJ = set;
        this.xH = i;
        this.VH = str;
        this.VI = c1384a;
        this.VJ = str2;
        this.VK = str3;
        this.VL = i2;
        this.VM = c1387b;
        this.VN = str4;
        this.HA = str5;
        this.lZ = i3;
        this.wp = str6;
        this.VO = c1388c;
        this.VP = z;
        this.VQ = str7;
        this.VR = c1389d;
        this.VS = str8;
        this.VT = i4;
        this.VU = list;
        this.VV = list2;
        this.VW = i5;
        this.VX = i6;
        this.VY = str9;
        this.ro = str10;
        this.VZ = list3;
        this.Wa = z2;
    }

    public static ih m3112i(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        ih aN = CREATOR.aN(obtain);
        obtain.recycle();
        return aN;
    }

    protected boolean m3113a(C0900a c0900a) {
        return this.UJ.contains(Integer.valueOf(c0900a.ff()));
    }

    protected Object aq(String str) {
        return null;
    }

    protected boolean ar(String str) {
        return false;
    }

    protected Object m3114b(C0900a c0900a) {
        switch (c0900a.ff()) {
            case Std.STD_URL /*2*/:
                return this.VH;
            case Std.STD_URI /*3*/:
                return this.VI;
            case Std.STD_CLASS /*4*/:
                return this.VJ;
            case Std.STD_JAVA_TYPE /*5*/:
                return this.VK;
            case Std.STD_CURRENCY /*6*/:
                return Integer.valueOf(this.VL);
            case Std.STD_PATTERN /*7*/:
                return this.VM;
            case Std.STD_LOCALE /*8*/:
                return this.VN;
            case Std.STD_CHARSET /*9*/:
                return this.HA;
            case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                return Integer.valueOf(this.lZ);
            case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                return this.wp;
            case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                return this.VO;
            case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                return Boolean.valueOf(this.VP);
            case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                return this.VQ;
            case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                return this.VR;
            case HttpEngine.MAX_REDIRECTS /*20*/:
                return this.VS;
            case C0065R.styleable.TwoWayView_android_visibility /*21*/:
                return Integer.valueOf(this.VT);
            case C0065R.styleable.TwoWayView_android_fitsSystemWindows /*22*/:
                return this.VU;
            case C0065R.styleable.TwoWayView_android_scrollbars /*23*/:
                return this.VV;
            case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                return Integer.valueOf(this.VW);
            case C0065R.styleable.TwoWayView_android_fadingEdgeLength /*25*/:
                return Integer.valueOf(this.VX);
            case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                return this.VY;
            case C0065R.styleable.TwoWayView_android_nextFocusRight /*27*/:
                return this.ro;
            case C0065R.styleable.TwoWayView_android_nextFocusUp /*28*/:
                return this.VZ;
            case C0065R.styleable.TwoWayView_android_nextFocusDown /*29*/:
                return Boolean.valueOf(this.Wa);
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
        }
    }

    public int describeContents() {
        ii iiVar = CREATOR;
        return 0;
    }

    public HashMap<String, C0900a<?, ?>> eY() {
        return UI;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ih)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ih ihVar = (ih) obj;
        for (C0900a c0900a : UI.values()) {
            if (m3113a(c0900a)) {
                if (!ihVar.m3113a(c0900a)) {
                    return false;
                }
                if (!m3114b(c0900a).equals(ihVar.m3114b(c0900a))) {
                    return false;
                }
            } else if (ihVar.m3113a(c0900a)) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object freeze() {
        return jC();
    }

    public String getAboutMe() {
        return this.VH;
    }

    public AgeRange getAgeRange() {
        return this.VI;
    }

    public String getBirthday() {
        return this.VJ;
    }

    public String getBraggingRights() {
        return this.VK;
    }

    public int getCircledByCount() {
        return this.VL;
    }

    public Cover getCover() {
        return this.VM;
    }

    public String getCurrentLocation() {
        return this.VN;
    }

    public String getDisplayName() {
        return this.HA;
    }

    public int getGender() {
        return this.lZ;
    }

    public String getId() {
        return this.wp;
    }

    public Image getImage() {
        return this.VO;
    }

    public String getLanguage() {
        return this.VQ;
    }

    public Name getName() {
        return this.VR;
    }

    public String getNickname() {
        return this.VS;
    }

    public int getObjectType() {
        return this.VT;
    }

    public List<Organizations> getOrganizations() {
        return (ArrayList) this.VU;
    }

    public List<PlacesLived> getPlacesLived() {
        return (ArrayList) this.VV;
    }

    public int getPlusOneCount() {
        return this.VW;
    }

    public int getRelationshipStatus() {
        return this.VX;
    }

    public String getTagline() {
        return this.VY;
    }

    public String getUrl() {
        return this.ro;
    }

    public List<Urls> getUrls() {
        return (ArrayList) this.VZ;
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean hasAboutMe() {
        return this.UJ.contains(Integer.valueOf(2));
    }

    public boolean hasAgeRange() {
        return this.UJ.contains(Integer.valueOf(3));
    }

    public boolean hasBirthday() {
        return this.UJ.contains(Integer.valueOf(4));
    }

    public boolean hasBraggingRights() {
        return this.UJ.contains(Integer.valueOf(5));
    }

    public boolean hasCircledByCount() {
        return this.UJ.contains(Integer.valueOf(6));
    }

    public boolean hasCover() {
        return this.UJ.contains(Integer.valueOf(7));
    }

    public boolean hasCurrentLocation() {
        return this.UJ.contains(Integer.valueOf(8));
    }

    public boolean hasDisplayName() {
        return this.UJ.contains(Integer.valueOf(9));
    }

    public boolean hasGender() {
        return this.UJ.contains(Integer.valueOf(12));
    }

    public boolean hasId() {
        return this.UJ.contains(Integer.valueOf(14));
    }

    public boolean hasImage() {
        return this.UJ.contains(Integer.valueOf(15));
    }

    public boolean hasIsPlusUser() {
        return this.UJ.contains(Integer.valueOf(16));
    }

    public boolean hasLanguage() {
        return this.UJ.contains(Integer.valueOf(18));
    }

    public boolean hasName() {
        return this.UJ.contains(Integer.valueOf(19));
    }

    public boolean hasNickname() {
        return this.UJ.contains(Integer.valueOf(20));
    }

    public boolean hasObjectType() {
        return this.UJ.contains(Integer.valueOf(21));
    }

    public boolean hasOrganizations() {
        return this.UJ.contains(Integer.valueOf(22));
    }

    public boolean hasPlacesLived() {
        return this.UJ.contains(Integer.valueOf(23));
    }

    public boolean hasPlusOneCount() {
        return this.UJ.contains(Integer.valueOf(24));
    }

    public boolean hasRelationshipStatus() {
        return this.UJ.contains(Integer.valueOf(25));
    }

    public boolean hasTagline() {
        return this.UJ.contains(Integer.valueOf(26));
    }

    public boolean hasUrl() {
        return this.UJ.contains(Integer.valueOf(27));
    }

    public boolean hasUrls() {
        return this.UJ.contains(Integer.valueOf(28));
    }

    public boolean hasVerified() {
        return this.UJ.contains(Integer.valueOf(29));
    }

    public int hashCode() {
        int i = 0;
        for (C0900a c0900a : UI.values()) {
            int hashCode;
            if (m3113a(c0900a)) {
                hashCode = m3114b(c0900a).hashCode() + (i + c0900a.ff());
            } else {
                hashCode = i;
            }
            i = hashCode;
        }
        return i;
    }

    public boolean isDataValid() {
        return true;
    }

    public boolean isPlusUser() {
        return this.VP;
    }

    public boolean isVerified() {
        return this.Wa;
    }

    List<C1391g> jA() {
        return this.VV;
    }

    List<C1392h> jB() {
        return this.VZ;
    }

    public ih jC() {
        return this;
    }

    Set<Integer> ja() {
        return this.UJ;
    }

    C1384a jv() {
        return this.VI;
    }

    C1387b jw() {
        return this.VM;
    }

    C1388c jx() {
        return this.VO;
    }

    C1389d jy() {
        return this.VR;
    }

    List<C1390f> jz() {
        return this.VU;
    }

    public void writeToParcel(Parcel out, int flags) {
        ii iiVar = CREATOR;
        ii.m1079a(this, out, flags);
    }
}
