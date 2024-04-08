package nl.ns.iris.entertain.interfaces.rest;

import lombok.extern.slf4j.Slf4j;
import nl.ns.iris.entertain.model.ExternalJoke;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class JokeApiRestClient {

    private final RestClient restClient;

    public JokeApiRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Value("${service-entertain-user.url.external.endpoint}")
    private String externalJokeUrl;

    public ExternalJoke fetchExternalJoke() {
        log.info("Fetching external joke from {}", externalJokeUrl);
        return restClient.get()
                .uri(externalJokeUrl)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(this::isErrorCode, (request, response) -> {
                    log.error("Error while fetching external joke! {}", response.getStatusCode());
                    throw new RuntimeException("Error while fetching external joke!");
                })
                .body(ExternalJoke.class);
    }

    private boolean isErrorCode(HttpStatusCode status) {
        return status.isError();
    }

}
