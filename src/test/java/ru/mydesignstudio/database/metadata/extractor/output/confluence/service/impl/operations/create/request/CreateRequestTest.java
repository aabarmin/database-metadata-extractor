package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CreateRequestTest {

  @Test
  void build_shouldHaveEmptyLabels() {
    final CreateRequest request = CreateRequest.builder().build();

    assertAll(
        () -> assertNotNull(request),
        () -> assertNotNull(request.getLabels()),
        () -> assertTrue(request.getLabels().isEmpty())
    );
  }
}