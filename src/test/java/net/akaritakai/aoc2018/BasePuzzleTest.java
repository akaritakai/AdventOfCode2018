package net.akaritakai.aoc2018;

import com.google.common.io.Resources;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@SuppressWarnings("UnstableApiUsage")
class BasePuzzleTest {

  private static final Map<Integer, String> _cachedPuzzles = new ConcurrentHashMap<>();

  String getStoredInput(final int day) throws Exception {
    if (!_cachedPuzzles.containsKey(day)) {
      final var url = Resources.getResource("puzzle/" + day);
      final var puzzle = Files.readString(Path.of(url.toURI()));
      _cachedPuzzles.put(day, puzzle);
    }
    return _cachedPuzzles.get(day);
  }
}
