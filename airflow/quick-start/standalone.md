# Standalone
https://airflow.apache.org/docs/apache-airflow/stable/start.html

## 1. Set Airflow Home
```shell
export AIRFLOW_HOME=~/airflow
echo $AIRFLOW_HOME
```

## 2. Install Airflow
Set Airflow version
```shell
AIRFLOW_VERSION=2.10.2
echo $AIRFLOW_VERSION
```

Set Python version
```shell
# check python version
python --version
PYTHON_VERSION="$(python -c 'import sys; print(f"{sys.version_info.major}.{sys.version_info.minor}")')"
echo $PYTHON_VERSION
```
> `-c` option enable you to run python script without file

Set constrain url
```shell
CONSTRAINT_URL="https://raw.githubusercontent.com/apache/airflow/constraints-${AIRFLOW_VERSION}/constraints-${PYTHON_VERSION}.txt"
echo $CONSTRAINT_URL
```
Install Airflow
```shell
pip install "apache-airflow==${AIRFLOW_VERSION}" --constraint "${CONSTRAINT_URL}"
```
> `--constraint`: restricts the version of dependencies. tell pip which version of dependency to use

### Run Airflow standalone
Airflow standalone initialize database, create user, start all components
```shell
airflow standalone
```
### Access the Airflow UI
http://localhost:8080

```shell
# show standalone password
cat $AIRFLOW_HOME/standalone_admin_password.txt
```

Standalone will create the $AIRFLOW_HOME folder. Out of box, Airflow uses `SQLight` database. 
```shell
cat $AIRFLOW_HOME/airflow.cfg
```
You can run commands that will trigger tasks
```shell
airflow tasks test example_bash_operator runme_0 2015-01-01
```
```shell
airflow dags backfill example_bash_operator --start-date 2015-01-01 --end-date 2015-01-02
```

# Stop Airflow
```shell
lsof -i :8080
```