package ru.mydesignstudio.database.metadata.extractor.database.column;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import ru.mydesignstudio.database.metadata.extractor.extract.result.ColumnModel;

class ColumnModelTest {

  @Test
  void columnModel_addUniqueByColumnId() {
    final Set<ColumnModel> models = new HashSet<>();
    models.add(createColumnModel(1));
    models.add(createColumnModel(1));

    assertAll(
        () -> assertNotNull(models),
        () -> assertThat(models).hasSize(1)
    );
  }

  private ColumnModel createColumnModel(int columnId) {
    final ColumnModel model = new ColumnModel();
    model.setColumnId(columnId);
    model.setColumnName(RandomStringUtils.randomAlphabetic(10));
    model.setComments(RandomStringUtils.random(10));
    return model;
  }
}