package de.marmor.texting.rest;

public class GameTeaser {
	
	private String id;
	private String name;
	private int nofPlayers;
	
	public GameTeaser(String id, String name, int nofPlayers) {
		this.id = id;
		this.name = name;
		this.nofPlayers = nofPlayers;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPlayers() {
		return nofPlayers;
	}
	public void setPlayers(int players) {
		this.nofPlayers = players;
	}
	
	
}
