/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.view.View
 *  com.google.android.gms.internal.ff.b
 *  com.google.android.gms.internal.ff.d
 */
package com.google.android.gms.games.internal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.internal.AbstractGamesCallbacks;
import com.google.android.gms.games.internal.GamesLog;
import com.google.android.gms.games.internal.IGamesCallbacks;
import com.google.android.gms.games.internal.IGamesService;
import com.google.android.gms.games.internal.PopupManager;
import com.google.android.gms.games.internal.constants.RequestType;
import com.google.android.gms.games.internal.game.Acls;
import com.google.android.gms.games.internal.game.ExtendedGameBuffer;
import com.google.android.gms.games.internal.game.GameInstanceBuffer;
import com.google.android.gms.games.internal.player.ExtendedPlayerBuffer;
import com.google.android.gms.games.internal.request.RequestUpdateOutcomes;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardEntity;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBufferHeader;
import com.google.android.gms.games.leaderboard.LeaderboardScoreEntity;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.ParticipantUtils;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomBuffer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchBuffer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.ff.b;
import com.google.android.gms.internal.ff.d;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class GamesClientImpl
extends ff<IGamesService>
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private boolean IA = false;
    private int IB;
    private final Binder IC;
    private final long IE;
    private final boolean IF;
    private final int IG;
    private final boolean IH;
    private final String Iu;
    private final Map<String, RealTimeSocket> Iv;
    private PlayerEntity Iw;
    private GameEntity Ix;
    private final PopupManager Iy;
    private boolean Iz = false;
    private final String wG;

    public GamesClientImpl(Context context, Looper looper, String string2, String string3, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String[] arrstring, int n2, View view, boolean bl2, boolean bl3, int n3, boolean bl4, int n4) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, arrstring);
        this.Iu = string2;
        this.wG = fq.f(string3);
        this.IC = new Binder();
        this.Iv = new HashMap<String, RealTimeSocket>();
        this.Iy = PopupManager.a(this, n2);
        this.f(view);
        this.IA = bl3;
        this.IB = n3;
        this.IE = this.hashCode();
        this.IF = bl2;
        this.IH = bl4;
        this.IG = n4;
        this.registerConnectionCallbacks(this);
        this.registerConnectionFailedListener(this);
    }

    private Room G(DataHolder parcelable) {
        RoomBuffer roomBuffer = new RoomBuffer((DataHolder)parcelable);
        parcelable = null;
        try {
            if (roomBuffer.getCount() > 0) {
                parcelable = (Room)((Room)roomBuffer.get(0)).freeze();
            }
            return parcelable;
        }
        finally {
            roomBuffer.close();
        }
    }

    /*
     * Exception decompiling
     */
    private RealTimeSocket aC(String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:394)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:446)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    private void gE() {
        for (RealTimeSocket realTimeSocket : this.Iv.values()) {
            try {
                realTimeSocket.close();
            }
            catch (IOException var2_3) {
                GamesLog.a("GamesClientImpl", "IOException:", var2_3);
            }
        }
        this.Iv.clear();
    }

    private void gk() {
        this.Iw = null;
    }

    protected IGamesService L(IBinder iBinder) {
        return IGamesService.Stub.N(iBinder);
    }

    public int a(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback, byte[] arrby, String string2, String string3) {
        try {
            int n2 = ((IGamesService)this.eM()).a((IGamesCallbacks)new RealTimeReliableMessageBinderCallbacks(reliableMessageSentCallback), arrby, string2, string3);
            return n2;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return -1;
        }
    }

    public int a(byte[] arrby, String string2, String[] arrstring) {
        fq.b(arrstring, (Object)"Participant IDs must not be null");
        try {
            int n2 = ((IGamesService)this.eM()).b(arrby, string2, arrstring);
            return n2;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return -1;
        }
    }

    public Intent a(int n2, int n3, boolean bl2) {
        try {
            Intent intent = ((IGamesService)this.eM()).a(n2, n3, bl2);
            return intent;
        }
        catch (RemoteException var4_5) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent a(int n2, byte[] object, int n3, Bitmap bitmap, String string2) {
        try {
            object = (Object)((IGamesService)this.eM()).a(n2, (byte[])object, n3, string2);
            fq.b(bitmap, (Object)"Must provide a non null icon");
            object.putExtra("com.google.android.gms.games.REQUEST_ITEM_ICON", (Parcelable)bitmap);
            return object;
        }
        catch (RemoteException var2_3) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent a(Room room, int n2) {
        try {
            room = ((IGamesService)this.eM()).a((RoomEntity)room.freeze(), n2);
            return room;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    @Override
    protected void a(int n2, IBinder iBinder, Bundle bundle) {
        if (n2 == 0 && bundle != null) {
            this.Iz = bundle.getBoolean("show_welcome_popup");
        }
        super.a(n2, iBinder, bundle);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void a(IBinder iBinder, Bundle bundle) {
        if (!this.isConnected()) return;
        try {
            ((IGamesService)this.eM()).a(iBinder, bundle);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Requests.LoadRequestsResult> d2, int n2, int n3, int n4) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new RequestsLoadedBinderCallbacks(d2), n2, n3, n4);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<GamesMetadata.LoadExtendedGamesResult> d2, int n2, int n3, boolean bl2, boolean bl3) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new ExtendedGamesLoadedBinderCallback(d2), n2, n3, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Players.LoadPlayersResult> d2, int n2, boolean bl2, boolean bl3) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new PlayersLoadedBinderCallback(d2), n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<TurnBasedMultiplayer.LoadMatchesResult> d2, int n2, int[] arrn) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new TurnBasedMatchesLoadedBinderCallbacks(d2), n2, arrn);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Leaderboards.LoadScoresResult> d2, LeaderboardScoreBuffer leaderboardScoreBuffer, int n2, int n3) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new LeaderboardScoresLoadedBinderCallback(d2), leaderboardScoreBuffer.hD().hE(), n2, n3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<TurnBasedMultiplayer.InitiateMatchResult> d2, TurnBasedMatchConfig turnBasedMatchConfig) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new TurnBasedMatchInitiatedBinderCallbacks(d2), turnBasedMatchConfig.getVariant(), turnBasedMatchConfig.getMinPlayers(), turnBasedMatchConfig.getInvitedPlayerIds(), turnBasedMatchConfig.getAutoMatchCriteria());
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Players.LoadPlayersResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new PlayersLoadedBinderCallback(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void a(a.d<Achievements.UpdateAchievementResult> var1_1, String var2_3, int var3_4) {
        if (var1_1 != null) ** GOTO lbl-1000
        var1_1 = null;
        try {}
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
        ** GOTO lbl9
lbl-1000: // 1 sources:
        {
            var1_1 = new AchievementUpdatedBinderCallback((a.d<Achievements.UpdateAchievementResult>)var1_1);
lbl9: // 2 sources:
            ((IGamesService)this.eM()).a((IGamesCallbacks)var1_1, var2_3, var3_4, this.Iy.gU(), this.Iy.gT());
            return;
        }
    }

    public void a(a.d<Leaderboards.LoadScoresResult> d2, String string2, int n2, int n3, int n4, boolean bl2) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new LeaderboardScoresLoadedBinderCallback(d2), string2, n2, n3, n4, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Players.LoadPlayersResult> d2, String string2, int n2, boolean bl2) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new PlayersLoadedBinderCallback(d2), string2, n2, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Players.LoadPlayersResult> d2, String string2, int n2, boolean bl2, boolean bl3) {
        if (!string2.equals("playedWith")) {
            throw new IllegalArgumentException("Invalid player collection: " + string2);
        }
        try {
            ((IGamesService)this.eM()).d(new PlayersLoadedBinderCallback(d2), string2, n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<GamesMetadata.LoadExtendedGamesResult> d2, String string2, int n2, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new ExtendedGamesLoadedBinderCallback(d2), string2, n2, bl2, bl3, bl4, bl5);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<TurnBasedMultiplayer.LoadMatchesResult> d2, String string2, int n2, int[] arrn) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new TurnBasedMatchesLoadedBinderCallbacks(d2), string2, n2, arrn);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void a(a.d<Leaderboards.SubmitScoreResult> var1_1, String var2_3, long var3_4, String var5_5) {
        if (var1_1 != null) ** GOTO lbl-1000
        var1_1 = null;
        try {}
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
        ** GOTO lbl9
lbl-1000: // 1 sources:
        {
            var1_1 = new SubmitScoreBinderCallbacks((a.d<Leaderboards.SubmitScoreResult>)var1_1);
lbl9: // 2 sources:
            ((IGamesService)this.eM()).a((IGamesCallbacks)var1_1, var2_3, var3_4, var5_5);
            return;
        }
    }

    public void a(a.d<TurnBasedMultiplayer.LeaveMatchResult> d2, String string2, String string3) {
        try {
            ((IGamesService)this.eM()).c((IGamesCallbacks)new TurnBasedMatchLeftBinderCallbacks(d2), string2, string3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Leaderboards.LoadPlayerScoreResult> d2, String string2, String string3, int n2, int n3) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new PlayerLeaderboardScoreLoadedBinderCallback(d2), string2, string3, n2, n3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Requests.LoadRequestsResult> d2, String string2, String string3, int n2, int n3, int n4) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new RequestsLoadedBinderCallbacks(d2), string2, string3, n2, n3, n4);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Leaderboards.LoadScoresResult> d2, String string2, String string3, int n2, int n3, int n4, boolean bl2) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new LeaderboardScoresLoadedBinderCallback(d2), string2, string3, n2, n3, n4, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Players.LoadPlayersResult> d2, String string2, String string3, int n2, boolean bl2, boolean bl3) {
        if (!string2.equals("playedWith") && !string2.equals("circled")) {
            throw new IllegalArgumentException("Invalid player collection: " + string2);
        }
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new PlayersLoadedBinderCallback(d2), string2, string3, n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Leaderboards.LeaderboardMetadataResult> d2, String string2, String string3, boolean bl2) {
        try {
            ((IGamesService)this.eM()).b((IGamesCallbacks)new LeaderboardsLoadedBinderCallback(d2), string2, string3, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Requests.UpdateRequestsResult> d2, String string2, String string3, String[] arrstring) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new RequestsUpdatedBinderCallbacks(d2), string2, string3, arrstring);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Leaderboards.LeaderboardMetadataResult> d2, String string2, boolean bl2) {
        try {
            ((IGamesService)this.eM()).c((IGamesCallbacks)new LeaderboardsLoadedBinderCallback(d2), string2, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<TurnBasedMultiplayer.UpdateMatchResult> d2, String string2, byte[] arrby, String string3, ParticipantResult[] arrparticipantResult) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new TurnBasedMatchUpdatedBinderCallbacks(d2), string2, arrby, string3, arrparticipantResult);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<TurnBasedMultiplayer.UpdateMatchResult> d2, String string2, byte[] arrby, ParticipantResult[] arrparticipantResult) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new TurnBasedMatchUpdatedBinderCallbacks(d2), string2, arrby, arrparticipantResult);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Requests.SendRequestResult> d2, String string2, String[] arrstring, int n2, byte[] arrby, int n3) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new RequestSentBinderCallbacks(d2), string2, arrstring, n2, arrby, n3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Players.LoadPlayersResult> d2, boolean bl2) {
        try {
            ((IGamesService)this.eM()).c((IGamesCallbacks)new PlayersLoadedBinderCallback(d2), bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Status> d2, boolean bl2, Bundle bundle) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new ContactSettingsUpdatedBinderCallback(d2), bl2, bundle);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(a.d<Players.LoadPlayersResult> d2, String[] arrstring) {
        try {
            ((IGamesService)this.eM()).c((IGamesCallbacks)new PlayersLoadedBinderCallback(d2), arrstring);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(OnInvitationReceivedListener object) {
        try {
            object = new InvitationReceivedBinderCallback((OnInvitationReceivedListener)object);
            ((IGamesService)this.eM()).a((IGamesCallbacks)object, this.IE);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(RoomConfig roomConfig) {
        try {
            RoomBinderCallbacks roomBinderCallbacks = new RoomBinderCallbacks(this, roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener());
            ((IGamesService)this.eM()).a((IGamesCallbacks)roomBinderCallbacks, (IBinder)this.IC, roomConfig.getVariant(), roomConfig.getInvitedPlayerIds(), roomConfig.getAutoMatchCriteria(), roomConfig.isSocketEnabled(), this.IE);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(RoomUpdateListener roomUpdateListener, String string2) {
        try {
            ((IGamesService)this.eM()).c((IGamesCallbacks)new RoomBinderCallbacks(this, roomUpdateListener), string2);
            this.gE();
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(OnTurnBasedMatchUpdateReceivedListener object) {
        try {
            object = new MatchUpdateReceivedBinderCallback((OnTurnBasedMatchUpdateReceivedListener)object);
            ((IGamesService)this.eM()).b((IGamesCallbacks)object, this.IE);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void a(OnRequestReceivedListener object) {
        try {
            object = new RequestReceivedBinderCallback((OnRequestReceivedListener)object);
            ((IGamesService)this.eM()).c((IGamesCallbacks)object, this.IE);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        String string2 = this.getContext().getResources().getConfiguration().locale.toString();
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.google.android.gms.games.key.isHeadless", this.IF);
        bundle.putBoolean("com.google.android.gms.games.key.showConnectingPopup", this.IA);
        bundle.putInt("com.google.android.gms.games.key.connectingPopupGravity", this.IB);
        bundle.putBoolean("com.google.android.gms.games.key.retryingSignIn", this.IH);
        bundle.putInt("com.google.android.gms.games.key.sdkVariant", this.IG);
        fm2.a(e2, 4452000, this.getContext().getPackageName(), this.wG, this.eL(), this.Iu, this.Iy.gU(), string2, bundle);
    }

    public Intent aA(String string2) {
        try {
            string2 = ((IGamesService)this.eM()).aA(string2);
            return string2;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void aB(String string2) {
        try {
            ((IGamesService)this.eM()).aI(string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void aX(int n2) {
        this.Iy.setGravity(n2);
    }

    public void aY(int n2) {
        try {
            ((IGamesService)this.eM()).aY(n2);
            return;
        }
        catch (RemoteException var2_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public Intent b(int n2, int n3, boolean bl2) {
        try {
            Intent intent = ((IGamesService)this.eM()).b(n2, n3, bl2);
            return intent;
        }
        catch (RemoteException var4_5) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void b(a.d<Status> d2) {
        try {
            ((IGamesService)this.eM()).a(new SignOutCompleteBinderCallbacks(d2));
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void b(a.d<Players.LoadPlayersResult> d2, int n2, boolean bl2, boolean bl3) {
        try {
            ((IGamesService)this.eM()).b((IGamesCallbacks)new PlayersLoadedBinderCallback(d2), n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void b(a.d<Achievements.UpdateAchievementResult> var1_1, String var2_3) {
        if (var1_1 != null) ** GOTO lbl-1000
        var1_1 = null;
        try {}
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
        ** GOTO lbl9
lbl-1000: // 1 sources:
        {
            var1_1 = new AchievementUpdatedBinderCallback((a.d<Achievements.UpdateAchievementResult>)var1_1);
lbl9: // 2 sources:
            ((IGamesService)this.eM()).a((IGamesCallbacks)var1_1, var2_3, this.Iy.gU(), this.Iy.gT());
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void b(a.d<Achievements.UpdateAchievementResult> var1_1, String var2_3, int var3_4) {
        if (var1_1 != null) ** GOTO lbl-1000
        var1_1 = null;
        try {}
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
        ** GOTO lbl9
lbl-1000: // 1 sources:
        {
            var1_1 = new AchievementUpdatedBinderCallback((a.d<Achievements.UpdateAchievementResult>)var1_1);
lbl9: // 2 sources:
            ((IGamesService)this.eM()).b((IGamesCallbacks)var1_1, var2_3, var3_4, this.Iy.gU(), this.Iy.gT());
            return;
        }
    }

    public void b(a.d<Leaderboards.LoadScoresResult> d2, String string2, int n2, int n3, int n4, boolean bl2) {
        try {
            ((IGamesService)this.eM()).b(new LeaderboardScoresLoadedBinderCallback(d2), string2, n2, n3, n4, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void b(a.d<GamesMetadata.LoadExtendedGamesResult> d2, String string2, int n2, boolean bl2, boolean bl3) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new ExtendedGamesLoadedBinderCallback(d2), string2, n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void b(a.d<TurnBasedMultiplayer.InitiateMatchResult> d2, String string2, String string3) {
        try {
            ((IGamesService)this.eM()).d((IGamesCallbacks)new TurnBasedMatchInitiatedBinderCallbacks(d2), string2, string3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void b(a.d<Leaderboards.LoadScoresResult> d2, String string2, String string3, int n2, int n3, int n4, boolean bl2) {
        try {
            ((IGamesService)this.eM()).b(new LeaderboardScoresLoadedBinderCallback(d2), string2, string3, n2, n3, n4, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void b(a.d<Achievements.LoadAchievementsResult> d2, String string2, String string3, boolean bl2) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new AchievementsLoadedBinderCallback(d2), string2, string3, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void b(a.d<Leaderboards.LeaderboardMetadataResult> d2, String string2, boolean bl2) {
        try {
            ((IGamesService)this.eM()).d((IGamesCallbacks)new LeaderboardsLoadedBinderCallback(d2), string2, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void b(a.d<Leaderboards.LeaderboardMetadataResult> d2, boolean bl2) {
        try {
            ((IGamesService)this.eM()).b((IGamesCallbacks)new LeaderboardsLoadedBinderCallback(d2), bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void b(a.d<Requests.UpdateRequestsResult> d2, String[] arrstring) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new RequestsUpdatedBinderCallbacks(d2), arrstring);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void b(RoomConfig roomConfig) {
        try {
            RoomBinderCallbacks roomBinderCallbacks = new RoomBinderCallbacks(this, roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener());
            ((IGamesService)this.eM()).a((IGamesCallbacks)roomBinderCallbacks, (IBinder)this.IC, roomConfig.getInvitationId(), roomConfig.isSocketEnabled(), this.IE);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected /* varargs */ void b(String ... arrstring) {
        int n2 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        do {
            boolean bl4;
            if (n2 >= arrstring.length) {
                fq.a(bl3, String.format("Games APIs requires %s to function.", "https://www.googleapis.com/auth/games"));
                return;
            }
            String string2 = arrstring[n2];
            if (string2.equals("https://www.googleapis.com/auth/games")) {
                bl4 = true;
            } else {
                bl4 = bl3;
                if (string2.equals("https://www.googleapis.com/auth/games.firstparty")) {
                    bl2 = true;
                    bl4 = bl3;
                }
            }
            ++n2;
            bl3 = bl4;
        } while (true);
    }

    @Override
    protected String bg() {
        return "com.google.android.gms.games.service.START";
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.games.internal.IGamesService";
    }

    public void c(a.d<Invitations.LoadInvitationsResult> d2, int n2) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new InvitationsLoadedBinderCallback(d2), n2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void c(a.d<Players.LoadPlayersResult> d2, int n2, boolean bl2, boolean bl3) {
        try {
            ((IGamesService)this.eM()).c(new PlayersLoadedBinderCallback(d2), n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void c(a.d<Achievements.UpdateAchievementResult> var1_1, String var2_3) {
        if (var1_1 != null) ** GOTO lbl-1000
        var1_1 = null;
        try {}
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
        ** GOTO lbl9
lbl-1000: // 1 sources:
        {
            var1_1 = new AchievementUpdatedBinderCallback((a.d<Achievements.UpdateAchievementResult>)var1_1);
lbl9: // 2 sources:
            ((IGamesService)this.eM()).b((IGamesCallbacks)var1_1, var2_3, this.Iy.gU(), this.Iy.gT());
            return;
        }
    }

    public void c(a.d<Invitations.LoadInvitationsResult> d2, String string2, int n2) {
        try {
            ((IGamesService)this.eM()).b((IGamesCallbacks)new InvitationsLoadedBinderCallback(d2), string2, n2, false);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void c(a.d<GamesMetadata.LoadExtendedGamesResult> d2, String string2, int n2, boolean bl2, boolean bl3) {
        try {
            ((IGamesService)this.eM()).c(new ExtendedGamesLoadedBinderCallback(d2), string2, n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void c(a.d<TurnBasedMultiplayer.InitiateMatchResult> d2, String string2, String string3) {
        try {
            ((IGamesService)this.eM()).e(new TurnBasedMatchInitiatedBinderCallbacks(d2), string2, string3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void c(a.d<Notifications.GameMuteStatusChangeResult> d2, String string2, boolean bl2) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new GameMuteStatusChangedBinderCallback(d2), string2, bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void c(a.d<Achievements.LoadAchievementsResult> d2, boolean bl2) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new AchievementsLoadedBinderCallback(d2), bl2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void c(a.d<Requests.UpdateRequestsResult> d2, String[] arrstring) {
        try {
            ((IGamesService)this.eM()).b((IGamesCallbacks)new RequestsUpdatedBinderCallbacks(d2), arrstring);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    @Override
    public void connect() {
        this.gk();
        super.connect();
    }

    public int d(byte[] arrby, String string2) {
        try {
            int n2 = ((IGamesService)this.eM()).b(arrby, string2, null);
            return n2;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return -1;
        }
    }

    public void d(a.d<Players.LoadPlayersResult> d2, int n2, boolean bl2, boolean bl3) {
        try {
            ((IGamesService)this.eM()).e(new PlayersLoadedBinderCallback(d2), n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void d(a.d<TurnBasedMultiplayer.InitiateMatchResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).l(new TurnBasedMatchInitiatedBinderCallbacks(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void d(a.d<Requests.LoadRequestSummariesResult> d2, String string2, int n2) {
        try {
            ((IGamesService)this.eM()).a((IGamesCallbacks)new RequestSummariesLoadedBinderCallbacks(d2), string2, n2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void d(a.d<Players.LoadPlayersResult> d2, String string2, int n2, boolean bl2, boolean bl3) {
        try {
            ((IGamesService)this.eM()).b((IGamesCallbacks)new PlayersLoadedBinderCallback(d2), string2, n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    @Override
    public Bundle dG() {
        Bundle bundle;
        block3 : {
            try {
                bundle = ((IGamesService)this.eM()).dG();
                if (bundle == null) break block3;
            }
            catch (RemoteException var1_2) {
                GamesLog.g("GamesClientImpl", "service died");
                return null;
            }
            bundle.setClassLoader(GamesClientImpl.class.getClassLoader());
        }
        return bundle;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        this.Iz = false;
        if (this.isConnected()) {
            try {
                IGamesService iGamesService = (IGamesService)this.eM();
                iGamesService.gF();
                iGamesService.o(this.IE);
            }
            catch (RemoteException var1_2) {
                GamesLog.g("GamesClientImpl", "Failed to notify client disconnect.");
            }
        }
        this.gE();
        super.disconnect();
    }

    public void e(a.d<Players.LoadExtendedPlayersResult> d2, int n2, boolean bl2, boolean bl3) {
        try {
            ((IGamesService)this.eM()).d(new ExtendedPlayersLoadedBinderCallback(d2), n2, bl2, bl3);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void e(a.d<TurnBasedMultiplayer.InitiateMatchResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).m(new TurnBasedMatchInitiatedBinderCallbacks(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void f(View view) {
        this.Iy.g(view);
    }

    public void f(a.d<TurnBasedMultiplayer.LeaveMatchResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).o(new TurnBasedMatchLeftBinderCallbacks(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void g(a.d<GamesMetadata.LoadGamesResult> d2) {
        try {
            ((IGamesService)this.eM()).d(new GamesLoadedBinderCallback(d2));
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void g(a.d<TurnBasedMultiplayer.CancelMatchResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).n(new TurnBasedMatchCanceledBinderCallbacks(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public int gA() {
        try {
            int n2 = ((IGamesService)this.eM()).gA();
            return n2;
        }
        catch (RemoteException var2_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return 2;
        }
    }

    public Intent gB() {
        try {
            Intent intent = ((IGamesService)this.eM()).gB();
            return intent;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public int gC() {
        try {
            int n2 = ((IGamesService)this.eM()).gC();
            return n2;
        }
        catch (RemoteException var2_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return 2;
        }
    }

    public int gD() {
        try {
            int n2 = ((IGamesService)this.eM()).gD();
            return n2;
        }
        catch (RemoteException var2_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return 2;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void gF() {
        if (!this.isConnected()) return;
        try {
            ((IGamesService)this.eM()).gF();
            return;
        }
        catch (RemoteException var1_1) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public String gl() {
        try {
            String string2 = ((IGamesService)this.eM()).gl();
            return string2;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public String gm() {
        try {
            String string2 = ((IGamesService)this.eM()).gm();
            return string2;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Player gn() {
        Object object;
        block9 : {
            this.bT();
            // MONITORENTER : this
            object = this.Iw;
            if (object != null) return this.Iw;
            object = new PlayerBuffer(((IGamesService)this.eM()).gG());
            {
                catch (RemoteException remoteException) {
                    GamesLog.g("GamesClientImpl", "service died");
                    return this.Iw;
                }
            }
            if (object.getCount() <= 0) break block9;
            this.Iw = (PlayerEntity)object.get(0).freeze();
        }
        object.close();
        // MONITOREXIT : this
        return this.Iw;
        catch (Throwable throwable) {
            object.close();
            throw throwable;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Game go() {
        Object object;
        block9 : {
            this.bT();
            // MONITORENTER : this
            object = this.Ix;
            if (object != null) return this.Ix;
            object = new GameBuffer(((IGamesService)this.eM()).gI());
            {
                catch (RemoteException remoteException) {
                    GamesLog.g("GamesClientImpl", "service died");
                    return this.Ix;
                }
            }
            if (object.getCount() <= 0) break block9;
            this.Ix = (GameEntity)object.get(0).freeze();
        }
        object.close();
        // MONITOREXIT : this
        return this.Ix;
        catch (Throwable throwable) {
            object.close();
            throw throwable;
        }
    }

    public Intent gp() {
        try {
            Intent intent = ((IGamesService)this.eM()).gp();
            return intent;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gq() {
        try {
            Intent intent = ((IGamesService)this.eM()).gq();
            return intent;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gr() {
        try {
            Intent intent = ((IGamesService)this.eM()).gr();
            return intent;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gs() {
        try {
            Intent intent = ((IGamesService)this.eM()).gs();
            return intent;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void gt() {
        try {
            ((IGamesService)this.eM()).p(this.IE);
            return;
        }
        catch (RemoteException var1_1) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void gu() {
        try {
            ((IGamesService)this.eM()).q(this.IE);
            return;
        }
        catch (RemoteException var1_1) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void gv() {
        try {
            ((IGamesService)this.eM()).r(this.IE);
            return;
        }
        catch (RemoteException var1_1) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public Intent gw() {
        try {
            Intent intent = ((IGamesService)this.eM()).gw();
            return intent;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gx() {
        try {
            Intent intent = ((IGamesService)this.eM()).gx();
            return intent;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public int gy() {
        try {
            int n2 = ((IGamesService)this.eM()).gy();
            return n2;
        }
        catch (RemoteException var2_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return 4368;
        }
    }

    public String gz() {
        try {
            String string2 = ((IGamesService)this.eM()).gz();
            return string2;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void h(a.d<Players.LoadOwnerCoverPhotoUrisResult> d2) {
        try {
            ((IGamesService)this.eM()).j(new OwnerCoverPhotoUrisLoadedBinderCallback(d2));
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void h(a.d<TurnBasedMultiplayer.LoadMatchResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).p(new TurnBasedMatchLoadedBinderCallbacks(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public RealTimeSocket i(String object, String string2) {
        block3 : {
            if (string2 == null || !ParticipantUtils.aV(string2)) {
                throw new IllegalArgumentException("Bad participant ID");
            }
            RealTimeSocket realTimeSocket = this.Iv.get(string2);
            if (realTimeSocket != null) {
                object = realTimeSocket;
                if (!realTimeSocket.isClosed()) break block3;
            }
            object = this.aC(string2);
        }
        return object;
    }

    public void i(a.d<Acls.LoadAclResult> d2) {
        try {
            ((IGamesService)this.eM()).h(new NotifyAclLoadedBinderCallback(d2));
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void i(a.d<GamesMetadata.LoadExtendedGamesResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).e(new ExtendedGamesLoadedBinderCallback(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void j(a.d<Notifications.ContactSettingLoadResult> d2) {
        try {
            ((IGamesService)this.eM()).i(new ContactSettingsLoadedBinderCallback(d2));
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void j(a.d<GamesMetadata.LoadGameInstancesResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).f(new GameInstancesLoadedBinderCallback(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void k(a.d<GamesMetadata.LoadGameSearchSuggestionsResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).q(new GameSearchSuggestionsLoadedBinderCallback(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void l(a.d<Invitations.LoadInvitationsResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).k(new InvitationsLoadedBinderCallback(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void l(String string2, int n2) {
        try {
            ((IGamesService)this.eM()).l(string2, n2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void m(a.d<Status> d2, String string2) {
        try {
            ((IGamesService)this.eM()).j(new NotifyAclUpdatedBinderCallback(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void m(String string2, int n2) {
        try {
            ((IGamesService)this.eM()).m(string2, n2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    public void n(a.d<Notifications.GameMuteStatusLoadResult> d2, String string2) {
        try {
            ((IGamesService)this.eM()).i(new GameMuteStatusLoadedBinderCallback(d2), string2);
            return;
        }
        catch (RemoteException var1_2) {
            GamesLog.g("GamesClientImpl", "service died");
            return;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (this.Iz) {
            this.Iy.gS();
            this.Iz = false;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.Iz = false;
    }

    @Override
    public void onConnectionSuspended(int n2) {
    }

    @Override
    protected /* synthetic */ IInterface r(IBinder iBinder) {
        return this.L(iBinder);
    }

    abstract class AbstractPeerStatusCallback
    extends AbstractRoomStatusCallback {
        private final ArrayList<String> II;

        AbstractPeerStatusCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] arrstring) {
            super(roomStatusUpdateListener, dataHolder);
            this.II = new ArrayList();
            int n2 = arrstring.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                this.II.add(arrstring[i2]);
            }
        }

        @Override
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            this.a(roomStatusUpdateListener, room, this.II);
        }

        protected abstract void a(RoomStatusUpdateListener var1, Room var2, ArrayList<String> var3);
    }

    abstract class AbstractRoomCallback
    extends d<RoomUpdateListener> {
        AbstractRoomCallback(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            super(roomUpdateListener, dataHolder);
        }

        protected void a(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            this.a(roomUpdateListener, GamesClientImpl.this.G(dataHolder), dataHolder.getStatusCode());
        }

        protected abstract void a(RoomUpdateListener var1, Room var2, int var3);
    }

    abstract class AbstractRoomStatusCallback
    extends d<RoomStatusUpdateListener> {
        AbstractRoomStatusCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            this.a(roomStatusUpdateListener, GamesClientImpl.this.G(dataHolder));
        }

        protected abstract void a(RoomStatusUpdateListener var1, Room var2);
    }

    final class AchievementUpdatedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Achievements.UpdateAchievementResult> wH;

        AchievementUpdatedBinderCallback(a.d<Achievements.UpdateAchievementResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void e(int n2, String string2) {
            GamesClientImpl.this.a((ff.b)((Object)new AchievementUpdatedCallback(this.wH, n2, string2)));
        }
    }

    final class AchievementUpdatedCallback
    extends b<a.d<Achievements.UpdateAchievementResult>>
    implements Achievements.UpdateAchievementResult {
        private final String IK;
        private final Status wJ;

        AchievementUpdatedCallback(a.d<Achievements.UpdateAchievementResult> d2, int n2, String string2) {
            super(d2);
            this.wJ = new Status(n2);
            this.IK = string2;
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<Achievements.UpdateAchievementResult> d2) {
            d2.b(this);
        }

        protected void dx() {
        }

        @Override
        public String getAchievementId() {
            return this.IK;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class AchievementsLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Achievements.LoadAchievementsResult> wH;

        AchievementsLoadedBinderCallback(a.d<Achievements.LoadAchievementsResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void b(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new AchievementsLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class AchievementsLoadedCallback
    extends ResultDataHolderCallback<a.d<Achievements.LoadAchievementsResult>>
    implements Achievements.LoadAchievementsResult {
        private final AchievementBuffer IL;

        AchievementsLoadedCallback(a.d<Achievements.LoadAchievementsResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.IL = new AchievementBuffer(dataHolder);
        }

        protected void a(a.d<Achievements.LoadAchievementsResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public AchievementBuffer getAchievements() {
            return this.IL;
        }
    }

    final class ConnectedToRoomCallback
    extends AbstractRoomStatusCallback {
        ConnectedToRoomCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        @Override
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onConnectedToRoom(room);
        }
    }

    final class ContactSettingsLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Notifications.ContactSettingLoadResult> wH;

        ContactSettingsLoadedBinderCallback(a.d<Notifications.ContactSettingLoadResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void B(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new ContactSettingsLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class ContactSettingsLoadedCallback
    extends ResultDataHolderCallback<a.d<Notifications.ContactSettingLoadResult>>
    implements Notifications.ContactSettingLoadResult {
        ContactSettingsLoadedCallback(a.d<Notifications.ContactSettingLoadResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
        }

        protected void a(a.d<Notifications.ContactSettingLoadResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }
    }

    final class ContactSettingsUpdatedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Status> wH;

        ContactSettingsUpdatedBinderCallback(a.d<Status> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void aV(int n2) {
            GamesClientImpl.this.a((ff.b)((Object)new ContactSettingsUpdatedCallback(this.wH, n2)));
        }
    }

    final class ContactSettingsUpdatedCallback
    extends b<a.d<Status>> {
        private final Status wJ;

        ContactSettingsUpdatedCallback(a.d<Status> d2, int n2) {
            super(d2);
            this.wJ = new Status(n2);
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<Status> d2) {
            d2.b(this.wJ);
        }

        protected void dx() {
        }
    }

    final class DisconnectedFromRoomCallback
    extends AbstractRoomStatusCallback {
        DisconnectedFromRoomCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        @Override
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onDisconnectedFromRoom(room);
        }
    }

    final class ExtendedGamesLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<GamesMetadata.LoadExtendedGamesResult> wH;

        ExtendedGamesLoadedBinderCallback(a.d<GamesMetadata.LoadExtendedGamesResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void h(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new ExtendedGamesLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class ExtendedGamesLoadedCallback
    extends ResultDataHolderCallback<a.d<GamesMetadata.LoadExtendedGamesResult>>
    implements GamesMetadata.LoadExtendedGamesResult {
        private final ExtendedGameBuffer IM;

        ExtendedGamesLoadedCallback(a.d<GamesMetadata.LoadExtendedGamesResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.IM = new ExtendedGameBuffer(dataHolder);
        }

        protected void a(a.d<GamesMetadata.LoadExtendedGamesResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }
    }

    final class ExtendedPlayersLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Players.LoadExtendedPlayersResult> wH;

        ExtendedPlayersLoadedBinderCallback(a.d<Players.LoadExtendedPlayersResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void f(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new ExtendedPlayersLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class ExtendedPlayersLoadedCallback
    extends ResultDataHolderCallback<a.d<Players.LoadExtendedPlayersResult>>
    implements Players.LoadExtendedPlayersResult {
        private final ExtendedPlayerBuffer IN;

        ExtendedPlayersLoadedCallback(a.d<Players.LoadExtendedPlayersResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.IN = new ExtendedPlayerBuffer(dataHolder);
        }

        protected void a(a.d<Players.LoadExtendedPlayersResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }
    }

    final class GameInstancesLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<GamesMetadata.LoadGameInstancesResult> wH;

        GameInstancesLoadedBinderCallback(a.d<GamesMetadata.LoadGameInstancesResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void i(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new GameInstancesLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class GameInstancesLoadedCallback
    extends ResultDataHolderCallback<a.d<GamesMetadata.LoadGameInstancesResult>>
    implements GamesMetadata.LoadGameInstancesResult {
        private final GameInstanceBuffer IO;

        GameInstancesLoadedCallback(a.d<GamesMetadata.LoadGameInstancesResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.IO = new GameInstanceBuffer(dataHolder);
        }

        protected void a(a.d<GamesMetadata.LoadGameInstancesResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }
    }

    final class GameMuteStatusChangedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Notifications.GameMuteStatusChangeResult> wH;

        GameMuteStatusChangedBinderCallback(a.d<Notifications.GameMuteStatusChangeResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void a(int n2, String string2, boolean bl2) {
            GamesClientImpl.this.a((ff.b)((Object)new GameMuteStatusChangedCallback(this.wH, n2, string2, bl2)));
        }
    }

    final class GameMuteStatusChangedCallback
    extends b<a.d<Notifications.GameMuteStatusChangeResult>>
    implements Notifications.GameMuteStatusChangeResult {
        private final String IP;
        private final boolean IQ;
        private final Status wJ;

        public GameMuteStatusChangedCallback(a.d<Notifications.GameMuteStatusChangeResult> d2, int n2, String string2, boolean bl2) {
            super(d2);
            this.wJ = new Status(n2);
            this.IP = string2;
            this.IQ = bl2;
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<Notifications.GameMuteStatusChangeResult> d2) {
            d2.b(this);
        }

        protected void dx() {
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class GameMuteStatusLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Notifications.GameMuteStatusLoadResult> wH;

        GameMuteStatusLoadedBinderCallback(a.d<Notifications.GameMuteStatusLoadResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void z(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new GameMuteStatusLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class GameMuteStatusLoadedCallback
    extends b<a.d<Notifications.GameMuteStatusLoadResult>>
    implements Notifications.GameMuteStatusLoadResult {
        private final String IP;
        private final boolean IQ;
        private final Status wJ;

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public GameMuteStatusLoadedCallback(a.d<Notifications.GameMuteStatusLoadResult> d2, DataHolder dataHolder) {
            super(d2);
            try {
                this.wJ = new Status(dataHolder.getStatusCode());
                if (dataHolder.getCount() > 0) {
                    this.IP = dataHolder.getString("external_game_id", 0, 0);
                    this.IQ = dataHolder.getBoolean("muted", 0, 0);
                    do {
                        return;
                        break;
                    } while (true);
                }
                this.IP = null;
                this.IQ = false;
                return;
            }
            finally {
                dataHolder.close();
            }
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<Notifications.GameMuteStatusLoadResult> d2) {
            d2.b(this);
        }

        protected void dx() {
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class GameSearchSuggestionsLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<GamesMetadata.LoadGameSearchSuggestionsResult> wH;

        GameSearchSuggestionsLoadedBinderCallback(a.d<GamesMetadata.LoadGameSearchSuggestionsResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void j(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new GameSearchSuggestionsLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class GameSearchSuggestionsLoadedCallback
    extends ResultDataHolderCallback<a.d<GamesMetadata.LoadGameSearchSuggestionsResult>>
    implements GamesMetadata.LoadGameSearchSuggestionsResult {
        private final DataHolder IR;

        GameSearchSuggestionsLoadedCallback(a.d<GamesMetadata.LoadGameSearchSuggestionsResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.IR = dataHolder;
        }

        protected void a(a.d<GamesMetadata.LoadGameSearchSuggestionsResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }
    }

    final class GamesLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<GamesMetadata.LoadGamesResult> wH;

        GamesLoadedBinderCallback(a.d<GamesMetadata.LoadGamesResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void g(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new GamesLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class GamesLoadedCallback
    extends ResultDataHolderCallback<a.d<GamesMetadata.LoadGamesResult>>
    implements GamesMetadata.LoadGamesResult {
        private final GameBuffer IS;

        GamesLoadedCallback(a.d<GamesMetadata.LoadGamesResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.IS = new GameBuffer(dataHolder);
        }

        protected void a(a.d<GamesMetadata.LoadGamesResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public GameBuffer getGames() {
            return this.IS;
        }
    }

    final class InvitationReceivedBinderCallback
    extends AbstractGamesCallbacks {
        private final OnInvitationReceivedListener IT;

        InvitationReceivedBinderCallback(OnInvitationReceivedListener onInvitationReceivedListener) {
            this.IT = onInvitationReceivedListener;
        }

        @Override
        public void l(DataHolder parcelable) {
            InvitationBuffer invitationBuffer;
            block4 : {
                invitationBuffer = new InvitationBuffer((DataHolder)parcelable);
                parcelable = null;
                if (invitationBuffer.getCount() > 0) {
                    parcelable = (Invitation)((Invitation)invitationBuffer.get(0)).freeze();
                }
                if (parcelable == null) break block4;
                GamesClientImpl.this.a((ff.b)((Object)new InvitationReceivedCallback(this.IT, (Invitation)parcelable)));
            }
            return;
            finally {
                invitationBuffer.close();
            }
        }

        @Override
        public void onInvitationRemoved(String string2) {
            GamesClientImpl.this.a((ff.b)((Object)new InvitationRemovedCallback(this.IT, string2)));
        }
    }

    final class InvitationReceivedCallback
    extends b<OnInvitationReceivedListener> {
        private final Invitation IU;

        InvitationReceivedCallback(OnInvitationReceivedListener onInvitationReceivedListener, Invitation invitation) {
            super(onInvitationReceivedListener);
            this.IU = invitation;
        }

        protected /* synthetic */ void a(Object object) {
            this.b((OnInvitationReceivedListener)object);
        }

        protected void b(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationReceived(this.IU);
        }

        protected void dx() {
        }
    }

    final class InvitationRemovedCallback
    extends b<OnInvitationReceivedListener> {
        private final String IV;

        InvitationRemovedCallback(OnInvitationReceivedListener onInvitationReceivedListener, String string2) {
            super(onInvitationReceivedListener);
            this.IV = string2;
        }

        protected /* synthetic */ void a(Object object) {
            this.b((OnInvitationReceivedListener)object);
        }

        protected void b(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationRemoved(this.IV);
        }

        protected void dx() {
        }
    }

    final class InvitationsLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Invitations.LoadInvitationsResult> wH;

        InvitationsLoadedBinderCallback(a.d<Invitations.LoadInvitationsResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void k(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new InvitationsLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class InvitationsLoadedCallback
    extends ResultDataHolderCallback<a.d<Invitations.LoadInvitationsResult>>
    implements Invitations.LoadInvitationsResult {
        private final InvitationBuffer IW;

        InvitationsLoadedCallback(a.d<Invitations.LoadInvitationsResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.IW = new InvitationBuffer(dataHolder);
        }

        protected void a(a.d<Invitations.LoadInvitationsResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public InvitationBuffer getInvitations() {
            return this.IW;
        }
    }

    final class JoinedRoomCallback
    extends AbstractRoomCallback {
        public JoinedRoomCallback(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            super(roomUpdateListener, dataHolder);
        }

        @Override
        public void a(RoomUpdateListener roomUpdateListener, Room room, int n2) {
            roomUpdateListener.onJoinedRoom(n2, room);
        }
    }

    final class LeaderboardScoresLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Leaderboards.LoadScoresResult> wH;

        LeaderboardScoresLoadedBinderCallback(a.d<Leaderboards.LoadScoresResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void a(DataHolder dataHolder, DataHolder dataHolder2) {
            GamesClientImpl.this.a((ff.b)((Object)new LeaderboardScoresLoadedCallback(this.wH, dataHolder, dataHolder2)));
        }
    }

    final class LeaderboardScoresLoadedCallback
    extends ResultDataHolderCallback<a.d<Leaderboards.LoadScoresResult>>
    implements Leaderboards.LoadScoresResult {
        private final LeaderboardEntity IX;
        private final LeaderboardScoreBuffer IY;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        LeaderboardScoresLoadedCallback(a.d<Leaderboards.LoadScoresResult> d2, DataHolder dataHolder, DataHolder dataHolder2) {
            super((GamesClientImpl)Object.this, d2, dataHolder2);
            Object.this = new LeaderboardBuffer(dataHolder);
            try {
                this.IX = Object.this.getCount() > 0 ? (LeaderboardEntity)((Leaderboard)Object.this.get(0)).freeze() : null;
                this.IY = new LeaderboardScoreBuffer(dataHolder2);
                return;
            }
            finally {
                Object.this.close();
            }
        }

        protected void a(a.d<Leaderboards.LoadScoresResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public Leaderboard getLeaderboard() {
            return this.IX;
        }

        @Override
        public LeaderboardScoreBuffer getScores() {
            return this.IY;
        }
    }

    final class LeaderboardsLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Leaderboards.LeaderboardMetadataResult> wH;

        LeaderboardsLoadedBinderCallback(a.d<Leaderboards.LeaderboardMetadataResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void c(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new LeaderboardsLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class LeaderboardsLoadedCallback
    extends ResultDataHolderCallback<a.d<Leaderboards.LeaderboardMetadataResult>>
    implements Leaderboards.LeaderboardMetadataResult {
        private final LeaderboardBuffer IZ;

        LeaderboardsLoadedCallback(a.d<Leaderboards.LeaderboardMetadataResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.IZ = new LeaderboardBuffer(dataHolder);
        }

        protected void a(a.d<Leaderboards.LeaderboardMetadataResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public LeaderboardBuffer getLeaderboards() {
            return this.IZ;
        }
    }

    final class LeftRoomCallback
    extends b<RoomUpdateListener> {
        private final int Ah;
        private final String Ja;

        LeftRoomCallback(RoomUpdateListener roomUpdateListener, int n2, String string2) {
            super(roomUpdateListener);
            this.Ah = n2;
            this.Ja = string2;
        }

        public void a(RoomUpdateListener roomUpdateListener) {
            roomUpdateListener.onLeftRoom(this.Ah, this.Ja);
        }

        protected void dx() {
        }
    }

    final class MatchRemovedCallback
    extends b<OnTurnBasedMatchUpdateReceivedListener> {
        private final String Jb;

        MatchRemovedCallback(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener, String string2) {
            super(onTurnBasedMatchUpdateReceivedListener);
            this.Jb = string2;
        }

        protected /* synthetic */ void a(Object object) {
            this.b((OnTurnBasedMatchUpdateReceivedListener)object);
        }

        protected void b(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchRemoved(this.Jb);
        }

        protected void dx() {
        }
    }

    final class MatchUpdateReceivedBinderCallback
    extends AbstractGamesCallbacks {
        private final OnTurnBasedMatchUpdateReceivedListener Jc;

        MatchUpdateReceivedBinderCallback(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            this.Jc = onTurnBasedMatchUpdateReceivedListener;
        }

        @Override
        public void onTurnBasedMatchRemoved(String string2) {
            GamesClientImpl.this.a((ff.b)((Object)new MatchRemovedCallback(this.Jc, string2)));
        }

        @Override
        public void r(DataHolder parcelable) {
            TurnBasedMatchBuffer turnBasedMatchBuffer;
            block4 : {
                turnBasedMatchBuffer = new TurnBasedMatchBuffer((DataHolder)parcelable);
                parcelable = null;
                if (turnBasedMatchBuffer.getCount() > 0) {
                    parcelable = (TurnBasedMatch)((TurnBasedMatch)turnBasedMatchBuffer.get(0)).freeze();
                }
                if (parcelable == null) break block4;
                GamesClientImpl.this.a((ff.b)((Object)new MatchUpdateReceivedCallback(this.Jc, (TurnBasedMatch)parcelable)));
            }
            return;
            finally {
                turnBasedMatchBuffer.close();
            }
        }
    }

    final class MatchUpdateReceivedCallback
    extends b<OnTurnBasedMatchUpdateReceivedListener> {
        private final TurnBasedMatch Jd;

        MatchUpdateReceivedCallback(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener, TurnBasedMatch turnBasedMatch) {
            super(onTurnBasedMatchUpdateReceivedListener);
            this.Jd = turnBasedMatch;
        }

        protected /* synthetic */ void a(Object object) {
            this.b((OnTurnBasedMatchUpdateReceivedListener)object);
        }

        protected void b(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchReceived(this.Jd);
        }

        protected void dx() {
        }
    }

    final class MessageReceivedCallback
    extends b<RealTimeMessageReceivedListener> {
        private final RealTimeMessage Je;

        MessageReceivedCallback(RealTimeMessageReceivedListener realTimeMessageReceivedListener, RealTimeMessage realTimeMessage) {
            super(realTimeMessageReceivedListener);
            this.Je = realTimeMessage;
        }

        public void a(RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            if (realTimeMessageReceivedListener != null) {
                realTimeMessageReceivedListener.onRealTimeMessageReceived(this.Je);
            }
        }

        protected void dx() {
        }
    }

    final class NotifyAclLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Acls.LoadAclResult> wH;

        NotifyAclLoadedBinderCallback(a.d<Acls.LoadAclResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void A(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new NotifyAclLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class NotifyAclLoadedCallback
    extends ResultDataHolderCallback<a.d<Acls.LoadAclResult>>
    implements Acls.LoadAclResult {
        NotifyAclLoadedCallback(a.d<Acls.LoadAclResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
        }

        protected void a(a.d<Acls.LoadAclResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }
    }

    final class NotifyAclUpdatedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Status> wH;

        NotifyAclUpdatedBinderCallback(a.d<Status> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void aU(int n2) {
            GamesClientImpl.this.a((ff.b)((Object)new NotifyAclUpdatedCallback(this.wH, n2)));
        }
    }

    final class NotifyAclUpdatedCallback
    extends b<a.d<Status>> {
        private final Status wJ;

        NotifyAclUpdatedCallback(a.d<Status> d2, int n2) {
            super(d2);
            this.wJ = new Status(n2);
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<Status> d2) {
            d2.b(this.wJ);
        }

        protected void dx() {
        }
    }

    final class OwnerCoverPhotoUrisLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Players.LoadOwnerCoverPhotoUrisResult> wH;

        OwnerCoverPhotoUrisLoadedBinderCallback(a.d<Players.LoadOwnerCoverPhotoUrisResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void c(int n2, Bundle bundle) {
            bundle.setClassLoader(this.getClass().getClassLoader());
            GamesClientImpl.this.a((ff.b)((Object)new OwnerCoverPhotoUrisLoadedCallback(this.wH, n2, bundle)));
        }
    }

    final class OwnerCoverPhotoUrisLoadedCallback
    extends b<a.d<Players.LoadOwnerCoverPhotoUrisResult>>
    implements Players.LoadOwnerCoverPhotoUrisResult {
        private final Bundle Jf;
        private final Status wJ;

        OwnerCoverPhotoUrisLoadedCallback(a.d<Players.LoadOwnerCoverPhotoUrisResult> d2, int n2, Bundle bundle) {
            super(d2);
            this.wJ = new Status(n2);
            this.Jf = bundle;
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<Players.LoadOwnerCoverPhotoUrisResult> d2) {
            d2.b(this);
        }

        protected void dx() {
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class P2PConnectedCallback
    extends b<RoomStatusUpdateListener> {
        private final String Jg;

        P2PConnectedCallback(RoomStatusUpdateListener roomStatusUpdateListener, String string2) {
            super(roomStatusUpdateListener);
            this.Jg = string2;
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PConnected(this.Jg);
            }
        }

        protected void dx() {
        }
    }

    final class P2PDisconnectedCallback
    extends b<RoomStatusUpdateListener> {
        private final String Jg;

        P2PDisconnectedCallback(RoomStatusUpdateListener roomStatusUpdateListener, String string2) {
            super(roomStatusUpdateListener);
            this.Jg = string2;
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PDisconnected(this.Jg);
            }
        }

        protected void dx() {
        }
    }

    final class PeerConnectedCallback
    extends AbstractPeerStatusCallback {
        PeerConnectedCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] arrstring) {
            super(roomStatusUpdateListener, dataHolder, arrstring);
        }

        @Override
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersConnected(room, arrayList);
        }
    }

    final class PeerDeclinedCallback
    extends AbstractPeerStatusCallback {
        PeerDeclinedCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] arrstring) {
            super(roomStatusUpdateListener, dataHolder, arrstring);
        }

        @Override
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerDeclined(room, arrayList);
        }
    }

    final class PeerDisconnectedCallback
    extends AbstractPeerStatusCallback {
        PeerDisconnectedCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] arrstring) {
            super(roomStatusUpdateListener, dataHolder, arrstring);
        }

        @Override
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersDisconnected(room, arrayList);
        }
    }

    final class PeerInvitedToRoomCallback
    extends AbstractPeerStatusCallback {
        PeerInvitedToRoomCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] arrstring) {
            super(roomStatusUpdateListener, dataHolder, arrstring);
        }

        @Override
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerInvitedToRoom(room, arrayList);
        }
    }

    final class PeerJoinedRoomCallback
    extends AbstractPeerStatusCallback {
        PeerJoinedRoomCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] arrstring) {
            super(roomStatusUpdateListener, dataHolder, arrstring);
        }

        @Override
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerJoined(room, arrayList);
        }
    }

    final class PeerLeftRoomCallback
    extends AbstractPeerStatusCallback {
        PeerLeftRoomCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] arrstring) {
            super(roomStatusUpdateListener, dataHolder, arrstring);
        }

        @Override
        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerLeft(room, arrayList);
        }
    }

    final class PlayerLeaderboardScoreLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Leaderboards.LoadPlayerScoreResult> wH;

        PlayerLeaderboardScoreLoadedBinderCallback(a.d<Leaderboards.LoadPlayerScoreResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void C(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new PlayerLeaderboardScoreLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class PlayerLeaderboardScoreLoadedCallback
    extends d<a.d<Leaderboards.LoadPlayerScoreResult>>
    implements Leaderboards.LoadPlayerScoreResult {
        private final LeaderboardScoreEntity Jh;
        private final Status wJ;

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        PlayerLeaderboardScoreLoadedCallback(a.d<Leaderboards.LoadPlayerScoreResult> d2, DataHolder dataHolder) {
            super(d2, dataHolder);
            this.wJ = new Status(dataHolder.getStatusCode());
            Object.this = new LeaderboardScoreBuffer(dataHolder);
            try {
                if (Object.this.getCount() > 0) {
                    this.Jh = (LeaderboardScoreEntity)Object.this.get(0).freeze();
                    do {
                        return;
                        break;
                    } while (true);
                }
                this.Jh = null;
                return;
            }
            finally {
                Object.this.close();
            }
        }

        protected void a(a.d<Leaderboards.LoadPlayerScoreResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public LeaderboardScore getScore() {
            return this.Jh;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class PlayersLoadedBinderCallback
    extends AbstractGamesCallbacks {
        private final a.d<Players.LoadPlayersResult> wH;

        PlayersLoadedBinderCallback(a.d<Players.LoadPlayersResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void e(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new PlayersLoadedCallback(this.wH, dataHolder)));
        }
    }

    final class PlayersLoadedCallback
    extends ResultDataHolderCallback<a.d<Players.LoadPlayersResult>>
    implements Players.LoadPlayersResult {
        private final PlayerBuffer Ji;

        PlayersLoadedCallback(a.d<Players.LoadPlayersResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.Ji = new PlayerBuffer(dataHolder);
        }

        protected void a(a.d<Players.LoadPlayersResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public PlayerBuffer getPlayers() {
            return this.Ji;
        }
    }

    final class RealTimeMessageSentCallback
    extends b<RealTimeMultiplayer.ReliableMessageSentCallback> {
        private final int Ah;
        private final String Jj;
        private final int Jk;

        RealTimeMessageSentCallback(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback, int n2, int n3, String string2) {
            super(reliableMessageSentCallback);
            this.Ah = n2;
            this.Jk = n3;
            this.Jj = string2;
        }

        public void a(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback) {
            if (reliableMessageSentCallback != null) {
                reliableMessageSentCallback.onRealTimeMessageSent(this.Ah, this.Jk, this.Jj);
            }
        }

        protected void dx() {
        }
    }

    final class RealTimeReliableMessageBinderCallbacks
    extends AbstractGamesCallbacks {
        final RealTimeMultiplayer.ReliableMessageSentCallback Jl;

        public RealTimeReliableMessageBinderCallbacks(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback) {
            this.Jl = reliableMessageSentCallback;
        }

        @Override
        public void b(int n2, int n3, String string2) {
            GamesClientImpl.this.a((ff.b)((Object)new RealTimeMessageSentCallback(this.Jl, n2, n3, string2)));
        }
    }

    final class RequestReceivedBinderCallback
    extends AbstractGamesCallbacks {
        private final OnRequestReceivedListener Jm;

        RequestReceivedBinderCallback(OnRequestReceivedListener onRequestReceivedListener) {
            this.Jm = onRequestReceivedListener;
        }

        @Override
        public void m(DataHolder parcelable) {
            GameRequestBuffer gameRequestBuffer;
            block4 : {
                gameRequestBuffer = new GameRequestBuffer((DataHolder)parcelable);
                parcelable = null;
                if (gameRequestBuffer.getCount() > 0) {
                    parcelable = (GameRequest)((GameRequest)gameRequestBuffer.get(0)).freeze();
                }
                if (parcelable == null) break block4;
                GamesClientImpl.this.a((ff.b)((Object)new RequestReceivedCallback(this.Jm, (GameRequest)parcelable)));
            }
            return;
            finally {
                gameRequestBuffer.close();
            }
        }

        @Override
        public void onRequestRemoved(String string2) {
            GamesClientImpl.this.a((ff.b)((Object)new RequestRemovedCallback(this.Jm, string2)));
        }
    }

    final class RequestReceivedCallback
    extends b<OnRequestReceivedListener> {
        private final GameRequest Jn;

        RequestReceivedCallback(OnRequestReceivedListener onRequestReceivedListener, GameRequest gameRequest) {
            super(onRequestReceivedListener);
            this.Jn = gameRequest;
        }

        protected /* synthetic */ void a(Object object) {
            this.b((OnRequestReceivedListener)object);
        }

        protected void b(OnRequestReceivedListener onRequestReceivedListener) {
            onRequestReceivedListener.onRequestReceived(this.Jn);
        }

        protected void dx() {
        }
    }

    final class RequestRemovedCallback
    extends b<OnRequestReceivedListener> {
        private final String Jo;

        RequestRemovedCallback(OnRequestReceivedListener onRequestReceivedListener, String string2) {
            super(onRequestReceivedListener);
            this.Jo = string2;
        }

        protected /* synthetic */ void a(Object object) {
            this.b((OnRequestReceivedListener)object);
        }

        protected void b(OnRequestReceivedListener onRequestReceivedListener) {
            onRequestReceivedListener.onRequestRemoved(this.Jo);
        }

        protected void dx() {
        }
    }

    final class RequestSentBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<Requests.SendRequestResult> Jp;

        public RequestSentBinderCallbacks(a.d<Requests.SendRequestResult> d2) {
            this.Jp = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void E(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new RequestSentCallback(this.Jp, dataHolder)));
        }
    }

    final class RequestSentCallback
    extends ResultDataHolderCallback<a.d<Requests.SendRequestResult>>
    implements Requests.SendRequestResult {
        private final GameRequest Jn;

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        RequestSentCallback(a.d<Requests.SendRequestResult> d2, DataHolder dataHolder) {
            super((GamesClientImpl)Object.this, d2, dataHolder);
            Object.this = new GameRequestBuffer(dataHolder);
            try {
                if (Object.this.getCount() > 0) {
                    this.Jn = (GameRequest)((GameRequest)Object.this.get(0)).freeze();
                    do {
                        return;
                        break;
                    } while (true);
                }
                this.Jn = null;
                return;
            }
            finally {
                Object.this.close();
            }
        }

        protected void a(a.d<Requests.SendRequestResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }
    }

    final class RequestSummariesLoadedBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<Requests.LoadRequestSummariesResult> Jq;

        public RequestSummariesLoadedBinderCallbacks(a.d<Requests.LoadRequestSummariesResult> d2) {
            this.Jq = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void F(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new RequestSummariesLoadedCallback(this.Jq, dataHolder)));
        }
    }

    final class RequestSummariesLoadedCallback
    extends ResultDataHolderCallback<a.d<Requests.LoadRequestSummariesResult>>
    implements Requests.LoadRequestSummariesResult {
        RequestSummariesLoadedCallback(a.d<Requests.LoadRequestSummariesResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
        }

        protected void a(a.d<Requests.LoadRequestSummariesResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }
    }

    final class RequestsLoadedBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<Requests.LoadRequestsResult> Jr;

        public RequestsLoadedBinderCallbacks(a.d<Requests.LoadRequestsResult> d2) {
            this.Jr = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void b(int n2, Bundle bundle) {
            bundle.setClassLoader(this.getClass().getClassLoader());
            Status status = new Status(n2);
            GamesClientImpl.this.a((ff.b)((Object)new RequestsLoadedCallback(this.Jr, status, bundle)));
        }
    }

    final class RequestsLoadedCallback
    extends b<a.d<Requests.LoadRequestsResult>>
    implements Requests.LoadRequestsResult {
        private final Bundle Js;
        private final Status wJ;

        RequestsLoadedCallback(a.d<Requests.LoadRequestsResult> d2, Status status, Bundle bundle) {
            super(d2);
            this.wJ = status;
            this.Js = bundle;
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<Requests.LoadRequestsResult> d2) {
            d2.b(this);
        }

        protected void dx() {
            this.release();
        }

        @Override
        public GameRequestBuffer getRequests(int n2) {
            String string2 = RequestType.bd(n2);
            if (!this.Js.containsKey(string2)) {
                return null;
            }
            return new GameRequestBuffer((DataHolder)this.Js.get(string2));
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }

        @Override
        public void release() {
            for (Object object : this.Js.keySet()) {
                if ((object = (DataHolder)this.Js.getParcelable((String)object)) == null) continue;
                object.close();
            }
        }
    }

    final class RequestsUpdatedBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<Requests.UpdateRequestsResult> Jt;

        public RequestsUpdatedBinderCallbacks(a.d<Requests.UpdateRequestsResult> d2) {
            this.Jt = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void D(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new RequestsUpdatedCallback(this.Jt, dataHolder)));
        }
    }

    final class RequestsUpdatedCallback
    extends ResultDataHolderCallback<a.d<Requests.UpdateRequestsResult>>
    implements Requests.UpdateRequestsResult {
        private final RequestUpdateOutcomes Ju;

        RequestsUpdatedCallback(a.d<Requests.UpdateRequestsResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            this.Ju = RequestUpdateOutcomes.J(dataHolder);
        }

        protected void a(a.d<Requests.UpdateRequestsResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public Set<String> getRequestIds() {
            return this.Ju.getRequestIds();
        }

        @Override
        public int getRequestOutcome(String string2) {
            return this.Ju.getRequestOutcome(string2);
        }
    }

    abstract class ResultDataHolderCallback<R extends a.d<?>>
    extends ff<IGamesService>
    implements Releasable,
    Result {
        final DataHolder BB;
        final Status wJ;

        public ResultDataHolderCallback(DataHolder r2) {
            super(r2, dataHolder);
            this.wJ = new Status(dataHolder.getStatusCode());
            this.BB = dataHolder;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }

        @Override
        public void release() {
            if (this.BB != null) {
                this.BB.close();
            }
        }
    }

    final class RoomAutoMatchingCallback
    extends AbstractRoomStatusCallback {
        RoomAutoMatchingCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        @Override
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomAutoMatching(room);
        }
    }

    final class RoomBinderCallbacks
    extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final RoomUpdateListener Jv;
        private final RoomStatusUpdateListener Jw;
        private final RealTimeMessageReceivedListener Jx;

        public RoomBinderCallbacks(GamesClientImpl gamesClientImpl, RoomUpdateListener roomUpdateListener) {
            this.IJ = gamesClientImpl;
            this.Jv = fq.b(roomUpdateListener, (Object)"Callbacks must not be null");
            this.Jw = null;
            this.Jx = null;
        }

        public RoomBinderCallbacks(GamesClientImpl gamesClientImpl, RoomUpdateListener roomUpdateListener, RoomStatusUpdateListener roomStatusUpdateListener, RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            this.IJ = gamesClientImpl;
            this.Jv = fq.b(roomUpdateListener, (Object)"Callbacks must not be null");
            this.Jw = roomStatusUpdateListener;
            this.Jx = realTimeMessageReceivedListener;
        }

        @Override
        public void a(DataHolder dataHolder, String[] arrstring) {
            this.IJ.a((ff.b)((Object)this.IJ.new PeerInvitedToRoomCallback(this.Jw, dataHolder, arrstring)));
        }

        @Override
        public void b(DataHolder dataHolder, String[] arrstring) {
            this.IJ.a((ff.b)((Object)this.IJ.new PeerJoinedRoomCallback(this.Jw, dataHolder, arrstring)));
        }

        @Override
        public void c(DataHolder dataHolder, String[] arrstring) {
            this.IJ.a((ff.b)((Object)this.IJ.new PeerLeftRoomCallback(this.Jw, dataHolder, arrstring)));
        }

        @Override
        public void d(DataHolder dataHolder, String[] arrstring) {
            this.IJ.a((ff.b)((Object)this.IJ.new PeerDeclinedCallback(this.Jw, dataHolder, arrstring)));
        }

        @Override
        public void e(DataHolder dataHolder, String[] arrstring) {
            this.IJ.a((ff.b)((Object)this.IJ.new PeerConnectedCallback(this.Jw, dataHolder, arrstring)));
        }

        @Override
        public void f(DataHolder dataHolder, String[] arrstring) {
            this.IJ.a((ff.b)((Object)this.IJ.new PeerDisconnectedCallback(this.Jw, dataHolder, arrstring)));
        }

        @Override
        public void onLeftRoom(int n2, String string2) {
            this.IJ.a((ff.b)((Object)this.IJ.new LeftRoomCallback(this.Jv, n2, string2)));
        }

        @Override
        public void onP2PConnected(String string2) {
            this.IJ.a((ff.b)((Object)this.IJ.new P2PConnectedCallback(this.Jw, string2)));
        }

        @Override
        public void onP2PDisconnected(String string2) {
            this.IJ.a((ff.b)((Object)this.IJ.new P2PDisconnectedCallback(this.Jw, string2)));
        }

        @Override
        public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
            this.IJ.a((ff.b)((Object)this.IJ.new MessageReceivedCallback(this.Jx, realTimeMessage)));
        }

        @Override
        public void s(DataHolder dataHolder) {
            this.IJ.a((ff.b)((Object)this.IJ.new RoomCreatedCallback(this.Jv, dataHolder)));
        }

        @Override
        public void t(DataHolder dataHolder) {
            this.IJ.a((ff.b)((Object)this.IJ.new JoinedRoomCallback(this.Jv, dataHolder)));
        }

        @Override
        public void u(DataHolder dataHolder) {
            this.IJ.a((ff.b)((Object)this.IJ.new RoomConnectingCallback(this.Jw, dataHolder)));
        }

        @Override
        public void v(DataHolder dataHolder) {
            this.IJ.a((ff.b)((Object)this.IJ.new RoomAutoMatchingCallback(this.Jw, dataHolder)));
        }

        @Override
        public void w(DataHolder dataHolder) {
            this.IJ.a((ff.b)((Object)this.IJ.new RoomConnectedCallback(this.Jv, dataHolder)));
        }

        @Override
        public void x(DataHolder dataHolder) {
            this.IJ.a((ff.b)((Object)this.IJ.new ConnectedToRoomCallback(this.Jw, dataHolder)));
        }

        @Override
        public void y(DataHolder dataHolder) {
            this.IJ.a((ff.b)((Object)this.IJ.new DisconnectedFromRoomCallback(this.Jw, dataHolder)));
        }
    }

    final class RoomConnectedCallback
    extends AbstractRoomCallback {
        RoomConnectedCallback(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            super(roomUpdateListener, dataHolder);
        }

        @Override
        public void a(RoomUpdateListener roomUpdateListener, Room room, int n2) {
            roomUpdateListener.onRoomConnected(n2, room);
        }
    }

    final class RoomConnectingCallback
    extends AbstractRoomStatusCallback {
        RoomConnectingCallback(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        @Override
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomConnecting(room);
        }
    }

    final class RoomCreatedCallback
    extends AbstractRoomCallback {
        public RoomCreatedCallback(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            super(roomUpdateListener, dataHolder);
        }

        @Override
        public void a(RoomUpdateListener roomUpdateListener, Room room, int n2) {
            roomUpdateListener.onRoomCreated(n2, room);
        }
    }

    final class SignOutCompleteBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<Status> wH;

        public SignOutCompleteBinderCallbacks(a.d<Status> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void du() {
            Status status = new Status(0);
            GamesClientImpl.this.a((ff.b)((Object)new SignOutCompleteCallback(this.wH, status)));
        }
    }

    final class SignOutCompleteCallback
    extends b<a.d<Status>> {
        private final Status wJ;

        public SignOutCompleteCallback(a.d<Status> d2, Status status) {
            super(d2);
            this.wJ = status;
        }

        public /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        public void c(a.d<Status> d2) {
            d2.b(this.wJ);
        }

        protected void dx() {
        }
    }

    final class SubmitScoreBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<Leaderboards.SubmitScoreResult> wH;

        public SubmitScoreBinderCallbacks(a.d<Leaderboards.SubmitScoreResult> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void d(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new SubmitScoreCallback(this.wH, dataHolder)));
        }
    }

    final class SubmitScoreCallback
    extends ResultDataHolderCallback<a.d<Leaderboards.SubmitScoreResult>>
    implements Leaderboards.SubmitScoreResult {
        private final ScoreSubmissionData Jy;

        public SubmitScoreCallback(a.d<Leaderboards.SubmitScoreResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
            try {
                this.Jy = new ScoreSubmissionData(dataHolder);
                return;
            }
            finally {
                dataHolder.close();
            }
        }

        public void a(a.d<Leaderboards.SubmitScoreResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public ScoreSubmissionData getScoreData() {
            return this.Jy;
        }
    }

    abstract class TurnBasedMatchCallback<T extends a.d<?>>
    extends ResultDataHolderCallback<T> {
        final TurnBasedMatch Jd;

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        TurnBasedMatchCallback(DataHolder t2) {
            super((GamesClientImpl)((Object)Object.this), t2, dataHolder);
            Object.this = new TurnBasedMatchBuffer(dataHolder);
            try {
                if (Object.this.getCount() > 0) {
                    this.Jd = (TurnBasedMatch)((TurnBasedMatch)Object.this.get(0)).freeze();
                    do {
                        return;
                        break;
                    } while (true);
                }
                this.Jd = null;
                return;
            }
            finally {
                Object.this.close();
            }
        }

        protected void a(T t2, DataHolder dataHolder) {
            this.k(t2);
        }

        public TurnBasedMatch getMatch() {
            return this.Jd;
        }

        abstract void k(T var1);
    }

    final class TurnBasedMatchCanceledBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.CancelMatchResult> Jz;

        public TurnBasedMatchCanceledBinderCallbacks(a.d<TurnBasedMultiplayer.CancelMatchResult> d2) {
            this.Jz = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void f(int n2, String string2) {
            Status status = new Status(n2);
            GamesClientImpl.this.a((ff.b)((Object)new TurnBasedMatchCanceledCallback(this.Jz, status, string2)));
        }
    }

    final class TurnBasedMatchCanceledCallback
    extends b<a.d<TurnBasedMultiplayer.CancelMatchResult>>
    implements TurnBasedMultiplayer.CancelMatchResult {
        private final String JA;
        private final Status wJ;

        TurnBasedMatchCanceledCallback(a.d<TurnBasedMultiplayer.CancelMatchResult> d2, Status status, String string2) {
            super(d2);
            this.wJ = status;
            this.JA = string2;
        }

        public /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        public void c(a.d<TurnBasedMultiplayer.CancelMatchResult> d2) {
            d2.b(this);
        }

        protected void dx() {
        }

        @Override
        public String getMatchId() {
            return this.JA;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class TurnBasedMatchInitiatedBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.InitiateMatchResult> JB;

        public TurnBasedMatchInitiatedBinderCallbacks(a.d<TurnBasedMultiplayer.InitiateMatchResult> d2) {
            this.JB = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void o(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new TurnBasedMatchInitiatedCallback(this.JB, dataHolder)));
        }
    }

    final class TurnBasedMatchInitiatedCallback
    extends TurnBasedMatchCallback<a.d<TurnBasedMultiplayer.InitiateMatchResult>>
    implements TurnBasedMultiplayer.InitiateMatchResult {
        TurnBasedMatchInitiatedCallback(a.d<TurnBasedMultiplayer.InitiateMatchResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
        }

        @Override
        protected void k(a.d<TurnBasedMultiplayer.InitiateMatchResult> d2) {
            d2.b(this);
        }
    }

    final class TurnBasedMatchLeftBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.LeaveMatchResult> JC;

        public TurnBasedMatchLeftBinderCallbacks(a.d<TurnBasedMultiplayer.LeaveMatchResult> d2) {
            this.JC = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void q(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new TurnBasedMatchLeftCallback(this.JC, dataHolder)));
        }
    }

    final class TurnBasedMatchLeftCallback
    extends TurnBasedMatchCallback<a.d<TurnBasedMultiplayer.LeaveMatchResult>>
    implements TurnBasedMultiplayer.LeaveMatchResult {
        TurnBasedMatchLeftCallback(a.d<TurnBasedMultiplayer.LeaveMatchResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
        }

        @Override
        protected void k(a.d<TurnBasedMultiplayer.LeaveMatchResult> d2) {
            d2.b(this);
        }
    }

    final class TurnBasedMatchLoadedBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.LoadMatchResult> JD;

        public TurnBasedMatchLoadedBinderCallbacks(a.d<TurnBasedMultiplayer.LoadMatchResult> d2) {
            this.JD = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void n(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new TurnBasedMatchLoadedCallback(this.JD, dataHolder)));
        }
    }

    final class TurnBasedMatchLoadedCallback
    extends TurnBasedMatchCallback<a.d<TurnBasedMultiplayer.LoadMatchResult>>
    implements TurnBasedMultiplayer.LoadMatchResult {
        TurnBasedMatchLoadedCallback(a.d<TurnBasedMultiplayer.LoadMatchResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
        }

        @Override
        protected void k(a.d<TurnBasedMultiplayer.LoadMatchResult> d2) {
            d2.b(this);
        }
    }

    final class TurnBasedMatchUpdatedBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.UpdateMatchResult> JE;

        public TurnBasedMatchUpdatedBinderCallbacks(a.d<TurnBasedMultiplayer.UpdateMatchResult> d2) {
            this.JE = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void p(DataHolder dataHolder) {
            GamesClientImpl.this.a((ff.b)((Object)new TurnBasedMatchUpdatedCallback(this.JE, dataHolder)));
        }
    }

    final class TurnBasedMatchUpdatedCallback
    extends TurnBasedMatchCallback<a.d<TurnBasedMultiplayer.UpdateMatchResult>>
    implements TurnBasedMultiplayer.UpdateMatchResult {
        TurnBasedMatchUpdatedCallback(a.d<TurnBasedMultiplayer.UpdateMatchResult> d2, DataHolder dataHolder) {
            super(GamesClientImpl.this, d2, dataHolder);
        }

        @Override
        protected void k(a.d<TurnBasedMultiplayer.UpdateMatchResult> d2) {
            d2.b(this);
        }
    }

    final class TurnBasedMatchesLoadedBinderCallbacks
    extends AbstractGamesCallbacks {
        private final a.d<TurnBasedMultiplayer.LoadMatchesResult> JF;

        public TurnBasedMatchesLoadedBinderCallbacks(a.d<TurnBasedMultiplayer.LoadMatchesResult> d2) {
            this.JF = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void a(int n2, Bundle bundle) {
            bundle.setClassLoader(this.getClass().getClassLoader());
            Status status = new Status(n2);
            GamesClientImpl.this.a((ff.b)((Object)new TurnBasedMatchesLoadedCallback(this.JF, status, bundle)));
        }
    }

    final class TurnBasedMatchesLoadedCallback
    extends b<a.d<TurnBasedMultiplayer.LoadMatchesResult>>
    implements TurnBasedMultiplayer.LoadMatchesResult {
        private final LoadMatchesResponse JG;
        private final Status wJ;

        TurnBasedMatchesLoadedCallback(a.d<TurnBasedMultiplayer.LoadMatchesResult> d2, Status status, Bundle bundle) {
            super(d2);
            this.wJ = status;
            this.JG = new LoadMatchesResponse(bundle);
        }

        protected /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        protected void c(a.d<TurnBasedMultiplayer.LoadMatchesResult> d2) {
            d2.b(this);
        }

        protected void dx() {
        }

        @Override
        public LoadMatchesResponse getMatches() {
            return this.JG;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }

        @Override
        public void release() {
            this.JG.close();
        }
    }

}

