package de.marmor.texting.model;

import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameText extends Game {

	private static final Logger LOG = LoggerFactory.getLogger(GameText.class);
	private GameSettingsText settingsText;
	private String story = "";
	private List<StoryPiece> storyAsList = new LinkedList<>();
	private String shownLetters = "";

	// private List<StoryPiece> story;

	

	public GameText(GameSettingsText settings) {
		super(settings);
		this.settingsText = settings;
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
