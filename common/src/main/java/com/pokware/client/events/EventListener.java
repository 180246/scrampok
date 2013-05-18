package com.pokware.client.events;

import com.pokware.protobuf.Events;

public interface EventListener {

	public void onEvent(Events events);
	
}
