package ru.mydesignstudio.database.metadata.extractor.output.confluence.service;

import org.springframework.lang.NonNull;

public interface ConfluenceFactory {
  Confluence create(@NonNull ConfluenceCredentials credentials);
}
