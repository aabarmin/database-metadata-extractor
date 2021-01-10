package ru.mydesignstudio.database.metadata.extractor.runner

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import ru.mydesignstudio.database.metadata.extractor.extractors.SourceMetadataExtractor
import ru.mydesignstudio.database.metadata.extractor.parameters.reader.ParametersReader
import ru.mydesignstudio.database.metadata.extractor.parameters.validator.ParametersValidator
import java.util.stream.Collectors

@Component
class ApplicationRunner @Autowired constructor (
        val sourceExtractor: SourceMetadataExtractor,
        val parametersReader: ParametersReader,
        val parametersValidator: ParametersValidator
        ) : CommandLineRunner {

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
        var outputs = parameters.sources.stream()
                .map { sourceExtractor.extract(it) }
                .flatMap { it.stream() }
                .collect(Collectors.toList())
        logger.info("Done")

        /*
        logger.info("Reading database metadata")
        val databaseMetadata = databaseExtractor.extract(parameters.sources)
        logger.info("Done")

        logger.info("Reading tables metadata")
        val tableMetadata = tableExtractor.extract(parameters.sources)
        logger.info("Done")

        logger.info("Output")
        metadataOutput.output(databaseMetadata, tableMetadata)
        logger.info("Done")
         */
    }
}