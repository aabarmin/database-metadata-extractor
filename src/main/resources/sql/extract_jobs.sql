select owner as schema_name,
       job_name,
       job_style,
       case when job_type is null
                 then 'PROGRAM'
            else job_type end as job_type,
       case when job_type is null
                 then program_name
                 else job_action end as job_action,
       start_date,
       case when repeat_interval is null
            then schedule_name
            else repeat_interval end as schedule,
       last_start_date,
       next_run_date,
       state
from sys.all_scheduler_jobs
WHERE owner = ?
order by owner,
         job_name