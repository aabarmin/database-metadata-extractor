select proc.owner as schema_name,
       proc.object_name as procedure_name,
       LISTAGG(args.argument_name || ' ' || args.in_out  ||
                ' ' || args.data_type, '; ')
              WITHIN GROUP (ORDER BY position) as arguments
from sys.all_procedures proc
left join sys.all_arguments args
    on proc.object_id = args.object_id
where proc.owner = ?
      and object_type = 'PROCEDURE'
group by proc.owner,  proc.object_name