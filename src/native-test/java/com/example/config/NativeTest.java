package com.example.config;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.NativeImageTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

@NativeImageTest
@QuarkusTestResource(CommonConfigOverridesResource.class)
public class NativeTest {

	@BeforeAll
	static void setUpBeforeAll() {
		RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
	}

	@Test
	void shouldOverridePropertyInNativeTest() {
		var propertyValue = ConfigProvider.getConfig().getValue("property.to.override", Integer.class);
		// overridden property in test
		assertEquals(99, propertyValue);

		//but in main code it is still the default value = 0
		given()
				.when().get("/demo")
				.then()
				.statusCode(200)
				.body(not("property.to.override: 99"))
				.body(is("property.to.override: 0"));
	}

}
