package net.akaritakai.aoc2018;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;

public class PuzzleInputFetcher {

    private static final PuzzleInputFetcher INSTANCE = new PuzzleInputFetcher();

    private final String _sessionToken;

    private PuzzleInputFetcher() {
        _sessionToken = getSessionData();
    }

    public static String getPuzzleInput(int day) {
        try {
            // If puzzle input is locally stored, return it
            Files.createDirectories(Path.of("puzzle"));
            var cachedPuzzle = Path.of("puzzle/" + day);
            if (cachedPuzzle.toFile().exists()) {
                return Files.readString(cachedPuzzle);
            }

            // Otherwise, fetch it
            final var client = HttpClient.newHttpClient();
            final var request = HttpRequest.newBuilder()
                    .uri(URI.create("https://adventofcode.com/2018/day/" + day + "/input"))
                    .header("cookie", "session=" + INSTANCE._sessionToken)
                    .GET()
                    .build();
            final var puzzle = client.send(request, BodyHandlers.ofString()).body();

            // Store the puzzle locally
            Files.writeString(cachedPuzzle, puzzle);

            return puzzle;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Couldn't get puzzle input for day " + day);
        }
    }

    private static String getSessionData() {
        try {
            return Files.readString(Path.of("cookie.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't get session data from cookie.txt");
        }
    }

}
