package ru.mydesignstudio.database.metadata.extractor.parameters;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class MetadataTable {
  private String name;
  private List<String> labels = Lists.newArrayList();
}
