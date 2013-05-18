package com.pokware.server.gamepool;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;
import com.pokware.ramjet.TargetLocator;
import com.pokware.server.game.Game;

@Singleton
public class GamePool implements TargetLocator<Game> {

	public static final int MIN_EMPTY_GAMES = 10;
	//public static final int MAX_EMPTY_GAMES = 100;
	//public static final int MAX_GAMES = 100;

	private final Map<Long, Game> activeGamePool = new ConcurrentHashMap<Long, Game>();

	public GamePool() {
	}

	public void add(Game game) throws MaximumNumberOfGamesReached {
		activeGamePool.put(game.getGameId(), game);
	}

	/**
	 * Do not modify the collection
	 * @return
	 */
	public Collection<Game> list() {		
		return activeGamePool.values();
	}

	@Override
	public Game locate(long targetId) {
		return activeGamePool.get(targetId);
	}

}
