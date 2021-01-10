package ru.mydesignstudio.database.metadata.extractor.database.uk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.database.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.database.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.result.UniqueKeyModel;
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

