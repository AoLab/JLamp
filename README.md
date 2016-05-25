# JLamp - JAVA implementation of lamp project!
## Introduction
Java implementation of the Lamp project.

**Note** that when running the above command the current directory must be the same as the generated sdk.

## JLamp - REST
### Introduction
This project contains web service functions required for turning the lamps on and off.

### Note for eclipse users
Because this project uses oracle sun related java packages it's access by default
is disabled and the autocompletion does not appear.
In order to fix this problem visit
[this](http://stackoverflow.com/questions/13155734/eclipse-cant-recognize-com-sun-net-httpserver-httpserver-package)
stackoverflow question. Second Answer :)

## REST API
### On for Interval
Turn the lamp on for the specified interval

- URL:
  /lamp/OnI
- Method:
  `POST`
- JSON params:

| Parameter  | Value           | Description                      |
|:----------:|:---------------:|:-------------------------------- |
| id         | String two char | the id of the destination lamp   |
| command    | Integer         | the interval in which lamp is on |

### Turn on/off  
Turn the lamp on or off generally (No interval specified)  

- URL:
  /lamp/turn
- Method:
  `POST`
- JSON params:

| Parameter  | Value           | Description                       |
|:----------:|:---------------:|:--------------------------------- |
| id         | String two char | the id of the destination lamp    |
| status     | Boolean         | `true` for on and `false` for off |

## Architecture
To see the awesome architecture of this project visit [AoLab/Lamp](https://github.com/AoLab/Lamp) 
