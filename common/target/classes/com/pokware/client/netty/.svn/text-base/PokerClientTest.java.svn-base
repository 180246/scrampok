/*
 * Copyright 2009 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.pokware.client.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.pokware.client.RequestFactory;
import com.pokware.client.ServerEventAdapter;
import com.pokware.protobuf.RPCMessages.Request;
import com.pokware.protobuf.RPCMessages.Response;

public class PokerClientTest {
	
	public static int responses = 0;
	
	public static void run() throws Exception {

		ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new PokerClientPipelineFactory(new ServerEventAdapter()));
		
		// Make a new connection.
		ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress("localhost", 55555));
		Channel channel = connectFuture.awaitUninterruptibly().getChannel();
		final PokerClientHandler handler = channel.getPipeline().get(PokerClientHandler.class);
		
		ChannelFuture connectFuture2 = bootstrap.connect(new InetSocketAddress("localhost", 55555));
		Channel channel2 = connectFuture2.awaitUninterruptibly().getChannel();
		final PokerClientHandler handler2 = channel2.getPipeline().get(PokerClientHandler.class);
		
		// Request and get the response.
		
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 20; i++) {
			newFixedThreadPool.submit(new Runnable() {			
				@Override
				public void run() {
					System.out.println("Sending request...");
					Request newListGamesRequest = RequestFactory.newListGamesRequest();
					try {
						Response response = handler.sendRequest(newListGamesRequest);												
						Response response2 = handler2.sendRequest(newListGamesRequest);							
						int status = response.getStatus();
						int status2 = response2.getStatus();
						System.out.println("response " + status + " " + status2);
						responses++;
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});	
		}	
		
		while(responses < 20) {
			Thread.sleep(1000);
		}
		
		newFixedThreadPool.shutdownNow();
				
		// Close the connection.
		channel.close().awaitUninterruptibly();

		// Shut down all thread pools to exit.
		bootstrap.releaseExternalResources();

	}
	
	public static void main(String[] args) throws Exception {
		run();
	}

}
