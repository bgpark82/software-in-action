FROM openjdk:21-jdk-slim

COPY *.jar /app/myapp.jar

# 포트 8080은 애플리케이션, 9010은 JMX, 1099는 RMI 포트입니다.
EXPOSE 8080 9010 1099

# JMX 및 RMI 관련 환경 변수 설정
ENV JAVA_OPTS="-Dcom.sun.management.jmxremote \
               -Dcom.sun.management.jmxremote.port=9010 \
               -Dcom.sun.management.jmxremote.rmi.port=9010 \
               -Dcom.sun.management.jmxremote.authenticate=false \
               -Dcom.sun.management.jmxremote.ssl=false \
               -Djava.rmi.server.hostname=0.0.0.0 \
               -Dcom.sun.management.jmxremote.local.only=false \
               -Djava.rmi.server.port=1099 \
               -Xms512m -Xmx512m"

# 애플리케이션을 실행합니다.
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/myapp.jar"]