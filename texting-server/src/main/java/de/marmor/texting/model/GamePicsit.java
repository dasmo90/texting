package de.marmor.texting.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GamePicsit extends Game {
	
	private GameSettingsPicsit settingsPicsit;
	private Map<String, Player> players = new HashMap<>();
	private Map<Integer,String> middle = new HashMap<>(); // die Karte ist der Key, und der player der Value
	private List<Integer> pileOfCards = new LinkedList<>();
	private int phase = -1; // -1: not started, 0: putDown, 1: choose
	private List<String> playersInOrder = new LinkedList<>();
	
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
	
	public boolean allPlayersReadyWith(int phase) {
		for(String playerKey : players.keySet()) {
			if(players.get(playerKey).getPhase() == phase) {
				return false;
			}
		}
		return true;
	}
	
	public boolean putAPicDown(String playerKey, int card) {
		if (status != 1) return false;
		if (phase != 0) return false;
		if (players.get(playerKey).putAPicDown(card)) {
			middle.put(card,playerKey);
			if(allPlayersReadyWith(0)) {
				phase = 1;
			}
			return true;
		}
		return false;
	}
	
	public boolean pickAPic(String playerKey, int card) {
		if (status != 1) return false;
		// Card must be in the middle, player can't pick himself, pick phase
		if (middle.containsKey(card) && !middle.get(card).equals(playerKey) && phase == 1) {
			Player pickedPlayer = players.get(middle.get(card));
			if (players.get(playerKey).pickAPic(pickedPlayer)) {
				if (allPlayersReadyWith(1)) {
					distributePoints();
					prepareNextRound();
				}
				return true;
			}
		}
		return false;
	}

	private void distributePoints() {
		Player whoseTurnPlayer = players.get(playersInOrder.get(whoseTurn));
		// everyone picked the right card
		if (whoseTurnPlayer.collectedPicks() == 0 || whoseTurnPlayer.collectedPicks() == nofPlayers-1) {
			for (String playerKey : players.keySet()) {
				players.get(playerKey).addToScore(2);
			}
			whoseTurnPlayer.addToScore(-2);
			
		} else { // not everyone picked the right card
			whoseTurnPlayer.addToScore(3);
			
			for (String playerKey : players.keySet()) {
				Player p = players.get(playerKey);
				if (!p.getMyTurn()) {
					if (p.getPickedCorrectly()) {
						p.addToScore(3);
					}
					p.addToScore(p.collectedPicks());
				}
			}
		}
	}
	
	private void prepareNextRound() {
		phase = 0;
		// set whose turn it is now
		players.get(playersInOrder.get(whoseTurn)).setMyTurn(false);
		whoseTurn = (whoseTurn+1)%nofPlayers;
		players.get(playersInOrder.get(whoseTurn)).setMyTurn(true);
		
		for (int i = 0; i < nofPlayers; i++) {
			Player p = players.get(playersInOrder.get(i));
			if (!p.drawCard(pileOfCards)) {
				end();
				
			} else {
				p.reset();
			}
			
		}
	}
	
	@Override
	public void start() {
		int cardsNeeded = settingsPicsit.getNofCardsOnHand()*settingsPicsit.getPlayers().size();
		
		if (status == 0 && cardsNeeded <= settingsPicsit.getPicSetSize()) {
			status = 1;
			phase = 0;
			
			currentRound = 1;
			nofPlayers = settingsPicsit.getPlayers().size();
			
			for (int i = 0; i < settingsPicsit.getPicSetSize(); i++) {
				pileOfCards.add(Integer.valueOf(i));
			}
			Collections.shuffle(pileOfCards);
			
			Integer[] order = new Integer[nofPlayers];
			for (int i = 0; i < nofPlayers; i++) {
				order[i] = i;
			}
			Collections.shuffle(Arrays.asList(order));

			List<String> p = new LinkedList<String>();
			for (String key : settingsPicsit.getPlayers().keySet()) {
				p.add(key);
				players.put(key, new Player());
			}
			for (int i = 0; i < nofPlayers; i++) {
				String playerKey = p.get(order[i]);
				playersInOrder.add(playerKey);
				for (int j = 0; j < settingsPicsit.getNofCardsOnHand(); j++) {
					players.get(playerKey).drawCard(pileOfCards);
				}
			}
			players.get(playersInOrder.get(0)).setTurn(true);
			settingsPicsit.forgetOwner();
		}
	}

	@Override
	public String whoseTurnId() {
		return playersInOrder.get(whoseTurn);
	}

	@Override
	public List<String> getPlayersInOrder() {
		return playersInOrder;
	}

	@Override
	public Map<String, String> removeFromRunningGame(String companionId) {
		Map<String, String> playersThatLeft = new HashMap<String, String>();
		if (status != 0) {
			if (playersInOrder.contains(companionId)) {
				nofPlayers--;
				players.remove(companionId);
				int index = playersInOrder.indexOf(companionId);
				playersInOrder.remove(companionId);
				if (whoseTurn > index) {
					whoseTurn--;
				} else if(whoseTurn == nofPlayers) {
					whoseTurn = 0;
				}
				return settingsPicsit.removePlayer(companionId);
			}
		}
		return playersThatLeft;
	}
	

}
