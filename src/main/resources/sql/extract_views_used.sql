select owner || '.' || name AS referencing_object,
       type as referencing_type,
       referenced_owner || '.' || referenced_name as referenced_object,
       referenced_type
from sys.all_dependencies
where name = ?
      and owner = ?
order by referencing_object