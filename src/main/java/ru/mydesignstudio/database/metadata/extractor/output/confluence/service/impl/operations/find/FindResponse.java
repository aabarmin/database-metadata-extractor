package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find;

import java.util.List;
import lombok.Data;

@Data
public class FindResponse {
  private List<FindResult> results;
}
