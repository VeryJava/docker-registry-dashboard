FROM java:8-jre

ADD ./target/docker-registry-dashboard-1.0-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/docker-registry-dashboard-1.0-SNAPSHOT.jar"]

EXPOSE 13028