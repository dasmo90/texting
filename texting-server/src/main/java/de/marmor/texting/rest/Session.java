package de.marmor.texting.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mbuerger on 16.12.2017.
 */
@RestController
public class Session {

	@RequestMapping("/")
	public String test() {
		return "Hello World!";
	}
}
