package de.marmor.texting.model;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by mbuerger on 18.12.2017.
 */
public class GameSettings {

	private static final Logger LOG = LoggerFactory.getLogger(GameSettings.class);
	private String name;
	private String ownerId;
	private int nofShownLetters;
	private int nofMinLetters;
	private int nofMaxLetters;
	private int nofRounds;
	private Map<String, String> players = new HashMap<String,String>();
	private boolean empty;
	
	public GameSettings(String name, String companionId, int show, int wrMin, int wrMax, int rounds) {
		// default settings if parameters are invalid
		if(show < 0) {
			show = 50;
		}
		if(wrMin < show) {
			wrMin = show;
		}
		if(wrMax < wrMin) {
			wrMax = wrMin+10;
		}
		if(rounds <= 0) {
			rounds = 1;
		}
		
		this.name = name+"'s game";
		nofShownLetters = show;
		nofMinLetters = wrMin;
		nofMaxLetters = wrMax;
		nofRounds = rounds;
		players.put(companionId, name);
		ownerId = companionId;
		empty = false;
	}
	
	public String getName() {
		return name;
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
	
	public int getMinLetters() {
		return nofMinLetters;
	}
	
	public int getMaxLetters() {
		return nofMaxLetters;
	}
	
	public int getShownLetters() {
		return nofShownLetters;
	}
	
	public int getRounds() {
		return nofRounds;
	}
	
	public void addPlayer(String newPlayerId, String newPlayerName) {
		if(!players.containsKey(newPlayerId)) {
			players.put(newPlayerId,newPlayerName);
		}
	}
	
	public Map<String,String> removePlayer(String leavingPlayerId) {
		Map<String,String> playersThatLeft = new HashMap<String,String>();
		if(players.containsKey(leavingPlayerId)) {
			LOG.info("Player found.");
			if(leavingPlayerId != ownerId) {
				playersThatLeft.put(leavingPlayerId, players.get(leavingPlayerId));
			} else {
				LOG.info("Owner leaves");
				playersThatLeft.putAll(players);
				empty = true;
			}
			for(String key : playersThatLeft.keySet()) {
				players.remove(key);
			}
			LOG.info("Number of leaving players: "+String.valueOf(playersThatLeft.size()));
			return playersThatLeft;
		}
		LOG.info("Nobody left the game.");
		return null;
	}
	
	public boolean isEmpty() {
		return empty;
	}

	public void forgetOwner() {
		ownerId = "";
	}
	

}
