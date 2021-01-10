package ru.mydesignstudio.database.metadata.extractor.parameters.reader

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.mydesignstudio.database.metadata.extractor.config.ObjectMapperConfiguration

@ContextConfiguration
@ExtendWith(SpringExtension::class)
@TestPropertySource(properties = [
    "parameters.file=classpath:\\params.json",
    "parameters.type=json"
])
internal class ClasspathJsonParametersReaderTest(
        @Autowired val unitUnderTest: ClasspathJsonParametersReader
) {
    @Test
    internal fun `Check the context starts correctly`() {
        assertNotNull(unitUnderTest)
    }

    @Test
    internal fun `Check the configuration deserialized correctly`() {
        var parameters = unitUnderTest.read()

        assertNotNull(parameters)
    }

    @Configuration
    @Import(value = [
        ClasspathJsonParametersReader::class,
        ObjectMapperConfiguration::class
    ])
    class ConfigurationForTest {

    }
}