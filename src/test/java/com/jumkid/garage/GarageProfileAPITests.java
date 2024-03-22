package com.jumkid.garage;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableTestContainers
@TestPropertySource("/application.share.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GarageProfileAPITests {

	@LocalServerPort
	private int port;

	@Value("${com.jumkid.jwt.test.user-token}")
	private String testUserToken;
	@Value("${com.jumkid.jwt.test.user-id}")
	private String testUserId;

	@BeforeAll
	static void setup() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Test
	@DisplayName("Get garage profile by id")
	@Order(1)
	void whenGivenId_shouldGetGarageProfile() {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
				.when()
					.get("/garages/1")
				.then()
					.log().all()
					.statusCode(HttpStatus.OK.value())
					.body("displayName", equalTo("TestGarage"),
							"garageLocations[0]", notNullValue(),
							"garageLocations[0].point", notNullValue());
	}

	@Test
	@DisplayName("Get garage profile without id - Forbidden")
	@Order(2)
	void whenGivenNoId_shouldGetForbidden() {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
				.when()
					.get("/garages")
				.then()
					.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@AfterAll
	static void cleanUp() {
		//void
	}
}
