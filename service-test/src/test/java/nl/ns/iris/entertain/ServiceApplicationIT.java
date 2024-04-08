package nl.ns.iris.entertain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.Future;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@Testcontainers
@DisplayName("ServiceApplicationIT")
@Profile("test")
public class ServiceApplicationIT {

    static final int APP_INTERNAL_PORT = 8080;
    final Network network = Network.newNetwork();
    static final Future<String> appImage;

    static {
        var dockerFile = new File("../Dockerfile");

        appImage = new ImageFromDockerfile("nl.ns.iris.entertain/service-entertain-user:latest", false)
                .withFileFromFile(".", dockerFile.getParentFile())
                .withFileFromFile("Dockerfile", dockerFile);
    }

    @Container
    final GenericContainer<?> wiremock = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:2.31.0"))
            .withNetworkAliases("wiremock")
            .withNetwork(network)
            .withFileSystemBind("../wiremock/input", "/home/wiremock/mappings")
            .waitingFor(Wait.forHttp("/__admin/docs/")
                    .withStartupTimeout(Duration.ofMinutes(5)));

    @Container
    final GenericContainer<?> app = new GenericContainer<>(appImage)
            .dependsOn(wiremock)
            .withNetwork(network)

            .withNetworkAliases("app")
            .withExposedPorts(APP_INTERNAL_PORT)
            .waitingFor(Wait.forLogMessage(".*Tomcat started on port.*", 1));


    @BeforeEach
    void setUp() {
        wiremock.start();
        app.start();

    }

    @AfterEach
    void tearDown() {
        app.stop();
        wiremock.close();
    }

    //    TODO: Run Container with profile 'test'
    @Test
    void shouldReturn200WhenNoJokesFount() {
        RestClient restClient = RestClient.create();

        restClient.get().uri("http://localhost" + APP_INTERNAL_PORT + "/V1/entertain/getJoke")
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(String.class);

    }

}
