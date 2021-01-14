select obj.owner,
       obj.object_name,
       obj.object_type,
       comm.comments
from   all_objects obj
left outer join all_tab_comments comm
           on obj.object_name = comm.table_name
          and obj.owner = comm.owner
where  obj.object_type in ('TABLE', 'VIEW')
and    obj.object_name = ?
and    obj.owner = ?
