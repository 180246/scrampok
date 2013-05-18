package com.pokware.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pokware.ramjet.RamJetEngine;
import com.pokware.ramjet.TargetLocator;
import com.pokware.server.game.Game;

@Singleton
public class GameRamJetEngine extends RamJetEngine<Game> {

	@Inject
	public GameRamJetEngine(TargetLocator<Game> targetLocator) {
		super(targetLocator);
	}

}
