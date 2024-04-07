package nl.ns.iris.entertain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Flags {
    
    boolean nsfw;
    boolean religious;
    boolean political;
    boolean racist;
    boolean sexist;
    boolean explicit;

    @JsonCreator
    public Flags(@JsonProperty("nsfw") boolean nsfw,
                 @JsonProperty("religious") boolean religious,
                 @JsonProperty("political") boolean political,
                 @JsonProperty("racist") boolean racist,
                 @JsonProperty("sexist") boolean sexist,
                 @JsonProperty("explicit") boolean explicit) {
        this.nsfw = nsfw;
        this.religious = religious;
        this.political = political;
        this.racist = racist;
        this.sexist = sexist;
        this.explicit = explicit;
    }
}
