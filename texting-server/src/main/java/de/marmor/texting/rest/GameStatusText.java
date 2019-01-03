package de.marmor.texting.rest;

import java.util.LinkedList;
import java.util.List;
import de.marmor.texting.model.GameText;
import de.marmor.texting.model.StoryPiece;

public class GameStatusText extends GameStatus{

	private String shownLetters;
	private int currentRound;
	private List<StoryPiece> story = new LinkedList<>();
	private int nofShownLetters;
	private int minLetters;
	private int maxLetters;
	private int nofRounds;

	public GameStatusText() {
		super();
	}
	
	public GameStatusText(GameText game, String currentPlayerId) {
		super(game, currentPlayerId);
		nofShownLetters = game.getSettings().getShownLetters();
		minLetters = game.getSettings().getMinLetters();
		maxLetters = game.getSettings().getMaxLetters();
		nofRounds = game.getSettings().getRounds();
		
		if(status == 0) {
			shownLetters = null;
			currentRound = 0;
			story = null;	
		} else if(status == 1) {
			shownLetters = game.getShownLetters();
			currentRound = game.getCurrentRound();
			story = null;
		} else {
			shownLetters = null;
			currentRound = game.getSettings().getRounds()+1;
			story = game.getStoryAsList();
		}
	}
	

	public String getShownLetters() {
		return shownLetters;
	}

	public void setShownLetters(String shownLetters) {
		this.shownLetters = shownLetters;
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

	public int getNofShownLetters() {
		return nofShownLetters;
	}

	public void setNofShownLetters(int nofShownLetters) {
		this.nofShownLetters = nofShownLetters;
	}

	public int getMinLetters() {
		return minLetters;
	}

	public void setMinLetters(int minLetters) {
		this.minLetters = minLetters;
	}

	public int getMaxLetters() {
		return maxLetters;
	}

	public void setMaxLetters(int maxLetters) {
		this.maxLetters = maxLetters;
	}

	public int getNofRounds() {
		return nofRounds;
	}

	public void setNofRounds(int nofRounds) {
		this.nofRounds = nofRounds;
	}
}
