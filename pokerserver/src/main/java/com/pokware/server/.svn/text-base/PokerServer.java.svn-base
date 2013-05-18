package com.pokware.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.google.inject.Inject;
import com.pokware.server.gamepool.GamePool;
import com.pokware.server.netty.PokerServerPipelineFactory;

public class PokerServer {

	private GamePool gamePool;

	@Inject
	public PokerServer(GamePool gamePool, PokerServerPipelineFactory pokerServerPipelineFactory) {
		// Configure the server.
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool(), 10));

		// Set up the event pipeline factory.
		bootstrap.setPipelineFactory(pokerServerPipelineFactory);

		// Bind and start to accept incoming connections.
		try {
			bootstrap.bind(new InetSocketAddress(55555));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}

		this.gamePool = gamePool;

	}

	public GamePool getGamePool() {
		return gamePool;
	}

}
