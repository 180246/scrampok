package com.pokware.client.netty;

import java.util.ArrayList;
import java.util.List;

public class PokerClientManager {
	
	private List<PokerClient> pokerClientList = new ArrayList<PokerClient>();
	
	public void addPokerClient(PokerClient client) {
		this.pokerClientList.add(client);
	}
	
	public PokerClient getPokerClient(String username) {
		for (PokerClient pokerClient : pokerClientList) {
			if (pokerClient.getUsername().equals(username)) {
				return pokerClient;
			}
		}
		return null;
	}
	
	public String[] getClientNameList() {
		String[] result = new String[pokerClientList.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = pokerClientList.get(i).getUsername();			
		}
		return result;
	}
	
	public List<PokerClient> getPokerClientList() {
		return pokerClientList;
	}

}
