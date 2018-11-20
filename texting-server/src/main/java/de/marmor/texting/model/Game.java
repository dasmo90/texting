package de.marmor.texting.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

	private static final Logger LOG = LoggerFactory.getLogger(Game.class);
	
	private GameSettings settings;
	protected int status = 0; // 0: not started yet, 1:in game, 2: ended
	protected List<String> playersInOrder = new LinkedList<>();
	protected int nofPlayers = 0;
	protected int whoseTurn = 0;
	protected int currentRound = 0;

	// private List<StoryPiece> story;

	public Game(GameSettings settings) {
		this.settings = settings;
	}

	public GameSettings getSettings() {
		return settings;
	}
	
	public int getCurrentRound() {
		return currentRound;
	}
	
	public int getStatus() {
		return status;
	}

	public int getWhoseTurn() {
		return whoseTurn;
	}
	
	public String whoseTurnId() {
		LOG.info(String.valueOf(whoseTurn));
		return playersInOrder.get(whoseTurn);
	}

	public List<String> getPlayersInOrder() {
		return playersInOrder;
	}

	public void start() {
		if (status == 0) {
			status = 1;
			LOG.info("Status 1");
			currentRound = 1;
			nofPlayers = settings.getPlayers().size();
			Integer[] order = new Integer[nofPlayers];
			for (int i = 0; i < nofPlayers; i++) {
				order[i] = i;
			}
			Collections.shuffle(Arrays.asList(order));
			LOG.info(Arrays.toString(order));
			List<String> players = new LinkedList<>();
			for (String key : settings.getPlayers().keySet()) {
				players.add(key);
			}
			for (int i = 0; i < nofPlayers; i++) {
				playersInOrder.add(players.get(order[i]));
			}
			settings.forgetOwner();
		}
	}

	public void end() {
		status = 2;
		LOG.info("Status = 2");
	}

	public Map<String, String> removeFromRunningGame(String companionId) {
		Map<String, String> playersThatLeft = new HashMap<String, String>();
		if (status != 0) {
			if (playersInOrder.contains(companionId)) {
				nofPlayers--;
				int index = playersInOrder.indexOf(companionId);
				playersInOrder.remove(companionId);
				if (whoseTurn > index) {
					whoseTurn--;
				} else if(whoseTurn == nofPlayers) {
					whoseTurn = 0;
				}
				return settings.removePlayer(companionId);
			}
		}
		return playersThatLeft;
	}

}
