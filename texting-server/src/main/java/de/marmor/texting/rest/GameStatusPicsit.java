package de.marmor.texting.rest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marmor.texting.model.GamePicsit;

public class GameStatusPicsit extends GameStatus{

	private static final Logger LOG = LoggerFactory.getLogger(GameStatusPicsit.class);

	private int myScore;
	private int gamePhase;
	private int myPhase;
	private Map<String,Integer> scores = new HashMap<String,Integer>(); // empty if game hasn't started yet
	private Map<String,Integer> middle = new HashMap<String,Integer>();
	private List<Integer> pileOfCards = new LinkedList<Integer>();
	private int nofCardsLeft;
	private String title;

	public GameStatusPicsit() {
		super();
	}
	
	public GameStatusPicsit(GamePicsit game, String currentPlayerId) {
		super(game, currentPlayerId);
		
		gamePhase = game.getPhase();
		pileOfCards = game.getPileOfCards();
		nofCardsLeft = pileOfCards.size();
		title = game.getTitle();
		
		if(status == 0) {
			myScore = 0;
			myPhase = -1;
	
		} else if(status == 1) {
			myScore = game.getCertainPlayer(currentPlayerId).getScore();
			myPhase = game.getCertainPlayer(currentPlayerId).getPhase();
			scores = game.getScores();
			middle = game.getMiddle();

		} else {
			myScore = game.getCertainPlayer(currentPlayerId).getScore();
			myPhase = game.getCertainPlayer(currentPlayerId).getPhase();
			scores = game.getScores();
		}
	}

	public int getMyScore() {
		return myScore;
	}

	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}
	
	public int getGamePhase() {
		return gamePhase;
	}
	
	public int getMyPhase() {
		return myPhase;
	}
	
	public Map<String,Integer> getScores() {
		return scores;
	}
	
	public Map<String,Integer> getMiddle() {
		return middle;
	}
	
	public List<Integer> getPileOfCards() {
		return pileOfCards;
	}
	
	public int getNofCardsLeft() {
		return nofCardsLeft;
	}
	
	public String getTitle() {
		return title;
	}
}
