package ru.mydesignstudio.database.metadata.extractor.extract.helper

interface QueryProvider {
    fun provide(name: String): String
}