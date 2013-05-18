package com.pokware.server.game;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.google.inject.Inject;
import com.pokware.common.Deck;
import com.pokware.protobuf.RPCMessages.Response;
import com.pokware.ramjet.CommandTarget;
import com.pokware.server.playerpool.PlayerLocator;
import com.pokware.server.states.GameState;

public class Game implements CommandTarget {

	private final static AtomicLong longGenerator = new AtomicLong(1);

	private final long gameId;

	private final String gameName;

	private final GameSettings gameSettings = new GameSettings();

	private GameState currentState = GameState.INIT;

	private transient Deck deck = null;

	private final Table table = new Table();

	private final TurnManager turnManager;

	private final Pot pot = new Pot();

	private final transient HashSet<Player> observerSet = new HashSet<Player>();

	private PlayerLocator playerLocator;

	@Inject
	public Game(GameNameGenerator gameNameGenerator, PlayerLocator playerLocator) {
		this.gameName = gameNameGenerator.newName();
		this.gameId = longGenerator.getAndIncrement();
		this.playerLocator = playerLocator;
		this.turnManager = new TurnManager(this, table);	
	}

	@Override
	public long getTargetId() {
		return getTargetId();
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}

	public Player getCurrentPlayer() {
		Seat currentPlayerSeat = turnManager.getCurrentPlayerSeat();
		return currentPlayerSeat.getPlayer();
	}

	public long getGameId() {
		return gameId;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public GameSettings getGameSettings() {
		return gameSettings;
	}

	public boolean sitPlayer(Player player, int sitIndex) {
		if (player == null) {
			throw new RuntimeException("Player cannot be null");
		}

		boolean sitPlayerSuccessFlag = table.sitPlayer(player, sitIndex);
		
		if (!sitPlayerSuccessFlag) {
			return false;
		}
		
		if (currentState != GameState.INIT) {
			table.get(sitIndex).setWaitingFlag(true);
		}
		
		return true;
	}

	public void broadcastMessage(Response response) {
		if (response == null) {
			throw new RuntimeException("Response cannot be null");
		}

		for (Player player : observerSet) {
			player.sendMessage(response);
		}
	}


	public GameSettings getGame() {
		return gameSettings;
	}

	public Pot getPot() {
		return pot;
	}

	public void addObserver(Player player) {		
		observerSet.add(player);				
	}

	public Set<Player> getObservers() {
		return observerSet;
	}

	public void bet(Player player, int amount) {
		Seat seat = table.getSeat(player);

		seat.decrementStack(amount);
		pot.incrementAmount(amount);
	}

	public void nextStep() {
		if (currentState != GameState.WAIT_FOR_PLAYER_BET) {
			throw new RuntimeException("Game is not in init or end-of-hand");
		}
	}

	public Table getTable() {
		return table;
	}

	public TurnManager getTurnManager() {
		return turnManager;
	}

	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", gameName=" + gameName + "]";
	}

	public com.pokware.protobuf.Data.Game toProtocolBuffer() {
		short numberOfPlayers = table.getNumberOfPlayers();
		com.pokware.protobuf.Data.Game.Builder protocolBufferBuilder = com.pokware.protobuf.Data.Game.newBuilder();
		protocolBufferBuilder.clear();
		protocolBufferBuilder.setGameId(gameId);
		protocolBufferBuilder.setGameName(gameName);
		protocolBufferBuilder.setBb(gameSettings.getBigBlindAmount());
		protocolBufferBuilder.setSb(gameSettings.getSmallBlindAmount());
		protocolBufferBuilder.setPlayers(table.getNumberOfPlayers());
		protocolBufferBuilder.setPlayers(numberOfPlayers);
		return protocolBufferBuilder.build();
	}

	public String getGameName() {
		return gameName;
	}

	public PlayerLocator getPlayerLocator() {
		return this.playerLocator;
	}

}
