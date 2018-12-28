
package de.marmor.texting.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marmor.texting.rest.GameStatus;
import de.marmor.texting.rest.GameStatusText;


public class GameTest {

	private static final Logger LOG = LoggerFactory.getLogger(GameTest.class);
	
	@Test
	public void testStoryPieceAdd() {
		GameSettingsText settings = new GameSettingsText("bob", "1", 5, 6, 12, 1);
		GameText game = new GameText(settings);
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
		GameSettingsText settings = new GameSettingsText("bob", "1", 5, 6, 12, 1);
		GameText game = new GameText(settings);
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
		GameSettingsText settings = new GameSettingsText("bob", "1", 5, 6, 12, 1);
		GameText game = new GameText(settings);
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
		GameSettingsText settings = new GameSettingsText("bob", "1", 5, 6, 12, 1);
		GameText game = new GameText(settings);
		game.getSettings().addPlayer("2", "alice");
		game.getSettings().addPlayer("3", "fu");
		game.getSettings().addPlayer("4", "fara");
		
		GameStatus gameStatusBob1 = new GameStatusText(game,"1");
		GameStatus gameStatusAlice1 = new GameStatusText(game,"2");
		
		assertTrue(gameStatusBob1.isMyGame());
		assertTrue(!gameStatusAlice1.isMyGame());
		
		game.start();
		
		GameStatus gameStatusBob2 = new GameStatusText(game,"1");
		GameStatus gameStatusAlice2 = new GameStatusText(game,"2");
		
		assertTrue(!gameStatusBob2.isMyGame());
		assertTrue(!gameStatusAlice2.isMyGame());
	}
	
	@Test
	public void whoseTurnIndex() {
		GameSettingsText settings = new GameSettingsText("bob", "1", 5, 6, 12, 1);
		GameText game = new GameText(settings);
		game.getSettings().addPlayer("2", "alice");
		game.getSettings().addPlayer("3", "fu");
		game.getSettings().addPlayer("4", "fara");
		
		game.start();
		
		GameStatus gameStatus1 = new GameStatusText(game,"1");
		
		assertTrue(gameStatus1.getWhosTurnIndex() == 0);
	}
	
	@Test
	public void testPicsitStart() {
		GameSettingsPicsit set = new GameSettingsPicsit("Carl", "1", 80, 6, true);
		GamePicsit game = new GamePicsit(set);
		game.getSettings().addPlayer("2", "Rumpumpel");
		game.getSettings().addPlayer("3", "Carla");
		game.getSettings().addPlayer("4", "Abraxas");
		
		game.start();
		assertEquals(80-(6*4),game.getPileOfCards().size());
		
		String turn = game.getPlayersInOrder().get(game.getWhoseTurn());
		
		List<String> playersWithoutTurn = new LinkedList<String>();
		
		for(int i = 0; i < game.getPlayersInOrder().size(); i++) {
			if(!game.getPlayersInOrder().get(i).equals(turn)) {
				playersWithoutTurn.add(game.getPlayersInOrder().get(i));
			}
		}
		
		int card = game.getCertainPlayer(turn).getFirstCardOnHand();
		
		int card0 = game.getCertainPlayer(playersWithoutTurn.get(0)).getFirstCardOnHand();
		int card1 = game.getCertainPlayer(playersWithoutTurn.get(1)).getFirstCardOnHand();
		int card2 = game.getCertainPlayer(playersWithoutTurn.get(2)).getFirstCardOnHand();
		
		assertEquals(0,game.getCertainPlayer(turn).getPhase());
		assertFalse(game.putAPicDown(turn,card));
		assertFalse(game.putAPicDown(playersWithoutTurn.get(0), card0));
		assertEquals(0,game.getCertainPlayer(turn).getPhase());
		assertFalse(game.decideTitleForPic(turn,card0,"CrazyShit"));
		assertEquals(0,game.getCertainPlayer(turn).getPhase());
		assertTrue(game.decideTitleForPic(turn, card,"CrazyShit"));
		assertEquals(2,game.getCertainPlayer(turn).getPhase());
		assertTrue(game.putAPicDown(playersWithoutTurn.get(0), card0));
		assertEquals(1,game.getCertainPlayer(playersWithoutTurn.get(0)).getPhase());
		assertEquals(0,game.getPhase());
		game.putAPicDown(playersWithoutTurn.get(1), card1);
		game.putAPicDown(playersWithoutTurn.get(2), card2);
		assertEquals(1,game.getPhase());
		
		assertFalse(game.pickAPic(turn, card0));
		assertFalse(game.pickAPic(playersWithoutTurn.get(0), card0));
		assertTrue(game.pickAPic(playersWithoutTurn.get(0), card));
		assertTrue(game.pickAPic(playersWithoutTurn.get(1), card));
		assertTrue(game.pickAPic(playersWithoutTurn.get(2), card));
		
		assertEquals(0,game.getCertainPlayer(turn).getScore());
		assertEquals(2,game.getCertainPlayer(playersWithoutTurn.get(0)).getScore());
		assertEquals(2,game.getCertainPlayer(playersWithoutTurn.get(1)).getScore());
		assertEquals(2,game.getCertainPlayer(playersWithoutTurn.get(2)).getScore());
		
	}
	
	@Test
	public void testPicsitScore() {
		GameSettingsPicsit set = new GameSettingsPicsit("Carl", "1", 80, 6, true);
		GamePicsit game = new GamePicsit(set);
		game.getSettings().addPlayer("2", "Rumpumpel");
		game.getSettings().addPlayer("3", "Carla");
		game.getSettings().addPlayer("4", "Abraxas");
		
		game.start();
		assertEquals(80-(6*4),game.getPileOfCards().size());
		
		String turn = game.getPlayersInOrder().get(game.getWhoseTurn());
		
		List<String> playersWithoutTurn = new LinkedList<String>();
		
		for(int i = 0; i < game.getPlayersInOrder().size(); i++) {
			if(!game.getPlayersInOrder().get(i).equals(turn)) {
				playersWithoutTurn.add(game.getPlayersInOrder().get(i));
			}
		}
		
		int card = game.getCertainPlayer(turn).getFirstCardOnHand();
		
		int card0 = game.getCertainPlayer(playersWithoutTurn.get(0)).getFirstCardOnHand();
		int card1 = game.getCertainPlayer(playersWithoutTurn.get(1)).getFirstCardOnHand();
		int card2 = game.getCertainPlayer(playersWithoutTurn.get(2)).getFirstCardOnHand();
		
		game.decideTitleForPic(turn, card,"CrazyShit");
		game.putAPicDown(playersWithoutTurn.get(0), card0);
		game.putAPicDown(playersWithoutTurn.get(1), card1);
		game.putAPicDown(playersWithoutTurn.get(2), card2);
		
		game.pickAPic(playersWithoutTurn.get(0), card1);
		game.pickAPic(playersWithoutTurn.get(1), card2);
		game.pickAPic(playersWithoutTurn.get(2), card0);
		
		assertEquals(0,game.getCertainPlayer(turn).getScore());
		assertEquals(2,game.getCertainPlayer(playersWithoutTurn.get(0)).getScore());
		assertEquals(2,game.getCertainPlayer(playersWithoutTurn.get(1)).getScore());
		assertEquals(2,game.getCertainPlayer(playersWithoutTurn.get(2)).getScore());
		
	}
	
	@Test
	public void testPicsitScore2() {
		GameSettingsPicsit set = new GameSettingsPicsit("Carl", "1", 80, 6, true);
		GamePicsit game = new GamePicsit(set);
		game.getSettings().addPlayer("2", "Rumpumpel");
		game.getSettings().addPlayer("3", "Carla");
		game.getSettings().addPlayer("4", "Abraxas");
		
		game.start();
		assertEquals(80-(6*4),game.getPileOfCards().size());
		
		String turn = game.getPlayersInOrder().get(game.getWhoseTurn());
		
		List<String> playersWithoutTurn = new LinkedList<String>();
		
		for(int i = 0; i < game.getPlayersInOrder().size(); i++) {
			if(!game.getPlayersInOrder().get(i).equals(turn)) {
				playersWithoutTurn.add(game.getPlayersInOrder().get(i));
			}
		}
		
		int card = game.getCertainPlayer(turn).getFirstCardOnHand();
		
		int card0 = game.getCertainPlayer(playersWithoutTurn.get(0)).getFirstCardOnHand();
		int card1 = game.getCertainPlayer(playersWithoutTurn.get(1)).getFirstCardOnHand();
		int card2 = game.getCertainPlayer(playersWithoutTurn.get(2)).getFirstCardOnHand();
		
		game.decideTitleForPic(turn, card,"CrazyShit");
		game.putAPicDown(playersWithoutTurn.get(0), card0);
		game.putAPicDown(playersWithoutTurn.get(1), card1);
		game.putAPicDown(playersWithoutTurn.get(2), card2);
		
		game.pickAPic(playersWithoutTurn.get(0), card);
		game.pickAPic(playersWithoutTurn.get(1), card0);
		game.pickAPic(playersWithoutTurn.get(2), card1);
		
		assertEquals(3,game.getCertainPlayer(turn).getScore());
		assertEquals(4,game.getCertainPlayer(playersWithoutTurn.get(0)).getScore());
		assertEquals(1,game.getCertainPlayer(playersWithoutTurn.get(1)).getScore());
		assertEquals(0,game.getCertainPlayer(playersWithoutTurn.get(2)).getScore());
		
	}
}
