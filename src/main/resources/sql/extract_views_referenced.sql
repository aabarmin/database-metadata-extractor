select owner as view_schema,
       name as view_name,
       referenced_owner as referenced_schema,
       referenced_name as referenced_name,
       referenced_type
from sys.all_dependencies
where type='VIEW'
      and referenced_type in ('TABLE','VIEW')
      and name = ?
      and owner = ?
order by owner,
         view_name