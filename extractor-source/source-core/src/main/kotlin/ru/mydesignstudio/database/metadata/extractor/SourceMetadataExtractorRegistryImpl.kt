package ru.mydesignstudio.database.metadata.extractor

import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.registry.SourceMetadataExtractorRegistry
import ru.mydesignstudio.database.metadata.extractor.source.SourceMetadataExtractor

@Component
class SourceMetadataExtractorRegistryImpl : SourceMetadataExtractorRegistry {
    private val registry: MutableMap<String, SourceMetadataExtractor> = HashMap()

    override fun register(extractor: SourceMetadataExtractor) {
        if (hasExtractor(extractor.getSourceType())) {
            throw RuntimeException("An extractor for type ${extractor.getSourceType()} has already been registered")
        }
        registry[extractor.getSourceType()] = extractor
    }

    override fun hasExtractor(type: String): Boolean {
        return registry.containsKey(type)
    }

    override fun getExtractor(type: String): SourceMetadataExtractor {
        if (!hasExtractor(type)) {
            throw RuntimeException("There is no extractor for type $type")
        }
        /**
         * Adding !! because there is a check prior, in case if there is no extractor
         * for the given type, an exception will be thrown
         */
        return registry[type]!!
    }
}