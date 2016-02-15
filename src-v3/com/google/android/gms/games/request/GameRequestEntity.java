package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.internal.fo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GameRequestEntity implements SafeParcelable, GameRequest {
    public static final GameRequestEntityCreator CREATOR;
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

    static {
        CREATOR = new GameRequestEntityCreator();
    }

    GameRequestEntity(int versionCode, GameEntity game, PlayerEntity sender, byte[] data, String requestId, ArrayList<PlayerEntity> recipients, int type, long creationTimestamp, long expirationTimestamp, Bundle recipientStatuses, int status) {
        this.xH = versionCode;
        this.Lt = game;
        this.Nm = sender;
        this.Nf = data;
        this.Jo = requestId;
        this.Nn = recipients;
        this.LF = type;
        this.Mu = creationTimestamp;
        this.No = expirationTimestamp;
        this.Np = recipientStatuses;
        this.MB = status;
    }

    public GameRequestEntity(GameRequest request) {
        this.xH = 2;
        this.Lt = new GameEntity(request.getGame());
        this.Nm = new PlayerEntity(request.getSender());
        this.Jo = request.getRequestId();
        this.LF = request.getType();
        this.Mu = request.getCreationTimestamp();
        this.No = request.getExpirationTimestamp();
        this.MB = request.getStatus();
        Object data = request.getData();
        if (data == null) {
            this.Nf = null;
        } else {
            this.Nf = new byte[data.length];
            System.arraycopy(data, 0, this.Nf, 0, data.length);
        }
        List recipients = request.getRecipients();
        int size = recipients.size();
        this.Nn = new ArrayList(size);
        this.Np = new Bundle();
        for (int i = 0; i < size; i++) {
            Player player = (Player) ((Player) recipients.get(i)).freeze();
            String playerId = player.getPlayerId();
            this.Nn.add((PlayerEntity) player);
            this.Np.putInt(playerId, request.getRecipientStatus(playerId));
        }
    }

    static int m2903a(GameRequest gameRequest) {
        return fo.hashCode(gameRequest.getGame(), gameRequest.getRecipients(), gameRequest.getRequestId(), gameRequest.getSender(), m2905b(gameRequest), Integer.valueOf(gameRequest.getType()), Long.valueOf(gameRequest.getCreationTimestamp()), Long.valueOf(gameRequest.getExpirationTimestamp()));
    }

    static boolean m2904a(GameRequest gameRequest, Object obj) {
        if (!(obj instanceof GameRequest)) {
            return false;
        }
        if (gameRequest == obj) {
            return true;
        }
        GameRequest gameRequest2 = (GameRequest) obj;
        return fo.equal(gameRequest2.getGame(), gameRequest.getGame()) && fo.equal(gameRequest2.getRecipients(), gameRequest.getRecipients()) && fo.equal(gameRequest2.getRequestId(), gameRequest.getRequestId()) && fo.equal(gameRequest2.getSender(), gameRequest.getSender()) && Arrays.equals(m2905b(gameRequest2), m2905b(gameRequest)) && fo.equal(Integer.valueOf(gameRequest2.getType()), Integer.valueOf(gameRequest.getType())) && fo.equal(Long.valueOf(gameRequest2.getCreationTimestamp()), Long.valueOf(gameRequest.getCreationTimestamp())) && fo.equal(Long.valueOf(gameRequest2.getExpirationTimestamp()), Long.valueOf(gameRequest.getExpirationTimestamp()));
    }

    private static int[] m2905b(GameRequest gameRequest) {
        List recipients = gameRequest.getRecipients();
        int size = recipients.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = gameRequest.getRecipientStatus(((Player) recipients.get(i)).getPlayerId());
        }
        return iArr;
    }

    static String m2906c(GameRequest gameRequest) {
        return fo.m976e(gameRequest).m975a("Game", gameRequest.getGame()).m975a("Sender", gameRequest.getSender()).m975a("Recipients", gameRequest.getRecipients()).m975a("Data", gameRequest.getData()).m975a("RequestId", gameRequest.getRequestId()).m975a("Type", Integer.valueOf(gameRequest.getType())).m975a("CreationTimestamp", Long.valueOf(gameRequest.getCreationTimestamp())).m975a("ExpirationTimestamp", Long.valueOf(gameRequest.getExpirationTimestamp())).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return m2904a(this, obj);
    }

    public GameRequest freeze() {
        return this;
    }

    public long getCreationTimestamp() {
        return this.Mu;
    }

    public byte[] getData() {
        return this.Nf;
    }

    public long getExpirationTimestamp() {
        return this.No;
    }

    public Game getGame() {
        return this.Lt;
    }

    public int getRecipientStatus(String playerId) {
        return this.Np.getInt(playerId, 0);
    }

    public List<Player> getRecipients() {
        return new ArrayList(this.Nn);
    }

    public String getRequestId() {
        return this.Jo;
    }

    public Player getSender() {
        return this.Nm;
    }

    public int getStatus() {
        return this.MB;
    }

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
        return m2903a(this);
    }

    public boolean isConsumed(String playerId) {
        return getRecipientStatus(playerId) == 1;
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return m2906c(this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        GameRequestEntityCreator.m581a(this, dest, flags);
    }
}
