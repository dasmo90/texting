package de.marmor.texting.model;

import java.util.LinkedList;
import java.util.List;

public class Player {
	
	private List<String> cardsOnHand = new LinkedList<String>();
	private boolean myTurn = false;
	private int score = 0;
	private String key;
	
	public Player(String key) {
		this.key = key;
	}
	
	public List<String> drawCard(List<String> pileOfCards) {
		if (pileOfCards.size() <= 0 || !myTurn) {
			return pileOfCards;
		}
		cardsOnHand.add(pileOfCards.get(0));
		pileOfCards.remove(0);
		return pileOfCards;
	}
	
	public String getKey() {
		return key;
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
	
}
