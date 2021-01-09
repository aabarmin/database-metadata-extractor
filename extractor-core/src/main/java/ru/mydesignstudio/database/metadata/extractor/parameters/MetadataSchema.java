package ru.mydesignstudio.database.metadata.extractor.parameters;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class MetadataSchema {
  private String name;
  private List<MetadataTable> tables = Lists.newArrayList();
  private List<String> labels = Lists.newArrayList();
}
