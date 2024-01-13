# POO-slither.io

## Introduction

This project is a Java implementation of the popular games [slither.io](https://en.wikipedia.org/wiki/Slither.io) and [snake](https://en.wikipedia.org/wiki/Snake_(video_game_genre)). 

It was developed as part of the Object-Oriented Programming Complementary course at the University of Paris. The goal of this project was to implement a game using the frameworks like Swing or JavaFX and to apply the concepts of object-oriented programming learned during the course.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Ensure you have `Gradle` installed on your machine. If not, you can use the Gradle Wrapper included in the project (`./gradlew`).

### Running the Project

To run the project, execute the following commands in your shell:

```bash
gradle clean
gradle build
gradle run
```

The same commands can be used to run the project using the Gradle Wrapper (`./gradlew`).

## Generating UML Diagrams

To generate UML diagrams, execute the following command in your shell:

```
> gradlew generateClassDiagrams
```

The generated diagrams will be located in the `app/docs/uml` directory.

## Running the Tests

To run the tests, execute the following commands in your shell:

```bash
gradle clean
gradle build
gradle test
```

The same commands can be used to run the tests using the Gradle Wrapper (`./gradlew`).

## Authors

- **PARIS** Albin
- **YAZICI** Servan
