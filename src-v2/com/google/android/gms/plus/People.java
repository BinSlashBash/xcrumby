/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.plus;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

public interface People {
    public Person getCurrentPerson(GoogleApiClient var1);

    public PendingResult<LoadPeopleResult> load(GoogleApiClient var1, Collection<String> var2);

    public /* varargs */ PendingResult<LoadPeopleResult> load(GoogleApiClient var1, String ... var2);

    public PendingResult<LoadPeopleResult> loadConnected(GoogleApiClient var1);

    public PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient var1, int var2, String var3);

    public PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient var1, String var2);

    public static interface LoadPeopleResult
    extends Releasable,
    Result {
        public String getNextPageToken();

        public PersonBuffer getPersonBuffer();
    }

    public static interface OrderBy {
        public static final int ALPHABETICAL = 0;
        public static final int BEST = 1;
    }

}

