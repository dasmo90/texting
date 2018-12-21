package de.marmor.texting.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marmor.texting.model.GamePicsit;

public class GameStatusPicsit extends GameStatus{

	private static final Logger LOG = LoggerFactory.getLogger(GameStatusPicsit.class);

	private int myScore;
	private int gamePhase;
	private int myPhase;

	public GameStatusPicsit() {
		super();
	}
	
	public GameStatusPicsit(GamePicsit game, String currentPlayerId) {
		super(game, currentPlayerId);
		
		gamePhase = game.getPhase();
		
		if(status == 0) {
			myScore = 0;
			myPhase = -1;
	
		} else if(status == 1) {
			myScore = game.getCertainPlayer(currentPlayerId).getScore();
			myPhase = game.getCertainPlayer(currentPlayerId).getPhase();

		} else {
			myScore = game.getCertainPlayer(currentPlayerId).getScore();
			myPhase = game.getCertainPlayer(currentPlayerId).getPhase();
		}
	}

	public int getMyScore() {
		return myScore;
	}

	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}
	
}
