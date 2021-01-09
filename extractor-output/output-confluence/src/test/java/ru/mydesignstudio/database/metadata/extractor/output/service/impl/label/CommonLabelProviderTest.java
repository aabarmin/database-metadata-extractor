package ru.mydesignstudio.database.metadata.extractor.output.service.impl.label;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.mydesignstudio.database.metadata.extractor.output.Label;
import ru.mydesignstudio.database.metadata.extractor.output.provider.CommonLabelProvider;

@SpringJUnitConfig(classes = {
    CommonLabelProvider.class
})
@TestPropertySource(properties = {
    "output.target=confluence",
    "confluence.label.common.values=label1,label2,label3",
    "confluence.label.common.prefix=global"
})
class CommonLabelProviderTest {
  @Autowired
  private CommonLabelProvider unitUnderTest;

  @Test
  void check_contextStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void provide_shouldReturnThree() {
    final Collection<Label> labels = unitUnderTest
        .provide();

    assertAll(
        () -> assertNotNull(labels),
        () -> assertEquals(3, labels.size()),
        () -> {
          final String prefix = labels.stream()
              .map(Label::getPrefix)
              .distinct()
              .findAny()
              .get();

          assertEquals("global", prefix);
        }
    );
  }
}