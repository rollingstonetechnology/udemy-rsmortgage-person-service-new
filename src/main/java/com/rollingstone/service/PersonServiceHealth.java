package com.rollingstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * This is an optional class used to inject application specific health check
 * into the Spring Boot health management endpoint.
 */
@Component
public class PersonServiceHealth implements HealthIndicator {
	
	@Autowired
	private ServiceProperties configuration;

	@Override
	public Health health() {
		return Health.up().withDetail("details",
				"{ 'internals' : 'getting close to limit', 'profile' : '" + this.configuration.getName() + "' }" +  this.configuration.getDescription())
				.status("itsok!").build();
	}
}
