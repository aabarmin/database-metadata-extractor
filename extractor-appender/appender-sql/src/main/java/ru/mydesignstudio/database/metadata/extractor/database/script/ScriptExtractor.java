package ru.mydesignstudio.database.metadata.extractor.database.script;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.database.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.database.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extract.result.ScriptModel;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
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