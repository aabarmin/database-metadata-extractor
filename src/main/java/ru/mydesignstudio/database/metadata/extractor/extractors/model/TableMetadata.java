package ru.mydesignstudio.database.metadata.extractor.extractors.model;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import ru.mydesignstudio.database.metadata.extractor.extractors.checks.CheckModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.column.ColumnModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.constraint.ConstraintModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.fk.ForeignKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.pk.PrimaryKeyModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.reference.ReferenceModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.trigger.TriggerModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.udf.UdfModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.view.referenced.ViewReferencedModel;
import ru.mydesignstudio.database.metadata.extractor.extractors.view.used.ViewUsedModel;

@Data
@ToString
public class TableMetadata {
  private String tableName;
  private String schemaName;
  private List<ColumnModel> columns;
  private List<PrimaryKeyModel> primaryKeys;
  private List<ForeignKeyModel> foreignKeys;
  private List<ConstraintModel> constraints;
  private List<ReferenceModel> references;
  private List<CheckModel> checks;
  private List<TriggerModel> triggers;
  private List<UdfModel> udfs;
  private List<ViewUsedModel> viewsUsed;
  private List<ViewReferencedModel> viewsReferenced;
}
