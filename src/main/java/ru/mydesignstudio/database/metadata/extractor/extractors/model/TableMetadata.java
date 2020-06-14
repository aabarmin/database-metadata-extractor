package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import java.util.Collection;
import lombok.Data;
import lombok.ToString;
import ru.mydesignstudio.database.metadata.extractor.extractors.checks.CheckModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.column.ColumnModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.constraint.ConstraintModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.fk.ForeignKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.pk.PrimaryKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.reference.ReferenceModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.trigger.TriggerModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.type.TypeModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.udf.UdfModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.uk.UniqueKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.view.referenced.ViewReferencedModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.view.used.ViewUsedModel;

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
}
