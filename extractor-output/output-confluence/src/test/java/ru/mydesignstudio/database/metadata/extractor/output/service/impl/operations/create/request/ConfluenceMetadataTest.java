package ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceMetadata;

class ConfluenceMetadataTest {

  @Test
  void shouldHaveDefaultValues() {
    final ConfluenceMetadata metadata = ConfluenceMetadata.builder().build();

    assertAll(
        () -> assertNotNull(metadata.getLabels()),
        () -> assertTrue(metadata.getLabels().isEmpty())
    );
  }
}