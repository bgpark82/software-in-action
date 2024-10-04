# Manual
### Migrate DB
```shell
airflow db migrate
```
### Create user
```shell
airflow users create \
    --username peter \
    --firstname Peter \
    --lastname Parker \
    --role Admin \
    --email spiderman@superhero.org
```
### Run webserver
When the web server is running, multiple worker start to initialize
```shell
airflow webserver --port 8080
```
### Run scheduler
Scheduler monitor DAGs, schedule tasks, handle task retries and backfill
```shell
airflow scheduler
```