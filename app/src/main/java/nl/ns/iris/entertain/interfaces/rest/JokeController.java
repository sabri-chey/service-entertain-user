package nl.ns.iris.entertain.interfaces.rest;

import lombok.extern.slf4j.Slf4j;
import nl.ns.iris.entertain.model.JokeOutput;
import nl.ns.iris.entertain.service.JokeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/V1/entertain", produces = "application/json")
@Slf4j
public class JokeController {

    private final JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping("/getJoke")
    public ResponseEntity<JokeOutput> getJoke() throws IOException {

        log.info("Got request for joke");
        Optional<JokeOutput> randomJokes = jokeService.getRandomJokes();
        return randomJokes.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
 