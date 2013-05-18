package com.pokware.server.game.commands.fromgame;

import com.pokware.common.Deck;
import com.pokware.ramjet.CommandScheduler;
import com.pokware.server.game.Game;
import com.pokware.server.game.GameCommand;

public class SetDeckCommand extends GameCommand {

	private final Deck deck;

	public SetDeckCommand(long gameId, Deck deck) {
		super(gameId);
		this.deck = deck;
	}

	@Override
	public int checkPermission(Game game) {
		return 0;
	}

	@Override
	public void executeOn(Game game, CommandScheduler<Game> commandScheduler) {
		game.setDeck(deck);
	}

}
