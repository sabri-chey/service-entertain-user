package nl.ns.iris.entertain.interfaces.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JokeControllerIT {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }


    @Test
    void shouldReturn404WhenNoJokesFount() {
        
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/entertain/getJoke")
                .then()
                .statusCode(404);

    }
//    @Test
//    void shouldReturnJoke() {
//
//        given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/v1/entertain/getJoke")
//                .then()
//                .statusCode(200);
//                .body(".", hasSize(1));
//    }
}
