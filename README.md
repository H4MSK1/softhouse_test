# Softhouse test
A test provided by Softhouse Consulting

## Getting started
### Prerequisites
- Java 8 JDK

## Running the existing executable JAR file
```cli
cd executable
java -jar softhouse_test-1.0-SNAPSHOT.jar
```

## Running the file converter

Make sure that the person_data.txt is located in the same folder as the JAR file itself.

```bash
java -jar softhouse_test-1.0-SNAPSHOT.jar

# OR if you want to specify the source file and output destination as following:
# java -jar softhouse_test-1.0-SNAPSHOT.jar <source file> <output file>
# Default value <source file> = person_data.txt
# Default value <output file> = output.xml
```

## For development

### Build using Gradle (Windows)

The following command will generate an executable JAR file in `/build/libs/`.
Open your terminal and run

```cli
gradlew.bat uberJar
```

### Build using Gradle (Unix)

The following command will generate an executable JAR file in `/build/libs/`.
Open your terminal and run

```cli
./gradlew uberJar
```

## Running tests
```cli
./gradlew test
```

## Generating test coverage reports

The test reports will be generated in `/build/reports/jacoco/test/`
```cli
./gradlew jacocoTestReport
```

------------------------------------------------------------------------------------------------------

#### Instruktioner

Filformat:

```
P|förnamn|efternamn
T|mobilnummer|fastnätsnummer
A|gata|stad|postnummer
F|namn|födelseår
P kan följas av T, A och F
F kan följas av T och A
```

Exempel:

```
P|Carl Gustaf|Bernadotte
T|0768-101801|08-101801
A|Drottningholms slott|Stockholm|10001
F|Victoria|1977
A|Haga Slott|Stockholm|10002
F|Carl Philip|1979
T|0768-101802|08-101802
P|Barack|Obama
A|1600 Pennsylvania Avenue|Washington, D.C
```

Ger XML som:

```xml
<people>
  <person>
    <firstname>Carl Gustaf</firstname>
    <lastname>Bernadotte</lastname>
    <address>
      <street>Drottningholms slott</street>
      ...
     </address>
    <phone>
      <mobile>0768-101801</mobile>
      ...
    </phone>
    <family>
      <name>Victoria</name>
        <born>1977</born>
        <address>...</address>
     </family>
     <family>...</family>
  </person>
  <person>...</person>
</people>
```
