package de.marmor.texting.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Game {

	private GameSettings settings;
	private int status = 0; // 0: not started yet, 1:started, 2: ended
	private List<String> playersInOrder = new LinkedList<String>();
	private int numberOfPlayers = 0;
	private String story = "";
	private String shownWords = "";
	private int whoseTurn = 0;
	
	public Game(GameSettings settings) {
		this.settings = settings;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getShownWords() {
		if(status == 1) {
			return shownWords;
		}
		return null;
	}
	
	public String getStory() {
		if(status == 2) {
			return story;
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
		numberOfPlayers = settings.getPlayers().size();
		int[] order = new int[numberOfPlayers];
		for(int i = 0; i< numberOfPlayers; i++) {
			order[i] = i;
		}
		Collections.shuffle(Arrays.asList(order));
		int j = 0;
		for(String key : settings.getPlayers().keySet()) {
			playersInOrder.add(order[j],key);
			j++;
		}
	}
	
	public boolean commitStoryPiece(String playerId, String storyPiece) {
		if(status == 1 && playerId.equals(playersInOrder.get(whoseTurn))) {
			// it's possible to write nothing!
			if(storyPiece == null || storyPiece.isEmpty()) {
				whoseTurn = (whoseTurn+1)%numberOfPlayers;
				return true;
			}
			String[] words = storyPiece.split("\\s");
			int numberOfWords = words.length;
			
			if(numberOfWords < settings.getMinWords() || numberOfWords > settings.getMaxWords()) {
				return false;
			}
			else {
				story = story + " " +storyPiece;
				shownWords = "";
				for(int i = 1; i <= settings.getShownWords(); i++) {
					shownWords = words[numberOfWords-i] + shownWords;
				}
				whoseTurn = (whoseTurn+1)%numberOfPlayers;
				return true;
			}
		}
		return false;
	}
	
	public void end() {
		status = 2;
	}

}
