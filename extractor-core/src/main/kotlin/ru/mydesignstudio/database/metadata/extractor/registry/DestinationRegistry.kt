package ru.mydesignstudio.database.metadata.extractor.registry

import ru.mydesignstudio.database.metadata.extractor.destination.OutputDestination

interface DestinationRegistry {
    /**
     * Register a given destination.
     */
    fun register(destination: OutputDestination)

    /**
     * Check if an output for the given destination is registered.
     */
    fun hasDestination(destination: String): Boolean

    /**
     * Get component to output to the given destination.
     */
    fun getDestination(destination: String): OutputDestination
}