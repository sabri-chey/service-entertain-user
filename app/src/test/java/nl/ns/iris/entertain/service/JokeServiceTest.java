package nl.ns.iris.entertain.service;

import nl.ns.iris.entertain.TestDataFactory;
import nl.ns.iris.entertain.interfaces.rest.JokeApiRestClient;
import nl.ns.iris.entertain.mapper.JokeMapper;
import nl.ns.iris.entertain.model.ExternalJoke;
import nl.ns.iris.entertain.model.Joke;
import nl.ns.iris.entertain.model.JokeOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class JokeServiceTest {

    @Mock
    private JokeApiRestClient externalJokeClient;
    @Mock
    JokeMapper jokeMapper;

    @InjectMocks
    JokeService jokeService;

    @Test
    void shouldHandleRequest()  {
        ExternalJoke externalJokeWithSixJokes = TestDataFactory.createExternalJoke();
        when(externalJokeClient.fetchExternalJoke()).thenReturn(externalJokeWithSixJokes);
        when(jokeMapper.map(any(Joke.class))).thenReturn(JokeOutput.builder().build());
        Optional<JokeOutput> result = jokeService.getRandomJokes();
        assertThat(result).isNotEmpty();
        verify(externalJokeClient).fetchExternalJoke();
        verify(jokeMapper, times(1)).map(any(Joke.class));
    }
    @Test
    void shouldNotReturnUnsafeJoke(){
        ExternalJoke externalJokeWithSixJokes = TestDataFactory.createExternalJoke();
        
    }

    @Test
    void shouldReturnEmptyIfNothingFounded()  {
        when(externalJokeClient.fetchExternalJoke()).thenReturn(null);
        Optional<JokeOutput> result = jokeService.getRandomJokes();
        assertThat(result.isEmpty()).isTrue();
        verify(jokeMapper, times(0)).map(any(Joke.class));
    }
   
}