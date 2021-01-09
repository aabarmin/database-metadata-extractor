package ru.mydesignstudio.database.metadata.extractor.extractors.script;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.ScriptModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Component
public class ScriptExtractor implements TableMetadataAppender {
    @StringResource("classpath:sql/extract_script.sql")
    private String extractQuery;

    @Autowired
    private ExtractHelper helper;

    @Override
    public void append(TableMetadata metadata, String schemaName, String tableName) {
        metadata.setScripts(helper.extract(extractQuery, new Object[] { schemaName, tableName }, ScriptModel.class));
    }

}