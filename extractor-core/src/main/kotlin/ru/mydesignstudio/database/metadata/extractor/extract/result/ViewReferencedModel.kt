package ru.mydesignstudio.database.metadata.extractor.extract.result

data class ViewReferencedModel(val referencingObject: String,
                               val referencingType: String,
                               val referencedObject: String,
                               val referencedType: String)