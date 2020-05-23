 select owner as trigger_schema_name,
       trigger_name,
       trigger_type,
       triggering_event,
       table_owner as schema_name,
       table_name as object_name,
       base_object_type as object_type,
       status,
       trigger_body as script
from sys.all_triggers
where TABLE_NAME = ? and owner = ?
order by trigger_name,
         table_owner,
         table_name,
         base_object_type