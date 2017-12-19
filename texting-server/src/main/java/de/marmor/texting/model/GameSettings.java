package de.marmor.texting.model;

import java.util.Map;

/**
 * Created by mbuerger on 18.12.2017.
 */
public class GameSettings {

	private String name;
	private String ownerId;
	private String gameId;
	private int numberOfShownWords;
	private int numberOfWrittenWordsMin;
	private int numberOfWrittenWordsMax;
	private Map<String, String> players;
	
	public GameSettings(String gameId, String name, String companionId, int show, int wrMin, int wrMax) {
		if(show < 0) {
			show = 1;
		}
		if(wrMin <= show) {
			wrMin = show;
		}
		if(wrMax < wrMin) {
			wrMax = wrMin+3;
		}
		this.name = name+"'s game";
		numberOfShownWords = show;
		numberOfWrittenWordsMin = wrMin;
		numberOfWrittenWordsMax = wrMax;
		players.put(companionId, name);
		ownerId = companionId;
		this.gameId = gameId;
	}
	
	public String getName() {
		return name+"'s game";
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addPlayer(String newPlayerId, String newPlayerName) {
		if(!players.containsKey(newPlayerId)) {
			players.put(newPlayerId,newPlayerName);
		}
	}
	
	public void removePlayer(String leavingPlayerId) {
		if(players.containsKey(leavingPlayerId)) {
			players.remove(leavingPlayerId);
		}
	}
}
