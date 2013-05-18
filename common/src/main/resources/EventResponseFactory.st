package com.pokware.protobuf;

import com.pokware.protobuf.Events.ServerEvent;
import com.pokware.protobuf.Events.ServerEvent.Type;
import com.pokware.protobuf.RPCMessages.Response;
import com.pokware.server.game.*;

public class EventResponseFactory {

$methods:{
	public static Response get$it.eventName$Response($it.parameterList$) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.$it.eventName$);
		
		Events.$it.eventName$.Builder eventBuilder = Events.$it.eventName$.newBuilder();	
$it.setters:{
		eventBuilder.set$it.capitalizedFieldName$($it.fieldName$);}$		
		
		serverEventBuilder.set$it.eventName$(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}
}$

}