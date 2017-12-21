package de.marmor.texting.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GameSettingsTest {

	@Test
	public void testGameName() {
		GameSettings gameSettings = new GameSettings("bob", "1", 5, 6, 12,3);

		assertEquals("bob's game", gameSettings.getName());
	}

	@Test
	public void testPlayersCount() {
		GameSettings gameSettings = new GameSettings("bob", "1", 5, 6, 12,3);
		gameSettings.addPlayer("2", "Kasandra");
		assertEquals(2, gameSettings.getPlayers().size());
		assertEquals("Kasandra", gameSettings.getPlayers().get("2"));
		assertTrue(gameSettings.getPlayers().containsKey("1"));
	}
}
