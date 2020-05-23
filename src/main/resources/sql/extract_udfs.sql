 select obj.owner as schema_name,
       obj.object_name as function_name,
       ret.data_type as return_type,
       LISTAGG(args.in_out || ' ' || args.data_type, '; ')
              WITHIN GROUP (ORDER BY position) as arguments
from sys.all_objects obj
join sys.all_arguments args on args.object_id = obj.object_id
join (
      select object_id,
             object_name,
             data_type
      from sys.all_arguments
      where position = 0
) ret on ret.object_id = args.object_id
       and ret.object_name = args.object_name
where obj.object_type = ?
      and obj.owner = ?
      and args.position > 0
group by obj.owner,
         obj.object_name,
         ret.data_type
order by schema_name,
         function_name