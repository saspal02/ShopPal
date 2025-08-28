package com.saswat.ShopPal.inventory;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void ShouldExistBySkuCodeAndQuantityGreaterThanEqual( ) {
		var responseBodyString = RestAssured.given()
				.param("skuCode", "iphone_13")
				.param("quantity", 100)
				.when()
				.get("/api/inventory")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.body().asString();

		assertThat(responseBodyString, Matchers.is("true"));
	}

}
