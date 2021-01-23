package ru.mydesignstudio.database.metadata.extractor

import assertk.assertThat
import assertk.assertions.isNotNull
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledIf
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.OracleContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.*
import ru.mydesignstudio.database.metadata.extractor.source.SourceMetadataExtractor

@DisabledIfEnvironmentVariable(named = "CI", matches = "true", disabledReason = "Switched off on CI")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
internal class OracleMetadataExtractorIntegrationTest {
    companion object {
        @Container
        val oracleContainer = OracleContainer("oracleinanutshell/oracle-xe-11g")
    }

    @Autowired
    private lateinit var metadataExtractor: SourceMetadataExtractor

    @Test
    internal fun `Check context starts`() {
        assertThat(metadataExtractor).isNotNull()
    }

    @Test
    internal fun `Try extract metadata`() {
        val result = metadataExtractor.extract(buildSource())

        assertNotNull(result)
    }

    fun buildSource(): MetadataSource {
        return MetadataSource(
                source = "ORACLE",
                connection = MetadataSourceConnection(
                        url = oracleContainer.jdbcUrl,
                        username = oracleContainer.username,
                        password = oracleContainer.password
                ),
                schemas = listOf(
                        MetadataSchema(
                                name = "HR",
                                labels = MetadataSchemaLabels(
                                        table = listOf(),
                                        view = listOf(),
                                        schema = listOf(),
                                        diagram = listOf()
                                ),
                                objects = listOf(
                                        MetadataObject(
                                                name = "EMPLOYEES",
                                                labels = listOf()
                                        )
                                )
                        )
                )
        )
    }
}