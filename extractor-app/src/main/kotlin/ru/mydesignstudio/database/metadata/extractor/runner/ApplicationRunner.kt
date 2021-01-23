package ru.mydesignstudio.database.metadata.extractor.runner

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.destination.MetadataOutput
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.reader.ParametersReader
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.validator.ParametersValidator
import ru.mydesignstudio.database.metadata.extractor.registry.SourceMetadataExtractorRegistry
import java.util.stream.Collectors

@Component
class ApplicationRunner constructor (
        private val sourceRegistry: SourceMetadataExtractorRegistry,
        private val parametersReader: ParametersReader,
        private val parametersValidator: ParametersValidator,
        private val metadataOutput: MetadataOutput) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(ApplicationRunner::class.java)

    override fun run(vararg args: String) {
        logger.info("Reading parameters file")
        val parameters = parametersReader.read()
        logger.info("Done")

        logger.info("Validating given parameters")
        val validationResult = parametersValidator.validate(parameters)
        if (!validationResult.isValid) {
            throw RuntimeException("Parameters are invalid: ${validationResult.message}")
        }
        logger.info("Parameters are correct")

        logger.info("Extracting metadata from sources")
        var metadata = parameters.sources.stream()
                .map { Pair(it, sourceRegistry.getExtractor(it.source)) }
                .map { p -> p.second.extract(p.first) }
                .flatMap { it.stream() }
                .collect(Collectors.toList())

        logger.info("Output")
        metadataOutput.output(metadata, parameters)
        logger.info("Done")
    }
}