/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.plus.model.people;

import com.google.android.gms.common.data.Freezable;
import java.util.List;

public interface Person
extends Freezable<Person> {
    public String getAboutMe();

    public AgeRange getAgeRange();

    public String getBirthday();

    public String getBraggingRights();

    public int getCircledByCount();

    public Cover getCover();

    public String getCurrentLocation();

    public String getDisplayName();

    public int getGender();

    public String getId();

    public Image getImage();

    public String getLanguage();

    public Name getName();

    public String getNickname();

    public int getObjectType();

    public List<Organizations> getOrganizations();

    public List<PlacesLived> getPlacesLived();

    public int getPlusOneCount();

    public int getRelationshipStatus();

    public String getTagline();

    public String getUrl();

    public List<Urls> getUrls();

    public boolean hasAboutMe();

    public boolean hasAgeRange();

    public boolean hasBirthday();

    public boolean hasBraggingRights();

    public boolean hasCircledByCount();

    public boolean hasCover();

    public boolean hasCurrentLocation();

    public boolean hasDisplayName();

    public boolean hasGender();

    public boolean hasId();

    public boolean hasImage();

    public boolean hasIsPlusUser();

    public boolean hasLanguage();

    public boolean hasName();

    public boolean hasNickname();

    public boolean hasObjectType();

    public boolean hasOrganizations();

    public boolean hasPlacesLived();

    public boolean hasPlusOneCount();

    public boolean hasRelationshipStatus();

    public boolean hasTagline();

    public boolean hasUrl();

    public boolean hasUrls();

    public boolean hasVerified();

    public boolean isPlusUser();

    public boolean isVerified();

    public static interface AgeRange
    extends Freezable<AgeRange> {
        public int getMax();

        public int getMin();

        public boolean hasMax();

        public boolean hasMin();
    }

    public static interface Cover
    extends Freezable<Cover> {
        public CoverInfo getCoverInfo();

        public CoverPhoto getCoverPhoto();

        public int getLayout();

        public boolean hasCoverInfo();

        public boolean hasCoverPhoto();

        public boolean hasLayout();

        public static interface CoverInfo
        extends Freezable<CoverInfo> {
            public int getLeftImageOffset();

            public int getTopImageOffset();

            public boolean hasLeftImageOffset();

            public boolean hasTopImageOffset();
        }

        public static interface CoverPhoto
        extends Freezable<CoverPhoto> {
            public int getHeight();

            public String getUrl();

            public int getWidth();

            public boolean hasHeight();

            public boolean hasUrl();

            public boolean hasWidth();
        }

        public static final class Layout {
            public static final int BANNER = 0;

            private Layout() {
            }
        }

    }

    public static final class Gender {
        public static final int FEMALE = 1;
        public static final int MALE = 0;
        public static final int OTHER = 2;

        private Gender() {
        }
    }

    public static interface Image
    extends Freezable<Image> {
        public String getUrl();

        public boolean hasUrl();
    }

    public static interface Name
    extends Freezable<Name> {
        public String getFamilyName();

        public String getFormatted();

        public String getGivenName();

        public String getHonorificPrefix();

        public String getHonorificSuffix();

        public String getMiddleName();

        public boolean hasFamilyName();

        public boolean hasFormatted();

        public boolean hasGivenName();

        public boolean hasHonorificPrefix();

        public boolean hasHonorificSuffix();

        public boolean hasMiddleName();
    }

    public static final class ObjectType {
        public static final int PAGE = 1;
        public static final int PERSON = 0;

        private ObjectType() {
        }
    }

    public static interface Organizations
    extends Freezable<Organizations> {
        public String getDepartment();

        public String getDescription();

        public String getEndDate();

        public String getLocation();

        public String getName();

        public String getStartDate();

        public String getTitle();

        public int getType();

        public boolean hasDepartment();

        public boolean hasDescription();

        public boolean hasEndDate();

        public boolean hasLocation();

        public boolean hasName();

        public boolean hasPrimary();

        public boolean hasStartDate();

        public boolean hasTitle();

        public boolean hasType();

        public boolean isPrimary();

        public static final class Type {
            public static final int SCHOOL = 1;
            public static final int WORK = 0;

            private Type() {
            }
        }

    }

    public static interface PlacesLived
    extends Freezable<PlacesLived> {
        public String getValue();

        public boolean hasPrimary();

        public boolean hasValue();

        public boolean isPrimary();
    }

    public static final class RelationshipStatus {
        public static final int ENGAGED = 2;
        public static final int IN_A_RELATIONSHIP = 1;
        public static final int IN_CIVIL_UNION = 8;
        public static final int IN_DOMESTIC_PARTNERSHIP = 7;
        public static final int ITS_COMPLICATED = 4;
        public static final int MARRIED = 3;
        public static final int OPEN_RELATIONSHIP = 5;
        public static final int SINGLE = 0;
        public static final int WIDOWED = 6;

        private RelationshipStatus() {
        }
    }

    public static interface Urls
    extends Freezable<Urls> {
        public String getLabel();

        public int getType();

        public String getValue();

        public boolean hasLabel();

        public boolean hasType();

        public boolean hasValue();

        public static final class Type {
            public static final int CONTRIBUTOR = 6;
            public static final int OTHER = 4;
            public static final int OTHER_PROFILE = 5;
            public static final int WEBSITE = 7;

            private Type() {
            }
        }

    }

}

