# Docker
build
```shell
docker build -t vvm .
```

run
```shell
docker run --rm -d -p 8081:8081 -p 9010:9010 -p 1099:1099 --name vm vvm
```

stop
```shell
docker stop vm
```

# Docker compose
```shell
docker-compose build
```

```shell
docker-compose up
```