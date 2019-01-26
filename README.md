# Otus Spring Framework Developer

- Student: Andrei Bogomja
- Course: Nov-2018

## Homework 5. DAO with Spring ORM + JPA

- Create library application
  - Use JPA and Hibernate only as JPA provider
  - Add comments to the books
  - Cover DAO with tests using H2 database

## How to get the project running

Clone repository from GitHub:

```shell
git clone git@github.com:ifqthenp/otus-spring-hw-05-spring-jpa
```

Change into the cloned folder:

```shell
cd otus-spring-hw-05-spring-jpa
```

Make `gradlew` script executable (or use `gradlew.bat` if running on Windows):

```shell
chmod +x gradlew 
```

Build executable `jar`:

```shell
./gradlew clean test bootJar
```

Run the program:

```shell
java -jar build/libs/otus-spring-hw-05-spring-jpa.jar
```
