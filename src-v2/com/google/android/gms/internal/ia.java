/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.e;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;

public final class ia
implements Moments {
    @Override
    public PendingResult<Moments.LoadMomentsResult> load(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(e e2) {
                e2.l(this);
            }
        });
    }

    @Override
    public PendingResult<Moments.LoadMomentsResult> load(GoogleApiClient googleApiClient, final int n2, final String string2, final Uri uri, final String string3, final String string4) {
        return googleApiClient.a(new a(){

            @Override
            protected void a(e e2) {
                e2.a(this, n2, string2, uri, string3, string4);
            }
        });
    }

    @Override
    public PendingResult<Status> remove(GoogleApiClient googleApiClient, final String string2) {
        return googleApiClient.b(new b(){

            @Override
            protected void a(e e2) {
                e2.removeMoment(string2);
                this.a(Status.Bv);
            }
        });
    }

    @Override
    public PendingResult<Status> write(GoogleApiClient googleApiClient, final Moment moment) {
        return googleApiClient.b(new c(){

            @Override
            protected void a(e e2) {
                e2.a(this, moment);
            }
        });
    }

    private static abstract class a
    extends Plus.a<Moments.LoadMomentsResult> {
        private a() {
        }

        public Moments.LoadMomentsResult aa(final Status status) {
            return new Moments.LoadMomentsResult(){

                @Override
                public MomentBuffer getMomentBuffer() {
                    return null;
                }

                @Override
                public String getNextPageToken() {
                    return null;
                }

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public String getUpdated() {
                    return null;
                }

                @Override
                public void release() {
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.aa(status);
        }

    }

    private static abstract class b
    extends Plus.a<Status> {
        private b() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

    private static abstract class c
    extends Plus.a<Status> {
        private c() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

}

