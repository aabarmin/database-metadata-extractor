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
import ru.mydesignstudio.database.metadata.extractor.extractors.reference.ReferenceModel;

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
                    if (getColumnMetadataByName(table.getColumns(), pkValue).getNullable().equals("N")) {
                        builder.append("* ");
                    }
                    builder.append(pkValue).append(" : ")
                            .append(getColumnMetadataByName(table.getColumns(), pkValue).getDataType())
                            .append("(").append(
                            getColumnMetadataByName(table.getColumns(), pkValue).getDataLength())
                            .append(") ")
                            .append(pk.getPrimaryKey());
                    builder.append(System.lineSeparator());
                }
            }
            builder.append("--");
            builder.append(System.lineSeparator());

            for (ForeignKeyModel fk : table.getForeignKeys()) {
                for (String fkValue : StringUtils.split(fk.getColumns(), ",")) {
                    fkValue = StringUtils.trim(fkValue);
                    if (getColumnMetadataByName(table.getColumns(), fkValue).getNullable().equals("N")) {
                        builder.append("* ");
                    }
                    builder.append(fkValue).append(" : ").append(getColumnMetadataByName(table.getColumns(), fkValue).getDataType())
                            .append("(").append(
                            getColumnMetadataByName(table.getColumns(), fkValue).getDataLength())
                            .append(") ")
                            .append(fk.getForeignKey());
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
                                if (!column.getColumnName().equals(pkValue) && !column.getColumnName().equals(fkValue)) {
                                    if (column.getNullable().equals("N")) {
                                        builder.append("* ");
                                    }
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
            builder.append(System.lineSeparator());
        }
        for (TableMetadata table : tableMetadata) {
            for (ReferenceModel refs : table.getReferences()) {
                if (getColumnMetadataByName(table.getColumns(), refs.getChildColumn()).getNullable().equals("N")) {
                    builder.append(refs.getChildTable()).append("}|--||");
                } else builder.append(refs.getChildTable()).append("}o--||");
                builder.append(refs.getParentTable());
                builder.append(System.lineSeparator());
            }
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
