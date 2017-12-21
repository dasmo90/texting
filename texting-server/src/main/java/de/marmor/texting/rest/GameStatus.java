package de.marmor.texting.rest;

import java.util.List;

import de.marmor.texting.model.StoryPiece;

public class GameStatus {

	private int status;

	private boolean yourTurn;
	private List<String> shownWords;
	private String whosTurnName;
	private List<String> players;
	private int currentRound;
	private List<StoryPiece> story;

	private int shownWordsCount;
	private int minWords;
	private int maxWords;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isYourTurn() {
		return yourTurn;
	}

	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}

	public List<String> getShownWords() {
		return shownWords;
	}

	public void setShownWords(List<String> shownWords) {
		this.shownWords = shownWords;
	}

	public String getWhosTurnName() {
		return whosTurnName;
	}

	public void setWhosTurnName(String whosTurnName) {
		this.whosTurnName = whosTurnName;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public int getShownWordsCount() {
		return shownWordsCount;
	}

	public void setShownWordsCount(int shownWordsCount) {
		this.shownWordsCount = shownWordsCount;
	}

	public int getMinWords() {
		return minWords;
	}

	public void setMinWords(int minWords) {
		this.minWords = minWords;
	}

	public int getMaxWords() {
		return maxWords;
	}

	public void setMaxWords(int maxWords) {
		this.maxWords = maxWords;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	public List<StoryPiece> getStory() {
		return story;
	}

	public void setStory(List<StoryPiece> story) {
		this.story = story;
	}
}
