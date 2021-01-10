package ru.mydesignstudio.database.metadata.extractor

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.apache.commons.lang.RandomStringUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchema
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSchemaLabels
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSource
import ru.mydesignstudio.database.metadata.extractor.extract.parameters.source.MetadataSourceConnection
import ru.mydesignstudio.database.metadata.extractor.schema.SchemaMetadataExtractor

@ExtendWith(MockitoExtension::class)
internal class OracleMetadataExtractorTest {
    private lateinit var unitUnderTest: OracleMetadataExtractor
    private lateinit var schemaExtractor: SchemaMetadataExtractor

    @BeforeEach
    internal fun setUp() {
        schemaExtractor = mock<SchemaMetadataExtractor>()
        unitUnderTest = OracleMetadataExtractor(schemaExtractor)
    }

    @Test
    internal fun `One call per schema`() {
        var metadataSource = MetadataSource(
                "ORACLE",
                createConnection(),
                listOf(
                        createSchema(),
                        createSchema()
                )
        )

        unitUnderTest.extract(metadataSource)

        verify(schemaExtractor, times(2)).extract(any(), any())
    }

    @Test
    internal fun `Should check if source of a proper type`() {
        var metadataSource = MetadataSource(
                "UNSUPPORTED",
                createConnection(),
                listOf(
                        createSchema(),
                        createSchema()
                )
        )

        assertThrows<IllegalArgumentException> { unitUnderTest.extract(metadataSource) }
    }

    private fun createConnection(): MetadataSourceConnection {
        return MetadataSourceConnection(
                RandomStringUtils.random(10),
                RandomStringUtils.random(10),
                RandomStringUtils.random(10)
        )
    }

    private fun createSchema(): MetadataSchema {
        return MetadataSchema(
                RandomStringUtils.random(10),
                emptyList(),
                createLabels()
        )
    }

    private fun createLabels(): MetadataSchemaLabels {
        return MetadataSchemaLabels(
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList()
        )
    }
}