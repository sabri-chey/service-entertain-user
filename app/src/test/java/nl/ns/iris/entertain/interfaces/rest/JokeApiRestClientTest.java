package nl.ns.iris.entertain.interfaces.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RestClientTest({JokeApiRestClient.class})
class JokeApiRestClientTest {

    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private JokeApiRestClient externalJokeRestClient;

    @Test
    void shouldGetJokeFromApi() throws IOException {

        server.expect(MockRestRequestMatchers.requestTo("http://localhost:8080/jokes"))
                .andRespond(MockRestResponseCreators.withSuccess(readTestData(), MediaType.APPLICATION_JSON));

        var joke = externalJokeRestClient.fetchExternalJoke();
        assertThat(joke.getAmount()).isEqualTo(10);
        assertThat(joke.getJokes().size()).isEqualTo(6);
        assertThat(joke.getJokes().get(0).getJoke()).isEqualTo("Explicit joke!");
    }


    @Test
    void shouldHandleError() throws IOException {

        server.expect(MockRestRequestMatchers.requestTo("http://localhost:8080/jokes"))
                .andRespond(MockRestResponseCreators.withServerError());

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> externalJokeRestClient.fetchExternalJoke(),
                "Expected fetchExternalJoke() to throw, but it didn't"
        );

        assertThat(thrown.getMessage()).isEqualTo("Error while fetching external joke!");
    }

    private String readTestData() throws IOException {
        return new String(getClass().getClassLoader().getResourceAsStream("request_message.json").readAllBytes());
    }

}
    