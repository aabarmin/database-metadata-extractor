package ru.mydesignstudio.database.metadata.extractor.extractors.uk;

import lombok.Data;

@Data
public class UniqueKeyModel {
    private String owner;
    private String constraintName;
    private String tableName;
    private String columnName;
    private String uniqueKey;
}
