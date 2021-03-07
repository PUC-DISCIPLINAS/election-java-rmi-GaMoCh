# Election

## Author

[Gabriel Moreira Chaves](https://github.com/GaMoCh)

## Requirements

- Java Development Kit 8 (JDK 8) or Docker

## Client commands

- **`vote <candidateId>`:** Used to vote for the candidate
- **`result <candidateId>`:** Used to see the candidate's number of votes
- **`exit`:** Used to exit the system

## Run with javac/java

### Compile code

```sh
javac -d bin -sourcepath src src/*.java src/**/*.java
```

### Run a server service

```sh
java -cp bin Server
```

### Run a client service

```sh
java -cp bin Client
```

## Run with Docker

### Authenticate to GitHub Packages

```sh
echo <READ_PACKAGES_TOKEN> | docker login docker.pkg.github.com -u <USERNAME> --password-stdin
```

### Pull Docker image

```sh
docker pull docker.pkg.github.com/puc-disciplinas/election-java-rmi-gamoch/election
```

### Rename Docker image

```sh
docker tag docker.pkg.github.com/puc-disciplinas/election-java-rmi-gamoch/election election
```

### Run a server service

```sh
docker run -it --rm --net host -v election-data:/data election Server
```

### Run a client service

```sh
docker run -it --rm --net host election Client
```
