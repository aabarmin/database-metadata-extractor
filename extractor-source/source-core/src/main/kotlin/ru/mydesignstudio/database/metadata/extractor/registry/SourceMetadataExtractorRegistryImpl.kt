package ru.mydesignstudio.database.metadata.extractor.registry

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.source.SourceMetadataExtractor
import javax.annotation.PostConstruct

@Component
class SourceMetadataExtractorRegistryImpl @Autowired constructor(
        private val context: ApplicationContext
) : SourceMetadataExtractorRegistry {
    private val registry: MutableMap<String, SourceMetadataExtractor> = HashMap()

    @PostConstruct
    fun init() {
        var beans = context.getBeansOfType(SourceMetadataExtractor::class.java)
        beans.values.forEach { register(it) }
    }

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