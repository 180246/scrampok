package com.pokware.server.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pokware.protobuf.Protobufable;
import com.pokware.protobuf.RPCMessages.Response;

public class Player implements Protobufable<com.pokware.protobuf.Data.Player> {

	private static Logger logger = LoggerFactory.getLogger(Player.class);
	private final long playerId;
	private final String userName;
	private transient PlayerMessageListener listener;
	private boolean connected = true;

	public Player(long playerId, String userName) {
		this.playerId = playerId;
		this.userName = userName;
	}

	public void setListener(PlayerMessageListener listener) {
		this.listener = listener;
	}

	public String getUserName() {
		return userName;
	}

	public synchronized void sendMessage(Response response) {
		if (listener != null) {
			try {
				listener.onResponse(response);
			} catch (Exception e) {
				logger.error("Player {} (id {}) : Error during sendMessage: {}", new Object[] { userName, playerId, e.toString() });
				connected = false;
			}
		}
	}

	public boolean isObserving(Game game) {
		return game.getObservers().contains(this);
	}

	public Player getNext(Table table) {
		Seat seat = table.getSeat(this);
		if (seat != null) {
			Seat nextOccupiedSeat = table.nextOccupiedActiveSeat(seat);
			return nextOccupiedSeat.getPlayer();
		} else {
			return null;
		}
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null && obj instanceof Player && ((Player) obj).getUserName().equals(userName));
	}

	public PlayerMessageListener getListener() {
		return listener;
	}

	public long getPlayerId() {
		return playerId;
	}

	@Override
	public String toString() {
		return "Player [ID=" + playerId + ":" + userName + "]";
	}

	@Override
	public com.pokware.protobuf.Data.Player toProtocolBuffer() {
		return com.pokware.protobuf.Data.Player.newBuilder().setPlayerId(playerId).setUserName(this.userName).build();
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

}
