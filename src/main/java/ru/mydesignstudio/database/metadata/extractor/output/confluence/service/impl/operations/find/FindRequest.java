package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find;

import lombok.Data;

@Data
public class FindRequest {
  private String title;
  private String spaceKey;
  private String expand = "history";
}
