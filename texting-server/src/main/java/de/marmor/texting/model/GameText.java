package de.marmor.texting.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameText extends Game {

	private static final Logger LOG = LoggerFactory.getLogger(GameText.class);
	private GameSettingsText settingsText;
	private String story = "";
	private List<StoryPiece> storyAsList = new LinkedList<>();
	private String shownLetters = "";
	private List<String> playersInOrder = new LinkedList<>();

	// private List<StoryPiece> story;

	public String whoseTurnId() {
		LOG.info(String.valueOf(whoseTurn));
		return playersInOrder.get(whoseTurn);
	}

	public List<String> getPlayersInOrder() {
		return playersInOrder;
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
				return settingsText.removePlayer(companionId);
			}
		}
		return playersThatLeft;
	}

	public GameText(GameSettingsText settings) {
		super(settings);
		this.settingsText = settings;
	}
	
	public GameSettingsText getSettings() {
		return settingsText;
	}
	

	public String getShownLetters() {
		if (status == 1) {
			LOG.info("Show: |" + shownLetters + "|");
			return shownLetters;
		}
		return null;
	}

	public String getStory() {
		if (status == 2) {
			LOG.info("Story: |" + story + "|");
			return story;

		}
		return null;
	}

	public List<StoryPiece> getStoryAsList() {
		if (status == 2) {
			return storyAsList;
		}
		return null;
	}
	
	public void start() {
		if (status == 0) {
			status = 1;
			LOG.info("Status 1");
			currentRound = 1;
			nofPlayers = settingsText.getPlayers().size();
			Integer[] order = new Integer[nofPlayers];
			for (int i = 0; i < nofPlayers; i++) {
				order[i] = i;
			}
			Collections.shuffle(Arrays.asList(order));
			LOG.info(Arrays.toString(order));
			List<String> players = new LinkedList<>();
			for (String key : settingsText.getPlayers().keySet()) {
				players.add(key);
			}
			for (int i = 0; i < nofPlayers; i++) {
				playersInOrder.add(players.get(order[i]));
			}
			settingsText.forgetOwner();
		}
	}


	public boolean commitStoryPiece(String playerId, String storyPiece) {
		LOG.info("Try to add something.");
		if (status == 1 && playerId.equals(playersInOrder.get(whoseTurn))) {
			// it's possible to write nothing!
			if (storyPiece == null || storyPiece.isEmpty()) {
				LOG.info("Empty string.");
				whoseTurn = (whoseTurn + 1) % nofPlayers;
				if (whoseTurn == 0) {
					LOG.info("Next Round.");
					currentRound++;
					if (currentRound > settingsText.getRounds()) {
						LOG.info("Last round finished.");
						status = 2;
						LOG.info("Status = 2");
					}
				}
				return true;
			}

			int nofLetters = storyPiece.length();
			LOG.info(String.valueOf(nofLetters));
			if (nofLetters < settingsText.getMinLetters() || nofLetters > settingsText.getMaxLetters()) {
				LOG.info("Wrong number of letters.");
				return false;
			} else {
				story = story + storyPiece + " ";
				storyAsList.add(new StoryPiece(playerId, settingsText.getPlayers().get(playerId), storyPiece));
				LOG.info("Story piece added.");
				char[] showi = new char[settingsText.getShownLetters()];
				storyPiece.getChars(nofLetters - settingsText.getShownLetters(), nofLetters, showi, 0);
				shownLetters = new String(showi);
				whoseTurn = (whoseTurn + 1) % nofPlayers;
				if (whoseTurn == 0) {
					LOG.info("Next round.");
					currentRound++;
					if (currentRound > settingsText.getRounds()) {
						LOG.info("Last round finished.");
						status = 2;
						LOG.info("Status = 2");
					}
				}
				return true;
			}
		}
		LOG.info("Wrong status or wrong player.");
		return false;
	}

}
