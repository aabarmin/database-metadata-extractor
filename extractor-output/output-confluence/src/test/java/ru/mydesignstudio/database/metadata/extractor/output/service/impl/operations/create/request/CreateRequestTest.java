package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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