# Database Metadata Extract

![](https://github.com/aabarmin/database-metadata-extractor/workflows/Java%20CI%20with%20Gradle/badge.svg)

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
$ tree extractor-app/build/libs

extractor-app/build/libs
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

Two types of Confluence servers are supported: standalone and cloud. Add `--confluence.type=cloud`
to use Confluence instance hostead at `atlassian.net` and `--confluence-type=server` for on-premise
instance. 

## Running database in Docker for testing purposes

In order to run Oracle 11g XE for testing purposes use the following command:

```shell script
$ ./run_database.sh
```

If the system says that the file is not executable do the following:

```shell script
$ chmod +x ./run_database.sh
```

## Running local Confluence server in Docker for testing purposes

In order to run Confluence Server for testing purposes use the following command:

```shell script
$ ./run_confluence.sh
```

If the file is not executable do the following:

```shell script
$ chmod +x ./run_confluence.sh
```

## Working with labels

By default, the system adds Confluence labels to the following artifacts:
* Tables
* Views
* Diagrams
* Databases

At the same time, it's possible adding labels for every element like a common label.

The following properties are responsible for it:

| Property | Label |
| --- | --- |
| `confluence.label.common.values` | Comma-separated list of common labels |
| `confluence.label.table.values` | Comma-separated list of labels to be added to tables |
| `confluence.label.view.values` | Comma-separated list of labels to be added to views |
| `confluence.label.diagram.values` | Comma-separated list of labels to be added to every diagram |
| `confluence.label.database.values` | Comma-separated list of labels to be added to DB descriptions |

All these parameters could be overwritten using the command-line arguments:

```shell script
$ java -jar metadata-extractor.jar \
    ... rest parameters ... \
    --confluence.label.common.values=common1,common2,common3 \
    --confluence.label.table.values=table1,table2,table3
```
