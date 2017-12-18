package de.marmor.texting.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mbuerger on 16.12.2017.
 */
@RestController
public class Session {

	private static final Logger LOG = LoggerFactory.getLogger(Session.class);

	private static int companionCounter = 0;
	private static int gameCounter = 0;

	private Map<Integer, String> companions = new HashMap<>();

	@RequestMapping(value = "/game/enter",  method = RequestMethod.GET)
	public int enterTheGame(@RequestParam("name") String name) {
		int newCompanionId = companionCounter++;
		companions.put(newCompanionId, name);
		return newCompanionId;
	}

	@RequestMapping(value = "/game/new",  method = RequestMethod.GET)
	public int newGame(@RequestParam("companionId") int companionId, @RequestParam("numberOfWords") int numberOfWords) {
		// TODO implements
		return 0;
	}
}
