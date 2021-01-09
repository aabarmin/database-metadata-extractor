package ru.mydesignstudio.database.metadata.extractor.output.provider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.mydesignstudio.database.metadata.extractor.output.Label;

@SpringJUnitConfig(classes = DatabaseLabelProvider.class)
@TestPropertySource(properties = {
    "confluence.label.database.prefix=global",
    "confluence.label.database.values="
})
class DatabaseLabelProviderTest {
  @Autowired
  private DatabaseLabelProvider unitUnderTest;

  @Test
  void check_contextStarts() {
    assertNotNull(unitUnderTest);
  }

  @Test
  void provide_shouldBeEmpty() {
    final Set<Label> labels = unitUnderTest.provide();

    assertAll(
        () -> assertNotNull(labels),
        () -> assertThat(labels, emptyCollectionOf(Label.class))
    );
  }
}