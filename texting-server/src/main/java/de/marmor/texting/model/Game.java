package de.marmor.texting.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Game {

	private static final Logger LOG = LoggerFactory.getLogger(Game.class);
	private GameSettings settings;
	private int status = 0; // 0: not started yet, 1:started, 2: ended
	private List<String> playersInOrder = new LinkedList<>();
	private int nofPlayers = 0;
	private String story = "";
	private List<String> storyAsList = new LinkedList<>();
	private String shownLetters = "";
	private int whoseTurn = 0;
	private int currentRound = 0;
	
	//private List<StoryPiece> story;
	
	public int getCurrentRound() {
		return currentRound;
	}
	
	public Game(GameSettings settings) {
		this.settings = settings;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getShownLetters() {
		if(status == 1) {
			LOG.info("Zeige: |"+shownLetters+"|");
			return shownLetters;
		}
		return null;
	}
	
	public String getStory() {
		if(status == 2) {
			LOG.info("Geschichte: |"+story+"|");
			return story;

		}
		return null;
	}
	
	public List<String> getStoryAsList(){
		if(status == 2) {
			return storyAsList;
		}
		return null;
	}
	
	public String whoseTurnId() {
		return playersInOrder.get(whoseTurn);
	}
	
	public GameSettings getSettings() {
		if(status == 0) {
			return settings;
		}
		return null;
	}
	
	public List<String> getOrder() {
		return playersInOrder;
	}
	
	public void start() {
		status = 1;
		LOG.info("Status 1");
		currentRound = 1;
		nofPlayers = settings.getPlayers().size();
		Integer[] order = new Integer[nofPlayers];
		for(int i = 0; i< nofPlayers; i++) {
			order[i] = i;
		}
		Collections.shuffle(Arrays.asList(order));
		LOG.info(Arrays.toString(order));
		List<String> players = new LinkedList<>();
		for(String key : settings.getPlayers().keySet()) {
			players.add(key);
		}
		for(int i = 0; i < nofPlayers; i++) {
			playersInOrder.add(players.get(order[i]));
		}
	}
	
	public boolean commitStoryPiece(String playerId, String storyPiece) {
		LOG.info("Verusche was zuzufuegen.");
		if(status == 1 && playerId.equals(playersInOrder.get(whoseTurn))) {
			// it's possible to write nothing!
			if(storyPiece == null || storyPiece.isEmpty()) {
				LOG.info("Leerer String");
				whoseTurn = (whoseTurn+1)%nofPlayers;
				if(whoseTurn == 0) {
					LOG.info("naechste Runde");
					currentRound++;
				}
				return true;
			}
			
			int nofLetters = storyPiece.length();
			
			if(nofLetters < settings.getMinLetters() || nofLetters > settings.getMaxLetters()) {
				LOG.info("Falsche Buchstabenzahl");
				return false;
			}
			else {
				story = story + storyPiece + " ";
				storyAsList.add(storyPiece);
				char[] showi = new char[settings.getShownLetters()];
				storyPiece.getChars(nofLetters-settings.getShownLetters(), nofLetters, showi, 0);
				shownLetters = new String(showi);
				whoseTurn = (whoseTurn+1)%nofPlayers;
				if(whoseTurn == 0) {
					LOG.info("naechste Runde");
					currentRound++;
				}
				LOG.info("Was hinzugefuegt.");
				return true;
			}
		}
		LOG.info("Da hat was nicht geklappt.");
		return false;
	}
	
	public void end() {
		status = 2;
		LOG.info("Status = 2");
	}

}
