package de.marmor.texting.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mbuerger on 16.12.2017.
 */
@RestController
public class Session {

	private static final Logger LOG = LoggerFactory.getLogger(Session.class);

	private Set<String> companions = new HashSet<String>();

	@RequestMapping(value = "/game/companion",  method = RequestMethod.GET)
	public Set<String> get() {
		return companions;
	}

	@RequestMapping(value = "/game/companion",  method = RequestMethod.PUT)
	public String end(@RequestParam("name") String name) {
		companions.add(name);
		return String.format("Hallo, %s", name);
	}
}
