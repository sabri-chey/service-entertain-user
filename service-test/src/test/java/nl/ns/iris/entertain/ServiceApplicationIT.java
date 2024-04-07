package nl.ns.iris.entertain;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Future;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@Testcontainers
@DisplayName("ServiceApplicationIT")
public class ServiceApplicationIT {

    static final int APP_INTERNAL_PORT = 8080;
    final Network network = Network.newNetwork();
    static final Future<String> appImage;
    static {
        var appDir = Optional
                .of(new File("../app"))
                .filter(dir -> dir.exists() && dir.isDirectory())
                .orElse(new File("app"));

        var dockerFile = new File(appDir, "../Dockerfile");

        appImage = new ImageFromDockerfile("nl.ns.iris.entertain/service-entertain-user:latest", false)
                .withFileFromFile(".", appDir)
                .withFileFromFile("Dockerfile", dockerFile);
    }
    
    @Container
    final GenericContainer<?> wiremock = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:2.31.0"))
            .withNetworkAliases("wiremock")
            .withNetwork(network)
            .withFileSystemBind("../wiremock/output", "/home/wiremock/mappings")
            .withFileSystemBind("../wiremock/input", "/home/wiremock/__files")
            .waitingFor(Wait.forHttp("/__admin/docs/")
                    .withStartupTimeout(Duration.ofMinutes(5)));
    
    @Container
    final GenericContainer<?> app = new GenericContainer<>(appImage)
            .dependsOn(wiremock)
            .withNetwork(network)
            .withNetworkAliases("app")
            .withExposedPorts(APP_INTERNAL_PORT)
            .waitingFor(Wait.forLogMessage(".*Profile prod activated.*", 1));
    
    @Test
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
