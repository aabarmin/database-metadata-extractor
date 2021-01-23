package ru.mydesignstudio.database.metadata.extractor.output;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TypeModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ObjectTypeExtractorTest {
  @InjectMocks
  private ObjectTypeExtractor unitUnderTest;

  @Test
  void extract_exceptionInCaseOfNull() {
    assertThrows(NullPointerException.class, () -> unitUnderTest.extract(null));
  }

  @Test
  void extract_shouldBeUnknownInCaseOfEmptyTypes() {
    assertEquals("Unknown", unitUnderTest.extract(TableMetadata.create()));
  }

  @Test
  void extract_shouldExtractValue() {
    final TypeModel typeModel = TypeModel.create("Value");
    final TableMetadata metadata = TableMetadata.create(typeModel);

    assertEquals("Value", unitUnderTest.extract(metadata));
  }
}