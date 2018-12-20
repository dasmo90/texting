package de.marmor.texting.rest;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marmor.texting.model.Game;
import de.marmor.texting.model.GameText;
import de.marmor.texting.model.StoryPiece;

public class GameStatus {

	private static final Logger LOG = LoggerFactory.getLogger(GameStatus.class);

	protected int status;
	protected boolean yourTurn;
	protected int whosTurnIndex;
	protected List<String> playerNames = new LinkedList<>();
	protected boolean myGame;

	public GameStatus() {
		
	}
	
	public GameStatus(Game game, String currentPlayerId) {
		status = game.getStatus();
		// once the game started, nobody will be the owner so myGame is always false
		myGame = game.getSettings().getOwnerId().equals(currentPlayerId);
		
		if(status == 0) {
			yourTurn = false;
			whosTurnIndex = -1;
			LOG.info(String.valueOf(game.getSettings().getPlayers().keySet().size()));
			for(String key : game.getSettings().getPlayers().keySet()) {
				LOG.info(key);
				playerNames.add(game.getSettings().getPlayers().get(key));
			}	
		} else if(status == 1) {
			yourTurn = Objects.equals(game.whoseTurnId(), currentPlayerId);
			whosTurnIndex = game.getWhoseTurn();
			for(int i = 0; i < game.getPlayersInOrder().size(); i++) {
				playerNames.add(game.getSettings().getPlayers().get(game.getPlayersInOrder().get(i)));
			}
		} else {
			yourTurn = false;
			whosTurnIndex = -1;
			for(int i = 0; i < game.getPlayersInOrder().size(); i++) {
				playerNames.add(game.getSettings().getPlayers().get(game.getPlayersInOrder().get(i)));
			}
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isYourTurn() {
		return yourTurn;
	}

	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}

	public int getWhosTurnIndex() {
		return whosTurnIndex;
	}

	public void setWhosTurnIndex(int whosTurnIndex) {
		this.whosTurnIndex = whosTurnIndex;
	}

	public List<String> getPlayerNames() {
		return playerNames;
	}

	public void setPlayerNames(List<String> playerNames) {
		this.playerNames = playerNames;
	}

	public boolean isMyGame() {
		return myGame;
	}

	public void setMyGame(boolean myGame) {
		this.myGame = myGame;
	}
}
