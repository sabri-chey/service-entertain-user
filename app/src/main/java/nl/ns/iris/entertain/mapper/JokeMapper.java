package nl.ns.iris.entertain.mapper;

import nl.ns.iris.entertain.model.Joke;
import nl.ns.iris.entertain.model.JokeOutput;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel="spring", builder=@Builder(disableBuilder=true))
public interface JokeMapper {
    
    @Mapping(target="randomJoke", source="joke")
    JokeOutput map(Joke joke);
    
}
