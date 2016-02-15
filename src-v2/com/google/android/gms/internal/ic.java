/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ga;
import com.google.android.gms.internal.id;
import com.google.android.gms.plus.model.moments.ItemScope;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class ic
extends ga
implements SafeParcelable,
ItemScope {
    public static final id CREATOR = new id();
    private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
    private String HD;
    private double NX;
    private double NY;
    private String Rd;
    private final Set<Integer> UJ;
    private ic UK;
    private List<String> UL;
    private ic UM;
    private String UN;
    private String UO;
    private String UP;
    private List<ic> UQ;
    private int UR;
    private List<ic> US;
    private ic UT;
    private List<ic> UU;
    private String UV;
    private String UW;
    private ic UX;
    private String UY;
    private String UZ;
    private String VA;
    private String VB;
    private String VC;
    private String VD;
    private List<ic> Va;
    private String Vb;
    private String Vc;
    private String Vd;
    private String Ve;
    private String Vf;
    private String Vg;
    private String Vh;
    private String Vi;
    private ic Vj;
    private String Vk;
    private String Vl;
    private String Vm;
    private ic Vn;
    private ic Vo;
    private ic Vp;
    private List<ic> Vq;
    private String Vr;
    private String Vs;
    private String Vt;
    private String Vu;
    private ic Vv;
    private String Vw;
    private String Vx;
    private String Vy;
    private ic Vz;
    private String lY;
    private String mName;
    private String ro;
    private String wp;
    private final int xH;

    static {
        UI.put("about", ga.a.a("about", 2, ic.class));
        UI.put("additionalName", ga.a.k("additionalName", 3));
        UI.put("address", ga.a.a("address", 4, ic.class));
        UI.put("addressCountry", ga.a.j("addressCountry", 5));
        UI.put("addressLocality", ga.a.j("addressLocality", 6));
        UI.put("addressRegion", ga.a.j("addressRegion", 7));
        UI.put("associated_media", ga.a.b("associated_media", 8, ic.class));
        UI.put("attendeeCount", ga.a.g("attendeeCount", 9));
        UI.put("attendees", ga.a.b("attendees", 10, ic.class));
        UI.put("audio", ga.a.a("audio", 11, ic.class));
        UI.put("author", ga.a.b("author", 12, ic.class));
        UI.put("bestRating", ga.a.j("bestRating", 13));
        UI.put("birthDate", ga.a.j("birthDate", 14));
        UI.put("byArtist", ga.a.a("byArtist", 15, ic.class));
        UI.put("caption", ga.a.j("caption", 16));
        UI.put("contentSize", ga.a.j("contentSize", 17));
        UI.put("contentUrl", ga.a.j("contentUrl", 18));
        UI.put("contributor", ga.a.b("contributor", 19, ic.class));
        UI.put("dateCreated", ga.a.j("dateCreated", 20));
        UI.put("dateModified", ga.a.j("dateModified", 21));
        UI.put("datePublished", ga.a.j("datePublished", 22));
        UI.put("description", ga.a.j("description", 23));
        UI.put("duration", ga.a.j("duration", 24));
        UI.put("embedUrl", ga.a.j("embedUrl", 25));
        UI.put("endDate", ga.a.j("endDate", 26));
        UI.put("familyName", ga.a.j("familyName", 27));
        UI.put("gender", ga.a.j("gender", 28));
        UI.put("geo", ga.a.a("geo", 29, ic.class));
        UI.put("givenName", ga.a.j("givenName", 30));
        UI.put("height", ga.a.j("height", 31));
        UI.put("id", ga.a.j("id", 32));
        UI.put("image", ga.a.j("image", 33));
        UI.put("inAlbum", ga.a.a("inAlbum", 34, ic.class));
        UI.put("latitude", ga.a.h("latitude", 36));
        UI.put("location", ga.a.a("location", 37, ic.class));
        UI.put("longitude", ga.a.h("longitude", 38));
        UI.put("name", ga.a.j("name", 39));
        UI.put("partOfTVSeries", ga.a.a("partOfTVSeries", 40, ic.class));
        UI.put("performers", ga.a.b("performers", 41, ic.class));
        UI.put("playerType", ga.a.j("playerType", 42));
        UI.put("postOfficeBoxNumber", ga.a.j("postOfficeBoxNumber", 43));
        UI.put("postalCode", ga.a.j("postalCode", 44));
        UI.put("ratingValue", ga.a.j("ratingValue", 45));
        UI.put("reviewRating", ga.a.a("reviewRating", 46, ic.class));
        UI.put("startDate", ga.a.j("startDate", 47));
        UI.put("streetAddress", ga.a.j("streetAddress", 48));
        UI.put("text", ga.a.j("text", 49));
        UI.put("thumbnail", ga.a.a("thumbnail", 50, ic.class));
        UI.put("thumbnailUrl", ga.a.j("thumbnailUrl", 51));
        UI.put("tickerSymbol", ga.a.j("tickerSymbol", 52));
        UI.put("type", ga.a.j("type", 53));
        UI.put("url", ga.a.j("url", 54));
        UI.put("width", ga.a.j("width", 55));
        UI.put("worstRating", ga.a.j("worstRating", 56));
    }

    public ic() {
        this.xH = 1;
        this.UJ = new HashSet<Integer>();
    }

    ic(Set<Integer> set, int n2, ic ic2, List<String> list, ic ic3, String string2, String string3, String string4, List<ic> list2, int n3, List<ic> list3, ic ic4, List<ic> list4, String string5, String string6, ic ic5, String string7, String string8, String string9, List<ic> list5, String string10, String string11, String string12, String string13, String string14, String string15, String string16, String string17, String string18, ic ic6, String string19, String string20, String string21, String string22, ic ic7, double d2, ic ic8, double d3, String string23, ic ic9, List<ic> list6, String string24, String string25, String string26, String string27, ic ic10, String string28, String string29, String string30, ic ic11, String string31, String string32, String string33, String string34, String string35, String string36) {
        this.UJ = set;
        this.xH = n2;
        this.UK = ic2;
        this.UL = list;
        this.UM = ic3;
        this.UN = string2;
        this.UO = string3;
        this.UP = string4;
        this.UQ = list2;
        this.UR = n3;
        this.US = list3;
        this.UT = ic4;
        this.UU = list4;
        this.UV = string5;
        this.UW = string6;
        this.UX = ic5;
        this.UY = string7;
        this.UZ = string8;
        this.lY = string9;
        this.Va = list5;
        this.Vb = string10;
        this.Vc = string11;
        this.Vd = string12;
        this.HD = string13;
        this.Ve = string14;
        this.Vf = string15;
        this.Vg = string16;
        this.Vh = string17;
        this.Vi = string18;
        this.Vj = ic6;
        this.Vk = string19;
        this.Vl = string20;
        this.wp = string21;
        this.Vm = string22;
        this.Vn = ic7;
        this.NX = d2;
        this.Vo = ic8;
        this.NY = d3;
        this.mName = string23;
        this.Vp = ic9;
        this.Vq = list6;
        this.Vr = string24;
        this.Vs = string25;
        this.Vt = string26;
        this.Vu = string27;
        this.Vv = ic10;
        this.Vw = string28;
        this.Vx = string29;
        this.Vy = string30;
        this.Vz = ic11;
        this.VA = string31;
        this.VB = string32;
        this.Rd = string33;
        this.ro = string34;
        this.VC = string35;
        this.VD = string36;
    }

    public ic(Set<Integer> set, ic ic2, List<String> list, ic ic3, String string2, String string3, String string4, List<ic> list2, int n2, List<ic> list3, ic ic4, List<ic> list4, String string5, String string6, ic ic5, String string7, String string8, String string9, List<ic> list5, String string10, String string11, String string12, String string13, String string14, String string15, String string16, String string17, String string18, ic ic6, String string19, String string20, String string21, String string22, ic ic7, double d2, ic ic8, double d3, String string23, ic ic9, List<ic> list6, String string24, String string25, String string26, String string27, ic ic10, String string28, String string29, String string30, ic ic11, String string31, String string32, String string33, String string34, String string35, String string36) {
        this.UJ = set;
        this.xH = 1;
        this.UK = ic2;
        this.UL = list;
        this.UM = ic3;
        this.UN = string2;
        this.UO = string3;
        this.UP = string4;
        this.UQ = list2;
        this.UR = n2;
        this.US = list3;
        this.UT = ic4;
        this.UU = list4;
        this.UV = string5;
        this.UW = string6;
        this.UX = ic5;
        this.UY = string7;
        this.UZ = string8;
        this.lY = string9;
        this.Va = list5;
        this.Vb = string10;
        this.Vc = string11;
        this.Vd = string12;
        this.HD = string13;
        this.Ve = string14;
        this.Vf = string15;
        this.Vg = string16;
        this.Vh = string17;
        this.Vi = string18;
        this.Vj = ic6;
        this.Vk = string19;
        this.Vl = string20;
        this.wp = string21;
        this.Vm = string22;
        this.Vn = ic7;
        this.NX = d2;
        this.Vo = ic8;
        this.NY = d3;
        this.mName = string23;
        this.Vp = ic9;
        this.Vq = list6;
        this.Vr = string24;
        this.Vs = string25;
        this.Vt = string26;
        this.Vu = string27;
        this.Vv = ic10;
        this.Vw = string28;
        this.Vx = string29;
        this.Vy = string30;
        this.Vz = ic11;
        this.VA = string31;
        this.VB = string32;
        this.Rd = string33;
        this.ro = string34;
        this.VC = string35;
        this.VD = string36;
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
                return this.UK;
            }
            case 3: {
                return this.UL;
            }
            case 4: {
                return this.UM;
            }
            case 5: {
                return this.UN;
            }
            case 6: {
                return this.UO;
            }
            case 7: {
                return this.UP;
            }
            case 8: {
                return this.UQ;
            }
            case 9: {
                return this.UR;
            }
            case 10: {
                return this.US;
            }
            case 11: {
                return this.UT;
            }
            case 12: {
                return this.UU;
            }
            case 13: {
                return this.UV;
            }
            case 14: {
                return this.UW;
            }
            case 15: {
                return this.UX;
            }
            case 16: {
                return this.UY;
            }
            case 17: {
                return this.UZ;
            }
            case 18: {
                return this.lY;
            }
            case 19: {
                return this.Va;
            }
            case 20: {
                return this.Vb;
            }
            case 21: {
                return this.Vc;
            }
            case 22: {
                return this.Vd;
            }
            case 23: {
                return this.HD;
            }
            case 24: {
                return this.Ve;
            }
            case 25: {
                return this.Vf;
            }
            case 26: {
                return this.Vg;
            }
            case 27: {
                return this.Vh;
            }
            case 28: {
                return this.Vi;
            }
            case 29: {
                return this.Vj;
            }
            case 30: {
                return this.Vk;
            }
            case 31: {
                return this.Vl;
            }
            case 32: {
                return this.wp;
            }
            case 33: {
                return this.Vm;
            }
            case 34: {
                return this.Vn;
            }
            case 36: {
                return this.NX;
            }
            case 37: {
                return this.Vo;
            }
            case 38: {
                return this.NY;
            }
            case 39: {
                return this.mName;
            }
            case 40: {
                return this.Vp;
            }
            case 41: {
                return this.Vq;
            }
            case 42: {
                return this.Vr;
            }
            case 43: {
                return this.Vs;
            }
            case 44: {
                return this.Vt;
            }
            case 45: {
                return this.Vu;
            }
            case 46: {
                return this.Vv;
            }
            case 47: {
                return this.Vw;
            }
            case 48: {
                return this.Vx;
            }
            case 49: {
                return this.Vy;
            }
            case 50: {
                return this.Vz;
            }
            case 51: {
                return this.VA;
            }
            case 52: {
                return this.VB;
            }
            case 53: {
                return this.Rd;
            }
            case 54: {
                return this.ro;
            }
            case 55: {
                return this.VC;
            }
            case 56: 
        }
        return this.VD;
    }

    public int describeContents() {
        id id2 = CREATOR;
        return 0;
    }

    @Override
    public HashMap<String, ga.a<?, ?>> eY() {
        return UI;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ic)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        object = (ic)object;
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
        return this.jq();
    }

    @Override
    public ItemScope getAbout() {
        return this.UK;
    }

    @Override
    public List<String> getAdditionalName() {
        return this.UL;
    }

    @Override
    public ItemScope getAddress() {
        return this.UM;
    }

    @Override
    public String getAddressCountry() {
        return this.UN;
    }

    @Override
    public String getAddressLocality() {
        return this.UO;
    }

    @Override
    public String getAddressRegion() {
        return this.UP;
    }

    @Override
    public List<ItemScope> getAssociated_media() {
        return (ArrayList)this.UQ;
    }

    @Override
    public int getAttendeeCount() {
        return this.UR;
    }

    @Override
    public List<ItemScope> getAttendees() {
        return (ArrayList)this.US;
    }

    @Override
    public ItemScope getAudio() {
        return this.UT;
    }

    @Override
    public List<ItemScope> getAuthor() {
        return (ArrayList)this.UU;
    }

    @Override
    public String getBestRating() {
        return this.UV;
    }

    @Override
    public String getBirthDate() {
        return this.UW;
    }

    @Override
    public ItemScope getByArtist() {
        return this.UX;
    }

    @Override
    public String getCaption() {
        return this.UY;
    }

    @Override
    public String getContentSize() {
        return this.UZ;
    }

    @Override
    public String getContentUrl() {
        return this.lY;
    }

    @Override
    public List<ItemScope> getContributor() {
        return (ArrayList)this.Va;
    }

    @Override
    public String getDateCreated() {
        return this.Vb;
    }

    @Override
    public String getDateModified() {
        return this.Vc;
    }

    @Override
    public String getDatePublished() {
        return this.Vd;
    }

    @Override
    public String getDescription() {
        return this.HD;
    }

    @Override
    public String getDuration() {
        return this.Ve;
    }

    @Override
    public String getEmbedUrl() {
        return this.Vf;
    }

    @Override
    public String getEndDate() {
        return this.Vg;
    }

    @Override
    public String getFamilyName() {
        return this.Vh;
    }

    @Override
    public String getGender() {
        return this.Vi;
    }

    @Override
    public ItemScope getGeo() {
        return this.Vj;
    }

    @Override
    public String getGivenName() {
        return this.Vk;
    }

    @Override
    public String getHeight() {
        return this.Vl;
    }

    @Override
    public String getId() {
        return this.wp;
    }

    @Override
    public String getImage() {
        return this.Vm;
    }

    @Override
    public ItemScope getInAlbum() {
        return this.Vn;
    }

    @Override
    public double getLatitude() {
        return this.NX;
    }

    @Override
    public ItemScope getLocation() {
        return this.Vo;
    }

    @Override
    public double getLongitude() {
        return this.NY;
    }

    @Override
    public String getName() {
        return this.mName;
    }

    @Override
    public ItemScope getPartOfTVSeries() {
        return this.Vp;
    }

    @Override
    public List<ItemScope> getPerformers() {
        return (ArrayList)this.Vq;
    }

    @Override
    public String getPlayerType() {
        return this.Vr;
    }

    @Override
    public String getPostOfficeBoxNumber() {
        return this.Vs;
    }

    @Override
    public String getPostalCode() {
        return this.Vt;
    }

    @Override
    public String getRatingValue() {
        return this.Vu;
    }

    @Override
    public ItemScope getReviewRating() {
        return this.Vv;
    }

    @Override
    public String getStartDate() {
        return this.Vw;
    }

    @Override
    public String getStreetAddress() {
        return this.Vx;
    }

    @Override
    public String getText() {
        return this.Vy;
    }

    @Override
    public ItemScope getThumbnail() {
        return this.Vz;
    }

    @Override
    public String getThumbnailUrl() {
        return this.VA;
    }

    @Override
    public String getTickerSymbol() {
        return this.VB;
    }

    @Override
    public String getType() {
        return this.Rd;
    }

    @Override
    public String getUrl() {
        return this.ro;
    }

    int getVersionCode() {
        return this.xH;
    }

    @Override
    public String getWidth() {
        return this.VC;
    }

    @Override
    public String getWorstRating() {
        return this.VD;
    }

    @Override
    public boolean hasAbout() {
        return this.UJ.contains(2);
    }

    @Override
    public boolean hasAdditionalName() {
        return this.UJ.contains(3);
    }

    @Override
    public boolean hasAddress() {
        return this.UJ.contains(4);
    }

    @Override
    public boolean hasAddressCountry() {
        return this.UJ.contains(5);
    }

    @Override
    public boolean hasAddressLocality() {
        return this.UJ.contains(6);
    }

    @Override
    public boolean hasAddressRegion() {
        return this.UJ.contains(7);
    }

    @Override
    public boolean hasAssociated_media() {
        return this.UJ.contains(8);
    }

    @Override
    public boolean hasAttendeeCount() {
        return this.UJ.contains(9);
    }

    @Override
    public boolean hasAttendees() {
        return this.UJ.contains(10);
    }

    @Override
    public boolean hasAudio() {
        return this.UJ.contains(11);
    }

    @Override
    public boolean hasAuthor() {
        return this.UJ.contains(12);
    }

    @Override
    public boolean hasBestRating() {
        return this.UJ.contains(13);
    }

    @Override
    public boolean hasBirthDate() {
        return this.UJ.contains(14);
    }

    @Override
    public boolean hasByArtist() {
        return this.UJ.contains(15);
    }

    @Override
    public boolean hasCaption() {
        return this.UJ.contains(16);
    }

    @Override
    public boolean hasContentSize() {
        return this.UJ.contains(17);
    }

    @Override
    public boolean hasContentUrl() {
        return this.UJ.contains(18);
    }

    @Override
    public boolean hasContributor() {
        return this.UJ.contains(19);
    }

    @Override
    public boolean hasDateCreated() {
        return this.UJ.contains(20);
    }

    @Override
    public boolean hasDateModified() {
        return this.UJ.contains(21);
    }

    @Override
    public boolean hasDatePublished() {
        return this.UJ.contains(22);
    }

    @Override
    public boolean hasDescription() {
        return this.UJ.contains(23);
    }

    @Override
    public boolean hasDuration() {
        return this.UJ.contains(24);
    }

    @Override
    public boolean hasEmbedUrl() {
        return this.UJ.contains(25);
    }

    @Override
    public boolean hasEndDate() {
        return this.UJ.contains(26);
    }

    @Override
    public boolean hasFamilyName() {
        return this.UJ.contains(27);
    }

    @Override
    public boolean hasGender() {
        return this.UJ.contains(28);
    }

    @Override
    public boolean hasGeo() {
        return this.UJ.contains(29);
    }

    @Override
    public boolean hasGivenName() {
        return this.UJ.contains(30);
    }

    @Override
    public boolean hasHeight() {
        return this.UJ.contains(31);
    }

    @Override
    public boolean hasId() {
        return this.UJ.contains(32);
    }

    @Override
    public boolean hasImage() {
        return this.UJ.contains(33);
    }

    @Override
    public boolean hasInAlbum() {
        return this.UJ.contains(34);
    }

    @Override
    public boolean hasLatitude() {
        return this.UJ.contains(36);
    }

    @Override
    public boolean hasLocation() {
        return this.UJ.contains(37);
    }

    @Override
    public boolean hasLongitude() {
        return this.UJ.contains(38);
    }

    @Override
    public boolean hasName() {
        return this.UJ.contains(39);
    }

    @Override
    public boolean hasPartOfTVSeries() {
        return this.UJ.contains(40);
    }

    @Override
    public boolean hasPerformers() {
        return this.UJ.contains(41);
    }

    @Override
    public boolean hasPlayerType() {
        return this.UJ.contains(42);
    }

    @Override
    public boolean hasPostOfficeBoxNumber() {
        return this.UJ.contains(43);
    }

    @Override
    public boolean hasPostalCode() {
        return this.UJ.contains(44);
    }

    @Override
    public boolean hasRatingValue() {
        return this.UJ.contains(45);
    }

    @Override
    public boolean hasReviewRating() {
        return this.UJ.contains(46);
    }

    @Override
    public boolean hasStartDate() {
        return this.UJ.contains(47);
    }

    @Override
    public boolean hasStreetAddress() {
        return this.UJ.contains(48);
    }

    @Override
    public boolean hasText() {
        return this.UJ.contains(49);
    }

    @Override
    public boolean hasThumbnail() {
        return this.UJ.contains(50);
    }

    @Override
    public boolean hasThumbnailUrl() {
        return this.UJ.contains(51);
    }

    @Override
    public boolean hasTickerSymbol() {
        return this.UJ.contains(52);
    }

    @Override
    public boolean hasType() {
        return this.UJ.contains(53);
    }

    @Override
    public boolean hasUrl() {
        return this.UJ.contains(54);
    }

    @Override
    public boolean hasWidth() {
        return this.UJ.contains(55);
    }

    @Override
    public boolean hasWorstRating() {
        return this.UJ.contains(56);
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

    Set<Integer> ja() {
        return this.UJ;
    }

    ic jb() {
        return this.UK;
    }

    ic jc() {
        return this.UM;
    }

    List<ic> jd() {
        return this.UQ;
    }

    List<ic> je() {
        return this.US;
    }

    ic jf() {
        return this.UT;
    }

    List<ic> jg() {
        return this.UU;
    }

    ic jh() {
        return this.UX;
    }

    List<ic> ji() {
        return this.Va;
    }

    ic jj() {
        return this.Vj;
    }

    ic jk() {
        return this.Vn;
    }

    ic jl() {
        return this.Vo;
    }

    ic jm() {
        return this.Vp;
    }

    List<ic> jn() {
        return this.Vq;
    }

    ic jo() {
        return this.Vv;
    }

    ic jp() {
        return this.Vz;
    }

    public ic jq() {
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        id id2 = CREATOR;
        id.a(this, parcel, n2);
    }
}

