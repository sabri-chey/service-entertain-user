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
    private static final String WIREMOCK_IMAGE = "wiremock/wiremock:2.31.0";
    private static final String WIREMOCK_ALIAS = "wiremock";
    private static final String ADMIN_DOCS_ENDPOINT = "/__admin/docs/";
    private static final int STARTUP_TIMEOUT_MINUTES = 5;

    static final Future<String> appImage;

    static {
        var dockerFile = new File("../Dockerfile");

        appImage = new ImageFromDockerfile("nl.ns.iris.entertain/service-entertain-user:latest", false)
                .withFileFromFile(".", dockerFile.getParentFile())
                .withFileFromFile("Dockerfile", dockerFile);
    }

    @Container
    final GenericContainer<?> wiremock = new GenericContainer<>(DockerImageName.parse(WIREMOCK_IMAGE))
            .withNetworkAliases(WIREMOCK_ALIAS)
            .withNetwork(network)
            .waitingFor(Wait.forHttp(ADMIN_DOCS_ENDPOINT)
                    .withStartupTimeout(Duration.ofMinutes(STARTUP_TIMEOUT_MINUTES)));

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
