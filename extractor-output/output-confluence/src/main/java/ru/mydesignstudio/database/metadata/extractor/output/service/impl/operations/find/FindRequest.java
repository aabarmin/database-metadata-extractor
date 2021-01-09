package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find;

import lombok.Data;

@Data
public class FindRequest {
  private String title;
  private String spaceKey;
  private String expand = "history";
}
