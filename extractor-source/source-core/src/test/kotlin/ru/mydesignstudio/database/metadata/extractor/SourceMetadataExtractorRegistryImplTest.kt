package ru.mydesignstudio.database.metadata.extractor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext
import ru.mydesignstudio.database.metadata.extractor.registry.SourceMetadataExtractorRegistry
import ru.mydesignstudio.database.metadata.extractor.registry.SourceMetadataExtractorRegistryImpl
import ru.mydesignstudio.database.metadata.extractor.source.SourceMetadataExtractor

internal class SourceMetadataExtractorRegistryImplTest {
    private val registry: SourceMetadataExtractorRegistry = SourceMetadataExtractorRegistryImpl(
            mock<ApplicationContext>()
    )

    @Test
    internal fun `Registered extractor should be available`() {
        var dummyExtractor: SourceMetadataExtractor = mock()
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