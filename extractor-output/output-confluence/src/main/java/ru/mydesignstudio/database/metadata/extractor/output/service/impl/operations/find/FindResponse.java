package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find;

import lombok.Data;

import java.util.List;

@Data
public class FindResponse {
  private List<FindResult> results;
}
