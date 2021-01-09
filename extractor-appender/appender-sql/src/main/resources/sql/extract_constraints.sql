  SELECT
       con.CONSTRAINT_TYPE,
       acc.owner as schema_name,
       acc.constraint_name,
       acc.table_name,
       LISTAGG(acc.column_name,',')
              WITHIN GROUP (order by acc.position) as columns,
       con.R_OWNER,
       con.R_CONSTRAINT_NAME
  FROM sys.all_constraints con
  join sys.all_cons_columns acc on con.owner = acc.owner
     and con.constraint_name = acc.constraint_name
 WHERE con.constraint_type NOT IN ('P', 'R')
   AND acc.table_name = ?
   AND acc.owner = ?
  group by con.CONSTRAINT_TYPE,
         acc.owner,
         acc.table_name,
         acc.constraint_name,
         con.R_OWNER,
         con.R_CONSTRAINT_NAME
order by acc.owner,
         acc.constraint_name