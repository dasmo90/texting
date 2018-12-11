package de.marmor.texting.model;

import java.util.HashMap;
import java.util.Map;

public class GameSettingsPicsit extends GameSettings {

	private Map<String, String> picSet = new HashMap<String,String>();
	
	public GameSettingsPicsit(String name, String companionId, String gameType, Map<String,String> pics) {
		super(name, companionId, gameType);
		this.picSet = pics;
	}

}
