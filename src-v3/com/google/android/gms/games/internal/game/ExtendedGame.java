package com.google.android.gms.games.internal.game;

import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Game;
import java.util.ArrayList;

public interface ExtendedGame extends Parcelable, Freezable<ExtendedGame> {
    ArrayList<GameBadge> gW();

    int gX();

    boolean gY();

    int gZ();

    Game getGame();

    long ha();

    long hb();

    String hc();

    long hd();

    String he();
}
