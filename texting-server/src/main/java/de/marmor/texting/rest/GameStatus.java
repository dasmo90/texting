package de.marmor.texting.rest;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marmor.texting.model.Game;
import de.marmor.texting.model.StoryPiece;

public class GameStatus {

	private static final Logger LOG = LoggerFactory.getLogger(GameStatus.class);
	private int status;
	private boolean yourTurn;
	private String whosTurnName;
	private String shownLetters;
	private List<String> playerNames = new LinkedList<>();
	private int currentRound;
	private List<StoryPiece> story = new LinkedList<>();
	private int nofShownLetters;
	private int minLetters;
	private int maxLetters;
	private int nofRounds;

	public GameStatus() {
		
	}
	
	public GameStatus(Game game, String currentPlayerId) {
		nofShownLetters = game.getSettings().getShownLetters();
		minLetters = game.getSettings().getMinLetters();
		maxLetters = game.getSettings().getMaxLetters();
		nofRounds = game.getSettings().getRounds();
		status = game.getStatus();
		
		if(status == 0) {
			yourTurn = false;
			whosTurnName = null;
			shownLetters = null;
			currentRound = 0;
			story = null;
			LOG.info(String.valueOf(game.getSettings().getPlayers().keySet().size()));
			for(String key : game.getSettings().getPlayers().keySet()) {
				LOG.info(key);
				playerNames.add(game.getSettings().getPlayers().get(key));
			}	
		} else if(status == 1) {
			yourTurn = game.whoseTurnId() == currentPlayerId;
			whosTurnName = game.getSettings().getPlayers().get(game.whoseTurnId());
			shownLetters = game.getShownLetters();
			currentRound = game.getCurrentRound();
			story = null;
			for(int i = 0; i < game.getPlayersInOrder().size(); i++) {
				playerNames.add(game.getSettings().getPlayers().get(game.getPlayersInOrder().get(i)));
			}
		} else {
			yourTurn = false;
			whosTurnName = null;
			shownLetters = null;
			currentRound = game.getSettings().getRounds()+1;
			story = game.getStoryAsList();
			for(int i = 0; i < game.getPlayersInOrder().size(); i++) {
				playerNames.add(game.getSettings().getPlayers().get(game.getPlayersInOrder().get(i)));
			}
		}
	}
	
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

	public String getShownWords() {
		return shownLetters;
	}

	public void setShownWords(String shownWords) {
		this.shownLetters = shownWords;
	}

	public String getWhosTurnName() {
		return whosTurnName;
	}

	public void setWhosTurnName(String whosTurnName) {
		this.whosTurnName = whosTurnName;
	}

	public List<String> getPlayers() {
		return playerNames;
	}

	public void setPlayers(List<String> playerNames) {
		this.playerNames = playerNames;
	}

	public int getShownWordsCount() {
		return nofShownLetters;
	}

	public void setShownWordsCount(int shownWordsCount) {
		this.nofShownLetters = shownWordsCount;
	}

	public int getMinWords() {
		return minLetters;
	}

	public void setMinWords(int minWords) {
		this.minLetters = minWords;
	}

	public int getMaxWords() {
		return maxLetters;
	}

	public void setMaxWords(int maxWords) {
		this.maxLetters = maxWords;
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

	public int getRounds() {
		return nofRounds;
	}

	public void setRounds(int nofRounds) {
		this.nofRounds = nofRounds;
	}
}
