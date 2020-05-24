package ru.mydesignstudio.database.metadata.extractor.extractors.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.column.ColumnModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

@Component
public class TypeExtractor implements TableMetadataAppender {
    @StringResource("classpath:sql/extract_type.sql")
    private String typeQuery;

    @Autowired
    private ExtractHelper helper;


}