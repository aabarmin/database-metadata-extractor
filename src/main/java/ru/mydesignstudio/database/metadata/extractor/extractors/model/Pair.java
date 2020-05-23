package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class Pair<K, V> {
  private final K key;
  private final V value;
}
