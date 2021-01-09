package ru.mydesignstudio.database.metadata.extractor.extractors.uk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.UniqueKeyModel;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Slf4j
@Component
public class UniqueKeyExtractor implements TableMetadataAppender {
    @StringResource("classpath:sql/extract_unique_keys.sql")
    private String extractQuery;

    @Autowired
    private ExtractHelper helper;

    @Override
    public void append(TableMetadata metadata, String schemaName, String tableName) {
        log.info("Extracting unique keys for table {} in schema {}", tableName, schemaName);

        metadata.setUniqueKeys(helper.extract(extractQuery, new Object[]{tableName, schemaName}, UniqueKeyModel.class));

        log.debug("Extracted {} unique keys", metadata.getForeignKeys().size());
    }
}

