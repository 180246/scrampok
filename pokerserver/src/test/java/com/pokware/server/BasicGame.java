package com.pokware.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.Socket;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pokware.client.ServerEventAdapter;
import com.pokware.client.netty.PokerClient;
import com.pokware.client.netty.RequestFailedException;
import com.pokware.protobuf.Data.Game;
import com.pokware.server.jobs.GamesSpawner;

public class BasicGame {

	private static Thread server;
	private static GameRamJetEngine gameRamJetEngine;
	private static GamesSpawner gameSpawner;
	private static PokerServer pokerServer;

	@BeforeClass
	public static void startServer() throws InterruptedException {
		server = new Thread() {
			@Override
			public void run() {
				Injector injector = Guice.createInjector(new PokerServerModule());
				gameRamJetEngine = injector.getInstance(GameRamJetEngine.class);
				gameSpawner = injector.getInstance(GamesSpawner.class);
				pokerServer = injector.getInstance(PokerServer.class);
			};
		};
		server.setDaemon(true);
		server.start();

		boolean open = false;
		while (!open) {
			Thread.sleep(100);
			try {
				Socket socket = new Socket("localhost", 55555);
				open = true;
				socket.close();
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void testTwoPlayers() throws RequestFailedException {
		PokerClient pokerClient1 = new PokerClient("fabien", "fabien");
		PokerClient pokerClient2 = new PokerClient("fabien2", "fabien2");
		PokerClient pokerClient3 = new PokerClient("fabien3", "fabien3");

		List<Game> listGames = pokerClient1.listGames();
		assertTrue(!listGames.isEmpty());

		// choose a game
		Game game = listGames.get(0);
		long gameId = game.getGameId();

		pokerClient1.addServerEventListener(gameId, new ServerEventAdapter());
		pokerClient2.addServerEventListener(gameId, new ServerEventAdapter());
		pokerClient3.addServerEventListener(gameId, new ServerEventAdapter());

		pokerClient1.joinTable(gameId); // makes them observers
		pokerClient2.joinTable(gameId);
		pokerClient3.joinTable(gameId);

		// sit 2 players
		pokerClient1.sit(gameId, 0);
		pokerClient2.sit(gameId, 1);

		// check number of players
		com.pokware.server.game.Game gameInstance = pokerServer.getGamePool().locate(gameId);
		assertEquals(2, gameInstance.getTable().getNumberOfPlayers());

		// check number of observers
		assertEquals(3, gameInstance.getObservers().size());

	}
}
