package com.google.android.gms.games.internal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.GamesMetadata.LoadExtendedGamesResult;
import com.google.android.gms.games.GamesMetadata.LoadGameInstancesResult;
import com.google.android.gms.games.GamesMetadata.LoadGameSearchSuggestionsResult;
import com.google.android.gms.games.GamesMetadata.LoadGamesResult;
import com.google.android.gms.games.Notifications.ContactSettingLoadResult;
import com.google.android.gms.games.Notifications.GameMuteStatusChangeResult;
import com.google.android.gms.games.Notifications.GameMuteStatusLoadResult;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.Players.LoadExtendedPlayersResult;
import com.google.android.gms.games.Players.LoadOwnerCoverPhotoUrisResult;
import com.google.android.gms.games.Players.LoadPlayersResult;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements.LoadAchievementsResult;
import com.google.android.gms.games.achievement.Achievements.UpdateAchievementResult;
import com.google.android.gms.games.internal.IGamesService.Stub;
import com.google.android.gms.games.internal.constants.RequestType;
import com.google.android.gms.games.internal.game.Acls.LoadAclResult;
import com.google.android.gms.games.internal.game.ExtendedGameBuffer;
import com.google.android.gms.games.internal.game.GameInstanceBuffer;
import com.google.android.gms.games.internal.player.ExtendedPlayerBuffer;
import com.google.android.gms.games.internal.request.RequestUpdateOutcomes;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardEntity;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScoreEntity;
import com.google.android.gms.games.leaderboard.Leaderboards.LeaderboardMetadataResult;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadPlayerScoreResult;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadScoresResult;
import com.google.android.gms.games.leaderboard.Leaderboards.SubmitScoreResult;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.Invitations.LoadInvitationsResult;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.ParticipantUtils;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer.ReliableMessageSentCallback;
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
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.CancelMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.InitiateMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LeaveMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LoadMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LoadMatchesResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.UpdateMatchResult;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import com.google.android.gms.games.request.Requests.LoadRequestSummariesResult;
import com.google.android.gms.games.request.Requests.LoadRequestsResult;
import com.google.android.gms.games.request.Requests.SendRequestResult;
import com.google.android.gms.games.request.Requests.UpdateRequestsResult;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.ff.C0391b;
import com.google.android.gms.internal.ff.C0889d;
import com.google.android.gms.internal.ff.C1374e;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class GamesClientImpl extends ff<IGamesService> implements ConnectionCallbacks, OnConnectionFailedListener {
    private boolean IA;
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
    private boolean Iz;
    private final String wG;

    final class ContactSettingsUpdatedCallback extends C0391b<C0244d<Status>> {
        final /* synthetic */ GamesClientImpl IJ;
        private final Status wJ;

        ContactSettingsUpdatedCallback(GamesClientImpl gamesClientImpl, C0244d<Status> resultHolder, int statusCode) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            this.wJ = new Status(statusCode);
        }

        protected /* synthetic */ void m1758a(Object obj) {
            m1759c((C0244d) obj);
        }

        protected void m1759c(C0244d<Status> c0244d) {
            c0244d.m132b(this.wJ);
        }

        protected void dx() {
        }
    }

    final class InvitationReceivedCallback extends C0391b<OnInvitationReceivedListener> {
        final /* synthetic */ GamesClientImpl IJ;
        private final Invitation IU;

        InvitationReceivedCallback(GamesClientImpl gamesClientImpl, OnInvitationReceivedListener listener, Invitation invitation) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.IU = invitation;
        }

        protected /* synthetic */ void m1760a(Object obj) {
            m1761b((OnInvitationReceivedListener) obj);
        }

        protected void m1761b(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationReceived(this.IU);
        }

        protected void dx() {
        }
    }

    final class InvitationRemovedCallback extends C0391b<OnInvitationReceivedListener> {
        final /* synthetic */ GamesClientImpl IJ;
        private final String IV;

        InvitationRemovedCallback(GamesClientImpl gamesClientImpl, OnInvitationReceivedListener listener, String invitationId) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.IV = invitationId;
        }

        protected /* synthetic */ void m1762a(Object obj) {
            m1763b((OnInvitationReceivedListener) obj);
        }

        protected void m1763b(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationRemoved(this.IV);
        }

        protected void dx() {
        }
    }

    final class LeftRoomCallback extends C0391b<RoomUpdateListener> {
        private final int Ah;
        final /* synthetic */ GamesClientImpl IJ;
        private final String Ja;

        LeftRoomCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, int statusCode, String roomId) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Ah = statusCode;
            this.Ja = roomId;
        }

        public void m1764a(RoomUpdateListener roomUpdateListener) {
            roomUpdateListener.onLeftRoom(this.Ah, this.Ja);
        }

        protected void dx() {
        }
    }

    final class MatchRemovedCallback extends C0391b<OnTurnBasedMatchUpdateReceivedListener> {
        final /* synthetic */ GamesClientImpl IJ;
        private final String Jb;

        MatchRemovedCallback(GamesClientImpl gamesClientImpl, OnTurnBasedMatchUpdateReceivedListener listener, String matchId) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Jb = matchId;
        }

        protected /* synthetic */ void m1766a(Object obj) {
            m1767b((OnTurnBasedMatchUpdateReceivedListener) obj);
        }

        protected void m1767b(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchRemoved(this.Jb);
        }

        protected void dx() {
        }
    }

    final class MatchUpdateReceivedCallback extends C0391b<OnTurnBasedMatchUpdateReceivedListener> {
        final /* synthetic */ GamesClientImpl IJ;
        private final TurnBasedMatch Jd;

        MatchUpdateReceivedCallback(GamesClientImpl gamesClientImpl, OnTurnBasedMatchUpdateReceivedListener listener, TurnBasedMatch match) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Jd = match;
        }

        protected /* synthetic */ void m1768a(Object obj) {
            m1769b((OnTurnBasedMatchUpdateReceivedListener) obj);
        }

        protected void m1769b(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchReceived(this.Jd);
        }

        protected void dx() {
        }
    }

    final class MessageReceivedCallback extends C0391b<RealTimeMessageReceivedListener> {
        final /* synthetic */ GamesClientImpl IJ;
        private final RealTimeMessage Je;

        MessageReceivedCallback(GamesClientImpl gamesClientImpl, RealTimeMessageReceivedListener listener, RealTimeMessage message) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Je = message;
        }

        public void m1770a(RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            if (realTimeMessageReceivedListener != null) {
                realTimeMessageReceivedListener.onRealTimeMessageReceived(this.Je);
            }
        }

        protected void dx() {
        }
    }

    final class NotifyAclUpdatedCallback extends C0391b<C0244d<Status>> {
        final /* synthetic */ GamesClientImpl IJ;
        private final Status wJ;

        NotifyAclUpdatedCallback(GamesClientImpl gamesClientImpl, C0244d<Status> resultHolder, int statusCode) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            this.wJ = new Status(statusCode);
        }

        protected /* synthetic */ void m1772a(Object obj) {
            m1773c((C0244d) obj);
        }

        protected void m1773c(C0244d<Status> c0244d) {
            c0244d.m132b(this.wJ);
        }

        protected void dx() {
        }
    }

    final class P2PConnectedCallback extends C0391b<RoomStatusUpdateListener> {
        final /* synthetic */ GamesClientImpl IJ;
        private final String Jg;

        P2PConnectedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, String participantId) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Jg = participantId;
        }

        public void m1774a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PConnected(this.Jg);
            }
        }

        protected void dx() {
        }
    }

    final class P2PDisconnectedCallback extends C0391b<RoomStatusUpdateListener> {
        final /* synthetic */ GamesClientImpl IJ;
        private final String Jg;

        P2PDisconnectedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, String participantId) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Jg = participantId;
        }

        public void m1776a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PDisconnected(this.Jg);
            }
        }

        protected void dx() {
        }
    }

    final class RealTimeMessageSentCallback extends C0391b<ReliableMessageSentCallback> {
        private final int Ah;
        final /* synthetic */ GamesClientImpl IJ;
        private final String Jj;
        private final int Jk;

        RealTimeMessageSentCallback(GamesClientImpl gamesClientImpl, ReliableMessageSentCallback listener, int statusCode, int token, String recipientParticipantId) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Ah = statusCode;
            this.Jk = token;
            this.Jj = recipientParticipantId;
        }

        public void m1778a(ReliableMessageSentCallback reliableMessageSentCallback) {
            if (reliableMessageSentCallback != null) {
                reliableMessageSentCallback.onRealTimeMessageSent(this.Ah, this.Jk, this.Jj);
            }
        }

        protected void dx() {
        }
    }

    final class RequestReceivedCallback extends C0391b<OnRequestReceivedListener> {
        final /* synthetic */ GamesClientImpl IJ;
        private final GameRequest Jn;

        RequestReceivedCallback(GamesClientImpl gamesClientImpl, OnRequestReceivedListener listener, GameRequest request) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Jn = request;
        }

        protected /* synthetic */ void m1780a(Object obj) {
            m1781b((OnRequestReceivedListener) obj);
        }

        protected void m1781b(OnRequestReceivedListener onRequestReceivedListener) {
            onRequestReceivedListener.onRequestReceived(this.Jn);
        }

        protected void dx() {
        }
    }

    final class RequestRemovedCallback extends C0391b<OnRequestReceivedListener> {
        final /* synthetic */ GamesClientImpl IJ;
        private final String Jo;

        RequestRemovedCallback(GamesClientImpl gamesClientImpl, OnRequestReceivedListener listener, String requestId) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Jo = requestId;
        }

        protected /* synthetic */ void m1782a(Object obj) {
            m1783b((OnRequestReceivedListener) obj);
        }

        protected void m1783b(OnRequestReceivedListener onRequestReceivedListener) {
            onRequestReceivedListener.onRequestRemoved(this.Jo);
        }

        protected void dx() {
        }
    }

    final class SignOutCompleteCallback extends C0391b<C0244d<Status>> {
        final /* synthetic */ GamesClientImpl IJ;
        private final Status wJ;

        public SignOutCompleteCallback(GamesClientImpl gamesClientImpl, C0244d<Status> resultHolder, Status status) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            this.wJ = status;
        }

        public /* synthetic */ void m1784a(Object obj) {
            m1785c((C0244d) obj);
        }

        public void m1785c(C0244d<Status> c0244d) {
            c0244d.m132b(this.wJ);
        }

        protected void dx() {
        }
    }

    abstract class AbstractRoomCallback extends C0889d<RoomUpdateListener> {
        final /* synthetic */ GamesClientImpl IJ;

        AbstractRoomCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        protected void m2760a(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            m2761a(roomUpdateListener, this.IJ.m2782G(dataHolder), dataHolder.getStatusCode());
        }

        protected abstract void m2761a(RoomUpdateListener roomUpdateListener, Room room, int i);
    }

    abstract class AbstractRoomStatusCallback extends C0889d<RoomStatusUpdateListener> {
        final /* synthetic */ GamesClientImpl IJ;

        AbstractRoomStatusCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        protected void m2763a(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            m2764a(roomStatusUpdateListener, this.IJ.m2782G(dataHolder));
        }

        protected abstract void m2764a(RoomStatusUpdateListener roomStatusUpdateListener, Room room);
    }

    final class AchievementUpdatedCallback extends C0391b<C0244d<UpdateAchievementResult>> implements UpdateAchievementResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final String IK;
        private final Status wJ;

        AchievementUpdatedCallback(GamesClientImpl gamesClientImpl, C0244d<UpdateAchievementResult> resultHolder, int statusCode, String achievementId) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            this.wJ = new Status(statusCode);
            this.IK = achievementId;
        }

        protected /* synthetic */ void m2766a(Object obj) {
            m2767c((C0244d) obj);
        }

        protected void m2767c(C0244d<UpdateAchievementResult> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
        }

        public String getAchievementId() {
            return this.IK;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    final class GameMuteStatusChangedCallback extends C0391b<C0244d<GameMuteStatusChangeResult>> implements GameMuteStatusChangeResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final String IP;
        private final boolean IQ;
        private final Status wJ;

        public GameMuteStatusChangedCallback(GamesClientImpl gamesClientImpl, C0244d<GameMuteStatusChangeResult> resultHolder, int statusCode, String externalGameId, boolean isMuted) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            this.wJ = new Status(statusCode);
            this.IP = externalGameId;
            this.IQ = isMuted;
        }

        protected /* synthetic */ void m2768a(Object obj) {
            m2769c((C0244d) obj);
        }

        protected void m2769c(C0244d<GameMuteStatusChangeResult> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    final class GameMuteStatusLoadedCallback extends C0391b<C0244d<GameMuteStatusLoadResult>> implements GameMuteStatusLoadResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final String IP;
        private final boolean IQ;
        private final Status wJ;

        public GameMuteStatusLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<GameMuteStatusLoadResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            try {
                this.wJ = new Status(dataHolder.getStatusCode());
                if (dataHolder.getCount() > 0) {
                    this.IP = dataHolder.getString("external_game_id", 0, 0);
                    this.IQ = dataHolder.getBoolean("muted", 0, 0);
                } else {
                    this.IP = null;
                    this.IQ = false;
                }
                dataHolder.close();
            } catch (Throwable th) {
                dataHolder.close();
            }
        }

        protected /* synthetic */ void m2770a(Object obj) {
            m2771c((C0244d) obj);
        }

        protected void m2771c(C0244d<GameMuteStatusLoadResult> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    final class OwnerCoverPhotoUrisLoadedCallback extends C0391b<C0244d<LoadOwnerCoverPhotoUrisResult>> implements LoadOwnerCoverPhotoUrisResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final Bundle Jf;
        private final Status wJ;

        OwnerCoverPhotoUrisLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadOwnerCoverPhotoUrisResult> resultHolder, int statusCode, Bundle bundle) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            this.wJ = new Status(statusCode);
            this.Jf = bundle;
        }

        protected /* synthetic */ void m2772a(Object obj) {
            m2773c((C0244d) obj);
        }

        protected void m2773c(C0244d<LoadOwnerCoverPhotoUrisResult> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    final class PlayerLeaderboardScoreLoadedCallback extends C0889d<C0244d<LoadPlayerScoreResult>> implements LoadPlayerScoreResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final LeaderboardScoreEntity Jh;
        private final Status wJ;

        PlayerLeaderboardScoreLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadPlayerScoreResult> resultHolder, DataHolder scoreHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, scoreHolder);
            this.wJ = new Status(scoreHolder.getStatusCode());
            LeaderboardScoreBuffer leaderboardScoreBuffer = new LeaderboardScoreBuffer(scoreHolder);
            try {
                if (leaderboardScoreBuffer.getCount() > 0) {
                    this.Jh = (LeaderboardScoreEntity) leaderboardScoreBuffer.get(0).freeze();
                } else {
                    this.Jh = null;
                }
                leaderboardScoreBuffer.close();
            } catch (Throwable th) {
                leaderboardScoreBuffer.close();
            }
        }

        protected void m2774a(C0244d<LoadPlayerScoreResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public LeaderboardScore getScore() {
            return this.Jh;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    final class RequestsLoadedCallback extends C0391b<C0244d<LoadRequestsResult>> implements LoadRequestsResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final Bundle Js;
        private final Status wJ;

        RequestsLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadRequestsResult> resultHolder, Status status, Bundle requestData) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            this.wJ = status;
            this.Js = requestData;
        }

        protected /* synthetic */ void m2776a(Object obj) {
            m2777c((C0244d) obj);
        }

        protected void m2777c(C0244d<LoadRequestsResult> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
            release();
        }

        public GameRequestBuffer getRequests(int requestType) {
            String bd = RequestType.bd(requestType);
            return !this.Js.containsKey(bd) ? null : new GameRequestBuffer((DataHolder) this.Js.get(bd));
        }

        public Status getStatus() {
            return this.wJ;
        }

        public void release() {
            for (String parcelable : this.Js.keySet()) {
                DataHolder dataHolder = (DataHolder) this.Js.getParcelable(parcelable);
                if (dataHolder != null) {
                    dataHolder.close();
                }
            }
        }
    }

    abstract class ResultDataHolderCallback<R extends C0244d<?>> extends C0889d<R> implements Releasable, Result {
        final DataHolder BB;
        final /* synthetic */ GamesClientImpl IJ;
        final Status wJ;

        public ResultDataHolderCallback(GamesClientImpl gamesClientImpl, R resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.wJ = new Status(dataHolder.getStatusCode());
            this.BB = dataHolder;
        }

        public Status getStatus() {
            return this.wJ;
        }

        public void release() {
            if (this.BB != null) {
                this.BB.close();
            }
        }
    }

    final class TurnBasedMatchCanceledCallback extends C0391b<C0244d<CancelMatchResult>> implements CancelMatchResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final String JA;
        private final Status wJ;

        TurnBasedMatchCanceledCallback(GamesClientImpl gamesClientImpl, C0244d<CancelMatchResult> resultHolder, Status status, String externalMatchId) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            this.wJ = status;
            this.JA = externalMatchId;
        }

        public /* synthetic */ void m2778a(Object obj) {
            m2779c((C0244d) obj);
        }

        public void m2779c(C0244d<CancelMatchResult> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
        }

        public String getMatchId() {
            return this.JA;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    final class TurnBasedMatchesLoadedCallback extends C0391b<C0244d<LoadMatchesResult>> implements LoadMatchesResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final LoadMatchesResponse JG;
        private final Status wJ;

        TurnBasedMatchesLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadMatchesResult> resultHolder, Status status, Bundle matchData) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder);
            this.wJ = status;
            this.JG = new LoadMatchesResponse(matchData);
        }

        protected /* synthetic */ void m2780a(Object obj) {
            m2781c((C0244d) obj);
        }

        protected void m2781c(C0244d<LoadMatchesResult> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
        }

        public LoadMatchesResponse getMatches() {
            return this.JG;
        }

        public Status getStatus() {
            return this.wJ;
        }

        public void release() {
            this.JG.close();
        }
    }

    abstract class AbstractPeerStatusCallback extends AbstractRoomStatusCallback {
        private final ArrayList<String> II;
        final /* synthetic */ GamesClientImpl IJ;

        AbstractPeerStatusCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
            this.II = new ArrayList();
            for (Object add : participantIds) {
                this.II.add(add);
            }
        }

        protected void m3300a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            m3301a(roomStatusUpdateListener, room, this.II);
        }

        protected abstract void m3301a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList);
    }

    final class AchievementUpdatedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<UpdateAchievementResult> wH;

        AchievementUpdatedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<UpdateAchievementResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3302e(int i, String str) {
            this.IJ.m2175a(new AchievementUpdatedCallback(this.IJ, this.wH, i, str));
        }
    }

    final class AchievementsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadAchievementsResult> wH;

        AchievementsLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadAchievementsResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3303b(DataHolder dataHolder) {
            this.IJ.m2175a(new AchievementsLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class AchievementsLoadedCallback extends ResultDataHolderCallback<C0244d<LoadAchievementsResult>> implements LoadAchievementsResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final AchievementBuffer IL;

        AchievementsLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadAchievementsResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.IL = new AchievementBuffer(dataHolder);
        }

        protected void m3304a(C0244d<LoadAchievementsResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public AchievementBuffer getAchievements() {
            return this.IL;
        }
    }

    final class ConnectedToRoomCallback extends AbstractRoomStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        ConnectedToRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void m3306a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onConnectedToRoom(room);
        }
    }

    final class ContactSettingsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<ContactSettingLoadResult> wH;

        ContactSettingsLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<ContactSettingLoadResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3307B(DataHolder dataHolder) {
            this.IJ.m2175a(new ContactSettingsLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class ContactSettingsLoadedCallback extends ResultDataHolderCallback<C0244d<ContactSettingLoadResult>> implements ContactSettingLoadResult {
        final /* synthetic */ GamesClientImpl IJ;

        ContactSettingsLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<ContactSettingLoadResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
        }

        protected void m3308a(C0244d<ContactSettingLoadResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }
    }

    final class ContactSettingsUpdatedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<Status> wH;

        ContactSettingsUpdatedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<Status> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void aV(int i) {
            this.IJ.m2175a(new ContactSettingsUpdatedCallback(this.IJ, this.wH, i));
        }
    }

    final class DisconnectedFromRoomCallback extends AbstractRoomStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        DisconnectedFromRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void m3310a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onDisconnectedFromRoom(room);
        }
    }

    final class ExtendedGamesLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadExtendedGamesResult> wH;

        ExtendedGamesLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadExtendedGamesResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3311h(DataHolder dataHolder) {
            this.IJ.m2175a(new ExtendedGamesLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class ExtendedGamesLoadedCallback extends ResultDataHolderCallback<C0244d<LoadExtendedGamesResult>> implements LoadExtendedGamesResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final ExtendedGameBuffer IM;

        ExtendedGamesLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadExtendedGamesResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.IM = new ExtendedGameBuffer(dataHolder);
        }

        protected void m3312a(C0244d<LoadExtendedGamesResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }
    }

    final class ExtendedPlayersLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadExtendedPlayersResult> wH;

        ExtendedPlayersLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadExtendedPlayersResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3314f(DataHolder dataHolder) {
            this.IJ.m2175a(new ExtendedPlayersLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class ExtendedPlayersLoadedCallback extends ResultDataHolderCallback<C0244d<LoadExtendedPlayersResult>> implements LoadExtendedPlayersResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final ExtendedPlayerBuffer IN;

        ExtendedPlayersLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadExtendedPlayersResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.IN = new ExtendedPlayerBuffer(dataHolder);
        }

        protected void m3315a(C0244d<LoadExtendedPlayersResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }
    }

    final class GameInstancesLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadGameInstancesResult> wH;

        GameInstancesLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadGameInstancesResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3317i(DataHolder dataHolder) {
            this.IJ.m2175a(new GameInstancesLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class GameInstancesLoadedCallback extends ResultDataHolderCallback<C0244d<LoadGameInstancesResult>> implements LoadGameInstancesResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final GameInstanceBuffer IO;

        GameInstancesLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadGameInstancesResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.IO = new GameInstanceBuffer(dataHolder);
        }

        protected void m3318a(C0244d<LoadGameInstancesResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }
    }

    final class GameMuteStatusChangedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<GameMuteStatusChangeResult> wH;

        GameMuteStatusChangedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<GameMuteStatusChangeResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3320a(int i, String str, boolean z) {
            this.IJ.m2175a(new GameMuteStatusChangedCallback(this.IJ, this.wH, i, str, z));
        }
    }

    final class GameMuteStatusLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<GameMuteStatusLoadResult> wH;

        GameMuteStatusLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<GameMuteStatusLoadResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3321z(DataHolder dataHolder) {
            this.IJ.m2175a(new GameMuteStatusLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class GameSearchSuggestionsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadGameSearchSuggestionsResult> wH;

        GameSearchSuggestionsLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadGameSearchSuggestionsResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3322j(DataHolder dataHolder) {
            this.IJ.m2175a(new GameSearchSuggestionsLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class GameSearchSuggestionsLoadedCallback extends ResultDataHolderCallback<C0244d<LoadGameSearchSuggestionsResult>> implements LoadGameSearchSuggestionsResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final DataHolder IR;

        GameSearchSuggestionsLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadGameSearchSuggestionsResult> resultHolder, DataHolder data) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, data);
            this.IR = data;
        }

        protected void m3323a(C0244d<LoadGameSearchSuggestionsResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }
    }

    final class GamesLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadGamesResult> wH;

        GamesLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadGamesResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3325g(DataHolder dataHolder) {
            this.IJ.m2175a(new GamesLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class GamesLoadedCallback extends ResultDataHolderCallback<C0244d<LoadGamesResult>> implements LoadGamesResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final GameBuffer IS;

        GamesLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadGamesResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.IS = new GameBuffer(dataHolder);
        }

        protected void m3326a(C0244d<LoadGamesResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public GameBuffer getGames() {
            return this.IS;
        }
    }

    final class InvitationReceivedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final OnInvitationReceivedListener IT;

        InvitationReceivedBinderCallback(GamesClientImpl gamesClientImpl, OnInvitationReceivedListener listener) {
            this.IJ = gamesClientImpl;
            this.IT = listener;
        }

        public void m3328l(DataHolder dataHolder) {
            InvitationBuffer invitationBuffer = new InvitationBuffer(dataHolder);
            Invitation invitation = null;
            try {
                if (invitationBuffer.getCount() > 0) {
                    invitation = (Invitation) ((Invitation) invitationBuffer.get(0)).freeze();
                }
                invitationBuffer.close();
                if (invitation != null) {
                    this.IJ.m2175a(new InvitationReceivedCallback(this.IJ, this.IT, invitation));
                }
            } catch (Throwable th) {
                invitationBuffer.close();
            }
        }

        public void onInvitationRemoved(String invitationId) {
            this.IJ.m2175a(new InvitationRemovedCallback(this.IJ, this.IT, invitationId));
        }
    }

    final class InvitationsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadInvitationsResult> wH;

        InvitationsLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadInvitationsResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3329k(DataHolder dataHolder) {
            this.IJ.m2175a(new InvitationsLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class InvitationsLoadedCallback extends ResultDataHolderCallback<C0244d<LoadInvitationsResult>> implements LoadInvitationsResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final InvitationBuffer IW;

        InvitationsLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadInvitationsResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.IW = new InvitationBuffer(dataHolder);
        }

        protected void m3330a(C0244d<LoadInvitationsResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public InvitationBuffer getInvitations() {
            return this.IW;
        }
    }

    final class JoinedRoomCallback extends AbstractRoomCallback {
        final /* synthetic */ GamesClientImpl IJ;

        public JoinedRoomCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void m3332a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onJoinedRoom(i, room);
        }
    }

    final class LeaderboardScoresLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadScoresResult> wH;

        LeaderboardScoresLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadScoresResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3333a(DataHolder dataHolder, DataHolder dataHolder2) {
            this.IJ.m2175a(new LeaderboardScoresLoadedCallback(this.IJ, this.wH, dataHolder, dataHolder2));
        }
    }

    final class LeaderboardScoresLoadedCallback extends ResultDataHolderCallback<C0244d<LoadScoresResult>> implements LoadScoresResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final LeaderboardEntity IX;
        private final LeaderboardScoreBuffer IY;

        LeaderboardScoresLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadScoresResult> resultHolder, DataHolder leaderboard, DataHolder scores) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, scores);
            LeaderboardBuffer leaderboardBuffer = new LeaderboardBuffer(leaderboard);
            try {
                if (leaderboardBuffer.getCount() > 0) {
                    this.IX = (LeaderboardEntity) ((Leaderboard) leaderboardBuffer.get(0)).freeze();
                } else {
                    this.IX = null;
                }
                leaderboardBuffer.close();
                this.IY = new LeaderboardScoreBuffer(scores);
            } catch (Throwable th) {
                leaderboardBuffer.close();
            }
        }

        protected void m3334a(C0244d<LoadScoresResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public Leaderboard getLeaderboard() {
            return this.IX;
        }

        public LeaderboardScoreBuffer getScores() {
            return this.IY;
        }
    }

    final class LeaderboardsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LeaderboardMetadataResult> wH;

        LeaderboardsLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LeaderboardMetadataResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3336c(DataHolder dataHolder) {
            this.IJ.m2175a(new LeaderboardsLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class LeaderboardsLoadedCallback extends ResultDataHolderCallback<C0244d<LeaderboardMetadataResult>> implements LeaderboardMetadataResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final LeaderboardBuffer IZ;

        LeaderboardsLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LeaderboardMetadataResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.IZ = new LeaderboardBuffer(dataHolder);
        }

        protected void m3337a(C0244d<LeaderboardMetadataResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public LeaderboardBuffer getLeaderboards() {
            return this.IZ;
        }
    }

    final class MatchUpdateReceivedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final OnTurnBasedMatchUpdateReceivedListener Jc;

        MatchUpdateReceivedBinderCallback(GamesClientImpl gamesClientImpl, OnTurnBasedMatchUpdateReceivedListener listener) {
            this.IJ = gamesClientImpl;
            this.Jc = listener;
        }

        public void onTurnBasedMatchRemoved(String matchId) {
            this.IJ.m2175a(new MatchRemovedCallback(this.IJ, this.Jc, matchId));
        }

        public void m3339r(DataHolder dataHolder) {
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            TurnBasedMatch turnBasedMatch = null;
            try {
                if (turnBasedMatchBuffer.getCount() > 0) {
                    turnBasedMatch = (TurnBasedMatch) ((TurnBasedMatch) turnBasedMatchBuffer.get(0)).freeze();
                }
                turnBasedMatchBuffer.close();
                if (turnBasedMatch != null) {
                    this.IJ.m2175a(new MatchUpdateReceivedCallback(this.IJ, this.Jc, turnBasedMatch));
                }
            } catch (Throwable th) {
                turnBasedMatchBuffer.close();
            }
        }
    }

    final class NotifyAclLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadAclResult> wH;

        NotifyAclLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadAclResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3340A(DataHolder dataHolder) {
            this.IJ.m2175a(new NotifyAclLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class NotifyAclLoadedCallback extends ResultDataHolderCallback<C0244d<LoadAclResult>> implements LoadAclResult {
        final /* synthetic */ GamesClientImpl IJ;

        NotifyAclLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadAclResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
        }

        protected void m3341a(C0244d<LoadAclResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }
    }

    final class NotifyAclUpdatedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<Status> wH;

        NotifyAclUpdatedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<Status> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void aU(int i) {
            this.IJ.m2175a(new NotifyAclUpdatedCallback(this.IJ, this.wH, i));
        }
    }

    final class OwnerCoverPhotoUrisLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadOwnerCoverPhotoUrisResult> wH;

        OwnerCoverPhotoUrisLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadOwnerCoverPhotoUrisResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3343c(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.IJ.m2175a(new OwnerCoverPhotoUrisLoadedCallback(this.IJ, this.wH, i, bundle));
        }
    }

    final class PlayerLeaderboardScoreLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadPlayerScoreResult> wH;

        PlayerLeaderboardScoreLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadPlayerScoreResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3344C(DataHolder dataHolder) {
            this.IJ.m2175a(new PlayerLeaderboardScoreLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class PlayersLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadPlayersResult> wH;

        PlayersLoadedBinderCallback(GamesClientImpl gamesClientImpl, C0244d<LoadPlayersResult> holder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) holder, (Object) "Holder must not be null");
        }

        public void m3345e(DataHolder dataHolder) {
            this.IJ.m2175a(new PlayersLoadedCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class PlayersLoadedCallback extends ResultDataHolderCallback<C0244d<LoadPlayersResult>> implements LoadPlayersResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final PlayerBuffer Ji;

        PlayersLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadPlayersResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.Ji = new PlayerBuffer(dataHolder);
        }

        protected void m3346a(C0244d<LoadPlayersResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public PlayerBuffer getPlayers() {
            return this.Ji;
        }
    }

    final class RealTimeReliableMessageBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        final ReliableMessageSentCallback Jl;

        public RealTimeReliableMessageBinderCallbacks(GamesClientImpl gamesClientImpl, ReliableMessageSentCallback messageSentCallbacks) {
            this.IJ = gamesClientImpl;
            this.Jl = messageSentCallbacks;
        }

        public void m3348b(int i, int i2, String str) {
            this.IJ.m2175a(new RealTimeMessageSentCallback(this.IJ, this.Jl, i, i2, str));
        }
    }

    final class RequestReceivedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final OnRequestReceivedListener Jm;

        RequestReceivedBinderCallback(GamesClientImpl gamesClientImpl, OnRequestReceivedListener listener) {
            this.IJ = gamesClientImpl;
            this.Jm = listener;
        }

        public void m3349m(DataHolder dataHolder) {
            GameRequestBuffer gameRequestBuffer = new GameRequestBuffer(dataHolder);
            GameRequest gameRequest = null;
            try {
                if (gameRequestBuffer.getCount() > 0) {
                    gameRequest = (GameRequest) ((GameRequest) gameRequestBuffer.get(0)).freeze();
                }
                gameRequestBuffer.close();
                if (gameRequest != null) {
                    this.IJ.m2175a(new RequestReceivedCallback(this.IJ, this.Jm, gameRequest));
                }
            } catch (Throwable th) {
                gameRequestBuffer.close();
            }
        }

        public void onRequestRemoved(String requestId) {
            this.IJ.m2175a(new RequestRemovedCallback(this.IJ, this.Jm, requestId));
        }
    }

    final class RequestSentBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<SendRequestResult> Jp;

        public RequestSentBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<SendRequestResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.Jp = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3350E(DataHolder dataHolder) {
            this.IJ.m2175a(new RequestSentCallback(this.IJ, this.Jp, dataHolder));
        }
    }

    final class RequestSentCallback extends ResultDataHolderCallback<C0244d<SendRequestResult>> implements SendRequestResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final GameRequest Jn;

        RequestSentCallback(GamesClientImpl gamesClientImpl, C0244d<SendRequestResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            GameRequestBuffer gameRequestBuffer = new GameRequestBuffer(dataHolder);
            try {
                if (gameRequestBuffer.getCount() > 0) {
                    this.Jn = (GameRequest) ((GameRequest) gameRequestBuffer.get(0)).freeze();
                } else {
                    this.Jn = null;
                }
                gameRequestBuffer.close();
            } catch (Throwable th) {
                gameRequestBuffer.close();
            }
        }

        protected void m3351a(C0244d<SendRequestResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }
    }

    final class RequestSummariesLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadRequestSummariesResult> Jq;

        public RequestSummariesLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<LoadRequestSummariesResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.Jq = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3353F(DataHolder dataHolder) {
            this.IJ.m2175a(new RequestSummariesLoadedCallback(this.IJ, this.Jq, dataHolder));
        }
    }

    final class RequestSummariesLoadedCallback extends ResultDataHolderCallback<C0244d<LoadRequestSummariesResult>> implements LoadRequestSummariesResult {
        final /* synthetic */ GamesClientImpl IJ;

        RequestSummariesLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadRequestSummariesResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
        }

        protected void m3354a(C0244d<LoadRequestSummariesResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }
    }

    final class RequestsLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadRequestsResult> Jr;

        public RequestsLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<LoadRequestsResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.Jr = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3356b(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.IJ.m2175a(new RequestsLoadedCallback(this.IJ, this.Jr, new Status(i), bundle));
        }
    }

    final class RequestsUpdatedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<UpdateRequestsResult> Jt;

        public RequestsUpdatedBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<UpdateRequestsResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.Jt = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3357D(DataHolder dataHolder) {
            this.IJ.m2175a(new RequestsUpdatedCallback(this.IJ, this.Jt, dataHolder));
        }
    }

    final class RequestsUpdatedCallback extends ResultDataHolderCallback<C0244d<UpdateRequestsResult>> implements UpdateRequestsResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final RequestUpdateOutcomes Ju;

        RequestsUpdatedCallback(GamesClientImpl gamesClientImpl, C0244d<UpdateRequestsResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            this.Ju = RequestUpdateOutcomes.m573J(dataHolder);
        }

        protected void m3358a(C0244d<UpdateRequestsResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public Set<String> getRequestIds() {
            return this.Ju.getRequestIds();
        }

        public int getRequestOutcome(String requestId) {
            return this.Ju.getRequestOutcome(requestId);
        }
    }

    final class RoomAutoMatchingCallback extends AbstractRoomStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        RoomAutoMatchingCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void m3360a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomAutoMatching(room);
        }
    }

    final class RoomBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final RoomUpdateListener Jv;
        private final RoomStatusUpdateListener Jw;
        private final RealTimeMessageReceivedListener Jx;

        public RoomBinderCallbacks(GamesClientImpl gamesClientImpl, RoomUpdateListener roomCallbacks) {
            this.IJ = gamesClientImpl;
            this.Jv = (RoomUpdateListener) fq.m982b((Object) roomCallbacks, (Object) "Callbacks must not be null");
            this.Jw = null;
            this.Jx = null;
        }

        public RoomBinderCallbacks(GamesClientImpl gamesClientImpl, RoomUpdateListener roomCallbacks, RoomStatusUpdateListener roomStatusCallbacks, RealTimeMessageReceivedListener realTimeMessageReceivedCallbacks) {
            this.IJ = gamesClientImpl;
            this.Jv = (RoomUpdateListener) fq.m982b((Object) roomCallbacks, (Object) "Callbacks must not be null");
            this.Jw = roomStatusCallbacks;
            this.Jx = realTimeMessageReceivedCallbacks;
        }

        public void m3361a(DataHolder dataHolder, String[] strArr) {
            this.IJ.m2175a(new PeerInvitedToRoomCallback(this.IJ, this.Jw, dataHolder, strArr));
        }

        public void m3362b(DataHolder dataHolder, String[] strArr) {
            this.IJ.m2175a(new PeerJoinedRoomCallback(this.IJ, this.Jw, dataHolder, strArr));
        }

        public void m3363c(DataHolder dataHolder, String[] strArr) {
            this.IJ.m2175a(new PeerLeftRoomCallback(this.IJ, this.Jw, dataHolder, strArr));
        }

        public void m3364d(DataHolder dataHolder, String[] strArr) {
            this.IJ.m2175a(new PeerDeclinedCallback(this.IJ, this.Jw, dataHolder, strArr));
        }

        public void m3365e(DataHolder dataHolder, String[] strArr) {
            this.IJ.m2175a(new PeerConnectedCallback(this.IJ, this.Jw, dataHolder, strArr));
        }

        public void m3366f(DataHolder dataHolder, String[] strArr) {
            this.IJ.m2175a(new PeerDisconnectedCallback(this.IJ, this.Jw, dataHolder, strArr));
        }

        public void onLeftRoom(int statusCode, String externalRoomId) {
            this.IJ.m2175a(new LeftRoomCallback(this.IJ, this.Jv, statusCode, externalRoomId));
        }

        public void onP2PConnected(String participantId) {
            this.IJ.m2175a(new P2PConnectedCallback(this.IJ, this.Jw, participantId));
        }

        public void onP2PDisconnected(String participantId) {
            this.IJ.m2175a(new P2PDisconnectedCallback(this.IJ, this.Jw, participantId));
        }

        public void onRealTimeMessageReceived(RealTimeMessage message) {
            this.IJ.m2175a(new MessageReceivedCallback(this.IJ, this.Jx, message));
        }

        public void m3367s(DataHolder dataHolder) {
            this.IJ.m2175a(new RoomCreatedCallback(this.IJ, this.Jv, dataHolder));
        }

        public void m3368t(DataHolder dataHolder) {
            this.IJ.m2175a(new JoinedRoomCallback(this.IJ, this.Jv, dataHolder));
        }

        public void m3369u(DataHolder dataHolder) {
            this.IJ.m2175a(new RoomConnectingCallback(this.IJ, this.Jw, dataHolder));
        }

        public void m3370v(DataHolder dataHolder) {
            this.IJ.m2175a(new RoomAutoMatchingCallback(this.IJ, this.Jw, dataHolder));
        }

        public void m3371w(DataHolder dataHolder) {
            this.IJ.m2175a(new RoomConnectedCallback(this.IJ, this.Jv, dataHolder));
        }

        public void m3372x(DataHolder dataHolder) {
            this.IJ.m2175a(new ConnectedToRoomCallback(this.IJ, this.Jw, dataHolder));
        }

        public void m3373y(DataHolder dataHolder) {
            this.IJ.m2175a(new DisconnectedFromRoomCallback(this.IJ, this.Jw, dataHolder));
        }
    }

    final class RoomConnectedCallback extends AbstractRoomCallback {
        final /* synthetic */ GamesClientImpl IJ;

        RoomConnectedCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void m3374a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomConnected(i, room);
        }
    }

    final class RoomConnectingCallback extends AbstractRoomStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        RoomConnectingCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void m3375a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomConnecting(room);
        }
    }

    final class RoomCreatedCallback extends AbstractRoomCallback {
        final /* synthetic */ GamesClientImpl IJ;

        public RoomCreatedCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void m3376a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomCreated(i, room);
        }
    }

    final class SignOutCompleteBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<Status> wH;

        public SignOutCompleteBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<Status> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void du() {
            this.IJ.m2175a(new SignOutCompleteCallback(this.IJ, this.wH, new Status(0)));
        }
    }

    final class SubmitScoreBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<SubmitScoreResult> wH;

        public SubmitScoreBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<SubmitScoreResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.wH = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3377d(DataHolder dataHolder) {
            this.IJ.m2175a(new SubmitScoreCallback(this.IJ, this.wH, dataHolder));
        }
    }

    final class SubmitScoreCallback extends ResultDataHolderCallback<C0244d<SubmitScoreResult>> implements SubmitScoreResult {
        final /* synthetic */ GamesClientImpl IJ;
        private final ScoreSubmissionData Jy;

        public SubmitScoreCallback(GamesClientImpl gamesClientImpl, C0244d<SubmitScoreResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
            try {
                this.Jy = new ScoreSubmissionData(dataHolder);
            } finally {
                dataHolder.close();
            }
        }

        public void m3378a(C0244d<SubmitScoreResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public ScoreSubmissionData getScoreData() {
            return this.Jy;
        }
    }

    abstract class TurnBasedMatchCallback<T extends C0244d<?>> extends ResultDataHolderCallback<T> {
        final /* synthetic */ GamesClientImpl IJ;
        final TurnBasedMatch Jd;

        TurnBasedMatchCallback(GamesClientImpl gamesClientImpl, T listener, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            try {
                if (turnBasedMatchBuffer.getCount() > 0) {
                    this.Jd = (TurnBasedMatch) ((TurnBasedMatch) turnBasedMatchBuffer.get(0)).freeze();
                } else {
                    this.Jd = null;
                }
                turnBasedMatchBuffer.close();
            } catch (Throwable th) {
                turnBasedMatchBuffer.close();
            }
        }

        protected void m3380a(T t, DataHolder dataHolder) {
            m3382k(t);
        }

        public TurnBasedMatch getMatch() {
            return this.Jd;
        }

        abstract void m3382k(T t);
    }

    final class TurnBasedMatchCanceledBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<CancelMatchResult> Jz;

        public TurnBasedMatchCanceledBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<CancelMatchResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.Jz = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3383f(int i, String str) {
            this.IJ.m2175a(new TurnBasedMatchCanceledCallback(this.IJ, this.Jz, new Status(i), str));
        }
    }

    final class TurnBasedMatchInitiatedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<InitiateMatchResult> JB;

        public TurnBasedMatchInitiatedBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<InitiateMatchResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.JB = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3384o(DataHolder dataHolder) {
            this.IJ.m2175a(new TurnBasedMatchInitiatedCallback(this.IJ, this.JB, dataHolder));
        }
    }

    final class TurnBasedMatchLeftBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LeaveMatchResult> JC;

        public TurnBasedMatchLeftBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<LeaveMatchResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.JC = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3385q(DataHolder dataHolder) {
            this.IJ.m2175a(new TurnBasedMatchLeftCallback(this.IJ, this.JC, dataHolder));
        }
    }

    final class TurnBasedMatchLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadMatchResult> JD;

        public TurnBasedMatchLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<LoadMatchResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.JD = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3386n(DataHolder dataHolder) {
            this.IJ.m2175a(new TurnBasedMatchLoadedCallback(this.IJ, this.JD, dataHolder));
        }
    }

    final class TurnBasedMatchUpdatedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<UpdateMatchResult> JE;

        public TurnBasedMatchUpdatedBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<UpdateMatchResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.JE = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3387p(DataHolder dataHolder) {
            this.IJ.m2175a(new TurnBasedMatchUpdatedCallback(this.IJ, this.JE, dataHolder));
        }
    }

    final class TurnBasedMatchesLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl IJ;
        private final C0244d<LoadMatchesResult> JF;

        public TurnBasedMatchesLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, C0244d<LoadMatchesResult> resultHolder) {
            this.IJ = gamesClientImpl;
            this.JF = (C0244d) fq.m982b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m3388a(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.IJ.m2175a(new TurnBasedMatchesLoadedCallback(this.IJ, this.JF, new Status(i), bundle));
        }
    }

    final class PeerConnectedCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        PeerConnectedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void m3490a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersConnected(room, arrayList);
        }
    }

    final class PeerDeclinedCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        PeerDeclinedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void m3491a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerDeclined(room, arrayList);
        }
    }

    final class PeerDisconnectedCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        PeerDisconnectedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void m3492a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersDisconnected(room, arrayList);
        }
    }

    final class PeerInvitedToRoomCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        PeerInvitedToRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void m3493a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerInvitedToRoom(room, arrayList);
        }
    }

    final class PeerJoinedRoomCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        PeerJoinedRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void m3494a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerJoined(room, arrayList);
        }
    }

    final class PeerLeftRoomCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl IJ;

        PeerLeftRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void m3495a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerLeft(room, arrayList);
        }
    }

    final class TurnBasedMatchInitiatedCallback extends TurnBasedMatchCallback<C0244d<InitiateMatchResult>> implements InitiateMatchResult {
        final /* synthetic */ GamesClientImpl IJ;

        TurnBasedMatchInitiatedCallback(GamesClientImpl gamesClientImpl, C0244d<InitiateMatchResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
        }

        protected void m3496k(C0244d<InitiateMatchResult> c0244d) {
            c0244d.m132b(this);
        }
    }

    final class TurnBasedMatchLeftCallback extends TurnBasedMatchCallback<C0244d<LeaveMatchResult>> implements LeaveMatchResult {
        final /* synthetic */ GamesClientImpl IJ;

        TurnBasedMatchLeftCallback(GamesClientImpl gamesClientImpl, C0244d<LeaveMatchResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
        }

        protected void m3497k(C0244d<LeaveMatchResult> c0244d) {
            c0244d.m132b(this);
        }
    }

    final class TurnBasedMatchLoadedCallback extends TurnBasedMatchCallback<C0244d<LoadMatchResult>> implements LoadMatchResult {
        final /* synthetic */ GamesClientImpl IJ;

        TurnBasedMatchLoadedCallback(GamesClientImpl gamesClientImpl, C0244d<LoadMatchResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
        }

        protected void m3498k(C0244d<LoadMatchResult> c0244d) {
            c0244d.m132b(this);
        }
    }

    final class TurnBasedMatchUpdatedCallback extends TurnBasedMatchCallback<C0244d<UpdateMatchResult>> implements UpdateMatchResult {
        final /* synthetic */ GamesClientImpl IJ;

        TurnBasedMatchUpdatedCallback(GamesClientImpl gamesClientImpl, C0244d<UpdateMatchResult> resultHolder, DataHolder dataHolder) {
            this.IJ = gamesClientImpl;
            super(gamesClientImpl, resultHolder, dataHolder);
        }

        protected void m3499k(C0244d<UpdateMatchResult> c0244d) {
            c0244d.m132b(this);
        }
    }

    public GamesClientImpl(Context context, Looper looper, String gamePackageName, String accountName, ConnectionCallbacks connectedListener, OnConnectionFailedListener connectionFailedListener, String[] scopes, int gravity, View gamesContentView, boolean isHeadless, boolean showConnectingPopup, int connectingPopupGravity, boolean retryingSignIn, int sdkVariant) {
        super(context, looper, connectedListener, connectionFailedListener, scopes);
        this.Iz = false;
        this.IA = false;
        this.Iu = gamePackageName;
        this.wG = (String) fq.m985f(accountName);
        this.IC = new Binder();
        this.Iv = new HashMap();
        this.Iy = PopupManager.m566a(this, gravity);
        m2857f(gamesContentView);
        this.IA = showConnectingPopup;
        this.IB = connectingPopupGravity;
        this.IE = (long) hashCode();
        this.IF = isHeadless;
        this.IH = retryingSignIn;
        this.IG = sdkVariant;
        registerConnectionCallbacks((ConnectionCallbacks) this);
        registerConnectionFailedListener((OnConnectionFailedListener) this);
    }

    private Room m2782G(DataHolder dataHolder) {
        RoomBuffer roomBuffer = new RoomBuffer(dataHolder);
        Room room = null;
        try {
            if (roomBuffer.getCount() > 0) {
                room = (Room) ((Room) roomBuffer.get(0)).freeze();
            }
            roomBuffer.close();
            return room;
        } catch (Throwable th) {
            roomBuffer.close();
        }
    }

    private RealTimeSocket aC(String str) {
        try {
            ParcelFileDescriptor aJ = ((IGamesService) eM()).aJ(str);
            RealTimeSocket libjingleNativeSocket;
            if (aJ != null) {
                GamesLog.m365f("GamesClientImpl", "Created native libjingle socket.");
                libjingleNativeSocket = new LibjingleNativeSocket(aJ);
                this.Iv.put(str, libjingleNativeSocket);
                return libjingleNativeSocket;
            }
            GamesLog.m365f("GamesClientImpl", "Unable to create native libjingle socket, resorting to old socket.");
            String aE = ((IGamesService) eM()).aE(str);
            if (aE == null) {
                return null;
            }
            LocalSocket localSocket = new LocalSocket();
            try {
                localSocket.connect(new LocalSocketAddress(aE));
                libjingleNativeSocket = new RealTimeSocketImpl(localSocket, str);
                this.Iv.put(str, libjingleNativeSocket);
                return libjingleNativeSocket;
            } catch (IOException e) {
                GamesLog.m367h("GamesClientImpl", "connect() call failed on socket: " + e.getMessage());
                return null;
            }
        } catch (RemoteException e2) {
            GamesLog.m367h("GamesClientImpl", "Unable to create socket. Service died.");
            return null;
        }
    }

    private void gE() {
        for (RealTimeSocket close : this.Iv.values()) {
            try {
                close.close();
            } catch (Throwable e) {
                GamesLog.m364a("GamesClientImpl", "IOException:", e);
            }
        }
        this.Iv.clear();
    }

    private void gk() {
        this.Iw = null;
    }

    protected IGamesService m2784L(IBinder iBinder) {
        return Stub.m1952N(iBinder);
    }

    public int m2785a(ReliableMessageSentCallback reliableMessageSentCallback, byte[] bArr, String str, String str2) {
        try {
            return ((IGamesService) eM()).m415a(new RealTimeReliableMessageBinderCallbacks(this, reliableMessageSentCallback), bArr, str, str2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return -1;
        }
    }

    public int m2786a(byte[] bArr, String str, String[] strArr) {
        fq.m982b((Object) strArr, (Object) "Participant IDs must not be null");
        try {
            return ((IGamesService) eM()).m463b(bArr, str, strArr);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return -1;
        }
    }

    public Intent m2787a(int i, int i2, boolean z) {
        try {
            return ((IGamesService) eM()).m416a(i, i2, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent m2788a(int i, byte[] bArr, int i2, Bitmap bitmap, String str) {
        try {
            Intent a = ((IGamesService) eM()).m417a(i, bArr, i2, str);
            fq.m982b((Object) bitmap, (Object) "Must provide a non null icon");
            a.putExtra("com.google.android.gms.games.REQUEST_ITEM_ICON", bitmap);
            return a;
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent m2789a(Room room, int i) {
        try {
            return ((IGamesService) eM()).m420a((RoomEntity) room.freeze(), i);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    protected void m2790a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 && bundle != null) {
            this.Iz = bundle.getBoolean("show_welcome_popup");
        }
        super.m2174a(i, iBinder, bundle);
    }

    public void m2791a(IBinder iBinder, Bundle bundle) {
        if (isConnected()) {
            try {
                ((IGamesService) eM()).m423a(iBinder, bundle);
            } catch (RemoteException e) {
                GamesLog.m366g("GamesClientImpl", "service died");
            }
        }
    }

    public void m2792a(C0244d<LoadRequestsResult> c0244d, int i, int i2, int i3) {
        try {
            ((IGamesService) eM()).m426a(new RequestsLoadedBinderCallbacks(this, c0244d), i, i2, i3);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2793a(C0244d<LoadExtendedGamesResult> c0244d, int i, int i2, boolean z, boolean z2) {
        try {
            ((IGamesService) eM()).m427a(new ExtendedGamesLoadedBinderCallback(this, c0244d), i, i2, z, z2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2794a(C0244d<LoadPlayersResult> c0244d, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) eM()).m429a(new PlayersLoadedBinderCallback(this, c0244d), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2795a(C0244d<LoadMatchesResult> c0244d, int i, int[] iArr) {
        try {
            ((IGamesService) eM()).m430a(new TurnBasedMatchesLoadedBinderCallbacks(this, c0244d), i, iArr);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2796a(C0244d<LoadScoresResult> c0244d, LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) {
        try {
            ((IGamesService) eM()).m433a(new LeaderboardScoresLoadedBinderCallback(this, c0244d), leaderboardScoreBuffer.hD().hE(), i, i2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2797a(C0244d<InitiateMatchResult> c0244d, TurnBasedMatchConfig turnBasedMatchConfig) {
        try {
            ((IGamesService) eM()).m428a(new TurnBasedMatchInitiatedBinderCallbacks(this, c0244d), turnBasedMatchConfig.getVariant(), turnBasedMatchConfig.getMinPlayers(), turnBasedMatchConfig.getInvitedPlayerIds(), turnBasedMatchConfig.getAutoMatchCriteria());
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2798a(C0244d<LoadPlayersResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m436a(new PlayersLoadedBinderCallback(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2799a(C0244d<UpdateAchievementResult> c0244d, String str, int i) {
        try {
            ((IGamesService) eM()).m439a(c0244d == null ? null : new AchievementUpdatedBinderCallback(this, c0244d), str, i, this.Iy.gU(), this.Iy.gT());
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2800a(C0244d<LoadScoresResult> c0244d, String str, int i, int i2, int i3, boolean z) {
        try {
            ((IGamesService) eM()).m438a(new LeaderboardScoresLoadedBinderCallback(this, c0244d), str, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2801a(C0244d<LoadPlayersResult> c0244d, String str, int i, boolean z) {
        try {
            ((IGamesService) eM()).m440a(new PlayersLoadedBinderCallback(this, c0244d), str, i, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2802a(C0244d<LoadPlayersResult> c0244d, String str, int i, boolean z, boolean z2) {
        if (str.equals("playedWith")) {
            try {
                ((IGamesService) eM()).m498d(new PlayersLoadedBinderCallback(this, c0244d), str, i, z, z2);
                return;
            } catch (RemoteException e) {
                GamesLog.m366g("GamesClientImpl", "service died");
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player collection: " + str);
    }

    public void m2803a(C0244d<LoadExtendedGamesResult> c0244d, String str, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        try {
            ((IGamesService) eM()).m442a(new ExtendedGamesLoadedBinderCallback(this, c0244d), str, i, z, z2, z3, z4);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2804a(C0244d<LoadMatchesResult> c0244d, String str, int i, int[] iArr) {
        try {
            ((IGamesService) eM()).m443a(new TurnBasedMatchesLoadedBinderCallbacks(this, c0244d), str, i, iArr);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2805a(C0244d<SubmitScoreResult> c0244d, String str, long j, String str2) {
        try {
            ((IGamesService) eM()).m445a(c0244d == null ? null : new SubmitScoreBinderCallbacks(this, c0244d), str, j, str2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2806a(C0244d<LeaveMatchResult> c0244d, String str, String str2) {
        try {
            ((IGamesService) eM()).m490c(new TurnBasedMatchLeftBinderCallbacks(this, c0244d), str, str2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2807a(C0244d<LoadPlayerScoreResult> c0244d, String str, String str2, int i, int i2) {
        try {
            ((IGamesService) eM()).m448a(new PlayerLeaderboardScoreLoadedBinderCallback(this, c0244d), str, str2, i, i2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2808a(C0244d<LoadRequestsResult> c0244d, String str, String str2, int i, int i2, int i3) {
        try {
            ((IGamesService) eM()).m449a(new RequestsLoadedBinderCallbacks(this, c0244d), str, str2, i, i2, i3);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2809a(C0244d<LoadScoresResult> c0244d, String str, String str2, int i, int i2, int i3, boolean z) {
        try {
            ((IGamesService) eM()).m450a(new LeaderboardScoresLoadedBinderCallback(this, c0244d), str, str2, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2810a(C0244d<LoadPlayersResult> c0244d, String str, String str2, int i, boolean z, boolean z2) {
        if (str.equals("playedWith") || str.equals("circled")) {
            try {
                ((IGamesService) eM()).m451a(new PlayersLoadedBinderCallback(this, c0244d), str, str2, i, z, z2);
                return;
            } catch (RemoteException e) {
                GamesLog.m366g("GamesClientImpl", "service died");
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player collection: " + str);
    }

    public void m2811a(C0244d<LeaderboardMetadataResult> c0244d, String str, String str2, boolean z) {
        try {
            ((IGamesService) eM()).m478b(new LeaderboardsLoadedBinderCallback(this, c0244d), str, str2, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2812a(C0244d<UpdateRequestsResult> c0244d, String str, String str2, String[] strArr) {
        try {
            ((IGamesService) eM()).m453a(new RequestsUpdatedBinderCallbacks(this, c0244d), str, str2, strArr);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2813a(C0244d<LeaderboardMetadataResult> c0244d, String str, boolean z) {
        try {
            ((IGamesService) eM()).m491c(new LeaderboardsLoadedBinderCallback(this, c0244d), str, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2814a(C0244d<UpdateMatchResult> c0244d, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) {
        try {
            ((IGamesService) eM()).m455a(new TurnBasedMatchUpdatedBinderCallbacks(this, c0244d), str, bArr, str2, participantResultArr);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2815a(C0244d<UpdateMatchResult> c0244d, String str, byte[] bArr, ParticipantResult[] participantResultArr) {
        try {
            ((IGamesService) eM()).m456a(new TurnBasedMatchUpdatedBinderCallbacks(this, c0244d), str, bArr, participantResultArr);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2816a(C0244d<SendRequestResult> c0244d, String str, String[] strArr, int i, byte[] bArr, int i2) {
        try {
            ((IGamesService) eM()).m458a(new RequestSentBinderCallbacks(this, c0244d), str, strArr, i, bArr, i2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2817a(C0244d<LoadPlayersResult> c0244d, boolean z) {
        try {
            ((IGamesService) eM()).m492c(new PlayersLoadedBinderCallback(this, c0244d), z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2818a(C0244d<Status> c0244d, boolean z, Bundle bundle) {
        try {
            ((IGamesService) eM()).m460a(new ContactSettingsUpdatedBinderCallback(this, c0244d), z, bundle);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2819a(C0244d<LoadPlayersResult> c0244d, String[] strArr) {
        try {
            ((IGamesService) eM()).m493c(new PlayersLoadedBinderCallback(this, c0244d), strArr);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2820a(OnInvitationReceivedListener onInvitationReceivedListener) {
        try {
            ((IGamesService) eM()).m431a(new InvitationReceivedBinderCallback(this, onInvitationReceivedListener), this.IE);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2821a(RoomConfig roomConfig) {
        try {
            ((IGamesService) eM()).m434a(new RoomBinderCallbacks(this, roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener()), this.IC, roomConfig.getVariant(), roomConfig.getInvitedPlayerIds(), roomConfig.getAutoMatchCriteria(), roomConfig.isSocketEnabled(), this.IE);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2822a(RoomUpdateListener roomUpdateListener, String str) {
        try {
            ((IGamesService) eM()).m488c(new RoomBinderCallbacks(this, roomUpdateListener), str);
            gE();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2823a(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
        try {
            ((IGamesService) eM()).m468b(new MatchUpdateReceivedBinderCallback(this, onTurnBasedMatchUpdateReceivedListener), this.IE);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2824a(OnRequestReceivedListener onRequestReceivedListener) {
        try {
            ((IGamesService) eM()).m486c(new RequestReceivedBinderCallback(this, onRequestReceivedListener), this.IE);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    protected void m2825a(fm fmVar, C1374e c1374e) throws RemoteException {
        String locale = getContext().getResources().getConfiguration().locale.toString();
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.google.android.gms.games.key.isHeadless", this.IF);
        bundle.putBoolean("com.google.android.gms.games.key.showConnectingPopup", this.IA);
        bundle.putInt("com.google.android.gms.games.key.connectingPopupGravity", this.IB);
        bundle.putBoolean("com.google.android.gms.games.key.retryingSignIn", this.IH);
        bundle.putInt("com.google.android.gms.games.key.sdkVariant", this.IG);
        fmVar.m952a(c1374e, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.wG, eL(), this.Iu, this.Iy.gU(), locale, bundle);
    }

    public Intent aA(String str) {
        try {
            return ((IGamesService) eM()).aA(str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void aB(String str) {
        try {
            ((IGamesService) eM()).aI(str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void aX(int i) {
        this.Iy.setGravity(i);
    }

    public void aY(int i) {
        try {
            ((IGamesService) eM()).aY(i);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public Intent m2826b(int i, int i2, boolean z) {
        try {
            return ((IGamesService) eM()).m464b(i, i2, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void m2827b(C0244d<Status> c0244d) {
        try {
            ((IGamesService) eM()).m424a(new SignOutCompleteBinderCallbacks(this, c0244d));
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2828b(C0244d<LoadPlayersResult> c0244d, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) eM()).m467b(new PlayersLoadedBinderCallback(this, c0244d), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2829b(C0244d<UpdateAchievementResult> c0244d, String str) {
        if (c0244d == null) {
            IGamesCallbacks iGamesCallbacks = null;
        } else {
            Object achievementUpdatedBinderCallback = new AchievementUpdatedBinderCallback(this, c0244d);
        }
        try {
            ((IGamesService) eM()).m446a(iGamesCallbacks, str, this.Iy.gU(), this.Iy.gT());
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2830b(C0244d<UpdateAchievementResult> c0244d, String str, int i) {
        try {
            ((IGamesService) eM()).m472b(c0244d == null ? null : new AchievementUpdatedBinderCallback(this, c0244d), str, i, this.Iy.gU(), this.Iy.gT());
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2831b(C0244d<LoadScoresResult> c0244d, String str, int i, int i2, int i3, boolean z) {
        try {
            ((IGamesService) eM()).m471b(new LeaderboardScoresLoadedBinderCallback(this, c0244d), str, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2832b(C0244d<LoadExtendedGamesResult> c0244d, String str, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) eM()).m441a(new ExtendedGamesLoadedBinderCallback(this, c0244d), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2833b(C0244d<InitiateMatchResult> c0244d, String str, String str2) {
        try {
            ((IGamesService) eM()).m499d(new TurnBasedMatchInitiatedBinderCallbacks(this, c0244d), str, str2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2834b(C0244d<LoadScoresResult> c0244d, String str, String str2, int i, int i2, int i3, boolean z) {
        try {
            ((IGamesService) eM()).m477b(new LeaderboardScoresLoadedBinderCallback(this, c0244d), str, str2, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2835b(C0244d<LoadAchievementsResult> c0244d, String str, String str2, boolean z) {
        try {
            ((IGamesService) eM()).m452a(new AchievementsLoadedBinderCallback(this, c0244d), str, str2, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2836b(C0244d<LeaderboardMetadataResult> c0244d, String str, boolean z) {
        try {
            ((IGamesService) eM()).m500d(new LeaderboardsLoadedBinderCallback(this, c0244d), str, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2837b(C0244d<LeaderboardMetadataResult> c0244d, boolean z) {
        try {
            ((IGamesService) eM()).m480b(new LeaderboardsLoadedBinderCallback(this, c0244d), z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2838b(C0244d<UpdateRequestsResult> c0244d, String[] strArr) {
        try {
            ((IGamesService) eM()).m462a(new RequestsUpdatedBinderCallbacks(this, c0244d), strArr);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2839b(RoomConfig roomConfig) {
        try {
            ((IGamesService) eM()).m435a(new RoomBinderCallbacks(this, roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener()), this.IC, roomConfig.getInvitationId(), roomConfig.isSocketEnabled(), this.IE);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    protected void m2840b(String... strArr) {
        int i = 0;
        boolean z = false;
        for (String str : strArr) {
            if (str.equals(Scopes.GAMES)) {
                z = true;
            } else if (str.equals("https://www.googleapis.com/auth/games.firstparty")) {
                i = 1;
            }
        }
        if (i != 0) {
            fq.m980a(!z, String.format("Cannot have both %s and %s!", new Object[]{Scopes.GAMES, "https://www.googleapis.com/auth/games.firstparty"}));
            return;
        }
        fq.m980a(z, String.format("Games APIs requires %s to function.", new Object[]{Scopes.GAMES}));
    }

    protected String bg() {
        return "com.google.android.gms.games.service.START";
    }

    protected String bh() {
        return "com.google.android.gms.games.internal.IGamesService";
    }

    public void m2841c(C0244d<LoadInvitationsResult> c0244d, int i) {
        try {
            ((IGamesService) eM()).m425a(new InvitationsLoadedBinderCallback(this, c0244d), i);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2842c(C0244d<LoadPlayersResult> c0244d, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) eM()).m485c(new PlayersLoadedBinderCallback(this, c0244d), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2843c(C0244d<UpdateAchievementResult> c0244d, String str) {
        if (c0244d == null) {
            IGamesCallbacks iGamesCallbacks = null;
        } else {
            Object achievementUpdatedBinderCallback = new AchievementUpdatedBinderCallback(this, c0244d);
        }
        try {
            ((IGamesService) eM()).m475b(iGamesCallbacks, str, this.Iy.gU(), this.Iy.gT());
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2844c(C0244d<LoadInvitationsResult> c0244d, String str, int i) {
        try {
            ((IGamesService) eM()).m473b(new InvitationsLoadedBinderCallback(this, c0244d), str, i, false);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2845c(C0244d<LoadExtendedGamesResult> c0244d, String str, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) eM()).m489c(new ExtendedGamesLoadedBinderCallback(this, c0244d), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2846c(C0244d<InitiateMatchResult> c0244d, String str, String str2) {
        try {
            ((IGamesService) eM()).m504e(new TurnBasedMatchInitiatedBinderCallbacks(this, c0244d), str, str2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2847c(C0244d<GameMuteStatusChangeResult> c0244d, String str, boolean z) {
        try {
            ((IGamesService) eM()).m454a(new GameMuteStatusChangedBinderCallback(this, c0244d), str, z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2848c(C0244d<LoadAchievementsResult> c0244d, boolean z) {
        try {
            ((IGamesService) eM()).m459a(new AchievementsLoadedBinderCallback(this, c0244d), z);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2849c(C0244d<UpdateRequestsResult> c0244d, String[] strArr) {
        try {
            ((IGamesService) eM()).m481b(new RequestsUpdatedBinderCallbacks(this, c0244d), strArr);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void connect() {
        gk();
        super.connect();
    }

    public int m2850d(byte[] bArr, String str) {
        try {
            return ((IGamesService) eM()).m463b(bArr, str, null);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return -1;
        }
    }

    public void m2851d(C0244d<LoadPlayersResult> c0244d, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) eM()).m502e(new PlayersLoadedBinderCallback(this, c0244d), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2852d(C0244d<InitiateMatchResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m519l(new TurnBasedMatchInitiatedBinderCallbacks(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2853d(C0244d<LoadRequestSummariesResult> c0244d, String str, int i) {
        try {
            ((IGamesService) eM()).m437a(new RequestSummariesLoadedBinderCallbacks(this, c0244d), str, i);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2854d(C0244d<LoadPlayersResult> c0244d, String str, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) eM()).m474b(new PlayersLoadedBinderCallback(this, c0244d), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public Bundle dG() {
        try {
            Bundle dG = ((IGamesService) eM()).dG();
            if (dG == null) {
                return dG;
            }
            dG.setClassLoader(GamesClientImpl.class.getClassLoader());
            return dG;
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void disconnect() {
        this.Iz = false;
        if (isConnected()) {
            try {
                IGamesService iGamesService = (IGamesService) eM();
                iGamesService.gF();
                iGamesService.m525o(this.IE);
            } catch (RemoteException e) {
                GamesLog.m366g("GamesClientImpl", "Failed to notify client disconnect.");
            }
        }
        gE();
        super.disconnect();
    }

    public void m2855e(C0244d<LoadExtendedPlayersResult> c0244d, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) eM()).m496d(new ExtendedPlayersLoadedBinderCallback(this, c0244d), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2856e(C0244d<InitiateMatchResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m521m(new TurnBasedMatchInitiatedBinderCallbacks(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2857f(View view) {
        this.Iy.m567g(view);
    }

    public void m2858f(C0244d<LeaveMatchResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m526o(new TurnBasedMatchLeftBinderCallbacks(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2859g(C0244d<LoadGamesResult> c0244d) {
        try {
            ((IGamesService) eM()).m495d(new GamesLoadedBinderCallback(this, c0244d));
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2860g(C0244d<CancelMatchResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m523n(new TurnBasedMatchCanceledBinderCallbacks(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public int gA() {
        try {
            return ((IGamesService) eM()).gA();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return 2;
        }
    }

    public Intent gB() {
        try {
            return ((IGamesService) eM()).gB();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public int gC() {
        try {
            return ((IGamesService) eM()).gC();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return 2;
        }
    }

    public int gD() {
        try {
            return ((IGamesService) eM()).gD();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return 2;
        }
    }

    public void gF() {
        if (isConnected()) {
            try {
                ((IGamesService) eM()).gF();
            } catch (RemoteException e) {
                GamesLog.m366g("GamesClientImpl", "service died");
            }
        }
    }

    public String gl() {
        try {
            return ((IGamesService) eM()).gl();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public String gm() {
        try {
            return ((IGamesService) eM()).gm();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Player gn() {
        PlayerBuffer playerBuffer;
        bT();
        synchronized (this) {
            if (this.Iw == null) {
                try {
                    playerBuffer = new PlayerBuffer(((IGamesService) eM()).gG());
                    if (playerBuffer.getCount() > 0) {
                        this.Iw = (PlayerEntity) playerBuffer.get(0).freeze();
                    }
                    playerBuffer.close();
                } catch (RemoteException e) {
                    GamesLog.m366g("GamesClientImpl", "service died");
                } catch (Throwable th) {
                    playerBuffer.close();
                }
            }
        }
        return this.Iw;
    }

    public Game go() {
        GameBuffer gameBuffer;
        bT();
        synchronized (this) {
            if (this.Ix == null) {
                try {
                    gameBuffer = new GameBuffer(((IGamesService) eM()).gI());
                    if (gameBuffer.getCount() > 0) {
                        this.Ix = (GameEntity) gameBuffer.get(0).freeze();
                    }
                    gameBuffer.close();
                } catch (RemoteException e) {
                    GamesLog.m366g("GamesClientImpl", "service died");
                } catch (Throwable th) {
                    gameBuffer.close();
                }
            }
        }
        return this.Ix;
    }

    public Intent gp() {
        try {
            return ((IGamesService) eM()).gp();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gq() {
        try {
            return ((IGamesService) eM()).gq();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gr() {
        try {
            return ((IGamesService) eM()).gr();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gs() {
        try {
            return ((IGamesService) eM()).gs();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void gt() {
        try {
            ((IGamesService) eM()).m528p(this.IE);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void gu() {
        try {
            ((IGamesService) eM()).m530q(this.IE);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void gv() {
        try {
            ((IGamesService) eM()).m532r(this.IE);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public Intent gw() {
        try {
            return ((IGamesService) eM()).gw();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent gx() {
        try {
            return ((IGamesService) eM()).gx();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public int gy() {
        try {
            return ((IGamesService) eM()).gy();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return 4368;
        }
    }

    public String gz() {
        try {
            return ((IGamesService) eM()).gz();
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
            return null;
        }
    }

    public void m2861h(C0244d<LoadOwnerCoverPhotoUrisResult> c0244d) {
        try {
            ((IGamesService) eM()).m514j(new OwnerCoverPhotoUrisLoadedBinderCallback(this, c0244d));
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2862h(C0244d<LoadMatchResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m529p(new TurnBasedMatchLoadedBinderCallbacks(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public RealTimeSocket m2863i(String str, String str2) {
        if (str2 == null || !ParticipantUtils.aV(str2)) {
            throw new IllegalArgumentException("Bad participant ID");
        }
        RealTimeSocket realTimeSocket = (RealTimeSocket) this.Iv.get(str2);
        return (realTimeSocket == null || realTimeSocket.isClosed()) ? aC(str2) : realTimeSocket;
    }

    public void m2864i(C0244d<LoadAclResult> c0244d) {
        try {
            ((IGamesService) eM()).m511h(new NotifyAclLoadedBinderCallback(this, c0244d));
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2865i(C0244d<LoadExtendedGamesResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m503e(new ExtendedGamesLoadedBinderCallback(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2866j(C0244d<ContactSettingLoadResult> c0244d) {
        try {
            ((IGamesService) eM()).m512i(new ContactSettingsLoadedBinderCallback(this, c0244d));
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2867j(C0244d<LoadGameInstancesResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m507f(new GameInstancesLoadedBinderCallback(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2868k(C0244d<LoadGameSearchSuggestionsResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m531q(new GameSearchSuggestionsLoadedBinderCallback(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2869l(C0244d<LoadInvitationsResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m517k(new InvitationsLoadedBinderCallback(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2870l(String str, int i) {
        try {
            ((IGamesService) eM()).m520l(str, i);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2871m(C0244d<Status> c0244d, String str) {
        try {
            ((IGamesService) eM()).m515j(new NotifyAclUpdatedBinderCallback(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2872m(String str, int i) {
        try {
            ((IGamesService) eM()).m522m(str, i);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void m2873n(C0244d<GameMuteStatusLoadResult> c0244d, String str) {
        try {
            ((IGamesService) eM()).m513i(new GameMuteStatusLoadedBinderCallback(this, c0244d), str);
        } catch (RemoteException e) {
            GamesLog.m366g("GamesClientImpl", "service died");
        }
    }

    public void onConnected(Bundle connectionHint) {
        if (this.Iz) {
            this.Iy.gS();
            this.Iz = false;
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        this.Iz = false;
    }

    public void onConnectionSuspended(int cause) {
    }

    protected /* synthetic */ IInterface m2874r(IBinder iBinder) {
        return m2784L(iBinder);
    }
}
