package com.jumkid.garage;

import com.jumkid.garage.config.EnableTestContainers;
import com.jumkid.garage.config.TestObjectsProperties;
import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.util.CloneUtil;
import com.jumkid.share.util.DateTimeUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableTestContainers
@TestPropertySource(value = {"classpath:application.share.properties"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GarageProfileAPITests {

	@LocalServerPort
	private int port;

	@Value("${com.jumkid.jwt.test.user-token}")
	private String testUserToken;
	@Value("${com.jumkid.jwt.test.user-id}")
	private String testUserId;
	@Autowired
	private TestObjectsProperties testObjectsProperties;

	@BeforeAll
	void setup() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	@DisplayName("Get garage profile by id")
	@Order(1)
	void whenGivenId_shouldGetGarageProfile() throws InterruptedException {
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
						"garageLocations[0].coordinate", notNullValue());
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
							"garageLocations[0].coordinate", notNullValue());
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
	@MethodSource("getValidGarageProfile")
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
							"garageLocations[0].coordinate", notNullValue());
	}

	@ParameterizedTest
	@DisplayName("Save garage profile anonymously - Forbidden")
	@Order(11)
	@MethodSource("getValidGarageProfile")
	void anonymous_whenSaveGarageProfile_shouldGetForbidden(GarageProfile garageProfile) {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.body(garageProfile)
					.contentType(ContentType.JSON)
				.when()
					.post("/garages")
				.then()
					.log().all()
					.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@ParameterizedTest
	@DisplayName("Save garage profile with exist display name - Conflict")
	@Order(12)
	@MethodSource("getValidGarageProfile")
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

	private Stream<Arguments> getValidGarageProfile() {
		GarageProfile test1 = CloneUtil.clone(testObjectsProperties.getGarageProfile());
		assert test1 != null;
		return List.of(test1).stream().map(Arguments::of);
	}

	@ParameterizedTest
	@DisplayName("Save invalid garage profile - Bad Request")
	@Order(13)
	@MethodSource("getInvalidGarageProfiles")
	void whenGivenInvalidGarageProfile_shouldGet400(GarageProfile garageProfile) {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.headers("Authorization", "Bearer " + testUserToken)
					.body(garageProfile)
					.contentType(ContentType.JSON)
				.when()
					.post("/garages")
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	private Stream<Arguments> getInvalidGarageProfiles() {
		GarageProfile seed = testObjectsProperties.getGarageProfile();

		GarageProfile test1 = CloneUtil.clone(seed);
		assert test1 != null;
		test1.setDisplayName(null);

		GarageProfile test2 = CloneUtil.clone(seed);
		assert test2 != null;
		test2.setDisplayName("a");

		GarageProfile test3 = CloneUtil.clone(seed);
		assert test3 != null;
		test3.getGarageLocations().get(0).setPhoneNumber("abc");

		return List.of(test1, test2, test3).stream().map(Arguments::of);
	}

	@ParameterizedTest
	@DisplayName("Update partial garage profile")
	@MethodSource("getPartialGarageProfiles")
	@Order(20)
	void whenGivenPartialGarageProfile_shouldUpdate(GarageProfile partialGarageProfile){
		long garageProfileId = 1;
		Response response = RestAssured
							.given()
								.baseUri("http://localhost:" + port)
								.headers("Authorization", "Bearer " + testUserToken)
								.contentType(ContentType.JSON)
							.when()
								.get("/garages/"+garageProfileId)
							.then()
								.statusCode(HttpStatus.OK.value())
							.extract()
								.response();

		partialGarageProfile.setModifiedOn(DateTimeUtils.stringToLocalDatetime(response.path("modifiedOn")));
		if (partialGarageProfile.getGarageLocations() != null) {
			partialGarageProfile.getGarageLocations().get(0)
					.setModifiedOn(DateTimeUtils.stringToLocalDatetime(response.path("garageLocations[0].modifiedOn")));
		}

		RestAssured
			.given()
				.baseUri("http://localhost:" + port)
				.headers("Authorization", "Bearer " + testUserToken)
				.body(partialGarageProfile)
				.contentType(ContentType.JSON)
			.when()
				.put("/garages/"+garageProfileId)
			.then()
				.log().all()
				.statusCode(HttpStatus.ACCEPTED.value())
				.body("displayName", equalTo(partialGarageProfile.getDisplayName()));
	}

	private Stream<Arguments> getPartialGarageProfiles() {
		List<GarageProfile> partialGarageProfiles = testObjectsProperties.getPartialGarageProfiles();
		return partialGarageProfiles.stream().map(Arguments::of);
	}

	@ParameterizedTest
	@DisplayName("Update partial garage profile as anonymous user - Forbidden")
	@MethodSource("getValidGarageProfile")
	@Order(21)
	void anonymous_whenUpdateGarageProfile_shouldGet403(GarageProfile partialGarageProfile){
		long garageProfileId = 1;
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.body(partialGarageProfile)
					.contentType(ContentType.JSON)
				.when()
					.put("/garages/"+garageProfileId)
				.then()
					.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@ParameterizedTest
	@DisplayName("Update garage profile with existing display name - Conflict")
	@MethodSource("getValidGarageProfile")
	@Order(22)
	void whenUpdateGarageProfileWithSameDisplayName_shouldGet409(GarageProfile garageProfile){
		long garageProfileId = 1;
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.headers("Authorization", "Bearer " + testUserToken)
					.body(garageProfile)
					.contentType(ContentType.JSON)
				.when()
					.put("/garages/"+garageProfileId)
				.then()
					.statusCode(HttpStatus.CONFLICT.value());
	}

	@RepeatedTest(value = 2, failureThreshold = 1)
	@DisplayName("Delete garage profile")
	@Order(30)
	void whenGivenGarageProfileId_shouldDelete() {
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
				.when()
					.delete("/garages/1")
				.then()
					.statusCode(HttpStatus.NO_CONTENT.value());
	}

	@Test
	@DisplayName("Delete garage profile with invalid id - No Content")
	@Order(31)
	void whenGivenInvalidGarageProfileId_shouldGet400() {
		long garageProfileId = 100L;
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.headers("Authorization", "Bearer " + testUserToken)
					.contentType(ContentType.JSON)
				.when()
					.delete("/garages/"+garageProfileId)
				.then()
					.statusCode(HttpStatus.NO_CONTENT.value());
	}

	@Test
	@DisplayName("Delete garage profile with invalid id - Forbidden")
	@Order(31)
	void anonymous_whenDeleteGarageProfile_shouldGet403() {
		long garageProfileId = 100L;
		RestAssured
				.given()
					.baseUri("http://localhost:" + port)
					.contentType(ContentType.JSON)
				.when()
					.delete("/garages/"+garageProfileId)
				.then()
					.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@AfterAll
	static void cleanUp() {
		//void
	}
}
