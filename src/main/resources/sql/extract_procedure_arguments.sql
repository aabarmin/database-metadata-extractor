select proc.owner as schema_name,
       proc.object_name as procedure_name,
       args.argument_name,
       args.in_out,
       args.data_type,
       args.data_length,
       args.data_precision,
       args.data_scale,
       args.defaulted,
       args.default_value
from sys.all_procedures proc
left join sys.all_arguments args
    on proc.object_id = args.object_id
where proc.owner = ?
      and object_type = 'PROCEDURE'
order by schema_name,
         procedure_name,
         args.position