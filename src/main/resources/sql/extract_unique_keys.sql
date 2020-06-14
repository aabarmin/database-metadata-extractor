SELECT * FROM (
select constr.owner,
       constr.constraint_name,
                         col_const.table_name,
                         col_const.column_name,
                         'UK' unique_key
                    from all_constraints constr
                         inner join all_cons_columns col_const
                             on constr.constraint_name = col_const.constraint_name
                            and constr.owner = col_const.owner
                   where constr.constraint_type = 'U'
                   union
                  select ind.owner,
                  ind.index_name as constraint_name,
                         col_ind.table_name,
                         col_ind.column_name,
                         'UK' unique_key
                    from all_indexes ind
                         inner join all_ind_columns col_ind
                            on ind.index_name = col_ind.index_name
                   where ind.uniqueness = 'UNIQUE'
) i
                   WHERE i.table_name = ?
                   AND  i.owner = ?

