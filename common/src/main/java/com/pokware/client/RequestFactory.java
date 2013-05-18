package com.pokware.client;

import java.util.concurrent.atomic.AtomicInteger;

import com.pokware.protobuf.RPCMessages.JoinTableRequest;
import com.pokware.protobuf.RPCMessages.ListGamesRequest;
import com.pokware.protobuf.RPCMessages.LoginRequest;
import com.pokware.protobuf.RPCMessages.Request;
import com.pokware.protobuf.RPCMessages.Request.Type;
import com.pokware.protobuf.RPCMessages.SitRequest;

public class RequestFactory {

	private static final AtomicInteger idGenerator = new AtomicInteger(1);
	
	public static Request newListGamesRequest() {				
		Request.Builder requestBuilder = Request.newBuilder().setType(Type.ListGamesRequest).setId(idGenerator.incrementAndGet());		
		ListGamesRequest.Builder listGamesRequestBuilder = ListGamesRequest.newBuilder();		
		requestBuilder.setListGamesRequest(listGamesRequestBuilder.build());						
		return requestBuilder.build();		
	}
	
	public static Request newJoinTableRequest(long gameId) {				
		Request.Builder requestBuilder = Request.newBuilder().setType(Type.JoinTableRequest).setId(idGenerator.incrementAndGet());		
		JoinTableRequest.Builder jointTableRequestBuilder = JoinTableRequest.newBuilder();					
		jointTableRequestBuilder.setGameId(gameId);		
		requestBuilder.setJoinTableRequest(jointTableRequestBuilder.build());
		return requestBuilder.build();		
	}

	public static Request newLoginRequest(String login, String password) {
		Request.Builder requestBuilder = Request.newBuilder().setType(Type.LoginRequest).setId(idGenerator.incrementAndGet());		
		LoginRequest.Builder loginRequestBuilder = LoginRequest.newBuilder();					
		loginRequestBuilder.setLogin(login);		
		loginRequestBuilder.setPassword(password);		
		requestBuilder.setLoginRequest(loginRequestBuilder);
		return requestBuilder.build();		
	}

	public static Request newSitRequest(long gameId, int sitIndex) {
		Request.Builder requestBuilder = Request.newBuilder().setType(Type.SitRequest).setId(idGenerator.incrementAndGet());		
		SitRequest.Builder sitRequestBuilder = SitRequest.newBuilder();					
		sitRequestBuilder.setGameId(gameId);		
		sitRequestBuilder.setSitIndex(sitIndex);		
		requestBuilder.setSitRequest(sitRequestBuilder.build());
		return requestBuilder.build();		
	}	
	
	
}
