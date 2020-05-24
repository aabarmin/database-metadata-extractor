package ru.mydesignstudio.database.metadata.extractor.extractors.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.ExtractHelper;
import ru.mydesignstudio.database.metadata.extractor.extractors.TableMetadataAppender;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.resource.StringResource;

import java.util.List;

@Component
public class TypeExtractor implements TableMetadataAppender {
    @StringResource("classpath:sql/extract_type.sql")
    private String typeQuery;

    @Autowired
    private ExtractHelper helper;

    @Override
    public void append(TableMetadata metadata, String schemaName, String tableName) {
        List<TypeModel> types = helper.extract(typeQuery, new Object[]{tableName}, TypeModel.class);
        metadata.setTypes(types);
    }
}