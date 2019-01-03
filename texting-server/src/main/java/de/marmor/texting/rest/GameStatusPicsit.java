package de.marmor.texting.rest;

import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marmor.texting.model.Card;
import de.marmor.texting.model.GamePicsit;

public class GameStatusPicsit extends GameStatus{

	private static final Logger LOG = LoggerFactory.getLogger(GameStatusPicsit.class);

	private int myScore;
	private int gamePhase;
	private int myPhase;
	private List<Integer> scores = new LinkedList<Integer>(); // list of scores in order of players
	private List<Integer> middle = new LinkedList<Integer>(); // list of cards in the middle, empty, if game in phase 0
	private List<Integer> pileOfCards = new LinkedList<Integer>();
	private int nofCardsLeft;
	private String title;	// this is the title that the current correct card was given
	private List<Integer> cardsOnHand = new LinkedList<Integer>(); // list of cards on players hand
	private int latestMiddleCard = -1; // last card that has been put into the middle by this player
	private int latestPickedCard = -1; // last card that has been picked by this player
	private int latestGainedScore = 0;
	
	private List<Card> latestMiddleSituation = new LinkedList<Card>(); // List of Card Objects

	public GameStatusPicsit() {
		super();
		LOG.info("GameStatusPicsit made.");
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
			cardsOnHand = game.getCertainPlayer(currentPlayerId).getCardsOnHand();
			latestMiddleCard = game.getCertainPlayer(currentPlayerId).getLatestPutDownCard();
			latestPickedCard = game.getCertainPlayer(currentPlayerId).getLatestPickedCard();
			latestGainedScore = game.getCertainPlayer(currentPlayerId).getLatestGainedScore();
			latestMiddleSituation = game.getLastRoundsMiddleCards();

		} else {
			myScore = game.getCertainPlayer(currentPlayerId).getScore();
			myPhase = game.getCertainPlayer(currentPlayerId).getPhase();
			scores = game.getScores();
			latestMiddleCard = game.getCertainPlayer(currentPlayerId).getLatestPutDownCard();
			latestPickedCard = game.getCertainPlayer(currentPlayerId).getLatestPickedCard();
			latestGainedScore = game.getCertainPlayer(currentPlayerId).getLatestGainedScore();
			latestMiddleSituation = game.getLastRoundsMiddleCards();
		}
	}
	
	public List<Card> getLatestMiddleSituation(){
		return latestMiddleSituation;
	}
	
	public int getMyLatestGainedScore() {
		return latestGainedScore;
	}
	
	public int getMyLatestMiddleCard() {
		return latestMiddleCard;
	}
	
	public int getMyLatestPickedCard() {
		return latestPickedCard;
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
	
	public List<Integer> getScores() {
		return scores;
	}
	
	public List<Integer> getMiddle() {
		return middle;
	}
	
	public List<Integer> getCardsOnHand() {
		return cardsOnHand;
	}
	
	/*public List<Integer> getPileOfCards() {
		return pileOfCards;
	}*/
	
	public int getNofCardsLeft() {
		return nofCardsLeft;
	}
	
	public String getTitle() {
		return title;
	}
	
}
