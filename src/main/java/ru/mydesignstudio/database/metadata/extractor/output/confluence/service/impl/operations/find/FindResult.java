package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find;

import lombok.Data;

@Data
public class FindResult {
  private String id;
  private String title;
  private String type;
  private String status;
  private FindResultVersion version;
}
