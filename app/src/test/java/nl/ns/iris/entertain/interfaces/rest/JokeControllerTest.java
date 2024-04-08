package nl.ns.iris.entertain.interfaces.rest;

import nl.ns.iris.entertain.service.JokeService;
import nl.ns.iris.entertain.mapper.JokeMapper;
import nl.ns.iris.entertain.model.JokeOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JokeControllerTest {

    @Mock
    JokeService jokeService;
    
    @Mock
    JokeMapper jokeMapper;

    @InjectMocks
    JokeController jokeController;

    @Test
    void shouldGetJoke() throws IOException {
        JokeOutput joke =  JokeOutput.builder()
                .id(1L)
                .randomJoke("someRandom Joke")
                .build();
        Mockito.when(jokeService.getRandomJokes()).thenReturn(Optional.of(joke));
        ResponseEntity<JokeOutput> result = jokeController.getJoke();
 
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(joke.getId());
        assertThat(result.getBody().getRandomJoke()).isEqualTo(joke.getRandomJoke());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void shouldHandleEmptyResults() throws IOException {
        Mockito.when(jokeService.getRandomJokes()).thenReturn(Optional.empty());
        ResponseEntity<JokeOutput> result = jokeController.getJoke();
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}