/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.identity.intents;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.a;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class UserAddressRequest
implements SafeParcelable {
    public static final Parcelable.Creator<UserAddressRequest> CREATOR = new a();
    List<CountrySpecification> Ny;
    private final int xH;

    UserAddressRequest() {
        this.xH = 1;
    }

    UserAddressRequest(int n2, List<CountrySpecification> list) {
        this.xH = n2;
        this.Ny = list;
    }

    public static Builder newBuilder() {
        UserAddressRequest userAddressRequest = new UserAddressRequest();
        userAddressRequest.getClass();
        return userAddressRequest.new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }

    public final class Builder {
        private Builder() {
        }

        public Builder addAllowedCountrySpecification(CountrySpecification countrySpecification) {
            if (UserAddressRequest.this.Ny == null) {
                UserAddressRequest.this.Ny = new ArrayList<CountrySpecification>();
            }
            UserAddressRequest.this.Ny.add(countrySpecification);
            return this;
        }

        public Builder addAllowedCountrySpecifications(Collection<CountrySpecification> collection) {
            if (UserAddressRequest.this.Ny == null) {
                UserAddressRequest.this.Ny = new ArrayList<CountrySpecification>();
            }
            UserAddressRequest.this.Ny.addAll(collection);
            return this;
        }

        public UserAddressRequest build() {
            if (UserAddressRequest.this.Ny != null) {
                UserAddressRequest.this.Ny = Collections.unmodifiableList(UserAddressRequest.this.Ny);
            }
            return UserAddressRequest.this;
        }
    }

}

