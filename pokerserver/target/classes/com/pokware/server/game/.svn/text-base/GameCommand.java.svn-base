package com.pokware.server.game;

import com.pokware.ramjet.Command;

public abstract class GameCommand extends Command<Game> {

	private final long gameId;

	public GameCommand(long gameId) {
		this.gameId = gameId;
	}

	@Override
	public long getTargetId() {
		return this.gameId;
	}

}
