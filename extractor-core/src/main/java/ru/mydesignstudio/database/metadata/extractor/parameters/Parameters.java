package ru.mydesignstudio.database.metadata.extractor.parameters;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import lombok.Data;

@Data
public class Parameters {
  private List<MetadataSource> sources = Lists.newArrayList();
}
