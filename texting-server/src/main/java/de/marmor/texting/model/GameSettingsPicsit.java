package de.marmor.texting.model;

public class GameSettingsPicsit extends GameSettings {
	
	private int picSetSize;
	private int nofCardsOnHand;
	private boolean typeTitle;
	
	public GameSettingsPicsit(String name, String companionId, int pics, int nofCardsOnHand, boolean typeTitle) {
		super(name, companionId,"Picsit");
		this.picSetSize = pics;
		this.nofCardsOnHand = nofCardsOnHand;
		this.typeTitle = typeTitle;
	}

	public /*List<String>*/int getPicSetSize() {
		return picSetSize;
	}
	
	public int getNofCardsOnHand() {
		return nofCardsOnHand;
	}
	
	public boolean getTypeTitle() {
		return typeTitle;
	}
}
