package nl.ns.iris.entertain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Joke {

    long id;
    String category;
    String type;
    String joke;
    Flags flags;
    boolean safe;
    String lang;

    @JsonCreator
    public Joke(@JsonProperty("id") long id,
                @JsonProperty("category") String category,
                @JsonProperty("type") String type,
                @JsonProperty("joke") String joke,
                @JsonProperty("flags") Flags flags,
                @JsonProperty("safe") boolean safe,
                @JsonProperty("lang") String lang) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.joke = joke;
        this.flags = flags;
        this.safe = safe;
        this.lang = lang;
    }
}
