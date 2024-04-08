package nl.ns.iris.entertain.mapper;


import nl.ns.iris.entertain.TestDataFactory;
import nl.ns.iris.entertain.model.Joke;
import nl.ns.iris.entertain.model.JokeOutput;
import org.mapstruct.factory.Mappers;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class JokeMapperTest {

    JokeMapper subject = Mappers.getMapper(JokeMapper.class);

    @Test
    public void shouldMap() {
        Joke externalJoke = TestDataFactory.createExternalJoke().getJokes().get(0);
        JokeOutput result = subject.map(externalJoke);

        assertThat(result.getId()).isEqualTo(externalJoke.getId());
        assertThat(result.getRandomJoke()).isEqualTo(externalJoke.getJoke());

    }

}