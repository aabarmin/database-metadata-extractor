select owner,
       object_name,
       object_type
from   all_objects
where  object_type in ('TABLE', 'VIEW')
and    object_name = ? and owner = ?