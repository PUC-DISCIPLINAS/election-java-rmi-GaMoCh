FROM openjdk:8u212-jdk-alpine3.9 AS build
WORKDIR /srv
RUN mkdir bin
COPY src src
RUN javac -d bin -sourcepath src src/*.java src/**/*.java

FROM openjdk:8u212-jre-alpine3.9
WORKDIR /srv
RUN mkdir /data
ENV STORAGE_PATH=/data/storage.data
COPY --from=build /srv/bin bin
COPY data data
ENTRYPOINT [ "java", "-cp", "bin" ]
CMD [ "Client" ]
