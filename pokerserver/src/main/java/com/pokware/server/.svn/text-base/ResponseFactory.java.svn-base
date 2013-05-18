package com.pokware.server;

import java.util.Collection;

import com.pokware.client.errors.Errors;
import com.pokware.protobuf.RPCMessages.ListGamesResponse;
import com.pokware.protobuf.RPCMessages.ListGamesResponse.Builder;
import com.pokware.protobuf.RPCMessages.LoginResponse;
import com.pokware.protobuf.RPCMessages.Response;
import com.pokware.server.game.Game;

public class ResponseFactory {

	public static Response getErrorResponse(int id, Errors.Key errorKey, Object... parameters) {
		Response.Builder responseBuilder = Response.newBuilder();
		responseBuilder.setId(id);
		responseBuilder.setStatus(errorKey.getIntegerCode());

		for (Object parameter : parameters) {
			responseBuilder.addErrorMessageParameter(parameter.toString());
		}

		return responseBuilder.build();
	}

	public static Response getListGameResponse(int id, Collection<Game> list) {
		Response.Builder responseBuilder = Response.newBuilder();
		Builder listGamesResponseBuilder = ListGamesResponse.newBuilder();

		for (Game game : list) {
			listGamesResponseBuilder.addGame(game.toProtocolBuffer());
		}

		ListGamesResponse listGamesResponse = listGamesResponseBuilder.build();

		responseBuilder.setListGamesResponse(listGamesResponse);
		responseBuilder.setId(id);
		responseBuilder.setStatus(0);

		return responseBuilder.build();
	}

	public static Response getLoginResponse(int id, boolean success, String token) {
		Response.Builder responseBuilder = Response.newBuilder();
		responseBuilder.setStatus(success ? 0 : Errors.Key.LoginFailed.getIntegerCode());
		responseBuilder.setId(id);
		responseBuilder.setLoginResponse(LoginResponse.newBuilder().setToken(token).build());
		return responseBuilder.build();
	}

	public static Response getDefaultResponse(int id) {
		Response.Builder responseBuilder = Response.newBuilder();
		responseBuilder.setId(id);
		responseBuilder.setStatus(0);
		return responseBuilder.build();
	}

}
