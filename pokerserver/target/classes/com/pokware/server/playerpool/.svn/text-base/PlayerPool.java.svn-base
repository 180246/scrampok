package com.pokware.server.playerpool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;
import com.pokware.server.game.Player;

@Singleton
public class PlayerPool implements PlayerLocator {
	
	private Map<String, String> playerDatabase = new ConcurrentHashMap<String, String>();
	
	private Map<Long, Player> playerMap = new ConcurrentHashMap<Long, Player>();

	public PlayerPool() {
		playerDatabase.put("fabien", "1:fabien:fabien");
		playerDatabase.put("fabien2", "2:fabien2:fabien2");
		playerDatabase.put("fabien3", "3:fabien3:fabien3");
		playerDatabase.put("fabien4", "4:fabien4:fabien4");
	}
	
	public Long authenticate(String username, String password) {
		if (playerDatabase.containsKey(username)) {			
			String string = playerDatabase.get(username);
			String[] split = string.split(":");
			if (split[2].equals(password)) {
				return Long.valueOf(split[0]);
			}			
		}		
		return null;		
	}
	
	public void addPlayer(Player player) {
		playerMap.put(player.getPlayerId(), player);
	}
	
	@Override
	public Player getPlayer(long playerId) {
		if (playerMap.containsKey(playerId)) {
			return playerMap.get(playerId);
		}
		else {
			return null;
		}
	}


}
