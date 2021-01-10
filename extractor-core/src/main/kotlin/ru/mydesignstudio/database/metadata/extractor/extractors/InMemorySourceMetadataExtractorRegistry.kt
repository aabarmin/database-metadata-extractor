package ru.mydesignstudio.database.metadata.extractor.extractors

import org.springframework.stereotype.Component

@Component
class InMemorySourceMetadataExtractorRegistry : SourceMetadataExtractorRegistry {
    private val registry: MutableMap<String, DatabaseMetadataExtractor> = HashMap()

    override fun register(extractor: DatabaseMetadataExtractor) {
        if (hasExtractor(extractor.getSourceType())) {
            throw RuntimeException("An extractor for type ${extractor.getSourceType()} has already been registered")
        }
        registry[extractor.getSourceType()] = extractor
    }

    override fun hasExtractor(type: String): Boolean {
        return registry.containsKey(type)
    }

    override fun getExtractor(type: String): DatabaseMetadataExtractor {
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