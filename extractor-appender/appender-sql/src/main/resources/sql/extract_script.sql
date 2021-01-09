select owner as schema_name,
       view_name,
       text as script
from sys.all_views
where owner = ?
      and view_name = ?
order by owner,
         view_name