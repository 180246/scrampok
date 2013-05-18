package com.pokware.client.netty;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.pokware.client.RequestFactory;
import com.pokware.client.ServerEventListener;
import com.pokware.protobuf.Data.Game;
import com.pokware.protobuf.Events.BetEvent;
import com.pokware.protobuf.Events.DealCardsEvent;
import com.pokware.protobuf.Events.FlopEvent;
import com.pokware.protobuf.Events.HandStartEvent;
import com.pokware.protobuf.Events.SeatStatusEvent;
import com.pokware.protobuf.Events.TableStatusEvent;
import com.pokware.protobuf.Events.TimeOutEvent;
import com.pokware.protobuf.Events.TurnEvent;
import com.pokware.protobuf.Events.WinEvent;
import com.pokware.protobuf.RPCMessages.Response;

public class PokerClient implements ServerEventListener {

	private ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
	private Channel channel;
	private PokerClientHandler handler;
	private String username;
	private Map<Long, ServerEventListener> serverEventListeners = new ConcurrentHashMap<Long, ServerEventListener>();

	public PokerClient(String username, String password) {
		bootstrap.setPipelineFactory(new PokerClientPipelineFactory(this));
		connect("localhost", 55555);
		try {
			login(username, password);
			this.username = username;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getUsername() {
		return username;
	}

	public void connect(String host, int port) {
		ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress("localhost", 55555));
		this.channel = connectFuture.awaitUninterruptibly().getChannel();
		this.handler = channel.getPipeline().get(PokerClientHandler.class);
	}

	public void close() {
		channel.close().awaitUninterruptibly();
		bootstrap.releaseExternalResources();
		channel = null;
	}

	public boolean isConnected() {
		return channel != null;
	}

	public void addServerEventListener(Long gameId, ServerEventListener listener) {
		serverEventListeners.put(gameId, listener);
	}

	public void login(String login, String password) throws RequestFailedException {
		handler.sendRequest(RequestFactory.newLoginRequest(login, password));
	}

	public List<Game> listGames() throws RequestFailedException {
		Response response = handler.sendRequest(RequestFactory.newListGamesRequest());
		return response.getListGamesResponse().getGameList();
	}

	public Response joinTable(long gameId) throws RequestFailedException {
		return handler.sendRequest(RequestFactory.newJoinTableRequest(gameId));
	}

	public Response sit(long gameId, int sitIndex) throws RequestFailedException {
		return handler.sendRequest(RequestFactory.newSitRequest(gameId, sitIndex));
	}

	@Override
	public void onBetEvent(BetEvent betEvent) {
		long gameId = betEvent.getGameId();
		checkGameId(gameId);
		serverEventListeners.get(gameId).onBetEvent(betEvent);
	}

	private void checkGameId(long gameId) {
		if (!serverEventListeners.containsKey(gameId)) {
			throw new RuntimeException("PokerClient [" + username + "] is not registered has a listener of game #" + gameId + ". Current games listened = "
					+ serverEventListeners.keySet());
		}
	}

	@Override
	public void onDealCardEvent(DealCardsEvent dealCardsEvent) {
		long gameId = dealCardsEvent.getGameId();
		checkGameId(gameId);
		serverEventListeners.get(gameId).onDealCardEvent(dealCardsEvent);
	}

	@Override
	public void onFlopEvent(FlopEvent flopEvent) {
		long gameId = flopEvent.getGameId();
		checkGameId(gameId);
		serverEventListeners.get(gameId).onFlopEvent(flopEvent);
	}

	@Override
	public void onHandStartEvent(HandStartEvent handStartEvent) {
		long gameId = handStartEvent.getGameId();
		checkGameId(gameId);
		serverEventListeners.get(gameId).onHandStartEvent(handStartEvent);
	}

	@Override
	public void onTableStatusEvent(TableStatusEvent event) {
		long gameId = event.getGameId();
		checkGameId(gameId);
		serverEventListeners.get(gameId).onTableStatusEvent(event);
	}

	@Override
	public void onSeatStatusEvent(SeatStatusEvent event) {
		long gameId = event.getGameId();
		checkGameId(gameId);
		serverEventListeners.get(gameId).onSeatStatusEvent(event);
	}

	@Override
	public void onTimeOutEvent(TimeOutEvent event) {
		long gameId = event.getGameId();
		checkGameId(gameId);
		serverEventListeners.get(gameId).onTimeOutEvent(event);
	}

	@Override
	public void onWinEvent(WinEvent event) {
		long gameId = event.getGameId();
		checkGameId(gameId);
		serverEventListeners.get(gameId).onWinEvent(event);
	}

	@Override
	public void onTurnEvent(TurnEvent event) {
		long gameId = event.getGameId();
		checkGameId(gameId);
		serverEventListeners.get(gameId).onTurnEvent(event);
	}

	@Override
	public String toString() {
		return username;
	}

}
