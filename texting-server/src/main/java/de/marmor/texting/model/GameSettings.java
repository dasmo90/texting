package de.marmor.texting.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mbuerger on 18.12.2017.
 */
public class GameSettings {

	private String name;
	private String ownerId;
	private int numberOfShownWords;
	private int numberOfWrittenWordsMin;
	private int numberOfWrittenWordsMax;
	private Map<String, String> players = new HashMap<String,String>();
	private int playerCounter;
	private boolean empty;
	
	public GameSettings(String name, String companionId, int show, int wrMin, int wrMax) {
		if(show < 0) {
			show = 1;
		}
		if(wrMin < show) {
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
		playerCounter = 1;
		empty = false;
	}
	
	public String getName() {
		return name+"'s game";
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwnerId() {
		return ownerId;
	}
	
	public Map<String, String> getPlayers(){
		return players;
	}
	
	public int getMinWords() {
		return numberOfWrittenWordsMin;
	}
	
	public int getMaxWords() {
		return numberOfWrittenWordsMax;
	}
	
	public int getShownWords() {
		return numberOfShownWords;
	}
	
	public void addPlayer(String newPlayerId, String newPlayerName) {
		if(!players.containsKey(newPlayerId)) {
			players.put(newPlayerId,newPlayerName);
			playerCounter++;
		}
	}
	
	public Map<String,String> removePlayer(String leavingPlayerId) {
		Map<String,String> playersThatLeft = new HashMap<String,String>();
		if(players.containsKey(leavingPlayerId)) {
			if(leavingPlayerId != ownerId) {
				playersThatLeft.put(leavingPlayerId, players.get(leavingPlayerId));
			} else {
				playersThatLeft = players;
				empty = true;
			}
			for(String key : playersThatLeft.keySet()) {
				players.remove(key);
			}
			return playersThatLeft;
		}
		return null;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	

}
