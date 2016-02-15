/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.b;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.metadata.internal.c;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class MetadataBuffer
extends DataBuffer<Metadata> {
    private static final String[] EL;
    private final String EM;
    private a EN;

    static {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator iterator = c.fS().iterator();
        while (iterator.hasNext()) {
            arrayList.addAll(iterator.next().fR());
        }
        EL = arrayList.toArray(new String[0]);
    }

    public MetadataBuffer(DataHolder dataHolder, String string2) {
        super(dataHolder);
        this.EM = string2;
    }

    @Override
    public Metadata get(int n2) {
        a a2;
        block2 : {
            a a3 = this.EN;
            if (a3 != null) {
                a2 = a3;
                if (a3.EO == n2) break block2;
            }
            this.EN = a2 = new a(this.BB, n2);
        }
        return a2;
    }

    public String getNextPageToken() {
        return this.EM;
    }

    private static class a
    extends Metadata {
        private final DataHolder BB;
        private final int BE;
        private final int EO;

        public a(DataHolder dataHolder, int n2) {
            this.BB = dataHolder;
            this.EO = n2;
            this.BE = dataHolder.G(n2);
        }

        @Override
        protected <T> T a(MetadataField<T> metadataField) {
            return metadataField.a(this.BB, this.EO, this.BE);
        }

        public Metadata fB() {
            MetadataBundle metadataBundle = MetadataBundle.fT();
            Iterator iterator = c.fS().iterator();
            while (iterator.hasNext()) {
                iterator.next().a(this.BB, metadataBundle, this.EO, this.BE);
            }
            return new b(metadataBundle);
        }

        @Override
        public /* synthetic */ Object freeze() {
            return this.fB();
        }

        @Override
        public boolean isDataValid() {
            if (!this.BB.isClosed()) {
                return true;
            }
            return false;
        }
    }

}

