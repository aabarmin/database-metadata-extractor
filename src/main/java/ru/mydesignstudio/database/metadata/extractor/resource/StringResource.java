package ru.mydesignstudio.database.metadata.extractor.resource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface StringResource {
  String value();
}
