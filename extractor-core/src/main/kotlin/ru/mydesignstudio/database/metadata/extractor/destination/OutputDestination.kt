package ru.mydesignstudio.database.metadata.extractor.destination

import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata

/**
 * Component that outputs extracted metadata to some destination, ex. to the HTML file.
 */
interface OutputDestination {
    /**
     * Output extracted metadata to the supported destination.
     */
    fun output(metadata: DatabaseMetadata)

    /**
     * Destination type.
     */
    fun getDestinationType(): String
}