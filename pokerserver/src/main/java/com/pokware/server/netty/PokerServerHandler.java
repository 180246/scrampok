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
package com.pokware.server.netty;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.pokware.client.errors.Errors;
import com.pokware.protobuf.ProtobufTools;
import com.pokware.protobuf.RPCMessages.LoginRequest;
import com.pokware.protobuf.RPCMessages.Request;
import com.pokware.protobuf.RPCMessages.Request.Type;
import com.pokware.protobuf.RPCMessages.Response;
import com.pokware.server.GameRamJetEngine;
import com.pokware.server.ResponseFactory;
import com.pokware.server.game.Player;
import com.pokware.server.game.PlayerMessageListener;
import com.pokware.server.game.commands.fromplayer.JoinTableCommand;
import com.pokware.server.game.commands.fromplayer.SitCommand;
import com.pokware.server.gamepool.GamePool;
import com.pokware.server.playerpool.PlayerPool;

public class PokerServerHandler extends SimpleChannelHandler {

	private static Logger logger = LoggerFactory.getLogger(PokerServerHandler.class);
	public static final ChannelGroup channels = new DefaultChannelGroup();
	private GameRamJetEngine ramJetEngine;
	private GamePool gamePool;
	private PlayerPool playerPool;

	private Player player;

	@Inject
	public PokerServerHandler(GamePool gamePool, GameRamJetEngine ramJetEngine, PlayerPool playerPool) {
		this.gamePool = gamePool;
		this.ramJetEngine = ramJetEngine;
		this.playerPool = playerPool;
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelConnected(ctx, e);
		logger.debug("Channel connected");
		channels.add(e.getChannel());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Request request = (Request) e.getMessage();

		logger.debug("Request {} #{}", request.getType(), request.getId());

		// The response object
		Response response = null;
		Type type = request.getType();

		switch (type) {
		case ListGamesRequest:
			response = listGamesRequest(request.getId());
			break;
		case LoginRequest:
			response = loginRequest(e.getChannel(), request.getId(), request.getLoginRequest());
			break;
		}
		if (response != null) {
			logger.debug("Response #{} status {}", response.getId(), response.getStatus());
			e.getChannel().write(response);
			return;
		} else {
			if (player == null) {
				response = ResponseFactory.getErrorResponse(request.getId(), Errors.Key.NotAuthenticated);
			} else {
				switch (type) {
				case JoinTableRequest:
					ramJetEngine.submitCommand(new JoinTableCommand(request.getId(), player.getPlayerId(), request.getJoinTableRequest()));					
					break;
				case SitRequest:
					ramJetEngine.submitCommand(new SitCommand(request.getId(), player.getPlayerId(), request.getSitRequest()));
					break;
				default:
					break;
				}
				response = ResponseFactory.getDefaultResponse(request.getId());
			}

			logger.info("Writing response: " + ProtobufTools.convertToString(response));
			e.getChannel().write(response);
		}

	}

	private Response loginRequest(final Channel channel, int requestId, LoginRequest loginRequest) {
		Long playerId = playerPool.authenticate(loginRequest.getLogin(), loginRequest.getPassword());
		if (playerId == null) {
			return ResponseFactory.getErrorResponse(requestId, Errors.Key.LoginFailed);
		}

		// check if the player is already logged
		Player player = playerPool.getPlayer(playerId);

		// if not, create a new player
		if (player == null) {
			PlayerMessageListener playerMessageListener = new PlayerMessageListener() {
				@Override
				public void onResponse(Response response) {
					channel.write(response);
				}
			};

			player = new Player(playerId, loginRequest.getLogin());
			player.setListener(playerMessageListener);

			this.player = player;
			playerPool.addPlayer(player);
		}

		return ResponseFactory.getLoginResponse(requestId, true, "token");
	}

	private Response listGamesRequest(int requestId) {
		return ResponseFactory.getListGameResponse(requestId, gamePool.list());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}

}
