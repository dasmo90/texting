package de.marmor.texting.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GamePicsit extends Game {
	
	private GameSettingsPicsit settingsPicsit;
	private List<Player> players = new LinkedList<>();
	
/*	private GameSettings settings;
	protected int status = 0; // 0: not started yet, 1:in game, 2: ended
	protected List<String> playersInOrder = new LinkedList<>();
	protected int nofPlayers = 0;
	protected int whoseTurn = 0;
	protected int currentRound = 0;*/
	
	public GamePicsit(GameSettingsPicsit settings) {
		super(settings);
		this.settingsPicsit = settings;
	}

	public GameSettingsPicsit getSettings() {
		return settingsPicsit;
	}

	@Override
	public void start() {
		if (status == 0) {
			status = 1;
			
			currentRound = 1;
			nofPlayers = settingsPicsit.getPlayers().size();
			Integer[] order = new Integer[nofPlayers];
			for (int i = 0; i < nofPlayers; i++) {
				order[i] = i;
			}
			Collections.shuffle(Arrays.asList(order));

			List<Player> p = new LinkedList<Player>();
			for (String key : settingsPicsit.getPlayers().keySet()) {
				p.add(new Player(key));
			}
			for (int i = 0; i < nofPlayers; i++) {
				players.add(p.get(order[i]));
			}
			settingsPicsit.forgetOwner();
		}
	}

	@Override
	public String whoseTurnId() {
		return players.get(whoseTurn).getKey();
	}

	@Override
	public List<String> getPlayersInOrder() {
		List<String> playerIds = new LinkedList<String>();
		for (int i = 0; i < nofPlayers; i++) {
			playerIds.add(players.get(i).getKey());
		}
		return playerIds;
	}

	@Override
	public Map<String, String> removeFromRunningGame(String companionId) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
