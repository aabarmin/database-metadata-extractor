package ru.mydesignstudio.database.metadata.extractor.extract.parameters.reader

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.ExtractParameters
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

@Component
@ConditionalOnProperty(prefix = "parameters", name = ["name"], havingValue = "json", matchIfMissing = true)
class ClasspathJsonParametersReader(
        @Value("\${parameters.file}") val parametersFilepath: String,
        val objectMapper: ObjectMapper
) : ParametersReader {

    override fun read(): ExtractParameters {
        if (StringUtils.startsWith(parametersFilepath, "classpath")) {
            val resourceLoader = DefaultResourceLoader()
            val paramsResource = resourceLoader.getResource(parametersFilepath)
            paramsResource.inputStream.use { configStream ->
                return objectMapper.readValue(configStream, ExtractParameters::class.java)
            }
        }
        val paramsPath = Paths.get(parametersFilepath)
        if (!Files.exists(paramsPath)) {
            throw RuntimeException("Can't find parameters file $parametersFilepath")
        }
        Files.newInputStream(paramsPath, StandardOpenOption.READ).use { configStream ->
            return objectMapper.readValue(configStream, ExtractParameters::class.java)
        }
    }
}