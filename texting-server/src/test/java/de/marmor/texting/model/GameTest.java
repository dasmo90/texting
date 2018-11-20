package de.marmor.texting.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marmor.texting.rest.GameStatus;


public class GameTest {

	private static final Logger LOG = LoggerFactory.getLogger(GameTest.class);
	
	@Test
	public void testStoryPieceAdd() {
		GameSettings settings = new GameSettings("bob", "1", 5, 6, 12,1);
		Game game = new Game(settings);
		game.getSettings().addPlayer("2", "fu");
		
		game.start();
		assertEquals(1, game.getCurrentRound());
		boolean ok = game.commitStoryPiece("1", "Hallo, du!");
		game.getShownLetters();
		if(!ok) {
			game.commitStoryPiece("2", "Jo jo jo!");
			game.getShownLetters();
			assertEquals(1, game.getCurrentRound());
			game.commitStoryPiece("1", "Hallo, du!");
			game.getShownLetters();
			assertEquals(2, game.getCurrentRound());
			game.getStory();
		} else {
			game.commitStoryPiece("2", "Jo jo jo!");
			game.getShownLetters();
			game.getStory();
			assertEquals(2, game.getCurrentRound());
		}
		
	}
	
	@Test
	public void testLeaveEnterBeforeGame() {
		GameSettings settings = new GameSettings("bob", "1", 5, 6, 12,1);
		Game game = new Game(settings);
		game.getSettings().addPlayer("2", "alice");
		game.getSettings().addPlayer("3", "fu");
		game.getSettings().addPlayer("4", "fara");
		
		assertEquals(4,game.getSettings().getPlayers().size());
		
		game.getSettings().removePlayer("3");
		assertEquals(3,game.getSettings().getPlayers().size());
		
		game.getSettings().addPlayer("2", "alice2");
		assertEquals(3,game.getSettings().getPlayers().size());
		
		game.getSettings().removePlayer("1");
		assertEquals(0,game.getSettings().getPlayers().size());
	}
	
	@Test
	public void testLeaveEnterInGame() {
		GameSettings settings = new GameSettings("bob", "1", 5, 6, 12,1);
		Game game = new Game(settings);
		game.getSettings().addPlayer("2", "alice");
		game.getSettings().addPlayer("3", "fu");
		game.getSettings().addPlayer("4", "fara");
		
		game.start();
		assertEquals(4,game.getSettings().getPlayers().size());
		assertEquals(4,game.getPlayersInOrder().size());
		
		String order = "";
		for(int i = 0; i < 4; i++) {
			order = order + String.valueOf(game.getPlayersInOrder().get(i));
		}
		LOG.info(order);
		
		game.commitStoryPiece(game.whoseTurnId(), "abcdef");
		game.commitStoryPiece(game.whoseTurnId(), "123456789");
		
		LOG.info(String.valueOf(game.whoseTurnId())+" am Zug");
		
		game.removeFromRunningGame("2");
		assertEquals(3,game.getSettings().getPlayers().size());
		assertEquals(3,game.getPlayersInOrder().size());
		
		order = "";
		for(int i = 0; i < 3; i++) {
			order = order + String.valueOf(game.getPlayersInOrder().get(i));
		}
		LOG.info(order);
		LOG.info(String.valueOf(game.whoseTurnId())+" am Zug");
		
		game.removeFromRunningGame("1");
		assertEquals(2,game.getSettings().getPlayers().size());
		assertEquals(2,game.getPlayersInOrder().size());
		
		order = "";
		for(int i = 0; i < 2; i++) {
			order = order + String.valueOf(game.getPlayersInOrder().get(i));
		}
		LOG.info(order);
		LOG.info(String.valueOf(game.whoseTurnId())+" am Zug");
	}
	
	@Test
	public void myGameTest() {
		GameSettings settings = new GameSettings("bob", "1", 5, 6, 12,1);
		Game game = new Game(settings);
		game.getSettings().addPlayer("2", "alice");
		game.getSettings().addPlayer("3", "fu");
		game.getSettings().addPlayer("4", "fara");
		
		GameStatus gameStatusBob1 = new GameStatus(game,"1");
		GameStatus gameStatusAlice1 = new GameStatus(game,"2");
		
		assertTrue(gameStatusBob1.isMyGame());
		assertTrue(!gameStatusAlice1.isMyGame());
		
		game.start();
		
		GameStatus gameStatusBob2 = new GameStatus(game,"1");
		GameStatus gameStatusAlice2 = new GameStatus(game,"2");
		
		assertTrue(!gameStatusBob2.isMyGame());
		assertTrue(!gameStatusAlice2.isMyGame());
	}
	
	@Test
	public void whoseTurnIndex() {
		GameSettings settings = new GameSettings("bob", "1", 5, 6, 12,1);
		Game game = new Game(settings);
		game.getSettings().addPlayer("2", "alice");
		game.getSettings().addPlayer("3", "fu");
		game.getSettings().addPlayer("4", "fara");
		
		game.start();
		
		GameStatus gameStatus1 = new GameStatus(game,"1");
	}
}
