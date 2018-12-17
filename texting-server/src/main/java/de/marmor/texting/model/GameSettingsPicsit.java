package de.marmor.texting.model;

import java.util.LinkedList;
import java.util.List;

public class GameSettingsPicsit extends GameSettings {

	private List<String> picSet = new LinkedList<String>();
	private int nofCardsOnHand;
	
	public GameSettingsPicsit(String name, String companionId, String gameType, LinkedList<String> pics, int nofCardsOnHand) {
		super(name, companionId, gameType);
		this.picSet = pics;
		this.nofCardsOnHand = nofCardsOnHand;
	}

	public List<String> getPicSet() {
		return picSet;
	}
	
	public int getNofCardsOnHand() {
		return nofCardsOnHand;
	}
}
