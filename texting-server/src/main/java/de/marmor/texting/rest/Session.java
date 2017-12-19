package de.marmor.texting.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.marmor.texting.model.Game;
import de.marmor.texting.model.GameSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mbuerger on 16.12.2017.
 */
@RestController
public class Session {

	private static final Logger LOG = LoggerFactory.getLogger(Session.class);

	private static int companionCounter = 0;
	private static int gameCounter = 0;

	private Map<String, String> companions = new HashMap<String, String>();
	private Map<String,GameSettings> gamesThatDidNotStartYet = new HashMap<String,GameSettings>();
	private Map<String,Game> games = new HashMap<String,Game>();

	@RequestMapping(value = "/game/enter",  method = RequestMethod.GET)
	public String login(@RequestParam("name") String name) {
		companionCounter++;
		String newCompanionId = UUID.randomUUID().toString();
		companions.put(newCompanionId, name);
		return newCompanionId;
	}
	
	@RequestMapping(value = "/game/new",  method = RequestMethod.GET)
	public String newGame(@RequestParam("companionId") String companionId,
			@RequestParam("numberOfShownWords") int numberOfShownWords,
			@RequestParam("numberOfWrittenWordsMin") int numberOfWrittenWordsMin,
			@RequestParam("numberOfWrittenWordsMax") int numberOfWrittenWordsMax) {
		String newSettingId = UUID.randomUUID().toString();
		GameSettings newGameSettings = new GameSettings(newSettingId, companions.get(companionId), companionId,
				numberOfShownWords, numberOfWrittenWordsMin, numberOfWrittenWordsMax);
		gamesThatDidNotStartYet.put(newSettingId, newGameSettings);
		return newSettingId;
	}
	
	@RequestMapping(value = "/game/enter",  method = RequestMethod.GET)
	public boolean enterGame(@RequestParam("companionId") String companionId,
			@RequestParam("settingsId") String settingsId) {
		if(gamesThatDidNotStartYet.containsKey(settingsId) && companions.containsKey(companionId)) {
			gamesThatDidNotStartYet.get(settingsId).addPlayer(companionId,companions.get(companionId));
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping(value = "/game/start", method = RequestMethod.GET)
	public String startGame(@RequestParam("settingsId") String settingsId) {
		if(gamesThatDidNotStartYet.containsKey(settingsId)) {
			gameCounter++;
			String newGamesId = UUID.randomUUID().toString();
			games.put(newGamesId, new Game(gamesThatDidNotStartYet.get(settingsId)));
			gamesThatDidNotStartYet.remove(settingsId);
			return newGamesId;
		} else {
			return null;
		}
	}
}
