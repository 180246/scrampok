package com.pokware.server.game.commands.fromgame;

import com.pokware.common.Card;
import com.pokware.common.Deck;
import com.pokware.protobuf.EventResponseFactory;
import com.pokware.protobuf.RPCMessages.Response;
import com.pokware.ramjet.CommandScheduler;
import com.pokware.server.game.Game;
import com.pokware.server.game.GameCommand;
import com.pokware.server.game.Player;
import com.pokware.server.game.Table;
import com.pokware.server.game.TurnManager;
import com.pokware.server.states.GameState;

public class StartHandCommand extends GameCommand {

	private final Deck deck;

	public StartHandCommand(long gameId, Deck deck) {
		super(gameId);
		this.deck = deck;
	}

	@Override
	public int checkPermission(Game game) {
		return 0;
	}

	@Override
	public void executeOn(Game game, CommandScheduler<Game> commandScheduler) {
		game.setCurrentState(GameState.HAND_START);

		Table table = game.getTable();
		short numberOfPlayers = table.getNumberOfPlayers();

		game.setDeck(deck);

		// Look up for all waiting player and put them in active mode
		table.removeAllWaitingFlags();

		// Init buttons, current player and last player
		TurnManager turnManager = game.getTurnManager();
		turnManager.init(commandScheduler);

		// Deal the cards and send the event
		for (int i = 0; i < numberOfPlayers; i++) {
			Player player = table.getPlayer(i);

			assert player != null;

			Card firstHole = game.getDeck().deal();
			Card secondHole = game.getDeck().deal();

			table.getSeat(player).setFirstCard(firstHole);
			table.getSeat(player).setSecondCard(secondHole);

			Response handStartEventResponse = EventResponseFactory.getHandStartEventResponse(game.getGameId(), firstHole.getIndex(), secondHole.getIndex());
			player.sendMessage(handStartEventResponse);
		}

	}

}
