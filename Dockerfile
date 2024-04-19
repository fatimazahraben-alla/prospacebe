FROM openjdk:17-alpine
ARG JAR_FILE=target/prospace-0.0.1-SNAPSHOT.jar
ARG USER=gomuser
ARG USER_ID=802
ARG USER_GROUP=gom
ARG USER_GROUP_ID=802
ARG USER_HOME=/home/${USER}
RUN \
    addgroup -S -g ${USER_GROUP_ID} ${USER_GROUP} \
    && adduser -S -u ${USER_ID} -h ${USER_HOME} -G ${USER_GROUP} ${USER}
RUN mkdir -p /etc/app
COPY ${JAR_FILE} /etc/app/app.jar
EXPOSE 8091
RUN chown ${USER}:${USER_GROUP} -R /etc/app
USER ${USER_ID}
WORKDIR /etc/app
ENTRYPOINT ["java","-jar","/etc/app/app.jar"]
