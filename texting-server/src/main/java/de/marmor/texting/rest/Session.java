package de.marmor.texting.rest;

import de.marmor.texting.http.StringResponseEntity;
import de.marmor.texting.model.Game;
import de.marmor.texting.model.GameSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by mbuerger on 16.12.2017.
 */
@RestController
@CrossOrigin(origins = "*")
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
	 * whoever makes a new game is the game owner, who is the only one allowed to
	 * start that game later on one can only participate in one game at a time to
	 * participate it is necessary to be logged in
	 * 
	 * @param shownWords
	 * @param minWords
	 * @param maxWords
	 * @return
	 */
	@RequestMapping(value = "/game/new", method = RequestMethod.GET)
	public ResponseEntity<String> newGame(@RequestParam("shownWords") int shownWords,
			@RequestParam("minWords") int minWords, @RequestParam("maxWords") int maxWords) {
		String companionId = getCompanionId();
		if (idleCompanions.containsKey(companionId)) {
			String newGameId = UUID.randomUUID().toString();
			GameSettings newGameSettings = new GameSettings(idleCompanions.get(companionId), companionId, shownWords,
					minWords, maxWords);
			games.put(newGameId, new Game(newGameSettings));
			idleCompanions.remove(companionId);
			return new ResponseEntity<>(newGameId, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
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
	 * one can only participate in one game at a time to participate it is necessary
	 * to be logged in
	 * 
	 * @return
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
	 * if one leaves a game it is possible to make or enter a new game again the
	 * game owner can't leave the game, he has to shut it
	 * 
	 * @return
	 */
	@RequestMapping(value = "/game/leave", method = RequestMethod.GET)
	public boolean leaveGame() {
		String companionId = getCompanionId();
		String gameId = getGameId();
		if (games.containsKey(gameId)) {
			if (games.get(gameId).getSettings().getPlayers().containsKey(companionId)
					&& !games.get(gameId).getSettings().getOwnerId().equals(companionId)) {
				Map<String, String> returnedCompanions = games.get(gameId).getSettings().removePlayer(companionId);
				idleCompanions.putAll(returnedCompanions);
				return true;
			}
		}
		return false;
	}

	/**
	 * only the game owner can shut his game all participants will be removed from
	 * the game and can participate in a new game
	 * 
	 * @return
	 */
	@RequestMapping(value = "/game/shut", method = RequestMethod.GET)
	public boolean shutGame() {
		String ownerId = getCompanionId();
		String gameId = getGameId();
		if (games.containsKey(gameId)) {
			if (games.get(gameId).getSettings().getOwnerId().equals(ownerId)) {
				Map<String, String> returnedCompanions = games.get(gameId).getSettings().removePlayer(ownerId);
				games.remove(gameId);
				idleCompanions.putAll(returnedCompanions);
				return true;
			}
		}
		return false;
	}

	/**
	 * only the game owner can start his game once started, nobody can enter the
	 * game any longer
	 */
	@RequestMapping(value = "/game/start", method = RequestMethod.GET)
	public void startGame() {
		String gameId = getGameId();
		String ownerId = getCompanionId();
		if (games.containsKey(gameId)) {
			if (games.get(gameId).getSettings().getOwnerId().equals(ownerId)
					&& !games.get(gameId).getSettings().isEmpty()) {
				games.get(gameId).start();
			}
		}
	}
	

	@RequestMapping(value = "/game/commit/storypiece", method = RequestMethod.GET)
	public boolean commitStoryPiece() {
		return false;
	}
	
	@RequestMapping(value = "/games/unstarted/poll", method = RequestMethod.GET)
	public List<GameTeaser> games() {
		return Collections.singletonList(new GameTeaser());
	}
	
	@RequestMapping(value = "/game/status/poll", method = RequestMethod.GET)
	public GameStatus gameStatus() {
		return new GameStatus();
	}

}
