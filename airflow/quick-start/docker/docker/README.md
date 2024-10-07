https://airflow.apache.org/docs/docker-stack/build.html#adding-new-apt-package

# Building the image
1. Adding the `apt` package
2. Adding a new `PyPI` dependency
3. embedding DAGs into the image

### 1. Adding the `apt` package
It's important to define user as `root` before installing package
and switch back to `airflow` user after installation is complete
```shell
FROM apache/airflow:2.10.2
USER root
RUN apt-get update \
  && apt-get install -y --no-install-recommends vim \
  && apt-get autoremove -yqq --purge \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/*
USER airflow
```
- `--no-install-recommends`: ensure only essential package are installed
- `autoremove`: remove unnecessary package automatically which no longer used
- `-qq`: quite mode
- `--purge`: remove configuration file associated with unnecessary package
- `clean`: remove local copies of packages file form no longer needed packages. optimize docker image size
- `/var/lib/apt/lists/*`: package list files. after installation these file no longer need

### 2. adding new PyPI package with constraints
When new packages have conflicting dependency, pip might decided to downgrade or upgrade airflow version
If airflow depend on some package which have a conflicting dependency, pip decide to resolve
```shell
FROM apache/airflow:2.10.2
RUN pip install --no-cache-dir "apache-airflow==${AIRFLOW_VERSION}" lxml
```
It's not necessary but good practice that you install apache-airflow with same version as image
This way you can ensure that the version you are using is the same version as one you are extending
If new package has a conflicting dependency, pip might decide to upgrade or downgrade airflow dependency automatically
So, adding version of airflow is good practice
This way if you have a conflicting requirements, *you will get an error message*

### 2. Adding packages with requirements.txt
```shell
FROM apache/airflow:2.10.2
COPY requirements.txt /
RUN pip install --no-cache-dir "apache-airflow==${AIRFLOW_VERSION}" -r /requirements.txt
```

### 2. adding PyPI package with constraints
https://airflow.apache.org/docs/docker-stack/build.html#adding-new-apt-package

You can use PyPI with constraint to install certain version of packages depend on Python version
1. It allow you to install specific version of packages
2. It also not allow to install potentially newer version that were released after the version of Airflow that you are using

```shell
FROM apache/airflow:2.10.2
RUN pip install --no-cache-dir "apache-airflow==${AIRFLOW_VERSION}" lxml
```
- `--no-cache-dir`: it not allow docker to cache or install ache of packages to keep image smaller

### 3. adding packages from requirements.txt
```shell
FROM apache/airflow:2.10.2
COPY requirements.txt /
RUN pip install --no-cache-dir "apache-airflow==${AIRFLOW_VERSION}" -r /requirements.txt
```

### 4. embedding DAGs
```shell
FROM apache/airflow:2.10.2

COPY --chown=airflow:root test_dag.py /opt/airflow/dags
```
DAGs file should be placed under `/opt/airflow/dags` which is Airflow default home directory

### 5. Add Airflow configuration with environment variables
add configuration to image. `airflow.cfg` (cfg: configuration) in $AIRFLOW_HOME directory
Airflow configuration by using this format: `AIRFLOW__{SECTION}__{KEY}`
```shell
FROM apache/airflow:2.10.2
ENV AIRFLOW__CORE__LOAD_EXAMPLES=True
ENV AIRFLOW__DATABASE__SQL_ALCHEMY_CONN=my_conn_string
```
# 2. Build container
```shell
docker rmi -f airflow-test-image:0.0.1
docker build . -f Dockerfile --pull --tag airflow-test-image:0.0.1
docker images | grep airflow-test-image
```
- `--pull`: force docker to download latest version of base image even if an image with the same name exists locally. ensure most updated

run image
```shell
docker rm airflow-test
docker run --rm --name airflow-test airflow-test-image:0.0.1
```
https://stackoverflow.com/questions/68477312/error-when-starting-custom-airflow-docker-image-group-or-command
If you run the image, it will by default try to run `airflow` command
