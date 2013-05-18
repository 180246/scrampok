package com.pokware.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pokware.server.jobs.GamesSpawner;

public class Startup {

	public static Logger logger = LoggerFactory.getLogger(Startup.class);

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		logger.info("Starting pokerserver...");
		Injector injector = Guice.createInjector(new PokerServerModule());
		GameRamJetEngine ramJetEngine = injector.getInstance(GameRamJetEngine.class);
		GamesSpawner gameSpawner = injector.getInstance(GamesSpawner.class);
		PokerServer pokerServer = injector.getInstance(PokerServer.class);
	}
}
