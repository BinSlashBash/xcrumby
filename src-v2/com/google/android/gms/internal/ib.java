/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.fk;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.e;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

public final class ib
implements People {
    @Override
    public Person getCurrentPerson(GoogleApiClient googleApiClient) {
        return Plus.a(googleApiClient, Plus.wx).getCurrentPerson();
    }

    @Override
    public PendingResult<People.LoadPeopleResult> load(GoogleApiClient googleApiClient, final Collection<String> collection) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(e e2) {
                e2.a(this, collection);
            }
        });
    }

    @Override
    public /* varargs */ PendingResult<People.LoadPeopleResult> load(GoogleApiClient googleApiClient, final String ... arrstring) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(e e2) {
                e2.d(this, arrstring);
            }
        });
    }

    @Override
    public PendingResult<People.LoadPeopleResult> loadConnected(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(e e2) {
                e2.m(this);
            }
        });
    }

    @Override
    public PendingResult<People.LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, final int n2, final String string2) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(e e2) {
                this.a(e2.a(this, n2, string2));
            }
        });
    }

    @Override
    public PendingResult<People.LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(e e2) {
                this.a(e2.o(this, string2));
            }
        });
    }

    private static abstract class a
    extends Plus.a<People.LoadPeopleResult> {
        private a() {
        }

        public People.LoadPeopleResult ab(final Status status) {
            return new People.LoadPeopleResult(){

                @Override
                public String getNextPageToken() {
                    return null;
                }

                @Override
                public PersonBuffer getPersonBuffer() {
                    return null;
                }

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public void release() {
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.ab(status);
        }

    }

}

