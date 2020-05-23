select referenced_owner || '.' || referenced_name as table_name,
       referenced_type as type,
       owner || '.' || name as referencing_object,
       type as referencing_type
from sys.all_dependencies
where referenced_type in('TABLE', 'VIEW')
      and referenced_name = ?
      and referenced_owner = ?
order by referencing_object