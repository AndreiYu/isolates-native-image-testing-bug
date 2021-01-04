package com.example.config;

import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

/**
 * Override common PROD profile configs that should be used during native-image tests
 */
public class CommonConfigOverridesResource implements QuarkusTestResourceLifecycleManager {

	@Override
	public Map<String, String> start() {
		return Map.of("property.to.override", String.valueOf(99));
	}

	@Override
	public void stop() {
		// do nothing
	}

	@Override
	public void init(Map<String, String> initArgs) {
		// do nothing
	}

}

