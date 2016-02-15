package com.google.android.gms.drive;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.C0288c;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.ArrayList;
import java.util.List;

public final class MetadataBuffer extends DataBuffer<Metadata> {
    private static final String[] EL;
    private final String EM;
    private C1301a EN;

    /* renamed from: com.google.android.gms.drive.MetadataBuffer.a */
    private static class C1301a extends Metadata {
        private final DataHolder BB;
        private final int BE;
        private final int EO;

        public C1301a(DataHolder dataHolder, int i) {
            this.BB = dataHolder;
            this.EO = i;
            this.BE = dataHolder.m1687G(i);
        }

        protected <T> T m2653a(MetadataField<T> metadataField) {
            return metadataField.m326a(this.BB, this.EO, this.BE);
        }

        public Metadata fB() {
            MetadataBundle fT = MetadataBundle.fT();
            for (MetadataField a : C0288c.fS()) {
                a.m327a(this.BB, fT, this.EO, this.BE);
            }
            return new C1302b(fT);
        }

        public /* synthetic */ Object freeze() {
            return fB();
        }

        public boolean isDataValid() {
            return !this.BB.isClosed();
        }
    }

    static {
        List arrayList = new ArrayList();
        for (MetadataField fR : C0288c.fS()) {
            arrayList.addAll(fR.fR());
        }
        EL = (String[]) arrayList.toArray(new String[0]);
    }

    public MetadataBuffer(DataHolder dataHolder, String nextPageToken) {
        super(dataHolder);
        this.EM = nextPageToken;
    }

    public Metadata get(int row) {
        C1301a c1301a = this.EN;
        if (c1301a != null && c1301a.EO == row) {
            return c1301a;
        }
        Metadata c1301a2 = new C1301a(this.BB, row);
        this.EN = c1301a2;
        return c1301a2;
    }

    public String getNextPageToken() {
        return this.EM;
    }
}
