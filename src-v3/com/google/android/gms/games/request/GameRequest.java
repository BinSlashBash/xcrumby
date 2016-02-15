package com.google.android.gms.games.request;

import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Player;
import java.util.List;

public interface GameRequest extends Parcelable, Freezable<GameRequest> {
    public static final int RECIPIENT_STATUS_ACCEPTED = 1;
    public static final int RECIPIENT_STATUS_PENDING = 0;
    public static final int STATUS_ACCEPTED = 1;
    public static final int STATUS_PENDING = 0;
    public static final int TYPE_ALL = 65535;
    public static final int TYPE_GIFT = 1;
    public static final int TYPE_WISH = 2;

    long getCreationTimestamp();

    byte[] getData();

    long getExpirationTimestamp();

    Game getGame();

    int getRecipientStatus(String str);

    List<Player> getRecipients();

    String getRequestId();

    Player getSender();

    int getStatus();

    int getType();

    boolean isConsumed(String str);
}
