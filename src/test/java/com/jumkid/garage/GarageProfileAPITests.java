package com.jumkid.garage;

import com.jumkid.garage.service.dto.GarageProfile;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
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
	@DisplayName("Get garage profile by id as anonymous user")
	@Order(2)
	void anonymous_whenGivenId_shouldGetGarageProfile() {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
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
	@DisplayName("Get garage profile by invalid id - NO_CONTENT")
	@Order(3)
	void whenGivenInvalidId_shouldGetNO_CONTENT() {
		RestAssured
				.given()
				.baseUri("http://localhost:" + port)
				.headers("Authorization", "Bearer " + testUserToken)
				.contentType(ContentType.JSON)
				.when()
				.get("/garages/-1")
				.then()
				.log().all()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}

	@Test
	@DisplayName("Get garage profile without id - 404")
	@Order(4)
	void whenGivenNoId_shouldResponse404() {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
				.when()
					.get("/garages")
				.then()
					.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@ParameterizedTest
	@DisplayName("Save garage profile data")
	@Order(10)
	@ArgumentsSource(GarageProfileArgumentsProvider.class)
	void whenGiveGarageProfile_shouldSave(GarageProfile garageProfile) {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.headers("Authorization", "Bearer " + testUserToken)
					.body(garageProfile)
					.contentType(ContentType.JSON)
				.when()
					.post("/garages")
				.then()
					.log().all()
					.statusCode(HttpStatus.CREATED.value())
					.body("id", notNullValue(),
							"modifiedOn", notNullValue(),
							"garageLocations[0]", notNullValue(),
							"garageLocations[0].point", notNullValue(),
							"garageLocations[0].point.x", equalTo((float)garageProfile.getGarageLocations().get(0).getPoint().getX()));
	}

	@ParameterizedTest
	@DisplayName("Save garage profile anonymously - Forbidden")
	@Order(11)
	@ArgumentsSource(GarageProfileArgumentsProvider.class)
	void anonymous_whenGiveGarageProfile_shouldGetForbidden(GarageProfile garageProfile) {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.body(garageProfile)
					.contentType(ContentType.JSON)
				.when()
					.post("/garages")
				.then()
					.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@ParameterizedTest
	@DisplayName("Save garage profile with exist display name - Conflict")
	@Order(12)
	@ArgumentsSource(GarageProfileArgumentsProvider.class)
	void whenGivenGarageProfileWithExistDisplayName_shouldGetConflict(GarageProfile garageProfile) {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.headers("Authorization", "Bearer " + testUserToken)
					.body(garageProfile)
					.contentType(ContentType.JSON)
				.when()
					.post("/garages")
				.then()
					.statusCode(HttpStatus.CONFLICT.value());
	}

	@AfterAll
	static void cleanUp() {
		//void
	}
}
