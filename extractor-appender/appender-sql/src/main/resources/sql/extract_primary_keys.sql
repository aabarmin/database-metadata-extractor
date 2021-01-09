  select
       'PK' AS primary_key,
       acc.owner as schema_name,
       acc.constraint_name,
       acc.table_name,
       LISTAGG(acc.column_name,',')
              WITHIN GROUP (order by acc.position) as columns
from sys.all_constraints con
join sys.all_cons_columns acc on con.owner = acc.owner
     and con.constraint_name = acc.constraint_name
where con.constraint_type = 'P'
      AND acc.table_name = ?
      AND acc.owner = ?
group by acc.owner,
         acc.table_name,
         acc.constraint_name
order by acc.owner,
         acc.constraint_name