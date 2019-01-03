package de.marmor.texting.model;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Player {
	
	private static final Logger LOG = LoggerFactory.getLogger(Player.class);
	
	private List<Integer> cardsOnHand = new LinkedList<>();
	private boolean myTurn = false;
	private int score = 0;
	private int phase = 0;
	private boolean pickedCorrectly = false;
	private int nofPicksForMe = 0;
	private int latestPutDownCard = -1;
	private int latestPickedCard = -1;
	private int latestGainedScore = 0;
	
	public Player() {
	}
	
	public int getFirstCardOnHand() {
		return cardsOnHand.get(0);
	}
	
	public List<Integer> getCardsOnHand() {
		return cardsOnHand;
	}
	
	public void setPickedCorrectly(boolean c) {
		pickedCorrectly = c;
	}
	
	public boolean getPickedCorrectly() {
		return pickedCorrectly;
	}
	
	public int getPhase() {
		return phase;
	}
	
	public boolean drawCard(List<Integer> pileOfCards) {
		if (pileOfCards.size() <= 0) {
			return false;
		}
		cardsOnHand.add(pileOfCards.get(0));
		pileOfCards.remove(0);
		return true;
	}
	
	public void setTurn(boolean turn) {
		myTurn = turn;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addToScore(int add) {
		score += add;
	}

	public boolean putAPicDown(Integer card) {
		if (phase != 0) {
			return false;
		}
		if (!cardsOnHand.contains(card)) {
			LOG.info("Card not on hand.");
			return false;
		}
		cardsOnHand.remove(Integer.valueOf(card));
		latestPutDownCard = card;
		// if it's your turn, skip the pick phase
		if(myTurn) {
			phase = 2;
		} else {
			phase = 1;
		}
		return true;
	}
	
	public boolean pickAPic(Player pickedPlayer, int card) {
		if (phase != 1) {
			return false;
		}
		pickedPlayer.gotPicked();
		if (pickedPlayer.getMyTurn()) {
			pickedCorrectly = true;
		}
		phase = 2;
		latestPickedCard = card;
		return true;
	}
	
	public void setLatestGainedScore(int score) {
		latestGainedScore = score;
	}
	
	public int getLatestGainedScore() {
		return latestGainedScore;
	}
	
	public int getLatestPutDownCard() {
		return latestPutDownCard;
	}
	
	public int getLatestPickedCard() {
		return latestPickedCard;
	}

	public boolean getMyTurn() {
		return myTurn;
	}
	
	public void setMyTurn(boolean turn) {
		myTurn = turn;
	}

	public void gotPicked() {
		nofPicksForMe++;
	}
	
	public int collectedPicks() {
		return nofPicksForMe;
	}
	
	public void reset() {
		phase = 0;
		nofPicksForMe = 0;
		pickedCorrectly = false;
	}
}
