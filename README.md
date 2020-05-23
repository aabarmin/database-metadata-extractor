# Database Metadata Extract

![](https://github.com/aabarmin/database-metadata-extractor/workflows/gradle/badge.svg)

A simple tool that allows extraction of metadata from the Database Metadata Tables. 

## How to build the tool

In order to build the tool use the following command:

```shell script
./gradlew clean build
```

If the system says that `gradlew` is not executable, do the following:

```shell script
$ chmod +x ./gradlew
```

As a result you'll have a file `metadata-extractor.jar` in the `build/libs` folder:

```shell script
$ tree build/libs

build/libs
└── metadata-extractor.jar

0 directories, 1 file
```

## Tool configuration

The tool is configured using the json configuration with the following syntax:

```json
{
  "schemas": [
    "HR",
    "FLOW_FILES"
  ],
  "tables": {
    "HR": [
      "DEPARTMENTS"
    ]
  }
}
```

The `schemas` section contains list of schemas to extract metadata from, the `tables` section contains
a list of schemas and tables inside them. 

## How to run the tool

The simplest way to run the tool is by using the command line:

```shell script
$ java -jar metadata-extractor.jar \
    --spring.datasource.url=<database connection string> \
    --spring.datasource.username=<database username> \ 
    --spring.datasource.password=<database password> \
    --output.html.folder=<folder name to output results> \
    --output.html.mode=<use "single" for genration one file for database and table>
    --parameters.file=<params filepath>
```

Connection string example for the Oracle database: `jdbc:oracle:thin:@localhost:51521:xe`

## Publication of metadata to Confluence Cloud

To publish metadata to Confluence Cloud add the following parameters to the command line:

```shell script
$ java -jar metadata-extractor.jar \
    ... rest parameters ... \
    --output.target=confluence \
    --confluence.space=<confluence space name> \
    --confluence.host=<cloud confluence host like example.attlassian.net> \
    --confluence.username=<confluence username> \
    --confluence.password=<confluence API Token> \
    --confluence.parent.page.id=<ID of parent page or skip to create top-level pages>
```

## Running database in Docker for testing purposes

In order to run Oracle 11g XE for testing purposese use the following command:

```shell script
$ ./run_database.sh
```

If the system says that the file is not executable do the following:

```shell script
$ chmod +x ./run_database.sh
```