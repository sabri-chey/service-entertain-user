package nl.ns.iris.entertain;

import nl.ns.iris.entertain.model.ExternalJoke;
import nl.ns.iris.entertain.model.Flags;
import nl.ns.iris.entertain.model.Joke;

import java.util.ArrayList;
import java.util.List;

public class TestDataFactory {


    public static ExternalJoke createExternalJoke() {
        return ExternalJoke.builder()
                .jokes(createJokes())
                .amount(6)
                .error(false)
                .build();
    }

    private static List<Joke> createJokes() {
        List<Joke> jokes = new ArrayList<>();
        jokes.add(createJoke(1, "Safe", true, false, false, false, "Safe short Joke!"));
        jokes.add(createJoke(2, "Safe", true, false, false, false, "Safe very very long joke!"));
        jokes.add(createJoke(3, "unsafe", false, false, false, false, "Unsafe Joke!"));
        jokes.add(createJoke(4, "Racist", true, true, false, false, "Racist Joke!"));
        jokes.add(createJoke(5, "Sexist", true, false, true, false, "Sexist Joke!"));
        jokes.add(createJoke(6, "Explicit", true, false, false, true, "Explicit Joke!"));
        return jokes;
    }

    public static Joke createJoke(int id, String category, boolean safe, boolean isRacist, boolean isSexist, boolean isExplicit, String jokeText) {
        return Joke.builder()
                .id(id)
                .category(category)
                .joke(jokeText)
                .flags(Flags.builder()
                        .racist(isRacist)
                        .sexist(isSexist)
                        .explicit(isExplicit)
                        .build())
                .safe(safe)
                .build();
    }
}