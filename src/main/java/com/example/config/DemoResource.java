package com.example.config;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/demo")
public class DemoResource {

	@Inject
	DemoService demoService;

	@GET
	public String getCountryByName() {
		return "property.to.override: " + demoService.readPropertyValue();
	}

}
