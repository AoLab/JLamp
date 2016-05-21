# JLamp - JAVA implementation of lamp project!
## Introduction
Java implementation of the Lamp project.

## Installation
Inorder to use this project you must:  
1. Generate sdk from the KAA admin UI.(For both JLamp-RPi and JLamp-REST.)   
2. Rename it to kaa-java-sdk-rpi and kaa-java-sdk-rest;  
3. From the commandline run the below commands:  

```shell
mvn install:install-file -Dfile=kaa-java-sdk-rpi.jar -DgroupId=ir.ac.aut.ceit.aolab.jlamp -DartifactId=kaa-sdk-java-rpi -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
```

```shell
mvn install:install-file -Dfile=kaa-java-sdk-rest.jar -DgroupId=ir.ac.aut.ceit.aolab.jlamp -DartifactId=kaa-sdk-java-rest -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
```

**Note** that when running the above command the current directory must be the same as the generated sdk.

## JLamp - REST
### Introduction
This project contains web service functions required for turning the lamps on and off.

### Compile
If you're trying to compile this on your own simply `mvn compile`.

### Note for eclipse users
Because this project uses oracle sun related java packages it's access by default
is disabled and the autocompletion does not appear.
In order to fix this problem visit
[this](http://stackoverflow.com/questions/13155734/eclipse-cant-recognize-com-sun-net-httpserver-httpserver-package)
stackoverflow question. Second Answer :)

## REST API  
### On for Interval  
Turn the lamp on for the specified interval  
| Parameter  | Value           | Description                   |
|:------------:|:-----------------:|:-------------------------------|
| id         | String two char | the id of the destination lamp|
* URL : /lamp/OnI  
* Method : ```POST```
* JSON params:  

## Architecture
To see the awesome architecture of this project visit [AoLab/Lamp](https://github.com/AoLab/Lamp) 
