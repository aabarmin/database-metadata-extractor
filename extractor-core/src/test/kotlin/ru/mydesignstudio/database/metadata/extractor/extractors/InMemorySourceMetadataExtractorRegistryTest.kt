package ru.mydesignstudio.database.metadata.extractor.extractors

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

internal class InMemorySourceMetadataExtractorRegistryTest {
    private val registry: SourceMetadataExtractorRegistry = InMemorySourceMetadataExtractorRegistry()

    @Test
    internal fun `Registered extractor should be available`() {
        var dummyExtractor: DatabaseMetadataExtractor = mock()
        whenever(dummyExtractor.getSourceType()).thenReturn("TEST")

        registry.register(dummyExtractor)

        assertTrue(registry.hasExtractor("TEST"))
    }

    @Test
    internal fun `There should be an exception for unregistered type`() {
        assertThrows(RuntimeException::class.java) {
            registry.getExtractor("UNKNOWN")
        }
    }
}