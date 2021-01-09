package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class TypeModel {
    private String owner;
    private String objectName;
    private String objectType;
    private String comments;
}
