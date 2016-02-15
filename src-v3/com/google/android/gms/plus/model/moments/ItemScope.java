package com.google.android.gms.plus.model.moments;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.internal.ic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ItemScope extends Freezable<ItemScope> {

    public static class Builder {
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

        public Builder() {
            this.UJ = new HashSet();
        }

        public ItemScope build() {
            return new ic(this.UJ, this.UK, this.UL, this.UM, this.UN, this.UO, this.UP, this.UQ, this.UR, this.US, this.UT, this.UU, this.UV, this.UW, this.UX, this.UY, this.UZ, this.lY, this.Va, this.Vb, this.Vc, this.Vd, this.HD, this.Ve, this.Vf, this.Vg, this.Vh, this.Vi, this.Vj, this.Vk, this.Vl, this.wp, this.Vm, this.Vn, this.NX, this.Vo, this.NY, this.mName, this.Vp, this.Vq, this.Vr, this.Vs, this.Vt, this.Vu, this.Vv, this.Vw, this.Vx, this.Vy, this.Vz, this.VA, this.VB, this.Rd, this.ro, this.VC, this.VD);
        }

        public Builder setAbout(ItemScope about) {
            this.UK = (ic) about;
            this.UJ.add(Integer.valueOf(2));
            return this;
        }

        public Builder setAdditionalName(List<String> additionalName) {
            this.UL = additionalName;
            this.UJ.add(Integer.valueOf(3));
            return this;
        }

        public Builder setAddress(ItemScope address) {
            this.UM = (ic) address;
            this.UJ.add(Integer.valueOf(4));
            return this;
        }

        public Builder setAddressCountry(String addressCountry) {
            this.UN = addressCountry;
            this.UJ.add(Integer.valueOf(5));
            return this;
        }

        public Builder setAddressLocality(String addressLocality) {
            this.UO = addressLocality;
            this.UJ.add(Integer.valueOf(6));
            return this;
        }

        public Builder setAddressRegion(String addressRegion) {
            this.UP = addressRegion;
            this.UJ.add(Integer.valueOf(7));
            return this;
        }

        public Builder setAssociated_media(List<ItemScope> associated_media) {
            this.UQ = associated_media;
            this.UJ.add(Integer.valueOf(8));
            return this;
        }

        public Builder setAttendeeCount(int attendeeCount) {
            this.UR = attendeeCount;
            this.UJ.add(Integer.valueOf(9));
            return this;
        }

        public Builder setAttendees(List<ItemScope> attendees) {
            this.US = attendees;
            this.UJ.add(Integer.valueOf(10));
            return this;
        }

        public Builder setAudio(ItemScope audio) {
            this.UT = (ic) audio;
            this.UJ.add(Integer.valueOf(11));
            return this;
        }

        public Builder setAuthor(List<ItemScope> author) {
            this.UU = author;
            this.UJ.add(Integer.valueOf(12));
            return this;
        }

        public Builder setBestRating(String bestRating) {
            this.UV = bestRating;
            this.UJ.add(Integer.valueOf(13));
            return this;
        }

        public Builder setBirthDate(String birthDate) {
            this.UW = birthDate;
            this.UJ.add(Integer.valueOf(14));
            return this;
        }

        public Builder setByArtist(ItemScope byArtist) {
            this.UX = (ic) byArtist;
            this.UJ.add(Integer.valueOf(15));
            return this;
        }

        public Builder setCaption(String caption) {
            this.UY = caption;
            this.UJ.add(Integer.valueOf(16));
            return this;
        }

        public Builder setContentSize(String contentSize) {
            this.UZ = contentSize;
            this.UJ.add(Integer.valueOf(17));
            return this;
        }

        public Builder setContentUrl(String contentUrl) {
            this.lY = contentUrl;
            this.UJ.add(Integer.valueOf(18));
            return this;
        }

        public Builder setContributor(List<ItemScope> contributor) {
            this.Va = contributor;
            this.UJ.add(Integer.valueOf(19));
            return this;
        }

        public Builder setDateCreated(String dateCreated) {
            this.Vb = dateCreated;
            this.UJ.add(Integer.valueOf(20));
            return this;
        }

        public Builder setDateModified(String dateModified) {
            this.Vc = dateModified;
            this.UJ.add(Integer.valueOf(21));
            return this;
        }

        public Builder setDatePublished(String datePublished) {
            this.Vd = datePublished;
            this.UJ.add(Integer.valueOf(22));
            return this;
        }

        public Builder setDescription(String description) {
            this.HD = description;
            this.UJ.add(Integer.valueOf(23));
            return this;
        }

        public Builder setDuration(String duration) {
            this.Ve = duration;
            this.UJ.add(Integer.valueOf(24));
            return this;
        }

        public Builder setEmbedUrl(String embedUrl) {
            this.Vf = embedUrl;
            this.UJ.add(Integer.valueOf(25));
            return this;
        }

        public Builder setEndDate(String endDate) {
            this.Vg = endDate;
            this.UJ.add(Integer.valueOf(26));
            return this;
        }

        public Builder setFamilyName(String familyName) {
            this.Vh = familyName;
            this.UJ.add(Integer.valueOf(27));
            return this;
        }

        public Builder setGender(String gender) {
            this.Vi = gender;
            this.UJ.add(Integer.valueOf(28));
            return this;
        }

        public Builder setGeo(ItemScope geo) {
            this.Vj = (ic) geo;
            this.UJ.add(Integer.valueOf(29));
            return this;
        }

        public Builder setGivenName(String givenName) {
            this.Vk = givenName;
            this.UJ.add(Integer.valueOf(30));
            return this;
        }

        public Builder setHeight(String height) {
            this.Vl = height;
            this.UJ.add(Integer.valueOf(31));
            return this;
        }

        public Builder setId(String id) {
            this.wp = id;
            this.UJ.add(Integer.valueOf(32));
            return this;
        }

        public Builder setImage(String image) {
            this.Vm = image;
            this.UJ.add(Integer.valueOf(33));
            return this;
        }

        public Builder setInAlbum(ItemScope inAlbum) {
            this.Vn = (ic) inAlbum;
            this.UJ.add(Integer.valueOf(34));
            return this;
        }

        public Builder setLatitude(double latitude) {
            this.NX = latitude;
            this.UJ.add(Integer.valueOf(36));
            return this;
        }

        public Builder setLocation(ItemScope location) {
            this.Vo = (ic) location;
            this.UJ.add(Integer.valueOf(37));
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.NY = longitude;
            this.UJ.add(Integer.valueOf(38));
            return this;
        }

        public Builder setName(String name) {
            this.mName = name;
            this.UJ.add(Integer.valueOf(39));
            return this;
        }

        public Builder setPartOfTVSeries(ItemScope partOfTVSeries) {
            this.Vp = (ic) partOfTVSeries;
            this.UJ.add(Integer.valueOf(40));
            return this;
        }

        public Builder setPerformers(List<ItemScope> performers) {
            this.Vq = performers;
            this.UJ.add(Integer.valueOf(41));
            return this;
        }

        public Builder setPlayerType(String playerType) {
            this.Vr = playerType;
            this.UJ.add(Integer.valueOf(42));
            return this;
        }

        public Builder setPostOfficeBoxNumber(String postOfficeBoxNumber) {
            this.Vs = postOfficeBoxNumber;
            this.UJ.add(Integer.valueOf(43));
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.Vt = postalCode;
            this.UJ.add(Integer.valueOf(44));
            return this;
        }

        public Builder setRatingValue(String ratingValue) {
            this.Vu = ratingValue;
            this.UJ.add(Integer.valueOf(45));
            return this;
        }

        public Builder setReviewRating(ItemScope reviewRating) {
            this.Vv = (ic) reviewRating;
            this.UJ.add(Integer.valueOf(46));
            return this;
        }

        public Builder setStartDate(String startDate) {
            this.Vw = startDate;
            this.UJ.add(Integer.valueOf(47));
            return this;
        }

        public Builder setStreetAddress(String streetAddress) {
            this.Vx = streetAddress;
            this.UJ.add(Integer.valueOf(48));
            return this;
        }

        public Builder setText(String text) {
            this.Vy = text;
            this.UJ.add(Integer.valueOf(49));
            return this;
        }

        public Builder setThumbnail(ItemScope thumbnail) {
            this.Vz = (ic) thumbnail;
            this.UJ.add(Integer.valueOf(50));
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl) {
            this.VA = thumbnailUrl;
            this.UJ.add(Integer.valueOf(51));
            return this;
        }

        public Builder setTickerSymbol(String tickerSymbol) {
            this.VB = tickerSymbol;
            this.UJ.add(Integer.valueOf(52));
            return this;
        }

        public Builder setType(String type) {
            this.Rd = type;
            this.UJ.add(Integer.valueOf(53));
            return this;
        }

        public Builder setUrl(String url) {
            this.ro = url;
            this.UJ.add(Integer.valueOf(54));
            return this;
        }

        public Builder setWidth(String width) {
            this.VC = width;
            this.UJ.add(Integer.valueOf(55));
            return this;
        }

        public Builder setWorstRating(String worstRating) {
            this.VD = worstRating;
            this.UJ.add(Integer.valueOf(56));
            return this;
        }
    }

    ItemScope getAbout();

    List<String> getAdditionalName();

    ItemScope getAddress();

    String getAddressCountry();

    String getAddressLocality();

    String getAddressRegion();

    List<ItemScope> getAssociated_media();

    int getAttendeeCount();

    List<ItemScope> getAttendees();

    ItemScope getAudio();

    List<ItemScope> getAuthor();

    String getBestRating();

    String getBirthDate();

    ItemScope getByArtist();

    String getCaption();

    String getContentSize();

    String getContentUrl();

    List<ItemScope> getContributor();

    String getDateCreated();

    String getDateModified();

    String getDatePublished();

    String getDescription();

    String getDuration();

    String getEmbedUrl();

    String getEndDate();

    String getFamilyName();

    String getGender();

    ItemScope getGeo();

    String getGivenName();

    String getHeight();

    String getId();

    String getImage();

    ItemScope getInAlbum();

    double getLatitude();

    ItemScope getLocation();

    double getLongitude();

    String getName();

    ItemScope getPartOfTVSeries();

    List<ItemScope> getPerformers();

    String getPlayerType();

    String getPostOfficeBoxNumber();

    String getPostalCode();

    String getRatingValue();

    ItemScope getReviewRating();

    String getStartDate();

    String getStreetAddress();

    String getText();

    ItemScope getThumbnail();

    String getThumbnailUrl();

    String getTickerSymbol();

    String getType();

    String getUrl();

    String getWidth();

    String getWorstRating();

    boolean hasAbout();

    boolean hasAdditionalName();

    boolean hasAddress();

    boolean hasAddressCountry();

    boolean hasAddressLocality();

    boolean hasAddressRegion();

    boolean hasAssociated_media();

    boolean hasAttendeeCount();

    boolean hasAttendees();

    boolean hasAudio();

    boolean hasAuthor();

    boolean hasBestRating();

    boolean hasBirthDate();

    boolean hasByArtist();

    boolean hasCaption();

    boolean hasContentSize();

    boolean hasContentUrl();

    boolean hasContributor();

    boolean hasDateCreated();

    boolean hasDateModified();

    boolean hasDatePublished();

    boolean hasDescription();

    boolean hasDuration();

    boolean hasEmbedUrl();

    boolean hasEndDate();

    boolean hasFamilyName();

    boolean hasGender();

    boolean hasGeo();

    boolean hasGivenName();

    boolean hasHeight();

    boolean hasId();

    boolean hasImage();

    boolean hasInAlbum();

    boolean hasLatitude();

    boolean hasLocation();

    boolean hasLongitude();

    boolean hasName();

    boolean hasPartOfTVSeries();

    boolean hasPerformers();

    boolean hasPlayerType();

    boolean hasPostOfficeBoxNumber();

    boolean hasPostalCode();

    boolean hasRatingValue();

    boolean hasReviewRating();

    boolean hasStartDate();

    boolean hasStreetAddress();

    boolean hasText();

    boolean hasThumbnail();

    boolean hasThumbnailUrl();

    boolean hasTickerSymbol();

    boolean hasType();

    boolean hasUrl();

    boolean hasWidth();

    boolean hasWorstRating();
}
