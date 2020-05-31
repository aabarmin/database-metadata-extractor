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
import ru.mydesignstudio.database.metadata.extractor.extractors.reference.ReferenceModel;

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

  @Test
  void generate_shouldDisplayEntity() {
    final PrimaryKeyModel primaryKey = new PrimaryKeyModel();
    primaryKey.setColumns("id_field");
    primaryKey.setPrimaryKey("PK");

    final ColumnModel primaryKeyColumn = new ColumnModel();
    primaryKeyColumn.setColumnName("id_field");
    primaryKeyColumn.setDataType("number");
    primaryKeyColumn.setDataLength(10);
    primaryKeyColumn.setNullable("N");

    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName("TableName");
    metadata.setPrimaryKeys(Collections.singletonList(primaryKey));
    metadata.setForeignKeys(Collections.emptyList());
    metadata.setReferences(Collections.emptyList());
    metadata.setColumns(Arrays.asList(primaryKeyColumn));

    final String output = unitUnderTest.generate(Collections.singletonList(metadata));

    assertThat(output).contains("entity TableName {");
    assertThat(output).contains("* id_field : number(10) PK");
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
    primaryKeyColumn1.setNullable("N");

    final ColumnModel primaryKeyColumn2 = new ColumnModel();
    primaryKeyColumn2.setColumnName("id_field2");
    primaryKeyColumn2.setDataType("string");
    primaryKeyColumn2.setDataLength(255);
    primaryKeyColumn2.setNullable("Y");

    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName("TableName");
    metadata.setPrimaryKeys(Collections.singletonList(primaryKey));
    metadata.setForeignKeys(Collections.emptyList());
    metadata.setReferences(Collections.emptyList());
    metadata.setColumns(Arrays.asList(primaryKeyColumn1, primaryKeyColumn2));

    final String output = unitUnderTest.generate(Collections.singletonList(metadata));

    assertThat(output).contains("entity TableName {");
    assertThat(output).contains("* id_field1 : number(10) PK");
    assertThat(output).contains("id_field2 : string(255) PK");
    assertThat(output).contains("}");
  }

  @Test
  void generate_shouldDisplayForeignKeys() {
    final ForeignKeyModel foreignKey = new ForeignKeyModel();
    foreignKey.setColumns("referenced_id1, referenced_id2");
    foreignKey.setForeignKey("FK");

    final ColumnModel foreigKeyColumn1 = new ColumnModel();
    foreigKeyColumn1.setColumnName("referenced_id1");
    foreigKeyColumn1.setDataType("number");
    foreigKeyColumn1.setDataLength(10);
    foreigKeyColumn1.setNullable("N");

    final ColumnModel foreigKeyColumn2 = new ColumnModel();
    foreigKeyColumn2.setColumnName("referenced_id2");
    foreigKeyColumn2.setDataType("string");
    foreigKeyColumn2.setDataLength(255);
    foreigKeyColumn2.setNullable("Y");

    final TableMetadata metadata = new TableMetadata();
    metadata.setTableName("TableName");
    metadata.setPrimaryKeys(Collections.emptyList());
    metadata.setForeignKeys(Collections.singletonList(foreignKey));
    metadata.setReferences(Collections.emptyList());
    metadata.setColumns(Arrays.asList(foreigKeyColumn1, foreigKeyColumn2));

    final String output = unitUnderTest.generate(Collections.singletonList(metadata));

    assertThat(output).contains("entity TableName {" + System.lineSeparator());
    assertThat(output).contains("--"+ System.lineSeparator());
    assertThat(output).contains("* referenced_id1 : number(10) FK");
    assertThat(output).contains("referenced_id2 : string(255) FK");
    assertThat(output).contains("}");
  }

  @Test
  void generate_shouldHaveZeroToMany() {
    final PrimaryKeyModel primaryKey = new PrimaryKeyModel();
    primaryKey.setColumns("id_field1");
    primaryKey.setPrimaryKey("PK");

    final ColumnModel primaryKeyColumn1 = new ColumnModel();
    primaryKeyColumn1.setColumnName("id_field1");
    primaryKeyColumn1.setDataType("number");
    primaryKeyColumn1.setDataLength(10);
    primaryKeyColumn1.setNullable("Y");

    final ForeignKeyModel foreignKey = new ForeignKeyModel();
    foreignKey.setColumns("referenced_id1");
    foreignKey.setForeignKey("FK");

    final ColumnModel foreigKeyColumn1 = new ColumnModel();
    foreigKeyColumn1.setColumnName("referenced_id1");
    foreigKeyColumn1.setDataType("number");
    foreigKeyColumn1.setDataLength(10);
    foreigKeyColumn1.setNullable("Y");

    final ForeignKeyModel foreignKey2 = new ForeignKeyModel();
    foreignKey2.setColumns("referenced_id1");
    foreignKey2.setForeignKey("FK");

    final ColumnModel foreigKeyColumn2 = new ColumnModel();
    foreigKeyColumn2.setColumnName("referenced_id2");
    foreigKeyColumn2.setDataType("string");
    foreigKeyColumn2.setDataLength(255);
    foreigKeyColumn2.setNullable("Y");

    final ColumnModel column1 = new ColumnModel();
    column1.setColumnName("id_field1");
    column1.setDataType("number");
    column1.setDataLength(10);
    column1.setNullable("Y");

    final ColumnModel column2 = new ColumnModel();
    column2.setColumnName("referenced_id1");
    column2.setDataType("number");
    column2.setDataLength(10);
    column2.setNullable("Y");

    final ColumnModel column3 = new ColumnModel();
    column3.setColumnName("non_key_field1");
    column3.setDataType("DATE");
    column3.setDataLength(7);
    column3.setNullable("Y");

    final ColumnModel column4 = new ColumnModel();
    column4.setColumnName("non_key_field2");
    column4.setDataType("DATE");
    column4.setDataLength(7);
    column4.setNullable("Y");

    final ColumnModel column5 = new ColumnModel();
    column5.setColumnName("referenced_id2");
    column5.setDataType("string");
    column5.setDataLength(255);
    column5.setNullable("Y");

    final ReferenceModel reference = new ReferenceModel();
    reference.setChildTable("TableName1");
    reference.setParentTable("TableName2");
    reference.setConstraintName("EMP_JOB_FK");
    reference.setChildColumn("referenced_id1");
    reference.setParentColumn("referenced_id2");

    final TableMetadata metadata1 = new TableMetadata();
    metadata1.setTableName("TableName1");
    metadata1.setPrimaryKeys(Collections.singletonList(primaryKey));
    metadata1.setForeignKeys(Collections.singletonList(foreignKey));
    metadata1.setReferences(Collections.singletonList(reference));
    metadata1.setColumns(Arrays.asList(column1, column2, column3, column4));

    final TableMetadata metadata2 = new TableMetadata();
    metadata2.setTableName("TableName2");
    metadata2.setPrimaryKeys(Collections.singletonList(primaryKey));
    metadata2.setForeignKeys(Collections.singletonList(foreignKey2));
    metadata2.setReferences(Collections.emptyList());
    metadata2.setColumns(Arrays.asList(column1, column2, column3, column4,column5));

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
    final PrimaryKeyModel primaryKey = new PrimaryKeyModel();
    primaryKey.setColumns("id_field1");
    primaryKey.setPrimaryKey("PK");

    final ColumnModel primaryKeyColumn1 = new ColumnModel();
    primaryKeyColumn1.setColumnName("id_field1");
    primaryKeyColumn1.setDataType("number");
    primaryKeyColumn1.setDataLength(10);
    primaryKeyColumn1.setNullable("N");

    final ForeignKeyModel foreignKey = new ForeignKeyModel();
    foreignKey.setColumns("referenced_id1");
    foreignKey.setForeignKey("FK");

    final ColumnModel foreigKeyColumn1 = new ColumnModel();
    foreigKeyColumn1.setColumnName("referenced_id1");
    foreigKeyColumn1.setDataType("number");
    foreigKeyColumn1.setDataLength(10);
    foreigKeyColumn1.setNullable("N");

    final ForeignKeyModel foreignKey2 = new ForeignKeyModel();
    foreignKey2.setColumns("referenced_id1");
    foreignKey2.setForeignKey("FK");

    final ColumnModel foreigKeyColumn2 = new ColumnModel();
    foreigKeyColumn2.setColumnName("referenced_id2");
    foreigKeyColumn2.setDataType("string");
    foreigKeyColumn2.setDataLength(255);
    foreigKeyColumn2.setNullable("Y");


    final ColumnModel column1 = new ColumnModel();
    column1.setColumnName("id_field1");
    column1.setDataType("number");
    column1.setDataLength(10);
    column1.setNullable("N");

    final ColumnModel column2 = new ColumnModel();
    column2.setColumnName("referenced_id1");
    column2.setDataType("number");
    column2.setDataLength(10);
    column2.setNullable("N");

    final ColumnModel column3 = new ColumnModel();
    column3.setColumnName("non_key_field1");
    column3.setDataType("DATE");
    column3.setDataLength(7);
    column3.setNullable("N");

    final ColumnModel column4 = new ColumnModel();
    column4.setColumnName("non_key_field2");
    column4.setDataType("DATE");
    column4.setDataLength(7);
    column4.setNullable("Y");

    final ColumnModel column5 = new ColumnModel();
    column5.setColumnName("referenced_id2");
    column5.setDataType("string");
    column5.setDataLength(255);
    column5.setNullable("Y");

    final ReferenceModel reference = new ReferenceModel();
    reference.setChildTable("TableName1");
    reference.setParentTable("TableName2");
    reference.setConstraintName("EMP_JOB_FK");
    reference.setChildColumn("referenced_id1");
    reference.setParentColumn("referenced_id2");

    final TableMetadata metadata1 = new TableMetadata();
    metadata1.setTableName("TableName1");
    metadata1.setPrimaryKeys(Collections.singletonList(primaryKey));
    metadata1.setForeignKeys(Collections.singletonList(foreignKey));
    metadata1.setReferences(Collections.singletonList(reference));
    metadata1.setColumns(Arrays.asList(column1, column2, column3, column4));

    final TableMetadata metadata2 = new TableMetadata();
    metadata2.setTableName("TableName2");
    metadata2.setPrimaryKeys(Collections.singletonList(primaryKey));
    metadata2.setForeignKeys(Collections.singletonList(foreignKey2));
    metadata2.setReferences(Collections.emptyList());
    metadata2.setColumns(Arrays.asList(column1, column2, column3, column4,column5));

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
