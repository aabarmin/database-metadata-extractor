package ru.mydesignstudio.database.metadata.extractor.output.uml;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extract.result.ColumnModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.ForeignKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.result.PrimaryKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.ReferenceModel;

@Slf4j
@Component
public class PlantUmlMarkupGenerator {

    public String generate(@NonNull List<TableMetadata> tableMetadata) {
        final StringBuilder builder = new StringBuilder();

        generateHeading(builder);
        generateEntities(tableMetadata, builder);
        generateReferences(tableMetadata, builder);
        generateTail(builder);

        log.debug("Generated markup is {}", builder);

        return builder.toString();
    }

    private void generateReferences(@NonNull List<TableMetadata> tableMetadata, @NonNull StringBuilder builder) {
        for (TableMetadata table : tableMetadata) {
            for (ReferenceModel refs : table.getReferences()) {
                if (getColumnMetadataByName(table.getColumns(), refs.getChildColumn()).getNullable().equals("N")) {
                    builder.append(refs.getChildTable()).append("}|--||");
                } else builder.append(refs.getChildTable()).append("}o--||");
                builder.append(refs.getParentTable());
                builder.append(System.lineSeparator());
            }
        }
    }

    private void generateEntities(@NonNull List<TableMetadata> tableMetadata, @NonNull StringBuilder builder) {
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
                if (!isPrimaryKey(column, table.getPrimaryKeys()) && !isForeignKey(column, table.getForeignKeys())) {
                    if (!column.isNullable()) {
                        builder.append("* ");
                    }
                    builder.append(column.getColumnName()).append(" : ")
                        .append(column.getDataType()).append("(")
                        .append(column.getDataLength()).append(")");
                    builder.append(System.lineSeparator());
                }
            }
            builder.append("}");
            builder.append(System.lineSeparator());
        }
    }

    private boolean isForeignKey(@NonNull ColumnModel column, @NonNull Collection<ForeignKeyModel> foreignKeys) {
        return foreignKeys.stream()
            .map(ForeignKeyModel::getColumns)
            .map(columns -> StringUtils.split(columns, ","))
            .flatMap(columnsArrays -> Arrays.stream(columnsArrays))
            .map(StringUtils::trim)
            .anyMatch(foreignKey -> StringUtils.equals(column.getColumnName(), foreignKey));
    }

    private boolean isPrimaryKey(@NonNull ColumnModel column, @NonNull Collection<PrimaryKeyModel> primaryKeys) {
        return primaryKeys.stream()
            .map(PrimaryKeyModel::getColumns)
            .map(columns -> StringUtils.split(columns, ","))
            .flatMap(columnsArrays -> Arrays.stream(columnsArrays))
            .map(StringUtils::trim)
            .anyMatch(primaryKey -> StringUtils.equals(column.getColumnName(), primaryKey));
    }

    private void generateTail(StringBuilder builder) {
        builder.append(System.lineSeparator());
        builder.append("@enduml");
    }

    private void generateHeading(StringBuilder builder) {
        builder.append("@startuml");
        builder.append(System.lineSeparator());
        builder.append("hide circle");
        builder.append(System.lineSeparator());
        builder.append("skinparam linetype ortho");
        builder.append(System.lineSeparator());
    }

    private ColumnModel getColumnMetadataByName(Collection<ColumnModel> columns, String columnName) {
        for (ColumnModel column : columns) {
            if (column.getColumnName().equals(columnName)) {
                return column;
            }
        }
        return null;
    }
}
