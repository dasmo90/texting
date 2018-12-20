package de.marmor.texting.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by mbuerger on 18.12.2017.
 */
public class GameSettingsText extends GameSettings{

	private static final Logger LOG = LoggerFactory.getLogger(GameSettingsText.class);
	private int nofShownLetters;
	private int nofMinLetters;
	private int nofMaxLetters;
	private int nofRounds;
	
	public GameSettingsText(String name, String companionId, int show, int wrMin, int wrMax, int rounds) {
		// default settings if parameters are invalid
		super(name, companionId);
		
		if(show < 0) {
			show = 50;
		}
		if(wrMin < show) {
			wrMin = show;
		}
		if(wrMax < wrMin) {
			wrMax = wrMin+10;
		}
		if(rounds <= 0) {
			rounds = 1;
		}
		
		nofShownLetters = show;
		nofMinLetters = wrMin;
		nofMaxLetters = wrMax;
		nofRounds = rounds;
	}
	
	public int getMinLetters() {
		return nofMinLetters;
	}
	
	public int getMaxLetters() {
		return nofMaxLetters;
	}
	
	public int getShownLetters() {
		return nofShownLetters;
	}
	
	public int getRounds() {
		return nofRounds;
	}

}
