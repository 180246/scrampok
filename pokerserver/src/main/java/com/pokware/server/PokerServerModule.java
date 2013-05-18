package com.pokware.server;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.pokware.ramjet.TargetLocator;
import com.pokware.server.game.Game;
import com.pokware.server.gamepool.GamePool;
import com.pokware.server.playerpool.PlayerLocator;
import com.pokware.server.playerpool.PlayerPool;

public class PokerServerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Key.get(new TypeLiteral<TargetLocator<Game>>(){})).to(GamePool.class);
		bind(PlayerLocator.class).to(PlayerPool.class);		
	}

}
