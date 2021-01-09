package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import java.util.Collection;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TableMetadata {
  private String tableName;
  private String schemaName;
  private Collection<ColumnModel> columns;
  private Collection<PrimaryKeyModel> primaryKeys;
  private Collection<ForeignKeyModel> foreignKeys;
  private Collection<ConstraintModel> constraints;
  private Collection<ReferenceModel> references;
  private Collection<CheckModel> checks;
  private Collection<TriggerModel> triggers;
  private Collection<UdfModel> udfs;
  private Collection<ViewUsedModel> viewsUsed;
  private Collection<ViewReferencedModel> viewsReferenced;
  private Collection<TypeModel> types;
  private Collection<UniqueKeyModel> uniqueKeys;
  private Collection<ScriptModel> scripts;
}
