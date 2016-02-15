/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.plus.model.moments;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.internal.ic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ItemScope
extends Freezable<ItemScope> {
    public ItemScope getAbout();

    public List<String> getAdditionalName();

    public ItemScope getAddress();

    public String getAddressCountry();

    public String getAddressLocality();

    public String getAddressRegion();

    public List<ItemScope> getAssociated_media();

    public int getAttendeeCount();

    public List<ItemScope> getAttendees();

    public ItemScope getAudio();

    public List<ItemScope> getAuthor();

    public String getBestRating();

    public String getBirthDate();

    public ItemScope getByArtist();

    public String getCaption();

    public String getContentSize();

    public String getContentUrl();

    public List<ItemScope> getContributor();

    public String getDateCreated();

    public String getDateModified();

    public String getDatePublished();

    public String getDescription();

    public String getDuration();

    public String getEmbedUrl();

    public String getEndDate();

    public String getFamilyName();

    public String getGender();

    public ItemScope getGeo();

    public String getGivenName();

    public String getHeight();

    public String getId();

    public String getImage();

    public ItemScope getInAlbum();

    public double getLatitude();

    public ItemScope getLocation();

    public double getLongitude();

    public String getName();

    public ItemScope getPartOfTVSeries();

    public List<ItemScope> getPerformers();

    public String getPlayerType();

    public String getPostOfficeBoxNumber();

    public String getPostalCode();

    public String getRatingValue();

    public ItemScope getReviewRating();

    public String getStartDate();

    public String getStreetAddress();

    public String getText();

    public ItemScope getThumbnail();

    public String getThumbnailUrl();

    public String getTickerSymbol();

    public String getType();

    public String getUrl();

    public String getWidth();

    public String getWorstRating();

    public boolean hasAbout();

    public boolean hasAdditionalName();

    public boolean hasAddress();

    public boolean hasAddressCountry();

    public boolean hasAddressLocality();

    public boolean hasAddressRegion();

    public boolean hasAssociated_media();

    public boolean hasAttendeeCount();

    public boolean hasAttendees();

    public boolean hasAudio();

    public boolean hasAuthor();

    public boolean hasBestRating();

    public boolean hasBirthDate();

    public boolean hasByArtist();

    public boolean hasCaption();

    public boolean hasContentSize();

    public boolean hasContentUrl();

    public boolean hasContributor();

    public boolean hasDateCreated();

    public boolean hasDateModified();

    public boolean hasDatePublished();

    public boolean hasDescription();

    public boolean hasDuration();

    public boolean hasEmbedUrl();

    public boolean hasEndDate();

    public boolean hasFamilyName();

    public boolean hasGender();

    public boolean hasGeo();

    public boolean hasGivenName();

    public boolean hasHeight();

    public boolean hasId();

    public boolean hasImage();

    public boolean hasInAlbum();

    public boolean hasLatitude();

    public boolean hasLocation();

    public boolean hasLongitude();

    public boolean hasName();

    public boolean hasPartOfTVSeries();

    public boolean hasPerformers();

    public boolean hasPlayerType();

    public boolean hasPostOfficeBoxNumber();

    public boolean hasPostalCode();

    public boolean hasRatingValue();

    public boolean hasReviewRating();

    public boolean hasStartDate();

    public boolean hasStreetAddress();

    public boolean hasText();

    public boolean hasThumbnail();

    public boolean hasThumbnailUrl();

    public boolean hasTickerSymbol();

    public boolean hasType();

    public boolean hasUrl();

    public boolean hasWidth();

    public boolean hasWorstRating();

    public static class Builder {
        private String HD;
        private double NX;
        private double NY;
        private String Rd;
        private final Set<Integer> UJ = new HashSet<Integer>();
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

        public ItemScope build() {
            return new ic(this.UJ, this.UK, this.UL, this.UM, this.UN, this.UO, this.UP, this.UQ, this.UR, this.US, this.UT, this.UU, this.UV, this.UW, this.UX, this.UY, this.UZ, this.lY, this.Va, this.Vb, this.Vc, this.Vd, this.HD, this.Ve, this.Vf, this.Vg, this.Vh, this.Vi, this.Vj, this.Vk, this.Vl, this.wp, this.Vm, this.Vn, this.NX, this.Vo, this.NY, this.mName, this.Vp, this.Vq, this.Vr, this.Vs, this.Vt, this.Vu, this.Vv, this.Vw, this.Vx, this.Vy, this.Vz, this.VA, this.VB, this.Rd, this.ro, this.VC, this.VD);
        }

        public Builder setAbout(ItemScope itemScope) {
            this.UK = (ic)itemScope;
            this.UJ.add(2);
            return this;
        }

        public Builder setAdditionalName(List<String> list) {
            this.UL = list;
            this.UJ.add(3);
            return this;
        }

        public Builder setAddress(ItemScope itemScope) {
            this.UM = (ic)itemScope;
            this.UJ.add(4);
            return this;
        }

        public Builder setAddressCountry(String string2) {
            this.UN = string2;
            this.UJ.add(5);
            return this;
        }

        public Builder setAddressLocality(String string2) {
            this.UO = string2;
            this.UJ.add(6);
            return this;
        }

        public Builder setAddressRegion(String string2) {
            this.UP = string2;
            this.UJ.add(7);
            return this;
        }

        public Builder setAssociated_media(List<ItemScope> list) {
            this.UQ = list;
            this.UJ.add(8);
            return this;
        }

        public Builder setAttendeeCount(int n2) {
            this.UR = n2;
            this.UJ.add(9);
            return this;
        }

        public Builder setAttendees(List<ItemScope> list) {
            this.US = list;
            this.UJ.add(10);
            return this;
        }

        public Builder setAudio(ItemScope itemScope) {
            this.UT = (ic)itemScope;
            this.UJ.add(11);
            return this;
        }

        public Builder setAuthor(List<ItemScope> list) {
            this.UU = list;
            this.UJ.add(12);
            return this;
        }

        public Builder setBestRating(String string2) {
            this.UV = string2;
            this.UJ.add(13);
            return this;
        }

        public Builder setBirthDate(String string2) {
            this.UW = string2;
            this.UJ.add(14);
            return this;
        }

        public Builder setByArtist(ItemScope itemScope) {
            this.UX = (ic)itemScope;
            this.UJ.add(15);
            return this;
        }

        public Builder setCaption(String string2) {
            this.UY = string2;
            this.UJ.add(16);
            return this;
        }

        public Builder setContentSize(String string2) {
            this.UZ = string2;
            this.UJ.add(17);
            return this;
        }

        public Builder setContentUrl(String string2) {
            this.lY = string2;
            this.UJ.add(18);
            return this;
        }

        public Builder setContributor(List<ItemScope> list) {
            this.Va = list;
            this.UJ.add(19);
            return this;
        }

        public Builder setDateCreated(String string2) {
            this.Vb = string2;
            this.UJ.add(20);
            return this;
        }

        public Builder setDateModified(String string2) {
            this.Vc = string2;
            this.UJ.add(21);
            return this;
        }

        public Builder setDatePublished(String string2) {
            this.Vd = string2;
            this.UJ.add(22);
            return this;
        }

        public Builder setDescription(String string2) {
            this.HD = string2;
            this.UJ.add(23);
            return this;
        }

        public Builder setDuration(String string2) {
            this.Ve = string2;
            this.UJ.add(24);
            return this;
        }

        public Builder setEmbedUrl(String string2) {
            this.Vf = string2;
            this.UJ.add(25);
            return this;
        }

        public Builder setEndDate(String string2) {
            this.Vg = string2;
            this.UJ.add(26);
            return this;
        }

        public Builder setFamilyName(String string2) {
            this.Vh = string2;
            this.UJ.add(27);
            return this;
        }

        public Builder setGender(String string2) {
            this.Vi = string2;
            this.UJ.add(28);
            return this;
        }

        public Builder setGeo(ItemScope itemScope) {
            this.Vj = (ic)itemScope;
            this.UJ.add(29);
            return this;
        }

        public Builder setGivenName(String string2) {
            this.Vk = string2;
            this.UJ.add(30);
            return this;
        }

        public Builder setHeight(String string2) {
            this.Vl = string2;
            this.UJ.add(31);
            return this;
        }

        public Builder setId(String string2) {
            this.wp = string2;
            this.UJ.add(32);
            return this;
        }

        public Builder setImage(String string2) {
            this.Vm = string2;
            this.UJ.add(33);
            return this;
        }

        public Builder setInAlbum(ItemScope itemScope) {
            this.Vn = (ic)itemScope;
            this.UJ.add(34);
            return this;
        }

        public Builder setLatitude(double d2) {
            this.NX = d2;
            this.UJ.add(36);
            return this;
        }

        public Builder setLocation(ItemScope itemScope) {
            this.Vo = (ic)itemScope;
            this.UJ.add(37);
            return this;
        }

        public Builder setLongitude(double d2) {
            this.NY = d2;
            this.UJ.add(38);
            return this;
        }

        public Builder setName(String string2) {
            this.mName = string2;
            this.UJ.add(39);
            return this;
        }

        public Builder setPartOfTVSeries(ItemScope itemScope) {
            this.Vp = (ic)itemScope;
            this.UJ.add(40);
            return this;
        }

        public Builder setPerformers(List<ItemScope> list) {
            this.Vq = list;
            this.UJ.add(41);
            return this;
        }

        public Builder setPlayerType(String string2) {
            this.Vr = string2;
            this.UJ.add(42);
            return this;
        }

        public Builder setPostOfficeBoxNumber(String string2) {
            this.Vs = string2;
            this.UJ.add(43);
            return this;
        }

        public Builder setPostalCode(String string2) {
            this.Vt = string2;
            this.UJ.add(44);
            return this;
        }

        public Builder setRatingValue(String string2) {
            this.Vu = string2;
            this.UJ.add(45);
            return this;
        }

        public Builder setReviewRating(ItemScope itemScope) {
            this.Vv = (ic)itemScope;
            this.UJ.add(46);
            return this;
        }

        public Builder setStartDate(String string2) {
            this.Vw = string2;
            this.UJ.add(47);
            return this;
        }

        public Builder setStreetAddress(String string2) {
            this.Vx = string2;
            this.UJ.add(48);
            return this;
        }

        public Builder setText(String string2) {
            this.Vy = string2;
            this.UJ.add(49);
            return this;
        }

        public Builder setThumbnail(ItemScope itemScope) {
            this.Vz = (ic)itemScope;
            this.UJ.add(50);
            return this;
        }

        public Builder setThumbnailUrl(String string2) {
            this.VA = string2;
            this.UJ.add(51);
            return this;
        }

        public Builder setTickerSymbol(String string2) {
            this.VB = string2;
            this.UJ.add(52);
            return this;
        }

        public Builder setType(String string2) {
            this.Rd = string2;
            this.UJ.add(53);
            return this;
        }

        public Builder setUrl(String string2) {
            this.ro = string2;
            this.UJ.add(54);
            return this;
        }

        public Builder setWidth(String string2) {
            this.VC = string2;
            this.UJ.add(55);
            return this;
        }

        public Builder setWorstRating(String string2) {
            this.VD = string2;
            this.UJ.add(56);
            return this;
        }
    }

}

