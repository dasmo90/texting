package de.marmor.texting.rest;

import de.marmor.texting.http.StringResponseEntity;
import de.marmor.texting.model.Game;
import de.marmor.texting.model.GameSettings;
import de.marmor.texting.model.GameSettingsText;
import de.marmor.texting.model.GameText;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by mbuerger on 16.12.2017.
 */
@RestController
public class Session {

	private static final String COOKIE_COMPANION_ID = "TEXTING-COOKIE-COMPANION-ID";
	private static final String COOKIE_GAME_ID = "TEXTING-COOKIE-GAME-ID";

	private static final Logger LOG = LoggerFactory.getLogger(Session.class);

	private Map<String, String> idleCompanions = new HashMap<>();
	private Map<String, Game> games = new HashMap<String, Game>();

	private final HttpServletRequest request;

	@Autowired
	public Session(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * one needs to be logged in to make or enter a game
	 * 
	 * @param name
	 *            name of the player
	 * @return player id
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
	public StringResponseEntity login(@RequestParam("name") String name) {
		String newCompanionId = UUID.randomUUID().toString();
		idleCompanions.put(newCompanionId, name);
		return new StringResponseEntity(newCompanionId, HttpStatus.OK);
	}

	
	/**
	 * a player who logs out, leaves (or shuts, if game owner) the game he or she might be in
	 * and is removed from idleCompanions
	 * 
	 * @return boolean if logout was successful
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public boolean logout() {
		String companionId = getCompanionId();
		// if player not in game, simply remove
		if (idleCompanions.containsKey(companionId)) {
			idleCompanions.remove(companionId);
			return true;
		} else {
			// find game, that contains player
			for(String key : games.keySet()) {
				if(games.get(key).getSettings().getPlayers().containsKey(companionId)) {
					int status = games.get(key).getStatus();
					// if game already started, remove player from running game
					if(status != 0) {
						games.get(key).removeFromRunningGame(companionId);
						return true;
					} else {
						// if owner logs out, all players return to idleCompanions except for owner
						if(games.get(key).getSettings().getOwnerId().equals(companionId)) {
							Map<String, String> returnedCompanions = games.get(key).getSettings().removePlayer(companionId);
							games.remove(key);
							idleCompanions.putAll(returnedCompanions);
							idleCompanions.remove(companionId);
							return true;
						} else {
							// if not the owner, just leave the game
							games.get(key).getSettings().removePlayer(companionId);
							return true;
						}
					}
				}
			}
			// if player is not idle and not in game, there is nobody to logout
			return false;
		}
	}

	/**
	 * whoever makes a new game is the game owner, who is the only one allowed to
	 * start that game later on one can only participate in one game at a time to
	 * participate it is necessary to be logged in
	 * 
	 * @param shownLetters
	 * @param minLetters
	 * @param maxLetters
	 * @param rounds
	 * @return game id
	 */
	@RequestMapping(value = "/game/new", method = RequestMethod.GET)
	public StringResponseEntity newGame(@RequestParam("shownLetters") int shownLetters,
			@RequestParam("minLetters") int minLetters, @RequestParam("maxLetters") int maxLetters,
			@RequestParam("rounds") int rounds) {
		String companionId = getCompanionId();
		if (idleCompanions.containsKey(companionId)) {
			LOG.info("Id is valid.");
			String newGameId = UUID.randomUUID().toString();
			GameSettings newGameSettings = new GameSettingsText(idleCompanions.get(companionId), companionId, shownLetters,
					minLetters, maxLetters, rounds, "texting");
			games.put(newGameId, new Game(newGameSettings));
			idleCompanions.remove(companionId);
			return new StringResponseEntity(newGameId, HttpStatus.OK);
		} else {
			LOG.info("Id is invalid: " + companionId);
			return new StringResponseEntity("", HttpStatus.FORBIDDEN);
		}

	}

	private String getCompanionId() {
		return getCookie(COOKIE_COMPANION_ID);
	}

	private String getGameId() {
		return getCookie(COOKIE_GAME_ID);
	}

	private <T> Stream<T> stream(T[] ary) {
		if (ary == null) {
			return Stream.empty();
		}
		return Arrays.stream(ary);
	}

	private String getCookie(String name) {
		return stream(request.getCookies()).filter(cookie -> name.equals(cookie.getName())).map(Cookie::getValue)
				.findAny().orElse(null);
	}

	/**
	 * one can only participate in one game at a time, to participate it is
	 * necessary to be logged in, when the game has already started it's not
	 * possible to enter anymore
	 * 
	 * @return true if companion exists and is idle and game exists, else false
	 */
	@RequestMapping(value = "/game/enter", method = RequestMethod.GET)
	public boolean enterGame() {
		String companionId = getCompanionId();
		String gameId = getGameId();
		if (games.containsKey(gameId) && idleCompanions.containsKey(companionId)) {
			if (games.get(gameId).getStatus() == 0) {
				games.get(gameId).getSettings().addPlayer(companionId, idleCompanions.get(companionId));
				idleCompanions.remove(companionId);
				return true;
			}
		}
		return false;
	}

	/**
	 * if one leaves a game it is possible to make or enter a new game again, the
	 * game owner can't leave the game if it hasn't started yet, he has to shut it
	 * then
	 * 
	 * @return true if the player was in the game and has left now, else false
	 */
	@RequestMapping(value = "/game/leave", method = RequestMethod.GET)
	public boolean leaveGame() {
		String companionId = getCompanionId();
		String gameId = getGameId();
		if (games.containsKey(gameId)) {
			if (games.get(gameId).getStatus() == 0
					&& games.get(gameId).getSettings().getPlayers().containsKey(companionId)
					&& !games.get(gameId).getSettings().getOwnerId().equals(companionId)) {
				Map<String, String> returnedCompanions = games.get(gameId).getSettings().removePlayer(companionId);
				idleCompanions.putAll(returnedCompanions);
				return true;
			} else if (games.get(gameId).getStatus() > 0) {
				Map<String, String> returnedCompanions = games.get(gameId).removeFromRunningGame(companionId);
				idleCompanions.putAll(returnedCompanions);
				if (games.get(gameId).getSettings().isEmpty()) {
					games.remove(gameId);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * only the game owner can shut his game, it also can only be shut, if it hasn't
	 * started yet all participants will be removed from the game and can
	 * participate in a new game
	 * 
	 * @return true if game was shut successfully, else false
	 */
	@RequestMapping(value = "/game/shut", method = RequestMethod.GET)
	public boolean shutGame() {
		String ownerId = getCompanionId();
		String gameId = getGameId();
		if (games.containsKey(gameId)) {
			if (games.get(gameId).getSettings().getOwnerId().equals(ownerId) && games.get(gameId).getStatus() == 0) {
				Map<String, String> returnedCompanions = games.get(gameId).getSettings().removePlayer(ownerId);
				games.remove(gameId);
				idleCompanions.putAll(returnedCompanions);
				return true;
			}
		}
		return false;
	}

	/**
	 * only the game owner can start his game, once started, nobody can enter the
	 * game any longer
	 * 
	 * @return true if game started successfully, else false
	 */
	@RequestMapping(value = "/game/start", method = RequestMethod.GET)
	public boolean startGame() {
		String gameId = getGameId();
		String ownerId = getCompanionId();
		if (games.containsKey(gameId)) {
			if (games.get(gameId).getSettings().getOwnerId().equals(ownerId) && games.get(gameId).getStatus() == 0) {
				games.get(gameId).start();
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * A story piece can only be committed, when the game is in status 1 (started
	 * and not ended yet) Only the player who's turn it is can commit a story piece
	 * 
	 * @param storyPiece
	 * @return true if commitment was successful, else false
	 */
	@RequestMapping(value = "/game/commit/storypiece", method = RequestMethod.GET)
	public boolean commitStoryPiece(@RequestParam("storypiece") String storyPiece) {
		String companionId = getCompanionId();
		String gameId = getGameId();
		if (games.get(gameId) instanceof GameText) {
			return ((GameText) games.get(gameId)).commitStoryPiece(companionId, storyPiece);
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/games/unstarted/poll", method = RequestMethod.GET)
	public List<GameTeaser> games() {
		List<GameTeaser> gameTeasers = new LinkedList<>();

		for (String key : games.keySet()) {
			if (games.get(key).getStatus() == 0) {
				gameTeasers.add(new GameTeaser(key, games.get(key).getSettings().getName(),
						games.get(key).getSettings().getPlayers().size()));
			}
		}
		return gameTeasers;
	}

	/**
	 * get full information about the game that the requesting companion is in
	 * if requesting companion is not in a game, return HttpStatus.FORBIDDEN
	 * 
	 * @return
	 */
	@RequestMapping(value = "/game/status/poll", method = RequestMethod.GET)
	public ResponseEntity<Object> gameStatus() {
		String companionId = getCompanionId();
		
		for(String key : games.keySet()) {
			if(games.get(key).getSettings().getPlayers().containsKey(companionId)) {
				if (games.get(key) instanceof GameText) {
					return new ResponseEntity<Object>(new GameStatus((GameText) games.get(key), companionId), HttpStatus.OK);
				}
			}
		}
		LOG.info("Player not in game.\n Player: " + companionId);
		return new ResponseEntity<Object>("", HttpStatus.FORBIDDEN);

	}

}
