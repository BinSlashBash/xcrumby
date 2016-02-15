/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fx;
import com.google.android.gms.internal.ga;
import com.google.android.gms.internal.ii;
import com.google.android.gms.internal.ij;
import com.google.android.gms.internal.ik;
import com.google.android.gms.internal.il;
import com.google.android.gms.internal.im;
import com.google.android.gms.internal.in;
import com.google.android.gms.internal.io;
import com.google.android.gms.internal.ip;
import com.google.android.gms.internal.iq;
import com.google.android.gms.internal.ir;
import com.google.android.gms.plus.model.people.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class ih
extends ga
implements SafeParcelable,
Person {
    public static final ii CREATOR = new ii();
    private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
    private String HA;
    private final Set<Integer> UJ;
    private String VH;
    private a VI;
    private String VJ;
    private String VK;
    private int VL;
    private b VM;
    private String VN;
    private c VO;
    private boolean VP;
    private String VQ;
    private d VR;
    private String VS;
    private int VT;
    private List<f> VU;
    private List<g> VV;
    private int VW;
    private int VX;
    private String VY;
    private List<h> VZ;
    private boolean Wa;
    private int lZ;
    private String ro;
    private String wp;
    private final int xH;

    static {
        UI.put("aboutMe", ga.a.j("aboutMe", 2));
        UI.put("ageRange", ga.a.a("ageRange", 3, a.class));
        UI.put("birthday", ga.a.j("birthday", 4));
        UI.put("braggingRights", ga.a.j("braggingRights", 5));
        UI.put("circledByCount", ga.a.g("circledByCount", 6));
        UI.put("cover", ga.a.a("cover", 7, b.class));
        UI.put("currentLocation", ga.a.j("currentLocation", 8));
        UI.put("displayName", ga.a.j("displayName", 9));
        UI.put("gender", ga.a.a("gender", 12, new fx().f("male", 0).f("female", 1).f("other", 2), false));
        UI.put("id", ga.a.j("id", 14));
        UI.put("image", ga.a.a("image", 15, c.class));
        UI.put("isPlusUser", ga.a.i("isPlusUser", 16));
        UI.put("language", ga.a.j("language", 18));
        UI.put("name", ga.a.a("name", 19, d.class));
        UI.put("nickname", ga.a.j("nickname", 20));
        UI.put("objectType", ga.a.a("objectType", 21, new fx().f("person", 0).f("page", 1), false));
        UI.put("organizations", ga.a.b("organizations", 22, f.class));
        UI.put("placesLived", ga.a.b("placesLived", 23, g.class));
        UI.put("plusOneCount", ga.a.g("plusOneCount", 24));
        UI.put("relationshipStatus", ga.a.a("relationshipStatus", 25, new fx().f("single", 0).f("in_a_relationship", 1).f("engaged", 2).f("married", 3).f("its_complicated", 4).f("open_relationship", 5).f("widowed", 6).f("in_domestic_partnership", 7).f("in_civil_union", 8), false));
        UI.put("tagline", ga.a.j("tagline", 26));
        UI.put("url", ga.a.j("url", 27));
        UI.put("urls", ga.a.b("urls", 28, h.class));
        UI.put("verified", ga.a.i("verified", 29));
    }

    public ih() {
        this.xH = 2;
        this.UJ = new HashSet<Integer>();
    }

    public ih(String string2, String string3, c c2, int n2, String string4) {
        this.xH = 2;
        this.UJ = new HashSet<Integer>();
        this.HA = string2;
        this.UJ.add(9);
        this.wp = string3;
        this.UJ.add(14);
        this.VO = c2;
        this.UJ.add(15);
        this.VT = n2;
        this.UJ.add(21);
        this.ro = string4;
        this.UJ.add(27);
    }

    ih(Set<Integer> set, int n2, String string2, a a2, String string3, String string4, int n3, b b2, String string5, String string6, int n4, String string7, c c2, boolean bl2, String string8, d d2, String string9, int n5, List<f> list, List<g> list2, int n6, int n7, String string10, String string11, List<h> list3, boolean bl3) {
        this.UJ = set;
        this.xH = n2;
        this.VH = string2;
        this.VI = a2;
        this.VJ = string3;
        this.VK = string4;
        this.VL = n3;
        this.VM = b2;
        this.VN = string5;
        this.HA = string6;
        this.lZ = n4;
        this.wp = string7;
        this.VO = c2;
        this.VP = bl2;
        this.VQ = string8;
        this.VR = d2;
        this.VS = string9;
        this.VT = n5;
        this.VU = list;
        this.VV = list2;
        this.VW = n6;
        this.VX = n7;
        this.VY = string10;
        this.ro = string11;
        this.VZ = list3;
        this.Wa = bl3;
    }

    public static ih i(byte[] object) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall((byte[])object, 0, object.length);
        parcel.setDataPosition(0);
        object = CREATOR.aN(parcel);
        parcel.recycle();
        return object;
    }

    @Override
    protected boolean a(ga.a a2) {
        return this.UJ.contains(a2.ff());
    }

    @Override
    protected Object aq(String string2) {
        return null;
    }

    @Override
    protected boolean ar(String string2) {
        return false;
    }

    @Override
    protected Object b(ga.a a2) {
        switch (a2.ff()) {
            default: {
                throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
            }
            case 2: {
                return this.VH;
            }
            case 3: {
                return this.VI;
            }
            case 4: {
                return this.VJ;
            }
            case 5: {
                return this.VK;
            }
            case 6: {
                return this.VL;
            }
            case 7: {
                return this.VM;
            }
            case 8: {
                return this.VN;
            }
            case 9: {
                return this.HA;
            }
            case 12: {
                return this.lZ;
            }
            case 14: {
                return this.wp;
            }
            case 15: {
                return this.VO;
            }
            case 16: {
                return this.VP;
            }
            case 18: {
                return this.VQ;
            }
            case 19: {
                return this.VR;
            }
            case 20: {
                return this.VS;
            }
            case 21: {
                return this.VT;
            }
            case 22: {
                return this.VU;
            }
            case 23: {
                return this.VV;
            }
            case 24: {
                return this.VW;
            }
            case 25: {
                return this.VX;
            }
            case 26: {
                return this.VY;
            }
            case 27: {
                return this.ro;
            }
            case 28: {
                return this.VZ;
            }
            case 29: 
        }
        return this.Wa;
    }

    public int describeContents() {
        ii ii2 = CREATOR;
        return 0;
    }

    @Override
    public HashMap<String, ga.a<?, ?>> eY() {
        return UI;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ih)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        object = (ih)object;
        for (ga.a a2 : UI.values()) {
            if (this.a(a2)) {
                if (object.a(a2)) {
                    if (this.b(a2).equals(object.b(a2))) continue;
                    return false;
                }
                return false;
            }
            if (!object.a(a2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.jC();
    }

    @Override
    public String getAboutMe() {
        return this.VH;
    }

    @Override
    public Person.AgeRange getAgeRange() {
        return this.VI;
    }

    @Override
    public String getBirthday() {
        return this.VJ;
    }

    @Override
    public String getBraggingRights() {
        return this.VK;
    }

    @Override
    public int getCircledByCount() {
        return this.VL;
    }

    @Override
    public Person.Cover getCover() {
        return this.VM;
    }

    @Override
    public String getCurrentLocation() {
        return this.VN;
    }

    @Override
    public String getDisplayName() {
        return this.HA;
    }

    @Override
    public int getGender() {
        return this.lZ;
    }

    @Override
    public String getId() {
        return this.wp;
    }

    @Override
    public Person.Image getImage() {
        return this.VO;
    }

    @Override
    public String getLanguage() {
        return this.VQ;
    }

    @Override
    public Person.Name getName() {
        return this.VR;
    }

    @Override
    public String getNickname() {
        return this.VS;
    }

    @Override
    public int getObjectType() {
        return this.VT;
    }

    @Override
    public List<Person.Organizations> getOrganizations() {
        return (ArrayList)this.VU;
    }

    @Override
    public List<Person.PlacesLived> getPlacesLived() {
        return (ArrayList)this.VV;
    }

    @Override
    public int getPlusOneCount() {
        return this.VW;
    }

    @Override
    public int getRelationshipStatus() {
        return this.VX;
    }

    @Override
    public String getTagline() {
        return this.VY;
    }

    @Override
    public String getUrl() {
        return this.ro;
    }

    @Override
    public List<Person.Urls> getUrls() {
        return (ArrayList)this.VZ;
    }

    int getVersionCode() {
        return this.xH;
    }

    @Override
    public boolean hasAboutMe() {
        return this.UJ.contains(2);
    }

    @Override
    public boolean hasAgeRange() {
        return this.UJ.contains(3);
    }

    @Override
    public boolean hasBirthday() {
        return this.UJ.contains(4);
    }

    @Override
    public boolean hasBraggingRights() {
        return this.UJ.contains(5);
    }

    @Override
    public boolean hasCircledByCount() {
        return this.UJ.contains(6);
    }

    @Override
    public boolean hasCover() {
        return this.UJ.contains(7);
    }

    @Override
    public boolean hasCurrentLocation() {
        return this.UJ.contains(8);
    }

    @Override
    public boolean hasDisplayName() {
        return this.UJ.contains(9);
    }

    @Override
    public boolean hasGender() {
        return this.UJ.contains(12);
    }

    @Override
    public boolean hasId() {
        return this.UJ.contains(14);
    }

    @Override
    public boolean hasImage() {
        return this.UJ.contains(15);
    }

    @Override
    public boolean hasIsPlusUser() {
        return this.UJ.contains(16);
    }

    @Override
    public boolean hasLanguage() {
        return this.UJ.contains(18);
    }

    @Override
    public boolean hasName() {
        return this.UJ.contains(19);
    }

    @Override
    public boolean hasNickname() {
        return this.UJ.contains(20);
    }

    @Override
    public boolean hasObjectType() {
        return this.UJ.contains(21);
    }

    @Override
    public boolean hasOrganizations() {
        return this.UJ.contains(22);
    }

    @Override
    public boolean hasPlacesLived() {
        return this.UJ.contains(23);
    }

    @Override
    public boolean hasPlusOneCount() {
        return this.UJ.contains(24);
    }

    @Override
    public boolean hasRelationshipStatus() {
        return this.UJ.contains(25);
    }

    @Override
    public boolean hasTagline() {
        return this.UJ.contains(26);
    }

    @Override
    public boolean hasUrl() {
        return this.UJ.contains(27);
    }

    @Override
    public boolean hasUrls() {
        return this.UJ.contains(28);
    }

    @Override
    public boolean hasVerified() {
        return this.UJ.contains(29);
    }

    public int hashCode() {
        Iterator iterator = UI.values().iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            ga.a a2 = iterator.next();
            if (!this.a(a2)) continue;
            int n3 = a2.ff();
            n2 = this.b(a2).hashCode() + (n2 + n3);
        }
        return n2;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    @Override
    public boolean isPlusUser() {
        return this.VP;
    }

    @Override
    public boolean isVerified() {
        return this.Wa;
    }

    List<g> jA() {
        return this.VV;
    }

    List<h> jB() {
        return this.VZ;
    }

    public ih jC() {
        return this;
    }

    Set<Integer> ja() {
        return this.UJ;
    }

    a jv() {
        return this.VI;
    }

    b jw() {
        return this.VM;
    }

    c jx() {
        return this.VO;
    }

    d jy() {
        return this.VR;
    }

    List<f> jz() {
        return this.VU;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ii ii2 = CREATOR;
        ii.a(this, parcel, n2);
    }

    public static final class a
    extends ga
    implements SafeParcelable,
    Person.AgeRange {
        public static final ij CREATOR = new ij();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
        private final Set<Integer> UJ;
        private int Wb;
        private int Wc;
        private final int xH;

        static {
            UI.put("max", ga.a.g("max", 2));
            UI.put("min", ga.a.g("min", 3));
        }

        public a() {
            this.xH = 1;
            this.UJ = new HashSet<Integer>();
        }

        a(Set<Integer> set, int n2, int n3, int n4) {
            this.UJ = set;
            this.xH = n2;
            this.Wb = n3;
            this.Wc = n4;
        }

        @Override
        protected boolean a(ga.a a2) {
            return this.UJ.contains(a2.ff());
        }

        @Override
        protected Object aq(String string2) {
            return null;
        }

        @Override
        protected boolean ar(String string2) {
            return false;
        }

        @Override
        protected Object b(ga.a a2) {
            switch (a2.ff()) {
                default: {
                    throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
                }
                case 2: {
                    return this.Wb;
                }
                case 3: 
            }
            return this.Wc;
        }

        public int describeContents() {
            ij ij2 = CREATOR;
            return 0;
        }

        @Override
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object object) {
            if (!(object instanceof a)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (a)object;
            for (ga.a a2 : UI.values()) {
                if (this.a(a2)) {
                    if (object.a(a2)) {
                        if (this.b(a2).equals(object.b(a2))) continue;
                        return false;
                    }
                    return false;
                }
                if (!object.a(a2)) continue;
                return false;
            }
            return true;
        }

        @Override
        public /* synthetic */ Object freeze() {
            return this.jD();
        }

        @Override
        public int getMax() {
            return this.Wb;
        }

        @Override
        public int getMin() {
            return this.Wc;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override
        public boolean hasMax() {
            return this.UJ.contains(2);
        }

        @Override
        public boolean hasMin() {
            return this.UJ.contains(3);
        }

        public int hashCode() {
            Iterator iterator = UI.values().iterator();
            int n2 = 0;
            while (iterator.hasNext()) {
                ga.a a2 = iterator.next();
                if (!this.a(a2)) continue;
                int n3 = a2.ff();
                n2 = this.b(a2).hashCode() + (n2 + n3);
            }
            return n2;
        }

        @Override
        public boolean isDataValid() {
            return true;
        }

        public a jD() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            ij ij2 = CREATOR;
            ij.a(this, parcel, n2);
        }
    }

    public static final class com.google.android.gms.internal.ih$b
    extends ga
    implements SafeParcelable,
    Person.Cover {
        public static final ik CREATOR = new ik();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
        private final Set<Integer> UJ;
        private a Wd;
        private b We;
        private int Wf;
        private final int xH;

        static {
            UI.put("coverInfo", ga.a.a("coverInfo", 2, a.class));
            UI.put("coverPhoto", ga.a.a("coverPhoto", 3, b.class));
            UI.put("layout", ga.a.a("layout", 4, new fx().f("banner", 0), false));
        }

        public com.google.android.gms.internal.ih$b() {
            this.xH = 1;
            this.UJ = new HashSet<Integer>();
        }

        com.google.android.gms.internal.ih$b(Set<Integer> set, int n2, a a2, b b2, int n3) {
            this.UJ = set;
            this.xH = n2;
            this.Wd = a2;
            this.We = b2;
            this.Wf = n3;
        }

        @Override
        protected boolean a(ga.a a2) {
            return this.UJ.contains(a2.ff());
        }

        @Override
        protected Object aq(String string2) {
            return null;
        }

        @Override
        protected boolean ar(String string2) {
            return false;
        }

        @Override
        protected Object b(ga.a a2) {
            switch (a2.ff()) {
                default: {
                    throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
                }
                case 2: {
                    return this.Wd;
                }
                case 3: {
                    return this.We;
                }
                case 4: 
            }
            return this.Wf;
        }

        public int describeContents() {
            ik ik2 = CREATOR;
            return 0;
        }

        @Override
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object object) {
            if (!(object instanceof com.google.android.gms.internal.ih$b)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (com.google.android.gms.internal.ih$b)object;
            for (ga.a a2 : UI.values()) {
                if (this.a(a2)) {
                    if (object.a(a2)) {
                        if (this.b(a2).equals(object.b(a2))) continue;
                        return false;
                    }
                    return false;
                }
                if (!object.a(a2)) continue;
                return false;
            }
            return true;
        }

        @Override
        public /* synthetic */ Object freeze() {
            return this.jG();
        }

        @Override
        public Person.Cover.CoverInfo getCoverInfo() {
            return this.Wd;
        }

        @Override
        public Person.Cover.CoverPhoto getCoverPhoto() {
            return this.We;
        }

        @Override
        public int getLayout() {
            return this.Wf;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override
        public boolean hasCoverInfo() {
            return this.UJ.contains(2);
        }

        @Override
        public boolean hasCoverPhoto() {
            return this.UJ.contains(3);
        }

        @Override
        public boolean hasLayout() {
            return this.UJ.contains(4);
        }

        public int hashCode() {
            Iterator iterator = UI.values().iterator();
            int n2 = 0;
            while (iterator.hasNext()) {
                ga.a a2 = iterator.next();
                if (!this.a(a2)) continue;
                int n3 = a2.ff();
                n2 = this.b(a2).hashCode() + (n2 + n3);
            }
            return n2;
        }

        @Override
        public boolean isDataValid() {
            return true;
        }

        a jE() {
            return this.Wd;
        }

        b jF() {
            return this.We;
        }

        public com.google.android.gms.internal.ih$b jG() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            ik ik2 = CREATOR;
            ik.a(this, parcel, n2);
        }

        public static final class a
        extends ga
        implements SafeParcelable,
        Person.Cover.CoverInfo {
            public static final il CREATOR = new il();
            private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
            private final Set<Integer> UJ;
            private int Wg;
            private int Wh;
            private final int xH;

            static {
                UI.put("leftImageOffset", ga.a.g("leftImageOffset", 2));
                UI.put("topImageOffset", ga.a.g("topImageOffset", 3));
            }

            public a() {
                this.xH = 1;
                this.UJ = new HashSet<Integer>();
            }

            a(Set<Integer> set, int n2, int n3, int n4) {
                this.UJ = set;
                this.xH = n2;
                this.Wg = n3;
                this.Wh = n4;
            }

            @Override
            protected boolean a(ga.a a2) {
                return this.UJ.contains(a2.ff());
            }

            @Override
            protected Object aq(String string2) {
                return null;
            }

            @Override
            protected boolean ar(String string2) {
                return false;
            }

            @Override
            protected Object b(ga.a a2) {
                switch (a2.ff()) {
                    default: {
                        throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
                    }
                    case 2: {
                        return this.Wg;
                    }
                    case 3: 
                }
                return this.Wh;
            }

            public int describeContents() {
                il il2 = CREATOR;
                return 0;
            }

            @Override
            public HashMap<String, ga.a<?, ?>> eY() {
                return UI;
            }

            public boolean equals(Object object) {
                if (!(object instanceof a)) {
                    return false;
                }
                if (this == object) {
                    return true;
                }
                object = (a)object;
                for (ga.a a2 : UI.values()) {
                    if (this.a(a2)) {
                        if (object.a(a2)) {
                            if (this.b(a2).equals(object.b(a2))) continue;
                            return false;
                        }
                        return false;
                    }
                    if (!object.a(a2)) continue;
                    return false;
                }
                return true;
            }

            @Override
            public /* synthetic */ Object freeze() {
                return this.jH();
            }

            @Override
            public int getLeftImageOffset() {
                return this.Wg;
            }

            @Override
            public int getTopImageOffset() {
                return this.Wh;
            }

            int getVersionCode() {
                return this.xH;
            }

            @Override
            public boolean hasLeftImageOffset() {
                return this.UJ.contains(2);
            }

            @Override
            public boolean hasTopImageOffset() {
                return this.UJ.contains(3);
            }

            public int hashCode() {
                Iterator iterator = UI.values().iterator();
                int n2 = 0;
                while (iterator.hasNext()) {
                    ga.a a2 = iterator.next();
                    if (!this.a(a2)) continue;
                    int n3 = a2.ff();
                    n2 = this.b(a2).hashCode() + (n2 + n3);
                }
                return n2;
            }

            @Override
            public boolean isDataValid() {
                return true;
            }

            public a jH() {
                return this;
            }

            Set<Integer> ja() {
                return this.UJ;
            }

            public void writeToParcel(Parcel parcel, int n2) {
                il il2 = CREATOR;
                il.a(this, parcel, n2);
            }
        }

        public static final class b
        extends ga
        implements SafeParcelable,
        Person.Cover.CoverPhoto {
            public static final im CREATOR = new im();
            private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
            private final Set<Integer> UJ;
            private int kr;
            private int ks;
            private String ro;
            private final int xH;

            static {
                UI.put("height", ga.a.g("height", 2));
                UI.put("url", ga.a.j("url", 3));
                UI.put("width", ga.a.g("width", 4));
            }

            public b() {
                this.xH = 1;
                this.UJ = new HashSet<Integer>();
            }

            b(Set<Integer> set, int n2, int n3, String string2, int n4) {
                this.UJ = set;
                this.xH = n2;
                this.ks = n3;
                this.ro = string2;
                this.kr = n4;
            }

            @Override
            protected boolean a(ga.a a2) {
                return this.UJ.contains(a2.ff());
            }

            @Override
            protected Object aq(String string2) {
                return null;
            }

            @Override
            protected boolean ar(String string2) {
                return false;
            }

            @Override
            protected Object b(ga.a a2) {
                switch (a2.ff()) {
                    default: {
                        throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
                    }
                    case 2: {
                        return this.ks;
                    }
                    case 3: {
                        return this.ro;
                    }
                    case 4: 
                }
                return this.kr;
            }

            public int describeContents() {
                im im2 = CREATOR;
                return 0;
            }

            @Override
            public HashMap<String, ga.a<?, ?>> eY() {
                return UI;
            }

            public boolean equals(Object object) {
                if (!(object instanceof b)) {
                    return false;
                }
                if (this == object) {
                    return true;
                }
                object = (b)object;
                for (ga.a a2 : UI.values()) {
                    if (this.a(a2)) {
                        if (object.a(a2)) {
                            if (this.b(a2).equals(object.b(a2))) continue;
                            return false;
                        }
                        return false;
                    }
                    if (!object.a(a2)) continue;
                    return false;
                }
                return true;
            }

            @Override
            public /* synthetic */ Object freeze() {
                return this.jI();
            }

            @Override
            public int getHeight() {
                return this.ks;
            }

            @Override
            public String getUrl() {
                return this.ro;
            }

            int getVersionCode() {
                return this.xH;
            }

            @Override
            public int getWidth() {
                return this.kr;
            }

            @Override
            public boolean hasHeight() {
                return this.UJ.contains(2);
            }

            @Override
            public boolean hasUrl() {
                return this.UJ.contains(3);
            }

            @Override
            public boolean hasWidth() {
                return this.UJ.contains(4);
            }

            public int hashCode() {
                Iterator iterator = UI.values().iterator();
                int n2 = 0;
                while (iterator.hasNext()) {
                    ga.a a2 = iterator.next();
                    if (!this.a(a2)) continue;
                    int n3 = a2.ff();
                    n2 = this.b(a2).hashCode() + (n2 + n3);
                }
                return n2;
            }

            @Override
            public boolean isDataValid() {
                return true;
            }

            public b jI() {
                return this;
            }

            Set<Integer> ja() {
                return this.UJ;
            }

            public void writeToParcel(Parcel parcel, int n2) {
                im im2 = CREATOR;
                im.a(this, parcel, n2);
            }
        }

    }

    public static final class c
    extends ga
    implements SafeParcelable,
    Person.Image {
        public static final in CREATOR = new in();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
        private final Set<Integer> UJ;
        private String ro;
        private final int xH;

        static {
            UI.put("url", ga.a.j("url", 2));
        }

        public c() {
            this.xH = 1;
            this.UJ = new HashSet<Integer>();
        }

        public c(String string2) {
            this.UJ = new HashSet<Integer>();
            this.xH = 1;
            this.ro = string2;
            this.UJ.add(2);
        }

        c(Set<Integer> set, int n2, String string2) {
            this.UJ = set;
            this.xH = n2;
            this.ro = string2;
        }

        @Override
        protected boolean a(ga.a a2) {
            return this.UJ.contains(a2.ff());
        }

        @Override
        protected Object aq(String string2) {
            return null;
        }

        @Override
        protected boolean ar(String string2) {
            return false;
        }

        @Override
        protected Object b(ga.a a2) {
            switch (a2.ff()) {
                default: {
                    throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
                }
                case 2: 
            }
            return this.ro;
        }

        public int describeContents() {
            in in2 = CREATOR;
            return 0;
        }

        @Override
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object object) {
            if (!(object instanceof c)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (c)object;
            for (ga.a a2 : UI.values()) {
                if (this.a(a2)) {
                    if (object.a(a2)) {
                        if (this.b(a2).equals(object.b(a2))) continue;
                        return false;
                    }
                    return false;
                }
                if (!object.a(a2)) continue;
                return false;
            }
            return true;
        }

        @Override
        public /* synthetic */ Object freeze() {
            return this.jJ();
        }

        @Override
        public String getUrl() {
            return this.ro;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override
        public boolean hasUrl() {
            return this.UJ.contains(2);
        }

        public int hashCode() {
            Iterator iterator = UI.values().iterator();
            int n2 = 0;
            while (iterator.hasNext()) {
                ga.a a2 = iterator.next();
                if (!this.a(a2)) continue;
                int n3 = a2.ff();
                n2 = this.b(a2).hashCode() + (n2 + n3);
            }
            return n2;
        }

        @Override
        public boolean isDataValid() {
            return true;
        }

        public c jJ() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            in in2 = CREATOR;
            in.a(this, parcel, n2);
        }
    }

    public static final class d
    extends ga
    implements SafeParcelable,
    Person.Name {
        public static final io CREATOR = new io();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
        private final Set<Integer> UJ;
        private String Vh;
        private String Vk;
        private String Wi;
        private String Wj;
        private String Wk;
        private String Wl;
        private final int xH;

        static {
            UI.put("familyName", ga.a.j("familyName", 2));
            UI.put("formatted", ga.a.j("formatted", 3));
            UI.put("givenName", ga.a.j("givenName", 4));
            UI.put("honorificPrefix", ga.a.j("honorificPrefix", 5));
            UI.put("honorificSuffix", ga.a.j("honorificSuffix", 6));
            UI.put("middleName", ga.a.j("middleName", 7));
        }

        public d() {
            this.xH = 1;
            this.UJ = new HashSet<Integer>();
        }

        d(Set<Integer> set, int n2, String string2, String string3, String string4, String string5, String string6, String string7) {
            this.UJ = set;
            this.xH = n2;
            this.Vh = string2;
            this.Wi = string3;
            this.Vk = string4;
            this.Wj = string5;
            this.Wk = string6;
            this.Wl = string7;
        }

        @Override
        protected boolean a(ga.a a2) {
            return this.UJ.contains(a2.ff());
        }

        @Override
        protected Object aq(String string2) {
            return null;
        }

        @Override
        protected boolean ar(String string2) {
            return false;
        }

        @Override
        protected Object b(ga.a a2) {
            switch (a2.ff()) {
                default: {
                    throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
                }
                case 2: {
                    return this.Vh;
                }
                case 3: {
                    return this.Wi;
                }
                case 4: {
                    return this.Vk;
                }
                case 5: {
                    return this.Wj;
                }
                case 6: {
                    return this.Wk;
                }
                case 7: 
            }
            return this.Wl;
        }

        public int describeContents() {
            io io2 = CREATOR;
            return 0;
        }

        @Override
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object object) {
            if (!(object instanceof d)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (d)object;
            for (ga.a a2 : UI.values()) {
                if (this.a(a2)) {
                    if (object.a(a2)) {
                        if (this.b(a2).equals(object.b(a2))) continue;
                        return false;
                    }
                    return false;
                }
                if (!object.a(a2)) continue;
                return false;
            }
            return true;
        }

        @Override
        public /* synthetic */ Object freeze() {
            return this.jK();
        }

        @Override
        public String getFamilyName() {
            return this.Vh;
        }

        @Override
        public String getFormatted() {
            return this.Wi;
        }

        @Override
        public String getGivenName() {
            return this.Vk;
        }

        @Override
        public String getHonorificPrefix() {
            return this.Wj;
        }

        @Override
        public String getHonorificSuffix() {
            return this.Wk;
        }

        @Override
        public String getMiddleName() {
            return this.Wl;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override
        public boolean hasFamilyName() {
            return this.UJ.contains(2);
        }

        @Override
        public boolean hasFormatted() {
            return this.UJ.contains(3);
        }

        @Override
        public boolean hasGivenName() {
            return this.UJ.contains(4);
        }

        @Override
        public boolean hasHonorificPrefix() {
            return this.UJ.contains(5);
        }

        @Override
        public boolean hasHonorificSuffix() {
            return this.UJ.contains(6);
        }

        @Override
        public boolean hasMiddleName() {
            return this.UJ.contains(7);
        }

        public int hashCode() {
            Iterator iterator = UI.values().iterator();
            int n2 = 0;
            while (iterator.hasNext()) {
                ga.a a2 = iterator.next();
                if (!this.a(a2)) continue;
                int n3 = a2.ff();
                n2 = this.b(a2).hashCode() + (n2 + n3);
            }
            return n2;
        }

        @Override
        public boolean isDataValid() {
            return true;
        }

        public d jK() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            io io2 = CREATOR;
            io.a(this, parcel, n2);
        }
    }

    public static class e {
        public static int bi(String string2) {
            if (string2.equals("person")) {
                return 0;
            }
            if (string2.equals("page")) {
                return 1;
            }
            throw new IllegalArgumentException("Unknown objectType string: " + string2);
        }
    }

    public static final class f
    extends ga
    implements SafeParcelable,
    Person.Organizations {
        public static final ip CREATOR = new ip();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
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
            UI.put("department", ga.a.j("department", 2));
            UI.put("description", ga.a.j("description", 3));
            UI.put("endDate", ga.a.j("endDate", 4));
            UI.put("location", ga.a.j("location", 5));
            UI.put("name", ga.a.j("name", 6));
            UI.put("primary", ga.a.i("primary", 7));
            UI.put("startDate", ga.a.j("startDate", 8));
            UI.put("title", ga.a.j("title", 9));
            UI.put("type", ga.a.a("type", 10, new fx().f("work", 0).f("school", 1), false));
        }

        public f() {
            this.xH = 1;
            this.UJ = new HashSet<Integer>();
        }

        f(Set<Integer> set, int n2, String string2, String string3, String string4, String string5, String string6, boolean bl2, String string7, String string8, int n3) {
            this.UJ = set;
            this.xH = n2;
            this.Wm = string2;
            this.HD = string3;
            this.Vg = string4;
            this.Wn = string5;
            this.mName = string6;
            this.Wo = bl2;
            this.Vw = string7;
            this.EB = string8;
            this.LF = n3;
        }

        @Override
        protected boolean a(ga.a a2) {
            return this.UJ.contains(a2.ff());
        }

        @Override
        protected Object aq(String string2) {
            return null;
        }

        @Override
        protected boolean ar(String string2) {
            return false;
        }

        @Override
        protected Object b(ga.a a2) {
            switch (a2.ff()) {
                default: {
                    throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
                }
                case 2: {
                    return this.Wm;
                }
                case 3: {
                    return this.HD;
                }
                case 4: {
                    return this.Vg;
                }
                case 5: {
                    return this.Wn;
                }
                case 6: {
                    return this.mName;
                }
                case 7: {
                    return this.Wo;
                }
                case 8: {
                    return this.Vw;
                }
                case 9: {
                    return this.EB;
                }
                case 10: 
            }
            return this.LF;
        }

        public int describeContents() {
            ip ip2 = CREATOR;
            return 0;
        }

        @Override
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object object) {
            if (!(object instanceof f)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (f)object;
            for (ga.a a2 : UI.values()) {
                if (this.a(a2)) {
                    if (object.a(a2)) {
                        if (this.b(a2).equals(object.b(a2))) continue;
                        return false;
                    }
                    return false;
                }
                if (!object.a(a2)) continue;
                return false;
            }
            return true;
        }

        @Override
        public /* synthetic */ Object freeze() {
            return this.jL();
        }

        @Override
        public String getDepartment() {
            return this.Wm;
        }

        @Override
        public String getDescription() {
            return this.HD;
        }

        @Override
        public String getEndDate() {
            return this.Vg;
        }

        @Override
        public String getLocation() {
            return this.Wn;
        }

        @Override
        public String getName() {
            return this.mName;
        }

        @Override
        public String getStartDate() {
            return this.Vw;
        }

        @Override
        public String getTitle() {
            return this.EB;
        }

        @Override
        public int getType() {
            return this.LF;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override
        public boolean hasDepartment() {
            return this.UJ.contains(2);
        }

        @Override
        public boolean hasDescription() {
            return this.UJ.contains(3);
        }

        @Override
        public boolean hasEndDate() {
            return this.UJ.contains(4);
        }

        @Override
        public boolean hasLocation() {
            return this.UJ.contains(5);
        }

        @Override
        public boolean hasName() {
            return this.UJ.contains(6);
        }

        @Override
        public boolean hasPrimary() {
            return this.UJ.contains(7);
        }

        @Override
        public boolean hasStartDate() {
            return this.UJ.contains(8);
        }

        @Override
        public boolean hasTitle() {
            return this.UJ.contains(9);
        }

        @Override
        public boolean hasType() {
            return this.UJ.contains(10);
        }

        public int hashCode() {
            Iterator iterator = UI.values().iterator();
            int n2 = 0;
            while (iterator.hasNext()) {
                ga.a a2 = iterator.next();
                if (!this.a(a2)) continue;
                int n3 = a2.ff();
                n2 = this.b(a2).hashCode() + (n2 + n3);
            }
            return n2;
        }

        @Override
        public boolean isDataValid() {
            return true;
        }

        @Override
        public boolean isPrimary() {
            return this.Wo;
        }

        public f jL() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            ip ip2 = CREATOR;
            ip.a(this, parcel, n2);
        }
    }

    public static final class g
    extends ga
    implements SafeParcelable,
    Person.PlacesLived {
        public static final iq CREATOR = new iq();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
        private final Set<Integer> UJ;
        private boolean Wo;
        private String mValue;
        private final int xH;

        static {
            UI.put("primary", ga.a.i("primary", 2));
            UI.put("value", ga.a.j("value", 3));
        }

        public g() {
            this.xH = 1;
            this.UJ = new HashSet<Integer>();
        }

        g(Set<Integer> set, int n2, boolean bl2, String string2) {
            this.UJ = set;
            this.xH = n2;
            this.Wo = bl2;
            this.mValue = string2;
        }

        @Override
        protected boolean a(ga.a a2) {
            return this.UJ.contains(a2.ff());
        }

        @Override
        protected Object aq(String string2) {
            return null;
        }

        @Override
        protected boolean ar(String string2) {
            return false;
        }

        @Override
        protected Object b(ga.a a2) {
            switch (a2.ff()) {
                default: {
                    throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
                }
                case 2: {
                    return this.Wo;
                }
                case 3: 
            }
            return this.mValue;
        }

        public int describeContents() {
            iq iq2 = CREATOR;
            return 0;
        }

        @Override
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object object) {
            if (!(object instanceof g)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (g)object;
            for (ga.a a2 : UI.values()) {
                if (this.a(a2)) {
                    if (object.a(a2)) {
                        if (this.b(a2).equals(object.b(a2))) continue;
                        return false;
                    }
                    return false;
                }
                if (!object.a(a2)) continue;
                return false;
            }
            return true;
        }

        @Override
        public /* synthetic */ Object freeze() {
            return this.jM();
        }

        @Override
        public String getValue() {
            return this.mValue;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override
        public boolean hasPrimary() {
            return this.UJ.contains(2);
        }

        @Override
        public boolean hasValue() {
            return this.UJ.contains(3);
        }

        public int hashCode() {
            Iterator iterator = UI.values().iterator();
            int n2 = 0;
            while (iterator.hasNext()) {
                ga.a a2 = iterator.next();
                if (!this.a(a2)) continue;
                int n3 = a2.ff();
                n2 = this.b(a2).hashCode() + (n2 + n3);
            }
            return n2;
        }

        @Override
        public boolean isDataValid() {
            return true;
        }

        @Override
        public boolean isPrimary() {
            return this.Wo;
        }

        public g jM() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            iq iq2 = CREATOR;
            iq.a(this, parcel, n2);
        }
    }

    public static final class h
    extends ga
    implements SafeParcelable,
    Person.Urls {
        public static final ir CREATOR = new ir();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
        private int LF;
        private final Set<Integer> UJ;
        private String Wp;
        private final int Wq = 4;
        private String mValue;
        private final int xH;

        static {
            UI.put("label", ga.a.j("label", 5));
            UI.put("type", ga.a.a("type", 6, new fx().f("home", 0).f("work", 1).f("blog", 2).f("profile", 3).f("other", 4).f("otherProfile", 5).f("contributor", 6).f("website", 7), false));
            UI.put("value", ga.a.j("value", 4));
        }

        public h() {
            this.xH = 2;
            this.UJ = new HashSet<Integer>();
        }

        h(Set<Integer> set, int n2, String string2, int n3, String string3, int n4) {
            this.UJ = set;
            this.xH = n2;
            this.Wp = string2;
            this.LF = n3;
            this.mValue = string3;
        }

        @Override
        protected boolean a(ga.a a2) {
            return this.UJ.contains(a2.ff());
        }

        @Override
        protected Object aq(String string2) {
            return null;
        }

        @Override
        protected boolean ar(String string2) {
            return false;
        }

        @Override
        protected Object b(ga.a a2) {
            switch (a2.ff()) {
                default: {
                    throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
                }
                case 5: {
                    return this.Wp;
                }
                case 6: {
                    return this.LF;
                }
                case 4: 
            }
            return this.mValue;
        }

        public int describeContents() {
            ir ir2 = CREATOR;
            return 0;
        }

        @Override
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object object) {
            if (!(object instanceof h)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (h)object;
            for (ga.a a2 : UI.values()) {
                if (this.a(a2)) {
                    if (object.a(a2)) {
                        if (this.b(a2).equals(object.b(a2))) continue;
                        return false;
                    }
                    return false;
                }
                if (!object.a(a2)) continue;
                return false;
            }
            return true;
        }

        @Override
        public /* synthetic */ Object freeze() {
            return this.jO();
        }

        @Override
        public String getLabel() {
            return this.Wp;
        }

        @Override
        public int getType() {
            return this.LF;
        }

        @Override
        public String getValue() {
            return this.mValue;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override
        public boolean hasLabel() {
            return this.UJ.contains(5);
        }

        @Override
        public boolean hasType() {
            return this.UJ.contains(6);
        }

        @Override
        public boolean hasValue() {
            return this.UJ.contains(4);
        }

        public int hashCode() {
            Iterator iterator = UI.values().iterator();
            int n2 = 0;
            while (iterator.hasNext()) {
                ga.a a2 = iterator.next();
                if (!this.a(a2)) continue;
                int n3 = a2.ff();
                n2 = this.b(a2).hashCode() + (n2 + n3);
            }
            return n2;
        }

        @Override
        public boolean isDataValid() {
            return true;
        }

        @Deprecated
        public int jN() {
            return 4;
        }

        public h jO() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            ir ir2 = CREATOR;
            ir.a(this, parcel, n2);
        }
    }

}

