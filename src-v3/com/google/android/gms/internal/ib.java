package com.google.android.gms.internal;

import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.Plus.C1500a;
import com.google.android.gms.plus.internal.C1415e;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

public final class ib implements People {

    /* renamed from: com.google.android.gms.internal.ib.a */
    private static abstract class C1548a extends C1500a<LoadPeopleResult> {

        /* renamed from: com.google.android.gms.internal.ib.a.1 */
        class C13831 implements LoadPeopleResult {
            final /* synthetic */ C1548a UH;
            final /* synthetic */ Status wz;

            C13831(C1548a c1548a, Status status) {
                this.UH = c1548a;
                this.wz = status;
            }

            public String getNextPageToken() {
                return null;
            }

            public PersonBuffer getPersonBuffer() {
                return null;
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private C1548a() {
        }

        public LoadPeopleResult ab(Status status) {
            return new C13831(this, status);
        }

        public /* synthetic */ Result m3583d(Status status) {
            return ab(status);
        }
    }

    /* renamed from: com.google.android.gms.internal.ib.1 */
    class C16581 extends C1548a {
        final /* synthetic */ int UD;
        final /* synthetic */ ib UE;
        final /* synthetic */ String Uw;

        C16581(ib ibVar, int i, String str) {
            this.UE = ibVar;
            this.UD = i;
            this.Uw = str;
            super();
        }

        protected void m3849a(C1415e c1415e) {
            m1661a(c1415e.m3193a((C0244d) this, this.UD, this.Uw));
        }
    }

    /* renamed from: com.google.android.gms.internal.ib.2 */
    class C16592 extends C1548a {
        final /* synthetic */ ib UE;
        final /* synthetic */ String Uw;

        C16592(ib ibVar, String str) {
            this.UE = ibVar;
            this.Uw = str;
            super();
        }

        protected void m3851a(C1415e c1415e) {
            m1661a(c1415e.m3203o(this, this.Uw));
        }
    }

    /* renamed from: com.google.android.gms.internal.ib.3 */
    class C16603 extends C1548a {
        final /* synthetic */ ib UE;

        C16603(ib ibVar) {
            this.UE = ibVar;
            super();
        }

        protected void m3853a(C1415e c1415e) {
            c1415e.m3201m(this);
        }
    }

    /* renamed from: com.google.android.gms.internal.ib.4 */
    class C16614 extends C1548a {
        final /* synthetic */ ib UE;
        final /* synthetic */ Collection UF;

        C16614(ib ibVar, Collection collection) {
            this.UE = ibVar;
            this.UF = collection;
            super();
        }

        protected void m3855a(C1415e c1415e) {
            c1415e.m3197a((C0244d) this, this.UF);
        }
    }

    /* renamed from: com.google.android.gms.internal.ib.5 */
    class C16625 extends C1548a {
        final /* synthetic */ ib UE;
        final /* synthetic */ String[] UG;

        C16625(ib ibVar, String[] strArr) {
            this.UE = ibVar;
            this.UG = strArr;
            super();
        }

        protected void m3857a(C1415e c1415e) {
            c1415e.m3199d(this, this.UG);
        }
    }

    public Person getCurrentPerson(GoogleApiClient googleApiClient) {
        return Plus.m1292a(googleApiClient, Plus.wx).getCurrentPerson();
    }

    public PendingResult<LoadPeopleResult> load(GoogleApiClient googleApiClient, Collection<String> personIds) {
        return googleApiClient.m124a(new C16614(this, personIds));
    }

    public PendingResult<LoadPeopleResult> load(GoogleApiClient googleApiClient, String... personIds) {
        return googleApiClient.m124a(new C16625(this, personIds));
    }

    public PendingResult<LoadPeopleResult> loadConnected(GoogleApiClient googleApiClient) {
        return googleApiClient.m124a(new C16603(this));
    }

    public PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, int orderBy, String pageToken) {
        return googleApiClient.m124a(new C16581(this, orderBy, pageToken));
    }

    public PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, String pageToken) {
        return googleApiClient.m124a(new C16592(this, pageToken));
    }
}
