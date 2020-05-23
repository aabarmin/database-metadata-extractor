select col.column_id,
       col.owner as schema_name,
       col.table_name,
       col.column_name,
       col.data_type,
       col.data_length,
       col.data_precision,
       col.data_scale,
       col.nullable,
       col.data_default as default_definition,
       com.COMMENTS
from sys.all_tab_columns col
LEFT JOIN ALL_COL_COMMENTS com ON (col.TABLE_NAME = com.TABLE_NAME AND col.COLUMN_NAME = com.COLUMN_NAME )
where col.table_name = ? and col.owner = ?
order by col.column_id