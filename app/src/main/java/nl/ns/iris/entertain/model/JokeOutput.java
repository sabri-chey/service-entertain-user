package nl.ns.iris.entertain.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JokeOutput {

    private long id;
    private String randomJoke;

    @JsonCreator
    public JokeOutput(@JsonProperty("id") long id, String randomJoke) {
        this.id = id;
        this.randomJoke = randomJoke;
    }
}
