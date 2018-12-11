package de.marmor.texting.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Game {

	private static final Logger LOG = LoggerFactory.getLogger(Game.class);
	
	private GameSettings settings;
	protected int status = 0; // 0: not started yet, 1:in game, 2: ended
	protected int nofPlayers = 0;
	protected int whoseTurn = 0;
	protected int currentRound = 0;

	// private List<StoryPiece> story;

	public Game(GameSettings settings) {
		this.settings = settings;
	}

	public abstract GameSettings getSettings();
	
	public int getCurrentRound() {
		return currentRound;
	}
	
	public int getStatus() {
		return status;
	}

	public int getWhoseTurn() {
		return whoseTurn;
	}
	
	public abstract String whoseTurnId();

	public abstract List<String> getPlayersInOrder();

	public abstract void start();

	public void end() {
		status = 2;
		LOG.info("Status = 2");
	}

	public abstract Map<String, String> removeFromRunningGame(String companionId);

}
