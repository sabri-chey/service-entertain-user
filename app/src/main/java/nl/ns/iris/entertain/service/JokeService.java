package nl.ns.iris.entertain.service;

import lombok.extern.slf4j.Slf4j;
import nl.ns.iris.entertain.interfaces.rest.JokeApiRestClient;
import nl.ns.iris.entertain.mapper.JokeMapper;
import nl.ns.iris.entertain.model.ExternalJoke;
import nl.ns.iris.entertain.model.Joke;
import nl.ns.iris.entertain.model.JokeOutput;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class JokeService {

    private final JokeApiRestClient externalJokeRestClient;
    private final JokeMapper jokeMapper;

    public JokeService(JokeApiRestClient externalJokeRestClient, JokeMapper jokeMapper) {
        this.externalJokeRestClient = externalJokeRestClient;
        this.jokeMapper = jokeMapper;
    }

    public Optional<JokeOutput> getRandomJokes() {
        ExternalJoke externalJoke = externalJokeRestClient.fetchExternalJoke();
        if (externalJoke != null ) {
            return externalJoke.getJokes().stream()
                    .filter(this::isSuitableJoke)
                    .min(this::compareJokeLength)
                    .map(jokeMapper::map);
        }
        log.warn("No External jokes found: {}", externalJoke);
        return Optional.empty();
    }

    private boolean isSuitableJoke(Joke joke) {
        log.info("validating joke!");
        return joke.isSafe()
                && !joke.getFlags().isSexist()
                && !joke.getFlags().isExplicit();
    }

    private int compareJokeLength(Joke joke1, Joke joke2) {
        log.info("comparing jokes!");
        return Integer.compare(joke1.getJoke().length(), joke2.getJoke().length());
    }
}
