package com.framework.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:9005", maxAge = 3600)
@RestController
@RequestMapping("/api/open")
public class AccountController {

	 @RequestMapping(value = "login", produces = "application/json;charset=UTF-8")
	public  String retrieve() {
		 Map<String,Object>map = new HashMap<String,Object>();
		 map.put("code",1);
		return map.toString();
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public void remove(@PathVariable Long id) {
		// ...
	}
}