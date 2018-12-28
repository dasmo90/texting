package de.marmor.texting.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marmor.texting.rest.GameStatusText;

public class GamePicsit extends Game {
	
	private static final Logger LOG = LoggerFactory.getLogger(GamePicsit.class);
	
	private GameSettingsPicsit settingsPicsit;
	private Map<String, Player> players = new HashMap<>();
	private Map<Integer,String> middle = new HashMap<>(); // die Karte ist der Key, und der player der Value
	private List<Integer> pileOfCards = new LinkedList<>();
	private int phase = -1; // -1: not started, 0: putDown, 1: choose
	private List<String> playersInOrder = new LinkedList<>();
	private String title = "";
	private boolean typeTitle = false;
	
	
	public GamePicsit(GameSettingsPicsit settings) {
		super(settings);
		this.settingsPicsit = settings;
		this.typeTitle = settings.getTypeTitle();
	}

	public GameSettingsPicsit getSettings() {
		return settingsPicsit;
	}
	
	public Player getCertainPlayer(String playerKey) {
		return players.get(playerKey);
	}
	
	public int getPhase() {
		return phase;
	}
	
	private boolean allPlayersReadyWith(int phase) {
		for(String playerKey : players.keySet()) {
			if(players.get(playerKey).getPhase() == phase) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean decideTitleForPic(String playerKey, int card, String title) {
		if(!typeTitle) return false;
		if(status != 1 || phase != 0 || !playerKey.equals(playersInOrder.get(whoseTurn))) {
			return false;
		}
		if(players.get(playerKey).putAPicDown(card)) {
			LOG.info("Card has been put down.");
			middle.put(card,playerKey);
			this.title = title;
			return true;
		}
		return false;
	}
	
	public boolean putAPicDown(String playerKey, int card) {
		if (status != 1 || phase != 0) {
			return false;
		}
		
		// if a title has to be typed in, the player whose turn it is has to use
		// the method decideTitleForPic()
		if (typeTitle && playerKey.equals(playersInOrder.get(whoseTurn))) {
			return false;
		}
		// if the player whose turn it is hasn't put down a card yet
		// nobody else can put down a card
		if (!playerKey.equals(playersInOrder.get(whoseTurn)) 
				&& players.get(playersInOrder.get(whoseTurn)).getPhase() == 0) {
			return false;
		}
		
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
		title = "";
		
		for (int i = 0; i < nofPlayers; i++) {
			Player p = players.get(playersInOrder.get(i));
			if (!p.drawCard(pileOfCards)) {
				phase = -1;
				end();
				
			} else {
				p.reset();
			}
			
		}
	}
	
	@Override
	public void start() {
		int cardsNeeded = settingsPicsit.getNofCardsOnHand()*settingsPicsit.getPlayers().size();
		
		if (status == 0 && cardsNeeded <= settingsPicsit.getPicSetSize() && settingsPicsit.getPlayers().size() > 1) {
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
	
	public Map<String,Integer> getScores() {
		Map<String,Integer> scores = new HashMap<String,Integer>();
		
		for(String key : players.keySet()) {
			scores.put(key, players.get(key).getScore());
		}
		return scores;
	}
	
	public Map<String,Integer> getMiddle() {
		Map<String,Integer> mid = new HashMap<String,Integer>();
		
		for (Integer key : middle.keySet()) {
			mid.put(middle.get(key), key);
		}
		
		return mid;
	}
	
	public List<Integer> getPileOfCards() {
		return pileOfCards;
	}
	
	public String getTitle() {
		return title;
	}
}
