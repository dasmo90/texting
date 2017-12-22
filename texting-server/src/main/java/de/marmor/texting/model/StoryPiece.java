package de.marmor.texting.model;

public class StoryPiece {

	private String companionId;
	private String companionName;
	private String text;

	public StoryPiece(String id, String name, String text) {
		this.companionId = id;
		this.setCompanionName(name);
		this.text = text;
	}
	
	public String getCompanionId() {
		return companionId;
	}

	public void setCompanionId(String companionId) {
		this.companionId = companionId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCompanionName() {
		return companionName;
	}

	public void setCompanionName(String companionName) {
		this.companionName = companionName;
	}
}
