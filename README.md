# JsonDiff

## Introduction

JsonDiff is a Java application based on SpringBoot that calculates the difference between two jsons Strings Base64 encoded received via REST interfaces. The difference is then calculated and returned.

## Usage

JsonDiff provides two POST Rest resources to receive the encoded json as input:

 - /v1/diff/<id>/left
 - /v1/diff/<id>/right
 
Those interfaces expect the following format as input:

```
{
    "inputString":"<ENCODED_JSON_STRING>"
}
```

Where the `ENCODED_JSON_STRING` is a one-line JSON String Base64 encoded.

After receiving both inputs, left and right, the diff between the jsons can be retrieved using the GET Rest resource: 

 - /v1/diff/<id>
 
This will retrieve the following result:

```
{
    "id": "<ID>",
    "leftInput":"<ENCODED_LEFT_INPUT>",
    "rightInput":"<ENCODED_RIGHT_INPUT>",
    "diffResult":"<EQUAL|DIFFERENT_SIZES|DIFFERENT_CONTENTS>",
    "diffs":[]
}
```

The diff can return one of three results:

 - EQUAL - Left and Right inputs are equal;
 - DIFFERENT_SIZES - The inputs have different sizes;
 - DIFFRENTE_CONTENTS - The inputs have the same size, however different content.
 
In the last case, a list containing the offset and length of the diff between the inputs is retrieved, on the array `diffs`.

There are some limitations on the diff:

 - Spaces are considered, so `{"a" : "b"}` is different of `{"a":"b"}`;
 - The diff is made on the complete json, not by parameters. For example, `{"abc":"123"}` and `{"abcd":"23"}` will show that they differ by`":"1` / `d":"`;  

## Running JsonDiff

JsonDiff uses maven, so you can compile and run the application using:

```
mvn package && mvn exec:java
```

This will compile, run unit tests, package the jar file and run the application.

To run the tests (unit and integration tests), you can run:

```
mvn verify
```

This will execute all tests of the project.

## Future Improvements

This project is still in development, and there are some possible improvements:

 - Right now, the data is saved in a Map object, and this can cause problems on memory usage, for example. This can be soled later using a database;
 - The diff can be improved to ignore whitespaces and focus only on the json itself;
 - The diff can be improved to diff the json structure, like show different order of parameters, different values for parameters, and so on.
