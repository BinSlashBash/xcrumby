package com.google.android.gms.internal;

import android.os.Parcel;
import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.ads.AdSize;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.ga.C0900a;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.zip.JSONzip;

public final class ic extends ga implements SafeParcelable, ItemScope {
    public static final id CREATOR;
    private static final HashMap<String, C0900a<?, ?>> UI;
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
        CREATOR = new id();
        UI = new HashMap();
        UI.put("about", C0900a.m2224a("about", 2, ic.class));
        UI.put("additionalName", C0900a.m2231k("additionalName", 3));
        UI.put("address", C0900a.m2224a("address", 4, ic.class));
        UI.put("addressCountry", C0900a.m2230j("addressCountry", 5));
        UI.put("addressLocality", C0900a.m2230j("addressLocality", 6));
        UI.put("addressRegion", C0900a.m2230j("addressRegion", 7));
        UI.put("associated_media", C0900a.m2225b("associated_media", 8, ic.class));
        UI.put("attendeeCount", C0900a.m2227g("attendeeCount", 9));
        UI.put("attendees", C0900a.m2225b("attendees", 10, ic.class));
        UI.put("audio", C0900a.m2224a("audio", 11, ic.class));
        UI.put("author", C0900a.m2225b("author", 12, ic.class));
        UI.put("bestRating", C0900a.m2230j("bestRating", 13));
        UI.put("birthDate", C0900a.m2230j("birthDate", 14));
        UI.put("byArtist", C0900a.m2224a("byArtist", 15, ic.class));
        UI.put("caption", C0900a.m2230j("caption", 16));
        UI.put("contentSize", C0900a.m2230j("contentSize", 17));
        UI.put("contentUrl", C0900a.m2230j("contentUrl", 18));
        UI.put("contributor", C0900a.m2225b("contributor", 19, ic.class));
        UI.put("dateCreated", C0900a.m2230j("dateCreated", 20));
        UI.put("dateModified", C0900a.m2230j("dateModified", 21));
        UI.put("datePublished", C0900a.m2230j("datePublished", 22));
        UI.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, C0900a.m2230j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, 23));
        UI.put("duration", C0900a.m2230j("duration", 24));
        UI.put("embedUrl", C0900a.m2230j("embedUrl", 25));
        UI.put("endDate", C0900a.m2230j("endDate", 26));
        UI.put("familyName", C0900a.m2230j("familyName", 27));
        UI.put("gender", C0900a.m2230j("gender", 28));
        UI.put("geo", C0900a.m2224a("geo", 29, ic.class));
        UI.put("givenName", C0900a.m2230j("givenName", 30));
        UI.put("height", C0900a.m2230j("height", 31));
        UI.put("id", C0900a.m2230j("id", 32));
        UI.put("image", C0900a.m2230j("image", 33));
        UI.put("inAlbum", C0900a.m2224a("inAlbum", 34, ic.class));
        UI.put("latitude", C0900a.m2228h("latitude", 36));
        UI.put("location", C0900a.m2224a("location", 37, ic.class));
        UI.put("longitude", C0900a.m2228h("longitude", 38));
        UI.put("name", C0900a.m2230j("name", 39));
        UI.put("partOfTVSeries", C0900a.m2224a("partOfTVSeries", 40, ic.class));
        UI.put("performers", C0900a.m2225b("performers", 41, ic.class));
        UI.put("playerType", C0900a.m2230j("playerType", 42));
        UI.put("postOfficeBoxNumber", C0900a.m2230j("postOfficeBoxNumber", 43));
        UI.put("postalCode", C0900a.m2230j("postalCode", 44));
        UI.put("ratingValue", C0900a.m2230j("ratingValue", 45));
        UI.put("reviewRating", C0900a.m2224a("reviewRating", 46, ic.class));
        UI.put("startDate", C0900a.m2230j("startDate", 47));
        UI.put("streetAddress", C0900a.m2230j("streetAddress", 48));
        UI.put("text", C0900a.m2230j("text", 49));
        UI.put("thumbnail", C0900a.m2224a("thumbnail", 50, ic.class));
        UI.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, C0900a.m2230j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, 51));
        UI.put("tickerSymbol", C0900a.m2230j("tickerSymbol", 52));
        UI.put("type", C0900a.m2230j("type", 53));
        UI.put(PlusShare.KEY_CALL_TO_ACTION_URL, C0900a.m2230j(PlusShare.KEY_CALL_TO_ACTION_URL, 54));
        UI.put("width", C0900a.m2230j("width", 55));
        UI.put("worstRating", C0900a.m2230j("worstRating", 56));
    }

    public ic() {
        this.xH = 1;
        this.UJ = new HashSet();
    }

    ic(Set<Integer> set, int i, ic icVar, List<String> list, ic icVar2, String str, String str2, String str3, List<ic> list2, int i2, List<ic> list3, ic icVar3, List<ic> list4, String str4, String str5, ic icVar4, String str6, String str7, String str8, List<ic> list5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, ic icVar5, String str18, String str19, String str20, String str21, ic icVar6, double d, ic icVar7, double d2, String str22, ic icVar8, List<ic> list6, String str23, String str24, String str25, String str26, ic icVar9, String str27, String str28, String str29, ic icVar10, String str30, String str31, String str32, String str33, String str34, String str35) {
        this.UJ = set;
        this.xH = i;
        this.UK = icVar;
        this.UL = list;
        this.UM = icVar2;
        this.UN = str;
        this.UO = str2;
        this.UP = str3;
        this.UQ = list2;
        this.UR = i2;
        this.US = list3;
        this.UT = icVar3;
        this.UU = list4;
        this.UV = str4;
        this.UW = str5;
        this.UX = icVar4;
        this.UY = str6;
        this.UZ = str7;
        this.lY = str8;
        this.Va = list5;
        this.Vb = str9;
        this.Vc = str10;
        this.Vd = str11;
        this.HD = str12;
        this.Ve = str13;
        this.Vf = str14;
        this.Vg = str15;
        this.Vh = str16;
        this.Vi = str17;
        this.Vj = icVar5;
        this.Vk = str18;
        this.Vl = str19;
        this.wp = str20;
        this.Vm = str21;
        this.Vn = icVar6;
        this.NX = d;
        this.Vo = icVar7;
        this.NY = d2;
        this.mName = str22;
        this.Vp = icVar8;
        this.Vq = list6;
        this.Vr = str23;
        this.Vs = str24;
        this.Vt = str25;
        this.Vu = str26;
        this.Vv = icVar9;
        this.Vw = str27;
        this.Vx = str28;
        this.Vy = str29;
        this.Vz = icVar10;
        this.VA = str30;
        this.VB = str31;
        this.Rd = str32;
        this.ro = str33;
        this.VC = str34;
        this.VD = str35;
    }

    public ic(Set<Integer> set, ic icVar, List<String> list, ic icVar2, String str, String str2, String str3, List<ic> list2, int i, List<ic> list3, ic icVar3, List<ic> list4, String str4, String str5, ic icVar4, String str6, String str7, String str8, List<ic> list5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, ic icVar5, String str18, String str19, String str20, String str21, ic icVar6, double d, ic icVar7, double d2, String str22, ic icVar8, List<ic> list6, String str23, String str24, String str25, String str26, ic icVar9, String str27, String str28, String str29, ic icVar10, String str30, String str31, String str32, String str33, String str34, String str35) {
        this.UJ = set;
        this.xH = 1;
        this.UK = icVar;
        this.UL = list;
        this.UM = icVar2;
        this.UN = str;
        this.UO = str2;
        this.UP = str3;
        this.UQ = list2;
        this.UR = i;
        this.US = list3;
        this.UT = icVar3;
        this.UU = list4;
        this.UV = str4;
        this.UW = str5;
        this.UX = icVar4;
        this.UY = str6;
        this.UZ = str7;
        this.lY = str8;
        this.Va = list5;
        this.Vb = str9;
        this.Vc = str10;
        this.Vd = str11;
        this.HD = str12;
        this.Ve = str13;
        this.Vf = str14;
        this.Vg = str15;
        this.Vh = str16;
        this.Vi = str17;
        this.Vj = icVar5;
        this.Vk = str18;
        this.Vl = str19;
        this.wp = str20;
        this.Vm = str21;
        this.Vn = icVar6;
        this.NX = d;
        this.Vo = icVar7;
        this.NY = d2;
        this.mName = str22;
        this.Vp = icVar8;
        this.Vq = list6;
        this.Vr = str23;
        this.Vs = str24;
        this.Vt = str25;
        this.Vu = str26;
        this.Vv = icVar9;
        this.Vw = str27;
        this.Vx = str28;
        this.Vy = str29;
        this.Vz = icVar10;
        this.VA = str30;
        this.VB = str31;
        this.Rd = str32;
        this.ro = str33;
        this.VC = str34;
        this.VD = str35;
    }

    protected boolean m3090a(C0900a c0900a) {
        return this.UJ.contains(Integer.valueOf(c0900a.ff()));
    }

    protected Object aq(String str) {
        return null;
    }

    protected boolean ar(String str) {
        return false;
    }

    protected Object m3091b(C0900a c0900a) {
        switch (c0900a.ff()) {
            case Std.STD_URL /*2*/:
                return this.UK;
            case Std.STD_URI /*3*/:
                return this.UL;
            case Std.STD_CLASS /*4*/:
                return this.UM;
            case Std.STD_JAVA_TYPE /*5*/:
                return this.UN;
            case Std.STD_CURRENCY /*6*/:
                return this.UO;
            case Std.STD_PATTERN /*7*/:
                return this.UP;
            case Std.STD_LOCALE /*8*/:
                return this.UQ;
            case Std.STD_CHARSET /*9*/:
                return Integer.valueOf(this.UR);
            case Std.STD_TIME_ZONE /*10*/:
                return this.US;
            case Std.STD_INET_ADDRESS /*11*/:
                return this.UT;
            case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                return this.UU;
            case CommonStatusCodes.ERROR /*13*/:
                return this.UV;
            case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                return this.UW;
            case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                return this.UX;
            case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                return this.UY;
            case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                return this.UZ;
            case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                return this.lY;
            case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                return this.Va;
            case HttpEngine.MAX_REDIRECTS /*20*/:
                return this.Vb;
            case C0065R.styleable.TwoWayView_android_visibility /*21*/:
                return this.Vc;
            case C0065R.styleable.TwoWayView_android_fitsSystemWindows /*22*/:
                return this.Vd;
            case C0065R.styleable.TwoWayView_android_scrollbars /*23*/:
                return this.HD;
            case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                return this.Ve;
            case C0065R.styleable.TwoWayView_android_fadingEdgeLength /*25*/:
                return this.Vf;
            case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                return this.Vg;
            case C0065R.styleable.TwoWayView_android_nextFocusRight /*27*/:
                return this.Vh;
            case C0065R.styleable.TwoWayView_android_nextFocusUp /*28*/:
                return this.Vi;
            case C0065R.styleable.TwoWayView_android_nextFocusDown /*29*/:
                return this.Vj;
            case C0065R.styleable.TwoWayView_android_clickable /*30*/:
                return this.Vk;
            case C0065R.styleable.TwoWayView_android_longClickable /*31*/:
                return this.Vl;
            case TransportMediator.FLAG_KEY_MEDIA_STOP /*32*/:
                return this.wp;
            case CharsToNameCanonicalizer.HASH_MULT /*33*/:
                return this.Vm;
            case C0065R.styleable.TwoWayView_android_duplicateParentState /*34*/:
                return this.Vn;
            case C0065R.styleable.TwoWayView_android_drawSelectorOnTop /*36*/:
                return Double.valueOf(this.NX);
            case C0065R.styleable.TwoWayView_android_choiceMode /*37*/:
                return this.Vo;
            case C0065R.styleable.TwoWayView_android_minWidth /*38*/:
                return Double.valueOf(this.NY);
            case C0065R.styleable.TwoWayView_android_minHeight /*39*/:
                return this.mName;
            case JSONzip.substringLimit /*40*/:
                return this.Vp;
            case C0065R.styleable.TwoWayView_android_keepScreenOn /*41*/:
                return this.Vq;
            case C0065R.styleable.TwoWayView_android_isScrollContainer /*42*/:
                return this.Vr;
            case C0065R.styleable.TwoWayView_android_hapticFeedbackEnabled /*43*/:
                return this.Vs;
            case C0065R.styleable.TwoWayView_android_onClick /*44*/:
                return this.Vt;
            case C0065R.styleable.TwoWayView_android_contentDescription /*45*/:
                return this.Vu;
            case C0065R.styleable.TwoWayView_android_scrollbarFadeDuration /*46*/:
                return this.Vv;
            case C0065R.styleable.TwoWayView_android_scrollbarDefaultDelayBeforeFade /*47*/:
                return this.Vw;
            case C0065R.styleable.TwoWayView_android_fadeScrollbars /*48*/:
                return this.Vx;
            case C0065R.styleable.TwoWayView_android_overScrollMode /*49*/:
                return this.Vy;
            case AdSize.PORTRAIT_AD_HEIGHT /*50*/:
                return this.Vz;
            case C0065R.styleable.TwoWayView_android_alpha /*51*/:
                return this.VA;
            case C0065R.styleable.TwoWayView_android_transformPivotX /*52*/:
                return this.VB;
            case C0065R.styleable.TwoWayView_android_transformPivotY /*53*/:
                return this.Rd;
            case C0065R.styleable.TwoWayView_android_translationX /*54*/:
                return this.ro;
            case C0065R.styleable.TwoWayView_android_translationY /*55*/:
                return this.VC;
            case C0065R.styleable.TwoWayView_android_scaleX /*56*/:
                return this.VD;
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
        }
    }

    public int describeContents() {
        id idVar = CREATOR;
        return 0;
    }

    public HashMap<String, C0900a<?, ?>> eY() {
        return UI;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ic)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ic icVar = (ic) obj;
        for (C0900a c0900a : UI.values()) {
            if (m3090a(c0900a)) {
                if (!icVar.m3090a(c0900a)) {
                    return false;
                }
                if (!m3091b(c0900a).equals(icVar.m3091b(c0900a))) {
                    return false;
                }
            } else if (icVar.m3090a(c0900a)) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object freeze() {
        return jq();
    }

    public ItemScope getAbout() {
        return this.UK;
    }

    public List<String> getAdditionalName() {
        return this.UL;
    }

    public ItemScope getAddress() {
        return this.UM;
    }

    public String getAddressCountry() {
        return this.UN;
    }

    public String getAddressLocality() {
        return this.UO;
    }

    public String getAddressRegion() {
        return this.UP;
    }

    public List<ItemScope> getAssociated_media() {
        return (ArrayList) this.UQ;
    }

    public int getAttendeeCount() {
        return this.UR;
    }

    public List<ItemScope> getAttendees() {
        return (ArrayList) this.US;
    }

    public ItemScope getAudio() {
        return this.UT;
    }

    public List<ItemScope> getAuthor() {
        return (ArrayList) this.UU;
    }

    public String getBestRating() {
        return this.UV;
    }

    public String getBirthDate() {
        return this.UW;
    }

    public ItemScope getByArtist() {
        return this.UX;
    }

    public String getCaption() {
        return this.UY;
    }

    public String getContentSize() {
        return this.UZ;
    }

    public String getContentUrl() {
        return this.lY;
    }

    public List<ItemScope> getContributor() {
        return (ArrayList) this.Va;
    }

    public String getDateCreated() {
        return this.Vb;
    }

    public String getDateModified() {
        return this.Vc;
    }

    public String getDatePublished() {
        return this.Vd;
    }

    public String getDescription() {
        return this.HD;
    }

    public String getDuration() {
        return this.Ve;
    }

    public String getEmbedUrl() {
        return this.Vf;
    }

    public String getEndDate() {
        return this.Vg;
    }

    public String getFamilyName() {
        return this.Vh;
    }

    public String getGender() {
        return this.Vi;
    }

    public ItemScope getGeo() {
        return this.Vj;
    }

    public String getGivenName() {
        return this.Vk;
    }

    public String getHeight() {
        return this.Vl;
    }

    public String getId() {
        return this.wp;
    }

    public String getImage() {
        return this.Vm;
    }

    public ItemScope getInAlbum() {
        return this.Vn;
    }

    public double getLatitude() {
        return this.NX;
    }

    public ItemScope getLocation() {
        return this.Vo;
    }

    public double getLongitude() {
        return this.NY;
    }

    public String getName() {
        return this.mName;
    }

    public ItemScope getPartOfTVSeries() {
        return this.Vp;
    }

    public List<ItemScope> getPerformers() {
        return (ArrayList) this.Vq;
    }

    public String getPlayerType() {
        return this.Vr;
    }

    public String getPostOfficeBoxNumber() {
        return this.Vs;
    }

    public String getPostalCode() {
        return this.Vt;
    }

    public String getRatingValue() {
        return this.Vu;
    }

    public ItemScope getReviewRating() {
        return this.Vv;
    }

    public String getStartDate() {
        return this.Vw;
    }

    public String getStreetAddress() {
        return this.Vx;
    }

    public String getText() {
        return this.Vy;
    }

    public ItemScope getThumbnail() {
        return this.Vz;
    }

    public String getThumbnailUrl() {
        return this.VA;
    }

    public String getTickerSymbol() {
        return this.VB;
    }

    public String getType() {
        return this.Rd;
    }

    public String getUrl() {
        return this.ro;
    }

    int getVersionCode() {
        return this.xH;
    }

    public String getWidth() {
        return this.VC;
    }

    public String getWorstRating() {
        return this.VD;
    }

    public boolean hasAbout() {
        return this.UJ.contains(Integer.valueOf(2));
    }

    public boolean hasAdditionalName() {
        return this.UJ.contains(Integer.valueOf(3));
    }

    public boolean hasAddress() {
        return this.UJ.contains(Integer.valueOf(4));
    }

    public boolean hasAddressCountry() {
        return this.UJ.contains(Integer.valueOf(5));
    }

    public boolean hasAddressLocality() {
        return this.UJ.contains(Integer.valueOf(6));
    }

    public boolean hasAddressRegion() {
        return this.UJ.contains(Integer.valueOf(7));
    }

    public boolean hasAssociated_media() {
        return this.UJ.contains(Integer.valueOf(8));
    }

    public boolean hasAttendeeCount() {
        return this.UJ.contains(Integer.valueOf(9));
    }

    public boolean hasAttendees() {
        return this.UJ.contains(Integer.valueOf(10));
    }

    public boolean hasAudio() {
        return this.UJ.contains(Integer.valueOf(11));
    }

    public boolean hasAuthor() {
        return this.UJ.contains(Integer.valueOf(12));
    }

    public boolean hasBestRating() {
        return this.UJ.contains(Integer.valueOf(13));
    }

    public boolean hasBirthDate() {
        return this.UJ.contains(Integer.valueOf(14));
    }

    public boolean hasByArtist() {
        return this.UJ.contains(Integer.valueOf(15));
    }

    public boolean hasCaption() {
        return this.UJ.contains(Integer.valueOf(16));
    }

    public boolean hasContentSize() {
        return this.UJ.contains(Integer.valueOf(17));
    }

    public boolean hasContentUrl() {
        return this.UJ.contains(Integer.valueOf(18));
    }

    public boolean hasContributor() {
        return this.UJ.contains(Integer.valueOf(19));
    }

    public boolean hasDateCreated() {
        return this.UJ.contains(Integer.valueOf(20));
    }

    public boolean hasDateModified() {
        return this.UJ.contains(Integer.valueOf(21));
    }

    public boolean hasDatePublished() {
        return this.UJ.contains(Integer.valueOf(22));
    }

    public boolean hasDescription() {
        return this.UJ.contains(Integer.valueOf(23));
    }

    public boolean hasDuration() {
        return this.UJ.contains(Integer.valueOf(24));
    }

    public boolean hasEmbedUrl() {
        return this.UJ.contains(Integer.valueOf(25));
    }

    public boolean hasEndDate() {
        return this.UJ.contains(Integer.valueOf(26));
    }

    public boolean hasFamilyName() {
        return this.UJ.contains(Integer.valueOf(27));
    }

    public boolean hasGender() {
        return this.UJ.contains(Integer.valueOf(28));
    }

    public boolean hasGeo() {
        return this.UJ.contains(Integer.valueOf(29));
    }

    public boolean hasGivenName() {
        return this.UJ.contains(Integer.valueOf(30));
    }

    public boolean hasHeight() {
        return this.UJ.contains(Integer.valueOf(31));
    }

    public boolean hasId() {
        return this.UJ.contains(Integer.valueOf(32));
    }

    public boolean hasImage() {
        return this.UJ.contains(Integer.valueOf(33));
    }

    public boolean hasInAlbum() {
        return this.UJ.contains(Integer.valueOf(34));
    }

    public boolean hasLatitude() {
        return this.UJ.contains(Integer.valueOf(36));
    }

    public boolean hasLocation() {
        return this.UJ.contains(Integer.valueOf(37));
    }

    public boolean hasLongitude() {
        return this.UJ.contains(Integer.valueOf(38));
    }

    public boolean hasName() {
        return this.UJ.contains(Integer.valueOf(39));
    }

    public boolean hasPartOfTVSeries() {
        return this.UJ.contains(Integer.valueOf(40));
    }

    public boolean hasPerformers() {
        return this.UJ.contains(Integer.valueOf(41));
    }

    public boolean hasPlayerType() {
        return this.UJ.contains(Integer.valueOf(42));
    }

    public boolean hasPostOfficeBoxNumber() {
        return this.UJ.contains(Integer.valueOf(43));
    }

    public boolean hasPostalCode() {
        return this.UJ.contains(Integer.valueOf(44));
    }

    public boolean hasRatingValue() {
        return this.UJ.contains(Integer.valueOf(45));
    }

    public boolean hasReviewRating() {
        return this.UJ.contains(Integer.valueOf(46));
    }

    public boolean hasStartDate() {
        return this.UJ.contains(Integer.valueOf(47));
    }

    public boolean hasStreetAddress() {
        return this.UJ.contains(Integer.valueOf(48));
    }

    public boolean hasText() {
        return this.UJ.contains(Integer.valueOf(49));
    }

    public boolean hasThumbnail() {
        return this.UJ.contains(Integer.valueOf(50));
    }

    public boolean hasThumbnailUrl() {
        return this.UJ.contains(Integer.valueOf(51));
    }

    public boolean hasTickerSymbol() {
        return this.UJ.contains(Integer.valueOf(52));
    }

    public boolean hasType() {
        return this.UJ.contains(Integer.valueOf(53));
    }

    public boolean hasUrl() {
        return this.UJ.contains(Integer.valueOf(54));
    }

    public boolean hasWidth() {
        return this.UJ.contains(Integer.valueOf(55));
    }

    public boolean hasWorstRating() {
        return this.UJ.contains(Integer.valueOf(56));
    }

    public int hashCode() {
        int i = 0;
        for (C0900a c0900a : UI.values()) {
            int hashCode;
            if (m3090a(c0900a)) {
                hashCode = m3091b(c0900a).hashCode() + (i + c0900a.ff());
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

    public void writeToParcel(Parcel out, int flags) {
        id idVar = CREATOR;
        id.m1077a(this, out, flags);
    }
}
