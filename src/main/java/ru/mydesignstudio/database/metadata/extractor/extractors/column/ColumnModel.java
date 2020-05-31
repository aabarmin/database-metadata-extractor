package ru.mydesignstudio.database.metadata.extractor.extractors.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.thymeleaf.util.StringUtils;

@Data
@EqualsAndHashCode(of = "columnId")
public class ColumnModel {
  private int columnId;
  private String schemaName;
  private String tableName;
  private String columnName;
  private String dataType;
  private int dataLength;
  private Integer dataPrecision;
  private Integer dataScale;
  private String nullable;
  private String defaultDefinition;
  private String comments;

  public boolean isNullable() {
    return StringUtils.equals("Y", getNullable());
  }
}
