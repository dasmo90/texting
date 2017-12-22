package de.marmor.texting.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameTest {

	@Test
	public void testStoryPieceAdd() {
		GameSettings settings = new GameSettings("bob", "1", 5, 6, 12,1);
		settings.addPlayer("2", "fu");
		
		Game game = new Game(settings);
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
}
