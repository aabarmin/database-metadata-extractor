package ru.mydesignstudio.database.metadata.extractor.extractors.script;

import lombok.Data;

@Data
public class ScriptModel {
    private String schemaName;
    private String viewName;
    private String script;
}