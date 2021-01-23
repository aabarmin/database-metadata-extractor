package ru.mydesignstudio.database.metadata.extractor.extract.helper

import org.apache.commons.io.IOUtils
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class ClassPathQueryProvider : QueryProvider {
    private val loader = DefaultResourceLoader();

    override fun provide(name: String): String {
        val resource = loader.getResource(name)
        resource.inputStream.use { stream ->
            return IOUtils.toString(stream, StandardCharsets.UTF_8)
        }
    }
}