/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.hn;

public final class hm
implements SafeParcelable {
    public static final hn CREATOR;
    public static final hm OH;
    public static final hm OI;
    public static final hm OJ;
    public static final hm OK;
    public static final hm OL;
    public static final hm OM;
    public static final hm ON;
    public static final hm OO;
    public static final hm OP;
    public static final hm OQ;
    public static final hm OR;
    public static final hm OS;
    public static final hm OT;
    public static final hm OU;
    public static final hm OV;
    public static final hm OW;
    public static final hm OX;
    public static final hm OY;
    public static final hm OZ;
    public static final hm PA;
    public static final hm PB;
    public static final hm PC;
    public static final hm PD;
    public static final hm PE;
    public static final hm PF;
    public static final hm PG;
    public static final hm PH;
    public static final hm PI;
    public static final hm PJ;
    public static final hm PK;
    public static final hm PL;
    public static final hm PM;
    public static final hm PN;
    public static final hm PO;
    public static final hm PP;
    public static final hm PQ;
    public static final hm PR;
    public static final hm PS;
    public static final hm PT;
    public static final hm PU;
    public static final hm PV;
    public static final hm PW;
    public static final hm PX;
    public static final hm PY;
    public static final hm PZ;
    public static final hm Pa;
    public static final hm Pb;
    public static final hm Pc;
    public static final hm Pd;
    public static final hm Pe;
    public static final hm Pf;
    public static final hm Pg;
    public static final hm Ph;
    public static final hm Pi;
    public static final hm Pj;
    public static final hm Pk;
    public static final hm Pl;
    public static final hm Pm;
    public static final hm Pn;
    public static final hm Po;
    public static final hm Pp;
    public static final hm Pq;
    public static final hm Pr;
    public static final hm Ps;
    public static final hm Pt;
    public static final hm Pu;
    public static final hm Pv;
    public static final hm Pw;
    public static final hm Px;
    public static final hm Py;
    public static final hm Pz;
    public static final hm QA;
    public static final hm QB;
    public static final hm QC;
    public static final hm QD;
    public static final hm QE;
    public static final hm QF;
    public static final hm QG;
    public static final hm QH;
    public static final hm QI;
    public static final hm QJ;
    public static final hm QK;
    public static final hm QL;
    public static final hm QM;
    public static final hm QN;
    public static final hm QO;
    public static final hm QP;
    public static final hm QQ;
    public static final hm QR;
    public static final hm QS;
    public static final hm QT;
    public static final hm QU;
    public static final hm QV;
    public static final hm QW;
    public static final hm QX;
    public static final hm QY;
    public static final hm QZ;
    public static final hm Qa;
    public static final hm Qb;
    public static final hm Qc;
    public static final hm Qd;
    public static final hm Qe;
    public static final hm Qf;
    public static final hm Qg;
    public static final hm Qh;
    public static final hm Qi;
    public static final hm Qj;
    public static final hm Qk;
    public static final hm Ql;
    public static final hm Qm;
    public static final hm Qn;
    public static final hm Qo;
    public static final hm Qp;
    public static final hm Qq;
    public static final hm Qr;
    public static final hm Qs;
    public static final hm Qt;
    public static final hm Qu;
    public static final hm Qv;
    public static final hm Qw;
    public static final hm Qx;
    public static final hm Qy;
    public static final hm Qz;
    public static final hm Ra;
    public static final hm Rb;
    public static final hm Rc;
    final String Rd;
    final int xH;

    static {
        OH = hm.aZ("accounting");
        OI = hm.aZ("airport");
        OJ = hm.aZ("amusement_park");
        OK = hm.aZ("aquarium");
        OL = hm.aZ("art_gallery");
        OM = hm.aZ("atm");
        ON = hm.aZ("bakery");
        OO = hm.aZ("bank");
        OP = hm.aZ("bar");
        OQ = hm.aZ("beauty_salon");
        OR = hm.aZ("bicycle_store");
        OS = hm.aZ("book_store");
        OT = hm.aZ("bowling_alley");
        OU = hm.aZ("bus_station");
        OV = hm.aZ("cafe");
        OW = hm.aZ("campground");
        OX = hm.aZ("car_dealer");
        OY = hm.aZ("car_rental");
        OZ = hm.aZ("car_repair");
        Pa = hm.aZ("car_wash");
        Pb = hm.aZ("casino");
        Pc = hm.aZ("cemetery");
        Pd = hm.aZ("church");
        Pe = hm.aZ("city_hall");
        Pf = hm.aZ("clothing_store");
        Pg = hm.aZ("convenience_store");
        Ph = hm.aZ("courthouse");
        Pi = hm.aZ("dentist");
        Pj = hm.aZ("department_store");
        Pk = hm.aZ("doctor");
        Pl = hm.aZ("electrician");
        Pm = hm.aZ("electronics_store");
        Pn = hm.aZ("embassy");
        Po = hm.aZ("establishment");
        Pp = hm.aZ("finance");
        Pq = hm.aZ("fire_station");
        Pr = hm.aZ("florist");
        Ps = hm.aZ("food");
        Pt = hm.aZ("funeral_home");
        Pu = hm.aZ("furniture_store");
        Pv = hm.aZ("gas_station");
        Pw = hm.aZ("general_contractor");
        Px = hm.aZ("grocery_or_supermarket");
        Py = hm.aZ("gym");
        Pz = hm.aZ("hair_care");
        PA = hm.aZ("hardware_store");
        PB = hm.aZ("health");
        PC = hm.aZ("hindu_temple");
        PD = hm.aZ("home_goods_store");
        PE = hm.aZ("hospital");
        PF = hm.aZ("insurance_agency");
        PG = hm.aZ("jewelry_store");
        PH = hm.aZ("laundry");
        PI = hm.aZ("lawyer");
        PJ = hm.aZ("library");
        PK = hm.aZ("liquor_store");
        PL = hm.aZ("local_government_office");
        PM = hm.aZ("locksmith");
        PN = hm.aZ("lodging");
        PO = hm.aZ("meal_delivery");
        PP = hm.aZ("meal_takeaway");
        PQ = hm.aZ("mosque");
        PR = hm.aZ("movie_rental");
        PS = hm.aZ("movie_theater");
        PT = hm.aZ("moving_company");
        PU = hm.aZ("museum");
        PV = hm.aZ("night_club");
        PW = hm.aZ("painter");
        PX = hm.aZ("park");
        PY = hm.aZ("parking");
        PZ = hm.aZ("pet_store");
        Qa = hm.aZ("pharmacy");
        Qb = hm.aZ("physiotherapist");
        Qc = hm.aZ("place_of_worship");
        Qd = hm.aZ("plumber");
        Qe = hm.aZ("police");
        Qf = hm.aZ("post_office");
        Qg = hm.aZ("real_estate_agency");
        Qh = hm.aZ("restaurant");
        Qi = hm.aZ("roofing_contractor");
        Qj = hm.aZ("rv_park");
        Qk = hm.aZ("school");
        Ql = hm.aZ("shoe_store");
        Qm = hm.aZ("shopping_mall");
        Qn = hm.aZ("spa");
        Qo = hm.aZ("stadium");
        Qp = hm.aZ("storage");
        Qq = hm.aZ("store");
        Qr = hm.aZ("subway_station");
        Qs = hm.aZ("synagogue");
        Qt = hm.aZ("taxi_stand");
        Qu = hm.aZ("train_station");
        Qv = hm.aZ("travel_agency");
        Qw = hm.aZ("university");
        Qx = hm.aZ("veterinary_care");
        Qy = hm.aZ("zoo");
        Qz = hm.aZ("administrative_area_level_1");
        QA = hm.aZ("administrative_area_level_2");
        QB = hm.aZ("administrative_area_level_3");
        QC = hm.aZ("colloquial_area");
        QD = hm.aZ("country");
        QE = hm.aZ("floor");
        QF = hm.aZ("geocode");
        QG = hm.aZ("intersection");
        QH = hm.aZ("locality");
        QI = hm.aZ("natural_feature");
        QJ = hm.aZ("neighborhood");
        QK = hm.aZ("political");
        QL = hm.aZ("point_of_interest");
        QM = hm.aZ("post_box");
        QN = hm.aZ("postal_code");
        QO = hm.aZ("postal_code_prefix");
        QP = hm.aZ("postal_town");
        QQ = hm.aZ("premise");
        QR = hm.aZ("room");
        QS = hm.aZ("route");
        QT = hm.aZ("street_address");
        QU = hm.aZ("sublocality");
        QV = hm.aZ("sublocality_level_1");
        QW = hm.aZ("sublocality_level_2");
        QX = hm.aZ("sublocality_level_3");
        QY = hm.aZ("sublocality_level_4");
        QZ = hm.aZ("sublocality_level_5");
        Ra = hm.aZ("subpremise");
        Rb = hm.aZ("transit_station");
        Rc = hm.aZ("other");
        CREATOR = new hn();
    }

    hm(int n2, String string2) {
        fq.ap(string2);
        this.xH = n2;
        this.Rd = string2;
    }

    public static hm aZ(String string2) {
        return new hm(0, string2);
    }

    public int describeContents() {
        hn hn2 = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (object instanceof hm && this.Rd.equals(((hm)object).Rd)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.Rd.hashCode();
    }

    public String toString() {
        return this.Rd;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        hn hn2 = CREATOR;
        hn.a(this, parcel, n2);
    }
}

