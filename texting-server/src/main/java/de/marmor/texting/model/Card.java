package de.marmor.texting.model;

import java.util.LinkedList;
import java.util.List;

public class Card {
	
	private int index;
	private int playerThatPutItDown;
	private List<Integer> playersThatPickedIt = new LinkedList<Integer>();
	private boolean correctCard;
	
	public Card(int index, int playerThatPutItDown, boolean correctCard) {
		this.index = index;
		this.playerThatPutItDown = playerThatPutItDown;
		this.correctCard = correctCard;
	}
	
	public void addPlayerPick(int playerIndex) {
		playersThatPickedIt.add(playerIndex);
	}
	
	public int getCardIndex() {
		return index;
	}
	
	public int getPlayerThatPutItDown() {
		return playerThatPutItDown;
	}
	
	public boolean getCorrect() {
		return correctCard;
	}
	
	public List<Integer> getPlayersThatPickedIt() {
		return playersThatPickedIt;
	}

}
