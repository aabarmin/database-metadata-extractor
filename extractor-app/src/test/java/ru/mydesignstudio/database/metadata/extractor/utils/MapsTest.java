package ru.mydesignstudio.database.metadata.extractor.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MapsTest {

  @Test
  void of_emptyMap() {
    final Map<Object, Object> map = Maps.of();

    assertAll(
        () -> assertNotNull(map),
        () -> assertTrue(map.isEmpty()),
        () -> assertThat(map).isInstanceOf(HashMap.class)
    );
  }

  @Test
  void of_evenNumberOfArguments() {
    assertThrows(IllegalArgumentException.class, () -> Maps.of(1));
  }

  @Test
  void of_oddNumberOfArguments() {
    final Map<Object, Object> map = Maps.of(
        "first", 1,
        "second", 2
    );

    assertAll(
        () -> assertNotNull(map),
        () -> assertThat(map).hasSize(2)
    );
  }
}