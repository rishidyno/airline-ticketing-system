# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add Maintainer Info
LABEL maintainer="lit2020054@iiitl.ac.in"

WORKDIR /app

COPY . .

## Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

CMD ["./mvnw","clean"]

CMD ["./mvnw","compile:compile"]

CMD ["./mvnw","package"]

#CMD ["./mvnw","deploy"]

#RUN <<EOF
#echo $(pwd)
#echo $(ls /app/target)
#echo $(ls /app)
#EOF

## Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/target/airline-ticketing-system-0.0.1-SNAPSHOT.jar"]