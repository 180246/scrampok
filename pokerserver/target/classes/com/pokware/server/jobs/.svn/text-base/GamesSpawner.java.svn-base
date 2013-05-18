package com.pokware.server.jobs;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.pokware.server.game.Game;
import com.pokware.server.gamepool.GamePool;
import com.pokware.server.gamepool.MaximumNumberOfGamesReached;

@Singleton
public class GamesSpawner extends TimerTask {

	private final GamePool gamePool;
	private final Timer timer = new Timer(true);
	private final Provider<Game> gameProvider;

	@Inject
	public GamesSpawner(GamePool gamePool, Provider<Game> gameProvider) {
		this.gamePool = gamePool;
		this.timer.schedule(this, 0, 60000);
		this.gameProvider = gameProvider;
	}

	@Override
	public void run() {
		Collection<Game> list = gamePool.list();
		int emptyGames = 0;
		for (Game game : list) {
			short numberOfPlayers = game.getTable().getNumberOfPlayers();
			if (numberOfPlayers == 0) {
				emptyGames++;
			}
		}
		if (emptyGames < GamePool.MIN_EMPTY_GAMES) {
			int gamesToCreate = GamePool.MIN_EMPTY_GAMES - emptyGames;
			for (int i = 0; i < gamesToCreate; i++) {
				try {
					Game game = gameProvider.get();
					gamePool.add(game);
				} catch (MaximumNumberOfGamesReached e) {
					break;
				}
			}
		}
	}

}
