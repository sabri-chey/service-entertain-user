package nl.ns.iris.entertain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExternalJoke {
    
    List<Joke> jokes;
    boolean error;
    int amount;

    @JsonCreator
    public ExternalJoke(@JsonProperty("jokes") List<Joke> jokes, @JsonProperty("error") boolean error, @JsonProperty("amount") int amount) {
        this.jokes = jokes;
        this.error = error;
        this.amount = amount;
    }

}
