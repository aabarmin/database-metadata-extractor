package ru.mydesignstudio.database.metadata.extractor.output;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TypeModel;
import ru.mydesignstudio.database.metadata.extractor.output.ObjectTypeExtractor;

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
    assertEquals("Unknown", unitUnderTest.extract(new TableMetadata()));
  }

  @Test
  void extract_shouldExtractValue() {
    final TableMetadata metadata = new TableMetadata();
    final TypeModel typeModel = new TypeModel();
    typeModel.setObjectType("Value");

    metadata.setTypes(Arrays.asList(typeModel));

    assertEquals("Value", unitUnderTest.extract(metadata));
  }
}