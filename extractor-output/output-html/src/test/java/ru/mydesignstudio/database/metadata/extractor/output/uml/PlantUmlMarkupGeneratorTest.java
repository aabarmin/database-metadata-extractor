package ru.mydesignstudio.database.metadata.extractor.output.uml;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.database.metadata.extractor.extract.result.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PlantUmlMarkupGeneratorTest {
  @InjectMocks
  private PlantUmlMarkupGenerator unitUnderTest;

  @Test
  void generate_shouldStartWithStartUml() {
    final String output = unitUnderTest.generate(Collections.emptyList());

    assertThat(output).startsWith("@startuml");
  }

  @Test
  void generate_shouldContainHideCircle() {
    final String output = unitUnderTest.generate(Collections.emptyList());

    assertThat(output).contains("hide circle");
  }

  @Test
  void generate_shouldContainlinetype() {
    final String output = unitUnderTest.generate(Collections.emptyList());

    assertThat(output).contains("skinparam linetype ortho");
  }

  @Test
  void generate_shouldEndWithEndUml() {
    final String output = unitUnderTest.generate(Collections.emptyList());

    assertThat(output).endsWith("@enduml");
  }

  private PrimaryKeyModel primaryKey(String columns) {
    return new PrimaryKeyModel(
        "PK",
        "schema_name",
        "constraint_name",
        "table_name",
        columns
    );
  }

  private ColumnModel column(String columnName, String dataType, int dataLength, String nullable) {
    return new ColumnModel(
        "schema_name",
        "table_name",
        columnName,
        1,
        dataType,
        "data_type_ext",
        dataLength,
        0,
        0,
        nullable,
        "default_definition",
        "primary_key",
        "foreign_key",
        "unique_key",
        "check_constraint",
        "comments"
    );
  }

  private ColumnModel columnNumberNotNullable(String columnName) {
    return column(columnName, "number", 10, "N");
  }

  private ColumnModel columnNumberNullable(String columnName) {
    return column(columnName, "number", 10, "Y");
  }

  private ColumnModel columnStringNullable(String columnName) {
    return column(columnName, "string", 255, "Y");
  }

  private ColumnModel columnDateNullable(String columnName) {
    return column(columnName, "DATE", 7, "Y");
  }

  private ColumnModel columnDateNotNullable(String columnName) {
    return column(columnName, "DATE", 7, "N");
  }

  private TableMetadata table(String tableName,
                              List<PrimaryKeyModel> primaryKeys,
                              List<ForeignKeyModel> foreignKeys,
                              List<ReferenceModel> references,
                              List<ColumnModel> columns) {
    return new TableMetadata(
        tableName,
        "schema_name",
        columns,
        primaryKeys,
        foreignKeys,
        Collections.emptyList(),
        references,
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList()
    );
  }

  private TableMetadata table(String tableName, Object... objects) {
    List<PrimaryKeyModel> primaryKeys = Lists.newArrayList();
    List<ForeignKeyModel> foreignKeys = Lists.newArrayList();
    List<ReferenceModel> referenceModels = Lists.newArrayList();
    List<ColumnModel> columnModels = Lists.newArrayList();

    for (Object object : objects) {
      if (object instanceof PrimaryKeyModel) {
        primaryKeys.add((PrimaryKeyModel) object);
      } else if (object instanceof ForeignKeyModel) {
        foreignKeys.add((ForeignKeyModel) object);
      } else if (object instanceof ReferenceModel) {
        referenceModels.add((ReferenceModel) object);
      } else if (object instanceof ColumnModel) {
        columnModels.add((ColumnModel) object);
      } else {
        throw new RuntimeException("Unsupported type " + object.getClass());
      }
    }

    return table(tableName, primaryKeys, foreignKeys, referenceModels, columnModels);
  }

  private ReferenceModel referenceModel(String childTable,
                                        String childColumn,
                                        String constraintName,
                                        String parentTable,
                                        String parentColumn) {
    return new ReferenceModel(
        childTable,
        childColumn,
        constraintName,
        parentTable,
        parentColumn
    );
  }

  private ForeignKeyModel foreignKey(String columns) {
    return new ForeignKeyModel(
        "FK",
        "constraint_type",
        "schema_name",
        "constraint_name",
        "table_name",
        columns,
        "rOwner",
        "rConstraintName"
    );
  }

  @Test
  void generate_shouldDisplayEntity() {
    final PrimaryKeyModel primaryKey = primaryKey("id_field");
    final ColumnModel primaryKeyColumn = columnNumberNotNullable("id_field");
    final TableMetadata metadata = table("TableName", primaryKey, primaryKeyColumn);

    final String output = unitUnderTest.generate(Collections.singletonList(metadata));

    assertThat(output).contains("entity TableName {");
    assertThat(output).contains("* id_field : number(10) PK");
    assertThat(output).contains("}");
  }

  @Test
  void generate_shouldDisplayManyPK() {
    final PrimaryKeyModel primaryKey = primaryKey("id_field1, id_field2");
    final ColumnModel primaryKeyColumn1 = columnNumberNotNullable("id_field1");
    final ColumnModel primaryKeyColumn2 = columnStringNullable("id_field2");
    final TableMetadata metadata = table("TableName", primaryKey, primaryKeyColumn1, primaryKeyColumn2);

    final String output = unitUnderTest.generate(Collections.singletonList(metadata));

    assertThat(output).contains("entity TableName {");
    assertThat(output).contains("* id_field1 : number(10) PK");
    assertThat(output).contains("id_field2 : string(255) PK");
    assertThat(output).contains("}");
  }

  @Test
  void generate_shouldDisplayForeignKeys() {
    final ForeignKeyModel foreignKey = foreignKey("referenced_id1, referenced_id2");
    final ColumnModel foreignKeyColumn1 = columnNumberNotNullable("referenced_id1");
    final ColumnModel foreignKeyColumn2 = columnStringNullable("referenced_id2");
    final TableMetadata metadata = table("TableName", foreignKey, foreignKeyColumn1, foreignKeyColumn2);

    final String output = unitUnderTest.generate(Collections.singletonList(metadata));

    assertThat(output).contains("entity TableName {" + System.lineSeparator());
    assertThat(output).contains("--"+ System.lineSeparator());
    assertThat(output).contains("* referenced_id1 : number(10) FK");
    assertThat(output).contains("referenced_id2 : string(255) FK");
    assertThat(output).contains("}");
  }

  @Test
  void generate_shouldHaveZeroToMany() {
    final PrimaryKeyModel primaryKey = primaryKey("id_field1");
    final ColumnModel primaryKeyColumn1 = columnNumberNullable("id_field1");
    final ForeignKeyModel foreignKey = foreignKey("referenced_id1");
    final ColumnModel foreigKeyColumn1 = columnNumberNotNullable("referenced_id1");
    final ForeignKeyModel foreignKey2 = foreignKey("referenced_id1");
    final ColumnModel foreigKeyColumn2 = columnStringNullable("referenced_id2");
    final ColumnModel column1 = columnNumberNullable("id_field1");
    final ColumnModel column2 = columnNumberNullable("referenced_id1");
    final ColumnModel column3 = columnDateNullable("non_key_field1");
    final ColumnModel column4 = columnDateNullable("non_key_field2");
    final ColumnModel column5 = columnStringNullable("referenced_id2");

    final ReferenceModel reference = referenceModel(
        "TableName1", "referenced_id1",
        "EMP_JOB_FK",
        "TableName2", "referenced_id2"
    );

    final TableMetadata metadata1 = table("TableName1", primaryKey, reference,
        foreignKey, column1, column2, column3, column4);
    final TableMetadata metadata2 = table("TableName2", primaryKey,
        foreignKey2, column1, column2, column3, column4, column5);

    final String output = unitUnderTest.generate(Arrays.asList(metadata1, metadata2));

    assertThat(output).contains("entity TableName1 {" + System.lineSeparator());
    assertThat(output).contains("id_field1 : number(10) PK");
    assertThat(output).contains("--"+ System.lineSeparator());
    assertThat(output).contains("referenced_id1 : number(10) FK");
    assertThat(output).contains("non_key_field1 : DATE(7)");
    assertThat(output).contains("non_key_field2 : DATE(7)");
    assertThat(output).contains("}");

    assertThat(output).contains("entity TableName2 {" + System.lineSeparator());
    assertThat(output).contains("id_field1 : number(10) PK");
    assertThat(output).contains("--"+ System.lineSeparator());
    assertThat(output).contains("referenced_id1 : number(10) FK");
    assertThat(output).contains("non_key_field1 : DATE(7)");
    assertThat(output).contains("non_key_field2 : DATE(7)");
    assertThat(output).contains("}");
    assertThat(output).contains("TableName1}o--||TableName2");
  }

  @Test
  void generate_shouldNotDisplayDuplicateValues() {
    final PrimaryKeyModel primaryKey = primaryKey("id_field1");
    final ColumnModel primaryKeyColumn1 = columnNumberNotNullable("id_field1");

    final ForeignKeyModel foreignKey = foreignKey("referenced_id1");
    final ColumnModel foreignKeyColumn1 = columnNumberNotNullable("referenced_id1");
    final ForeignKeyModel foreignKey2 = foreignKey("referenced_id1");
    final ColumnModel foreignKeyColumn2 = columnStringNullable("referenced_id2");
    final ColumnModel column1 = columnNumberNotNullable("id_field1");
    final ColumnModel column2 = columnNumberNotNullable("referenced_id1");
    final ColumnModel column3 = columnDateNotNullable("non_key_field1");
    final ColumnModel column4 = columnDateNotNullable("non_key_field2");
    final ColumnModel column5 = columnStringNullable("referenced_id2");

    final ReferenceModel reference = referenceModel(
        "TableName1", "referenced_id1",
        "EMP_JOB_FK",
        "TableName2", "referenced_id2"
    );

    final TableMetadata metadata1 = table("TableName1", primaryKey, foreignKey, reference,
        column1, column2, column3, column4);
    final TableMetadata metadata2 = table("TableName2", primaryKey, foreignKey2,
        column1, column2, column3, column4, column5);

    final String output = unitUnderTest.generate(Arrays.asList(metadata1, metadata2));

    assertThat(output).contains("entity TableName1 {" + System.lineSeparator());
    assertThat(output).contains("* id_field1 : number(10) PK");
    assertThat(output).contains("--"+ System.lineSeparator());
    assertThat(output).contains("* referenced_id1 : number(10) FK");
    assertThat(output).contains("* non_key_field1 : DATE(7)");
    assertThat(output).contains("non_key_field2 : DATE(7)");
    assertThat(output).contains("}");

    assertThat(output).contains("entity TableName2 {" + System.lineSeparator());
    assertThat(output).contains("* id_field1 : number(10) PK");
    assertThat(output).contains("--"+ System.lineSeparator());
    assertThat(output).contains("* referenced_id1 : number(10) FK");
    assertThat(output).contains("* non_key_field1 : DATE(7)");
    assertThat(output).contains("non_key_field2 : DATE(7)");
    assertThat(output).contains("}");
    assertThat(output).contains("TableName1}|--||TableName2");
  }
}
