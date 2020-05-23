  select ctr.owner as schema_name,
       ctr.constraint_name,
       ctr.table_name,
       col.column_name,
       ctr.search_condition as constraint,
       ctr.status
from sys.all_constraints ctr
join sys.all_cons_columns col
     on ctr.owner = col.owner
     and ctr.constraint_name = col.constraint_name
     and ctr.table_name = col.table_name
where ctr.constraint_type = 'C'
AND CTR.TABLE_NAME = ? and ctr.owner = ?
order by ctr.owner, ctr.table_name, ctr.constraint_name