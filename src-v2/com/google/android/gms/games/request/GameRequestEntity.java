/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestEntityCreator;
import com.google.android.gms.internal.fo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class GameRequestEntity
implements SafeParcelable,
GameRequest {
    public static final GameRequestEntityCreator CREATOR = new GameRequestEntityCreator();
    private final String Jo;
    private final int LF;
    private final GameEntity Lt;
    private final int MB;
    private final long Mu;
    private final byte[] Nf;
    private final PlayerEntity Nm;
    private final ArrayList<PlayerEntity> Nn;
    private final long No;
    private final Bundle Np;
    private final int xH;

    GameRequestEntity(int n2, GameEntity gameEntity, PlayerEntity playerEntity, byte[] arrby, String string2, ArrayList<PlayerEntity> arrayList, int n3, long l2, long l3, Bundle bundle, int n4) {
        this.xH = n2;
        this.Lt = gameEntity;
        this.Nm = playerEntity;
        this.Nf = arrby;
        this.Jo = string2;
        this.Nn = arrayList;
        this.LF = n3;
        this.Mu = l2;
        this.No = l3;
        this.Np = bundle;
        this.MB = n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public GameRequestEntity(GameRequest gameRequest) {
        this.xH = 2;
        this.Lt = new GameEntity(gameRequest.getGame());
        this.Nm = new PlayerEntity(gameRequest.getSender());
        this.Jo = gameRequest.getRequestId();
        this.LF = gameRequest.getType();
        this.Mu = gameRequest.getCreationTimestamp();
        this.No = gameRequest.getExpirationTimestamp();
        this.MB = gameRequest.getStatus();
        Object object = gameRequest.getData();
        if (object == null) {
            this.Nf = null;
        } else {
            this.Nf = new byte[object.length];
            System.arraycopy(object, 0, this.Nf, 0, object.length);
        }
        object = gameRequest.getRecipients();
        int n2 = object.size();
        this.Nn = new ArrayList(n2);
        this.Np = new Bundle();
        int n3 = 0;
        while (n3 < n2) {
            Player player = (Player)((Player)object.get(n3)).freeze();
            String string2 = player.getPlayerId();
            this.Nn.add((PlayerEntity)player);
            this.Np.putInt(string2, gameRequest.getRecipientStatus(string2));
            ++n3;
        }
    }

    static int a(GameRequest gameRequest) {
        return fo.hashCode(gameRequest.getGame(), gameRequest.getRecipients(), gameRequest.getRequestId(), gameRequest.getSender(), GameRequestEntity.b(gameRequest), gameRequest.getType(), gameRequest.getCreationTimestamp(), gameRequest.getExpirationTimestamp());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(GameRequest gameRequest, Object object) {
        boolean bl2 = true;
        if (!(object instanceof GameRequest)) {
            return false;
        }
        boolean bl3 = bl2;
        if (gameRequest == object) return bl3;
        if (!fo.equal((object = (GameRequest)object).getGame(), gameRequest.getGame())) return false;
        if (!fo.equal(object.getRecipients(), gameRequest.getRecipients())) return false;
        if (!fo.equal(object.getRequestId(), gameRequest.getRequestId())) return false;
        if (!fo.equal(object.getSender(), gameRequest.getSender())) return false;
        if (!Arrays.equals(GameRequestEntity.b((GameRequest)object), GameRequestEntity.b(gameRequest))) return false;
        if (!fo.equal(object.getType(), gameRequest.getType())) return false;
        if (!fo.equal(object.getCreationTimestamp(), gameRequest.getCreationTimestamp())) return false;
        bl3 = bl2;
        if (fo.equal(object.getExpirationTimestamp(), gameRequest.getExpirationTimestamp())) return bl3;
        return false;
    }

    private static int[] b(GameRequest gameRequest) {
        List<Player> list = gameRequest.getRecipients();
        int n2 = list.size();
        int[] arrn = new int[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            arrn[i2] = gameRequest.getRecipientStatus(list.get(i2).getPlayerId());
        }
        return arrn;
    }

    static String c(GameRequest gameRequest) {
        return fo.e(gameRequest).a("Game", gameRequest.getGame()).a("Sender", gameRequest.getSender()).a("Recipients", gameRequest.getRecipients()).a("Data", gameRequest.getData()).a("RequestId", gameRequest.getRequestId()).a("Type", gameRequest.getType()).a("CreationTimestamp", gameRequest.getCreationTimestamp()).a("ExpirationTimestamp", gameRequest.getExpirationTimestamp()).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return GameRequestEntity.a(this, object);
    }

    @Override
    public GameRequest freeze() {
        return this;
    }

    @Override
    public long getCreationTimestamp() {
        return this.Mu;
    }

    @Override
    public byte[] getData() {
        return this.Nf;
    }

    @Override
    public long getExpirationTimestamp() {
        return this.No;
    }

    @Override
    public Game getGame() {
        return this.Lt;
    }

    @Override
    public int getRecipientStatus(String string2) {
        return this.Np.getInt(string2, 0);
    }

    @Override
    public List<Player> getRecipients() {
        return new ArrayList<Player>(this.Nn);
    }

    @Override
    public String getRequestId() {
        return this.Jo;
    }

    @Override
    public Player getSender() {
        return this.Nm;
    }

    @Override
    public int getStatus() {
        return this.MB;
    }

    @Override
    public int getType() {
        return this.LF;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public Bundle hK() {
        return this.Np;
    }

    public int hashCode() {
        return GameRequestEntity.a(this);
    }

    @Override
    public boolean isConsumed(String string2) {
        if (this.getRecipientStatus(string2) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return GameRequestEntity.c(this);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        GameRequestEntityCreator.a(this, parcel, n2);
    }
}

