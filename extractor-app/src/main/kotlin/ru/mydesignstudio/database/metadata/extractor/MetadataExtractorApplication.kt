package ru.mydesignstudio.database.metadata.extractor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MetadataExtractorApplication

fun main(args: Array<String>) {
    runApplication<MetadataExtractorApplication>(*args)
}