SELECT a.table_name child_table, a.column_name child_column, a.constraint_name,
      b.table_name parent_table, b.column_name parent_column
  FROM all_cons_columns a
  JOIN all_constraints c ON a.owner = c.owner AND a.constraint_name = c.constraint_name
 join all_cons_columns b on c.owner = b.owner and c.r_constraint_name = b.constraint_name
 WHERE c.constraint_type = 'R'
   AND a.table_name = ?
   AND a.OWNER = ?