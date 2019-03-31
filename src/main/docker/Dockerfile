FROM openjdk:8
ADD zno-online.war app.war
RUN bash -c 'touch /app.war'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=dev", "-jar", "/app.war"]