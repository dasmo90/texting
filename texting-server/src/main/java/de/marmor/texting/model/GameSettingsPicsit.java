package de.marmor.texting.model;

public class GameSettingsPicsit extends GameSettings {
	
	private int picSetSize;
	private int nofCardsOnHand;
	
	public GameSettingsPicsit(String name, String companionId, int pics, int nofCardsOnHand) {
		super(name, companionId);
		this.picSetSize = pics;
		this.nofCardsOnHand = nofCardsOnHand;
	}

	public /*List<String>*/int getPicSetSize() {
		return picSetSize;
	}
	
	public int getNofCardsOnHand() {
		return nofCardsOnHand;
	}
}
