package ru.mydesignstudio.database.metadata.extractor.output.service.impl.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfluenceParams {
  private final String space;
  private final int parentPageId;
  private final String confluenceHost;
  private final int confluencePort;
  private final String confluenceProtocol;
  private final String confluenceType;
  private final String username;
  private final String password;
}
