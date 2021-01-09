package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import lombok.Data;

@Data
public class ScriptModel {
    private String schemaName;
    private String viewName;
    private String script;
}