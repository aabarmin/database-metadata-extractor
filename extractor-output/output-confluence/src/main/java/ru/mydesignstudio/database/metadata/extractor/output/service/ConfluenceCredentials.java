package ru.mydesignstudio.database.metadata.extractor.output.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ConfluenceCredentials {
  private final String username;
  private final String password;
}
