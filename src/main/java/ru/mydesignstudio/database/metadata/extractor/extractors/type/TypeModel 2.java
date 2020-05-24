package ru.mydesignstudio.database.metadata.extractor.extractors.type;

import lombok.Data;

@Data
public class TypeModel {
    private String owner;
    private String tableName;
    private String objectType;
}
