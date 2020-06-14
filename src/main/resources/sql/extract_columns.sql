select col.owner as schema_name,
       col.table_name,
       col.COLUMN_ID,
       col.column_name,
       col.data_type,
       decode(char_length,
              0, data_type,
              data_type || '(' || char_length || ')') as data_type_ext,
       col.data_length,
       col.data_precision,
       col.data_scale,
       col.nullable,
       col.data_default as default_value,
       nvl(pk.primary_key, ' ') as primary_key,
       nvl(fk.foreign_key, ' ') as foreign_key,
       nvl(uk.unique_key, ' ') as unique_key,
       nvl(check_const.check_constraint, ' ') check_constraint,
       comm.comments
  from all_objects tab
       inner join all_tab_columns col
           on col.owner = tab.owner
          and col.table_name = tab.object_name
       left join all_col_comments comm
           on col.owner = comm.owner
          and col.table_name = comm.table_name
          and col.column_name = comm.column_name
       left join (select constr.owner,
                         col_const.table_name,
                         col_const.column_name,
                         'PK' primary_key
                    from all_constraints constr
                         inner join all_cons_columns col_const
                             on constr.constraint_name = col_const.constraint_name
                            and col_const.owner = constr.owner
                   where constr.constraint_type = 'P') pk
           on col.table_name = pk.table_name
          and col.column_name = pk.column_name
          and col.owner = pk.owner
       left join (select constr.owner,
                         col_const.table_name,
                         col_const.column_name,
                         'FK' foreign_key
                    from all_constraints constr
                         inner join all_cons_columns col_const
                             on constr.constraint_name = col_const.constraint_name
                            and col_const.owner = constr.owner
                   where constr.constraint_type = 'R'
                   group by constr.owner,
                            col_const.table_name,
                            col_const.column_name) fk
           on col.table_name = fk.table_name
          and col.column_name = fk.column_name
          and col.owner = fk.owner
       left join (select constr.owner,
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
                         col_ind.table_name,
                         col_ind.column_name,
                         'UK' unique_key
                    from all_indexes ind
                         inner join all_ind_columns col_ind
                            on ind.index_name = col_ind.index_name
                   where ind.uniqueness = 'UNIQUE') uk
           on col.table_name = uk.table_name
          and col.column_name = uk.column_name
          and col.owner = uk.owner
       left join (select constr.owner,
                         col_const.table_name,
                         col_const.column_name,
                         'C' check_constraint
                    from all_constraints constr
                         inner join all_cons_columns col_const
                             on constr.constraint_name = col_const.constraint_name
                            and col_const.owner = constr.owner
                   where constr.constraint_type = 'C'
                   group by constr.owner,
                         col_const.table_name,
                         col_const.column_name) check_const
           on col.table_name = check_const.table_name
          and col.column_name = check_const.column_name
          and col.owner = check_const.owner
 where
 tab.object_type in ('TABLE', 'VIEW')
 AND col.table_name = ?
 and    col.owner = ?
 order by col.owner,
       col.table_name,
       col.COLUMN_ID