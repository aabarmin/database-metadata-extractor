select owner || '.' || name as referencing_object,
       type as referencing_type,
       referenced_owner || '.' || referenced_name as referenced_object,
       referenced_type
from sys.all_dependencies
where referenced_name = ?
      and referenced_owner = ?
order by referencing_object