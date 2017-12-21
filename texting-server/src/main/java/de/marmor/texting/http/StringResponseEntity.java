package de.marmor.texting.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by mbuerger on 21.12.2017.
 */
public class StringResponseEntity  extends ResponseEntity<String> {

	public StringResponseEntity(HttpStatus status) {
		super(status);
	}

	public StringResponseEntity(String body, HttpStatus status) {
		super("\"" + body + "\"", status);
	}
}
