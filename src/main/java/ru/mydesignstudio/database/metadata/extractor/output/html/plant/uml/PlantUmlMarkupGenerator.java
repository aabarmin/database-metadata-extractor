package ru.mydesignstudio.database.metadata.extractor.output.html.plant.uml;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.column.ColumnModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.fk.ForeignKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.pk.PrimaryKeyModel;

@Slf4j
@Component
public class PlantUmlMarkupGenerator {

  public String generate(List<DatabaseMetadata> databaseMetadata,
      List<TableMetadata> tableMetadata) {
    final StringBuilder builder = new StringBuilder();

    builder.append("@startuml");
    builder.append(System.lineSeparator());
    builder.append("hide circle");
    builder.append(System.lineSeparator());
    builder.append("skinparam linetype ortho");
    builder.append(System.lineSeparator());

    for (TableMetadata table : tableMetadata) {
      builder.append("entity ").append(table.getTableName()).append(" {");
      builder.append(System.lineSeparator());
      for (PrimaryKeyModel pk : table.getPrimaryKeys()) {
        for (String pkValue : StringUtils.split(pk.getColumns(), ",")) {
          pkValue = StringUtils.trim(pkValue);
          builder.append("* ").append(pkValue).append(" : ")
              .append(getColumnMetadataByName(table.getColumns(), pkValue).getDataType())
              .append("(").append(
              getColumnMetadataByName(table.getColumns(), pkValue).getDataLength())
              .append(")").append(" <<")
              .append(pk.getPrimaryKey())
              .append(">>");
          builder.append(System.lineSeparator());
        }
      }
      builder.append("--");
      builder.append(System.lineSeparator());

      for (ForeignKeyModel fk : table.getForeignKeys()) {
        for (String fkValue : StringUtils.split(fk.getColumns(), ",")) {
          fkValue = StringUtils.trim(fkValue);
          builder.append("* ").append(fkValue).append(" : ")
              .append(getColumnMetadataByName(table.getColumns(), fkValue).getDataType())
              .append("(").append(
              getColumnMetadataByName(table.getColumns(), fkValue).getDataLength())
              .append(")").append(" <<")
              .append(fk.getForeignKey())
              .append(">>");
          builder.append(System.lineSeparator());
        }
      }

      for (ColumnModel column : table.getColumns()) {
        for (PrimaryKeyModel pk : table.getPrimaryKeys()) {
          for (String pkValue : StringUtils.split(pk.getColumns(), ",")) {
            pkValue = StringUtils.trim(pkValue);
            for (ForeignKeyModel fk : table.getForeignKeys()) {
              for (String fkValue : StringUtils.split(fk.getColumns(), ",")) {
                fkValue = StringUtils.trim(fkValue);
                if (!column.getColumnName().equals(pkValue)&&!column.getColumnName().equals(fkValue)) {
                  builder.append(column.getColumnName()).append(" : ")
                      .append(column.getDataType()).append("(")
                      .append(column.getDataLength()).append(")");
                  builder.append(System.lineSeparator());
                }
              }
            }

          }
        }
      }
      builder.append("}");
    }

    builder.append(System.lineSeparator());
    builder.append("@enduml");

    log.info("Generated markup is {}", builder);

    return builder.toString();
  }

  private ColumnModel getColumnMetadataByName(List<ColumnModel> columns, String columnName) {
    for (ColumnModel column : columns) {
      if (column.getColumnName().equals(columnName)) {
        return column;
      }
    }
    return null;
  }
}
