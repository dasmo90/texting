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

	private Map<String, String> idleCompanions = new HashMap<String, String>();
	private Map<String,Game> games = new HashMap<String,Game>();

	// one needs to be logged in to make or enter a game
	@RequestMapping(value = "/game/login",  method = RequestMethod.GET)
	public String login(@RequestParam("name") String name) {
		companionCounter++;
		String newCompanionId = UUID.randomUUID().toString();
		idleCompanions.put(newCompanionId, name);
		return newCompanionId;
	}
	
	// whoever makes a new game is the game owner, who is the only one allowed to start that game later on
	// one can only participate in one game at a time
	// to participate it is necessary to be logged in
	@RequestMapping(value = "/game/new",  method = RequestMethod.GET)
	public String newGame(@RequestParam("companionId") String companionId,
			@RequestParam("numberOfShownWords") int numberOfShownWords,
			@RequestParam("numberOfWrittenWordsMin") int numberOfWrittenWordsMin,
			@RequestParam("numberOfWrittenWordsMax") int numberOfWrittenWordsMax) {
		if(idleCompanions.containsKey(companionId)) {
			String newGameId = UUID.randomUUID().toString();
			GameSettings newGameSettings = new GameSettings(idleCompanions.get(companionId), companionId,
					numberOfShownWords, numberOfWrittenWordsMin, numberOfWrittenWordsMax);
			gameCounter++;
			games.put(newGameId, new Game(newGameSettings));
			companionCounter--;
			idleCompanions.remove(companionId);
			return newGameId;
		} else {
			return null;
		}
		
	}
	
	// one can only participate in one game at a time
	// to participate it is necessary to be logged in
	@RequestMapping(value = "/game/enter",  method = RequestMethod.GET)
	public boolean enterGame(@RequestParam("companionId") String companionId,
			@RequestParam("gameId") String gameId) {
		if(games.containsKey(gameId) && idleCompanions.containsKey(companionId)) {
			if(games.get(gameId).getStatus() == 0) {
				games.get(gameId).getSettings().addPlayer(companionId,idleCompanions.get(companionId));
				companionCounter--;
				idleCompanions.remove(companionId);
				return true;
			}
		}
		return false;
	}
	
	// if one leaves a game it is possible to make or enter a new game again
	// the game owner can't leave the game, he has to shut it
	@RequestMapping(value = "/game/leave",  method = RequestMethod.GET)
	public boolean leaveGame(@RequestParam("gameId") String gameId, @RequestParam("companionId") String companionId) {
		if(games.containsKey(gameId)) {
			if(games.get(gameId).getSettings().getPlayers().containsKey(companionId) &&
					!games.get(gameId).getSettings().getOwnerId().equals(companionId)) {
				Map<String,String> returnedCompanions = games.get(gameId).getSettings().removePlayer(companionId);
				idleCompanions.putAll(returnedCompanions);
				companionCounter = companionCounter + returnedCompanions.size();
				return true;
			}
		}
		return false;
	}
	
	// only the game owner can shut his game
	// all participants will be removed from the game and can participate in a new game
	@RequestMapping(value = "/game/shut",  method = RequestMethod.GET)
	public boolean shutGame(@RequestParam("gameId") String gameId, @RequestParam("ownerId") String ownerId) {
		if(games.containsKey(gameId)) {
			if(games.get(gameId).getSettings().getOwnerId().equals(ownerId)) {
				Map<String,String> returnedCompanions = games.get(gameId).getSettings().removePlayer(ownerId);
				games.remove(gameId);
				gameCounter--;
				idleCompanions.putAll(returnedCompanions);
				companionCounter = companionCounter + returnedCompanions.size();
					return true;
			}
		}
		return false;
	}
	
	// only the game owner can start his game
	// once started, nobody can enter the game any longer
	@RequestMapping(value = "/game/start", method = RequestMethod.GET)
	public void startGame(@RequestParam("gameId") String gameId, @RequestParam("ownerId") String ownerId) {
		if(games.containsKey(gameId)) {
			if(games.get(gameId).getSettings().getOwnerId().equals(ownerId) && !games.get(gameId).getSettings().isEmpty()) {
				games.get(gameId).start();
			}
		}
	}
	
	
}
