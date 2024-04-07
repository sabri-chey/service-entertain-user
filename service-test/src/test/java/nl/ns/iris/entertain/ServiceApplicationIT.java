package nl.ns.iris.entertain;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@Testcontainers
@DisplayName("ServiceApplicationIT")
public class ServiceApplicationIT {

    static final int APP_INTERNAL_PORT = 8080;
    final Network network = Network.newNetwork();

    
//    @Container
//    final GenericContainer<?> wiremock = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:2.31.0"))
//            .withNetworkAliases("wiremock")
//            .withNetwork(network)
//            .withFileSystemBind("../wiremock/output", "/home/wiremock/mappings")
//            .withFileSystemBind("../wiremock/input", "/home/wiremock/__files")
//            .waitingFor(Wait.forHttp("/__admin/docs/")
//                    .withStartupTimeout(Duration.ofMinutes(5)));
//    
//    @Container
//    final GenericContainer<?> app = new GenericContainer<>(null)
//            .dependsOn(wiremock)
//            .withNetwork(network)
//            .withNetworkAliases("app")
//            .withExposedPorts(APP_INTERNAL_PORT)
//            .waitingFor(Wait.forLogMessage(".*Profile prod activated.*", 1));
    
//    TODO 
//    @Test
    void shouldReturn404WhenNoJokesFount() {
        
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/entertain/getJoke")
                .then()
                .statusCode(404)
                .body(".",hasSize(1));
    }
    
    
}
