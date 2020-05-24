package ru.mydesignstudio.database.metadata.extractor.output.html.plant.uml;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.database.metadata.extractor.extractors.column.ColumnModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.fk.ForeignKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.pk.PrimaryKeyModel;

@ExtendWith(MockitoExtension.class)
class PlantUmlMarkupGeneratorTest {
  @InjectMocks
  private PlantUmlMarkupGenerator unitUnderTest;

  @Test
  void generate_shouldStartWithStartUml() {
    final String output = unitUnderTest.generate(Collections.emptyList(), Collections.emptyList());

    assertThat(output).startsWith("@startuml");
  }

  @Test
  void generate_shouldContainHideCircle() {
    final String output = unitUnderTest.generate(Collections.emptyList(), Collections.emptyList());

    assertThat(output).contains("hide circle");
  }

  @Test
  void generate_shouldContainlinetype() {
    final String output = unitUnderTest.generate(Collections.emptyList(), Collections.emptyList());

    assertThat(output).contains("skinparam linetype ortho");
  }

  @Test
  void generate_shouldEndWithEndUml() {
    final String output = unitUnderTest.generate(Collections.emptyList(), Collections.emptyList());

    assertThat(output).endsWith("@enduml");
  }

  @Test
  void generate_shouldDisplayEntity() {
    final PrimaryKeyModel primaryKey = new PrimaryKeyModel();
    primaryKey.setColumns("id_field");
    primaryKey.setPrimaryKey("PK");

    final ColumnModel primaryKeyColumn = new ColumnModel();
    primaryKeyColumn.setColumnName("id_field");
    primaryKeyColumn.setDataType("number");
    primaryKeyColumn.setDataLength(10);

    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName("Table Name");
    metadata.setPrimaryKeys(Collections.singletonList(primaryKey));
    metadata.setForeignKeys(Collections.emptyList());
    metadata.setColumns(Arrays.asList(primaryKeyColumn));

    final String output = unitUnderTest.generate(Collections.emptyList(), Collections.singletonList(metadata));

    assertThat(output).contains("entity Table Name {");
    assertThat(output).contains("* id_field : number(10) <<PK>>");
    assertThat(output).contains("}");
  }

  @Test
  void generate_shouldDisplayManyPK() {
    final PrimaryKeyModel primaryKey = new PrimaryKeyModel();
    primaryKey.setColumns("id_field1, id_field2");
    primaryKey.setPrimaryKey("PK");

    final ColumnModel primaryKeyColumn1 = new ColumnModel();
    primaryKeyColumn1.setColumnName("id_field1");
    primaryKeyColumn1.setDataType("number");
    primaryKeyColumn1.setDataLength(10);

    final ColumnModel primaryKeyColumn2 = new ColumnModel();
    primaryKeyColumn2.setColumnName("id_field2");
    primaryKeyColumn2.setDataType("string");
    primaryKeyColumn2.setDataLength(255);

    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName("Table Name");
    metadata.setPrimaryKeys(Collections.singletonList(primaryKey));
    metadata.setForeignKeys(Collections.emptyList());
    metadata.setColumns(Arrays.asList(primaryKeyColumn1, primaryKeyColumn2));

    final String output = unitUnderTest.generate(Collections.emptyList(), Collections.singletonList(metadata));

    assertThat(output).contains("entity Table Name {");
    assertThat(output).contains("* id_field1 : number(10) <<PK>>");
    assertThat(output).contains("* id_field2 : string(255) <<PK>>");
    assertThat(output).contains("}");
  }

  @Test
  void generate_shoulddisplayForeignKeys() {
    final ForeignKeyModel foreignKey = new ForeignKeyModel();
    foreignKey.setColumns("referenced_id1, referenced_id2");
    foreignKey.setForeignKey("FK");

    final ColumnModel foreigKeyColumn1 = new ColumnModel();
    foreigKeyColumn1.setColumnName("referenced_id1");
    foreigKeyColumn1.setDataType("number");
    foreigKeyColumn1.setDataLength(10);

    final ColumnModel foreigKeyColumn2 = new ColumnModel();
    foreigKeyColumn2.setColumnName("referenced_id2");
    foreigKeyColumn2.setDataType("string");
    foreigKeyColumn2.setDataLength(255);

    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName("Table Name");
    metadata.setPrimaryKeys(Collections.emptyList());
    metadata.setForeignKeys(Collections.singletonList(foreignKey));
    metadata.setColumns(Arrays.asList(foreigKeyColumn1, foreigKeyColumn2));

    final String output = unitUnderTest.generate(Collections.emptyList(), Collections.singletonList(metadata));

    assertThat(output).contains("entity Table Name {" + System.lineSeparator());
    assertThat(output).contains("--"+ System.lineSeparator());
    assertThat(output).contains("* referenced_id1 : number(10) <<FK>>");
    assertThat(output).contains("* referenced_id2 : string(255) <<FK>>");
    assertThat(output).contains("}");
  }

  @Test
  void generate_shouldDisplayOtherColumns() {
    final ColumnModel column = new ColumnModel();
    column.setColumnName("non_key_field");
    column.setDataType("DATE");
    column.setDataLength(7);

    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName("Table Name");
    metadata.setPrimaryKeys(Collections.emptyList());
    metadata.setForeignKeys(Collections.emptyList());
    metadata.setColumns(Arrays.asList(column));

    final String output = unitUnderTest.generate(Collections.emptyList(), Collections.singletonList(metadata));

    assertThat(output).contains("entity Table Name {" + System.lineSeparator());
    assertThat(output).contains("--"+ System.lineSeparator());
    assertThat(output).contains("non_key_field : DATE(7)");
    assertThat(output).contains("}");
  }

  @Test
  void generate_shouldNotDisplayDuplicateValues() {
    final PrimaryKeyModel primaryKey = new PrimaryKeyModel();
    primaryKey.setColumns("id_field1");
    primaryKey.setPrimaryKey("PK");

    final ColumnModel primaryKeyColumn1 = new ColumnModel();
    primaryKeyColumn1.setColumnName("id_field1");
    primaryKeyColumn1.setDataType("number");
    primaryKeyColumn1.setDataLength(10);

    final ForeignKeyModel foreignKey = new ForeignKeyModel();
    foreignKey.setColumns("referenced_id1");
    foreignKey.setForeignKey("FK");

    final ColumnModel foreigKeyColumn1 = new ColumnModel();
    foreigKeyColumn1.setColumnName("referenced_id1");
    foreigKeyColumn1.setDataType("number");
    foreigKeyColumn1.setDataLength(10);

    final ColumnModel column1 = new ColumnModel();
    column1.setColumnName("id_field1");
    column1.setDataType("number");
    column1.setDataLength(10);

    final ColumnModel column2 = new ColumnModel();
    column2.setColumnName("referenced_id1");
    column2.setDataType("number");
    column2.setDataLength(10);

    final ColumnModel column3 = new ColumnModel();
    column3.setColumnName("non_key_field");
    column3.setDataType("DATE");
    column3.setDataLength(7);

    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName("Table Name");
    metadata.setPrimaryKeys(Collections.singletonList(primaryKey));
    metadata.setForeignKeys(Collections.singletonList(foreignKey));
    metadata.setColumns(Arrays.asList(column1, column2, column3));

    final String output = unitUnderTest.generate(Collections.emptyList(), Collections.singletonList(metadata));

    assertThat(output).contains("entity Table Name {" + System.lineSeparator());
    assertThat(output).contains("* id_field1 : number(10) <<PK>>");
    assertThat(output).contains("--"+ System.lineSeparator());
    assertThat(output).contains("* referenced_id1 : number(10) <<FK>>");
    assertThat(output).contains("non_key_field : DATE(7)");
    assertThat(output).contains("}");
  }
}
