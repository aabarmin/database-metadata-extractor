package ru.mydesignstudio.database.metadata.extractor.destination

import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata
import ru.mydesignstudio.database.metadata.extractor.output.Output

/**
 * Component that outputs extracted metadata to some destination, ex. to the HTML file.
 */
interface OutputDestination {
    fun isValidParams(params: Map<String, String>): Boolean

    /**
     * Output extracted metadata to the supported destination.
     */
    fun output(metadata: DatabaseMetadata, params: Map<String, String>): Collection<Output>

    /**
     * Destination type.
     */
    fun getDestinationType(): String
}