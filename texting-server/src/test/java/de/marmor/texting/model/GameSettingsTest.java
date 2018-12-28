package de.marmor.texting.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GameSettingsTest {

	@Test
	public void testGameName() {
		GameSettings gameSettings = new GameSettingsText("bob", "1", 5, 6, 12,3);

		assertEquals("bob's game (Texting)", gameSettings.getName());
		
	}

	@Test
	public void testPlayersCount() {
		GameSettings gameSettings = new GameSettingsText("bob", "1", 5, 6, 12,3);
		gameSettings.addPlayer("2", "Kasandra");
		assertEquals(2, gameSettings.getPlayers().size());
		assertEquals("Kasandra", gameSettings.getPlayers().get("2"));
		assertTrue(gameSettings.getPlayers().containsKey("1"));
	}
	
	@Test
	public void testGameNamePic() {
		GameSettings gameSettings = new GameSettingsPicsit("Carl", "1", 80, 6, true);
		assertEquals("Carl's game (Picsit)", gameSettings.getName());
		gameSettings.addPlayer("2", "Rumpumpel");
		assertEquals(2, gameSettings.getPlayers().size());
		assertEquals("Rumpumpel", gameSettings.getPlayers().get("2"));
		assertFalse(gameSettings.getPlayers().containsKey("0"));
	}
}
