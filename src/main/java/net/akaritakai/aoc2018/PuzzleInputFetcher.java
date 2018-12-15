package net.akaritakai.aoc2018;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


class PuzzleInputFetcher {

  private static final PuzzleInputFetcher INSTANCE = new PuzzleInputFetcher();

  private final String _sessionToken;
  private final Map<Integer, String> _cachedPuzzles = new ConcurrentHashMap<>();

  private PuzzleInputFetcher() {
    _sessionToken = getSessionData();
  }

  public static String getPuzzleInput(final int day) {
    try {
      // If puzzle input is stored in memory, return it
      if (INSTANCE._cachedPuzzles.containsKey(day)) {
        return INSTANCE._cachedPuzzles.get(day);
      }

      // If puzzle input is locally stored, place it in memory, and return it
      Files.createDirectories(Path.of("puzzle"));
      var localPuzzle = Path.of("puzzle/" + day);
      if (localPuzzle.toFile().exists()) {
        final var puzzle = Files.readString(localPuzzle);
        INSTANCE._cachedPuzzles.put(day, puzzle);
        return puzzle;
      }

      // Otherwise, fetch it from the Advent of Code website
      final var client = HttpClient.newHttpClient();
      final var request = HttpRequest.newBuilder()
          .uri(URI.create("https://adventofcode.com/2018/day/" + day + "/input"))
          .header("cookie", "session=" + INSTANCE._sessionToken)
          .GET()
          .build();
      final var puzzle = client.send(request, BodyHandlers.ofString()).body();

      // Store the puzzle locally, and place it in memory
      Files.writeString(localPuzzle, puzzle);
      INSTANCE._cachedPuzzles.put(day, puzzle);

      return puzzle;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Couldn't get puzzle input for day " + day);
    }
  }

  private static String getSessionData() {
    try {
      return Files.readString(Path.of("cookie.txt")).trim();
    } catch (IOException e) {
      throw new RuntimeException("Couldn't get session data from cookie.txt");
    }
  }
}
