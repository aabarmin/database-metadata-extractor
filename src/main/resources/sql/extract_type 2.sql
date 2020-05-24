select owner,
       object_name as table_name,
       object_type
from   all_objects
where  object_type in ('TABLE', 'VIEW')
and    table_name = ?