package ru.mydesignstudio.database.metadata.extractor.utils;

import java.util.HashMap;
import java.util.Map;

public class Maps {
  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> of(Object... values) {
    if (values.length % 2 != 0) {
      throw new IllegalArgumentException("Invalid number of arguments");
    }
    final Map<K, V> result = new HashMap<>();

    for (int i = 0; i < values.length - 1; i = i + 2) {
      result.put((K) values[i], (V) values[i + 1]);
    }

    return result;
  }
}
