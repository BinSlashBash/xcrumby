package com.google.android.gms.identity.intents;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class UserAddressRequest implements SafeParcelable {
    public static final Creator<UserAddressRequest> CREATOR;
    List<CountrySpecification> Ny;
    private final int xH;

    public final class Builder {
        final /* synthetic */ UserAddressRequest Nz;

        private Builder(UserAddressRequest userAddressRequest) {
            this.Nz = userAddressRequest;
        }

        public Builder addAllowedCountrySpecification(CountrySpecification countrySpecification) {
            if (this.Nz.Ny == null) {
                this.Nz.Ny = new ArrayList();
            }
            this.Nz.Ny.add(countrySpecification);
            return this;
        }

        public Builder addAllowedCountrySpecifications(Collection<CountrySpecification> countrySpecifications) {
            if (this.Nz.Ny == null) {
                this.Nz.Ny = new ArrayList();
            }
            this.Nz.Ny.addAll(countrySpecifications);
            return this;
        }

        public UserAddressRequest build() {
            if (this.Nz.Ny != null) {
                this.Nz.Ny = Collections.unmodifiableList(this.Nz.Ny);
            }
            return this.Nz;
        }
    }

    static {
        CREATOR = new C0318a();
    }

    UserAddressRequest() {
        this.xH = 1;
    }

    UserAddressRequest(int versionCode, List<CountrySpecification> allowedCountrySpecifications) {
        this.xH = versionCode;
        this.Ny = allowedCountrySpecifications;
    }

    public static Builder newBuilder() {
        UserAddressRequest userAddressRequest = new UserAddressRequest();
        userAddressRequest.getClass();
        return new Builder(null);
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel out, int flags) {
        C0318a.m586a(this, out, flags);
    }
}
